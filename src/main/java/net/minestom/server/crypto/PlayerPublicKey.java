// Déclaration du paquet de ce fichier
package net.minestom.server.crypto;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.security.PublicKey;
// Import d'une classe nécessaire
import java.time.Instant;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

/**
 * Player's public key used to sign chat messages
 */
// Déclaration de type (classe/interface/enum/record)
public record PlayerPublicKey(Instant expiresAt, PublicKey publicKey, byte[] signature) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<PlayerPublicKey> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            INSTANT_MS, PlayerPublicKey::expiresAt,
            // Instruction de code
            PUBLIC_KEY, PlayerPublicKey::publicKey,
            // Instruction de code
            BYTE_ARRAY, PlayerPublicKey::signature,
            // Instruction de code
            PlayerPublicKey::new
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
