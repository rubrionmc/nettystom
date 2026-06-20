// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import org.junit.jupiter.api.DisplayName;
// Import d'une classe nécessaire
import org.junit.jupiter.api.RepeatedTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;
// Import d'une classe nécessaire
import org.junit.jupiter.params.ParameterizedTest;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.CsvSource;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.ValueSource;

// Import d'une classe nécessaire
import java.util.HashSet;
// Import d'une classe nécessaire
import java.util.Random;
// Import d'une classe nécessaire
import java.util.Set;

// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.hashBlockCoord;
// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.hashGlobalCoord;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class CoordinateHashTest {

    // Test constants
    // Affecte une valeur
    private static final int COLLISION_TEST_SIZE = 100000;
    // Affecte une valeur
    private static final int DISTRIBUTION_BINS = 1000;
    // Affecte une valeur
    private static final double DISTRIBUTION_TOLERANCE = 0.05; // 5% tolerance for uniform distribution

    // Annotation pour l'élément suivant
    @Test
    // Annotation pour l'élément suivant
    @DisplayName("hashBlockCoord - Basic functionality test")
    // Début d'une méthode/d'un bloc
    public void testhashBlockCoordBasic() {
        // Test basic functionality
        // Appelle une méthode
        long hash1 = hashBlockCoord(0, 0, 0);
        // Appelle une méthode
        long hash2 = hashBlockCoord(1, 1, 1);
        // Appelle une méthode
        long hash3 = hashBlockCoord(-1, -1, -1);

        // Hash should be deterministic
        // Appelle une méthode
        assertEquals(hash1, hashBlockCoord(0, 0, 0));
        // Appelle une méthode
        assertEquals(hash2, hashBlockCoord(1, 1, 1));
        // Appelle une méthode
        assertEquals(hash3, hashBlockCoord(-1, -1, -1));

        // Different inputs should produce different hashes (with high probability)
        // Appelle une méthode
        assertNotEquals(hash1, hash2);
        // Appelle une méthode
        assertNotEquals(hash1, hash3);
        // Appelle une méthode
        assertNotEquals(hash2, hash3);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Annotation pour l'élément suivant
    @DisplayName("hashGlobalCoord - Basic functionality test")
    // Début d'une méthode/d'un bloc
    public void testhashGlobalCoordBasic() {
        // Test basic functionality
        // Appelle une méthode
        long hash1 = hashGlobalCoord(0.0, 0.0, 0.0);
        // Appelle une méthode
        long hash2 = hashGlobalCoord(1.0, 1.0, 1.0);
        // Appelle une méthode
        long hash3 = hashGlobalCoord(-1.0, -1.0, -1.0);

        // Hash should be deterministic
        // Appelle une méthode
        assertEquals(hash1, hashGlobalCoord(0.0, 0.0, 0.0));
        // Appelle une méthode
        assertEquals(hash2, hashGlobalCoord(1.0, 1.0, 1.0));
        // Appelle une méthode
        assertEquals(hash3, hashGlobalCoord(-1.0, -1.0, -1.0));

        // Different inputs should produce different hashes (with high probability)
        // Appelle une méthode
        assertNotEquals(hash1, hash2);
        // Appelle une méthode
        assertNotEquals(hash1, hash3);
        // Appelle une méthode
        assertNotEquals(hash2, hash3);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Annotation pour l'élément suivant
    @DisplayName("hashBlockCoord - Edge cases and extreme values")
    // Début d'une méthode/d'un bloc
    public void testhashBlockCoordEdgeCases() {
        // Test with maximum and minimum integer values
        // Appelle une méthode
        long hashMax = hashBlockCoord(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        // Appelle une méthode
        long hashMin = hashBlockCoord(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
        // Appelle une méthode
        long hashMixed = hashBlockCoord(Integer.MAX_VALUE, Integer.MIN_VALUE, 0);

        // Should not throw exceptions
        // Appelle une méthode
        assertNotEquals(0L, hashMax);
        // Appelle une méthode
        assertNotEquals(0L, hashMin);
        // Appelle une méthode
        assertNotEquals(0L, hashMixed);

        // All should be different
        // Appelle une méthode
        assertNotEquals(hashMax, hashMin);
        // Appelle une méthode
        assertNotEquals(hashMax, hashMixed);
        // Appelle une méthode
        assertNotEquals(hashMin, hashMixed);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Annotation pour l'élément suivant
    @DisplayName("hashGlobalCoord - Edge cases and extreme values")
    // Début d'une méthode/d'un bloc
    public void testhashGlobalCoordEdgeCases() {
        // Test with special double values
        // Appelle une méthode
        long hashZero = hashGlobalCoord(0.0, 0.0, 0.0);
        // Appelle une méthode
        long hashNegZero = hashGlobalCoord(-0.0, -0.0, -0.0);
        // Appelle une méthode
        long hashInf = hashGlobalCoord(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NaN);
        // Appelle une méthode
        long hashMax = hashGlobalCoord(Double.MAX_VALUE, Double.MIN_VALUE, Double.MIN_NORMAL);

        // Should not throw exceptions
        // Appelle une méthode
        assertNotEquals(0L, hashZero);
        // Appelle une méthode
        assertNotEquals(0L, hashNegZero);
        // Appelle une méthode
        assertNotEquals(0L, hashInf);
        // Appelle une méthode
        assertNotEquals(0L, hashMax);

        // Special case: +0.0 and -0.0 should hash differently due to IEEE-754 bit patterns
        // Appelle une méthode
        assertNotEquals(hashZero, hashNegZero);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Annotation pour l'élément suivant
    @DisplayName("hashBlockCoord - Sensitivity to single coordinate changes")
    // Début d'une méthode/d'un bloc
    public void testhashBlockCoordSensitivity() {
        // Appelle une méthode
        long baseHash = hashBlockCoord(100, 200, 300);

        // Small changes in each coordinate should produce different hashes
        // Appelle une méthode
        long xChange = hashBlockCoord(101, 200, 300);
        // Appelle une méthode
        long yChange = hashBlockCoord(100, 201, 300);
        // Appelle une méthode
        long zChange = hashBlockCoord(100, 200, 301);

        // Appelle une méthode
        assertNotEquals(baseHash, xChange);
        // Appelle une méthode
        assertNotEquals(baseHash, yChange);
        // Appelle une méthode
        assertNotEquals(baseHash, zChange);

        // All changes should be different from each other
        // Appelle une méthode
        assertNotEquals(xChange, yChange);
        // Appelle une méthode
        assertNotEquals(xChange, zChange);
        // Appelle une méthode
        assertNotEquals(yChange, zChange);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Annotation pour l'élément suivant
    @DisplayName("hashGlobalCoord - Sensitivity to small coordinate changes")
    // Début d'une méthode/d'un bloc
    public void testhashGlobalCoordSensitivity() {
        // Appelle une méthode
        long baseHash = hashGlobalCoord(100.0, 200.0, 300.0);

        // Small changes in each coordinate should produce different hashes
        // Appelle une méthode
        long xChange = hashGlobalCoord(100.000001, 200.0, 300.0);
        // Appelle une méthode
        long yChange = hashGlobalCoord(100.0, 200.000001, 300.0);
        // Appelle une méthode
        long zChange = hashGlobalCoord(100.0, 200.0, 300.000001);

        // Appelle une méthode
        assertNotEquals(baseHash, xChange);
        // Appelle une méthode
        assertNotEquals(baseHash, yChange);
        // Appelle une méthode
        assertNotEquals(baseHash, zChange);

        // All changes should be different from each other
        // Appelle une méthode
        assertNotEquals(xChange, yChange);
        // Appelle une méthode
        assertNotEquals(xChange, zChange);
        // Appelle une méthode
        assertNotEquals(yChange, zChange);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Annotation pour l'élément suivant
    @DisplayName("hashBlockCoord - Coordinate order independence test")
    // Début d'une méthode/d'un bloc
    public void testhashBlockCoordOrderIndependence() {
        // Different permutations should produce different hashes
        // Appelle une méthode
        long hash123 = hashBlockCoord(1, 2, 3);
        // Appelle une méthode
        long hash132 = hashBlockCoord(1, 3, 2);
        // Appelle une méthode
        long hash213 = hashBlockCoord(2, 1, 3);
        // Appelle une méthode
        long hash231 = hashBlockCoord(2, 3, 1);
        // Appelle une méthode
        long hash312 = hashBlockCoord(3, 1, 2);
        // Appelle une méthode
        long hash321 = hashBlockCoord(3, 2, 1);

        // Appelle une méthode
        Set<Long> hashes = Set.of(hash123, hash132, hash213, hash231, hash312, hash321);
        // Appelle une méthode
        assertEquals(6, hashes.size(), "All permutations should produce different hashes");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Annotation pour l'élément suivant
    @DisplayName("hashGlobalCoord - Coordinate order independence test")
    // Début d'une méthode/d'un bloc
    public void testhashGlobalCoordOrderIndependence() {
        // Different permutations should produce different hashes
        // Appelle une méthode
        long hash123 = hashGlobalCoord(1.0, 2.0, 3.0);
        // Appelle une méthode
        long hash132 = hashGlobalCoord(1.0, 3.0, 2.0);
        // Appelle une méthode
        long hash213 = hashGlobalCoord(2.0, 1.0, 3.0);
        // Appelle une méthode
        long hash231 = hashGlobalCoord(2.0, 3.0, 1.0);
        // Appelle une méthode
        long hash312 = hashGlobalCoord(3.0, 1.0, 2.0);
        // Appelle une méthode
        long hash321 = hashGlobalCoord(3.0, 2.0, 1.0);

        // Appelle une méthode
        Set<Long> hashes = Set.of(hash123, hash132, hash213, hash231, hash312, hash321);
        // Appelle une méthode
        assertEquals(6, hashes.size(), "All permutations should produce different hashes");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Annotation pour l'élément suivant
    @DisplayName("hashBlockCoord - Collision resistance test")
    // Début d'une méthode/d'un bloc
    public void testhashBlockCoordCollisions() {
        // Appelle une méthode
        Set<Long> hashes = new HashSet<>();
        // Affecte une valeur
        int collisions = 0;

        // Test with a large number of sequential coordinates
        // Boucle : répète un bloc
        for (int i = -1000; i < 1000; i++) {
            // Boucle : répète un bloc
            for (int j = -1000; j < 1000; j++) {
                // Boucle : répète un bloc
                for (int k = -1000; k < 1000; k++) {
                    // Appelle une méthode
                    long hash = hashBlockCoord(i, j, k);
                    // Embranchement : vérifie une condition
                    if (!hashes.add(hash)) {
                        // Instruction de code
                        collisions++;
                    // Fin d'un bloc/d'une expression
                    }
                    // Embranchement : vérifie une condition
                    if (hashes.size() > COLLISION_TEST_SIZE) break;
                // Fin d'un bloc/d'une expression
                }
                // Embranchement : vérifie une condition
                if (hashes.size() > COLLISION_TEST_SIZE) break;
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (hashes.size() > COLLISION_TEST_SIZE) break;
        // Fin d'un bloc/d'une expression
        }

        // Collision rate should be very low (< 0.1%)
        // Appelle une méthode
        double collisionRate = (double) collisions / hashes.size();
        // Instruction de code
        assertTrue(collisionRate < 0.001,
                // Appelle une méthode
                String.format("Collision rate too high: %.4f%% (expected < 0.1%%)", collisionRate * 100));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Annotation pour l'élément suivant
    @DisplayName("hashGlobalCoord - Collision resistance test")
    // Début d'une méthode/d'un bloc
    public void testhashGlobalCoordCollisions() {
        // Appelle une méthode
        Set<Long> hashes = new HashSet<>();
        // Affecte une valeur
        int collisions = 0;
        // Affecte une valeur
        Random random = new Random(42); // Fixed seed for reproducibility

        // Test with random coordinates
        // Boucle : répète un bloc
        for (int i = 0; i < COLLISION_TEST_SIZE; i++) {
            // Appelle une méthode
            double x = random.nextGaussian() * 1000;
            // Appelle une méthode
            double y = random.nextGaussian() * 1000;
            // Appelle une méthode
            double z = random.nextGaussian() * 1000;

            // Appelle une méthode
            long hash = hashGlobalCoord(x, y, z);
            // Embranchement : vérifie une condition
            if (!hashes.add(hash)) {
                // Instruction de code
                collisions++;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Collision rate should be very low (< 0.1%)
        // Appelle une méthode
        double collisionRate = (double) collisions / hashes.size();
        // Instruction de code
        assertTrue(collisionRate < 0.001,
                // Appelle une méthode
                String.format("Collision rate too high: %.4f%% (expected < 0.1%%)", collisionRate * 100));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Annotation pour l'élément suivant
    @DisplayName("hashBlockCoord - Distribution uniformity test")
    // Début d'une méthode/d'un bloc
    public void testhashBlockCoordDistribution() {
        // Affecte une valeur
        int[] buckets = new int[DISTRIBUTION_BINS];

        // Generate hashes and distribute into buckets
        // Boucle : répète un bloc
        for (int i = 0; i < COLLISION_TEST_SIZE; i++) {
            // Boucle : répète un bloc
            for (int j = 0; j < 10; j++) {
                // Boucle : répète un bloc
                for (int k = 0; k < 10; k++) {
                    // Appelle une méthode
                    long hash = hashBlockCoord(i, j, k);
                    // Appelle une méthode
                    int bucket = (int) (Math.abs(hash) % DISTRIBUTION_BINS);
                    // Instruction de code
                    buckets[bucket]++;
                    // Embranchement : vérifie une condition
                    if (i * 100 + j * 10 + k >= COLLISION_TEST_SIZE) return;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Check for reasonable distribution
        // Affecte une valeur
        int expected = COLLISION_TEST_SIZE / DISTRIBUTION_BINS;
        // Appelle une méthode
        int tolerance = (int) (expected * DISTRIBUTION_TOLERANCE);

        // Boucle : répète un bloc
        for (int i = 0; i < DISTRIBUTION_BINS; i++) {
            // Instruction de code
            assertTrue(Math.abs(buckets[i] - expected) <= tolerance,
                    // Appelle une méthode
                    String.format("Bucket %d has %d items, expected %d ± %d", i, buckets[i], expected, tolerance));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @ValueSource(ints = {-1000000, -1000, -1, 1, 1000, 1000000})
    // Annotation pour l'élément suivant
    @DisplayName("hashBlockCoord - Parameterized boundary tests")
    // Début d'une méthode/d'un bloc
    public void testhashBlockCoordBoundaries(int value) {
        // Test various boundary values
        // Appelle une méthode
        long hash = hashBlockCoord(value, value, value);
        // Appelle une méthode
        assertNotEquals(0L, hash, "Hash should not be zero for input: " + value);

        // Test with mixed signs
        // Appelle une méthode
        long hashMixed = hashBlockCoord(value, -value, value);
        // Appelle une méthode
        assertNotEquals(hash, hashMixed, "Mixed signs should produce different hash: " + value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @CsvSource({
            // Instruction de code
            "0.0, 0.0, 0.0",
            // Instruction de code
            "1.0, 1.0, 1.0",
            // Instruction de code
            "-1.0, -1.0, -1.0",
            // Instruction de code
            "3.14159, 2.71828, 1.41421",
            // Instruction de code
            "1e-10, 1e10, 1e-100",
            // Instruction de code
            "0.000001, 0.000002, 0.000003"
    // Instruction de code
    })
    // Annotation pour l'élément suivant
    @DisplayName("hashGlobalCoord - Parameterized precision tests")
    // Début d'une méthode/d'un bloc
    public void testhashGlobalCoordPrecision(double x, double y, double z) {
        // Appelle une méthode
        long hash = hashGlobalCoord(x, y, z);
        // Appelle une méthode
        assertNotEquals(0L, hash, String.format("Hash should not be zero for input: (%.10f, %.10f, %.10f)", x, y, z));

        // Test consistency
        // Appelle une méthode
        assertEquals(hash, hashGlobalCoord(x, y, z), "Hash should be consistent");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Annotation pour l'élément suivant
    @DisplayName("hashBlockCoord - Avalanche effect test")
    // Début d'une méthode/d'un bloc
    public void testhashBlockCoordAvalanche() {
        // Test that flipping a single bit in input changes approximately half the output bits
        // Appelle une méthode
        long hash1 = hashBlockCoord(0, 0, 0);
        // Affecte une valeur
        long hash2 = hashBlockCoord(1, 0, 0); // Flip lowest bit of x

        // Appelle une méthode
        int differentBits = Long.bitCount(hash1 ^ hash2);

        // Should change approximately 32 bits (50% of 64 bits)
        // Instruction de code
        assertTrue(differentBits >= 20 && differentBits <= 44,
                // Appelle une méthode
                String.format("Avalanche effect insufficient: %d bits changed (expected 20-44)", differentBits));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Annotation pour l'élément suivant
    @DisplayName("hashGlobalCoord - Avalanche effect test")
    // Début d'une méthode/d'un bloc
    public void testhashGlobalCoordAvalanche() {
        // Test that small changes in input produce large changes in output
        // Appelle une méthode
        long hash1 = hashGlobalCoord(1.0, 1.0, 1.0);
        // Affecte une valeur
        long hash2 = hashGlobalCoord(1.0000000000000002, 1.0, 1.0); // Smallest possible change

        // Appelle une méthode
        int differentBits = Long.bitCount(hash1 ^ hash2);

        // Should change approximately 32 bits (50% of 64 bits)
        // Instruction de code
        assertTrue(differentBits >= 20 && differentBits <= 44,
                // Appelle une méthode
                String.format("Avalanche effect insufficient: %d bits changed (expected 20-44)", differentBits));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @RepeatedTest(100)
    // Annotation pour l'élément suivant
    @DisplayName("hashBlockCoord - Randomized stress test")
    // Début d'une méthode/d'un bloc
    public void testhashBlockCoordRandomized() {
        // Appelle une méthode
        Random random = new Random();

        // Appelle une méthode
        int x = random.nextInt();
        // Appelle une méthode
        int y = random.nextInt();
        // Appelle une méthode
        int z = random.nextInt();

        // Appelle une méthode
        long hash = hashBlockCoord(x, y, z);

        // Basic sanity checks
        // Appelle une méthode
        assertNotEquals(0L, hash, "Hash should not be zero");
        // Appelle une méthode
        assertEquals(hash, hashBlockCoord(x, y, z), "Hash should be deterministic");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @RepeatedTest(100)
    // Annotation pour l'élément suivant
    @DisplayName("hashGlobalCoord - Randomized stress test")
    // Début d'une méthode/d'un bloc
    public void testhashGlobalCoordRandomized() {
        // Appelle une méthode
        Random random = new Random();

        // Appelle une méthode
        double x = random.nextGaussian() * 1e10;
        // Appelle une méthode
        double y = random.nextGaussian() * 1e10;
        // Appelle une méthode
        double z = random.nextGaussian() * 1e10;

        // Appelle une méthode
        long hash = hashGlobalCoord(x, y, z);

        // Basic sanity checks
        // Appelle une méthode
        assertNotEquals(0L, hash, "Hash should not be zero");
        // Appelle une méthode
        assertEquals(hash, hashGlobalCoord(x, y, z), "Hash should be deterministic");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
