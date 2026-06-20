// Package declaration for this file
package net.minestom.server.utils.chunk;

// Import of a required class
import net.minestom.server.instance.DynamicChunk;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertFalse;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class ChunkUpdateLimitCheckerTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testHistory(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var limiter = new ChunkUpdateLimitChecker(3);

        // Calls a method
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 0)));
        // Calls a method
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 1)));
        // Calls a method
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 2)));
        // history : 0, 1, 2

        // Calls a method
        assertFalse(limiter.addToHistory(new DynamicChunk(instance, 0, 0)));
        // history : 1, 2, 0
        // Calls a method
        assertFalse(limiter.addToHistory(new DynamicChunk(instance, 0, 1)));
        // history : 2, 0, 1
        // Calls a method
        assertFalse(limiter.addToHistory(new DynamicChunk(instance, 0, 2)));
        // history : 0, 1, 2

        // Calls a method
        assertFalse(limiter.addToHistory(new DynamicChunk(instance, 0, 2)));
        // history : 1, 2, 2
        // Calls a method
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 0)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testOneSlotHistory(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var limiter = new ChunkUpdateLimitChecker(1);
        // Calls a method
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 0)));
        // Calls a method
        assertFalse(limiter.addToHistory(new DynamicChunk(instance, 0, 0)));
        // Calls a method
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 1)));
        // Calls a method
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 0)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testDisabling(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var limiter = new ChunkUpdateLimitChecker(0);
        // Calls a method
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 0)));
        // Calls a method
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 0)));
        // Calls a method
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 1)));
    // End of a block/expression
    }
// End of a block/expression
}
