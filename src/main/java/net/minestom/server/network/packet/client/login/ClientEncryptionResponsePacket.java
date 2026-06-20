// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.login;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BYTE_ARRAY;

// Déclaration de type (classe/interface/enum/record)
public record ClientEncryptionResponsePacket(byte[] sharedSecret,
                                             // Début d'une méthode/d'un bloc
                                             byte[] encryptedVerifyToken) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientEncryptionResponsePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BYTE_ARRAY, ClientEncryptionResponsePacket::sharedSecret,
            // Instruction de code
            BYTE_ARRAY, ClientEncryptionResponsePacket::encryptedVerifyToken,
            // Instruction de code
            ClientEncryptionResponsePacket::new);
// Fin d'un bloc/d'une expression
}
