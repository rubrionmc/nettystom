// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.book.EditBookEvent;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientEditBookPacket;
// Import of a required class
import net.minestom.server.utils.inventory.PlayerInventoryUtils;

// Type declaration (class/interface/enum/record)
public class BookListener {

    // Start of a method/block
    public static void listener(ClientEditBookPacket packet, Player player) {
        // Calls a method
        int minestomSlot = PlayerInventoryUtils.convertPlayerInventorySlotToMinestomSlot(packet.slot());
        // Branch: checks a condition
        if (!PlayerInventoryUtils.isHotbarOrOffHandSlot(minestomSlot)) return;

        // Calls a method
        final ItemStack itemStack = player.getInventory().getItemStack(minestomSlot);
        // Calls a method
        EventDispatcher.call(new EditBookEvent(player, itemStack, packet.pages(), packet.title()));
    // End of a block/expression
    }

// End of a block/expression
}
