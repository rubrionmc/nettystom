// Package declaration for this file
package net.minestom.server.event.inventory;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.InventoryEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import of a required class
import net.minestom.server.inventory.AbstractInventory;
// Import of a required class
import net.minestom.server.inventory.Inventory;

/**
 * Called when a player open an {@link AbstractInventory}.
 * <p>
 * Executed by {@link Player#openInventory(Inventory)}.
 */
// Type declaration (class/interface/enum/record)
public class InventoryOpenEvent implements InventoryEvent, PlayerInstanceEvent, CancellableEvent {

    // Code statement
    private AbstractInventory inventory;
    // Code statement
    private final Player player;

    // Code statement
    private boolean cancelled;

    // Start of a method/block
    public InventoryOpenEvent(AbstractInventory inventory, Player player) {
        // Access to the current/parent object
        this.inventory = inventory;
        // Access to the current/parent object
        this.player = player;
    // End of a block/expression
    }

    /**
     * Gets the player who opens the inventory.
     *
     * @return the player who opens the inventory
     */
    // Start of a method/block
    public Player getPlayer() {
        // Returns a value to the caller
        return player;
    // End of a block/expression
    }

    /**
     * Gets the inventory to open, this could have been change by the {@link #setInventory(AbstractInventory)}.
     *
     * @return the inventory to open, null to just close the current inventory if any
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public AbstractInventory getInventory() {
        // Returns a value to the caller
        return inventory;
    // End of a block/expression
    }

    /**
     * Changes the inventory to open.
     * <p>
     * To do not open any inventory see {@link #setCancelled(boolean)}.
     *
     * @param inventory the inventory to open
     */
    // Start of a method/block
    public void setInventory(AbstractInventory inventory) {
        // Access to the current/parent object
        this.inventory = inventory;
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
