// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

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
public record EntityPositionPacket(int entityId, short deltaX, short deltaY, short deltaZ, boolean onGround)
        // Début d'une méthode/d'un bloc
        implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<EntityPositionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, EntityPositionPacket::entityId,
            // Instruction de code
            SHORT, EntityPositionPacket::deltaX,
            // Instruction de code
            SHORT, EntityPositionPacket::deltaY,
            // Instruction de code
            SHORT, EntityPositionPacket::deltaZ,
            // Instruction de code
            BOOLEAN, EntityPositionPacket::onGround,
            // Instruction de code
            EntityPositionPacket::new);

    // Instruction de code
    public static EntityPositionPacket getPacket(int entityId,
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
        return new EntityPositionPacket(entityId, deltaX, deltaY, deltaZ, onGround);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
