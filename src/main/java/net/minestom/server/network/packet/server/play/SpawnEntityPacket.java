// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.UUID;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record SpawnEntityPacket(
        // Instruction de code
        int entityId, UUID uuid, EntityType type,
        // Instruction de code
        Pos position, float headRot, int data,
        // Instruction de code
        Vec velocity
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<SpawnEntityPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, SpawnEntityPacket::entityId,
            // Instruction de code
            UUID, SpawnEntityPacket::uuid,
            // Instruction de code
            EntityType.NETWORK_TYPE, SpawnEntityPacket::type,
            // Instruction de code
            DOUBLE, value -> value.position.x(),
            // Instruction de code
            DOUBLE, value -> value.position.y(),
            // Instruction de code
            DOUBLE, value -> value.position.z(),
            // Instruction de code
            LP_VECTOR3, SpawnEntityPacket::velocity,
            // Instruction de code
            BYTE, value -> (byte) (value.position.pitch() * 256f / 360f),
            // Instruction de code
            BYTE, value -> (byte) (value.position.yaw() * 256f / 360f),
            // Instruction de code
            BYTE, value -> (byte) (value.headRot * 256f / 360f),
            // Instruction de code
            VAR_INT, SpawnEntityPacket::data,
            // Instruction de code
            (entityId, uuid, type, x, y, z, velocity, pitch, yaw, headRot, data) ->
                    // Crée un nouvel objet
                    new SpawnEntityPacket(entityId, uuid, type,
                            // Crée un nouvel objet
                            new Pos(x, y, z, yaw * 360f / 256f, pitch * 360f / 256f),
                            // Instruction de code
                            headRot * 360f / 256f, data, velocity)
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
