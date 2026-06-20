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

// Déclaration de type (classe/interface/enum/record)
public record RecipeBookRemovePacket(List<Integer> displayIds) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<RecipeBookRemovePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.VAR_INT.list(), RecipeBookRemovePacket::displayIds,
            // Instruction de code
            RecipeBookRemovePacket::new);

    // Début d'une méthode/d'un bloc
    public RecipeBookRemovePacket {
        // Appelle une méthode
        displayIds = List.copyOf(displayIds);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
