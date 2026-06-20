// Package declaration for this file
package net.minestom.server.collision;

// Import of a required class
import net.minestom.server.coordinate.BlockVec;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

/**
 * A block-aligned, absolute bounding box.
 *
 * <p>This is in contrast to BoundingBox which is relative to its owner's position, and precise.</p>
 */
// Type declaration (class/interface/enum/record)
public record BlockBoundingBox(Point min, Point max) {
    // Assigns a value
    public static final NetworkBuffer.Type<BlockBoundingBox> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.BLOCK_POSITION, BlockBoundingBox::min,
            // Code statement
            NetworkBuffer.BLOCK_POSITION, BlockBoundingBox::max,
            // Code statement
            BlockBoundingBox::new);

    // Start of a method/block
    public BlockBoundingBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        // Calls a method
        this(new BlockVec(minX, minY, minZ), new BlockVec(maxX, maxY, maxZ));
    // End of a block/expression
    }

    // Start of a method/block
    public int minX() {
        // Returns a value to the caller
        return min.blockX();
    // End of a block/expression
    }

    // Start of a method/block
    public int minY() {
        // Returns a value to the caller
        return min.blockY();
    // End of a block/expression
    }

    // Start of a method/block
    public int minZ() {
        // Returns a value to the caller
        return min.blockZ();
    // End of a block/expression
    }

    // Start of a method/block
    public int maxX() {
        // Returns a value to the caller
        return max.blockX();
    // End of a block/expression
    }

    // Start of a method/block
    public int maxY() {
        // Returns a value to the caller
        return max.blockY();
    // End of a block/expression
    }

    // Start of a method/block
    public int maxZ() {
        // Returns a value to the caller
        return max.blockZ();
    // End of a block/expression
    }

// End of a block/expression
}
