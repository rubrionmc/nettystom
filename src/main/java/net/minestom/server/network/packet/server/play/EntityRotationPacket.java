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
public record EntityRotationPacket(int entityId, float yaw, float pitch,
                                   // Start of a method/block
                                   boolean onGround) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<EntityRotationPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, EntityRotationPacket::entityId,
            // Code statement
            BYTE, value -> (byte) (value.yaw * 256f / 360f),
            // Code statement
            BYTE, value -> (byte) (value.pitch * 256f / 360f),
            // Code statement
            BOOLEAN, EntityRotationPacket::onGround,
            // Code statement
            (entityId, yaw, pitch, onGround) -> new EntityRotationPacket(entityId,
                    // Code statement
                    yaw * 360f / 256f, pitch * 360f / 256f, onGround)
    // End of a block/expression
    );
// End of a block/expression
}
