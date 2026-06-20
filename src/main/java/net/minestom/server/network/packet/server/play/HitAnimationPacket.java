// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.FLOAT;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record HitAnimationPacket(int entityId, float yaw) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<HitAnimationPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, HitAnimationPacket::entityId,
            // Instruction de code
            FLOAT, HitAnimationPacket::yaw,
            // Instruction de code
            HitAnimationPacket::new);
// Fin d'un bloc/d'une expression
}
