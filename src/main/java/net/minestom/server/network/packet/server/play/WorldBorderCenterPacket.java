// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.DOUBLE;

// Type declaration (class/interface/enum/record)
public record WorldBorderCenterPacket(double x, double z) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<WorldBorderCenterPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            DOUBLE, WorldBorderCenterPacket::x,
            // Code statement
            DOUBLE, WorldBorderCenterPacket::z,
            // Code statement
            WorldBorderCenterPacket::new);
// End of a block/expression
}
