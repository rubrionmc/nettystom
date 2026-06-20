// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.login;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.Arrays;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record EncryptionRequestPacket(
        // Instruction de code
        String serverId,
        // Instruction de code
        byte[] publicKey,
        // Instruction de code
        byte[] verifyToken,
        // Instruction de code
        boolean shouldAuthenticate
// Début d'une méthode/d'un bloc
) implements ServerPacket.Login {
    // Affecte une valeur
    public static final NetworkBuffer.Type<EncryptionRequestPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            STRING, EncryptionRequestPacket::serverId,
            // Instruction de code
            BYTE_ARRAY, EncryptionRequestPacket::publicKey,
            // Instruction de code
            BYTE_ARRAY, EncryptionRequestPacket::verifyToken,
            // Instruction de code
            BOOLEAN, EncryptionRequestPacket::shouldAuthenticate,
            // Instruction de code
            EncryptionRequestPacket::new);

    // Début d'une méthode/d'un bloc
    public EncryptionRequestPacket {
        // Appelle une méthode
        publicKey = publicKey.clone();
        // Appelle une méthode
        verifyToken = verifyToken.clone();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object object) {
        // Embranchement : vérifie une condition
        if (!(object instanceof EncryptionRequestPacket(String id, byte[] key, byte[] token, boolean authenticate))) return false;
        // Renvoie une valeur à l'appelant
        return shouldAuthenticate() == authenticate && serverId().equals(id) && Arrays.equals(publicKey(), key) && Arrays.equals(verifyToken(), token);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Appelle une méthode
        int result = serverId().hashCode();
        // Appelle une méthode
        result = 31 * result + Arrays.hashCode(publicKey());
        // Appelle une méthode
        result = 31 * result + Arrays.hashCode(verifyToken());
        // Appelle une méthode
        result = 31 * result + Boolean.hashCode(shouldAuthenticate());
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
