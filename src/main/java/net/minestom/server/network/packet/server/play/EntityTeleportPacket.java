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
import net.minestom.server.network.NetworkBufferTemplate;
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
    public static final NetworkBuffer.Type<EntityTeleportPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, EntityTeleportPacket::entityId,
            // Instruction de code
            VECTOR3D, EntityTeleportPacket::position,
            // Instruction de code
            VECTOR3D, EntityTeleportPacket::delta,
            // Instruction de code
            FLOAT, value -> value.position.yaw(),
            // Instruction de code
            FLOAT, value -> value.position.pitch(),
            // Instruction de code
            INT, EntityTeleportPacket::flags,
            // Instruction de code
            BOOLEAN, EntityTeleportPacket::onGround,
            // Instruction de code
            (entityId, absPosition, deltaMovement, yaw, pitch, flags, onGround) ->
                    // Crée un nouvel objet
                    new EntityTeleportPacket(entityId, absPosition.asPos().withView(yaw, pitch),
                            // Instruction de code
                            deltaMovement, flags, onGround)
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
