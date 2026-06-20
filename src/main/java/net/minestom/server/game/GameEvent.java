// Déclaration du paquet de ce fichier
package net.minestom.server.game;


// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import net.minestom.server.registry.StaticProtocolObject;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;

/**
 * Represents a game event.
 * Used for a wide variety of events, from weather to bed use to game mode to demo messages.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface GameEvent extends StaticProtocolObject<GameEvent>, GameEvents permits GameEventImpl {

    // Appelle une méthode
    NetworkBuffer.Type<GameEvent> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(GameEvent::fromId, GameEvent::id);

    /**
     * Returns the game event registry.
     *
     * @return the game event registry or null if not found
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Annotation pour l'élément suivant
    @Nullable
    // Appelle une méthode
    RegistryData.GameEventEntry registry();

    // Début d'une méthode/d'un bloc
    static Collection<GameEvent> values() {
        // Renvoie une valeur à l'appelant
        return GameEventImpl.REGISTRY.values();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable GameEvent fromKey(@KeyPattern String key) {
        // Renvoie une valeur à l'appelant
        return fromKey(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable GameEvent fromKey(Key key) {
        // Renvoie une valeur à l'appelant
        return GameEventImpl.REGISTRY.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable GameEvent fromId(int id) {
        // Renvoie une valeur à l'appelant
        return GameEventImpl.REGISTRY.get(id);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Registry<GameEvent> staticRegistry() {
        // Renvoie une valeur à l'appelant
        return GameEventImpl.REGISTRY;
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
