// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.SHORT;

// Type declaration (class/interface/enum/record)
public record ClientHeldItemChangePacket(short slot) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientHeldItemChangePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            SHORT, ClientHeldItemChangePacket::slot,
            // Code statement
            ClientHeldItemChangePacket::new);
// End of a block/expression
}
