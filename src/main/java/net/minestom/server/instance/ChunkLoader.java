// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.instance.anvil.AnvilLoader;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.concurrent.Phaser;

/**
 * Interface implemented to change the way chunks are loaded/saved.
 * <p>
 * See {@link AnvilLoader} for the default implementation used in {@link InstanceContainer}.
 */
// Déclaration de type (classe/interface/enum/record)
public interface ChunkLoader {

    /**
     * Returns the no op chunk loader
     * @return the no op loader.
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    static ChunkLoader noop() {
        // Renvoie une valeur à l'appelant
        return NoopChunkLoaderImpl.INSTANCE;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Loads instance data from the loader.
     *
     * @param instance the instance to retrieve the data from
     */
    // Début d'une méthode/d'un bloc
    default void loadInstance(Instance instance) {
    // Fin d'un bloc/d'une expression
    }

    /**
     * Loads a {@link Chunk}, all blocks should be set since the {@link net.minestom.server.instance.generator.Generator} is not applied.
     *
     * @param instance the {@link Instance} where the {@link Chunk} belong
     * @param chunkX   the chunk X
     * @param chunkZ   the chunk Z
     * @return the chunk, or null if not present
     */
    // Annotation pour l'élément suivant
    @Nullable Chunk loadChunk(Instance instance, int chunkX, int chunkZ);

    // Début d'une méthode/d'un bloc
    default void saveInstance(Instance instance) {
    // Fin d'un bloc/d'une expression
    }

    /**
     * Saves a {@link Chunk} with an optional callback for when it is done.
     *
     * @param chunk the {@link Chunk} to save
     */
    // Appelle une méthode
    void saveChunk(Chunk chunk);

    /**
     * Saves multiple chunks with an optional callback for when it is done.
     * <p>
     * Implementations need to check {@link #supportsParallelSaving()} to support the feature if possible.
     *
     * @param chunks the chunks to save
     */
    // Début d'une méthode/d'un bloc
    default void saveChunks(Collection<Chunk> chunks) {
        // Embranchement : vérifie une condition
        if (supportsParallelSaving()) {
            // Appelle une méthode
            Phaser phaser = new Phaser(1);
            // Boucle : répète un bloc
            for (Chunk chunk : chunks) {
                // Appelle une méthode
                phaser.register();
                // Début d'une méthode/d'un bloc
                Thread.startVirtualThread(() -> {
                    // Gestion des exceptions
                    try {
                        // Appelle une méthode
                        saveChunk(chunk);
                        // Appelle une méthode
                        phaser.arriveAndDeregister();
                    // Début d'une méthode/d'un bloc
                    } catch (Throwable e) {
                        // Appelle une méthode
                        MinecraftServer.getExceptionManager().handleException(e);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                });
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            phaser.arriveAndAwaitAdvance();
        // Branche alternative de la condition
        } else {
            // Boucle : répète un bloc
            for (Chunk chunk : chunks) {
                // Appelle une méthode
                saveChunk(chunk);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Supports for instance/chunk saving in virtual threads.
     *
     * @return true if the chunk loader supports parallel saving
     */
    // Début d'une méthode/d'un bloc
    default boolean supportsParallelSaving() {
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Supports for instance/chunk loading in virtual threads.
     *
     * @return true if the chunk loader supports parallel loading
     */
    // Début d'une méthode/d'un bloc
    default boolean supportsParallelLoading() {
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Called when a chunk is unloaded, so that this chunk loader can unload any resource it is holding.
     * Note: Minestom currently has no way to determine whether the chunk comes from this loader, so you may get
     * unload requests for chunks not created by the loader.
     *
     * @param chunk the chunk to unload
     */
    // Début d'une méthode/d'un bloc
    default void unloadChunk(Chunk chunk) {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
