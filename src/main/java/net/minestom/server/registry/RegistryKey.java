// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Keyed;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a reference to a {@link Registry} entry.
 *
 * @param <T> the type of the registry entry
 */
// Annotation pour l'élément suivant
@ApiStatus.NonExtendable
// Déclaration de type (classe/interface/enum/record)
public non-sealed interface RegistryKey<T> extends Holder<T>, Keyed {

    // Début d'une méthode/d'un bloc
    static <T> NetworkBuffer.Type<RegistryKey<T>> networkType(Registries.Selector<T> selector) {
        // Renvoie une valeur à l'appelant
        return new RegistryNetworkTypes.RegistryKeyImpl<>(selector);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> Codec<RegistryKey<T>> codec(Registries.Selector<T> selector) {
        // Renvoie une valeur à l'appelant
        return new RegistryCodecs.RegistryKeyImpl<>(selector);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> NetworkBuffer.Type<RegistryKey<T>> uncheckedNetworkType() {
        // Renvoie une valeur à l'appelant
        return NetworkBuffer.KEY.transform(RegistryKeyImpl::new, RegistryKey::key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> Codec<RegistryKey<T>> uncheckedCodec() {
        // Renvoie une valeur à l'appelant
        return Codec.KEY.transform(RegistryKeyImpl::new, RegistryKey::key);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new {@link RegistryKey} from the given raw string. Should not be used externally.
     * Registry keys are returned from {@link DynamicRegistry#register(Key, Object)}.
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static <T> RegistryKey<T> unsafeOf(String key) {
        // Renvoie une valeur à l'appelant
        return unsafeOf(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new {@link RegistryKey} from the given raw string. Should not be used externally.
     * Registry keys are returned from {@link DynamicRegistry#register(Key, Object)}.
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static <T> RegistryKey<T> unsafeOf(Key key) {
        // Renvoie une valeur à l'appelant
        return new RegistryKeyImpl<>(key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default String name() {
        // Renvoie une valeur à l'appelant
        return key().asString();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
