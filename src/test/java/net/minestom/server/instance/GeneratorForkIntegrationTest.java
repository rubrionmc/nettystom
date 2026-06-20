// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.generator.GenerationUnit;
// Import of a required class
import net.minestom.server.world.biome.Biome;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class GeneratorForkIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void local(Env env) {
        // Calls a method
        var manager = env.process().instance();
        // Calls a method
        var instance = manager.createInstanceContainer();
        // Assigns a value
        var block = Block.STONE;
        // Start of a method/block
        instance.setGenerator(unit -> {
            // Calls a method
            var u = unit.fork(unit.absoluteStart(), unit.absoluteEnd());
            // Calls a method
            assertEquals(unit.absoluteStart(), u.absoluteStart());
            // Calls a method
            assertEquals(unit.absoluteEnd(), u.absoluteEnd());
            // Calls a method
            u.modifier().setRelative(0, 0, 0, Block.STONE);
        // End of a block/expression
        });
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Calls a method
        assertEquals(block, instance.getBlock(0, -64, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void size(Env env) {
        // Calls a method
        var manager = env.process().instance();
        // Calls a method
        var instance = manager.createInstanceContainer();
        // Set the Generator
        // Start of a method/block
        instance.setGenerator(unit -> {
            // Calls a method
            Point start = unit.absoluteStart();
            // Calls a method
            GenerationUnit fork = unit.fork(start, start.add(18, 18, 18));
            // Calls a method
            assertDoesNotThrow(() -> fork.modifier().setBlock(start.add(17, 17, 17), Block.STONE));
        // End of a block/expression
        });
        // Load the chunks
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Calls a method
        instance.setGenerator(null);
        // Calls a method
        instance.loadChunk(1, 1).join();
        // Calls a method
        assertEquals(Block.STONE, instance.getBlock(17, -64 + 17, 17));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void signal(Env env) {
        // Calls a method
        var manager = env.process().instance();
        // Calls a method
        var instance = manager.createInstanceContainer();
        // Assigns a value
        var block = Block.STONE;
        // Start of a method/block
        instance.setGenerator(unit -> {
            // Calls a method
            var u = unit.fork(unit.absoluteStart(), unit.absoluteEnd().add(16, 0, 16));
            // Calls a method
            assertEquals(unit.absoluteStart(), u.absoluteStart());
            // Calls a method
            assertEquals(unit.absoluteEnd().add(16, 0, 16), u.absoluteEnd());
            // Calls a method
            u.modifier().setRelative(16, 0, 0, Block.STONE);
            // Calls a method
            u.modifier().setRelative(16, 33, 0, Block.STONE);
        // End of a block/expression
        });
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Calls a method
        instance.setGenerator(null);
        // Calls a method
        instance.loadChunk(1, 0).join();
        // Calls a method
        assertEquals(block, instance.getBlock(16, -64, 0));
        // Calls a method
        assertEquals(block, instance.getBlock(16, -31, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void air(Env env) {
        // Calls a method
        var manager = env.process().instance();
        // Calls a method
        var instance = manager.createInstanceContainer();
        // Start of a method/block
        instance.setGenerator(unit -> {
            // Calls a method
            var u = unit.fork(unit.absoluteStart(), unit.absoluteEnd().add(16, 0, 16));
            // Calls a method
            u.modifier().setRelative(16, 39 + 64, 0, Block.AIR);
        // End of a block/expression
        });
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Calls a method
        instance.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.STONE));
        // Calls a method
        instance.loadChunk(1, 0).join();
        // Calls a method
        assertEquals(Block.AIR, instance.getBlock(16, 39, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void fillHeight(Env env) {
        // Calls a method
        var manager = env.process().instance();
        // Calls a method
        var instance = manager.createInstanceContainer();
        // Start of a method/block
        instance.setGenerator(unit -> {
            // Calls a method
            var u = unit.fork(unit.absoluteStart(), unit.absoluteEnd().add(16, 0, 16));
            // Calls a method
            u.modifier().fillHeight(0, 40, Block.STONE);
        // End of a block/expression
        });
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Calls a method
        instance.setGenerator(null);
        // Calls a method
        instance.loadChunk(1, 0).join();
        // Loop: repeats a block
        for (int y = 0; y < 40; y++) {
            // Calls a method
            assertEquals(Block.STONE, instance.getBlock(16, y, 0), "y=" + y);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void biome(Env env) {
        // Calls a method
        var manager = env.process().instance();

        // Calls a method
        var instance = manager.createInstanceContainer();
        // Start of a method/block
        instance.setGenerator(unit -> {
            // Calls a method
            var u = unit.fork(unit.absoluteStart(), unit.absoluteEnd().add(16, 0, 16));
            // Calls a method
            assertThrows(IllegalStateException.class, () -> u.modifier().setBiome(16, 0, 0, Biome.PLAINS));
            // Calls a method
            assertThrows(IllegalStateException.class, () -> u.modifier().fillBiome(Biome.PLAINS));
        // End of a block/expression
        });
        // Calls a method
        instance.loadChunk(0, 0).join();
    // End of a block/expression
    }
// End of a block/expression
}
