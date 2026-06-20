// Déclaration du paquet de ce fichier
package net.minestom.server.world;

// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTag;
// Import d'une classe nécessaire
import net.minestom.server.utils.IntProvider;
// Import d'une classe nécessaire
import net.minestom.server.world.attribute.EnvironmentAttributeMap;
// Import d'une classe nécessaire
import net.minestom.server.world.timeline.Timeline;

// Déclaration de type (classe/interface/enum/record)
record DimensionTypeImpl(
        // Instruction de code
        boolean hasFixedTime,
        // Instruction de code
        boolean hasSkylight,
        // Instruction de code
        boolean hasCeiling,
        // Boucle : répète un bloc
        double coordinateScale,
        // Instruction de code
        int minY,
        // Instruction de code
        int height,
        // Instruction de code
        int logicalHeight,
        // Instruction de code
        String infiniburn,
        // Instruction de code
        float ambientLight,
        // Instruction de code
        IntProvider monsterSpawnLightLevel,
        // Instruction de code
        int monsterSpawnBlockLightLimit,
        // Instruction de code
        Skybox skybox,
        // Instruction de code
        CardinalLight cardinalLight,
        // Instruction de code
        EnvironmentAttributeMap attributes,
        // Instruction de code
        RegistryTag<Timeline> timelines
// Début d'une méthode/d'un bloc
) implements DimensionType {
// Fin d'un bloc/d'une expression
}
