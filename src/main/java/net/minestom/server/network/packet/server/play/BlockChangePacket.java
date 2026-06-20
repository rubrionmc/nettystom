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
import static net.minestom.server.network.NetworkBuffer.BLOCK_POSITION;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record BlockChangePacket(Point blockPosition, int blockStateId) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<BlockChangePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            BLOCK_POSITION, BlockChangePacket::blockPosition,
            // Code statement
            VAR_INT, BlockChangePacket::blockStateId,
            // Code statement
            BlockChangePacket::new);

    // Start of a method/block
    public BlockChangePacket(Point blockPosition, Block block) {
        // Calls a method
        this(blockPosition, block.stateId());
    // End of a block/expression
    }
// End of a block/expression
}
