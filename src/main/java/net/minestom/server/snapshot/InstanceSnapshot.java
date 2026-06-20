// Package declaration for this file
package net.minestom.server.snapshot;

// Import of a required class
import net.minestom.server.coordinate.CoordConversion;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.tag.TagReadable;
// Import of a required class
import net.minestom.server.world.DimensionType;
// Import of a required class
import net.minestom.server.world.biome.Biome;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
public sealed interface InstanceSnapshot extends Snapshot, Block.Getter, Biome.Getter, TagReadable
        // Start of a method/block
        permits SnapshotImpl.Instance {
    // Calls a method
    RegistryKey<DimensionType> dimensionType();

    // Calls a method
    long worldAge();

    // Calls a method
    long time();

    // Annotation for the following element
    @Override
    // Start of a method/block
    default @UnknownNullability Block getBlock(int x, int y, int z, Condition condition) {
        // Calls a method
        ChunkSnapshot chunk = chunk(CoordConversion.globalToChunk(x), CoordConversion.globalToChunk(z));
        // Returns a value to the caller
        return Objects.requireNonNull(chunk).getBlock(x, y, z, condition);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default RegistryKey<Biome> getBiome(int x, int y, int z) {
        // Calls a method
        ChunkSnapshot chunk = chunk(CoordConversion.globalToChunk(x), CoordConversion.globalToChunk(z));
        // Returns a value to the caller
        return Objects.requireNonNull(chunk).getBiome(x, y, z);
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable ChunkSnapshot chunk(int chunkX, int chunkZ);

    // Start of a method/block
    default @Nullable ChunkSnapshot chunkAt(Point point) {
        // Returns a value to the caller
        return chunk(point.chunkX(), point.chunkZ());
    // End of a block/expression
    }

    // Calls a method
    Collection<ChunkSnapshot> chunks();

    // Calls a method
    Collection<EntitySnapshot> entities();

    // Calls a method
    ServerSnapshot server();
// End of a block/expression
}
