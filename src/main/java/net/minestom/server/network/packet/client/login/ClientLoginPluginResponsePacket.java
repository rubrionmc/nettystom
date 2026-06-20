// Package declaration for this file
package net.minestom.server.network.packet.client.login;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Arrays;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.RAW_BYTES;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record ClientLoginPluginResponsePacket(int messageId, byte @Nullable [] data) implements ClientPacket.Login {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientLoginPluginResponsePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, ClientLoginPluginResponsePacket::messageId,
            // Code statement
            RAW_BYTES.optional(), ClientLoginPluginResponsePacket::data,
            // Code statement
            ClientLoginPluginResponsePacket::new);

    // Start of a method/block
    public ClientLoginPluginResponsePacket {
        // Calls a method
        data = data != null ? data.clone() : null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object object) {
        // Branch: checks a condition
        if (!(object instanceof ClientLoginPluginResponsePacket(int id, byte[] data1))) return false;
        // Returns a value to the caller
        return messageId() == id && Arrays.equals(data(), data1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Calls a method
        int result = messageId();
        // Calls a method
        result = 31 * result + Arrays.hashCode(data());
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }
// End of a block/expression
}
