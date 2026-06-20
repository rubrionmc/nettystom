// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientPlaceRecipePacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.PlaceGhostRecipePacket;
// Import of a required class
import net.minestom.server.recipe.RecipeManager;
// Import of a required class
import net.minestom.server.recipe.display.RecipeDisplay;

// Type declaration (class/interface/enum/record)
public class RecipeListener {

    // Start of a method/block
    public static void listener(ClientPlaceRecipePacket packet, Player player) {
        // Calls a method
        final RecipeManager recipeManager = MinecraftServer.getRecipeManager();
        // Calls a method
        final RecipeDisplay recipeDisplay = recipeManager.getRecipeDisplay(packet.recipeDisplayId(), player);
        // Branch: checks a condition
        if (recipeDisplay == null) return;

        // Calls a method
        player.sendPacket(new PlaceGhostRecipePacket(packet.windowId(), recipeDisplay));
    // End of a block/expression
    }
// End of a block/expression
}
