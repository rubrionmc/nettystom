// Déclaration du paquet de ce fichier
package net.minestom.server.crypto;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.util.UUID;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.UUID;

// Déclaration de type (classe/interface/enum/record)
public record ChatSession(UUID sessionId, PlayerPublicKey publicKey) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ChatSession> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            UUID, ChatSession::sessionId,
            // Instruction de code
            PlayerPublicKey.SERIALIZER, ChatSession::publicKey,
            // Instruction de code
            ChatSession::new
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
