// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.status;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;

// Déclaration de type (classe/interface/enum/record)
public record ResponsePacket(String jsonResponse) implements ServerPacket.Status {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ResponsePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            STRING, ResponsePacket::jsonResponse,
            // Instruction de code
            ResponsePacket::new);
// Fin d'un bloc/d'une expression
}
