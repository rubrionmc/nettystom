// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.coordinate.Point;
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
public record PlayerPositionAndLookPacket(
        // Code statement
        int teleportId, Point position, Point delta,
        // Code statement
        float yaw, float pitch,
        // Annotation for the following element
        @MagicConstant(flagsFromClass = RelativeFlags.class) int flags
// Start of a method/block
) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<PlayerPositionAndLookPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, PlayerPositionAndLookPacket::teleportId,
            // Code statement
            VECTOR3D, PlayerPositionAndLookPacket::position,
            // Code statement
            VECTOR3D, PlayerPositionAndLookPacket::delta,
            // Code statement
            FLOAT, PlayerPositionAndLookPacket::yaw,
            // Code statement
            FLOAT, PlayerPositionAndLookPacket::pitch,
            // Code statement
            INT, PlayerPositionAndLookPacket::flags,
            // Code statement
            PlayerPositionAndLookPacket::new);
// End of a block/expression
}