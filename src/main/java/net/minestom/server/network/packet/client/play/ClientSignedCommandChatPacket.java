// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.crypto.ArgumentSignatures;
// Import d'une classe nécessaire
import net.minestom.server.crypto.LastSeenMessages;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ClientSignedCommandChatPacket(String message, long timestamp,
                                            // Instruction de code
                                            long salt, ArgumentSignatures signatures,
                                            // Instruction de code
                                            LastSeenMessages.Update lastSeenMessages,
                                            // Début d'une méthode/d'un bloc
                                            byte checksum) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientSignedCommandChatPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            STRING, ClientSignedCommandChatPacket::message,
            // Instruction de code
            LONG, ClientSignedCommandChatPacket::timestamp,
            // Instruction de code
            LONG, ClientSignedCommandChatPacket::salt,
            // Instruction de code
            ArgumentSignatures.SERIALIZER, ClientSignedCommandChatPacket::signatures,
            // Instruction de code
            LastSeenMessages.Update.SERIALIZER, ClientSignedCommandChatPacket::lastSeenMessages,
            // Instruction de code
            BYTE, ClientSignedCommandChatPacket::checksum,
            // Instruction de code
            ClientSignedCommandChatPacket::new
    // Fin d'un bloc/d'une expression
    );

    // Début d'une méthode/d'un bloc
    public ClientSignedCommandChatPacket {
        // Appelle une méthode
        Check.argCondition(message.length() > 256, "Message length cannot be greater than 256");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
