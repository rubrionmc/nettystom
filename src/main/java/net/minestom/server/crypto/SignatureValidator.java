// Déclaration du paquet de ce fichier
package net.minestom.server.crypto;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.utils.crypto.KeyUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.security.*;
// Import d'une classe nécessaire
import java.util.function.Consumer;

/**
 * General purpose functional interface to verify signatures.<br>
 * Built in validators:
 * <ul>
 *     <li>{@link SignatureValidator#PASS}: will always report true</li>
 *     <li>{@link SignatureValidator#FAIL}: will always report false</li>
 *     <li>{@link SignatureValidator#YGGDRASIL}: Uses SHA1 with RSA and Yggdrasil Public Key for
 *     verifying signatures</li>
 *     <li>{@link SignatureValidator#from(Player)}: Uses SHA256 with RSA and the
 *     Player's {@link PlayerPublicKey#publicKey()}</li>
 *     <li>{@link SignatureValidator#from(PublicKey, KeyUtils.SignatureAlgorithm)}: General purpose factory method</li>
 * </ul>
 */
// Annotation pour l'élément suivant
@FunctionalInterface
// Déclaration de type (classe/interface/enum/record)
public interface SignatureValidator {
    // Affecte une valeur
    SignatureValidator PASS = (payload, signature) -> true;
    // Affecte une valeur
    SignatureValidator FAIL = (payload, signature) -> false;
    // Appelle une méthode
    SignatureValidator YGGDRASIL = createYggdrasilValidator();

    /**
     * Validate signature. This should not throw any exception instead it should
     * return false.
     *
     * @return true only if the signature is valid
     */
    // Appelle une méthode
    boolean validate(byte[] payload, byte[] signature);

    // Début d'une méthode/d'un bloc
    default boolean validate(Consumer<NetworkBuffer> payload, byte[] signature) {
        // Renvoie une valeur à l'appelant
        return validate(NetworkBuffer.makeArray(payload), signature);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static SignatureValidator from(PublicKey publicKey, KeyUtils.SignatureAlgorithm algorithm) {
        // Renvoie une valeur à l'appelant
        return ((payload, signature) -> {
            // Gestion des exceptions
            try {
                // Appelle une méthode
                final Signature sig = Signature.getInstance(algorithm.name());
                // Appelle une méthode
                sig.initVerify(publicKey);
                // Appelle une méthode
                sig.update(payload);
                // Renvoie une valeur à l'appelant
                return sig.verify(signature);
            // Début d'une méthode/d'un bloc
            } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
                // Renvoie une valeur à l'appelant
                return false;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a validator from the player's public key using SHA256 with RSA
     *
     * @param player source of the key
     * @return null if the player didn't send a public key
     */
    // Début d'une méthode/d'un bloc
    static @Nullable SignatureValidator from(Player player) {
        // Embranchement : vérifie une condition
        if (player.getPlayerConnection().playerPublicKey() == null) return null;
        // Renvoie une valeur à l'appelant
        return from(player.getPlayerConnection().playerPublicKey().publicKey(), KeyUtils.SignatureAlgorithm.SHA256withRSA);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static SignatureValidator createYggdrasilValidator() {
        // Gestion des exceptions
        try (var stream = SignatureValidator.class.getResourceAsStream("/yggdrasil_session_pubkey.der")) {
            // Embranchement : vérifie une condition
            if (stream == null) {
                // Appelle une méthode
                MinecraftServer.LOGGER.error("Couldn't find Yggdrasil public key, falling back to prohibiting validator!");
                // Renvoie une valeur à l'appelant
                return FAIL;
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return from(KeyUtils.publicRSAKeyFrom(stream.readAllBytes()), KeyUtils.SignatureAlgorithm.SHA1withRSA);
        // Début d'une méthode/d'un bloc
        } catch (Exception e) {
            // Appelle une méthode
            MinecraftServer.LOGGER.error("Exception while reading Yggdrasil public key, falling back to prohibiting validator!", e);
            // Renvoie une valeur à l'appelant
            return FAIL;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
