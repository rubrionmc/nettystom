// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BYTE;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record EntityHeadLookPacket(int entityId, float yaw) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<EntityHeadLookPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, EntityHeadLookPacket::entityId,
            // Code statement
            BYTE, value -> (byte) (value.yaw * 256f / 360f),
            // Code statement
            (entityId, yaw) -> new EntityHeadLookPacket(entityId, yaw * 360f / 256f)
    // End of a block/expression
    );
// End of a block/expression
}
