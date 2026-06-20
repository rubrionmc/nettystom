// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BYTE;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record EntityHeadLookPacket(int entityId, float yaw) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<EntityHeadLookPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, EntityHeadLookPacket::entityId,
            // Instruction de code
            BYTE, value -> (byte) (value.yaw * 256f / 360f),
            // Instruction de code
            (entityId, yaw) -> new EntityHeadLookPacket(entityId, yaw * 360f / 256f)
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
