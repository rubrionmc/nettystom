// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.FLOAT;

// Déclaration de type (classe/interface/enum/record)
public record ClientChunkBatchReceivedPacket(float targetChunksPerTick) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientChunkBatchReceivedPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            FLOAT, ClientChunkBatchReceivedPacket::targetChunksPerTick,
            // Instruction de code
            ClientChunkBatchReceivedPacket::new);
// Fin d'un bloc/d'une expression
}
