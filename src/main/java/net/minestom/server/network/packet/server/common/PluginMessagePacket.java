// Package declaration for this file
package net.minestom.server.network.packet.server.common;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.Objects;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.RAW_BYTES;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;

// Type declaration (class/interface/enum/record)
public record PluginMessagePacket(String channel,
                                  // Start of a method/block
                                  byte[] data) implements ServerPacket.Configuration, ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<PluginMessagePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            STRING, PluginMessagePacket::channel,
            // Code statement
            RAW_BYTES, PluginMessagePacket::data,
            // Code statement
            PluginMessagePacket::new);

    // Start of a method/block
    public PluginMessagePacket {
        // Calls a method
        data = data.clone();
    // End of a block/expression
    }

    /**
     * Gets the current server brand name packet.
     * <p>
     * Sent to all players when the name changes.
     *
     * @return the current brand name packet
     */
    // Start of a method/block
    public static PluginMessagePacket brandPacket(String brandName) {
        // Calls a method
        final byte[] data = NetworkBuffer.makeArray(STRING, brandName);
        // Returns a value to the caller
        return new PluginMessagePacket("minecraft:brand", data);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object object) {
        // Branch: checks a condition
        if (!(object instanceof PluginMessagePacket(String channel1, byte[] data1))) return false;
        // Returns a value to the caller
        return Arrays.equals(data(), data1) && Objects.equals(channel(), channel1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Calls a method
        int result = Objects.hashCode(channel());
        // Calls a method
        result = 31 * result + Arrays.hashCode(data());
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }
// End of a block/expression
}
