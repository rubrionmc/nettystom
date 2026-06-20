// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class ChunkHeightmapIntegrationTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testChunkHeightmap(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Calls a method
        var chunk = instance.getChunk(0, 0);

        // Calls a method
        var heightmap = chunk.motionBlockingHeightmap().getHeight(0, 0);
        // Calls a method
        assertEquals(39, heightmap);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void heightMapPlaceTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Calls a method
        var chunk = instance.getChunk(0, 0);

        // Start of a block
        {
            // Calls a method
            instance.setBlock(0, 40, 0, Block.STONE);
            // Calls a method
            var heightmap = chunk.motionBlockingHeightmap().getHeight(0, 0);
            // Calls a method
            assertEquals(40, heightmap);
        // End of a block/expression
        }

        // Start of a block
        {
            // Calls a method
            instance.setBlock(0, 45, 0, Block.STONE);
            // Calls a method
            var heightmap = chunk.motionBlockingHeightmap().getHeight(0, 0);
            // Calls a method
            assertEquals(45, heightmap);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void heightMapRemoveTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Calls a method
        var chunk = instance.getChunk(0, 0);

        // Start of a block
        {
            // Calls a method
            instance.setBlock(0, 45, 0, Block.STONE);
            // Calls a method
            var heightmap = chunk.motionBlockingHeightmap().getHeight(0, 0);
            // Calls a method
            assertEquals(45, heightmap);
        // End of a block/expression
        }

        // Start of a block
        {
            // Calls a method
            instance.setBlock(0, 45, 0, Block.AIR);
            // Calls a method
            var heightmap = chunk.motionBlockingHeightmap().getHeight(0, 0);
            // Calls a method
            assertEquals(39, heightmap);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
