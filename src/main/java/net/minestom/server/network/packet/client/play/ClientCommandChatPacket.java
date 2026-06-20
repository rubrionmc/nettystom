// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;

// Déclaration de type (classe/interface/enum/record)
public record ClientCommandChatPacket(String message) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientCommandChatPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            STRING, ClientCommandChatPacket::message,
            // Instruction de code
            ClientCommandChatPacket::new);

    // Début d'une méthode/d'un bloc
    public ClientCommandChatPacket {
        // Appelle une méthode
        Check.argCondition(message.length() > 256, "Message length cannot be greater than 256");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
