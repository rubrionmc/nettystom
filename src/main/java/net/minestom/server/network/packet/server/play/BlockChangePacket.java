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
import static net.minestom.server.network.NetworkBuffer.BLOCK_POSITION;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record BlockChangePacket(Point blockPosition, int blockStateId) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<BlockChangePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BLOCK_POSITION, BlockChangePacket::blockPosition,
            // Instruction de code
            VAR_INT, BlockChangePacket::blockStateId,
            // Instruction de code
            BlockChangePacket::new);

    // Début d'une méthode/d'un bloc
    public BlockChangePacket(Point blockPosition, Block block) {
        // Appelle une méthode
        this(blockPosition, block.stateId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
