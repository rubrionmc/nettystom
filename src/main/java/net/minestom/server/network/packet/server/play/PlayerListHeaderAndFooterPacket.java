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

// Type declaration (class/interface/enum/record)
public record PlayerListHeaderAndFooterPacket(Component header,
                                              // Start of a method/block
                                              Component footer) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Assigns a value
    public static final NetworkBuffer.Type<PlayerListHeaderAndFooterPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            COMPONENT, PlayerListHeaderAndFooterPacket::header,
            // Code statement
            COMPONENT, PlayerListHeaderAndFooterPacket::footer,
            // Code statement
            PlayerListHeaderAndFooterPacket::new);

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Component> components() {
        // Returns a value to the caller
        return List.of(header, footer);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Returns a value to the caller
        return new PlayerListHeaderAndFooterPacket(operator.apply(header), operator.apply(footer));
    // End of a block/expression
    }
// End of a block/expression
}
