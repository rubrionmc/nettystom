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
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_LONG;

// Type declaration (class/interface/enum/record)
public record WorldBorderLerpSizePacket(double oldDiameter, double newDiameter,
                                        // Start of a method/block
                                        long speed) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<WorldBorderLerpSizePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            DOUBLE, WorldBorderLerpSizePacket::oldDiameter,
            // Code statement
            DOUBLE, WorldBorderLerpSizePacket::newDiameter,
            // Code statement
            VAR_LONG, WorldBorderLerpSizePacket::speed,
            // Code statement
            WorldBorderLerpSizePacket::new);
// End of a block/expression
}
