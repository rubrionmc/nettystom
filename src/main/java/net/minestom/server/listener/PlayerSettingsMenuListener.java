// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerGameRulesRequestEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerSetGameRulesEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientSetGameRulesPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientStatusPacket;

// Déclaration de type (classe/interface/enum/record)
public final class PlayerSettingsMenuListener {

    // Début d'une méthode/d'un bloc
    public static void requestGameRules(ClientStatusPacket ignored, Player player) {
        // Appelle une méthode
        EventDispatcher.call(new PlayerGameRulesRequestEvent(player));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void setGameRules(ClientSetGameRulesPacket packet, Player player) {
        // Appelle une méthode
        EventDispatcher.call(new PlayerSetGameRulesEvent(player, packet.entries()));
    // Fin d'un bloc/d'une expression
    }

    //todo: add listeners for setting difficulty
// Fin d'un bloc/d'une expression
}
