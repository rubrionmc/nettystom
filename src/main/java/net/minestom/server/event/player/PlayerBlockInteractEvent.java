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
 * Called when a player interacts with a block (right-click).
 * This is also called when a block is placed.
 */
// Type declaration (class/interface/enum/record)
public class PlayerBlockInteractEvent implements PlayerInstanceEvent, BlockEvent, CancellableEvent {

    // Code statement
    private final Player player;
    // Code statement
    private final PlayerHand hand;
    // Code statement
    private final Instance instance;
    // Code statement
    private final Block block;
    // Code statement
    private final BlockVec blockPosition;
    // Code statement
    private final Point cursorPosition;
    // Code statement
    private final BlockFace blockFace;

    /**
     * Does this interaction block the normal item use?
     * True for containers which open an inventory instead of letting blocks be placed
     */
    // Code statement
    private boolean blocksItemUse;

    // Code statement
    private boolean cancelled;

    // Code statement
    public PlayerBlockInteractEvent(Player player, PlayerHand hand, Instance instance,
                                    // Code statement
                                    Block block, BlockVec blockPosition, Point cursorPosition,
                                    // Start of a method/block
                                    BlockFace blockFace) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.hand = hand;
        // Access to the current/parent object
        this.instance = instance;
        // Access to the current/parent object
        this.block = block;
        // Access to the current/parent object
        this.blockPosition = blockPosition;
        // Access to the current/parent object
        this.cursorPosition = cursorPosition;
        // Access to the current/parent object
        this.blockFace = blockFace;
    // End of a block/expression
    }

    /**
     * Gets if the event should block the item use.
     *
     * @return true if the item use is blocked, false otherwise
     */
    // Start of a method/block
    public boolean isBlockingItemUse() {
        // Returns a value to the caller
        return blocksItemUse;
    // End of a block/expression
    }

    /**
     * Sets the blocking item use state of this event
     * Note: If this is true, then no {@link PlayerUseItemOnBlockEvent} will be fired.
     * @param blocks - true to block item interactions, false to not block
     */
    // Start of a method/block
    public void setBlockingItemUse(boolean blocks) {
        // Access to the current/parent object
        this.blocksItemUse = blocks;
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

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Block getBlock() {
        // Returns a value to the caller
        return block;
    // End of a block/expression
    }

    /**
     * Gets the position of the interacted block.
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

    /**
     * Gets the cursor position of the interacted block
     * @return the cursor position of the interaction
     */
    // Code statement
    public Point getCursorPosition() { return cursorPosition; }

    /**
     * Gets the hand used for the interaction.
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
     * Gets the block face.
     *
     * @return the block face
     */
    // Start of a method/block
    public BlockFace getBlockFace() {
        // Returns a value to the caller
        return blockFace;
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
