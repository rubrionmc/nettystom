// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.crypto.MessageSignature;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Déclaration de type (classe/interface/enum/record)
public record DeleteChatPacket(MessageSignature signature) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DeleteChatPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            MessageSignature.SERIALIZER, DeleteChatPacket::signature,
            // Instruction de code
            DeleteChatPacket::new
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
