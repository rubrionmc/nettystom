// Package declaration for this file
package net.minestom.server.utils.chunk;

// Import of a required class
import net.minestom.server.coordinate.CoordConversion;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class ChunkCache implements Block.Getter {
    // Code statement
    private final Instance instance;
    // Code statement
    private @Nullable Chunk chunk;

    // Code statement
    private final @Nullable Block defaultBlock;

    // Code statement
    public ChunkCache(Instance instance, @Nullable Chunk chunk,
                      // Annotation for the following element
                      @Nullable Block defaultBlock) {
        // Access to the current/parent object
        this.instance = instance;
        // Access to the current/parent object
        this.chunk = chunk;
        // Access to the current/parent object
        this.defaultBlock = defaultBlock;
    // End of a block/expression
    }

    // Start of a method/block
    public ChunkCache(Instance instance, @Nullable Chunk chunk) {
        // Calls a method
        this(instance, chunk, Block.AIR);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @UnknownNullability Block getBlock(int x, int y, int z, Condition condition) {
        // Assigns a value
        Chunk chunk = this.chunk;
        // Calls a method
        final int chunkX = CoordConversion.globalToChunk(x);
        // Calls a method
        final int chunkZ = CoordConversion.globalToChunk(z);
        // Branch: checks a condition
        if (chunk == null || !chunk.isLoaded() ||
                // Start of a method/block
                chunk.getChunkX() != chunkX || chunk.getChunkZ() != chunkZ) {
            // Access to the current/parent object
            this.chunk = chunk = this.instance.getChunk(chunkX, chunkZ);
        // End of a block/expression
        }
        // Branch: checks a condition
        if (chunk != null) {
            // Calls a method
            chunk.lockReadLock();
            // Exception handling
            try {
                // Returns a value to the caller
                return chunk.getBlock(x, y, z, condition);
            // Start of a method/block
            } finally {
                // Calls a method
                chunk.unlockReadLock();
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else return defaultBlock;
    // End of a block/expression
    }
// End of a block/expression
}
