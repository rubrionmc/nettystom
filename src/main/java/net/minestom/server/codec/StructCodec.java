// Déclaration du paquet de ce fichier
package net.minestom.server.codec;

// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder.MapBuilder;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder.MapLike;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate.*;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
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
// Déclaration de type (classe/interface/enum/record)
public interface StructCodec<R> extends Codec<R> {
    /**
     * A special key used to instruct the codec to inline the value instead of wrapping it in a map.
     * The inlined codec must also be a StructCodec.
     */
    // Affecte une valeur
    String INLINE = "$$inline$$";

    /**
     * Decode a value {@link R} from the backing map of {@link D}
     *
     * @param coder the transcoder for {@link D}
     * @param map   the map to decode from
     * @param <D>   the transcoder type
     * @return the result of decoding
     */
    // Appelle une méthode
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
    // Appelle une méthode
    <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map);

    /**
     * {@inheritDoc}
     *
     * @param coder the transcoder to use
     * @param value the value to decode
     * @param <D>   the transcoder type
     * @return the result from decoding
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default <D> Result<R> decode(Transcoder<D> coder, D value) {
        // Appelle une méthode
        Objects.requireNonNull(value, "Value cannot be null");
        // Renvoie une valeur à l'appelant
        return coder.getMap(value).map(map -> decodeFromMap(coder, map));
    // Fin d'un bloc/d'une expression
    }

    /**
     * {@inheritDoc}
     *
     * @param coder the transcoder to use
     * @param value the value to encode, if null returns error
     * @param <D>   the transcoder type
     * @return the result from encoding
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default <D> Result<D> encode(Transcoder<D> coder, @Nullable R value) {
        // Embranchement : vérifie une condition
        if (value == null) return new Result.Error<>("null");
        // Renvoie une valeur à l'appelant
        return encodeToMap(coder, value, coder.createMap());
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    default StructCodec<R> orElseStruct(StructCodec<R> other) {
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<R> primaryResult = StructCodec.this.decodeFromMap(coder, map);
                // Embranchement : vérifie une condition
                if (primaryResult instanceof Result.Ok<R> primaryOk)
                    // Renvoie une valeur à l'appelant
                    return primaryOk;

                // Primary did not work, try secondary
                // Appelle une méthode
                final Result<R> secondaryResult = other.decodeFromMap(coder, map);
                // Embranchement : vérifie une condition
                if (secondaryResult instanceof Result.Ok<R> secondaryOk)
                    // Renvoie une valeur à l'appelant
                    return secondaryOk;

                // Secondary did not work either, return error from primary.
                // Renvoie une valeur à l'appelant
                return primaryResult;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Renvoie une valeur à l'appelant
                return StructCodec.this.encodeToMap(coder, value, map);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the value in any struct.
     *
     * @param value the value to return of {@link R}
     * @param <R>   the return type
     * @return the new struct codec for value
     */
    // Début d'une méthode/d'un bloc
    static <R> StructCodec<R> struct(R value) {
        // Appelle une méthode
        final Result<R> ok = new Result.Ok<>(Objects.requireNonNull(value, "value"));
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Renvoie une valeur à l'appelant
                return ok;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Lazily returns the value in any struct.
     *
     * @param ctor the value to return of {@link R}
     * @param <R>  the return type
     * @return the new struct codec for value
     */
    // Début d'une méthode/d'un bloc
    static <R> StructCodec<R> struct(Supplier<R> ctor) {
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.get());
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            F1<P1, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            String name2, Codec<P2> codec2, Function<R, P2> getter2,
            // Instruction de code
            F2<P1, P2, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(name2, "name2");
        // Appelle une méthode
        Objects.requireNonNull(codec2, "codec2");
        // Appelle une méthode
        Objects.requireNonNull(getter2, "getter2");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Appelle une méthode
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Embranchement : vérifie une condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Renvoie une valeur à l'appelant
                    return result2.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1, value2));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Appelle une méthode
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Embranchement : vérifie une condition
                if (result2 != null) return result2;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            String name2, Codec<P2> codec2, Function<R, P2> getter2,
            // Instruction de code
            String name3, Codec<P3> codec3, Function<R, P3> getter3,
            // Instruction de code
            F3<P1, P2, P3, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(name2, "name2");
        // Appelle une méthode
        Objects.requireNonNull(codec2, "codec2");
        // Appelle une méthode
        Objects.requireNonNull(getter2, "getter2");
        // Appelle une méthode
        Objects.requireNonNull(name3, "name3");
        // Appelle une méthode
        Objects.requireNonNull(codec3, "codec3");
        // Appelle une méthode
        Objects.requireNonNull(getter3, "getter3");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Appelle une méthode
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Embranchement : vérifie une condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Renvoie une valeur à l'appelant
                    return result2.cast();
                // Appelle une méthode
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Embranchement : vérifie une condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Renvoie une valeur à l'appelant
                    return result3.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1, value2, value3));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Appelle une méthode
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Embranchement : vérifie une condition
                if (result2 != null) return result2;
                // Appelle une méthode
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Embranchement : vérifie une condition
                if (result3 != null) return result3;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            String name2, Codec<P2> codec2, Function<R, P2> getter2,
            // Instruction de code
            String name3, Codec<P3> codec3, Function<R, P3> getter3,
            // Instruction de code
            String name4, Codec<P4> codec4, Function<R, P4> getter4,
            // Instruction de code
            F4<P1, P2, P3, P4, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(name2, "name2");
        // Appelle une méthode
        Objects.requireNonNull(codec2, "codec2");
        // Appelle une méthode
        Objects.requireNonNull(getter2, "getter2");
        // Appelle une méthode
        Objects.requireNonNull(name3, "name3");
        // Appelle une méthode
        Objects.requireNonNull(codec3, "codec3");
        // Appelle une méthode
        Objects.requireNonNull(getter3, "getter3");
        // Appelle une méthode
        Objects.requireNonNull(name4, "name4");
        // Appelle une méthode
        Objects.requireNonNull(codec4, "codec4");
        // Appelle une méthode
        Objects.requireNonNull(getter4, "getter4");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Appelle une méthode
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Embranchement : vérifie une condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Renvoie une valeur à l'appelant
                    return result2.cast();
                // Appelle une méthode
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Embranchement : vérifie une condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Renvoie une valeur à l'appelant
                    return result3.cast();
                // Appelle une méthode
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Embranchement : vérifie une condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Renvoie une valeur à l'appelant
                    return result4.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Appelle une méthode
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Embranchement : vérifie une condition
                if (result2 != null) return result2;
                // Appelle une méthode
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Embranchement : vérifie une condition
                if (result3 != null) return result3;
                // Appelle une méthode
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Embranchement : vérifie une condition
                if (result4 != null) return result4;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            String name2, Codec<P2> codec2, Function<R, P2> getter2,
            // Instruction de code
            String name3, Codec<P3> codec3, Function<R, P3> getter3,
            // Instruction de code
            String name4, Codec<P4> codec4, Function<R, P4> getter4,
            // Instruction de code
            String name5, Codec<P5> codec5, Function<R, P5> getter5,
            // Instruction de code
            F5<P1, P2, P3, P4, P5, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(name2, "name2");
        // Appelle une méthode
        Objects.requireNonNull(codec2, "codec2");
        // Appelle une méthode
        Objects.requireNonNull(getter2, "getter2");
        // Appelle une méthode
        Objects.requireNonNull(name3, "name3");
        // Appelle une méthode
        Objects.requireNonNull(codec3, "codec3");
        // Appelle une méthode
        Objects.requireNonNull(getter3, "getter3");
        // Appelle une méthode
        Objects.requireNonNull(name4, "name4");
        // Appelle une méthode
        Objects.requireNonNull(codec4, "codec4");
        // Appelle une méthode
        Objects.requireNonNull(getter4, "getter4");
        // Appelle une méthode
        Objects.requireNonNull(name5, "name5");
        // Appelle une méthode
        Objects.requireNonNull(codec5, "codec5");
        // Appelle une méthode
        Objects.requireNonNull(getter5, "getter5");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Appelle une méthode
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Embranchement : vérifie une condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Renvoie une valeur à l'appelant
                    return result2.cast();
                // Appelle une méthode
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Embranchement : vérifie une condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Renvoie une valeur à l'appelant
                    return result3.cast();
                // Appelle une méthode
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Embranchement : vérifie une condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Renvoie une valeur à l'appelant
                    return result4.cast();
                // Appelle une méthode
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Embranchement : vérifie une condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Renvoie une valeur à l'appelant
                    return result5.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Appelle une méthode
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Embranchement : vérifie une condition
                if (result2 != null) return result2;
                // Appelle une méthode
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Embranchement : vérifie une condition
                if (result3 != null) return result3;
                // Appelle une méthode
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Embranchement : vérifie une condition
                if (result4 != null) return result4;
                // Appelle une méthode
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Embranchement : vérifie une condition
                if (result5 != null) return result5;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            String name2, Codec<P2> codec2, Function<R, P2> getter2,
            // Instruction de code
            String name3, Codec<P3> codec3, Function<R, P3> getter3,
            // Instruction de code
            String name4, Codec<P4> codec4, Function<R, P4> getter4,
            // Instruction de code
            String name5, Codec<P5> codec5, Function<R, P5> getter5,
            // Instruction de code
            String name6, Codec<P6> codec6, Function<R, P6> getter6,
            // Instruction de code
            F6<P1, P2, P3, P4, P5, P6, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(name2, "name2");
        // Appelle une méthode
        Objects.requireNonNull(codec2, "codec2");
        // Appelle une méthode
        Objects.requireNonNull(getter2, "getter2");
        // Appelle une méthode
        Objects.requireNonNull(name3, "name3");
        // Appelle une méthode
        Objects.requireNonNull(codec3, "codec3");
        // Appelle une méthode
        Objects.requireNonNull(getter3, "getter3");
        // Appelle une méthode
        Objects.requireNonNull(name4, "name4");
        // Appelle une méthode
        Objects.requireNonNull(codec4, "codec4");
        // Appelle une méthode
        Objects.requireNonNull(getter4, "getter4");
        // Appelle une méthode
        Objects.requireNonNull(name5, "name5");
        // Appelle une méthode
        Objects.requireNonNull(codec5, "codec5");
        // Appelle une méthode
        Objects.requireNonNull(getter5, "getter5");
        // Appelle une méthode
        Objects.requireNonNull(name6, "name6");
        // Appelle une méthode
        Objects.requireNonNull(codec6, "codec6");
        // Appelle une méthode
        Objects.requireNonNull(getter6, "getter6");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Appelle une méthode
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Embranchement : vérifie une condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Renvoie une valeur à l'appelant
                    return result2.cast();
                // Appelle une méthode
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Embranchement : vérifie une condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Renvoie une valeur à l'appelant
                    return result3.cast();
                // Appelle une méthode
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Embranchement : vérifie une condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Renvoie une valeur à l'appelant
                    return result4.cast();
                // Appelle une méthode
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Embranchement : vérifie une condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Renvoie une valeur à l'appelant
                    return result5.cast();
                // Appelle une méthode
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Embranchement : vérifie une condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Renvoie une valeur à l'appelant
                    return result6.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Appelle une méthode
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Embranchement : vérifie une condition
                if (result2 != null) return result2;
                // Appelle une méthode
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Embranchement : vérifie une condition
                if (result3 != null) return result3;
                // Appelle une méthode
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Embranchement : vérifie une condition
                if (result4 != null) return result4;
                // Appelle une méthode
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Embranchement : vérifie une condition
                if (result5 != null) return result5;
                // Appelle une méthode
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Embranchement : vérifie une condition
                if (result6 != null) return result6;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            String name2, Codec<P2> codec2, Function<R, P2> getter2,
            // Instruction de code
            String name3, Codec<P3> codec3, Function<R, P3> getter3,
            // Instruction de code
            String name4, Codec<P4> codec4, Function<R, P4> getter4,
            // Instruction de code
            String name5, Codec<P5> codec5, Function<R, P5> getter5,
            // Instruction de code
            String name6, Codec<P6> codec6, Function<R, P6> getter6,
            // Instruction de code
            String name7, Codec<P7> codec7, Function<R, P7> getter7,
            // Instruction de code
            F7<P1, P2, P3, P4, P5, P6, P7, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(name2, "name2");
        // Appelle une méthode
        Objects.requireNonNull(codec2, "codec2");
        // Appelle une méthode
        Objects.requireNonNull(getter2, "getter2");
        // Appelle une méthode
        Objects.requireNonNull(name3, "name3");
        // Appelle une méthode
        Objects.requireNonNull(codec3, "codec3");
        // Appelle une méthode
        Objects.requireNonNull(getter3, "getter3");
        // Appelle une méthode
        Objects.requireNonNull(name4, "name4");
        // Appelle une méthode
        Objects.requireNonNull(codec4, "codec4");
        // Appelle une méthode
        Objects.requireNonNull(getter4, "getter4");
        // Appelle une méthode
        Objects.requireNonNull(name5, "name5");
        // Appelle une méthode
        Objects.requireNonNull(codec5, "codec5");
        // Appelle une méthode
        Objects.requireNonNull(getter5, "getter5");
        // Appelle une méthode
        Objects.requireNonNull(name6, "name6");
        // Appelle une méthode
        Objects.requireNonNull(codec6, "codec6");
        // Appelle une méthode
        Objects.requireNonNull(getter6, "getter6");
        // Appelle une méthode
        Objects.requireNonNull(name7, "name7");
        // Appelle une méthode
        Objects.requireNonNull(codec7, "codec7");
        // Appelle une méthode
        Objects.requireNonNull(getter7, "getter7");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Appelle une méthode
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Embranchement : vérifie une condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Renvoie une valeur à l'appelant
                    return result2.cast();
                // Appelle une méthode
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Embranchement : vérifie une condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Renvoie une valeur à l'appelant
                    return result3.cast();
                // Appelle une méthode
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Embranchement : vérifie une condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Renvoie une valeur à l'appelant
                    return result4.cast();
                // Appelle une méthode
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Embranchement : vérifie une condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Renvoie une valeur à l'appelant
                    return result5.cast();
                // Appelle une méthode
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Embranchement : vérifie une condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Renvoie une valeur à l'appelant
                    return result6.cast();
                // Appelle une méthode
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Embranchement : vérifie une condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Renvoie une valeur à l'appelant
                    return result7.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Appelle une méthode
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Embranchement : vérifie une condition
                if (result2 != null) return result2;
                // Appelle une méthode
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Embranchement : vérifie une condition
                if (result3 != null) return result3;
                // Appelle une méthode
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Embranchement : vérifie une condition
                if (result4 != null) return result4;
                // Appelle une méthode
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Embranchement : vérifie une condition
                if (result5 != null) return result5;
                // Appelle une méthode
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Embranchement : vérifie une condition
                if (result6 != null) return result6;
                // Appelle une méthode
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Embranchement : vérifie une condition
                if (result7 != null) return result7;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            String name2, Codec<P2> codec2, Function<R, P2> getter2,
            // Instruction de code
            String name3, Codec<P3> codec3, Function<R, P3> getter3,
            // Instruction de code
            String name4, Codec<P4> codec4, Function<R, P4> getter4,
            // Instruction de code
            String name5, Codec<P5> codec5, Function<R, P5> getter5,
            // Instruction de code
            String name6, Codec<P6> codec6, Function<R, P6> getter6,
            // Instruction de code
            String name7, Codec<P7> codec7, Function<R, P7> getter7,
            // Instruction de code
            String name8, Codec<P8> codec8, Function<R, P8> getter8,
            // Instruction de code
            F8<P1, P2, P3, P4, P5, P6, P7, P8, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(name2, "name2");
        // Appelle une méthode
        Objects.requireNonNull(codec2, "codec2");
        // Appelle une méthode
        Objects.requireNonNull(getter2, "getter2");
        // Appelle une méthode
        Objects.requireNonNull(name3, "name3");
        // Appelle une méthode
        Objects.requireNonNull(codec3, "codec3");
        // Appelle une méthode
        Objects.requireNonNull(getter3, "getter3");
        // Appelle une méthode
        Objects.requireNonNull(name4, "name4");
        // Appelle une méthode
        Objects.requireNonNull(codec4, "codec4");
        // Appelle une méthode
        Objects.requireNonNull(getter4, "getter4");
        // Appelle une méthode
        Objects.requireNonNull(name5, "name5");
        // Appelle une méthode
        Objects.requireNonNull(codec5, "codec5");
        // Appelle une méthode
        Objects.requireNonNull(getter5, "getter5");
        // Appelle une méthode
        Objects.requireNonNull(name6, "name6");
        // Appelle une méthode
        Objects.requireNonNull(codec6, "codec6");
        // Appelle une méthode
        Objects.requireNonNull(getter6, "getter6");
        // Appelle une méthode
        Objects.requireNonNull(name7, "name7");
        // Appelle une méthode
        Objects.requireNonNull(codec7, "codec7");
        // Appelle une méthode
        Objects.requireNonNull(getter7, "getter7");
        // Appelle une méthode
        Objects.requireNonNull(name8, "name8");
        // Appelle une méthode
        Objects.requireNonNull(codec8, "codec8");
        // Appelle une méthode
        Objects.requireNonNull(getter8, "getter8");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Appelle une méthode
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Embranchement : vérifie une condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Renvoie une valeur à l'appelant
                    return result2.cast();
                // Appelle une méthode
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Embranchement : vérifie une condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Renvoie une valeur à l'appelant
                    return result3.cast();
                // Appelle une méthode
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Embranchement : vérifie une condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Renvoie une valeur à l'appelant
                    return result4.cast();
                // Appelle une méthode
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Embranchement : vérifie une condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Renvoie une valeur à l'appelant
                    return result5.cast();
                // Appelle une méthode
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Embranchement : vérifie une condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Renvoie une valeur à l'appelant
                    return result6.cast();
                // Appelle une méthode
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Embranchement : vérifie une condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Renvoie une valeur à l'appelant
                    return result7.cast();
                // Appelle une méthode
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Embranchement : vérifie une condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Renvoie une valeur à l'appelant
                    return result8.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Appelle une méthode
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Embranchement : vérifie une condition
                if (result2 != null) return result2;
                // Appelle une méthode
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Embranchement : vérifie une condition
                if (result3 != null) return result3;
                // Appelle une méthode
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Embranchement : vérifie une condition
                if (result4 != null) return result4;
                // Appelle une méthode
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Embranchement : vérifie une condition
                if (result5 != null) return result5;
                // Appelle une méthode
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Embranchement : vérifie une condition
                if (result6 != null) return result6;
                // Appelle une méthode
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Embranchement : vérifie une condition
                if (result7 != null) return result7;
                // Appelle une méthode
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Embranchement : vérifie une condition
                if (result8 != null) return result8;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            String name2, Codec<P2> codec2, Function<R, P2> getter2,
            // Instruction de code
            String name3, Codec<P3> codec3, Function<R, P3> getter3,
            // Instruction de code
            String name4, Codec<P4> codec4, Function<R, P4> getter4,
            // Instruction de code
            String name5, Codec<P5> codec5, Function<R, P5> getter5,
            // Instruction de code
            String name6, Codec<P6> codec6, Function<R, P6> getter6,
            // Instruction de code
            String name7, Codec<P7> codec7, Function<R, P7> getter7,
            // Instruction de code
            String name8, Codec<P8> codec8, Function<R, P8> getter8,
            // Instruction de code
            String name9, Codec<P9> codec9, Function<R, P9> getter9,
            // Instruction de code
            F9<P1, P2, P3, P4, P5, P6, P7, P8, P9, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(name2, "name2");
        // Appelle une méthode
        Objects.requireNonNull(codec2, "codec2");
        // Appelle une méthode
        Objects.requireNonNull(getter2, "getter2");
        // Appelle une méthode
        Objects.requireNonNull(name3, "name3");
        // Appelle une méthode
        Objects.requireNonNull(codec3, "codec3");
        // Appelle une méthode
        Objects.requireNonNull(getter3, "getter3");
        // Appelle une méthode
        Objects.requireNonNull(name4, "name4");
        // Appelle une méthode
        Objects.requireNonNull(codec4, "codec4");
        // Appelle une méthode
        Objects.requireNonNull(getter4, "getter4");
        // Appelle une méthode
        Objects.requireNonNull(name5, "name5");
        // Appelle une méthode
        Objects.requireNonNull(codec5, "codec5");
        // Appelle une méthode
        Objects.requireNonNull(getter5, "getter5");
        // Appelle une méthode
        Objects.requireNonNull(name6, "name6");
        // Appelle une méthode
        Objects.requireNonNull(codec6, "codec6");
        // Appelle une méthode
        Objects.requireNonNull(getter6, "getter6");
        // Appelle une méthode
        Objects.requireNonNull(name7, "name7");
        // Appelle une méthode
        Objects.requireNonNull(codec7, "codec7");
        // Appelle une méthode
        Objects.requireNonNull(getter7, "getter7");
        // Appelle une méthode
        Objects.requireNonNull(name8, "name8");
        // Appelle une méthode
        Objects.requireNonNull(codec8, "codec8");
        // Appelle une méthode
        Objects.requireNonNull(getter8, "getter8");
        // Appelle une méthode
        Objects.requireNonNull(name9, "name9");
        // Appelle une méthode
        Objects.requireNonNull(codec9, "codec9");
        // Appelle une méthode
        Objects.requireNonNull(getter9, "getter9");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Appelle une méthode
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Embranchement : vérifie une condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Renvoie une valeur à l'appelant
                    return result2.cast();
                // Appelle une méthode
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Embranchement : vérifie une condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Renvoie une valeur à l'appelant
                    return result3.cast();
                // Appelle une méthode
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Embranchement : vérifie une condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Renvoie une valeur à l'appelant
                    return result4.cast();
                // Appelle une méthode
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Embranchement : vérifie une condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Renvoie une valeur à l'appelant
                    return result5.cast();
                // Appelle une méthode
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Embranchement : vérifie une condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Renvoie une valeur à l'appelant
                    return result6.cast();
                // Appelle une méthode
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Embranchement : vérifie une condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Renvoie une valeur à l'appelant
                    return result7.cast();
                // Appelle une méthode
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Embranchement : vérifie une condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Renvoie une valeur à l'appelant
                    return result8.cast();
                // Appelle une méthode
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Embranchement : vérifie une condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Renvoie une valeur à l'appelant
                    return result9.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Appelle une méthode
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Embranchement : vérifie une condition
                if (result2 != null) return result2;
                // Appelle une méthode
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Embranchement : vérifie une condition
                if (result3 != null) return result3;
                // Appelle une méthode
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Embranchement : vérifie une condition
                if (result4 != null) return result4;
                // Appelle une méthode
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Embranchement : vérifie une condition
                if (result5 != null) return result5;
                // Appelle une méthode
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Embranchement : vérifie une condition
                if (result6 != null) return result6;
                // Appelle une méthode
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Embranchement : vérifie une condition
                if (result7 != null) return result7;
                // Appelle une méthode
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Embranchement : vérifie une condition
                if (result8 != null) return result8;
                // Appelle une méthode
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Embranchement : vérifie une condition
                if (result9 != null) return result9;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            String name2, Codec<P2> codec2, Function<R, P2> getter2,
            // Instruction de code
            String name3, Codec<P3> codec3, Function<R, P3> getter3,
            // Instruction de code
            String name4, Codec<P4> codec4, Function<R, P4> getter4,
            // Instruction de code
            String name5, Codec<P5> codec5, Function<R, P5> getter5,
            // Instruction de code
            String name6, Codec<P6> codec6, Function<R, P6> getter6,
            // Instruction de code
            String name7, Codec<P7> codec7, Function<R, P7> getter7,
            // Instruction de code
            String name8, Codec<P8> codec8, Function<R, P8> getter8,
            // Instruction de code
            String name9, Codec<P9> codec9, Function<R, P9> getter9,
            // Instruction de code
            String name10, Codec<P10> codec10, Function<R, P10> getter10,
            // Instruction de code
            F10<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(name2, "name2");
        // Appelle une méthode
        Objects.requireNonNull(codec2, "codec2");
        // Appelle une méthode
        Objects.requireNonNull(getter2, "getter2");
        // Appelle une méthode
        Objects.requireNonNull(name3, "name3");
        // Appelle une méthode
        Objects.requireNonNull(codec3, "codec3");
        // Appelle une méthode
        Objects.requireNonNull(getter3, "getter3");
        // Appelle une méthode
        Objects.requireNonNull(name4, "name4");
        // Appelle une méthode
        Objects.requireNonNull(codec4, "codec4");
        // Appelle une méthode
        Objects.requireNonNull(getter4, "getter4");
        // Appelle une méthode
        Objects.requireNonNull(name5, "name5");
        // Appelle une méthode
        Objects.requireNonNull(codec5, "codec5");
        // Appelle une méthode
        Objects.requireNonNull(getter5, "getter5");
        // Appelle une méthode
        Objects.requireNonNull(name6, "name6");
        // Appelle une méthode
        Objects.requireNonNull(codec6, "codec6");
        // Appelle une méthode
        Objects.requireNonNull(getter6, "getter6");
        // Appelle une méthode
        Objects.requireNonNull(name7, "name7");
        // Appelle une méthode
        Objects.requireNonNull(codec7, "codec7");
        // Appelle une méthode
        Objects.requireNonNull(getter7, "getter7");
        // Appelle une méthode
        Objects.requireNonNull(name8, "name8");
        // Appelle une méthode
        Objects.requireNonNull(codec8, "codec8");
        // Appelle une méthode
        Objects.requireNonNull(getter8, "getter8");
        // Appelle une méthode
        Objects.requireNonNull(name9, "name9");
        // Appelle une méthode
        Objects.requireNonNull(codec9, "codec9");
        // Appelle une méthode
        Objects.requireNonNull(getter9, "getter9");
        // Appelle une méthode
        Objects.requireNonNull(name10, "name10");
        // Appelle une méthode
        Objects.requireNonNull(codec10, "codec10");
        // Appelle une méthode
        Objects.requireNonNull(getter10, "getter10");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Appelle une méthode
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Embranchement : vérifie une condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Renvoie une valeur à l'appelant
                    return result2.cast();
                // Appelle une méthode
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Embranchement : vérifie une condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Renvoie une valeur à l'appelant
                    return result3.cast();
                // Appelle une méthode
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Embranchement : vérifie une condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Renvoie une valeur à l'appelant
                    return result4.cast();
                // Appelle une méthode
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Embranchement : vérifie une condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Renvoie une valeur à l'appelant
                    return result5.cast();
                // Appelle une méthode
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Embranchement : vérifie une condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Renvoie une valeur à l'appelant
                    return result6.cast();
                // Appelle une méthode
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Embranchement : vérifie une condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Renvoie une valeur à l'appelant
                    return result7.cast();
                // Appelle une méthode
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Embranchement : vérifie une condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Renvoie une valeur à l'appelant
                    return result8.cast();
                // Appelle une méthode
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Embranchement : vérifie une condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Renvoie une valeur à l'appelant
                    return result9.cast();
                // Appelle une méthode
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Embranchement : vérifie une condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Renvoie une valeur à l'appelant
                    return result10.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Appelle une méthode
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Embranchement : vérifie une condition
                if (result2 != null) return result2;
                // Appelle une méthode
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Embranchement : vérifie une condition
                if (result3 != null) return result3;
                // Appelle une méthode
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Embranchement : vérifie une condition
                if (result4 != null) return result4;
                // Appelle une méthode
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Embranchement : vérifie une condition
                if (result5 != null) return result5;
                // Appelle une méthode
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Embranchement : vérifie une condition
                if (result6 != null) return result6;
                // Appelle une méthode
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Embranchement : vérifie une condition
                if (result7 != null) return result7;
                // Appelle une méthode
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Embranchement : vérifie une condition
                if (result8 != null) return result8;
                // Appelle une méthode
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Embranchement : vérifie une condition
                if (result9 != null) return result9;
                // Appelle une méthode
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Embranchement : vérifie une condition
                if (result10 != null) return result10;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            String name2, Codec<P2> codec2, Function<R, P2> getter2,
            // Instruction de code
            String name3, Codec<P3> codec3, Function<R, P3> getter3,
            // Instruction de code
            String name4, Codec<P4> codec4, Function<R, P4> getter4,
            // Instruction de code
            String name5, Codec<P5> codec5, Function<R, P5> getter5,
            // Instruction de code
            String name6, Codec<P6> codec6, Function<R, P6> getter6,
            // Instruction de code
            String name7, Codec<P7> codec7, Function<R, P7> getter7,
            // Instruction de code
            String name8, Codec<P8> codec8, Function<R, P8> getter8,
            // Instruction de code
            String name9, Codec<P9> codec9, Function<R, P9> getter9,
            // Instruction de code
            String name10, Codec<P10> codec10, Function<R, P10> getter10,
            // Instruction de code
            String name11, Codec<P11> codec11, Function<R, P11> getter11,
            // Instruction de code
            F11<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(name2, "name2");
        // Appelle une méthode
        Objects.requireNonNull(codec2, "codec2");
        // Appelle une méthode
        Objects.requireNonNull(getter2, "getter2");
        // Appelle une méthode
        Objects.requireNonNull(name3, "name3");
        // Appelle une méthode
        Objects.requireNonNull(codec3, "codec3");
        // Appelle une méthode
        Objects.requireNonNull(getter3, "getter3");
        // Appelle une méthode
        Objects.requireNonNull(name4, "name4");
        // Appelle une méthode
        Objects.requireNonNull(codec4, "codec4");
        // Appelle une méthode
        Objects.requireNonNull(getter4, "getter4");
        // Appelle une méthode
        Objects.requireNonNull(name5, "name5");
        // Appelle une méthode
        Objects.requireNonNull(codec5, "codec5");
        // Appelle une méthode
        Objects.requireNonNull(getter5, "getter5");
        // Appelle une méthode
        Objects.requireNonNull(name6, "name6");
        // Appelle une méthode
        Objects.requireNonNull(codec6, "codec6");
        // Appelle une méthode
        Objects.requireNonNull(getter6, "getter6");
        // Appelle une méthode
        Objects.requireNonNull(name7, "name7");
        // Appelle une méthode
        Objects.requireNonNull(codec7, "codec7");
        // Appelle une méthode
        Objects.requireNonNull(getter7, "getter7");
        // Appelle une méthode
        Objects.requireNonNull(name8, "name8");
        // Appelle une méthode
        Objects.requireNonNull(codec8, "codec8");
        // Appelle une méthode
        Objects.requireNonNull(getter8, "getter8");
        // Appelle une méthode
        Objects.requireNonNull(name9, "name9");
        // Appelle une méthode
        Objects.requireNonNull(codec9, "codec9");
        // Appelle une méthode
        Objects.requireNonNull(getter9, "getter9");
        // Appelle une méthode
        Objects.requireNonNull(name10, "name10");
        // Appelle une méthode
        Objects.requireNonNull(codec10, "codec10");
        // Appelle une méthode
        Objects.requireNonNull(getter10, "getter10");
        // Appelle une méthode
        Objects.requireNonNull(name11, "name11");
        // Appelle une méthode
        Objects.requireNonNull(codec11, "codec11");
        // Appelle une méthode
        Objects.requireNonNull(getter11, "getter11");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Appelle une méthode
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Embranchement : vérifie une condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Renvoie une valeur à l'appelant
                    return result2.cast();
                // Appelle une méthode
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Embranchement : vérifie une condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Renvoie une valeur à l'appelant
                    return result3.cast();
                // Appelle une méthode
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Embranchement : vérifie une condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Renvoie une valeur à l'appelant
                    return result4.cast();
                // Appelle une méthode
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Embranchement : vérifie une condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Renvoie une valeur à l'appelant
                    return result5.cast();
                // Appelle une méthode
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Embranchement : vérifie une condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Renvoie une valeur à l'appelant
                    return result6.cast();
                // Appelle une méthode
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Embranchement : vérifie une condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Renvoie une valeur à l'appelant
                    return result7.cast();
                // Appelle une méthode
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Embranchement : vérifie une condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Renvoie une valeur à l'appelant
                    return result8.cast();
                // Appelle une méthode
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Embranchement : vérifie une condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Renvoie une valeur à l'appelant
                    return result9.cast();
                // Appelle une méthode
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Embranchement : vérifie une condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Renvoie une valeur à l'appelant
                    return result10.cast();
                // Appelle une méthode
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Embranchement : vérifie une condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Renvoie une valeur à l'appelant
                    return result11.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Appelle une méthode
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Embranchement : vérifie une condition
                if (result2 != null) return result2;
                // Appelle une méthode
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Embranchement : vérifie une condition
                if (result3 != null) return result3;
                // Appelle une méthode
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Embranchement : vérifie une condition
                if (result4 != null) return result4;
                // Appelle une méthode
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Embranchement : vérifie une condition
                if (result5 != null) return result5;
                // Appelle une méthode
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Embranchement : vérifie une condition
                if (result6 != null) return result6;
                // Appelle une méthode
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Embranchement : vérifie une condition
                if (result7 != null) return result7;
                // Appelle une méthode
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Embranchement : vérifie une condition
                if (result8 != null) return result8;
                // Appelle une méthode
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Embranchement : vérifie une condition
                if (result9 != null) return result9;
                // Appelle une méthode
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Embranchement : vérifie une condition
                if (result10 != null) return result10;
                // Appelle une méthode
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Embranchement : vérifie une condition
                if (result11 != null) return result11;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            String name2, Codec<P2> codec2, Function<R, P2> getter2,
            // Instruction de code
            String name3, Codec<P3> codec3, Function<R, P3> getter3,
            // Instruction de code
            String name4, Codec<P4> codec4, Function<R, P4> getter4,
            // Instruction de code
            String name5, Codec<P5> codec5, Function<R, P5> getter5,
            // Instruction de code
            String name6, Codec<P6> codec6, Function<R, P6> getter6,
            // Instruction de code
            String name7, Codec<P7> codec7, Function<R, P7> getter7,
            // Instruction de code
            String name8, Codec<P8> codec8, Function<R, P8> getter8,
            // Instruction de code
            String name9, Codec<P9> codec9, Function<R, P9> getter9,
            // Instruction de code
            String name10, Codec<P10> codec10, Function<R, P10> getter10,
            // Instruction de code
            String name11, Codec<P11> codec11, Function<R, P11> getter11,
            // Instruction de code
            String name12, Codec<P12> codec12, Function<R, P12> getter12,
            // Instruction de code
            F12<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(name2, "name2");
        // Appelle une méthode
        Objects.requireNonNull(codec2, "codec2");
        // Appelle une méthode
        Objects.requireNonNull(getter2, "getter2");
        // Appelle une méthode
        Objects.requireNonNull(name3, "name3");
        // Appelle une méthode
        Objects.requireNonNull(codec3, "codec3");
        // Appelle une méthode
        Objects.requireNonNull(getter3, "getter3");
        // Appelle une méthode
        Objects.requireNonNull(name4, "name4");
        // Appelle une méthode
        Objects.requireNonNull(codec4, "codec4");
        // Appelle une méthode
        Objects.requireNonNull(getter4, "getter4");
        // Appelle une méthode
        Objects.requireNonNull(name5, "name5");
        // Appelle une méthode
        Objects.requireNonNull(codec5, "codec5");
        // Appelle une méthode
        Objects.requireNonNull(getter5, "getter5");
        // Appelle une méthode
        Objects.requireNonNull(name6, "name6");
        // Appelle une méthode
        Objects.requireNonNull(codec6, "codec6");
        // Appelle une méthode
        Objects.requireNonNull(getter6, "getter6");
        // Appelle une méthode
        Objects.requireNonNull(name7, "name7");
        // Appelle une méthode
        Objects.requireNonNull(codec7, "codec7");
        // Appelle une méthode
        Objects.requireNonNull(getter7, "getter7");
        // Appelle une méthode
        Objects.requireNonNull(name8, "name8");
        // Appelle une méthode
        Objects.requireNonNull(codec8, "codec8");
        // Appelle une méthode
        Objects.requireNonNull(getter8, "getter8");
        // Appelle une méthode
        Objects.requireNonNull(name9, "name9");
        // Appelle une méthode
        Objects.requireNonNull(codec9, "codec9");
        // Appelle une méthode
        Objects.requireNonNull(getter9, "getter9");
        // Appelle une méthode
        Objects.requireNonNull(name10, "name10");
        // Appelle une méthode
        Objects.requireNonNull(codec10, "codec10");
        // Appelle une méthode
        Objects.requireNonNull(getter10, "getter10");
        // Appelle une méthode
        Objects.requireNonNull(name11, "name11");
        // Appelle une méthode
        Objects.requireNonNull(codec11, "codec11");
        // Appelle une méthode
        Objects.requireNonNull(getter11, "getter11");
        // Appelle une méthode
        Objects.requireNonNull(name12, "name12");
        // Appelle une méthode
        Objects.requireNonNull(codec12, "codec12");
        // Appelle une méthode
        Objects.requireNonNull(getter12, "getter12");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Appelle une méthode
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Embranchement : vérifie une condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Renvoie une valeur à l'appelant
                    return result2.cast();
                // Appelle une méthode
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Embranchement : vérifie une condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Renvoie une valeur à l'appelant
                    return result3.cast();
                // Appelle une méthode
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Embranchement : vérifie une condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Renvoie une valeur à l'appelant
                    return result4.cast();
                // Appelle une méthode
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Embranchement : vérifie une condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Renvoie une valeur à l'appelant
                    return result5.cast();
                // Appelle une méthode
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Embranchement : vérifie une condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Renvoie une valeur à l'appelant
                    return result6.cast();
                // Appelle une méthode
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Embranchement : vérifie une condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Renvoie une valeur à l'appelant
                    return result7.cast();
                // Appelle une méthode
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Embranchement : vérifie une condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Renvoie une valeur à l'appelant
                    return result8.cast();
                // Appelle une méthode
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Embranchement : vérifie une condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Renvoie une valeur à l'appelant
                    return result9.cast();
                // Appelle une méthode
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Embranchement : vérifie une condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Renvoie une valeur à l'appelant
                    return result10.cast();
                // Appelle une méthode
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Embranchement : vérifie une condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Renvoie une valeur à l'appelant
                    return result11.cast();
                // Appelle une méthode
                final Result<P12> result12 = get(coder, codec12, name12, map);
                // Embranchement : vérifie une condition
                if (!(result12 instanceof Result.Ok(P12 value12)))
                    // Renvoie une valeur à l'appelant
                    return result12.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Appelle une méthode
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Embranchement : vérifie une condition
                if (result2 != null) return result2;
                // Appelle une méthode
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Embranchement : vérifie une condition
                if (result3 != null) return result3;
                // Appelle une méthode
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Embranchement : vérifie une condition
                if (result4 != null) return result4;
                // Appelle une méthode
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Embranchement : vérifie une condition
                if (result5 != null) return result5;
                // Appelle une méthode
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Embranchement : vérifie une condition
                if (result6 != null) return result6;
                // Appelle une méthode
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Embranchement : vérifie une condition
                if (result7 != null) return result7;
                // Appelle une méthode
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Embranchement : vérifie une condition
                if (result8 != null) return result8;
                // Appelle une méthode
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Embranchement : vérifie une condition
                if (result9 != null) return result9;
                // Appelle une méthode
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Embranchement : vérifie une condition
                if (result10 != null) return result10;
                // Appelle une méthode
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Embranchement : vérifie une condition
                if (result11 != null) return result11;
                // Appelle une méthode
                final Result<D> result12 = put(coder, codec12, map, name12, getter12.apply(value));
                // Embranchement : vérifie une condition
                if (result12 != null) return result12;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            String name2, Codec<P2> codec2, Function<R, P2> getter2,
            // Instruction de code
            String name3, Codec<P3> codec3, Function<R, P3> getter3,
            // Instruction de code
            String name4, Codec<P4> codec4, Function<R, P4> getter4,
            // Instruction de code
            String name5, Codec<P5> codec5, Function<R, P5> getter5,
            // Instruction de code
            String name6, Codec<P6> codec6, Function<R, P6> getter6,
            // Instruction de code
            String name7, Codec<P7> codec7, Function<R, P7> getter7,
            // Instruction de code
            String name8, Codec<P8> codec8, Function<R, P8> getter8,
            // Instruction de code
            String name9, Codec<P9> codec9, Function<R, P9> getter9,
            // Instruction de code
            String name10, Codec<P10> codec10, Function<R, P10> getter10,
            // Instruction de code
            String name11, Codec<P11> codec11, Function<R, P11> getter11,
            // Instruction de code
            String name12, Codec<P12> codec12, Function<R, P12> getter12,
            // Instruction de code
            String name13, Codec<P13> codec13, Function<R, P13> getter13,
            // Instruction de code
            F13<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(name2, "name2");
        // Appelle une méthode
        Objects.requireNonNull(codec2, "codec2");
        // Appelle une méthode
        Objects.requireNonNull(getter2, "getter2");
        // Appelle une méthode
        Objects.requireNonNull(name3, "name3");
        // Appelle une méthode
        Objects.requireNonNull(codec3, "codec3");
        // Appelle une méthode
        Objects.requireNonNull(getter3, "getter3");
        // Appelle une méthode
        Objects.requireNonNull(name4, "name4");
        // Appelle une méthode
        Objects.requireNonNull(codec4, "codec4");
        // Appelle une méthode
        Objects.requireNonNull(getter4, "getter4");
        // Appelle une méthode
        Objects.requireNonNull(name5, "name5");
        // Appelle une méthode
        Objects.requireNonNull(codec5, "codec5");
        // Appelle une méthode
        Objects.requireNonNull(getter5, "getter5");
        // Appelle une méthode
        Objects.requireNonNull(name6, "name6");
        // Appelle une méthode
        Objects.requireNonNull(codec6, "codec6");
        // Appelle une méthode
        Objects.requireNonNull(getter6, "getter6");
        // Appelle une méthode
        Objects.requireNonNull(name7, "name7");
        // Appelle une méthode
        Objects.requireNonNull(codec7, "codec7");
        // Appelle une méthode
        Objects.requireNonNull(getter7, "getter7");
        // Appelle une méthode
        Objects.requireNonNull(name8, "name8");
        // Appelle une méthode
        Objects.requireNonNull(codec8, "codec8");
        // Appelle une méthode
        Objects.requireNonNull(getter8, "getter8");
        // Appelle une méthode
        Objects.requireNonNull(name9, "name9");
        // Appelle une méthode
        Objects.requireNonNull(codec9, "codec9");
        // Appelle une méthode
        Objects.requireNonNull(getter9, "getter9");
        // Appelle une méthode
        Objects.requireNonNull(name10, "name10");
        // Appelle une méthode
        Objects.requireNonNull(codec10, "codec10");
        // Appelle une méthode
        Objects.requireNonNull(getter10, "getter10");
        // Appelle une méthode
        Objects.requireNonNull(name11, "name11");
        // Appelle une méthode
        Objects.requireNonNull(codec11, "codec11");
        // Appelle une méthode
        Objects.requireNonNull(getter11, "getter11");
        // Appelle une méthode
        Objects.requireNonNull(name12, "name12");
        // Appelle une méthode
        Objects.requireNonNull(codec12, "codec12");
        // Appelle une méthode
        Objects.requireNonNull(getter12, "getter12");
        // Appelle une méthode
        Objects.requireNonNull(name13, "name13");
        // Appelle une méthode
        Objects.requireNonNull(codec13, "codec13");
        // Appelle une méthode
        Objects.requireNonNull(getter13, "getter13");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Appelle une méthode
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Embranchement : vérifie une condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Renvoie une valeur à l'appelant
                    return result2.cast();
                // Appelle une méthode
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Embranchement : vérifie une condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Renvoie une valeur à l'appelant
                    return result3.cast();
                // Appelle une méthode
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Embranchement : vérifie une condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Renvoie une valeur à l'appelant
                    return result4.cast();
                // Appelle une méthode
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Embranchement : vérifie une condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Renvoie une valeur à l'appelant
                    return result5.cast();
                // Appelle une méthode
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Embranchement : vérifie une condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Renvoie une valeur à l'appelant
                    return result6.cast();
                // Appelle une méthode
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Embranchement : vérifie une condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Renvoie une valeur à l'appelant
                    return result7.cast();
                // Appelle une méthode
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Embranchement : vérifie une condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Renvoie une valeur à l'appelant
                    return result8.cast();
                // Appelle une méthode
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Embranchement : vérifie une condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Renvoie une valeur à l'appelant
                    return result9.cast();
                // Appelle une méthode
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Embranchement : vérifie une condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Renvoie une valeur à l'appelant
                    return result10.cast();
                // Appelle une méthode
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Embranchement : vérifie une condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Renvoie une valeur à l'appelant
                    return result11.cast();
                // Appelle une méthode
                final Result<P12> result12 = get(coder, codec12, name12, map);
                // Embranchement : vérifie une condition
                if (!(result12 instanceof Result.Ok(P12 value12)))
                    // Renvoie une valeur à l'appelant
                    return result12.cast();
                // Appelle une méthode
                final Result<P13> result13 = get(coder, codec13, name13, map);
                // Embranchement : vérifie une condition
                if (!(result13 instanceof Result.Ok(P13 value13)))
                    // Renvoie une valeur à l'appelant
                    return result13.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Appelle une méthode
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Embranchement : vérifie une condition
                if (result2 != null) return result2;
                // Appelle une méthode
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Embranchement : vérifie une condition
                if (result3 != null) return result3;
                // Appelle une méthode
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Embranchement : vérifie une condition
                if (result4 != null) return result4;
                // Appelle une méthode
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Embranchement : vérifie une condition
                if (result5 != null) return result5;
                // Appelle une méthode
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Embranchement : vérifie une condition
                if (result6 != null) return result6;
                // Appelle une méthode
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Embranchement : vérifie une condition
                if (result7 != null) return result7;
                // Appelle une méthode
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Embranchement : vérifie une condition
                if (result8 != null) return result8;
                // Appelle une méthode
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Embranchement : vérifie une condition
                if (result9 != null) return result9;
                // Appelle une méthode
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Embranchement : vérifie une condition
                if (result10 != null) return result10;
                // Appelle une méthode
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Embranchement : vérifie une condition
                if (result11 != null) return result11;
                // Appelle une méthode
                final Result<D> result12 = put(coder, codec12, map, name12, getter12.apply(value));
                // Embranchement : vérifie une condition
                if (result12 != null) return result12;
                // Appelle une méthode
                final Result<D> result13 = put(coder, codec13, map, name13, getter13.apply(value));
                // Embranchement : vérifie une condition
                if (result13 != null) return result13;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            String name2, Codec<P2> codec2, Function<R, P2> getter2,
            // Instruction de code
            String name3, Codec<P3> codec3, Function<R, P3> getter3,
            // Instruction de code
            String name4, Codec<P4> codec4, Function<R, P4> getter4,
            // Instruction de code
            String name5, Codec<P5> codec5, Function<R, P5> getter5,
            // Instruction de code
            String name6, Codec<P6> codec6, Function<R, P6> getter6,
            // Instruction de code
            String name7, Codec<P7> codec7, Function<R, P7> getter7,
            // Instruction de code
            String name8, Codec<P8> codec8, Function<R, P8> getter8,
            // Instruction de code
            String name9, Codec<P9> codec9, Function<R, P9> getter9,
            // Instruction de code
            String name10, Codec<P10> codec10, Function<R, P10> getter10,
            // Instruction de code
            String name11, Codec<P11> codec11, Function<R, P11> getter11,
            // Instruction de code
            String name12, Codec<P12> codec12, Function<R, P12> getter12,
            // Instruction de code
            String name13, Codec<P13> codec13, Function<R, P13> getter13,
            // Instruction de code
            String name14, Codec<P14> codec14, Function<R, P14> getter14,
            // Instruction de code
            F14<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(name2, "name2");
        // Appelle une méthode
        Objects.requireNonNull(codec2, "codec2");
        // Appelle une méthode
        Objects.requireNonNull(getter2, "getter2");
        // Appelle une méthode
        Objects.requireNonNull(name3, "name3");
        // Appelle une méthode
        Objects.requireNonNull(codec3, "codec3");
        // Appelle une méthode
        Objects.requireNonNull(getter3, "getter3");
        // Appelle une méthode
        Objects.requireNonNull(name4, "name4");
        // Appelle une méthode
        Objects.requireNonNull(codec4, "codec4");
        // Appelle une méthode
        Objects.requireNonNull(getter4, "getter4");
        // Appelle une méthode
        Objects.requireNonNull(name5, "name5");
        // Appelle une méthode
        Objects.requireNonNull(codec5, "codec5");
        // Appelle une méthode
        Objects.requireNonNull(getter5, "getter5");
        // Appelle une méthode
        Objects.requireNonNull(name6, "name6");
        // Appelle une méthode
        Objects.requireNonNull(codec6, "codec6");
        // Appelle une méthode
        Objects.requireNonNull(getter6, "getter6");
        // Appelle une méthode
        Objects.requireNonNull(name7, "name7");
        // Appelle une méthode
        Objects.requireNonNull(codec7, "codec7");
        // Appelle une méthode
        Objects.requireNonNull(getter7, "getter7");
        // Appelle une méthode
        Objects.requireNonNull(name8, "name8");
        // Appelle une méthode
        Objects.requireNonNull(codec8, "codec8");
        // Appelle une méthode
        Objects.requireNonNull(getter8, "getter8");
        // Appelle une méthode
        Objects.requireNonNull(name9, "name9");
        // Appelle une méthode
        Objects.requireNonNull(codec9, "codec9");
        // Appelle une méthode
        Objects.requireNonNull(getter9, "getter9");
        // Appelle une méthode
        Objects.requireNonNull(name10, "name10");
        // Appelle une méthode
        Objects.requireNonNull(codec10, "codec10");
        // Appelle une méthode
        Objects.requireNonNull(getter10, "getter10");
        // Appelle une méthode
        Objects.requireNonNull(name11, "name11");
        // Appelle une méthode
        Objects.requireNonNull(codec11, "codec11");
        // Appelle une méthode
        Objects.requireNonNull(getter11, "getter11");
        // Appelle une méthode
        Objects.requireNonNull(name12, "name12");
        // Appelle une méthode
        Objects.requireNonNull(codec12, "codec12");
        // Appelle une méthode
        Objects.requireNonNull(getter12, "getter12");
        // Appelle une méthode
        Objects.requireNonNull(name13, "name13");
        // Appelle une méthode
        Objects.requireNonNull(codec13, "codec13");
        // Appelle une méthode
        Objects.requireNonNull(getter13, "getter13");
        // Appelle une méthode
        Objects.requireNonNull(name14, "name14");
        // Appelle une méthode
        Objects.requireNonNull(codec14, "codec14");
        // Appelle une méthode
        Objects.requireNonNull(getter14, "getter14");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Appelle une méthode
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Embranchement : vérifie une condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Renvoie une valeur à l'appelant
                    return result2.cast();
                // Appelle une méthode
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Embranchement : vérifie une condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Renvoie une valeur à l'appelant
                    return result3.cast();
                // Appelle une méthode
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Embranchement : vérifie une condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Renvoie une valeur à l'appelant
                    return result4.cast();
                // Appelle une méthode
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Embranchement : vérifie une condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Renvoie une valeur à l'appelant
                    return result5.cast();
                // Appelle une méthode
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Embranchement : vérifie une condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Renvoie une valeur à l'appelant
                    return result6.cast();
                // Appelle une méthode
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Embranchement : vérifie une condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Renvoie une valeur à l'appelant
                    return result7.cast();
                // Appelle une méthode
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Embranchement : vérifie une condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Renvoie une valeur à l'appelant
                    return result8.cast();
                // Appelle une méthode
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Embranchement : vérifie une condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Renvoie une valeur à l'appelant
                    return result9.cast();
                // Appelle une méthode
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Embranchement : vérifie une condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Renvoie une valeur à l'appelant
                    return result10.cast();
                // Appelle une méthode
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Embranchement : vérifie une condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Renvoie une valeur à l'appelant
                    return result11.cast();
                // Appelle une méthode
                final Result<P12> result12 = get(coder, codec12, name12, map);
                // Embranchement : vérifie une condition
                if (!(result12 instanceof Result.Ok(P12 value12)))
                    // Renvoie une valeur à l'appelant
                    return result12.cast();
                // Appelle une méthode
                final Result<P13> result13 = get(coder, codec13, name13, map);
                // Embranchement : vérifie une condition
                if (!(result13 instanceof Result.Ok(P13 value13)))
                    // Renvoie une valeur à l'appelant
                    return result13.cast();
                // Appelle une méthode
                final Result<P14> result14 = get(coder, codec14, name14, map);
                // Embranchement : vérifie une condition
                if (!(result14 instanceof Result.Ok(P14 value14)))
                    // Renvoie une valeur à l'appelant
                    return result14.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Appelle une méthode
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Embranchement : vérifie une condition
                if (result2 != null) return result2;
                // Appelle une méthode
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Embranchement : vérifie une condition
                if (result3 != null) return result3;
                // Appelle une méthode
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Embranchement : vérifie une condition
                if (result4 != null) return result4;
                // Appelle une méthode
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Embranchement : vérifie une condition
                if (result5 != null) return result5;
                // Appelle une méthode
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Embranchement : vérifie une condition
                if (result6 != null) return result6;
                // Appelle une méthode
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Embranchement : vérifie une condition
                if (result7 != null) return result7;
                // Appelle une méthode
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Embranchement : vérifie une condition
                if (result8 != null) return result8;
                // Appelle une méthode
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Embranchement : vérifie une condition
                if (result9 != null) return result9;
                // Appelle une méthode
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Embranchement : vérifie une condition
                if (result10 != null) return result10;
                // Appelle une méthode
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Embranchement : vérifie une condition
                if (result11 != null) return result11;
                // Appelle une méthode
                final Result<D> result12 = put(coder, codec12, map, name12, getter12.apply(value));
                // Embranchement : vérifie une condition
                if (result12 != null) return result12;
                // Appelle une méthode
                final Result<D> result13 = put(coder, codec13, map, name13, getter13.apply(value));
                // Embranchement : vérifie une condition
                if (result13 != null) return result13;
                // Appelle une méthode
                final Result<D> result14 = put(coder, codec14, map, name14, getter14.apply(value));
                // Embranchement : vérifie une condition
                if (result14 != null) return result14;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            String name2, Codec<P2> codec2, Function<R, P2> getter2,
            // Instruction de code
            String name3, Codec<P3> codec3, Function<R, P3> getter3,
            // Instruction de code
            String name4, Codec<P4> codec4, Function<R, P4> getter4,
            // Instruction de code
            String name5, Codec<P5> codec5, Function<R, P5> getter5,
            // Instruction de code
            String name6, Codec<P6> codec6, Function<R, P6> getter6,
            // Instruction de code
            String name7, Codec<P7> codec7, Function<R, P7> getter7,
            // Instruction de code
            String name8, Codec<P8> codec8, Function<R, P8> getter8,
            // Instruction de code
            String name9, Codec<P9> codec9, Function<R, P9> getter9,
            // Instruction de code
            String name10, Codec<P10> codec10, Function<R, P10> getter10,
            // Instruction de code
            String name11, Codec<P11> codec11, Function<R, P11> getter11,
            // Instruction de code
            String name12, Codec<P12> codec12, Function<R, P12> getter12,
            // Instruction de code
            String name13, Codec<P13> codec13, Function<R, P13> getter13,
            // Instruction de code
            String name14, Codec<P14> codec14, Function<R, P14> getter14,
            // Instruction de code
            String name15, Codec<P15> codec15, Function<R, P15> getter15,
            // Instruction de code
            F15<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(name2, "name2");
        // Appelle une méthode
        Objects.requireNonNull(codec2, "codec2");
        // Appelle une méthode
        Objects.requireNonNull(getter2, "getter2");
        // Appelle une méthode
        Objects.requireNonNull(name3, "name3");
        // Appelle une méthode
        Objects.requireNonNull(codec3, "codec3");
        // Appelle une méthode
        Objects.requireNonNull(getter3, "getter3");
        // Appelle une méthode
        Objects.requireNonNull(name4, "name4");
        // Appelle une méthode
        Objects.requireNonNull(codec4, "codec4");
        // Appelle une méthode
        Objects.requireNonNull(getter4, "getter4");
        // Appelle une méthode
        Objects.requireNonNull(name5, "name5");
        // Appelle une méthode
        Objects.requireNonNull(codec5, "codec5");
        // Appelle une méthode
        Objects.requireNonNull(getter5, "getter5");
        // Appelle une méthode
        Objects.requireNonNull(name6, "name6");
        // Appelle une méthode
        Objects.requireNonNull(codec6, "codec6");
        // Appelle une méthode
        Objects.requireNonNull(getter6, "getter6");
        // Appelle une méthode
        Objects.requireNonNull(name7, "name7");
        // Appelle une méthode
        Objects.requireNonNull(codec7, "codec7");
        // Appelle une méthode
        Objects.requireNonNull(getter7, "getter7");
        // Appelle une méthode
        Objects.requireNonNull(name8, "name8");
        // Appelle une méthode
        Objects.requireNonNull(codec8, "codec8");
        // Appelle une méthode
        Objects.requireNonNull(getter8, "getter8");
        // Appelle une méthode
        Objects.requireNonNull(name9, "name9");
        // Appelle une méthode
        Objects.requireNonNull(codec9, "codec9");
        // Appelle une méthode
        Objects.requireNonNull(getter9, "getter9");
        // Appelle une méthode
        Objects.requireNonNull(name10, "name10");
        // Appelle une méthode
        Objects.requireNonNull(codec10, "codec10");
        // Appelle une méthode
        Objects.requireNonNull(getter10, "getter10");
        // Appelle une méthode
        Objects.requireNonNull(name11, "name11");
        // Appelle une méthode
        Objects.requireNonNull(codec11, "codec11");
        // Appelle une méthode
        Objects.requireNonNull(getter11, "getter11");
        // Appelle une méthode
        Objects.requireNonNull(name12, "name12");
        // Appelle une méthode
        Objects.requireNonNull(codec12, "codec12");
        // Appelle une méthode
        Objects.requireNonNull(getter12, "getter12");
        // Appelle une méthode
        Objects.requireNonNull(name13, "name13");
        // Appelle une méthode
        Objects.requireNonNull(codec13, "codec13");
        // Appelle une méthode
        Objects.requireNonNull(getter13, "getter13");
        // Appelle une méthode
        Objects.requireNonNull(name14, "name14");
        // Appelle une méthode
        Objects.requireNonNull(codec14, "codec14");
        // Appelle une méthode
        Objects.requireNonNull(getter14, "getter14");
        // Appelle une méthode
        Objects.requireNonNull(name15, "name15");
        // Appelle une méthode
        Objects.requireNonNull(codec15, "codec15");
        // Appelle une méthode
        Objects.requireNonNull(getter15, "getter15");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Appelle une méthode
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Embranchement : vérifie une condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Renvoie une valeur à l'appelant
                    return result2.cast();
                // Appelle une méthode
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Embranchement : vérifie une condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Renvoie une valeur à l'appelant
                    return result3.cast();
                // Appelle une méthode
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Embranchement : vérifie une condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Renvoie une valeur à l'appelant
                    return result4.cast();
                // Appelle une méthode
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Embranchement : vérifie une condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Renvoie une valeur à l'appelant
                    return result5.cast();
                // Appelle une méthode
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Embranchement : vérifie une condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Renvoie une valeur à l'appelant
                    return result6.cast();
                // Appelle une méthode
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Embranchement : vérifie une condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Renvoie une valeur à l'appelant
                    return result7.cast();
                // Appelle une méthode
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Embranchement : vérifie une condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Renvoie une valeur à l'appelant
                    return result8.cast();
                // Appelle une méthode
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Embranchement : vérifie une condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Renvoie une valeur à l'appelant
                    return result9.cast();
                // Appelle une méthode
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Embranchement : vérifie une condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Renvoie une valeur à l'appelant
                    return result10.cast();
                // Appelle une méthode
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Embranchement : vérifie une condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Renvoie une valeur à l'appelant
                    return result11.cast();
                // Appelle une méthode
                final Result<P12> result12 = get(coder, codec12, name12, map);
                // Embranchement : vérifie une condition
                if (!(result12 instanceof Result.Ok(P12 value12)))
                    // Renvoie une valeur à l'appelant
                    return result12.cast();
                // Appelle une méthode
                final Result<P13> result13 = get(coder, codec13, name13, map);
                // Embranchement : vérifie une condition
                if (!(result13 instanceof Result.Ok(P13 value13)))
                    // Renvoie une valeur à l'appelant
                    return result13.cast();
                // Appelle une méthode
                final Result<P14> result14 = get(coder, codec14, name14, map);
                // Embranchement : vérifie une condition
                if (!(result14 instanceof Result.Ok(P14 value14)))
                    // Renvoie une valeur à l'appelant
                    return result14.cast();
                // Appelle une méthode
                final Result<P15> result15 = get(coder, codec15, name15, map);
                // Embranchement : vérifie une condition
                if (!(result15 instanceof Result.Ok(P15 value15)))
                    // Renvoie une valeur à l'appelant
                    return result15.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Appelle une méthode
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Embranchement : vérifie une condition
                if (result2 != null) return result2;
                // Appelle une méthode
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Embranchement : vérifie une condition
                if (result3 != null) return result3;
                // Appelle une méthode
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Embranchement : vérifie une condition
                if (result4 != null) return result4;
                // Appelle une méthode
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Embranchement : vérifie une condition
                if (result5 != null) return result5;
                // Appelle une méthode
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Embranchement : vérifie une condition
                if (result6 != null) return result6;
                // Appelle une méthode
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Embranchement : vérifie une condition
                if (result7 != null) return result7;
                // Appelle une méthode
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Embranchement : vérifie une condition
                if (result8 != null) return result8;
                // Appelle une méthode
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Embranchement : vérifie une condition
                if (result9 != null) return result9;
                // Appelle une méthode
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Embranchement : vérifie une condition
                if (result10 != null) return result10;
                // Appelle une méthode
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Embranchement : vérifie une condition
                if (result11 != null) return result11;
                // Appelle une méthode
                final Result<D> result12 = put(coder, codec12, map, name12, getter12.apply(value));
                // Embranchement : vérifie une condition
                if (result12 != null) return result12;
                // Appelle une méthode
                final Result<D> result13 = put(coder, codec13, map, name13, getter13.apply(value));
                // Embranchement : vérifie une condition
                if (result13 != null) return result13;
                // Appelle une méthode
                final Result<D> result14 = put(coder, codec14, map, name14, getter14.apply(value));
                // Embranchement : vérifie une condition
                if (result14 != null) return result14;
                // Appelle une méthode
                final Result<D> result15 = put(coder, codec15, map, name15, getter15.apply(value));
                // Embranchement : vérifie une condition
                if (result15 != null) return result15;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            String name2, Codec<P2> codec2, Function<R, P2> getter2,
            // Instruction de code
            String name3, Codec<P3> codec3, Function<R, P3> getter3,
            // Instruction de code
            String name4, Codec<P4> codec4, Function<R, P4> getter4,
            // Instruction de code
            String name5, Codec<P5> codec5, Function<R, P5> getter5,
            // Instruction de code
            String name6, Codec<P6> codec6, Function<R, P6> getter6,
            // Instruction de code
            String name7, Codec<P7> codec7, Function<R, P7> getter7,
            // Instruction de code
            String name8, Codec<P8> codec8, Function<R, P8> getter8,
            // Instruction de code
            String name9, Codec<P9> codec9, Function<R, P9> getter9,
            // Instruction de code
            String name10, Codec<P10> codec10, Function<R, P10> getter10,
            // Instruction de code
            String name11, Codec<P11> codec11, Function<R, P11> getter11,
            // Instruction de code
            String name12, Codec<P12> codec12, Function<R, P12> getter12,
            // Instruction de code
            String name13, Codec<P13> codec13, Function<R, P13> getter13,
            // Instruction de code
            String name14, Codec<P14> codec14, Function<R, P14> getter14,
            // Instruction de code
            String name15, Codec<P15> codec15, Function<R, P15> getter15,
            // Instruction de code
            String name16, Codec<P16> codec16, Function<R, P16> getter16,
            // Instruction de code
            F16<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(name2, "name2");
        // Appelle une méthode
        Objects.requireNonNull(codec2, "codec2");
        // Appelle une méthode
        Objects.requireNonNull(getter2, "getter2");
        // Appelle une méthode
        Objects.requireNonNull(name3, "name3");
        // Appelle une méthode
        Objects.requireNonNull(codec3, "codec3");
        // Appelle une méthode
        Objects.requireNonNull(getter3, "getter3");
        // Appelle une méthode
        Objects.requireNonNull(name4, "name4");
        // Appelle une méthode
        Objects.requireNonNull(codec4, "codec4");
        // Appelle une méthode
        Objects.requireNonNull(getter4, "getter4");
        // Appelle une méthode
        Objects.requireNonNull(name5, "name5");
        // Appelle une méthode
        Objects.requireNonNull(codec5, "codec5");
        // Appelle une méthode
        Objects.requireNonNull(getter5, "getter5");
        // Appelle une méthode
        Objects.requireNonNull(name6, "name6");
        // Appelle une méthode
        Objects.requireNonNull(codec6, "codec6");
        // Appelle une méthode
        Objects.requireNonNull(getter6, "getter6");
        // Appelle une méthode
        Objects.requireNonNull(name7, "name7");
        // Appelle une méthode
        Objects.requireNonNull(codec7, "codec7");
        // Appelle une méthode
        Objects.requireNonNull(getter7, "getter7");
        // Appelle une méthode
        Objects.requireNonNull(name8, "name8");
        // Appelle une méthode
        Objects.requireNonNull(codec8, "codec8");
        // Appelle une méthode
        Objects.requireNonNull(getter8, "getter8");
        // Appelle une méthode
        Objects.requireNonNull(name9, "name9");
        // Appelle une méthode
        Objects.requireNonNull(codec9, "codec9");
        // Appelle une méthode
        Objects.requireNonNull(getter9, "getter9");
        // Appelle une méthode
        Objects.requireNonNull(name10, "name10");
        // Appelle une méthode
        Objects.requireNonNull(codec10, "codec10");
        // Appelle une méthode
        Objects.requireNonNull(getter10, "getter10");
        // Appelle une méthode
        Objects.requireNonNull(name11, "name11");
        // Appelle une méthode
        Objects.requireNonNull(codec11, "codec11");
        // Appelle une méthode
        Objects.requireNonNull(getter11, "getter11");
        // Appelle une méthode
        Objects.requireNonNull(name12, "name12");
        // Appelle une méthode
        Objects.requireNonNull(codec12, "codec12");
        // Appelle une méthode
        Objects.requireNonNull(getter12, "getter12");
        // Appelle une méthode
        Objects.requireNonNull(name13, "name13");
        // Appelle une méthode
        Objects.requireNonNull(codec13, "codec13");
        // Appelle une méthode
        Objects.requireNonNull(getter13, "getter13");
        // Appelle une méthode
        Objects.requireNonNull(name14, "name14");
        // Appelle une méthode
        Objects.requireNonNull(codec14, "codec14");
        // Appelle une méthode
        Objects.requireNonNull(getter14, "getter14");
        // Appelle une méthode
        Objects.requireNonNull(name15, "name15");
        // Appelle une méthode
        Objects.requireNonNull(codec15, "codec15");
        // Appelle une méthode
        Objects.requireNonNull(getter15, "getter15");
        // Appelle une méthode
        Objects.requireNonNull(name16, "name16");
        // Appelle une méthode
        Objects.requireNonNull(codec16, "codec16");
        // Appelle une méthode
        Objects.requireNonNull(getter16, "getter16");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Appelle une méthode
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Embranchement : vérifie une condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Renvoie une valeur à l'appelant
                    return result2.cast();
                // Appelle une méthode
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Embranchement : vérifie une condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Renvoie une valeur à l'appelant
                    return result3.cast();
                // Appelle une méthode
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Embranchement : vérifie une condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Renvoie une valeur à l'appelant
                    return result4.cast();
                // Appelle une méthode
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Embranchement : vérifie une condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Renvoie une valeur à l'appelant
                    return result5.cast();
                // Appelle une méthode
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Embranchement : vérifie une condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Renvoie une valeur à l'appelant
                    return result6.cast();
                // Appelle une méthode
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Embranchement : vérifie une condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Renvoie une valeur à l'appelant
                    return result7.cast();
                // Appelle une méthode
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Embranchement : vérifie une condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Renvoie une valeur à l'appelant
                    return result8.cast();
                // Appelle une méthode
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Embranchement : vérifie une condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Renvoie une valeur à l'appelant
                    return result9.cast();
                // Appelle une méthode
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Embranchement : vérifie une condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Renvoie une valeur à l'appelant
                    return result10.cast();
                // Appelle une méthode
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Embranchement : vérifie une condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Renvoie une valeur à l'appelant
                    return result11.cast();
                // Appelle une méthode
                final Result<P12> result12 = get(coder, codec12, name12, map);
                // Embranchement : vérifie une condition
                if (!(result12 instanceof Result.Ok(P12 value12)))
                    // Renvoie une valeur à l'appelant
                    return result12.cast();
                // Appelle une méthode
                final Result<P13> result13 = get(coder, codec13, name13, map);
                // Embranchement : vérifie une condition
                if (!(result13 instanceof Result.Ok(P13 value13)))
                    // Renvoie une valeur à l'appelant
                    return result13.cast();
                // Appelle une méthode
                final Result<P14> result14 = get(coder, codec14, name14, map);
                // Embranchement : vérifie une condition
                if (!(result14 instanceof Result.Ok(P14 value14)))
                    // Renvoie une valeur à l'appelant
                    return result14.cast();
                // Appelle une méthode
                final Result<P15> result15 = get(coder, codec15, name15, map);
                // Embranchement : vérifie une condition
                if (!(result15 instanceof Result.Ok(P15 value15)))
                    // Renvoie une valeur à l'appelant
                    return result15.cast();
                // Appelle une méthode
                final Result<P16> result16 = get(coder, codec16, name16, map);
                // Embranchement : vérifie une condition
                if (!(result16 instanceof Result.Ok(P16 value16)))
                    // Renvoie une valeur à l'appelant
                    return result16.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Appelle une méthode
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Embranchement : vérifie une condition
                if (result2 != null) return result2;
                // Appelle une méthode
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Embranchement : vérifie une condition
                if (result3 != null) return result3;
                // Appelle une méthode
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Embranchement : vérifie une condition
                if (result4 != null) return result4;
                // Appelle une méthode
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Embranchement : vérifie une condition
                if (result5 != null) return result5;
                // Appelle une méthode
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Embranchement : vérifie une condition
                if (result6 != null) return result6;
                // Appelle une méthode
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Embranchement : vérifie une condition
                if (result7 != null) return result7;
                // Appelle une méthode
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Embranchement : vérifie une condition
                if (result8 != null) return result8;
                // Appelle une méthode
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Embranchement : vérifie une condition
                if (result9 != null) return result9;
                // Appelle une méthode
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Embranchement : vérifie une condition
                if (result10 != null) return result10;
                // Appelle une méthode
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Embranchement : vérifie une condition
                if (result11 != null) return result11;
                // Appelle une méthode
                final Result<D> result12 = put(coder, codec12, map, name12, getter12.apply(value));
                // Embranchement : vérifie une condition
                if (result12 != null) return result12;
                // Appelle une méthode
                final Result<D> result13 = put(coder, codec13, map, name13, getter13.apply(value));
                // Embranchement : vérifie une condition
                if (result13 != null) return result13;
                // Appelle une méthode
                final Result<D> result14 = put(coder, codec14, map, name14, getter14.apply(value));
                // Embranchement : vérifie une condition
                if (result14 != null) return result14;
                // Appelle une méthode
                final Result<D> result15 = put(coder, codec15, map, name15, getter15.apply(value));
                // Embranchement : vérifie une condition
                if (result15 != null) return result15;
                // Appelle une méthode
                final Result<D> result16 = put(coder, codec16, map, name16, getter16.apply(value));
                // Embranchement : vérifie une condition
                if (result16 != null) return result16;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            String name2, Codec<P2> codec2, Function<R, P2> getter2,
            // Instruction de code
            String name3, Codec<P3> codec3, Function<R, P3> getter3,
            // Instruction de code
            String name4, Codec<P4> codec4, Function<R, P4> getter4,
            // Instruction de code
            String name5, Codec<P5> codec5, Function<R, P5> getter5,
            // Instruction de code
            String name6, Codec<P6> codec6, Function<R, P6> getter6,
            // Instruction de code
            String name7, Codec<P7> codec7, Function<R, P7> getter7,
            // Instruction de code
            String name8, Codec<P8> codec8, Function<R, P8> getter8,
            // Instruction de code
            String name9, Codec<P9> codec9, Function<R, P9> getter9,
            // Instruction de code
            String name10, Codec<P10> codec10, Function<R, P10> getter10,
            // Instruction de code
            String name11, Codec<P11> codec11, Function<R, P11> getter11,
            // Instruction de code
            String name12, Codec<P12> codec12, Function<R, P12> getter12,
            // Instruction de code
            String name13, Codec<P13> codec13, Function<R, P13> getter13,
            // Instruction de code
            String name14, Codec<P14> codec14, Function<R, P14> getter14,
            // Instruction de code
            String name15, Codec<P15> codec15, Function<R, P15> getter15,
            // Instruction de code
            String name16, Codec<P16> codec16, Function<R, P16> getter16,
            // Instruction de code
            String name17, Codec<P17> codec17, Function<R, P17> getter17,
            // Instruction de code
            F17<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(name2, "name2");
        // Appelle une méthode
        Objects.requireNonNull(codec2, "codec2");
        // Appelle une méthode
        Objects.requireNonNull(getter2, "getter2");
        // Appelle une méthode
        Objects.requireNonNull(name3, "name3");
        // Appelle une méthode
        Objects.requireNonNull(codec3, "codec3");
        // Appelle une méthode
        Objects.requireNonNull(getter3, "getter3");
        // Appelle une méthode
        Objects.requireNonNull(name4, "name4");
        // Appelle une méthode
        Objects.requireNonNull(codec4, "codec4");
        // Appelle une méthode
        Objects.requireNonNull(getter4, "getter4");
        // Appelle une méthode
        Objects.requireNonNull(name5, "name5");
        // Appelle une méthode
        Objects.requireNonNull(codec5, "codec5");
        // Appelle une méthode
        Objects.requireNonNull(getter5, "getter5");
        // Appelle une méthode
        Objects.requireNonNull(name6, "name6");
        // Appelle une méthode
        Objects.requireNonNull(codec6, "codec6");
        // Appelle une méthode
        Objects.requireNonNull(getter6, "getter6");
        // Appelle une méthode
        Objects.requireNonNull(name7, "name7");
        // Appelle une méthode
        Objects.requireNonNull(codec7, "codec7");
        // Appelle une méthode
        Objects.requireNonNull(getter7, "getter7");
        // Appelle une méthode
        Objects.requireNonNull(name8, "name8");
        // Appelle une méthode
        Objects.requireNonNull(codec8, "codec8");
        // Appelle une méthode
        Objects.requireNonNull(getter8, "getter8");
        // Appelle une méthode
        Objects.requireNonNull(name9, "name9");
        // Appelle une méthode
        Objects.requireNonNull(codec9, "codec9");
        // Appelle une méthode
        Objects.requireNonNull(getter9, "getter9");
        // Appelle une méthode
        Objects.requireNonNull(name10, "name10");
        // Appelle une méthode
        Objects.requireNonNull(codec10, "codec10");
        // Appelle une méthode
        Objects.requireNonNull(getter10, "getter10");
        // Appelle une méthode
        Objects.requireNonNull(name11, "name11");
        // Appelle une méthode
        Objects.requireNonNull(codec11, "codec11");
        // Appelle une méthode
        Objects.requireNonNull(getter11, "getter11");
        // Appelle une méthode
        Objects.requireNonNull(name12, "name12");
        // Appelle une méthode
        Objects.requireNonNull(codec12, "codec12");
        // Appelle une méthode
        Objects.requireNonNull(getter12, "getter12");
        // Appelle une méthode
        Objects.requireNonNull(name13, "name13");
        // Appelle une méthode
        Objects.requireNonNull(codec13, "codec13");
        // Appelle une méthode
        Objects.requireNonNull(getter13, "getter13");
        // Appelle une méthode
        Objects.requireNonNull(name14, "name14");
        // Appelle une méthode
        Objects.requireNonNull(codec14, "codec14");
        // Appelle une méthode
        Objects.requireNonNull(getter14, "getter14");
        // Appelle une méthode
        Objects.requireNonNull(name15, "name15");
        // Appelle une méthode
        Objects.requireNonNull(codec15, "codec15");
        // Appelle une méthode
        Objects.requireNonNull(getter15, "getter15");
        // Appelle une méthode
        Objects.requireNonNull(name16, "name16");
        // Appelle une méthode
        Objects.requireNonNull(codec16, "codec16");
        // Appelle une méthode
        Objects.requireNonNull(getter16, "getter16");
        // Appelle une méthode
        Objects.requireNonNull(name17, "name17");
        // Appelle une méthode
        Objects.requireNonNull(codec17, "codec17");
        // Appelle une méthode
        Objects.requireNonNull(getter17, "getter17");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Appelle une méthode
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Embranchement : vérifie une condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Renvoie une valeur à l'appelant
                    return result2.cast();
                // Appelle une méthode
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Embranchement : vérifie une condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Renvoie une valeur à l'appelant
                    return result3.cast();
                // Appelle une méthode
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Embranchement : vérifie une condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Renvoie une valeur à l'appelant
                    return result4.cast();
                // Appelle une méthode
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Embranchement : vérifie une condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Renvoie une valeur à l'appelant
                    return result5.cast();
                // Appelle une méthode
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Embranchement : vérifie une condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Renvoie une valeur à l'appelant
                    return result6.cast();
                // Appelle une méthode
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Embranchement : vérifie une condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Renvoie une valeur à l'appelant
                    return result7.cast();
                // Appelle une méthode
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Embranchement : vérifie une condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Renvoie une valeur à l'appelant
                    return result8.cast();
                // Appelle une méthode
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Embranchement : vérifie une condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Renvoie une valeur à l'appelant
                    return result9.cast();
                // Appelle une méthode
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Embranchement : vérifie une condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Renvoie une valeur à l'appelant
                    return result10.cast();
                // Appelle une méthode
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Embranchement : vérifie une condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Renvoie une valeur à l'appelant
                    return result11.cast();
                // Appelle une méthode
                final Result<P12> result12 = get(coder, codec12, name12, map);
                // Embranchement : vérifie une condition
                if (!(result12 instanceof Result.Ok(P12 value12)))
                    // Renvoie une valeur à l'appelant
                    return result12.cast();
                // Appelle une méthode
                final Result<P13> result13 = get(coder, codec13, name13, map);
                // Embranchement : vérifie une condition
                if (!(result13 instanceof Result.Ok(P13 value13)))
                    // Renvoie une valeur à l'appelant
                    return result13.cast();
                // Appelle une méthode
                final Result<P14> result14 = get(coder, codec14, name14, map);
                // Embranchement : vérifie une condition
                if (!(result14 instanceof Result.Ok(P14 value14)))
                    // Renvoie une valeur à l'appelant
                    return result14.cast();
                // Appelle une méthode
                final Result<P15> result15 = get(coder, codec15, name15, map);
                // Embranchement : vérifie une condition
                if (!(result15 instanceof Result.Ok(P15 value15)))
                    // Renvoie une valeur à l'appelant
                    return result15.cast();
                // Appelle une méthode
                final Result<P16> result16 = get(coder, codec16, name16, map);
                // Embranchement : vérifie une condition
                if (!(result16 instanceof Result.Ok(P16 value16)))
                    // Renvoie une valeur à l'appelant
                    return result16.cast();
                // Appelle une méthode
                final Result<P17> result17 = get(coder, codec17, name17, map);
                // Embranchement : vérifie une condition
                if (!(result17 instanceof Result.Ok(P17 value17)))
                    // Renvoie une valeur à l'appelant
                    return result17.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Appelle une méthode
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Embranchement : vérifie une condition
                if (result2 != null) return result2;
                // Appelle une méthode
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Embranchement : vérifie une condition
                if (result3 != null) return result3;
                // Appelle une méthode
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Embranchement : vérifie une condition
                if (result4 != null) return result4;
                // Appelle une méthode
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Embranchement : vérifie une condition
                if (result5 != null) return result5;
                // Appelle une méthode
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Embranchement : vérifie une condition
                if (result6 != null) return result6;
                // Appelle une méthode
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Embranchement : vérifie une condition
                if (result7 != null) return result7;
                // Appelle une méthode
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Embranchement : vérifie une condition
                if (result8 != null) return result8;
                // Appelle une méthode
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Embranchement : vérifie une condition
                if (result9 != null) return result9;
                // Appelle une méthode
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Embranchement : vérifie une condition
                if (result10 != null) return result10;
                // Appelle une méthode
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Embranchement : vérifie une condition
                if (result11 != null) return result11;
                // Appelle une méthode
                final Result<D> result12 = put(coder, codec12, map, name12, getter12.apply(value));
                // Embranchement : vérifie une condition
                if (result12 != null) return result12;
                // Appelle une méthode
                final Result<D> result13 = put(coder, codec13, map, name13, getter13.apply(value));
                // Embranchement : vérifie une condition
                if (result13 != null) return result13;
                // Appelle une méthode
                final Result<D> result14 = put(coder, codec14, map, name14, getter14.apply(value));
                // Embranchement : vérifie une condition
                if (result14 != null) return result14;
                // Appelle une méthode
                final Result<D> result15 = put(coder, codec15, map, name15, getter15.apply(value));
                // Embranchement : vérifie une condition
                if (result15 != null) return result15;
                // Appelle une méthode
                final Result<D> result16 = put(coder, codec16, map, name16, getter16.apply(value));
                // Embranchement : vérifie une condition
                if (result16 != null) return result16;
                // Appelle une méthode
                final Result<D> result17 = put(coder, codec17, map, name17, getter17.apply(value));
                // Embranchement : vérifie une condition
                if (result17 != null) return result17;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object, P18 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            String name2, Codec<P2> codec2, Function<R, P2> getter2,
            // Instruction de code
            String name3, Codec<P3> codec3, Function<R, P3> getter3,
            // Instruction de code
            String name4, Codec<P4> codec4, Function<R, P4> getter4,
            // Instruction de code
            String name5, Codec<P5> codec5, Function<R, P5> getter5,
            // Instruction de code
            String name6, Codec<P6> codec6, Function<R, P6> getter6,
            // Instruction de code
            String name7, Codec<P7> codec7, Function<R, P7> getter7,
            // Instruction de code
            String name8, Codec<P8> codec8, Function<R, P8> getter8,
            // Instruction de code
            String name9, Codec<P9> codec9, Function<R, P9> getter9,
            // Instruction de code
            String name10, Codec<P10> codec10, Function<R, P10> getter10,
            // Instruction de code
            String name11, Codec<P11> codec11, Function<R, P11> getter11,
            // Instruction de code
            String name12, Codec<P12> codec12, Function<R, P12> getter12,
            // Instruction de code
            String name13, Codec<P13> codec13, Function<R, P13> getter13,
            // Instruction de code
            String name14, Codec<P14> codec14, Function<R, P14> getter14,
            // Instruction de code
            String name15, Codec<P15> codec15, Function<R, P15> getter15,
            // Instruction de code
            String name16, Codec<P16> codec16, Function<R, P16> getter16,
            // Instruction de code
            String name17, Codec<P17> codec17, Function<R, P17> getter17,
            // Instruction de code
            String name18, Codec<P18> codec18, Function<R, P18> getter18,
            // Instruction de code
            F18<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(name2, "name2");
        // Appelle une méthode
        Objects.requireNonNull(codec2, "codec2");
        // Appelle une méthode
        Objects.requireNonNull(getter2, "getter2");
        // Appelle une méthode
        Objects.requireNonNull(name3, "name3");
        // Appelle une méthode
        Objects.requireNonNull(codec3, "codec3");
        // Appelle une méthode
        Objects.requireNonNull(getter3, "getter3");
        // Appelle une méthode
        Objects.requireNonNull(name4, "name4");
        // Appelle une méthode
        Objects.requireNonNull(codec4, "codec4");
        // Appelle une méthode
        Objects.requireNonNull(getter4, "getter4");
        // Appelle une méthode
        Objects.requireNonNull(name5, "name5");
        // Appelle une méthode
        Objects.requireNonNull(codec5, "codec5");
        // Appelle une méthode
        Objects.requireNonNull(getter5, "getter5");
        // Appelle une méthode
        Objects.requireNonNull(name6, "name6");
        // Appelle une méthode
        Objects.requireNonNull(codec6, "codec6");
        // Appelle une méthode
        Objects.requireNonNull(getter6, "getter6");
        // Appelle une méthode
        Objects.requireNonNull(name7, "name7");
        // Appelle une méthode
        Objects.requireNonNull(codec7, "codec7");
        // Appelle une méthode
        Objects.requireNonNull(getter7, "getter7");
        // Appelle une méthode
        Objects.requireNonNull(name8, "name8");
        // Appelle une méthode
        Objects.requireNonNull(codec8, "codec8");
        // Appelle une méthode
        Objects.requireNonNull(getter8, "getter8");
        // Appelle une méthode
        Objects.requireNonNull(name9, "name9");
        // Appelle une méthode
        Objects.requireNonNull(codec9, "codec9");
        // Appelle une méthode
        Objects.requireNonNull(getter9, "getter9");
        // Appelle une méthode
        Objects.requireNonNull(name10, "name10");
        // Appelle une méthode
        Objects.requireNonNull(codec10, "codec10");
        // Appelle une méthode
        Objects.requireNonNull(getter10, "getter10");
        // Appelle une méthode
        Objects.requireNonNull(name11, "name11");
        // Appelle une méthode
        Objects.requireNonNull(codec11, "codec11");
        // Appelle une méthode
        Objects.requireNonNull(getter11, "getter11");
        // Appelle une méthode
        Objects.requireNonNull(name12, "name12");
        // Appelle une méthode
        Objects.requireNonNull(codec12, "codec12");
        // Appelle une méthode
        Objects.requireNonNull(getter12, "getter12");
        // Appelle une méthode
        Objects.requireNonNull(name13, "name13");
        // Appelle une méthode
        Objects.requireNonNull(codec13, "codec13");
        // Appelle une méthode
        Objects.requireNonNull(getter13, "getter13");
        // Appelle une méthode
        Objects.requireNonNull(name14, "name14");
        // Appelle une méthode
        Objects.requireNonNull(codec14, "codec14");
        // Appelle une méthode
        Objects.requireNonNull(getter14, "getter14");
        // Appelle une méthode
        Objects.requireNonNull(name15, "name15");
        // Appelle une méthode
        Objects.requireNonNull(codec15, "codec15");
        // Appelle une méthode
        Objects.requireNonNull(getter15, "getter15");
        // Appelle une méthode
        Objects.requireNonNull(name16, "name16");
        // Appelle une méthode
        Objects.requireNonNull(codec16, "codec16");
        // Appelle une méthode
        Objects.requireNonNull(getter16, "getter16");
        // Appelle une méthode
        Objects.requireNonNull(name17, "name17");
        // Appelle une méthode
        Objects.requireNonNull(codec17, "codec17");
        // Appelle une méthode
        Objects.requireNonNull(getter17, "getter17");
        // Appelle une méthode
        Objects.requireNonNull(name18, "name18");
        // Appelle une méthode
        Objects.requireNonNull(codec18, "codec18");
        // Appelle une méthode
        Objects.requireNonNull(getter18, "getter18");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Appelle une méthode
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Embranchement : vérifie une condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Renvoie une valeur à l'appelant
                    return result2.cast();
                // Appelle une méthode
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Embranchement : vérifie une condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Renvoie une valeur à l'appelant
                    return result3.cast();
                // Appelle une méthode
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Embranchement : vérifie une condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Renvoie une valeur à l'appelant
                    return result4.cast();
                // Appelle une méthode
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Embranchement : vérifie une condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Renvoie une valeur à l'appelant
                    return result5.cast();
                // Appelle une méthode
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Embranchement : vérifie une condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Renvoie une valeur à l'appelant
                    return result6.cast();
                // Appelle une méthode
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Embranchement : vérifie une condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Renvoie une valeur à l'appelant
                    return result7.cast();
                // Appelle une méthode
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Embranchement : vérifie une condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Renvoie une valeur à l'appelant
                    return result8.cast();
                // Appelle une méthode
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Embranchement : vérifie une condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Renvoie une valeur à l'appelant
                    return result9.cast();
                // Appelle une méthode
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Embranchement : vérifie une condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Renvoie une valeur à l'appelant
                    return result10.cast();
                // Appelle une méthode
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Embranchement : vérifie une condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Renvoie une valeur à l'appelant
                    return result11.cast();
                // Appelle une méthode
                final Result<P12> result12 = get(coder, codec12, name12, map);
                // Embranchement : vérifie une condition
                if (!(result12 instanceof Result.Ok(P12 value12)))
                    // Renvoie une valeur à l'appelant
                    return result12.cast();
                // Appelle une méthode
                final Result<P13> result13 = get(coder, codec13, name13, map);
                // Embranchement : vérifie une condition
                if (!(result13 instanceof Result.Ok(P13 value13)))
                    // Renvoie une valeur à l'appelant
                    return result13.cast();
                // Appelle une méthode
                final Result<P14> result14 = get(coder, codec14, name14, map);
                // Embranchement : vérifie une condition
                if (!(result14 instanceof Result.Ok(P14 value14)))
                    // Renvoie une valeur à l'appelant
                    return result14.cast();
                // Appelle une méthode
                final Result<P15> result15 = get(coder, codec15, name15, map);
                // Embranchement : vérifie une condition
                if (!(result15 instanceof Result.Ok(P15 value15)))
                    // Renvoie une valeur à l'appelant
                    return result15.cast();
                // Appelle une méthode
                final Result<P16> result16 = get(coder, codec16, name16, map);
                // Embranchement : vérifie une condition
                if (!(result16 instanceof Result.Ok(P16 value16)))
                    // Renvoie une valeur à l'appelant
                    return result16.cast();
                // Appelle une méthode
                final Result<P17> result17 = get(coder, codec17, name17, map);
                // Embranchement : vérifie une condition
                if (!(result17 instanceof Result.Ok(P17 value17)))
                    // Renvoie une valeur à l'appelant
                    return result17.cast();
                // Appelle une méthode
                final Result<P18> result18 = get(coder, codec18, name18, map);
                // Embranchement : vérifie une condition
                if (!(result18 instanceof Result.Ok(P18 value18)))
                    // Renvoie une valeur à l'appelant
                    return result18.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Appelle une méthode
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Embranchement : vérifie une condition
                if (result2 != null) return result2;
                // Appelle une méthode
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Embranchement : vérifie une condition
                if (result3 != null) return result3;
                // Appelle une méthode
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Embranchement : vérifie une condition
                if (result4 != null) return result4;
                // Appelle une méthode
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Embranchement : vérifie une condition
                if (result5 != null) return result5;
                // Appelle une méthode
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Embranchement : vérifie une condition
                if (result6 != null) return result6;
                // Appelle une méthode
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Embranchement : vérifie une condition
                if (result7 != null) return result7;
                // Appelle une méthode
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Embranchement : vérifie une condition
                if (result8 != null) return result8;
                // Appelle une méthode
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Embranchement : vérifie une condition
                if (result9 != null) return result9;
                // Appelle une méthode
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Embranchement : vérifie une condition
                if (result10 != null) return result10;
                // Appelle une méthode
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Embranchement : vérifie une condition
                if (result11 != null) return result11;
                // Appelle une méthode
                final Result<D> result12 = put(coder, codec12, map, name12, getter12.apply(value));
                // Embranchement : vérifie une condition
                if (result12 != null) return result12;
                // Appelle une méthode
                final Result<D> result13 = put(coder, codec13, map, name13, getter13.apply(value));
                // Embranchement : vérifie une condition
                if (result13 != null) return result13;
                // Appelle une méthode
                final Result<D> result14 = put(coder, codec14, map, name14, getter14.apply(value));
                // Embranchement : vérifie une condition
                if (result14 != null) return result14;
                // Appelle une méthode
                final Result<D> result15 = put(coder, codec15, map, name15, getter15.apply(value));
                // Embranchement : vérifie une condition
                if (result15 != null) return result15;
                // Appelle une méthode
                final Result<D> result16 = put(coder, codec16, map, name16, getter16.apply(value));
                // Embranchement : vérifie une condition
                if (result16 != null) return result16;
                // Appelle une méthode
                final Result<D> result17 = put(coder, codec17, map, name17, getter17.apply(value));
                // Embranchement : vérifie une condition
                if (result17 != null) return result17;
                // Appelle une méthode
                final Result<D> result18 = put(coder, codec18, map, name18, getter18.apply(value));
                // Embranchement : vérifie une condition
                if (result18 != null) return result18;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object, P18 extends @UnknownNullability Object, P19 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            String name2, Codec<P2> codec2, Function<R, P2> getter2,
            // Instruction de code
            String name3, Codec<P3> codec3, Function<R, P3> getter3,
            // Instruction de code
            String name4, Codec<P4> codec4, Function<R, P4> getter4,
            // Instruction de code
            String name5, Codec<P5> codec5, Function<R, P5> getter5,
            // Instruction de code
            String name6, Codec<P6> codec6, Function<R, P6> getter6,
            // Instruction de code
            String name7, Codec<P7> codec7, Function<R, P7> getter7,
            // Instruction de code
            String name8, Codec<P8> codec8, Function<R, P8> getter8,
            // Instruction de code
            String name9, Codec<P9> codec9, Function<R, P9> getter9,
            // Instruction de code
            String name10, Codec<P10> codec10, Function<R, P10> getter10,
            // Instruction de code
            String name11, Codec<P11> codec11, Function<R, P11> getter11,
            // Instruction de code
            String name12, Codec<P12> codec12, Function<R, P12> getter12,
            // Instruction de code
            String name13, Codec<P13> codec13, Function<R, P13> getter13,
            // Instruction de code
            String name14, Codec<P14> codec14, Function<R, P14> getter14,
            // Instruction de code
            String name15, Codec<P15> codec15, Function<R, P15> getter15,
            // Instruction de code
            String name16, Codec<P16> codec16, Function<R, P16> getter16,
            // Instruction de code
            String name17, Codec<P17> codec17, Function<R, P17> getter17,
            // Instruction de code
            String name18, Codec<P18> codec18, Function<R, P18> getter18,
            // Instruction de code
            String name19, Codec<P19> codec19, Function<R, P19> getter19,
            // Instruction de code
            F19<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(name2, "name2");
        // Appelle une méthode
        Objects.requireNonNull(codec2, "codec2");
        // Appelle une méthode
        Objects.requireNonNull(getter2, "getter2");
        // Appelle une méthode
        Objects.requireNonNull(name3, "name3");
        // Appelle une méthode
        Objects.requireNonNull(codec3, "codec3");
        // Appelle une méthode
        Objects.requireNonNull(getter3, "getter3");
        // Appelle une méthode
        Objects.requireNonNull(name4, "name4");
        // Appelle une méthode
        Objects.requireNonNull(codec4, "codec4");
        // Appelle une méthode
        Objects.requireNonNull(getter4, "getter4");
        // Appelle une méthode
        Objects.requireNonNull(name5, "name5");
        // Appelle une méthode
        Objects.requireNonNull(codec5, "codec5");
        // Appelle une méthode
        Objects.requireNonNull(getter5, "getter5");
        // Appelle une méthode
        Objects.requireNonNull(name6, "name6");
        // Appelle une méthode
        Objects.requireNonNull(codec6, "codec6");
        // Appelle une méthode
        Objects.requireNonNull(getter6, "getter6");
        // Appelle une méthode
        Objects.requireNonNull(name7, "name7");
        // Appelle une méthode
        Objects.requireNonNull(codec7, "codec7");
        // Appelle une méthode
        Objects.requireNonNull(getter7, "getter7");
        // Appelle une méthode
        Objects.requireNonNull(name8, "name8");
        // Appelle une méthode
        Objects.requireNonNull(codec8, "codec8");
        // Appelle une méthode
        Objects.requireNonNull(getter8, "getter8");
        // Appelle une méthode
        Objects.requireNonNull(name9, "name9");
        // Appelle une méthode
        Objects.requireNonNull(codec9, "codec9");
        // Appelle une méthode
        Objects.requireNonNull(getter9, "getter9");
        // Appelle une méthode
        Objects.requireNonNull(name10, "name10");
        // Appelle une méthode
        Objects.requireNonNull(codec10, "codec10");
        // Appelle une méthode
        Objects.requireNonNull(getter10, "getter10");
        // Appelle une méthode
        Objects.requireNonNull(name11, "name11");
        // Appelle une méthode
        Objects.requireNonNull(codec11, "codec11");
        // Appelle une méthode
        Objects.requireNonNull(getter11, "getter11");
        // Appelle une méthode
        Objects.requireNonNull(name12, "name12");
        // Appelle une méthode
        Objects.requireNonNull(codec12, "codec12");
        // Appelle une méthode
        Objects.requireNonNull(getter12, "getter12");
        // Appelle une méthode
        Objects.requireNonNull(name13, "name13");
        // Appelle une méthode
        Objects.requireNonNull(codec13, "codec13");
        // Appelle une méthode
        Objects.requireNonNull(getter13, "getter13");
        // Appelle une méthode
        Objects.requireNonNull(name14, "name14");
        // Appelle une méthode
        Objects.requireNonNull(codec14, "codec14");
        // Appelle une méthode
        Objects.requireNonNull(getter14, "getter14");
        // Appelle une méthode
        Objects.requireNonNull(name15, "name15");
        // Appelle une méthode
        Objects.requireNonNull(codec15, "codec15");
        // Appelle une méthode
        Objects.requireNonNull(getter15, "getter15");
        // Appelle une méthode
        Objects.requireNonNull(name16, "name16");
        // Appelle une méthode
        Objects.requireNonNull(codec16, "codec16");
        // Appelle une méthode
        Objects.requireNonNull(getter16, "getter16");
        // Appelle une méthode
        Objects.requireNonNull(name17, "name17");
        // Appelle une méthode
        Objects.requireNonNull(codec17, "codec17");
        // Appelle une méthode
        Objects.requireNonNull(getter17, "getter17");
        // Appelle une méthode
        Objects.requireNonNull(name18, "name18");
        // Appelle une méthode
        Objects.requireNonNull(codec18, "codec18");
        // Appelle une méthode
        Objects.requireNonNull(getter18, "getter18");
        // Appelle une méthode
        Objects.requireNonNull(name19, "name19");
        // Appelle une méthode
        Objects.requireNonNull(codec19, "codec19");
        // Appelle une méthode
        Objects.requireNonNull(getter19, "getter19");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Appelle une méthode
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Embranchement : vérifie une condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Renvoie une valeur à l'appelant
                    return result2.cast();
                // Appelle une méthode
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Embranchement : vérifie une condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Renvoie une valeur à l'appelant
                    return result3.cast();
                // Appelle une méthode
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Embranchement : vérifie une condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Renvoie une valeur à l'appelant
                    return result4.cast();
                // Appelle une méthode
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Embranchement : vérifie une condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Renvoie une valeur à l'appelant
                    return result5.cast();
                // Appelle une méthode
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Embranchement : vérifie une condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Renvoie une valeur à l'appelant
                    return result6.cast();
                // Appelle une méthode
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Embranchement : vérifie une condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Renvoie une valeur à l'appelant
                    return result7.cast();
                // Appelle une méthode
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Embranchement : vérifie une condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Renvoie une valeur à l'appelant
                    return result8.cast();
                // Appelle une méthode
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Embranchement : vérifie une condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Renvoie une valeur à l'appelant
                    return result9.cast();
                // Appelle une méthode
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Embranchement : vérifie une condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Renvoie une valeur à l'appelant
                    return result10.cast();
                // Appelle une méthode
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Embranchement : vérifie une condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Renvoie une valeur à l'appelant
                    return result11.cast();
                // Appelle une méthode
                final Result<P12> result12 = get(coder, codec12, name12, map);
                // Embranchement : vérifie une condition
                if (!(result12 instanceof Result.Ok(P12 value12)))
                    // Renvoie une valeur à l'appelant
                    return result12.cast();
                // Appelle une méthode
                final Result<P13> result13 = get(coder, codec13, name13, map);
                // Embranchement : vérifie une condition
                if (!(result13 instanceof Result.Ok(P13 value13)))
                    // Renvoie une valeur à l'appelant
                    return result13.cast();
                // Appelle une méthode
                final Result<P14> result14 = get(coder, codec14, name14, map);
                // Embranchement : vérifie une condition
                if (!(result14 instanceof Result.Ok(P14 value14)))
                    // Renvoie une valeur à l'appelant
                    return result14.cast();
                // Appelle une méthode
                final Result<P15> result15 = get(coder, codec15, name15, map);
                // Embranchement : vérifie une condition
                if (!(result15 instanceof Result.Ok(P15 value15)))
                    // Renvoie une valeur à l'appelant
                    return result15.cast();
                // Appelle une méthode
                final Result<P16> result16 = get(coder, codec16, name16, map);
                // Embranchement : vérifie une condition
                if (!(result16 instanceof Result.Ok(P16 value16)))
                    // Renvoie une valeur à l'appelant
                    return result16.cast();
                // Appelle une méthode
                final Result<P17> result17 = get(coder, codec17, name17, map);
                // Embranchement : vérifie une condition
                if (!(result17 instanceof Result.Ok(P17 value17)))
                    // Renvoie une valeur à l'appelant
                    return result17.cast();
                // Appelle une méthode
                final Result<P18> result18 = get(coder, codec18, name18, map);
                // Embranchement : vérifie une condition
                if (!(result18 instanceof Result.Ok(P18 value18)))
                    // Renvoie une valeur à l'appelant
                    return result18.cast();
                // Appelle une méthode
                final Result<P19> result19 = get(coder, codec19, name19, map);
                // Embranchement : vérifie une condition
                if (!(result19 instanceof Result.Ok(P19 value19)))
                    // Renvoie une valeur à l'appelant
                    return result19.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Appelle une méthode
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Embranchement : vérifie une condition
                if (result2 != null) return result2;
                // Appelle une méthode
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Embranchement : vérifie une condition
                if (result3 != null) return result3;
                // Appelle une méthode
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Embranchement : vérifie une condition
                if (result4 != null) return result4;
                // Appelle une méthode
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Embranchement : vérifie une condition
                if (result5 != null) return result5;
                // Appelle une méthode
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Embranchement : vérifie une condition
                if (result6 != null) return result6;
                // Appelle une méthode
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Embranchement : vérifie une condition
                if (result7 != null) return result7;
                // Appelle une méthode
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Embranchement : vérifie une condition
                if (result8 != null) return result8;
                // Appelle une méthode
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Embranchement : vérifie une condition
                if (result9 != null) return result9;
                // Appelle une méthode
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Embranchement : vérifie une condition
                if (result10 != null) return result10;
                // Appelle une méthode
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Embranchement : vérifie une condition
                if (result11 != null) return result11;
                // Appelle une méthode
                final Result<D> result12 = put(coder, codec12, map, name12, getter12.apply(value));
                // Embranchement : vérifie une condition
                if (result12 != null) return result12;
                // Appelle une méthode
                final Result<D> result13 = put(coder, codec13, map, name13, getter13.apply(value));
                // Embranchement : vérifie une condition
                if (result13 != null) return result13;
                // Appelle une méthode
                final Result<D> result14 = put(coder, codec14, map, name14, getter14.apply(value));
                // Embranchement : vérifie une condition
                if (result14 != null) return result14;
                // Appelle une méthode
                final Result<D> result15 = put(coder, codec15, map, name15, getter15.apply(value));
                // Embranchement : vérifie une condition
                if (result15 != null) return result15;
                // Appelle une méthode
                final Result<D> result16 = put(coder, codec16, map, name16, getter16.apply(value));
                // Embranchement : vérifie une condition
                if (result16 != null) return result16;
                // Appelle une méthode
                final Result<D> result17 = put(coder, codec17, map, name17, getter17.apply(value));
                // Embranchement : vérifie une condition
                if (result17 != null) return result17;
                // Appelle une méthode
                final Result<D> result18 = put(coder, codec18, map, name18, getter18.apply(value));
                // Embranchement : vérifie une condition
                if (result18 != null) return result18;
                // Appelle une méthode
                final Result<D> result19 = put(coder, codec19, map, name19, getter19.apply(value));
                // Embranchement : vérifie une condition
                if (result19 != null) return result19;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static <R, P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object, P18 extends @UnknownNullability Object, P19 extends @UnknownNullability Object, P20 extends @UnknownNullability Object> StructCodec<R> struct(
            // Instruction de code
            String name1, Codec<P1> codec1, Function<R, P1> getter1,
            // Instruction de code
            String name2, Codec<P2> codec2, Function<R, P2> getter2,
            // Instruction de code
            String name3, Codec<P3> codec3, Function<R, P3> getter3,
            // Instruction de code
            String name4, Codec<P4> codec4, Function<R, P4> getter4,
            // Instruction de code
            String name5, Codec<P5> codec5, Function<R, P5> getter5,
            // Instruction de code
            String name6, Codec<P6> codec6, Function<R, P6> getter6,
            // Instruction de code
            String name7, Codec<P7> codec7, Function<R, P7> getter7,
            // Instruction de code
            String name8, Codec<P8> codec8, Function<R, P8> getter8,
            // Instruction de code
            String name9, Codec<P9> codec9, Function<R, P9> getter9,
            // Instruction de code
            String name10, Codec<P10> codec10, Function<R, P10> getter10,
            // Instruction de code
            String name11, Codec<P11> codec11, Function<R, P11> getter11,
            // Instruction de code
            String name12, Codec<P12> codec12, Function<R, P12> getter12,
            // Instruction de code
            String name13, Codec<P13> codec13, Function<R, P13> getter13,
            // Instruction de code
            String name14, Codec<P14> codec14, Function<R, P14> getter14,
            // Instruction de code
            String name15, Codec<P15> codec15, Function<R, P15> getter15,
            // Instruction de code
            String name16, Codec<P16> codec16, Function<R, P16> getter16,
            // Instruction de code
            String name17, Codec<P17> codec17, Function<R, P17> getter17,
            // Instruction de code
            String name18, Codec<P18> codec18, Function<R, P18> getter18,
            // Instruction de code
            String name19, Codec<P19> codec19, Function<R, P19> getter19,
            // Instruction de code
            String name20, Codec<P20> codec20, Function<R, P20> getter20,
            // Instruction de code
            F20<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, P20, R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        Objects.requireNonNull(name1, "name1");
        // Appelle une méthode
        Objects.requireNonNull(codec1, "codec1");
        // Appelle une méthode
        Objects.requireNonNull(getter1, "getter1");
        // Appelle une méthode
        Objects.requireNonNull(name2, "name2");
        // Appelle une méthode
        Objects.requireNonNull(codec2, "codec2");
        // Appelle une méthode
        Objects.requireNonNull(getter2, "getter2");
        // Appelle une méthode
        Objects.requireNonNull(name3, "name3");
        // Appelle une méthode
        Objects.requireNonNull(codec3, "codec3");
        // Appelle une méthode
        Objects.requireNonNull(getter3, "getter3");
        // Appelle une méthode
        Objects.requireNonNull(name4, "name4");
        // Appelle une méthode
        Objects.requireNonNull(codec4, "codec4");
        // Appelle une méthode
        Objects.requireNonNull(getter4, "getter4");
        // Appelle une méthode
        Objects.requireNonNull(name5, "name5");
        // Appelle une méthode
        Objects.requireNonNull(codec5, "codec5");
        // Appelle une méthode
        Objects.requireNonNull(getter5, "getter5");
        // Appelle une méthode
        Objects.requireNonNull(name6, "name6");
        // Appelle une méthode
        Objects.requireNonNull(codec6, "codec6");
        // Appelle une méthode
        Objects.requireNonNull(getter6, "getter6");
        // Appelle une méthode
        Objects.requireNonNull(name7, "name7");
        // Appelle une méthode
        Objects.requireNonNull(codec7, "codec7");
        // Appelle une méthode
        Objects.requireNonNull(getter7, "getter7");
        // Appelle une méthode
        Objects.requireNonNull(name8, "name8");
        // Appelle une méthode
        Objects.requireNonNull(codec8, "codec8");
        // Appelle une méthode
        Objects.requireNonNull(getter8, "getter8");
        // Appelle une méthode
        Objects.requireNonNull(name9, "name9");
        // Appelle une méthode
        Objects.requireNonNull(codec9, "codec9");
        // Appelle une méthode
        Objects.requireNonNull(getter9, "getter9");
        // Appelle une méthode
        Objects.requireNonNull(name10, "name10");
        // Appelle une méthode
        Objects.requireNonNull(codec10, "codec10");
        // Appelle une méthode
        Objects.requireNonNull(getter10, "getter10");
        // Appelle une méthode
        Objects.requireNonNull(name11, "name11");
        // Appelle une méthode
        Objects.requireNonNull(codec11, "codec11");
        // Appelle une méthode
        Objects.requireNonNull(getter11, "getter11");
        // Appelle une méthode
        Objects.requireNonNull(name12, "name12");
        // Appelle une méthode
        Objects.requireNonNull(codec12, "codec12");
        // Appelle une méthode
        Objects.requireNonNull(getter12, "getter12");
        // Appelle une méthode
        Objects.requireNonNull(name13, "name13");
        // Appelle une méthode
        Objects.requireNonNull(codec13, "codec13");
        // Appelle une méthode
        Objects.requireNonNull(getter13, "getter13");
        // Appelle une méthode
        Objects.requireNonNull(name14, "name14");
        // Appelle une méthode
        Objects.requireNonNull(codec14, "codec14");
        // Appelle une méthode
        Objects.requireNonNull(getter14, "getter14");
        // Appelle une méthode
        Objects.requireNonNull(name15, "name15");
        // Appelle une méthode
        Objects.requireNonNull(codec15, "codec15");
        // Appelle une méthode
        Objects.requireNonNull(getter15, "getter15");
        // Appelle une méthode
        Objects.requireNonNull(name16, "name16");
        // Appelle une méthode
        Objects.requireNonNull(codec16, "codec16");
        // Appelle une méthode
        Objects.requireNonNull(getter16, "getter16");
        // Appelle une méthode
        Objects.requireNonNull(name17, "name17");
        // Appelle une méthode
        Objects.requireNonNull(codec17, "codec17");
        // Appelle une méthode
        Objects.requireNonNull(getter17, "getter17");
        // Appelle une méthode
        Objects.requireNonNull(name18, "name18");
        // Appelle une méthode
        Objects.requireNonNull(codec18, "codec18");
        // Appelle une méthode
        Objects.requireNonNull(getter18, "getter18");
        // Appelle une méthode
        Objects.requireNonNull(name19, "name19");
        // Appelle une méthode
        Objects.requireNonNull(codec19, "codec19");
        // Appelle une méthode
        Objects.requireNonNull(getter19, "getter19");
        // Appelle une méthode
        Objects.requireNonNull(name20, "name20");
        // Appelle une méthode
        Objects.requireNonNull(codec20, "codec20");
        // Appelle une méthode
        Objects.requireNonNull(getter20, "getter20");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new StructCodec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
                // Appelle une méthode
                final Result<P1> result1 = get(coder, codec1, name1, map);
                // Embranchement : vérifie une condition
                if (!(result1 instanceof Result.Ok(P1 value1)))
                    // Renvoie une valeur à l'appelant
                    return result1.cast();
                // Appelle une méthode
                final Result<P2> result2 = get(coder, codec2, name2, map);
                // Embranchement : vérifie une condition
                if (!(result2 instanceof Result.Ok(P2 value2)))
                    // Renvoie une valeur à l'appelant
                    return result2.cast();
                // Appelle une méthode
                final Result<P3> result3 = get(coder, codec3, name3, map);
                // Embranchement : vérifie une condition
                if (!(result3 instanceof Result.Ok(P3 value3)))
                    // Renvoie une valeur à l'appelant
                    return result3.cast();
                // Appelle une méthode
                final Result<P4> result4 = get(coder, codec4, name4, map);
                // Embranchement : vérifie une condition
                if (!(result4 instanceof Result.Ok(P4 value4)))
                    // Renvoie une valeur à l'appelant
                    return result4.cast();
                // Appelle une méthode
                final Result<P5> result5 = get(coder, codec5, name5, map);
                // Embranchement : vérifie une condition
                if (!(result5 instanceof Result.Ok(P5 value5)))
                    // Renvoie une valeur à l'appelant
                    return result5.cast();
                // Appelle une méthode
                final Result<P6> result6 = get(coder, codec6, name6, map);
                // Embranchement : vérifie une condition
                if (!(result6 instanceof Result.Ok(P6 value6)))
                    // Renvoie une valeur à l'appelant
                    return result6.cast();
                // Appelle une méthode
                final Result<P7> result7 = get(coder, codec7, name7, map);
                // Embranchement : vérifie une condition
                if (!(result7 instanceof Result.Ok(P7 value7)))
                    // Renvoie une valeur à l'appelant
                    return result7.cast();
                // Appelle une méthode
                final Result<P8> result8 = get(coder, codec8, name8, map);
                // Embranchement : vérifie une condition
                if (!(result8 instanceof Result.Ok(P8 value8)))
                    // Renvoie une valeur à l'appelant
                    return result8.cast();
                // Appelle une méthode
                final Result<P9> result9 = get(coder, codec9, name9, map);
                // Embranchement : vérifie une condition
                if (!(result9 instanceof Result.Ok(P9 value9)))
                    // Renvoie une valeur à l'appelant
                    return result9.cast();
                // Appelle une méthode
                final Result<P10> result10 = get(coder, codec10, name10, map);
                // Embranchement : vérifie une condition
                if (!(result10 instanceof Result.Ok(P10 value10)))
                    // Renvoie une valeur à l'appelant
                    return result10.cast();
                // Appelle une méthode
                final Result<P11> result11 = get(coder, codec11, name11, map);
                // Embranchement : vérifie une condition
                if (!(result11 instanceof Result.Ok(P11 value11)))
                    // Renvoie une valeur à l'appelant
                    return result11.cast();
                // Appelle une méthode
                final Result<P12> result12 = get(coder, codec12, name12, map);
                // Embranchement : vérifie une condition
                if (!(result12 instanceof Result.Ok(P12 value12)))
                    // Renvoie une valeur à l'appelant
                    return result12.cast();
                // Appelle une méthode
                final Result<P13> result13 = get(coder, codec13, name13, map);
                // Embranchement : vérifie une condition
                if (!(result13 instanceof Result.Ok(P13 value13)))
                    // Renvoie une valeur à l'appelant
                    return result13.cast();
                // Appelle une méthode
                final Result<P14> result14 = get(coder, codec14, name14, map);
                // Embranchement : vérifie une condition
                if (!(result14 instanceof Result.Ok(P14 value14)))
                    // Renvoie une valeur à l'appelant
                    return result14.cast();
                // Appelle une méthode
                final Result<P15> result15 = get(coder, codec15, name15, map);
                // Embranchement : vérifie une condition
                if (!(result15 instanceof Result.Ok(P15 value15)))
                    // Renvoie une valeur à l'appelant
                    return result15.cast();
                // Appelle une méthode
                final Result<P16> result16 = get(coder, codec16, name16, map);
                // Embranchement : vérifie une condition
                if (!(result16 instanceof Result.Ok(P16 value16)))
                    // Renvoie une valeur à l'appelant
                    return result16.cast();
                // Appelle une méthode
                final Result<P17> result17 = get(coder, codec17, name17, map);
                // Embranchement : vérifie une condition
                if (!(result17 instanceof Result.Ok(P17 value17)))
                    // Renvoie une valeur à l'appelant
                    return result17.cast();
                // Appelle une méthode
                final Result<P18> result18 = get(coder, codec18, name18, map);
                // Embranchement : vérifie une condition
                if (!(result18 instanceof Result.Ok(P18 value18)))
                    // Renvoie une valeur à l'appelant
                    return result18.cast();
                // Appelle une méthode
                final Result<P19> result19 = get(coder, codec19, name19, map);
                // Embranchement : vérifie une condition
                if (!(result19 instanceof Result.Ok(P19 value19)))
                    // Renvoie une valeur à l'appelant
                    return result19.cast();
                // Appelle une méthode
                final Result<P20> result20 = get(coder, codec20, name20, map);
                // Embranchement : vérifie une condition
                if (!(result20 instanceof Result.Ok(P20 value20)))
                    // Renvoie une valeur à l'appelant
                    return result20.cast();
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(ctor.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
                // Appelle une méthode
                final Result<D> result1 = put(coder, codec1, map, name1, getter1.apply(value));
                // Embranchement : vérifie une condition
                if (result1 != null) return result1;
                // Appelle une méthode
                final Result<D> result2 = put(coder, codec2, map, name2, getter2.apply(value));
                // Embranchement : vérifie une condition
                if (result2 != null) return result2;
                // Appelle une méthode
                final Result<D> result3 = put(coder, codec3, map, name3, getter3.apply(value));
                // Embranchement : vérifie une condition
                if (result3 != null) return result3;
                // Appelle une méthode
                final Result<D> result4 = put(coder, codec4, map, name4, getter4.apply(value));
                // Embranchement : vérifie une condition
                if (result4 != null) return result4;
                // Appelle une méthode
                final Result<D> result5 = put(coder, codec5, map, name5, getter5.apply(value));
                // Embranchement : vérifie une condition
                if (result5 != null) return result5;
                // Appelle une méthode
                final Result<D> result6 = put(coder, codec6, map, name6, getter6.apply(value));
                // Embranchement : vérifie une condition
                if (result6 != null) return result6;
                // Appelle une méthode
                final Result<D> result7 = put(coder, codec7, map, name7, getter7.apply(value));
                // Embranchement : vérifie une condition
                if (result7 != null) return result7;
                // Appelle une méthode
                final Result<D> result8 = put(coder, codec8, map, name8, getter8.apply(value));
                // Embranchement : vérifie une condition
                if (result8 != null) return result8;
                // Appelle une méthode
                final Result<D> result9 = put(coder, codec9, map, name9, getter9.apply(value));
                // Embranchement : vérifie une condition
                if (result9 != null) return result9;
                // Appelle une méthode
                final Result<D> result10 = put(coder, codec10, map, name10, getter10.apply(value));
                // Embranchement : vérifie une condition
                if (result10 != null) return result10;
                // Appelle une méthode
                final Result<D> result11 = put(coder, codec11, map, name11, getter11.apply(value));
                // Embranchement : vérifie une condition
                if (result11 != null) return result11;
                // Appelle une méthode
                final Result<D> result12 = put(coder, codec12, map, name12, getter12.apply(value));
                // Embranchement : vérifie une condition
                if (result12 != null) return result12;
                // Appelle une méthode
                final Result<D> result13 = put(coder, codec13, map, name13, getter13.apply(value));
                // Embranchement : vérifie une condition
                if (result13 != null) return result13;
                // Appelle une méthode
                final Result<D> result14 = put(coder, codec14, map, name14, getter14.apply(value));
                // Embranchement : vérifie une condition
                if (result14 != null) return result14;
                // Appelle une méthode
                final Result<D> result15 = put(coder, codec15, map, name15, getter15.apply(value));
                // Embranchement : vérifie une condition
                if (result15 != null) return result15;
                // Appelle une méthode
                final Result<D> result16 = put(coder, codec16, map, name16, getter16.apply(value));
                // Embranchement : vérifie une condition
                if (result16 != null) return result16;
                // Appelle une méthode
                final Result<D> result17 = put(coder, codec17, map, name17, getter17.apply(value));
                // Embranchement : vérifie une condition
                if (result17 != null) return result17;
                // Appelle une méthode
                final Result<D> result18 = put(coder, codec18, map, name18, getter18.apply(value));
                // Embranchement : vérifie une condition
                if (result18 != null) return result18;
                // Appelle une méthode
                final Result<D> result19 = put(coder, codec19, map, name19, getter19.apply(value));
                // Embranchement : vérifie une condition
                if (result19 != null) return result19;
                // Appelle une méthode
                final Result<D> result20 = put(coder, codec20, map, name20, getter20.apply(value));
                // Embranchement : vérifie une condition
                if (result20 != null) return result20;
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static <D, T> Result<@UnknownNullability T> get(Transcoder<D> coder, Codec<T> codec, String key, MapLike<D> map) {
        // Embranchement : vérifie une condition
        if (INLINE.equals(key)) {
            // Affecte une valeur
            final Codec<T> decodeCodec = codec instanceof CodecImpl.OptionalImpl<T>(
                    // Instruction de code
                    Codec<T> inner, T ignored
            // Instruction de code
            ) ? inner : codec;
            // Embranchement : vérifie une condition
            if (!(decodeCodec instanceof StructCodec<T> s)) return new Result.Error<>(key + ": not a struct");

            // Appelle une méthode
            final Result<T> decodeResult = s.decodeFromMap(coder, map);
            // Embranchement : vérifie une condition
            if (decodeResult instanceof Result.Error<T> && codec instanceof CodecImpl.OptionalImpl<T>(
                    // Instruction de code
                    Codec<T> ignored, T defaultValue
            // Instruction de code
            )) return new Result.Ok<>(defaultValue);

            // Renvoie une valeur à l'appelant
            return decodeResult.mapError(e -> key + ": " + e);
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (codec instanceof CodecImpl.OptionalImpl<T>(Codec<T> inner, T defaultValue)) {
            // Renvoie une valeur à l'appelant
            return switch (map.getValue(key)) {
                // Embranchement multiple (switch/case)
                case Result.Ok(D innerValue) -> inner.decode(coder, innerValue)
                        // Appelle une méthode
                        .mapError(e -> key + ": " + e);
                // Embranchement multiple (switch/case)
                case Result.Error(String ignored) -> new Result.Ok<>(defaultValue);
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return map.getValue(key)
                // Instruction de code
                .map(innerValue -> codec.decode(coder, innerValue))
                // Appelle une méthode
                .mapError(e -> key + ": " + e);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static <D, T> @Nullable Result<D> put(Transcoder<D> coder, Codec<T> codec, MapBuilder<D> map, String key, @Nullable T value) {
        // Embranchement : vérifie une condition
        if (value == null) {
            // Embranchement : vérifie une condition
            if (!(codec instanceof CodecImpl.OptionalImpl<T>))
                // Renvoie une valeur à l'appelant
                return new Result.Error<>(key + ": null");
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (INLINE.equals(key)) {
            // Affecte une valeur
            final Codec<T> encodeCodec = codec instanceof CodecImpl.OptionalImpl<T>(
                    // Instruction de code
                    Codec<T> inner, T ignored
            // Instruction de code
            ) ? inner : codec;
            // Embranchement : vérifie une condition
            if (!(encodeCodec instanceof StructCodec<T> s))
                // Renvoie une valeur à l'appelant
                return new Result.Error<>(key + ": not a struct");
            // Appelle une méthode
            final Result<D> mapEncodeResult = s.encodeToMap(coder, value, map);
            // Embranchement : vérifie une condition
            if (mapEncodeResult instanceof Result.Error<?> e)
                // Renvoie une valeur à l'appelant
                return new Result.Error<>(key + ": " + e);
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return switch (codec.encode(coder, value)) {
            // Embranchement multiple (switch/case)
            case Result.Ok(D ok) -> {
                // Embranchement : vérifie une condition
                if (ok != null) map.put(key, ok);
                // Instruction de code
                yield null;
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case Result.Error(String message) -> new Result.Error<>(key + ": " + message);
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}

