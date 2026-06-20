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
import static net.minestom.server.network.NetworkBuffer.BLOCK_POSITION;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;

// Type declaration (class/interface/enum/record)
public record OpenSignEditorPacket(Point position, boolean isFrontText) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<OpenSignEditorPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            BLOCK_POSITION, OpenSignEditorPacket::position,
            // Code statement
            BOOLEAN, OpenSignEditorPacket::isFrontText,
            // Code statement
            OpenSignEditorPacket::new);
// End of a block/expression
}
