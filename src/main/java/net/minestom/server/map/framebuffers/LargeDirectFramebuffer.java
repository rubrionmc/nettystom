// Package declaration for this file
package net.minestom.server.map.framebuffers;

// Import of a required class
import net.minestom.server.map.Framebuffer;
// Import of a required class
import net.minestom.server.map.LargeFramebuffer;
// Import of a required class
import net.minestom.server.map.MapColors;

/**
 * {@link LargeFramebuffer} with direct access to the colors array.
 * <p>
 * This implementation does not throw errors when accessing out-of-bounds coordinates through sub-views, and will instead
 * use {@link MapColors#NONE}. This is only the case for sub-views, access through {@link #setMapColor(int, int, byte)}
 * and {@link #getMapColor(int, int)} will throw an exception if out-of-bounds coordinates are inputted.
 */
// Type declaration (class/interface/enum/record)
public class LargeDirectFramebuffer implements LargeFramebuffer {

    // Code statement
    private final int width;
    // Code statement
    private final int height;
    // Code statement
    private final byte[] colors;

    /**
     * Creates a new {@link LargeDirectFramebuffer} with the desired size
     *
     * @param width
     * @param height
     */
    // Start of a method/block
    public LargeDirectFramebuffer(int width, int height) {
        // Access to the current/parent object
        this.width = width;
        // Access to the current/parent object
        this.height = height;
        // Access to the current/parent object
        this.colors = new byte[width * height];
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int width() {
        // Returns a value to the caller
        return width;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int height() {
        // Returns a value to the caller
        return height;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Framebuffer createSubView(int left, int top) {
        // Returns a value to the caller
        return new LargeFramebufferDefaultView(this, left, top);
    // End of a block/expression
    }

    // Start of a method/block
    public LargeDirectFramebuffer setMapColor(int x, int y, byte color) {
        // Branch: checks a condition
        if (!bounds(x, y)) throw new IndexOutOfBoundsException("Invalid x;y coordinate: " + x + ";" + y);
        // Assigns a value
        colors[y * width + x] = color;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public byte getMapColor(int x, int y) {
        // Branch: checks a condition
        if (!bounds(x, y)) throw new IndexOutOfBoundsException("Invalid x;y coordinate: " + x + ";" + y);
        // Returns a value to the caller
        return colors[y * width + x];
    // End of a block/expression
    }

    // Start of a method/block
    private boolean bounds(int x, int y) {
        // Returns a value to the caller
        return x >= 0 && x < width && y >= 0 && y < height;
    // End of a block/expression
    }

    // Start of a method/block
    public byte[] getColors() {
        // Returns a value to the caller
        return colors;
    // End of a block/expression
    }
// End of a block/expression
}
