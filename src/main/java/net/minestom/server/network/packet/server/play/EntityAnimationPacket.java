// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record EntityAnimationPacket(int entityId, Animation animation) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<EntityAnimationPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, EntityAnimationPacket::entityId,
            // Instruction de code
            NetworkBuffer.Enum(Animation.class), EntityAnimationPacket::animation,
            // Instruction de code
            EntityAnimationPacket::new
    // Fin d'un bloc/d'une expression
    );

    // Déclaration de type (classe/interface/enum/record)
    public enum Animation {
        // Instruction de code
        SWING_MAIN_ARM,
        // Instruction de code
        TAKE_DAMAGE,
        // Instruction de code
        LEAVE_BED,
        // Instruction de code
        SWING_OFF_HAND,
        // Instruction de code
        CRITICAL_EFFECT,
        // Instruction de code
        MAGICAL_CRITICAL_EFFECT
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
