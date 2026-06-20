// Package declaration for this file
package net.minestom.server.game;


// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import net.minestom.server.registry.StaticProtocolObject;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;

/**
 * Represents a game event.
 * Used for a wide variety of events, from weather to bed use to game mode to demo messages.
 */
// Type declaration (class/interface/enum/record)
public sealed interface GameEvent extends StaticProtocolObject<GameEvent>, GameEvents permits GameEventImpl {

    // Calls a method
    NetworkBuffer.Type<GameEvent> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(GameEvent::fromId, GameEvent::id);

    /**
     * Returns the game event registry.
     *
     * @return the game event registry or null if not found
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Annotation for the following element
    @Nullable
    // Calls a method
    RegistryData.GameEventEntry registry();

    // Start of a method/block
    static Collection<GameEvent> values() {
        // Returns a value to the caller
        return GameEventImpl.REGISTRY.values();
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable GameEvent fromKey(@KeyPattern String key) {
        // Returns a value to the caller
        return fromKey(Key.key(key));
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable GameEvent fromKey(Key key) {
        // Returns a value to the caller
        return GameEventImpl.REGISTRY.get(key);
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable GameEvent fromId(int id) {
        // Returns a value to the caller
        return GameEventImpl.REGISTRY.get(id);
    // End of a block/expression
    }

    // Start of a method/block
    static Registry<GameEvent> staticRegistry() {
        // Returns a value to the caller
        return GameEventImpl.REGISTRY;
    // End of a block/expression
    }

// End of a block/expression
}
