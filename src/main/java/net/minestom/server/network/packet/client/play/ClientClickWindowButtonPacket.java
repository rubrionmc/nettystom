// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record ClientClickWindowButtonPacket(int windowId, int buttonId) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientClickWindowButtonPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, ClientClickWindowButtonPacket::windowId,
            // Instruction de code
            VAR_INT, ClientClickWindowButtonPacket::buttonId,
            // Instruction de code
            ClientClickWindowButtonPacket::new);
// Fin d'un bloc/d'une expression
}
