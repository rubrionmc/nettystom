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
public record KeepAlivePacket(long id) implements ServerPacket.Configuration, ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<KeepAlivePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            LONG, KeepAlivePacket::id, KeepAlivePacket::new);
// End of a block/expression
}
