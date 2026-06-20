// Package declaration for this file
package net.minestom.server.instance.palette;

// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
// Import of a required class
import it.unimi.dsi.fastutil.ints.IntArrayList;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.utils.MathUtils;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.function.IntUnaryOperator;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

/**
 * Palette is a data storage with three storage models used to store blocks and biomes
 * <br>
 * Single Value Mode {@code (bitsPerEntry == 0)}: All blocks have the same value.
 * No arrays allocated, value stored in count field.
 * <br>
 * Indirect Mode {@code (bitsPerEntry <= maxBitsPerEntry)}: Uses palette compression.
 * Values array stores palette indices, paletteToValueList and valueToPaletteMap
 * provide bidirectional mapping between indices and block values.
 * <br>
 * Direct Mode {@code (bitsPerEntry > maxBitsPerEntry)}: Stores block values directly.
 * No palette structures, values array contains actual block values using directBits.
 * <br>
 * You can optimize for space/speed using {@link #optimize(Optimization)}
 */
// Type declaration (class/interface/enum/record)
public sealed interface Palette permits PaletteImpl {
    // Assigns a value
    int BLOCK_DIMENSION = 16;
    // Assigns a value
    int BLOCK_PALETTE_MIN_BITS = 4;
    // Assigns a value
    int BLOCK_PALETTE_MAX_BITS = 8;
    // Assigns a value
    int BLOCK_PALETTE_DIRECT_BITS = 15;

    // Assigns a value
    int BIOME_DIMENSION = 4;
    // Assigns a value
    int BIOME_PALETTE_MIN_BITS = 1;
    // Assigns a value
    int BIOME_PALETTE_MAX_BITS = 3;
    // Annotation for the following element
    @ApiStatus.Internal
    // Assigns a value
    int BIOME_PALETTE_DIRECT_BITS = 6; // Vary based on biome count, this is just a sensible default

    // Start of a method/block
    static Palette blocks(int bitsPerEntry) {
        // Returns a value to the caller
        return sized(BLOCK_DIMENSION, BLOCK_PALETTE_MIN_BITS, BLOCK_PALETTE_MAX_BITS, BLOCK_PALETTE_DIRECT_BITS, bitsPerEntry);
    // End of a block/expression
    }

    // Start of a method/block
    static Palette biomes(int bitsPerEntry) {
        // Returns a value to the caller
        return sized(BIOME_DIMENSION, BIOME_PALETTE_MIN_BITS, BIOME_PALETTE_MAX_BITS, BIOME_PALETTE_DIRECT_BITS, bitsPerEntry);
    // End of a block/expression
    }

    // Start of a method/block
    static Palette blocks() {
        // Returns a value to the caller
        return empty(BLOCK_DIMENSION, BLOCK_PALETTE_MIN_BITS, BLOCK_PALETTE_MAX_BITS, BLOCK_PALETTE_DIRECT_BITS);
    // End of a block/expression
    }

    // Start of a method/block
    static Palette biomes() {
        // Returns a value to the caller
        return empty(BIOME_DIMENSION, BIOME_PALETTE_MIN_BITS, BIOME_PALETTE_MAX_BITS, BIOME_PALETTE_DIRECT_BITS);
    // End of a block/expression
    }

    // Start of a method/block
    static Palette empty(int dimension, int minBitsPerEntry, int maxBitsPerEntry, int directBits) {
        // Returns a value to the caller
        return new PaletteImpl((byte) dimension, (byte) minBitsPerEntry, (byte) maxBitsPerEntry, (byte) directBits);
    // End of a block/expression
    }

    // Start of a method/block
    static Palette sized(int dimension, int minBitsPerEntry, int maxBitsPerEntry, int directBits, int bitsPerEntry) {
        // Returns a value to the caller
        return new PaletteImpl((byte) dimension, (byte) minBitsPerEntry, (byte) maxBitsPerEntry, (byte) directBits, (byte) bitsPerEntry);
    // End of a block/expression
    }

    // Calls a method
    int get(int x, int y, int z);

    // Calls a method
    void getAll(EntryConsumer consumer);

    // Calls a method
    void getAllPresent(EntryConsumer consumer);

    // Calls a method
    int height(int x, int z, EntryPredicate predicate);

    // Calls a method
    void set(int x, int y, int z, int value);

    // Calls a method
    void fill(int value);

    // Calls a method
    void load(int[] palette, long[] values);

    // Calls a method
    void offset(int offset);

    // Calls a method
    void replace(int oldValue, int newValue);

    // Calls a method
    void setAll(EntrySupplier supplier);

    // Calls a method
    void replace(int x, int y, int z, IntUnaryOperator operator);

    // Calls a method
    void replaceAll(EntryFunction function);

    /**
     * Efficiently copies values from another palette with the given offset.
     * <p>
     * Both palettes must have the same dimension.
     *
     * @param source  the source palette to copy from
     * @param offsetX the X offset to apply when copying
     * @param offsetY the Y offset to apply when copying
     * @param offsetZ the Z offset to apply when copying
     */
    // Calls a method
    void copyFrom(Palette source, int offsetX, int offsetY, int offsetZ);

    /**
     * Efficiently copies values from another palette starting at position (0, 0, 0).
     * <p>
     * Both palettes must have the same dimension.
     * <p>
     * This is a convenience method equivalent to calling {@code copyFrom(source, 0, 0, 0)}.
     *
     * @param source the source palette to copy from
     */
    // Calls a method
    void copyFrom(Palette source);

    /**
     * Returns the number of entries in this palette.
     */
    // Calls a method
    int count();

    /**
     * Returns the number of entries in this palette that match the given value.
     *
     * @param value the value to count
     * @return the number of entries matching the value
     */
    // Calls a method
    int count(int value);

    // Start of a method/block
    default boolean isEmpty() {
        // Returns a value to the caller
        return count() == 0;
    // End of a block/expression
    }

    /**
     * Checks if the palette contains the given value.
     *
     * @param value the value to check
     * @return true if the palette contains the value, false otherwise
     */
    // Calls a method
    boolean any(int value);

    /**
     * Returns the number of bits used per entry.
     */
    // Calls a method
    int bitsPerEntry();

    // Calls a method
    int dimension();

    /**
     * Returns the maximum number of entries in this palette.
     */
    // Start of a method/block
    default int maxSize() {
        // Calls a method
        final int dimension = dimension();
        // Returns a value to the caller
        return dimension * dimension * dimension;
    // End of a block/expression
    }

    /**
     * Attempts to optimize the current {@link Palette}
     * <br>
     * If plausible the only optimization will be performed is converting to a single value regardless of {@link Optimization}
     * @param focus the optimization focus
     */
    // Calls a method
    void optimize(Optimization focus);

    /**
     * An optimization mode to use with {@link #optimize(Optimization)}
     */
    // Type declaration (class/interface/enum/record)
    enum Optimization {
        /**
         * Will attempt to make indirect to save space.
         */
        // Code statement
        SIZE,
        /**
         * Will attempt to make direct to reduce lookup.
         */
        // Code statement
        SPEED,
    // End of a block/expression
    }

    /**
     * Compare palettes content independently of their storage format.
     *
     * @param palette the palette to compare with
     * @return true if the palettes are equivalent, false otherwise
     */
    // Calls a method
    boolean compare(Palette palette);

    // Calls a method
    Palette clone();

    // Annotation for the following element
    @ApiStatus.Internal
    // Calls a method
    int paletteIndexToValue(int value);

    // Annotation for the following element
    @ApiStatus.Internal
    // Calls a method
    int valueToPaletteIndex(int value);

    /**
     * Gets the single value of this palette if it is a single value palette, otherwise returns -1.
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Calls a method
    int singleValue();

    /**
     * Gets the value array if it has one, otherwise returns null (i.e. single value palette).
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Calls a method
    long @Nullable [] indexedValues();

    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    interface EntrySupplier {
        // Calls a method
        int get(int x, int y, int z);
    // End of a block/expression
    }

    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    interface EntryConsumer {
        // Calls a method
        void accept(int x, int y, int z, int value);
    // End of a block/expression
    }

    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    interface EntryFunction {
        // Calls a method
        int apply(int x, int y, int z, int value);
    // End of a block/expression
    }

    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    interface EntryPredicate {
        // Calls a method
        boolean get(int x, int y, int z, int value);
    // End of a block/expression
    }

    // Calls a method
    NetworkBuffer.Type<Palette> BLOCK_SERIALIZER = serializer(BLOCK_DIMENSION, BLOCK_PALETTE_MIN_BITS, BLOCK_PALETTE_MAX_BITS, BLOCK_PALETTE_DIRECT_BITS);

    // Start of a method/block
    static NetworkBuffer.Type<Palette> biomeSerializer(int biomeCount) {
        // Calls a method
        final int directBits = MathUtils.bitsToRepresent(biomeCount);
        // Returns a value to the caller
        return serializer(BIOME_DIMENSION, BIOME_PALETTE_MIN_BITS, BIOME_PALETTE_MAX_BITS, directBits);
    // End of a block/expression
    }

    // Start of a method/block
    static NetworkBuffer.Type<Palette> serializer(int dimension, int minIndirect, int maxIndirect, int directBits) {
        // Returns a value to the caller
        return new NetworkBuffer.Type<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void write(NetworkBuffer buffer, Palette palette) {
                // Calls a method
                PaletteImpl value = (PaletteImpl) palette;
                // Temporary fix for biome direct bits depending on the number of registered biomes
                // Branch: checks a condition
                if (directBits != value.directBits && !value.hasPalette()) {
                    // Calls a method
                    PaletteImpl tmp = new PaletteImpl((byte) dimension, (byte) minIndirect, (byte) maxIndirect, (byte) directBits);
                    // Calls a method
                    tmp.setAll(value::get);
                    // Assigns a value
                    value = tmp;
                // End of a block/expression
                }
                // Assigns a value
                final byte bitsPerEntry = value.bitsPerEntry;
                // Calls a method
                buffer.write(BYTE, bitsPerEntry);
                // Branch: checks a condition
                if (bitsPerEntry == 0) {
                    // Calls a method
                    buffer.write(VAR_INT, value.count);
                // Alternative branch of the condition
                } else {
                    // Branch: checks a condition
                    if (value.hasPalette()) {
                        // Calls a method
                        buffer.write(VAR_INT.list(), value.paletteToValueList);
                    // End of a block/expression
                    }
                    // Loop: repeats a block
                    for (long l : value.values) buffer.write(LONG, l);
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Palette read(NetworkBuffer buffer) {
                // Calls a method
                final byte bitsPerEntry = buffer.read(BYTE);
                // Branch: checks a condition
                if (bitsPerEntry != 0 && (bitsPerEntry < minIndirect || (bitsPerEntry > maxIndirect && bitsPerEntry != directBits)))
                    // Throws an exception
                    throw new IllegalArgumentException("Invalid bitsPerEntry: " + bitsPerEntry);
                // Calls a method
                PaletteImpl result = new PaletteImpl((byte) dimension, (byte) minIndirect, (byte) maxIndirect, (byte) directBits);
                // Assigns a value
                result.bitsPerEntry = bitsPerEntry;
                // Branch: checks a condition
                if (bitsPerEntry == 0) {
                    // Single value palette
                    // Calls a method
                    result.count = buffer.read(VAR_INT);
                    // Returns a value to the caller
                    return result;
                // End of a block/expression
                }
                // Assigns a value
                int[] palette = null;
                // Branch: checks a condition
                if (result.hasPalette()) {
                    // Indirect palette
                    // Calls a method
                    palette = buffer.read(VAR_INT_ARRAY);
                    // Branch: checks a condition
                    if (palette.length == 0 || palette.length > Palettes.maxPaletteSize(bitsPerEntry))
                        // Throws an exception
                        throw new IllegalArgumentException("Invalid palette length: " + palette.length);
                    // Calls a method
                    result.paletteToValueList = new IntArrayList(palette);
                    // Calls a method
                    result.valueToPaletteMap = new Int2IntOpenHashMap(palette.length);
                    // Calls a method
                    result.valueToPaletteMap.defaultReturnValue(-1);
                    // Loop: repeats a block
                    for (int i = 0; i < palette.length; i++) {
                        // Calls a method
                        result.valueToPaletteMap.put(palette[i], i);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Calls a method
                final long[] data = new long[Palettes.arrayLength(dimension, bitsPerEntry)];
                // Loop: repeats a block
                for (int i = 0; i < data.length; i++) data[i] = buffer.read(LONG);
                // Assigns a value
                result.values = data;
                // Branch: checks a condition
                if (palette != null) Palettes.validateIndices(bitsPerEntry, dimension, data, palette.length);
                // Calls a method
                result.recount();
                // Returns a value to the caller
                return result;
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }
// End of a block/expression
}
