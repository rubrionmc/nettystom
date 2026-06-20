// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VECTOR3D;

// Déclaration de type (classe/interface/enum/record)
public record MoveMinecartPacket(int entityId, List<LerpStep> lerpSteps) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<MoveMinecartPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.VAR_INT, MoveMinecartPacket::entityId,
            // Instruction de code
            LerpStep.SERIALIZER.list(Short.MAX_VALUE), MoveMinecartPacket::lerpSteps,
            // Instruction de code
            MoveMinecartPacket::new);

    // Déclaration de type (classe/interface/enum/record)
    public record LerpStep(
            // Instruction de code
            Point position, Point velocity,
            // Instruction de code
            float yaw, float pitch, float weight
    // Début d'une méthode/d'un bloc
    ) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<LerpStep> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                VECTOR3D, LerpStep::position,
                // Instruction de code
                VECTOR3D, LerpStep::velocity,
                // Instruction de code
                NetworkBuffer.FLOAT, LerpStep::yaw,
                // Instruction de code
                NetworkBuffer.FLOAT, LerpStep::pitch,
                // Instruction de code
                NetworkBuffer.FLOAT, LerpStep::weight,
                // Instruction de code
                LerpStep::new);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
