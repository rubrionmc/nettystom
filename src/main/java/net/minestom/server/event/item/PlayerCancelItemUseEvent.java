// Package declaration for this file
package net.minestom.server.event.item;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.event.trait.ItemEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import of a required class
import net.minestom.server.item.ItemStack;

/**
 * Called when a player stops using an item before the item has completed its usage, including the amount of
 * time the item was used before cancellation.
 *
 * <p>This includes cases like half eating a food, but also includes shooting a bow.</p>
 */
// Type declaration (class/interface/enum/record)
public class PlayerCancelItemUseEvent implements PlayerInstanceEvent, ItemEvent {
    // Code statement
    private final Player player;
    // Code statement
    private final PlayerHand hand;
    // Code statement
    private final ItemStack itemStack;
    // Code statement
    private final long useDuration;
    // Assigns a value
    private boolean isRiptideSpinAttack = false;

    // Start of a method/block
    public PlayerCancelItemUseEvent(Player player, PlayerHand hand, ItemStack itemStack, long useDuration) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.hand = hand;
        // Access to the current/parent object
        this.itemStack = itemStack;
        // Access to the current/parent object
        this.useDuration = useDuration;
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
    public long getUseDuration() {
        // Returns a value to the caller
        return useDuration;
    // End of a block/expression
    }

    /**
     * True if this event will transition the player into a riptide spin attack.
     */
    // Start of a method/block
    public boolean isRiptideSpinAttack() {
        // Returns a value to the caller
        return isRiptideSpinAttack;
    // End of a block/expression
    }

    /**
     * True if this event will transition the player into a riptide spin attack.
     */
    // Start of a method/block
    public void setRiptideSpinAttack(boolean riptideSpinAttack) {
        // Assigns a value
        isRiptideSpinAttack = riptideSpinAttack;
    // End of a block/expression
    }
// End of a block/expression
}
