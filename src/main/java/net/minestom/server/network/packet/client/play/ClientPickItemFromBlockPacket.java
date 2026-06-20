// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BLOCK_POSITION;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;

// Type declaration (class/interface/enum/record)
public record ClientPickItemFromBlockPacket(Point pos, boolean includeData) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientPickItemFromBlockPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            BLOCK_POSITION, ClientPickItemFromBlockPacket::pos,
            // Code statement
            BOOLEAN, ClientPickItemFromBlockPacket::includeData,
            // Code statement
            ClientPickItemFromBlockPacket::new);
// End of a block/expression
}
