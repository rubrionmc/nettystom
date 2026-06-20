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
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record ClientQueryBlockNbtPacket(int transactionId, Point blockPosition) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientQueryBlockNbtPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, ClientQueryBlockNbtPacket::transactionId,
            // Instruction de code
            BLOCK_POSITION, ClientQueryBlockNbtPacket::blockPosition,
            // Instruction de code
            ClientQueryBlockNbtPacket::new);
// Fin d'un bloc/d'une expression
}
