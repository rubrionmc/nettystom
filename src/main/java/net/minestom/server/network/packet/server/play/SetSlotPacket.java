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

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.SHORT;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record SetSlotPacket(int windowId, int stateId, short slot,
                            // Start of a method/block
                            ItemStack itemStack) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Assigns a value
    public static final NetworkBuffer.Type<SetSlotPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, SetSlotPacket::windowId,
            // Code statement
            VAR_INT, SetSlotPacket::stateId,
            // Code statement
            SHORT, SetSlotPacket::slot,
            // Code statement
            ItemStack.NETWORK_TYPE, SetSlotPacket::itemStack,
            // Code statement
            SetSlotPacket::new);

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
        return new SetSlotPacket(this.windowId, this.stateId, this.slot, ItemStack.copyWithOperator(this.itemStack, operator));
    // End of a block/expression
    }

// End of a block/expression
}
