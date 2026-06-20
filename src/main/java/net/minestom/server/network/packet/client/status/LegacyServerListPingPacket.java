// Package declaration for this file
package net.minestom.server.network.packet.client.status;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BYTE;

// Type declaration (class/interface/enum/record)
public record LegacyServerListPingPacket(byte payload) implements ClientPacket.Status {
    // Assigns a value
    public static final NetworkBuffer.Type<LegacyServerListPingPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            BYTE, LegacyServerListPingPacket::payload,
            // Code statement
            LegacyServerListPingPacket::new);
// End of a block/expression
}
