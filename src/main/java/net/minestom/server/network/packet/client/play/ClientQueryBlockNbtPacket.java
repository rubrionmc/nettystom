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
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record ClientQueryBlockNbtPacket(int transactionId, Point blockPosition) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientQueryBlockNbtPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, ClientQueryBlockNbtPacket::transactionId,
            // Code statement
            BLOCK_POSITION, ClientQueryBlockNbtPacket::blockPosition,
            // Code statement
            ClientQueryBlockNbtPacket::new);
// End of a block/expression
}
