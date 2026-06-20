// Package declaration for this file
package net.minestom.server.network.packet.client.play;

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
public record ClientUseItemPacket(PlayerHand hand, int sequence, float yaw,
                                  // Start of a method/block
                                  float pitch) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientUseItemPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            Enum(PlayerHand.class), ClientUseItemPacket::hand,
            // Code statement
            VAR_INT, ClientUseItemPacket::sequence,
            // Code statement
            FLOAT, ClientUseItemPacket::yaw,
            // Code statement
            FLOAT, ClientUseItemPacket::pitch,
            // Code statement
            ClientUseItemPacket::new);
// End of a block/expression
}
