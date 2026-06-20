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
    public static final NetworkBuffer.Type<SpawnEntityPacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, SpawnEntityPacket value) {
            // Appelle une méthode
            buffer.write(VAR_INT, value.entityId);
            // Appelle une méthode
            buffer.write(UUID, value.uuid);
            // Appelle une méthode
            buffer.write(EntityType.NETWORK_TYPE, value.type);

            // Appelle une méthode
            buffer.write(DOUBLE, value.position.x());
            // Appelle une méthode
            buffer.write(DOUBLE, value.position.y());
            // Appelle une méthode
            buffer.write(DOUBLE, value.position.z());

            // Appelle une méthode
            buffer.write(LP_VECTOR3, value.velocity);

            // Appelle une méthode
            buffer.write(BYTE, (byte) (value.position.pitch() * 256 / 360));
            // Appelle une méthode
            buffer.write(BYTE, (byte) (value.position.yaw() * 256 / 360));
            // Appelle une méthode
            buffer.write(BYTE, (byte) (value.headRot * 256 / 360));

            // Appelle une méthode
            buffer.write(VAR_INT, value.data);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public SpawnEntityPacket read(NetworkBuffer buffer) {
            // Appelle une méthode
            int entityId = buffer.read(VAR_INT);
            // Appelle une méthode
            UUID uuid = buffer.read(UUID);
            // Appelle une méthode
            EntityType type = buffer.read(EntityType.NETWORK_TYPE);
            // Boucle : répète un bloc
            double x = buffer.read(DOUBLE), y = buffer.read(DOUBLE), z = buffer.read(DOUBLE);
            // Appelle une méthode
            Vec velocity = buffer.read(LP_VECTOR3);
            // Appelle une méthode
            float pitch = buffer.read(BYTE) * 360f / 256f;
            // Appelle une méthode
            float yaw = buffer.read(BYTE) * 360f / 256f;
            // Appelle une méthode
            float headRot = buffer.read(BYTE) * 360f / 256f;
            // Appelle une méthode
            int data = buffer.read(VAR_INT);
            // Renvoie une valeur à l'appelant
            return new SpawnEntityPacket(
                    // Instruction de code
                    entityId, uuid, type,
                    // Crée un nouvel objet
                    new Pos(x, y, z, yaw, pitch),
                    // Instruction de code
                    headRot, data, velocity
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };
// Fin d'un bloc/d'une expression
}
