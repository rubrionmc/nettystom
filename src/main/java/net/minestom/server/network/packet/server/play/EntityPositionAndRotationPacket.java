// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.coordinate.CoordConversion;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record EntityPositionAndRotationPacket(int entityId, short deltaX, short deltaY, short deltaZ,
                                              // Start of a method/block
                                              float yaw, float pitch, boolean onGround) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<EntityPositionAndRotationPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, EntityPositionAndRotationPacket::entityId,
            // Code statement
            SHORT, EntityPositionAndRotationPacket::deltaX,
            // Code statement
            SHORT, EntityPositionAndRotationPacket::deltaY,
            // Code statement
            SHORT, EntityPositionAndRotationPacket::deltaZ,
            // Code statement
            BYTE, value -> (byte) (value.yaw * 256f / 360f),
            // Code statement
            BYTE, value -> (byte) (value.pitch * 256f / 360f),
            // Code statement
            BOOLEAN, EntityPositionAndRotationPacket::onGround,
            // Code statement
            (entityId, deltaX, deltaY, deltaZ, yaw, pitch, onGround) -> new EntityPositionAndRotationPacket(
                    // Code statement
                    entityId, deltaX, deltaY, deltaZ,
                    // Code statement
                    yaw * 360f / 256f, pitch * 360f / 256f, onGround)
    // End of a block/expression
    );

    // Code statement
    public static EntityPositionAndRotationPacket getPacket(int entityId,
                                                            // Code statement
                                                            Pos newPosition, Pos oldPosition,
                                                            // Start of a method/block
                                                            boolean onGround) {
        // Calls a method
        final short deltaX = CoordConversion.deltaShort4096(newPosition.x(), oldPosition.x());
        // Calls a method
        final short deltaY = CoordConversion.deltaShort4096(newPosition.y(), oldPosition.y());
        // Calls a method
        final short deltaZ = CoordConversion.deltaShort4096(newPosition.z(), oldPosition.z());
        // Returns a value to the caller
        return new EntityPositionAndRotationPacket(entityId, deltaX, deltaY, deltaZ, newPosition.yaw(), newPosition.pitch(), onGround);
    // End of a block/expression
    }
// End of a block/expression
}
