// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerAnvilInputEvent;
// Import d'une classe nécessaire
import net.minestom.server.inventory.Inventory;
// Import d'une classe nécessaire
import net.minestom.server.inventory.InventoryType;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientNameItemPacket;

// Déclaration de type (classe/interface/enum/record)
public final class AnvilListener {

    // Début d'une méthode/d'un bloc
    public static void nameItemListener(ClientNameItemPacket packet, Player player) {
        // Embranchement : vérifie une condition
        if (!(player.getOpenInventory() instanceof Inventory openInventory))
            // Renvoie une valeur à l'appelant
            return;
        // Embranchement : vérifie une condition
        if (openInventory.getInventoryType() != InventoryType.ANVIL)
            // Renvoie une valeur à l'appelant
            return;

        // Appelle une méthode
        EventDispatcher.call(new PlayerAnvilInputEvent(player, openInventory, packet.itemName()));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private AnvilListener() {
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
