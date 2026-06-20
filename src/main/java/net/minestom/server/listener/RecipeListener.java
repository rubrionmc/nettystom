// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientPlaceRecipePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.PlaceGhostRecipePacket;
// Import d'une classe nécessaire
import net.minestom.server.recipe.RecipeManager;
// Import d'une classe nécessaire
import net.minestom.server.recipe.display.RecipeDisplay;

// Déclaration de type (classe/interface/enum/record)
public class RecipeListener {

    // Début d'une méthode/d'un bloc
    public static void listener(ClientPlaceRecipePacket packet, Player player) {
        // Appelle une méthode
        final RecipeManager recipeManager = MinecraftServer.getRecipeManager();
        // Appelle une méthode
        final RecipeDisplay recipeDisplay = recipeManager.getRecipeDisplay(packet.recipeDisplayId(), player);
        // Embranchement : vérifie une condition
        if (recipeDisplay == null) return;

        // Appelle une méthode
        player.sendPacket(new PlaceGhostRecipePacket(packet.windowId(), recipeDisplay));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
