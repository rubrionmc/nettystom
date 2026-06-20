// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;

// Type declaration (class/interface/enum/record)
public record ClientCommandChatPacket(String message) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientCommandChatPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            STRING, ClientCommandChatPacket::message,
            // Code statement
            ClientCommandChatPacket::new);

    // Start of a method/block
    public ClientCommandChatPacket {
        // Calls a method
        Check.argCondition(message.length() > 256, "Message length cannot be greater than 256");
    // End of a block/expression
    }
// End of a block/expression
}
