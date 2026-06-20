// Package declaration for this file
package net.minestom.server.utils.chunk;

// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.Instance;

/**
 * Used to customize which type of {@link Chunk} an implementation should use.
 */
// Annotation for the following element
@FunctionalInterface
// Type declaration (class/interface/enum/record)
public interface ChunkSupplier {

    /**
     * Creates a {@link Chunk} object.
     *
     * @param instance the linked instance
     * @param chunkX   the chunk X
     * @param chunkZ   the chunk Z
     * @return a newly {@link Chunk} object, cannot be null
     */
    // Calls a method
    Chunk createChunk(Instance instance, int chunkX, int chunkZ);
// End of a block/expression
}
