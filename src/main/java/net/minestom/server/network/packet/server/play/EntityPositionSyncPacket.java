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

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record EntityPositionSyncPacket(
        // Instruction de code
        int entityId, Point position, Point delta,
        // Instruction de code
        float yaw, float pitch, boolean onGround
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<EntityPositionSyncPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, EntityPositionSyncPacket::entityId,
            // Instruction de code
            VECTOR3D, EntityPositionSyncPacket::position,
            // Instruction de code
            VECTOR3D, EntityPositionSyncPacket::delta,
            // Instruction de code
            FLOAT, EntityPositionSyncPacket::yaw,
            // Instruction de code
            FLOAT, EntityPositionSyncPacket::pitch,
            // Instruction de code
            BOOLEAN, EntityPositionSyncPacket::onGround,
            // Instruction de code
            EntityPositionSyncPacket::new);
// Fin d'un bloc/d'une expression
}
