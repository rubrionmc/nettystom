// Package declaration for this file
package net.minestom.server.crypto;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.utils.crypto.KeyUtils;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.security.*;
// Import of a required class
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
// Annotation for the following element
@FunctionalInterface
// Type declaration (class/interface/enum/record)
public interface SignatureValidator {
    // Calls a method
    SignatureValidator PASS = (_, _) -> true;
    // Calls a method
    SignatureValidator FAIL = (_, _) -> false;
    // Calls a method
    SignatureValidator YGGDRASIL = createYggdrasilValidator();

    /**
     * Validate signature. This should not throw any exception instead it should
     * return false.
     *
     * @return true only if the signature is valid
     */
    // Calls a method
    boolean validate(byte[] payload, byte[] signature);

    // Start of a method/block
    default boolean validate(Consumer<NetworkBuffer> payload, byte[] signature) {
        // Returns a value to the caller
        return validate(NetworkBuffer.makeArray(payload), signature);
    // End of a block/expression
    }

    // Start of a method/block
    static SignatureValidator from(PublicKey publicKey, KeyUtils.SignatureAlgorithm algorithm) {
        // Returns a value to the caller
        return ((payload, signature) -> {
            // Exception handling
            try {
                // Calls a method
                final Signature sig = Signature.getInstance(algorithm.name());
                // Calls a method
                sig.initVerify(publicKey);
                // Calls a method
                sig.update(payload);
                // Returns a value to the caller
                return sig.verify(signature);
            // Start of a method/block
            } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
                // Returns a value to the caller
                return false;
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }

    /**
     * Creates a validator from the player's public key using SHA256 with RSA
     *
     * @param player source of the key
     * @return null if the player didn't send a public key
     */
    // Start of a method/block
    static @Nullable SignatureValidator from(Player player) {
        // Calls a method
        final PlayerPublicKey playerPublicKey = player.getPlayerConnection().playerPublicKey();
        // Branch: checks a condition
        if (playerPublicKey == null) return null;
        // Returns a value to the caller
        return from(playerPublicKey.publicKey(), KeyUtils.SignatureAlgorithm.SHA256withRSA);
    // End of a block/expression
    }

    // Start of a method/block
    private static SignatureValidator createYggdrasilValidator() {
        // Exception handling
        try (var stream = SignatureValidator.class.getResourceAsStream("/yggdrasil_session_pubkey.der")) {
            // Branch: checks a condition
            if (stream == null) {
                // Calls a method
                MinecraftServer.LOGGER.error("Couldn't find Yggdrasil public key, falling back to prohibiting validator!");
                // Returns a value to the caller
                return FAIL;
            // End of a block/expression
            }
            // Returns a value to the caller
            return from(KeyUtils.publicRSAKeyFrom(stream.readAllBytes()), KeyUtils.SignatureAlgorithm.SHA1withRSA);
        // Start of a method/block
        } catch (Exception e) {
            // Calls a method
            MinecraftServer.LOGGER.error("Exception while reading Yggdrasil public key, falling back to prohibiting validator!", e);
            // Returns a value to the caller
            return FAIL;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
