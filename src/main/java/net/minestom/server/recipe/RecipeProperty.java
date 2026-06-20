// Déclaration du paquet de ce fichier
package net.minestom.server.recipe;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Keyed;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.stream.Collectors;

// Déclaration de type (classe/interface/enum/record)
public enum RecipeProperty implements Keyed {
    // Instruction de code
    SMITHING_BASE("smithing_base"),
    // Instruction de code
    SMITHING_TEMPLATE("smithing_template"),
    // Instruction de code
    SMITHING_ADDITION("smithing_addition"),
    // Instruction de code
    FURNACE_INPUT("furnace_input"),
    // Instruction de code
    BLAST_FURNACE_INPUT("blast_furnace_input"),
    // Instruction de code
    SMOKER_INPUT("smoker_input"),
    // Appelle une méthode
    CAMPFIRE_INPUT("campfire_input");

    // Affecte une valeur
    private static final Map<Key, RecipeProperty> BY_KEY = Arrays.stream(values())
            // Appelle une méthode
            .collect(Collectors.toMap(RecipeProperty::key, Function.identity()));

    // Affecte une valeur
    public static final NetworkBuffer.Type<RecipeProperty> NETWORK_TYPE = NetworkBuffer.STRING.transform(
            // Instruction de code
            key -> Objects.requireNonNull(fromKey(key)),
            // Appelle une méthode
            recipeProperty -> recipeProperty.key().asMinimalString());

    // Début d'une méthode/d'un bloc
    public static @Nullable RecipeProperty fromKey(String key) {
        // Renvoie une valeur à l'appelant
        return fromKey(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static @Nullable RecipeProperty fromKey(Key key) {
        // Renvoie une valeur à l'appelant
        return BY_KEY.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private final Key key;

    // Début d'une méthode/d'un bloc
    RecipeProperty(String id) {
        // Accès à l'objet courant/parent
        this.key = Key.key("minecraft", id);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Key key() {
        // Renvoie une valeur à l'appelant
        return key;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
