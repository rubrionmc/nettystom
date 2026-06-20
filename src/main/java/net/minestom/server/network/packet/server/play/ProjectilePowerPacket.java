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
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record ProjectilePowerPacket(
        // Code statement
        int entityId, double accelerationPower
// Start of a method/block
) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ProjectilePowerPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, ProjectilePowerPacket::entityId,
            // Code statement
            DOUBLE, ProjectilePowerPacket::accelerationPower,
            // Code statement
            ProjectilePowerPacket::new);
// End of a block/expression
}
