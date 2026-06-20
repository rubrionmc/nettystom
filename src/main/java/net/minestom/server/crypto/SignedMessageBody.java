// Package declaration for this file
package net.minestom.server.crypto;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Import of a required class
import java.time.Instant;

// Type declaration (class/interface/enum/record)
public final class SignedMessageBody {

    // Type declaration (class/interface/enum/record)
    public record Packed(String content, Instant timeStamp, long salt,
                         // Start of a method/block
                         LastSeenMessages.Packed lastSeen) {
        // Start of a method/block
        public Packed {
            // Branch: checks a condition
            if (content.length() > MessageSignature.SIGNATURE_BYTE_LENGTH) {
                // Throws an exception
                throw new IllegalArgumentException("Message content too long");
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Assigns a value
        public static final NetworkBuffer.Type<Packed> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.STRING, Packed::content,
                // Code statement
                NetworkBuffer.INSTANT_MS, Packed::timeStamp,
                // Code statement
                NetworkBuffer.LONG, Packed::salt,
                // Code statement
                LastSeenMessages.Packed.SERIALIZER, Packed::lastSeen,
                // Code statement
                Packed::new
        // End of a block/expression
        );
    // End of a block/expression
    }
// End of a block/expression
}
