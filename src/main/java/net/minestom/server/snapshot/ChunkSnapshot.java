// Package declaration for this file
package net.minestom.server.snapshot;

// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.tag.TagReadable;
// Import of a required class
import net.minestom.server.world.biome.Biome;

// Import of a required class
import java.util.Collection;

// Type declaration (class/interface/enum/record)
public sealed interface ChunkSnapshot extends Snapshot, Block.Getter, Biome.Getter, TagReadable
        // Start of a method/block
        permits SnapshotImpl.Chunk {
    // Calls a method
    int chunkX();

    // Calls a method
    int chunkZ();

    // Calls a method
    InstanceSnapshot instance();

    // Calls a method
    Collection<EntitySnapshot> entities();
// End of a block/expression
}
