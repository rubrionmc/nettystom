// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.book.EditBookEvent;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientEditBookPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.inventory.PlayerInventoryUtils;

// Déclaration de type (classe/interface/enum/record)
public class BookListener {

    // Début d'une méthode/d'un bloc
    public static void listener(ClientEditBookPacket packet, Player player) {
        // Appelle une méthode
        int minestomSlot = PlayerInventoryUtils.convertPlayerInventorySlotToMinestomSlot(packet.slot());
        // Embranchement : vérifie une condition
        if (!PlayerInventoryUtils.isHotbarOrOffHandSlot(minestomSlot)) return;

        // Appelle une méthode
        final ItemStack itemStack = player.getInventory().getItemStack(minestomSlot);
        // Appelle une méthode
        EventDispatcher.call(new EditBookEvent(player, itemStack, packet.pages(), packet.title()));
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
