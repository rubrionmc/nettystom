// Déclaration du paquet de ce fichier
package net.minestom.server.recipe;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.CachedPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.SendablePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.DeclareRecipesPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.RecipeBookAddPacket;
// Import d'une classe nécessaire
import net.minestom.server.recipe.display.RecipeDisplay;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;
// Import d'une classe nécessaire
import java.util.function.Predicate;

// Déclaration de type (classe/interface/enum/record)
public final class RecipeManager {
    // Appelle une méthode
    private static final AtomicInteger NEXT_DISPLAY_ID = new AtomicInteger();

    // Déclaration de type (classe/interface/enum/record)
    private record RecipeData(
            // Instruction de code
            Recipe recipe,
            // Instruction de code
            List<RecipeBookAddPacket.Entry> displays,
            // Instruction de code
            Predicate<Player> predicate
    // Début d'une méthode/d'un bloc
    ) {
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    private final CachedPacket declareRecipesPacket = new CachedPacket(this::createDeclareRecipesPacket);

    // Affecte une valeur
    private final Map<Recipe, RecipeData> recipes = new ConcurrentHashMap<>();
    // Affecte une valeur
    private final Int2ObjectMap<Map.Entry<RecipeBookAddPacket.Entry, Predicate<Player>>> recipeBookEntryIdMap =
            // Appelle une méthode
            Int2ObjectMaps.synchronize(new Int2ObjectArrayMap<>());

    // Début d'une méthode/d'un bloc
    public void addRecipe(Recipe recipe) {
        // Appelle une méthode
        addRecipe(recipe, player -> true);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void addRecipe(Recipe recipe, Predicate<Player> predicate) {
        // Affecte une valeur
        List<RecipeBookAddPacket.Entry> recipeBookEntries = new ArrayList<>();
        // Appelle une méthode
        final RecipeBookCategory recipeBookCategory = recipe.recipeBookCategory();
        // Embranchement : vérifie une condition
        if (recipeBookCategory != null) {
            // Boucle : répète un bloc
            for (var display : recipe.createRecipeDisplays()) {
                // Appelle une méthode
                int displayId = NEXT_DISPLAY_ID.getAndIncrement();
                // Instruction de code
                recipeBookEntries.add(new RecipeBookAddPacket.Entry( //todo groups
                        // Instruction de code
                        displayId, display, null, recipeBookCategory,
                        // Instruction de code
                        recipe.craftingRequirements(), false, false
                // Instruction de code
                ));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        var existingRecipe = recipes.putIfAbsent(recipe, new RecipeData(recipe, recipeBookEntries, predicate));
        // Appelle une méthode
        Check.argCondition(existingRecipe != null, "Recipe is already registered: " + recipe);
        // Boucle : répète un bloc
        for (RecipeBookAddPacket.Entry entry : recipeBookEntries) {
            // Appelle une méthode
            recipeBookEntryIdMap.put(entry.displayId(), Map.entry(entry, predicate));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void removeRecipe(Recipe recipe) {
        // Appelle une méthode
        final RecipeData removed = recipes.remove(recipe);
        // Embranchement : vérifie une condition
        if (removed != null) {
            // Boucle : répète un bloc
            for (var entry : removed.displays) {
                // Appelle une méthode
                recipeBookEntryIdMap.remove(entry.displayId());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Set<Recipe> getRecipes() {
        // Renvoie une valeur à l'appelant
        return recipes.keySet();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Get the recipe display for the specified display id, optionally testing visibility against the given player.
     *
     * @param displayId the display id
     * @param player    the player to test visibility against, or null to ignore visibility
     * @return the recipe display, or null if not found or not visible
     */
    // Début d'une méthode/d'un bloc
    public @Nullable RecipeDisplay getRecipeDisplay(int displayId, @Nullable Player player) {
        // Appelle une méthode
        var recipeBookEntry = recipeBookEntryIdMap.get(displayId);
        // Embranchement : vérifie une condition
        if (recipeBookEntry == null || (player != null && !recipeBookEntry.getValue().test(player))) return null;

        // Renvoie une valeur à l'appelant
        return recipeBookEntry.getKey().display();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public SendablePacket getDeclareRecipesPacket() {
        // Renvoie une valeur à l'appelant
        return declareRecipesPacket;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@link RecipeBookAddPacket} which replaces the recipe book with the currently unlocked
     * recipes for this player.
     *
     * @param player the player to create the packet for
     * @return the recipe book add packet with replace set to true
     */
    // Début d'une méthode/d'un bloc
    public RecipeBookAddPacket createRecipeBookResetPacket(Player player) {
        // Affecte une valeur
        final List<RecipeBookAddPacket.Entry> entries = new ArrayList<>();
        // Boucle : répète un bloc
        for (final Map.Entry<Recipe, RecipeData> recipeEntry : recipes.entrySet()) {
            // Embranchement : vérifie une condition
            if (!recipeEntry.getValue().predicate.test(player)) continue;

            // Appelle une méthode
            entries.addAll(recipeEntry.getValue().displays);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new RecipeBookAddPacket(entries, true);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private DeclareRecipesPacket createDeclareRecipesPacket() {
        // Collect the item properties for the client
        // Affecte une valeur
        final Map<RecipeProperty, Set<Material>> itemProperties = new HashMap<>();
        // Boucle : répète un bloc
        for (var recipe : recipes.keySet()) {
            // Boucle : répète un bloc
            for (var entry : recipe.itemProperties().entrySet()) {
                // Appelle une méthode
                itemProperties.computeIfAbsent(entry.getKey(), k -> new HashSet<>()).addAll(entry.getValue());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        final Map<RecipeProperty, List<Material>> itemPropertiesLists = new HashMap<>();
        // Boucle : répète un bloc
        for (var entry : itemProperties.entrySet()) { // Sets to lists
            // Appelle une méthode
            itemPropertiesLists.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        // Fin d'un bloc/d'une expression
        }

        // Collect the stonecutter recipes for the client
        // Affecte une valeur
        final List<DeclareRecipesPacket.StonecutterRecipe> stonecutterRecipes = new ArrayList<>();
        // Boucle : répète un bloc
        for (var recipeBookEntry : recipeBookEntryIdMap.values()) {
            // Embranchement : vérifie une condition
            if (!(recipeBookEntry.getKey().display() instanceof RecipeDisplay.Stonecutter stonecutterDisplay))
                // Passe à l'itération suivante de la boucle
                continue;

            // Appelle une méthode
            final Ingredient input = Ingredient.fromSlotDisplay(stonecutterDisplay.ingredient());
            // Embranchement : vérifie une condition
            if (input == null) continue;

            // Appelle une méthode
            stonecutterRecipes.add(new DeclareRecipesPacket.StonecutterRecipe(input, stonecutterDisplay.result()));
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return new DeclareRecipesPacket(itemPropertiesLists, stonecutterRecipes);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
