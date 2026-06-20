// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.inventory.CreativeInventoryActionEvent;
// Import d'une classe nécessaire
import net.minestom.server.inventory.PlayerInventory;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientCreativeInventoryActionPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.inventory.PlayerInventoryUtils;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
public final class CreativeInventoryActionListener {
    // Début d'une méthode/d'un bloc
    public static void listener(ClientCreativeInventoryActionPacket packet, Player player) {
        // Embranchement : vérifie une condition
        if (player.getGameMode() != GameMode.CREATIVE) return;
        // Appelle une méthode
        short slot = packet.slot();
        // Appelle une méthode
        final ItemStack sentItem = packet.item();
        // Embranchement : vérifie une condition
        if (slot == -1) {
            // Drop item
            // Appelle une méthode
            CreativeInventoryActionEvent event = new CreativeInventoryActionEvent(player, slot, sentItem);
            // Appelle une méthode
            EventDispatcher.call(event);
            // Embranchement : vérifie une condition
            if (event.isCancelled()) return;
            // Appelle une méthode
            player.dropItem(event.getClickedItem());
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Bounds check
        // 0 is crafting result inventory slot, ignore attempts to place into it
        // Embranchement : vérifie une condition
        if (slot < 1 || slot > PlayerInventoryUtils.OFFHAND_SLOT) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Set item
        // Appelle une méthode
        slot = (short) PlayerInventoryUtils.convertWindow0SlotToMinestomSlot(slot);
        // Appelle une méthode
        PlayerInventory inventory = player.getInventory();

        // Appelle une méthode
        CreativeInventoryActionEvent event = new CreativeInventoryActionEvent(player, slot, sentItem);
        // Appelle une méthode
        EventDispatcher.call(event);
        // Appelle une méthode
        final ItemStack setItem = event.getClickedItem();
        // Appelle une méthode
        final ItemStack previousItem = inventory.getItemStack(slot);

        // Embranchement : vérifie une condition
        if (event.isCancelled()) {
            // Event is cancelled, keep the old item
            // Appelle une méthode
            player.getInventory().sendSlotRefresh(slot, previousItem);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final boolean isEqualToSentItem = Objects.equals(setItem, sentItem);

        // Embranchement : vérifie une condition
        if (Objects.equals(previousItem, sentItem) && isEqualToSentItem) {
            // Item is already present, ignore
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        inventory.setItemStack(slot, setItem);

        // Embranchement : vérifie une condition
        if (!isEqualToSentItem) {
            // Appelle une méthode
            player.getInventory().sendSlotRefresh(slot, setItem);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
