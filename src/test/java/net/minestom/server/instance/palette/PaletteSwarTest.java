// Package declaration for this file
package net.minestom.server.instance.palette;

// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Random;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

/// Cross-checks the SWAR lane helpers in {@link Palettes} against a naive per-lane reference
/// across every supported bit width and many sizes (including partial final longs).
// Type declaration (class/interface/enum/record)
public class PaletteSwarTest {
    // Assigns a value
    private static final int ITERATIONS = 2000;

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void countEqualsMatchesNaive() {
        // Calls a method
        final Random random = new Random(1234567);
        // Loop: repeats a block
        for (int it = 0; it < ITERATIONS; it++) {
            // Calls a method
            final int bits = random.nextInt(1, 17);
            // Assigns a value
            final int range = 1 << bits;
            // Calls a method
            final int size = random.nextInt(1, 5000);
            // Calls a method
            final int[] indices = randomIndices(random, size, range);
            // Calls a method
            final long[] packed = Palettes.pack(indices, bits);
            // Calls a method
            final int target = random.nextInt(0, range);
            // Code statement
            assertEquals(naiveCount(indices, target), Palettes.countEquals(bits, packed, size, target),
                    // Calls a method
                    () -> "count bits=" + bits + " size=" + size + " target=" + target);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void anyEqualsMatchesNaive() {
        // Calls a method
        final Random random = new Random(7654321);
        // Loop: repeats a block
        for (int it = 0; it < ITERATIONS; it++) {
            // Calls a method
            final int bits = random.nextInt(1, 17);
            // Assigns a value
            final int range = 1 << bits;
            // Calls a method
            final int size = random.nextInt(1, 5000);
            // Bias towards small ranges so both present/absent outcomes are common.
            // Calls a method
            final int effectiveRange = Math.max(2, Math.min(range, random.nextInt(2, 9)));
            // Calls a method
            final int[] indices = randomIndices(random, size, effectiveRange);
            // Calls a method
            final long[] packed = Palettes.pack(indices, bits);
            // Calls a method
            final int target = random.nextInt(0, range);
            // Code statement
            assertEquals(naiveAny(indices, target), Palettes.anyEquals(bits, packed, size, target),
                    // Calls a method
                    () -> "any bits=" + bits + " size=" + size + " target=" + target);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void replaceEqualsMatchesNaive() {
        // Calls a method
        final Random random = new Random(192837465);
        // Loop: repeats a block
        for (int it = 0; it < ITERATIONS; it++) {
            // Calls a method
            final int bits = random.nextInt(1, 17);
            // Assigns a value
            final int range = 1 << bits;
            // Calls a method
            final int size = random.nextInt(1, 5000);
            // Calls a method
            final int[] indices = randomIndices(random, size, range);
            // Calls a method
            final long[] packed = Palettes.pack(indices, bits);
            // Calls a method
            final int oldValue = random.nextInt(0, range);
            // Calls a method
            final int newValue = random.nextInt(0, range);

            // Calls a method
            final int replaced = Palettes.replaceEquals(bits, packed, size, oldValue, newValue);
            // Code statement
            assertEquals(naiveCount(indices, oldValue), replaced,
                    // Calls a method
                    () -> "replace count bits=" + bits + " size=" + size + " old=" + oldValue + " new=" + newValue);

            // Verify the mutated array unpacks to the expected content.
            // Calls a method
            final int[] expected = indices.clone();
            // Loop: repeats a block
            for (int i = 0; i < expected.length; i++) if (expected[i] == oldValue) expected[i] = newValue;
            // Assigns a value
            final int[] actual = new int[size];
            // Calls a method
            Palettes.unpack(actual, packed, bits);
            // Code statement
            assertArrayEquals(expected, actual,
                    // Calls a method
                    () -> "replace content bits=" + bits + " size=" + size + " old=" + oldValue + " new=" + newValue);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void countAllZeroAndAllSet() {
        // Loop: repeats a block
        for (int bits = 1; bits <= 16; bits++) {
            // Assigns a value
            final int range = 1 << bits;
            // Assigns a value
            final int size = 4096;
            // Assigns a value
            final int[] zeros = new int[size];
            // Calls a method
            final long[] packedZeros = Palettes.pack(zeros, bits);
            // Calls a method
            assertEquals(size, Palettes.countEquals(bits, packedZeros, size, 0), "all-zero bits=" + bits);
            // Calls a method
            assertFalse(Palettes.anyEquals(bits, packedZeros, size, Math.min(range - 1, 1)), "all-zero any bits=" + bits);

            // Assigns a value
            final int fillValue = range - 1;
            // Assigns a value
            final int[] full = new int[size];
            // Calls a method
            java.util.Arrays.fill(full, fillValue);
            // Calls a method
            final long[] packedFull = Palettes.pack(full, bits);
            // Calls a method
            assertEquals(size, Palettes.countEquals(bits, packedFull, size, fillValue), "all-set bits=" + bits);
            // Calls a method
            assertEquals(0, Palettes.countEquals(bits, packedFull, size, 0), "all-set zero count bits=" + bits);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static int[] randomIndices(Random random, int size, int range) {
        // Assigns a value
        final int[] indices = new int[size];
        // Loop: repeats a block
        for (int i = 0; i < size; i++) indices[i] = random.nextInt(0, range);
        // Returns a value to the caller
        return indices;
    // End of a block/expression
    }

    // Start of a method/block
    private static int naiveCount(int[] indices, int target) {
        // Assigns a value
        int count = 0;
        // Loop: repeats a block
        for (int v : indices) if (v == target) count++;
        // Returns a value to the caller
        return count;
    // End of a block/expression
    }

    // Start of a method/block
    private static boolean naiveAny(int[] indices, int target) {
        // Loop: repeats a block
        for (int v : indices) if (v == target) return true;
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }
// End of a block/expression
}
