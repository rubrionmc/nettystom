// Déclaration du paquet de ce fichier
package net.minestom.server.component;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Decoder;
// Import d'une classe nécessaire
import net.minestom.server.codec.Encoder;
// Import d'une classe nécessaire
import net.minestom.server.item.enchant.EffectComponent;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.registry.StaticProtocolObject;

/**
 * A common type to represent all forms of component in the game. Each group of component types has its own declaration
 * file (see {@link net.minestom.server.component.DataComponent} for example).
 *
 * @param <T> The value type of the component
 * @see net.minestom.server.component.DataComponent
 * @see EffectComponent
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface DataComponent<T> extends StaticProtocolObject<DataComponent<T>>, Encoder<T>, Decoder<T> permits DataComponentImpl {

    // Appelle une méthode
    NetworkBuffer.Type<DataComponent<?>> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(DataComponent::fromId, DataComponent::id);
    // Appelle une méthode
    Codec<DataComponent<?>> CODEC = Codec.STRING.transform(DataComponent::fromKey, DataComponent::name);

    // Appelle une méthode
    NetworkBuffer.Type<DataComponentMap> MAP_NETWORK_TYPE = DataComponentMap.networkType(DataComponent::fromId);
    // Appelle une méthode
    Codec<DataComponentMap> MAP_NBT_TYPE = DataComponentMap.codec(DataComponent::fromId, DataComponent::fromKey);

    // Appelle une méthode
    NetworkBuffer.Type<DataComponentMap> PATCH_NETWORK_TYPE = DataComponentMap.patchNetworkType(DataComponent::fromId, true);
    // Appelle une méthode
    NetworkBuffer.Type<DataComponentMap> UNTRUSTED_PATCH_NETWORK_TYPE = DataComponentMap.patchNetworkType(DataComponent::fromId, false);
    // Appelle une méthode
    Codec<DataComponentMap> PATCH_CODEC = DataComponentMap.patchCodec(DataComponent::fromId, DataComponent::fromKey);

    /**
     * Represents any type which can hold data components. Represents a finalized view of a component, that is to say
     * an implementation may represent a patch on top of another Holder, however the return values of this type
     * will always represent the merged view.
     */
    // Déclaration de type (classe/interface/enum/record)
    interface Holder {
        // Début d'une méthode/d'un bloc
        default boolean has(DataComponent<?> component) {
            // Renvoie une valeur à l'appelant
            return get(component) != null;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        <T> @Nullable T get(DataComponent<T> component);

        // Début d'une méthode/d'un bloc
        default <T> T get(DataComponent<T> component, T defaultValue) {
            // Appelle une méthode
            final T value = get(component);
            // Renvoie une valeur à l'appelant
            return value != null ? value : defaultValue;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Value(DataComponent<?> component, @Nullable Object value) {
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    boolean isSynced();
    // Appelle une méthode
    boolean isSerialized();
    // Annotation pour l'élément suivant
    @Nullable NetworkBuffer.Type<T> networkType();
    // Annotation pour l'élément suivant
    @Nullable Codec<T> codec();

    // Appelle une méthode
    T read(NetworkBuffer reader);
    // Appelle une méthode
    void write(NetworkBuffer writer, T value);

    /**
     * Freezes the given value if possible. For example, collections should be frozen.
     * <br>
     * Note: Only {@link T} itself is required to be frozen, the objects inside {@link T} should be immutable.
     *
     * @param value the value to freeze
     * @return the frozen value, or the original value if it could not be frozen
     */
    // Appelle une méthode
    T freeze(T value);

    // Début d'une méthode/d'un bloc
    static @Nullable DataComponent<?> fromKey(@KeyPattern String key) {
        // Renvoie une valeur à l'appelant
        return fromKey(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable DataComponent<?> fromKey(Key key) {
        // Renvoie une valeur à l'appelant
        return DataComponentImpl.NAMESPACES.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable DataComponent<?> fromId(int id) {
        // Renvoie une valeur à l'appelant
        return DataComponentImpl.IDS.get(id);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Collection<DataComponent<?>> values() {
        // Renvoie une valeur à l'appelant
        return DataComponentImpl.NAMESPACES.values();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Instruction de code
    static <T> DataComponent<T> createHeadless(
            // Instruction de code
            int id, Key key,
            // Annotation pour l'élément suivant
            @Nullable NetworkBuffer.Type<T> network,
            // Annotation pour l'élément suivant
            @Nullable Codec<T> codec,
            // Annotation pour l'élément suivant
            @Nullable UnaryOperator<T> freeze
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new DataComponentImpl<>(id, key, network, codec, freeze);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
