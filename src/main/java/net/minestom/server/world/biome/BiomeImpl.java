// Package declaration for this file
package net.minestom.server.world.biome;

// Import of a required class
import net.minestom.server.world.attribute.EnvironmentAttributeMap;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
record BiomeImpl(
        // Code statement
        boolean hasPrecipitation,
        // Code statement
        float temperature,
        // Code statement
        TemperatureModifier temperatureModifier,
        // Code statement
        float downfall,
        // Code statement
        EnvironmentAttributeMap attributes,
        // Code statement
        BiomeEffects effects
// Start of a method/block
) implements Biome {

    // Start of a method/block
    public BiomeImpl {
        // Calls a method
        Objects.requireNonNull(temperatureModifier, "temperatureModifier");
        // Calls a method
        Objects.requireNonNull(attributes, "attributes");
        // Calls a method
        Objects.requireNonNull(effects, "effects");
    // End of a block/expression
    }

// End of a block/expression
}
