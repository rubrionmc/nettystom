// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.COMPONENT;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record DisguisedChatPacket(
        // Instruction de code
        Component message,
        // Instruction de code
        int type,
        // Instruction de code
        Component senderName,
        // Annotation pour l'élément suivant
        @Nullable Component targetName
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DisguisedChatPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            COMPONENT, DisguisedChatPacket::message,
            // Instruction de code
            VAR_INT, DisguisedChatPacket::type,
            // Instruction de code
            COMPONENT, DisguisedChatPacket::senderName,
            // Instruction de code
            COMPONENT.optional(), DisguisedChatPacket::targetName,
            // Instruction de code
            DisguisedChatPacket::new);
// Fin d'un bloc/d'une expression
}
