// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Déclaration de type (classe/interface/enum/record)
public record EntityVelocityPacket(int entityId, Vec velocity) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<EntityVelocityPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.VAR_INT, EntityVelocityPacket::entityId,
            // Instruction de code
            NetworkBuffer.LP_VECTOR3, EntityVelocityPacket::velocity,
            // Instruction de code
            EntityVelocityPacket::new);
// Fin d'un bloc/d'une expression
}
