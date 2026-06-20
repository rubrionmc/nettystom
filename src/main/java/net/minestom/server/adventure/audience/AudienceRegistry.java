// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.audience;

// Import d'une classe nécessaire
import net.kyori.adventure.audience.Audience;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Keyed;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.function.Predicate;
// Import d'une classe nécessaire
import java.util.function.Supplier;

/**
 * Holder of custom audiences.
 */
// Déclaration de type (classe/interface/enum/record)
public class AudienceRegistry {

    // Instruction de code
    private final Map<Key, Collection<Audience>> registry;
    // Instruction de code
    private final Function<Key, Collection<Audience>> provider;

    /**
     * Creates a new audience registrar with a given backing map.
     *
     * @param backingMap        the backing map
     * @param backingCollection a provider for the backing collection
     */
    // Début d'une méthode/d'un bloc
    public AudienceRegistry(Map<Key, Collection<Audience>> backingMap, Supplier<Collection<Audience>> backingCollection) {
        // Accès à l'objet courant/parent
        this.registry = backingMap;
        // Accès à l'objet courant/parent
        this.provider = key -> backingCollection.get();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if this registry is empty.
     *
     * @return {@code true} if it is, {@code false} otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean isEmpty() {
        // Renvoie une valeur à l'appelant
        return this.registry.isEmpty();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds some audiences to the registry.
     *
     * @param keyed     the provider of the key
     * @param audiences the audiences
     */
    // Début d'une méthode/d'un bloc
    public void register(Keyed keyed, Audience... audiences) {
        // Accès à l'objet courant/parent
        this.register(keyed.key(), audiences);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds some audiences to the registry.
     *
     * @param keyed     the provider of the key
     * @param audiences the audiences
     */
    // Début d'une méthode/d'un bloc
    public void register(Keyed keyed, Collection<Audience> audiences) {
        // Accès à l'objet courant/parent
        this.register(keyed.key(), audiences);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds some audiences to the registry.
     *
     * @param key       the key to store the audiences under
     * @param audiences the audiences
     */
    // Début d'une méthode/d'un bloc
    public void register(Key key, Audience... audiences) {
        // Embranchement : vérifie une condition
        if (audiences == null || audiences.length == 0) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Accès à l'objet courant/parent
        this.register(key, Arrays.asList(audiences));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds some audiences to the registry.
     *
     * @param key       the key to store the audiences under
     * @param audiences the audiences
     */
    // Début d'une méthode/d'un bloc
    public void register(Key key, Collection<Audience> audiences) {
        // Embranchement : vérifie une condition
        if (!audiences.isEmpty()) {
            // Accès à l'objet courant/parent
            this.registry.computeIfAbsent(key, this.provider).addAll(audiences);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets every audience in the registry.
     *
     * @return an iterable containing every audience member
     */
    // Début d'une méthode/d'un bloc
    public Iterable<? extends Audience> all() {
        // Embranchement : vérifie une condition
        if (this.isEmpty()) {
            // Renvoie une valeur à l'appelant
            return List.of();
        // Branche alternative de la condition
        } else {
            // Renvoie une valeur à l'appelant
            return this.registry.values().stream().flatMap(Collection::stream).toList();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets every audience in the registry under a specific key.
     *
     * @param keyed the key provider
     * @return an iterable containing the audience members
     */
    // Début d'une méthode/d'un bloc
    public Iterable<? extends Audience> of(Keyed keyed) {
        // Renvoie une valeur à l'appelant
        return this.of(keyed.key());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets every audience in the registry under a specific key.
     *
     * @param key the key
     * @return an iterable containing the audience members
     */
    // Début d'une méthode/d'un bloc
    public Iterable<? extends Audience> of(Key key) {
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableCollection(this.registry.getOrDefault(key, this.provider.apply(null)));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets every audience member in the registry who matches a given predicate.
     *
     * @param filter the predicate
     * @return the matching audience members
     */
    // Début d'une méthode/d'un bloc
    public Iterable<? extends Audience> of(Predicate<Audience> filter) {
        // Renvoie une valeur à l'appelant
        return this.registry.values().stream().flatMap(Collection::stream).filter(filter).toList();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
