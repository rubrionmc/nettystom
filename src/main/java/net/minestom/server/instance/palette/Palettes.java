// Déclaration du paquet de ce fichier
package net.minestom.server.instance.palette;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;

// Import d'une classe nécessaire
import java.util.Arrays;

// Déclaration de type (classe/interface/enum/record)
public final class Palettes {
    // Début d'une méthode/d'un bloc
    private Palettes() {
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static long[] pack(int[] ints, int bitsPerEntry) {
        // Appelle une méthode
        final int intsPerLong = (int) Math.floor(64d / bitsPerEntry);
        // Appelle une méthode
        long[] longs = new long[(int) Math.ceil(ints.length / (double) intsPerLong)];
        // Affecte une valeur
        final long mask = (1L << bitsPerEntry) - 1L;
        // Boucle : répète un bloc
        for (int i = 0; i < longs.length; i++) {
            // Boucle : répète un bloc
            for (int intIndex = 0; intIndex < intsPerLong; intIndex++) {
                // Affecte une valeur
                final int bitIndex = intIndex * bitsPerEntry;
                // Affecte une valeur
                final int intActualIndex = intIndex + i * intsPerLong;
                // Embranchement : vérifie une condition
                if (intActualIndex < ints.length) {
                    // Affecte une valeur
                    longs[i] |= (ints[intActualIndex] & mask) << bitIndex;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return longs;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void unpack(int[] out, long[] in, int bitsPerEntry) {
        // Instruction de code
        assert in.length != 0 : "unpack input array is zero";

        // Appelle une méthode
        final double intsPerLong = Math.floor(64d / bitsPerEntry);
        // Appelle une méthode
        final int intsPerLongCeil = (int) Math.ceil(intsPerLong);

        // Affecte une valeur
        final long mask = (1L << bitsPerEntry) - 1L;
        // Boucle : répète un bloc
        for (int i = 0; i < out.length; i++) {
            // Affecte une valeur
            final int longIndex = i / intsPerLongCeil;
            // Affecte une valeur
            final int subIndex = i % intsPerLongCeil;
            // Affecte une valeur
            out[i] = (int) ((in[longIndex] >>> (bitsPerEntry * subIndex)) & mask);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int maxPaletteSize(int bitsPerEntry) {
        // Renvoie une valeur à l'appelant
        return 1 << bitsPerEntry;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int arrayLength(int dimension, int bitsPerEntry) {
        // Affecte une valeur
        final int elementCount = dimension * dimension * dimension;
        // Affecte une valeur
        final int valuesPerLong = 64 / bitsPerEntry;
        // Renvoie une valeur à l'appelant
        return (elementCount + valuesPerLong - 1) / valuesPerLong;
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static int read(int dimension, int bitsPerEntry, long[] values,
                           // Début d'une méthode/d'un bloc
                           int x, int y, int z) {
        // Appelle une méthode
        final int sectionIndex = sectionIndex(dimension, x, y, z);
        // Affecte une valeur
        final int valuesPerLong = 64 / bitsPerEntry;
        // Affecte une valeur
        final int index = sectionIndex / valuesPerLong;
        // Affecte une valeur
        final int bitIndex = (sectionIndex - index * valuesPerLong) * bitsPerEntry;
        // Affecte une valeur
        final int mask = (1 << bitsPerEntry) - 1;
        // Renvoie une valeur à l'appelant
        return (int) (values[index] >> bitIndex) & mask;
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static int write(int dimension, int bitsPerEntry, long[] values,
                            // Début d'une méthode/d'un bloc
                            int x, int y, int z, int value) {
        // Affecte une valeur
        final int valuesPerLong = 64 / bitsPerEntry;
        // Appelle une méthode
        final int sectionIndex = sectionIndex(dimension, x, y, z);
        // Affecte une valeur
        final int index = sectionIndex / valuesPerLong;
        // Affecte une valeur
        final int bitIndex = (sectionIndex - index * valuesPerLong) * bitsPerEntry;

        // Affecte une valeur
        final long block = values[index];
        // Affecte une valeur
        final long clear = (1L << bitsPerEntry) - 1L;
        // Affecte une valeur
        final long oldBlock = block >> bitIndex & clear;
        // Affecte une valeur
        values[index] = block & ~(clear << bitIndex) | ((long) value << bitIndex);
        // Renvoie une valeur à l'appelant
        return (int) oldBlock;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void fill(int bitsPerEntry, long[] values, int value) {
        // Affecte une valeur
        final int valuesPerLong = 64 / bitsPerEntry;
        // Affecte une valeur
        long block = 0;
        // Boucle : répète un bloc
        for (int i = 0; i < valuesPerLong; i++) block |= (long) value << i * bitsPerEntry;
        // Appelle une méthode
        Arrays.fill(values, block);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int count(int bitsPerEntry, long[] values) {
        // Affecte une valeur
        final int valuesPerLong = 64 / bitsPerEntry;
        // Affecte une valeur
        int count = 0;
        // Boucle : répète un bloc
        for (long block : values) {
            // Boucle : répète un bloc
            for (int i = 0; i < valuesPerLong; i++) {
                // Affecte une valeur
                count += (int) ((block >>> i * bitsPerEntry) & ((1 << bitsPerEntry) - 1));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return count;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int sectionIndex(int dimension, int x, int y, int z) {
        // Appelle une méthode
        final int dimensionBitCount = MathUtils.bitsToRepresent(dimension - 1);
        // Renvoie une valeur à l'appelant
        return y << (dimensionBitCount << 1) | z << dimensionBitCount | x;
    // Fin d'un bloc/d'une expression
    }

    // Optimized operations

    // Début d'une méthode/d'un bloc
    public static void getAllFill(byte dimension, int value, Palette.EntryConsumer consumer) {
        // Boucle : répète un bloc
        for (byte y = 0; y < dimension; y++)
            // Boucle : répète un bloc
            for (byte z = 0; z < dimension; z++)
                // Boucle : répète un bloc
                for (byte x = 0; x < dimension; x++)
                    // Appelle une méthode
                    consumer.accept(x, y, z, value);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static long[] remap(int dimension, int oldBitsPerEntry, int newBitsPerEntry,
                               // Début d'une méthode/d'un bloc
                               long[] values, Int2IntFunction function) {
        // Appelle une méthode
        final long[] result = new long[arrayLength(dimension, newBitsPerEntry)];
        // Affecte une valeur
        final int magicMask = (1 << oldBitsPerEntry) - 1;
        // Affecte une valeur
        final int oldValuesPerLong = 64 / oldBitsPerEntry;
        // Affecte une valeur
        final int newValuesPerLong = 64 / newBitsPerEntry;
        // Affecte une valeur
        final int size = dimension * dimension * dimension;
        // Affecte une valeur
        long newValue = 0;
        // Affecte une valeur
        int newValueIndex = 0;
        // Affecte une valeur
        int newBitIndex = 0;
        // Début d'une méthode/d'un bloc
        outer: {
            // Boucle : répète un bloc
            for (int i = 0; i < values.length; i++) {
                // Affecte une valeur
                long value = values[i];
                // Affecte une valeur
                final int startIndex = i * oldValuesPerLong;
                // Appelle une méthode
                final int endIndex = Math.min(startIndex + oldValuesPerLong, size);
                // Boucle : répète un bloc
                for (int index = startIndex; index < endIndex; index++) {
                    // Affecte une valeur
                    final int paletteIndex = (int) (value & magicMask);
                    // Instruction de code
                    value >>>= oldBitsPerEntry;
                    // Appelle une méthode
                    newValue |= ((long) function.get(paletteIndex)) << (newBitIndex++ * newBitsPerEntry);
                    // Embranchement : vérifie une condition
                    if (newBitIndex >= newValuesPerLong) {
                        // Affecte une valeur
                        result[newValueIndex++] = newValue;
                        // Embranchement : vérifie une condition
                        if (newValueIndex == result.length) {
                            // Interrompt la boucle/le bloc
                            break outer;
                        // Fin d'un bloc/d'une expression
                        }
                        // Affecte une valeur
                        newBitIndex = 0;
                        // Affecte une valeur
                        newValue = 0;
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Affecte une valeur
            result[newValueIndex] = newValue;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
