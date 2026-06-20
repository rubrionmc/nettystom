// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.SuspiciousGravelBlockHandler;
// Import of a required class
import net.minestom.server.instance.block.rule.BlockPlacementRule;
// Import of a required class
import net.minestom.server.tag.Tag;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.concurrent.atomic.AtomicReference;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertThrows;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class InstanceBlockIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void basic(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Code statement
        assertThrows(NullPointerException.class, () -> instance.getBlock(0, 0, 0),
                // Code statement
                "No exception throw when getting a block in an unloaded chunk");

        // Calls a method
        instance.loadChunk(0, 0).join();
        // Calls a method
        assertEquals(Block.AIR, instance.getBlock(0, 50, 0));

        // Calls a method
        instance.setBlock(0, 50, 0, Block.GRASS_BLOCK);
        // Calls a method
        assertEquals(Block.GRASS_BLOCK, instance.getBlock(0, 50, 0));

        // Calls a method
        instance.setBlock(0, 50, 0, Block.STONE);
        // Calls a method
        assertEquals(Block.STONE, instance.getBlock(0, 50, 0));

        // Code statement
        assertThrows(NullPointerException.class, () -> instance.getBlock(16, 0, 0),
                // Code statement
                "No exception throw when getting a block in an unloaded chunk");
        // Calls a method
        instance.loadChunk(1, 0).join();
        // Calls a method
        assertEquals(Block.AIR, instance.getBlock(16, 50, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void unloadCache(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.loadChunk(0, 0).join();

        // Calls a method
        instance.setBlock(0, 50, 0, Block.GRASS_BLOCK);
        // Calls a method
        assertEquals(Block.GRASS_BLOCK, instance.getBlock(0, 50, 0));

        // Calls a method
        instance.unloadChunk(0, 0);
        // Code statement
        assertThrows(NullPointerException.class, () -> instance.getBlock(0, 0, 0),
                // Code statement
                "No exception throw when getting a block in an unloaded chunk");

        // Calls a method
        instance.loadChunk(0, 0).join();
        // Calls a method
        assertEquals(Block.AIR, instance.getBlock(0, 50, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockNbt(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Code statement
        assertThrows(NullPointerException.class, () -> instance.getBlock(0, 0, 0),
                // Code statement
                "No exception throw when getting a block in an unloaded chunk");

        // Calls a method
        instance.loadChunk(0, 0).join();

        // Calls a method
        var tag = Tag.Integer("key");
        // Calls a method
        var block = Block.STONE.withTag(tag, 5);
        // Calls a method
        var point = new Vec(0, 50, 0);
        // Initial placement
        // Calls a method
        instance.setBlock(point, block);
        // Calls a method
        assertEquals(5, instance.getBlock(point).getTag(tag));

        // Override
        // Calls a method
        instance.setBlock(point, block.withTag(tag, 7));
        // Calls a method
        assertEquals(7, instance.getBlock(point).getTag(tag));

        // Different block type
        // Calls a method
        instance.setBlock(point, Block.GRASS_BLOCK.withTag(tag, 8));
        // Calls a method
        assertEquals(8, instance.getBlock(point).getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void handlerPresentInPlacementRuleUpdate(Env env) {

        // Calls a method
        AtomicReference<Block> currentBlock = new AtomicReference<>();
        // Calls a method
        env.process().block().registerHandler(SuspiciousGravelBlockHandler.INSTANCE.getKey(), () -> SuspiciousGravelBlockHandler.INSTANCE);
        // Start of a method/block
        env.process().block().registerBlockPlacementRule(new BlockPlacementRule(Block.SUSPICIOUS_GRAVEL) {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public @Nullable Block blockPlace(PlacementState placementState) {
                // Returns a value to the caller
                return block;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Block blockUpdate(UpdateState updateState) {
                // Calls a method
                currentBlock.set(updateState.currentBlock());
                // Returns a value to the caller
                return super.blockUpdate(updateState);
            // End of a block/expression
            }
        // End of a block/expression
        });

        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var theBlock = Block.SUSPICIOUS_GRAVEL.withHandler(SuspiciousGravelBlockHandler.INSTANCE);
        // Calls a method
        instance.setBlock(0, 50, 0, theBlock);
        // Calls a method
        instance.setBlock(1, 50, 0, theBlock);

        // Calls a method
        assertEquals(theBlock, currentBlock.get());
    // End of a block/expression
    }
// End of a block/expression
}
