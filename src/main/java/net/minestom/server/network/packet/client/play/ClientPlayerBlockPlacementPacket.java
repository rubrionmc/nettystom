// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ClientPlayerBlockPlacementPacket(
        // Instruction de code
        PlayerHand hand, Point blockPosition, BlockFace blockFace,
        // Instruction de code
        float cursorPositionX, float cursorPositionY, float cursorPositionZ,
        // Début d'une méthode/d'un bloc
        boolean insideBlock, boolean hitWorldBorder, int sequence) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientPlayerBlockPlacementPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            Enum(PlayerHand.class), ClientPlayerBlockPlacementPacket::hand,
            // Instruction de code
            BLOCK_POSITION, ClientPlayerBlockPlacementPacket::blockPosition,
            // Instruction de code
            Enum(BlockFace.class), ClientPlayerBlockPlacementPacket::blockFace,
            // Instruction de code
            FLOAT, ClientPlayerBlockPlacementPacket::cursorPositionX,
            // Instruction de code
            FLOAT, ClientPlayerBlockPlacementPacket::cursorPositionY,
            // Instruction de code
            FLOAT, ClientPlayerBlockPlacementPacket::cursorPositionZ,
            // Instruction de code
            BOOLEAN, ClientPlayerBlockPlacementPacket::insideBlock,
            // Instruction de code
            BOOLEAN, ClientPlayerBlockPlacementPacket::hitWorldBorder,
            // Instruction de code
            VAR_INT, ClientPlayerBlockPlacementPacket::sequence,
            // Instruction de code
            ClientPlayerBlockPlacementPacket::new);
// Fin d'un bloc/d'une expression
}
