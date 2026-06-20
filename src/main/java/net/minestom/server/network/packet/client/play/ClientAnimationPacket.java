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

// Type declaration (class/interface/enum/record)
public record ClientAnimationPacket(PlayerHand hand) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientAnimationPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.Enum(PlayerHand.class), ClientAnimationPacket::hand,
            // Code statement
            ClientAnimationPacket::new);
// End of a block/expression
}
