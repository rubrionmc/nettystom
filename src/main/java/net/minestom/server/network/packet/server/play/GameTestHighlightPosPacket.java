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

// Type declaration (class/interface/enum/record)
public record GameTestHighlightPosPacket(
        // Code statement
        Point absoluteBlockPosition,
        // Code statement
        Point relativeBlockPosition
// Start of a method/block
) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<GameTestHighlightPosPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.BLOCK_POSITION, GameTestHighlightPosPacket::absoluteBlockPosition,
            // Code statement
            NetworkBuffer.BLOCK_POSITION, GameTestHighlightPosPacket::relativeBlockPosition,
            // Code statement
            GameTestHighlightPosPacket::new);
// End of a block/expression
}
