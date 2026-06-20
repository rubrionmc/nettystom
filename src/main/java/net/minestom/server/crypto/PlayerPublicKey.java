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
// Import d'une classe nécessaire
import java.util.Arrays;

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

    // Début d'une méthode/d'un bloc
    public PlayerPublicKey {
        // Appelle une méthode
        signature = signature.clone();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object o) {
        // Embranchement : vérifie une condition
        if (!(o instanceof PlayerPublicKey(Instant at, PublicKey key, byte[] signature1))) return false;
        // Renvoie une valeur à l'appelant
        return Arrays.equals(signature(), signature1) && expiresAt().equals(at) && publicKey().equals(key);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Appelle une méthode
        int result = expiresAt().hashCode();
        // Appelle une méthode
        result = 31 * result + publicKey().hashCode();
        // Appelle une méthode
        result = 31 * result + Arrays.hashCode(signature());
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
