// Package declaration for this file
package net.minestom.server.adventure.audience;

// Import of a required class
import net.kyori.adventure.audience.Audience;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.Keyed;
// Import of a required class
import net.minestom.server.entity.Player;

// Import of a required class
import java.util.function.Predicate;

/**
 * A generic provider of {@link Audience audiences} or some subtype.
 *
 * @param <A> the type that is provided
 */
// Type declaration (class/interface/enum/record)
public interface AudienceProvider<A> {

    /**
     * Gets all audience members. This returns {@link #players()} combined with
     * {@link #customs()} and {@link #console()}. This can be a costly operation, so it
     * is often preferable to use {@link #server()} instead.
     *
     * @return all audience members
     */
    // Calls a method
    A all();

    /**
     * Gets all audience members that are of type {@link Player}.
     *
     * @return all players
     */
    // Calls a method
    A players();

    /**
     * Gets all audience members that are of type {@link Player} and match the predicate.
     *
     * @param filter the predicate
     * @return all players matching the predicate
     */
    // Calls a method
    A players(Predicate<? super Player> filter);

    /**
     * Gets the console as an audience.
     *
     * @return the console
     */
    // Calls a method
    A console();

    /**
     * Gets the combination of {@link #players()} and {@link #console()}.
     *
     * @return the audience of all players and the console
     */
    // Calls a method
    A server();

    /**
     * Gets all custom audience members stored using the given keyed object.
     *
     * @param keyed the keyed object
     * @return all custom audience members stored using the key of the object
     */
    // Start of a method/block
    default A custom(Keyed keyed) {
        // Returns a value to the caller
        return this.custom(keyed.key());
    // End of a block/expression
    }

    /**
     * Gets all custom audience members stored using the given key.
     *
     * @param key the key
     * @return all custom audience members stored using the key
     */
    // Calls a method
    A custom(Key key);

    /**
     * Gets all custom audience members stored using the given keyed object that match
     * the given predicate.
     *
     * @param keyed  the keyed object
     * @param filter the predicate
     * @return all custom audience members stored using the key
     */
    // Start of a method/block
    default A custom(Keyed keyed, Predicate<? super Audience> filter) {
        // Returns a value to the caller
        return this.custom(keyed.key(), filter);
    // End of a block/expression
    }

    /**
     * Gets all custom audience members stored using the given key that match the
     * given predicate.
     *
     * @param key    the key
     * @param filter the predicate
     * @return all custom audience members stored using the key
     */
    // Calls a method
    A custom(Key key, Predicate<? super Audience> filter);

    /**
     * Gets all custom audience members.
     *
     * @return all custom audience members
     */
    // Calls a method
    A customs();

    /**
     * Gets all custom audience members matching the given predicate.
     *
     * @param filter the predicate
     * @return all matching custom audience members
     */
    // Calls a method
    A customs(Predicate<? super Audience> filter);

    /**
     * Gets all audience members that match the given predicate.
     *
     * @param filter the predicate
     * @return all matching audience members
     */
    // Calls a method
    A all(Predicate<? super Audience> filter);

    /**
     * Gets the audience registry used to register custom audiences.
     *
     * @return the registry
     */
    // Calls a method
    AudienceRegistry registry();
// End of a block/expression
}
