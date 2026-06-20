// Package declaration for this file
package net.minestom.server.crypto;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Import of a required class
import java.security.PublicKey;
// Import of a required class
import java.time.Instant;
// Import of a required class
import java.util.Arrays;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

/**
 * Player's public key used to sign chat messages
 */
// Type declaration (class/interface/enum/record)
public record PlayerPublicKey(Instant expiresAt, PublicKey publicKey, byte[] signature) {
    // Assigns a value
    public static final NetworkBuffer.Type<PlayerPublicKey> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            INSTANT_MS, PlayerPublicKey::expiresAt,
            // Code statement
            PUBLIC_KEY, PlayerPublicKey::publicKey,
            // Code statement
            BYTE_ARRAY, PlayerPublicKey::signature,
            // Code statement
            PlayerPublicKey::new
    // End of a block/expression
    );

    // Start of a method/block
    public PlayerPublicKey {
        // Calls a method
        signature = signature.clone();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object o) {
        // Branch: checks a condition
        if (!(o instanceof PlayerPublicKey(Instant at, PublicKey key, byte[] signature1))) return false;
        // Returns a value to the caller
        return Arrays.equals(signature(), signature1) && expiresAt().equals(at) && publicKey().equals(key);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Calls a method
        int result = expiresAt().hashCode();
        // Calls a method
        result = 31 * result + publicKey().hashCode();
        // Calls a method
        result = 31 * result + Arrays.hashCode(signature());
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }
// End of a block/expression
}
