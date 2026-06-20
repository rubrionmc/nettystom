// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record UpdateViewDistancePacket(int viewDistance) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<UpdateViewDistancePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, UpdateViewDistancePacket::viewDistance,
            // Code statement
            UpdateViewDistancePacket::new);
// End of a block/expression
}
