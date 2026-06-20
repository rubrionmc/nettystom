// Package declaration for this file
package net.minestom.server.codec;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.Style;
// Import of a required class
import net.kyori.adventure.util.TriState;
// Import of a required class
import net.minestom.server.codec.CodecImpl.PrimitiveImpl;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.utils.Either;
// Import of a required class
import net.minestom.server.utils.ThrowingFunction;
// Import of a required class
import net.minestom.server.utils.UUIDUtils;
// Import of a required class
import net.minestom.server.utils.Unit;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;
// Import of a required class
import org.jetbrains.annotations.Unmodifiable;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;
// Import of a required class
import java.util.function.Function;
// Import of a required class
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
// Type declaration (class/interface/enum/record)
public interface Codec<T extends @UnknownNullability Object> extends Encoder<T>, Decoder<T> {

    /**
     * A raw value wrapper for entry is an object combined with its current decoder.
     * Allows converting of an intermediary object of a transcoder into the requested transcoder.
     * <br>
     * Useful when dealing with objects that have the same type required as their transcoder
     * for example NBT and JSON.
     */
    // Type declaration (class/interface/enum/record)
    sealed interface RawValue permits CodecImpl.RawValueImpl {
        /**
         * Creates a RawValue instance
         *
         * @param coder the transcoder
         * @param value the value
         * @param <D>   The Object type
         * @return the new raw value instance
         */
        // Annotation for the following element
        @Contract(pure = true, value = "_, _ -> new")
        // Start of a method/block
        static <D> RawValue of(Transcoder<D> coder, D value) {
            // Returns a value to the caller
            return new CodecImpl.RawValueImpl<>(coder, value);
        // End of a block/expression
        }

        /**
         * Converts the current value into another transcoder
         *
         * @param coder the transcoder to convert the object into
         * @param <D>   the resultant type; transcoder type.
         * @return the {@link Result} of converting to {@code coder}.
         */
        // Calls a method
        <D> Result<D> convertTo(Transcoder<D> coder);
    // End of a block/expression
    }

    // Calls a method
    Codec<RawValue> RAW_VALUE = new CodecImpl.RawValueCodecImpl();

    // Calls a method
    Codec<Unit> UNIT = StructCodec.struct(Unit.INSTANCE);

    // Calls a method
    Codec<Boolean> BOOLEAN = new PrimitiveImpl<>(Transcoder::createBoolean, Transcoder::getBoolean);

    // Calls a method
    Codec<TriState> TRI_STATE = new CodecImpl.TriStateImpl();

    // Calls a method
    Codec<Byte> BYTE = new PrimitiveImpl<>(Transcoder::createByte, Transcoder::getByte);

    // Calls a method
    Codec<Short> SHORT = new PrimitiveImpl<>(Transcoder::createShort, Transcoder::getShort);

    // Calls a method
    Codec<Integer> INT = new PrimitiveImpl<>(Transcoder::createInt, Transcoder::getInt);

    // Calls a method
    Codec<Long> LONG = new PrimitiveImpl<>(Transcoder::createLong, Transcoder::getLong);

    // Calls a method
    Codec<Float> FLOAT = new PrimitiveImpl<>(Transcoder::createFloat, Transcoder::getFloat);

    // Calls a method
    Codec<Double> DOUBLE = new PrimitiveImpl<>(Transcoder::createDouble, Transcoder::getDouble);

    // Calls a method
    Codec<String> STRING = new PrimitiveImpl<>(Transcoder::createString, Transcoder::getString);

    // Calls a method
    Codec<Key> KEY = STRING.transform(Key::key, Key::asString);

    // Calls a method
    Codec<byte[]> BYTE_ARRAY = new PrimitiveImpl<>(Transcoder::createByteArray, Transcoder::getByteArray);

    // Calls a method
    Codec<int[]> INT_ARRAY = new PrimitiveImpl<>(Transcoder::createIntArray, Transcoder::getIntArray);

    // Calls a method
    Codec<long[]> LONG_ARRAY = new PrimitiveImpl<>(Transcoder::createLongArray, Transcoder::getLongArray);

    // Calls a method
    Codec<UUID> UUID = Codec.INT_ARRAY.transform(UUIDUtils::intArrayToUuid, UUIDUtils::uuidToIntArray);

    // Calls a method
    Codec<UUID> UUID_STRING = STRING.transform(java.util.UUID::fromString, java.util.UUID::toString);

    // Calls a method
    Codec<UUID> UUID_COERCED = UUID.orElse(UUID_STRING);

    // Assigns a value
    Codec<Component> COMPONENT = ComponentCodecs.COMPONENT;

    // Assigns a value
    Codec<Style> COMPONENT_STYLE = ComponentCodecs.STYLE;

    // Calls a method
    Codec<Point> BLOCK_POSITION = new CodecImpl.BlockPositionImpl();

    // Calls a method
    Codec<Point> VECTOR3D = new CodecImpl.Vector3DImpl();

    // Assigns a value
    Codec<BinaryTag> NBT = RAW_VALUE.transform(
            // Code statement
            value -> value.convertTo(Transcoder.NBT).orElseThrow(),
            // Calls a method
            value -> RawValue.of(Transcoder.NBT, value));

    // Calls a method
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
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    static <E extends Enum<E>> Codec<E> Enum(Class<E> enumClass) {
        // Calls a method
        Objects.requireNonNull(enumClass, "Enum class cannot be null");
        // Returns a value to the caller
        return STRING.transform(
                // Code statement
                value -> Enum.valueOf(enumClass, value.toUpperCase(Locale.ROOT)),
                // Calls a method
                value -> value.name().toLowerCase(Locale.ROOT));
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    static <T> Codec<T> Recursive(Function<Codec<T>, Codec<T>> func) {
        // Returns a value to the caller
        return new CodecImpl.RecursiveImpl<>(func).delegate;
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    static <T> Codec<T> ForwardRef(Supplier<Codec<T>> supplier) {
        // Returns a value to the caller
        return new CodecImpl.ForwardRefImpl<>(supplier);
    // End of a block/expression
    }

    /**
     * @param registry         the codec registry
     * @param serializerGetter the codec getter
     * @param key              the map key
     * @param <T>              the struct codec type.
     * @return a {@link StructCodec}
     * @deprecated Use {@link #RegistryTaggedUnion(String, Registry, Function)} instead.
     * Shortcut for {@link Codec#RegistryTaggedUnion(Registries.Selector, Function, String)}
     */
    // Annotation for the following element
    @Deprecated
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Code statement
    static <T> StructCodec<T> RegistryTaggedUnion(
            // Code statement
            Registry<StructCodec<? extends T>> registry,
            // Code statement
            Function<T, StructCodec<? extends T>> serializerGetter,
            // Code statement
            String key
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return Codec.RegistryTaggedUnion(key, registry, serializerGetter);
    // End of a block/expression
    }

    /**
     * @param registrySelector the registry selector used during lookup.
     * @param serializerGetter the serializer for each value of {@link T}
     * @param key              the map key for {@link T}
     * @param <T>              the codec type
     * @return a {@link StructCodec} bidirectionally mapping values of {@link T}
     * @deprecated Use {@link #RegistryTaggedUnion(String, Registries.Selector, Function)} instead.
     * Creates a {@link StructCodec} to bidirectionally map values of {@link T} to their encoded values
     * <br>
     * Registry selectors will be used to lookup values of codecs of {@link T}.
     * Then will be used to map to object {@link T} from {@code key}
     */
    // Annotation for the following element
    @Deprecated
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Code statement
    static <T> StructCodec<T> RegistryTaggedUnion(
            // Code statement
            Registries.Selector<StructCodec<? extends T>> registrySelector,
            // Code statement
            Function<T, StructCodec<? extends T>> serializerGetter,
            // Code statement
            String key
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return Codec.RegistryTaggedUnion(key, registrySelector, serializerGetter);
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(pure = true, value = "_, _ -> new")
    // Code statement
    static <T> StructCodec<T> RegistryTaggedUnion(
            // Code statement
            Registry<StructCodec<? extends T>> registry,
            // Code statement
            Function<T, StructCodec<? extends T>> serializerGetter
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return Codec.RegistryTaggedUnion("type", registry, serializerGetter);
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Code statement
    static <T> StructCodec<T> RegistryTaggedUnion(
            // Code statement
            String key,
            // Code statement
            Registry<StructCodec<? extends T>> registry,
            // Code statement
            Function<T, StructCodec<? extends T>> serializerGetter
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(registry, "registry");
        // Returns a value to the caller
        return Codec.RegistryTaggedUnion(key, (ignored) -> registry, serializerGetter); // Stable Value/Lazy Constant
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(pure = true, value = "_, _ -> new")
    // Code statement
    static <T> StructCodec<T> RegistryTaggedUnion(
            // Code statement
            Registries.Selector<StructCodec<? extends T>> registrySelector,
            // Code statement
            Function<T, StructCodec<? extends T>> serializerGetter
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new CodecImpl.RegistryTaggedUnionImpl<>("type", registrySelector, serializerGetter);
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Code statement
    static <T> StructCodec<T> RegistryTaggedUnion(
            // Code statement
            String key,
            // Code statement
            Registries.Selector<StructCodec<? extends T>> registrySelector,
            // Code statement
            Function<T, StructCodec<? extends T>> serializerGetter
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new CodecImpl.RegistryTaggedUnionImpl<>(key, registrySelector, serializerGetter);
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(pure = true, value = "_, _ -> new")
    // Start of a method/block
    static <L, R> Codec<Either<L, R>> Either(Codec<L> leftCodec, Codec<R> rightCodec) {
        // Returns a value to the caller
        return new CodecImpl.EitherImpl<>(leftCodec, rightCodec);
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(pure = true, value = "_, _ -> new")
    // Start of a method/block
    static <L, R> StructCodec<Either<L, R>> EitherStruct(StructCodec<L> leftCodec, StructCodec<R> rightCodec) {
        // Returns a value to the caller
        return new CodecImpl.EitherStructImpl<>(leftCodec, rightCodec);
    // End of a block/expression
    }

    /**
     * Creates an optional codec, where null is encodable into {@link Transcoder#createNull()}.
     *
     * @return the optional codec of type {@link T}
     */
    // Annotation for the following element
    @Contract(pure = true, value = "-> new")
    // Start of a method/block
    default Codec<@Nullable T> optional() {
        // Returns a value to the caller
        return new CodecImpl.OptionalImpl<>(this, null);
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    default Codec<@UnknownNullability T> optional(T defaultValue) {
        // We really have no idea what nullability this will have as optional still accepts null values, but the default value could never be null
        // Returns a value to the caller
        return new CodecImpl.OptionalImpl<>(this, Objects.requireNonNull(defaultValue, "Default value cannot be null"));
    // End of a block/expression
    }

    /**
     * Transforms an object from {@link S} to another {@link T} and from {@link T} back to {@link S}
     *
     * @param to   the function to {@link S} from {@link T}
     * @param from the function from {@link S} to {@link T}
     * @param <S>  the type
     * @return the transforming codec of {@link S}
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_, _ -> new")
    // Start of a method/block
    default <S extends @UnknownNullability Object> Codec<S> transform(ThrowingFunction<T, S> to, ThrowingFunction<S, T> from) {
        // Returns a value to the caller
        return new CodecImpl.TransformImpl<>(this, to, from);
    // End of a block/expression
    }

    /**
     * Creates an unmodifiable list codec of {@link T} where its size is no larger than {@code maxSize}.
     *
     * @param maxSize the max size of the list before returning an error result.
     * @return the list codec of type {@link T}
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    default Codec<@Unmodifiable List<T>> list(int maxSize) {
        // Returns a value to the caller
        return new CodecImpl.ListImpl<>(this, maxSize);
    // End of a block/expression
    }

    /**
     * Creates an unmodifiable unbounded list codec. See {@link #list(int)}
     *
     * @return the unbounded list codec of type {@link T}
     */
    // Annotation for the following element
    @Contract(pure = true, value = "-> new")
    // Start of a method/block
    default Codec<@Unmodifiable List<T>> list() {
        // Returns a value to the caller
        return list(Integer.MAX_VALUE);
    // End of a block/expression
    }

    /**
     * Returns an unmodifiable list or the first element or null if no such element exists.
     *
     * @param maxSize the max size of the list before returning an error result
     * @return the list codec of type {@link T}
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    default Codec<@Unmodifiable @Nullable List<T>> listOrSingle(int maxSize) {
        // Returns a value to the caller
        return Either(Codec.this.list(maxSize), Codec.this).transform(
                // Code statement
                either -> either.unify(Function.identity(), List::of),
                // Code statement
                list -> list.size() == 1 ? Either.right(list.getFirst()) : Either.left(list)
        // End of a block/expression
        );
    // End of a block/expression
    }

    /**
     * Returns an unmodifiable unbounded list or the first element or null if no such element exists.
     * See {@link #listOrSingle(int)}
     *
     * @return the list codec of type {@link T}
     */
    // Annotation for the following element
    @Contract(pure = true, value = "-> new")
    // Start of a method/block
    default Codec<@Unmodifiable @Nullable List<T>> listOrSingle() {
        // Returns a value to the caller
        return this.listOrSingle(Integer.MAX_VALUE);
    // End of a block/expression
    }

    /**
     * Creates an unmodifiable set where its max is no larger than {@code maxSize}
     *
     * @param maxSize the max size before returning an error result
     * @return the set codec of type {@link T}
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    default Codec<@Unmodifiable Set<T>> set(int maxSize) {
        // Returns a value to the caller
        return new CodecImpl.SetImpl<>(Codec.this, maxSize);
    // End of a block/expression
    }

    /**
     * Creates an unmodifiable unbounded set. See {@link #set(int)}
     *
     * @return the set codec of type {@link T}
     */
    // Annotation for the following element
    @Contract(pure = true, value = "-> new")
    // Start of a method/block
    default Codec<@Unmodifiable Set<T>> set() {
        // Returns a value to the caller
        return set(Integer.MAX_VALUE);
    // End of a block/expression
    }

    /**
     * Creates an unmodifiable map of key {@link T} and value of {@link V}
     *
     * @param valueCodec the codec to use for {@link V}
     * @param maxSize    the max size before returning an error result.
     * @param <V>        the value type
     * @return the map codec of type {@link T} and {@link V}
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_, _ -> new")
    // Start of a method/block
    default <V> Codec<@Unmodifiable Map<T, V>> mapValue(Codec<V> valueCodec, int maxSize) {
        // Returns a value to the caller
        return new CodecImpl.MapImpl<>(Codec.this, valueCodec, maxSize);
    // End of a block/expression
    }

    /**
     * Creates an unmodifiable map of key {@link T} and value of {@link V}. See {@link #mapValue(Codec, int)}
     *
     * @param valueCodec the codec to use for {@link V}
     * @param <V>        the value type
     * @return the map codec of type {@link T} and {@link V}
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    default <V> Codec<@Unmodifiable Map<T, V>> mapValue(Codec<V> valueCodec) {
        // Returns a value to the caller
        return mapValue(valueCodec, Integer.MAX_VALUE);
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    default <V> Codec<@Unmodifiable Map<T, V>> mapValueTyped(Function<T, Codec<V>> mapper, int maxSize, boolean cached) {
        // Returns a value to the caller
        return new CodecImpl.TypedMapImpl<>(Codec.this, mapper,
                // Calls a method
                maxSize, cached ? new ConcurrentHashMap<>() : null);
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(pure = true, value = "_, _ -> new")
    // Start of a method/block
    default <V> Codec<@Unmodifiable Map<T, V>> mapValueTyped(Function<T, Codec<V>> mapper, int maxSize) {
        // Returns a value to the caller
        return mapValueTyped(mapper, maxSize, false);
    // End of a block/expression
    }

    /**
     * Creates an unmodifiable map of key {@link T} and value of {@link V}
     * where the codec for {@link V} is determined by the key {@link T}
     *
     * @param mapper the function to get the codec for {@link V} from {@link T}
     * @param cached whether to cache codecs for each key
     * @param <V>    the value type
     * @return the map codec of type {@link T} and {@link V}
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_, _ -> new")
    // Start of a method/block
    default <V> Codec<@Unmodifiable Map<T, V>> mapValueTyped(Function<T, Codec<V>> mapper, boolean cached) {
        // Returns a value to the caller
        return mapValueTyped(mapper, Integer.MAX_VALUE, cached);
    // End of a block/expression
    }

    /**
     * Creates an unmodifiable map of key {@link T} and value of {@link V}
     * where the codec for {@link V} is determined by the key {@link T}
     *
     * @param mapper the function to get the codec for {@link V} from {@link T}
     * @param <V>    the value type
     * @return the map codec of type {@link T} and {@link V}
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    default <V> Codec<@Unmodifiable Map<T, V>> mapValueTyped(Function<T, Codec<V>> mapper) {
        // Returns a value to the caller
        return mapValueTyped(mapper, Integer.MAX_VALUE, false);
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(pure = true, value = "_, _ -> new")
    // Start of a method/block
    default <R> StructCodec<R> unionType(Function<T, StructCodec<? extends R>> serializers, Function<R, ? extends T> keyFunc) {
        // Returns a value to the caller
        return unionType("type", serializers, keyFunc);
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Code statement
    default <R> StructCodec<R> unionType(
            // Code statement
            String keyField,
            // Code statement
            Function<T, StructCodec<? extends R>> serializers,
            // Code statement
            Function<R, ? extends T> keyFunc
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new CodecImpl.UnionImpl<>(keyField, this, serializers, keyFunc);
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    default Codec<T> orElse(Codec<T> other) {
        // Returns a value to the caller
        return new CodecImpl.OrElseImpl<>(this, other);
    // End of a block/expression
    }

    /**
     * Creates a or else codec where it will attempt to use the first codec
     * then use the second one and transform via mapper if it fails.
     * <br>
     * If both codecs fail the first error will be returned instead.
     *
     * @param other  the other codec
     * @param mapper the mapper to transform the error into a value of {@link T}
     * @return the or else codec of {@link T}
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_, _ -> new")
    // Start of a method/block
    default <S> Codec<T> orElse(Codec<S> other, ThrowingFunction<S, T> mapper) {
        // Returns a value to the caller
        return new CodecImpl.OrElseImpl<>(this, other.transform(mapper, _ -> {
            // Throws an exception
            throw new UnsupportedOperationException("unreachable");
        // Code statement
        }));
    // End of a block/expression
    }

// End of a block/expression
}
