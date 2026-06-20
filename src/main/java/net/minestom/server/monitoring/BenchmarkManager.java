// Déclaration du paquet de ce fichier
package net.minestom.server.monitoring;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.longs.Long2LongMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.TextComponent;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.slf4j.Logger;
// Import d'une classe nécessaire
import org.slf4j.LoggerFactory;

// Import d'une classe nécessaire
import java.lang.management.ManagementFactory;
// Import d'une classe nécessaire
import java.lang.management.ThreadInfo;
// Import d'une classe nécessaire
import java.lang.management.ThreadMXBean;
// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.Collections;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;

// Import statique d'un membre
import static net.minestom.server.MinecraftServer.THREAD_NAME_TICK;
// Import statique d'un membre
import static net.minestom.server.MinecraftServer.THREAD_NAME_TICK_SCHEDULER;

/**
 * Small monitoring tools that can be used to check the current memory usage and Minestom threads CPU usage.
 * <p>
 * Needs to be enabled with {@link #enable(Duration)}. Memory can then be accessed with {@link #getUsedMemory()}
 * and the CPUs usage with {@link #getResultMap()} or {@link #getCpuMonitoringMessage()}.
 * <p>
 * Be aware that this is not the most accurate method, you should use a proper java profiler depending on your needs.
 */
// Déclaration de type (classe/interface/enum/record)
public final class BenchmarkManager {
    // Appelle une méthode
    private final static Logger LOGGER = LoggerFactory.getLogger(BenchmarkManager.class);
    // Appelle une méthode
    private static final ThreadMXBean THREAD_MX_BEAN = ManagementFactory.getThreadMXBean();
    // Appelle une méthode
    private static final List<String> THREADS = new ArrayList<>();

    // Début d'une méthode/d'un bloc
    static {
        // Appelle une méthode
        THREADS.add(THREAD_NAME_TICK_SCHEDULER);
        // Appelle une méthode
        THREADS.add(THREAD_NAME_TICK);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    private final Long2LongMap lastCpuTimeMap = new Long2LongOpenHashMap();
    // Appelle une méthode
    private final Long2LongMap lastUserTimeMap = new Long2LongOpenHashMap();
    // Appelle une méthode
    private final Long2LongMap lastWaitedMap = new Long2LongOpenHashMap();
    // Appelle une méthode
    private final Long2LongMap lastBlockedMap = new Long2LongOpenHashMap();
    // Appelle une méthode
    private final Map<String, ThreadResult> resultMap = new ConcurrentHashMap<>();

    // Affecte une valeur
    private boolean enabled = false;
    // Affecte une valeur
    private volatile boolean stop = false;
    // Instruction de code
    private long time;

    // Début d'une méthode/d'un bloc
    public void enable(Duration duration) {
        // Appelle une méthode
        Check.stateCondition(enabled, "A benchmark is already running, please disable it first.");
        // Gestion des exceptions
        try {
            // Appelle une méthode
            THREAD_MX_BEAN.setThreadContentionMonitoringEnabled(true);
            // Appelle une méthode
            THREAD_MX_BEAN.setThreadCpuTimeEnabled(true);
        // Début d'une méthode/d'un bloc
        } catch (Throwable e) {
            // Likely unsupported by the JVM (e.g. Substrate VM)
            // Appelle une méthode
            LOGGER.warn("Could not enable thread monitoring", e);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Accès à l'objet courant/parent
        this.time = duration.toMillis();

        // Affecte une valeur
        final Thread thread = new Thread(null, () -> {
            // Boucle : répète un bloc
            while (!stop) {
                // Appelle une méthode
                refreshData();
                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    Thread.sleep(time);
                // Début d'une méthode/d'un bloc
                } catch (InterruptedException e) {
                    // Appelle une méthode
                    MinecraftServer.getExceptionManager().handleException(e);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Affecte une valeur
            stop = false;
        // Instruction de code
        }, MinecraftServer.THREAD_NAME_BENCHMARK);
        // Appelle une méthode
        thread.setDaemon(true);
        // Appelle une méthode
        thread.start();

        // Accès à l'objet courant/parent
        this.enabled = true;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void disable() {
        // Accès à l'objet courant/parent
        this.stop = true;
        // Accès à l'objet courant/parent
        this.enabled = false;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void addThreadMonitor(String threadName) {
        // Appelle une méthode
        THREADS.add(threadName);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the heap memory used by the server in bytes.
     *
     * @return the memory used by the server
     */
    // Début d'une méthode/d'un bloc
    public long getUsedMemory() {
        // Renvoie une valeur à l'appelant
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Map<String, ThreadResult> getResultMap() {
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableMap(resultMap);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Component getCpuMonitoringMessage() {
        // Embranchement : vérifie une condition
        if (!enabled) return Component.text("CPU monitoring is disabled");
        // Appelle une méthode
        TextComponent.Builder benchmarkMessage = Component.text();
        // Boucle : répète un bloc
        for (var resultEntry : resultMap.entrySet()) {
            // Appelle une méthode
            final String name = resultEntry.getKey();
            // Appelle une méthode
            final ThreadResult result = resultEntry.getValue();

            // Appelle une méthode
            benchmarkMessage.append(Component.text(name, NamedTextColor.GRAY));
            // Appelle une méthode
            benchmarkMessage.append(Component.text(": "));
            // Appelle une méthode
            benchmarkMessage.append(Component.text(MathUtils.round(result.getCpuPercentage(), 2), NamedTextColor.YELLOW));
            // Appelle une méthode
            benchmarkMessage.append(Component.text("% CPU ", NamedTextColor.YELLOW));
            // Appelle une méthode
            benchmarkMessage.append(Component.text(MathUtils.round(result.getUserPercentage(), 2), NamedTextColor.RED));
            // Appelle une méthode
            benchmarkMessage.append(Component.text("% USER ", NamedTextColor.RED));
            // Appelle une méthode
            benchmarkMessage.append(Component.text(MathUtils.round(result.getBlockedPercentage(), 2), NamedTextColor.LIGHT_PURPLE));
            // Appelle une méthode
            benchmarkMessage.append(Component.text("% BLOCKED ", NamedTextColor.LIGHT_PURPLE));
            // Appelle une méthode
            benchmarkMessage.append(Component.text(MathUtils.round(result.getWaitedPercentage(), 2), NamedTextColor.GREEN));
            // Appelle une méthode
            benchmarkMessage.append(Component.text("% WAITED ", NamedTextColor.GREEN));
            // Appelle une méthode
            benchmarkMessage.append(Component.newline());
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return benchmarkMessage.build();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void refreshData() {
        // Appelle une méthode
        ThreadInfo[] threadInfo = THREAD_MX_BEAN.getThreadInfo(THREAD_MX_BEAN.getAllThreadIds());
        // Boucle : répète un bloc
        for (ThreadInfo threadInfo2 : threadInfo) {
            // Embranchement : vérifie une condition
            if (threadInfo2 == null) continue; // Can happen if the thread does not exist
            // Appelle une méthode
            final String name = threadInfo2.getThreadName();
            // Embranchement : vérifie une condition
            if (THREADS.stream().noneMatch(name::startsWith)) continue;

            // Appelle une méthode
            final long id = threadInfo2.getThreadId();

            // Appelle une méthode
            final long lastCpuTime = lastCpuTimeMap.getOrDefault(id, 0L);
            // Appelle une méthode
            final long lastUserTime = lastUserTimeMap.getOrDefault(id, 0L);
            // Appelle une méthode
            final long lastWaitedTime = lastWaitedMap.getOrDefault(id, 0L);
            // Appelle une méthode
            final long lastBlockedTime = lastBlockedMap.getOrDefault(id, 0L);

            // Appelle une méthode
            final long blockedTime = threadInfo2.getBlockedTime();
            // Appelle une méthode
            final long waitedTime = threadInfo2.getWaitedTime();
            // Appelle une méthode
            final long cpuTime = THREAD_MX_BEAN.getThreadCpuTime(id);
            // Appelle une méthode
            final long userTime = THREAD_MX_BEAN.getThreadUserTime(id);

            // Appelle une méthode
            lastCpuTimeMap.put(id, cpuTime);
            // Appelle une méthode
            lastUserTimeMap.put(id, userTime);
            // Appelle une méthode
            lastWaitedMap.put(id, waitedTime);
            // Appelle une méthode
            lastBlockedMap.put(id, blockedTime);

            // Appelle une méthode
            final double totalCpuTime = (double) (cpuTime - lastCpuTime) / 1000000D;
            // Appelle une méthode
            final double totalUserTime = (double) (userTime - lastUserTime) / 1000000D;
            // Affecte une valeur
            final long totalBlocked = blockedTime - lastBlockedTime;
            // Affecte une valeur
            final long totalWaited = waitedTime - lastWaitedTime;

            // Appelle une méthode
            final double cpuPercentage = totalCpuTime / (double) time * 100L;
            // Appelle une méthode
            final double userPercentage = totalUserTime / (double) time * 100L;
            // Appelle une méthode
            final double waitedPercentage = totalWaited / (double) time * 100L;
            // Appelle une méthode
            final double blockedPercentage = totalBlocked / (double) time * 100L;

            // Appelle une méthode
            ThreadResult threadResult = new ThreadResult(cpuPercentage, userPercentage, waitedPercentage, blockedPercentage);
            // Appelle une méthode
            resultMap.put(name, threadResult);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
