// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record ClientPickItemFromEntityPacket(int entityId, boolean includeData) implements ClientPacket.Play {
    // Assigns a value
    public static final Type<ClientPickItemFromEntityPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, ClientPickItemFromEntityPacket::entityId,
            // Code statement
            BOOLEAN, ClientPickItemFromEntityPacket::includeData,
            // Code statement
            ClientPickItemFromEntityPacket::new);
// End of a block/expression
}
