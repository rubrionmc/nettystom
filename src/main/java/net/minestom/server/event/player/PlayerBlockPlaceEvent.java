// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.coordinate.BlockVec;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.event.trait.*;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;

/**
 * Called when a player tries placing a block.
 */
// Type declaration (class/interface/enum/record)
public class PlayerBlockPlaceEvent implements PlayerInstanceEvent, BlockEvent, CancellableEvent {

    // Code statement
    private final Player player;
    // Code statement
    private final Instance instance;
    // Code statement
    private Block block;
    // Code statement
    private final BlockFace blockFace;
    // Code statement
    private final BlockVec blockPosition;
    // Code statement
    private final Point cursorPosition;
    // Code statement
    private final PlayerHand hand;

    // Code statement
    private boolean consumeBlock;
    // Code statement
    private boolean doBlockUpdates;

    // Code statement
    private boolean cancelled;

    // Code statement
    public PlayerBlockPlaceEvent(Player player, Instance instance, Block block,
                                 // Code statement
                                 BlockFace blockFace, BlockVec blockPosition,
                                 // Start of a method/block
                                 Point cursorPosition, PlayerHand hand) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.instance = instance;
        // Access to the current/parent object
        this.block = block;
        // Access to the current/parent object
        this.blockFace = blockFace;
        // Access to the current/parent object
        this.blockPosition = blockPosition;
        // Access to the current/parent object
        this.cursorPosition = cursorPosition;
        // Access to the current/parent object
        this.hand = hand;
        // Access to the current/parent object
        this.consumeBlock = true;
        // Access to the current/parent object
        this.doBlockUpdates = true;
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
     * Gets the block which will be placed.
     *
     * @return the block to place
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
     * Changes the block to be placed.
     *
     * @param block the new block
     */
    // Start of a method/block
    public void setBlock(Block block) {
        // Access to the current/parent object
        this.block = block;
    // End of a block/expression
    }

    // Start of a method/block
    public BlockFace getBlockFace() {
        // Returns a value to the caller
        return blockFace;
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

    // Start of a method/block
    public Point getCursorPosition() {
        // Returns a value to the caller
        return cursorPosition;
    // End of a block/expression
    }

    /**
     * Gets the hand with which the player is trying to place.
     *
     * @return the hand used
     */
    // Start of a method/block
    public PlayerHand getHand() {
        // Returns a value to the caller
        return hand;
    // End of a block/expression
    }

    /**
     * Should the block be consumed if not cancelled.
     *
     * @param consumeBlock true if the block should be consumer (-1 amount), false otherwise
     */
    // Start of a method/block
    public void consumeBlock(boolean consumeBlock) {
        // Access to the current/parent object
        this.consumeBlock = consumeBlock;
    // End of a block/expression
    }

    /**
     * Should the block be consumed if not cancelled.
     *
     * @return true if the block will be consumed, false otherwise
     */
    // Start of a method/block
    public boolean doesConsumeBlock() {
        // Returns a value to the caller
        return consumeBlock;
    // End of a block/expression
    }

    /**
     * Should the place trigger updates (on self and neighbors)
     * @param doBlockUpdates true if this placement should do block updates
     */
    // Start of a method/block
    public void setDoBlockUpdates(boolean doBlockUpdates) {
        // Access to the current/parent object
        this.doBlockUpdates = doBlockUpdates;
    // End of a block/expression
    }

    /**
     * Should the place trigger updates (on self and neighbors)
     * @return true if this placement should do block updates
     */
    // Start of a method/block
    public boolean shouldDoBlockUpdates() {
        // Returns a value to the caller
        return doBlockUpdates;
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
