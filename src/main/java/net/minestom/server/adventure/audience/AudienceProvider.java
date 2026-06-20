// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.audience;

// Import d'une classe nécessaire
import net.kyori.adventure.audience.Audience;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Keyed;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;

// Import d'une classe nécessaire
import java.util.function.Predicate;

/**
 * A generic provider of {@link Audience audiences} or some subtype.
 *
 * @param <A> the type that is provided
 */
// Déclaration de type (classe/interface/enum/record)
public interface AudienceProvider<A> {

    /**
     * Gets all audience members. This returns {@link #players()} combined with
     * {@link #customs()} and {@link #console()}. This can be a costly operation, so it
     * is often preferable to use {@link #server()} instead.
     *
     * @return all audience members
     */
    // Appelle une méthode
    A all();

    /**
     * Gets all audience members that are of type {@link Player}.
     *
     * @return all players
     */
    // Appelle une méthode
    A players();

    /**
     * Gets all audience members that are of type {@link Player} and match the predicate.
     *
     * @param filter the predicate
     * @return all players matching the predicate
     */
    // Appelle une méthode
    A players(Predicate<Player> filter);

    /**
     * Gets the console as an audience.
     *
     * @return the console
     */
    // Appelle une méthode
    A console();

    /**
     * Gets the combination of {@link #players()} and {@link #console()}.
     *
     * @return the audience of all players and the console
     */
    // Appelle une méthode
    A server();

    /**
     * Gets all custom audience members stored using the given keyed object.
     *
     * @param keyed the keyed object
     * @return all custom audience members stored using the key of the object
     */
    // Début d'une méthode/d'un bloc
    default A custom(Keyed keyed) {
        // Renvoie une valeur à l'appelant
        return this.custom(keyed.key());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all custom audience members stored using the given key.
     *
     * @param key the key
     * @return all custom audience members stored using the key
     */
    // Appelle une méthode
    A custom(Key key);

    /**
     * Gets all custom audience members stored using the given keyed object that match
     * the given predicate.
     *
     * @param keyed  the keyed object
     * @param filter the predicate
     * @return all custom audience members stored using the key
     */
    // Début d'une méthode/d'un bloc
    default A custom(Keyed keyed, Predicate<Audience> filter) {
        // Renvoie une valeur à l'appelant
        return this.custom(keyed.key(), filter);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all custom audience members stored using the given key that match the
     * given predicate.
     *
     * @param key    the key
     * @param filter the predicate
     * @return all custom audience members stored using the key
     */
    // Appelle une méthode
    A custom(Key key, Predicate<Audience> filter);

    /**
     * Gets all custom audience members.
     *
     * @return all custom audience members
     */
    // Appelle une méthode
    A customs();

    /**
     * Gets all custom audience members matching the given predicate.
     *
     * @param filter the predicate
     * @return all matching custom audience members
     */
    // Appelle une méthode
    A customs(Predicate<Audience> filter);

    /**
     * Gets all audience members that match the given predicate.
     *
     * @param filter the predicate
     * @return all matching audience members
     */
    // Appelle une méthode
    A all(Predicate<Audience> filter);

    /**
     * Gets the audience registry used to register custom audiences.
     *
     * @return the registry
     */
    // Appelle une méthode
    AudienceRegistry registry();
// Fin d'un bloc/d'une expression
}
