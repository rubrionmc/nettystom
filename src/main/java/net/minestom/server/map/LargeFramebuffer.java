// Package declaration for this file
package net.minestom.server.map;

// Import of a required class
import net.minestom.server.network.packet.server.play.MapDataPacket;

// Import of a required class
import java.util.List;

/**
 * Framebuffer that is meant to be split in sub-framebuffers.
 * Contrary to {@link Framebuffer}, LargeFramebuffer supports sizes over 128x128 pixels.
 */
// Type declaration (class/interface/enum/record)
public interface LargeFramebuffer {

    // Calls a method
    int width();

    // Calls a method
    int height();

    /**
     * Returns a new {@link Framebuffer} that represent a 128x128 sub-view of this framebuffer.
     * Implementations are free (but not guaranteed) to throw exceptions if left &amp; top produces out-of-bounds coordinates.
     *
     * @param left
     * @param top
     * @return the sub-view {@link Framebuffer}
     */
    // Calls a method
    Framebuffer createSubView(int left, int top);

    // Calls a method
    byte getMapColor(int x, int y);

    /**
     * Prepares the packet to render a 128x128 sub view of this framebuffer
     */
    // Start of a method/block
    default MapDataPacket preparePacket(int mapId, int left, int top) {
        // Assigns a value
        byte[] colors = new byte[Framebuffer.WIDTH * Framebuffer.WIDTH];
        // Calls a method
        final int width = Math.min(width(), left + Framebuffer.WIDTH) - left;
        // Calls a method
        final int height = Math.min(height(), top + Framebuffer.HEIGHT) - top;
        // Loop: repeats a block
        for (int y = top; y < top+height; y++) {
            // Loop: repeats a block
            for (int x = left; x < left+width; x++) {
                // Calls a method
                final byte color = getMapColor(x, y);
                // Calls a method
                colors[Framebuffer.index(x - left, y - top)] = color;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return new MapDataPacket(mapId, (byte) 0, false,
                // Code statement
                false, List.of(),
                // Creates a new object
                new MapDataPacket.ColorContent((byte) width, (byte) height,
                        // Code statement
                        (byte) 0, (byte) 0,
                        // Code statement
                        colors));
    // End of a block/expression
    }
// End of a block/expression
}
