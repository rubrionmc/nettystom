// Package declaration for this file
package net.minestom.server.instance.gamerule;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.registry.RegistryData;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
record GameRuleImpl<T>(Key key, int id, T defaultValue) implements GameRule<T> {
    // Calls a method
    static final Registry<GameRule<?>> REGISTRY = RegistryData.createStaticRegistry(Key.key("game_rule"), GameRuleImpl::parse);

    // default is typed as String
    // Start of a method/block
    static GameRule<?> parse(@KeyPattern String namespace, RegistryData.Properties properties) {
        // Returns a value to the caller
        return switch (properties.getString("type")) {
            // Multiple branching (switch/case)
            case "boolean" ->
                    // Creates a new object
                    new GameRuleImpl<>(Key.key(namespace), properties.getInt("id"), Boolean.valueOf(properties.getString("default")));
            // Multiple branching (switch/case)
            case "integer" ->
                    // Creates a new object
                    new GameRuleImpl<>(Key.key(namespace), properties.getInt("id"), Integer.valueOf(properties.getString("default")));
            // Multiple branching (switch/case)
            default -> throw new IllegalArgumentException("Unknown game rule type: " + properties.getString("type"));
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    static <T> GameRule<T> get(@KeyPattern String key) {
        // Returns a value to the caller
        return (GameRule<T>) Objects.requireNonNull(REGISTRY.get(Key.key(key)));
    // End of a block/expression
    }
// End of a block/expression
}
