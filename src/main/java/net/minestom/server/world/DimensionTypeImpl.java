// Package declaration for this file
package net.minestom.server.world;

// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.registry.RegistryTag;
// Import of a required class
import net.minestom.server.utils.IntProvider;
// Import of a required class
import net.minestom.server.world.attribute.EnvironmentAttributeMap;
// Import of a required class
import net.minestom.server.world.clock.WorldClock;
// Import of a required class
import net.minestom.server.world.timeline.Timeline;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
record DimensionTypeImpl(
        // Code statement
        boolean hasFixedTime,
        // Code statement
        boolean hasSkylight,
        // Code statement
        boolean hasCeiling,
        // Code statement
        boolean hasEnderDragonFight,
        // Code statement
        double coordinateScale,
        // Code statement
        int minY,
        // Code statement
        int height,
        // Code statement
        int logicalHeight,
        // Code statement
        String infiniburn,
        // Code statement
        float ambientLight,
        // Code statement
        IntProvider monsterSpawnLightLevel,
        // Code statement
        int monsterSpawnBlockLightLimit,
        // Code statement
        Skybox skybox,
        // Code statement
        CardinalLight cardinalLight,
        // Code statement
        EnvironmentAttributeMap attributes,
        // Code statement
        RegistryTag<Timeline> timelines,
        // Annotation for the following element
        @Nullable RegistryKey<WorldClock> defaultClock
// Start of a method/block
) implements DimensionType {
// End of a block/expression
}
