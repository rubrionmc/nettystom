// Package declaration for this file
package net.minestom.server.instance.palette;

// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
// Import of a required class
import net.minestom.server.utils.MathUtils;

// Import of a required class
import java.util.Arrays;

// Type declaration (class/interface/enum/record)
public final class Palettes {
    // Start of a method/block
    private Palettes() {
    // End of a block/expression
    }

    // Start of a method/block
    public static long[] pack(int[] ints, int bitsPerEntry) {
        // Calls a method
        final int intsPerLong = (int) Math.floor(64d / bitsPerEntry);
        // Calls a method
        long[] longs = new long[(int) Math.ceil(ints.length / (double) intsPerLong)];
        // Calls a method
        final long mask = (1L << bitsPerEntry) - 1L;
        // Loop: repeats a block
        for (int i = 0; i < longs.length; i++) {
            // Loop: repeats a block
            for (int intIndex = 0; intIndex < intsPerLong; intIndex++) {
                // Assigns a value
                final int bitIndex = intIndex * bitsPerEntry;
                // Assigns a value
                final int intActualIndex = intIndex + i * intsPerLong;
                // Branch: checks a condition
                if (intActualIndex < ints.length) {
                    // Calls a method
                    longs[i] |= (ints[intActualIndex] & mask) << bitIndex;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return longs;
    // End of a block/expression
    }

    // Start of a method/block
    public static void unpack(int[] out, long[] in, int bitsPerEntry) {
        // Code statement
        assert in.length != 0 : "unpack input array is zero";

        // Calls a method
        final double intsPerLong = Math.floor(64d / bitsPerEntry);
        // Calls a method
        final int intsPerLongCeil = (int) Math.ceil(intsPerLong);

        // Calls a method
        final long mask = (1L << bitsPerEntry) - 1L;
        // Loop: repeats a block
        for (int i = 0; i < out.length; i++) {
            // Assigns a value
            final int longIndex = i / intsPerLongCeil;
            // Assigns a value
            final int subIndex = i % intsPerLongCeil;
            // Calls a method
            out[i] = (int) ((in[longIndex] >>> (bitsPerEntry * subIndex)) & mask);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static int maxPaletteSize(int bitsPerEntry) {
        // Returns a value to the caller
        return 1 << bitsPerEntry;
    // End of a block/expression
    }

    // Start of a method/block
    public static int arrayLength(int dimension, int bitsPerEntry) {
        // Assigns a value
        final int elementCount = dimension * dimension * dimension;
        // Assigns a value
        final int valuesPerLong = 64 / bitsPerEntry;
        // Returns a value to the caller
        return (elementCount + valuesPerLong - 1) / valuesPerLong;
    // End of a block/expression
    }

    // Code statement
    public static int read(int dimension, int bitsPerEntry, long[] values,
                           // Start of a method/block
                           int x, int y, int z) {
        // Calls a method
        final int sectionIndex = sectionIndex(dimension, x, y, z);
        // Assigns a value
        final int valuesPerLong = 64 / bitsPerEntry;
        // Assigns a value
        final int index = sectionIndex / valuesPerLong;
        // Calls a method
        final int bitIndex = (sectionIndex - index * valuesPerLong) * bitsPerEntry;
        // Calls a method
        final int mask = (1 << bitsPerEntry) - 1;
        // Returns a value to the caller
        return (int) (values[index] >> bitIndex) & mask;
    // End of a block/expression
    }

    // Code statement
    public static int write(int dimension, int bitsPerEntry, long[] values,
                            // Start of a method/block
                            int x, int y, int z, int value) {
        // Assigns a value
        final int valuesPerLong = 64 / bitsPerEntry;
        // Calls a method
        final int sectionIndex = sectionIndex(dimension, x, y, z);
        // Assigns a value
        final int index = sectionIndex / valuesPerLong;
        // Calls a method
        final int bitIndex = (sectionIndex - index * valuesPerLong) * bitsPerEntry;

        // Assigns a value
        final long block = values[index];
        // Calls a method
        final long clear = (1L << bitsPerEntry) - 1L;
        // Assigns a value
        final long oldBlock = block >> bitIndex & clear;
        // Calls a method
        values[index] = block & ~(clear << bitIndex) | ((long) value << bitIndex);
        // Returns a value to the caller
        return (int) oldBlock;
    // End of a block/expression
    }

    // Start of a method/block
    public static void fill(int bitsPerEntry, long[] values, int value) {
        // Calls a method
        Arrays.fill(values, broadcast(bitsPerEntry, value));
    // End of a block/expression
    }

    // Start of a method/block
    public static int count(int bitsPerEntry, long[] values) {
        // Assigns a value
        final int valuesPerLong = 64 / bitsPerEntry;
        // Assigns a value
        int count = 0;
        // Loop: repeats a block
        for (long block : values) {
            // Loop: repeats a block
            for (int i = 0; i < valuesPerLong; i++) {
                // Calls a method
                count += (int) ((block >>> i * bitsPerEntry) & ((1 << bitsPerEntry) - 1));
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return count;
    // End of a block/expression
    }

    /// Builds a 64-bit pattern with {@code value} placed in every {@code bitsPerEntry}-wide lane.
    // Start of a method/block
    public static long broadcast(int bitsPerEntry, int value) {
        // Assigns a value
        final int valuesPerLong = 64 / bitsPerEntry;
        // Assigns a value
        long pattern = 0L;
        // Loop: repeats a block
        for (int i = 0; i < valuesPerLong; i++) pattern |= (long) value << (i * bitsPerEntry);
        // Returns a value to the caller
        return pattern;
    // End of a block/expression
    }

    /// Counts the packed entries equal to {@code target} among the first {@code size} entries.
    /// Scans 64 bits at a time using borrow-safe SWAR zero-lane detection.
    // Start of a method/block
    public static int countEquals(int bitsPerEntry, long[] values, int size, int target) {
        // Assigns a value
        final int valuesPerLong = 64 / bitsPerEntry;
        // Calls a method
        final long ones = broadcast(bitsPerEntry, 1);
        // Calls a method
        final long lowMask = ones * ((1L << (bitsPerEntry - 1)) - 1);
        // Calls a method
        final long highBits = ones * (1L << (bitsPerEntry - 1));
        // Assigns a value
        final long broadcastTarget = ones * target;
        // Assigns a value
        int result = 0;
        // Loop: repeats a block
        for (int i = 0, idx = 0; i < values.length; i++, idx += valuesPerLong) {
            // Calls a method
            result += Long.bitCount(matchingLanes(values[i], broadcastTarget, lowMask, highBits, size - idx, valuesPerLong, bitsPerEntry));
        // End of a block/expression
        }
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    /// Returns true if any of the first {@code size} packed entries equals {@code target}.
    // Start of a method/block
    public static boolean anyEquals(int bitsPerEntry, long[] values, int size, int target) {
        // Assigns a value
        final int valuesPerLong = 64 / bitsPerEntry;
        // Calls a method
        final long ones = broadcast(bitsPerEntry, 1);
        // Calls a method
        final long lowMask = ones * ((1L << (bitsPerEntry - 1)) - 1);
        // Calls a method
        final long highBits = ones * (1L << (bitsPerEntry - 1));
        // Assigns a value
        final long broadcastTarget = ones * target;
        // Loop: repeats a block
        for (int i = 0, idx = 0; i < values.length; i++, idx += valuesPerLong) {
            // Branch: checks a condition
            if (matchingLanes(values[i], broadcastTarget, lowMask, highBits, size - idx, valuesPerLong, bitsPerEntry) != 0)
                // Returns a value to the caller
                return true;
        // End of a block/expression
        }
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    /// Replaces every packed entry equal to {@code oldValue} with {@code newValue} among the first
    /// {@code size} entries, returning the number of entries replaced.
    // Start of a method/block
    public static int replaceEquals(int bitsPerEntry, long[] values, int size, int oldValue, int newValue) {
        // Assigns a value
        final int valuesPerLong = 64 / bitsPerEntry;
        // Calls a method
        final long ones = broadcast(bitsPerEntry, 1);
        // Calls a method
        final long lowMask = ones * ((1L << (bitsPerEntry - 1)) - 1);
        // Calls a method
        final long highBits = ones * (1L << (bitsPerEntry - 1));
        // Assigns a value
        final long broadcastOld = ones * oldValue;
        // Assigns a value
        final long broadcastNew = ones * newValue;
        // Assigns a value
        int result = 0;
        // Loop: repeats a block
        for (int i = 0, idx = 0; i < values.length; i++, idx += valuesPerLong) {
            // Assigns a value
            final long block = values[i];
            // Calls a method
            final long zeros = matchingLanes(block, broadcastOld, lowMask, highBits, size - idx, valuesPerLong, bitsPerEntry);
            // Branch: checks a condition
            if (zeros == 0) continue;
            // Expand each lane's high-bit marker to a full-lane mask, then swap the matching lanes.
            // Calls a method
            final long laneMask = zeros | (zeros - (zeros >>> (bitsPerEntry - 1)));
            // Calls a method
            values[i] = (block & ~laneMask) | (broadcastNew & laneMask);
            // Calls a method
            result += Long.bitCount(zeros);
        // End of a block/expression
        }
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    /// High bit set in each lane equal to {@code broadcastTarget}, restricted to the first
    /// {@code remaining} lanes. Borrow-safe so a zero lane never spills into its neighbour.
    // Code statement
    private static long matchingLanes(long block, long broadcastTarget, long lowMask, long highBits,
                                      // Start of a method/block
                                      int remaining, int valuesPerLong, int bitsPerEntry) {
        // Assigns a value
        final long x = block ^ broadcastTarget;
        // Calls a method
        final long t = (x & lowMask) + lowMask;
        // Calls a method
        long zeros = ~(t | x) & highBits;
        // Branch: checks a condition
        if (remaining < valuesPerLong) zeros &= (1L << (remaining * bitsPerEntry)) - 1L;
        // Returns a value to the caller
        return zeros;
    // End of a block/expression
    }

    // Start of a method/block
    public static int sectionIndex(int dimension, int x, int y, int z) {
        // Calls a method
        final int dimensionBitCount = MathUtils.bitsToRepresent(dimension - 1);
        // Returns a value to the caller
        return y << (dimensionBitCount << 1) | z << dimensionBitCount | x;
    // End of a block/expression
    }

    // Start of a method/block
    static void validateIndices(int bitsPerEntry, int dimension, long[] values, int paletteSize) {
        // Assigns a value
        final int valuesPerLong = 64 / bitsPerEntry;
        // Assigns a value
        final int size = dimension * dimension * dimension;
        // Calls a method
        final long mask = (1L << bitsPerEntry) - 1L;
        // Loop: repeats a block
        for (int i = 0, idx = 0; i < values.length; i++) {
            // Assigns a value
            long block = values[i];
            // Calls a method
            final int end = Math.min(valuesPerLong, size - idx);
            // Loop: repeats a block
            for (int j = 0; j < end; j++, idx++) {
                // Calls a method
                final int paletteIdx = (int) (block & mask);
                // Branch: checks a condition
                if (paletteIdx >= paletteSize)
                    // Throws an exception
                    throw new IllegalArgumentException("Palette index out of range: " + paletteIdx + " >= " + paletteSize);
                // Code statement
                block >>>= bitsPerEntry;
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Optimized operations

    // Start of a method/block
    public static void getAllFill(byte dimension, int value, Palette.EntryConsumer consumer) {
        // Loop: repeats a block
        for (byte y = 0; y < dimension; y++)
            // Loop: repeats a block
            for (byte z = 0; z < dimension; z++)
                // Loop: repeats a block
                for (byte x = 0; x < dimension; x++)
                    // Calls a method
                    consumer.accept(x, y, z, value);
    // End of a block/expression
    }

    // Code statement
    public static long[] remap(int dimension, int oldBitsPerEntry, int newBitsPerEntry,
                               // Start of a method/block
                               long[] values, Int2IntFunction function) {
        // Calls a method
        final long[] result = new long[arrayLength(dimension, newBitsPerEntry)];
        // Calls a method
        final int magicMask = (1 << oldBitsPerEntry) - 1;
        // Assigns a value
        final int oldValuesPerLong = 64 / oldBitsPerEntry;
        // Assigns a value
        final int newValuesPerLong = 64 / newBitsPerEntry;
        // Assigns a value
        final int size = dimension * dimension * dimension;
        // Assigns a value
        long newValue = 0;
        // Assigns a value
        int newValueIndex = 0;
        // Assigns a value
        int newBitIndex = 0;
        // Code statement
        outer:
        // Start of a block
        {
            // Loop: repeats a block
            for (int i = 0; i < values.length; i++) {
                // Assigns a value
                long value = values[i];
                // Assigns a value
                final int startIndex = i * oldValuesPerLong;
                // Calls a method
                final int endIndex = Math.min(startIndex + oldValuesPerLong, size);
                // Loop: repeats a block
                for (int index = startIndex; index < endIndex; index++) {
                    // Calls a method
                    final int paletteIndex = (int) (value & magicMask);
                    // Code statement
                    value >>>= oldBitsPerEntry;
                    // Calls a method
                    newValue |= ((long) function.get(paletteIndex)) << (newBitIndex++ * newBitsPerEntry);
                    // Branch: checks a condition
                    if (newBitIndex >= newValuesPerLong) {
                        // Assigns a value
                        result[newValueIndex++] = newValue;
                        // Branch: checks a condition
                        if (newValueIndex == result.length) {
                            // Breaks out of the loop/block
                            break outer;
                        // End of a block/expression
                        }
                        // Assigns a value
                        newBitIndex = 0;
                        // Assigns a value
                        newValue = 0;
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Assigns a value
            result[newValueIndex] = newValue;
        // End of a block/expression
        }
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }
// End of a block/expression
}
