// Package declaration for this file
package net.minestom.server.crypto;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Import of a required class
import java.util.UUID;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.UUID;

// Type declaration (class/interface/enum/record)
public record ChatSession(UUID sessionId, PlayerPublicKey publicKey) {
    // Assigns a value
    public static final NetworkBuffer.Type<ChatSession> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            UUID, ChatSession::sessionId,
            // Code statement
            PlayerPublicKey.SERIALIZER, ChatSession::publicKey,
            // Code statement
            ChatSession::new
    // End of a block/expression
    );
// End of a block/expression
}
