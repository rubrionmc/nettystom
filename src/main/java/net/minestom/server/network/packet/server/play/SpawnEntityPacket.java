// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Import of a required class
import java.util.UUID;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record SpawnEntityPacket(
        // Code statement
        int entityId, UUID uuid, EntityType type,
        // Code statement
        Pos position, float headRot, int data,
        // Code statement
        Vec velocity
// Start of a method/block
) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<SpawnEntityPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, SpawnEntityPacket::entityId,
            // Code statement
            UUID, SpawnEntityPacket::uuid,
            // Code statement
            EntityType.NETWORK_TYPE, SpawnEntityPacket::type,
            // Code statement
            DOUBLE, value -> value.position.x(),
            // Code statement
            DOUBLE, value -> value.position.y(),
            // Code statement
            DOUBLE, value -> value.position.z(),
            // Code statement
            LP_VECTOR3, SpawnEntityPacket::velocity,
            // Code statement
            BYTE, value -> (byte) (value.position.pitch() * 256f / 360f),
            // Code statement
            BYTE, value -> (byte) (value.position.yaw() * 256f / 360f),
            // Code statement
            BYTE, value -> (byte) (value.headRot * 256f / 360f),
            // Code statement
            VAR_INT, SpawnEntityPacket::data,
            // Code statement
            (entityId, uuid, type, x, y, z, velocity, pitch, yaw, headRot, data) ->
                    // Creates a new object
                    new SpawnEntityPacket(entityId, uuid, type,
                            // Creates a new object
                            new Pos(x, y, z, yaw * 360f / 256f, pitch * 360f / 256f),
                            // Code statement
                            headRot * 360f / 256f, data, velocity)
    // End of a block/expression
    );
// End of a block/expression
}
