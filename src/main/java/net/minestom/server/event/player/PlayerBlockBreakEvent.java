// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.coordinate.BlockVec;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.*;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;

// Type declaration (class/interface/enum/record)
public class PlayerBlockBreakEvent implements PlayerInstanceEvent, BlockEvent, CancellableEvent {

    // Code statement
    private final Player player;
    // Code statement
    private final Instance instance;
    // Code statement
    private final Block block;
    // Code statement
    private Block resultBlock;
    // Code statement
    private final BlockVec blockPosition;
    // Code statement
    private final BlockFace blockFace;

    // Code statement
    private boolean cancelled;

    // Code statement
    public PlayerBlockBreakEvent(Player player, Instance instance,
                                 // Code statement
                                 Block block, Block resultBlock, BlockVec blockPosition,
                                 // Start of a method/block
                                 BlockFace blockFace) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.instance = instance;
        // Access to the current/parent object
        this.block = block;
        // Access to the current/parent object
        this.resultBlock = resultBlock;
        // Access to the current/parent object
        this.blockPosition = blockPosition;
        // Access to the current/parent object
        this.blockFace = blockFace;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Instance getInstance() {
        // Returns a value to the caller
        return instance;
    // End of a block/expression
    }

    /**
     * Gets the block to break
     *
     * @return the block
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Block getBlock() {
        // Returns a value to the caller
        return block;
    // End of a block/expression
    }

    /**
     * Gets the block which will replace {@link #getBlock()}.
     *
     * @return the result block
     */
    // Start of a method/block
    public Block getResultBlock() {
        // Returns a value to the caller
        return resultBlock;
    // End of a block/expression
    }

    /**
     * Gets the face at which the block was broken
     *
     * @return the block face
     */
    // Start of a method/block
    public BlockFace getBlockFace() {
        // Returns a value to the caller
        return blockFace;
    // End of a block/expression
    }

    /**
     * Changes the result of the event.
     *
     * @param resultBlock the new block
     */
    // Start of a method/block
    public void setResultBlock(Block resultBlock) {
        // Access to the current/parent object
        this.resultBlock = resultBlock;
    // End of a block/expression
    }

    /**
     * Gets the block position.
     *
     * @return the block position
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public BlockVec getBlockPosition() {
        // Returns a value to the caller
        return blockPosition;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isCancelled() {
        // Returns a value to the caller
        return cancelled;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setCancelled(boolean cancel) {
        // Access to the current/parent object
        this.cancelled = cancel;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Player getPlayer() {
        // Returns a value to the caller
        return player;
    // End of a block/expression
    }
// End of a block/expression
}
