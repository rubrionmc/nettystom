// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BYTE;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;

// Type declaration (class/interface/enum/record)
public record DisplayScoreboardPacket(byte position, String scoreName) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<DisplayScoreboardPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            BYTE, DisplayScoreboardPacket::position,
            // Code statement
            STRING, DisplayScoreboardPacket::scoreName,
            // Code statement
            DisplayScoreboardPacket::new);
// End of a block/expression
}
