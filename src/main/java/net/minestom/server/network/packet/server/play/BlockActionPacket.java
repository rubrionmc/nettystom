// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record BlockActionPacket(Point blockPosition, byte actionId,
                                // Start of a method/block
                                byte actionParam, int blockId) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<BlockActionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            BLOCK_POSITION, BlockActionPacket::blockPosition,
            // Code statement
            BYTE, BlockActionPacket::actionId,
            // Code statement
            BYTE, BlockActionPacket::actionParam,
            // Code statement
            VAR_INT, BlockActionPacket::blockId,
            // Code statement
            BlockActionPacket::new);

    // Start of a method/block
    public BlockActionPacket(Point blockPosition, byte actionId, byte actionParam, Block block) {
        // Calls a method
        this(blockPosition, actionId, actionParam, block.id());
    // End of a block/expression
    }
// End of a block/expression
}
