// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.POS;

// Type declaration (class/interface/enum/record)
public record VehicleMovePacket(Pos position) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<VehicleMovePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            POS, VehicleMovePacket::position, VehicleMovePacket::new);
// End of a block/expression
}
