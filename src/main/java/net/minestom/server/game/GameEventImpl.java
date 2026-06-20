// Package declaration for this file
package net.minestom.server.game;


// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

/**
 * Represents a game event implementation.
 * Used for a wide variety of events, from weather to bed use to game mode to demo messages.
 */
// Type declaration (class/interface/enum/record)
record GameEventImpl(RegistryData.GameEventEntry registry, Key key, int id) implements GameEvent {
    // Assigns a value
    static final Registry<GameEvent> REGISTRY = RegistryData.createStaticRegistry(
            // Calls a method
            Key.key("game_event"), GameEventImpl::createImpl);

    /**
     * Creates a new {@link GameEventImpl} with the given namespace and properties.
     *
     * @param namespace  the namespace
     * @param properties the properties
     * @return a new {@link GameEventImpl}
     */
    // Start of a method/block
    private static GameEventImpl createImpl(String namespace, RegistryData.Properties properties) {
        // Returns a value to the caller
        return new GameEventImpl(RegistryData.gameEventEntry(namespace, properties));
    // End of a block/expression
    }

    /**
     * Creates a new {@link GameEventImpl} with the given registry.
     *
     * @param registry the registry
     */
    // Start of a method/block
    private GameEventImpl(RegistryData.GameEventEntry registry) {
        // Calls a method
        this(registry, registry.key(), registry.main().getInt("id"));
    // End of a block/expression
    }

    // Start of a method/block
    public static @UnknownNullability GameEvent get(String key) {
        // Returns a value to the caller
        return REGISTRY.get(Key.key(key));
    // End of a block/expression
    }

// End of a block/expression
}