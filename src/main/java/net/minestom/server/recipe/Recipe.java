// Déclaration du paquet de ce fichier
package net.minestom.server.recipe;

// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.recipe.display.RecipeDisplay;
// Import d'une classe nécessaire
import net.minestom.server.recipe.display.SlotDisplay;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public interface Recipe {

    /**
     * Creates recipe displays for use in the recipe book.
     *
     * <p>Displays should be consistent across calls and not specific to a player, they may be cached in {@link RecipeManager}.</p>
     *
     * <p>Note that stonecutter recipes are always sent to the client and not present in the recipe book.
     * Stonecutter ingredients must be {@link SlotDisplay.Item} or {@link SlotDisplay.Tag} to be shown
     * on the client.</p>
     *
     * @return a list of recipe displays, or none if the recipe should not be displayed in the recipe book
     */
    // Début d'une méthode/d'un bloc
    default List<RecipeDisplay> createRecipeDisplays() {
        // Renvoie une valeur à l'appelant
        return List.of();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the item properties associated with this recipe. These are sent to the client to indicate
     * client side special slot prediction. For example, if a recipe includes {@link Material#STONE} in
     * {@link RecipeProperty#FURNACE_INPUT}, the client will predict that item being placed into a furnace
     * input (note that final placement is still decided by the server).
     *
     * <p>Item properties should be consistent across calls and not specific to a player, they may be cached in {@link RecipeManager}.</p>
     *
     * @return A map of item properties associated with this recipe.
     */
    // Début d'une méthode/d'un bloc
    default Map<RecipeProperty, List<Material>> itemProperties() {
        // Renvoie une valeur à l'appelant
        return Map.of();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default @Nullable String recipeBookGroup() {
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default @Nullable RecipeBookCategory recipeBookCategory() {
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default @Nullable List<Ingredient> craftingRequirements() {
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
