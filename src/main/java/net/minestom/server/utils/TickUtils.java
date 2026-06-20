// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Import of a required class
import java.time.Duration;

/**
 * Tick related utilities.
 */
// Type declaration (class/interface/enum/record)
public final class TickUtils {
    /**
     * Number of ticks per second for the default Java-edition client.
     */
    // Assigns a value
    public static final int CLIENT_TPS = 20;

    /**
     * Length of time per tick for the default Java-edition client.
     */
    // Assigns a value
    public static final int CLIENT_TICK_MS = 50;

    /**
     * Creates a number of ticks from a given duration, based on {@link MinecraftServer#TICK_MS}.
     *
     * @param duration the duration
     * @return the number of ticks
     * @throws IllegalArgumentException if duration is negative
     */
    // Start of a method/block
    public static int fromDuration(Duration duration) {
        // Returns a value to the caller
        return TickUtils.fromDuration(duration, MinecraftServer.TICK_MS);
    // End of a block/expression
    }

    /**
     * Creates a number of ticks from a given duration.
     *
     * @param duration  the duration
     * @param msPerTick the number of milliseconds per tick
     * @return the number of ticks
     * @throws IllegalArgumentException if duration is negative
     */
    // Start of a method/block
    public static int fromDuration(Duration duration, int msPerTick) {
        // Calls a method
        Check.argCondition(duration.isNegative(), "Duration cannot be negative");
        // Returns a value to the caller
        return (int) (duration.toMillis() / msPerTick);
    // End of a block/expression
    }
// End of a block/expression
}
