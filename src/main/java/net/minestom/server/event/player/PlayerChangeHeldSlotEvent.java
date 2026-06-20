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
// Import of a required class
import net.minestom.server.utils.MathUtils;
// Import of a required class
import net.minestom.server.utils.validate.Check;

/**
 * Called when a player change his held slot (by pressing 1-9 keys).
 */
// Type declaration (class/interface/enum/record)
public class PlayerChangeHeldSlotEvent implements PlayerInstanceEvent, CancellableEvent {

    // Code statement
    private final Player player;
    // Code statement
    private final byte oldSlot;
    // Code statement
    private byte newSlot;

    // Code statement
    private boolean cancelled;

    // Start of a method/block
    public PlayerChangeHeldSlotEvent(Player player, byte oldSlot, byte newSlot) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.oldSlot = oldSlot;
        // Access to the current/parent object
        this.newSlot = newSlot;
    // End of a block/expression
    }

    /**
     * Gets the slot number that the player is currently holding
     *
     * @return The slot index that the player currently is holding
     */
    // Start of a method/block
    public byte getOldSlot() {
        // Returns a value to the caller
        return oldSlot;
    // End of a block/expression
    }

    /**
     * Gets the slot which the player will hold.
     * @return the future slot
     */
    // Start of a method/block
    public byte getNewSlot() {
        // Returns a value to the caller
        return newSlot;
    // End of a block/expression
    }

    /**
     * Changes the final held slot of the player.
     *
     * @param slot the new held slot
     * @throws IllegalArgumentException if <code>slot</code> is not between 0 and 8
     */
    // Start of a method/block
    public void setNewSlot(byte slot) {
        // Calls a method
        Check.argCondition(!MathUtils.isBetween(slot, 0, 8), "The held slot needs to be between 0 and 8");
        // Access to the current/parent object
        this.newSlot = slot;
    // End of a block/expression
    }

    /**
     * Gets the ItemStack in the player's currently held slot
     * @return The ItemStack in the player's currently held slot
     */
    // Start of a method/block
    public ItemStack getItemInOldSlot() {
        // Returns a value to the caller
        return player.getInventory().getItemStack(oldSlot);
    // End of a block/expression
    }

    /**
     * Gets the ItemStack in the slot the player will hold
     * @return The ItemStack in the final held slot of the player
     */
    // Start of a method/block
    public ItemStack getItemInNewSlot() {
        // Returns a value to the caller
        return player.getInventory().getItemStack(newSlot);
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
