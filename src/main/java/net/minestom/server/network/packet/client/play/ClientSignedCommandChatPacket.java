// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.crypto.ArgumentSignatures;
// Import of a required class
import net.minestom.server.crypto.LastSeenMessages;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record ClientSignedCommandChatPacket(String message, long timestamp,
                                            // Code statement
                                            long salt, ArgumentSignatures signatures,
                                            // Code statement
                                            LastSeenMessages.Update lastSeenMessages,
                                            // Start of a method/block
                                            byte checksum) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientSignedCommandChatPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            STRING, ClientSignedCommandChatPacket::message,
            // Code statement
            LONG, ClientSignedCommandChatPacket::timestamp,
            // Code statement
            LONG, ClientSignedCommandChatPacket::salt,
            // Code statement
            ArgumentSignatures.SERIALIZER, ClientSignedCommandChatPacket::signatures,
            // Code statement
            LastSeenMessages.Update.SERIALIZER, ClientSignedCommandChatPacket::lastSeenMessages,
            // Code statement
            BYTE, ClientSignedCommandChatPacket::checksum,
            // Code statement
            ClientSignedCommandChatPacket::new
    // End of a block/expression
    );

    // Start of a method/block
    public ClientSignedCommandChatPacket {
        // Calls a method
        Check.argCondition(message.length() > 256, "Message length cannot be greater than 256");
    // End of a block/expression
    }
// End of a block/expression
}
