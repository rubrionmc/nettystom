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

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VECTOR3D;

// Type declaration (class/interface/enum/record)
public record MoveMinecartPacket(int entityId, List<LerpStep> lerpSteps) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<MoveMinecartPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.VAR_INT, MoveMinecartPacket::entityId,
            // Code statement
            LerpStep.SERIALIZER.list(Short.MAX_VALUE), MoveMinecartPacket::lerpSteps,
            // Code statement
            MoveMinecartPacket::new);

    // Start of a method/block
    public MoveMinecartPacket {
        // Calls a method
        lerpSteps = List.copyOf(lerpSteps);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record LerpStep(
            // Code statement
            Point position, Point velocity,
            // Code statement
            float yaw, float pitch, float weight
    // Start of a method/block
    ) {
        // Assigns a value
        public static final NetworkBuffer.Type<LerpStep> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                VECTOR3D, LerpStep::position,
                // Code statement
                VECTOR3D, LerpStep::velocity,
                // Code statement
                NetworkBuffer.FLOAT, LerpStep::yaw,
                // Code statement
                NetworkBuffer.FLOAT, LerpStep::pitch,
                // Code statement
                NetworkBuffer.FLOAT, LerpStep::weight,
                // Code statement
                LerpStep::new);
    // End of a block/expression
    }
// End of a block/expression
}
