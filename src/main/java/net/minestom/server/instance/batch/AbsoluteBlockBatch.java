// Package declaration for this file
package net.minestom.server.instance.batch;

// Import of a required class
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
// Import of a required class
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
// Import of a required class
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
// Import of a required class
import net.minestom.server.coordinate.CoordConversion;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.InstanceContainer;
// Import of a required class
import net.minestom.server.instance.LightingChunk;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.HashSet;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;
// Import of a required class
import java.util.concurrent.CountDownLatch;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;
// Import of a required class
import java.util.function.Consumer;

/**
 * A {@link Batch} which can be used when changes are required across chunk borders,
 * but the changes do not need any translation. If translation is required,
 * use a {@link RelativeBlockBatch} instead.
 * <p>
 * Coordinates are relative to the world origin.
 *
 * @see Batch
 * @see RelativeBlockBatch
 */
// Type declaration (class/interface/enum/record)
public class AbsoluteBlockBatch implements Batch<Consumer<AbsoluteBlockBatch>> {

    // In the form of <Chunk Index, Batch>
    // Calls a method
    private final Long2ObjectMap<ChunkBatch> chunkBatchesMap = new Long2ObjectOpenHashMap<>();

    // Available for other implementations to handle.
    // Code statement
    protected final CountDownLatch readyLatch;
    // Code statement
    private final BatchOption options;

    // Calls a method
    private volatile BatchOption inverseOption = new BatchOption();

    // Start of a method/block
    public AbsoluteBlockBatch() {
        // Calls a method
        this(new BatchOption());
    // End of a block/expression
    }

    // Start of a method/block
    public AbsoluteBlockBatch(BatchOption options) {
        // Calls a method
        this(options, true);
    // End of a block/expression
    }

    // Start of a method/block
    private AbsoluteBlockBatch(BatchOption options, boolean ready) {
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
        final int chunkX = CoordConversion.globalToChunk(x);
        // Calls a method
        final int chunkZ = CoordConversion.globalToChunk(z);
        // Calls a method
        final long chunkIndex = CoordConversion.chunkIndex(chunkX, chunkZ);

        // Code statement
        final ChunkBatch chunkBatch;
        // Start of a method/block
        synchronized (chunkBatchesMap) {
            // Calls a method
            chunkBatch = chunkBatchesMap.computeIfAbsent(chunkIndex, i -> new ChunkBatch(this.options));
        // End of a block/expression
        }

        // Calls a method
        final int relativeX = x - (chunkX * Chunk.CHUNK_SIZE_X);
        // Calls a method
        final int relativeZ = z - (chunkZ * Chunk.CHUNK_SIZE_Z);
        // Calls a method
        chunkBatch.setBlock(relativeX, y, relativeZ, block);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void clear() {
        // Start of a method/block
        synchronized (chunkBatchesMap) {
            // Access to the current/parent object
            this.chunkBatchesMap.clear();
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
     * Applies this batch to the given instance.
     *
     * @param instance The instance in which the batch should be applied
     * @param callback The callback to be executed when the batch is applied
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public @UnknownNullability AbsoluteBlockBatch apply(Instance instance, @Nullable Consumer<@UnknownNullability AbsoluteBlockBatch> callback) {
        // Returns a value to the caller
        return apply(instance, callback, true);
    // End of a block/expression
    }

    /**
     * Applies this batch to the given instance, and execute the callback immediately when the
     * blocks have been applied, in an unknown thread.
     *
     * @param instance The instance in which the batch should be applied
     * @param callback The callback to be executed when the batch is applied
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Start of a method/block
    public @UnknownNullability AbsoluteBlockBatch unsafeApply(Instance instance, @Nullable Consumer<@UnknownNullability AbsoluteBlockBatch> callback) {
        // Returns a value to the caller
        return apply(instance, callback, false);
    // End of a block/expression
    }

    /**
     * Applies this batch to the given instance, and execute the callback depending on safeCallback.
     *
     * @param instance     The instance in which the batch should be applied
     * @param callback     The callback to be executed when the batch is applied
     * @param safeCallback If true, the callback will be executed in the next instance update.
     *                     Otherwise, it will be executed immediately upon completion
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Start of a method/block
    protected @UnknownNullability AbsoluteBlockBatch apply(Instance instance, @Nullable Consumer<@UnknownNullability AbsoluteBlockBatch> callback, boolean safeCallback) {
        // Branch: checks a condition
        if (!this.options.isUnsafeApply()) this.awaitReady();

        // Calls a method
        final AbsoluteBlockBatch inverse = this.options.shouldCalculateInverse() ? new AbsoluteBlockBatch(inverseOption) : null;
        // Start of a method/block
        synchronized (chunkBatchesMap) {
            // Calls a method
            AtomicInteger counter = new AtomicInteger();
            // Calls a method
            Set<Chunk> updated = ConcurrentHashMap.newKeySet();

            // Loop: repeats a block
            for (var entry : Long2ObjectMaps.fastIterable(chunkBatchesMap)) {
                // Calls a method
                final long chunkIndex = entry.getLongKey();
                // Calls a method
                final int chunkX = CoordConversion.chunkIndexGetX(chunkIndex);
                // Calls a method
                final int chunkZ = CoordConversion.chunkIndexGetZ(chunkIndex);
                // Calls a method
                final ChunkBatch batch = entry.getValue();
                // Assigns a value
                ChunkBatch chunkInverse = batch.apply(instance, chunkX, chunkZ, c -> {
                    // Calls a method
                    final boolean isLast = counter.incrementAndGet() == chunkBatchesMap.size();
                    // Execute the callback if this was the last chunk to process
                    // Branch: checks a condition
                    if (isLast) {
                        // Branch: checks a condition
                        if (inverse != null) inverse.readyLatch.countDown();
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
                                instance.scheduleNextTick(inst -> callback.accept(inverse));
                            // Alternative branch of the condition
                            } else {
                                // Calls a method
                                callback.accept(inverse);
                            // End of a block/expression
                            }
                        // End of a block/expression
                        }

                        // Calls a method
                        Set<Chunk> expanded = new HashSet<>();
                        // Loop: repeats a block
                        for (Chunk chunk : updated) {
                            // Loop: repeats a block
                            for (int i = -1; i <= 1; ++i) {
                                // Loop: repeats a block
                                for (int j = -1; j <= 1; ++j) {
                                    // Calls a method
                                    Chunk toAdd = instance.getChunk(chunk.getChunkX() + i, chunk.getChunkZ() + j);
                                    // Branch: checks a condition
                                    if (toAdd != null) {
                                        // Calls a method
                                        expanded.add(toAdd);
                                    // End of a block/expression
                                    }
                                // End of a block/expression
                                }
                            // End of a block/expression
                            }
                        // End of a block/expression
                        }

                        // Update the chunk's light
                        // Loop: repeats a block
                        for (Chunk chunk : expanded) {
                            // Branch: checks a condition
                            if (chunk instanceof LightingChunk dc) {
                                // Calls a method
                                dc.sendLighting();
                            // End of a block/expression
                            }
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                });
                // Branch: checks a condition
                if (inverse != null) inverse.chunkBatchesMap.put(chunkIndex, chunkInverse);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Returns a value to the caller
        return inverse;
    // End of a block/expression
    }

    // Start of a method/block
    public BatchOption getInverseOption() {
        // Returns a value to the caller
        return inverseOption;
    // End of a block/expression
    }

    // Start of a method/block
    public void setInverseOption(BatchOption inverseOption) {
        // Access to the current/parent object
        this.inverseOption = inverseOption;
    // End of a block/expression
    }
// End of a block/expression
}
