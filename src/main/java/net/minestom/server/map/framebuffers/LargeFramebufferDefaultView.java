// Package declaration for this file
package net.minestom.server.map.framebuffers;

// Import of a required class
import net.minestom.server.map.Framebuffer;
// Import of a required class
import net.minestom.server.map.LargeFramebuffer;
// Import of a required class
import net.minestom.server.map.MapColors;

// Type declaration (class/interface/enum/record)
public class LargeFramebufferDefaultView implements Framebuffer {
    // Code statement
    private final LargeFramebuffer parent;
    // Code statement
    private final int x;
    // Code statement
    private final int y;
    // Assigns a value
    private final byte[] colors = new byte[WIDTH*HEIGHT];

    // Start of a method/block
    public LargeFramebufferDefaultView(LargeFramebuffer parent, int x, int y) {
        // Access to the current/parent object
        this.parent = parent;
        // Access to the current/parent object
        this.x = x;
        // Access to the current/parent object
        this.y = y;
    // End of a block/expression
    }

    // Start of a method/block
    private boolean bounds(int x, int y) {
        // Returns a value to the caller
        return x >= 0 && x < parent.width() && y >= 0 && y < parent.height();
    // End of a block/expression
    }

    // Start of a method/block
    private byte colorOrNone(int x, int y) {
        // Branch: checks a condition
        if(!bounds(x, y)) return MapColors.NONE.baseColor();
        // Returns a value to the caller
        return parent.getMapColor(x, y);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public byte[] toMapColors() {
        // Loop: repeats a block
        for (int y = 0; y < HEIGHT; y++) {
            // Loop: repeats a block
            for (int x = 0; x < WIDTH; x++) {
                // Calls a method
                colors[Framebuffer.index(x, y)] = colorOrNone(x+this.x, y+this.y);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return colors;
    // End of a block/expression
    }
// End of a block/expression
}
