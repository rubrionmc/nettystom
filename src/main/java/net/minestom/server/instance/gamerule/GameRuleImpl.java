// Déclaration du paquet de ce fichier
package net.minestom.server.instance.gamerule;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
record GameRuleImpl<T>(Key key, int id, T defaultValue) implements GameRule<T> {
    // Appelle une méthode
    static final Registry<GameRule<?>> REGISTRY = RegistryData.createStaticRegistry(Key.key("game_rule"), GameRuleImpl::parse);

    // default is typed as String
    // Début d'une méthode/d'un bloc
    static GameRule<?> parse(@KeyPattern String namespace, RegistryData.Properties properties) {
        // Renvoie une valeur à l'appelant
        return switch (properties.getString("type")) {
            // Embranchement multiple (switch/case)
            case "boolean" ->
                    // Crée un nouvel objet
                    new GameRuleImpl<>(Key.key(namespace), properties.getInt("id"), Boolean.valueOf(properties.getString("default")));
            // Embranchement multiple (switch/case)
            case "integer" ->
                    // Crée un nouvel objet
                    new GameRuleImpl<>(Key.key(namespace), properties.getInt("id"), Integer.valueOf(properties.getString("default")));
            // Embranchement multiple (switch/case)
            default -> throw new IllegalArgumentException("Unknown game rule type: " + properties.getString("type"));
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    static <T> GameRule<T> get(@KeyPattern String key) {
        // Renvoie une valeur à l'appelant
        return (GameRule<T>) Objects.requireNonNull(REGISTRY.get(Key.key(key)));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
