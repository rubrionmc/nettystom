// Package declaration for this file
package net.minestom.server.codec;

// Import of a required class
import net.minestom.server.codec.Transcoder.MapBuilder;
// Import of a required class
import net.minestom.server.codec.Transcoder.MapLike;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate.*;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.function.Supplier;

/**
 * A struct codec is a map backed {@link Codec}, where the keys are strings.
 * See {@link Codec}, {@link Decoder} and {@link Encoder}
 * <br>
 * You can also use {@link #struct(String, Codec, Function, F1)} to create as templating
 * similar to {@link net.minestom.server.network.NetworkBufferTemplate}
 * <p>
 * {@inheritDoc}
 * <br>
 * You can use structs to create complex objects
 * <pre>{@code
 * record MyObject(double coolnessFactor, @Nullable String of) {
 *     static final StructCodec<MyObject> CODEC = StructCodec.struct(
 *             "id", Codec.DOUBLE, MyObject::coolnessFactor,
 *             "name", Codec.STRING.optional(), MyObject::of,
 *             MyObject::new
 *     );
 *
 *     public MyObject {
 *         coolnessFactor = Math.clamp(coolnessFactor, 0.0, 2.0); // Too powerful
 *     }
 * }
 *
 * MyObject value = new MyObject(7.8d, "me"); // Or use a null name for no name.
 * // Encoding to JSON
 * JsonElement encoded = MyObject.CODEC.encode(Transcoder.JSON, value).orElseThrow();
 * // Decoding from JSON
 * MyObject decoded = MyObject.CODEC.decode(Transcoder.JSON, encoded).orElseThrow();
 * }</pre>
 *
 * @param <R> the return type, never null.
 */
// Type declaration (class/interface/enum/record)
public interface StructCodec<R> extends Codec<R> {
    /**
     * A special key used to instruct the codec to inline the value instead of wrapping it in a map.
     * The inlined codec must also be a StructCodec.
     */
    // Assigns a value
    String INLINE = "$$inline$$";

    /**
     * Decode a value {@link R} from the backing map of {@link D}
     *
     * @param coder the transcoder for {@link D}
     * @param map   the map to decode from
     * @param <D>   the transcoder type
     * @return the result of decoding
     */
    // Calls a method
    <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map);

    /**
     * Decode a value {@link R} into the backing map of {@link D}
     *
     * @param coder the transcoder for {@link D}
     * @param value the value of {@link R} to encode
     * @param map   the map to decode from
     * @param <D>   the transcoder type
     * @return the result of encoding
     */
    // Calls a method
    <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map);

    /**
     * {@inheritDoc}
     *
     * @param coder the transcoder to use
     * @param value the value to decode
     * @param <D>   the transcoder type
     * @return the result from decoding
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    default <D> Result<R> decode(Transcoder<D> coder, D value) {
        // Calls a method
        Objects.requireNonNull(value, "Value cannot be null");
        // Returns a value to the caller
        return coder.getMap(value).map(map -> decodeFromMap(coder, map));
    // End of a block/expression
    }

    /**
     * {@inheritDoc}
     *
     * @param coder the transcoder to use
     * @param value the value to encode, if null returns error
     * @param <D>   the transcoder type
     * @return the result from encoding
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    default <D> Result<D> encode(Transcoder<D> coder, @Nullable R value) {
        // Branch: checks a condition
        if (value == null) return new Result.Error<>("null");
        // Returns a value to the caller
        return encodeToMap(coder, value, coder.createMap());
    // End of a block/expression
    }

    /**
     * Similar to {@link #orElse(Codec)} but uses the map backing instead.
     * <br>
     * For decoding it attempts to use the current codec or uses the other codec,
     * if neither work returns the firsts error.
     *
     * @param other the other struct codec
     * @return the new or else struct
     */
    // Start of a method/block
    default StructCodec<R> orElseStruct(StructCodec<R> other) {
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<R> primaryResult = StructCodec.this.decodeFromMap(coder, map);
                // Branch: checks a condition
                if (primaryResult instanceof Result.Ok<R> primaryOk)
                    // Returns a value to the caller
                    return primaryOk;

                // Primary did not work, try secondary
                // Calls a method
                final Result<R> secondaryResult = other.decodeFromMap(coder, map);
                // Branch: checks a condition
                if (secondaryResult instanceof Result.Ok<R> secondaryOk)
                    // Returns a value to the caller
                    return secondaryOk;

                // Secondary did not work either, return error from primary.
                // Returns a value to the caller
                return primaryResult;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Returns a value to the caller
                return StructCodec.this.encodeToMap(coder, value, map);
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Returns the value in any struct.
     *
     * @param value the value to return of {@link R}
     * @param <R>   the return type
     * @return the new struct codec for value
     */
    // Start of a method/block
    static <R> StructCodec<R> struct(R value) {
        // Calls a method
        final Result<R> ok = new Result.Ok<>(Objects.requireNonNull(value, "value"));
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Returns a value to the caller
                return ok;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Lazily returns the value in any struct.
     *
     * @param ctor the value to return of {@link R}
     * @param <R>  the return type
     * @return the new struct codec for value
     */
    // Start of a method/block
    static <R> StructCodec<R> struct(Supplier<R> ctor) {
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Returns a value to the caller
                return new Result.Ok<>(ctor.get());
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1   the name/key for {@link P1}
     * @param codec1  the codec for {@link P1}
     * @param getter1 the getter for {@link P1}
     * @param ctor    the constructor for {@link R}
     * @param <R>     the return type
     * @param <P1>    the first parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            F1<? super P1, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1   the name/key for {@link P1}
     * @param codec1  the codec for {@link P1}
     * @param getter1 the getter for {@link P1}
     * @param name2   the name/key for {@link P2}
     * @param codec2  the codec for {@link P2}
     * @param getter2 the getter for {@link P2}
     * @param ctor    the constructor for {@link R}
     * @param <R>     the return type
     * @param <P1>    the first parameter type
     * @param <P2>    the second parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            String name2, Codec<P2> codec2, Function<? super R, ? extends P2> getter2,
            // Code statement
            F2<? super P1, ? super P2, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(name2, "name2");
        // Calls a method
        Objects.requireNonNull(codec2, "codec2");
        // Calls a method
        Objects.requireNonNull(getter2, "getter2");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Calls a method
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Branch: checks a condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Returns a value to the caller
                    return result2.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1, value2));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Calls a method
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Branch: checks a condition
                if (result2 != null) return result2;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1   the name/key for {@link P1}
     * @param codec1  the codec for {@link P1}
     * @param getter1 the getter for {@link P1}
     * @param name2   the name/key for {@link P2}
     * @param codec2  the codec for {@link P2}
     * @param getter2 the getter for {@link P2}
     * @param name3   the name/key for {@link P3}
     * @param codec3  the codec for {@link P3}
     * @param getter3 the getter for {@link P3}
     * @param ctor    the constructor for {@link R}
     * @param <R>     the return type
     * @param <P1>    the first parameter type
     * @param <P2>    the second parameter type
     * @param <P3>    the third parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            String name2, Codec<P2> codec2, Function<? super R, ? extends P2> getter2,
            // Code statement
            String name3, Codec<P3> codec3, Function<? super R, ? extends P3> getter3,
            // Code statement
            F3<? super P1, ? super P2, ? super P3, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(name2, "name2");
        // Calls a method
        Objects.requireNonNull(codec2, "codec2");
        // Calls a method
        Objects.requireNonNull(getter2, "getter2");
        // Calls a method
        Objects.requireNonNull(name3, "name3");
        // Calls a method
        Objects.requireNonNull(codec3, "codec3");
        // Calls a method
        Objects.requireNonNull(getter3, "getter3");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Calls a method
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Branch: checks a condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Returns a value to the caller
                    return result2.cast();
                // Calls a method
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Branch: checks a condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Returns a value to the caller
                    return result3.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1, value2, value3));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Calls a method
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Branch: checks a condition
                if (result2 != null) return result2;
                // Calls a method
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Branch: checks a condition
                if (result3 != null) return result3;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1   the name/key for {@link P1}
     * @param codec1  the codec for {@link P1}
     * @param getter1 the getter for {@link P1}
     * @param name2   the name/key for {@link P2}
     * @param codec2  the codec for {@link P2}
     * @param getter2 the getter for {@link P2}
     * @param name3   the name/key for {@link P3}
     * @param codec3  the codec for {@link P3}
     * @param getter3 the getter for {@link P3}
     * @param name4   the name/key for {@link P4}
     * @param codec4  the codec for {@link P4}
     * @param getter4 the getter for {@link P4}
     * @param ctor    the constructor for {@link R}
     * @param <R>     the return type
     * @param <P1>    the first parameter type
     * @param <P2>    the second parameter type
     * @param <P3>    the third parameter type
     * @param <P4>    the fourth parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            String name2, Codec<P2> codec2, Function<? super R, ? extends P2> getter2,
            // Code statement
            String name3, Codec<P3> codec3, Function<? super R, ? extends P3> getter3,
            // Code statement
            String name4, Codec<P4> codec4, Function<? super R, ? extends P4> getter4,
            // Code statement
            F4<? super P1, ? super P2, ? super P3, ? super P4, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(name2, "name2");
        // Calls a method
        Objects.requireNonNull(codec2, "codec2");
        // Calls a method
        Objects.requireNonNull(getter2, "getter2");
        // Calls a method
        Objects.requireNonNull(name3, "name3");
        // Calls a method
        Objects.requireNonNull(codec3, "codec3");
        // Calls a method
        Objects.requireNonNull(getter3, "getter3");
        // Calls a method
        Objects.requireNonNull(name4, "name4");
        // Calls a method
        Objects.requireNonNull(codec4, "codec4");
        // Calls a method
        Objects.requireNonNull(getter4, "getter4");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Calls a method
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Branch: checks a condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Returns a value to the caller
                    return result2.cast();
                // Calls a method
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Branch: checks a condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Returns a value to the caller
                    return result3.cast();
                // Calls a method
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Branch: checks a condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Returns a value to the caller
                    return result4.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Calls a method
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Branch: checks a condition
                if (result2 != null) return result2;
                // Calls a method
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Branch: checks a condition
                if (result3 != null) return result3;
                // Calls a method
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Branch: checks a condition
                if (result4 != null) return result4;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1   the name/key for {@link P1}
     * @param codec1  the codec for {@link P1}
     * @param getter1 the getter for {@link P1}
     * @param name2   the name/key for {@link P2}
     * @param codec2  the codec for {@link P2}
     * @param getter2 the getter for {@link P2}
     * @param name3   the name/key for {@link P3}
     * @param codec3  the codec for {@link P3}
     * @param getter3 the getter for {@link P3}
     * @param name4   the name/key for {@link P4}
     * @param codec4  the codec for {@link P4}
     * @param getter4 the getter for {@link P4}
     * @param name5   the name/key for {@link P5}
     * @param codec5  the codec for {@link P5}
     * @param getter5 the getter for {@link P5}
     * @param ctor    the constructor for {@link R}
     * @param <R>     the return type
     * @param <P1>    the first parameter type
     * @param <P2>    the second parameter type
     * @param <P3>    the third parameter type
     * @param <P4>    the fourth parameter type
     * @param <P5>    the fifth parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            String name2, Codec<P2> codec2, Function<? super R, ? extends P2> getter2,
            // Code statement
            String name3, Codec<P3> codec3, Function<? super R, ? extends P3> getter3,
            // Code statement
            String name4, Codec<P4> codec4, Function<? super R, ? extends P4> getter4,
            // Code statement
            String name5, Codec<P5> codec5, Function<? super R, ? extends P5> getter5,
            // Code statement
            F5<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(name2, "name2");
        // Calls a method
        Objects.requireNonNull(codec2, "codec2");
        // Calls a method
        Objects.requireNonNull(getter2, "getter2");
        // Calls a method
        Objects.requireNonNull(name3, "name3");
        // Calls a method
        Objects.requireNonNull(codec3, "codec3");
        // Calls a method
        Objects.requireNonNull(getter3, "getter3");
        // Calls a method
        Objects.requireNonNull(name4, "name4");
        // Calls a method
        Objects.requireNonNull(codec4, "codec4");
        // Calls a method
        Objects.requireNonNull(getter4, "getter4");
        // Calls a method
        Objects.requireNonNull(name5, "name5");
        // Calls a method
        Objects.requireNonNull(codec5, "codec5");
        // Calls a method
        Objects.requireNonNull(getter5, "getter5");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Calls a method
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Branch: checks a condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Returns a value to the caller
                    return result2.cast();
                // Calls a method
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Branch: checks a condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Returns a value to the caller
                    return result3.cast();
                // Calls a method
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Branch: checks a condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Returns a value to the caller
                    return result4.cast();
                // Calls a method
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Branch: checks a condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Returns a value to the caller
                    return result5.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Calls a method
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Branch: checks a condition
                if (result2 != null) return result2;
                // Calls a method
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Branch: checks a condition
                if (result3 != null) return result3;
                // Calls a method
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Branch: checks a condition
                if (result4 != null) return result4;
                // Calls a method
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Branch: checks a condition
                if (result5 != null) return result5;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1   the name/key for {@link P1}
     * @param codec1  the codec for {@link P1}
     * @param getter1 the getter for {@link P1}
     * @param name2   the name/key for {@link P2}
     * @param codec2  the codec for {@link P2}
     * @param getter2 the getter for {@link P2}
     * @param name3   the name/key for {@link P3}
     * @param codec3  the codec for {@link P3}
     * @param getter3 the getter for {@link P3}
     * @param name4   the name/key for {@link P4}
     * @param codec4  the codec for {@link P4}
     * @param getter4 the getter for {@link P4}
     * @param name5   the name/key for {@link P5}
     * @param codec5  the codec for {@link P5}
     * @param getter5 the getter for {@link P5}
     * @param name6   the name/key for {@link P6}
     * @param codec6  the codec for {@link P6}
     * @param getter6 the getter for {@link P6}
     * @param ctor    the constructor for {@link R}
     * @param <R>     the return type
     * @param <P1>    the first parameter type
     * @param <P2>    the second parameter type
     * @param <P3>    the third parameter type
     * @param <P4>    the fourth parameter type
     * @param <P5>    the fifth parameter type
     * @param <P6>    the sixth parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            String name2, Codec<P2> codec2, Function<? super R, ? extends P2> getter2,
            // Code statement
            String name3, Codec<P3> codec3, Function<? super R, ? extends P3> getter3,
            // Code statement
            String name4, Codec<P4> codec4, Function<? super R, ? extends P4> getter4,
            // Code statement
            String name5, Codec<P5> codec5, Function<? super R, ? extends P5> getter5,
            // Code statement
            String name6, Codec<P6> codec6, Function<? super R, ? extends P6> getter6,
            // Code statement
            F6<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(name2, "name2");
        // Calls a method
        Objects.requireNonNull(codec2, "codec2");
        // Calls a method
        Objects.requireNonNull(getter2, "getter2");
        // Calls a method
        Objects.requireNonNull(name3, "name3");
        // Calls a method
        Objects.requireNonNull(codec3, "codec3");
        // Calls a method
        Objects.requireNonNull(getter3, "getter3");
        // Calls a method
        Objects.requireNonNull(name4, "name4");
        // Calls a method
        Objects.requireNonNull(codec4, "codec4");
        // Calls a method
        Objects.requireNonNull(getter4, "getter4");
        // Calls a method
        Objects.requireNonNull(name5, "name5");
        // Calls a method
        Objects.requireNonNull(codec5, "codec5");
        // Calls a method
        Objects.requireNonNull(getter5, "getter5");
        // Calls a method
        Objects.requireNonNull(name6, "name6");
        // Calls a method
        Objects.requireNonNull(codec6, "codec6");
        // Calls a method
        Objects.requireNonNull(getter6, "getter6");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Calls a method
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Branch: checks a condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Returns a value to the caller
                    return result2.cast();
                // Calls a method
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Branch: checks a condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Returns a value to the caller
                    return result3.cast();
                // Calls a method
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Branch: checks a condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Returns a value to the caller
                    return result4.cast();
                // Calls a method
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Branch: checks a condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Returns a value to the caller
                    return result5.cast();
                // Calls a method
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Branch: checks a condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Returns a value to the caller
                    return result6.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Calls a method
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Branch: checks a condition
                if (result2 != null) return result2;
                // Calls a method
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Branch: checks a condition
                if (result3 != null) return result3;
                // Calls a method
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Branch: checks a condition
                if (result4 != null) return result4;
                // Calls a method
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Branch: checks a condition
                if (result5 != null) return result5;
                // Calls a method
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Branch: checks a condition
                if (result6 != null) return result6;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1   the name/key for {@link P1}
     * @param codec1  the codec for {@link P1}
     * @param getter1 the getter for {@link P1}
     * @param name2   the name/key for {@link P2}
     * @param codec2  the codec for {@link P2}
     * @param getter2 the getter for {@link P2}
     * @param name3   the name/key for {@link P3}
     * @param codec3  the codec for {@link P3}
     * @param getter3 the getter for {@link P3}
     * @param name4   the name/key for {@link P4}
     * @param codec4  the codec for {@link P4}
     * @param getter4 the getter for {@link P4}
     * @param name5   the name/key for {@link P5}
     * @param codec5  the codec for {@link P5}
     * @param getter5 the getter for {@link P5}
     * @param name6   the name/key for {@link P6}
     * @param codec6  the codec for {@link P6}
     * @param getter6 the getter for {@link P6}
     * @param name7   the name/key for {@link P7}
     * @param codec7  the codec for {@link P7}
     * @param getter7 the getter for {@link P7}
     * @param ctor    the constructor for {@link R}
     * @param <R>     the return type
     * @param <P1>    the first parameter type
     * @param <P2>    the second parameter type
     * @param <P3>    the third parameter type
     * @param <P4>    the fourth parameter type
     * @param <P5>    the fifth parameter type
     * @param <P6>    the sixth parameter type
     * @param <P7>    the seventh parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            String name2, Codec<P2> codec2, Function<? super R, ? extends P2> getter2,
            // Code statement
            String name3, Codec<P3> codec3, Function<? super R, ? extends P3> getter3,
            // Code statement
            String name4, Codec<P4> codec4, Function<? super R, ? extends P4> getter4,
            // Code statement
            String name5, Codec<P5> codec5, Function<? super R, ? extends P5> getter5,
            // Code statement
            String name6, Codec<P6> codec6, Function<? super R, ? extends P6> getter6,
            // Code statement
            String name7, Codec<P7> codec7, Function<? super R, ? extends P7> getter7,
            // Code statement
            F7<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(name2, "name2");
        // Calls a method
        Objects.requireNonNull(codec2, "codec2");
        // Calls a method
        Objects.requireNonNull(getter2, "getter2");
        // Calls a method
        Objects.requireNonNull(name3, "name3");
        // Calls a method
        Objects.requireNonNull(codec3, "codec3");
        // Calls a method
        Objects.requireNonNull(getter3, "getter3");
        // Calls a method
        Objects.requireNonNull(name4, "name4");
        // Calls a method
        Objects.requireNonNull(codec4, "codec4");
        // Calls a method
        Objects.requireNonNull(getter4, "getter4");
        // Calls a method
        Objects.requireNonNull(name5, "name5");
        // Calls a method
        Objects.requireNonNull(codec5, "codec5");
        // Calls a method
        Objects.requireNonNull(getter5, "getter5");
        // Calls a method
        Objects.requireNonNull(name6, "name6");
        // Calls a method
        Objects.requireNonNull(codec6, "codec6");
        // Calls a method
        Objects.requireNonNull(getter6, "getter6");
        // Calls a method
        Objects.requireNonNull(name7, "name7");
        // Calls a method
        Objects.requireNonNull(codec7, "codec7");
        // Calls a method
        Objects.requireNonNull(getter7, "getter7");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Calls a method
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Branch: checks a condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Returns a value to the caller
                    return result2.cast();
                // Calls a method
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Branch: checks a condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Returns a value to the caller
                    return result3.cast();
                // Calls a method
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Branch: checks a condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Returns a value to the caller
                    return result4.cast();
                // Calls a method
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Branch: checks a condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Returns a value to the caller
                    return result5.cast();
                // Calls a method
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Branch: checks a condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Returns a value to the caller
                    return result6.cast();
                // Calls a method
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Branch: checks a condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Returns a value to the caller
                    return result7.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Calls a method
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Branch: checks a condition
                if (result2 != null) return result2;
                // Calls a method
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Branch: checks a condition
                if (result3 != null) return result3;
                // Calls a method
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Branch: checks a condition
                if (result4 != null) return result4;
                // Calls a method
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Branch: checks a condition
                if (result5 != null) return result5;
                // Calls a method
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Branch: checks a condition
                if (result6 != null) return result6;
                // Calls a method
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Branch: checks a condition
                if (result7 != null) return result7;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }


    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1   the name/key for {@link P1}
     * @param codec1  the codec for {@link P1}
     * @param getter1 the getter for {@link P1}
     * @param name2   the name/key for {@link P2}
     * @param codec2  the codec for {@link P2}
     * @param getter2 the getter for {@link P2}
     * @param name3   the name/key for {@link P3}
     * @param codec3  the codec for {@link P3}
     * @param getter3 the getter for {@link P3}
     * @param name4   the name/key for {@link P4}
     * @param codec4  the codec for {@link P4}
     * @param getter4 the getter for {@link P4}
     * @param name5   the name/key for {@link P5}
     * @param codec5  the codec for {@link P5}
     * @param getter5 the getter for {@link P5}
     * @param name6   the name/key for {@link P6}
     * @param codec6  the codec for {@link P6}
     * @param getter6 the getter for {@link P6}
     * @param name7   the name/key for {@link P7}
     * @param codec7  the codec for {@link P7}
     * @param getter7 the getter for {@link P7}
     * @param name8   the name/key for {@link P8}
     * @param codec8  the codec for {@link P8}
     * @param getter8 the getter for {@link P8}
     * @param ctor    the constructor for {@link R}
     * @param <R>     the return type
     * @param <P1>    the first parameter type
     * @param <P2>    the second parameter type
     * @param <P3>    the third parameter type
     * @param <P4>    the fourth parameter type
     * @param <P5>    the fifth parameter type
     * @param <P6>    the sixth parameter type
     * @param <P7>    the seventh parameter type
     * @param <P8>    the eighth parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            String name2, Codec<P2> codec2, Function<? super R, ? extends P2> getter2,
            // Code statement
            String name3, Codec<P3> codec3, Function<? super R, ? extends P3> getter3,
            // Code statement
            String name4, Codec<P4> codec4, Function<? super R, ? extends P4> getter4,
            // Code statement
            String name5, Codec<P5> codec5, Function<? super R, ? extends P5> getter5,
            // Code statement
            String name6, Codec<P6> codec6, Function<? super R, ? extends P6> getter6,
            // Code statement
            String name7, Codec<P7> codec7, Function<? super R, ? extends P7> getter7,
            // Code statement
            String name8, Codec<P8> codec8, Function<? super R, ? extends P8> getter8,
            // Code statement
            F8<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(name2, "name2");
        // Calls a method
        Objects.requireNonNull(codec2, "codec2");
        // Calls a method
        Objects.requireNonNull(getter2, "getter2");
        // Calls a method
        Objects.requireNonNull(name3, "name3");
        // Calls a method
        Objects.requireNonNull(codec3, "codec3");
        // Calls a method
        Objects.requireNonNull(getter3, "getter3");
        // Calls a method
        Objects.requireNonNull(name4, "name4");
        // Calls a method
        Objects.requireNonNull(codec4, "codec4");
        // Calls a method
        Objects.requireNonNull(getter4, "getter4");
        // Calls a method
        Objects.requireNonNull(name5, "name5");
        // Calls a method
        Objects.requireNonNull(codec5, "codec5");
        // Calls a method
        Objects.requireNonNull(getter5, "getter5");
        // Calls a method
        Objects.requireNonNull(name6, "name6");
        // Calls a method
        Objects.requireNonNull(codec6, "codec6");
        // Calls a method
        Objects.requireNonNull(getter6, "getter6");
        // Calls a method
        Objects.requireNonNull(name7, "name7");
        // Calls a method
        Objects.requireNonNull(codec7, "codec7");
        // Calls a method
        Objects.requireNonNull(getter7, "getter7");
        // Calls a method
        Objects.requireNonNull(name8, "name8");
        // Calls a method
        Objects.requireNonNull(codec8, "codec8");
        // Calls a method
        Objects.requireNonNull(getter8, "getter8");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Calls a method
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Branch: checks a condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Returns a value to the caller
                    return result2.cast();
                // Calls a method
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Branch: checks a condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Returns a value to the caller
                    return result3.cast();
                // Calls a method
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Branch: checks a condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Returns a value to the caller
                    return result4.cast();
                // Calls a method
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Branch: checks a condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Returns a value to the caller
                    return result5.cast();
                // Calls a method
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Branch: checks a condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Returns a value to the caller
                    return result6.cast();
                // Calls a method
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Branch: checks a condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Returns a value to the caller
                    return result7.cast();
                // Calls a method
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Branch: checks a condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Returns a value to the caller
                    return result8.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Calls a method
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Branch: checks a condition
                if (result2 != null) return result2;
                // Calls a method
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Branch: checks a condition
                if (result3 != null) return result3;
                // Calls a method
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Branch: checks a condition
                if (result4 != null) return result4;
                // Calls a method
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Branch: checks a condition
                if (result5 != null) return result5;
                // Calls a method
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Branch: checks a condition
                if (result6 != null) return result6;
                // Calls a method
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Branch: checks a condition
                if (result7 != null) return result7;
                // Calls a method
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Branch: checks a condition
                if (result8 != null) return result8;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1   the name/key for {@link P1}
     * @param codec1  the codec for {@link P1}
     * @param getter1 the getter for {@link P1}
     * @param name2   the name/key for {@link P2}
     * @param codec2  the codec for {@link P2}
     * @param getter2 the getter for {@link P2}
     * @param name3   the name/key for {@link P3}
     * @param codec3  the codec for {@link P3}
     * @param getter3 the getter for {@link P3}
     * @param name4   the name/key for {@link P4}
     * @param codec4  the codec for {@link P4}
     * @param getter4 the getter for {@link P4}
     * @param name5   the name/key for {@link P5}
     * @param codec5  the codec for {@link P5}
     * @param getter5 the getter for {@link P5}
     * @param name6   the name/key for {@link P6}
     * @param codec6  the codec for {@link P6}
     * @param getter6 the getter for {@link P6}
     * @param name7   the name/key for {@link P7}
     * @param codec7  the codec for {@link P7}
     * @param getter7 the getter for {@link P7}
     * @param name8   the name/key for {@link P8}
     * @param codec8  the codec for {@link P8}
     * @param getter8 the getter for {@link P8}
     * @param name9   the name/key for {@link P9}
     * @param codec9  the codec for {@link P9}
     * @param getter9 the getter for {@link P9}
     * @param ctor    the constructor for {@link R}
     * @param <R>     the return type
     * @param <P1>    the first parameter type
     * @param <P2>    the second parameter type
     * @param <P3>    the third parameter type
     * @param <P4>    the fourth parameter type
     * @param <P5>    the fifth parameter type
     * @param <P6>    the sixth parameter type
     * @param <P7>    the seventh parameter type
     * @param <P8>    the eighth parameter type
     * @param <P9>    the ninth parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            String name2, Codec<P2> codec2, Function<? super R, ? extends P2> getter2,
            // Code statement
            String name3, Codec<P3> codec3, Function<? super R, ? extends P3> getter3,
            // Code statement
            String name4, Codec<P4> codec4, Function<? super R, ? extends P4> getter4,
            // Code statement
            String name5, Codec<P5> codec5, Function<? super R, ? extends P5> getter5,
            // Code statement
            String name6, Codec<P6> codec6, Function<? super R, ? extends P6> getter6,
            // Code statement
            String name7, Codec<P7> codec7, Function<? super R, ? extends P7> getter7,
            // Code statement
            String name8, Codec<P8> codec8, Function<? super R, ? extends P8> getter8,
            // Code statement
            String name9, Codec<P9> codec9, Function<? super R, ? extends P9> getter9,
            // Code statement
            F9<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(name2, "name2");
        // Calls a method
        Objects.requireNonNull(codec2, "codec2");
        // Calls a method
        Objects.requireNonNull(getter2, "getter2");
        // Calls a method
        Objects.requireNonNull(name3, "name3");
        // Calls a method
        Objects.requireNonNull(codec3, "codec3");
        // Calls a method
        Objects.requireNonNull(getter3, "getter3");
        // Calls a method
        Objects.requireNonNull(name4, "name4");
        // Calls a method
        Objects.requireNonNull(codec4, "codec4");
        // Calls a method
        Objects.requireNonNull(getter4, "getter4");
        // Calls a method
        Objects.requireNonNull(name5, "name5");
        // Calls a method
        Objects.requireNonNull(codec5, "codec5");
        // Calls a method
        Objects.requireNonNull(getter5, "getter5");
        // Calls a method
        Objects.requireNonNull(name6, "name6");
        // Calls a method
        Objects.requireNonNull(codec6, "codec6");
        // Calls a method
        Objects.requireNonNull(getter6, "getter6");
        // Calls a method
        Objects.requireNonNull(name7, "name7");
        // Calls a method
        Objects.requireNonNull(codec7, "codec7");
        // Calls a method
        Objects.requireNonNull(getter7, "getter7");
        // Calls a method
        Objects.requireNonNull(name8, "name8");
        // Calls a method
        Objects.requireNonNull(codec8, "codec8");
        // Calls a method
        Objects.requireNonNull(getter8, "getter8");
        // Calls a method
        Objects.requireNonNull(name9, "name9");
        // Calls a method
        Objects.requireNonNull(codec9, "codec9");
        // Calls a method
        Objects.requireNonNull(getter9, "getter9");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Calls a method
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Branch: checks a condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Returns a value to the caller
                    return result2.cast();
                // Calls a method
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Branch: checks a condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Returns a value to the caller
                    return result3.cast();
                // Calls a method
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Branch: checks a condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Returns a value to the caller
                    return result4.cast();
                // Calls a method
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Branch: checks a condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Returns a value to the caller
                    return result5.cast();
                // Calls a method
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Branch: checks a condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Returns a value to the caller
                    return result6.cast();
                // Calls a method
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Branch: checks a condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Returns a value to the caller
                    return result7.cast();
                // Calls a method
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Branch: checks a condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Returns a value to the caller
                    return result8.cast();
                // Calls a method
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Branch: checks a condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Returns a value to the caller
                    return result9.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Calls a method
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Branch: checks a condition
                if (result2 != null) return result2;
                // Calls a method
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Branch: checks a condition
                if (result3 != null) return result3;
                // Calls a method
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Branch: checks a condition
                if (result4 != null) return result4;
                // Calls a method
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Branch: checks a condition
                if (result5 != null) return result5;
                // Calls a method
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Branch: checks a condition
                if (result6 != null) return result6;
                // Calls a method
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Branch: checks a condition
                if (result7 != null) return result7;
                // Calls a method
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Branch: checks a condition
                if (result8 != null) return result8;
                // Calls a method
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Branch: checks a condition
                if (result9 != null) return result9;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1    the name/key for {@link P1}
     * @param codec1   the codec for {@link P1}
     * @param getter1  the getter for {@link P1}
     * @param name2    the name/key for {@link P2}
     * @param codec2   the codec for {@link P2}
     * @param getter2  the getter for {@link P2}
     * @param name3    the name/key for {@link P3}
     * @param codec3   the codec for {@link P3}
     * @param getter3  the getter for {@link P3}
     * @param name4    the name/key for {@link P4}
     * @param codec4   the codec for {@link P4}
     * @param getter4  the getter for {@link P4}
     * @param name5    the name/key for {@link P5}
     * @param codec5   the codec for {@link P5}
     * @param getter5  the getter for {@link P5}
     * @param name6    the name/key for {@link P6}
     * @param codec6   the codec for {@link P6}
     * @param getter6  the getter for {@link P6}
     * @param name7    the name/key for {@link P7}
     * @param codec7   the codec for {@link P7}
     * @param getter7  the getter for {@link P7}
     * @param name8    the name/key for {@link P8}
     * @param codec8   the codec for {@link P8}
     * @param getter8  the getter for {@link P8}
     * @param name9    the name/key for {@link P9}
     * @param codec9   the codec for {@link P9}
     * @param getter9  the getter for {@link P9}
     * @param name10   the name/key for {@link P10}
     * @param codec10  the codec for {@link P10}
     * @param getter10 the getter for {@link P10}
     * @param ctor     the constructor for {@link R}
     * @param <R>      the return type
     * @param <P1>     the first parameter type
     * @param <P2>     the second parameter type
     * @param <P3>     the third parameter type
     * @param <P4>     the fourth parameter type
     * @param <P5>     the fifth parameter type
     * @param <P6>     the sixth parameter type
     * @param <P7>     the seventh parameter type
     * @param <P8>     the eighth parameter type
     * @param <P9>     the ninth parameter type
     * @param <P10>    the tenth parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            String name2, Codec<P2> codec2, Function<? super R, ? extends P2> getter2,
            // Code statement
            String name3, Codec<P3> codec3, Function<? super R, ? extends P3> getter3,
            // Code statement
            String name4, Codec<P4> codec4, Function<? super R, ? extends P4> getter4,
            // Code statement
            String name5, Codec<P5> codec5, Function<? super R, ? extends P5> getter5,
            // Code statement
            String name6, Codec<P6> codec6, Function<? super R, ? extends P6> getter6,
            // Code statement
            String name7, Codec<P7> codec7, Function<? super R, ? extends P7> getter7,
            // Code statement
            String name8, Codec<P8> codec8, Function<? super R, ? extends P8> getter8,
            // Code statement
            String name9, Codec<P9> codec9, Function<? super R, ? extends P9> getter9,
            // Code statement
            String name10, Codec<P10> codec10, Function<? super R, ? extends P10> getter10,
            // Code statement
            F10<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(name2, "name2");
        // Calls a method
        Objects.requireNonNull(codec2, "codec2");
        // Calls a method
        Objects.requireNonNull(getter2, "getter2");
        // Calls a method
        Objects.requireNonNull(name3, "name3");
        // Calls a method
        Objects.requireNonNull(codec3, "codec3");
        // Calls a method
        Objects.requireNonNull(getter3, "getter3");
        // Calls a method
        Objects.requireNonNull(name4, "name4");
        // Calls a method
        Objects.requireNonNull(codec4, "codec4");
        // Calls a method
        Objects.requireNonNull(getter4, "getter4");
        // Calls a method
        Objects.requireNonNull(name5, "name5");
        // Calls a method
        Objects.requireNonNull(codec5, "codec5");
        // Calls a method
        Objects.requireNonNull(getter5, "getter5");
        // Calls a method
        Objects.requireNonNull(name6, "name6");
        // Calls a method
        Objects.requireNonNull(codec6, "codec6");
        // Calls a method
        Objects.requireNonNull(getter6, "getter6");
        // Calls a method
        Objects.requireNonNull(name7, "name7");
        // Calls a method
        Objects.requireNonNull(codec7, "codec7");
        // Calls a method
        Objects.requireNonNull(getter7, "getter7");
        // Calls a method
        Objects.requireNonNull(name8, "name8");
        // Calls a method
        Objects.requireNonNull(codec8, "codec8");
        // Calls a method
        Objects.requireNonNull(getter8, "getter8");
        // Calls a method
        Objects.requireNonNull(name9, "name9");
        // Calls a method
        Objects.requireNonNull(codec9, "codec9");
        // Calls a method
        Objects.requireNonNull(getter9, "getter9");
        // Calls a method
        Objects.requireNonNull(name10, "name10");
        // Calls a method
        Objects.requireNonNull(codec10, "codec10");
        // Calls a method
        Objects.requireNonNull(getter10, "getter10");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Calls a method
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Branch: checks a condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Returns a value to the caller
                    return result2.cast();
                // Calls a method
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Branch: checks a condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Returns a value to the caller
                    return result3.cast();
                // Calls a method
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Branch: checks a condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Returns a value to the caller
                    return result4.cast();
                // Calls a method
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Branch: checks a condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Returns a value to the caller
                    return result5.cast();
                // Calls a method
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Branch: checks a condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Returns a value to the caller
                    return result6.cast();
                // Calls a method
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Branch: checks a condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Returns a value to the caller
                    return result7.cast();
                // Calls a method
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Branch: checks a condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Returns a value to the caller
                    return result8.cast();
                // Calls a method
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Branch: checks a condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Returns a value to the caller
                    return result9.cast();
                // Calls a method
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Branch: checks a condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Returns a value to the caller
                    return result10.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Calls a method
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Branch: checks a condition
                if (result2 != null) return result2;
                // Calls a method
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Branch: checks a condition
                if (result3 != null) return result3;
                // Calls a method
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Branch: checks a condition
                if (result4 != null) return result4;
                // Calls a method
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Branch: checks a condition
                if (result5 != null) return result5;
                // Calls a method
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Branch: checks a condition
                if (result6 != null) return result6;
                // Calls a method
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Branch: checks a condition
                if (result7 != null) return result7;
                // Calls a method
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Branch: checks a condition
                if (result8 != null) return result8;
                // Calls a method
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Branch: checks a condition
                if (result9 != null) return result9;
                // Calls a method
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Branch: checks a condition
                if (result10 != null) return result10;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1    the name/key for {@link P1}
     * @param codec1   the codec for {@link P1}
     * @param getter1  the getter for {@link P1}
     * @param name2    the name/key for {@link P2}
     * @param codec2   the codec for {@link P2}
     * @param getter2  the getter for {@link P2}
     * @param name3    the name/key for {@link P3}
     * @param codec3   the codec for {@link P3}
     * @param getter3  the getter for {@link P3}
     * @param name4    the name/key for {@link P4}
     * @param codec4   the codec for {@link P4}
     * @param getter4  the getter for {@link P4}
     * @param name5    the name/key for {@link P5}
     * @param codec5   the codec for {@link P5}
     * @param getter5  the getter for {@link P5}
     * @param name6    the name/key for {@link P6}
     * @param codec6   the codec for {@link P6}
     * @param getter6  the getter for {@link P6}
     * @param name7    the name/key for {@link P7}
     * @param codec7   the codec for {@link P7}
     * @param getter7  the getter for {@link P7}
     * @param name8    the name/key for {@link P8}
     * @param codec8   the codec for {@link P8}
     * @param getter8  the getter for {@link P8}
     * @param name9    the name/key for {@link P9}
     * @param codec9   the codec for {@link P9}
     * @param getter9  the getter for {@link P9}
     * @param name10   the name/key for {@link P10}
     * @param codec10  the codec for {@link P10}
     * @param getter10 the getter for {@link P10}
     * @param name11   the name/key for {@link P11}
     * @param codec11  the codec for {@link P11}
     * @param getter11 the getter for {@link P11}
     * @param ctor     the constructor for {@link R}
     * @param <R>      the return type
     * @param <P1>     the first parameter type
     * @param <P2>     the second parameter type
     * @param <P3>     the third parameter type
     * @param <P4>     the fourth parameter type
     * @param <P5>     the fifth parameter type
     * @param <P6>     the sixth parameter type
     * @param <P7>     the seventh parameter type
     * @param <P8>     the eighth parameter type
     * @param <P9>     the ninth parameter type
     * @param <P10>    the tenth parameter type
     * @param <P11>    the eleventh parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            String name2, Codec<P2> codec2, Function<? super R, ? extends P2> getter2,
            // Code statement
            String name3, Codec<P3> codec3, Function<? super R, ? extends P3> getter3,
            // Code statement
            String name4, Codec<P4> codec4, Function<? super R, ? extends P4> getter4,
            // Code statement
            String name5, Codec<P5> codec5, Function<? super R, ? extends P5> getter5,
            // Code statement
            String name6, Codec<P6> codec6, Function<? super R, ? extends P6> getter6,
            // Code statement
            String name7, Codec<P7> codec7, Function<? super R, ? extends P7> getter7,
            // Code statement
            String name8, Codec<P8> codec8, Function<? super R, ? extends P8> getter8,
            // Code statement
            String name9, Codec<P9> codec9, Function<? super R, ? extends P9> getter9,
            // Code statement
            String name10, Codec<P10> codec10, Function<? super R, ? extends P10> getter10,
            // Code statement
            String name11, Codec<P11> codec11, Function<? super R, ? extends P11> getter11,
            // Code statement
            F11<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(name2, "name2");
        // Calls a method
        Objects.requireNonNull(codec2, "codec2");
        // Calls a method
        Objects.requireNonNull(getter2, "getter2");
        // Calls a method
        Objects.requireNonNull(name3, "name3");
        // Calls a method
        Objects.requireNonNull(codec3, "codec3");
        // Calls a method
        Objects.requireNonNull(getter3, "getter3");
        // Calls a method
        Objects.requireNonNull(name4, "name4");
        // Calls a method
        Objects.requireNonNull(codec4, "codec4");
        // Calls a method
        Objects.requireNonNull(getter4, "getter4");
        // Calls a method
        Objects.requireNonNull(name5, "name5");
        // Calls a method
        Objects.requireNonNull(codec5, "codec5");
        // Calls a method
        Objects.requireNonNull(getter5, "getter5");
        // Calls a method
        Objects.requireNonNull(name6, "name6");
        // Calls a method
        Objects.requireNonNull(codec6, "codec6");
        // Calls a method
        Objects.requireNonNull(getter6, "getter6");
        // Calls a method
        Objects.requireNonNull(name7, "name7");
        // Calls a method
        Objects.requireNonNull(codec7, "codec7");
        // Calls a method
        Objects.requireNonNull(getter7, "getter7");
        // Calls a method
        Objects.requireNonNull(name8, "name8");
        // Calls a method
        Objects.requireNonNull(codec8, "codec8");
        // Calls a method
        Objects.requireNonNull(getter8, "getter8");
        // Calls a method
        Objects.requireNonNull(name9, "name9");
        // Calls a method
        Objects.requireNonNull(codec9, "codec9");
        // Calls a method
        Objects.requireNonNull(getter9, "getter9");
        // Calls a method
        Objects.requireNonNull(name10, "name10");
        // Calls a method
        Objects.requireNonNull(codec10, "codec10");
        // Calls a method
        Objects.requireNonNull(getter10, "getter10");
        // Calls a method
        Objects.requireNonNull(name11, "name11");
        // Calls a method
        Objects.requireNonNull(codec11, "codec11");
        // Calls a method
        Objects.requireNonNull(getter11, "getter11");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Calls a method
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Branch: checks a condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Returns a value to the caller
                    return result2.cast();
                // Calls a method
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Branch: checks a condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Returns a value to the caller
                    return result3.cast();
                // Calls a method
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Branch: checks a condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Returns a value to the caller
                    return result4.cast();
                // Calls a method
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Branch: checks a condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Returns a value to the caller
                    return result5.cast();
                // Calls a method
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Branch: checks a condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Returns a value to the caller
                    return result6.cast();
                // Calls a method
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Branch: checks a condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Returns a value to the caller
                    return result7.cast();
                // Calls a method
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Branch: checks a condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Returns a value to the caller
                    return result8.cast();
                // Calls a method
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Branch: checks a condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Returns a value to the caller
                    return result9.cast();
                // Calls a method
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Branch: checks a condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Returns a value to the caller
                    return result10.cast();
                // Calls a method
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Branch: checks a condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Returns a value to the caller
                    return result11.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Calls a method
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Branch: checks a condition
                if (result2 != null) return result2;
                // Calls a method
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Branch: checks a condition
                if (result3 != null) return result3;
                // Calls a method
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Branch: checks a condition
                if (result4 != null) return result4;
                // Calls a method
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Branch: checks a condition
                if (result5 != null) return result5;
                // Calls a method
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Branch: checks a condition
                if (result6 != null) return result6;
                // Calls a method
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Branch: checks a condition
                if (result7 != null) return result7;
                // Calls a method
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Branch: checks a condition
                if (result8 != null) return result8;
                // Calls a method
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Branch: checks a condition
                if (result9 != null) return result9;
                // Calls a method
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Branch: checks a condition
                if (result10 != null) return result10;
                // Calls a method
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Branch: checks a condition
                if (result11 != null) return result11;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }


    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1    the name/key for {@link P1}
     * @param codec1   the codec for {@link P1}
     * @param getter1  the getter for {@link P1}
     * @param name2    the name/key for {@link P2}
     * @param codec2   the codec for {@link P2}
     * @param getter2  the getter for {@link P2}
     * @param name3    the name/key for {@link P3}
     * @param codec3   the codec for {@link P3}
     * @param getter3  the getter for {@link P3}
     * @param name4    the name/key for {@link P4}
     * @param codec4   the codec for {@link P4}
     * @param getter4  the getter for {@link P4}
     * @param name5    the name/key for {@link P5}
     * @param codec5   the codec for {@link P5}
     * @param getter5  the getter for {@link P5}
     * @param name6    the name/key for {@link P6}
     * @param codec6   the codec for {@link P6}
     * @param getter6  the getter for {@link P6}
     * @param name7    the name/key for {@link P7}
     * @param codec7   the codec for {@link P7}
     * @param getter7  the getter for {@link P7}
     * @param name8    the name/key for {@link P8}
     * @param codec8   the codec for {@link P8}
     * @param getter8  the getter for {@link P8}
     * @param name9    the name/key for {@link P9}
     * @param codec9   the codec for {@link P9}
     * @param getter9  the getter for {@link P9}
     * @param name10   the name/key for {@link P10}
     * @param codec10  the codec for {@link P10}
     * @param getter10 the getter for {@link P10}
     * @param name11   the name/key for {@link P11}
     * @param codec11  the codec for {@link P11}
     * @param getter11 the getter for {@link P11}
     * @param name12   the name/key for {@link P12}
     * @param codec12  the codec for {@link P12}
     * @param getter12 the getter for {@link P12}
     * @param ctor     the constructor for {@link R}
     * @param <R>      the return type
     * @param <P1>     the first parameter type
     * @param <P2>     the second parameter type
     * @param <P3>     the third parameter type
     * @param <P4>     the fourth parameter type
     * @param <P5>     the fifth parameter type
     * @param <P6>     the sixth parameter type
     * @param <P7>     the seventh parameter type
     * @param <P8>     the eighth parameter type
     * @param <P9>     the ninth parameter type
     * @param <P10>    the tenth parameter type
     * @param <P11>    the eleventh parameter type
     * @param <P12>    the twelfth parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            String name2, Codec<P2> codec2, Function<? super R, ? extends P2> getter2,
            // Code statement
            String name3, Codec<P3> codec3, Function<? super R, ? extends P3> getter3,
            // Code statement
            String name4, Codec<P4> codec4, Function<? super R, ? extends P4> getter4,
            // Code statement
            String name5, Codec<P5> codec5, Function<? super R, ? extends P5> getter5,
            // Code statement
            String name6, Codec<P6> codec6, Function<? super R, ? extends P6> getter6,
            // Code statement
            String name7, Codec<P7> codec7, Function<? super R, ? extends P7> getter7,
            // Code statement
            String name8, Codec<P8> codec8, Function<? super R, ? extends P8> getter8,
            // Code statement
            String name9, Codec<P9> codec9, Function<? super R, ? extends P9> getter9,
            // Code statement
            String name10, Codec<P10> codec10, Function<? super R, ? extends P10> getter10,
            // Code statement
            String name11, Codec<P11> codec11, Function<? super R, ? extends P11> getter11,
            // Code statement
            String name12, Codec<P12> codec12, Function<? super R, ? extends P12> getter12,
            // Code statement
            F12<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? super P12, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(name2, "name2");
        // Calls a method
        Objects.requireNonNull(codec2, "codec2");
        // Calls a method
        Objects.requireNonNull(getter2, "getter2");
        // Calls a method
        Objects.requireNonNull(name3, "name3");
        // Calls a method
        Objects.requireNonNull(codec3, "codec3");
        // Calls a method
        Objects.requireNonNull(getter3, "getter3");
        // Calls a method
        Objects.requireNonNull(name4, "name4");
        // Calls a method
        Objects.requireNonNull(codec4, "codec4");
        // Calls a method
        Objects.requireNonNull(getter4, "getter4");
        // Calls a method
        Objects.requireNonNull(name5, "name5");
        // Calls a method
        Objects.requireNonNull(codec5, "codec5");
        // Calls a method
        Objects.requireNonNull(getter5, "getter5");
        // Calls a method
        Objects.requireNonNull(name6, "name6");
        // Calls a method
        Objects.requireNonNull(codec6, "codec6");
        // Calls a method
        Objects.requireNonNull(getter6, "getter6");
        // Calls a method
        Objects.requireNonNull(name7, "name7");
        // Calls a method
        Objects.requireNonNull(codec7, "codec7");
        // Calls a method
        Objects.requireNonNull(getter7, "getter7");
        // Calls a method
        Objects.requireNonNull(name8, "name8");
        // Calls a method
        Objects.requireNonNull(codec8, "codec8");
        // Calls a method
        Objects.requireNonNull(getter8, "getter8");
        // Calls a method
        Objects.requireNonNull(name9, "name9");
        // Calls a method
        Objects.requireNonNull(codec9, "codec9");
        // Calls a method
        Objects.requireNonNull(getter9, "getter9");
        // Calls a method
        Objects.requireNonNull(name10, "name10");
        // Calls a method
        Objects.requireNonNull(codec10, "codec10");
        // Calls a method
        Objects.requireNonNull(getter10, "getter10");
        // Calls a method
        Objects.requireNonNull(name11, "name11");
        // Calls a method
        Objects.requireNonNull(codec11, "codec11");
        // Calls a method
        Objects.requireNonNull(getter11, "getter11");
        // Calls a method
        Objects.requireNonNull(name12, "name12");
        // Calls a method
        Objects.requireNonNull(codec12, "codec12");
        // Calls a method
        Objects.requireNonNull(getter12, "getter12");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Calls a method
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Branch: checks a condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Returns a value to the caller
                    return result2.cast();
                // Calls a method
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Branch: checks a condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Returns a value to the caller
                    return result3.cast();
                // Calls a method
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Branch: checks a condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Returns a value to the caller
                    return result4.cast();
                // Calls a method
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Branch: checks a condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Returns a value to the caller
                    return result5.cast();
                // Calls a method
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Branch: checks a condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Returns a value to the caller
                    return result6.cast();
                // Calls a method
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Branch: checks a condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Returns a value to the caller
                    return result7.cast();
                // Calls a method
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Branch: checks a condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Returns a value to the caller
                    return result8.cast();
                // Calls a method
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Branch: checks a condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Returns a value to the caller
                    return result9.cast();
                // Calls a method
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Branch: checks a condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Returns a value to the caller
                    return result10.cast();
                // Calls a method
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Branch: checks a condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Returns a value to the caller
                    return result11.cast();
                // Calls a method
                final Result<P12> result12 = get(coder, codec12, name12, map);
                // Branch: checks a condition
                if (!(result12 instanceof Result.Ok(P12 value12)))
                    // Returns a value to the caller
                    return result12.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Calls a method
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Branch: checks a condition
                if (result2 != null) return result2;
                // Calls a method
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Branch: checks a condition
                if (result3 != null) return result3;
                // Calls a method
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Branch: checks a condition
                if (result4 != null) return result4;
                // Calls a method
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Branch: checks a condition
                if (result5 != null) return result5;
                // Calls a method
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Branch: checks a condition
                if (result6 != null) return result6;
                // Calls a method
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Branch: checks a condition
                if (result7 != null) return result7;
                // Calls a method
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Branch: checks a condition
                if (result8 != null) return result8;
                // Calls a method
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Branch: checks a condition
                if (result9 != null) return result9;
                // Calls a method
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Branch: checks a condition
                if (result10 != null) return result10;
                // Calls a method
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Branch: checks a condition
                if (result11 != null) return result11;
                // Calls a method
                final Result<D> result12 = put(coder, codec12, map, name12, getter12.apply(value));
                // Branch: checks a condition
                if (result12 != null) return result12;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1    the name/key for {@link P1}
     * @param codec1   the codec for {@link P1}
     * @param getter1  the getter for {@link P1}
     * @param name2    the name/key for {@link P2}
     * @param codec2   the codec for {@link P2}
     * @param getter2  the getter for {@link P2}
     * @param name3    the name/key for {@link P3}
     * @param codec3   the codec for {@link P3}
     * @param getter3  the getter for {@link P3}
     * @param name4    the name/key for {@link P4}
     * @param codec4   the codec for {@link P4}
     * @param getter4  the getter for {@link P4}
     * @param name5    the name/key for {@link P5}
     * @param codec5   the codec for {@link P5}
     * @param getter5  the getter for {@link P5}
     * @param name6    the name/key for {@link P6}
     * @param codec6   the codec for {@link P6}
     * @param getter6  the getter for {@link P6}
     * @param name7    the name/key for {@link P7}
     * @param codec7   the codec for {@link P7}
     * @param getter7  the getter for {@link P7}
     * @param name8    the name/key for {@link P8}
     * @param codec8   the codec for {@link P8}
     * @param getter8  the getter for {@link P8}
     * @param name9    the name/key for {@link P9}
     * @param codec9   the codec for {@link P9}
     * @param getter9  the getter for {@link P9}
     * @param name10   the name/key for {@link P10}
     * @param codec10  the codec for {@link P10}
     * @param getter10 the getter for {@link P10}
     * @param name11   the name/key for {@link P11}
     * @param codec11  the codec for {@link P11}
     * @param getter11 the getter for {@link P11}
     * @param name12   the name/key for {@link P12}
     * @param codec12  the codec for {@link P12}
     * @param getter12 the getter for {@link P12}
     * @param name13   the name/key for {@link P13}
     * @param codec13  the codec for {@link P13}
     * @param getter13 the getter for {@link P13}
     * @param ctor     the constructor for {@link R}
     * @param <R>      the return type
     * @param <P1>     the first parameter type
     * @param <P2>     the second parameter type
     * @param <P3>     the third parameter type
     * @param <P4>     the fourth parameter type
     * @param <P5>     the fifth parameter type
     * @param <P6>     the sixth parameter type
     * @param <P7>     the seventh parameter type
     * @param <P8>     the eighth parameter type
     * @param <P9>     the ninth parameter type
     * @param <P10>    the tenth parameter type
     * @param <P11>    the eleventh parameter type
     * @param <P12>    the twelfth parameter type
     * @param <P13>    the thirteenth parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            String name2, Codec<P2> codec2, Function<? super R, ? extends P2> getter2,
            // Code statement
            String name3, Codec<P3> codec3, Function<? super R, ? extends P3> getter3,
            // Code statement
            String name4, Codec<P4> codec4, Function<? super R, ? extends P4> getter4,
            // Code statement
            String name5, Codec<P5> codec5, Function<? super R, ? extends P5> getter5,
            // Code statement
            String name6, Codec<P6> codec6, Function<? super R, ? extends P6> getter6,
            // Code statement
            String name7, Codec<P7> codec7, Function<? super R, ? extends P7> getter7,
            // Code statement
            String name8, Codec<P8> codec8, Function<? super R, ? extends P8> getter8,
            // Code statement
            String name9, Codec<P9> codec9, Function<? super R, ? extends P9> getter9,
            // Code statement
            String name10, Codec<P10> codec10, Function<? super R, ? extends P10> getter10,
            // Code statement
            String name11, Codec<P11> codec11, Function<? super R, ? extends P11> getter11,
            // Code statement
            String name12, Codec<P12> codec12, Function<? super R, ? extends P12> getter12,
            // Code statement
            String name13, Codec<P13> codec13, Function<? super R, ? extends P13> getter13,
            // Code statement
            F13<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? super P12, ? super P13, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(name2, "name2");
        // Calls a method
        Objects.requireNonNull(codec2, "codec2");
        // Calls a method
        Objects.requireNonNull(getter2, "getter2");
        // Calls a method
        Objects.requireNonNull(name3, "name3");
        // Calls a method
        Objects.requireNonNull(codec3, "codec3");
        // Calls a method
        Objects.requireNonNull(getter3, "getter3");
        // Calls a method
        Objects.requireNonNull(name4, "name4");
        // Calls a method
        Objects.requireNonNull(codec4, "codec4");
        // Calls a method
        Objects.requireNonNull(getter4, "getter4");
        // Calls a method
        Objects.requireNonNull(name5, "name5");
        // Calls a method
        Objects.requireNonNull(codec5, "codec5");
        // Calls a method
        Objects.requireNonNull(getter5, "getter5");
        // Calls a method
        Objects.requireNonNull(name6, "name6");
        // Calls a method
        Objects.requireNonNull(codec6, "codec6");
        // Calls a method
        Objects.requireNonNull(getter6, "getter6");
        // Calls a method
        Objects.requireNonNull(name7, "name7");
        // Calls a method
        Objects.requireNonNull(codec7, "codec7");
        // Calls a method
        Objects.requireNonNull(getter7, "getter7");
        // Calls a method
        Objects.requireNonNull(name8, "name8");
        // Calls a method
        Objects.requireNonNull(codec8, "codec8");
        // Calls a method
        Objects.requireNonNull(getter8, "getter8");
        // Calls a method
        Objects.requireNonNull(name9, "name9");
        // Calls a method
        Objects.requireNonNull(codec9, "codec9");
        // Calls a method
        Objects.requireNonNull(getter9, "getter9");
        // Calls a method
        Objects.requireNonNull(name10, "name10");
        // Calls a method
        Objects.requireNonNull(codec10, "codec10");
        // Calls a method
        Objects.requireNonNull(getter10, "getter10");
        // Calls a method
        Objects.requireNonNull(name11, "name11");
        // Calls a method
        Objects.requireNonNull(codec11, "codec11");
        // Calls a method
        Objects.requireNonNull(getter11, "getter11");
        // Calls a method
        Objects.requireNonNull(name12, "name12");
        // Calls a method
        Objects.requireNonNull(codec12, "codec12");
        // Calls a method
        Objects.requireNonNull(getter12, "getter12");
        // Calls a method
        Objects.requireNonNull(name13, "name13");
        // Calls a method
        Objects.requireNonNull(codec13, "codec13");
        // Calls a method
        Objects.requireNonNull(getter13, "getter13");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Calls a method
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Branch: checks a condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Returns a value to the caller
                    return result2.cast();
                // Calls a method
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Branch: checks a condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Returns a value to the caller
                    return result3.cast();
                // Calls a method
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Branch: checks a condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Returns a value to the caller
                    return result4.cast();
                // Calls a method
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Branch: checks a condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Returns a value to the caller
                    return result5.cast();
                // Calls a method
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Branch: checks a condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Returns a value to the caller
                    return result6.cast();
                // Calls a method
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Branch: checks a condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Returns a value to the caller
                    return result7.cast();
                // Calls a method
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Branch: checks a condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Returns a value to the caller
                    return result8.cast();
                // Calls a method
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Branch: checks a condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Returns a value to the caller
                    return result9.cast();
                // Calls a method
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Branch: checks a condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Returns a value to the caller
                    return result10.cast();
                // Calls a method
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Branch: checks a condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Returns a value to the caller
                    return result11.cast();
                // Calls a method
                final Result<P12> result12 = get(coder, codec12, name12, map);
                // Branch: checks a condition
                if (!(result12 instanceof Result.Ok(P12 value12)))
                    // Returns a value to the caller
                    return result12.cast();
                // Calls a method
                final Result<P13> result13 = get(coder, codec13, name13, map);
                // Branch: checks a condition
                if (!(result13 instanceof Result.Ok(P13 value13)))
                    // Returns a value to the caller
                    return result13.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Calls a method
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Branch: checks a condition
                if (result2 != null) return result2;
                // Calls a method
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Branch: checks a condition
                if (result3 != null) return result3;
                // Calls a method
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Branch: checks a condition
                if (result4 != null) return result4;
                // Calls a method
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Branch: checks a condition
                if (result5 != null) return result5;
                // Calls a method
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Branch: checks a condition
                if (result6 != null) return result6;
                // Calls a method
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Branch: checks a condition
                if (result7 != null) return result7;
                // Calls a method
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Branch: checks a condition
                if (result8 != null) return result8;
                // Calls a method
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Branch: checks a condition
                if (result9 != null) return result9;
                // Calls a method
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Branch: checks a condition
                if (result10 != null) return result10;
                // Calls a method
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Branch: checks a condition
                if (result11 != null) return result11;
                // Calls a method
                final Result<D> result12 = put(coder, codec12, map, name12, getter12.apply(value));
                // Branch: checks a condition
                if (result12 != null) return result12;
                // Calls a method
                final Result<D> result13 = put(coder, codec13, map, name13, getter13.apply(value));
                // Branch: checks a condition
                if (result13 != null) return result13;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1    the name/key for {@link P1}
     * @param codec1   the codec for {@link P1}
     * @param getter1  the getter for {@link P1}
     * @param name2    the name/key for {@link P2}
     * @param codec2   the codec for {@link P2}
     * @param getter2  the getter for {@link P2}
     * @param name3    the name/key for {@link P3}
     * @param codec3   the codec for {@link P3}
     * @param getter3  the getter for {@link P3}
     * @param name4    the name/key for {@link P4}
     * @param codec4   the codec for {@link P4}
     * @param getter4  the getter for {@link P4}
     * @param name5    the name/key for {@link P5}
     * @param codec5   the codec for {@link P5}
     * @param getter5  the getter for {@link P5}
     * @param name6    the name/key for {@link P6}
     * @param codec6   the codec for {@link P6}
     * @param getter6  the getter for {@link P6}
     * @param name7    the name/key for {@link P7}
     * @param codec7   the codec for {@link P7}
     * @param getter7  the getter for {@link P7}
     * @param name8    the name/key for {@link P8}
     * @param codec8   the codec for {@link P8}
     * @param getter8  the getter for {@link P8}
     * @param name9    the name/key for {@link P9}
     * @param codec9   the codec for {@link P9}
     * @param getter9  the getter for {@link P9}
     * @param name10   the name/key for {@link P10}
     * @param codec10  the codec for {@link P10}
     * @param getter10 the getter for {@link P10}
     * @param name11   the name/key for {@link P11}
     * @param codec11  the codec for {@link P11}
     * @param getter11 the getter for {@link P11}
     * @param name12   the name/key for {@link P12}
     * @param codec12  the codec for {@link P12}
     * @param getter12 the getter for {@link P12}
     * @param name13   the name/key for {@link P13}
     * @param codec13  the codec for {@link P13}
     * @param getter13 the getter for {@link P13}
     * @param name14   the name/key for {@link P14}
     * @param codec14  the codec for {@link P14}
     * @param getter14 the getter for {@link P14}
     * @param ctor     the constructor for {@link R}
     * @param <R>      the return type
     * @param <P1>     the first parameter type
     * @param <P2>     the second parameter type
     * @param <P3>     the third parameter type
     * @param <P4>     the fourth parameter type
     * @param <P5>     the fifth parameter type
     * @param <P6>     the sixth parameter type
     * @param <P7>     the seventh parameter type
     * @param <P8>     the eighth parameter type
     * @param <P9>     the ninth parameter type
     * @param <P10>    the tenth parameter type
     * @param <P11>    the eleventh parameter type
     * @param <P12>    the twelfth parameter type
     * @param <P13>    the thirteenth parameter type
     * @param <P14>    the fourteenth parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            String name2, Codec<P2> codec2, Function<? super R, ? extends P2> getter2,
            // Code statement
            String name3, Codec<P3> codec3, Function<? super R, ? extends P3> getter3,
            // Code statement
            String name4, Codec<P4> codec4, Function<? super R, ? extends P4> getter4,
            // Code statement
            String name5, Codec<P5> codec5, Function<? super R, ? extends P5> getter5,
            // Code statement
            String name6, Codec<P6> codec6, Function<? super R, ? extends P6> getter6,
            // Code statement
            String name7, Codec<P7> codec7, Function<? super R, ? extends P7> getter7,
            // Code statement
            String name8, Codec<P8> codec8, Function<? super R, ? extends P8> getter8,
            // Code statement
            String name9, Codec<P9> codec9, Function<? super R, ? extends P9> getter9,
            // Code statement
            String name10, Codec<P10> codec10, Function<? super R, ? extends P10> getter10,
            // Code statement
            String name11, Codec<P11> codec11, Function<? super R, ? extends P11> getter11,
            // Code statement
            String name12, Codec<P12> codec12, Function<? super R, ? extends P12> getter12,
            // Code statement
            String name13, Codec<P13> codec13, Function<? super R, ? extends P13> getter13,
            // Code statement
            String name14, Codec<P14> codec14, Function<? super R, ? extends P14> getter14,
            // Code statement
            F14<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? super P12, ? super P13, ? super P14, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(name2, "name2");
        // Calls a method
        Objects.requireNonNull(codec2, "codec2");
        // Calls a method
        Objects.requireNonNull(getter2, "getter2");
        // Calls a method
        Objects.requireNonNull(name3, "name3");
        // Calls a method
        Objects.requireNonNull(codec3, "codec3");
        // Calls a method
        Objects.requireNonNull(getter3, "getter3");
        // Calls a method
        Objects.requireNonNull(name4, "name4");
        // Calls a method
        Objects.requireNonNull(codec4, "codec4");
        // Calls a method
        Objects.requireNonNull(getter4, "getter4");
        // Calls a method
        Objects.requireNonNull(name5, "name5");
        // Calls a method
        Objects.requireNonNull(codec5, "codec5");
        // Calls a method
        Objects.requireNonNull(getter5, "getter5");
        // Calls a method
        Objects.requireNonNull(name6, "name6");
        // Calls a method
        Objects.requireNonNull(codec6, "codec6");
        // Calls a method
        Objects.requireNonNull(getter6, "getter6");
        // Calls a method
        Objects.requireNonNull(name7, "name7");
        // Calls a method
        Objects.requireNonNull(codec7, "codec7");
        // Calls a method
        Objects.requireNonNull(getter7, "getter7");
        // Calls a method
        Objects.requireNonNull(name8, "name8");
        // Calls a method
        Objects.requireNonNull(codec8, "codec8");
        // Calls a method
        Objects.requireNonNull(getter8, "getter8");
        // Calls a method
        Objects.requireNonNull(name9, "name9");
        // Calls a method
        Objects.requireNonNull(codec9, "codec9");
        // Calls a method
        Objects.requireNonNull(getter9, "getter9");
        // Calls a method
        Objects.requireNonNull(name10, "name10");
        // Calls a method
        Objects.requireNonNull(codec10, "codec10");
        // Calls a method
        Objects.requireNonNull(getter10, "getter10");
        // Calls a method
        Objects.requireNonNull(name11, "name11");
        // Calls a method
        Objects.requireNonNull(codec11, "codec11");
        // Calls a method
        Objects.requireNonNull(getter11, "getter11");
        // Calls a method
        Objects.requireNonNull(name12, "name12");
        // Calls a method
        Objects.requireNonNull(codec12, "codec12");
        // Calls a method
        Objects.requireNonNull(getter12, "getter12");
        // Calls a method
        Objects.requireNonNull(name13, "name13");
        // Calls a method
        Objects.requireNonNull(codec13, "codec13");
        // Calls a method
        Objects.requireNonNull(getter13, "getter13");
        // Calls a method
        Objects.requireNonNull(name14, "name14");
        // Calls a method
        Objects.requireNonNull(codec14, "codec14");
        // Calls a method
        Objects.requireNonNull(getter14, "getter14");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Calls a method
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Branch: checks a condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Returns a value to the caller
                    return result2.cast();
                // Calls a method
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Branch: checks a condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Returns a value to the caller
                    return result3.cast();
                // Calls a method
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Branch: checks a condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Returns a value to the caller
                    return result4.cast();
                // Calls a method
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Branch: checks a condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Returns a value to the caller
                    return result5.cast();
                // Calls a method
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Branch: checks a condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Returns a value to the caller
                    return result6.cast();
                // Calls a method
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Branch: checks a condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Returns a value to the caller
                    return result7.cast();
                // Calls a method
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Branch: checks a condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Returns a value to the caller
                    return result8.cast();
                // Calls a method
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Branch: checks a condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Returns a value to the caller
                    return result9.cast();
                // Calls a method
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Branch: checks a condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Returns a value to the caller
                    return result10.cast();
                // Calls a method
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Branch: checks a condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Returns a value to the caller
                    return result11.cast();
                // Calls a method
                final Result<P12> result12 = get(coder, codec12, name12, map);
                // Branch: checks a condition
                if (!(result12 instanceof Result.Ok(P12 value12)))
                    // Returns a value to the caller
                    return result12.cast();
                // Calls a method
                final Result<P13> result13 = get(coder, codec13, name13, map);
                // Branch: checks a condition
                if (!(result13 instanceof Result.Ok(P13 value13)))
                    // Returns a value to the caller
                    return result13.cast();
                // Calls a method
                final Result<P14> result14 = get(coder, codec14, name14, map);
                // Branch: checks a condition
                if (!(result14 instanceof Result.Ok(P14 value14)))
                    // Returns a value to the caller
                    return result14.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Calls a method
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Branch: checks a condition
                if (result2 != null) return result2;
                // Calls a method
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Branch: checks a condition
                if (result3 != null) return result3;
                // Calls a method
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Branch: checks a condition
                if (result4 != null) return result4;
                // Calls a method
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Branch: checks a condition
                if (result5 != null) return result5;
                // Calls a method
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Branch: checks a condition
                if (result6 != null) return result6;
                // Calls a method
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Branch: checks a condition
                if (result7 != null) return result7;
                // Calls a method
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Branch: checks a condition
                if (result8 != null) return result8;
                // Calls a method
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Branch: checks a condition
                if (result9 != null) return result9;
                // Calls a method
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Branch: checks a condition
                if (result10 != null) return result10;
                // Calls a method
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Branch: checks a condition
                if (result11 != null) return result11;
                // Calls a method
                final Result<D> result12 = put(coder, codec12, map, name12, getter12.apply(value));
                // Branch: checks a condition
                if (result12 != null) return result12;
                // Calls a method
                final Result<D> result13 = put(coder, codec13, map, name13, getter13.apply(value));
                // Branch: checks a condition
                if (result13 != null) return result13;
                // Calls a method
                final Result<D> result14 = put(coder, codec14, map, name14, getter14.apply(value));
                // Branch: checks a condition
                if (result14 != null) return result14;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1    the name/key for {@link P1}
     * @param codec1   the codec for {@link P1}
     * @param getter1  the getter for {@link P1}
     * @param name2    the name/key for {@link P2}
     * @param codec2   the codec for {@link P2}
     * @param getter2  the getter for {@link P2}
     * @param name3    the name/key for {@link P3}
     * @param codec3   the codec for {@link P3}
     * @param getter3  the getter for {@link P3}
     * @param name4    the name/key for {@link P4}
     * @param codec4   the codec for {@link P4}
     * @param getter4  the getter for {@link P4}
     * @param name5    the name/key for {@link P5}
     * @param codec5   the codec for {@link P5}
     * @param getter5  the getter for {@link P5}
     * @param name6    the name/key for {@link P6}
     * @param codec6   the codec for {@link P6}
     * @param getter6  the getter for {@link P6}
     * @param name7    the name/key for {@link P7}
     * @param codec7   the codec for {@link P7}
     * @param getter7  the getter for {@link P7}
     * @param name8    the name/key for {@link P8}
     * @param codec8   the codec for {@link P8}
     * @param getter8  the getter for {@link P8}
     * @param name9    the name/key for {@link P9}
     * @param codec9   the codec for {@link P9}
     * @param getter9  the getter for {@link P9}
     * @param name10   the name/key for {@link P10}
     * @param codec10  the codec for {@link P10}
     * @param getter10 the getter for {@link P10}
     * @param name11   the name/key for {@link P11}
     * @param codec11  the codec for {@link P11}
     * @param getter11 the getter for {@link P11}
     * @param name12   the name/key for {@link P12}
     * @param codec12  the codec for {@link P12}
     * @param getter12 the getter for {@link P12}
     * @param name13   the name/key for {@link P13}
     * @param codec13  the codec for {@link P13}
     * @param getter13 the getter for {@link P13}
     * @param name14   the name/key for {@link P14}
     * @param codec14  the codec for {@link P14}
     * @param getter14 the getter for {@link P14}
     * @param name15   the name/key for {@link P15}
     * @param codec15  the codec for {@link P15}
     * @param getter15 the getter for {@link P15}
     * @param ctor     the constructor for {@link R}
     * @param <R>      the return type
     * @param <P1>     the first parameter type
     * @param <P2>     the second parameter type
     * @param <P3>     the third parameter type
     * @param <P4>     the fourth parameter type
     * @param <P5>     the fifth parameter type
     * @param <P6>     the sixth parameter type
     * @param <P7>     the seventh parameter type
     * @param <P8>     the eighth parameter type
     * @param <P9>     the ninth parameter type
     * @param <P10>    the tenth parameter type
     * @param <P11>    the eleventh parameter type
     * @param <P12>    the twelfth parameter type
     * @param <P13>    the thirteenth parameter type
     * @param <P14>    the fourteenth parameter type
     * @param <P15>    the fifteenth parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            String name2, Codec<P2> codec2, Function<? super R, ? extends P2> getter2,
            // Code statement
            String name3, Codec<P3> codec3, Function<? super R, ? extends P3> getter3,
            // Code statement
            String name4, Codec<P4> codec4, Function<? super R, ? extends P4> getter4,
            // Code statement
            String name5, Codec<P5> codec5, Function<? super R, ? extends P5> getter5,
            // Code statement
            String name6, Codec<P6> codec6, Function<? super R, ? extends P6> getter6,
            // Code statement
            String name7, Codec<P7> codec7, Function<? super R, ? extends P7> getter7,
            // Code statement
            String name8, Codec<P8> codec8, Function<? super R, ? extends P8> getter8,
            // Code statement
            String name9, Codec<P9> codec9, Function<? super R, ? extends P9> getter9,
            // Code statement
            String name10, Codec<P10> codec10, Function<? super R, ? extends P10> getter10,
            // Code statement
            String name11, Codec<P11> codec11, Function<? super R, ? extends P11> getter11,
            // Code statement
            String name12, Codec<P12> codec12, Function<? super R, ? extends P12> getter12,
            // Code statement
            String name13, Codec<P13> codec13, Function<? super R, ? extends P13> getter13,
            // Code statement
            String name14, Codec<P14> codec14, Function<? super R, ? extends P14> getter14,
            // Code statement
            String name15, Codec<P15> codec15, Function<? super R, ? extends P15> getter15,
            // Code statement
            F15<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? super P12, ? super P13, ? super P14, ? super P15, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(name2, "name2");
        // Calls a method
        Objects.requireNonNull(codec2, "codec2");
        // Calls a method
        Objects.requireNonNull(getter2, "getter2");
        // Calls a method
        Objects.requireNonNull(name3, "name3");
        // Calls a method
        Objects.requireNonNull(codec3, "codec3");
        // Calls a method
        Objects.requireNonNull(getter3, "getter3");
        // Calls a method
        Objects.requireNonNull(name4, "name4");
        // Calls a method
        Objects.requireNonNull(codec4, "codec4");
        // Calls a method
        Objects.requireNonNull(getter4, "getter4");
        // Calls a method
        Objects.requireNonNull(name5, "name5");
        // Calls a method
        Objects.requireNonNull(codec5, "codec5");
        // Calls a method
        Objects.requireNonNull(getter5, "getter5");
        // Calls a method
        Objects.requireNonNull(name6, "name6");
        // Calls a method
        Objects.requireNonNull(codec6, "codec6");
        // Calls a method
        Objects.requireNonNull(getter6, "getter6");
        // Calls a method
        Objects.requireNonNull(name7, "name7");
        // Calls a method
        Objects.requireNonNull(codec7, "codec7");
        // Calls a method
        Objects.requireNonNull(getter7, "getter7");
        // Calls a method
        Objects.requireNonNull(name8, "name8");
        // Calls a method
        Objects.requireNonNull(codec8, "codec8");
        // Calls a method
        Objects.requireNonNull(getter8, "getter8");
        // Calls a method
        Objects.requireNonNull(name9, "name9");
        // Calls a method
        Objects.requireNonNull(codec9, "codec9");
        // Calls a method
        Objects.requireNonNull(getter9, "getter9");
        // Calls a method
        Objects.requireNonNull(name10, "name10");
        // Calls a method
        Objects.requireNonNull(codec10, "codec10");
        // Calls a method
        Objects.requireNonNull(getter10, "getter10");
        // Calls a method
        Objects.requireNonNull(name11, "name11");
        // Calls a method
        Objects.requireNonNull(codec11, "codec11");
        // Calls a method
        Objects.requireNonNull(getter11, "getter11");
        // Calls a method
        Objects.requireNonNull(name12, "name12");
        // Calls a method
        Objects.requireNonNull(codec12, "codec12");
        // Calls a method
        Objects.requireNonNull(getter12, "getter12");
        // Calls a method
        Objects.requireNonNull(name13, "name13");
        // Calls a method
        Objects.requireNonNull(codec13, "codec13");
        // Calls a method
        Objects.requireNonNull(getter13, "getter13");
        // Calls a method
        Objects.requireNonNull(name14, "name14");
        // Calls a method
        Objects.requireNonNull(codec14, "codec14");
        // Calls a method
        Objects.requireNonNull(getter14, "getter14");
        // Calls a method
        Objects.requireNonNull(name15, "name15");
        // Calls a method
        Objects.requireNonNull(codec15, "codec15");
        // Calls a method
        Objects.requireNonNull(getter15, "getter15");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Calls a method
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Branch: checks a condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Returns a value to the caller
                    return result2.cast();
                // Calls a method
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Branch: checks a condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Returns a value to the caller
                    return result3.cast();
                // Calls a method
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Branch: checks a condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Returns a value to the caller
                    return result4.cast();
                // Calls a method
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Branch: checks a condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Returns a value to the caller
                    return result5.cast();
                // Calls a method
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Branch: checks a condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Returns a value to the caller
                    return result6.cast();
                // Calls a method
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Branch: checks a condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Returns a value to the caller
                    return result7.cast();
                // Calls a method
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Branch: checks a condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Returns a value to the caller
                    return result8.cast();
                // Calls a method
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Branch: checks a condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Returns a value to the caller
                    return result9.cast();
                // Calls a method
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Branch: checks a condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Returns a value to the caller
                    return result10.cast();
                // Calls a method
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Branch: checks a condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Returns a value to the caller
                    return result11.cast();
                // Calls a method
                final Result<P12> result12 = get(coder, codec12, name12, map);
                // Branch: checks a condition
                if (!(result12 instanceof Result.Ok(P12 value12)))
                    // Returns a value to the caller
                    return result12.cast();
                // Calls a method
                final Result<P13> result13 = get(coder, codec13, name13, map);
                // Branch: checks a condition
                if (!(result13 instanceof Result.Ok(P13 value13)))
                    // Returns a value to the caller
                    return result13.cast();
                // Calls a method
                final Result<P14> result14 = get(coder, codec14, name14, map);
                // Branch: checks a condition
                if (!(result14 instanceof Result.Ok(P14 value14)))
                    // Returns a value to the caller
                    return result14.cast();
                // Calls a method
                final Result<P15> result15 = get(coder, codec15, name15, map);
                // Branch: checks a condition
                if (!(result15 instanceof Result.Ok(P15 value15)))
                    // Returns a value to the caller
                    return result15.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Calls a method
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Branch: checks a condition
                if (result2 != null) return result2;
                // Calls a method
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Branch: checks a condition
                if (result3 != null) return result3;
                // Calls a method
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Branch: checks a condition
                if (result4 != null) return result4;
                // Calls a method
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Branch: checks a condition
                if (result5 != null) return result5;
                // Calls a method
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Branch: checks a condition
                if (result6 != null) return result6;
                // Calls a method
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Branch: checks a condition
                if (result7 != null) return result7;
                // Calls a method
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Branch: checks a condition
                if (result8 != null) return result8;
                // Calls a method
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Branch: checks a condition
                if (result9 != null) return result9;
                // Calls a method
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Branch: checks a condition
                if (result10 != null) return result10;
                // Calls a method
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Branch: checks a condition
                if (result11 != null) return result11;
                // Calls a method
                final Result<D> result12 = put(coder, codec12, map, name12, getter12.apply(value));
                // Branch: checks a condition
                if (result12 != null) return result12;
                // Calls a method
                final Result<D> result13 = put(coder, codec13, map, name13, getter13.apply(value));
                // Branch: checks a condition
                if (result13 != null) return result13;
                // Calls a method
                final Result<D> result14 = put(coder, codec14, map, name14, getter14.apply(value));
                // Branch: checks a condition
                if (result14 != null) return result14;
                // Calls a method
                final Result<D> result15 = put(coder, codec15, map, name15, getter15.apply(value));
                // Branch: checks a condition
                if (result15 != null) return result15;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1    the name/key for {@link P1}
     * @param codec1   the codec for {@link P1}
     * @param getter1  the getter for {@link P1}
     * @param name2    the name/key for {@link P2}
     * @param codec2   the codec for {@link P2}
     * @param getter2  the getter for {@link P2}
     * @param name3    the name/key for {@link P3}
     * @param codec3   the codec for {@link P3}
     * @param getter3  the getter for {@link P3}
     * @param name4    the name/key for {@link P4}
     * @param codec4   the codec for {@link P4}
     * @param getter4  the getter for {@link P4}
     * @param name5    the name/key for {@link P5}
     * @param codec5   the codec for {@link P5}
     * @param getter5  the getter for {@link P5}
     * @param name6    the name/key for {@link P6}
     * @param codec6   the codec for {@link P6}
     * @param getter6  the getter for {@link P6}
     * @param name7    the name/key for {@link P7}
     * @param codec7   the codec for {@link P7}
     * @param getter7  the getter for {@link P7}
     * @param name8    the name/key for {@link P8}
     * @param codec8   the codec for {@link P8}
     * @param getter8  the getter for {@link P8}
     * @param name9    the name/key for {@link P9}
     * @param codec9   the codec for {@link P9}
     * @param getter9  the getter for {@link P9}
     * @param name10   the name/key for {@link P10}
     * @param codec10  the codec for {@link P10}
     * @param getter10 the getter for {@link P10}
     * @param name11   the name/key for {@link P11}
     * @param codec11  the codec for {@link P11}
     * @param getter11 the getter for {@link P11}
     * @param name12   the name/key for {@link P12}
     * @param codec12  the codec for {@link P12}
     * @param getter12 the getter for {@link P12}
     * @param name13   the name/key for {@link P13}
     * @param codec13  the codec for {@link P13}
     * @param getter13 the getter for {@link P13}
     * @param name14   the name/key for {@link P14}
     * @param codec14  the codec for {@link P14}
     * @param getter14 the getter for {@link P14}
     * @param name15   the name/key for {@link P15}
     * @param codec15  the codec for {@link P15}
     * @param getter15 the getter for {@link P15}
     * @param name16   the name/key for {@link P16}
     * @param codec16  the codec for {@link P16}
     * @param getter16 the getter for {@link P16}
     * @param ctor     the constructor for {@link R}
     * @param <R>      the return type
     * @param <P1>     the first parameter type
     * @param <P2>     the second parameter type
     * @param <P3>     the third parameter type
     * @param <P4>     the fourth parameter type
     * @param <P5>     the fifth parameter type
     * @param <P6>     the sixth parameter type
     * @param <P7>     the seventh parameter type
     * @param <P8>     the eighth parameter type
     * @param <P9>     the ninth parameter type
     * @param <P10>    the tenth parameter type
     * @param <P11>    the eleventh parameter type
     * @param <P12>    the twelfth parameter type
     * @param <P13>    the thirteenth parameter type
     * @param <P14>    the fourteenth parameter type
     * @param <P15>    the fifteenth parameter type
     * @param <P16>    the sixteenth parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            String name2, Codec<P2> codec2, Function<? super R, ? extends P2> getter2,
            // Code statement
            String name3, Codec<P3> codec3, Function<? super R, ? extends P3> getter3,
            // Code statement
            String name4, Codec<P4> codec4, Function<? super R, ? extends P4> getter4,
            // Code statement
            String name5, Codec<P5> codec5, Function<? super R, ? extends P5> getter5,
            // Code statement
            String name6, Codec<P6> codec6, Function<? super R, ? extends P6> getter6,
            // Code statement
            String name7, Codec<P7> codec7, Function<? super R, ? extends P7> getter7,
            // Code statement
            String name8, Codec<P8> codec8, Function<? super R, ? extends P8> getter8,
            // Code statement
            String name9, Codec<P9> codec9, Function<? super R, ? extends P9> getter9,
            // Code statement
            String name10, Codec<P10> codec10, Function<? super R, ? extends P10> getter10,
            // Code statement
            String name11, Codec<P11> codec11, Function<? super R, ? extends P11> getter11,
            // Code statement
            String name12, Codec<P12> codec12, Function<? super R, ? extends P12> getter12,
            // Code statement
            String name13, Codec<P13> codec13, Function<? super R, ? extends P13> getter13,
            // Code statement
            String name14, Codec<P14> codec14, Function<? super R, ? extends P14> getter14,
            // Code statement
            String name15, Codec<P15> codec15, Function<? super R, ? extends P15> getter15,
            // Code statement
            String name16, Codec<P16> codec16, Function<? super R, ? extends P16> getter16,
            // Code statement
            F16<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? super P12, ? super P13, ? super P14, ? super P15, ? super P16, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(name2, "name2");
        // Calls a method
        Objects.requireNonNull(codec2, "codec2");
        // Calls a method
        Objects.requireNonNull(getter2, "getter2");
        // Calls a method
        Objects.requireNonNull(name3, "name3");
        // Calls a method
        Objects.requireNonNull(codec3, "codec3");
        // Calls a method
        Objects.requireNonNull(getter3, "getter3");
        // Calls a method
        Objects.requireNonNull(name4, "name4");
        // Calls a method
        Objects.requireNonNull(codec4, "codec4");
        // Calls a method
        Objects.requireNonNull(getter4, "getter4");
        // Calls a method
        Objects.requireNonNull(name5, "name5");
        // Calls a method
        Objects.requireNonNull(codec5, "codec5");
        // Calls a method
        Objects.requireNonNull(getter5, "getter5");
        // Calls a method
        Objects.requireNonNull(name6, "name6");
        // Calls a method
        Objects.requireNonNull(codec6, "codec6");
        // Calls a method
        Objects.requireNonNull(getter6, "getter6");
        // Calls a method
        Objects.requireNonNull(name7, "name7");
        // Calls a method
        Objects.requireNonNull(codec7, "codec7");
        // Calls a method
        Objects.requireNonNull(getter7, "getter7");
        // Calls a method
        Objects.requireNonNull(name8, "name8");
        // Calls a method
        Objects.requireNonNull(codec8, "codec8");
        // Calls a method
        Objects.requireNonNull(getter8, "getter8");
        // Calls a method
        Objects.requireNonNull(name9, "name9");
        // Calls a method
        Objects.requireNonNull(codec9, "codec9");
        // Calls a method
        Objects.requireNonNull(getter9, "getter9");
        // Calls a method
        Objects.requireNonNull(name10, "name10");
        // Calls a method
        Objects.requireNonNull(codec10, "codec10");
        // Calls a method
        Objects.requireNonNull(getter10, "getter10");
        // Calls a method
        Objects.requireNonNull(name11, "name11");
        // Calls a method
        Objects.requireNonNull(codec11, "codec11");
        // Calls a method
        Objects.requireNonNull(getter11, "getter11");
        // Calls a method
        Objects.requireNonNull(name12, "name12");
        // Calls a method
        Objects.requireNonNull(codec12, "codec12");
        // Calls a method
        Objects.requireNonNull(getter12, "getter12");
        // Calls a method
        Objects.requireNonNull(name13, "name13");
        // Calls a method
        Objects.requireNonNull(codec13, "codec13");
        // Calls a method
        Objects.requireNonNull(getter13, "getter13");
        // Calls a method
        Objects.requireNonNull(name14, "name14");
        // Calls a method
        Objects.requireNonNull(codec14, "codec14");
        // Calls a method
        Objects.requireNonNull(getter14, "getter14");
        // Calls a method
        Objects.requireNonNull(name15, "name15");
        // Calls a method
        Objects.requireNonNull(codec15, "codec15");
        // Calls a method
        Objects.requireNonNull(getter15, "getter15");
        // Calls a method
        Objects.requireNonNull(name16, "name16");
        // Calls a method
        Objects.requireNonNull(codec16, "codec16");
        // Calls a method
        Objects.requireNonNull(getter16, "getter16");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Calls a method
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Branch: checks a condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Returns a value to the caller
                    return result2.cast();
                // Calls a method
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Branch: checks a condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Returns a value to the caller
                    return result3.cast();
                // Calls a method
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Branch: checks a condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Returns a value to the caller
                    return result4.cast();
                // Calls a method
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Branch: checks a condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Returns a value to the caller
                    return result5.cast();
                // Calls a method
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Branch: checks a condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Returns a value to the caller
                    return result6.cast();
                // Calls a method
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Branch: checks a condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Returns a value to the caller
                    return result7.cast();
                // Calls a method
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Branch: checks a condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Returns a value to the caller
                    return result8.cast();
                // Calls a method
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Branch: checks a condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Returns a value to the caller
                    return result9.cast();
                // Calls a method
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Branch: checks a condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Returns a value to the caller
                    return result10.cast();
                // Calls a method
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Branch: checks a condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Returns a value to the caller
                    return result11.cast();
                // Calls a method
                final Result<P12> result12 = get(coder, codec12, name12, map);
                // Branch: checks a condition
                if (!(result12 instanceof Result.Ok(P12 value12)))
                    // Returns a value to the caller
                    return result12.cast();
                // Calls a method
                final Result<P13> result13 = get(coder, codec13, name13, map);
                // Branch: checks a condition
                if (!(result13 instanceof Result.Ok(P13 value13)))
                    // Returns a value to the caller
                    return result13.cast();
                // Calls a method
                final Result<P14> result14 = get(coder, codec14, name14, map);
                // Branch: checks a condition
                if (!(result14 instanceof Result.Ok(P14 value14)))
                    // Returns a value to the caller
                    return result14.cast();
                // Calls a method
                final Result<P15> result15 = get(coder, codec15, name15, map);
                // Branch: checks a condition
                if (!(result15 instanceof Result.Ok(P15 value15)))
                    // Returns a value to the caller
                    return result15.cast();
                // Calls a method
                final Result<P16> result16 = get(coder, codec16, name16, map);
                // Branch: checks a condition
                if (!(result16 instanceof Result.Ok(P16 value16)))
                    // Returns a value to the caller
                    return result16.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Calls a method
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Branch: checks a condition
                if (result2 != null) return result2;
                // Calls a method
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Branch: checks a condition
                if (result3 != null) return result3;
                // Calls a method
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Branch: checks a condition
                if (result4 != null) return result4;
                // Calls a method
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Branch: checks a condition
                if (result5 != null) return result5;
                // Calls a method
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Branch: checks a condition
                if (result6 != null) return result6;
                // Calls a method
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Branch: checks a condition
                if (result7 != null) return result7;
                // Calls a method
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Branch: checks a condition
                if (result8 != null) return result8;
                // Calls a method
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Branch: checks a condition
                if (result9 != null) return result9;
                // Calls a method
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Branch: checks a condition
                if (result10 != null) return result10;
                // Calls a method
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Branch: checks a condition
                if (result11 != null) return result11;
                // Calls a method
                final Result<D> result12 = put(coder, codec12, map, name12, getter12.apply(value));
                // Branch: checks a condition
                if (result12 != null) return result12;
                // Calls a method
                final Result<D> result13 = put(coder, codec13, map, name13, getter13.apply(value));
                // Branch: checks a condition
                if (result13 != null) return result13;
                // Calls a method
                final Result<D> result14 = put(coder, codec14, map, name14, getter14.apply(value));
                // Branch: checks a condition
                if (result14 != null) return result14;
                // Calls a method
                final Result<D> result15 = put(coder, codec15, map, name15, getter15.apply(value));
                // Branch: checks a condition
                if (result15 != null) return result15;
                // Calls a method
                final Result<D> result16 = put(coder, codec16, map, name16, getter16.apply(value));
                // Branch: checks a condition
                if (result16 != null) return result16;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1    the name/key for {@link P1}
     * @param codec1   the codec for {@link P1}
     * @param getter1  the getter for {@link P1}
     * @param name2    the name/key for {@link P2}
     * @param codec2   the codec for {@link P2}
     * @param getter2  the getter for {@link P2}
     * @param name3    the name/key for {@link P3}
     * @param codec3   the codec for {@link P3}
     * @param getter3  the getter for {@link P3}
     * @param name4    the name/key for {@link P4}
     * @param codec4   the codec for {@link P4}
     * @param getter4  the getter for {@link P4}
     * @param name5    the name/key for {@link P5}
     * @param codec5   the codec for {@link P5}
     * @param getter5  the getter for {@link P5}
     * @param name6    the name/key for {@link P6}
     * @param codec6   the codec for {@link P6}
     * @param getter6  the getter for {@link P6}
     * @param name7    the name/key for {@link P7}
     * @param codec7   the codec for {@link P7}
     * @param getter7  the getter for {@link P7}
     * @param name8    the name/key for {@link P8}
     * @param codec8   the codec for {@link P8}
     * @param getter8  the getter for {@link P8}
     * @param name9    the name/key for {@link P9}
     * @param codec9   the codec for {@link P9}
     * @param getter9  the getter for {@link P9}
     * @param name10   the name/key for {@link P10}
     * @param codec10  the codec for {@link P10}
     * @param getter10 the getter for {@link P10}
     * @param name11   the name/key for {@link P11}
     * @param codec11  the codec for {@link P11}
     * @param getter11 the getter for {@link P11}
     * @param name12   the name/key for {@link P12}
     * @param codec12  the codec for {@link P12}
     * @param getter12 the getter for {@link P12}
     * @param name13   the name/key for {@link P13}
     * @param codec13  the codec for {@link P13}
     * @param getter13 the getter for {@link P13}
     * @param name14   the name/key for {@link P14}
     * @param codec14  the codec for {@link P14}
     * @param getter14 the getter for {@link P14}
     * @param name15   the name/key for {@link P15}
     * @param codec15  the codec for {@link P15}
     * @param getter15 the getter for {@link P15}
     * @param name16   the name/key for {@link P16}
     * @param codec16  the codec for {@link P16}
     * @param getter16 the getter for {@link P16}
     * @param name17   the name/key for {@link P17}
     * @param codec17  the codec for {@link P17}
     * @param getter17 the getter for {@link P17}
     * @param ctor     the constructor for {@link R}
     * @param <R>      the return type
     * @param <P1>     the first parameter type
     * @param <P2>     the second parameter type
     * @param <P3>     the third parameter type
     * @param <P4>     the fourth parameter type
     * @param <P5>     the fifth parameter type
     * @param <P6>     the sixth parameter type
     * @param <P7>     the seventh parameter type
     * @param <P8>     the eighth parameter type
     * @param <P9>     the ninth parameter type
     * @param <P10>    the tenth parameter type
     * @param <P11>    the eleventh parameter type
     * @param <P12>    the twelfth parameter type
     * @param <P13>    the thirteenth parameter type
     * @param <P14>    the fourteenth parameter type
     * @param <P15>    the fifteenth parameter type
     * @param <P16>    the sixteenth parameter type
     * @param <P17>    the seventeenth parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            String name2, Codec<P2> codec2, Function<? super R, ? extends P2> getter2,
            // Code statement
            String name3, Codec<P3> codec3, Function<? super R, ? extends P3> getter3,
            // Code statement
            String name4, Codec<P4> codec4, Function<? super R, ? extends P4> getter4,
            // Code statement
            String name5, Codec<P5> codec5, Function<? super R, ? extends P5> getter5,
            // Code statement
            String name6, Codec<P6> codec6, Function<? super R, ? extends P6> getter6,
            // Code statement
            String name7, Codec<P7> codec7, Function<? super R, ? extends P7> getter7,
            // Code statement
            String name8, Codec<P8> codec8, Function<? super R, ? extends P8> getter8,
            // Code statement
            String name9, Codec<P9> codec9, Function<? super R, ? extends P9> getter9,
            // Code statement
            String name10, Codec<P10> codec10, Function<? super R, ? extends P10> getter10,
            // Code statement
            String name11, Codec<P11> codec11, Function<? super R, ? extends P11> getter11,
            // Code statement
            String name12, Codec<P12> codec12, Function<? super R, ? extends P12> getter12,
            // Code statement
            String name13, Codec<P13> codec13, Function<? super R, ? extends P13> getter13,
            // Code statement
            String name14, Codec<P14> codec14, Function<? super R, ? extends P14> getter14,
            // Code statement
            String name15, Codec<P15> codec15, Function<? super R, ? extends P15> getter15,
            // Code statement
            String name16, Codec<P16> codec16, Function<? super R, ? extends P16> getter16,
            // Code statement
            String name17, Codec<P17> codec17, Function<? super R, ? extends P17> getter17,
            // Code statement
            F17<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? super P12, ? super P13, ? super P14, ? super P15, ? super P16, ? super P17, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(name2, "name2");
        // Calls a method
        Objects.requireNonNull(codec2, "codec2");
        // Calls a method
        Objects.requireNonNull(getter2, "getter2");
        // Calls a method
        Objects.requireNonNull(name3, "name3");
        // Calls a method
        Objects.requireNonNull(codec3, "codec3");
        // Calls a method
        Objects.requireNonNull(getter3, "getter3");
        // Calls a method
        Objects.requireNonNull(name4, "name4");
        // Calls a method
        Objects.requireNonNull(codec4, "codec4");
        // Calls a method
        Objects.requireNonNull(getter4, "getter4");
        // Calls a method
        Objects.requireNonNull(name5, "name5");
        // Calls a method
        Objects.requireNonNull(codec5, "codec5");
        // Calls a method
        Objects.requireNonNull(getter5, "getter5");
        // Calls a method
        Objects.requireNonNull(name6, "name6");
        // Calls a method
        Objects.requireNonNull(codec6, "codec6");
        // Calls a method
        Objects.requireNonNull(getter6, "getter6");
        // Calls a method
        Objects.requireNonNull(name7, "name7");
        // Calls a method
        Objects.requireNonNull(codec7, "codec7");
        // Calls a method
        Objects.requireNonNull(getter7, "getter7");
        // Calls a method
        Objects.requireNonNull(name8, "name8");
        // Calls a method
        Objects.requireNonNull(codec8, "codec8");
        // Calls a method
        Objects.requireNonNull(getter8, "getter8");
        // Calls a method
        Objects.requireNonNull(name9, "name9");
        // Calls a method
        Objects.requireNonNull(codec9, "codec9");
        // Calls a method
        Objects.requireNonNull(getter9, "getter9");
        // Calls a method
        Objects.requireNonNull(name10, "name10");
        // Calls a method
        Objects.requireNonNull(codec10, "codec10");
        // Calls a method
        Objects.requireNonNull(getter10, "getter10");
        // Calls a method
        Objects.requireNonNull(name11, "name11");
        // Calls a method
        Objects.requireNonNull(codec11, "codec11");
        // Calls a method
        Objects.requireNonNull(getter11, "getter11");
        // Calls a method
        Objects.requireNonNull(name12, "name12");
        // Calls a method
        Objects.requireNonNull(codec12, "codec12");
        // Calls a method
        Objects.requireNonNull(getter12, "getter12");
        // Calls a method
        Objects.requireNonNull(name13, "name13");
        // Calls a method
        Objects.requireNonNull(codec13, "codec13");
        // Calls a method
        Objects.requireNonNull(getter13, "getter13");
        // Calls a method
        Objects.requireNonNull(name14, "name14");
        // Calls a method
        Objects.requireNonNull(codec14, "codec14");
        // Calls a method
        Objects.requireNonNull(getter14, "getter14");
        // Calls a method
        Objects.requireNonNull(name15, "name15");
        // Calls a method
        Objects.requireNonNull(codec15, "codec15");
        // Calls a method
        Objects.requireNonNull(getter15, "getter15");
        // Calls a method
        Objects.requireNonNull(name16, "name16");
        // Calls a method
        Objects.requireNonNull(codec16, "codec16");
        // Calls a method
        Objects.requireNonNull(getter16, "getter16");
        // Calls a method
        Objects.requireNonNull(name17, "name17");
        // Calls a method
        Objects.requireNonNull(codec17, "codec17");
        // Calls a method
        Objects.requireNonNull(getter17, "getter17");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Calls a method
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Branch: checks a condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Returns a value to the caller
                    return result2.cast();
                // Calls a method
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Branch: checks a condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Returns a value to the caller
                    return result3.cast();
                // Calls a method
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Branch: checks a condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Returns a value to the caller
                    return result4.cast();
                // Calls a method
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Branch: checks a condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Returns a value to the caller
                    return result5.cast();
                // Calls a method
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Branch: checks a condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Returns a value to the caller
                    return result6.cast();
                // Calls a method
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Branch: checks a condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Returns a value to the caller
                    return result7.cast();
                // Calls a method
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Branch: checks a condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Returns a value to the caller
                    return result8.cast();
                // Calls a method
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Branch: checks a condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Returns a value to the caller
                    return result9.cast();
                // Calls a method
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Branch: checks a condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Returns a value to the caller
                    return result10.cast();
                // Calls a method
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Branch: checks a condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Returns a value to the caller
                    return result11.cast();
                // Calls a method
                final Result<P12> result12 = get(coder, codec12, name12, map);
                // Branch: checks a condition
                if (!(result12 instanceof Result.Ok(P12 value12)))
                    // Returns a value to the caller
                    return result12.cast();
                // Calls a method
                final Result<P13> result13 = get(coder, codec13, name13, map);
                // Branch: checks a condition
                if (!(result13 instanceof Result.Ok(P13 value13)))
                    // Returns a value to the caller
                    return result13.cast();
                // Calls a method
                final Result<P14> result14 = get(coder, codec14, name14, map);
                // Branch: checks a condition
                if (!(result14 instanceof Result.Ok(P14 value14)))
                    // Returns a value to the caller
                    return result14.cast();
                // Calls a method
                final Result<P15> result15 = get(coder, codec15, name15, map);
                // Branch: checks a condition
                if (!(result15 instanceof Result.Ok(P15 value15)))
                    // Returns a value to the caller
                    return result15.cast();
                // Calls a method
                final Result<P16> result16 = get(coder, codec16, name16, map);
                // Branch: checks a condition
                if (!(result16 instanceof Result.Ok(P16 value16)))
                    // Returns a value to the caller
                    return result16.cast();
                // Calls a method
                final Result<P17> result17 = get(coder, codec17, name17, map);
                // Branch: checks a condition
                if (!(result17 instanceof Result.Ok(P17 value17)))
                    // Returns a value to the caller
                    return result17.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Calls a method
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Branch: checks a condition
                if (result2 != null) return result2;
                // Calls a method
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Branch: checks a condition
                if (result3 != null) return result3;
                // Calls a method
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Branch: checks a condition
                if (result4 != null) return result4;
                // Calls a method
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Branch: checks a condition
                if (result5 != null) return result5;
                // Calls a method
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Branch: checks a condition
                if (result6 != null) return result6;
                // Calls a method
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Branch: checks a condition
                if (result7 != null) return result7;
                // Calls a method
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Branch: checks a condition
                if (result8 != null) return result8;
                // Calls a method
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Branch: checks a condition
                if (result9 != null) return result9;
                // Calls a method
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Branch: checks a condition
                if (result10 != null) return result10;
                // Calls a method
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Branch: checks a condition
                if (result11 != null) return result11;
                // Calls a method
                final Result<D> result12 = put(coder, codec12, map, name12, getter12.apply(value));
                // Branch: checks a condition
                if (result12 != null) return result12;
                // Calls a method
                final Result<D> result13 = put(coder, codec13, map, name13, getter13.apply(value));
                // Branch: checks a condition
                if (result13 != null) return result13;
                // Calls a method
                final Result<D> result14 = put(coder, codec14, map, name14, getter14.apply(value));
                // Branch: checks a condition
                if (result14 != null) return result14;
                // Calls a method
                final Result<D> result15 = put(coder, codec15, map, name15, getter15.apply(value));
                // Branch: checks a condition
                if (result15 != null) return result15;
                // Calls a method
                final Result<D> result16 = put(coder, codec16, map, name16, getter16.apply(value));
                // Branch: checks a condition
                if (result16 != null) return result16;
                // Calls a method
                final Result<D> result17 = put(coder, codec17, map, name17, getter17.apply(value));
                // Branch: checks a condition
                if (result17 != null) return result17;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1    the name/key for {@link P1}
     * @param codec1   the codec for {@link P1}
     * @param getter1  the getter for {@link P1}
     * @param name2    the name/key for {@link P2}
     * @param codec2   the codec for {@link P2}
     * @param getter2  the getter for {@link P2}
     * @param name3    the name/key for {@link P3}
     * @param codec3   the codec for {@link P3}
     * @param getter3  the getter for {@link P3}
     * @param name4    the name/key for {@link P4}
     * @param codec4   the codec for {@link P4}
     * @param getter4  the getter for {@link P4}
     * @param name5    the name/key for {@link P5}
     * @param codec5   the codec for {@link P5}
     * @param getter5  the getter for {@link P5}
     * @param name6    the name/key for {@link P6}
     * @param codec6   the codec for {@link P6}
     * @param getter6  the getter for {@link P6}
     * @param name7    the name/key for {@link P7}
     * @param codec7   the codec for {@link P7}
     * @param getter7  the getter for {@link P7}
     * @param name8    the name/key for {@link P8}
     * @param codec8   the codec for {@link P8}
     * @param getter8  the getter for {@link P8}
     * @param name9    the name/key for {@link P9}
     * @param codec9   the codec for {@link P9}
     * @param getter9  the getter for {@link P9}
     * @param name10   the name/key for {@link P10}
     * @param codec10  the codec for {@link P10}
     * @param getter10 the getter for {@link P10}
     * @param name11   the name/key for {@link P11}
     * @param codec11  the codec for {@link P11}
     * @param getter11 the getter for {@link P11}
     * @param name12   the name/key for {@link P12}
     * @param codec12  the codec for {@link P12}
     * @param getter12 the getter for {@link P12}
     * @param name13   the name/key for {@link P13}
     * @param codec13  the codec for {@link P13}
     * @param getter13 the getter for {@link P13}
     * @param name14   the name/key for {@link P14}
     * @param codec14  the codec for {@link P14}
     * @param getter14 the getter for {@link P14}
     * @param name15   the name/key for {@link P15}
     * @param codec15  the codec for {@link P15}
     * @param getter15 the getter for {@link P15}
     * @param name16   the name/key for {@link P16}
     * @param codec16  the codec for {@link P16}
     * @param getter16 the getter for {@link P16}
     * @param name17   the name/key for {@link P17}
     * @param codec17  the codec for {@link P17}
     * @param getter17 the getter for {@link P17}
     * @param name18   the name/key for {@link P18}
     * @param codec18  the codec for {@link P18}
     * @param getter18 the getter for {@link P18}
     * @param ctor     the constructor for {@link R}
     * @param <R>      the return type
     * @param <P1>     the first parameter type
     * @param <P2>     the second parameter type
     * @param <P3>     the third parameter type
     * @param <P4>     the fourth parameter type
     * @param <P5>     the fifth parameter type
     * @param <P6>     the sixth parameter type
     * @param <P7>     the seventh parameter type
     * @param <P8>     the eighth parameter type
     * @param <P9>     the ninth parameter type
     * @param <P10>    the tenth parameter type
     * @param <P11>    the eleventh parameter type
     * @param <P12>    the twelfth parameter type
     * @param <P13>    the thirteenth parameter type
     * @param <P14>    the fourteenth parameter type
     * @param <P15>    the fifteenth parameter type
     * @param <P16>    the sixteenth parameter type
     * @param <P17>    the seventeenth parameter type
     * @param <P18>    the eighteenth parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object, P18 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            String name2, Codec<P2> codec2, Function<? super R, ? extends P2> getter2,
            // Code statement
            String name3, Codec<P3> codec3, Function<? super R, ? extends P3> getter3,
            // Code statement
            String name4, Codec<P4> codec4, Function<? super R, ? extends P4> getter4,
            // Code statement
            String name5, Codec<P5> codec5, Function<? super R, ? extends P5> getter5,
            // Code statement
            String name6, Codec<P6> codec6, Function<? super R, ? extends P6> getter6,
            // Code statement
            String name7, Codec<P7> codec7, Function<? super R, ? extends P7> getter7,
            // Code statement
            String name8, Codec<P8> codec8, Function<? super R, ? extends P8> getter8,
            // Code statement
            String name9, Codec<P9> codec9, Function<? super R, ? extends P9> getter9,
            // Code statement
            String name10, Codec<P10> codec10, Function<? super R, ? extends P10> getter10,
            // Code statement
            String name11, Codec<P11> codec11, Function<? super R, ? extends P11> getter11,
            // Code statement
            String name12, Codec<P12> codec12, Function<? super R, ? extends P12> getter12,
            // Code statement
            String name13, Codec<P13> codec13, Function<? super R, ? extends P13> getter13,
            // Code statement
            String name14, Codec<P14> codec14, Function<? super R, ? extends P14> getter14,
            // Code statement
            String name15, Codec<P15> codec15, Function<? super R, ? extends P15> getter15,
            // Code statement
            String name16, Codec<P16> codec16, Function<? super R, ? extends P16> getter16,
            // Code statement
            String name17, Codec<P17> codec17, Function<? super R, ? extends P17> getter17,
            // Code statement
            String name18, Codec<P18> codec18, Function<? super R, ? extends P18> getter18,
            // Code statement
            F18<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? super P12, ? super P13, ? super P14, ? super P15, ? super P16, ? super P17, ? super P18, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(name2, "name2");
        // Calls a method
        Objects.requireNonNull(codec2, "codec2");
        // Calls a method
        Objects.requireNonNull(getter2, "getter2");
        // Calls a method
        Objects.requireNonNull(name3, "name3");
        // Calls a method
        Objects.requireNonNull(codec3, "codec3");
        // Calls a method
        Objects.requireNonNull(getter3, "getter3");
        // Calls a method
        Objects.requireNonNull(name4, "name4");
        // Calls a method
        Objects.requireNonNull(codec4, "codec4");
        // Calls a method
        Objects.requireNonNull(getter4, "getter4");
        // Calls a method
        Objects.requireNonNull(name5, "name5");
        // Calls a method
        Objects.requireNonNull(codec5, "codec5");
        // Calls a method
        Objects.requireNonNull(getter5, "getter5");
        // Calls a method
        Objects.requireNonNull(name6, "name6");
        // Calls a method
        Objects.requireNonNull(codec6, "codec6");
        // Calls a method
        Objects.requireNonNull(getter6, "getter6");
        // Calls a method
        Objects.requireNonNull(name7, "name7");
        // Calls a method
        Objects.requireNonNull(codec7, "codec7");
        // Calls a method
        Objects.requireNonNull(getter7, "getter7");
        // Calls a method
        Objects.requireNonNull(name8, "name8");
        // Calls a method
        Objects.requireNonNull(codec8, "codec8");
        // Calls a method
        Objects.requireNonNull(getter8, "getter8");
        // Calls a method
        Objects.requireNonNull(name9, "name9");
        // Calls a method
        Objects.requireNonNull(codec9, "codec9");
        // Calls a method
        Objects.requireNonNull(getter9, "getter9");
        // Calls a method
        Objects.requireNonNull(name10, "name10");
        // Calls a method
        Objects.requireNonNull(codec10, "codec10");
        // Calls a method
        Objects.requireNonNull(getter10, "getter10");
        // Calls a method
        Objects.requireNonNull(name11, "name11");
        // Calls a method
        Objects.requireNonNull(codec11, "codec11");
        // Calls a method
        Objects.requireNonNull(getter11, "getter11");
        // Calls a method
        Objects.requireNonNull(name12, "name12");
        // Calls a method
        Objects.requireNonNull(codec12, "codec12");
        // Calls a method
        Objects.requireNonNull(getter12, "getter12");
        // Calls a method
        Objects.requireNonNull(name13, "name13");
        // Calls a method
        Objects.requireNonNull(codec13, "codec13");
        // Calls a method
        Objects.requireNonNull(getter13, "getter13");
        // Calls a method
        Objects.requireNonNull(name14, "name14");
        // Calls a method
        Objects.requireNonNull(codec14, "codec14");
        // Calls a method
        Objects.requireNonNull(getter14, "getter14");
        // Calls a method
        Objects.requireNonNull(name15, "name15");
        // Calls a method
        Objects.requireNonNull(codec15, "codec15");
        // Calls a method
        Objects.requireNonNull(getter15, "getter15");
        // Calls a method
        Objects.requireNonNull(name16, "name16");
        // Calls a method
        Objects.requireNonNull(codec16, "codec16");
        // Calls a method
        Objects.requireNonNull(getter16, "getter16");
        // Calls a method
        Objects.requireNonNull(name17, "name17");
        // Calls a method
        Objects.requireNonNull(codec17, "codec17");
        // Calls a method
        Objects.requireNonNull(getter17, "getter17");
        // Calls a method
        Objects.requireNonNull(name18, "name18");
        // Calls a method
        Objects.requireNonNull(codec18, "codec18");
        // Calls a method
        Objects.requireNonNull(getter18, "getter18");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Calls a method
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Branch: checks a condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Returns a value to the caller
                    return result2.cast();
                // Calls a method
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Branch: checks a condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Returns a value to the caller
                    return result3.cast();
                // Calls a method
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Branch: checks a condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Returns a value to the caller
                    return result4.cast();
                // Calls a method
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Branch: checks a condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Returns a value to the caller
                    return result5.cast();
                // Calls a method
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Branch: checks a condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Returns a value to the caller
                    return result6.cast();
                // Calls a method
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Branch: checks a condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Returns a value to the caller
                    return result7.cast();
                // Calls a method
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Branch: checks a condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Returns a value to the caller
                    return result8.cast();
                // Calls a method
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Branch: checks a condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Returns a value to the caller
                    return result9.cast();
                // Calls a method
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Branch: checks a condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Returns a value to the caller
                    return result10.cast();
                // Calls a method
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Branch: checks a condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Returns a value to the caller
                    return result11.cast();
                // Calls a method
                final Result<P12> result12 = get(coder, codec12, name12, map);
                // Branch: checks a condition
                if (!(result12 instanceof Result.Ok(P12 value12)))
                    // Returns a value to the caller
                    return result12.cast();
                // Calls a method
                final Result<P13> result13 = get(coder, codec13, name13, map);
                // Branch: checks a condition
                if (!(result13 instanceof Result.Ok(P13 value13)))
                    // Returns a value to the caller
                    return result13.cast();
                // Calls a method
                final Result<P14> result14 = get(coder, codec14, name14, map);
                // Branch: checks a condition
                if (!(result14 instanceof Result.Ok(P14 value14)))
                    // Returns a value to the caller
                    return result14.cast();
                // Calls a method
                final Result<P15> result15 = get(coder, codec15, name15, map);
                // Branch: checks a condition
                if (!(result15 instanceof Result.Ok(P15 value15)))
                    // Returns a value to the caller
                    return result15.cast();
                // Calls a method
                final Result<P16> result16 = get(coder, codec16, name16, map);
                // Branch: checks a condition
                if (!(result16 instanceof Result.Ok(P16 value16)))
                    // Returns a value to the caller
                    return result16.cast();
                // Calls a method
                final Result<P17> result17 = get(coder, codec17, name17, map);
                // Branch: checks a condition
                if (!(result17 instanceof Result.Ok(P17 value17)))
                    // Returns a value to the caller
                    return result17.cast();
                // Calls a method
                final Result<P18> result18 = get(coder, codec18, name18, map);
                // Branch: checks a condition
                if (!(result18 instanceof Result.Ok(P18 value18)))
                    // Returns a value to the caller
                    return result18.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Calls a method
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Branch: checks a condition
                if (result2 != null) return result2;
                // Calls a method
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Branch: checks a condition
                if (result3 != null) return result3;
                // Calls a method
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Branch: checks a condition
                if (result4 != null) return result4;
                // Calls a method
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Branch: checks a condition
                if (result5 != null) return result5;
                // Calls a method
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Branch: checks a condition
                if (result6 != null) return result6;
                // Calls a method
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Branch: checks a condition
                if (result7 != null) return result7;
                // Calls a method
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Branch: checks a condition
                if (result8 != null) return result8;
                // Calls a method
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Branch: checks a condition
                if (result9 != null) return result9;
                // Calls a method
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Branch: checks a condition
                if (result10 != null) return result10;
                // Calls a method
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Branch: checks a condition
                if (result11 != null) return result11;
                // Calls a method
                final Result<D> result12 = put(coder, codec12, map, name12, getter12.apply(value));
                // Branch: checks a condition
                if (result12 != null) return result12;
                // Calls a method
                final Result<D> result13 = put(coder, codec13, map, name13, getter13.apply(value));
                // Branch: checks a condition
                if (result13 != null) return result13;
                // Calls a method
                final Result<D> result14 = put(coder, codec14, map, name14, getter14.apply(value));
                // Branch: checks a condition
                if (result14 != null) return result14;
                // Calls a method
                final Result<D> result15 = put(coder, codec15, map, name15, getter15.apply(value));
                // Branch: checks a condition
                if (result15 != null) return result15;
                // Calls a method
                final Result<D> result16 = put(coder, codec16, map, name16, getter16.apply(value));
                // Branch: checks a condition
                if (result16 != null) return result16;
                // Calls a method
                final Result<D> result17 = put(coder, codec17, map, name17, getter17.apply(value));
                // Branch: checks a condition
                if (result17 != null) return result17;
                // Calls a method
                final Result<D> result18 = put(coder, codec18, map, name18, getter18.apply(value));
                // Branch: checks a condition
                if (result18 != null) return result18;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1    the name/key for {@link P1}
     * @param codec1   the codec for {@link P1}
     * @param getter1  the getter for {@link P1}
     * @param name2    the name/key for {@link P2}
     * @param codec2   the codec for {@link P2}
     * @param getter2  the getter for {@link P2}
     * @param name3    the name/key for {@link P3}
     * @param codec3   the codec for {@link P3}
     * @param getter3  the getter for {@link P3}
     * @param name4    the name/key for {@link P4}
     * @param codec4   the codec for {@link P4}
     * @param getter4  the getter for {@link P4}
     * @param name5    the name/key for {@link P5}
     * @param codec5   the codec for {@link P5}
     * @param getter5  the getter for {@link P5}
     * @param name6    the name/key for {@link P6}
     * @param codec6   the codec for {@link P6}
     * @param getter6  the getter for {@link P6}
     * @param name7    the name/key for {@link P7}
     * @param codec7   the codec for {@link P7}
     * @param getter7  the getter for {@link P7}
     * @param name8    the name/key for {@link P8}
     * @param codec8   the codec for {@link P8}
     * @param getter8  the getter for {@link P8}
     * @param name9    the name/key for {@link P9}
     * @param codec9   the codec for {@link P9}
     * @param getter9  the getter for {@link P9}
     * @param name10   the name/key for {@link P10}
     * @param codec10  the codec for {@link P10}
     * @param getter10 the getter for {@link P10}
     * @param name11   the name/key for {@link P11}
     * @param codec11  the codec for {@link P11}
     * @param getter11 the getter for {@link P11}
     * @param name12   the name/key for {@link P12}
     * @param codec12  the codec for {@link P12}
     * @param getter12 the getter for {@link P12}
     * @param name13   the name/key for {@link P13}
     * @param codec13  the codec for {@link P13}
     * @param getter13 the getter for {@link P13}
     * @param name14   the name/key for {@link P14}
     * @param codec14  the codec for {@link P14}
     * @param getter14 the getter for {@link P14}
     * @param name15   the name/key for {@link P15}
     * @param codec15  the codec for {@link P15}
     * @param getter15 the getter for {@link P15}
     * @param name16   the name/key for {@link P16}
     * @param codec16  the codec for {@link P16}
     * @param getter16 the getter for {@link P16}
     * @param name17   the name/key for {@link P17}
     * @param codec17  the codec for {@link P17}
     * @param getter17 the getter for {@link P17}
     * @param name18   the name/key for {@link P18}
     * @param codec18  the codec for {@link P18}
     * @param getter18 the getter for {@link P18}
     * @param name19   the name/key for {@link P19}
     * @param codec19  the codec for {@link P19}
     * @param getter19 the getter for {@link P19}
     * @param ctor     the constructor for {@link R}
     * @param <R>      the return type
     * @param <P1>     the first parameter type
     * @param <P2>     the second parameter type
     * @param <P3>     the third parameter type
     * @param <P4>     the fourth parameter type
     * @param <P5>     the fifth parameter type
     * @param <P6>     the sixth parameter type
     * @param <P7>     the seventh parameter type
     * @param <P8>     the eighth parameter type
     * @param <P9>     the ninth parameter type
     * @param <P10>    the tenth parameter type
     * @param <P11>    the eleventh parameter type
     * @param <P12>    the twelfth parameter type
     * @param <P13>    the thirteenth parameter type
     * @param <P14>    the fourteenth parameter type
     * @param <P15>    the fifteenth parameter type
     * @param <P16>    the sixteenth parameter type
     * @param <P17>    the seventeenth parameter type
     * @param <P18>    the eighteenth parameter type
     * @param <P19>    the nineteenth parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object, P18 extends @UnknownNullability Object, P19 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            String name2, Codec<P2> codec2, Function<? super R, ? extends P2> getter2,
            // Code statement
            String name3, Codec<P3> codec3, Function<? super R, ? extends P3> getter3,
            // Code statement
            String name4, Codec<P4> codec4, Function<? super R, ? extends P4> getter4,
            // Code statement
            String name5, Codec<P5> codec5, Function<? super R, ? extends P5> getter5,
            // Code statement
            String name6, Codec<P6> codec6, Function<? super R, ? extends P6> getter6,
            // Code statement
            String name7, Codec<P7> codec7, Function<? super R, ? extends P7> getter7,
            // Code statement
            String name8, Codec<P8> codec8, Function<? super R, ? extends P8> getter8,
            // Code statement
            String name9, Codec<P9> codec9, Function<? super R, ? extends P9> getter9,
            // Code statement
            String name10, Codec<P10> codec10, Function<? super R, ? extends P10> getter10,
            // Code statement
            String name11, Codec<P11> codec11, Function<? super R, ? extends P11> getter11,
            // Code statement
            String name12, Codec<P12> codec12, Function<? super R, ? extends P12> getter12,
            // Code statement
            String name13, Codec<P13> codec13, Function<? super R, ? extends P13> getter13,
            // Code statement
            String name14, Codec<P14> codec14, Function<? super R, ? extends P14> getter14,
            // Code statement
            String name15, Codec<P15> codec15, Function<? super R, ? extends P15> getter15,
            // Code statement
            String name16, Codec<P16> codec16, Function<? super R, ? extends P16> getter16,
            // Code statement
            String name17, Codec<P17> codec17, Function<? super R, ? extends P17> getter17,
            // Code statement
            String name18, Codec<P18> codec18, Function<? super R, ? extends P18> getter18,
            // Code statement
            String name19, Codec<P19> codec19, Function<? super R, ? extends P19> getter19,
            // Code statement
            F19<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? super P12, ? super P13, ? super P14, ? super P15, ? super P16, ? super P17, ? super P18, ? super P19, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(name2, "name2");
        // Calls a method
        Objects.requireNonNull(codec2, "codec2");
        // Calls a method
        Objects.requireNonNull(getter2, "getter2");
        // Calls a method
        Objects.requireNonNull(name3, "name3");
        // Calls a method
        Objects.requireNonNull(codec3, "codec3");
        // Calls a method
        Objects.requireNonNull(getter3, "getter3");
        // Calls a method
        Objects.requireNonNull(name4, "name4");
        // Calls a method
        Objects.requireNonNull(codec4, "codec4");
        // Calls a method
        Objects.requireNonNull(getter4, "getter4");
        // Calls a method
        Objects.requireNonNull(name5, "name5");
        // Calls a method
        Objects.requireNonNull(codec5, "codec5");
        // Calls a method
        Objects.requireNonNull(getter5, "getter5");
        // Calls a method
        Objects.requireNonNull(name6, "name6");
        // Calls a method
        Objects.requireNonNull(codec6, "codec6");
        // Calls a method
        Objects.requireNonNull(getter6, "getter6");
        // Calls a method
        Objects.requireNonNull(name7, "name7");
        // Calls a method
        Objects.requireNonNull(codec7, "codec7");
        // Calls a method
        Objects.requireNonNull(getter7, "getter7");
        // Calls a method
        Objects.requireNonNull(name8, "name8");
        // Calls a method
        Objects.requireNonNull(codec8, "codec8");
        // Calls a method
        Objects.requireNonNull(getter8, "getter8");
        // Calls a method
        Objects.requireNonNull(name9, "name9");
        // Calls a method
        Objects.requireNonNull(codec9, "codec9");
        // Calls a method
        Objects.requireNonNull(getter9, "getter9");
        // Calls a method
        Objects.requireNonNull(name10, "name10");
        // Calls a method
        Objects.requireNonNull(codec10, "codec10");
        // Calls a method
        Objects.requireNonNull(getter10, "getter10");
        // Calls a method
        Objects.requireNonNull(name11, "name11");
        // Calls a method
        Objects.requireNonNull(codec11, "codec11");
        // Calls a method
        Objects.requireNonNull(getter11, "getter11");
        // Calls a method
        Objects.requireNonNull(name12, "name12");
        // Calls a method
        Objects.requireNonNull(codec12, "codec12");
        // Calls a method
        Objects.requireNonNull(getter12, "getter12");
        // Calls a method
        Objects.requireNonNull(name13, "name13");
        // Calls a method
        Objects.requireNonNull(codec13, "codec13");
        // Calls a method
        Objects.requireNonNull(getter13, "getter13");
        // Calls a method
        Objects.requireNonNull(name14, "name14");
        // Calls a method
        Objects.requireNonNull(codec14, "codec14");
        // Calls a method
        Objects.requireNonNull(getter14, "getter14");
        // Calls a method
        Objects.requireNonNull(name15, "name15");
        // Calls a method
        Objects.requireNonNull(codec15, "codec15");
        // Calls a method
        Objects.requireNonNull(getter15, "getter15");
        // Calls a method
        Objects.requireNonNull(name16, "name16");
        // Calls a method
        Objects.requireNonNull(codec16, "codec16");
        // Calls a method
        Objects.requireNonNull(getter16, "getter16");
        // Calls a method
        Objects.requireNonNull(name17, "name17");
        // Calls a method
        Objects.requireNonNull(codec17, "codec17");
        // Calls a method
        Objects.requireNonNull(getter17, "getter17");
        // Calls a method
        Objects.requireNonNull(name18, "name18");
        // Calls a method
        Objects.requireNonNull(codec18, "codec18");
        // Calls a method
        Objects.requireNonNull(getter18, "getter18");
        // Calls a method
        Objects.requireNonNull(name19, "name19");
        // Calls a method
        Objects.requireNonNull(codec19, "codec19");
        // Calls a method
        Objects.requireNonNull(getter19, "getter19");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Calls a method
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Branch: checks a condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Returns a value to the caller
                    return result2.cast();
                // Calls a method
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Branch: checks a condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Returns a value to the caller
                    return result3.cast();
                // Calls a method
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Branch: checks a condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Returns a value to the caller
                    return result4.cast();
                // Calls a method
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Branch: checks a condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Returns a value to the caller
                    return result5.cast();
                // Calls a method
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Branch: checks a condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Returns a value to the caller
                    return result6.cast();
                // Calls a method
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Branch: checks a condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Returns a value to the caller
                    return result7.cast();
                // Calls a method
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Branch: checks a condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Returns a value to the caller
                    return result8.cast();
                // Calls a method
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Branch: checks a condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Returns a value to the caller
                    return result9.cast();
                // Calls a method
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Branch: checks a condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Returns a value to the caller
                    return result10.cast();
                // Calls a method
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Branch: checks a condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Returns a value to the caller
                    return result11.cast();
                // Calls a method
                final Result<P12> result12 = get(coder, codec12, name12, map);
                // Branch: checks a condition
                if (!(result12 instanceof Result.Ok(P12 value12)))
                    // Returns a value to the caller
                    return result12.cast();
                // Calls a method
                final Result<P13> result13 = get(coder, codec13, name13, map);
                // Branch: checks a condition
                if (!(result13 instanceof Result.Ok(P13 value13)))
                    // Returns a value to the caller
                    return result13.cast();
                // Calls a method
                final Result<P14> result14 = get(coder, codec14, name14, map);
                // Branch: checks a condition
                if (!(result14 instanceof Result.Ok(P14 value14)))
                    // Returns a value to the caller
                    return result14.cast();
                // Calls a method
                final Result<P15> result15 = get(coder, codec15, name15, map);
                // Branch: checks a condition
                if (!(result15 instanceof Result.Ok(P15 value15)))
                    // Returns a value to the caller
                    return result15.cast();
                // Calls a method
                final Result<P16> result16 = get(coder, codec16, name16, map);
                // Branch: checks a condition
                if (!(result16 instanceof Result.Ok(P16 value16)))
                    // Returns a value to the caller
                    return result16.cast();
                // Calls a method
                final Result<P17> result17 = get(coder, codec17, name17, map);
                // Branch: checks a condition
                if (!(result17 instanceof Result.Ok(P17 value17)))
                    // Returns a value to the caller
                    return result17.cast();
                // Calls a method
                final Result<P18> result18 = get(coder, codec18, name18, map);
                // Branch: checks a condition
                if (!(result18 instanceof Result.Ok(P18 value18)))
                    // Returns a value to the caller
                    return result18.cast();
                // Calls a method
                final Result<P19> result19 = get(coder, codec19, name19, map);
                // Branch: checks a condition
                if (!(result19 instanceof Result.Ok(P19 value19)))
                    // Returns a value to the caller
                    return result19.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Calls a method
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Branch: checks a condition
                if (result2 != null) return result2;
                // Calls a method
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Branch: checks a condition
                if (result3 != null) return result3;
                // Calls a method
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Branch: checks a condition
                if (result4 != null) return result4;
                // Calls a method
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Branch: checks a condition
                if (result5 != null) return result5;
                // Calls a method
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Branch: checks a condition
                if (result6 != null) return result6;
                // Calls a method
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Branch: checks a condition
                if (result7 != null) return result7;
                // Calls a method
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Branch: checks a condition
                if (result8 != null) return result8;
                // Calls a method
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Branch: checks a condition
                if (result9 != null) return result9;
                // Calls a method
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Branch: checks a condition
                if (result10 != null) return result10;
                // Calls a method
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Branch: checks a condition
                if (result11 != null) return result11;
                // Calls a method
                final Result<D> result12 = put(coder, codec12, map, name12, getter12.apply(value));
                // Branch: checks a condition
                if (result12 != null) return result12;
                // Calls a method
                final Result<D> result13 = put(coder, codec13, map, name13, getter13.apply(value));
                // Branch: checks a condition
                if (result13 != null) return result13;
                // Calls a method
                final Result<D> result14 = put(coder, codec14, map, name14, getter14.apply(value));
                // Branch: checks a condition
                if (result14 != null) return result14;
                // Calls a method
                final Result<D> result15 = put(coder, codec15, map, name15, getter15.apply(value));
                // Branch: checks a condition
                if (result15 != null) return result15;
                // Calls a method
                final Result<D> result16 = put(coder, codec16, map, name16, getter16.apply(value));
                // Branch: checks a condition
                if (result16 != null) return result16;
                // Calls a method
                final Result<D> result17 = put(coder, codec17, map, name17, getter17.apply(value));
                // Branch: checks a condition
                if (result17 != null) return result17;
                // Calls a method
                final Result<D> result18 = put(coder, codec18, map, name18, getter18.apply(value));
                // Branch: checks a condition
                if (result18 != null) return result18;
                // Calls a method
                final Result<D> result19 = put(coder, codec19, map, name19, getter19.apply(value));
                // Branch: checks a condition
                if (result19 != null) return result19;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Creates a struct template. See {@link StructCodec}
     *
     * @param name1    the name/key for {@link P1}
     * @param codec1   the codec for {@link P1}
     * @param getter1  the getter for {@link P1}
     * @param name2    the name/key for {@link P2}
     * @param codec2   the codec for {@link P2}
     * @param getter2  the getter for {@link P2}
     * @param name3    the name/key for {@link P3}
     * @param codec3   the codec for {@link P3}
     * @param getter3  the getter for {@link P3}
     * @param name4    the name/key for {@link P4}
     * @param codec4   the codec for {@link P4}
     * @param getter4  the getter for {@link P4}
     * @param name5    the name/key for {@link P5}
     * @param codec5   the codec for {@link P5}
     * @param getter5  the getter for {@link P5}
     * @param name6    the name/key for {@link P6}
     * @param codec6   the codec for {@link P6}
     * @param getter6  the getter for {@link P6}
     * @param name7    the name/key for {@link P7}
     * @param codec7   the codec for {@link P7}
     * @param getter7  the getter for {@link P7}
     * @param name8    the name/key for {@link P8}
     * @param codec8   the codec for {@link P8}
     * @param getter8  the getter for {@link P8}
     * @param name9    the name/key for {@link P9}
     * @param codec9   the codec for {@link P9}
     * @param getter9  the getter for {@link P9}
     * @param name10   the name/key for {@link P10}
     * @param codec10  the codec for {@link P10}
     * @param getter10 the getter for {@link P10}
     * @param name11   the name/key for {@link P11}
     * @param codec11  the codec for {@link P11}
     * @param getter11 the getter for {@link P11}
     * @param name12   the name/key for {@link P12}
     * @param codec12  the codec for {@link P12}
     * @param getter12 the getter for {@link P12}
     * @param name13   the name/key for {@link P13}
     * @param codec13  the codec for {@link P13}
     * @param getter13 the getter for {@link P13}
     * @param name14   the name/key for {@link P14}
     * @param codec14  the codec for {@link P14}
     * @param getter14 the getter for {@link P14}
     * @param name15   the name/key for {@link P15}
     * @param codec15  the codec for {@link P15}
     * @param getter15 the getter for {@link P15}
     * @param name16   the name/key for {@link P16}
     * @param codec16  the codec for {@link P16}
     * @param getter16 the getter for {@link P16}
     * @param name17   the name/key for {@link P17}
     * @param codec17  the codec for {@link P17}
     * @param getter17 the getter for {@link P17}
     * @param name18   the name/key for {@link P18}
     * @param codec18  the codec for {@link P18}
     * @param getter18 the getter for {@link P18}
     * @param name19   the name/key for {@link P19}
     * @param codec19  the codec for {@link P19}
     * @param getter19 the getter for {@link P19}
     * @param name20   the name/key for {@link P20}
     * @param codec20  the codec for {@link P20}
     * @param getter20 the getter for {@link P20}
     * @param ctor     the constructor for {@link R}
     * @param <R>      the return type
     * @param <P1>     the first parameter type
     * @param <P2>     the second parameter type
     * @param <P3>     the third parameter type
     * @param <P4>     the fourth parameter type
     * @param <P5>     the fifth parameter type
     * @param <P6>     the sixth parameter type
     * @param <P7>     the seventh parameter type
     * @param <P8>     the eighth parameter type
     * @param <P9>     the ninth parameter type
     * @param <P10>    the tenth parameter type
     * @param <P11>    the eleventh parameter type
     * @param <P12>    the twelfth parameter type
     * @param <P13>    the thirteenth parameter type
     * @param <P14>    the fourteenth parameter type
     * @param <P15>    the fifteenth parameter type
     * @param <P16>    the sixteenth parameter type
     * @param <P17>    the seventeenth parameter type
     * @param <P18>    the eighteenth parameter type
     * @param <P19>    the nineteenth parameter type
     * @param <P20>    the twentieth parameter type
     * @return the new {@link StructCodec} template.
     */
    // Code statement
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object, P18 extends @UnknownNullability Object, P19 extends @UnknownNullability Object, P20 extends @UnknownNullability Object> StructCodec<R> struct(
            // Code statement
            String name1, Codec<P1> codec1, Function<? super R, ? extends P1> getter1,
            // Code statement
            String name2, Codec<P2> codec2, Function<? super R, ? extends P2> getter2,
            // Code statement
            String name3, Codec<P3> codec3, Function<? super R, ? extends P3> getter3,
            // Code statement
            String name4, Codec<P4> codec4, Function<? super R, ? extends P4> getter4,
            // Code statement
            String name5, Codec<P5> codec5, Function<? super R, ? extends P5> getter5,
            // Code statement
            String name6, Codec<P6> codec6, Function<? super R, ? extends P6> getter6,
            // Code statement
            String name7, Codec<P7> codec7, Function<? super R, ? extends P7> getter7,
            // Code statement
            String name8, Codec<P8> codec8, Function<? super R, ? extends P8> getter8,
            // Code statement
            String name9, Codec<P9> codec9, Function<? super R, ? extends P9> getter9,
            // Code statement
            String name10, Codec<P10> codec10, Function<? super R, ? extends P10> getter10,
            // Code statement
            String name11, Codec<P11> codec11, Function<? super R, ? extends P11> getter11,
            // Code statement
            String name12, Codec<P12> codec12, Function<? super R, ? extends P12> getter12,
            // Code statement
            String name13, Codec<P13> codec13, Function<? super R, ? extends P13> getter13,
            // Code statement
            String name14, Codec<P14> codec14, Function<? super R, ? extends P14> getter14,
            // Code statement
            String name15, Codec<P15> codec15, Function<? super R, ? extends P15> getter15,
            // Code statement
            String name16, Codec<P16> codec16, Function<? super R, ? extends P16> getter16,
            // Code statement
            String name17, Codec<P17> codec17, Function<? super R, ? extends P17> getter17,
            // Code statement
            String name18, Codec<P18> codec18, Function<? super R, ? extends P18> getter18,
            // Code statement
            String name19, Codec<P19> codec19, Function<? super R, ? extends P19> getter19,
            // Code statement
            String name20, Codec<P20> codec20, Function<? super R, ? extends P20> getter20,
            // Code statement
            F20<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? super P12, ? super P13, ? super P14, ? super P15, ? super P16, ? super P17, ? super P18, ? super P19, ? super P20, ? extends R> ctor
    // Start of a method/block
    ) {
        // Calls a method
        Objects.requireNonNull(name1, "name1");
        // Calls a method
        Objects.requireNonNull(codec1, "codec1");
        // Calls a method
        Objects.requireNonNull(getter1, "getter1");
        // Calls a method
        Objects.requireNonNull(name2, "name2");
        // Calls a method
        Objects.requireNonNull(codec2, "codec2");
        // Calls a method
        Objects.requireNonNull(getter2, "getter2");
        // Calls a method
        Objects.requireNonNull(name3, "name3");
        // Calls a method
        Objects.requireNonNull(codec3, "codec3");
        // Calls a method
        Objects.requireNonNull(getter3, "getter3");
        // Calls a method
        Objects.requireNonNull(name4, "name4");
        // Calls a method
        Objects.requireNonNull(codec4, "codec4");
        // Calls a method
        Objects.requireNonNull(getter4, "getter4");
        // Calls a method
        Objects.requireNonNull(name5, "name5");
        // Calls a method
        Objects.requireNonNull(codec5, "codec5");
        // Calls a method
        Objects.requireNonNull(getter5, "getter5");
        // Calls a method
        Objects.requireNonNull(name6, "name6");
        // Calls a method
        Objects.requireNonNull(codec6, "codec6");
        // Calls a method
        Objects.requireNonNull(getter6, "getter6");
        // Calls a method
        Objects.requireNonNull(name7, "name7");
        // Calls a method
        Objects.requireNonNull(codec7, "codec7");
        // Calls a method
        Objects.requireNonNull(getter7, "getter7");
        // Calls a method
        Objects.requireNonNull(name8, "name8");
        // Calls a method
        Objects.requireNonNull(codec8, "codec8");
        // Calls a method
        Objects.requireNonNull(getter8, "getter8");
        // Calls a method
        Objects.requireNonNull(name9, "name9");
        // Calls a method
        Objects.requireNonNull(codec9, "codec9");
        // Calls a method
        Objects.requireNonNull(getter9, "getter9");
        // Calls a method
        Objects.requireNonNull(name10, "name10");
        // Calls a method
        Objects.requireNonNull(codec10, "codec10");
        // Calls a method
        Objects.requireNonNull(getter10, "getter10");
        // Calls a method
        Objects.requireNonNull(name11, "name11");
        // Calls a method
        Objects.requireNonNull(codec11, "codec11");
        // Calls a method
        Objects.requireNonNull(getter11, "getter11");
        // Calls a method
        Objects.requireNonNull(name12, "name12");
        // Calls a method
        Objects.requireNonNull(codec12, "codec12");
        // Calls a method
        Objects.requireNonNull(getter12, "getter12");
        // Calls a method
        Objects.requireNonNull(name13, "name13");
        // Calls a method
        Objects.requireNonNull(codec13, "codec13");
        // Calls a method
        Objects.requireNonNull(getter13, "getter13");
        // Calls a method
        Objects.requireNonNull(name14, "name14");
        // Calls a method
        Objects.requireNonNull(codec14, "codec14");
        // Calls a method
        Objects.requireNonNull(getter14, "getter14");
        // Calls a method
        Objects.requireNonNull(name15, "name15");
        // Calls a method
        Objects.requireNonNull(codec15, "codec15");
        // Calls a method
        Objects.requireNonNull(getter15, "getter15");
        // Calls a method
        Objects.requireNonNull(name16, "name16");
        // Calls a method
        Objects.requireNonNull(codec16, "codec16");
        // Calls a method
        Objects.requireNonNull(getter16, "getter16");
        // Calls a method
        Objects.requireNonNull(name17, "name17");
        // Calls a method
        Objects.requireNonNull(codec17, "codec17");
        // Calls a method
        Objects.requireNonNull(getter17, "getter17");
        // Calls a method
        Objects.requireNonNull(name18, "name18");
        // Calls a method
        Objects.requireNonNull(codec18, "codec18");
        // Calls a method
        Objects.requireNonNull(getter18, "getter18");
        // Calls a method
        Objects.requireNonNull(name19, "name19");
        // Calls a method
        Objects.requireNonNull(codec19, "codec19");
        // Calls a method
        Objects.requireNonNull(getter19, "getter19");
        // Calls a method
        Objects.requireNonNull(name20, "name20");
        // Calls a method
        Objects.requireNonNull(codec20, "codec20");
        // Calls a method
        Objects.requireNonNull(getter20, "getter20");
        // Calls a method
        Objects.requireNonNull(ctor, "ctor");
        // Returns a value to the caller
        return new StructCodec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Calls a method
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Branch: checks a condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Returns a value to the caller
                    return result1.cast();
                // Calls a method
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Branch: checks a condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Returns a value to the caller
                    return result2.cast();
                // Calls a method
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Branch: checks a condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Returns a value to the caller
                    return result3.cast();
                // Calls a method
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Branch: checks a condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Returns a value to the caller
                    return result4.cast();
                // Calls a method
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Branch: checks a condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Returns a value to the caller
                    return result5.cast();
                // Calls a method
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Branch: checks a condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Returns a value to the caller
                    return result6.cast();
                // Calls a method
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Branch: checks a condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Returns a value to the caller
                    return result7.cast();
                // Calls a method
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Branch: checks a condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Returns a value to the caller
                    return result8.cast();
                // Calls a method
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Branch: checks a condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Returns a value to the caller
                    return result9.cast();
                // Calls a method
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Branch: checks a condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Returns a value to the caller
                    return result10.cast();
                // Calls a method
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Branch: checks a condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Returns a value to the caller
                    return result11.cast();
                // Calls a method
                final Result<P12> result12 = get(coder, codec12, name12, map);
                // Branch: checks a condition
                if (!(result12 instanceof Result.Ok(P12 value12)))
                    // Returns a value to the caller
                    return result12.cast();
                // Calls a method
                final Result<P13> result13 = get(coder, codec13, name13, map);
                // Branch: checks a condition
                if (!(result13 instanceof Result.Ok(P13 value13)))
                    // Returns a value to the caller
                    return result13.cast();
                // Calls a method
                final Result<P14> result14 = get(coder, codec14, name14, map);
                // Branch: checks a condition
                if (!(result14 instanceof Result.Ok(P14 value14)))
                    // Returns a value to the caller
                    return result14.cast();
                // Calls a method
                final Result<P15> result15 = get(coder, codec15, name15, map);
                // Branch: checks a condition
                if (!(result15 instanceof Result.Ok(P15 value15)))
                    // Returns a value to the caller
                    return result15.cast();
                // Calls a method
                final Result<P16> result16 = get(coder, codec16, name16, map);
                // Branch: checks a condition
                if (!(result16 instanceof Result.Ok(P16 value16)))
                    // Returns a value to the caller
                    return result16.cast();
                // Calls a method
                final Result<P17> result17 = get(coder, codec17, name17, map);
                // Branch: checks a condition
                if (!(result17 instanceof Result.Ok(P17 value17)))
                    // Returns a value to the caller
                    return result17.cast();
                // Calls a method
                final Result<P18> result18 = get(coder, codec18, name18, map);
                // Branch: checks a condition
                if (!(result18 instanceof Result.Ok(P18 value18)))
                    // Returns a value to the caller
                    return result18.cast();
                // Calls a method
                final Result<P19> result19 = get(coder, codec19, name19, map);
                // Branch: checks a condition
                if (!(result19 instanceof Result.Ok(P19 value19)))
                    // Returns a value to the caller
                    return result19.cast();
                // Calls a method
                final Result<P20> result20 = get(coder, codec20, name20, map);
                // Branch: checks a condition
                if (!(result20 instanceof Result.Ok(P20 value20)))
                    // Returns a value to the caller
                    return result20.cast();
                // Returns a value to the caller
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Calls a method
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Branch: checks a condition
                if (result1 != null) return result1;
                // Calls a method
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Branch: checks a condition
                if (result2 != null) return result2;
                // Calls a method
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Branch: checks a condition
                if (result3 != null) return result3;
                // Calls a method
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Branch: checks a condition
                if (result4 != null) return result4;
                // Calls a method
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Branch: checks a condition
                if (result5 != null) return result5;
                // Calls a method
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Branch: checks a condition
                if (result6 != null) return result6;
                // Calls a method
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Branch: checks a condition
                if (result7 != null) return result7;
                // Calls a method
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Branch: checks a condition
                if (result8 != null) return result8;
                // Calls a method
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Branch: checks a condition
                if (result9 != null) return result9;
                // Calls a method
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Branch: checks a condition
                if (result10 != null) return result10;
                // Calls a method
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Branch: checks a condition
                if (result11 != null) return result11;
                // Calls a method
                final Result<D> result12 = put(coder, codec12, map, name12, getter12.apply(value));
                // Branch: checks a condition
                if (result12 != null) return result12;
                // Calls a method
                final Result<D> result13 = put(coder, codec13, map, name13, getter13.apply(value));
                // Branch: checks a condition
                if (result13 != null) return result13;
                // Calls a method
                final Result<D> result14 = put(coder, codec14, map, name14, getter14.apply(value));
                // Branch: checks a condition
                if (result14 != null) return result14;
                // Calls a method
                final Result<D> result15 = put(coder, codec15, map, name15, getter15.apply(value));
                // Branch: checks a condition
                if (result15 != null) return result15;
                // Calls a method
                final Result<D> result16 = put(coder, codec16, map, name16, getter16.apply(value));
                // Branch: checks a condition
                if (result16 != null) return result16;
                // Calls a method
                final Result<D> result17 = put(coder, codec17, map, name17, getter17.apply(value));
                // Branch: checks a condition
                if (result17 != null) return result17;
                // Calls a method
                final Result<D> result18 = put(coder, codec18, map, name18, getter18.apply(value));
                // Branch: checks a condition
                if (result18 != null) return result18;
                // Calls a method
                final Result<D> result19 = put(coder, codec19, map, name19, getter19.apply(value));
                // Branch: checks a condition
                if (result19 != null) return result19;
                // Calls a method
                final Result<D> result20 = put(coder, codec20, map, name20, getter20.apply(value));
                // Branch: checks a condition
                if (result20 != null) return result20;
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    private static <D, T> Result<@UnknownNullability T> get(Transcoder<D> coder, Codec<T> codec, String key, MapLike<D> map) {
        // Branch: checks a condition
        if (INLINE.equals(key)) {
            // Assigns a value
            final Codec<T> decodeCodec = codec instanceof CodecImpl.OptionalImpl<T>(
                    // Code statement
                    Codec<T> inner, T ignored
            // Code statement
            ) ? inner : codec;
            // Branch: checks a condition
            if (!(decodeCodec instanceof StructCodec<T> s)) return new Result.Error<>(key + ": not a struct");

            // Calls a method
            final Result<T> decodeResult = s.decodeFromMap(coder, map);
            // Branch: checks a condition
            if (decodeResult instanceof Result.Error<T> && codec instanceof CodecImpl.OptionalImpl<T>(
                    // Code statement
                    Codec<T> ignored, T defaultValue
            // Calls a method
            )) return new Result.Ok<>(defaultValue);

            // Returns a value to the caller
            return decodeResult.mapError(e -> key + ": " + e);
        // End of a block/expression
        }
        // Branch: checks a condition
        if (codec instanceof CodecImpl.OptionalImpl<T>(Codec<T> inner, T defaultValue)) {
            // Returns a value to the caller
            return switch (map.getValue(key)) {
                // Multiple branching (switch/case)
                case Result.Ok(D innerValue) -> Objects.equals(innerValue, coder.createNull())
                        // Code statement
                        ? new Result.Ok<>(defaultValue)
                        // Calls a method
                        : inner.decode(coder, innerValue).mapError(e -> key + ": " + e);
                // Multiple branching (switch/case)
                case Result.Error(String ignored) -> new Result.Ok<>(defaultValue);
            // End of a block/expression
            };
        // End of a block/expression
        }
        // Returns a value to the caller
        return map.getValue(key)
                // Code statement
                .map(innerValue -> codec.decode(coder, innerValue))
                // Calls a method
                .mapError(e -> key + ": " + e);
    // End of a block/expression
    }

    // Start of a method/block
    private static <D, T> @Nullable Result<D> put(Transcoder<D> coder, Codec<T> codec, MapBuilder<D> map, String key, @Nullable T value) {
        // Branch: checks a condition
        if (value == null) {
            // Branch: checks a condition
            if (!(codec instanceof CodecImpl.OptionalImpl<T>))
                // Returns a value to the caller
                return new Result.Error<>(key + ": null");
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }

        // Branch: checks a condition
        if (INLINE.equals(key)) {
            // Assigns a value
            final Codec<T> encodeCodec = codec instanceof CodecImpl.OptionalImpl<T>(
                    // Code statement
                    Codec<T> inner, T ignored
            // Code statement
            ) ? inner : codec;
            // Branch: checks a condition
            if (!(encodeCodec instanceof StructCodec<T> s))
                // Returns a value to the caller
                return new Result.Error<>(key + ": not a struct");
            // Calls a method
            final Result<D> mapEncodeResult = s.encodeToMap(coder, value, map);
            // Branch: checks a condition
            if (mapEncodeResult instanceof Result.Error<?> e)
                // Returns a value to the caller
                return new Result.Error<>(key + ": " + e);
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
        // Returns a value to the caller
        return switch (codec.encode(coder, value)) {
            // Multiple branching (switch/case)
            case Result.Ok(D ok) -> {
                // Branch: checks a condition
                if (ok != null) map.put(key, ok);
                // Code statement
                yield null;
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case Result.Error(String message) -> new Result.Error<>(key + ": " + message);
        // End of a block/expression
        };
    // End of a block/expression
    }

// End of a block/expression
}

