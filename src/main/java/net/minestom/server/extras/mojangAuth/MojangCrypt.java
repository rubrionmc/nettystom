// Déclaration du paquet de ce fichier
package net.minestom.server.extras.mojangAuth;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.slf4j.Logger;
// Import d'une classe nécessaire
import org.slf4j.LoggerFactory;

// Import d'une classe nécessaire
import javax.crypto.*;
// Import d'une classe nécessaire
import javax.crypto.spec.IvParameterSpec;
// Import d'une classe nécessaire
import javax.crypto.spec.SecretKeySpec;
// Import d'une classe nécessaire
import java.io.UnsupportedEncodingException;
// Import d'une classe nécessaire
import java.security.*;

// Déclaration de type (classe/interface/enum/record)
public final class MojangCrypt {
    // Appelle une méthode
    private static final Logger LOGGER = LoggerFactory.getLogger(MojangCrypt.class);

    // Début d'une méthode/d'un bloc
    public static @Nullable KeyPair generateKeyPair() {
        // Gestion des exceptions
        try {
            // Appelle une méthode
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            // Appelle une méthode
            keyGen.initialize(1024);
            // Renvoie une valeur à l'appelant
            return keyGen.generateKeyPair();
        // Début d'une méthode/d'un bloc
        } catch (NoSuchAlgorithmException e) {
            // Appelle une méthode
            MinecraftServer.getExceptionManager().handleException(e);
            // Appelle une méthode
            LOGGER.error("Key pair generation failed!");
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static byte @Nullable [] digestData(String data, PublicKey publicKey, SecretKey secretKey) {
        // Gestion des exceptions
        try {
            // Renvoie une valeur à l'appelant
            return digestData("SHA-1", data.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded());
        // Début d'une méthode/d'un bloc
        } catch (UnsupportedEncodingException e) {
            // Appelle une méthode
            MinecraftServer.getExceptionManager().handleException(e);
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static byte @Nullable [] digestData(String algorithm, byte[]... data) {
        // Gestion des exceptions
        try {
            // Appelle une méthode
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            // Boucle : répète un bloc
            for (byte[] bytes : data) {
                // Appelle une méthode
                digest.update(bytes);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return digest.digest();
        // Début d'une méthode/d'un bloc
        } catch (NoSuchAlgorithmException e) {
            // Appelle une méthode
            MinecraftServer.getExceptionManager().handleException(e);
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static SecretKey decryptByteToSecretKey(PrivateKey privateKey, byte[] bytes) {
        // Renvoie une valeur à l'appelant
        return new SecretKeySpec(decryptUsingKey(privateKey, bytes), "AES");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static byte[] decryptUsingKey(Key key, byte[] bytes) {
        // Renvoie une valeur à l'appelant
        return cipherData(2, key, bytes);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static byte[] cipherData(int mode, Key key, byte[] data) {
        // Gestion des exceptions
        try {
            // Renvoie une valeur à l'appelant
            return setupCipher(mode, key.getAlgorithm(), key).doFinal(data);
        // Début d'une méthode/d'un bloc
        } catch (IllegalBlockSizeException | BadPaddingException var4) {
            // Appelle une méthode
            MinecraftServer.getExceptionManager().handleException(var4);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        LOGGER.error("Cipher data failed!");
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Cipher setupCipher(int mode, String transformation, Key key) {
        // Gestion des exceptions
        try {
            // Appelle une méthode
            Cipher cipher4 = Cipher.getInstance(transformation);
            // Appelle une méthode
            cipher4.init(mode, key);
            // Renvoie une valeur à l'appelant
            return cipher4;
        // Début d'une méthode/d'un bloc
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException var4) {
            // Appelle une méthode
            MinecraftServer.getExceptionManager().handleException(var4);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        LOGGER.error("Cipher creation failed!");
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Cipher getCipher(int mode, Key key) {
        // Gestion des exceptions
        try {
            // Appelle une méthode
            Cipher cipher3 = Cipher.getInstance("AES/CFB8/NoPadding");
            // Appelle une méthode
            cipher3.init(mode, key, new IvParameterSpec(key.getEncoded()));
            // Renvoie une valeur à l'appelant
            return cipher3;
        // Début d'une méthode/d'un bloc
        } catch (GeneralSecurityException e) {
            // Lève une exception
            throw new RuntimeException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
