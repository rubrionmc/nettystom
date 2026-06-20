// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.ItemEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import of a required class
import net.minestom.server.item.ItemStack;

/**
 * Event when an item is used without clicking on a block.
 */
// Type declaration (class/interface/enum/record)
public class PlayerUseItemEvent implements PlayerInstanceEvent, ItemEvent, CancellableEvent {

    // Code statement
    private final Player player;
    // Code statement
    private final PlayerHand hand;
    // Code statement
    private final ItemStack itemStack;

    // Code statement
    private long itemUseTime;
    // Code statement
    private boolean cancelled;

    // Start of a method/block
    public PlayerUseItemEvent(Player player, PlayerHand hand, ItemStack itemStack, long itemUseTime) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.hand = hand;
        // Access to the current/parent object
        this.itemStack = itemStack;
        // Access to the current/parent object
        this.itemUseTime = itemUseTime;
    // End of a block/expression
    }

    /**
     * Gets which hand the player used.
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
     * Gets the item which has been used.
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

    /**
     * Gets the item usage duration. After this amount of milliseconds,
     * the animation will stop automatically and {@link net.minestom.server.event.item.PlayerFinishItemUseEvent} is called.
     *
     * @return the item use time
     */
    // Start of a method/block
    public long getItemUseTime() {
        // Returns a value to the caller
        return itemUseTime;
    // End of a block/expression
    }

    /**
     * Changes the item usage duration.
     *
     * @param itemUseTime the new item use time
     */
    // Start of a method/block
    public void setItemUseTime(long itemUseTime) {
        // Access to the current/parent object
        this.itemUseTime = itemUseTime;
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
