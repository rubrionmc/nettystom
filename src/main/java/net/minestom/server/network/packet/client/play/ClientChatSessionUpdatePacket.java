// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.crypto.ChatSession;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Déclaration de type (classe/interface/enum/record)
public record ClientChatSessionUpdatePacket(ChatSession chatSession) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientChatSessionUpdatePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            ChatSession.SERIALIZER, ClientChatSessionUpdatePacket::chatSession,
            // Instruction de code
            ClientChatSessionUpdatePacket::new
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
