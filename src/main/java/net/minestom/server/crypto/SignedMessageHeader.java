// Package declaration for this file
package net.minestom.server.crypto;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.UUID;

// Type declaration (class/interface/enum/record)
public record SignedMessageHeader(@Nullable MessageSignature previousSignature, UUID sender) {
    // Assigns a value
    public static final NetworkBuffer.Type<SignedMessageHeader> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            MessageSignature.SERIALIZER.optional(), SignedMessageHeader::previousSignature,
            // Code statement
            NetworkBuffer.UUID, SignedMessageHeader::sender,
            // Code statement
            SignedMessageHeader::new
    // End of a block/expression
    );
// End of a block/expression
}
