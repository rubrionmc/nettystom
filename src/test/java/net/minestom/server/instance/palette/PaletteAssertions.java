// Package declaration for this file
package net.minestom.server.instance.palette;

// Import of a required class
import java.util.List;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
final class PaletteAssertions {
    // Start of a method/block
    private PaletteAssertions() {
    // End of a block/expression
    }

    // Start of a method/block
    static List<Palette> testPalettes() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                Palette.sized(2, 1, 5, 15, 3),
                // Code statement
                Palette.sized(4, 1, 5, 15, 3),
                // Code statement
                Palette.sized(8, 1, 5, 15, 3),
                // Code statement
                Palette.sized(16, 1, 5, 15, 3),
                // Code statement
                Palette.blocks()
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Start of a method/block
    static void assertAllEquals(int expected, Palette palette) {
        // Calls a method
        final int dim = palette.dimension();
        // Loop: repeats a block
        for (int y = 0; y < dim; y++) {
            // Loop: repeats a block
            for (int z = 0; z < dim; z++) {
                // Loop: repeats a block
                for (int x = 0; x < dim; x++) {
                    // Code statement
                    assertEquals(expected, palette.get(x, y, z),
                            // Calls a method
                            "Mismatch at (" + x + "," + y + "," + z + ")");
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
