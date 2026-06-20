// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;

/**
 * {@link RegistryTag} is a collection of keys from a particular registry.
 *
 * <p>The collection may be backed by a registry (synced, referenced by {@link TagKey}), or direct (list backed).</p>
 *
 * <p>Note that all elements of a direct tag must still be members of the registry.</p>
 *
 * @param <T> The type of the registry object.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface RegistryTag<T> extends HolderSet<T>, Iterable<RegistryKey<T>>
        // Début d'une méthode/d'un bloc
        permits RegistryTagImpl.Empty, RegistryTagImpl.Backed, RegistryTagImpl.Direct {

    // Début d'une méthode/d'un bloc
    static <T> NetworkBuffer.Type<RegistryTag<T>> networkType(Registries.Selector<T> selector) {
        // Renvoie une valeur à l'appelant
        return new RegistryNetworkTypes.RegistryTagImpl<>(selector);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> Codec<RegistryTag<T>> codec(Registries.Selector<T> selector) {
        // Renvoie une valeur à l'appelant
        return new RegistryCodecs.RegistryTagImpl<>(selector);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> RegistryTag<T> empty() {
        //noinspection unchecked
        // Renvoie une valeur à l'appelant
        return (RegistryTag<T>) RegistryTagImpl.Empty.INSTANCE;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SafeVarargs
    // Début d'une méthode/d'un bloc
    static <T> RegistryTag<T> direct(RegistryKey<T>... keys) {
        // Embranchement : vérifie une condition
        if (keys.length == 0) return empty();
        // Renvoie une valeur à l'appelant
        return new RegistryTagImpl.Direct<>(List.of(keys));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> RegistryTag<T> direct(Collection<? extends RegistryKey<T>> values) {
        // Embranchement : vérifie une condition
        if (values.isEmpty()) return empty();
        // Renvoie une valeur à l'appelant
        return new RegistryTagImpl.Direct<>(List.copyOf(values));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable TagKey<T> key();

    // Appelle une méthode
    boolean contains(RegistryKey<T> value);

    // Appelle une méthode
    int size();

// Fin d'un bloc/d'une expression
}
