// Package declaration for this file
package net.minestom.server.crypto;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Import of a required class
import java.util.Arrays;

// Type declaration (class/interface/enum/record)
public record SaltSignaturePair(long salt, byte[] signature) {
    // Assigns a value
    public static final NetworkBuffer.Type<SaltSignaturePair> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.LONG, SaltSignaturePair::salt,
            // Code statement
            NetworkBuffer.BYTE_ARRAY, SaltSignaturePair::signature,
            // Code statement
            SaltSignaturePair::new
    // End of a block/expression
    );

    // Start of a method/block
    public SaltSignaturePair {
        // Calls a method
        signature = signature.clone();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object o) {
        // Branch: checks a condition
        if (!(o instanceof SaltSignaturePair(long salt1, byte[] signature1))) return false;
        // Returns a value to the caller
        return salt() == salt1 && Arrays.equals(signature(), signature1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Calls a method
        int result = Long.hashCode(salt());
        // Calls a method
        result = 31 * result + Arrays.hashCode(signature());
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }
// End of a block/expression
}
