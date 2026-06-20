// Package declaration for this file
package net.minestom.server.coordinate;

// Import of a required class
import org.junit.jupiter.api.DisplayName;
// Import of a required class
import org.junit.jupiter.api.RepeatedTest;
// Import of a required class
import org.junit.jupiter.api.Test;
// Import of a required class
import org.junit.jupiter.params.ParameterizedTest;
// Import of a required class
import org.junit.jupiter.params.provider.CsvSource;
// Import of a required class
import org.junit.jupiter.params.provider.ValueSource;

// Import of a required class
import java.util.HashSet;
// Import of a required class
import java.util.Random;
// Import of a required class
import java.util.Set;

// Static import of a member
import static net.minestom.server.coordinate.CoordConversion.hashBlockCoord;
// Static import of a member
import static net.minestom.server.coordinate.CoordConversion.hashGlobalCoord;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class CoordinateHashTest {

    // Test constants
    // Assigns a value
    private static final int COLLISION_TEST_SIZE = 100000;
    // Assigns a value
    private static final int DISTRIBUTION_BINS = 1000;
    // Assigns a value
    private static final double DISTRIBUTION_TOLERANCE = 0.05; // 5% tolerance for uniform distribution

    // Annotation for the following element
    @Test
    // Annotation for the following element
    @DisplayName("hashBlockCoord - Basic functionality test")
    // Start of a method/block
    public void testhashBlockCoordBasic() {
        // Test basic functionality
        // Calls a method
        long hash1 = hashBlockCoord(0, 0, 0);
        // Calls a method
        long hash2 = hashBlockCoord(1, 1, 1);
        // Calls a method
        long hash3 = hashBlockCoord(-1, -1, -1);

        // Hash should be deterministic
        // Calls a method
        assertEquals(hash1, hashBlockCoord(0, 0, 0));
        // Calls a method
        assertEquals(hash2, hashBlockCoord(1, 1, 1));
        // Calls a method
        assertEquals(hash3, hashBlockCoord(-1, -1, -1));

        // Different inputs should produce different hashes (with high probability)
        // Calls a method
        assertNotEquals(hash1, hash2);
        // Calls a method
        assertNotEquals(hash1, hash3);
        // Calls a method
        assertNotEquals(hash2, hash3);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Annotation for the following element
    @DisplayName("hashGlobalCoord - Basic functionality test")
    // Start of a method/block
    public void testhashGlobalCoordBasic() {
        // Test basic functionality
        // Calls a method
        long hash1 = hashGlobalCoord(0.0, 0.0, 0.0);
        // Calls a method
        long hash2 = hashGlobalCoord(1.0, 1.0, 1.0);
        // Calls a method
        long hash3 = hashGlobalCoord(-1.0, -1.0, -1.0);

        // Hash should be deterministic
        // Calls a method
        assertEquals(hash1, hashGlobalCoord(0.0, 0.0, 0.0));
        // Calls a method
        assertEquals(hash2, hashGlobalCoord(1.0, 1.0, 1.0));
        // Calls a method
        assertEquals(hash3, hashGlobalCoord(-1.0, -1.0, -1.0));

        // Different inputs should produce different hashes (with high probability)
        // Calls a method
        assertNotEquals(hash1, hash2);
        // Calls a method
        assertNotEquals(hash1, hash3);
        // Calls a method
        assertNotEquals(hash2, hash3);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Annotation for the following element
    @DisplayName("hashBlockCoord - Edge cases and extreme values")
    // Start of a method/block
    public void testhashBlockCoordEdgeCases() {
        // Test with maximum and minimum integer values
        // Calls a method
        long hashMax = hashBlockCoord(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        // Calls a method
        long hashMin = hashBlockCoord(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
        // Calls a method
        long hashMixed = hashBlockCoord(Integer.MAX_VALUE, Integer.MIN_VALUE, 0);

        // Should not throw exceptions
        // Calls a method
        assertNotEquals(0L, hashMax);
        // Calls a method
        assertNotEquals(0L, hashMin);
        // Calls a method
        assertNotEquals(0L, hashMixed);

        // All should be different
        // Calls a method
        assertNotEquals(hashMax, hashMin);
        // Calls a method
        assertNotEquals(hashMax, hashMixed);
        // Calls a method
        assertNotEquals(hashMin, hashMixed);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Annotation for the following element
    @DisplayName("hashGlobalCoord - Edge cases and extreme values")
    // Start of a method/block
    public void testhashGlobalCoordEdgeCases() {
        // Test with special double values
        // Calls a method
        long hashZero = hashGlobalCoord(0.0, 0.0, 0.0);
        // Calls a method
        long hashNegZero = hashGlobalCoord(-0.0, -0.0, -0.0);
        // Calls a method
        long hashInf = hashGlobalCoord(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NaN);
        // Calls a method
        long hashMax = hashGlobalCoord(Double.MAX_VALUE, Double.MIN_VALUE, Double.MIN_NORMAL);

        // Should not throw exceptions
        // Calls a method
        assertNotEquals(0L, hashZero);
        // Calls a method
        assertNotEquals(0L, hashNegZero);
        // Calls a method
        assertNotEquals(0L, hashInf);
        // Calls a method
        assertNotEquals(0L, hashMax);

        // Special case: +0.0 and -0.0 should hash differently due to IEEE-754 bit patterns
        // Calls a method
        assertNotEquals(hashZero, hashNegZero);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Annotation for the following element
    @DisplayName("hashBlockCoord - Sensitivity to single coordinate changes")
    // Start of a method/block
    public void testhashBlockCoordSensitivity() {
        // Calls a method
        long baseHash = hashBlockCoord(100, 200, 300);

        // Small changes in each coordinate should produce different hashes
        // Calls a method
        long xChange = hashBlockCoord(101, 200, 300);
        // Calls a method
        long yChange = hashBlockCoord(100, 201, 300);
        // Calls a method
        long zChange = hashBlockCoord(100, 200, 301);

        // Calls a method
        assertNotEquals(baseHash, xChange);
        // Calls a method
        assertNotEquals(baseHash, yChange);
        // Calls a method
        assertNotEquals(baseHash, zChange);

        // All changes should be different from each other
        // Calls a method
        assertNotEquals(xChange, yChange);
        // Calls a method
        assertNotEquals(xChange, zChange);
        // Calls a method
        assertNotEquals(yChange, zChange);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Annotation for the following element
    @DisplayName("hashGlobalCoord - Sensitivity to small coordinate changes")
    // Start of a method/block
    public void testhashGlobalCoordSensitivity() {
        // Calls a method
        long baseHash = hashGlobalCoord(100.0, 200.0, 300.0);

        // Small changes in each coordinate should produce different hashes
        // Calls a method
        long xChange = hashGlobalCoord(100.000001, 200.0, 300.0);
        // Calls a method
        long yChange = hashGlobalCoord(100.0, 200.000001, 300.0);
        // Calls a method
        long zChange = hashGlobalCoord(100.0, 200.0, 300.000001);

        // Calls a method
        assertNotEquals(baseHash, xChange);
        // Calls a method
        assertNotEquals(baseHash, yChange);
        // Calls a method
        assertNotEquals(baseHash, zChange);

        // All changes should be different from each other
        // Calls a method
        assertNotEquals(xChange, yChange);
        // Calls a method
        assertNotEquals(xChange, zChange);
        // Calls a method
        assertNotEquals(yChange, zChange);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Annotation for the following element
    @DisplayName("hashBlockCoord - Coordinate order independence test")
    // Start of a method/block
    public void testhashBlockCoordOrderIndependence() {
        // Different permutations should produce different hashes
        // Calls a method
        long hash123 = hashBlockCoord(1, 2, 3);
        // Calls a method
        long hash132 = hashBlockCoord(1, 3, 2);
        // Calls a method
        long hash213 = hashBlockCoord(2, 1, 3);
        // Calls a method
        long hash231 = hashBlockCoord(2, 3, 1);
        // Calls a method
        long hash312 = hashBlockCoord(3, 1, 2);
        // Calls a method
        long hash321 = hashBlockCoord(3, 2, 1);

        // Calls a method
        Set<Long> hashes = Set.of(hash123, hash132, hash213, hash231, hash312, hash321);
        // Calls a method
        assertEquals(6, hashes.size(), "All permutations should produce different hashes");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Annotation for the following element
    @DisplayName("hashGlobalCoord - Coordinate order independence test")
    // Start of a method/block
    public void testhashGlobalCoordOrderIndependence() {
        // Different permutations should produce different hashes
        // Calls a method
        long hash123 = hashGlobalCoord(1.0, 2.0, 3.0);
        // Calls a method
        long hash132 = hashGlobalCoord(1.0, 3.0, 2.0);
        // Calls a method
        long hash213 = hashGlobalCoord(2.0, 1.0, 3.0);
        // Calls a method
        long hash231 = hashGlobalCoord(2.0, 3.0, 1.0);
        // Calls a method
        long hash312 = hashGlobalCoord(3.0, 1.0, 2.0);
        // Calls a method
        long hash321 = hashGlobalCoord(3.0, 2.0, 1.0);

        // Calls a method
        Set<Long> hashes = Set.of(hash123, hash132, hash213, hash231, hash312, hash321);
        // Calls a method
        assertEquals(6, hashes.size(), "All permutations should produce different hashes");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Annotation for the following element
    @DisplayName("hashBlockCoord - Collision resistance test")
    // Start of a method/block
    public void testhashBlockCoordCollisions() {
        // Calls a method
        Set<Long> hashes = new HashSet<>();
        // Assigns a value
        int collisions = 0;

        // Test with a large number of sequential coordinates
        // Loop: repeats a block
        for (int i = -1000; i < 1000; i++) {
            // Loop: repeats a block
            for (int j = -1000; j < 1000; j++) {
                // Loop: repeats a block
                for (int k = -1000; k < 1000; k++) {
                    // Calls a method
                    long hash = hashBlockCoord(i, j, k);
                    // Branch: checks a condition
                    if (!hashes.add(hash)) {
                        // Code statement
                        collisions++;
                    // End of a block/expression
                    }
                    // Branch: checks a condition
                    if (hashes.size() > COLLISION_TEST_SIZE) break;
                // End of a block/expression
                }
                // Branch: checks a condition
                if (hashes.size() > COLLISION_TEST_SIZE) break;
            // End of a block/expression
            }
            // Branch: checks a condition
            if (hashes.size() > COLLISION_TEST_SIZE) break;
        // End of a block/expression
        }

        // Collision rate should be very low (< 0.1%)
        // Calls a method
        double collisionRate = (double) collisions / hashes.size();
        // Code statement
        assertTrue(collisionRate < 0.001,
                // Calls a method
                String.format("Collision rate too high: %.4f%% (expected < 0.1%%)", collisionRate * 100));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Annotation for the following element
    @DisplayName("hashGlobalCoord - Collision resistance test")
    // Start of a method/block
    public void testhashGlobalCoordCollisions() {
        // Calls a method
        Set<Long> hashes = new HashSet<>();
        // Assigns a value
        int collisions = 0;
        // Assigns a value
        Random random = new Random(42); // Fixed seed for reproducibility

        // Test with random coordinates
        // Loop: repeats a block
        for (int i = 0; i < COLLISION_TEST_SIZE; i++) {
            // Calls a method
            double x = random.nextGaussian() * 1000;
            // Calls a method
            double y = random.nextGaussian() * 1000;
            // Calls a method
            double z = random.nextGaussian() * 1000;

            // Calls a method
            long hash = hashGlobalCoord(x, y, z);
            // Branch: checks a condition
            if (!hashes.add(hash)) {
                // Code statement
                collisions++;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Collision rate should be very low (< 0.1%)
        // Calls a method
        double collisionRate = (double) collisions / hashes.size();
        // Code statement
        assertTrue(collisionRate < 0.001,
                // Calls a method
                String.format("Collision rate too high: %.4f%% (expected < 0.1%%)", collisionRate * 100));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Annotation for the following element
    @DisplayName("hashBlockCoord - Distribution uniformity test")
    // Start of a method/block
    public void testhashBlockCoordDistribution() {
        // Assigns a value
        int[] buckets = new int[DISTRIBUTION_BINS];

        // Generate hashes and distribute into buckets
        // Loop: repeats a block
        for (int i = 0; i < COLLISION_TEST_SIZE; i++) {
            // Loop: repeats a block
            for (int j = 0; j < 10; j++) {
                // Loop: repeats a block
                for (int k = 0; k < 10; k++) {
                    // Calls a method
                    long hash = hashBlockCoord(i, j, k);
                    // Calls a method
                    int bucket = (int) (Math.abs(hash) % DISTRIBUTION_BINS);
                    // Code statement
                    buckets[bucket]++;
                    // Branch: checks a condition
                    if (i * 100 + j * 10 + k >= COLLISION_TEST_SIZE) return;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Check for reasonable distribution
        // Assigns a value
        int expected = COLLISION_TEST_SIZE / DISTRIBUTION_BINS;
        // Calls a method
        int tolerance = (int) (expected * DISTRIBUTION_TOLERANCE);

        // Loop: repeats a block
        for (int i = 0; i < DISTRIBUTION_BINS; i++) {
            // Code statement
            assertTrue(Math.abs(buckets[i] - expected) <= tolerance,
                    // Calls a method
                    String.format("Bucket %d has %d items, expected %d ± %d", i, buckets[i], expected, tolerance));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @ValueSource(ints = {-1000000, -1000, -1, 1, 1000, 1000000})
    // Annotation for the following element
    @DisplayName("hashBlockCoord - Parameterized boundary tests")
    // Start of a method/block
    public void testhashBlockCoordBoundaries(int value) {
        // Test various boundary values
        // Calls a method
        long hash = hashBlockCoord(value, value, value);
        // Calls a method
        assertNotEquals(0L, hash, "Hash should not be zero for input: " + value);

        // Test with mixed signs
        // Calls a method
        long hashMixed = hashBlockCoord(value, -value, value);
        // Calls a method
        assertNotEquals(hash, hashMixed, "Mixed signs should produce different hash: " + value);
    // End of a block/expression
    }

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @CsvSource({
            // Code statement
            "0.0, 0.0, 0.0",
            // Code statement
            "1.0, 1.0, 1.0",
            // Code statement
            "-1.0, -1.0, -1.0",
            // Code statement
            "3.14159, 2.71828, 1.41421",
            // Code statement
            "1e-10, 1e10, 1e-100",
            // Code statement
            "0.000001, 0.000002, 0.000003"
    // Code statement
    })
    // Annotation for the following element
    @DisplayName("hashGlobalCoord - Parameterized precision tests")
    // Start of a method/block
    public void testhashGlobalCoordPrecision(double x, double y, double z) {
        // Calls a method
        long hash = hashGlobalCoord(x, y, z);
        // Calls a method
        assertNotEquals(0L, hash, String.format("Hash should not be zero for input: (%.10f, %.10f, %.10f)", x, y, z));

        // Test consistency
        // Calls a method
        assertEquals(hash, hashGlobalCoord(x, y, z), "Hash should be consistent");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Annotation for the following element
    @DisplayName("hashBlockCoord - Avalanche effect test")
    // Start of a method/block
    public void testhashBlockCoordAvalanche() {
        // Test that flipping a single bit in input changes approximately half the output bits
        // Calls a method
        long hash1 = hashBlockCoord(0, 0, 0);
        // Assigns a value
        long hash2 = hashBlockCoord(1, 0, 0); // Flip lowest bit of x

        // Calls a method
        int differentBits = Long.bitCount(hash1 ^ hash2);

        // Should change approximately 32 bits (50% of 64 bits)
        // Code statement
        assertTrue(differentBits >= 20 && differentBits <= 44,
                // Calls a method
                String.format("Avalanche effect insufficient: %d bits changed (expected 20-44)", differentBits));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Annotation for the following element
    @DisplayName("hashGlobalCoord - Avalanche effect test")
    // Start of a method/block
    public void testhashGlobalCoordAvalanche() {
        // Test that small changes in input produce large changes in output
        // Calls a method
        long hash1 = hashGlobalCoord(1.0, 1.0, 1.0);
        // Assigns a value
        long hash2 = hashGlobalCoord(1.0000000000000002, 1.0, 1.0); // Smallest possible change

        // Calls a method
        int differentBits = Long.bitCount(hash1 ^ hash2);

        // Should change approximately 32 bits (50% of 64 bits)
        // Code statement
        assertTrue(differentBits >= 20 && differentBits <= 44,
                // Calls a method
                String.format("Avalanche effect insufficient: %d bits changed (expected 20-44)", differentBits));
    // End of a block/expression
    }

    // Annotation for the following element
    @RepeatedTest(100)
    // Annotation for the following element
    @DisplayName("hashBlockCoord - Randomized stress test")
    // Start of a method/block
    public void testhashBlockCoordRandomized() {
        // Calls a method
        Random random = new Random();

        // Calls a method
        int x = random.nextInt();
        // Calls a method
        int y = random.nextInt();
        // Calls a method
        int z = random.nextInt();

        // Calls a method
        long hash = hashBlockCoord(x, y, z);

        // Basic sanity checks
        // Calls a method
        assertNotEquals(0L, hash, "Hash should not be zero");
        // Calls a method
        assertEquals(hash, hashBlockCoord(x, y, z), "Hash should be deterministic");
    // End of a block/expression
    }

    // Annotation for the following element
    @RepeatedTest(100)
    // Annotation for the following element
    @DisplayName("hashGlobalCoord - Randomized stress test")
    // Start of a method/block
    public void testhashGlobalCoordRandomized() {
        // Calls a method
        Random random = new Random();

        // Calls a method
        double x = random.nextGaussian() * 1e10;
        // Calls a method
        double y = random.nextGaussian() * 1e10;
        // Calls a method
        double z = random.nextGaussian() * 1e10;

        // Calls a method
        long hash = hashGlobalCoord(x, y, z);

        // Basic sanity checks
        // Calls a method
        assertNotEquals(0L, hash, "Hash should not be zero");
        // Calls a method
        assertEquals(hash, hashGlobalCoord(x, y, z), "Hash should be deterministic");
    // End of a block/expression
    }
// End of a block/expression
}
