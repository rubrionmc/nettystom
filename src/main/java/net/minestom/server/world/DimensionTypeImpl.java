// Déclaration du paquet de ce fichier
package net.minestom.server.world;

// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTag;
// Import d'une classe nécessaire
import net.minestom.server.utils.IntProvider;
// Import d'une classe nécessaire
import net.minestom.server.world.attribute.EnvironmentAttributeMap;
// Import d'une classe nécessaire
import net.minestom.server.world.clock.WorldClock;
// Import d'une classe nécessaire
import net.minestom.server.world.timeline.Timeline;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
record DimensionTypeImpl(
        // Instruction de code
        boolean hasFixedTime,
        // Instruction de code
        boolean hasSkylight,
        // Instruction de code
        boolean hasCeiling,
        // Instruction de code
        boolean hasEnderDragonFight,
        // Instruction de code
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
        RegistryTag<Timeline> timelines,
        // Annotation pour l'élément suivant
        @Nullable RegistryKey<WorldClock> defaultClock
// Début d'une méthode/d'un bloc
) implements DimensionType {
// Fin d'un bloc/d'une expression
}
