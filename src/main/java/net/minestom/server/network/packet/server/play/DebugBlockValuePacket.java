// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.debug.DebugSubscription;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Déclaration de type (classe/interface/enum/record)
public record DebugBlockValuePacket(
        // Instruction de code
        Point blockPosition,
        // Instruction de code
        DebugSubscription.Update<?> update
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DebugBlockValuePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.BLOCK_POSITION, DebugBlockValuePacket::blockPosition,
            // Instruction de code
            DebugSubscription.Update.NETWORK_TYPE, DebugBlockValuePacket::update,
            // Instruction de code
            DebugBlockValuePacket::new);
// Fin d'un bloc/d'une expression
}
