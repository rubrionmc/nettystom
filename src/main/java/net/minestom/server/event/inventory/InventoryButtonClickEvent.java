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

/**
 * Represents an event triggered when a player interacts with a button in an {@link AbstractInventory}, such
 * as the entries in a stonecutter, the buttons in an enchanting table, etc.
 * <br>
 * See the <a href="https://minecraft.wiki/w/Java_Edition_protocol/Inventory">minecraft protocol wiki</a> for a
 * list of all button ids.
 */
// Type declaration (class/interface/enum/record)
public class InventoryButtonClickEvent implements InventoryEvent, PlayerInstanceEvent {
    // Code statement
    private final Player player;
    // Code statement
    private final AbstractInventory inventory;
    // Code statement
    private final int buttonId;

    // Start of a method/block
    public InventoryButtonClickEvent(Player player, AbstractInventory inventory, int buttonId) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.inventory = inventory;
        // Access to the current/parent object
        this.buttonId = buttonId;
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

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Player getPlayer() {
        // Returns a value to the caller
        return player;
    // End of a block/expression
    }

    // Start of a method/block
    public int getButtonId() {
        // Returns a value to the caller
        return buttonId;
    // End of a block/expression
    }
// End of a block/expression
}
