// Package declaration for this file
package net.minestom.server.world.timeline;

// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.world.attribute.EnvironmentAttribute;
// Import of a required class
import net.minestom.server.world.clock.ClockTimeMarker;
// Import of a required class
import net.minestom.server.world.clock.WorldClock;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
public record TimelineImpl(
        // Code statement
        RegistryKey<WorldClock> clock,
        // Annotation for the following element
        @Nullable Integer periodTicks,
        // Code statement
        Map<EnvironmentAttribute<?>, Track<?, ?>> tracks,
        // Code statement
        Map<RegistryKey<ClockTimeMarker>, TimeMarkerInfo> timeMarkers
// Start of a method/block
) implements Timeline {

    // Start of a method/block
    public TimelineImpl {
        // Calls a method
        Objects.requireNonNull(clock, "clock");
        // Calls a method
        tracks = Map.copyOf(tracks);
        // Calls a method
        timeMarkers = Map.copyOf(timeMarkers);
    // End of a block/expression
    }
// End of a block/expression
}
