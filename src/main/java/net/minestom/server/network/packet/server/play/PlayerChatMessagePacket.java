// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.crypto.FilterMask;
// Import d'une classe nécessaire
import net.minestom.server.crypto.SignedMessageBody;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.UUID;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

/**
 * Represents an outgoing chat message packet.
 */
// Déclaration de type (classe/interface/enum/record)
public record PlayerChatMessagePacket(int globalIndex, UUID sender, int index, byte @Nullable [] signature,
                                      // Instruction de code
                                      SignedMessageBody.Packed messageBody,
                                      // Annotation pour l'élément suivant
                                      @Nullable Component unsignedContent, FilterMask filterMask,
                                      // Instruction de code
                                      int msgTypeId, Component msgTypeName,
                                      // Annotation pour l'élément suivant
                                      @Nullable Component msgTypeTarget) implements ServerPacket.Play, ServerPacket.ComponentHolding {

    // Affecte une valeur
    public static final NetworkBuffer.Type<PlayerChatMessagePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, PlayerChatMessagePacket::globalIndex,
            // Instruction de code
            UUID, PlayerChatMessagePacket::sender,
            // Instruction de code
            VAR_INT, PlayerChatMessagePacket::index,
            // Instruction de code
            RAW_BYTES.optional(), PlayerChatMessagePacket::signature,
            // Instruction de code
            SignedMessageBody.Packed.SERIALIZER, PlayerChatMessagePacket::messageBody,
            // Instruction de code
            COMPONENT.optional(), PlayerChatMessagePacket::unsignedContent,
            // Instruction de code
            FilterMask.SERIALIZER, PlayerChatMessagePacket::filterMask,
            // Instruction de code
            VAR_INT, PlayerChatMessagePacket::msgTypeId,
            // Instruction de code
            COMPONENT, PlayerChatMessagePacket::msgTypeName,
            // Instruction de code
            COMPONENT, PlayerChatMessagePacket::msgTypeTarget,
            // Instruction de code
            PlayerChatMessagePacket::new
    // Fin d'un bloc/d'une expression
    );

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Component> components() {
        // Affecte une valeur
        final ArrayList<Component> list = new ArrayList<>();
        // Appelle une méthode
        list.add(msgTypeName);
        // Embranchement : vérifie une condition
        if (unsignedContent != null) list.add(unsignedContent);
        // Embranchement : vérifie une condition
        if (msgTypeTarget != null) list.add(msgTypeTarget);
        // Renvoie une valeur à l'appelant
        return List.copyOf(list);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Renvoie une valeur à l'appelant
        return new PlayerChatMessagePacket(globalIndex, sender, index, signature,
                // Instruction de code
                messageBody, operator.apply(unsignedContent), filterMask,
                // Appelle une méthode
                msgTypeId, operator.apply(msgTypeName), operator.apply(msgTypeTarget));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
