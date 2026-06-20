// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Import of a required class
import java.util.Map;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record ClientClickWindowPacket(int windowId, int stateId,
                                      // Code statement
                                      short slot, byte button, ClickType clickType,
                                      // Code statement
                                      Map<Short, ItemStack.Hash> changedSlots,
                                      // Start of a method/block
                                      ItemStack.Hash clickedItem) implements ClientPacket.Play {
    // Assigns a value
    public static final int MAX_CHANGED_SLOTS = 128;

    // Assigns a value
    public static final NetworkBuffer.Type<ClientClickWindowPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, ClientClickWindowPacket::windowId,
            // Code statement
            VAR_INT, ClientClickWindowPacket::stateId,
            // Code statement
            SHORT, ClientClickWindowPacket::slot,
            // Code statement
            BYTE, ClientClickWindowPacket::button,
            // Code statement
            Enum(ClickType.class), ClientClickWindowPacket::clickType,
            // Code statement
            SHORT.mapValue(ItemStack.Hash.NETWORK_TYPE, MAX_CHANGED_SLOTS), ClientClickWindowPacket::changedSlots,
            // Code statement
            ItemStack.Hash.NETWORK_TYPE, ClientClickWindowPacket::clickedItem,
            // Code statement
            ClientClickWindowPacket::new);

    // Start of a method/block
    public ClientClickWindowPacket {
        // Calls a method
        changedSlots = Map.copyOf(changedSlots);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum ClickType {
        // Code statement
        PICKUP,
        // Code statement
        QUICK_MOVE,
        // Code statement
        SWAP,
        // Code statement
        CLONE,
        // Code statement
        THROW,
        // Code statement
        QUICK_CRAFT,
        // Code statement
        PICKUP_ALL
    // End of a block/expression
    }
// End of a block/expression
}
