// Package declaration for this file
package net.minestom.server.network.packet.server.common;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.LONG;

// Type declaration (class/interface/enum/record)
public record PingResponsePacket(long number) implements ServerPacket.Status, ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<PingResponsePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            LONG, PingResponsePacket::number,
            // Code statement
            PingResponsePacket::new);
// End of a block/expression
}
