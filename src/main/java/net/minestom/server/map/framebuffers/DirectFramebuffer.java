// Package declaration for this file
package net.minestom.server.map.framebuffers;

// Import of a required class
import net.minestom.server.map.Framebuffer;

/**
 * {@link Framebuffer} with direct access to the colors array
 */
// Type declaration (class/interface/enum/record)
public class DirectFramebuffer implements Framebuffer {

    // Assigns a value
    private final byte[] colors = new byte[WIDTH * HEIGHT];

    /**
     * Mutable colors array
     *
     * @return
     */
    // Start of a method/block
    public byte[] getColors() {
        // Returns a value to the caller
        return colors;
    // End of a block/expression
    }

    // Start of a method/block
    public byte get(int x, int z) {
        // Returns a value to the caller
        return colors[Framebuffer.index(x, z)];
    // End of a block/expression
    }

    // Start of a method/block
    public DirectFramebuffer set(int x, int z, byte color) {
        // Calls a method
        colors[Framebuffer.index(x, z)] = color;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public byte[] toMapColors() {
        // Returns a value to the caller
        return colors;
    // End of a block/expression
    }
// End of a block/expression
}
