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
public record WorldEventPacket(int effectId, Point position, int data,
                               // Start of a method/block
                               boolean disableRelativeVolume) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<WorldEventPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            INT, WorldEventPacket::effectId,
            // Code statement
            BLOCK_POSITION, WorldEventPacket::position,
            // Code statement
            INT, WorldEventPacket::data,
            // Code statement
            BOOLEAN, WorldEventPacket::disableRelativeVolume,
            // Code statement
            WorldEventPacket::new);
// End of a block/expression
}
