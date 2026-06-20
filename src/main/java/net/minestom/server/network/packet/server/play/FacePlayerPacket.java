// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record FacePlayerPacket(FacePosition facePosition,
                               // Instruction de code
                               Point target, int entityId,
                               // Début d'une méthode/d'un bloc
                               FacePosition entityFacePosition) implements ServerPacket.Play {

    // Affecte une valeur
    public static final NetworkBuffer.Type<FacePlayerPacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, FacePlayerPacket value) {
            // Appelle une méthode
            buffer.write(Enum(FacePosition.class), value.facePosition);
            // Appelle une méthode
            buffer.write(VECTOR3D, value.target);
            // Affecte une valeur
            final boolean isEntity = value.entityId > 0;
            // Appelle une méthode
            buffer.write(BOOLEAN, isEntity);
            // Embranchement : vérifie une condition
            if (isEntity) {
                // Appelle une méthode
                buffer.write(VAR_INT, value.entityId);
                // Appelle une méthode
                buffer.write(Enum(FacePosition.class), value.entityFacePosition);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public FacePlayerPacket read(NetworkBuffer buffer) {
            // Renvoie une valeur à l'appelant
            return new FacePlayerPacket(buffer.read(Enum(FacePosition.class)),
                    // Instruction de code
                    buffer.read(VECTOR3D), buffer.read(BOOLEAN) ? buffer.read(VAR_INT) : 0,
                    // Appelle une méthode
                    buffer.readableBytes() > 0 ? buffer.read(Enum(FacePosition.class)) : null);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Déclaration de type (classe/interface/enum/record)
    public enum FacePosition {
        // Instruction de code
        FEET, EYES
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
