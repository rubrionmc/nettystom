// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// This is the packet sent when you toggle a slot in a crafter UI
// Type declaration (class/interface/enum/record)
public record ClientWindowSlotStatePacket(int slot, int windowId, boolean newState) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientWindowSlotStatePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, ClientWindowSlotStatePacket::slot,
            // Code statement
            VAR_INT, ClientWindowSlotStatePacket::windowId,
            // Code statement
            BOOLEAN, ClientWindowSlotStatePacket::newState,
            // Code statement
            ClientWindowSlotStatePacket::new);
// End of a block/expression
}
