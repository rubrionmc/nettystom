// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.inventory.CreativeInventoryActionEvent;
// Import of a required class
import net.minestom.server.inventory.PlayerInventory;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientCreativeInventoryActionPacket;
// Import of a required class
import net.minestom.server.utils.inventory.PlayerInventoryUtils;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
public final class CreativeInventoryActionListener {
    // Start of a method/block
    public static void listener(ClientCreativeInventoryActionPacket packet, Player player) {
        // Branch: checks a condition
        if (player.getGameMode() != GameMode.CREATIVE) return;
        // Calls a method
        short slot = packet.slot();
        // Calls a method
        final ItemStack sentItem = packet.item();
        // Branch: checks a condition
        if (slot == -1) {
            // Drop item
            // Calls a method
            CreativeInventoryActionEvent event = new CreativeInventoryActionEvent(player, slot, sentItem);
            // Calls a method
            EventDispatcher.call(event);
            // Branch: checks a condition
            if (event.isCancelled()) return;
            // Calls a method
            player.dropItem(event.getClickedItem());
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Bounds check
        // 0 is crafting result inventory slot, ignore attempts to place into it
        // Branch: checks a condition
        if (slot < 1 || slot > PlayerInventoryUtils.OFFHAND_SLOT) {
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Set item
        // Calls a method
        slot = (short) PlayerInventoryUtils.convertWindow0SlotToMinestomSlot(slot);
        // Calls a method
        PlayerInventory inventory = player.getInventory();

        // Calls a method
        CreativeInventoryActionEvent event = new CreativeInventoryActionEvent(player, slot, sentItem);
        // Calls a method
        EventDispatcher.call(event);
        // Calls a method
        final ItemStack setItem = event.getClickedItem();
        // Calls a method
        final ItemStack previousItem = inventory.getItemStack(slot);

        // Branch: checks a condition
        if (event.isCancelled()) {
            // Event is cancelled, keep the old item
            // Calls a method
            player.getInventory().sendSlotRefresh(slot, previousItem);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        final boolean isEqualToSentItem = Objects.equals(setItem, sentItem);

        // Branch: checks a condition
        if (Objects.equals(previousItem, sentItem) && isEqualToSentItem) {
            // Item is already present, ignore
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        inventory.setItemStack(slot, setItem);

        // Branch: checks a condition
        if (!isEqualToSentItem) {
            // Calls a method
            player.getInventory().sendSlotRefresh(slot, setItem);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
