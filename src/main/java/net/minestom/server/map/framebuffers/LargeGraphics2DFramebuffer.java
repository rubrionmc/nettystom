// Package declaration for this file
package net.minestom.server.map.framebuffers;

// Import of a required class
import net.minestom.server.map.Framebuffer;
// Import of a required class
import net.minestom.server.map.LargeFramebuffer;
// Import of a required class
import net.minestom.server.map.MapColors;

// Import of a required class
import java.awt.*;
// Import of a required class
import java.awt.image.BufferedImage;
// Import of a required class
import java.awt.image.DataBufferInt;

/**
 * {@link LargeFramebuffer} that embeds a {@link BufferedImage},
 * allowing for rendering directly via {@link Graphics2D} or its pixel array.
 */
// Type declaration (class/interface/enum/record)
public class LargeGraphics2DFramebuffer implements LargeFramebuffer {

    // Code statement
    private final BufferedImage backingImage;
    // Code statement
    private final Graphics2D renderer;
    // Code statement
    private final int[] pixels;
    // Code statement
    private final int width;
    // Code statement
    private final int height;

    // Start of a method/block
    public LargeGraphics2DFramebuffer(int width, int height) {
        // Access to the current/parent object
        this.width = width;
        // Access to the current/parent object
        this.height = height;
        // Calls a method
        backingImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
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
        return pixels[x + z * width]; // stride is always the width of the image
    // End of a block/expression
    }

    // Start of a method/block
    public LargeGraphics2DFramebuffer set(int x, int z, int rgb) {
        // Assigns a value
        pixels[x + z * width] = rgb;
        // Returns a value to the caller
        return this;
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

    // Annotation for the following element
    @Override
    // Start of a method/block
    public byte getMapColor(int x, int y) {
        // Returns a value to the caller
        return MapColors.closestColor(get(x, y)).getIndex();
    // End of a block/expression
    }
// End of a block/expression
}
