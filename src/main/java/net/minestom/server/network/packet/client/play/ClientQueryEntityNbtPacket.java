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
public record ClientQueryEntityNbtPacket(int transactionId, int entityId) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientQueryEntityNbtPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, ClientQueryEntityNbtPacket::transactionId,
            // Code statement
            VAR_INT, ClientQueryEntityNbtPacket::entityId,
            // Code statement
            ClientQueryEntityNbtPacket::new);
// End of a block/expression
}
