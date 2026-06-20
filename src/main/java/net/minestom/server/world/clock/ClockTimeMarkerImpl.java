// Package declaration for this file
package net.minestom.server.world.clock;

// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
public record ClockTimeMarkerImpl(RegistryKey<WorldClock> clock, int ticks, @Nullable Integer periodTicks,
                                  // Start of a method/block
                                  boolean showInCommands) implements ClockTimeMarker {
    // Start of a method/block
    public ClockTimeMarkerImpl {
        // Calls a method
        Objects.requireNonNull(clock, "clock");
        // Calls a method
        Check.argCondition(ticks < 0, "ticks must be positive");
    // End of a block/expression
    }
// End of a block/expression
}
