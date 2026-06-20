// Package declaration for this file
package net.minestom.server.network.packet.server.common;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Import of a required class
import java.util.Arrays;

// Type declaration (class/interface/enum/record)
public record CookieStorePacket(
        // Code statement
        String key, byte[] value
// Start of a method/block
) implements ServerPacket.Configuration, ServerPacket.Play {
    // Assigns a value
    public static final int MAX_VALUE_LENGTH = 5120;

    // Assigns a value
    public static final NetworkBuffer.Type<CookieStorePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.STRING, CookieStorePacket::key,
            // Code statement
            NetworkBuffer.BYTE_ARRAY, CookieStorePacket::value,
            // Code statement
            CookieStorePacket::new);

    // Start of a method/block
    public CookieStorePacket {
        // Calls a method
        Check.argCondition(value.length > MAX_VALUE_LENGTH, "Cookie value length too long: {0} > {1}", value.length, MAX_VALUE_LENGTH);
        // Calls a method
        value = value.clone();
    // End of a block/expression
    }

    // Start of a method/block
    public CookieStorePacket(Key key, byte[] value) {
        // Calls a method
        this(key.asString(), value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object object) {
        // Branch: checks a condition
        if (!(object instanceof CookieStorePacket(String key1, byte[] value1))) return false;
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
