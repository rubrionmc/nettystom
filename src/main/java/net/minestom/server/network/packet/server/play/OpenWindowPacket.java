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
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.UnaryOperator;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.COMPONENT;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record OpenWindowPacket(int windowId, int windowType,
                               // Start of a method/block
                               Component title) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Assigns a value
    public static final NetworkBuffer.Type<OpenWindowPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, OpenWindowPacket::windowId,
            // Code statement
            VAR_INT, OpenWindowPacket::windowType,
            // Code statement
            COMPONENT, OpenWindowPacket::title,
            // Code statement
            OpenWindowPacket::new);

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Component> components() {
        // Returns a value to the caller
        return List.of(this.title);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Returns a value to the caller
        return new OpenWindowPacket(this.windowId, this.windowType, operator.apply(this.title));
    // End of a block/expression
    }
// End of a block/expression
}
