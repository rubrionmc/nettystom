// Package declaration for this file
package net.minestom.server.adventure.audience;

// Import of a required class
import net.kyori.adventure.audience.Audience;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.Keyed;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.function.Predicate;
// Import of a required class
import java.util.function.Supplier;

/**
 * Holder of custom audiences.
 */
// Type declaration (class/interface/enum/record)
public class AudienceRegistry {

    // Code statement
    private final Map<Key, Collection<Audience>> registry;
    // Code statement
    private final Function<Key, Collection<Audience>> provider;

    /**
     * Creates a new audience registrar with a given backing map.
     *
     * @param backingMap        the backing map
     * @param backingCollection a provider for the backing collection
     */
    // Start of a method/block
    public AudienceRegistry(Map<Key, Collection<Audience>> backingMap, Supplier<Collection<Audience>> backingCollection) {
        // Access to the current/parent object
        this.registry = backingMap;
        // Access to the current/parent object
        this.provider = key -> backingCollection.get();
    // End of a block/expression
    }

    /**
     * Checks if this registry is empty.
     *
     * @return {@code true} if it is, {@code false} otherwise
     */
    // Start of a method/block
    public boolean isEmpty() {
        // Returns a value to the caller
        return this.registry.isEmpty();
    // End of a block/expression
    }

    /**
     * Adds some audiences to the registry.
     *
     * @param keyed     the provider of the key
     * @param audiences the audiences
     */
    // Start of a method/block
    public void register(Keyed keyed, Audience... audiences) {
        // Access to the current/parent object
        this.register(keyed.key(), audiences);
    // End of a block/expression
    }

    /**
     * Adds some audiences to the registry.
     *
     * @param keyed     the provider of the key
     * @param audiences the audiences
     */
    // Start of a method/block
    public void register(Keyed keyed, Collection<? extends Audience> audiences) {
        // Access to the current/parent object
        this.register(keyed.key(), audiences);
    // End of a block/expression
    }

    /**
     * Adds some audiences to the registry.
     *
     * @param key       the key to store the audiences under
     * @param audiences the audiences
     */
    // Start of a method/block
    public void register(Key key, Audience... audiences) {
        // Branch: checks a condition
        if (audiences == null || audiences.length == 0) {
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Access to the current/parent object
        this.register(key, Arrays.asList(audiences));
    // End of a block/expression
    }

    /**
     * Adds some audiences to the registry.
     *
     * @param key       the key to store the audiences under
     * @param audiences the audiences
     */
    // Start of a method/block
    public void register(Key key, Collection<? extends Audience> audiences) {
        // Branch: checks a condition
        if (!audiences.isEmpty()) {
            // Access to the current/parent object
            this.registry.computeIfAbsent(key, this.provider).addAll(audiences);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets every audience in the registry.
     *
     * @return an iterable containing every audience member
     */
    // Start of a method/block
    public Iterable<? extends Audience> all() {
        // Branch: checks a condition
        if (this.isEmpty()) {
            // Returns a value to the caller
            return List.of();
        // Alternative branch of the condition
        } else {
            // Returns a value to the caller
            return this.registry.values().stream().flatMap(Collection::stream).toList();
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets every audience in the registry under a specific key.
     *
     * @param keyed the key provider
     * @return an iterable containing the audience members
     */
    // Start of a method/block
    public Iterable<? extends Audience> of(Keyed keyed) {
        // Returns a value to the caller
        return this.of(keyed.key());
    // End of a block/expression
    }

    /**
     * Gets every audience in the registry under a specific key.
     *
     * @param key the key
     * @return an iterable containing the audience members
     */
    // Start of a method/block
    public Iterable<? extends Audience> of(Key key) {
        // Returns a value to the caller
        return Collections.unmodifiableCollection(this.registry.getOrDefault(key, this.provider.apply(null)));
    // End of a block/expression
    }

    /**
     * Gets every audience member in the registry who matches a given predicate.
     *
     * @param filter the predicate
     * @return the matching audience members
     */
    // Start of a method/block
    public Iterable<? extends Audience> of(Predicate<? super Audience> filter) {
        // Returns a value to the caller
        return this.registry.values().stream().flatMap(Collection::stream).filter(filter).toList();
    // End of a block/expression
    }
// End of a block/expression
}
