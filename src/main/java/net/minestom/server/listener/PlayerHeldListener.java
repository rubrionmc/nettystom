// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerChangeHeldSlotEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientHeldItemChangePacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;

// Déclaration de type (classe/interface/enum/record)
public class PlayerHeldListener {

    // Début d'une méthode/d'un bloc
    public static void heldListener(ClientHeldItemChangePacket packet, Player player) {
        // Embranchement : vérifie une condition
        if (!MathUtils.isBetween(packet.slot(), 0, 8)) {
            // Incorrect packet, ignore
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final byte newSlot = (byte) packet.slot();
        // Appelle une méthode
        final byte oldSlot = player.getHeldSlot();

        // Appelle une méthode
        PlayerChangeHeldSlotEvent changeHeldSlotEvent = new PlayerChangeHeldSlotEvent(player, oldSlot, newSlot);
        // Appelle une méthode
        EventDispatcher.call(changeHeldSlotEvent);

        // Embranchement : vérifie une condition
        if (!changeHeldSlotEvent.isCancelled()) {
            // Event hasn't been canceled, process it

            // Appelle une méthode
            final byte resultSlot = changeHeldSlotEvent.getNewSlot();

            // If the held slot has been changed by the event, send the change to the player
            // Embranchement : vérifie une condition
            if (resultSlot != newSlot) {
                // Appelle une méthode
                player.setHeldItemSlot(resultSlot);
            // Branche alternative de la condition
            } else {
                // Otherwise, simply refresh the player field
                // Appelle une méthode
                player.refreshHeldSlot(resultSlot);
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else {
            // Event has been canceled, send the last held slot to refresh the client
            // Appelle une méthode
            player.setHeldItemSlot(oldSlot);
        // Fin d'un bloc/d'une expression
        }

        // Player is not using offhand, reset item use
        // Embranchement : vérifie une condition
        if (player.getItemUseHand() != PlayerHand.OFF) {
            // Appelle une méthode
            player.refreshActiveHand(false, false, false);
            // Appelle une méthode
            player.clearItemUse();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
