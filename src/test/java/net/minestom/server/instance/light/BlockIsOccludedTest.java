// Package declaration for this file
package net.minestom.server.instance.light;

// Import of a required class
import net.minestom.server.collision.Shape;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Map;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertFalse;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;

// Type declaration (class/interface/enum/record)
public class BlockIsOccludedTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockAir() {
        // Calls a method
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Loop: repeats a block
        for (BlockFace face : BlockFace.values()) {
            // Calls a method
            assertFalse(airBlock.isOccluded(airBlock, face));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockLantern() {
        // Calls a method
        Shape shape = Block.LANTERN.registry().occlusionShape();
        // Calls a method
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Loop: repeats a block
        for (BlockFace face : BlockFace.values()) {
            // Calls a method
            assertFalse(shape.isOccluded(airBlock, face));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockSpruceLeaves() {
        // Calls a method
        Shape shape = Block.SPRUCE_LEAVES.registry().occlusionShape();
        // Calls a method
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Loop: repeats a block
        for (BlockFace face : BlockFace.values()) {
            // Calls a method
            assertFalse(shape.isOccluded(airBlock, face));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockCauldron() {
        // Calls a method
        Shape shape = Block.CAULDRON.registry().occlusionShape();
        // Calls a method
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Loop: repeats a block
        for (BlockFace face : BlockFace.values()) {
            // Calls a method
            assertFalse(shape.isOccluded(airBlock, face));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockSlabBottomAir() {
        // Calls a method
        Shape shape = Block.SANDSTONE_SLAB.registry().occlusionShape();
        // Calls a method
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Calls a method
        assertTrue(shape.isOccluded(airBlock, BlockFace.BOTTOM));

        // Calls a method
        assertFalse(shape.isOccluded(airBlock, BlockFace.NORTH));
        // Calls a method
        assertFalse(shape.isOccluded(airBlock, BlockFace.SOUTH));
        // Calls a method
        assertFalse(shape.isOccluded(airBlock, BlockFace.EAST));
        // Calls a method
        assertFalse(shape.isOccluded(airBlock, BlockFace.WEST));
        // Calls a method
        assertFalse(shape.isOccluded(airBlock, BlockFace.TOP));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockSlabTopEnchantingTable() {
        // Calls a method
        Shape shape1 = Block.SANDSTONE_SLAB.withProperty("type", "top").registry().occlusionShape();
        // Calls a method
        Shape shape2 = Block.ENCHANTING_TABLE.registry().occlusionShape();

        // Calls a method
        assertFalse(shape1.isOccluded(shape2, BlockFace.BOTTOM));

        // Calls a method
        assertTrue(shape1.isOccluded(shape2, BlockFace.NORTH));
        // Calls a method
        assertTrue(shape1.isOccluded(shape2, BlockFace.SOUTH));
        // Calls a method
        assertTrue(shape1.isOccluded(shape2, BlockFace.EAST));
        // Calls a method
        assertTrue(shape1.isOccluded(shape2, BlockFace.WEST));
        // Calls a method
        assertTrue(shape1.isOccluded(shape2, BlockFace.TOP));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockStairWest() {
        // Assigns a value
        Shape shape = Block.SANDSTONE_STAIRS.withProperties(Map.of(
                // Code statement
                "facing", "west",
                // Code statement
                "half", "bottom",
                // Calls a method
                "shape", "straight")).registry().occlusionShape();

        // Calls a method
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Calls a method
        assertTrue(shape.isOccluded(airBlock, BlockFace.WEST));
        // Calls a method
        assertTrue(shape.isOccluded(airBlock, BlockFace.BOTTOM));

        // Calls a method
        assertFalse(shape.isOccluded(airBlock, BlockFace.SOUTH));
        // Calls a method
        assertFalse(shape.isOccluded(airBlock, BlockFace.EAST));
        // Calls a method
        assertFalse(shape.isOccluded(airBlock, BlockFace.NORTH));
        // Calls a method
        assertFalse(shape.isOccluded(airBlock, BlockFace.TOP));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockSlabBottomStone() {
        // Calls a method
        Shape shape = Block.SANDSTONE_SLAB.registry().occlusionShape();
        // Calls a method
        Shape stoneBlock = Block.STONE.registry().occlusionShape();

        // Calls a method
        assertTrue(shape.isOccluded(stoneBlock, BlockFace.BOTTOM));
        // Calls a method
        assertTrue(shape.isOccluded(stoneBlock, BlockFace.NORTH));
        // Calls a method
        assertTrue(shape.isOccluded(stoneBlock, BlockFace.SOUTH));
        // Calls a method
        assertTrue(shape.isOccluded(stoneBlock, BlockFace.EAST));
        // Calls a method
        assertTrue(shape.isOccluded(stoneBlock, BlockFace.WEST));
        // Calls a method
        assertTrue(shape.isOccluded(stoneBlock, BlockFace.TOP));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockStone() {
        // Calls a method
        Shape shape = Block.STONE.registry().occlusionShape();
        // Calls a method
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Loop: repeats a block
        for (BlockFace face : BlockFace.values()) {
            // Calls a method
            assertTrue(shape.isOccluded(airBlock, face));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockStair() {
        // Calls a method
        Shape shape = Block.SANDSTONE_STAIRS.registry().occlusionShape();
        // Calls a method
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Calls a method
        assertTrue(shape.isOccluded(airBlock, BlockFace.NORTH));
        // Calls a method
        assertTrue(shape.isOccluded(airBlock, BlockFace.BOTTOM));

        // Calls a method
        assertFalse(shape.isOccluded(airBlock, BlockFace.SOUTH));
        // Calls a method
        assertFalse(shape.isOccluded(airBlock, BlockFace.EAST));
        // Calls a method
        assertFalse(shape.isOccluded(airBlock, BlockFace.WEST));
        // Calls a method
        assertFalse(shape.isOccluded(airBlock, BlockFace.TOP));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockSlab() {
        // Calls a method
        Shape shape = Block.SANDSTONE_SLAB.registry().occlusionShape();
        // Calls a method
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Calls a method
        assertTrue(shape.isOccluded(airBlock, BlockFace.BOTTOM));

        // Calls a method
        assertFalse(shape.isOccluded(airBlock, BlockFace.NORTH));
        // Calls a method
        assertFalse(shape.isOccluded(airBlock, BlockFace.SOUTH));
        // Calls a method
        assertFalse(shape.isOccluded(airBlock, BlockFace.EAST));
        // Calls a method
        assertFalse(shape.isOccluded(airBlock, BlockFace.WEST));
        // Calls a method
        assertFalse(shape.isOccluded(airBlock, BlockFace.TOP));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockSlabBottomAndSlabTop() {
        // Calls a method
        Shape shape1 = Block.SANDSTONE_SLAB.registry().occlusionShape();
        // Calls a method
        Shape shape2 = Block.SANDSTONE_SLAB.withProperty("type", "top").registry().occlusionShape();

        // Calls a method
        assertFalse(shape1.isOccluded(shape2, BlockFace.TOP));

        // Calls a method
        assertTrue(shape1.isOccluded(shape2, BlockFace.BOTTOM));
        // Calls a method
        assertTrue(shape1.isOccluded(shape2, BlockFace.EAST));
        // Calls a method
        assertTrue(shape1.isOccluded(shape2, BlockFace.WEST));
        // Calls a method
        assertTrue(shape1.isOccluded(shape2, BlockFace.NORTH));
        // Calls a method
        assertTrue(shape1.isOccluded(shape2, BlockFace.SOUTH));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockSlabBottomAndSlabBottom() {
        // Calls a method
        Shape shape = Block.SANDSTONE_SLAB.registry().occlusionShape();

        // Calls a method
        assertTrue(shape.isOccluded(shape, BlockFace.BOTTOM));
        // Calls a method
        assertTrue(shape.isOccluded(shape, BlockFace.TOP));

        // Calls a method
        assertFalse(shape.isOccluded(shape, BlockFace.EAST));
        // Calls a method
        assertFalse(shape.isOccluded(shape, BlockFace.WEST));
        // Calls a method
        assertFalse(shape.isOccluded(shape, BlockFace.NORTH));
        // Calls a method
        assertFalse(shape.isOccluded(shape, BlockFace.SOUTH));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockStairAndSlabBottom() {
        // Calls a method
        Shape shape1 = Block.STONE_STAIRS.registry().occlusionShape();
        // Calls a method
        Shape shape2 = Block.SANDSTONE_SLAB.registry().occlusionShape();

        // Calls a method
        assertTrue(shape1.isOccluded(shape2, BlockFace.BOTTOM));
        // Calls a method
        assertTrue(shape1.isOccluded(shape2, BlockFace.NORTH));
        // Calls a method
        assertTrue(shape1.isOccluded(shape2, BlockFace.TOP));

        // Calls a method
        assertFalse(shape1.isOccluded(shape2, BlockFace.EAST));
        // Calls a method
        assertFalse(shape1.isOccluded(shape2, BlockFace.WEST));
        // Calls a method
        assertFalse(shape1.isOccluded(shape2, BlockFace.SOUTH));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockStairAndSlabTop() {
        // Calls a method
        Shape shape1 = Block.STONE_STAIRS.registry().occlusionShape();
        // Calls a method
        Shape shape2 = Block.SANDSTONE_SLAB.withProperty("type", "top").registry().occlusionShape();

        // Calls a method
        assertTrue(shape1.isOccluded(shape2, BlockFace.NORTH));
        // Calls a method
        assertTrue(shape1.isOccluded(shape2, BlockFace.BOTTOM));
        // Calls a method
        assertTrue(shape1.isOccluded(shape2, BlockFace.EAST));
        // Calls a method
        assertTrue(shape1.isOccluded(shape2, BlockFace.WEST));
        // Calls a method
        assertTrue(shape1.isOccluded(shape2, BlockFace.SOUTH));

        // Calls a method
        assertFalse(shape1.isOccluded(shape2, BlockFace.TOP));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void occlusionShapeLeaves() {
        // Calls a method
        Shape shape = Block.OAK_LEAVES.registry().occlusionShape();
        // Calls a method
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Loop: repeats a block
        for (BlockFace face : BlockFace.values()) {
            // Calls a method
            assertFalse(shape.isOccluded(airBlock, face));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void collisionShapeLeaves() {
        // Calls a method
        Shape shape = Block.OAK_LEAVES.registry().collisionShape();
        // Calls a method
        Shape airBlock = Block.AIR.registry().collisionShape();

        // Loop: repeats a block
        for (BlockFace face : BlockFace.values()) {
            // Calls a method
            assertTrue(shape.isOccluded(airBlock, face));
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
