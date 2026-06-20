// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.login;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import d'une classe nécessaire
import java.util.Arrays;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BYTE_ARRAY;

// Déclaration de type (classe/interface/enum/record)
public record ClientEncryptionResponsePacket(byte[] sharedSecret,
                                             // Début d'une méthode/d'un bloc
                                             byte[] encryptedVerifyToken) implements ClientPacket.Login {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientEncryptionResponsePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BYTE_ARRAY, ClientEncryptionResponsePacket::sharedSecret,
            // Instruction de code
            BYTE_ARRAY, ClientEncryptionResponsePacket::encryptedVerifyToken,
            // Instruction de code
            ClientEncryptionResponsePacket::new);

    // Début d'une méthode/d'un bloc
    public ClientEncryptionResponsePacket {
        // Appelle une méthode
        sharedSecret = sharedSecret.clone();
        // Appelle une méthode
        encryptedVerifyToken = encryptedVerifyToken.clone();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object object) {
        // Embranchement : vérifie une condition
        if (!(object instanceof ClientEncryptionResponsePacket(byte[] secret, byte[] verifyToken))) return false;
        // Renvoie une valeur à l'appelant
        return Arrays.equals(sharedSecret(), secret) && Arrays.equals(encryptedVerifyToken(), verifyToken);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Appelle une méthode
        int result = Arrays.hashCode(sharedSecret());
        // Appelle une méthode
        result = 31 * result + Arrays.hashCode(encryptedVerifyToken());
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
