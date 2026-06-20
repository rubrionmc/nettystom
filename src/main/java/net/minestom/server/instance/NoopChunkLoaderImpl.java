// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
record NoopChunkLoaderImpl() implements ChunkLoader {
    // Calls a method
    static final NoopChunkLoaderImpl INSTANCE = new NoopChunkLoaderImpl();

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable Chunk loadChunk(Instance instance, int chunkX, int chunkZ) {
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void saveChunk(Chunk chunk) {
        // Empty
    // End of a block/expression
    }
// End of a block/expression
}
