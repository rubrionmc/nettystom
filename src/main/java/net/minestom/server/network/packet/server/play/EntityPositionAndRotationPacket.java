// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.CoordConversion;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record EntityPositionAndRotationPacket(int entityId, short deltaX, short deltaY, short deltaZ,
                                              // Début d'une méthode/d'un bloc
                                              float yaw, float pitch, boolean onGround) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<EntityPositionAndRotationPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, EntityPositionAndRotationPacket::entityId,
            // Instruction de code
            SHORT, EntityPositionAndRotationPacket::deltaX,
            // Instruction de code
            SHORT, EntityPositionAndRotationPacket::deltaY,
            // Instruction de code
            SHORT, EntityPositionAndRotationPacket::deltaZ,
            // Instruction de code
            BYTE, value -> (byte) (value.yaw * 256f / 360f),
            // Instruction de code
            BYTE, value -> (byte) (value.pitch * 256f / 360f),
            // Instruction de code
            BOOLEAN, EntityPositionAndRotationPacket::onGround,
            // Instruction de code
            (entityId, deltaX, deltaY, deltaZ, yaw, pitch, onGround) -> new EntityPositionAndRotationPacket(
                    // Instruction de code
                    entityId, deltaX, deltaY, deltaZ,
                    // Instruction de code
                    yaw * 360f / 256f, pitch * 360f / 256f, onGround)
    // Fin d'un bloc/d'une expression
    );

    // Instruction de code
    public static EntityPositionAndRotationPacket getPacket(int entityId,
                                                            // Instruction de code
                                                            Pos newPosition, Pos oldPosition,
                                                            // Début d'une méthode/d'un bloc
                                                            boolean onGround) {
        // Appelle une méthode
        final short deltaX = CoordConversion.deltaShort4096(newPosition.x(), oldPosition.x());
        // Appelle une méthode
        final short deltaY = CoordConversion.deltaShort4096(newPosition.y(), oldPosition.y());
        // Appelle une méthode
        final short deltaZ = CoordConversion.deltaShort4096(newPosition.z(), oldPosition.z());
        // Renvoie une valeur à l'appelant
        return new EntityPositionAndRotationPacket(entityId, deltaX, deltaY, deltaZ, newPosition.yaw(), newPosition.pitch(), onGround);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
