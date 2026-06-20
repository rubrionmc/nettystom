// Package declaration for this file
package net.minestom.server.codec;

// Import of a required class
import com.google.gson.JsonElement;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Unmodifiable;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;

/**
 * Transcoders are responsible for converting "primitive" java objects into their respective {@link D} types.
 * They are also responsible for unwrapping these objects back to their primitives.
 * <br>
 * Commonly used transcoders are accessible through static fields like {@link Transcoder#JSON}
 * @param <D> the intermediary type used by the transcoder
 */
// Type declaration (class/interface/enum/record)
public interface Transcoder<D> {

    // Assigns a value
    Transcoder<BinaryTag> NBT = TranscoderNbtImpl.INSTANCE;
    // Assigns a value
    Transcoder<JsonElement> JSON = TranscoderJsonImpl.INSTANCE;
    // Assigns a value
    Transcoder<Object> JAVA = TranscoderJavaImpl.INSTANCE;
    // Annotation for the following element
    @ApiStatus.Experimental
    // Assigns a value
    Transcoder<Integer> CRC32_HASH = TranscoderCrc32Impl.INSTANCE;

    /**
     * Creates a null representation of {@link D}
     * @return the null object, never {@code null}
     */
    // Calls a method
    D createNull();

    /**
     * Attempts to unwrap a boolean from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Calls a method
    Result<Boolean> getBoolean(D value);

    /**
     * Creates a boolean representation of {@link D}
     * @param value the boolean primitive
     * @return the representation of value in {@link D}
     */
    // Calls a method
    D createBoolean(boolean value);

    /**
     * Attempts to unwrap a byte from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Calls a method
    Result<Byte> getByte(D value);

    /**
     * Creates a byte representation of {@link D}
     * @param value the byte primitive
     * @return the representation of value in {@link D}
     */
    // Calls a method
    D createByte(byte value);

    /**
     * Attempts to unwrap a short from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Calls a method
    Result<Short> getShort(D value);

    /**
     * Creates a short representation of {@link D}
     * @param value the short primitive
     * @return the representation of value in {@link D}
     */
    // Calls a method
    D createShort(short value);

    /**
     * Attempts to unwrap an int from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Calls a method
    Result<Integer> getInt(D value);

    /**
     * Creates an int representation of {@link D}
     * @param value the int primitive
     * @return the representation of value in {@link D}
     */
    // Calls a method
    D createInt(int value);

    /**
     * Attempts to unwrap a long from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Calls a method
    Result<Long> getLong(D value);

    /**
     * Creates a long representation of {@link D}
     * @param value the long primitive
     * @return the representation of value in {@link D}
     */
    // Calls a method
    D createLong(long value);

    /**
     * Attempts to unwrap a float from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Calls a method
    Result<Float> getFloat(D value);

    /**
     * Creates a float representation of {@link D}
     * @param value the float primitive
     * @return the representation of value in {@link D}
     */
    // Calls a method
    D createFloat(float value);

    /**
     * Attempts to unwrap a double from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Calls a method
    Result<Double> getDouble(D value);

    /**
     * Creates a float representation of {@link D}
     * @param value the float primitive
     * @return the representation of value in {@link D}
     */
    // Calls a method
    D createDouble(double value);

    /**
     * Attempts to unwrap a string from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Calls a method
    Result<String> getString(D value);

    /**
     * Creates a string representation of {@link D}
     * @param value the string primitive
     * @return the representation of value in {@link D}
     */
    // Calls a method
    D createString(String value);

    /**
     * Attempts to unwrap a list from the value {@link D}
     * <br>
     * The {@link List} decoded possibly has more of {@link D} contained inside.
     * @param value the value to unwrap
     * @return the result
     */
    // Calls a method
    Result<@Unmodifiable List<D>> getList(D value);

    /**
     * A empty list intermediary
     * @return the empty list intermediary
     */
    // Start of a method/block
    default D emptyList() {
        // Returns a value to the caller
        return createList(0).build();
    // End of a block/expression
    }

    /**
     * Creates a {@link ListBuilder}
     * @param expectedSize the initial size
     * @return a list builder
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    ListBuilder<D> createList(int expectedSize);

    /**
     * Attempts to unwrap a map from the value {@link D}
     * <br>
     * The {@link MapLike} decoded possibly has more of {@link D} contained inside.
     * @param value the value to unwrap
     * @return the result
     */
    // Calls a method
    Result<MapLike<D>> getMap(D value);

    /**
     * A emtpy map intermediary
     * @return the empty map intermediary
     */
    // Start of a method/block
    default D emptyMap() {
        // Returns a value to the caller
        return createMap().build();
    // End of a block/expression
    }

    /**
     * Creates a {@link MapBuilder}
     * @return a new {@link MapBuilder}
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    MapBuilder<D> createMap();

    /**
     * Attempts to unwrap a {@code byte[]} from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Start of a method/block
    default Result<byte[]> getByteArray(D value) {
        // Calls a method
        final Result<List<D>> listResult = getList(value);
        // Branch: checks a condition
        if (!(listResult instanceof Result.Ok(List<D> list)))
            // Returns a value to the caller
            return listResult.cast();
        // Calls a method
        final byte[] byteArray = new byte[list.size()];
        // Loop: repeats a block
        for (int i = 0; i < list.size(); i++) {
            // Calls a method
            final Result<Byte> byteResult = getByte(list.get(i));
            // Branch: checks a condition
            if (!(byteResult instanceof Result.Ok(Byte byteValue)))
                // Returns a value to the caller
                return byteResult.cast();
            // Assigns a value
            byteArray[i] = byteValue;
        // End of a block/expression
        }
        // Returns a value to the caller
        return new Result.Ok<>(byteArray);
    // End of a block/expression
    }

    /**
     * Creates a {@code byte[]} representation of {@link D}
     * @param value the byte array
     * @return {@link D} representation of {@code byte[]}
     */
    // Start of a method/block
    default D createByteArray(byte[] value) {
        // Calls a method
        final ListBuilder<D> list = createList(value.length);
        // Loop: repeats a block
        for (byte b : value) list.add(createByte(b));
        // Returns a value to the caller
        return list.build();
    // End of a block/expression
    }

    /**
     * Attempts to unwrap a {@code int[]} from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Start of a method/block
    default Result<int[]> getIntArray(D value) {
        // Calls a method
        final Result<List<D>> listResult = getList(value);
        // Branch: checks a condition
        if (!(listResult instanceof Result.Ok(List<D> list)))
            // Returns a value to the caller
            return listResult.cast();
        // Calls a method
        final int[] intArray = new int[list.size()];
        // Loop: repeats a block
        for (int i = 0; i < list.size(); i++) {
            // Calls a method
            final Result<Integer> intResult = getInt(list.get(i));
            // Branch: checks a condition
            if (!(intResult instanceof Result.Ok(Integer intValue)))
                // Returns a value to the caller
                return intResult.cast();
            // Assigns a value
            intArray[i] = intValue;
        // End of a block/expression
        }
        // Returns a value to the caller
        return new Result.Ok<>(intArray);
    // End of a block/expression
    }

    /**
     * Creates a {@code int[]} representation of {@link D}
     * @param value the int array
     * @return {@link D} representation of {@code int[]}
     */
    // Start of a method/block
    default D createIntArray(int[] value) {
        // Calls a method
        final ListBuilder<D> list = createList(value.length);
        // Loop: repeats a block
        for (int i : value) list.add(createInt(i));
        // Returns a value to the caller
        return list.build();
    // End of a block/expression
    }

    /**
     * Attempts to unwrap a {@code long[]} from the value {@link D}
     * @param value the value to unwrap
     * @return the result
     */
    // Start of a method/block
    default Result<long[]> getLongArray(D value) {
        // Calls a method
        final Result<List<D>> listResult = getList(value);
        // Branch: checks a condition
        if (!(listResult instanceof Result.Ok(List<D> list)))
            // Returns a value to the caller
            return listResult.cast();
        // Calls a method
        final long[] longArray = new long[list.size()];
        // Loop: repeats a block
        for (int i = 0; i < list.size(); i++) {
            // Calls a method
            final Result<Long> longResult = getLong(list.get(i));
            // Branch: checks a condition
            if (!(longResult instanceof Result.Ok(Long longValue)))
                // Returns a value to the caller
                return longResult.cast();
            // Assigns a value
            longArray[i] = longValue;
        // End of a block/expression
        }
        // Returns a value to the caller
        return new Result.Ok<>(longArray);
    // End of a block/expression
    }

    /**
     * Creates a {@code long[]} representation of {@link D}
     * @param value the long array
     * @return {@link D} representation of {@code long[]}
     */
    // Start of a method/block
    default D createLongArray(long[] value) {
        // Calls a method
        final ListBuilder<D> list = createList(value.length);
        // Loop: repeats a block
        for (long l : value) list.add(createLong(l));
        // Returns a value to the caller
        return list.build();
    // End of a block/expression
    }

    /**
     * Converts the current intermediary of {@link D} into intermediary {@link O}
     * @param coder the transcoder to convert to
     * @param value the value to convert
     * @return the resultant of the conversion
     * @param <O> the intermediary type to convert to
     */
    // Calls a method
    <O> Result<O> convertTo(Transcoder<O> coder, D value);

    /**
     * List builders are used to eventually build a list.
     * <br>
     * They are considered mutable containers, but provide builder semantics.
     * @param <D> the transcoder type
     */
    // Type declaration (class/interface/enum/record)
    interface ListBuilder<D> {
        // Calls a method
        ListBuilder<D> add(D value);

        // Calls a method
        D build();
    // End of a block/expression
    }

    /**
     * Represents an immutable {@link java.util.Map} like object.
     * @param <D> the transcoder type
     */
    // Type declaration (class/interface/enum/record)
    interface MapLike<D> {

        /**
         * Gets all the keys
         * @return the collection of keys
         */
        // Annotation for the following element
        @Contract(pure = true)
        // Annotation for the following element
        @Unmodifiable Collection<String> keys();

        /**
         * Checks if the map has the value mapped to the key
         * @param key the key to check
         * @return true if present; false otherwise
         */
        // Annotation for the following element
        @Contract(pure = true)
        // Calls a method
        boolean hasValue(String key);

        /**
         * Gets the value of the key in a result.
         * <br>
         * Check if the key has a value using {@link #hasValue(String)}
         * @param key the key to use
         * @return the result, {@link Result.Error} if missing
         */
        // Calls a method
        Result<D> getValue(String key);

        /**
         * @return the size of the map
         */
        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        default int size() {
            // Returns a value to the caller
            return keys().size();
        // End of a block/expression
        }

        /**
         * @return true if the size is zero
         */
        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        default boolean isEmpty() {
            // Returns a value to the caller
            return size() == 0;
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Map builders are used to eventually build a map
     * <br>
     * They are considered mutable containers, but provide builder semantics.
     * @param <D> the type of object used by the transcoder
     */
    // Type declaration (class/interface/enum/record)
    interface MapBuilder<D> {

        /**
         * Puts an entry onto the map
         * @param key the key
         * @param value the value
         * @return this
         */
        // Calls a method
        MapBuilder<D> put(D key, D value);

        /**
         * Puts an entry onto the map
         * @param key the string key
         * @param value the value
         * @return this
         */
        // Calls a method
        MapBuilder<D> put(String key, D value);

        /**
         * Build the map with the current values
         * @return the completed map of type {@link D}
         */
        // Calls a method
        D build();
    // End of a block/expression
    }

// End of a block/expression
}
