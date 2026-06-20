// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record ClientInteractEntityPacket(int targetId, PlayerHand hand, Vec location, boolean usingSecondaryAction) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientInteractEntityPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, ClientInteractEntityPacket::targetId,
            // Code statement
            PlayerHand.NETWORK_TYPE, ClientInteractEntityPacket::hand,
            // Code statement
            LP_VECTOR3, ClientInteractEntityPacket::location,
            // Code statement
            BOOLEAN, ClientInteractEntityPacket::usingSecondaryAction,
            // Code statement
            ClientInteractEntityPacket::new
    // End of a block/expression
    );
// End of a block/expression
}
