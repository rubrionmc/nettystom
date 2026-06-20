// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import of a required class
import net.minestom.server.item.ItemStack;

/**
 * Called when a player attempts to use a stab attack on an item with the {@link net.minestom.server.item.component.PiercingWeapon} enchantment.
 */
// Type declaration (class/interface/enum/record)
public class PlayerStabEvent implements PlayerInstanceEvent {
    // Code statement
    private final Player player;

    // Start of a method/block
    public PlayerStabEvent(Player player) {
        // Access to the current/parent object
        this.player = player;
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

    /**
     * Gets the item which the player attacked with.
     *
     * @return the item in main hand
     */
    // Start of a method/block
    public ItemStack getItemStack() {
        // Returns a value to the caller
        return player.getItemInMainHand();
    // End of a block/expression
    }
// End of a block/expression
}
