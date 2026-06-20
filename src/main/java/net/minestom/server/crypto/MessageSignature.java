// Package declaration for this file
package net.minestom.server.crypto;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.Arrays;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.FixedRawBytes;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record MessageSignature(byte[] signature) {
    // Assigns a value
    static final int SIGNATURE_BYTE_LENGTH = 256;

    // Start of a method/block
    public MessageSignature {
        // Branch: checks a condition
        if (signature.length != SIGNATURE_BYTE_LENGTH) {
            // Throws an exception
            throw new IllegalArgumentException("Signature must be 256 bytes long");
        // End of a block/expression
        }
        // Calls a method
        signature = signature.clone();
    // End of a block/expression
    }

    // Assigns a value
    public static final NetworkBuffer.Type<MessageSignature> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            FixedRawBytes(SIGNATURE_BYTE_LENGTH), MessageSignature::signature,
            // Code statement
            MessageSignature::new
    // End of a block/expression
    );

    // Type declaration (class/interface/enum/record)
    public record Packed(int id, @UnknownNullability MessageSignature fullSignature) {
        // Assigns a value
        private static final int FULL_SIGNATURE = -1;

        // Start of a method/block
        public Packed(MessageSignature signature) {
            // Calls a method
            this(FULL_SIGNATURE, signature);
        // End of a block/expression
        }

        // Start of a method/block
        public Packed {
            // Calls a method
            Check.argCondition(id == FULL_SIGNATURE && fullSignature == null, "Full signature must be present");
        // End of a block/expression
        }

        // Assigns a value
        public static final NetworkBuffer.Type<Packed> SERIALIZER = new NetworkBuffer.Type<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void write(NetworkBuffer buffer, Packed value) {
                // Calls a method
                buffer.write(VAR_INT, value.id + 1);
                // Branch: checks a condition
                if (value.fullSignature != null) buffer.write(MessageSignature.SERIALIZER, value.fullSignature);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Packed read(NetworkBuffer buffer) {
                // Calls a method
                final int id = buffer.read(VAR_INT) - 1;
                // Returns a value to the caller
                return id == FULL_SIGNATURE ? new MessageSignature.Packed(buffer.read(MessageSignature.SERIALIZER))
                        // Calls a method
                        : new MessageSignature.Packed(id, null);
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object o) {
        // Branch: checks a condition
        if (!(o instanceof MessageSignature(byte[] signature1))) return false;
        // Returns a value to the caller
        return Arrays.equals(signature(), signature1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Returns a value to the caller
        return Arrays.hashCode(signature());
    // End of a block/expression
    }
// End of a block/expression
}
