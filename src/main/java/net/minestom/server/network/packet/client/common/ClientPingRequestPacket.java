// Package declaration for this file
package net.minestom.server.network.packet.client.common;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.LONG;

// Type declaration (class/interface/enum/record)
public record ClientPingRequestPacket(long number) implements ClientPacket.Status, ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientPingRequestPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            LONG, ClientPingRequestPacket::number, ClientPingRequestPacket::new);
// End of a block/expression
}
