// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * <p>Represents either a reference to a registry entry {@link RegistryKey} or a direct registry value.</p>
 *
 * <p>Whether registry values implement this type depends on client support for direct values.</p>
 *
 * @param <T> the type of the registry entry
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface Holder<T> permits RegistryKey, Holder.Direct {

    // Annotation pour l'élément suivant
    @ApiStatus.NonExtendable
    // Déclaration de type (classe/interface/enum/record)
    non-sealed interface Direct<T> extends Holder<T> {
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    static <T extends Holder<T>> NetworkBuffer.Type<Holder<T>> networkType(
            // Instruction de code
            Registries.Selector<T> selector,
            // Instruction de code
            NetworkBuffer.Type<T> registryNetworkType
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new RegistryNetworkTypes.HolderNetworkTypeImpl<>(selector, registryNetworkType);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    static <T extends Holder<T>> Codec<Holder<T>> codec(
            // Instruction de code
            Registries.Selector<T> selector,
            // Instruction de code
            Codec<T> registryCodec
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new RegistryCodecs.HolderCodec<>(selector, registryCodec);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default boolean isDirect() {
        // Renvoie une valeur à l'appelant
        return !(this instanceof RegistryKey<T>);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default @Nullable RegistryKey<T> asKey() {
        // Renvoie une valeur à l'appelant
        return this instanceof RegistryKey<T> ? (RegistryKey<T>) this : null;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default @Nullable T asValue() {
        //noinspection unchecked
        // Renvoie une valeur à l'appelant
        return this instanceof RegistryKey<T> ? null : (T) this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Either<RegistryKey<T>, T> unwrap() {
        // Embranchement : vérifie une condition
        if (this instanceof RegistryKey<T> key) {
            // Renvoie une valeur à l'appelant
            return Either.left(key);
        // Branche alternative de la condition
        } else {
            //noinspection unchecked
            // Renvoie une valeur à l'appelant
            return Either.right((T) this);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default @Nullable T resolve(DynamicRegistry<T> registry) {
        // Appelle une méthode
        final var key = asKey();
        // Embranchement : vérifie une condition
        if (key != null) {
            // Renvoie une valeur à l'appelant
            return registry.get(key);
        // Branche alternative de la condition
        } else {
            //noinspection unchecked
            // Renvoie une valeur à l'appelant
            return (T) this;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
