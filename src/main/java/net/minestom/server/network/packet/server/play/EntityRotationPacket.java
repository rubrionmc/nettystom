// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record EntityRotationPacket(int entityId, float yaw, float pitch,
                                   // Début d'une méthode/d'un bloc
                                   boolean onGround) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<EntityRotationPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, EntityRotationPacket::entityId,
            // Instruction de code
            BYTE, value -> (byte) (value.yaw * 256f / 360f),
            // Instruction de code
            BYTE, value -> (byte) (value.pitch * 256f / 360f),
            // Instruction de code
            BOOLEAN, EntityRotationPacket::onGround,
            // Instruction de code
            (entityId, yaw, pitch, onGround) -> new EntityRotationPacket(entityId,
                    // Instruction de code
                    yaw * 360f / 256f, pitch * 360f / 256f, onGround)
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
