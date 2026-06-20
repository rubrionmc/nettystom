// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.Keyed;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a reference to a {@link Registry} entry.
 *
 * @param <T> the type of the registry entry
 */
// Annotation for the following element
@ApiStatus.NonExtendable
// Type declaration (class/interface/enum/record)
public non-sealed interface RegistryKey<T> extends Holder<T>, Keyed {

    // Start of a method/block
    static <T> NetworkBuffer.Type<RegistryKey<T>> networkType(Registries.Selector<T> selector) {
        // Returns a value to the caller
        return new RegistryNetworkTypes.RegistryKeyImpl<>(selector);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> Codec<RegistryKey<T>> codec(Registries.Selector<T> selector) {
        // Returns a value to the caller
        return new RegistryCodecs.RegistryKeyImpl<>(selector);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> NetworkBuffer.Type<RegistryKey<T>> uncheckedNetworkType() {
        // Returns a value to the caller
        return NetworkBuffer.KEY.transform(RegistryKeyImpl::new, RegistryKey::key);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> Codec<RegistryKey<T>> uncheckedCodec() {
        // Returns a value to the caller
        return Codec.KEY.transform(RegistryKeyImpl::new, RegistryKey::key);
    // End of a block/expression
    }

    /**
     * Creates a new {@link RegistryKey} from the given raw string. Should not be used externally.
     * Registry keys are returned from {@link DynamicRegistry#register(Key, Object)}.
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static <T> RegistryKey<T> unsafeOf(String key) {
        // Returns a value to the caller
        return unsafeOf(Key.key(key));
    // End of a block/expression
    }

    /**
     * Creates a new {@link RegistryKey} from the given raw string. Should not be used externally.
     * Registry keys are returned from {@link DynamicRegistry#register(Key, Object)}.
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static <T> RegistryKey<T> unsafeOf(Key key) {
        // Returns a value to the caller
        return new RegistryKeyImpl<>(key);
    // End of a block/expression
    }

    // Start of a method/block
    default String name() {
        // Returns a value to the caller
        return key().asString();
    // End of a block/expression
    }

// End of a block/expression
}
