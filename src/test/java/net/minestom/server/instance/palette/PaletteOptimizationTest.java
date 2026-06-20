// Package declaration for this file
package net.minestom.server.instance.palette;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Random;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;

// Type declaration (class/interface/enum/record)
public class PaletteOptimizationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void empty() {
        // Calls a method
        var palette = createPalette();
        // Calls a method
        paletteEqualsOptimized(palette);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void single() {
        // Calls a method
        var palette = createPalette();
        // Calls a method
        palette.set(0, 0, 0, 1);
        // Calls a method
        paletteEqualsOptimized(palette);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void random() {
        // Calls a method
        var random = new Random(12345);
        // Calls a method
        var palette = createPalette();
        // Calls a method
        palette.setAll((x, y, z) -> random.nextInt(256));
        // Calls a method
        paletteEqualsOptimized(palette);
        // Calls a method
        palette.setAll((x, y, z) -> random.nextInt(2));
        // Calls a method
        paletteEqualsOptimized(palette);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void manualFill() {
        // Calls a method
        var palette = createPalette();
        // Calls a method
        palette.setAll((x, y, z) -> 1);
        // Calls a method
        paletteEqualsOptimized(palette);
        // Calls a method
        palette.setAll((x, y, z) -> 2);
        // Calls a method
        paletteEqualsOptimized(palette);
        // Calls a method
        palette.setAll((x, y, z) -> 0);
        // Calls a method
        paletteEqualsOptimized(palette);
    // End of a block/expression
    }

    // Start of a method/block
    PaletteImpl createPalette() {
        // Returns a value to the caller
        return (PaletteImpl) Palette.blocks();
    // End of a block/expression
    }

    // Start of a method/block
    Palette optimized(Palette palette, Palette.Optimization optimization) {
        // Calls a method
        palette = palette.clone();
        // Calls a method
        palette.optimize(optimization);
        // Returns a value to the caller
        return palette;
    // End of a block/expression
    }

    // Start of a method/block
    void paletteEqualsOptimized(Palette palette) {
        // Calls a method
        paletteEquals(palette, optimized(palette, Palette.Optimization.SIZE), true);
        // Calls a method
        paletteEquals(palette, optimized(palette, Palette.Optimization.SPEED), false);
    // End of a block/expression
    }

    // Start of a method/block
    void paletteEquals(Palette palette, Palette optimized, boolean sizeCompare) {
        // Calls a method
        assertTrue(palette.compare(optimized));
        // Branch: checks a condition
        if (sizeCompare) {
            // Calls a method
            var array = NetworkBuffer.makeArray(Palette.BLOCK_SERIALIZER, palette);
            // Assigns a value
            int length1 = array.length;
            // Calls a method
            array = NetworkBuffer.makeArray(Palette.BLOCK_SERIALIZER, optimized);
            // Assigns a value
            int length2 = array.length;
            // Calls a method
            assertTrue(length1 >= length2, "Optimized palette is bigger than the original one: " + length1 + " : " + length2);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
