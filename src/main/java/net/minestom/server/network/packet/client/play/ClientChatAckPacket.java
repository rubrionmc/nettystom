// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record ClientChatAckPacket(int offset) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientChatAckPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, ClientChatAckPacket::offset,
            // Code statement
            ClientChatAckPacket::new);
// End of a block/expression
}
