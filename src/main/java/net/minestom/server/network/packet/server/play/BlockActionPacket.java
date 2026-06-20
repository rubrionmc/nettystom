// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record BlockActionPacket(Point blockPosition, byte actionId,
                                // Début d'une méthode/d'un bloc
                                byte actionParam, int blockId) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<BlockActionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BLOCK_POSITION, BlockActionPacket::blockPosition,
            // Instruction de code
            BYTE, BlockActionPacket::actionId,
            // Instruction de code
            BYTE, BlockActionPacket::actionParam,
            // Instruction de code
            VAR_INT, BlockActionPacket::blockId,
            // Instruction de code
            BlockActionPacket::new);

    // Début d'une méthode/d'un bloc
    public BlockActionPacket(Point blockPosition, byte actionId, byte actionParam, Block block) {
        // Appelle une méthode
        this(blockPosition, actionId, actionParam, block.id());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
