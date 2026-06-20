// Package declaration for this file
package net.minestom.server.map.framebuffers;

// Import of a required class
import net.minestom.server.map.Framebuffer;
// Import of a required class
import net.minestom.server.map.MapColors;

// Import of a required class
import java.awt.*;
// Import of a required class
import java.awt.image.BufferedImage;
// Import of a required class
import java.awt.image.DataBufferInt;

/**
 * {@link Framebuffer} that embeds a BufferedImage, allowing for rendering directly via Graphics2D or its pixel array.
 */
// Type declaration (class/interface/enum/record)
public class Graphics2DFramebuffer implements Framebuffer {

    // Assigns a value
    private final byte[] colors = new byte[WIDTH * HEIGHT];
    // Calls a method
    private final BufferedImage backingImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    // Code statement
    private final Graphics2D renderer;
    // Code statement
    private final int[] pixels;

    // Start of a method/block
    public Graphics2DFramebuffer() {
        // Calls a method
        renderer = backingImage.createGraphics();
        // Calls a method
        pixels = ((DataBufferInt) backingImage.getRaster().getDataBuffer()).getData();
    // End of a block/expression
    }

    // Start of a method/block
    public Graphics2D getRenderer() {
        // Returns a value to the caller
        return renderer;
    // End of a block/expression
    }

    // Start of a method/block
    public BufferedImage getBackingImage() {
        // Returns a value to the caller
        return backingImage;
    // End of a block/expression
    }

    // Start of a method/block
    public int get(int x, int z) {
        // Returns a value to the caller
        return pixels[x + z * WIDTH]; // stride is always the width of the image
    // End of a block/expression
    }

    // Start of a method/block
    public Graphics2DFramebuffer set(int x, int z, int rgb) {
        // Assigns a value
        pixels[x + z * WIDTH] = rgb;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public byte[] toMapColors() {
        // TODO: update subparts only
        // Loop: repeats a block
        for (int x = 0; x < 128; x++) {
            // Loop: repeats a block
            for (int z = 0; z < 128; z++) {
                // Calls a method
                colors[Framebuffer.index(x, z)] = MapColors.closestColor(get(x, z)).getIndex();
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
