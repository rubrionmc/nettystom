// Package declaration for this file
package net.minestom.server.network.packet.server.login;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Import of a required class
import java.util.Arrays;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record LoginPluginRequestPacket(int messageId, String channel,
                                       // Start of a method/block
                                       byte[] data) implements ServerPacket.Login {
    // Assigns a value
    public static final NetworkBuffer.Type<LoginPluginRequestPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, LoginPluginRequestPacket::messageId,
            // Code statement
            STRING, LoginPluginRequestPacket::channel,
            // Code statement
            RAW_BYTES, LoginPluginRequestPacket::data,
            // Code statement
            LoginPluginRequestPacket::new);

    // Start of a method/block
    public LoginPluginRequestPacket {
        // Calls a method
        data = data.clone();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object object) {
        // Branch: checks a condition
        if (!(object instanceof LoginPluginRequestPacket(int messageId1, String channel1, byte[] data1))) return false;
        // Returns a value to the caller
        return messageId() == messageId1 && Arrays.equals(data(), data1) && channel().equals(channel1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Calls a method
        int result = messageId();
        // Calls a method
        result = 31 * result + channel().hashCode();
        // Calls a method
        result = 31 * result + Arrays.hashCode(data());
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }
// End of a block/expression
}
