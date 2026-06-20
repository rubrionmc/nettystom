// Package declaration for this file
package net.minestom.server.recipe;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.Keyed;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.stream.Collectors;

// Type declaration (class/interface/enum/record)
public enum RecipeProperty implements Keyed {
    // Code statement
    SMITHING_BASE("smithing_base"),
    // Code statement
    SMITHING_TEMPLATE("smithing_template"),
    // Code statement
    SMITHING_ADDITION("smithing_addition"),
    // Code statement
    FURNACE_INPUT("furnace_input"),
    // Code statement
    BLAST_FURNACE_INPUT("blast_furnace_input"),
    // Code statement
    SMOKER_INPUT("smoker_input"),
    // Calls a method
    CAMPFIRE_INPUT("campfire_input");

    // Assigns a value
    private static final Map<Key, RecipeProperty> BY_KEY = Arrays.stream(values())
            // Calls a method
            .collect(Collectors.toMap(RecipeProperty::key, Function.identity()));

    // Assigns a value
    public static final NetworkBuffer.Type<RecipeProperty> NETWORK_TYPE = NetworkBuffer.STRING.transform(
            // Code statement
            key -> Objects.requireNonNull(fromKey(key)),
            // Calls a method
            recipeProperty -> recipeProperty.key().asMinimalString());

    // Start of a method/block
    public static @Nullable RecipeProperty fromKey(String key) {
        // Returns a value to the caller
        return fromKey(Key.key(key));
    // End of a block/expression
    }

    // Start of a method/block
    public static @Nullable RecipeProperty fromKey(Key key) {
        // Returns a value to the caller
        return BY_KEY.get(key);
    // End of a block/expression
    }

    // Code statement
    private final Key key;

    // Start of a method/block
    RecipeProperty(String id) {
        // Access to the current/parent object
        this.key = Key.key("minecraft", id);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Key key() {
        // Returns a value to the caller
        return key;
    // End of a block/expression
    }
// End of a block/expression
}
