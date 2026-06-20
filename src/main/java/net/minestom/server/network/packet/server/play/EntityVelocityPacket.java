// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Type declaration (class/interface/enum/record)
public record EntityVelocityPacket(int entityId, Vec velocity) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<EntityVelocityPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.VAR_INT, EntityVelocityPacket::entityId,
            // Code statement
            NetworkBuffer.LP_VECTOR3, EntityVelocityPacket::velocity,
            // Code statement
            EntityVelocityPacket::new);
// End of a block/expression
}
