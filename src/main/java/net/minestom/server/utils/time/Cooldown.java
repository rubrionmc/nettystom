// Package declaration for this file
package net.minestom.server.utils.time;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.time.temporal.ChronoUnit;
// Import of a required class
import java.time.temporal.TemporalUnit;

// Type declaration (class/interface/enum/record)
public final class Cooldown {
    // Code statement
    private final Duration duration;
    // Code statement
    private final TemporalUnit temporalUnit;
    // if this cooldown object as a lastUpdate set
    // Code statement
    private boolean hasLastUpdate;
    // Code statement
    private long lastUpdate;

    /**
     * Creates a cooldown with a measurement unit of {@link ChronoUnit#MILLIS}
     */
    // Start of a method/block
    public Cooldown(Duration duration) {
        // Calls a method
        this(duration, ChronoUnit.MILLIS);
    // End of a block/expression
    }

    /**
     * Creates a cooldown with a given unit of measurement.
     * <p>
     * All calls to {@link #refreshLastUpdate(long)} and {@link #isReady(long)} must pass values in the given unit.
     *
     * @param duration     the duration of the cooldown
     * @param temporalUnit the unit of measurement
     */
    // Start of a method/block
    public Cooldown(Duration duration, TemporalUnit temporalUnit) {
        // Access to the current/parent object
        this.duration = duration;
        // Access to the current/parent object
        this.temporalUnit = temporalUnit;
        // Access to the current/parent object
        this.hasLastUpdate = false;
    // End of a block/expression
    }

    /**
     * @return the unit of measurement
     */
    // Start of a method/block
    public TemporalUnit getTemporalUnit() {
        // Returns a value to the caller
        return temporalUnit;
    // End of a block/expression
    }

    // Start of a method/block
    public Duration getDuration() {
        // Returns a value to the caller
        return this.duration;
    // End of a block/expression
    }

    /**
     * @param lastUpdate the time of the last update, in nanos
     */
    // Start of a method/block
    public void refreshLastUpdate(long lastUpdate) {
        // Access to the current/parent object
        this.hasLastUpdate = true;
        // Access to the current/parent object
        this.lastUpdate = lastUpdate;
    // End of a block/expression
    }

    /**
     * Checks if the cooldown is ready again
     *
     * @param time the time, in nanos
     */
    // Start of a method/block
    public boolean isReady(long time) {
        // Branch: checks a condition
        if (!hasLastUpdate) return true;
        // Returns a value to the caller
        return !hasCooldown(temporalUnit, time, lastUpdate, duration);
    // End of a block/expression
    }

    /**
     * Gets if something is in cooldown based on a {@code currentTime}.
     *
     * @param currentTime  the current time in milliseconds
     * @param lastUpdate   the last update in milliseconds
     * @param cooldownUnit the time unit of the cooldown
     * @param cooldown     the value of the cooldown
     * @return true if the cooldown is in progress, false otherwise
     */
    // Start of a method/block
    public static boolean hasCooldown(long currentTime, long lastUpdate, TemporalUnit cooldownUnit, long cooldown) {
        // Returns a value to the caller
        return hasCooldown(currentTime, lastUpdate, Duration.of(cooldown, cooldownUnit));
    // End of a block/expression
    }

    /**
     * Gets if something is in cooldown based on a {@code currentTime}.
     *
     * @param currentTime the current time in milliseconds
     * @param lastUpdate  the last update in milliseconds
     * @param duration    the cooldown
     * @return true if the cooldown is in progress, false otherwise
     */
    // Start of a method/block
    public static boolean hasCooldown(long currentTime, long lastUpdate, Duration duration) {
        // Returns a value to the caller
        return hasCooldown(ChronoUnit.MILLIS, currentTime, lastUpdate, duration);
    // End of a block/expression
    }

    /**
     * Gets if something is in cooldown based on a {@code currentTime}.
     *
     * @param temporalUnit the {@link TemporalUnit} of {@code currentTime} and {@code lastUpdate}
     * @param currentTime  the current time in milliseconds
     * @param lastUpdate   the last update in milliseconds
     * @param cooldownUnit the time unit of the cooldown
     * @param cooldown     the value of the cooldown
     * @return true if the cooldown is in progress, false otherwise
     */
    // Start of a method/block
    public static boolean hasCooldown(TemporalUnit temporalUnit, long currentTime, long lastUpdate, TemporalUnit cooldownUnit, long cooldown) {
        // Returns a value to the caller
        return hasCooldown(temporalUnit, currentTime, lastUpdate, Duration.of(cooldown, cooldownUnit));
    // End of a block/expression
    }

    /**
     * Gets if something is in cooldown based on a {@code currentTime}.
     *
     * @param temporalUnit the {@link TemporalUnit} of {@code currentTime} and {@code lastUpdate}
     * @param currentTime  the current time in the given {@code temporalUnit}
     * @param lastUpdate   the last update in the given {@code temporalUnit}
     * @param duration     the cooldown
     * @return true if the cooldown is in progress, false otherwise
     */
    // Start of a method/block
    public static boolean hasCooldown(TemporalUnit temporalUnit, long currentTime, long lastUpdate, Duration duration) {
        // Returns a value to the caller
        return Duration.of(currentTime - lastUpdate, temporalUnit).compareTo(duration) < 0;
    // End of a block/expression
    }

    /**
     * Gets if something is in cooldown based on the current time ({@link System#nanoTime()}).
     *
     * @param lastUpdate   the last update in {@link System#nanoTime()}
     * @param temporalUnit the time unit of the cooldown
     * @param cooldown     the value of the cooldown
     * @return true if the cooldown is in progress, false otherwise
     */
    // Start of a method/block
    public static boolean hasCooldown(long lastUpdate, TemporalUnit temporalUnit, int cooldown) {
        // Returns a value to the caller
        return hasCooldown(ChronoUnit.NANOS, System.nanoTime(), lastUpdate, temporalUnit, cooldown);
    // End of a block/expression
    }
// End of a block/expression
}
