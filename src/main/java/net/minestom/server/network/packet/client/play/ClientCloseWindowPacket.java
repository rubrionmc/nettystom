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
public record ClientCloseWindowPacket(int windowId) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientCloseWindowPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, ClientCloseWindowPacket::windowId,
            // Instruction de code
            ClientCloseWindowPacket::new);
// Fin d'un bloc/d'une expression
}
