// Package declaration for this file
package net.minestom.server.snapshot;

// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class ChunkSnapshotIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blocks(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 0, 0, Block.STONE);
        // Calls a method
        var snapshot = ServerSnapshot.update();

        // Calls a method
        var inst = snapshot.instances().iterator().next();
        // Calls a method
        assertEquals(Block.STONE, inst.getBlock(0, 0, 0));

        // Calls a method
        assertEquals(1, inst.chunks().size());
        // Calls a method
        var chunk = inst.chunks().iterator().next();
        // Calls a method
        assertEquals(Block.STONE, chunk.getBlock(0, 0, 0));
    // End of a block/expression
    }
// End of a block/expression
}
