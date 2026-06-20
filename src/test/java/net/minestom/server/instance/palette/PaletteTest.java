// Package declaration for this file
package net.minestom.server.instance.palette;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.HashSet;
// Import of a required class
import java.util.Random;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;

// Static import of a member
import static net.minestom.server.instance.palette.PaletteAssertions.assertAllEquals;
// Static import of a member
import static net.minestom.server.instance.palette.PaletteAssertions.testPalettes;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class PaletteTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singlePlacement() {
        // Calls a method
        var palette = Palette.blocks();
        // Calls a method
        palette.set(0, 0, 1, 1);
        // Calls a method
        assertEquals(1, palette.get(0, 0, 1));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void placement() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            assertEquals(0, palette.get(0, 0, 0), "Default value should be 0");
            // Calls a method
            assertEquals(0, palette.count());
            // Calls a method
            palette.set(0, 0, 0, 64);
            // Calls a method
            assertEquals(64, palette.get(0, 0, 0));
            // Calls a method
            assertEquals(1, palette.count());

            // Calls a method
            palette.set(1, 0, 0, 65);
            // Calls a method
            assertEquals(64, palette.get(0, 0, 0));
            // Calls a method
            assertEquals(65, palette.get(1, 0, 0));
            // Calls a method
            assertEquals(2, palette.count());

            // Calls a method
            palette.set(0, 1, 0, 66);
            // Calls a method
            assertEquals(64, palette.get(0, 0, 0));
            // Calls a method
            assertEquals(65, palette.get(1, 0, 0));
            // Calls a method
            assertEquals(66, palette.get(0, 1, 0));
            // Calls a method
            assertEquals(3, palette.count());

            // Calls a method
            palette.set(0, 0, 1, 67);
            // Calls a method
            assertEquals(64, palette.get(0, 0, 0));
            // Calls a method
            assertEquals(65, palette.get(1, 0, 0));
            // Calls a method
            assertEquals(66, palette.get(0, 1, 0));
            // Calls a method
            assertEquals(67, palette.get(0, 0, 1));
            // Calls a method
            assertEquals(4, palette.count());

            // Calls a method
            palette.set(0, 0, 1, 68);
            // Calls a method
            assertEquals(4, palette.count());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void placementHighValue() {
        // Assigns a value
        final int value = 250_000;
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.set(0, 0, 1, value);
            // Calls a method
            assertEquals(value, palette.get(0, 0, 1));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void negPlacement() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            assertThrows(IllegalArgumentException.class, () -> palette.set(-1, 0, 0, 64));
            // Calls a method
            assertThrows(IllegalArgumentException.class, () -> palette.set(0, -1, 0, 64));
            // Calls a method
            assertThrows(IllegalArgumentException.class, () -> palette.set(0, 0, -1, 64));

            // Calls a method
            assertThrows(IllegalArgumentException.class, () -> palette.get(-1, 0, 0));
            // Calls a method
            assertThrows(IllegalArgumentException.class, () -> palette.get(0, -1, 0));
            // Calls a method
            assertThrows(IllegalArgumentException.class, () -> palette.get(0, 0, -1));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void resize() {
        // Calls a method
        Palette palette = Palette.sized(16, 1, 5, 15, 2);
        // Calls a method
        palette.set(0, 0, 0, 1);
        // Calls a method
        assertEquals(2, palette.bitsPerEntry());
        // Calls a method
        palette.set(0, 0, 1, 2);
        // Calls a method
        assertEquals(2, palette.bitsPerEntry());
        // Calls a method
        palette.set(0, 0, 2, 3);
        // Calls a method
        assertEquals(2, palette.bitsPerEntry());

        // Calls a method
        palette.set(0, 0, 3, 4);
        // Calls a method
        assertEquals(3, palette.bitsPerEntry());
        // Calls a method
        assertEquals(1, palette.get(0, 0, 0));
        // Calls a method
        assertEquals(2, palette.get(0, 0, 1));
        // Calls a method
        assertEquals(3, palette.get(0, 0, 2));
        // Calls a method
        assertEquals(4, palette.get(0, 0, 3));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void fill() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            assertEquals(0, palette.count());
            // Calls a method
            palette.set(0, 0, 0, 5);
            // Calls a method
            assertEquals(1, palette.count());
            // Calls a method
            assertEquals(5, palette.get(0, 0, 0));
            // Calls a method
            palette.fill(6);
            // Calls a method
            assertEquals(6, palette.get(0, 0, 0));
            // Calls a method
            assertEquals(palette.maxSize(), palette.count());
            // Calls a method
            assertAllEquals(6, palette);

            // Calls a method
            palette.fill(0);
            // Calls a method
            assertEquals(0, palette.count());
            // Calls a method
            assertAllEquals(0, palette);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void offset() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.fill(0);
            // Calls a method
            palette.offset(1);
            // Calls a method
            assertAllEquals(1, palette);

            // Calls a method
            palette.fill(1);
            // Calls a method
            palette.set(0, 0, 1, 2);
            // Calls a method
            palette.offset(-1);
            // Loop: repeats a block
            for (int x = 0; x < palette.dimension(); x++) {
                // Loop: repeats a block
                for (int y = 0; y < palette.dimension(); y++) {
                    // Loop: repeats a block
                    for (int z = 0; z < palette.dimension(); z++) {
                        // Branch: checks a condition
                        if (x == 0 && y == 0 && z == 1) {
                            // Calls a method
                            assertEquals(1, palette.get(x, y, z));
                        // Alternative branch of the condition
                        } else {
                            // Calls a method
                            assertEquals(0, palette.get(x, y, z));
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.setAll((x, y, z) -> x + y + z + 100);
            // Calls a method
            palette.offset(50);
            // Start of a method/block
            palette.getAll((x, y, z, value) -> {
                // Assigns a value
                int expected = x + y + z + 100 + 50;
                // Calls a method
                assertEquals(expected, value);
            // End of a block/expression
            });
        // End of a block/expression
        }

        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.set(0, 0, 1, 1);
            // Calls a method
            palette.set(0, 1, 0, 2);
            // Calls a method
            palette.set(1, 0, 0, 3);
            // Calls a method
            palette.offset(50);
            // Start of a method/block
            palette.getAll((x, y, z, value) -> {
                // Branch: checks a condition
                if (x == 0 && y == 0 && z == 1) {
                    // Calls a method
                    assertEquals(51, value);
                // Branch: checks a condition
                } else if (x == 0 && y == 1 && z == 0) {
                    // Calls a method
                    assertEquals(52, value);
                // Branch: checks a condition
                } else if (x == 1 && y == 0 && z == 0) {
                    // Calls a method
                    assertEquals(53, value);
                // Alternative branch of the condition
                } else {
                    // Calls a method
                    assertEquals(50, value);
                // End of a block/expression
                }
            // End of a block/expression
            });
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void offsetCount() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            assertEquals(0, palette.count());
            // Calls a method
            palette.fill(0);
            // Calls a method
            assertEquals(0, palette.count());
            // Calls a method
            palette.offset(1);
            // Calls a method
            assertEquals(palette.maxSize(), palette.count());
            // Calls a method
            palette.offset(-1);
            // Calls a method
            assertEquals(0, palette.count());
        // End of a block/expression
        }
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.fill(1);
            // Calls a method
            assertEquals(palette.maxSize(), palette.count());
            // Calls a method
            palette.set(0, 0, 1, 2);
            // Calls a method
            palette.set(0, 1, 0, 3);
            // Calls a method
            palette.set(1, 0, 0, 4);
            // Calls a method
            palette.offset(-1);
            // Calls a method
            assertEquals(3, palette.count());
            // Calls a method
            palette.offset(1);
            // Calls a method
            assertEquals(palette.maxSize(), palette.count());
        // End of a block/expression
        }
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.setAll((x, y, z) -> x + y + z + 100);
            // Calls a method
            assertEquals(palette.maxSize(), palette.count());
            // Calls a method
            palette.offset(50);
            // Calls a method
            assertEquals(palette.maxSize(), palette.count());
            // Calls a method
            palette.offset(-50);
            // Calls a method
            assertEquals(palette.maxSize(), palette.count());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void replace() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.fill(0);
            // Calls a method
            palette.replace(0, 1);
            // Calls a method
            assertAllEquals(1, palette);

            // Calls a method
            palette.fill(1);
            // Calls a method
            palette.set(0, 0, 1, 2);
            // Calls a method
            palette.replace(2, 3);
            // Loop: repeats a block
            for (int x = 0; x < palette.dimension(); x++) {
                // Loop: repeats a block
                for (int y = 0; y < palette.dimension(); y++) {
                    // Loop: repeats a block
                    for (int z = 0; z < palette.dimension(); z++) {
                        // Branch: checks a condition
                        if (x == 0 && y == 0 && z == 1) {
                            // Calls a method
                            assertEquals(3, palette.get(x, y, z));
                        // Alternative branch of the condition
                        } else {
                            // Calls a method
                            assertEquals(1, palette.get(x, y, z));
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.set(0, 0, 1, 1);
            // Calls a method
            palette.set(0, 1, 0, 2);
            // Calls a method
            palette.set(1, 0, 0, 3);
            // Calls a method
            palette.replace(0, 50);
            // Start of a method/block
            palette.getAll((x, y, z, value) -> {
                // Branch: checks a condition
                if (x == 0 && y == 0 && z == 1) {
                    // Calls a method
                    assertEquals(1, value);
                // Branch: checks a condition
                } else if (x == 0 && y == 1 && z == 0) {
                    // Calls a method
                    assertEquals(2, value);
                // Branch: checks a condition
                } else if (x == 1 && y == 0 && z == 0) {
                    // Calls a method
                    assertEquals(3, value);
                // Alternative branch of the condition
                } else {
                    // Calls a method
                    assertEquals(50, value);
                // End of a block/expression
                }
            // End of a block/expression
            });
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void replaceCount() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.fill(0);
            // Calls a method
            palette.replace(0, 1);
            // Calls a method
            assertEquals(palette.maxSize(), palette.count());
            // Calls a method
            palette.replace(1, 0);
            // Calls a method
            assertEquals(0, palette.count());
        // End of a block/expression
        }
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.set(0, 0, 1, 1);
            // Calls a method
            palette.set(1, 1, 1, 1);
            // Calls a method
            palette.set(0, 1, 0, 2);
            // Calls a method
            palette.set(1, 0, 0, 3);
            // Calls a method
            assertEquals(4, palette.count());
            // Calls a method
            palette.replace(1, 0);
            // Calls a method
            assertEquals(2, palette.count());
        // End of a block/expression
        }
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.setAll((x, y, z) -> x + y + z + 100);
            // Calls a method
            assertEquals(palette.maxSize(), palette.count());
            // Calls a method
            palette.replace(100, 0);
            // Calls a method
            assertEquals(palette.maxSize() - 1, palette.count());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void replaceWithExistingValue() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.set(0, 0, 0, 1);
            // Calls a method
            palette.set(1, 0, 0, 2);
            // Calls a method
            palette.set(0, 1, 0, 2);

            // Calls a method
            palette.replace(1, 2);

            // Calls a method
            assertEquals(2, palette.get(0, 0, 0));
            // Calls a method
            assertEquals(2, palette.get(1, 0, 0));
            // Calls a method
            assertEquals(2, palette.get(0, 1, 0));
            // Calls a method
            assertEquals(3, palette.count(2));
            // Calls a method
            assertEquals(0, palette.count(1));
            // Calls a method
            assertFalse(palette.any(1));
            // Calls a method
            assertTrue(palette.any(2));

            // Calls a method
            palette.set(1, 1, 0, 1);
            // Calls a method
            assertEquals(1, palette.get(1, 1, 0));
            // Calls a method
            assertEquals(1, palette.count(1));
            // Calls a method
            assertEquals(3, palette.count(2));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void countValue() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            assertEquals(palette.maxSize(), palette.count(0));
            // Calls a method
            assertEquals(0, palette.count(1));
        // End of a block/expression
        }
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.fill(0);
            // Calls a method
            assertEquals(palette.maxSize(), palette.count(0));
            // Calls a method
            palette.replace(0, 1);
            // Calls a method
            assertEquals(0, palette.count(0));
            // Calls a method
            assertEquals(palette.maxSize(), palette.count(1));
        // End of a block/expression
        }
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.set(0, 0, 1, 1);
            // Calls a method
            palette.set(1, 1, 1, 1);
            // Calls a method
            palette.set(0, 1, 0, 2);
            // Calls a method
            palette.set(1, 0, 0, 3);
            // Calls a method
            assertEquals(palette.maxSize() - 4, palette.count(0));
            // Calls a method
            assertEquals(2, palette.count(1));
            // Calls a method
            assertEquals(1, palette.count(2));
            // Calls a method
            assertEquals(1, palette.count(3));
            // Calls a method
            assertEquals(0, palette.count(4));
        // End of a block/expression
        }
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.setAll((x, y, z) -> x + y + z + 100);
            // Calls a method
            assertEquals(0, palette.count(0));
            // Calls a method
            assertEquals(1, palette.count(100));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void anyValue() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Initially all zero
            // Calls a method
            assertFalse(palette.any(1));
            // Calls a method
            assertTrue(palette.any(0));
            // Calls a method
            palette.set(0, 0, 1, 1);
            // Calls a method
            assertTrue(palette.any(1));
            // Calls a method
            assertTrue(palette.any(0));
            // Calls a method
            palette.set(0, 0, 1, 0);
            // Calls a method
            assertFalse(palette.any(1));
            // Calls a method
            assertTrue(palette.any(0));
            // Calls a method
            palette.set(0, 0, 1, 1);
            // Calls a method
            palette.replace(0, 2);
            // Calls a method
            assertTrue(palette.any(1));
            // Calls a method
            assertFalse(palette.any(0));
            // Calls a method
            assertTrue(palette.any(2));
            // Calls a method
            palette.replace(1, 2);
            // Calls a method
            assertFalse(palette.any(1));
            // Calls a method
            assertTrue(palette.any(2));
        // End of a block/expression
        }
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.fill(5);
            // Calls a method
            assertTrue(palette.any(5));
            // Calls a method
            assertFalse(palette.any(0));
            // Calls a method
            palette.fill(0);
            // Calls a method
            assertFalse(palette.any(5));
            // Calls a method
            assertTrue(palette.any(0));
        // End of a block/expression
        }
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.setAll((x, y, z) -> (x + y + z) % 3);
            // Calls a method
            assertTrue(palette.any(0));
            // Calls a method
            assertTrue(palette.any(1));
            // Calls a method
            assertTrue(palette.any(2));
            // Calls a method
            assertFalse(palette.any(3));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void countValueEdgeCases() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // All zero
            // Calls a method
            assertEquals(palette.maxSize(), palette.count(0));
            // Calls a method
            assertEquals(0, palette.count(-1));
            // Calls a method
            assertEquals(0, palette.count(Integer.MAX_VALUE));
            // Fill with negative value
            // Calls a method
            palette.fill(-7);
            // Calls a method
            assertEquals(palette.maxSize(), palette.count(-7));
            // Calls a method
            assertEquals(0, palette.count(0));
            // Fill with max int
            // Calls a method
            palette.fill(Integer.MAX_VALUE);
            // Calls a method
            assertEquals(palette.maxSize(), palette.count(Integer.MAX_VALUE));
            // Calls a method
            assertEquals(0, palette.count(0));
        // End of a block/expression
        }
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.setAll((x, y, z) -> (x == 0 && y == 0 && z == 0) ? 42 : 0);
            // Calls a method
            assertEquals(1, palette.count(42));
            // Calls a method
            assertEquals(palette.maxSize() - 1, palette.count(0));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void bulk() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            final int dimension = palette.dimension();
            // Place
            // Loop: repeats a block
            for (int x = 0; x < dimension; x++) {
                // Loop: repeats a block
                for (int y = 0; y < dimension; y++) {
                    // Loop: repeats a block
                    for (int z = 0; z < dimension; z++) {
                        // Calls a method
                        palette.set(x, y, z, x + y + z + 1);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Calls a method
            assertEquals(palette.maxSize(), palette.count());
            // Verify
            // Loop: repeats a block
            for (int x = 0; x < dimension; x++) {
                // Loop: repeats a block
                for (int y = 0; y < dimension; y++) {
                    // Loop: repeats a block
                    for (int z = 0; z < dimension; z++) {
                        // Calls a method
                        assertEquals(x + y + z + 1, palette.get(x, y, z));
                    // End of a block/expression
                    }
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
    // Start of a method/block
    public void bulkAll() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Fill all entries
            // Calls a method
            palette.setAll((x, y, z) -> x + y + z + 1);
            // Code statement
            palette.getAll((x, y, z, value) -> assertEquals(x + y + z + 1, value,
                    // Calls a method
                    "x: " + x + ", y: " + y + ", z: " + z + ", dimension: " + palette.dimension()));

            // Replacing
            // Start of a method/block
            palette.replaceAll((x, y, z, value) -> {
                // Calls a method
                assertEquals(x + y + z + 1, value);
                // Returns a value to the caller
                return x + y + z + 2;
            // End of a block/expression
            });
            // Calls a method
            palette.getAll((x, y, z, value) -> assertEquals(x + y + z + 2, value));
        // End of a block/expression
        }

        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.setAll((x, y, z) -> x + y + z + 100);
            // Calls a method
            assertEquals(100, palette.get(0, 0, 0));
            // Code statement
            palette.getAll((x, y, z, value) -> assertEquals(x + y + z + 100, value,
                    // Calls a method
                    "x: " + x + ", y: " + y + ", z: " + z + ", dimension: " + palette.dimension()));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void bulkAllOrder() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            AtomicInteger count = new AtomicInteger();

            // Ensure that the lambda is called for every entry
            // even if the array is initialized
            // Calls a method
            palette.getAll((x, y, z, value) -> count.incrementAndGet());
            // Calls a method
            assertEquals(count.get(), palette.maxSize());

            // Fill all entries
            // Calls a method
            count.set(0);
            // Calls a method
            Set<Point> points = new HashSet<>();
            // Start of a method/block
            palette.setAll((x, y, z) -> {
                // Calls a method
                assertTrue(points.add(new Vec(x, y, z)), "Duplicate point: " + x + ", " + y + ", " + z + ", dimension " + palette.dimension());
                // Returns a value to the caller
                return count.incrementAndGet();
            // End of a block/expression
            });
            // Calls a method
            assertEquals(palette.maxSize(), palette.count());
            // Calls a method
            assertEquals(palette.count(), count.get());

            // Calls a method
            count.set(0);
            // Calls a method
            palette.getAll((x, y, z, value) -> assertEquals(count.incrementAndGet(), value));
            // Calls a method
            assertEquals(count.get(), palette.count());

            // Replacing
            // Calls a method
            count.set(0);
            // Start of a method/block
            palette.replaceAll((x, y, z, value) -> {
                // Calls a method
                assertEquals(count.incrementAndGet(), value);
                // Returns a value to the caller
                return count.get();
            // End of a block/expression
            });
            // Calls a method
            assertEquals(count.get(), palette.count());

            // Calls a method
            count.set(0);
            // Calls a method
            palette.getAll((x, y, z, value) -> assertEquals(count.incrementAndGet(), value));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void setAllConstant() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.setAll((x, y, z) -> 1);
            // Calls a method
            palette.getAll((x, y, z, value) -> assertEquals(1, value));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void setAllBig() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.setAll((x, y, z) -> x + y + z + 100);
            // Calls a method
            assertEquals(palette.maxSize(), palette.count());
            // Calls a method
            assertEquals(100, palette.get(0, 0, 0));
            // Start of a method/block
            palette.getAll((x, y, z, value) -> {
                // Assigns a value
                int expected = x + y + z + 100;
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
    public void getAllEmpty() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.getAll((x, y, z, value) -> assertEquals(0, value));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void getAllPresent() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.getAllPresent((x, y, z, value) -> fail("The palette should be empty"));
            // Calls a method
            palette.set(0, 0, 1, 1);
            // Start of a method/block
            palette.getAllPresent((x, y, z, value) -> {
                // Calls a method
                assertEquals(0, x);
                // Calls a method
                assertEquals(0, y);
                // Calls a method
                assertEquals(1, z);
                // Calls a method
                assertEquals(1, value);
            // End of a block/expression
            });
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void getAllPresentNonAirFill() {
        // Filling with a non-air value then editing a cell must still report every non-air cell.
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.fill(5);
            // Calls a method
            palette.set(0, 0, 0, 7);
            // Calls a method
            AtomicInteger reported = new AtomicInteger();
            // Start of a method/block
            palette.getAllPresent((x, y, z, value) -> {
                // Calls a method
                assertNotEquals(0, value, "air must never be reported as present");
                // Calls a method
                assertEquals(x == 0 && y == 0 && z == 0 ? 7 : 5, value);
                // Calls a method
                reported.incrementAndGet();
            // End of a block/expression
            });
            // Calls a method
            assertEquals(palette.maxSize(), reported.get());
            // Calls a method
            assertEquals(palette.count(), reported.get(), "getAllPresent must agree with count()");
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void getAllPresentNonAirFillThenAir() {
        // Carving a single air cell out of a non-air fill must exclude only that cell.
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.fill(5);
            // Calls a method
            palette.set(0, 0, 0, 0);
            // Calls a method
            AtomicInteger reported = new AtomicInteger();
            // Start of a method/block
            palette.getAllPresent((x, y, z, value) -> {
                // Calls a method
                assertEquals(5, value);
                // Calls a method
                assertFalse(x == 0 && y == 0 && z == 0, "the air cell must be excluded");
                // Calls a method
                reported.incrementAndGet();
            // End of a block/expression
            });
            // Calls a method
            assertEquals(palette.maxSize() - 1, reported.get());
            // Calls a method
            assertEquals(palette.count(), reported.get(), "getAllPresent must agree with count()");
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void replaceAll() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.setAll((x, y, z) -> x + y + z + 1);
            // Start of a method/block
            palette.replaceAll((x, y, z, value) -> {
                // Calls a method
                assertEquals(x + y + z + 1, value);
                // Returns a value to the caller
                return x + y + z + 2;
            // End of a block/expression
            });
            // Calls a method
            palette.getAll((x, y, z, value) -> assertEquals(x + y + z + 2, value));
        // End of a block/expression
        }

        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.fill(0);
            // Start of a method/block
            palette.replaceAll((x, y, z, value) -> {
                // Calls a method
                assertEquals(0, value);
                // Returns a value to the caller
                return value + 1;
            // End of a block/expression
            });
            // Calls a method
            palette.getAll((x, y, z, value) -> assertEquals(1, value));
        // End of a block/expression
        }

        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.fill(1);
            // Start of a method/block
            palette.replaceAll((x, y, z, value) -> {
                // Calls a method
                assertEquals(1, value);
                // Returns a value to the caller
                return value + 1;
            // End of a block/expression
            });
            // Calls a method
            palette.getAll((x, y, z, value) -> assertEquals(2, value));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void replaceUnary() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            palette.set(0, 0, 0, 1);
            // Start of a method/block
            palette.replace(0, 0, 0, operand -> {
                // Calls a method
                assertEquals(1, operand);
                // Returns a value to the caller
                return 2;
            // End of a block/expression
            });
            // Calls a method
            assertEquals(2, palette.get(0, 0, 0));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void replaceLoop() {
        // Calls a method
        var palette = Palette.sized(2, 1, 8, 15, 4);
        // Calls a method
        palette.setAll((x, y, z) -> x + y + z);
        // Calls a method
        final int dimension = palette.dimension();
        // Loop: repeats a block
        for (int x = 0; x < dimension; x++) {
            // Loop: repeats a block
            for (int y = 0; y < dimension; y++) {
                // Loop: repeats a block
                for (int z = 0; z < dimension; z++) {
                    // Calls a method
                    palette.replace(x, y, z, value -> value + 1);
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
    // Start of a method/block
    public void dimension() {
        // Calls a method
        assertThrows(Exception.class, () -> Palette.empty(-4, 5, 3, 15));
        // Calls a method
        assertThrows(Exception.class, () -> Palette.empty(0, 5, 3, 15));
        // Calls a method
        assertThrows(Exception.class, () -> Palette.empty(1, 5, 3, 15));
        // Calls a method
        assertDoesNotThrow(() -> Palette.empty(2, 5, 3, 15));
        // Calls a method
        assertThrows(Exception.class, () -> Palette.empty(3, 5, 3, 15));
        // Calls a method
        assertDoesNotThrow(() -> Palette.empty(4, 5, 3, 15));
        // Calls a method
        assertThrows(Exception.class, () -> Palette.empty(6, 5, 3, 15));
        // Calls a method
        assertDoesNotThrow(() -> Palette.empty(16, 5, 3, 15));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void serializationBlockEmpty() {
        // Calls a method
        NetworkBuffer buffer = NetworkBuffer.resizableBuffer();
        // Calls a method
        Palette palette = Palette.blocks();
        // Calls a method
        buffer.write(Palette.BLOCK_SERIALIZER, palette);

        // Calls a method
        Palette deserialized = buffer.read(Palette.BLOCK_SERIALIZER);
        // Calls a method
        assertTrue(palette.compare(deserialized));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void serializationBlockPalette() {
        // Calls a method
        NetworkBuffer buffer = NetworkBuffer.resizableBuffer();
        // Calls a method
        Palette palette = Palette.blocks();
        // Calls a method
        palette.set(0, 0, 0, 1);
        // Calls a method
        palette.set(1, 0, 0, 2);
        // Calls a method
        buffer.write(Palette.BLOCK_SERIALIZER, palette);

        // Calls a method
        Palette deserialized = buffer.read(Palette.BLOCK_SERIALIZER);
        // Calls a method
        assertTrue(palette.compare(deserialized));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void serializationBlockLinearMutation() {
        // Calls a method
        NetworkBuffer buffer = NetworkBuffer.resizableBuffer();
        // Calls a method
        Palette palette = Palette.blocks();
        // Calls a method
        palette.set(0, 0, 0, 1);
        // Calls a method
        palette.set(1, 0, 0, 2);

        // Calls a method
        buffer.write(Palette.BLOCK_SERIALIZER, palette);
        // Calls a method
        Palette deserialized = buffer.read(Palette.BLOCK_SERIALIZER);

        // Calls a method
        deserialized.set(2, 0, 0, 3);

        // Calls a method
        assertEquals(1, deserialized.get(0, 0, 0));
        // Calls a method
        assertEquals(2, deserialized.get(1, 0, 0));
        // Calls a method
        assertEquals(3, deserialized.get(2, 0, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void serializationBlockDirect() {
        // Calls a method
        NetworkBuffer buffer = NetworkBuffer.resizableBuffer();
        // Calls a method
        Random random = new Random(12345);
        // Calls a method
        Palette palette = Palette.blocks();
        // Calls a method
        palette.setAll((x, y, z) -> random.nextInt(2048));

        // Calls a method
        buffer.write(Palette.BLOCK_SERIALIZER, palette);

        // Calls a method
        Palette deserialized = buffer.read(Palette.BLOCK_SERIALIZER);
        // Calls a method
        assertTrue(palette.compare(deserialized));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void serializationBiomeEmpty() {
        // Calls a method
        final var serializer = Palette.biomeSerializer(128);
        // Calls a method
        NetworkBuffer buffer = NetworkBuffer.resizableBuffer();
        // Calls a method
        Palette palette = Palette.biomes();
        // Calls a method
        buffer.write(serializer, palette);

        // Calls a method
        Palette deserialized = buffer.read(serializer);
        // Calls a method
        assertTrue(palette.compare(deserialized));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void serializationBiomePalette() {
        // Calls a method
        final var serializer = Palette.biomeSerializer(128);
        // Calls a method
        NetworkBuffer buffer = NetworkBuffer.resizableBuffer();
        // Calls a method
        Palette palette = Palette.biomes();
        // Calls a method
        palette.set(0, 0, 0, 1);
        // Calls a method
        palette.set(1, 0, 0, 2);
        // Calls a method
        buffer.write(serializer, palette);

        // Calls a method
        Palette deserialized = buffer.read(serializer);
        // Calls a method
        assertTrue(palette.compare(deserialized));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void serializationBiomeDirect() {
        // Calls a method
        final var serializer = Palette.biomeSerializer(128);
        // Calls a method
        NetworkBuffer buffer = NetworkBuffer.resizableBuffer();
        // Calls a method
        Palette palette = Palette.biomes();
        // Calls a method
        Random random = new Random(12345);
        // Calls a method
        palette.setAll((x, y, z) -> random.nextInt(2048));

        // Calls a method
        buffer.write(serializer, palette);

        // Calls a method
        Palette deserialized = buffer.read(serializer);
        // Calls a method
        assertTrue(palette.compare(deserialized));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void loadBelowMinBitsPerEntry() {
        // Test loading with bpe below minBitsPerEntry - should resize to minBitsPerEntry
        // Assigns a value
        Palette palette = Palette.sized(4, 4, 8, 15, 4); // min=4, max=8, direct=15

        // Assigns a value
        int[] paletteData = {0, 1, 2, 3}; // 4 values need 2 bits, but min is 4
        // Assigns a value
        long[] values = new long[]{0x3210L}; // packed with 2 bits per entry

        // Calls a method
        palette.load(paletteData, values);

        // Should be resized to minBitsPerEntry (4)
        // Calls a method
        assertEquals(4, palette.bitsPerEntry());

        // Values should still be accessible correctly
        // Calls a method
        assertEquals(0, palette.get(0, 0, 0));
        // Calls a method
        assertEquals(1, palette.get(1, 0, 0));
        // Calls a method
        assertEquals(2, palette.get(2, 0, 0));
        // Calls a method
        assertEquals(3, palette.get(3, 0, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void loadAboveMaxBitsPerEntry() {
        // Test loading with bpe above maxBitsPerEntry - should become direct palette
        // Assigns a value
        Palette palette = Palette.sized(4, 1, 3, 15, 1); // min=1, max=3, direct=15

        // Create palette that would need more than 3 bits (max) - 16 values need 4 bits
        // Assigns a value
        int[] paletteData = new int[16];
        // Loop: repeats a block
        for (int i = 0; i < 16; i++) {
            // Assigns a value
            paletteData[i] = i + 100; // arbitrary values
        // End of a block/expression
        }

        // Create values array with 4 bits per entry
        // Assigns a value
        long[] values = new long[4]; // 64 entries, 4 bits each = 16 longs per entry, 4 longs total
        // Loop: repeats a block
        for (int i = 0; i < 64; i++) {
            // Assigns a value
            int longIndex = i / 16;
            // Calls a method
            int bitIndex = (i % 16) * 4;
            // Calls a method
            values[longIndex] |= ((long) (i % 16)) << bitIndex;
        // End of a block/expression
        }

        // Calls a method
        palette.load(paletteData, values);

        // Should become direct palette (directBits = 15)
        // Calls a method
        assertEquals(15, palette.bitsPerEntry());

        // Should not have a palette anymore (direct mode)
        // Calls a method
        assertNull(((PaletteImpl) palette).paletteToValueList);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void loadWithinRange() {
        // Test loading with bpe within min-max range - should use calculated bpe
        // Assigns a value
        Palette palette = Palette.sized(4, 2, 6, 15, 2); // min=2, max=6, direct=15

        // Assigns a value
        int[] paletteData = {0, 10, 20, 30, 40}; // 5 values need 3 bits
        // Assigns a value
        long[] values = new long[12]; // 64 entries, 3 bits each

        // Fill with some test pattern
        // Loop: repeats a block
        for (int i = 0; i < 64; i++) {
            // Assigns a value
            int longIndex = i / 21; // 21 values per long with 3 bits each (63 bits used)
            // Calls a method
            int bitIndex = (i % 21) * 3;
            // Branch: checks a condition
            if (longIndex < values.length) {
                // Calls a method
                values[longIndex] |= ((long) (i % 5)) << bitIndex;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        palette.load(paletteData, values);

        // Should use 3 bits (calculated from palette size)
        // Calls a method
        assertEquals(3, palette.bitsPerEntry());

        // Should have palette
        // Calls a method
        assertNotNull(((PaletteImpl) palette).paletteToValueList);

        // Verify palette contents
        // Calls a method
        assertEquals(5, ((PaletteImpl) palette).paletteToValueList.size());
        // Calls a method
        assertEquals(0, ((PaletteImpl) palette).paletteToValueList.getInt(0));
        // Calls a method
        assertEquals(10, ((PaletteImpl) palette).paletteToValueList.getInt(1));
        // Calls a method
        assertEquals(20, ((PaletteImpl) palette).paletteToValueList.getInt(2));
        // Calls a method
        assertEquals(30, ((PaletteImpl) palette).paletteToValueList.getInt(3));
        // Calls a method
        assertEquals(40, ((PaletteImpl) palette).paletteToValueList.getInt(4));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void loadExactlyMinBitsPerEntry() {
        // Test loading where calculated bpe equals minBitsPerEntry
        // Assigns a value
        Palette palette = Palette.sized(4, 3, 8, 15, 3); // min=3, max=8, direct=15

        // Assigns a value
        int[] paletteData = {0, 1, 2, 3, 4, 5, 6, 7}; // 8 values need exactly 3 bits
        // Assigns a value
        long[] values = new long[12]; // 64 entries, 3 bits each

        // Calls a method
        palette.load(paletteData, values);

        // Should use exactly minBitsPerEntry (3)
        // Calls a method
        assertEquals(3, palette.bitsPerEntry());

        // Should have palette
        // Calls a method
        assertNotNull(((PaletteImpl) palette).paletteToValueList);
        // Calls a method
        assertEquals(8, ((PaletteImpl) palette).paletteToValueList.size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void loadExactlyMaxBitsPerEntry() {
        // Test loading where calculated bpe equals maxBitsPerEntry
        // Assigns a value
        Palette palette = Palette.sized(4, 2, 4, 15, 2); // min=2, max=4, direct=15

        // Assigns a value
        int[] paletteData = new int[16]; // 16 values need exactly 4 bits
        // Loop: repeats a block
        for (int i = 0; i < 16; i++) {
            // Assigns a value
            paletteData[i] = i * 10;
        // End of a block/expression
        }
        // Assigns a value
        long[] values = new long[16]; // 64 entries, 4 bits each

        // Calls a method
        palette.load(paletteData, values);

        // Should use exactly maxBitsPerEntry (4)
        // Calls a method
        assertEquals(4, palette.bitsPerEntry());

        // Should still have palette (not direct)
        // Calls a method
        assertNotNull(((PaletteImpl) palette).paletteToValueList);
        // Calls a method
        assertEquals(16, ((PaletteImpl) palette).paletteToValueList.size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void loadEmptyPalette() {
        // Test loading with empty palette
        // Calls a method
        Palette palette = Palette.sized(4, 1, 8, 15, 1);

        // Assigns a value
        int[] paletteData = {0}; // Single value palette
        // Assigns a value
        long[] values = new long[4]; // All zeros

        // Calls a method
        palette.load(paletteData, values);

        // Should use minBitsPerEntry since 1 value needs 0 bits but min is 1
        // Calls a method
        assertEquals(1, palette.bitsPerEntry());

        // Should have palette with single entry
        // Calls a method
        assertNotNull(((PaletteImpl) palette).paletteToValueList);
        // Calls a method
        assertEquals(1, ((PaletteImpl) palette).paletteToValueList.size());
        // Calls a method
        assertEquals(0, ((PaletteImpl) palette).paletteToValueList.getInt(0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void loadValuesCloned() {
        // Test that values array is properly cloned
        // Calls a method
        Palette palette = Palette.sized(4, 2, 6, 15, 2);

        // Assigns a value
        int[] paletteData = {0, 1, 2};
        // Assigns a value
        long[] originalValues = {0x123456789ABCDEFL, 0xFEDCBA9876543210L};

        // Calls a method
        palette.load(paletteData, originalValues);

        // Modify original array
        // Assigns a value
        originalValues[0] = 0L;
        // Assigns a value
        originalValues[1] = 0L;

        // Palette should still have the original values
        // Calls a method
        long[] paletteValues = palette.indexedValues();
        // Calls a method
        assertNotNull(paletteValues);
        // Calls a method
        assertEquals(0x123456789ABCDEFL, paletteValues[0]);
        // Calls a method
        assertEquals(0xFEDCBA9876543210L, paletteValues[1]);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void loadThousandsOfIndicesBecomesDirectPalette() {
        // Test loading with thousands of indices to ensure it becomes a direct palette
        // Assigns a value
        Palette palette = Palette.blocks(); // min=4, max=8, direct=15

        // Create palette with thousands of unique values (way more than max palette size of 2^8=256)
        // Assigns a value
        final int uniqueValueCount = 5000;
        // Assigns a value
        int[] paletteData = new int[uniqueValueCount];
        // Loop: repeats a block
        for (int i = 0; i < uniqueValueCount; i++) {
            // Assigns a value
            paletteData[i] = i + 1000; // Use offset to avoid zero values
        // End of a block/expression
        }

        // Calculate bits needed: log2(5000) ≈ 13 bits, which exceeds maxBitsPerEntry (8)
        // This should force direct palette mode
        // Assigns a value
        int calculatedBits = 13; // Math.ceil(Math.log(uniqueValueCount) / Math.log(2))

        // Create values array for 4096 entries (16x16x16) with calculated bits per entry
        // Assigns a value
        final int totalEntries = 16 * 16 * 16; // 4096 entries
        // Assigns a value
        final int valuesPerLong = 64 / calculatedBits;
        // Calls a method
        final int valuesArrayLength = (totalEntries + valuesPerLong - 1) / valuesPerLong;
        // Assigns a value
        long[] values = new long[valuesArrayLength];

        // Fill with pattern using modulo to cycle through available palette indices
        // Calls a method
        final long mask = (1L << calculatedBits) - 1;
        // Loop: repeats a block
        for (int i = 0; i < totalEntries; i++) {
            // Assigns a value
            int paletteIndex = i % uniqueValueCount;
            // Assigns a value
            int longIndex = i / valuesPerLong;
            // Calls a method
            int bitIndex = (i % valuesPerLong) * calculatedBits;
            // Calls a method
            values[longIndex] |= ((long) paletteIndex & mask) << bitIndex;
        // End of a block/expression
        }

        // Calls a method
        palette.load(paletteData, values);

        // Should become direct palette since uniqueValueCount >> 2^maxBitsPerEntry
        // Code statement
        assertEquals(Palette.BLOCK_PALETTE_DIRECT_BITS, palette.bitsPerEntry(),
                // Code statement
                "Palette should use direct bits when loaded with thousands of indices");

        // Should not have indirect palette structures (direct mode)
        // Calls a method
        PaletteImpl impl = (PaletteImpl) palette;
        // Code statement
        assertNull(impl.paletteToValueList,
                // Code statement
                "Direct palette should not have paletteToValueList");

        // Verify we can still read some values correctly
        // In direct mode, palette indices become the actual values
        // Calls a method
        int firstValue = palette.get(0, 0, 0);
        // Code statement
        assertTrue(firstValue >= 1000 && firstValue < 1000 + uniqueValueCount,
                // Code statement
                "Value should be within expected range for direct palette: " + firstValue);

        // Verify the palette has proper count (non-zero blocks)
        // Calls a method
        assertTrue(palette.count() > 0, "Palette should have non-zero count");
        // Calls a method
        assertTrue(palette.count() <= palette.maxSize(), "Count should not exceed max size");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void height() {
        // Loop: repeats a block
        for (Palette palette : testPalettes()) {
            // Calls a method
            final int dimension = palette.dimension();

            // Test with empty palette - predicate that always returns true should find the
            // top
            // Calls a method
            assertEquals(dimension - 1, palette.height(0, 0, (x, y, z, value) -> true));
            // Predicate that always returns false should return -1
            // Calls a method
            assertEquals(-1, palette.height(0, 0, (x, y, z, value) -> false));

            // Set a block at the top
            // Calls a method
            palette.set(0, dimension - 1, 0, 1);
            // Calls a method
            assertEquals(dimension - 1, palette.height(0, 0, (x, y, z, value) -> value != 0));

            // Set a block in the middle
            // Branch: checks a condition
            if (dimension > 1) {
                // Calls a method
                palette.set(1, dimension / 2, 1, 2);
                // Calls a method
                assertEquals(dimension / 2, palette.height(1, 1, (x, y, z, value) -> value != 0));
            // End of a block/expression
            }

            // Set blocks at multiple heights - should return the highest one
            // Branch: checks a condition
            if (dimension > 2) {
                // Calls a method
                palette.set(2, 1, 2, 3);
                // Calls a method
                palette.set(2, dimension - 2, 2, 4);
                // Calls a method
                assertEquals(dimension - 2, palette.height(2, 2, (x, y, z, value) -> value != 0));
            // End of a block/expression
            }

            // Test with predicate that matches air (value 0)
            // Code statement
            palette.fill(5); // Fill with non-zero value
            // Calls a method
            int testX = Math.min(1, dimension - 1);
            // Calls a method
            int testZ = Math.min(1, dimension - 1);
            // Code statement
            palette.set(testX, dimension / 2, testZ, 0); // Set one block to air
            // Calls a method
            assertEquals(dimension / 2, palette.height(testX, testZ, (x, y, z, value) -> value == 0));

            // Test edge cases - coordinates at boundaries
            // Calls a method
            palette.fill(0);
            // Calls a method
            palette.set(dimension - 1, dimension - 1, dimension - 1, 10);
            // Calls a method
            assertEquals(dimension - 1, palette.height(dimension - 1, dimension - 1, (x, y, z, value) -> value != 0));

            // Test with complex predicate
            // Calls a method
            palette.fill(0);
            // Loop: repeats a block
            for (int y = 0; y < dimension; y++) {
                // Calls a method
                palette.set(0, y, 0, y + 1);
            // End of a block/expression
            }
            // Find highest block with value > 5
            // Assigns a value
            int expectedHeight = -1;
            // Loop: repeats a block
            for (int y = dimension - 1; y >= 0; y--) {
                // Branch: checks a condition
                if (y + 1 > 5) {
                    // Assigns a value
                    expectedHeight = y;
                    // Breaks out of the loop/block
                    break;
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Calls a method
            assertEquals(expectedHeight, palette.height(0, 0, (x, y, z, value) -> value > 5));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void heightValidation() {
        // Calls a method
        Palette palette = Palette.blocks();
        // Calls a method
        final int dimension = palette.dimension();

        // Test invalid coordinates
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> palette.height(-1, 0, (x, y, z, value) -> true));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> palette.height(0, -1, (x, y, z, value) -> true));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> palette.height(dimension, 0, (x, y, z, value) -> true));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> palette.height(0, dimension, (x, y, z, value) -> true));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void heightOptimization() {
        // Test single-value palette optimization
        // Calls a method
        Palette singleValuePalette = Palette.blocks();
        // Calls a method
        singleValuePalette.fill(42);
        
        // Should find the value at the top
        // Calls a method
        assertEquals(15, singleValuePalette.height(0, 0, (x, y, z, value) -> value == 42));
        // Calls a method
        assertEquals(-1, singleValuePalette.height(0, 0, (x, y, z, value) -> value == 0));
        
        // Test multi-value palette optimization
        // Calls a method
        Palette multiValuePalette = Palette.blocks();
        // Calls a method
        multiValuePalette.set(5, 10, 5, 100);
        // Calls a method
        multiValuePalette.set(5, 8, 5, 200);
        // Calls a method
        multiValuePalette.set(5, 12, 5, 300);
        
        // Should find the highest matching block
        // Calls a method
        assertEquals(12, multiValuePalette.height(5, 5, (x, y, z, value) -> value != 0));
        // Calls a method
        assertEquals(10, multiValuePalette.height(5, 5, (x, y, z, value) -> value == 100));
        // Calls a method
        assertEquals(8, multiValuePalette.height(5, 5, (x, y, z, value) -> value == 200));
        // Calls a method
        assertEquals(12, multiValuePalette.height(5, 5, (x, y, z, value) -> value == 300));
        // Calls a method
        assertEquals(-1, multiValuePalette.height(5, 5, (x, y, z, value) -> value == 999));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void count() {
        // Calls a method
        Palette testPalette = Palette.blocks();
        // Calls a method
        testPalette.fill(5000);
        // Calls a method
        assertEquals(4096, testPalette.count());

        // Should correctly count
        // Calls a method
        testPalette.set(0, 0, 0, 0);
        // Calls a method
        testPalette.set(0, 0, 1, 1);
        // Calls a method
        testPalette.set(0, 0, 2, 2);
        // Calls a method
        testPalette.set(0, 0, 3, 3);
        // Calls a method
        assertEquals(4095, testPalette.count());

        // Calls a method
        testPalette.set(0, 0, 0, 5000);
        // Calls a method
        assertEquals(4096, testPalette.count());

        // Calls a method
        testPalette.replace(5000, 0);
        // Calls a method
        assertEquals(3, testPalette.count());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void loadCount() {
        // Calls a method
        Palette testPalette = Palette.empty(4, 4, 8, 12);
        // Assigns a value
        int[] palette = new int[] { 10, 2, 4, 0 };
        // 12 palette values that lead to 0 and 6 zeroed palette values
        // Assigns a value
        long[] values = new long[] { 0x01230123, 0x00130013, 0x33333333, 0x22222222 };
        // Calls a method
        testPalette.load(palette, values);
        // Calls a method
        assertEquals(testPalette.maxSize() - 12, testPalette.count());
    // End of a block/expression
    }

// End of a block/expression
}
