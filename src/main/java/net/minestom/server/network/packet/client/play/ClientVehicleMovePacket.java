// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.POS;

// Type declaration (class/interface/enum/record)
public record ClientVehicleMovePacket(Pos position, boolean onGround) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientVehicleMovePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            POS, ClientVehicleMovePacket::position,
            // Code statement
            BOOLEAN, ClientVehicleMovePacket::onGround,
            // Code statement
            ClientVehicleMovePacket::new);
// End of a block/expression
}
