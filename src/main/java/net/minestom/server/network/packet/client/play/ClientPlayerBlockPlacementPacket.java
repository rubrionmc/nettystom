// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record ClientPlayerBlockPlacementPacket(
        // Code statement
        PlayerHand hand, Point blockPosition, BlockFace blockFace,
        // Code statement
        float cursorPositionX, float cursorPositionY, float cursorPositionZ,
        // Start of a method/block
        boolean insideBlock, boolean hitWorldBorder, int sequence) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientPlayerBlockPlacementPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            Enum(PlayerHand.class), ClientPlayerBlockPlacementPacket::hand,
            // Code statement
            BLOCK_POSITION, ClientPlayerBlockPlacementPacket::blockPosition,
            // Code statement
            Enum(BlockFace.class), ClientPlayerBlockPlacementPacket::blockFace,
            // Code statement
            FLOAT, ClientPlayerBlockPlacementPacket::cursorPositionX,
            // Code statement
            FLOAT, ClientPlayerBlockPlacementPacket::cursorPositionY,
            // Code statement
            FLOAT, ClientPlayerBlockPlacementPacket::cursorPositionZ,
            // Code statement
            BOOLEAN, ClientPlayerBlockPlacementPacket::insideBlock,
            // Code statement
            BOOLEAN, ClientPlayerBlockPlacementPacket::hitWorldBorder,
            // Code statement
            VAR_INT, ClientPlayerBlockPlacementPacket::sequence,
            // Code statement
            ClientPlayerBlockPlacementPacket::new);
// End of a block/expression
}
