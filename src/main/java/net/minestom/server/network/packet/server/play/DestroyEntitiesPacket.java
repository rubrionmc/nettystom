// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record DestroyEntitiesPacket(List<Integer> entityIds) implements ServerPacket.Play {
    // Affecte une valeur
    public static final int MAX_ENTRIES = Short.MAX_VALUE;

    // Affecte une valeur
    public static final NetworkBuffer.Type<DestroyEntitiesPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT.list(Short.MAX_VALUE), DestroyEntitiesPacket::entityIds,
            // Instruction de code
            DestroyEntitiesPacket::new);

    // Début d'une méthode/d'un bloc
    public DestroyEntitiesPacket {
        // Appelle une méthode
        entityIds = List.copyOf(entityIds);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public DestroyEntitiesPacket(int entityId) {
        // Appelle une méthode
        this(List.of(entityId));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
