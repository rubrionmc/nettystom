// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.event.trait.ItemEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.item.ItemStack;

/**
 * Used when a player is clicking on a block with an item (but is not a block in item form).
 */
// Type declaration (class/interface/enum/record)
public class PlayerUseItemOnBlockEvent implements PlayerInstanceEvent, ItemEvent {

    // Code statement
    private final Player player;
    // Code statement
    private final PlayerHand hand;
    // Code statement
    private final ItemStack itemStack;
    // Code statement
    private final Point position;
    // Code statement
    private final Point cursorPosition;
    // Code statement
    private final BlockFace blockFace;

    // Code statement
    public PlayerUseItemOnBlockEvent(Player player, PlayerHand hand,
                                     // Code statement
                                     ItemStack itemStack,
                                     // Code statement
                                     Point position, Point cursorPosition,
                                     // Start of a method/block
                                     BlockFace blockFace) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.hand = hand;
        // Access to the current/parent object
        this.itemStack = itemStack;
        // Access to the current/parent object
        this.position = position;
        // Access to the current/parent object
        this.cursorPosition = cursorPosition;
        // Access to the current/parent object
        this.blockFace = blockFace;
    // End of a block/expression
    }

    /**
     * Gets the position of the interacted block.
     *
     * @return the block position
     */
    // Start of a method/block
    public Point getPosition() {
        // Returns a value to the caller
        return position;
    // End of a block/expression
    }

    /**
     * Gets the cursor position of the interacted block
     *
     * @return the cursor position of the interaction
     */
    // Code statement
    public Point getCursorPosition() { return cursorPosition; }

    /**
     * Gets which face the player has interacted with.
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
     * Gets which hand the player used to interact with the block.
     *
     * @return the hand
     */
    // Start of a method/block
    public PlayerHand getHand() {
        // Returns a value to the caller
        return hand;
    // End of a block/expression
    }

    /**
     * Gets with which item the player has interacted with the block.
     *
     * @return the item
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public ItemStack getItemStack() {
        // Returns a value to the caller
        return itemStack;
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
