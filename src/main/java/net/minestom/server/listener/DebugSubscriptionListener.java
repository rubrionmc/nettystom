// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerDebugSubscriptionsRequestEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientDebugSubscriptionRequestPacket;

// Déclaration de type (classe/interface/enum/record)
public final class DebugSubscriptionListener {

    // Début d'une méthode/d'un bloc
    public static void requestListener(ClientDebugSubscriptionRequestPacket packet, Player player) {
        // Appelle une méthode
        PlayerDebugSubscriptionsRequestEvent event = new PlayerDebugSubscriptionsRequestEvent(player, packet.subscriptions());
        // Appelle une méthode
        EventDispatcher.call(event);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
