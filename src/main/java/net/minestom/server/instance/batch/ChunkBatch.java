// Déclaration du paquet de ce fichier
package net.minestom.server.instance.batch;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.IntArraySet;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.IntSet;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.CoordConversion;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.InstanceContainer;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.utils.callback.OptionalCallback;
// Import d'une classe nécessaire
import net.minestom.server.utils.chunk.ChunkCallback;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;
// Import d'une classe nécessaire
import org.slf4j.Logger;
// Import d'une classe nécessaire
import org.slf4j.LoggerFactory;

// Import d'une classe nécessaire
import java.util.concurrent.CountDownLatch;

/**
 * A Batch used when all the block changed are contained inside a single chunk.
 * If more than one chunk is needed, use an {@link AbsoluteBlockBatch} instead.
 * <p>
 * The batch can be placed in any chunk in any instance, however it will always remain
 * aligned to a chunk border. If completely translatable block changes are needed, use a
 * {@link RelativeBlockBatch} instead.
 * <p>
 * Coordinates are relative to the chunk (0-15) instead of world coordinates.
 *
 * @see Batch
 */
// Déclaration de type (classe/interface/enum/record)
public class ChunkBatch implements Batch<ChunkCallback> {

    // Appelle une méthode
    private static final Logger LOGGER = LoggerFactory.getLogger(ChunkBatch.class);

    // Appelle une méthode
    private final Int2ObjectMap<Block> blocks = new Int2ObjectOpenHashMap<>();
    // Available for other implementations to handle.
    // Instruction de code
    protected final CountDownLatch readyLatch;
    // Instruction de code
    private final BatchOption options;

    // Début d'une méthode/d'un bloc
    public ChunkBatch() {
        // Appelle une méthode
        this(new BatchOption());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ChunkBatch(BatchOption options) {
        // Appelle une méthode
        this(options, true);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private ChunkBatch(BatchOption options, boolean ready) {
        // Accès à l'objet courant/parent
        this.readyLatch = new CountDownLatch(ready ? 0 : 1);
        // Accès à l'objet courant/parent
        this.options = options;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setBlock(int x, int y, int z, Block block) {
        // Appelle une méthode
        final int index = CoordConversion.chunkBlockIndex(x, y, z);
        // Début d'une méthode/d'un bloc
        synchronized (blocks) {
            // Accès à l'objet courant/parent
            this.blocks.put(index, block);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void clear() {
        // Début d'une méthode/d'un bloc
        synchronized (blocks) {
            // Accès à l'objet courant/parent
            this.blocks.clear();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isReady() {
        // Renvoie une valeur à l'appelant
        return this.readyLatch.getCount() == 0;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void awaitReady() {
        // Gestion des exceptions
        try {
            // Accès à l'objet courant/parent
            this.readyLatch.await();
        // Début d'une méthode/d'un bloc
        } catch (InterruptedException e) {
            // Lève une exception
            throw new RuntimeException("#awaitReady interrupted!", e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Apply this batch to chunk (0, 0).
     *
     * @param instance The instance in which the batch should be applied
     * @param callback The callback to be executed when the batch is applied
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @UnknownNullability ChunkBatch apply(Instance instance, @Nullable ChunkCallback callback) {
        // Renvoie une valeur à l'appelant
        return apply(instance, 0, 0, callback);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Apply this batch to the given chunk.
     *
     * @param instance The instance in which the batch should be applied
     * @param chunkX   The x chunk coordinate of the target chunk
     * @param chunkZ   The z chunk coordinate of the target chunk
     * @param callback The callback to be executed when the batch is applied.
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Début d'une méthode/d'un bloc
    public @UnknownNullability ChunkBatch apply(Instance instance, int chunkX, int chunkZ, @Nullable ChunkCallback callback) {
        // Appelle une méthode
        final Chunk chunk = instance.getChunk(chunkX, chunkZ);
        // Embranchement : vérifie une condition
        if (chunk == null) {
            // Instruction de code
            LOGGER.warn("Unable to apply ChunkBatch to unloaded chunk ({}, {}) in {}.",
                    // Appelle une méthode
                    chunkX, chunkZ, instance.getUuid());
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return apply(instance, chunk, callback);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Apply this batch to the given chunk.
     *
     * @param instance The instance in which the batch should be applied
     * @param chunk    The target chunk
     * @param callback The callback to be executed when the batch is applied
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Début d'une méthode/d'un bloc
    public @UnknownNullability ChunkBatch apply(Instance instance, Chunk chunk, @Nullable ChunkCallback callback) {
        // Renvoie une valeur à l'appelant
        return apply(instance, chunk, callback, true);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Apply this batch to the given chunk, and execute the callback
     * immediately when the blocks have been applied, in an unknown thread.
     *
     * @param instance The instance in which the batch should be applied
     * @param chunk    The target chunk
     * @param callback The callback to be executed when the batch is applied
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Début d'une méthode/d'un bloc
    public @UnknownNullability ChunkBatch unsafeApply(Instance instance, Chunk chunk, @Nullable ChunkCallback callback) {
        // Renvoie une valeur à l'appelant
        return apply(instance, chunk, callback, false);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Apply this batch to the given chunk, and execute the callback depending on safeCallback.
     *
     * @param instance     The instance in which the batch should be applied
     * @param chunk        The target chunk
     * @param callback     The callback to be executed when the batch is applied
     * @param safeCallback If true, the callback will be executed in the next instance update.
     *                     Otherwise, it will be executed immediately upon completion
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Instruction de code
    protected @UnknownNullability ChunkBatch apply(Instance instance,
                                                   // Instruction de code
                                                   Chunk chunk, @Nullable ChunkCallback callback,
                                                   // Début d'une méthode/d'un bloc
                                                   boolean safeCallback) {
        // Embranchement : vérifie une condition
        if (!this.options.isUnsafeApply()) this.awaitReady();

        // Appelle une méthode
        final ChunkBatch inverse = this.options.shouldCalculateInverse() ? new ChunkBatch(options, false) : null;
        // Appelle une méthode
        BLOCK_BATCH_POOL.execute(() -> singleThreadFlush(instance, chunk, inverse, callback, safeCallback));
        // Renvoie une valeur à l'appelant
        return inverse;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies this batch in the current thread, executing the callback upon completion.
     */
    // Instruction de code
    private void singleThreadFlush(Instance instance, Chunk chunk, @Nullable ChunkBatch inverse,
                                   // Annotation pour l'élément suivant
                                   @Nullable ChunkCallback callback, boolean safeCallback) {
        // Gestion des exceptions
        try {
            // Embranchement : vérifie une condition
            if (!chunk.isLoaded()) {
                // Instruction de code
                LOGGER.warn("Unable to apply ChunkBatch to unloaded chunk ({}, {}) in {}.",
                        // Appelle une méthode
                        chunk.getChunkX(), chunk.getChunkZ(), instance.getUuid());
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }

            // Embranchement : vérifie une condition
            if (this.options.isFullChunk()) {
                // Clear the chunk
                // Appelle une méthode
                chunk.reset();
            // Fin d'un bloc/d'une expression
            }

            // Embranchement : vérifie une condition
            if (blocks.isEmpty()) {
                // Nothing to flush
                // Appelle une méthode
                OptionalCallback.execute(callback, chunk);
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            final IntSet sections = new IntArraySet();
            // Appelle une méthode
            chunk.lockWriteLock();
            // Gestion des exceptions
            try {
                // Début d'une méthode/d'un bloc
                synchronized (blocks) {
                    // Boucle : répète un bloc
                    for (var entry : blocks.int2ObjectEntrySet()) {
                        // Appelle une méthode
                        final int position = entry.getIntKey();
                        // Appelle une méthode
                        final Block block = entry.getValue();
                        // Appelle une méthode
                        final int section = apply(chunk, position, block, inverse);
                        // Appelle une méthode
                        sections.add(section);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Début d'une méthode/d'un bloc
            } finally {
                // Appelle une méthode
                chunk.unlockWriteLock();
            // Fin d'un bloc/d'une expression
            }

            // Embranchement : vérifie une condition
            if (inverse != null) inverse.readyLatch.countDown();
            // Appelle une méthode
            updateChunk(instance, chunk, sections, callback, safeCallback);
        // Début d'une méthode/d'un bloc
        } catch (Exception e) {
            // Appelle une méthode
            e.printStackTrace();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies a single block change given a chunk and a value in the described format.
     *
     * @param chunk The chunk to apply the change
     * @param index the block position computed using {@link CoordConversion#chunkBlockIndex(int, int, int)}
     * @param block the block to place
     * @return The chunk section which the block was placed
     */
    // Début d'une méthode/d'un bloc
    private int apply(Chunk chunk, int index, Block block, @Nullable ChunkBatch inverse) {
        // Appelle une méthode
        final int x = CoordConversion.chunkBlockIndexGetX(index);
        // Appelle une méthode
        final int y = CoordConversion.chunkBlockIndexGetY(index);
        // Appelle une méthode
        final int z = CoordConversion.chunkBlockIndexGetZ(index);
        // Embranchement : vérifie une condition
        if (inverse != null) {
            // Appelle une méthode
            Block prevBlock = chunk.getBlock(x, y, z);
            // Appelle une méthode
            inverse.setBlock(x, y, z, prevBlock);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        chunk.setBlock(x, y, z, block);
        // Renvoie une valeur à l'appelant
        return CoordConversion.globalToChunk(y);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates the given chunk for all of its viewers, and executes the callback.
     */
    // Début d'une méthode/d'un bloc
    private void updateChunk(Instance instance, Chunk chunk, IntSet updatedSections, @Nullable ChunkCallback callback, boolean safeCallback) {
        // Refresh chunk for viewers
        // Embranchement : vérifie une condition
        if (options.shouldSendUpdate()) {
            // TODO update all sections from `updatedSections`
            // Appelle une méthode
            chunk.sendChunk();
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (instance instanceof InstanceContainer) {
            // FIXME: put method in Instance instead
            // Appelle une méthode
            ((InstanceContainer) instance).refreshLastBlockChangeTime();
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (callback != null) {
            // Embranchement : vérifie une condition
            if (safeCallback) {
                // Appelle une méthode
                instance.scheduleNextTick(inst -> callback.accept(chunk));
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                callback.accept(chunk);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}