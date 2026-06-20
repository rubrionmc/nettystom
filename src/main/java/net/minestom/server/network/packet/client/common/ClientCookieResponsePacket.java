// Package declaration for this file
package net.minestom.server.network.packet.client.common;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.network.packet.server.common.CookieStorePacket;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Arrays;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BYTE_ARRAY;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;

// Type declaration (class/interface/enum/record)
public record ClientCookieResponsePacket(
        // Code statement
        String key,
        // Code statement
        byte @Nullable [] value
// Start of a method/block
) implements ClientPacket.Login, ClientPacket.Configuration, ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientCookieResponsePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            STRING, ClientCookieResponsePacket::key,
            // Code statement
            BYTE_ARRAY.optional(), ClientCookieResponsePacket::value,
            // Code statement
            ClientCookieResponsePacket::new);

    // Start of a method/block
    public ClientCookieResponsePacket {
        // Calls a method
        Check.argCondition(key.length() > Short.MAX_VALUE, "Key length cannot be greater than Short.MAX_VALUE");
        // Code statement
        Check.argCondition(value != null && value.length > CookieStorePacket.MAX_VALUE_LENGTH,
                // Code statement
                "Value is too long: {0} > {1}", value != null ? value.length : 0, CookieStorePacket.MAX_VALUE_LENGTH);
        // Calls a method
        value = value != null ? value.clone() : null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object object) {
        // Branch: checks a condition
        if (!(object instanceof ClientCookieResponsePacket(String key1, byte[] value1))) return false;
        // Returns a value to the caller
        return key().equals(key1) && Arrays.equals(value(), value1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Calls a method
        int result = key().hashCode();
        // Calls a method
        result = 31 * result + Arrays.hashCode(value());
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }
// End of a block/expression
}
