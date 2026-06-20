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

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.SHORT;

// Type declaration (class/interface/enum/record)
public record ClientCreativeInventoryActionPacket(short slot, ItemStack item) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientCreativeInventoryActionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            SHORT, ClientCreativeInventoryActionPacket::slot,
            // Code statement
            ItemStack.UNTRUSTED_NETWORK_TYPE, ClientCreativeInventoryActionPacket::item,
            // Code statement
            ClientCreativeInventoryActionPacket::new);
// End of a block/expression
}
