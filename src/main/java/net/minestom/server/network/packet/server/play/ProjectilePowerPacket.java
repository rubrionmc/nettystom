// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.DOUBLE;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record ProjectilePowerPacket(
        // Instruction de code
        int entityId, double accelerationPower
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ProjectilePowerPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, ProjectilePowerPacket::entityId,
            // Boucle : répète un bloc
            DOUBLE, ProjectilePowerPacket::accelerationPower,
            // Instruction de code
            ProjectilePowerPacket::new);
// Fin d'un bloc/d'une expression
}
