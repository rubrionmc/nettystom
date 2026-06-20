// Package declaration for this file
package net.minestom.server.event.item;

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
import net.minestom.server.item.ItemAnimation;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.utils.validate.Check;

/**
 * Called when a player begins using an item with the item, animation, and duration.
 *
 * <p>Setting the use duration to zero or cancelling the event will prevent consumption.</p>
 */
// Type declaration (class/interface/enum/record)
public class PlayerBeginItemUseEvent implements PlayerInstanceEvent, ItemEvent, CancellableEvent {
    // Code statement
    private final Player player;
    // Code statement
    private final PlayerHand hand;
    // Code statement
    private final ItemStack itemStack;
    // Code statement
    private final ItemAnimation animation;
    // Code statement
    private long itemUseDuration;

    // Assigns a value
    private boolean cancelled = false;

    // Code statement
    public PlayerBeginItemUseEvent(Player player, PlayerHand hand,
                                   // Code statement
                                   ItemStack itemStack, ItemAnimation animation,
                                   // Start of a method/block
                                   long itemUseDuration) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.hand = hand;
        // Access to the current/parent object
        this.itemStack = itemStack;
        // Access to the current/parent object
        this.animation = animation;
        // Access to the current/parent object
        this.itemUseDuration = itemUseDuration;
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

    // Start of a method/block
    public PlayerHand getHand() {
        // Returns a value to the caller
        return hand;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ItemStack getItemStack() {
        // Returns a value to the caller
        return itemStack;
    // End of a block/expression
    }

    // Start of a method/block
    public ItemAnimation getAnimation() {
        // Returns a value to the caller
        return animation;
    // End of a block/expression
    }

    /**
     * Returns the item use duration, in ticks. A duration of zero will prevent consumption (same effect as cancellation).
     *
     * @return the current item use duration
     */
    // Start of a method/block
    public long getItemUseDuration() {
        // Returns a value to the caller
        return itemUseDuration;
    // End of a block/expression
    }

    /**
     * Sets the item use duration, in ticks.
     */
    // Start of a method/block
    public void setItemUseDuration(long itemUseDuration) {
        // Calls a method
        Check.argCondition(itemUseDuration < 0, "Item use duration cannot be negative");
        // Access to the current/parent object
        this.itemUseDuration = itemUseDuration;
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
    public void setCancelled(boolean cancelled) {
        // Access to the current/parent object
        this.cancelled = cancelled;
    // End of a block/expression
    }
// End of a block/expression
}
