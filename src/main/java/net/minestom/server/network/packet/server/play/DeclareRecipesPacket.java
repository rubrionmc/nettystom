// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.recipe.Ingredient;
// Import d'une classe nécessaire
import net.minestom.server.recipe.RecipeProperty;
// Import d'une classe nécessaire
import net.minestom.server.recipe.display.SlotDisplay;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public record DeclareRecipesPacket(
        // Instruction de code
        Map<RecipeProperty, List<Material>> itemProperties,
        // Instruction de code
        List<StonecutterRecipe> stonecutterRecipes
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play {
    // Affecte une valeur
    private static final int MAX_ITEMS_PER_PROPERTY = Short.MAX_VALUE;
    // Affecte une valeur
    private static final int MAX_STONECUTTER_RECIPES = Short.MAX_VALUE;

    // Affecte une valeur
    public static final NetworkBuffer.Type<DeclareRecipesPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            RecipeProperty.NETWORK_TYPE.mapValue(Material.NETWORK_TYPE.list(MAX_ITEMS_PER_PROPERTY)), DeclareRecipesPacket::itemProperties,
            // Instruction de code
            StonecutterRecipe.NETWORK_TYPE.list(MAX_STONECUTTER_RECIPES), DeclareRecipesPacket::stonecutterRecipes,
            // Instruction de code
            DeclareRecipesPacket::new);

    // Début d'une méthode/d'un bloc
    public DeclareRecipesPacket {
        // Appelle une méthode
        itemProperties = Map.copyOf(itemProperties);
        // Appelle une méthode
        stonecutterRecipes = List.copyOf(stonecutterRecipes);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record StonecutterRecipe(
            // Instruction de code
            Ingredient ingredient,
            // Instruction de code
            SlotDisplay optionDisplay
    // Début d'une méthode/d'un bloc
    ) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<StonecutterRecipe> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                Ingredient.NETWORK_TYPE, StonecutterRecipe::ingredient,
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, StonecutterRecipe::optionDisplay,
                // Instruction de code
                StonecutterRecipe::new);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
