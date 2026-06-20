// Package declaration for this file
package net.minestom.server.instance.batch;

// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import org.jetbrains.annotations.Contract;

/**
 * Represents options for {@link Batch}s.
 */
// Type declaration (class/interface/enum/record)
public class BatchOption {

    // Assigns a value
    private boolean fullChunk = false;
    // Assigns a value
    private boolean calculateInverse = false;
    // Assigns a value
    private boolean unsafeApply = false;
    // Assigns a value
    private boolean sendUpdate = true;

    // Start of a method/block
    public BatchOption() {
    // End of a block/expression
    }

    /**
     * Gets if the batch is responsible for composing the whole chunk.
     * <p>
     * Having it to true means that the batch will clear the chunk data before placing the blocks.
     * <p>
     * Defaults to false.
     *
     * @return true if the batch is responsible for all the chunk
     */
    // Start of a method/block
    public boolean isFullChunk() {
        // Returns a value to the caller
        return fullChunk;
    // End of a block/expression
    }

    /**
     * Gets if the batch will calculate the inverse of the batch when it is applied for an 'undo' behavior.
     * <p>
     * This flag will determine the return value of {@link Batch#apply(Instance, Object)} (and other variants).
     * If true, a {@link Batch} will be returned. Otherwise null will be returned.
     * <p>
     * Defaults to false.
     *
     * @return true if the batch will calculate its inverse on application
     * @see #isUnsafeApply()
     */
    // Start of a method/block
    public boolean shouldCalculateInverse() {
        // Returns a value to the caller
        return calculateInverse;
    // End of a block/expression
    }

    /**
     * Gets if the batch will wait ignore whether it is ready or not when applying it.
     * <p>
     * If set, the batch may not be ready, or it may be partially ready which will cause an undefined result.
     * {@link Batch#isReady()} and {@link Batch#awaitReady()} may be used to check if it is ready and block
     * until it is ready.
     * <p>
     * The default implementations of {@link ChunkBatch}, {@link AbsoluteBlockBatch}, and {@link RelativeBlockBatch}
     * are always ready unless they are an inverse batch. This is not a safe assumption, and may change in the future.
     * <p>
     * Defaults to false.
     *
     * @return true if the batch will immediately
     */
    // Start of a method/block
    public boolean isUnsafeApply() {
        // Returns a value to the caller
        return this.unsafeApply;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean shouldSendUpdate() {
        // Returns a value to the caller
        return sendUpdate;
    // End of a block/expression
    }

    /**
     * @param fullChunk true to make this batch composes the whole chunk
     * @return 'this' for chaining
     * @see #isFullChunk()
     */
    // Annotation for the following element
    @Contract("_ -> this")
    // Start of a method/block
    public BatchOption setFullChunk(boolean fullChunk) {
        // Access to the current/parent object
        this.fullChunk = fullChunk;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * @param calculateInverse true to make this batch calculate the inverse on application
     * @return 'this' for chaining
     * @see #shouldCalculateInverse()
     */
    // Annotation for the following element
    @Contract("_ -> this")
    // Start of a method/block
    public BatchOption setCalculateInverse(boolean calculateInverse) {
        // Access to the current/parent object
        this.calculateInverse = calculateInverse;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * @param unsafeApply true to make this batch apply without checking if it is ready to apply.
     * @return 'this' for chaining
     * @see #isUnsafeApply()
     * @see Batch#isReady()
     */
    // Annotation for the following element
    @Contract("_ -> this")
    // Start of a method/block
    public BatchOption setUnsafeApply(boolean unsafeApply) {
        // Access to the current/parent object
        this.unsafeApply = unsafeApply;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract("_ -> this")
    // Start of a method/block
    public BatchOption setSendUpdate(boolean sendUpdate) {
        // Access to the current/parent object
        this.sendUpdate = sendUpdate;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }
// End of a block/expression
}
