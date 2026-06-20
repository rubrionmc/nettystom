// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record InitializeWorldBorderPacket(double x, double z,
                                          // Code statement
                                          double oldDiameter, double newDiameter, long speed,
                                          // Code statement
                                          int portalTeleportBoundary, int warningTime,
                                          // Start of a method/block
                                          int warningBlocks) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<InitializeWorldBorderPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            DOUBLE, InitializeWorldBorderPacket::x,
            // Code statement
            DOUBLE, InitializeWorldBorderPacket::z,
            // Code statement
            DOUBLE, InitializeWorldBorderPacket::oldDiameter,
            // Code statement
            DOUBLE, InitializeWorldBorderPacket::newDiameter,
            // Code statement
            VAR_LONG, InitializeWorldBorderPacket::speed,
            // Code statement
            VAR_INT, InitializeWorldBorderPacket::portalTeleportBoundary,
            // Code statement
            VAR_INT, InitializeWorldBorderPacket::warningTime,
            // Code statement
            VAR_INT, InitializeWorldBorderPacket::warningBlocks,
            // Code statement
            InitializeWorldBorderPacket::new);
// End of a block/expression
}
