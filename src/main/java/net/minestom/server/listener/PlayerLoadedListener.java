// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerLoadedEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientPlayerLoadedPacket;

// Déclaration de type (classe/interface/enum/record)
public final class PlayerLoadedListener {

    // Début d'une méthode/d'un bloc
    public static void listener(ClientPlayerLoadedPacket packet, Player player) {
        // Appelle une méthode
        EventDispatcher.call(new PlayerLoadedEvent(player));
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
