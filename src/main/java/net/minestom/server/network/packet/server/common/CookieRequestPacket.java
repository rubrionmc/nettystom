// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.common;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;

// Déclaration de type (classe/interface/enum/record)
public record CookieRequestPacket(String key) implements
        // Début d'une méthode/d'un bloc
        ServerPacket.Login, ServerPacket.Configuration, ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<CookieRequestPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            STRING, CookieRequestPacket::key,
            // Instruction de code
            CookieRequestPacket::new);
// Fin d'un bloc/d'une expression
}
