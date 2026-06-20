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
public record ClientCloseWindowPacket(int windowId) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientCloseWindowPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, ClientCloseWindowPacket::windowId,
            // Code statement
            ClientCloseWindowPacket::new);
// End of a block/expression
}
