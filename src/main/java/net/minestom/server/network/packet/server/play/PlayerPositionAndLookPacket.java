// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
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
public record PlayerPositionAndLookPacket(
        // Instruction de code
        int teleportId, Point position, Point delta,
        // Instruction de code
        float yaw, float pitch,
        // Annotation pour l'élément suivant
        @MagicConstant(flagsFromClass = RelativeFlags.class) int flags
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<PlayerPositionAndLookPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, PlayerPositionAndLookPacket::teleportId,
            // Instruction de code
            VECTOR3D, PlayerPositionAndLookPacket::position,
            // Instruction de code
            VECTOR3D, PlayerPositionAndLookPacket::delta,
            // Instruction de code
            FLOAT, PlayerPositionAndLookPacket::yaw,
            // Instruction de code
            FLOAT, PlayerPositionAndLookPacket::pitch,
            // Instruction de code
            INT, PlayerPositionAndLookPacket::flags,
            // Instruction de code
            PlayerPositionAndLookPacket::new);
// Fin d'un bloc/d'une expression
}