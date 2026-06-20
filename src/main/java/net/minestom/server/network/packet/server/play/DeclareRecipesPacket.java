// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.recipe.Ingredient;
// Import of a required class
import net.minestom.server.recipe.RecipeProperty;
// Import of a required class
import net.minestom.server.recipe.display.SlotDisplay;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public record DeclareRecipesPacket(
        // Code statement
        Map<RecipeProperty, List<Material>> itemProperties,
        // Code statement
        List<StonecutterRecipe> stonecutterRecipes
// Start of a method/block
) implements ServerPacket.Play {
    // Assigns a value
    private static final int MAX_ITEMS_PER_PROPERTY = Short.MAX_VALUE;
    // Assigns a value
    private static final int MAX_STONECUTTER_RECIPES = Short.MAX_VALUE;

    // Assigns a value
    public static final NetworkBuffer.Type<DeclareRecipesPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            RecipeProperty.NETWORK_TYPE.mapValue(Material.NETWORK_TYPE.list(MAX_ITEMS_PER_PROPERTY)), DeclareRecipesPacket::itemProperties,
            // Code statement
            StonecutterRecipe.NETWORK_TYPE.list(MAX_STONECUTTER_RECIPES), DeclareRecipesPacket::stonecutterRecipes,
            // Code statement
            DeclareRecipesPacket::new);

    // Start of a method/block
    public DeclareRecipesPacket {
        // Calls a method
        itemProperties = Map.copyOf(itemProperties);
        // Calls a method
        stonecutterRecipes = List.copyOf(stonecutterRecipes);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record StonecutterRecipe(
            // Code statement
            Ingredient ingredient,
            // Code statement
            SlotDisplay optionDisplay
    // Start of a method/block
    ) {
        // Assigns a value
        public static final NetworkBuffer.Type<StonecutterRecipe> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                Ingredient.NETWORK_TYPE, StonecutterRecipe::ingredient,
                // Code statement
                SlotDisplay.NETWORK_TYPE, StonecutterRecipe::optionDisplay,
                // Code statement
                StonecutterRecipe::new);
    // End of a block/expression
    }

// End of a block/expression
}
