// Package declaration for this file
package net.minestom.server.component;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.function.UnaryOperator;

// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.Decoder;
// Import of a required class
import net.minestom.server.codec.Encoder;
// Import of a required class
import net.minestom.server.item.enchant.EffectComponent;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.StaticProtocolObject;

/**
 * A common type to represent all forms of component in the game. Each group of component types has its own declaration
 * file (see {@link net.minestom.server.component.DataComponent} for example).
 *
 * @param <T> The value type of the component
 * @see net.minestom.server.component.DataComponent
 * @see EffectComponent
 */
// Type declaration (class/interface/enum/record)
public sealed interface DataComponent<T> extends StaticProtocolObject<DataComponent<T>>, Encoder<T>, Decoder<T> permits DataComponentImpl {

    // Calls a method
    NetworkBuffer.Type<DataComponent<?>> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(DataComponent::fromId, DataComponent::id);
    // Calls a method
    Codec<DataComponent<?>> CODEC = Codec.STRING.transform(DataComponent::fromKey, DataComponent::name);

    // Calls a method
    NetworkBuffer.Type<DataComponentMap> MAP_NETWORK_TYPE = DataComponentMap.networkType(DataComponent::fromId);
    // Calls a method
    Codec<DataComponentMap> MAP_NBT_TYPE = DataComponentMap.codec(DataComponent::fromId, DataComponent::fromKey);

    // Calls a method
    NetworkBuffer.Type<DataComponentMap> PATCH_NETWORK_TYPE = DataComponentMap.patchNetworkType(DataComponent::fromId, true);
    // Calls a method
    NetworkBuffer.Type<DataComponentMap> UNTRUSTED_PATCH_NETWORK_TYPE = DataComponentMap.patchNetworkType(DataComponent::fromId, false);
    // Calls a method
    Codec<DataComponentMap> PATCH_CODEC = DataComponentMap.patchCodec(DataComponent::fromId, DataComponent::fromKey);

    /**
     * Represents any type which can hold data components. Represents a finalized view of a component, that is to say
     * an implementation may represent a patch on top of another Holder, however the return values of this type
     * will always represent the merged view.
     */
    // Type declaration (class/interface/enum/record)
    interface Holder {
        // Start of a method/block
        default boolean has(DataComponent<?> component) {
            // Returns a value to the caller
            return get(component) != null;
        // End of a block/expression
        }

        // Calls a method
        <T> @Nullable T get(DataComponent<T> component);

        // Start of a method/block
        default <T> T get(DataComponent<T> component, T defaultValue) {
            // Calls a method
            final T value = get(component);
            // Returns a value to the caller
            return value != null ? value : defaultValue;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Value(DataComponent<?> component, @Nullable Object value) {
    // End of a block/expression
    }

    // Calls a method
    boolean isSynced();
    // Calls a method
    boolean isSerialized();
    // Annotation for the following element
    @Nullable NetworkBuffer.Type<T> networkType();
    // Annotation for the following element
    @Nullable Codec<T> codec();

    // Calls a method
    T read(NetworkBuffer reader);
    // Calls a method
    void write(NetworkBuffer writer, T value);

    /**
     * Freezes the given value if possible. For example, collections should be frozen.
     * <br>
     * Note: Only {@link T} itself is required to be frozen, the objects inside {@link T} should be immutable.
     *
     * @param value the value to freeze
     * @return the frozen value, or the original value if it could not be frozen
     */
    // Calls a method
    T freeze(T value);

    // Start of a method/block
    static @Nullable DataComponent<?> fromKey(@KeyPattern String key) {
        // Returns a value to the caller
        return fromKey(Key.key(key));
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable DataComponent<?> fromKey(Key key) {
        // Returns a value to the caller
        return DataComponentImpl.NAMESPACES.get(key);
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable DataComponent<?> fromId(int id) {
        // Returns a value to the caller
        return DataComponentImpl.IDS.get(id);
    // End of a block/expression
    }

    // Start of a method/block
    static Collection<DataComponent<?>> values() {
        // Returns a value to the caller
        return DataComponentImpl.NAMESPACES.values();
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Code statement
    static <T> DataComponent<T> createHeadless(
            // Code statement
            int id, Key key,
            // Annotation for the following element
            @Nullable NetworkBuffer.Type<T> network,
            // Annotation for the following element
            @Nullable Codec<T> codec,
            // Annotation for the following element
            @Nullable UnaryOperator<T> freeze
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new DataComponentImpl<>(id, key, network, codec, freeze);
    // End of a block/expression
    }
// End of a block/expression
}
