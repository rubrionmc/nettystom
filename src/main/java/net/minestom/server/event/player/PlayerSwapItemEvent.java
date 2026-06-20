// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import of a required class
import net.minestom.server.item.ItemStack;

/**
 * Called when a player is trying to swap his main and off hand item.
 */
// Type declaration (class/interface/enum/record)
public class PlayerSwapItemEvent implements PlayerInstanceEvent, CancellableEvent {

    // Code statement
    private final Player player;
    // Code statement
    private ItemStack mainHandItem;
    // Code statement
    private ItemStack offHandItem;

    // Code statement
    private boolean cancelled;

    // Start of a method/block
    public PlayerSwapItemEvent(Player player, ItemStack mainHandItem, ItemStack offHandItem) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.mainHandItem = mainHandItem;
        // Access to the current/parent object
        this.offHandItem = offHandItem;
    // End of a block/expression
    }

    /**
     * Gets the item which will be in player main hand after the event.
     *
     * @return the item in main hand
     */
    // Start of a method/block
    public ItemStack getMainHandItem() {
        // Returns a value to the caller
        return mainHandItem;
    // End of a block/expression
    }

    /**
     * Changes the item which will be in the player main hand.
     *
     * @param mainHandItem the main hand item
     */
    // Start of a method/block
    public void setMainHandItem(ItemStack mainHandItem) {
        // Access to the current/parent object
        this.mainHandItem = mainHandItem;
    // End of a block/expression
    }

    /**
     * Gets the item which will be in player off hand after the event.
     *
     * @return the item in off hand
     */
    // Start of a method/block
    public ItemStack getOffHandItem() {
        // Returns a value to the caller
        return offHandItem;
    // End of a block/expression
    }

    /**
     * Changes the item which will be in the player off hand.
     *
     * @param offHandItem the off hand item
     */
    // Start of a method/block
    public void setOffHandItem(ItemStack offHandItem) {
        // Access to the current/parent object
        this.offHandItem = offHandItem;
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
