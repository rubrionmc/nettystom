// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.AdvancementTabEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientAdvancementTabPacket;

// Déclaration de type (classe/interface/enum/record)
public class AdvancementTabListener {

    // Début d'une méthode/d'un bloc
    public static void listener(ClientAdvancementTabPacket packet, Player player) {
        // Appelle une méthode
        final String tabIdentifier = packet.tabIdentifier();
        // Embranchement : vérifie une condition
        if (tabIdentifier != null) {
            // Appelle une méthode
            EventDispatcher.call(new AdvancementTabEvent(player, packet.action(), tabIdentifier));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
