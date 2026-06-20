// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.debug.DebugSubscription;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Déclaration de type (classe/interface/enum/record)
public record DebugChunkValuePacket(long chunkPos, DebugSubscription.Update<?> update) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DebugChunkValuePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.LONG, DebugChunkValuePacket::chunkPos,
            // Instruction de code
            DebugSubscription.Update.NETWORK_TYPE, DebugChunkValuePacket::update,
            // Instruction de code
            DebugChunkValuePacket::new);
// Fin d'un bloc/d'une expression
}
