// Package declaration for this file
package net.minestom.server.world.clock;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public sealed interface ClockTimeMarker extends ClockTimeMarkers permits ClockTimeMarkerImpl {
    // The default keys for ClockTimeMarker aren't a registry currently, just keys for a map.
    // Calls a method
    Codec<RegistryKey<ClockTimeMarker>> CODEC = RegistryKey.uncheckedCodec();

    // Code statement
    static ClockTimeMarker create(RegistryKey<WorldClock> clock, int ticks, @Nullable Integer periodTicks,
                                  // Start of a method/block
                                  boolean showInCommands) {
        // Returns a value to the caller
        return new ClockTimeMarkerImpl(clock, ticks, periodTicks, showInCommands);
    // End of a block/expression
    }

    // Start of a method/block
    static RegistryKey<ClockTimeMarker> key(Key key) {
        // Returns a value to the caller
        return RegistryKey.unsafeOf(key);
    // End of a block/expression
    }

    // Calls a method
    RegistryKey<WorldClock> clock();

    // Calls a method
    int ticks();

    // Annotation for the following element
    @Nullable Integer periodTicks();

    // Calls a method
    boolean showInCommands();
// End of a block/expression
}
