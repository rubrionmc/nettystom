// Package declaration for this file
package net.minestom.server.utils.crypto;

// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.nio.charset.StandardCharsets;
// Import of a required class
import java.security.KeyFactory;
// Import of a required class
import java.security.NoSuchAlgorithmException;
// Import of a required class
import java.security.PublicKey;
// Import of a required class
import java.security.spec.InvalidKeySpecException;
// Import of a required class
import java.security.spec.X509EncodedKeySpec;
// Import of a required class
import java.util.Base64;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class KeyUtils {
    // Calls a method
    private static final Base64.Encoder MIME_ENCODER = Base64.getMimeEncoder(76, "\n".getBytes(StandardCharsets.UTF_8));
    // Assigns a value
    private static final String RSA_HEADER = "-----BEGIN RSA PUBLIC KEY-----\n";
    // Assigns a value
    private static final String RSA_FOOTER = "\n-----END RSA PUBLIC KEY-----\n";

    // Type declaration (class/interface/enum/record)
    public enum SignatureAlgorithm {
        // Code statement
        SHA256withRSA,
        // Code statement
        SHA1withRSA
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum KeyAlgorithm {
        // Code statement
        RSA
    // End of a block/expression
    }

    // Start of a method/block
    private KeyUtils() {
        //no instance
    // End of a block/expression
    }

    // Start of a method/block
    public static String rsaPublicKeyToString(PublicKey publicKey) {
        // Branch: checks a condition
        if (!publicKey.getAlgorithm().equals(KeyAlgorithm.RSA.name())) {
            // Throws an exception
            throw new IllegalArgumentException("The provided key isn't an RSA key!");
        // Alternative branch of the condition
        } else {
            // Returns a value to the caller
            return RSA_HEADER + MIME_ENCODER.encodeToString(publicKey.getEncoded()) + RSA_FOOTER;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static PublicKey publicRSAKeyFrom(byte[] data) {
        // Calls a method
        final X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        // Code statement
        final KeyFactory keyFactory;
        // Exception handling
        try {
            // Calls a method
            keyFactory = KeyFactory.getInstance(KeyAlgorithm.RSA.name());
            // Returns a value to the caller
            return keyFactory.generatePublic(spec);
        // Start of a method/block
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // Throws an exception
            throw new RuntimeException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
