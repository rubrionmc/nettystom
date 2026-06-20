// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Déclaration de type (classe/interface/enum/record)
public record GameTestHighlightPosPacket(
        // Instruction de code
        Point absoluteBlockPosition,
        // Instruction de code
        Point relativeBlockPosition
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<GameTestHighlightPosPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.BLOCK_POSITION, GameTestHighlightPosPacket::absoluteBlockPosition,
            // Instruction de code
            NetworkBuffer.BLOCK_POSITION, GameTestHighlightPosPacket::relativeBlockPosition,
            // Instruction de code
            GameTestHighlightPosPacket::new);
// Fin d'un bloc/d'une expression
}
