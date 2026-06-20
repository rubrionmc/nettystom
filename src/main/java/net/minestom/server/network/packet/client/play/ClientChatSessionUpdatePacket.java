// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.crypto.ChatSession;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Type declaration (class/interface/enum/record)
public record ClientChatSessionUpdatePacket(ChatSession chatSession) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientChatSessionUpdatePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            ChatSession.SERIALIZER, ClientChatSessionUpdatePacket::chatSession,
            // Code statement
            ClientChatSessionUpdatePacket::new
    // End of a block/expression
    );
// End of a block/expression
}
