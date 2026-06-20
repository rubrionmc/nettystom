// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.recipe.display.RecipeDisplay;

// Type declaration (class/interface/enum/record)
public record PlaceGhostRecipePacket(int windowId, RecipeDisplay recipe) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<PlaceGhostRecipePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.VAR_INT, PlaceGhostRecipePacket::windowId,
            // Code statement
            RecipeDisplay.NETWORK_TYPE, PlaceGhostRecipePacket::recipe,
            // Code statement
            PlaceGhostRecipePacket::new);
// End of a block/expression
}
