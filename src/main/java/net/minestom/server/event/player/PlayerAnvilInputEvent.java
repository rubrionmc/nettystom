// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.InventoryEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import of a required class
import net.minestom.server.inventory.Inventory;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientNameItemPacket;

/**
 * Called every time a {@link Player} types a letter in an anvil GUI.
 *
 * @see ClientNameItemPacket
 */
// Type declaration (class/interface/enum/record)
public class PlayerAnvilInputEvent implements PlayerInstanceEvent, InventoryEvent {

    // Code statement
    private final Player player;
    // Code statement
    private final Inventory inventory;
    // Code statement
    private final String input;

    // Start of a method/block
    public PlayerAnvilInputEvent(Player player, Inventory inventory, String input) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.inventory = inventory;
        // Access to the current/parent object
        this.input = input;
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
    public String getInput() {
        // Returns a value to the caller
        return input;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Inventory getInventory() {
        // Returns a value to the caller
        return inventory;
    // End of a block/expression
    }

// End of a block/expression
}
