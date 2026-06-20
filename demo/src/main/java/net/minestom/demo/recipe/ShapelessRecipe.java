// Package declaration for this file
package net.minestom.demo.recipe;

// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.recipe.Ingredient;
// Import of a required class
import net.minestom.server.recipe.Recipe;
// Import of a required class
import net.minestom.server.recipe.RecipeBookCategory;
// Import of a required class
import net.minestom.server.recipe.display.RecipeDisplay;
// Import of a required class
import net.minestom.server.recipe.display.SlotDisplay;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record ShapelessRecipe(
        // Code statement
        RecipeBookCategory recipeBookCategory,
        // Code statement
        List<Material> ingredients,
        // Code statement
        ItemStack result
// Start of a method/block
) implements Recipe {

    // Annotation for the following element
    @Override
    // Start of a method/block
    public List<RecipeDisplay> createRecipeDisplays() {
        // Returns a value to the caller
        return List.of(new RecipeDisplay.CraftingShapeless(
                // Code statement
                ingredients.stream().map(item -> (SlotDisplay) new SlotDisplay.Item(item)).toList(),
                // Creates a new object
                new SlotDisplay.ItemStack(result),
                // Creates a new object
                new SlotDisplay.Item(Material.CRAFTING_TABLE)
        // Code statement
        ));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public List<Ingredient> craftingRequirements() {
        // Returns a value to the caller
        return List.of(new Ingredient(ingredients));
    // End of a block/expression
    }

// End of a block/expression
}
