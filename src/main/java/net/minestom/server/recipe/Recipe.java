// Package declaration for this file
package net.minestom.server.recipe;

// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.recipe.display.RecipeDisplay;
// Import of a required class
import net.minestom.server.recipe.display.SlotDisplay;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
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
    // Start of a method/block
    default List<RecipeDisplay> createRecipeDisplays() {
        // Returns a value to the caller
        return List.of();
    // End of a block/expression
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
    // Start of a method/block
    default Map<RecipeProperty, List<Material>> itemProperties() {
        // Returns a value to the caller
        return Map.of();
    // End of a block/expression
    }

    // Start of a method/block
    default @Nullable String recipeBookGroup() {
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    // Start of a method/block
    default @Nullable RecipeBookCategory recipeBookCategory() {
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    // Start of a method/block
    default @Nullable List<Ingredient> craftingRequirements() {
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

// End of a block/expression
}
