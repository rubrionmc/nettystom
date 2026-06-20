// Package declaration for this file
package net.minestom.server.recipe;

// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.network.packet.server.CachedPacket;
// Import of a required class
import net.minestom.server.network.packet.server.SendablePacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.DeclareRecipesPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.RecipeBookAddPacket;
// Import of a required class
import net.minestom.server.recipe.display.RecipeDisplay;
// Import of a required class
import net.minestom.server.recipe.display.SlotDisplay;
// Import of a required class
import net.minestom.server.registry.RegistryTag;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;
// Import of a required class
import java.util.function.Predicate;

// Type declaration (class/interface/enum/record)
public final class RecipeManager {
    // Calls a method
    private static final AtomicInteger NEXT_DISPLAY_ID = new AtomicInteger();

    // Type declaration (class/interface/enum/record)
    private record RecipeData(
            // Code statement
            Recipe recipe,
            // Code statement
            List<RecipeBookAddPacket.Entry> displays,
            // Code statement
            Predicate<Player> predicate
    // Start of a method/block
    ) {
    // End of a block/expression
    }

    // Calls a method
    private final CachedPacket declareRecipesPacket = new CachedPacket(this::createDeclareRecipesPacket);

    // Calls a method
    private final Map<Recipe, RecipeData> recipes = new ConcurrentHashMap<>();
    // Code statement
    private final Int2ObjectMap<Map.Entry<RecipeBookAddPacket.Entry, Predicate<Player>>> recipeBookEntryIdMap =
            // Calls a method
            Int2ObjectMaps.synchronize(new Int2ObjectArrayMap<>());

    // Start of a method/block
    public void addRecipe(Recipe recipe) {
        // Calls a method
        addRecipe(recipe, player -> true);
    // End of a block/expression
    }

    // Start of a method/block
    public void addRecipe(Recipe recipe, Predicate<Player> predicate) {
        // Calls a method
        List<RecipeBookAddPacket.Entry> recipeBookEntries = new ArrayList<>();
        // Calls a method
        final RecipeBookCategory recipeBookCategory = recipe.recipeBookCategory();
        // Branch: checks a condition
        if (recipeBookCategory != null) {
            // Loop: repeats a block
            for (var display : recipe.createRecipeDisplays()) {
                // Calls a method
                int displayId = NEXT_DISPLAY_ID.getAndIncrement();
                // Code statement
                recipeBookEntries.add(new RecipeBookAddPacket.Entry( //todo groups
                        // Code statement
                        displayId, display, null, recipeBookCategory,
                        // Code statement
                        recipe.craftingRequirements(), false, false
                // Code statement
                ));
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        var existingRecipe = recipes.putIfAbsent(recipe, new RecipeData(recipe, recipeBookEntries, predicate));
        // Calls a method
        Check.argCondition(existingRecipe != null, "Recipe is already registered: " + recipe);
        // Loop: repeats a block
        for (RecipeBookAddPacket.Entry entry : recipeBookEntries) {
            // Calls a method
            recipeBookEntryIdMap.put(entry.displayId(), Map.entry(entry, predicate));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public void removeRecipe(Recipe recipe) {
        // Calls a method
        final RecipeData removed = recipes.remove(recipe);
        // Branch: checks a condition
        if (removed != null) {
            // Loop: repeats a block
            for (var entry : removed.displays) {
                // Calls a method
                recipeBookEntryIdMap.remove(entry.displayId());
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public Set<Recipe> getRecipes() {
        // Returns a value to the caller
        return recipes.keySet();
    // End of a block/expression
    }

    /**
     * Get the recipe display for the specified display id, optionally testing visibility against the given player.
     *
     * @param displayId the display id
     * @param player    the player to test visibility against, or null to ignore visibility
     * @return the recipe display, or null if not found or not visible
     */
    // Start of a method/block
    public @Nullable RecipeDisplay getRecipeDisplay(int displayId, @Nullable Player player) {
        // Calls a method
        var recipeBookEntry = recipeBookEntryIdMap.get(displayId);
        // Branch: checks a condition
        if (recipeBookEntry == null || (player != null && !recipeBookEntry.getValue().test(player))) return null;

        // Returns a value to the caller
        return recipeBookEntry.getKey().display();
    // End of a block/expression
    }

    // Start of a method/block
    public SendablePacket getDeclareRecipesPacket() {
        // Returns a value to the caller
        return declareRecipesPacket;
    // End of a block/expression
    }

    /**
     * Creates a {@link RecipeBookAddPacket} which replaces the recipe book with the currently unlocked
     * recipes for this player.
     *
     * @param player the player to create the packet for
     * @return the recipe book add packet with replace set to true
     */
    // Start of a method/block
    public RecipeBookAddPacket createRecipeBookResetPacket(Player player) {
        // Calls a method
        final List<RecipeBookAddPacket.Entry> entries = new ArrayList<>();
        // Loop: repeats a block
        for (final Map.Entry<Recipe, RecipeData> recipeEntry : recipes.entrySet()) {
            // Branch: checks a condition
            if (!recipeEntry.getValue().predicate.test(player)) continue;

            // Calls a method
            entries.addAll(recipeEntry.getValue().displays);
        // End of a block/expression
        }
        // Returns a value to the caller
        return new RecipeBookAddPacket(entries, true);
    // End of a block/expression
    }

    // Start of a method/block
    private DeclareRecipesPacket createDeclareRecipesPacket() {
        // Collect the item properties for the client
        // Calls a method
        final Map<RecipeProperty, Set<Material>> itemProperties = new HashMap<>();
        // Loop: repeats a block
        for (var recipe : recipes.keySet()) {
            // Loop: repeats a block
            for (var entry : recipe.itemProperties().entrySet()) {
                // Calls a method
                itemProperties.computeIfAbsent(entry.getKey(), k -> new HashSet<>()).addAll(entry.getValue());
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        final Map<RecipeProperty, List<Material>> itemPropertiesLists = new HashMap<>();
        // Loop: repeats a block
        for (var entry : itemProperties.entrySet()) { // Sets to lists
            // Calls a method
            itemPropertiesLists.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        // End of a block/expression
        }

        // Collect the stonecutter recipes for the client
        // Calls a method
        final List<DeclareRecipesPacket.StonecutterRecipe> stonecutterRecipes = new ArrayList<>();
        // Loop: repeats a block
        for (var recipeBookEntry : recipeBookEntryIdMap.values()) {
            // Branch: checks a condition
            if (!(recipeBookEntry.getKey().display() instanceof RecipeDisplay.Stonecutter stonecutterDisplay))
                // Continues to the next loop iteration
                continue;

            // Calls a method
            final Ingredient input = ingredientFromSlotDisplay(stonecutterDisplay.ingredient());
            // Branch: checks a condition
            if (input == null) continue;

            // Calls a method
            stonecutterRecipes.add(new DeclareRecipesPacket.StonecutterRecipe(input, stonecutterDisplay.result()));
        // End of a block/expression
        }

        // Returns a value to the caller
        return new DeclareRecipesPacket(itemPropertiesLists, stonecutterRecipes);
    // End of a block/expression
    }

    // Start of a method/block
    private static @Nullable Ingredient ingredientFromSlotDisplay(SlotDisplay slotDisplay) {
        // Returns a value to the caller
        return switch (slotDisplay) {
            // Multiple branching (switch/case)
            case SlotDisplay.Item item -> new Ingredient(item.material());
            // Multiple branching (switch/case)
            case SlotDisplay.Tag tag -> {
                // Calls a method
                final RegistryTag<Material> tagValue = Material.staticRegistry().getTag(tag.tag());
                // Calls a method
                yield tagValue != null ? new Ingredient(tagValue) : null;
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            default -> null;
        // End of a block/expression
        };
    // End of a block/expression
    }

// End of a block/expression
}
