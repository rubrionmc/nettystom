// Package declaration for this file
package net.minestom.server.instance.batch;

// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import of a required class
import it.unimi.dsi.fastutil.ints.IntArraySet;
// Import of a required class
import it.unimi.dsi.fastutil.ints.IntSet;
// Import of a required class
import net.minestom.server.coordinate.CoordConversion;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.InstanceContainer;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.utils.callback.OptionalCallback;
// Import of a required class
import net.minestom.server.utils.chunk.ChunkCallback;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;
// Import of a required class
import org.slf4j.Logger;
// Import of a required class
import org.slf4j.LoggerFactory;

// Import of a required class
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
// Type declaration (class/interface/enum/record)
public class ChunkBatch implements Batch<ChunkCallback> {

    // Calls a method
    private static final Logger LOGGER = LoggerFactory.getLogger(ChunkBatch.class);

    // Calls a method
    private final Int2ObjectMap<Block> blocks = new Int2ObjectOpenHashMap<>();
    // Available for other implementations to handle.
    // Code statement
    protected final CountDownLatch readyLatch;
    // Code statement
    private final BatchOption options;

    // Start of a method/block
    public ChunkBatch() {
        // Calls a method
        this(new BatchOption());
    // End of a block/expression
    }

    // Start of a method/block
    public ChunkBatch(BatchOption options) {
        // Calls a method
        this(options, true);
    // End of a block/expression
    }

    // Start of a method/block
    private ChunkBatch(BatchOption options, boolean ready) {
        // Access to the current/parent object
        this.readyLatch = new CountDownLatch(ready ? 0 : 1);
        // Access to the current/parent object
        this.options = options;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setBlock(int x, int y, int z, Block block) {
        // Calls a method
        final int index = CoordConversion.chunkBlockIndex(x, y, z);
        // Start of a method/block
        synchronized (blocks) {
            // Access to the current/parent object
            this.blocks.put(index, block);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void clear() {
        // Start of a method/block
        synchronized (blocks) {
            // Access to the current/parent object
            this.blocks.clear();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isReady() {
        // Returns a value to the caller
        return this.readyLatch.getCount() == 0;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void awaitReady() {
        // Exception handling
        try {
            // Access to the current/parent object
            this.readyLatch.await();
        // Start of a method/block
        } catch (InterruptedException e) {
            // Throws an exception
            throw new RuntimeException("#awaitReady interrupted!", e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Apply this batch to chunk (0, 0).
     *
     * @param instance The instance in which the batch should be applied
     * @param callback The callback to be executed when the batch is applied
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public @UnknownNullability ChunkBatch apply(Instance instance, @Nullable ChunkCallback callback) {
        // Returns a value to the caller
        return apply(instance, 0, 0, callback);
    // End of a block/expression
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
    // Start of a method/block
    public @UnknownNullability ChunkBatch apply(Instance instance, int chunkX, int chunkZ, @Nullable ChunkCallback callback) {
        // Calls a method
        final Chunk chunk = instance.getChunk(chunkX, chunkZ);
        // Branch: checks a condition
        if (chunk == null) {
            // Code statement
            LOGGER.warn("Unable to apply ChunkBatch to unloaded chunk ({}, {}) in {}.",
                    // Calls a method
                    chunkX, chunkZ, instance.getUuid());
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
        // Returns a value to the caller
        return apply(instance, chunk, callback);
    // End of a block/expression
    }

    /**
     * Apply this batch to the given chunk.
     *
     * @param instance The instance in which the batch should be applied
     * @param chunk    The target chunk
     * @param callback The callback to be executed when the batch is applied
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Start of a method/block
    public @UnknownNullability ChunkBatch apply(Instance instance, Chunk chunk, @Nullable ChunkCallback callback) {
        // Returns a value to the caller
        return apply(instance, chunk, callback, true);
    // End of a block/expression
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
    // Start of a method/block
    public @UnknownNullability ChunkBatch unsafeApply(Instance instance, Chunk chunk, @Nullable ChunkCallback callback) {
        // Returns a value to the caller
        return apply(instance, chunk, callback, false);
    // End of a block/expression
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
    // Code statement
    protected @UnknownNullability ChunkBatch apply(Instance instance,
                                                   // Code statement
                                                   Chunk chunk, @Nullable ChunkCallback callback,
                                                   // Start of a method/block
                                                   boolean safeCallback) {
        // Branch: checks a condition
        if (!this.options.isUnsafeApply()) this.awaitReady();

        // Calls a method
        final ChunkBatch inverse = this.options.shouldCalculateInverse() ? new ChunkBatch(options, false) : null;
        // Calls a method
        BLOCK_BATCH_POOL.execute(() -> singleThreadFlush(instance, chunk, inverse, callback, safeCallback));
        // Returns a value to the caller
        return inverse;
    // End of a block/expression
    }

    /**
     * Applies this batch in the current thread, executing the callback upon completion.
     */
    // Code statement
    private void singleThreadFlush(Instance instance, Chunk chunk, @Nullable ChunkBatch inverse,
                                   // Annotation for the following element
                                   @Nullable ChunkCallback callback, boolean safeCallback) {
        // Exception handling
        try {
            // Branch: checks a condition
            if (!chunk.isLoaded()) {
                // Code statement
                LOGGER.warn("Unable to apply ChunkBatch to unloaded chunk ({}, {}) in {}.",
                        // Calls a method
                        chunk.getChunkX(), chunk.getChunkZ(), instance.getUuid());
                // Returns a value to the caller
                return;
            // End of a block/expression
            }

            // Branch: checks a condition
            if (this.options.isFullChunk()) {
                // Clear the chunk
                // Calls a method
                chunk.reset();
            // End of a block/expression
            }

            // Branch: checks a condition
            if (blocks.isEmpty()) {
                // Nothing to flush
                // Calls a method
                OptionalCallback.execute(callback, chunk);
                // Returns a value to the caller
                return;
            // End of a block/expression
            }

            // Calls a method
            final IntSet sections = new IntArraySet();
            // Calls a method
            chunk.lockWriteLock();
            // Exception handling
            try {
                // Start of a method/block
                synchronized (blocks) {
                    // Loop: repeats a block
                    for (var entry : blocks.int2ObjectEntrySet()) {
                        // Calls a method
                        final int position = entry.getIntKey();
                        // Calls a method
                        final Block block = entry.getValue();
                        // Calls a method
                        final int section = apply(chunk, position, block, inverse);
                        // Calls a method
                        sections.add(section);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // Start of a method/block
            } finally {
                // Calls a method
                chunk.unlockWriteLock();
            // End of a block/expression
            }

            // Branch: checks a condition
            if (inverse != null) inverse.readyLatch.countDown();
            // Calls a method
            updateChunk(instance, chunk, sections, callback, safeCallback);
        // Start of a method/block
        } catch (Exception e) {
            // Calls a method
            e.printStackTrace();
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Applies a single block change given a chunk and a value in the described format.
     *
     * @param chunk The chunk to apply the change
     * @param index the block position computed using {@link CoordConversion#chunkBlockIndex(int, int, int)}
     * @param block the block to place
     * @return The chunk section which the block was placed
     */
    // Start of a method/block
    private int apply(Chunk chunk, int index, Block block, @Nullable ChunkBatch inverse) {
        // Calls a method
        final int x = CoordConversion.chunkBlockIndexGetX(index);
        // Calls a method
        final int y = CoordConversion.chunkBlockIndexGetY(index);
        // Calls a method
        final int z = CoordConversion.chunkBlockIndexGetZ(index);
        // Branch: checks a condition
        if (inverse != null) {
            // Calls a method
            Block prevBlock = chunk.getBlock(x, y, z);
            // Calls a method
            inverse.setBlock(x, y, z, prevBlock);
        // End of a block/expression
        }
        // Calls a method
        chunk.setBlock(x, y, z, block);
        // Returns a value to the caller
        return CoordConversion.globalToChunk(y);
    // End of a block/expression
    }

    /**
     * Updates the given chunk for all of its viewers, and executes the callback.
     */
    // Start of a method/block
    private void updateChunk(Instance instance, Chunk chunk, IntSet updatedSections, @Nullable ChunkCallback callback, boolean safeCallback) {
        // Refresh chunk for viewers
        // Branch: checks a condition
        if (options.shouldSendUpdate()) {
            // TODO update all sections from `updatedSections`
            // Calls a method
            chunk.sendChunk();
        // End of a block/expression
        }

        // Branch: checks a condition
        if (instance instanceof InstanceContainer) {
            // FIXME: put method in Instance instead
            // Calls a method
            ((InstanceContainer) instance).refreshLastBlockChangeTime();
        // End of a block/expression
        }

        // Branch: checks a condition
        if (callback != null) {
            // Branch: checks a condition
            if (safeCallback) {
                // Calls a method
                instance.scheduleNextTick(inst -> callback.accept(chunk));
            // Alternative branch of the condition
            } else {
                // Calls a method
                callback.accept(chunk);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}