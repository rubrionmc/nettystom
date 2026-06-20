// Déclaration du paquet de ce fichier
package net.minestom.server.thread;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.ServerProcess;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class TickSchedulerThread extends MinestomThread {
    // Affecte une valeur
    private static final long TICK_TIME_NANOS = 1_000_000_000L / ServerFlag.SERVER_TICKS_PER_SECOND;
    // Windows has an issue with periodically being unable to sleep for < ~16ms at a time
    // Affecte une valeur
    private static final long SLEEP_THRESHOLD = System.getProperty("os.name", "")
            // Appelle une méthode
            .toLowerCase().startsWith("windows") ? 17 : 2;

    // Instruction de code
    private final ServerProcess serverProcess;

    // Début d'une méthode/d'un bloc
    public TickSchedulerThread(ServerProcess serverProcess) {
        // Accès à l'objet courant/parent
        super(MinecraftServer.THREAD_NAME_TICK_SCHEDULER);
        // Accès à l'objet courant/parent
        this.serverProcess = serverProcess;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void run() {
        // Affecte une valeur
        long ticks = 0;
        // Appelle une méthode
        long baseTime = System.nanoTime();
        // Boucle : répète un bloc
        while (serverProcess.isAlive()) {
            // Appelle une méthode
            final long tickStart = System.nanoTime();
            // Gestion des exceptions
            try {
                // Appelle une méthode
                serverProcess.ticker().tick(tickStart);
            // Début d'une méthode/d'un bloc
            } catch (Throwable e) {
                // Appelle une méthode
                serverProcess.exception().handleException(e);
            // Fin d'un bloc/d'une expression
            }

            // Instruction de code
            ticks++;
            // Affecte une valeur
            long nextTickTime = baseTime + ticks * TICK_TIME_NANOS;
            // Appelle une méthode
            waitUntilNextTick(nextTickTime);
            // Check if the server can not keep up with the tickrate
            // if it gets too far behind, reset the ticks & baseTime
            // to avoid running too many ticks at once
            // Embranchement : vérifie une condition
            if (System.nanoTime() > nextTickTime + TICK_TIME_NANOS * ServerFlag.SERVER_MAX_TICK_CATCH_UP) {
                // Appelle une méthode
                baseTime = System.nanoTime();
                // Affecte une valeur
                ticks = 0;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void waitUntilNextTick(long nextTickTimeNanos) {
        // Instruction de code
        long currentTime;
        // Boucle : répète un bloc
        while ((currentTime = System.nanoTime()) < nextTickTimeNanos) {
            // Affecte une valeur
            long remainingTime = nextTickTimeNanos - currentTime;
            // Sleep less the closer we are to the next tick
            // Affecte une valeur
            long remainingMilliseconds = remainingTime / 1_000_000L;
            // Embranchement : vérifie une condition
            if (remainingMilliseconds >= SLEEP_THRESHOLD) {
                // Appelle une méthode
                sleepThread(remainingMilliseconds / 2);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void sleepThread(long time) {
        // Gestion des exceptions
        try {
            // Appelle une méthode
            Thread.sleep(time);
        // Début d'une méthode/d'un bloc
        } catch (InterruptedException e) {
            // Appelle une méthode
            serverProcess.exception().handleException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
