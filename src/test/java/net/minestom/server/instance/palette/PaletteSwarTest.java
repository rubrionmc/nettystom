// Déclaration du paquet de ce fichier
package net.minestom.server.instance.palette;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Random;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

/// Cross-checks the SWAR lane helpers in {@link Palettes} against a naive per-lane reference
/// across every supported bit width and many sizes (including partial final longs).
// Déclaration de type (classe/interface/enum/record)
public class PaletteSwarTest {
    // Affecte une valeur
    private static final int ITERATIONS = 2000;

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void countEqualsMatchesNaive() {
        // Appelle une méthode
        final Random random = new Random(1234567);
        // Boucle : répète un bloc
        for (int it = 0; it < ITERATIONS; it++) {
            // Appelle une méthode
            final int bits = random.nextInt(1, 17);
            // Affecte une valeur
            final int range = 1 << bits;
            // Appelle une méthode
            final int size = random.nextInt(1, 5000);
            // Appelle une méthode
            final int[] indices = randomIndices(random, size, range);
            // Appelle une méthode
            final long[] packed = Palettes.pack(indices, bits);
            // Appelle une méthode
            final int target = random.nextInt(0, range);
            // Instruction de code
            assertEquals(naiveCount(indices, target), Palettes.countEquals(bits, packed, size, target),
                    // Appelle une méthode
                    () -> "count bits=" + bits + " size=" + size + " target=" + target);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void anyEqualsMatchesNaive() {
        // Appelle une méthode
        final Random random = new Random(7654321);
        // Boucle : répète un bloc
        for (int it = 0; it < ITERATIONS; it++) {
            // Appelle une méthode
            final int bits = random.nextInt(1, 17);
            // Affecte une valeur
            final int range = 1 << bits;
            // Appelle une méthode
            final int size = random.nextInt(1, 5000);
            // Bias towards small ranges so both present/absent outcomes are common.
            // Appelle une méthode
            final int effectiveRange = Math.max(2, Math.min(range, random.nextInt(2, 9)));
            // Appelle une méthode
            final int[] indices = randomIndices(random, size, effectiveRange);
            // Appelle une méthode
            final long[] packed = Palettes.pack(indices, bits);
            // Appelle une méthode
            final int target = random.nextInt(0, range);
            // Instruction de code
            assertEquals(naiveAny(indices, target), Palettes.anyEquals(bits, packed, size, target),
                    // Appelle une méthode
                    () -> "any bits=" + bits + " size=" + size + " target=" + target);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void replaceEqualsMatchesNaive() {
        // Appelle une méthode
        final Random random = new Random(192837465);
        // Boucle : répète un bloc
        for (int it = 0; it < ITERATIONS; it++) {
            // Appelle une méthode
            final int bits = random.nextInt(1, 17);
            // Affecte une valeur
            final int range = 1 << bits;
            // Appelle une méthode
            final int size = random.nextInt(1, 5000);
            // Appelle une méthode
            final int[] indices = randomIndices(random, size, range);
            // Appelle une méthode
            final long[] packed = Palettes.pack(indices, bits);
            // Appelle une méthode
            final int oldValue = random.nextInt(0, range);
            // Appelle une méthode
            final int newValue = random.nextInt(0, range);

            // Appelle une méthode
            final int replaced = Palettes.replaceEquals(bits, packed, size, oldValue, newValue);
            // Instruction de code
            assertEquals(naiveCount(indices, oldValue), replaced,
                    // Appelle une méthode
                    () -> "replace count bits=" + bits + " size=" + size + " old=" + oldValue + " new=" + newValue);

            // Verify the mutated array unpacks to the expected content.
            // Appelle une méthode
            final int[] expected = indices.clone();
            // Boucle : répète un bloc
            for (int i = 0; i < expected.length; i++) if (expected[i] == oldValue) expected[i] = newValue;
            // Affecte une valeur
            final int[] actual = new int[size];
            // Appelle une méthode
            Palettes.unpack(actual, packed, bits);
            // Instruction de code
            assertArrayEquals(expected, actual,
                    // Appelle une méthode
                    () -> "replace content bits=" + bits + " size=" + size + " old=" + oldValue + " new=" + newValue);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void countAllZeroAndAllSet() {
        // Boucle : répète un bloc
        for (int bits = 1; bits <= 16; bits++) {
            // Affecte une valeur
            final int range = 1 << bits;
            // Affecte une valeur
            final int size = 4096;
            // Affecte une valeur
            final int[] zeros = new int[size];
            // Appelle une méthode
            final long[] packedZeros = Palettes.pack(zeros, bits);
            // Appelle une méthode
            assertEquals(size, Palettes.countEquals(bits, packedZeros, size, 0), "all-zero bits=" + bits);
            // Appelle une méthode
            assertFalse(Palettes.anyEquals(bits, packedZeros, size, Math.min(range - 1, 1)), "all-zero any bits=" + bits);

            // Affecte une valeur
            final int fillValue = range - 1;
            // Affecte une valeur
            final int[] full = new int[size];
            // Appelle une méthode
            java.util.Arrays.fill(full, fillValue);
            // Appelle une méthode
            final long[] packedFull = Palettes.pack(full, bits);
            // Appelle une méthode
            assertEquals(size, Palettes.countEquals(bits, packedFull, size, fillValue), "all-set bits=" + bits);
            // Appelle une méthode
            assertEquals(0, Palettes.countEquals(bits, packedFull, size, 0), "all-set zero count bits=" + bits);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static int[] randomIndices(Random random, int size, int range) {
        // Affecte une valeur
        final int[] indices = new int[size];
        // Boucle : répète un bloc
        for (int i = 0; i < size; i++) indices[i] = random.nextInt(0, range);
        // Renvoie une valeur à l'appelant
        return indices;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static int naiveCount(int[] indices, int target) {
        // Affecte une valeur
        int count = 0;
        // Boucle : répète un bloc
        for (int v : indices) if (v == target) count++;
        // Renvoie une valeur à l'appelant
        return count;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static boolean naiveAny(int[] indices, int target) {
        // Boucle : répète un bloc
        for (int v : indices) if (v == target) return true;
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
