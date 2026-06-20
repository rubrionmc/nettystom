// Package declaration for this file
package net.minestom.server.instance.batch;

// Import of a required class
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
// Import of a required class
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.function.Consumer;

/**
 * A {@link Batch} which can be used when changes are required across chunk borders, and
 * are going to be reused in different places. If translation is not required, {@link AbsoluteBlockBatch}
 * should be used instead for efficiency purposes.
 * <p>
 * Coordinates are relative to (0, 0, 0) with some limitations. All coordinates must
 * fit within a 16 bit integer of the first coordinate (32,767 blocks). If blocks must
 * be spread out over a larger area, an {@link AbsoluteBlockBatch} should be used.
 * <p>
 * All inverses are {@link AbsoluteBlockBatch}s and represent the inverse of the application
 * at the position which it was applied.
 * <p>
 * If a batch will be used multiple times at the same coordinate, it is suggested
 * to convert it to an {@link AbsoluteBlockBatch} and cache the result. Application
 * of absolute batches (currently) is significantly faster than their relative counterpart.
 *
 * @see Batch
 * @see AbsoluteBlockBatch
 */
// Type declaration (class/interface/enum/record)
public class RelativeBlockBatch implements Batch<Consumer<AbsoluteBlockBatch>> {
    // relative pos format: nothing/relative x/relative y/relative z (16/16/16/16 bits)

    // Need to be synchronized manually
    // Format: relative pos - block
    // Calls a method
    private final Long2ObjectMap<Block> blockIdMap = new Long2ObjectOpenHashMap<>();

    // Code statement
    private final BatchOption options;

    // Assigns a value
    private volatile boolean firstEntry = true;
    // Code statement
    private int offsetX, offsetY, offsetZ;

    // Start of a method/block
    public RelativeBlockBatch() {
        // Calls a method
        this(new BatchOption());
    // End of a block/expression
    }

    // Start of a method/block
    public RelativeBlockBatch(BatchOption options) {
        // Access to the current/parent object
        this.options = options;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setBlock(int x, int y, int z, Block block) {
        // Save the offsets if it is the first entry
        // Branch: checks a condition
        if (firstEntry) {
            // Access to the current/parent object
            this.firstEntry = false;

            // Access to the current/parent object
            this.offsetX = x;
            // Access to the current/parent object
            this.offsetY = y;
            // Access to the current/parent object
            this.offsetZ = z;
        // End of a block/expression
        }

        // Subtract offset
        // Code statement
        x -= offsetX;
        // Code statement
        y -= offsetY;
        // Code statement
        z -= offsetZ;

        // Verify that blocks are not too far from each other
        // Calls a method
        Check.argCondition(Math.abs(x) > Short.MAX_VALUE, "Relative x position may not be more than 16 bits long.");
        // Calls a method
        Check.argCondition(Math.abs(y) > Short.MAX_VALUE, "Relative y position may not be more than 16 bits long.");
        // Calls a method
        Check.argCondition(Math.abs(z) > Short.MAX_VALUE, "Relative z position may not be more than 16 bits long.");

        // Calls a method
        long pos = Short.toUnsignedLong((short)x);
        // Calls a method
        pos = (pos << 16) | Short.toUnsignedLong((short)y);
        // Calls a method
        pos = (pos << 16) | Short.toUnsignedLong((short)z);

        //final int block = (blockStateId << 16) | customBlockId;
        // Start of a method/block
        synchronized (blockIdMap) {
            // Access to the current/parent object
            this.blockIdMap.put(pos, block);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void clear() {
        // Start of a method/block
        synchronized (blockIdMap) {
            // Access to the current/parent object
            this.blockIdMap.clear();
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Applies this batch to the given instance at the origin (0, 0, 0) of the instance.
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
        return apply(instance, 0, 0, 0, callback);
    // End of a block/expression
    }

    /**
     * Applies this batch to the given instance at the given block position.
     *
     * @param instance The instance in which the batch should be applied
     * @param position The position to apply the batch
     * @param callback The callback to be executed when the batch is applied
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Start of a method/block
    public @UnknownNullability AbsoluteBlockBatch apply(Instance instance, Point position, @Nullable Consumer<@UnknownNullability AbsoluteBlockBatch> callback) {
        // Returns a value to the caller
        return apply(instance, position.blockX(), position.blockY(), position.blockZ(), callback);
    // End of a block/expression
    }

    /**
     * Applies this batch to the given instance at the given position.
     *
     * @param instance The instance in which the batch should be applied
     * @param x        The x position to apply the batch
     * @param y        The y position to apply the batch
     * @param z        The z position to apply the batch
     * @param callback The callback to be executed when the batch is applied
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Start of a method/block
    public @UnknownNullability AbsoluteBlockBatch apply(Instance instance, int x, int y, int z, @Nullable Consumer<@UnknownNullability AbsoluteBlockBatch> callback) {
        // Returns a value to the caller
        return apply(instance, x, y, z, callback, true);
    // End of a block/expression
    }

    /**
     * Applies this batch to the given instance at the given position, and execute the callback
     * immediately when the blocks have been applied, int an unknown thread.
     *
     * @param instance The instance in which the batch should be applied
     * @param x        The x position to apply the batch
     * @param y        The y position to apply the batch
     * @param z        The z position to apply the batch
     * @param callback The callback to be executed when the batch is applied
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Start of a method/block
    public @UnknownNullability AbsoluteBlockBatch applyUnsafe(Instance instance, int x, int y, int z, @Nullable Consumer<@UnknownNullability AbsoluteBlockBatch> callback) {
        // Returns a value to the caller
        return apply(instance, x, y, z, callback, false);
    // End of a block/expression
    }

    /**
     * Applies this batch to the given instance at the given position, execute the callback depending on safeCallback.
     *
     * @param instance     The instance in which the batch should be applied
     * @param x            The x position to apply the batch
     * @param y            The y position to apply the batch
     * @param z            The z position to apply the batch
     * @param callback     The callback to be executed when the batch is applied
     * @param safeCallback If true, the callback will be executed in the next instance update. Otherwise it will be executed immediately upon completion
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Start of a method/block
    protected @UnknownNullability AbsoluteBlockBatch apply(Instance instance, int x, int y, int z, @Nullable Consumer<@UnknownNullability AbsoluteBlockBatch> callback, boolean safeCallback) {
        // Returns a value to the caller
        return this.toAbsoluteBatch(x, y, z).apply(instance, callback, safeCallback);
    // End of a block/expression
    }

    /**
     * Converts this batch to an absolute batch at the origin (0, 0, 0).
     *
     * @return An absolute batch of this batch at the origin
     */
    // Start of a method/block
    public AbsoluteBlockBatch toAbsoluteBatch() {
        // Returns a value to the caller
        return toAbsoluteBatch(0, 0, 0);
    // End of a block/expression
    }

    /**
     * Converts this batch to an absolute batch at the given coordinates.
     *
     * @param x The x position of the batch in the world
     * @param y The y position of the batch in the world
     * @param z The z position of the batch in the world
     * @return An absolute batch of this batch at (x, y, z)
     */
    // Start of a method/block
    public AbsoluteBlockBatch toAbsoluteBatch(int x, int y, int z) {
        // Calls a method
        final AbsoluteBlockBatch batch = new AbsoluteBlockBatch(this.options);
        // Start of a method/block
        synchronized (blockIdMap) {
            // Loop: repeats a block
            for (var entry : blockIdMap.long2ObjectEntrySet()) {
                // Calls a method
                final long pos = entry.getLongKey();
                // Calls a method
                final short relZ = (short) (pos & 0xFFFF);
                // Calls a method
                final short relY = (short) ((pos >> 16) & 0xFFFF);
                // Calls a method
                final short relX = (short) ((pos >> 32) & 0xFFFF);

                // Calls a method
                final Block block = entry.getValue();

                // Assigns a value
                final int finalX = x + offsetX + relX;
                // Assigns a value
                final int finalY = y + offsetY + relY;
                // Assigns a value
                final int finalZ = z + offsetZ + relZ;

                // Calls a method
                batch.setBlock(finalX, finalY, finalZ, block);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return batch;
    // End of a block/expression
    }
// End of a block/expression
}
