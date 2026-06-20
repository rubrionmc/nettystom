// Package declaration for this file
package net.minestom.server.entity.attribute;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.kyori.adventure.translation.Translatable;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import net.minestom.server.registry.StaticProtocolObject;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;

// Type declaration (class/interface/enum/record)
public sealed interface Attribute extends StaticProtocolObject<Attribute>, Attributes,
        // Start of a method/block
        Translatable permits AttributeImpl {
    // Calls a method
    NetworkBuffer.Type<Attribute> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(Attribute::fromId, Attribute::id);
    // Calls a method
    Codec<Attribute> CODEC = Codec.STRING.transform(AttributeImpl::get, Attribute::name);

    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    RegistryData.AttributeEntry registry();

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Key key() {
        // Returns a value to the caller
        return registry().key();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default int id() {
        // Returns a value to the caller
        return registry().id();
    // End of a block/expression
    }

    // Start of a method/block
    default double defaultValue() {
        // Returns a value to the caller
        return registry().defaultValue();
    // End of a block/expression
    }

    // Start of a method/block
    default double minValue() {
        // Returns a value to the caller
        return registry().minValue();
    // End of a block/expression
    }

    // Start of a method/block
    default double maxValue() {
        // Returns a value to the caller
        return registry().maxValue();
    // End of a block/expression
    }

    // Start of a method/block
    default boolean isSynced() {
        // Returns a value to the caller
        return registry().clientSync();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default String translationKey() {
        // Returns a value to the caller
        return registry().translationKey();
    // End of a block/expression
    }

    // Start of a method/block
    static Collection<Attribute> values() {
        // Returns a value to the caller
        return AttributeImpl.REGISTRY.values();
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable Attribute fromKey(@KeyPattern String key) {
        // Returns a value to the caller
        return fromKey(Key.key(key));
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable Attribute fromKey(Key key) {
        // Returns a value to the caller
        return AttributeImpl.REGISTRY.get(key);
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable Attribute fromId(int id) {
        // Returns a value to the caller
        return AttributeImpl.REGISTRY.get(id);
    // End of a block/expression
    }

// End of a block/expression
}
