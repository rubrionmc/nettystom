// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.ClientPingRequestPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.PingResponsePacket;

// Déclaration de type (classe/interface/enum/record)
public final class PlayPingListener {

    // Début d'une méthode/d'un bloc
    public static void requestListener(ClientPingRequestPacket packet, Player player) {
        // Appelle une méthode
        player.sendPacket(new PingResponsePacket(packet.number()));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
