// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.crypto.FilterMask;
// Import of a required class
import net.minestom.server.crypto.MessageSignature;
// Import of a required class
import net.minestom.server.crypto.SignedMessageBody;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.UUID;
// Import of a required class
import java.util.function.UnaryOperator;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

/**
 * Represents an outgoing chat message packet.
 */
// Type declaration (class/interface/enum/record)
public record PlayerChatMessagePacket(int globalIndex, UUID sender, int index, @Nullable MessageSignature signature,
                                      // Code statement
                                      SignedMessageBody.Packed messageBody,
                                      // Annotation for the following element
                                      @Nullable Component unsignedContent, FilterMask filterMask,
                                      // Code statement
                                      int msgTypeId, Component msgTypeName,
                                      // Annotation for the following element
                                      @Nullable Component msgTypeTarget) implements ServerPacket.Play, ServerPacket.ComponentHolding {

    // Assigns a value
    public static final NetworkBuffer.Type<PlayerChatMessagePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, PlayerChatMessagePacket::globalIndex,
            // Code statement
            UUID, PlayerChatMessagePacket::sender,
            // Code statement
            VAR_INT, PlayerChatMessagePacket::index,
            // Code statement
            MessageSignature.SERIALIZER.optional(), PlayerChatMessagePacket::signature,
            // Code statement
            SignedMessageBody.Packed.SERIALIZER, PlayerChatMessagePacket::messageBody,
            // Code statement
            COMPONENT.optional(), PlayerChatMessagePacket::unsignedContent,
            // Code statement
            FilterMask.SERIALIZER, PlayerChatMessagePacket::filterMask,
            // Code statement
            VAR_INT, PlayerChatMessagePacket::msgTypeId,
            // Code statement
            COMPONENT, PlayerChatMessagePacket::msgTypeName,
            // Code statement
            COMPONENT.optional(), PlayerChatMessagePacket::msgTypeTarget,
            // Code statement
            PlayerChatMessagePacket::new
    // End of a block/expression
    );

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Component> components() {
        // Calls a method
        final ArrayList<Component> list = new ArrayList<>();
        // Calls a method
        list.add(msgTypeName);
        // Branch: checks a condition
        if (unsignedContent != null) list.add(unsignedContent);
        // Branch: checks a condition
        if (msgTypeTarget != null) list.add(msgTypeTarget);
        // Returns a value to the caller
        return List.copyOf(list);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Returns a value to the caller
        return new PlayerChatMessagePacket(globalIndex, sender, index, signature,
                // Code statement
                messageBody, operator.apply(unsignedContent), filterMask,
                // Calls a method
                msgTypeId, operator.apply(msgTypeName), operator.apply(msgTypeTarget));
    // End of a block/expression
    }
// End of a block/expression
}
