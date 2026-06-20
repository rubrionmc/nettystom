// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerAnvilInputEvent;
// Import of a required class
import net.minestom.server.inventory.Inventory;
// Import of a required class
import net.minestom.server.inventory.InventoryType;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientNameItemPacket;

// Type declaration (class/interface/enum/record)
public final class AnvilListener {

    // Start of a method/block
    public static void nameItemListener(ClientNameItemPacket packet, Player player) {
        // Branch: checks a condition
        if (!(player.getOpenInventory() instanceof Inventory openInventory))
            // Returns a value to the caller
            return;
        // Branch: checks a condition
        if (openInventory.getInventoryType() != InventoryType.ANVIL)
            // Returns a value to the caller
            return;

        // Calls a method
        EventDispatcher.call(new PlayerAnvilInputEvent(player, openInventory, packet.itemName()));
    // End of a block/expression
    }

    // Start of a method/block
    private AnvilListener() {
    // End of a block/expression
    }

// End of a block/expression
}
