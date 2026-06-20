// Package declaration for this file
package net.minestom.server.recipe.display;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.adventure.ComponentHolder;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.UnaryOperator;

// Type declaration (class/interface/enum/record)
public sealed interface RecipeDisplay extends ComponentHolder<RecipeDisplay> {
    // Assigns a value
    NetworkBuffer.Type<RecipeDisplay> NETWORK_TYPE = RecipeDisplayType.NETWORK_TYPE
            // Calls a method
            .unionType(RecipeDisplay::dataSerializer, RecipeDisplay::recipeDisplayToType);

    // Type declaration (class/interface/enum/record)
    record CraftingShapeless(
            // Code statement
            List<SlotDisplay> ingredients,
            // Code statement
            SlotDisplay result,
            // Code statement
            SlotDisplay craftingStation
    // Start of a method/block
    ) implements RecipeDisplay {
        // Assigns a value
        private static final int MAX_INGREDIENTS = Short.MAX_VALUE;

        // Assigns a value
        public static final NetworkBuffer.Type<CraftingShapeless> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                SlotDisplay.NETWORK_TYPE.list(MAX_INGREDIENTS), CraftingShapeless::ingredients,
                // Code statement
                SlotDisplay.NETWORK_TYPE, CraftingShapeless::result,
                // Code statement
                SlotDisplay.NETWORK_TYPE, CraftingShapeless::craftingStation,
                // Code statement
                CraftingShapeless::new);

        // Start of a method/block
        public CraftingShapeless {
            // Calls a method
            ingredients = List.copyOf(ingredients);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<Component> components() {
            // Calls a method
            final var components = new ArrayList<Component>();
            // Loop: repeats a block
            for (SlotDisplay ingredient : ingredients)
                // Calls a method
                components.addAll(ingredient.components());
            // Calls a method
            components.addAll(result.components());
            // Calls a method
            components.addAll(craftingStation.components());
            // Returns a value to the caller
            return List.copyOf(components);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public RecipeDisplay copyWithOperator(UnaryOperator<Component> operator) {
            // Calls a method
            final var newIngredients = new ArrayList<SlotDisplay>();
            // Loop: repeats a block
            for (SlotDisplay ingredient : ingredients)
                // Calls a method
                newIngredients.add(ingredient.copyWithOperator(operator));
            // Returns a value to the caller
            return new CraftingShapeless(newIngredients, result.copyWithOperator(operator), craftingStation.copyWithOperator(operator));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record CraftingShaped(
            // Code statement
            int width, int height,
            // Code statement
            List<SlotDisplay> ingredients,
            // Code statement
            SlotDisplay result,
            // Code statement
            SlotDisplay craftingStation
    // Start of a method/block
    ) implements RecipeDisplay {
        // Assigns a value
        private static final int MAX_INGREDIENTS = Short.MAX_VALUE;

        // Assigns a value
        public static final NetworkBuffer.Type<CraftingShaped> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.VAR_INT, CraftingShaped::width,
                // Code statement
                NetworkBuffer.VAR_INT, CraftingShaped::height,
                // Code statement
                SlotDisplay.NETWORK_TYPE.list(MAX_INGREDIENTS), CraftingShaped::ingredients,
                // Code statement
                SlotDisplay.NETWORK_TYPE, CraftingShaped::result,
                // Code statement
                SlotDisplay.NETWORK_TYPE, CraftingShaped::craftingStation,
                // Code statement
                CraftingShaped::new);

        // Start of a method/block
        public CraftingShaped {
            // Branch: checks a condition
            if (ingredients.size() != width * height)
                // Throws an exception
                throw new IllegalArgumentException("Invalid shaped recipe, ingredients size must be equal to width * height");
            // Calls a method
            ingredients = List.copyOf(ingredients);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<Component> components() {
            // Calls a method
            final var components = new ArrayList<Component>();
            // Loop: repeats a block
            for (SlotDisplay ingredient : ingredients)
                // Calls a method
                components.addAll(ingredient.components());
            // Calls a method
            components.addAll(result.components());
            // Calls a method
            components.addAll(craftingStation.components());
            // Returns a value to the caller
            return List.copyOf(components);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public RecipeDisplay copyWithOperator(UnaryOperator<Component> operator) {
            // Calls a method
            final var newIngredients = new ArrayList<SlotDisplay>();
            // Loop: repeats a block
            for (SlotDisplay ingredient : ingredients)
                // Calls a method
                newIngredients.add(ingredient.copyWithOperator(operator));
            // Returns a value to the caller
            return new CraftingShaped(width, height, newIngredients, result.copyWithOperator(operator), craftingStation.copyWithOperator(operator));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Furnace(
            // Code statement
            SlotDisplay ingredient,
            // Code statement
            SlotDisplay fuel,
            // Code statement
            SlotDisplay result,
            // Code statement
            SlotDisplay craftingStation,
            // Code statement
            int duration, float experience
    // Start of a method/block
    ) implements RecipeDisplay {
        // Assigns a value
        public static final NetworkBuffer.Type<Furnace> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                SlotDisplay.NETWORK_TYPE, Furnace::ingredient,
                // Code statement
                SlotDisplay.NETWORK_TYPE, Furnace::fuel,
                // Code statement
                SlotDisplay.NETWORK_TYPE, Furnace::result,
                // Code statement
                SlotDisplay.NETWORK_TYPE, Furnace::craftingStation,
                // Code statement
                NetworkBuffer.VAR_INT, Furnace::duration,
                // Code statement
                NetworkBuffer.FLOAT, Furnace::experience,
                // Code statement
                Furnace::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<Component> components() {
            // Calls a method
            final var components = new ArrayList<Component>();
            // Calls a method
            components.addAll(ingredient.components());
            // Calls a method
            components.addAll(fuel.components());
            // Calls a method
            components.addAll(result.components());
            // Calls a method
            components.addAll(craftingStation.components());
            // Returns a value to the caller
            return List.copyOf(components);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public RecipeDisplay copyWithOperator(UnaryOperator<Component> operator) {
            // Returns a value to the caller
            return new Furnace(ingredient.copyWithOperator(operator), fuel.copyWithOperator(operator),
                    // Code statement
                    result.copyWithOperator(operator), craftingStation.copyWithOperator(operator),
                    // Code statement
                    duration, experience);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Stonecutter(
            // Code statement
            SlotDisplay ingredient,
            // Code statement
            SlotDisplay result,
            // Code statement
            SlotDisplay craftingStation
    // Start of a method/block
    ) implements RecipeDisplay {
        // Assigns a value
        public static final NetworkBuffer.Type<Stonecutter> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                SlotDisplay.NETWORK_TYPE, Stonecutter::ingredient,
                // Code statement
                SlotDisplay.NETWORK_TYPE, Stonecutter::result,
                // Code statement
                SlotDisplay.NETWORK_TYPE, Stonecutter::craftingStation,
                // Code statement
                Stonecutter::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<Component> components() {
            // Calls a method
            final var components = new ArrayList<Component>();
            // Calls a method
            components.addAll(ingredient.components());
            // Calls a method
            components.addAll(result.components());
            // Calls a method
            components.addAll(craftingStation.components());
            // Returns a value to the caller
            return List.copyOf(components);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public RecipeDisplay copyWithOperator(UnaryOperator<Component> operator) {
            // Returns a value to the caller
            return new Stonecutter(ingredient.copyWithOperator(operator), result.copyWithOperator(operator),
                    // Calls a method
                    craftingStation.copyWithOperator(operator));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Smithing(
            // Code statement
            SlotDisplay template,
            // Code statement
            SlotDisplay base,
            // Code statement
            SlotDisplay addition,
            // Code statement
            SlotDisplay result,
            // Code statement
            SlotDisplay craftingStation
    // Start of a method/block
    ) implements RecipeDisplay {
        // Assigns a value
        public static final NetworkBuffer.Type<Smithing> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                SlotDisplay.NETWORK_TYPE, Smithing::template,
                // Code statement
                SlotDisplay.NETWORK_TYPE, Smithing::base,
                // Code statement
                SlotDisplay.NETWORK_TYPE, Smithing::addition,
                // Code statement
                SlotDisplay.NETWORK_TYPE, Smithing::result,
                // Code statement
                SlotDisplay.NETWORK_TYPE, Smithing::craftingStation,
                // Code statement
                Smithing::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<Component> components() {
            // Calls a method
            final var components = new ArrayList<Component>();
            // Calls a method
            components.addAll(template.components());
            // Calls a method
            components.addAll(base.components());
            // Calls a method
            components.addAll(addition.components());
            // Calls a method
            components.addAll(result.components());
            // Calls a method
            components.addAll(craftingStation.components());
            // Returns a value to the caller
            return List.copyOf(components);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public RecipeDisplay copyWithOperator(UnaryOperator<Component> operator) {
            // Returns a value to the caller
            return new Smithing(template.copyWithOperator(operator), base.copyWithOperator(operator),
                    // Calls a method
                    addition.copyWithOperator(operator), result.copyWithOperator(operator), craftingStation.copyWithOperator(operator));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static NetworkBuffer.Type<? extends RecipeDisplay> dataSerializer(RecipeDisplayType type) {
        // Returns a value to the caller
        return switch (type) {
            // Multiple branching (switch/case)
            case CRAFTING_SHAPELESS -> CraftingShapeless.NETWORK_TYPE;
            // Multiple branching (switch/case)
            case CRAFTING_SHAPED -> CraftingShaped.NETWORK_TYPE;
            // Multiple branching (switch/case)
            case FURNACE -> Furnace.NETWORK_TYPE;
            // Multiple branching (switch/case)
            case STONECUTTER -> Stonecutter.NETWORK_TYPE;
            // Multiple branching (switch/case)
            case SMITHING -> Smithing.NETWORK_TYPE;
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    private static RecipeDisplayType recipeDisplayToType(RecipeDisplay recipeDisplay) {
        // Returns a value to the caller
        return switch (recipeDisplay) {
            // Multiple branching (switch/case)
            case CraftingShapeless ignored -> RecipeDisplayType.CRAFTING_SHAPELESS;
            // Multiple branching (switch/case)
            case CraftingShaped ignored -> RecipeDisplayType.CRAFTING_SHAPED;
            // Multiple branching (switch/case)
            case Furnace ignored -> RecipeDisplayType.FURNACE;
            // Multiple branching (switch/case)
            case Stonecutter ignored -> RecipeDisplayType.STONECUTTER;
            // Multiple branching (switch/case)
            case Smithing ignored -> RecipeDisplayType.SMITHING;
        // End of a block/expression
        };
    // End of a block/expression
    }
// End of a block/expression
}
