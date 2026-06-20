// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.INT;

// Déclaration de type (classe/interface/enum/record)
public record AttachEntityPacket(int attachedEntityId, int holdingEntityId) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<AttachEntityPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            INT, AttachEntityPacket::attachedEntityId,
            // Instruction de code
            INT, AttachEntityPacket::holdingEntityId,
            // Instruction de code
            AttachEntityPacket::new);
// Fin d'un bloc/d'une expression
}
