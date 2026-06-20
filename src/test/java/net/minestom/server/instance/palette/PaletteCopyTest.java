// Package declaration for this file
package net.minestom.server.instance.palette;

// Import of a required class
import org.junit.jupiter.api.DisplayName;
// Import of a required class
import org.junit.jupiter.api.Nested;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Random;

// Static import of a member
import static net.minestom.server.instance.palette.PaletteAssertions.assertAllEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class PaletteCopyTest {

    // Annotation for the following element
    @Nested
    // Annotation for the following element
    @DisplayName("Basic Copy Operations")
    // Type declaration (class/interface/enum/record)
    class BasicCopyOperations {

        // Annotation for the following element
        @Test
        // Annotation for the following element
        @DisplayName("Copy from empty palette to empty palette")
        // Start of a method/block
        void copyEmptyToEmpty() {
            // Calls a method
            Palette source = Palette.blocks();
            // Calls a method
            Palette target = Palette.blocks();

            // Calls a method
            target.copyFrom(source);

            // Calls a method
            assertEquals(0, target.count());
            // Calls a method
            assertEquals(0, target.bitsPerEntry());
            // Calls a method
            assertTrue(target.compare(source));
        // End of a block/expression
        }

        // Annotation for the following element
        @Test
        // Annotation for the following element
        @DisplayName("Copy from single value palette to empty palette")
        // Start of a method/block
        void copySingleValueToEmpty() {
            // Calls a method
            Palette source = Palette.blocks();
            // Calls a method
            Palette target = Palette.blocks();

            // Calls a method
            source.fill(42);
            // Calls a method
            target.copyFrom(source);

            // Calls a method
            assertEquals(source.count(), target.count());
            // Calls a method
            assertEquals(source.bitsPerEntry(), target.bitsPerEntry());
            // Calls a method
            assertTrue(target.compare(source));

            // Calls a method
            assertAllEquals(42, target);
        // End of a block/expression
        }

        // Annotation for the following element
        @Test
        // Annotation for the following element
        @DisplayName("Copy from multi-value palette to empty palette")
        // Start of a method/block
        void copyMultiValueToEmpty() {
            // Calls a method
            Palette source = Palette.blocks();
            // Calls a method
            Palette target = Palette.blocks();

            // Set up source with multiple values
            // Calls a method
            source.set(0, 0, 0, 10);
            // Calls a method
            source.set(1, 1, 1, 20);
            // Calls a method
            source.set(2, 2, 2, 30);
            // Calls a method
            source.set(15, 15, 15, 40);

            // Calls a method
            target.copyFrom(source);

            // Calls a method
            assertEquals(source.count(), target.count());
            // Calls a method
            assertEquals(source.bitsPerEntry(), target.bitsPerEntry());
            // Calls a method
            assertTrue(target.compare(source));

            // Verify specific values
            // Calls a method
            assertEquals(10, target.get(0, 0, 0));
            // Calls a method
            assertEquals(20, target.get(1, 1, 1));
            // Calls a method
            assertEquals(30, target.get(2, 2, 2));
            // Calls a method
            assertEquals(40, target.get(15, 15, 15));
            // Code statement
            assertEquals(0, target.get(5, 5, 5)); // Default value
        // End of a block/expression
        }

        // Annotation for the following element
        @Test
        // Annotation for the following element
        @DisplayName("Copy to non-empty palette overwrites existing data")
        // Start of a method/block
        void copyToNonEmptyPalette() {
            // Calls a method
            Palette source = Palette.blocks();
            // Calls a method
            Palette target = Palette.blocks();

            // Set up target with initial data
            // Calls a method
            target.set(0, 0, 0, 99);
            // Calls a method
            target.set(1, 1, 1, 88);

            // Set up source with different data
            // Calls a method
            source.set(2, 2, 2, 77);
            // Calls a method
            source.set(3, 3, 3, 66);

            // Calls a method
            target.copyFrom(source);

            // Calls a method
            assertTrue(target.compare(source));
            // Code statement
            assertEquals(0, target.get(0, 0, 0)); // Original data overwritten
            // Code statement
            assertEquals(0, target.get(1, 1, 1)); // Original data overwritten
            // Code statement
            assertEquals(77, target.get(2, 2, 2)); // Source data copied
            // Code statement
            assertEquals(66, target.get(3, 3, 3)); // Source data copied
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Nested
    // Annotation for the following element
    @DisplayName("Different Palette Types")
    // Type declaration (class/interface/enum/record)
    class DifferentPaletteTypes {

        // Annotation for the following element
        @Test
        // Annotation for the following element
        @DisplayName("Copy between block palettes")
        // Start of a method/block
        void copyBetweenBlockPalettes() {
            // Assigns a value
            List<Palette> palettes = List.of(
                    // Code statement
                    Palette.blocks(),
                    // Code statement
                    Palette.sized(16, 4, 8, 15, 4),
                    // Code statement
                    Palette.sized(16, 4, 8, 15, 6),
                    // Code statement
                    Palette.sized(16, 4, 8, 15, 8)
            // End of a block/expression
            );

            // Loop: repeats a block
            for (Palette source : palettes) {
                // Loop: repeats a block
                for (Palette target : palettes) {
                    // Set up source data
                    // Calls a method
                    source.set(0, 0, 0, 100);
                    // Calls a method
                    source.set(5, 10, 15, 200);
                    // Calls a method
                    source.set(15, 0, 0, 300);

                    // Calls a method
                    target.copyFrom(source);

                    // Code statement
                    assertTrue(target.compare(source),
                            // Code statement
                            String.format("Copy failed from %d bits to %d bits",
                                    // Calls a method
                                    source.bitsPerEntry(), target.bitsPerEntry()));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Test
        // Annotation for the following element
        @DisplayName("Copy between biome palettes")
        // Start of a method/block
        void copyBetweenBiomePalettes() {
            // Calls a method
            Palette source = Palette.biomes();
            // Calls a method
            Palette target = Palette.biomes();

            // Set up source with biome data
            // Code statement
            source.set(0, 0, 0, 1); // Plains
            // Code statement
            source.set(1, 1, 1, 2); // Desert
            // Code statement
            source.set(2, 2, 2, 3); // Forest
            // Code statement
            source.set(3, 3, 3, 4); // Ocean

            // Calls a method
            target.copyFrom(source);

            // Calls a method
            assertTrue(target.compare(source));
            // Calls a method
            assertEquals(1, target.get(0, 0, 0));
            // Calls a method
            assertEquals(2, target.get(1, 1, 1));
            // Calls a method
            assertEquals(3, target.get(2, 2, 2));
            // Calls a method
            assertEquals(4, target.get(3, 3, 3));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Nested
    // Annotation for the following element
    @DisplayName("Edge Cases and Error Conditions")
    // Type declaration (class/interface/enum/record)
    class EdgeCasesAndErrorConditions {

        // Annotation for the following element
        @Test
        // Annotation for the following element
        @DisplayName("Copy from palette with dimension mismatch throws exception")
        // Start of a method/block
        void copyDimensionMismatchThrowsException() {
            // Assigns a value
            Palette blockPalette = Palette.blocks(); // 16x16x16
            // Assigns a value
            Palette biomePalette = Palette.biomes();  // 4x4x4

            // Assigns a value
            IllegalArgumentException exception = assertThrows(
                    // Code statement
                    IllegalArgumentException.class,
                    // Code statement
                    () -> blockPalette.copyFrom(biomePalette)
            // End of a block/expression
            );

            // Calls a method
            assertTrue(exception.getMessage().contains("dimension"));
        // End of a block/expression
        }

        // Annotation for the following element
        @Test
        // Annotation for the following element
        @DisplayName("Copy from zero bits per entry palette")
        // Start of a method/block
        void copyFromZeroBitsPerEntry() {
            // Calls a method
            Palette source = Palette.blocks();
            // Calls a method
            Palette target = Palette.blocks();

            // Source has zero bits per entry (single value)
            // Calls a method
            assertEquals(0, source.bitsPerEntry());

            // Calls a method
            target.copyFrom(source);

            // Calls a method
            assertEquals(0, target.bitsPerEntry());
            // Calls a method
            assertEquals(0, target.count());
            // Calls a method
            assertTrue(target.compare(source));
        // End of a block/expression
        }

        // Annotation for the following element
        @Test
        // Annotation for the following element
        @DisplayName("Copy from palette with zero count")
        // Start of a method/block
        void copyFromZeroCount() {
            // Calls a method
            Palette source = Palette.blocks();
            // Calls a method
            Palette target = Palette.blocks();

            // Ensure source has zero count
            // Calls a method
            assertEquals(0, source.count());

            // Calls a method
            target.copyFrom(source);

            // Calls a method
            assertEquals(0, target.count());
            // Calls a method
            assertEquals(0, target.bitsPerEntry());
            // Calls a method
            assertTrue(target.compare(source));
        // End of a block/expression
        }

        // Annotation for the following element
        @Test
        // Annotation for the following element
        @DisplayName("Copy high value entries")
        // Start of a method/block
        void copyHighValueEntries() {
            // Calls a method
            Palette source = Palette.blocks();
            // Calls a method
            Palette target = Palette.blocks();

            // Assigns a value
            int highValue = 1_000_000;
            // Calls a method
            source.set(0, 0, 0, highValue);
            // Calls a method
            source.set(15, 15, 15, highValue + 1);

            // Calls a method
            target.copyFrom(source);

            // Calls a method
            assertTrue(target.compare(source));
            // Calls a method
            assertEquals(highValue, target.get(0, 0, 0));
            // Calls a method
            assertEquals(highValue + 1, target.get(15, 15, 15));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Nested
    // Annotation for the following element
    @DisplayName("Internal Data Structure Integrity")
    // Type declaration (class/interface/enum/record)
    class InternalDataStructureIntegrity {

        // Annotation for the following element
        @Test
        // Annotation for the following element
        @DisplayName("Copied palette maintains independence from source")
        // Start of a method/block
        void copiedPaletteMaintainsIndependence() {
            // Calls a method
            Palette source = Palette.blocks();
            // Calls a method
            Palette target = Palette.blocks();

            // Set up source
            // Calls a method
            source.set(0, 0, 0, 10);
            // Calls a method
            source.set(1, 1, 1, 20);

            // Calls a method
            target.copyFrom(source);

            // Verify initial copy is correct
            // Calls a method
            assertTrue(target.compare(source));

            // Modify source after copy
            // Calls a method
            source.set(2, 2, 2, 30);
            // Code statement
            source.set(0, 0, 0, 99); // Change existing value

            // Target should remain unchanged
            // Calls a method
            assertEquals(10, target.get(0, 0, 0));
            // Calls a method
            assertEquals(20, target.get(1, 1, 1));
            // Code statement
            assertEquals(0, target.get(2, 2, 2)); // Should not have new value
            // Code statement
            assertFalse(target.compare(source)); // Should no longer be equal
        // End of a block/expression
        }

        // Annotation for the following element
        @Test
        // Annotation for the following element
        @DisplayName("Copy preserves exact palette state")
        // Start of a method/block
        void copyPreservesExactPaletteState() {
            // Calls a method
            Palette source = Palette.blocks();
            // Calls a method
            Palette target = Palette.blocks();

            // Create a complex palette state
            // Assigns a value
            Random random = new Random(12345); // Fixed seed for reproducibility
            // Loop: repeats a block
            for (int i = 0; i < 50; i++) {
                // Calls a method
                int x = random.nextInt(16);
                // Calls a method
                int y = random.nextInt(16);
                // Calls a method
                int z = random.nextInt(16);
                // Calls a method
                int value = random.nextInt(1000) + 1;
                // Calls a method
                source.set(x, y, z, value);
            // End of a block/expression
            }

            // Calls a method
            int originalCount = source.count();
            // Calls a method
            int originalBitsPerEntry = source.bitsPerEntry();

            // Calls a method
            target.copyFrom(source);

            // Calls a method
            assertEquals(originalCount, target.count());
            // Calls a method
            assertEquals(originalBitsPerEntry, target.bitsPerEntry());
            // Calls a method
            assertTrue(target.compare(source));

            // Verify every position matches
            // Loop: repeats a block
            for (int x = 0; x < 16; x++) {
                // Loop: repeats a block
                for (int y = 0; y < 16; y++) {
                    // Loop: repeats a block
                    for (int z = 0; z < 16; z++) {
                        // Code statement
                        assertEquals(source.get(x, y, z), target.get(x, y, z),
                                // Calls a method
                                String.format("Mismatch at position (%d, %d, %d)", x, y, z));
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Test
        // Annotation for the following element
        @DisplayName("Copy handles palette resize scenarios")
        // Start of a method/block
        void copyHandlesPaletteResizeScenarios() {
            // Test copying from a palette that has undergone resizing
            // Calls a method
            Palette source = Palette.sized(16, 1, 5, 15, 2);
            // Calls a method
            Palette target = Palette.blocks();

            // Fill with values that will cause resize in source
            // Calls a method
            source.set(0, 0, 0, 1);
            // Calls a method
            source.set(0, 0, 1, 2);
            // Calls a method
            source.set(0, 0, 2, 3);
            // Calls a method
            assertEquals(2, source.bitsPerEntry());

            // Code statement
            source.set(0, 0, 3, 4); // This should trigger resize to 3 bits
            // Calls a method
            assertEquals(3, source.bitsPerEntry());

            // Add more values to increase palette size
            // Loop: repeats a block
            for (int i = 5; i <= 10; i++) {
                // Calls a method
                source.set(i, 0, 0, i);
            // End of a block/expression
            }

            // Calls a method
            target.copyFrom(source);

            // Calls a method
            assertTrue(target.compare(source));
            // Calls a method
            assertEquals(source.bitsPerEntry(), target.bitsPerEntry());

            // Verify all values are preserved
            // Loop: repeats a block
            for (int i = 1; i <= 4; i++) {
                // Calls a method
                assertEquals(i, target.get(0, 0, i - 1));
            // End of a block/expression
            }
            // Loop: repeats a block
            for (int i = 5; i <= 10; i++) {
                // Calls a method
                assertEquals(i, target.get(i, 0, 0));
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Nested
    // Annotation for the following element
    @DisplayName("Performance and Stress Tests")
    // Type declaration (class/interface/enum/record)
    class PerformanceAndStressTests {

        // Annotation for the following element
        @Test
        // Annotation for the following element
        @DisplayName("Copy fully populated palette")
        // Start of a method/block
        void copyFullyPopulatedPalette() {
            // Calls a method
            Palette source = Palette.blocks();
            // Calls a method
            Palette target = Palette.blocks();

            // Fill entire palette with unique values
            // Assigns a value
            int value = 1;
            // Loop: repeats a block
            for (int x = 0; x < 16; x++) {
                // Loop: repeats a block
                for (int y = 0; y < 16; y++) {
                    // Loop: repeats a block
                    for (int z = 0; z < 16; z++) {
                        // Calls a method
                        source.set(x, y, z, value++);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Assigns a value
            assertEquals(4096, source.count()); // 16^3 = 4096

            // Calls a method
            target.copyFrom(source);

            // Calls a method
            assertTrue(target.compare(source));
            // Calls a method
            assertEquals(4096, target.count());

            // Verify all values are preserved
            // Assigns a value
            value = 1;
            // Loop: repeats a block
            for (int x = 0; x < 16; x++) {
                // Loop: repeats a block
                for (int y = 0; y < 16; y++) {
                    // Loop: repeats a block
                    for (int z = 0; z < 16; z++) {
                        // Calls a method
                        assertEquals(value++, target.get(x, y, z));
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Test
        // Annotation for the following element
        @DisplayName("Copy sparse palette")
        // Start of a method/block
        void copySparsePalette() {
            // Calls a method
            Palette source = Palette.blocks();
            // Calls a method
            Palette target = Palette.blocks();

            // Set only a few values in a large palette
            // Calls a method
            source.set(0, 0, 0, 100);
            // Calls a method
            source.set(7, 8, 9, 200);
            // Calls a method
            source.set(15, 15, 15, 300);

            // Calls a method
            assertEquals(3, source.count());

            // Calls a method
            target.copyFrom(source);

            // Calls a method
            assertTrue(target.compare(source));
            // Calls a method
            assertEquals(3, target.count());
            // Calls a method
            assertEquals(100, target.get(0, 0, 0));
            // Calls a method
            assertEquals(200, target.get(7, 8, 9));
            // Calls a method
            assertEquals(300, target.get(15, 15, 15));

            // Verify other positions are default (0)
            // Calls a method
            assertEquals(0, target.get(1, 1, 1));
            // Calls a method
            assertEquals(0, target.get(8, 8, 8));
            // Calls a method
            assertEquals(0, target.get(14, 14, 14));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Nested
    // Annotation for the following element
    @DisplayName("Multiple Copy Operations")
    // Type declaration (class/interface/enum/record)
    class MultipleCopyOperations {

        // Annotation for the following element
        @Test
        // Annotation for the following element
        @DisplayName("Chain multiple copy operations")
        // Start of a method/block
        void chainMultipleCopyOperations() {
            // Calls a method
            Palette palette1 = Palette.blocks();
            // Calls a method
            Palette palette2 = Palette.blocks();
            // Calls a method
            Palette palette3 = Palette.blocks();

            // Set up initial data
            // Calls a method
            palette1.set(0, 0, 0, 111);
            // Calls a method
            palette1.set(5, 5, 5, 222);

            // Copy chain: palette1 -> palette2 -> palette3
            // Calls a method
            palette2.copyFrom(palette1);
            // Calls a method
            palette3.copyFrom(palette2);

            // Calls a method
            assertTrue(palette3.compare(palette1));
            // Calls a method
            assertTrue(palette3.compare(palette2));

            // Calls a method
            assertEquals(111, palette3.get(0, 0, 0));
            // Calls a method
            assertEquals(222, palette3.get(5, 5, 5));
        // End of a block/expression
        }

        // Annotation for the following element
        @Test
        // Annotation for the following element
        @DisplayName("Copy operation is idempotent")
        // Start of a method/block
        void copyOperationIsIdempotent() {
            // Calls a method
            Palette source = Palette.blocks();
            // Calls a method
            Palette target = Palette.blocks();

            // Set up source
            // Calls a method
            source.set(1, 2, 3, 456);
            // Calls a method
            source.set(4, 5, 6, 789);

            // Copy once
            // Calls a method
            target.copyFrom(source);
            // Calls a method
            assertTrue(target.compare(source));

            // Create a backup to compare against
            // Calls a method
            Palette backup = target.clone();

            // Copy again - should not change anything
            // Calls a method
            target.copyFrom(source);
            // Calls a method
            assertTrue(target.compare(source));
            // Calls a method
            assertTrue(target.compare(backup));
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
