// Déclaration du paquet de ce fichier
package net.minestom.server.instance.palette;

// Import d'une classe nécessaire
import org.junit.jupiter.api.DisplayName;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Nested;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Random;

// Import statique d'un membre
import static net.minestom.server.instance.palette.PaletteAssertions.assertAllEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class PaletteCopyTest {

    // Annotation pour l'élément suivant
    @Nested
    // Annotation pour l'élément suivant
    @DisplayName("Basic Copy Operations")
    // Déclaration de type (classe/interface/enum/record)
    class BasicCopyOperations {

        // Annotation pour l'élément suivant
        @Test
        // Annotation pour l'élément suivant
        @DisplayName("Copy from empty palette to empty palette")
        // Début d'une méthode/d'un bloc
        void copyEmptyToEmpty() {
            // Appelle une méthode
            Palette source = Palette.blocks();
            // Appelle une méthode
            Palette target = Palette.blocks();

            // Appelle une méthode
            target.copyFrom(source);

            // Appelle une méthode
            assertEquals(0, target.count());
            // Appelle une méthode
            assertEquals(0, target.bitsPerEntry());
            // Appelle une méthode
            assertTrue(target.compare(source));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Test
        // Annotation pour l'élément suivant
        @DisplayName("Copy from single value palette to empty palette")
        // Début d'une méthode/d'un bloc
        void copySingleValueToEmpty() {
            // Appelle une méthode
            Palette source = Palette.blocks();
            // Appelle une méthode
            Palette target = Palette.blocks();

            // Appelle une méthode
            source.fill(42);
            // Appelle une méthode
            target.copyFrom(source);

            // Appelle une méthode
            assertEquals(source.count(), target.count());
            // Appelle une méthode
            assertEquals(source.bitsPerEntry(), target.bitsPerEntry());
            // Appelle une méthode
            assertTrue(target.compare(source));

            // Appelle une méthode
            assertAllEquals(42, target);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Test
        // Annotation pour l'élément suivant
        @DisplayName("Copy from multi-value palette to empty palette")
        // Début d'une méthode/d'un bloc
        void copyMultiValueToEmpty() {
            // Appelle une méthode
            Palette source = Palette.blocks();
            // Appelle une méthode
            Palette target = Palette.blocks();

            // Set up source with multiple values
            // Appelle une méthode
            source.set(0, 0, 0, 10);
            // Appelle une méthode
            source.set(1, 1, 1, 20);
            // Appelle une méthode
            source.set(2, 2, 2, 30);
            // Appelle une méthode
            source.set(15, 15, 15, 40);

            // Appelle une méthode
            target.copyFrom(source);

            // Appelle une méthode
            assertEquals(source.count(), target.count());
            // Appelle une méthode
            assertEquals(source.bitsPerEntry(), target.bitsPerEntry());
            // Appelle une méthode
            assertTrue(target.compare(source));

            // Verify specific values
            // Appelle une méthode
            assertEquals(10, target.get(0, 0, 0));
            // Appelle une méthode
            assertEquals(20, target.get(1, 1, 1));
            // Appelle une méthode
            assertEquals(30, target.get(2, 2, 2));
            // Appelle une méthode
            assertEquals(40, target.get(15, 15, 15));
            // Instruction de code
            assertEquals(0, target.get(5, 5, 5)); // Default value
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Test
        // Annotation pour l'élément suivant
        @DisplayName("Copy to non-empty palette overwrites existing data")
        // Début d'une méthode/d'un bloc
        void copyToNonEmptyPalette() {
            // Appelle une méthode
            Palette source = Palette.blocks();
            // Appelle une méthode
            Palette target = Palette.blocks();

            // Set up target with initial data
            // Appelle une méthode
            target.set(0, 0, 0, 99);
            // Appelle une méthode
            target.set(1, 1, 1, 88);

            // Set up source with different data
            // Appelle une méthode
            source.set(2, 2, 2, 77);
            // Appelle une méthode
            source.set(3, 3, 3, 66);

            // Appelle une méthode
            target.copyFrom(source);

            // Appelle une méthode
            assertTrue(target.compare(source));
            // Instruction de code
            assertEquals(0, target.get(0, 0, 0)); // Original data overwritten
            // Instruction de code
            assertEquals(0, target.get(1, 1, 1)); // Original data overwritten
            // Instruction de code
            assertEquals(77, target.get(2, 2, 2)); // Source data copied
            // Instruction de code
            assertEquals(66, target.get(3, 3, 3)); // Source data copied
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nested
    // Annotation pour l'élément suivant
    @DisplayName("Different Palette Types")
    // Déclaration de type (classe/interface/enum/record)
    class DifferentPaletteTypes {

        // Annotation pour l'élément suivant
        @Test
        // Annotation pour l'élément suivant
        @DisplayName("Copy between block palettes")
        // Début d'une méthode/d'un bloc
        void copyBetweenBlockPalettes() {
            // Affecte une valeur
            List<Palette> palettes = List.of(
                    // Instruction de code
                    Palette.blocks(),
                    // Instruction de code
                    Palette.sized(16, 4, 8, 15, 4),
                    // Instruction de code
                    Palette.sized(16, 4, 8, 15, 6),
                    // Instruction de code
                    Palette.sized(16, 4, 8, 15, 8)
            // Fin d'un bloc/d'une expression
            );

            // Boucle : répète un bloc
            for (Palette source : palettes) {
                // Boucle : répète un bloc
                for (Palette target : palettes) {
                    // Set up source data
                    // Appelle une méthode
                    source.set(0, 0, 0, 100);
                    // Appelle une méthode
                    source.set(5, 10, 15, 200);
                    // Appelle une méthode
                    source.set(15, 0, 0, 300);

                    // Appelle une méthode
                    target.copyFrom(source);

                    // Instruction de code
                    assertTrue(target.compare(source),
                            // Instruction de code
                            String.format("Copy failed from %d bits to %d bits",
                                    // Appelle une méthode
                                    source.bitsPerEntry(), target.bitsPerEntry()));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Test
        // Annotation pour l'élément suivant
        @DisplayName("Copy between biome palettes")
        // Début d'une méthode/d'un bloc
        void copyBetweenBiomePalettes() {
            // Appelle une méthode
            Palette source = Palette.biomes();
            // Appelle une méthode
            Palette target = Palette.biomes();

            // Set up source with biome data
            // Instruction de code
            source.set(0, 0, 0, 1); // Plains
            // Instruction de code
            source.set(1, 1, 1, 2); // Desert
            // Instruction de code
            source.set(2, 2, 2, 3); // Forest
            // Instruction de code
            source.set(3, 3, 3, 4); // Ocean

            // Appelle une méthode
            target.copyFrom(source);

            // Appelle une méthode
            assertTrue(target.compare(source));
            // Appelle une méthode
            assertEquals(1, target.get(0, 0, 0));
            // Appelle une méthode
            assertEquals(2, target.get(1, 1, 1));
            // Appelle une méthode
            assertEquals(3, target.get(2, 2, 2));
            // Appelle une méthode
            assertEquals(4, target.get(3, 3, 3));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nested
    // Annotation pour l'élément suivant
    @DisplayName("Edge Cases and Error Conditions")
    // Déclaration de type (classe/interface/enum/record)
    class EdgeCasesAndErrorConditions {

        // Annotation pour l'élément suivant
        @Test
        // Annotation pour l'élément suivant
        @DisplayName("Copy from palette with dimension mismatch throws exception")
        // Début d'une méthode/d'un bloc
        void copyDimensionMismatchThrowsException() {
            // Affecte une valeur
            Palette blockPalette = Palette.blocks(); // 16x16x16
            // Affecte une valeur
            Palette biomePalette = Palette.biomes();  // 4x4x4

            // Affecte une valeur
            IllegalArgumentException exception = assertThrows(
                    // Instruction de code
                    IllegalArgumentException.class,
                    // Instruction de code
                    () -> blockPalette.copyFrom(biomePalette)
            // Fin d'un bloc/d'une expression
            );

            // Appelle une méthode
            assertTrue(exception.getMessage().contains("dimension"));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Test
        // Annotation pour l'élément suivant
        @DisplayName("Copy from zero bits per entry palette")
        // Début d'une méthode/d'un bloc
        void copyFromZeroBitsPerEntry() {
            // Appelle une méthode
            Palette source = Palette.blocks();
            // Appelle une méthode
            Palette target = Palette.blocks();

            // Source has zero bits per entry (single value)
            // Appelle une méthode
            assertEquals(0, source.bitsPerEntry());

            // Appelle une méthode
            target.copyFrom(source);

            // Appelle une méthode
            assertEquals(0, target.bitsPerEntry());
            // Appelle une méthode
            assertEquals(0, target.count());
            // Appelle une méthode
            assertTrue(target.compare(source));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Test
        // Annotation pour l'élément suivant
        @DisplayName("Copy from palette with zero count")
        // Début d'une méthode/d'un bloc
        void copyFromZeroCount() {
            // Appelle une méthode
            Palette source = Palette.blocks();
            // Appelle une méthode
            Palette target = Palette.blocks();

            // Ensure source has zero count
            // Appelle une méthode
            assertEquals(0, source.count());

            // Appelle une méthode
            target.copyFrom(source);

            // Appelle une méthode
            assertEquals(0, target.count());
            // Appelle une méthode
            assertEquals(0, target.bitsPerEntry());
            // Appelle une méthode
            assertTrue(target.compare(source));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Test
        // Annotation pour l'élément suivant
        @DisplayName("Copy high value entries")
        // Début d'une méthode/d'un bloc
        void copyHighValueEntries() {
            // Appelle une méthode
            Palette source = Palette.blocks();
            // Appelle une méthode
            Palette target = Palette.blocks();

            // Affecte une valeur
            int highValue = 1_000_000;
            // Appelle une méthode
            source.set(0, 0, 0, highValue);
            // Appelle une méthode
            source.set(15, 15, 15, highValue + 1);

            // Appelle une méthode
            target.copyFrom(source);

            // Appelle une méthode
            assertTrue(target.compare(source));
            // Appelle une méthode
            assertEquals(highValue, target.get(0, 0, 0));
            // Appelle une méthode
            assertEquals(highValue + 1, target.get(15, 15, 15));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nested
    // Annotation pour l'élément suivant
    @DisplayName("Internal Data Structure Integrity")
    // Déclaration de type (classe/interface/enum/record)
    class InternalDataStructureIntegrity {

        // Annotation pour l'élément suivant
        @Test
        // Annotation pour l'élément suivant
        @DisplayName("Copied palette maintains independence from source")
        // Début d'une méthode/d'un bloc
        void copiedPaletteMaintainsIndependence() {
            // Appelle une méthode
            Palette source = Palette.blocks();
            // Appelle une méthode
            Palette target = Palette.blocks();

            // Set up source
            // Appelle une méthode
            source.set(0, 0, 0, 10);
            // Appelle une méthode
            source.set(1, 1, 1, 20);

            // Appelle une méthode
            target.copyFrom(source);

            // Verify initial copy is correct
            // Appelle une méthode
            assertTrue(target.compare(source));

            // Modify source after copy
            // Appelle une méthode
            source.set(2, 2, 2, 30);
            // Instruction de code
            source.set(0, 0, 0, 99); // Change existing value

            // Target should remain unchanged
            // Appelle une méthode
            assertEquals(10, target.get(0, 0, 0));
            // Appelle une méthode
            assertEquals(20, target.get(1, 1, 1));
            // Instruction de code
            assertEquals(0, target.get(2, 2, 2)); // Should not have new value
            // Instruction de code
            assertFalse(target.compare(source)); // Should no longer be equal
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Test
        // Annotation pour l'élément suivant
        @DisplayName("Copy preserves exact palette state")
        // Début d'une méthode/d'un bloc
        void copyPreservesExactPaletteState() {
            // Appelle une méthode
            Palette source = Palette.blocks();
            // Appelle une méthode
            Palette target = Palette.blocks();

            // Create a complex palette state
            // Affecte une valeur
            Random random = new Random(12345); // Fixed seed for reproducibility
            // Boucle : répète un bloc
            for (int i = 0; i < 50; i++) {
                // Appelle une méthode
                int x = random.nextInt(16);
                // Appelle une méthode
                int y = random.nextInt(16);
                // Appelle une méthode
                int z = random.nextInt(16);
                // Appelle une méthode
                int value = random.nextInt(1000) + 1;
                // Appelle une méthode
                source.set(x, y, z, value);
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            int originalCount = source.count();
            // Appelle une méthode
            int originalBitsPerEntry = source.bitsPerEntry();

            // Appelle une méthode
            target.copyFrom(source);

            // Appelle une méthode
            assertEquals(originalCount, target.count());
            // Appelle une méthode
            assertEquals(originalBitsPerEntry, target.bitsPerEntry());
            // Appelle une méthode
            assertTrue(target.compare(source));

            // Verify every position matches
            // Boucle : répète un bloc
            for (int x = 0; x < 16; x++) {
                // Boucle : répète un bloc
                for (int y = 0; y < 16; y++) {
                    // Boucle : répète un bloc
                    for (int z = 0; z < 16; z++) {
                        // Instruction de code
                        assertEquals(source.get(x, y, z), target.get(x, y, z),
                                // Appelle une méthode
                                String.format("Mismatch at position (%d, %d, %d)", x, y, z));
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
        // Annotation pour l'élément suivant
        @DisplayName("Copy handles palette resize scenarios")
        // Début d'une méthode/d'un bloc
        void copyHandlesPaletteResizeScenarios() {
            // Test copying from a palette that has undergone resizing
            // Appelle une méthode
            Palette source = Palette.sized(16, 1, 5, 15, 2);
            // Appelle une méthode
            Palette target = Palette.blocks();

            // Fill with values that will cause resize in source
            // Appelle une méthode
            source.set(0, 0, 0, 1);
            // Appelle une méthode
            source.set(0, 0, 1, 2);
            // Appelle une méthode
            source.set(0, 0, 2, 3);
            // Appelle une méthode
            assertEquals(2, source.bitsPerEntry());

            // Instruction de code
            source.set(0, 0, 3, 4); // This should trigger resize to 3 bits
            // Appelle une méthode
            assertEquals(3, source.bitsPerEntry());

            // Add more values to increase palette size
            // Boucle : répète un bloc
            for (int i = 5; i <= 10; i++) {
                // Appelle une méthode
                source.set(i, 0, 0, i);
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            target.copyFrom(source);

            // Appelle une méthode
            assertTrue(target.compare(source));
            // Appelle une méthode
            assertEquals(source.bitsPerEntry(), target.bitsPerEntry());

            // Verify all values are preserved
            // Boucle : répète un bloc
            for (int i = 1; i <= 4; i++) {
                // Appelle une méthode
                assertEquals(i, target.get(0, 0, i - 1));
            // Fin d'un bloc/d'une expression
            }
            // Boucle : répète un bloc
            for (int i = 5; i <= 10; i++) {
                // Appelle une méthode
                assertEquals(i, target.get(i, 0, 0));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nested
    // Annotation pour l'élément suivant
    @DisplayName("Performance and Stress Tests")
    // Déclaration de type (classe/interface/enum/record)
    class PerformanceAndStressTests {

        // Annotation pour l'élément suivant
        @Test
        // Annotation pour l'élément suivant
        @DisplayName("Copy fully populated palette")
        // Début d'une méthode/d'un bloc
        void copyFullyPopulatedPalette() {
            // Appelle une méthode
            Palette source = Palette.blocks();
            // Appelle une méthode
            Palette target = Palette.blocks();

            // Fill entire palette with unique values
            // Affecte une valeur
            int value = 1;
            // Boucle : répète un bloc
            for (int x = 0; x < 16; x++) {
                // Boucle : répète un bloc
                for (int y = 0; y < 16; y++) {
                    // Boucle : répète un bloc
                    for (int z = 0; z < 16; z++) {
                        // Appelle une méthode
                        source.set(x, y, z, value++);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Affecte une valeur
            assertEquals(4096, source.count()); // 16^3 = 4096

            // Appelle une méthode
            target.copyFrom(source);

            // Appelle une méthode
            assertTrue(target.compare(source));
            // Appelle une méthode
            assertEquals(4096, target.count());

            // Verify all values are preserved
            // Affecte une valeur
            value = 1;
            // Boucle : répète un bloc
            for (int x = 0; x < 16; x++) {
                // Boucle : répète un bloc
                for (int y = 0; y < 16; y++) {
                    // Boucle : répète un bloc
                    for (int z = 0; z < 16; z++) {
                        // Appelle une méthode
                        assertEquals(value++, target.get(x, y, z));
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
        // Annotation pour l'élément suivant
        @DisplayName("Copy sparse palette")
        // Début d'une méthode/d'un bloc
        void copySparsePalette() {
            // Appelle une méthode
            Palette source = Palette.blocks();
            // Appelle une méthode
            Palette target = Palette.blocks();

            // Set only a few values in a large palette
            // Appelle une méthode
            source.set(0, 0, 0, 100);
            // Appelle une méthode
            source.set(7, 8, 9, 200);
            // Appelle une méthode
            source.set(15, 15, 15, 300);

            // Appelle une méthode
            assertEquals(3, source.count());

            // Appelle une méthode
            target.copyFrom(source);

            // Appelle une méthode
            assertTrue(target.compare(source));
            // Appelle une méthode
            assertEquals(3, target.count());
            // Appelle une méthode
            assertEquals(100, target.get(0, 0, 0));
            // Appelle une méthode
            assertEquals(200, target.get(7, 8, 9));
            // Appelle une méthode
            assertEquals(300, target.get(15, 15, 15));

            // Verify other positions are default (0)
            // Appelle une méthode
            assertEquals(0, target.get(1, 1, 1));
            // Appelle une méthode
            assertEquals(0, target.get(8, 8, 8));
            // Appelle une méthode
            assertEquals(0, target.get(14, 14, 14));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nested
    // Annotation pour l'élément suivant
    @DisplayName("Multiple Copy Operations")
    // Déclaration de type (classe/interface/enum/record)
    class MultipleCopyOperations {

        // Annotation pour l'élément suivant
        @Test
        // Annotation pour l'élément suivant
        @DisplayName("Chain multiple copy operations")
        // Début d'une méthode/d'un bloc
        void chainMultipleCopyOperations() {
            // Appelle une méthode
            Palette palette1 = Palette.blocks();
            // Appelle une méthode
            Palette palette2 = Palette.blocks();
            // Appelle une méthode
            Palette palette3 = Palette.blocks();

            // Set up initial data
            // Appelle une méthode
            palette1.set(0, 0, 0, 111);
            // Appelle une méthode
            palette1.set(5, 5, 5, 222);

            // Copy chain: palette1 -> palette2 -> palette3
            // Appelle une méthode
            palette2.copyFrom(palette1);
            // Appelle une méthode
            palette3.copyFrom(palette2);

            // Appelle une méthode
            assertTrue(palette3.compare(palette1));
            // Appelle une méthode
            assertTrue(palette3.compare(palette2));

            // Appelle une méthode
            assertEquals(111, palette3.get(0, 0, 0));
            // Appelle une méthode
            assertEquals(222, palette3.get(5, 5, 5));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Test
        // Annotation pour l'élément suivant
        @DisplayName("Copy operation is idempotent")
        // Début d'une méthode/d'un bloc
        void copyOperationIsIdempotent() {
            // Appelle une méthode
            Palette source = Palette.blocks();
            // Appelle une méthode
            Palette target = Palette.blocks();

            // Set up source
            // Appelle une méthode
            source.set(1, 2, 3, 456);
            // Appelle une méthode
            source.set(4, 5, 6, 789);

            // Copy once
            // Appelle une méthode
            target.copyFrom(source);
            // Appelle une méthode
            assertTrue(target.compare(source));

            // Create a backup to compare against
            // Appelle une méthode
            Palette backup = target.clone();

            // Copy again - should not change anything
            // Appelle une méthode
            target.copyFrom(source);
            // Appelle une méthode
            assertTrue(target.compare(source));
            // Appelle une méthode
            assertTrue(target.compare(backup));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
