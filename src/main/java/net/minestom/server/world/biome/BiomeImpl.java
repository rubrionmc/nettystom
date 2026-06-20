// Déclaration du paquet de ce fichier
package net.minestom.server.world.biome;

// Import d'une classe nécessaire
import net.minestom.server.world.attribute.EnvironmentAttributeMap;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
record BiomeImpl(
        // Instruction de code
        boolean hasPrecipitation,
        // Instruction de code
        float temperature,
        // Instruction de code
        TemperatureModifier temperatureModifier,
        // Instruction de code
        float downfall,
        // Instruction de code
        EnvironmentAttributeMap attributes,
        // Instruction de code
        BiomeEffects effects
// Début d'une méthode/d'un bloc
) implements Biome {

    // Début d'une méthode/d'un bloc
    public BiomeImpl {
        // Appelle une méthode
        Objects.requireNonNull(temperatureModifier, "temperatureModifier");
        // Appelle une méthode
        Objects.requireNonNull(attributes, "attributes");
        // Appelle une méthode
        Objects.requireNonNull(effects, "effects");
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
