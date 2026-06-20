// Package declaration for this file
package net.minestom.server.utils.time;

// Import of a required class
import net.minestom.server.MinecraftServer;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.time.temporal.Temporal;
// Import of a required class
import java.time.temporal.TemporalUnit;

/**
 * A TemporalUnit that represents one tick.
 */
// Type declaration (class/interface/enum/record)
public final class Tick implements TemporalUnit {
    /**
     * A TemporalUnit representing the server tick. This is defined using
     * {@link MinecraftServer#TICK_MS}.
     */
    // Calls a method
    public static final Tick SERVER_TICKS = new Tick(MinecraftServer.TICK_MS);

    /**
     * A TemporalUnit representing the client tick. This is always equal to 50ms.
     */
    // Calls a method
    public static final Tick CLIENT_TICKS = new Tick(50);

    // Code statement
    private final long milliseconds;
    // Code statement
    private final int tps;

    /**
     * Creates a new tick.
     *
     * @param length the length of the tick in milliseconds
     */
    // Start of a method/block
    private Tick(long length) {
        // Branch: checks a condition
        if (length <= 0) {
            // Throws an exception
            throw new IllegalArgumentException("length cannot be negative");
        // End of a block/expression
        }

        // Access to the current/parent object
        this.milliseconds = length;
        // Access to the current/parent object
        this.tps = Math.toIntExact(Duration.ofSeconds(1).dividedBy(Duration.ofMillis(this.milliseconds)));
    // End of a block/expression
    }

    /**
     * Creates a duration from an amount of ticks.
     *
     * @param ticks the amount of ticks
     * @return the duration
     */
    // Start of a method/block
    public static Duration server(long ticks) {
        // Returns a value to the caller
        return Duration.of(ticks, SERVER_TICKS);
    // End of a block/expression
    }

    /**
     * Creates a duration from an amount of client-side ticks.
     *
     * @param ticks the amount of ticks
     * @return the duration
     */
    // Start of a method/block
    public static Duration client(long ticks) {
        // Returns a value to the caller
        return Duration.of(ticks, CLIENT_TICKS);
    // End of a block/expression
    }

    /**
     * Gets the number of whole ticks that occur in the provided duration. Note that this
     * method returns an {@code int} as this is the unit that Minecraft stores ticks in.
     *
     * @param duration the duration
     * @return the number of whole ticks in this duration
     * @throws ArithmeticException if the duration is zero or an overflow occurs
     */
    // Start of a method/block
    public int fromDuration(Duration duration) {
        // Returns a value to the caller
        return Math.toIntExact(duration.dividedBy(this.getDuration()));
    // End of a block/expression
    }

    /**
     * Gets the whole number of these ticks that occur in one second.
     *
     * @return the number
     */
    // Start of a method/block
    public int getTicksPerSecond() {
        // Returns a value to the caller
        return this.tps;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Duration getDuration() {
        // Returns a value to the caller
        return Duration.ofMillis(this.milliseconds);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isDurationEstimated() {
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isDateBased() {
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isTimeBased() {
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings("unchecked") // following ChronoUnit#addTo
    // Annotation for the following element
    @Override
    // Start of a method/block
    public <R extends Temporal> R addTo(R temporal, long amount) {
        // Returns a value to the caller
        return (R) temporal.plus(amount, this);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public long between(Temporal start, Temporal end) {
        // Returns a value to the caller
        return start.until(end, this);
    // End of a block/expression
    }
// End of a block/expression
}