// Package declaration for this file
package net.minestom.server.map;

// Import of a required class
import net.minestom.server.network.packet.server.play.MapDataPacket;

// Import of a required class
import java.util.List;

/**
 * Framebuffer to render to a map
 */
// Type declaration (class/interface/enum/record)
public interface Framebuffer {

    // Assigns a value
    int WIDTH = 128;
    // Assigns a value
    int HEIGHT = 128;

    // Calls a method
    byte[] toMapColors();

    // Start of a method/block
    default MapDataPacket preparePacket(int mapId) {
        // Returns a value to the caller
        return preparePacket(mapId, 0, 0, WIDTH, HEIGHT);
    // End of a block/expression
    }

    // Start of a method/block
    default MapDataPacket preparePacket(int mapId, int minX, int minY, int width, int height) {
        // Code statement
        byte[] colors;
        // Branch: checks a condition
        if (minX == 0 && minY == 0 && width == WIDTH && height == HEIGHT) {
            // Calls a method
            colors = toMapColors();
        // Alternative branch of the condition
        } else {
            // Assigns a value
            colors = new byte[width * height];
            // Calls a method
            final byte[] mapColors = toMapColors();
            // Loop: repeats a block
            for (int y = minY; y < Math.min(HEIGHT, minY + height); y++) {
                // Loop: repeats a block
                for (int x = minX; x < Math.min(WIDTH, minX + width); x++) {
                    // Calls a method
                    byte color = mapColors[index(x, y, WIDTH)];
                    // Calls a method
                    colors[index(x - minX, y - minY, width)] = color;
                // End of a block/expression
                }
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
                        (byte) minX, (byte) minY,
                        // Code statement
                        colors));
    // End of a block/expression
    }

    // Start of a method/block
    static int index(int x, int z) {
        // Returns a value to the caller
        return index(x, z, WIDTH);
    // End of a block/expression
    }

    // Start of a method/block
    static int index(int x, int z, int stride) {
        // Returns a value to the caller
        return z * stride + x;
    // End of a block/expression
    }

// End of a block/expression
}
