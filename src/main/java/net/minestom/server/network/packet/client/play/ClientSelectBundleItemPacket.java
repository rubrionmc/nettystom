// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record ClientSelectBundleItemPacket(int slot, int selectedIndex) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientSelectBundleItemPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, ClientSelectBundleItemPacket::slot,
            // Code statement
            VAR_INT, ClientSelectBundleItemPacket::selectedIndex,
            // Code statement
            ClientSelectBundleItemPacket::new);
// End of a block/expression
}
