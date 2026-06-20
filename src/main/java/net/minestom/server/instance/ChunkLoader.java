// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.instance.anvil.AnvilLoader;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.concurrent.Phaser;

/**
 * Interface implemented to change the way chunks are loaded/saved.
 * <p>
 * See {@link AnvilLoader} for the default implementation used in {@link InstanceContainer}.
 */
// Type declaration (class/interface/enum/record)
public interface ChunkLoader {

    /**
     * Returns the no op chunk loader
     * @return the no op loader.
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    static ChunkLoader noop() {
        // Returns a value to the caller
        return NoopChunkLoaderImpl.INSTANCE;
    // End of a block/expression
    }

    /**
     * Loads instance data from the loader.
     *
     * @param instance the instance to retrieve the data from
     */
    // Start of a method/block
    default void loadInstance(Instance instance) {
    // End of a block/expression
    }

    /**
     * Loads a {@link Chunk}, all blocks should be set since the {@link net.minestom.server.instance.generator.Generator} is not applied.
     *
     * @param instance the {@link Instance} where the {@link Chunk} belong
     * @param chunkX   the chunk X
     * @param chunkZ   the chunk Z
     * @return the chunk, or null if not present
     */
    // Annotation for the following element
    @Nullable Chunk loadChunk(Instance instance, int chunkX, int chunkZ);

    // Start of a method/block
    default void saveInstance(Instance instance) {
    // End of a block/expression
    }

    /**
     * Saves a {@link Chunk} with an optional callback for when it is done.
     *
     * @param chunk the {@link Chunk} to save
     */
    // Calls a method
    void saveChunk(Chunk chunk);

    /**
     * Saves multiple chunks with an optional callback for when it is done.
     * <p>
     * Implementations need to check {@link #supportsParallelSaving()} to support the feature if possible.
     *
     * @param chunks the chunks to save
     */
    // Start of a method/block
    default void saveChunks(Collection<Chunk> chunks) {
        // Branch: checks a condition
        if (supportsParallelSaving()) {
            // Calls a method
            Phaser phaser = new Phaser(1);
            // Loop: repeats a block
            for (Chunk chunk : chunks) {
                // Calls a method
                phaser.register();
                // Start of a method/block
                Thread.startVirtualThread(() -> {
                    // Exception handling
                    try {
                        // Calls a method
                        saveChunk(chunk);
                        // Calls a method
                        phaser.arriveAndDeregister();
                    // Start of a method/block
                    } catch (Throwable e) {
                        // Calls a method
                        MinecraftServer.getExceptionManager().handleException(e);
                    // End of a block/expression
                    }
                // End of a block/expression
                });
            // End of a block/expression
            }
            // Calls a method
            phaser.arriveAndAwaitAdvance();
        // Alternative branch of the condition
        } else {
            // Loop: repeats a block
            for (Chunk chunk : chunks) {
                // Calls a method
                saveChunk(chunk);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Supports for instance/chunk saving in virtual threads.
     *
     * @return true if the chunk loader supports parallel saving
     */
    // Start of a method/block
    default boolean supportsParallelSaving() {
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    /**
     * Supports for instance/chunk loading in virtual threads.
     *
     * @return true if the chunk loader supports parallel loading
     */
    // Start of a method/block
    default boolean supportsParallelLoading() {
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    /**
     * Called when a chunk is unloaded, so that this chunk loader can unload any resource it is holding.
     * Note: Minestom currently has no way to determine whether the chunk comes from this loader, so you may get
     * unload requests for chunks not created by the loader.
     *
     * @param chunk the chunk to unload
     */
    // Start of a method/block
    default void unloadChunk(Chunk chunk) {
    // End of a block/expression
    }
// End of a block/expression
}
