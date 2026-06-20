// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.recipe.display.RecipeDisplay;

// Déclaration de type (classe/interface/enum/record)
public record PlaceGhostRecipePacket(int windowId, RecipeDisplay recipe) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<PlaceGhostRecipePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.VAR_INT, PlaceGhostRecipePacket::windowId,
            // Instruction de code
            RecipeDisplay.NETWORK_TYPE, PlaceGhostRecipePacket::recipe,
            // Instruction de code
            PlaceGhostRecipePacket::new);
// Fin d'un bloc/d'une expression
}
