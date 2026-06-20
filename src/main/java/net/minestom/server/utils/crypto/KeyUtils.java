// Déclaration du paquet de ce fichier
package net.minestom.server.utils.crypto;

// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.nio.charset.StandardCharsets;
// Import d'une classe nécessaire
import java.security.KeyFactory;
// Import d'une classe nécessaire
import java.security.NoSuchAlgorithmException;
// Import d'une classe nécessaire
import java.security.PublicKey;
// Import d'une classe nécessaire
import java.security.spec.InvalidKeySpecException;
// Import d'une classe nécessaire
import java.security.spec.X509EncodedKeySpec;
// Import d'une classe nécessaire
import java.util.Base64;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class KeyUtils {
    // Appelle une méthode
    private static final Base64.Encoder MIME_ENCODER = Base64.getMimeEncoder(76, "\n".getBytes(StandardCharsets.UTF_8));
    // Affecte une valeur
    private static final String RSA_HEADER = "-----BEGIN RSA PUBLIC KEY-----\n";
    // Affecte une valeur
    private static final String RSA_FOOTER = "\n-----END RSA PUBLIC KEY-----\n";

    // Déclaration de type (classe/interface/enum/record)
    public enum SignatureAlgorithm {
        // Instruction de code
        SHA256withRSA,
        // Instruction de code
        SHA1withRSA
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum KeyAlgorithm {
        // Instruction de code
        RSA
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private KeyUtils() {
        //no instance
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static String rsaPublicKeyToString(PublicKey publicKey) {
        // Embranchement : vérifie une condition
        if (!publicKey.getAlgorithm().equals(KeyAlgorithm.RSA.name())) {
            // Lève une exception
            throw new IllegalArgumentException("The provided key isn't an RSA key!");
        // Branche alternative de la condition
        } else {
            // Renvoie une valeur à l'appelant
            return RSA_HEADER + MIME_ENCODER.encodeToString(publicKey.getEncoded()) + RSA_FOOTER;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static PublicKey publicRSAKeyFrom(byte[] data) {
        // Appelle une méthode
        final X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        // Instruction de code
        final KeyFactory keyFactory;
        // Gestion des exceptions
        try {
            // Appelle une méthode
            keyFactory = KeyFactory.getInstance(KeyAlgorithm.RSA.name());
            // Renvoie une valeur à l'appelant
            return keyFactory.generatePublic(spec);
        // Début d'une méthode/d'un bloc
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // Lève une exception
            throw new RuntimeException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
