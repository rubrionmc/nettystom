// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.RelativeFlags;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import org.intellij.lang.annotations.MagicConstant;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record EntityTeleportPacket(
        // Code statement
        int entityId, Pos position, Point delta,
        // Annotation for the following element
        @MagicConstant(flagsFromClass = RelativeFlags.class) int flags,
        // Start of a method/block
        boolean onGround) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<EntityTeleportPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, EntityTeleportPacket::entityId,
            // Code statement
            VECTOR3D, EntityTeleportPacket::position,
            // Code statement
            VECTOR3D, EntityTeleportPacket::delta,
            // Code statement
            FLOAT, value -> value.position.yaw(),
            // Code statement
            FLOAT, value -> value.position.pitch(),
            // Code statement
            INT, EntityTeleportPacket::flags,
            // Code statement
            BOOLEAN, EntityTeleportPacket::onGround,
            // Code statement
            (entityId, absPosition, deltaMovement, yaw, pitch, flags, onGround) ->
                    // Creates a new object
                    new EntityTeleportPacket(entityId, absPosition.asPos().withView(yaw, pitch),
                            // Code statement
                            deltaMovement, flags, onGround)
    // End of a block/expression
    );
// End of a block/expression
}
