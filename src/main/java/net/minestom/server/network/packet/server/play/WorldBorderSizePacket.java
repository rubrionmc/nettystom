// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.DOUBLE;

// Type declaration (class/interface/enum/record)
public record WorldBorderSizePacket(double diameter) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<WorldBorderSizePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            DOUBLE, WorldBorderSizePacket::diameter,
            // Code statement
            WorldBorderSizePacket::new);
// End of a block/expression
}
