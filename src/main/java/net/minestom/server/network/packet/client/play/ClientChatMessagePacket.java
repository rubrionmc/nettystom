// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.crypto.MessageSignature;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.BitSet;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record ClientChatMessagePacket(String message, long timestamp,
                                      // Code statement
                                      long salt, @Nullable MessageSignature signature,
                                      // Start of a method/block
                                      int ackOffset, BitSet ackList, byte checksum) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientChatMessagePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            STRING, ClientChatMessagePacket::message,
            // Code statement
            LONG, ClientChatMessagePacket::timestamp,
            // Code statement
            LONG, ClientChatMessagePacket::salt,
            // Code statement
            MessageSignature.SERIALIZER.optional(), ClientChatMessagePacket::signature,
            // Code statement
            VAR_INT, ClientChatMessagePacket::ackOffset,
            // Code statement
            FixedBitSet(20), ClientChatMessagePacket::ackList,
            // Code statement
            BYTE, ClientChatMessagePacket::checksum,
            // Code statement
            ClientChatMessagePacket::new
    // End of a block/expression
    );

    // Start of a method/block
    public ClientChatMessagePacket {
        // Calls a method
        Check.argCondition(message.length() > 256, "Message length cannot be greater than 256");
        // Calls a method
        ackList = (BitSet) ackList.clone();
    // End of a block/expression
    }
// End of a block/expression
}
