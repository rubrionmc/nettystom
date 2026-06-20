// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BLOCK_POSITION;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;

// Déclaration de type (classe/interface/enum/record)
public record ClientPickItemFromBlockPacket(Point pos, boolean includeData) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientPickItemFromBlockPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BLOCK_POSITION, ClientPickItemFromBlockPacket::pos,
            // Instruction de code
            BOOLEAN, ClientPickItemFromBlockPacket::includeData,
            // Instruction de code
            ClientPickItemFromBlockPacket::new);
// Fin d'un bloc/d'une expression
}
