// Package declaration for this file
package net.minestom.server.extras.mojangAuth;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.slf4j.Logger;
// Import of a required class
import org.slf4j.LoggerFactory;

// Import of a required class
import javax.crypto.*;
// Import of a required class
import javax.crypto.spec.IvParameterSpec;
// Import of a required class
import javax.crypto.spec.SecretKeySpec;
// Import of a required class
import java.io.UnsupportedEncodingException;
// Import of a required class
import java.security.*;

// Type declaration (class/interface/enum/record)
public final class MojangCrypt {
    // Calls a method
    private static final Logger LOGGER = LoggerFactory.getLogger(MojangCrypt.class);

    // Start of a method/block
    public static @Nullable KeyPair generateKeyPair() {
        // Exception handling
        try {
            // Calls a method
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            // Calls a method
            keyGen.initialize(1024);
            // Returns a value to the caller
            return keyGen.generateKeyPair();
        // Start of a method/block
        } catch (NoSuchAlgorithmException e) {
            // Calls a method
            MinecraftServer.getExceptionManager().handleException(e);
            // Calls a method
            LOGGER.error("Key pair generation failed!");
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static byte @Nullable [] digestData(String data, PublicKey publicKey, SecretKey secretKey) {
        // Exception handling
        try {
            // Returns a value to the caller
            return digestData("SHA-1", data.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded());
        // Start of a method/block
        } catch (UnsupportedEncodingException e) {
            // Calls a method
            MinecraftServer.getExceptionManager().handleException(e);
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static byte @Nullable [] digestData(String algorithm, byte[]... data) {
        // Exception handling
        try {
            // Calls a method
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            // Loop: repeats a block
            for (byte[] bytes : data) {
                // Calls a method
                digest.update(bytes);
            // End of a block/expression
            }
            // Returns a value to the caller
            return digest.digest();
        // Start of a method/block
        } catch (NoSuchAlgorithmException e) {
            // Calls a method
            MinecraftServer.getExceptionManager().handleException(e);
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static SecretKey decryptByteToSecretKey(PrivateKey privateKey, byte[] bytes) {
        // Returns a value to the caller
        return new SecretKeySpec(decryptUsingKey(privateKey, bytes), "AES");
    // End of a block/expression
    }

    // Start of a method/block
    public static byte[] decryptUsingKey(Key key, byte[] bytes) {
        // Returns a value to the caller
        return cipherData(2, key, bytes);
    // End of a block/expression
    }

    // Start of a method/block
    private static byte[] cipherData(int mode, Key key, byte[] data) {
        // Exception handling
        try {
            // Returns a value to the caller
            return setupCipher(mode, key.getAlgorithm(), key).doFinal(data);
        // Start of a method/block
        } catch (IllegalBlockSizeException | BadPaddingException var4) {
            // Calls a method
            MinecraftServer.getExceptionManager().handleException(var4);
        // End of a block/expression
        }
        // Calls a method
        LOGGER.error("Cipher data failed!");
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    // Start of a method/block
    private static Cipher setupCipher(int mode, String transformation, Key key) {
        // Exception handling
        try {
            // Calls a method
            Cipher cipher4 = Cipher.getInstance(transformation);
            // Calls a method
            cipher4.init(mode, key);
            // Returns a value to the caller
            return cipher4;
        // Start of a method/block
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException var4) {
            // Calls a method
            MinecraftServer.getExceptionManager().handleException(var4);
        // End of a block/expression
        }
        // Calls a method
        LOGGER.error("Cipher creation failed!");
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    // Start of a method/block
    public static Cipher getCipher(int mode, Key key) {
        // Exception handling
        try {
            // Calls a method
            Cipher cipher3 = Cipher.getInstance("AES/CFB8/NoPadding");
            // Calls a method
            cipher3.init(mode, key, new IvParameterSpec(key.getEncoded()));
            // Returns a value to the caller
            return cipher3;
        // Start of a method/block
        } catch (GeneralSecurityException e) {
            // Throws an exception
            throw new RuntimeException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
