// Package declaration for this file
package net.minestom.server.utils.time;

// Import of a required class
import java.time.temporal.ChronoUnit;
// Import of a required class
import java.time.temporal.TemporalUnit;

// Type declaration (class/interface/enum/record)
public final class TimeUnit {
    // Assigns a value
    public static final TemporalUnit DAY = ChronoUnit.DAYS;
    // Assigns a value
    public static final TemporalUnit HOUR = ChronoUnit.HOURS;
    // Assigns a value
    public static final TemporalUnit MINUTE = ChronoUnit.MINUTES;
    // Assigns a value
    public static final TemporalUnit SECOND = ChronoUnit.SECONDS;
    // Assigns a value
    public static final TemporalUnit MILLISECOND = ChronoUnit.MILLIS;
    // Assigns a value
    public static final TemporalUnit SERVER_TICK = Tick.SERVER_TICKS;
    // Assigns a value
    public static final TemporalUnit CLIENT_TICK = Tick.CLIENT_TICKS;

    // Start of a method/block
    private TimeUnit() {
    // End of a block/expression
    }
// End of a block/expression
}
