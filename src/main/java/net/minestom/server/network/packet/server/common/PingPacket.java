// Package declaration for this file
package net.minestom.server.network.packet.server.common;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.INT;

// Type declaration (class/interface/enum/record)
public record PingPacket(int id) implements ServerPacket.Configuration, ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<PingPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            INT, PingPacket::id, PingPacket::new);
// End of a block/expression
}
