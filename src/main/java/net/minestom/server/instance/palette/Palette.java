// Déclaration du paquet de ce fichier
package net.minestom.server.instance.palette;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.IntArrayList;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.function.IntUnaryOperator;

// Import statique d'un membre
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
// Déclaration de type (classe/interface/enum/record)
public sealed interface Palette permits PaletteImpl {
    // Affecte une valeur
    int BLOCK_DIMENSION = 16;
    // Affecte une valeur
    int BLOCK_PALETTE_MIN_BITS = 4;
    // Affecte une valeur
    int BLOCK_PALETTE_MAX_BITS = 8;
    // Affecte une valeur
    int BLOCK_PALETTE_DIRECT_BITS = 15;

    // Affecte une valeur
    int BIOME_DIMENSION = 4;
    // Affecte une valeur
    int BIOME_PALETTE_MIN_BITS = 1;
    // Affecte une valeur
    int BIOME_PALETTE_MAX_BITS = 3;
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Affecte une valeur
    int BIOME_PALETTE_DIRECT_BITS = 6; // Vary based on biome count, this is just a sensible default

    // Début d'une méthode/d'un bloc
    static Palette blocks(int bitsPerEntry) {
        // Renvoie une valeur à l'appelant
        return sized(BLOCK_DIMENSION, BLOCK_PALETTE_MIN_BITS, BLOCK_PALETTE_MAX_BITS, BLOCK_PALETTE_DIRECT_BITS, bitsPerEntry);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Palette biomes(int bitsPerEntry) {
        // Renvoie une valeur à l'appelant
        return sized(BIOME_DIMENSION, BIOME_PALETTE_MIN_BITS, BIOME_PALETTE_MAX_BITS, BIOME_PALETTE_DIRECT_BITS, bitsPerEntry);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Palette blocks() {
        // Renvoie une valeur à l'appelant
        return empty(BLOCK_DIMENSION, BLOCK_PALETTE_MIN_BITS, BLOCK_PALETTE_MAX_BITS, BLOCK_PALETTE_DIRECT_BITS);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Palette biomes() {
        // Renvoie une valeur à l'appelant
        return empty(BIOME_DIMENSION, BIOME_PALETTE_MIN_BITS, BIOME_PALETTE_MAX_BITS, BIOME_PALETTE_DIRECT_BITS);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Palette empty(int dimension, int minBitsPerEntry, int maxBitsPerEntry, int directBits) {
        // Renvoie une valeur à l'appelant
        return new PaletteImpl((byte) dimension, (byte) minBitsPerEntry, (byte) maxBitsPerEntry, (byte) directBits);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Palette sized(int dimension, int minBitsPerEntry, int maxBitsPerEntry, int directBits, int bitsPerEntry) {
        // Renvoie une valeur à l'appelant
        return new PaletteImpl((byte) dimension, (byte) minBitsPerEntry, (byte) maxBitsPerEntry, (byte) directBits, (byte) bitsPerEntry);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    int get(int x, int y, int z);

    // Appelle une méthode
    void getAll(EntryConsumer consumer);

    // Appelle une méthode
    void getAllPresent(EntryConsumer consumer);

    // Appelle une méthode
    int height(int x, int z, EntryPredicate predicate);

    // Appelle une méthode
    void set(int x, int y, int z, int value);

    // Appelle une méthode
    void fill(int value);

    // Appelle une méthode
    void load(int[] palette, long[] values);

    // Appelle une méthode
    void offset(int offset);

    // Appelle une méthode
    void replace(int oldValue, int newValue);

    // Appelle une méthode
    void setAll(EntrySupplier supplier);

    // Appelle une méthode
    void replace(int x, int y, int z, IntUnaryOperator operator);

    // Appelle une méthode
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
    // Appelle une méthode
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
    // Appelle une méthode
    void copyFrom(Palette source);

    /**
     * Returns the number of entries in this palette.
     */
    // Appelle une méthode
    int count();

    /**
     * Returns the number of entries in this palette that match the given value.
     *
     * @param value the value to count
     * @return the number of entries matching the value
     */
    // Appelle une méthode
    int count(int value);

    // Début d'une méthode/d'un bloc
    default boolean isEmpty() {
        // Renvoie une valeur à l'appelant
        return count() == 0;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if the palette contains the given value.
     *
     * @param value the value to check
     * @return true if the palette contains the value, false otherwise
     */
    // Appelle une méthode
    boolean any(int value);

    /**
     * Returns the number of bits used per entry.
     */
    // Appelle une méthode
    int bitsPerEntry();

    // Appelle une méthode
    int dimension();

    /**
     * Returns the maximum number of entries in this palette.
     */
    // Début d'une méthode/d'un bloc
    default int maxSize() {
        // Appelle une méthode
        final int dimension = dimension();
        // Renvoie une valeur à l'appelant
        return dimension * dimension * dimension;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Attempts to optimize the current {@link Palette}
     * <br>
     * If plausible the only optimization will be performed is converting to a single value regardless of {@link Optimization}
     * @param focus the optimization focus
     */
    // Appelle une méthode
    void optimize(Optimization focus);

    /**
     * An optimization mode to use with {@link #optimize(Optimization)}
     */
    // Déclaration de type (classe/interface/enum/record)
    enum Optimization {
        /**
         * Will attempt to make indirect to save space.
         */
        // Instruction de code
        SIZE,
        /**
         * Will attempt to make direct to reduce lookup.
         */
        // Instruction de code
        SPEED,
    // Fin d'un bloc/d'une expression
    }

    /**
     * Compare palettes content independently of their storage format.
     *
     * @param palette the palette to compare with
     * @return true if the palettes are equivalent, false otherwise
     */
    // Appelle une méthode
    boolean compare(Palette palette);

    // Appelle une méthode
    Palette clone();

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Appelle une méthode
    int paletteIndexToValue(int value);

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Appelle une méthode
    int valueToPaletteIndex(int value);

    /**
     * Gets the single value of this palette if it is a single value palette, otherwise returns -1.
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Appelle une méthode
    int singleValue();

    /**
     * Gets the value array if it has one, otherwise returns null (i.e. single value palette).
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Appelle une méthode
    long @Nullable [] indexedValues();

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    interface EntrySupplier {
        // Appelle une méthode
        int get(int x, int y, int z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    interface EntryConsumer {
        // Appelle une méthode
        void accept(int x, int y, int z, int value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    interface EntryFunction {
        // Appelle une méthode
        int apply(int x, int y, int z, int value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    interface EntryPredicate {
        // Appelle une méthode
        boolean get(int x, int y, int z, int value);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    NetworkBuffer.Type<Palette> BLOCK_SERIALIZER = serializer(BLOCK_DIMENSION, BLOCK_PALETTE_MIN_BITS, BLOCK_PALETTE_MAX_BITS, BLOCK_PALETTE_DIRECT_BITS);

    // Début d'une méthode/d'un bloc
    static NetworkBuffer.Type<Palette> biomeSerializer(int biomeCount) {
        // Appelle une méthode
        final int directBits = MathUtils.bitsToRepresent(biomeCount);
        // Renvoie une valeur à l'appelant
        return serializer(BIOME_DIMENSION, BIOME_PALETTE_MIN_BITS, BIOME_PALETTE_MAX_BITS, directBits);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static NetworkBuffer.Type<Palette> serializer(int dimension, int minIndirect, int maxIndirect, int directBits) {
        // Renvoie une valeur à l'appelant
        return new NetworkBuffer.Type<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, Palette palette) {
                // Appelle une méthode
                PaletteImpl value = (PaletteImpl) palette;
                // Temporary fix for biome direct bits depending on the number of registered biomes
                // Embranchement : vérifie une condition
                if (directBits != value.directBits && !value.hasPalette()) {
                    // Appelle une méthode
                    PaletteImpl tmp = new PaletteImpl((byte) dimension, (byte) minIndirect, (byte) maxIndirect, (byte) directBits);
                    // Appelle une méthode
                    tmp.setAll(value::get);
                    // Affecte une valeur
                    value = tmp;
                // Fin d'un bloc/d'une expression
                }
                // Affecte une valeur
                final byte bitsPerEntry = value.bitsPerEntry;
                // Appelle une méthode
                buffer.write(BYTE, bitsPerEntry);
                // Embranchement : vérifie une condition
                if (bitsPerEntry == 0) {
                    // Appelle une méthode
                    buffer.write(VAR_INT, value.count);
                // Branche alternative de la condition
                } else {
                    // Embranchement : vérifie une condition
                    if (value.hasPalette()) {
                        // Appelle une méthode
                        buffer.write(VAR_INT.list(), value.paletteToValueList);
                    // Fin d'un bloc/d'une expression
                    }
                    // Boucle : répète un bloc
                    for (long l : value.values) buffer.write(LONG, l);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Palette read(NetworkBuffer buffer) {
                // Appelle une méthode
                final byte bitsPerEntry = buffer.read(BYTE);
                // Embranchement : vérifie une condition
                if (bitsPerEntry != 0 && (bitsPerEntry < minIndirect || (bitsPerEntry > maxIndirect && bitsPerEntry != directBits)))
                    // Lève une exception
                    throw new IllegalArgumentException("Invalid bitsPerEntry: " + bitsPerEntry);
                // Appelle une méthode
                PaletteImpl result = new PaletteImpl((byte) dimension, (byte) minIndirect, (byte) maxIndirect, (byte) directBits);
                // Affecte une valeur
                result.bitsPerEntry = bitsPerEntry;
                // Embranchement : vérifie une condition
                if (bitsPerEntry == 0) {
                    // Single value palette
                    // Appelle une méthode
                    result.count = buffer.read(VAR_INT);
                    // Renvoie une valeur à l'appelant
                    return result;
                // Fin d'un bloc/d'une expression
                }
                // Affecte une valeur
                int[] palette = null;
                // Embranchement : vérifie une condition
                if (result.hasPalette()) {
                    // Indirect palette
                    // Appelle une méthode
                    palette = buffer.read(VAR_INT_ARRAY);
                    // Embranchement : vérifie une condition
                    if (palette.length == 0 || palette.length > Palettes.maxPaletteSize(bitsPerEntry))
                        // Lève une exception
                        throw new IllegalArgumentException("Invalid palette length: " + palette.length);
                    // Appelle une méthode
                    result.paletteToValueList = new IntArrayList(palette);
                    // Appelle une méthode
                    result.valueToPaletteMap = new Int2IntOpenHashMap(palette.length);
                    // Appelle une méthode
                    result.valueToPaletteMap.defaultReturnValue(-1);
                    // Boucle : répète un bloc
                    for (int i = 0; i < palette.length; i++) {
                        // Appelle une méthode
                        result.valueToPaletteMap.put(palette[i], i);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                final long[] data = new long[Palettes.arrayLength(dimension, bitsPerEntry)];
                // Boucle : répète un bloc
                for (int i = 0; i < data.length; i++) data[i] = buffer.read(LONG);
                // Affecte une valeur
                result.values = data;
                // Embranchement : vérifie une condition
                if (palette != null) Palettes.validateIndices(bitsPerEntry, dimension, data, palette.length);
                // Appelle une méthode
                result.recount();
                // Renvoie une valeur à l'appelant
                return result;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
