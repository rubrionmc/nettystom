// Déclaration du paquet de ce fichier
package net.minestom.server.codec;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.Style;
// Import d'une classe nécessaire
import net.kyori.adventure.util.TriState;
// Import d'une classe nécessaire
import net.minestom.server.codec.CodecImpl.PrimitiveImpl;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;
// Import d'une classe nécessaire
import net.minestom.server.utils.ThrowingFunction;
// Import d'une classe nécessaire
import net.minestom.server.utils.UUIDUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.Unit;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Unmodifiable;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.function.Supplier;

/**
 * <p>
 * A {@link Codec} represents a combined {@link Encoder} and {@link Decoder} for a value.
 * Enabling easy encoding and decoding of values to and from a between formats, making serialization simple, reusable and type safe.
 * Going between formats is handled by {@link Transcoder}.
 * </p>
 * <p>
 * Most of the primitive or commonly used codecs are provided as static fields in this interface.
 * For example, {@link Codec#INT} is a codec for integers, and {@link Codec#STRING} is a codec for strings.
 * You can even use {@link Codec#Enum(Class)} for enums, which will convert the enum to a string
 * representation and back.
 * </p>
 * Codecs are immutable, you must chain methods to create a codec that you want. For example
 * <pre>{@code
 *         Codec<@Nullable String> codec = Codec.STRING.optional()
 *         Codec<Set<@Nullable String>> setCodec = codec.set();
 *     }
 * </pre>
 * <p>
 * Heavily inspired by <a href="https://github.com/Mojang/DataFixerUpper">Mojang/DataFixerUpper</a>,
 * licensed under the MIT license.
 * </p>
 *
 * @param <T> The type to be represented by this codec, nullable T will provide nullable results.
 */
// Déclaration de type (classe/interface/enum/record)
public interface Codec<T extends @UnknownNullability Object> extends Encoder<T>, Decoder<T> {

    /**
     * A raw value wrapper for entry is an object combined with its current decoder.
     * Allows converting of an intermediary object of a transcoder into the requested transcoder.
     * <br>
     * Useful when dealing with objects that have the same type required as their transcoder
     * for example NBT and JSON.
     */
    // Déclaration de type (classe/interface/enum/record)
    sealed interface RawValue permits CodecImpl.RawValueImpl {
        /**
         * Creates a RawValue instance
         *
         * @param coder the transcoder
         * @param value the value
         * @param <D>   The Object type
         * @return the new raw value instance
         */
        // Annotation pour l'élément suivant
        @Contract(pure = true, value = "_, _ -> new")
        // Début d'une méthode/d'un bloc
        static <D> RawValue of(Transcoder<D> coder, D value) {
            // Renvoie une valeur à l'appelant
            return new CodecImpl.RawValueImpl<>(coder, value);
        // Fin d'un bloc/d'une expression
        }

        /**
         * Converts the current value into another transcoder
         *
         * @param coder the transcoder to convert the object into
         * @param <D>   the resultant type; transcoder type.
         * @return the {@link Result} of converting to {@code coder}.
         */
        // Appelle une méthode
        <D> Result<D> convertTo(Transcoder<D> coder);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    Codec<RawValue> RAW_VALUE = new CodecImpl.RawValueCodecImpl();

    // Appelle une méthode
    Codec<Unit> UNIT = StructCodec.struct(Unit.INSTANCE);

    // Affecte une valeur
    Codec<Boolean> BOOLEAN = new PrimitiveImpl<>(Transcoder::createBoolean, Transcoder::getBoolean);

    // Appelle une méthode
    Codec<TriState> TRI_STATE = new CodecImpl.TriStateImpl();

    // Affecte une valeur
    Codec<Byte> BYTE = new PrimitiveImpl<>(Transcoder::createByte, Transcoder::getByte);

    // Affecte une valeur
    Codec<Short> SHORT = new PrimitiveImpl<>(Transcoder::createShort, Transcoder::getShort);

    // Affecte une valeur
    Codec<Integer> INT = new PrimitiveImpl<>(Transcoder::createInt, Transcoder::getInt);

    // Affecte une valeur
    Codec<Long> LONG = new PrimitiveImpl<>(Transcoder::createLong, Transcoder::getLong);

    // Affecte une valeur
    Codec<Float> FLOAT = new PrimitiveImpl<>(Transcoder::createFloat, Transcoder::getFloat);

    // Affecte une valeur
    Codec<Double> DOUBLE = new PrimitiveImpl<>(Transcoder::createDouble, Transcoder::getDouble);

    // Affecte une valeur
    Codec<String> STRING = new PrimitiveImpl<>(Transcoder::createString, Transcoder::getString);

    // Appelle une méthode
    Codec<Key> KEY = STRING.transform(Key::key, Key::asString);

    // Affecte une valeur
    Codec<byte[]> BYTE_ARRAY = new PrimitiveImpl<>(Transcoder::createByteArray, Transcoder::getByteArray);

    // Affecte une valeur
    Codec<int[]> INT_ARRAY = new PrimitiveImpl<>(Transcoder::createIntArray, Transcoder::getIntArray);

    // Affecte une valeur
    Codec<long[]> LONG_ARRAY = new PrimitiveImpl<>(Transcoder::createLongArray, Transcoder::getLongArray);

    // Appelle une méthode
    Codec<UUID> UUID = Codec.INT_ARRAY.transform(UUIDUtils::intArrayToUuid, UUIDUtils::uuidToIntArray);

    // Appelle une méthode
    Codec<UUID> UUID_STRING = STRING.transform(java.util.UUID::fromString, java.util.UUID::toString);

    // Appelle une méthode
    Codec<UUID> UUID_COERCED = UUID.orElse(UUID_STRING);

    // Affecte une valeur
    Codec<Component> COMPONENT = ComponentCodecs.COMPONENT;

    // Affecte une valeur
    Codec<Style> COMPONENT_STYLE = ComponentCodecs.STYLE;

    // Appelle une méthode
    Codec<Point> BLOCK_POSITION = new CodecImpl.BlockPositionImpl();

    // Appelle une méthode
    Codec<Point> VECTOR3D = new CodecImpl.Vector3DImpl();

    // Affecte une valeur
    Codec<BinaryTag> NBT = RAW_VALUE.transform(
            // Instruction de code
            value -> value.convertTo(Transcoder.NBT).orElseThrow(),
            // Appelle une méthode
            value -> RawValue.of(Transcoder.NBT, value));

    // Appelle une méthode
    StructCodec<CompoundBinaryTag> NBT_COMPOUND = new CodecImpl.CompoundBinaryTagImpl();

    /**
     * Creates an enum codec from a given class
     * <br>
     * Converts the {@link Enum#name()} into lowercase when encoding
     * and uppercase into decoding then passing it to {@link Enum#valueOf(Class, String)}
     *
     * @param enumClass the enum class
     * @param <E>       Enum type, E must be an enum
     * @return the codec enum
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    static <E extends Enum<E>> Codec<E> Enum(Class<E> enumClass) {
        // Appelle une méthode
        Objects.requireNonNull(enumClass, "Enum class cannot be null");
        // Renvoie une valeur à l'appelant
        return STRING.transform(
                // Instruction de code
                value -> Enum.valueOf(enumClass, value.toUpperCase(Locale.ROOT)),
                // Appelle une méthode
                value -> value.name().toLowerCase(Locale.ROOT));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Create a recursive codec from the parent codec
     * <br>
     * Useful when you want to keep encoding/decoding until there is nothing left.
     *
     * @param func the function to get the codec from.
     * @param <T>  The codec Type
     * @return the recursive codec
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    static <T> Codec<T> Recursive(Function<Codec<T>, Codec<T>> func) {
        // Renvoie une valeur à l'appelant
        return new CodecImpl.RecursiveImpl<>(func).delegate;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Lazily gets the reference of a codec; considered immutably lazy.
     * <br>
     * Useful for breaking possible cyclic loading of recursive codecs.
     * This may become a stable value in the future; don't rely on supplier getting called multiple times.
     *
     * @param supplier the supplier to load the codec from.
     * @param <T>      the codec type
     * @return the supplier
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    static <T> Codec<T> ForwardRef(Supplier<Codec<T>> supplier) {
        // Renvoie une valeur à l'appelant
        return new CodecImpl.ForwardRefImpl<>(supplier);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated Use {@link #RegistryTaggedUnion(String, Registry, Function)} instead.
     * Shortcut for {@link Codec#RegistryTaggedUnion(Registries.Selector, Function, String)}
     *
     * @param registry         the codec registry
     * @param serializerGetter the codec getter
     * @param key              the map key
     * @param <T>              the struct codec type.
     * @return a {@link StructCodec}
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Instruction de code
    static <T> StructCodec<T> RegistryTaggedUnion(
            // Instruction de code
            Registry<StructCodec<? extends T>> registry,
            // Instruction de code
            Function<T, StructCodec<? extends T>> serializerGetter,
            // Instruction de code
            String key
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return Codec.RegistryTaggedUnion(key, registry, serializerGetter);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated Use {@link #RegistryTaggedUnion(String, Registries.Selector, Function)} instead.
     * Creates a {@link StructCodec} to bidirectionally map values of {@link T} to their encoded values
     * <br>
     * Registry selectors will be used to lookup values of codecs of {@link T}.
     * Then will be used to map to object {@link T} from {@code key}
     *
     * @param registrySelector the registry selector used during lookup.
     * @param serializerGetter the serializer for each value of {@link T}
     * @param key              the map key for {@link T}
     * @param <T>              the codec type
     * @return a {@link StructCodec} bidirectionally mapping values of {@link T}
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Instruction de code
    static <T> StructCodec<T> RegistryTaggedUnion(
            // Instruction de code
            Registries.Selector<StructCodec<? extends T>> registrySelector,
            // Instruction de code
            Function<T, StructCodec<? extends T>> serializerGetter,
            // Instruction de code
            String key
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return Codec.RegistryTaggedUnion(key, registrySelector, serializerGetter);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Shortcut for {@link Codec#RegistryTaggedUnion(String, Registry, Function)}
     * where {@code key} will be {@code "type"}
     *
     * @param registry         the codec registry
     * @param serializerGetter the codec getter
     * @param <T>              the struct codec type.
     * @return a {@link StructCodec}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _ -> new")
    // Instruction de code
    static <T> StructCodec<T> RegistryTaggedUnion(
            // Instruction de code
            Registry<StructCodec<? extends T>> registry,
            // Instruction de code
            Function<T, StructCodec<? extends T>> serializerGetter
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return Codec.RegistryTaggedUnion("type", registry, serializerGetter);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Shortcut for {@link Codec#RegistryTaggedUnion(String, Registries.Selector, Function)}
     * where {@code selector} will be {@code registry}
     *
     * @param key              the map key
     * @param registry         the codec registry
     * @param serializerGetter the codec getter
     * @param <T>              the struct codec type.
     * @return a {@link StructCodec}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Instruction de code
    static <T> StructCodec<T> RegistryTaggedUnion(
            // Instruction de code
            String key,
            // Instruction de code
            Registry<StructCodec<? extends T>> registry,
            // Instruction de code
            Function<T, StructCodec<? extends T>> serializerGetter
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(registry, "registry");
        // Renvoie une valeur à l'appelant
        return Codec.RegistryTaggedUnion(key, (ignored) -> registry, serializerGetter); // Stable Value/Lazy Constant
    // Fin d'un bloc/d'une expression
    }

    /**
     * Shortcut for {@link Codec#RegistryTaggedUnion(String, Registries.Selector, Function)}
     * where {@code key} will be {@code "type"}
     *
     * @param registrySelector the registry selector used during lookup.
     * @param serializerGetter the serializer for each value of {@link T}
     * @param <T>              the codec type
     * @return a {@link StructCodec} bidirectionally mapping values of {@link T}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _ -> new")
    // Instruction de code
    static <T> StructCodec<T> RegistryTaggedUnion(
            // Instruction de code
            Registries.Selector<StructCodec<? extends T>> registrySelector,
            // Instruction de code
            Function<T, StructCodec<? extends T>> serializerGetter
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new CodecImpl.RegistryTaggedUnionImpl<>("type", registrySelector, serializerGetter);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@link StructCodec} to bidirectionally map values of {@link T} to their encoded values
     * <br>
     * Registry selectors will be used to lookup values of codecs of {@link T}.
     * Then will be used to map to object {@link T} from {@code key}
     *
     * @param key              the map key for {@link T}
     * @param registrySelector the registry selector used during lookup.
     * @param serializerGetter the serializer for each value of {@link T}
     * @param <T>              the codec type
     * @return a {@link StructCodec} bidirectionally mapping values of {@link T}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Instruction de code
    static <T> StructCodec<T> RegistryTaggedUnion(
            // Instruction de code
            String key,
            // Instruction de code
            Registries.Selector<StructCodec<? extends T>> registrySelector,
            // Instruction de code
            Function<T, StructCodec<? extends T>> serializerGetter
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new CodecImpl.RegistryTaggedUnionImpl<>(key, registrySelector, serializerGetter);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an Either Codec, depending on the value of Either decides which codec to use.
     *
     * @param leftCodec  the left codec
     * @param rightCodec the right codec
     * @param <L>        the left type
     * @param <R>        the right type
     * @return a {@link Codec} with {@link Either} of {@link L} and {@link R}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _ -> new")
    // Début d'une méthode/d'un bloc
    static <L, R> Codec<Either<L, R>> Either(Codec<L> leftCodec, Codec<R> rightCodec) {
        // Renvoie une valeur à l'appelant
        return new CodecImpl.EitherImpl<>(leftCodec, rightCodec);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an Either Codec, depending on the value of Either decides which codec to use.
     *
     * @param leftCodec  the left codec
     * @param rightCodec the right codec
     * @param <L>        the left type
     * @param <R>        the right type
     * @return a {@link StructCodec} with {@link Either} of {@link L} and {@link R}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _ -> new")
    // Début d'une méthode/d'un bloc
    static <L, R> StructCodec<Either<L, R>> EitherStruct(StructCodec<L> leftCodec, StructCodec<R> rightCodec) {
        // Renvoie une valeur à l'appelant
        return new CodecImpl.EitherStructImpl<>(leftCodec, rightCodec);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an optional codec, where null is encodable into {@link Transcoder#createNull()}.
     *
     * @return the optional codec of type {@link T}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> new")
    // Début d'une méthode/d'un bloc
    default Codec<@Nullable T> optional() {
        // Renvoie une valeur à l'appelant
        return new CodecImpl.OptionalImpl<>(this, null);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an optional codec, where null is encodable
     * and is encoded when value equals {@code defaultValue} or null through {@link Transcoder#createNull()}.
     * <br>
     * The default value will be used if the decoding is null or fails to decode.
     *
     * @param defaultValue the default value
     * @return the optional codec of type {@link T}
     * @throws NullPointerException if defaultValue is null, use {@link #optional()} instead.
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    default Codec<@UnknownNullability T> optional(T defaultValue) {
        // We really have no idea what nullability this will have as optional still accepts null values, but the default value could never be null
        // Renvoie une valeur à l'appelant
        return new CodecImpl.OptionalImpl<>(this, Objects.requireNonNull(defaultValue, "Default value cannot be null"));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Transforms an object from {@link S} to another {@link T} and from {@link T} back to {@link S}
     *
     * @param to   the function to {@link S} from {@link T}
     * @param from the function from {@link S} to {@link T}
     * @param <S>  the type
     * @return the transforming codec of {@link S}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _ -> new")
    // Début d'une méthode/d'un bloc
    default <S extends @UnknownNullability Object> Codec<S> transform(ThrowingFunction<T, S> to, ThrowingFunction<S, T> from) {
        // Renvoie une valeur à l'appelant
        return new CodecImpl.TransformImpl<>(this, to, from);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an unmodifiable list codec of {@link T} where its size is no larger than {@code maxSize}.
     *
     * @param maxSize the max size of the list before returning an error result.
     * @return the list codec of type {@link T}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    default Codec<@Unmodifiable List<T>> list(int maxSize) {
        // Renvoie une valeur à l'appelant
        return new CodecImpl.ListImpl<>(this, maxSize);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an unmodifiable unbounded list codec. See {@link #list(int)}
     *
     * @return the unbounded list codec of type {@link T}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> new")
    // Début d'une méthode/d'un bloc
    default Codec<@Unmodifiable List<T>> list() {
        // Renvoie une valeur à l'appelant
        return list(Integer.MAX_VALUE);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns an unmodifiable list or the first element or null if no such element exists.
     *
     * @param maxSize the max size of the list before returning an error result
     * @return the list codec of type {@link T}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    default Codec<@Unmodifiable @Nullable List<T>> listOrSingle(int maxSize) {
        // Renvoie une valeur à l'appelant
        return Either(Codec.this.list(maxSize), Codec.this).transform(
                // Instruction de code
                either -> either.unify(Function.identity(), List::of),
                // Instruction de code
                list -> list.size() == 1 ? Either.right(list.getFirst()) : Either.left(list)
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns an unmodifiable unbounded list or the first element or null if no such element exists.
     * See {@link #listOrSingle(int)}
     *
     * @return the list codec of type {@link T}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> new")
    // Début d'une méthode/d'un bloc
    default Codec<@Unmodifiable @Nullable List<T>> listOrSingle() {
        // Renvoie une valeur à l'appelant
        return this.listOrSingle(Integer.MAX_VALUE);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an unmodifiable set where its max is no larger than {@code maxSize}
     *
     * @param maxSize the max size before returning an error result
     * @return the set codec of type {@link T}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    default Codec<@Unmodifiable Set<T>> set(int maxSize) {
        // Renvoie une valeur à l'appelant
        return new CodecImpl.SetImpl<>(Codec.this, maxSize);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an unmodifiable unbounded set. See {@link #set(int)}
     *
     * @return the set codec of type {@link T}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> new")
    // Début d'une méthode/d'un bloc
    default Codec<@Unmodifiable Set<T>> set() {
        // Renvoie une valeur à l'appelant
        return set(Integer.MAX_VALUE);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an unmodifiable map of key {@link T} and value of {@link V}
     *
     * @param valueCodec the codec to use for {@link V}
     * @param maxSize    the max size before returning an error result.
     * @param <V>        the value type
     * @return the map codec of type {@link T} and {@link V}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _ -> new")
    // Début d'une méthode/d'un bloc
    default <V> Codec<@Unmodifiable Map<T, V>> mapValue(Codec<V> valueCodec, int maxSize) {
        // Renvoie une valeur à l'appelant
        return new CodecImpl.MapImpl<>(Codec.this, valueCodec, maxSize);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an unmodifiable map of key {@link T} and value of {@link V}. See {@link #mapValue(Codec, int)}
     *
     * @param valueCodec the codec to use for {@link V}
     * @param <V>        the value type
     * @return the map codec of type {@link T} and {@link V}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    default <V> Codec<@Unmodifiable Map<T, V>> mapValue(Codec<V> valueCodec) {
        // Renvoie une valeur à l'appelant
        return mapValue(valueCodec, Integer.MAX_VALUE);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an unmodifiable map of key {@link T} and value of {@link V}
     * where the codec for {@link V} is determined by the key {@link T}
     *
     * @param mapper  the function to get the codec for {@link V} from {@link T}
     * @param maxSize the max size before returning an error result.
     * @param cached  whether to cache codecs for each key
     * @param <V>     the value type
     * @return the map codec of type {@link T} and {@link V}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Début d'une méthode/d'un bloc
    default <V> Codec<@Unmodifiable Map<T, V>> mapValueTyped(Function<T, Codec<V>> mapper, int maxSize, boolean cached) {
        // Renvoie une valeur à l'appelant
        return new CodecImpl.TypedMapImpl<>(Codec.this, mapper,
                // Instruction de code
                maxSize, cached ? new ConcurrentHashMap<>() : null);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an unmodifiable map of key {@link T} and value of {@link V}
     * where the codec for {@link V} is determined by the key {@link T}
     *
     * @param mapper  the function to get the codec for {@link V} from {@link T}
     * @param maxSize the max size before returning an error result.
     * @param <V>     the value type
     * @return the map codec of type {@link T} and {@link V}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _ -> new")
    // Début d'une méthode/d'un bloc
    default <V> Codec<@Unmodifiable Map<T, V>> mapValueTyped(Function<T, Codec<V>> mapper, int maxSize) {
        // Renvoie une valeur à l'appelant
        return mapValueTyped(mapper, maxSize, false);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an unmodifiable map of key {@link T} and value of {@link V}
     * where the codec for {@link V} is determined by the key {@link T}
     *
     * @param mapper  the function to get the codec for {@link V} from {@link T}
     * @param cached  whether to cache codecs for each key
     * @param <V>     the value type
     * @return the map codec of type {@link T} and {@link V}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _ -> new")
    // Début d'une méthode/d'un bloc
    default <V> Codec<@Unmodifiable Map<T, V>> mapValueTyped(Function<T, Codec<V>> mapper, boolean cached) {
        // Renvoie une valeur à l'appelant
        return mapValueTyped(mapper, Integer.MAX_VALUE, cached);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an unmodifiable map of key {@link T} and value of {@link V}
     * where the codec for {@link V} is determined by the key {@link T}
     *
     * @param mapper  the function to get the codec for {@link V} from {@link T}
     * @param <V>     the value type
     * @return the map codec of type {@link T} and {@link V}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    default <V> Codec<@Unmodifiable Map<T, V>> mapValueTyped(Function<T, Codec<V>> mapper) {
        // Renvoie une valeur à l'appelant
        return mapValueTyped(mapper, Integer.MAX_VALUE, false);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a union type of type {@link R}. See {@link #unionType(String, Function, Function)}
     * <br>
     * Useful when you have an interface of {@link T} and want a codec subclasses of {@link T}
     *
     * @param serializers the map from {@link T} value to its serializer
     * @param keyFunc     to map from {@link R} to its value of {@link T}
     * @param <R>         the return type; {@link T} or a subclass
     * @return the struct codec union of {@link R}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _ -> new")
    // Début d'une méthode/d'un bloc
    default <R> StructCodec<R> unionType(Function<T, StructCodec<? extends R>> serializers, Function<R, ? extends T> keyFunc) {
        // Renvoie une valeur à l'appelant
        return unionType("type", serializers, keyFunc);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a union type of type {@link R}
     * <br>
     * Useful when you have an interface of {@link T} and want a codec subclasses of {@link T}
     *
     * @param keyField    the map key
     * @param serializers the map from {@link T} value to its serializer
     * @param keyFunc     to map from {@link R} to its value of {@link T}
     * @param <R>         the return type; {@link T} or a subclass
     * @return the struct codec union of {@link R}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Instruction de code
    default <R> StructCodec<R> unionType(
            // Instruction de code
            String keyField,
            // Instruction de code
            Function<T, StructCodec<? extends R>> serializers,
            // Instruction de code
            Function<R, ? extends T> keyFunc
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new CodecImpl.UnionImpl<>(keyField, this, serializers, keyFunc);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a or else codec where it will attempt to use the first codec
     * then use the second one if it fails.
     * <br>
     * If both codecs fail the first error will be returned instead.
     *
     * @param other the other codec
     * @return the or else codec of {@link T}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    default Codec<T> orElse(Codec<T> other) {
        // Renvoie une valeur à l'appelant
        return new CodecImpl.OrElseImpl<>(this, other);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a or else codec where it will attempt to use the first codec
     * then use the second one and transform via mapper if it fails.
     * <br>
     * If both codecs fail the first error will be returned instead.
     *
     * @param other the other codec
     * @param mapper the mapper to transform the error into a value of {@link T}
     * @return the or else codec of {@link T}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    default <S> Codec<T> orElse(Codec<S> other, ThrowingFunction<S, T> mapper) {
        // Renvoie une valeur à l'appelant
        return new CodecImpl.OrElseImpl<>(this, other.transform(mapper, _ -> {
            // Lève une exception
            throw new UnsupportedOperationException("unreachable");
        // Instruction de code
        }));
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
