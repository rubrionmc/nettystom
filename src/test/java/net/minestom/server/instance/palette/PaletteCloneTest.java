// Package declaration for this file
package net.minestom.server.instance.palette;

// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Random;

// Static import of a member
import static net.minestom.server.instance.palette.PaletteAssertions.assertAllEquals;
// Static import of a member
import static net.minestom.server.instance.palette.PaletteAssertions.testPalettes;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for Palette clone functionality.
 * Tests cloning behavior, independence of cloned palettes, resizing effects, and data integrity.
 */
// Type declaration (class/interface/enum/record)
public class PaletteCloneTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void basicClone() {
        // Calls a method
        var palettes = testPalettes();
        // Loop: repeats a block
        for (Palette original : palettes) {
            // Set some initial values
            // Calls a method
            original.set(0, 0, 0, 42);
            // Calls a method
            original.set(1, 1, 1, 84);

            // Calls a method
            Palette cloned = original.clone();

            // Verify clone has same values
            // Calls a method
            assertEquals(original.get(0, 0, 0), cloned.get(0, 0, 0));
            // Calls a method
            assertEquals(original.get(1, 1, 1), cloned.get(1, 1, 1));
            // Calls a method
            assertEquals(original.count(), cloned.count());
            // Calls a method
            assertEquals(original.dimension(), cloned.dimension());
            // Calls a method
            assertEquals(original.bitsPerEntry(), cloned.bitsPerEntry());

            // Verify compare method works
            // Calls a method
            assertTrue(original.compare(cloned));
            // Calls a method
            assertTrue(cloned.compare(original));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cloneIndependence() {
        // Calls a method
        var palettes = testPalettes();
        // Loop: repeats a block
        for (Palette original : palettes) {
            // Fill original with pattern
            // Calls a method
            original.setAll((x, y, z) -> x + y * 10 + z * 100);

            // Calls a method
            Palette cloned = original.clone();

            // Modify original
            // Calls a method
            original.set(0, 0, 0, 999);
            // Calls a method
            original.set(1, 0, 0, 888);

            // Verify clone is unaffected
            // Assigns a value
            assertEquals(0, cloned.get(0, 0, 0)); // x=0, y=0, z=0: 0 + 0*10 + 0*100 = 0
            // Assigns a value
            assertEquals(1, cloned.get(1, 0, 0)); // x=1, y=0, z=0: 1 + 0*10 + 0*100 = 1

            // Verify original was changed
            // Calls a method
            assertEquals(999, original.get(0, 0, 0));
            // Calls a method
            assertEquals(888, original.get(1, 0, 0));

            // Branch: checks a condition
            if (cloned.dimension() > 2) {
                // Modify clone
                // Calls a method
                cloned.set(2, 2, 2, 777);

                // Verify original is unaffected by clone modification
                // Assigns a value
                int expected = 2 + 2 * 10 + 2 * 100; // Should be 222
                // Calls a method
                assertEquals(expected, original.get(2, 2, 2));
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cloneEmptyPalette() {
        // Calls a method
        var palettes = testPalettes();
        // Loop: repeats a block
        for (Palette original : palettes) {
            // Clone empty palette
            // Calls a method
            Palette cloned = original.clone();

            // Calls a method
            assertEquals(0, cloned.count());
            // Calls a method
            assertEquals(original.dimension(), cloned.dimension());
            // Calls a method
            assertTrue(original.compare(cloned));

            // Calls a method
            assertAllEquals(0, cloned);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cloneFullPalette() {
        // Calls a method
        var palettes = testPalettes();
        // Loop: repeats a block
        for (Palette original : palettes) {
            // Fill entire palette
            // Calls a method
            original.fill(123);

            // Calls a method
            Palette cloned = original.clone();

            // Calls a method
            assertEquals(original.count(), cloned.count());
            // Calls a method
            assertEquals(original.maxSize(), cloned.count());
            // Calls a method
            assertTrue(original.compare(cloned));

            // Calls a method
            assertAllEquals(123, cloned);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cloneWithPatternData() {
        // Calls a method
        var palettes = testPalettes();
        // Loop: repeats a block
        for (Palette original : palettes) {
            // Create complex pattern
            // Start of a method/block
            original.setAll((x, y, z) -> {
                // Assigns a value
                int value = x * 1000 + y * 100 + z * 10;
                // Returns a value to the caller
                return Math.abs(value) % 65536; // Keep within reasonable range
            // End of a block/expression
            });

            // Calls a method
            Palette cloned = original.clone();

            // Calls a method
            assertEquals(original.count(), cloned.count());
            // Calls a method
            assertTrue(original.compare(cloned));

            // Verify pattern is preserved
            // Start of a method/block
            cloned.getAll((x, y, z, value) -> {
                // Calls a method
                int expected = Math.abs(x * 1000 + y * 100 + z * 10) % 65536;
                // Code statement
                assertEquals(expected, value,
                        // Calls a method
                        String.format("Mismatch at (%d,%d,%d)", x, y, z));
            // End of a block/expression
            });
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cloneAfterResize() {
        // Calls a method
        Palette original = Palette.blocks();
        // Fill with initial data to force one storage type
        // Loop: repeats a block
        for (int i = 0; i < 10; i++) {
            // Calls a method
            original.set(i % original.dimension(), 0, 0, i + 1);
        // End of a block/expression
        }
        // Loop: repeats a block
        for (int i = 0; i < 10; i++) {
            // Calls a method
            assertEquals(i + 1, original.get(i % original.dimension(), 0, 0));
        // End of a block/expression
        }

        // Calls a method
        int initialDimension = original.dimension();
        // Calls a method
        int initialBitsPerEntry = original.bitsPerEntry();
        // Calls a method
        int initialCount = original.count();

        // Calls a method
        Palette cloned = original.clone();
        // Verify basic properties
        // Calls a method
        assertEquals(initialDimension, cloned.dimension());
        // Calls a method
        assertEquals(initialBitsPerEntry, cloned.bitsPerEntry());
        // Calls a method
        assertEquals(initialCount, cloned.count());
        // Calls a method
        assertTrue(original.compare(cloned));
        // Loop: repeats a block
        for (int i = 0; i < 10; i++) {
            // Calls a method
            assertEquals(i + 1, cloned.get(i % cloned.dimension(), 0, 0));
        // End of a block/expression
        }

        // Now force resize by adding many unique values to original
        // Assigns a value
        Random random = new Random(42); // Deterministic
        // Loop: repeats a block
        for (int i = 0; i < original.maxSize() / 2; i++) {
            // Calls a method
            int x = random.nextInt(original.dimension());
            // Calls a method
            int y = random.nextInt(original.dimension());
            // Calls a method
            int z = random.nextInt(original.dimension());
            // Code statement
            original.set(x, y, z, 1000 + i); // Unique large values
        // End of a block/expression
        }

        // Verify original may have resized
        // (bitsPerEntry might have increased)

        // Verify clone still has original data and hasn't been affected
        // Calls a method
        assertEquals(initialBitsPerEntry, cloned.bitsPerEntry());
        // Calls a method
        assertEquals(initialCount, cloned.count());

        // Verify clone still has original values
        // Loop: repeats a block
        for (int i = 0; i < 10; i++) {
            // Calls a method
            assertEquals(i + 1, cloned.get(i % cloned.dimension(), 0, 0));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cloneAndModifyBothDirections() {
        // Calls a method
        var palettes = testPalettes();
        // Loop: repeats a block
        for (Palette original : palettes) {
            // Set initial pattern
            // Calls a method
            original.setAll((x, y, z) -> (x + y + z) % 256);

            // Calls a method
            Palette cloned = original.clone();
            // Calls a method
            assertTrue(original.compare(cloned));

            // Modify original extensively
            // Calls a method
            original.fill(500);

            // Modify clone extensively
            // Calls a method
            cloned.fill(600);

            // Verify they're completely independent
            // Calls a method
            assertFalse(original.compare(cloned));

            // Calls a method
            assertAllEquals(500, original);
            // Calls a method
            assertAllEquals(600, cloned);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cloneWithOffset() {
        // Calls a method
        var palettes = testPalettes();
        // Loop: repeats a block
        for (Palette original : palettes) {
            // Set pattern and apply offset
            // Calls a method
            original.setAll((x, y, z) -> x + y + z + 100);
            // Calls a method
            original.offset(50);

            // Calls a method
            Palette cloned = original.clone();

            // Calls a method
            assertTrue(original.compare(cloned));

            // Verify offset was preserved in clone
            // Start of a method/block
            cloned.getAll((x, y, z, value) -> {
                // Assigns a value
                int expected = x + y + z + 100 + 50;
                // Calls a method
                assertEquals(expected, value);
            // End of a block/expression
            });

            // Apply different offset to original
            // Calls a method
            original.offset(-25);

            // Verify clone is unaffected
            // Start of a method/block
            cloned.getAll((x, y, z, value) -> {
                // Assigns a value
                int expected = x + y + z + 100 + 50;
                // Calls a method
                assertEquals(expected, value);
            // End of a block/expression
            });
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cloneWithReplace() {
        // Calls a method
        var palettes = testPalettes();
        // Loop: repeats a block
        for (Palette original : palettes) {
            // Set initial values
            // Calls a method
            original.setAll((x, y, z) -> x + y + z);

            // Calls a method
            Palette cloned = original.clone();

            // Apply replace operation to original
            // Calls a method
            original.replaceAll((x, y, z, value) -> value * 2);

            // Verify clone is unaffected
            // Calls a method
            cloned.getAll((x, y, z, value) -> assertEquals(x + y + z, value));

            // Apply different replace to clone
            // Calls a method
            cloned.replaceAll((x, y, z, value) -> value + 1000);

            // Verify both have correct values
            // Calls a method
            original.getAll((x, y, z, value) -> assertEquals((x + y + z) * 2, value));

            // Calls a method
            cloned.getAll((x, y, z, value) -> assertEquals(x + y + z + 1000, value));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void multipleClonesIndependence() {
        // Calls a method
        var palettes = testPalettes();
        // Loop: repeats a block
        for (Palette original : palettes) {
            // Calls a method
            original.setAll((x, y, z) -> x * 100 + y * 10 + z);

            // Create multiple clones
            // Calls a method
            Palette clone1 = original.clone();
            // Calls a method
            Palette clone2 = original.clone();
            // Calls a method
            Palette clone3 = original.clone();

            // Verify all are equal initially
            // Calls a method
            assertTrue(original.compare(clone1));
            // Calls a method
            assertTrue(original.compare(clone2));
            // Calls a method
            assertTrue(original.compare(clone3));
            // Calls a method
            assertTrue(clone1.compare(clone2));

            // Modify each differently
            // Calls a method
            original.fill(1);
            // Calls a method
            clone1.fill(2);
            // Calls a method
            clone2.fill(3);
            // Calls a method
            clone3.fill(4);

            // Verify all are different
            // Calls a method
            assertFalse(original.compare(clone1));
            // Calls a method
            assertFalse(original.compare(clone2));
            // Calls a method
            assertFalse(clone1.compare(clone2));
            // Calls a method
            assertFalse(clone2.compare(clone3));

            // Verify each has correct values
            // Calls a method
            assertEquals(original.maxSize(), original.count());
            // Calls a method
            original.getAll((x, y, z, value) -> assertEquals(1, value));

            // Calls a method
            clone1.getAll((x, y, z, value) -> assertEquals(2, value));
            // Calls a method
            clone2.getAll((x, y, z, value) -> assertEquals(3, value));
            // Calls a method
            clone3.getAll((x, y, z, value) -> assertEquals(4, value));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cloneOptimization() {
        // Calls a method
        var palettes = testPalettes();
        // Loop: repeats a block
        for (Palette original : palettes) {
            // Create sparse data
            // Calls a method
            original.set(0, 0, 0, 100);
            // Calls a method
            original.set(original.dimension() - 1, original.dimension() - 1, original.dimension() - 1, 200);

            // Calls a method
            Palette cloned = original.clone();

            // Apply optimization to original
            // Calls a method
            original.optimize(Palette.Optimization.SIZE);

            // Verify clone is unaffected by optimization
            // Calls a method
            assertTrue(original.compare(cloned));
            // Calls a method
            assertEquals(100, cloned.get(0, 0, 0));
            // Calls a method
            assertEquals(200, cloned.get(original.dimension() - 1, original.dimension() - 1, original.dimension() - 1));

            // Apply different optimization to clone
            // Calls a method
            cloned.optimize(Palette.Optimization.SPEED);

            // Both should still have same data despite different optimizations
            // Calls a method
            assertTrue(original.compare(cloned));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cloneDifferentPaletteTypes() {
        // Test blocks vs biomes vs custom sized palettes
        // Calls a method
        Palette blockPalette = Palette.blocks();
        // Calls a method
        Palette biomePalette = Palette.biomes();
        // Calls a method
        Palette customPalette = Palette.sized(8, 2, 6, 12, 4);

        // Calls a method
        List<Palette> palettes = List.of(blockPalette, biomePalette, customPalette);

        // Loop: repeats a block
        for (Palette original : palettes) {
            // Calls a method
            original.setAll((x, y, z) -> (x + y + z) % 100);

            // Calls a method
            Palette cloned = original.clone();

            // Calls a method
            assertEquals(original.dimension(), cloned.dimension());
            // Calls a method
            assertEquals(original.bitsPerEntry(), cloned.bitsPerEntry());
            // Calls a method
            assertEquals(original.count(), cloned.count());
            // Calls a method
            assertTrue(original.compare(cloned));

            // Verify independence
            // Calls a method
            original.set(0, 0, 0, 999);
            // Calls a method
            assertNotEquals(999, cloned.get(0, 0, 0));
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
