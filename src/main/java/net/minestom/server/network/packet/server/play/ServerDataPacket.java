// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Arrays;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BYTE_ARRAY;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.COMPONENT;

// Type declaration (class/interface/enum/record)
public record ServerDataPacket(Component motd, byte @Nullable [] iconBase64) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ServerDataPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            COMPONENT, ServerDataPacket::motd,
            // Code statement
            BYTE_ARRAY.optional(), ServerDataPacket::iconBase64,
            // Code statement
            ServerDataPacket::new);

    // Start of a method/block
    public ServerDataPacket {
        // Calls a method
        iconBase64 = iconBase64 != null ? iconBase64.clone() : null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object o) {
        // Branch: checks a condition
        if (!(o instanceof ServerDataPacket(Component motd1, byte[] base64))) return false;
        // Returns a value to the caller
        return motd().equals(motd1) && Arrays.equals(iconBase64(), base64);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Calls a method
        int result = motd().hashCode();
        // Calls a method
        result = 31 * result + Arrays.hashCode(iconBase64());
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }
// End of a block/expression
}
