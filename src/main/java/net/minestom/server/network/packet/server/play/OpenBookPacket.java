// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Type declaration (class/interface/enum/record)
public record OpenBookPacket(PlayerHand hand) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<OpenBookPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.Enum(PlayerHand.class), OpenBookPacket::hand,
            // Code statement
            OpenBookPacket::new);
// End of a block/expression
}
