// Package declaration for this file
package net.minestom.server.monitoring;

// Import of a required class
import it.unimi.dsi.fastutil.longs.Long2LongMap;
// Import of a required class
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.TextComponent;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.utils.MathUtils;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.slf4j.Logger;
// Import of a required class
import org.slf4j.LoggerFactory;

// Import of a required class
import java.lang.management.ManagementFactory;
// Import of a required class
import java.lang.management.ThreadInfo;
// Import of a required class
import java.lang.management.ThreadMXBean;
// Import of a required class
import java.time.Duration;
// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.Collections;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;

// Static import of a member
import static net.minestom.server.MinecraftServer.THREAD_NAME_TICK;
// Static import of a member
import static net.minestom.server.MinecraftServer.THREAD_NAME_TICK_SCHEDULER;

/**
 * Small monitoring tools that can be used to check the current memory usage and Minestom threads CPU usage.
 * <p>
 * Needs to be enabled with {@link #enable(Duration)}. Memory can then be accessed with {@link #getUsedMemory()}
 * and the CPUs usage with {@link #getResultMap()} or {@link #getCpuMonitoringMessage()}.
 * <p>
 * Be aware that this is not the most accurate method, you should use a proper java profiler depending on your needs.
 */
// Type declaration (class/interface/enum/record)
public final class BenchmarkManager {
    // Calls a method
    private final static Logger LOGGER = LoggerFactory.getLogger(BenchmarkManager.class);
    // Calls a method
    private static final ThreadMXBean THREAD_MX_BEAN = ManagementFactory.getThreadMXBean();
    // Calls a method
    private static final List<String> THREADS = new ArrayList<>();

    // Start of a method/block
    static {
        // Calls a method
        THREADS.add(THREAD_NAME_TICK_SCHEDULER);
        // Calls a method
        THREADS.add(THREAD_NAME_TICK);
    // End of a block/expression
    }

    // Calls a method
    private final Long2LongMap lastCpuTimeMap = new Long2LongOpenHashMap();
    // Calls a method
    private final Long2LongMap lastUserTimeMap = new Long2LongOpenHashMap();
    // Calls a method
    private final Long2LongMap lastWaitedMap = new Long2LongOpenHashMap();
    // Calls a method
    private final Long2LongMap lastBlockedMap = new Long2LongOpenHashMap();
    // Calls a method
    private final Map<String, ThreadResult> resultMap = new ConcurrentHashMap<>();

    // Assigns a value
    private boolean enabled = false;
    // Assigns a value
    private volatile boolean stop = false;
    // Code statement
    private long time;

    // Start of a method/block
    public void enable(Duration duration) {
        // Calls a method
        Check.stateCondition(enabled, "A benchmark is already running, please disable it first.");
        // Exception handling
        try {
            // Calls a method
            THREAD_MX_BEAN.setThreadContentionMonitoringEnabled(true);
            // Calls a method
            THREAD_MX_BEAN.setThreadCpuTimeEnabled(true);
        // Start of a method/block
        } catch (Throwable e) {
            // Likely unsupported by the JVM (e.g. Substrate VM)
            // Calls a method
            LOGGER.warn("Could not enable thread monitoring", e);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Access to the current/parent object
        this.time = duration.toMillis();

        // Assigns a value
        final Thread thread = new Thread(null, () -> {
            // Loop: repeats a block
            while (!stop) {
                // Calls a method
                refreshData();
                // Exception handling
                try {
                    // Calls a method
                    Thread.sleep(time);
                // Start of a method/block
                } catch (InterruptedException e) {
                    // Calls a method
                    MinecraftServer.getExceptionManager().handleException(e);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Assigns a value
            stop = false;
        // Code statement
        }, MinecraftServer.THREAD_NAME_BENCHMARK);
        // Calls a method
        thread.setDaemon(true);
        // Calls a method
        thread.start();

        // Access to the current/parent object
        this.enabled = true;
    // End of a block/expression
    }

    // Start of a method/block
    public void disable() {
        // Access to the current/parent object
        this.stop = true;
        // Access to the current/parent object
        this.enabled = false;
    // End of a block/expression
    }

    // Start of a method/block
    public void addThreadMonitor(String threadName) {
        // Calls a method
        THREADS.add(threadName);
    // End of a block/expression
    }

    /**
     * Gets the heap memory used by the server in bytes.
     *
     * @return the memory used by the server
     */
    // Start of a method/block
    public long getUsedMemory() {
        // Returns a value to the caller
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    // End of a block/expression
    }

    // Start of a method/block
    public Map<String, ThreadResult> getResultMap() {
        // Returns a value to the caller
        return Collections.unmodifiableMap(resultMap);
    // End of a block/expression
    }

    // Start of a method/block
    public Component getCpuMonitoringMessage() {
        // Branch: checks a condition
        if (!enabled) return Component.text("CPU monitoring is disabled");
        // Calls a method
        TextComponent.Builder benchmarkMessage = Component.text();
        // Loop: repeats a block
        for (var resultEntry : resultMap.entrySet()) {
            // Calls a method
            final String name = resultEntry.getKey();
            // Calls a method
            final ThreadResult result = resultEntry.getValue();

            // Calls a method
            benchmarkMessage.append(Component.text(name, NamedTextColor.GRAY));
            // Calls a method
            benchmarkMessage.append(Component.text(": "));
            // Calls a method
            benchmarkMessage.append(Component.text(MathUtils.round(result.getCpuPercentage(), 2), NamedTextColor.YELLOW));
            // Calls a method
            benchmarkMessage.append(Component.text("% CPU ", NamedTextColor.YELLOW));
            // Calls a method
            benchmarkMessage.append(Component.text(MathUtils.round(result.getUserPercentage(), 2), NamedTextColor.RED));
            // Calls a method
            benchmarkMessage.append(Component.text("% USER ", NamedTextColor.RED));
            // Calls a method
            benchmarkMessage.append(Component.text(MathUtils.round(result.getBlockedPercentage(), 2), NamedTextColor.LIGHT_PURPLE));
            // Calls a method
            benchmarkMessage.append(Component.text("% BLOCKED ", NamedTextColor.LIGHT_PURPLE));
            // Calls a method
            benchmarkMessage.append(Component.text(MathUtils.round(result.getWaitedPercentage(), 2), NamedTextColor.GREEN));
            // Calls a method
            benchmarkMessage.append(Component.text("% WAITED ", NamedTextColor.GREEN));
            // Calls a method
            benchmarkMessage.append(Component.newline());
        // End of a block/expression
        }
        // Returns a value to the caller
        return benchmarkMessage.build();
    // End of a block/expression
    }

    // Start of a method/block
    private void refreshData() {
        // Calls a method
        ThreadInfo[] threadInfo = THREAD_MX_BEAN.getThreadInfo(THREAD_MX_BEAN.getAllThreadIds());
        // Loop: repeats a block
        for (ThreadInfo threadInfo2 : threadInfo) {
            // Branch: checks a condition
            if (threadInfo2 == null) continue; // Can happen if the thread does not exist
            // Calls a method
            final String name = threadInfo2.getThreadName();
            // Branch: checks a condition
            if (THREADS.stream().noneMatch(name::startsWith)) continue;

            // Calls a method
            final long id = threadInfo2.getThreadId();

            // Calls a method
            final long lastCpuTime = lastCpuTimeMap.getOrDefault(id, 0L);
            // Calls a method
            final long lastUserTime = lastUserTimeMap.getOrDefault(id, 0L);
            // Calls a method
            final long lastWaitedTime = lastWaitedMap.getOrDefault(id, 0L);
            // Calls a method
            final long lastBlockedTime = lastBlockedMap.getOrDefault(id, 0L);

            // Calls a method
            final long blockedTime = threadInfo2.getBlockedTime();
            // Calls a method
            final long waitedTime = threadInfo2.getWaitedTime();
            // Calls a method
            final long cpuTime = THREAD_MX_BEAN.getThreadCpuTime(id);
            // Calls a method
            final long userTime = THREAD_MX_BEAN.getThreadUserTime(id);

            // Calls a method
            lastCpuTimeMap.put(id, cpuTime);
            // Calls a method
            lastUserTimeMap.put(id, userTime);
            // Calls a method
            lastWaitedMap.put(id, waitedTime);
            // Calls a method
            lastBlockedMap.put(id, blockedTime);

            // Calls a method
            final double totalCpuTime = (double) (cpuTime - lastCpuTime) / 1000000D;
            // Calls a method
            final double totalUserTime = (double) (userTime - lastUserTime) / 1000000D;
            // Assigns a value
            final long totalBlocked = blockedTime - lastBlockedTime;
            // Assigns a value
            final long totalWaited = waitedTime - lastWaitedTime;

            // Calls a method
            final double cpuPercentage = totalCpuTime / (double) time * 100L;
            // Calls a method
            final double userPercentage = totalUserTime / (double) time * 100L;
            // Calls a method
            final double waitedPercentage = totalWaited / (double) time * 100L;
            // Calls a method
            final double blockedPercentage = totalBlocked / (double) time * 100L;

            // Calls a method
            ThreadResult threadResult = new ThreadResult(cpuPercentage, userPercentage, waitedPercentage, blockedPercentage);
            // Calls a method
            resultMap.put(name, threadResult);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
