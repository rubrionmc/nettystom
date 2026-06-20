// Déclaration du paquet de ce fichier
package net.minestom.demo.recipe;

// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.recipe.Ingredient;
// Import d'une classe nécessaire
import net.minestom.server.recipe.Recipe;
// Import d'une classe nécessaire
import net.minestom.server.recipe.RecipeBookCategory;
// Import d'une classe nécessaire
import net.minestom.server.recipe.display.RecipeDisplay;
// Import d'une classe nécessaire
import net.minestom.server.recipe.display.SlotDisplay;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record ShapelessRecipe(
        // Instruction de code
        RecipeBookCategory recipeBookCategory,
        // Instruction de code
        List<Material> ingredients,
        // Instruction de code
        ItemStack result
// Début d'une méthode/d'un bloc
) implements Recipe {

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public List<RecipeDisplay> createRecipeDisplays() {
        // Renvoie une valeur à l'appelant
        return List.of(new RecipeDisplay.CraftingShapeless(
                // Instruction de code
                ingredients.stream().map(item -> (SlotDisplay) new SlotDisplay.Item(item)).toList(),
                // Crée un nouvel objet
                new SlotDisplay.ItemStack(result),
                // Crée un nouvel objet
                new SlotDisplay.Item(Material.CRAFTING_TABLE)
        // Instruction de code
        ));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public List<Ingredient> craftingRequirements() {
        // Renvoie une valeur à l'appelant
        return List.of(new Ingredient(ingredients));
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
