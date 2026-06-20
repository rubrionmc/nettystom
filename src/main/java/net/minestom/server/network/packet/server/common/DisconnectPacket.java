// Package declaration for this file
package net.minestom.server.network.packet.server.common;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.UnaryOperator;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.COMPONENT;

// Type declaration (class/interface/enum/record)
public record DisconnectPacket(Component message) implements ServerPacket.Configuration, ServerPacket.Play,
        // Start of a method/block
        ServerPacket.ComponentHolding {
    // Assigns a value
    public static final NetworkBuffer.Type<DisconnectPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            COMPONENT, DisconnectPacket::message, DisconnectPacket::new);

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Component> components() {
        // Returns a value to the caller
        return List.of(message);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Returns a value to the caller
        return new DisconnectPacket(operator.apply(message));
    // End of a block/expression
    }
// End of a block/expression
}
