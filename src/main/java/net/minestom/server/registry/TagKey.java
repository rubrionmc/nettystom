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

// Type declaration (class/interface/enum/record)
public sealed interface TagKey<T> extends Keyed permits TagKeyImpl {
    // Start of a method/block
    static <T> Codec<TagKey<T>> codec(Registries.Selector<T> selector) {
        // Returns a value to the caller
        return new RegistryCodecs.TagKeyImpl<>(selector, false);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> Codec<TagKey<T>> hashCodec(Registries.Selector<T> selector) {
        // Returns a value to the caller
        return new RegistryCodecs.TagKeyImpl<>(selector, true);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> NetworkBuffer.Type<TagKey<T>> networkType(Registries.Selector<T> selector) {
        // Returns a value to the caller
        return NetworkBuffer.KEY.transform(TagKeyImpl::new, TagKey::key);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> TagKey<T> ofHash(String hashedKey) {
        // Branch: checks a condition
        if (!hashedKey.startsWith("#"))
            // Throws an exception
            throw new IllegalArgumentException("Hashed key must start with '#': " + hashedKey);
        // Returns a value to the caller
        return new TagKeyImpl<>(Key.key(hashedKey.substring(1)));
    // End of a block/expression
    }

    // Start of a method/block
    default String hashedKey() {
        // Returns a value to the caller
        return "#" + key().asString();
    // End of a block/expression
    }


// End of a block/expression
}
