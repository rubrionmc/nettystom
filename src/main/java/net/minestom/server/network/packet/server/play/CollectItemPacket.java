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
public record CollectItemPacket(int collectedEntityId, int collectorEntityId, int pickupItemCount)
        // Début d'une méthode/d'un bloc
        implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<CollectItemPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, CollectItemPacket::collectedEntityId,
            // Instruction de code
            VAR_INT, CollectItemPacket::collectorEntityId,
            // Instruction de code
            VAR_INT, CollectItemPacket::pickupItemCount,
            // Instruction de code
            CollectItemPacket::new);
// Fin d'un bloc/d'une expression
}
