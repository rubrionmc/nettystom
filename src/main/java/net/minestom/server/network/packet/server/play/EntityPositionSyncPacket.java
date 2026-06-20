// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record EntityPositionSyncPacket(
        // Code statement
        int entityId, Point position, Point delta,
        // Code statement
        float yaw, float pitch, boolean onGround
// Start of a method/block
) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<EntityPositionSyncPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, EntityPositionSyncPacket::entityId,
            // Code statement
            VECTOR3D, EntityPositionSyncPacket::position,
            // Code statement
            VECTOR3D, EntityPositionSyncPacket::delta,
            // Code statement
            FLOAT, EntityPositionSyncPacket::yaw,
            // Code statement
            FLOAT, EntityPositionSyncPacket::pitch,
            // Code statement
            BOOLEAN, EntityPositionSyncPacket::onGround,
            // Code statement
            EntityPositionSyncPacket::new);
// End of a block/expression
}
