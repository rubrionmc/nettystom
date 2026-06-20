// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record BlockBreakAnimationPacket(int entityId, Point blockPosition,
                                        // Start of a method/block
                                        byte destroyStage) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<BlockBreakAnimationPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, BlockBreakAnimationPacket::entityId,
            // Code statement
            BLOCK_POSITION, BlockBreakAnimationPacket::blockPosition,
            // Code statement
            BYTE, BlockBreakAnimationPacket::destroyStage,
            // Code statement
            BlockBreakAnimationPacket::new);
// End of a block/expression
}