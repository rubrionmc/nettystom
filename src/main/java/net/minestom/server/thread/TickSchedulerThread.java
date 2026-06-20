// Package declaration for this file
package net.minestom.server.thread;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.ServerProcess;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class TickSchedulerThread extends MinestomThread {
    // Assigns a value
    private static final long TICK_TIME_NANOS = 1_000_000_000L / ServerFlag.SERVER_TICKS_PER_SECOND;
    // Windows has an issue with periodically being unable to sleep for < ~16ms at a time
    // Assigns a value
    private static final long SLEEP_THRESHOLD = System.getProperty("os.name", "")
            // Calls a method
            .toLowerCase().startsWith("windows") ? 17 : 2;

    // Code statement
    private final ServerProcess serverProcess;

    // Start of a method/block
    public TickSchedulerThread(ServerProcess serverProcess) {
        // Access to the current/parent object
        super(MinecraftServer.THREAD_NAME_TICK_SCHEDULER);
        // Access to the current/parent object
        this.serverProcess = serverProcess;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void run() {
        // Assigns a value
        long ticks = 0;
        // Calls a method
        long baseTime = System.nanoTime();
        // Loop: repeats a block
        while (serverProcess.isAlive()) {
            // Calls a method
            final long tickStart = System.nanoTime();
            // Exception handling
            try {
                // Calls a method
                serverProcess.ticker().tick(tickStart);
            // Start of a method/block
            } catch (Throwable e) {
                // Calls a method
                serverProcess.exception().handleException(e);
            // End of a block/expression
            }

            // Code statement
            ticks++;
            // Assigns a value
            long nextTickTime = baseTime + ticks * TICK_TIME_NANOS;
            // Calls a method
            waitUntilNextTick(nextTickTime);
            // Check if the server can not keep up with the tickrate
            // if it gets too far behind, reset the ticks & baseTime
            // to avoid running too many ticks at once
            // Branch: checks a condition
            if (System.nanoTime() > nextTickTime + TICK_TIME_NANOS * ServerFlag.SERVER_MAX_TICK_CATCH_UP) {
                // Calls a method
                baseTime = System.nanoTime();
                // Assigns a value
                ticks = 0;
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void waitUntilNextTick(long nextTickTimeNanos) {
        // Code statement
        long currentTime;
        // Loop: repeats a block
        while ((currentTime = System.nanoTime()) < nextTickTimeNanos) {
            // Assigns a value
            long remainingTime = nextTickTimeNanos - currentTime;
            // Sleep less the closer we are to the next tick
            // Assigns a value
            long remainingMilliseconds = remainingTime / 1_000_000L;
            // Branch: checks a condition
            if (remainingMilliseconds >= SLEEP_THRESHOLD) {
                // Calls a method
                sleepThread(remainingMilliseconds / 2);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void sleepThread(long time) {
        // Exception handling
        try {
            // Calls a method
            Thread.sleep(time);
        // Start of a method/block
        } catch (InterruptedException e) {
            // Calls a method
            serverProcess.exception().handleException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
