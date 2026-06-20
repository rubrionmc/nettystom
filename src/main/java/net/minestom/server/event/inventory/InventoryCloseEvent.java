// Package declaration for this file
package net.minestom.server.event.inventory;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.InventoryEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import of a required class
import net.minestom.server.inventory.AbstractInventory;
// Import of a required class
import net.minestom.server.inventory.Inventory;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/**
 * Called when an {@link AbstractInventory} is closed by a player.
 */
// Type declaration (class/interface/enum/record)
public class InventoryCloseEvent implements InventoryEvent, PlayerInstanceEvent {

    // Code statement
    private final AbstractInventory inventory;
    // Code statement
    private final Player player;
    // Code statement
    private final boolean fromClient;
    // Code statement
    private Inventory newInventory;

    // Start of a method/block
    public InventoryCloseEvent(AbstractInventory inventory, Player player, boolean fromClient) {
        // Access to the current/parent object
        this.inventory = inventory;
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.fromClient = fromClient;
    // End of a block/expression
    }

    /**
     * Gets the player who closed the inventory.
     *
     * @return the player who closed the inventory
     */
    // Start of a method/block
    public Player getPlayer() {
        // Returns a value to the caller
        return player;
    // End of a block/expression
    }

    /**
     * Gets whether the client closed the inventory or the server did.
     *
     * @return true if the client closed the inventory, false if the server closed the inventory
     */
    // Start of a method/block
    public boolean isFromClient() {
        // Returns a value to the caller
        return fromClient;
    // End of a block/expression
    }

    /**
     * Gets the new inventory to open.
     *
     * @return the new inventory to open, null if there isn't any
     */
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public Inventory getNewInventory() {
        // Returns a value to the caller
        return newInventory;
    // End of a block/expression
    }

    /**
     * Can be used to open a new inventory after closing the previous one.
     *
     * @param newInventory the inventory to open, null to do not open any
     */
    // Start of a method/block
    public void setNewInventory(@Nullable Inventory newInventory) {
        // Access to the current/parent object
        this.newInventory = newInventory;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public AbstractInventory getInventory() {
        // Returns a value to the caller
        return inventory;
    // End of a block/expression
    }
// End of a block/expression
}
