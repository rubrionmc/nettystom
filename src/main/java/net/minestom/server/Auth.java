// Package declaration for this file
package net.minestom.server;

// Import of a required class
import net.minestom.server.extras.mojangAuth.MojangCrypt;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import javax.crypto.Mac;
// Import of a required class
import javax.crypto.spec.SecretKeySpec;
// Import of a required class
import java.security.*;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.Set;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public sealed interface Auth {
    // Type declaration (class/interface/enum/record)
    record Offline() implements Auth {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Online(KeyPair keyPair) implements Auth {
        // Start of a method/block
        public Online() {
            // Calls a method
            this(Objects.requireNonNull(MojangCrypt.generateKeyPair()));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Velocity(Key key) implements Auth {
        // Assigns a value
        public static final String PLAYER_INFO_CHANNEL = "velocity:player_info";
        // Assigns a value
        private static final String MAC_ALGORITHM = "HmacSHA256";
        // Assigns a value
        private static final int SUPPORTED_FORWARDING_VERSION = 1;

        // Start of a method/block
        public Velocity(String secret) {
            // Calls a method
            this(secretKey(secret));
        // End of a block/expression
        }

        // Start of a method/block
        public boolean checkIntegrity(NetworkBuffer buffer) {
            // Assigns a value
            final byte[] signature = new byte[32];
            // Loop: repeats a block
            for (int i = 0; i < signature.length; i++) {
                // Calls a method
                signature[i] = buffer.read(BYTE);
            // End of a block/expression
            }
            // Calls a method
            final long index = buffer.readIndex();
            // Calls a method
            final byte[] data = buffer.read(RAW_BYTES);
            // Calls a method
            buffer.readIndex(index);
            // Exception handling
            try {
                // Calls a method
                Mac mac = Mac.getInstance(MAC_ALGORITHM);
                // Calls a method
                mac.init(key);
                // Calls a method
                final byte[] mySignature = mac.doFinal(data);
                // Branch: checks a condition
                if (!MessageDigest.isEqual(signature, mySignature)) {
                    // Returns a value to the caller
                    return false;
                // End of a block/expression
                }
            // Start of a method/block
            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                // Throws an exception
                throw new RuntimeException(e);
            // End of a block/expression
            }
            // Calls a method
            final int version = buffer.read(VAR_INT);
            // Returns a value to the caller
            return version == SUPPORTED_FORWARDING_VERSION;
        // End of a block/expression
        }

        // Start of a method/block
        public static Key secretKey(String secret) {
            // Returns a value to the caller
            return new SecretKeySpec(secret.getBytes(), MAC_ALGORITHM);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Bungee(@Nullable Set<String> bungeeGuardTokens) implements Auth {
        // Start of a method/block
        public Bungee {
            // Branch: checks a condition
            if (bungeeGuardTokens != null && bungeeGuardTokens.isEmpty()) {
                // Throws an exception
                throw new IllegalArgumentException("BungeeGuard tokens cannot be empty");
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Start of a method/block
        public Bungee() {
            // Calls a method
            this(null);
        // End of a block/expression
        }

        // Start of a method/block
        public boolean validToken(String token) {
            // Returns a value to the caller
            return bungeeGuardTokens == null || bungeeGuardTokens.contains(token);
        // End of a block/expression
        }

        // Start of a method/block
        public boolean guard() {
            // Returns a value to the caller
            return bungeeGuardTokens != null;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
