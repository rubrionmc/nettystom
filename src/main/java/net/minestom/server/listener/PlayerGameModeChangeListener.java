// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerGameModeRequestEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientChangeGameModePacket;

// Déclaration de type (classe/interface/enum/record)
public final class PlayerGameModeChangeListener {

    // Début d'une méthode/d'un bloc
    public static void listener(ClientChangeGameModePacket packet, Player player) {
        // Appelle une méthode
        PlayerGameModeRequestEvent playerGameModeRequestEvent = new PlayerGameModeRequestEvent(player, packet.gameMode());
        // Appelle une méthode
        EventDispatcher.call(playerGameModeRequestEvent);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
