// Déclaration du paquet de ce fichier
package net.minestom.server.game;


// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

/**
 * Represents a game event implementation.
 * Used for a wide variety of events, from weather to bed use to game mode to demo messages.
 */
// Déclaration de type (classe/interface/enum/record)
record GameEventImpl(RegistryData.GameEventEntry registry, Key key, int id) implements GameEvent {
    // Affecte une valeur
    static final Registry<GameEvent> REGISTRY = RegistryData.createStaticRegistry(
            // Appelle une méthode
            Key.key("game_event"), GameEventImpl::createImpl);

    /**
     * Creates a new {@link GameEventImpl} with the given namespace and properties.
     *
     * @param namespace  the namespace
     * @param properties the properties
     * @return a new {@link GameEventImpl}
     */
    // Début d'une méthode/d'un bloc
    private static GameEventImpl createImpl(String namespace, RegistryData.Properties properties) {
        // Renvoie une valeur à l'appelant
        return new GameEventImpl(RegistryData.gameEventEntry(namespace, properties));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new {@link GameEventImpl} with the given registry.
     *
     * @param registry the registry
     */
    // Début d'une méthode/d'un bloc
    private GameEventImpl(RegistryData.GameEventEntry registry) {
        // Appelle une méthode
        this(registry, registry.key(), registry.main().getInt("id"));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static @UnknownNullability GameEvent get(String key) {
        // Renvoie une valeur à l'appelant
        return REGISTRY.get(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}