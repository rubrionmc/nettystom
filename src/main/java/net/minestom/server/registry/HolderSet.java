// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.minestom.server.codec.Codec;

// Import of a required class
import java.util.Iterator;
// Import of a required class
import java.util.List;

/**
 * A HolderSet is either a registry tag or a list of direct holders. Mixing is not allowed.
 */
// Type declaration (class/interface/enum/record)
public sealed interface HolderSet<T> permits HolderSet.Direct, RegistryTag {

    // Code statement
    static <T extends Holder<T>> Codec<HolderSet<T>> codec(
            // Code statement
            Registries.Selector<T> selector,
            // Code statement
            Codec<T> registryCodec
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new RegistryCodecs.HolderSetImpl<>(RegistryTag.codec(selector), registryCodec);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Direct<T extends Holder.Direct<T>>(List<T> values) implements HolderSet<T>, Iterable<T> {
        // Start of a method/block
        public Direct {
            // Calls a method
            values = List.copyOf(values);
        // End of a block/expression
        }

        // Annotation for the following element
        @SafeVarargs
        // Start of a method/block
        public Direct(T... values) {
            // Calls a method
            this(List.of(values));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Iterator<T> iterator() {
            // Returns a value to the caller
            return values.iterator();
        // End of a block/expression
        }

    // End of a block/expression
    }

// End of a block/expression
}
