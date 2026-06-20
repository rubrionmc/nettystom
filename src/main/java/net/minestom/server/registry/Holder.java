// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.utils.Either;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/**
 * <p>Represents either a reference to a registry entry {@link RegistryKey} or a direct registry value.</p>
 *
 * <p>Whether registry values implement this type depends on client support for direct values.</p>
 *
 * @param <T> the type of the registry entry
 */
// Type declaration (class/interface/enum/record)
public sealed interface Holder<T> permits RegistryKey, Holder.Direct {

    // Annotation for the following element
    @ApiStatus.NonExtendable
    // Type declaration (class/interface/enum/record)
    non-sealed interface Direct<T> extends Holder<T> {
    // End of a block/expression
    }

    // Code statement
    static <T extends Holder<T>> NetworkBuffer.Type<Holder<T>> networkType(
            // Code statement
            Registries.Selector<T> selector,
            // Code statement
            NetworkBuffer.Type<T> registryNetworkType
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new RegistryNetworkTypes.HolderNetworkTypeImpl<>(selector, registryNetworkType);
    // End of a block/expression
    }

    // Code statement
    static <T extends Holder<T>> Codec<Holder<T>> codec(
            // Code statement
            Registries.Selector<T> selector,
            // Code statement
            Codec<T> registryCodec
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new RegistryCodecs.HolderCodec<>(selector, registryCodec);
    // End of a block/expression
    }

    // Start of a method/block
    default boolean isDirect() {
        // Returns a value to the caller
        return !(this instanceof RegistryKey<T>);
    // End of a block/expression
    }

    // Start of a method/block
    default @Nullable RegistryKey<T> asKey() {
        // Returns a value to the caller
        return this instanceof RegistryKey<T> ? (RegistryKey<T>) this : null;
    // End of a block/expression
    }

    // Start of a method/block
    default @Nullable T asValue() {
        //noinspection unchecked
        // Returns a value to the caller
        return this instanceof RegistryKey<T> ? null : (T) this;
    // End of a block/expression
    }

    // Start of a method/block
    default Either<RegistryKey<T>, T> unwrap() {
        // Branch: checks a condition
        if (this instanceof RegistryKey<T> key) {
            // Returns a value to the caller
            return Either.left(key);
        // Alternative branch of the condition
        } else {
            //noinspection unchecked
            // Returns a value to the caller
            return Either.right((T) this);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    default @Nullable T resolve(DynamicRegistry<T> registry) {
        // Calls a method
        final var key = asKey();
        // Branch: checks a condition
        if (key != null) {
            // Returns a value to the caller
            return registry.get(key);
        // Alternative branch of the condition
        } else {
            //noinspection unchecked
            // Returns a value to the caller
            return (T) this;
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
