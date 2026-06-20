// Déclaration du paquet de ce fichier
package net.minestom.server.instance.palette;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Random;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for Palette clone functionality.
 * Tests cloning behavior, independence of cloned palettes, resizing effects, and data integrity.
 */
// Déclaration de type (classe/interface/enum/record)
public class PaletteCloneTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void basicClone() {
        // Appelle une méthode
        var palettes = testPalettes();
        // Boucle : répète un bloc
        for (Palette original : palettes) {
            // Set some initial values
            // Appelle une méthode
            original.set(0, 0, 0, 42);
            // Appelle une méthode
            original.set(1, 1, 1, 84);

            // Appelle une méthode
            Palette cloned = original.clone();

            // Verify clone has same values
            // Appelle une méthode
            assertEquals(original.get(0, 0, 0), cloned.get(0, 0, 0));
            // Appelle une méthode
            assertEquals(original.get(1, 1, 1), cloned.get(1, 1, 1));
            // Appelle une méthode
            assertEquals(original.count(), cloned.count());
            // Appelle une méthode
            assertEquals(original.dimension(), cloned.dimension());
            // Appelle une méthode
            assertEquals(original.bitsPerEntry(), cloned.bitsPerEntry());

            // Verify compare method works
            // Appelle une méthode
            assertTrue(original.compare(cloned));
            // Appelle une méthode
            assertTrue(cloned.compare(original));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cloneIndependence() {
        // Appelle une méthode
        var palettes = testPalettes();
        // Boucle : répète un bloc
        for (Palette original : palettes) {
            // Fill original with pattern
            // Appelle une méthode
            original.setAll((x, y, z) -> x + y * 10 + z * 100);

            // Appelle une méthode
            Palette cloned = original.clone();

            // Modify original
            // Appelle une méthode
            original.set(0, 0, 0, 999);
            // Appelle une méthode
            original.set(1, 0, 0, 888);

            // Verify clone is unaffected
            // Affecte une valeur
            assertEquals(0, cloned.get(0, 0, 0)); // x=0, y=0, z=0: 0 + 0*10 + 0*100 = 0
            // Affecte une valeur
            assertEquals(1, cloned.get(1, 0, 0)); // x=1, y=0, z=0: 1 + 0*10 + 0*100 = 1

            // Verify original was changed
            // Appelle une méthode
            assertEquals(999, original.get(0, 0, 0));
            // Appelle une méthode
            assertEquals(888, original.get(1, 0, 0));

            // Embranchement : vérifie une condition
            if (cloned.dimension() > 2) {
                // Modify clone
                // Appelle une méthode
                cloned.set(2, 2, 2, 777);

                // Verify original is unaffected by clone modification
                // Affecte une valeur
                int expected = 2 + 2 * 10 + 2 * 100; // Should be 222
                // Appelle une méthode
                assertEquals(expected, original.get(2, 2, 2));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cloneEmptyPalette() {
        // Appelle une méthode
        var palettes = testPalettes();
        // Boucle : répète un bloc
        for (Palette original : palettes) {
            // Clone empty palette
            // Appelle une méthode
            Palette cloned = original.clone();

            // Appelle une méthode
            assertEquals(0, cloned.count());
            // Appelle une méthode
            assertEquals(original.dimension(), cloned.dimension());
            // Appelle une méthode
            assertTrue(original.compare(cloned));

            // Verify all values are 0
            // Boucle : répète un bloc
            for (int x = 0; x < cloned.dimension(); x++) {
                // Boucle : répète un bloc
                for (int y = 0; y < cloned.dimension(); y++) {
                    // Boucle : répète un bloc
                    for (int z = 0; z < cloned.dimension(); z++) {
                        // Appelle une méthode
                        assertEquals(0, cloned.get(x, y, z));
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cloneFullPalette() {
        // Appelle une méthode
        var palettes = testPalettes();
        // Boucle : répète un bloc
        for (Palette original : palettes) {
            // Fill entire palette
            // Appelle une méthode
            original.fill(123);

            // Appelle une méthode
            Palette cloned = original.clone();

            // Appelle une méthode
            assertEquals(original.count(), cloned.count());
            // Appelle une méthode
            assertEquals(original.maxSize(), cloned.count());
            // Appelle une méthode
            assertTrue(original.compare(cloned));

            // Verify all values are correct
            // Boucle : répète un bloc
            for (int x = 0; x < cloned.dimension(); x++) {
                // Boucle : répète un bloc
                for (int y = 0; y < cloned.dimension(); y++) {
                    // Boucle : répète un bloc
                    for (int z = 0; z < cloned.dimension(); z++) {
                        // Appelle une méthode
                        assertEquals(123, cloned.get(x, y, z));
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cloneWithPatternData() {
        // Appelle une méthode
        var palettes = testPalettes();
        // Boucle : répète un bloc
        for (Palette original : palettes) {
            // Create complex pattern
            // Début d'une méthode/d'un bloc
            original.setAll((x, y, z) -> {
                // Affecte une valeur
                int value = x * 1000 + y * 100 + z * 10;
                // Renvoie une valeur à l'appelant
                return Math.abs(value) % 65536; // Keep within reasonable range
            // Fin d'un bloc/d'une expression
            });

            // Appelle une méthode
            Palette cloned = original.clone();

            // Appelle une méthode
            assertEquals(original.count(), cloned.count());
            // Appelle une méthode
            assertTrue(original.compare(cloned));

            // Verify pattern is preserved
            // Début d'une méthode/d'un bloc
            cloned.getAll((x, y, z, value) -> {
                // Appelle une méthode
                int expected = Math.abs(x * 1000 + y * 100 + z * 10) % 65536;
                // Instruction de code
                assertEquals(expected, value,
                        // Appelle une méthode
                        String.format("Mismatch at (%d,%d,%d)", x, y, z));
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cloneAfterResize() {
        // Appelle une méthode
        Palette original = Palette.blocks();
        // Fill with initial data to force one storage type
        // Boucle : répète un bloc
        for (int i = 0; i < 10; i++) {
            // Appelle une méthode
            original.set(i % original.dimension(), 0, 0, i + 1);
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (int i = 0; i < 10; i++) {
            // Appelle une méthode
            assertEquals(i + 1, original.get(i % original.dimension(), 0, 0));
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        int initialDimension = original.dimension();
        // Appelle une méthode
        int initialBitsPerEntry = original.bitsPerEntry();
        // Appelle une méthode
        int initialCount = original.count();

        // Appelle une méthode
        Palette cloned = original.clone();
        // Verify basic properties
        // Appelle une méthode
        assertEquals(initialDimension, cloned.dimension());
        // Appelle une méthode
        assertEquals(initialBitsPerEntry, cloned.bitsPerEntry());
        // Appelle une méthode
        assertEquals(initialCount, cloned.count());
        // Appelle une méthode
        assertTrue(original.compare(cloned));
        // Boucle : répète un bloc
        for (int i = 0; i < 10; i++) {
            // Appelle une méthode
            assertEquals(i + 1, cloned.get(i % cloned.dimension(), 0, 0));
        // Fin d'un bloc/d'une expression
        }

        // Now force resize by adding many unique values to original
        // Affecte une valeur
        Random random = new Random(42); // Deterministic
        // Boucle : répète un bloc
        for (int i = 0; i < original.maxSize() / 2; i++) {
            // Appelle une méthode
            int x = random.nextInt(original.dimension());
            // Appelle une méthode
            int y = random.nextInt(original.dimension());
            // Appelle une méthode
            int z = random.nextInt(original.dimension());
            // Instruction de code
            original.set(x, y, z, 1000 + i); // Unique large values
        // Fin d'un bloc/d'une expression
        }

        // Verify original may have resized
        // (bitsPerEntry might have increased)

        // Verify clone still has original data and hasn't been affected
        // Appelle une méthode
        assertEquals(initialBitsPerEntry, cloned.bitsPerEntry());
        // Appelle une méthode
        assertEquals(initialCount, cloned.count());

        // Verify clone still has original values
        // Boucle : répète un bloc
        for (int i = 0; i < 10; i++) {
            // Appelle une méthode
            assertEquals(i + 1, cloned.get(i % cloned.dimension(), 0, 0));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cloneAndModifyBothDirections() {
        // Appelle une méthode
        var palettes = testPalettes();
        // Boucle : répète un bloc
        for (Palette original : palettes) {
            // Set initial pattern
            // Appelle une méthode
            original.setAll((x, y, z) -> (x + y + z) % 256);

            // Appelle une méthode
            Palette cloned = original.clone();
            // Appelle une méthode
            assertTrue(original.compare(cloned));

            // Modify original extensively
            // Appelle une méthode
            original.fill(500);

            // Modify clone extensively
            // Appelle une méthode
            cloned.fill(600);

            // Verify they're completely independent
            // Appelle une méthode
            assertFalse(original.compare(cloned));

            // Check original
            // Boucle : répète un bloc
            for (int x = 0; x < original.dimension(); x++) {
                // Boucle : répète un bloc
                for (int y = 0; y < original.dimension(); y++) {
                    // Boucle : répète un bloc
                    for (int z = 0; z < original.dimension(); z++) {
                        // Appelle une méthode
                        assertEquals(500, original.get(x, y, z));
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Check clone
            // Boucle : répète un bloc
            for (int x = 0; x < cloned.dimension(); x++) {
                // Boucle : répète un bloc
                for (int y = 0; y < cloned.dimension(); y++) {
                    // Boucle : répète un bloc
                    for (int z = 0; z < cloned.dimension(); z++) {
                        // Appelle une méthode
                        assertEquals(600, cloned.get(x, y, z));
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cloneWithOffset() {
        // Appelle une méthode
        var palettes = testPalettes();
        // Boucle : répète un bloc
        for (Palette original : palettes) {
            // Set pattern and apply offset
            // Appelle une méthode
            original.setAll((x, y, z) -> x + y + z + 100);
            // Appelle une méthode
            original.offset(50);

            // Appelle une méthode
            Palette cloned = original.clone();

            // Appelle une méthode
            assertTrue(original.compare(cloned));

            // Verify offset was preserved in clone
            // Début d'une méthode/d'un bloc
            cloned.getAll((x, y, z, value) -> {
                // Affecte une valeur
                int expected = x + y + z + 100 + 50;
                // Appelle une méthode
                assertEquals(expected, value);
            // Fin d'un bloc/d'une expression
            });

            // Apply different offset to original
            // Appelle une méthode
            original.offset(-25);

            // Verify clone is unaffected
            // Début d'une méthode/d'un bloc
            cloned.getAll((x, y, z, value) -> {
                // Affecte une valeur
                int expected = x + y + z + 100 + 50;
                // Appelle une méthode
                assertEquals(expected, value);
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cloneWithReplace() {
        // Appelle une méthode
        var palettes = testPalettes();
        // Boucle : répète un bloc
        for (Palette original : palettes) {
            // Set initial values
            // Appelle une méthode
            original.setAll((x, y, z) -> x + y + z);

            // Appelle une méthode
            Palette cloned = original.clone();

            // Apply replace operation to original
            // Appelle une méthode
            original.replaceAll((x, y, z, value) -> value * 2);

            // Verify clone is unaffected
            // Appelle une méthode
            cloned.getAll((x, y, z, value) -> assertEquals(x + y + z, value));

            // Apply different replace to clone
            // Appelle une méthode
            cloned.replaceAll((x, y, z, value) -> value + 1000);

            // Verify both have correct values
            // Appelle une méthode
            original.getAll((x, y, z, value) -> assertEquals((x + y + z) * 2, value));

            // Appelle une méthode
            cloned.getAll((x, y, z, value) -> assertEquals(x + y + z + 1000, value));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void multipleClonesIndependence() {
        // Appelle une méthode
        var palettes = testPalettes();
        // Boucle : répète un bloc
        for (Palette original : palettes) {
            // Appelle une méthode
            original.setAll((x, y, z) -> x * 100 + y * 10 + z);

            // Create multiple clones
            // Appelle une méthode
            Palette clone1 = original.clone();
            // Appelle une méthode
            Palette clone2 = original.clone();
            // Appelle une méthode
            Palette clone3 = original.clone();

            // Verify all are equal initially
            // Appelle une méthode
            assertTrue(original.compare(clone1));
            // Appelle une méthode
            assertTrue(original.compare(clone2));
            // Appelle une méthode
            assertTrue(original.compare(clone3));
            // Appelle une méthode
            assertTrue(clone1.compare(clone2));

            // Modify each differently
            // Appelle une méthode
            original.fill(1);
            // Appelle une méthode
            clone1.fill(2);
            // Appelle une méthode
            clone2.fill(3);
            // Appelle une méthode
            clone3.fill(4);

            // Verify all are different
            // Appelle une méthode
            assertFalse(original.compare(clone1));
            // Appelle une méthode
            assertFalse(original.compare(clone2));
            // Appelle une méthode
            assertFalse(clone1.compare(clone2));
            // Appelle une méthode
            assertFalse(clone2.compare(clone3));

            // Verify each has correct values
            // Appelle une méthode
            assertEquals(original.maxSize(), original.count());
            // Appelle une méthode
            original.getAll((x, y, z, value) -> assertEquals(1, value));

            // Appelle une méthode
            clone1.getAll((x, y, z, value) -> assertEquals(2, value));
            // Appelle une méthode
            clone2.getAll((x, y, z, value) -> assertEquals(3, value));
            // Appelle une méthode
            clone3.getAll((x, y, z, value) -> assertEquals(4, value));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cloneOptimization() {
        // Appelle une méthode
        var palettes = testPalettes();
        // Boucle : répète un bloc
        for (Palette original : palettes) {
            // Create sparse data
            // Appelle une méthode
            original.set(0, 0, 0, 100);
            // Appelle une méthode
            original.set(original.dimension() - 1, original.dimension() - 1, original.dimension() - 1, 200);

            // Appelle une méthode
            Palette cloned = original.clone();

            // Apply optimization to original
            // Appelle une méthode
            original.optimize(Palette.Optimization.SIZE);

            // Verify clone is unaffected by optimization
            // Appelle une méthode
            assertTrue(original.compare(cloned));
            // Appelle une méthode
            assertEquals(100, cloned.get(0, 0, 0));
            // Appelle une méthode
            assertEquals(200, cloned.get(original.dimension() - 1, original.dimension() - 1, original.dimension() - 1));

            // Apply different optimization to clone
            // Appelle une méthode
            cloned.optimize(Palette.Optimization.SPEED);

            // Both should still have same data despite different optimizations
            // Appelle une méthode
            assertTrue(original.compare(cloned));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cloneDifferentPaletteTypes() {
        // Test blocks vs biomes vs custom sized palettes
        // Appelle une méthode
        Palette blockPalette = Palette.blocks();
        // Appelle une méthode
        Palette biomePalette = Palette.biomes();
        // Appelle une méthode
        Palette customPalette = Palette.sized(8, 2, 6, 12, 4);

        // Appelle une méthode
        List<Palette> palettes = List.of(blockPalette, biomePalette, customPalette);

        // Boucle : répète un bloc
        for (Palette original : palettes) {
            // Appelle une méthode
            original.setAll((x, y, z) -> (x + y + z) % 100);

            // Appelle une méthode
            Palette cloned = original.clone();

            // Appelle une méthode
            assertEquals(original.dimension(), cloned.dimension());
            // Appelle une méthode
            assertEquals(original.bitsPerEntry(), cloned.bitsPerEntry());
            // Appelle une méthode
            assertEquals(original.count(), cloned.count());
            // Appelle une méthode
            assertTrue(original.compare(cloned));

            // Verify independence
            // Appelle une méthode
            original.set(0, 0, 0, 999);
            // Appelle une méthode
            assertNotEquals(999, cloned.get(0, 0, 0));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static List<Palette> testPalettes() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                Palette.sized(2, 1, 5, 15, 3),
                // Instruction de code
                Palette.sized(4, 1, 5, 15, 3),
                // Instruction de code
                Palette.sized(8, 1, 5, 15, 3),
                // Instruction de code
                Palette.sized(16, 1, 5, 15, 3),
                // Instruction de code
                Palette.blocks()
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
