// Package declaration for this file
package net.minestom.server.network.packet.client.common;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Import of a required class
import java.util.Arrays;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.RAW_BYTES;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;

// Type declaration (class/interface/enum/record)
public record ClientPluginMessagePacket(String channel, byte[] data) implements ClientPacket.Configuration, ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientPluginMessagePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            STRING, ClientPluginMessagePacket::channel,
            // Code statement
            RAW_BYTES, ClientPluginMessagePacket::data,
            // Code statement
            ClientPluginMessagePacket::new);

    // Start of a method/block
    public ClientPluginMessagePacket {
        // Branch: checks a condition
        if (channel.length() > 256)
            // Throws an exception
            throw new IllegalArgumentException("Channel cannot be more than 256 characters long");
        // Calls a method
        Check.argCondition(data.length > Short.MAX_VALUE, "Data cannot be longer than Short.MAX_VALUE bytes");
        // Calls a method
        data = data.clone();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object o) {
        // Branch: checks a condition
        if (!(o instanceof ClientPluginMessagePacket(String channel1, byte[] data1))) return false;
        // Returns a value to the caller
        return Arrays.equals(data(), data1) && channel().equals(channel1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Calls a method
        int result = channel().hashCode();
        // Calls a method
        result = 31 * result + Arrays.hashCode(data());
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }
// End of a block/expression
}
