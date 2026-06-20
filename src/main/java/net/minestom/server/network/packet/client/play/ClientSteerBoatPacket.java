// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;

// Type declaration (class/interface/enum/record)
public record ClientSteerBoatPacket(boolean leftPaddleTurning, boolean rightPaddleTurning) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientSteerBoatPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            BOOLEAN, ClientSteerBoatPacket::leftPaddleTurning,
            // Code statement
            BOOLEAN, ClientSteerBoatPacket::rightPaddleTurning,
            // Code statement
            ClientSteerBoatPacket::new);
// End of a block/expression
}
