// Package declaration for this file
package net.minestom.server.instance.heightmap;

// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.block.Block;

// Type declaration (class/interface/enum/record)
public class MotionBlockingHeightmap extends Heightmap {
    // Start of a method/block
    public MotionBlockingHeightmap(Chunk attachedChunk) {
        // Access to the current/parent object
        super(attachedChunk);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected boolean checkBlock(Block block) {
        // Returns a value to the caller
        return (block.isSolid() && !block.compare(Block.COBWEB) && !block.compare(Block.BAMBOO_SAPLING))
                // Code statement
                || block.isLiquid()
                // Calls a method
                || "true".equals(block.getProperty("waterlogged"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Type type() {
        // Returns a value to the caller
        return Type.MOTION_BLOCKING;
    // End of a block/expression
    }
// End of a block/expression
}
