// Package declaration for this file
package net.minestom.server.network.packet.server.login;

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
import static net.minestom.server.network.NetworkBuffer.JSON_COMPONENT;

// Type declaration (class/interface/enum/record)
public record LoginDisconnectPacket(Component kickMessage) implements ServerPacket.Login,
        // Start of a method/block
        ServerPacket.ComponentHolding {
    // Assigns a value
    public static final NetworkBuffer.Type<LoginDisconnectPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            JSON_COMPONENT, LoginDisconnectPacket::kickMessage,
            // Code statement
            LoginDisconnectPacket::new);

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Component> components() {
        // Returns a value to the caller
        return List.of(this.kickMessage);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public LoginDisconnectPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Returns a value to the caller
        return new LoginDisconnectPacket(operator.apply(this.kickMessage));
    // End of a block/expression
    }
// End of a block/expression
}
