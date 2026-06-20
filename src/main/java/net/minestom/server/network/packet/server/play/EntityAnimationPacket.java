// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BYTE;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record EntityAnimationPacket(int entityId, Animation animation) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<EntityAnimationPacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, EntityAnimationPacket value) {
            // Appelle une méthode
            buffer.write(VAR_INT, value.entityId);
            // Appelle une méthode
            buffer.write(BYTE, (byte) value.animation.ordinal());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public EntityAnimationPacket read(NetworkBuffer buffer) {
            // Renvoie une valeur à l'appelant
            return new EntityAnimationPacket(buffer.read(VAR_INT), Animation.values()[buffer.read(BYTE)]);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

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
