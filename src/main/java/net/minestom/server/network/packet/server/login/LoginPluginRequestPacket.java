// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.login;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record LoginPluginRequestPacket(int messageId, String channel,
                                       // Début d'une méthode/d'un bloc
                                       byte[] data) implements ServerPacket.Login {
    // Affecte une valeur
    public static final NetworkBuffer.Type<LoginPluginRequestPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, LoginPluginRequestPacket::messageId,
            // Instruction de code
            STRING, LoginPluginRequestPacket::channel,
            // Instruction de code
            RAW_BYTES, LoginPluginRequestPacket::data,
            // Instruction de code
            LoginPluginRequestPacket::new);
// Fin d'un bloc/d'une expression
}
