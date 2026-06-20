// Déclaration du paquet de ce fichier
package net.minestom.server.codec;

// Import d'une classe nécessaire
import com.google.gson.JsonElement;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Unmodifiable;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;

/**
 * Transcoders are responsible for converting "primitive" java objects into their respective {@link D} types.
 * They are also responsible for unwrapping these objects back to their primitives.
 * <br>
 * Commonly used transcoders are accessible through static fields like {@link Transcoder#JSON}
 * @param <D> the intermediary type used by the transcoder
 */
// Déclaration de type (classe/interface/enum/record)
public interface Transcoder<D> {

    // Affecte une valeur
    Transcoder<BinaryTag> NBT = TranscoderNbtImpl.INSTANCE;
    // Affecte une valeur
    Transcoder<JsonElement> JSON = TranscoderJsonImpl.INSTANCE;
    // Affecte une valeur
    Transcoder<Object> JAVA = TranscoderJavaImpl.INSTANCE;
    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Affecte une valeur
    Transcoder<Integer> CRC32_HASH = TranscoderCrc32Impl.INSTANCE;

    /**
     * Creates a null representation of {@link D}
     * @return the null object, never {@code null}
     */
    // Appelle une méthode
    D createNull();

    /**
     * Attempts to unwrap a boolean from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Appelle une méthode
    Result<Boolean> getBoolean(D value);

    /**
     * Creates a boolean representation of {@link D}
     * @param value the boolean primitive
     * @return the representation of value in {@link D}
     */
    // Appelle une méthode
    D createBoolean(boolean value);

    /**
     * Attempts to unwrap a byte from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Appelle une méthode
    Result<Byte> getByte(D value);

    /**
     * Creates a byte representation of {@link D}
     * @param value the byte primitive
     * @return the representation of value in {@link D}
     */
    // Appelle une méthode
    D createByte(byte value);

    /**
     * Attempts to unwrap a short from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Appelle une méthode
    Result<Short> getShort(D value);

    /**
     * Creates a short representation of {@link D}
     * @param value the short primitive
     * @return the representation of value in {@link D}
     */
    // Appelle une méthode
    D createShort(short value);

    /**
     * Attempts to unwrap an int from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Appelle une méthode
    Result<Integer> getInt(D value);

    /**
     * Creates an int representation of {@link D}
     * @param value the int primitive
     * @return the representation of value in {@link D}
     */
    // Appelle une méthode
    D createInt(int value);

    /**
     * Attempts to unwrap a long from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Appelle une méthode
    Result<Long> getLong(D value);

    /**
     * Creates a long representation of {@link D}
     * @param value the long primitive
     * @return the representation of value in {@link D}
     */
    // Appelle une méthode
    D createLong(long value);

    /**
     * Attempts to unwrap a float from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Appelle une méthode
    Result<Float> getFloat(D value);

    /**
     * Creates a float representation of {@link D}
     * @param value the float primitive
     * @return the representation of value in {@link D}
     */
    // Appelle une méthode
    D createFloat(float value);

    /**
     * Attempts to unwrap a double from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Appelle une méthode
    Result<Double> getDouble(D value);

    /**
     * Creates a float representation of {@link D}
     * @param value the float primitive
     * @return the representation of value in {@link D}
     */
    // Appelle une méthode
    D createDouble(double value);

    /**
     * Attempts to unwrap a string from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Appelle une méthode
    Result<String> getString(D value);

    /**
     * Creates a string representation of {@link D}
     * @param value the string primitive
     * @return the representation of value in {@link D}
     */
    // Appelle une méthode
    D createString(String value);

    /**
     * Attempts to unwrap a list from the value {@link D}
     * <br>
     * The {@link List} decoded possibly has more of {@link D} contained inside.
     * @param value the value to unwrap
     * @return the result
     */
    // Appelle une méthode
    Result<@Unmodifiable List<D>> getList(D value);

    /**
     * A empty list intermediary
     * @return the empty list intermediary
     */
    // Début d'une méthode/d'un bloc
    default D emptyList() {
        // Renvoie une valeur à l'appelant
        return createList(0).build();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@link ListBuilder}
     * @param expectedSize the initial size
     * @return a list builder
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    ListBuilder<D> createList(int expectedSize);

    /**
     * Attempts to unwrap a map from the value {@link D}
     * <br>
     * The {@link MapLike} decoded possibly has more of {@link D} contained inside.
     * @param value the value to unwrap
     * @return the result
     */
    // Appelle une méthode
    Result<MapLike<D>> getMap(D value);

    /**
     * A emtpy map intermediary
     * @return the empty map intermediary
     */
    // Début d'une méthode/d'un bloc
    default D emptyMap() {
        // Renvoie une valeur à l'appelant
        return createMap().build();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@link MapBuilder}
     * @return a new {@link MapBuilder}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    MapBuilder<D> createMap();

    /**
     * Attempts to unwrap a {@code byte[]} from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Début d'une méthode/d'un bloc
    default Result<byte[]> getByteArray(D value) {
        // Appelle une méthode
        final Result<List<D>> listResult = getList(value);
        // Embranchement : vérifie une condition
        if (!(listResult instanceof Result.Ok(List<D> list)))
            // Renvoie une valeur à l'appelant
            return listResult.cast();
        // Appelle une méthode
        final byte[] byteArray = new byte[list.size()];
        // Boucle : répète un bloc
        for (int i = 0; i < list.size(); i++) {
            // Appelle une méthode
            final Result<Byte> byteResult = getByte(list.get(i));
            // Embranchement : vérifie une condition
            if (!(byteResult instanceof Result.Ok(Byte byteValue)))
                // Renvoie une valeur à l'appelant
                return byteResult.cast();
            // Affecte une valeur
            byteArray[i] = byteValue;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(byteArray);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@code byte[]} representation of {@link D}
     * @param value the byte array
     * @return {@link D} representation of {@code byte[]}
     */
    // Début d'une méthode/d'un bloc
    default D createByteArray(byte[] value) {
        // Appelle une méthode
        final ListBuilder<D> list = createList(value.length);
        // Boucle : répète un bloc
        for (byte b : value) list.add(createByte(b));
        // Renvoie une valeur à l'appelant
        return list.build();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Attempts to unwrap a {@code int[]} from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Début d'une méthode/d'un bloc
    default Result<int[]> getIntArray(D value) {
        // Appelle une méthode
        final Result<List<D>> listResult = getList(value);
        // Embranchement : vérifie une condition
        if (!(listResult instanceof Result.Ok(List<D> list)))
            // Renvoie une valeur à l'appelant
            return listResult.cast();
        // Appelle une méthode
        final int[] intArray = new int[list.size()];
        // Boucle : répète un bloc
        for (int i = 0; i < list.size(); i++) {
            // Appelle une méthode
            final Result<Integer> intResult = getInt(list.get(i));
            // Embranchement : vérifie une condition
            if (!(intResult instanceof Result.Ok(Integer intValue)))
                // Renvoie une valeur à l'appelant
                return intResult.cast();
            // Affecte une valeur
            intArray[i] = intValue;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(intArray);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@code int[]} representation of {@link D}
     * @param value the int array
     * @return {@link D} representation of {@code int[]}
     */
    // Début d'une méthode/d'un bloc
    default D createIntArray(int[] value) {
        // Appelle une méthode
        final ListBuilder<D> list = createList(value.length);
        // Boucle : répète un bloc
        for (int i : value) list.add(createInt(i));
        // Renvoie une valeur à l'appelant
        return list.build();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Attempts to unwrap a {@code long[]} from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Début d'une méthode/d'un bloc
    default Result<long[]> getLongArray(D value) {
        // Appelle une méthode
        final Result<List<D>> listResult = getList(value);
        // Embranchement : vérifie une condition
        if (!(listResult instanceof Result.Ok(List<D> list)))
            // Renvoie une valeur à l'appelant
            return listResult.cast();
        // Appelle une méthode
        final long[] longArray = new long[list.size()];
        // Boucle : répète un bloc
        for (int i = 0; i < list.size(); i++) {
            // Appelle une méthode
            final Result<Long> longResult = getLong(list.get(i));
            // Embranchement : vérifie une condition
            if (!(longResult instanceof Result.Ok(Long longValue)))
                // Renvoie une valeur à l'appelant
                return longResult.cast();
            // Affecte une valeur
            longArray[i] = longValue;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(longArray);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@code long[]} representation of {@link D}
     * @param value the long array
     * @return {@link D} representation of {@code long[]}
     */
    // Début d'une méthode/d'un bloc
    default D createLongArray(long[] value) {
        // Appelle une méthode
        final ListBuilder<D> list = createList(value.length);
        // Boucle : répète un bloc
        for (long l : value) list.add(createLong(l));
        // Renvoie une valeur à l'appelant
        return list.build();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Converts the current intermediary of {@link D} into intermediary {@link O}
     * @param coder the transcoder to convert to
     * @param value the value to convert
     * @return the resultant of the conversion
     * @param <O> the intermediary type to convert to
     */
    // Appelle une méthode
    <O> Result<O> convertTo(Transcoder<O> coder, D value);

    /**
     * List builders are used to eventually build a list.
     * <br>
     * They are considered mutable containers, but provide builder semantics.
     * @param <D> the transcoder type
     */
    // Déclaration de type (classe/interface/enum/record)
    interface ListBuilder<D> {
        // Appelle une méthode
        ListBuilder<D> add(D value);

        // Appelle une méthode
        D build();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Represents an immutable {@link java.util.Map} like object.
     * @param <D> the transcoder type
     */
    // Déclaration de type (classe/interface/enum/record)
    interface MapLike<D> {

        /**
         * Gets all the keys
         * @return the collection of keys
         */
        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Annotation pour l'élément suivant
        @Unmodifiable Collection<String> keys();

        /**
         * Checks if the map has the value mapped to the key
         * @param key the key to check
         * @return true if present; false otherwise
         */
        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Appelle une méthode
        boolean hasValue(String key);

        /**
         * Gets the value of the key in a result.
         * <br>
         * Check if the key has a value using {@link #hasValue(String)}
         * @param key the key to use
         * @return the result, {@link Result.Error} if missing
         */
        // Appelle une méthode
        Result<D> getValue(String key);

        /**
         * @return the size of the map
         */
        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        default int size() {
            // Renvoie une valeur à l'appelant
            return keys().size();
        // Fin d'un bloc/d'une expression
        }

        /**
         * @return true if the size is zero
         */
        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        default boolean isEmpty() {
            // Renvoie une valeur à l'appelant
            return size() == 0;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Map builders are used to eventually build a map
     * <br>
     * They are considered mutable containers, but provide builder semantics.
     * @param <D> the type of object used by the transcoder
     */
    // Déclaration de type (classe/interface/enum/record)
    interface MapBuilder<D> {

        /**
         * Puts an entry onto the map
         * @param key the key
         * @param value the value
         * @return this
         */
        // Appelle une méthode
        MapBuilder<D> put(D key, D value);

        /**
         * Puts an entry onto the map
         * @param key the string key
         * @param value the value
         * @return this
         */
        // Appelle une méthode
        MapBuilder<D> put(String key, D value);

        /**
         * Build the map with the current values
         * @return the completed map of type {@link D}
         */
        // Appelle une méthode
        D build();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
