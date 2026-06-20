// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.UnaryOperator;

// Type declaration (class/interface/enum/record)
public record TestInstanceBlockStatus(
        // Code statement
        Component status,
        // Annotation for the following element
        @Nullable Point size
// Start of a method/block
) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Assigns a value
    public static final NetworkBuffer.Type<TestInstanceBlockStatus> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.COMPONENT, TestInstanceBlockStatus::status,
            // Code statement
            NetworkBuffer.VECTOR3I.optional(), TestInstanceBlockStatus::size,
            // Code statement
            TestInstanceBlockStatus::new);

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Component> components() {
        // Returns a value to the caller
        return List.of(status);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Returns a value to the caller
        return new TestInstanceBlockStatus(operator.apply(status), size);
    // End of a block/expression
    }
// End of a block/expression
}
