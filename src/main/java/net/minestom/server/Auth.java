// Déclaration du paquet de ce fichier
package net.minestom.server;

// Import d'une classe nécessaire
import net.minestom.server.extras.mojangAuth.MojangCrypt;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import javax.crypto.Mac;
// Import d'une classe nécessaire
import javax.crypto.spec.SecretKeySpec;
// Import d'une classe nécessaire
import java.security.*;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.Set;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public sealed interface Auth {
    // Déclaration de type (classe/interface/enum/record)
    record Offline() implements Auth {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Online(KeyPair keyPair) implements Auth {
        // Début d'une méthode/d'un bloc
        public Online() {
            // Appelle une méthode
            this(Objects.requireNonNull(MojangCrypt.generateKeyPair()));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Velocity(Key key) implements Auth {
        // Affecte une valeur
        public static final String PLAYER_INFO_CHANNEL = "velocity:player_info";
        // Affecte une valeur
        private static final String MAC_ALGORITHM = "HmacSHA256";
        // Affecte une valeur
        private static final int SUPPORTED_FORWARDING_VERSION = 1;

        // Début d'une méthode/d'un bloc
        public Velocity(String secret) {
            // Appelle une méthode
            this(secretKey(secret));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean checkIntegrity(NetworkBuffer buffer) {
            // Affecte une valeur
            final byte[] signature = new byte[32];
            // Boucle : répète un bloc
            for (int i = 0; i < signature.length; i++) {
                // Appelle une méthode
                signature[i] = buffer.read(BYTE);
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            final long index = buffer.readIndex();
            // Appelle une méthode
            final byte[] data = buffer.read(RAW_BYTES);
            // Appelle une méthode
            buffer.readIndex(index);
            // Gestion des exceptions
            try {
                // Appelle une méthode
                Mac mac = Mac.getInstance(MAC_ALGORITHM);
                // Appelle une méthode
                mac.init(key);
                // Appelle une méthode
                final byte[] mySignature = mac.doFinal(data);
                // Embranchement : vérifie une condition
                if (!MessageDigest.isEqual(signature, mySignature)) {
                    // Renvoie une valeur à l'appelant
                    return false;
                // Fin d'un bloc/d'une expression
                }
            // Début d'une méthode/d'un bloc
            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                // Lève une exception
                throw new RuntimeException(e);
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            final int version = buffer.read(VAR_INT);
            // Renvoie une valeur à l'appelant
            return version == SUPPORTED_FORWARDING_VERSION;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public static Key secretKey(String secret) {
            // Renvoie une valeur à l'appelant
            return new SecretKeySpec(secret.getBytes(), MAC_ALGORITHM);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Bungee(@Nullable Set<String> bungeeGuardTokens) implements Auth {
        // Début d'une méthode/d'un bloc
        public Bungee {
            // Embranchement : vérifie une condition
            if (bungeeGuardTokens != null && bungeeGuardTokens.isEmpty()) {
                // Lève une exception
                throw new IllegalArgumentException("BungeeGuard tokens cannot be empty");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Bungee() {
            // Appelle une méthode
            this(null);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean validToken(String token) {
            // Renvoie une valeur à l'appelant
            return bungeeGuardTokens == null || bungeeGuardTokens.contains(token);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean guard() {
            // Renvoie une valeur à l'appelant
            return bungeeGuardTokens != null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
