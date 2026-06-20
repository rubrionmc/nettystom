// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Déclaration de type (classe/interface/enum/record)
public record PlayerRotationPacket(
        // Instruction de code
        float yaw,
        // Instruction de code
        boolean relativeYaw,
        // Instruction de code
        float pitch,
        // Instruction de code
        boolean relativePitch
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<PlayerRotationPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.FLOAT, PlayerRotationPacket::yaw,
            // Instruction de code
            NetworkBuffer.BOOLEAN, PlayerRotationPacket::relativeYaw,
            // Instruction de code
            NetworkBuffer.FLOAT, PlayerRotationPacket::pitch,
            // Instruction de code
            NetworkBuffer.BOOLEAN, PlayerRotationPacket::relativePitch,
            // Instruction de code
            PlayerRotationPacket::new);
// Fin d'un bloc/d'une expression
}
