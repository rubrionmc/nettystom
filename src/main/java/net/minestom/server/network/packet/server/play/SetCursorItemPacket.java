// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.function.UnaryOperator;

// Type declaration (class/interface/enum/record)
public record SetCursorItemPacket(ItemStack itemStack) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Assigns a value
    public static final NetworkBuffer.Type<SetCursorItemPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            ItemStack.NETWORK_TYPE, SetCursorItemPacket::itemStack,
            // Code statement
            SetCursorItemPacket::new);

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Component> components() {
        // Returns a value to the caller
        return ItemStack.textComponents(itemStack);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Returns a value to the caller
        return new SetCursorItemPacket(ItemStack.copyWithOperator(itemStack, operator));
    // End of a block/expression
    }
// End of a block/expression
}
