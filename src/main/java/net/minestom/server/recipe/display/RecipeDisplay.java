// Déclaration du paquet de ce fichier
package net.minestom.server.recipe.display;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.adventure.ComponentHolder;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Déclaration de type (classe/interface/enum/record)
public sealed interface RecipeDisplay extends ComponentHolder<RecipeDisplay> {
    // Affecte une valeur
    NetworkBuffer.Type<RecipeDisplay> NETWORK_TYPE = RecipeDisplayType.NETWORK_TYPE
            // Appelle une méthode
            .unionType(RecipeDisplay::dataSerializer, RecipeDisplay::recipeDisplayToType);

    // Déclaration de type (classe/interface/enum/record)
    record CraftingShapeless(
            // Instruction de code
            List<SlotDisplay> ingredients,
            // Instruction de code
            SlotDisplay result,
            // Instruction de code
            SlotDisplay craftingStation
    // Début d'une méthode/d'un bloc
    ) implements RecipeDisplay {
        // Affecte une valeur
        private static final int MAX_INGREDIENTS = Short.MAX_VALUE;

        // Affecte une valeur
        public static final NetworkBuffer.Type<CraftingShapeless> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                SlotDisplay.NETWORK_TYPE.list(MAX_INGREDIENTS), CraftingShapeless::ingredients,
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, CraftingShapeless::result,
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, CraftingShapeless::craftingStation,
                // Instruction de code
                CraftingShapeless::new);

        // Début d'une méthode/d'un bloc
        public CraftingShapeless {
            // Appelle une méthode
            ingredients = List.copyOf(ingredients);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<Component> components() {
            // Appelle une méthode
            final var components = new ArrayList<Component>();
            // Boucle : répète un bloc
            for (SlotDisplay ingredient : ingredients)
                // Appelle une méthode
                components.addAll(ingredient.components());
            // Appelle une méthode
            components.addAll(result.components());
            // Appelle une méthode
            components.addAll(craftingStation.components());
            // Renvoie une valeur à l'appelant
            return List.copyOf(components);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public RecipeDisplay copyWithOperator(UnaryOperator<Component> operator) {
            // Appelle une méthode
            final var newIngredients = new ArrayList<SlotDisplay>();
            // Boucle : répète un bloc
            for (SlotDisplay ingredient : ingredients)
                // Appelle une méthode
                newIngredients.add(ingredient.copyWithOperator(operator));
            // Renvoie une valeur à l'appelant
            return new CraftingShapeless(newIngredients, result.copyWithOperator(operator), craftingStation.copyWithOperator(operator));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record CraftingShaped(
            // Instruction de code
            int width, int height,
            // Instruction de code
            List<SlotDisplay> ingredients,
            // Instruction de code
            SlotDisplay result,
            // Instruction de code
            SlotDisplay craftingStation
    // Début d'une méthode/d'un bloc
    ) implements RecipeDisplay {
        // Affecte une valeur
        private static final int MAX_INGREDIENTS = Short.MAX_VALUE;

        // Affecte une valeur
        public static final NetworkBuffer.Type<CraftingShaped> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.VAR_INT, CraftingShaped::width,
                // Instruction de code
                NetworkBuffer.VAR_INT, CraftingShaped::height,
                // Instruction de code
                SlotDisplay.NETWORK_TYPE.list(MAX_INGREDIENTS), CraftingShaped::ingredients,
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, CraftingShaped::result,
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, CraftingShaped::craftingStation,
                // Instruction de code
                CraftingShaped::new);

        // Début d'une méthode/d'un bloc
        public CraftingShaped {
            // Embranchement : vérifie une condition
            if (ingredients.size() != width * height)
                // Lève une exception
                throw new IllegalArgumentException("Invalid shaped recipe, ingredients size must be equal to width * height");
            // Appelle une méthode
            ingredients = List.copyOf(ingredients);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<Component> components() {
            // Appelle une méthode
            final var components = new ArrayList<Component>();
            // Boucle : répète un bloc
            for (SlotDisplay ingredient : ingredients)
                // Appelle une méthode
                components.addAll(ingredient.components());
            // Appelle une méthode
            components.addAll(result.components());
            // Appelle une méthode
            components.addAll(craftingStation.components());
            // Renvoie une valeur à l'appelant
            return List.copyOf(components);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public RecipeDisplay copyWithOperator(UnaryOperator<Component> operator) {
            // Appelle une méthode
            final var newIngredients = new ArrayList<SlotDisplay>();
            // Boucle : répète un bloc
            for (SlotDisplay ingredient : ingredients)
                // Appelle une méthode
                newIngredients.add(ingredient.copyWithOperator(operator));
            // Renvoie une valeur à l'appelant
            return new CraftingShaped(width, height, newIngredients, result.copyWithOperator(operator), craftingStation.copyWithOperator(operator));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Furnace(
            // Instruction de code
            SlotDisplay ingredient,
            // Instruction de code
            SlotDisplay fuel,
            // Instruction de code
            SlotDisplay result,
            // Instruction de code
            SlotDisplay craftingStation,
            // Instruction de code
            int duration, float experience
    // Début d'une méthode/d'un bloc
    ) implements RecipeDisplay {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Furnace> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, Furnace::ingredient,
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, Furnace::fuel,
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, Furnace::result,
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, Furnace::craftingStation,
                // Instruction de code
                NetworkBuffer.VAR_INT, Furnace::duration,
                // Instruction de code
                NetworkBuffer.FLOAT, Furnace::experience,
                // Instruction de code
                Furnace::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<Component> components() {
            // Appelle une méthode
            final var components = new ArrayList<Component>();
            // Appelle une méthode
            components.addAll(ingredient.components());
            // Appelle une méthode
            components.addAll(fuel.components());
            // Appelle une méthode
            components.addAll(result.components());
            // Appelle une méthode
            components.addAll(craftingStation.components());
            // Renvoie une valeur à l'appelant
            return List.copyOf(components);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public RecipeDisplay copyWithOperator(UnaryOperator<Component> operator) {
            // Renvoie une valeur à l'appelant
            return new Furnace(ingredient.copyWithOperator(operator), fuel.copyWithOperator(operator),
                    // Instruction de code
                    result.copyWithOperator(operator), craftingStation.copyWithOperator(operator),
                    // Instruction de code
                    duration, experience);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Stonecutter(
            // Instruction de code
            SlotDisplay ingredient,
            // Instruction de code
            SlotDisplay result,
            // Instruction de code
            SlotDisplay craftingStation
    // Début d'une méthode/d'un bloc
    ) implements RecipeDisplay {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Stonecutter> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, Stonecutter::ingredient,
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, Stonecutter::result,
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, Stonecutter::craftingStation,
                // Instruction de code
                Stonecutter::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<Component> components() {
            // Appelle une méthode
            final var components = new ArrayList<Component>();
            // Appelle une méthode
            components.addAll(ingredient.components());
            // Appelle une méthode
            components.addAll(result.components());
            // Appelle une méthode
            components.addAll(craftingStation.components());
            // Renvoie une valeur à l'appelant
            return List.copyOf(components);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public RecipeDisplay copyWithOperator(UnaryOperator<Component> operator) {
            // Renvoie une valeur à l'appelant
            return new Stonecutter(ingredient.copyWithOperator(operator), result.copyWithOperator(operator),
                    // Appelle une méthode
                    craftingStation.copyWithOperator(operator));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Smithing(
            // Instruction de code
            SlotDisplay template,
            // Instruction de code
            SlotDisplay base,
            // Instruction de code
            SlotDisplay addition,
            // Instruction de code
            SlotDisplay result,
            // Instruction de code
            SlotDisplay craftingStation
    // Début d'une méthode/d'un bloc
    ) implements RecipeDisplay {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Smithing> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, Smithing::template,
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, Smithing::base,
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, Smithing::addition,
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, Smithing::result,
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, Smithing::craftingStation,
                // Instruction de code
                Smithing::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<Component> components() {
            // Appelle une méthode
            final var components = new ArrayList<Component>();
            // Appelle une méthode
            components.addAll(template.components());
            // Appelle une méthode
            components.addAll(base.components());
            // Appelle une méthode
            components.addAll(addition.components());
            // Appelle une méthode
            components.addAll(result.components());
            // Appelle une méthode
            components.addAll(craftingStation.components());
            // Renvoie une valeur à l'appelant
            return List.copyOf(components);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public RecipeDisplay copyWithOperator(UnaryOperator<Component> operator) {
            // Renvoie une valeur à l'appelant
            return new Smithing(template.copyWithOperator(operator), base.copyWithOperator(operator),
                    // Appelle une méthode
                    addition.copyWithOperator(operator), result.copyWithOperator(operator), craftingStation.copyWithOperator(operator));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static NetworkBuffer.Type<? extends RecipeDisplay> dataSerializer(RecipeDisplayType type) {
        // Renvoie une valeur à l'appelant
        return switch (type) {
            // Embranchement multiple (switch/case)
            case CRAFTING_SHAPELESS -> CraftingShapeless.NETWORK_TYPE;
            // Embranchement multiple (switch/case)
            case CRAFTING_SHAPED -> CraftingShaped.NETWORK_TYPE;
            // Embranchement multiple (switch/case)
            case FURNACE -> Furnace.NETWORK_TYPE;
            // Embranchement multiple (switch/case)
            case STONECUTTER -> Stonecutter.NETWORK_TYPE;
            // Embranchement multiple (switch/case)
            case SMITHING -> Smithing.NETWORK_TYPE;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static RecipeDisplayType recipeDisplayToType(RecipeDisplay recipeDisplay) {
        // Renvoie une valeur à l'appelant
        return switch (recipeDisplay) {
            // Embranchement multiple (switch/case)
            case CraftingShapeless ignored -> RecipeDisplayType.CRAFTING_SHAPELESS;
            // Embranchement multiple (switch/case)
            case CraftingShaped ignored -> RecipeDisplayType.CRAFTING_SHAPED;
            // Embranchement multiple (switch/case)
            case Furnace ignored -> RecipeDisplayType.FURNACE;
            // Embranchement multiple (switch/case)
            case Stonecutter ignored -> RecipeDisplayType.STONECUTTER;
            // Embranchement multiple (switch/case)
            case Smithing ignored -> RecipeDisplayType.SMITHING;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
