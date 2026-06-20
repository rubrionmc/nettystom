// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.RelativeFlags;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import org.intellij.lang.annotations.MagicConstant;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record EntityTeleportPacket(
        // Instruction de code
        int entityId, Pos position, Point delta,
        // Annotation pour l'élément suivant
        @MagicConstant(flagsFromClass = RelativeFlags.class) int flags,
        // Début d'une méthode/d'un bloc
        boolean onGround) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<EntityTeleportPacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, EntityTeleportPacket value) {
            // Appelle une méthode
            buffer.write(VAR_INT, value.entityId);
            // Appelle une méthode
            buffer.write(VECTOR3D, value.position.asVec());
            // Appelle une méthode
            buffer.write(VECTOR3D, value.delta);
            // Appelle une méthode
            buffer.write(FLOAT, value.position.yaw());
            // Appelle une méthode
            buffer.write(FLOAT, value.position.pitch());
            // Appelle une méthode
            buffer.write(INT, value.flags);
            // Appelle une méthode
            buffer.write(BOOLEAN, value.onGround);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public EntityTeleportPacket read(NetworkBuffer buffer) {
            // Appelle une méthode
            int entityId = buffer.read(VAR_INT);
            // Order is x,y,z for position, then x,y,z for delta move, then yaw and pitch
            // Appelle une méthode
            Point absPosition = buffer.read(VECTOR3D);
            // Appelle une méthode
            Point deltaMovement = buffer.read(VECTOR3D);
            // Renvoie une valeur à l'appelant
            return new EntityTeleportPacket(entityId, new Pos(absPosition, buffer.read(FLOAT), buffer.read(FLOAT)),
                            // Appelle une méthode
                            deltaMovement, buffer.read(INT), buffer.read(BOOLEAN));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };
// Fin d'un bloc/d'une expression
}
