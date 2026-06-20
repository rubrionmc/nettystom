// Package declaration for this file
package net.minestom.server.network.packet.client.common;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.INT;

// Type declaration (class/interface/enum/record)
public record ClientPongPacket(int id) implements ClientPacket.Configuration, ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientPongPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            INT, ClientPongPacket::id, ClientPongPacket::new);
// End of a block/expression
}
