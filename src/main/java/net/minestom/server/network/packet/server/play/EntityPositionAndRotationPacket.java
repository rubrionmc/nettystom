// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record EntityPositionAndRotationPacket(int entityId, short deltaX, short deltaY, short deltaZ,
                                              // Début d'une méthode/d'un bloc
                                              float yaw, float pitch, boolean onGround) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<EntityPositionAndRotationPacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, EntityPositionAndRotationPacket value) {
            // Appelle une méthode
            buffer.write(VAR_INT, value.entityId);
            // Appelle une méthode
            buffer.write(SHORT, value.deltaX);
            // Appelle une méthode
            buffer.write(SHORT, value.deltaY);
            // Appelle une méthode
            buffer.write(SHORT, value.deltaZ);
            // Appelle une méthode
            buffer.write(BYTE, (byte) (value.yaw * 256 / 360));
            // Appelle une méthode
            buffer.write(BYTE, (byte) (value.pitch * 256 / 360));
            // Appelle une méthode
            buffer.write(BOOLEAN, value.onGround);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public EntityPositionAndRotationPacket read(NetworkBuffer buffer) {
            // Renvoie une valeur à l'appelant
            return new EntityPositionAndRotationPacket(buffer.read(VAR_INT),
                    // Instruction de code
                    buffer.read(SHORT), buffer.read(SHORT), buffer.read(SHORT),
                    // Instruction de code
                    buffer.read(BYTE) * 360f / 256f, buffer.read(BYTE) * 360f / 256f,
                    // Appelle une méthode
                    buffer.read(BOOLEAN));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Instruction de code
    public static EntityPositionAndRotationPacket getPacket(int entityId,
                                                            // Instruction de code
                                                            Pos newPosition, Pos oldPosition,
                                                            // Début d'une méthode/d'un bloc
                                                            boolean onGround) {
        // Appelle une méthode
        final short deltaX = (short) ((newPosition.x() * 32 - oldPosition.x() * 32) * 128);
        // Appelle une méthode
        final short deltaY = (short) ((newPosition.y() * 32 - oldPosition.y() * 32) * 128);
        // Appelle une méthode
        final short deltaZ = (short) ((newPosition.z() * 32 - oldPosition.z() * 32) * 128);
        // Renvoie une valeur à l'appelant
        return new EntityPositionAndRotationPacket(entityId, deltaX, deltaY, deltaZ, newPosition.yaw(), newPosition.pitch(), onGround);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
