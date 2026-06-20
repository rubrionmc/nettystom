// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.login;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record EncryptionRequestPacket(
        // Instruction de code
        String serverId,
        // Instruction de code
        byte [] publicKey,
        // Instruction de code
        byte [] verifyToken,
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
// Fin d'un bloc/d'une expression
}
