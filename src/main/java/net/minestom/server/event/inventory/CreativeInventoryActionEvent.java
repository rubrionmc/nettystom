// Package declaration for this file
package net.minestom.server.event.inventory;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import of a required class
import net.minestom.server.item.ItemStack;

/**
 * Called when a player interacts with an item in the creative menu
 */
// Type declaration (class/interface/enum/record)
public class CreativeInventoryActionEvent implements PlayerInstanceEvent, CancellableEvent {
    // Code statement
    private final Player player;
    // Code statement
    private final int slot;
    // Code statement
    private ItemStack clickedItem;
    // Code statement
    private boolean cancelled;

    // Code statement
    public CreativeInventoryActionEvent(Player player,
                                        // Code statement
                                        int slot,
                                        // Start of a method/block
                                        ItemStack clicked) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.slot = slot;
        // Access to the current/parent object
        this.clickedItem = clicked;
        // Access to the current/parent object
        this.cancelled = false;
    // End of a block/expression
    }

    /**
     * Gets the player who is trying to click on the inventory.
     *
     * @return the player who clicked
     */
    // Start of a method/block
    public Player getPlayer() {
        // Returns a value to the caller
        return player;
    // End of a block/expression
    }

    /**
     * Gets the clicked slot number.
     *
     * @return the clicked slot number
     */
    // Start of a method/block
    public int getSlot() {
        // Returns a value to the caller
        return slot;
    // End of a block/expression
    }

    /**
     * Gets the item which has been clicked.
     *
     * @return the clicked item
     */
    // Start of a method/block
    public ItemStack getClickedItem() {
        // Returns a value to the caller
        return clickedItem;
    // End of a block/expression
    }

    /**
     * Changes the clicked item.
     *
     * @param clickedItem the clicked item
     */
    // Start of a method/block
    public void setClickedItem(ItemStack clickedItem) {
        // Access to the current/parent object
        this.clickedItem = clickedItem;
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
// End of a block/expression
}
