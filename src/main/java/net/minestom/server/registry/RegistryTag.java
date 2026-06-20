// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;
// Import of a required class
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
// Type declaration (class/interface/enum/record)
public sealed interface RegistryTag<T> extends HolderSet<T>, Iterable<RegistryKey<T>>
        // Start of a method/block
        permits RegistryTagImpl.Empty, RegistryTagImpl.Backed, RegistryTagImpl.Direct {

    // Start of a method/block
    static <T> NetworkBuffer.Type<RegistryTag<T>> networkType(Registries.Selector<T> selector) {
        // Returns a value to the caller
        return new RegistryNetworkTypes.RegistryTagImpl<>(selector);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> Codec<RegistryTag<T>> codec(Registries.Selector<T> selector) {
        // Returns a value to the caller
        return new RegistryCodecs.RegistryTagImpl<>(selector);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> RegistryTag<T> empty() {
        //noinspection unchecked
        // Returns a value to the caller
        return (RegistryTag<T>) RegistryTagImpl.Empty.INSTANCE;
    // End of a block/expression
    }

    // Annotation for the following element
    @SafeVarargs
    // Start of a method/block
    static <T> RegistryTag<T> direct(RegistryKey<T>... keys) {
        // Branch: checks a condition
        if (keys.length == 0) return empty();
        // Returns a value to the caller
        return new RegistryTagImpl.Direct<>(List.of(keys));
    // End of a block/expression
    }

    // Start of a method/block
    static <T> RegistryTag<T> direct(Collection<? extends RegistryKey<T>> values) {
        // Branch: checks a condition
        if (values.isEmpty()) return empty();
        // Returns a value to the caller
        return new RegistryTagImpl.Direct<>(List.copyOf(values));
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable TagKey<T> key();

    // Calls a method
    boolean contains(RegistryKey<T> value);

    // Calls a method
    int size();

// End of a block/expression
}
