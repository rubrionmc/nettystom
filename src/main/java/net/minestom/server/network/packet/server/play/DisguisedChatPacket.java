// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.COMPONENT;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record DisguisedChatPacket(
        // Code statement
        Component message,
        // Code statement
        int type,
        // Code statement
        Component senderName,
        // Annotation for the following element
        @Nullable Component targetName
// Start of a method/block
) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<DisguisedChatPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            COMPONENT, DisguisedChatPacket::message,
            // Code statement
            VAR_INT, DisguisedChatPacket::type,
            // Code statement
            COMPONENT, DisguisedChatPacket::senderName,
            // Code statement
            COMPONENT.optional(), DisguisedChatPacket::targetName,
            // Code statement
            DisguisedChatPacket::new);
// End of a block/expression
}
