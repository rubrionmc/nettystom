// Déclaration du paquet de ce fichier
package net.minestom.server.listener.common;

// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.ClientCookieResponsePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;

// Déclaration de type (classe/interface/enum/record)
public final class CookieListener {

    // Début d'une méthode/d'un bloc
    public static void handleCookieResponse(ClientCookieResponsePacket packet, PlayerConnection connection) {
        // Appelle une méthode
        connection.receiveCookieResponse(packet.key(), packet.value());
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private CookieListener() {}
// Fin d'un bloc/d'une expression
}
