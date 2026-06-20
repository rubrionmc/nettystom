// Package declaration for this file
package net.minestom.server.instance.generator;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNull;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class GeneratorForkConsumerIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void empty(Env env) {
        // Calls a method
        var manager = env.process().instance();
        // Calls a method
        var instance = manager.createInstanceContainer();
        // Calls a method
        AtomicReference<Exception> failed = new AtomicReference<>();
        // Start of a method/block
        instance.setGenerator(unit -> {
            // Exception handling
            try {
                // Start of a method/block
                unit.fork(setter -> {
                // End of a block/expression
                });
            // Start of a method/block
            } catch (Exception e) {
                // Calls a method
                failed.set(e);
            // End of a block/expression
            }
        // End of a block/expression
        });
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Calls a method
        assertNull(failed.get(), "Failed: " + failed.get());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void local(Env env) {
        // Calls a method
        var manager = env.process().instance();
        // Calls a method
        var instance = manager.createInstanceContainer();
        // Start of a method/block
        instance.setGenerator(unit -> unit.fork(setter -> {
            // Calls a method
            var dynamic = (GeneratorImpl.DynamicFork) setter;
            // Calls a method
            assertNull(dynamic.minSection);
            // Calls a method
            assertEquals(0, dynamic.width);
            // Calls a method
            assertEquals(0, dynamic.height);
            // Calls a method
            assertEquals(0, dynamic.depth);
            // Calls a method
            setter.setBlock(unit.absoluteStart(), Block.STONE);
            // Calls a method
            assertEquals(unit.absoluteStart(), dynamic.minSection);
            // Calls a method
            assertEquals(1, dynamic.width);
            // Calls a method
            assertEquals(1, dynamic.height);
            // Calls a method
            assertEquals(1, dynamic.depth);
        // Code statement
        }));
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Calls a method
        assertEquals(Block.STONE, instance.getBlock(0, -64, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void doubleLocal(Env env) {
        // Calls a method
        var manager = env.process().instance();
        // Calls a method
        var instance = manager.createInstanceContainer();
        // Start of a method/block
        instance.setGenerator(unit -> unit.fork(setter -> {
            // Calls a method
            setter.setBlock(unit.absoluteStart(), Block.STONE);
            // Calls a method
            setter.setBlock(unit.absoluteStart().add(1, 0, 0), Block.STONE);
        // Code statement
        }));
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Calls a method
        assertEquals(Block.STONE, instance.getBlock(0, -64, 0));
        // Calls a method
        assertEquals(Block.STONE, instance.getBlock(1, -64, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void neighborZ(Env env) {
        // Calls a method
        var manager = env.process().instance();
        // Calls a method
        var instance = manager.createInstanceContainer();
        // Start of a method/block
        instance.setGenerator(unit -> unit.fork(setter -> {
            // Calls a method
            var dynamic = (GeneratorImpl.DynamicFork) setter;
            // Calls a method
            assertNull(dynamic.minSection);
            // Calls a method
            assertEquals(0, dynamic.width);
            // Calls a method
            assertEquals(0, dynamic.height);
            // Calls a method
            assertEquals(0, dynamic.depth);
            // Calls a method
            setter.setBlock(unit.absoluteStart(), Block.STONE);
            // Calls a method
            setter.setBlock(unit.absoluteStart().add(0, 0, 16), Block.GRASS_BLOCK);
            // Calls a method
            assertEquals(unit.absoluteStart(), dynamic.minSection);
            // Calls a method
            assertEquals(1, dynamic.width);
            // Calls a method
            assertEquals(1, dynamic.height);
            // Calls a method
            assertEquals(2, dynamic.depth);
        // Code statement
        }));
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Calls a method
        instance.setGenerator(null);
        // Calls a method
        instance.loadChunk(0, 1).join();
        // Calls a method
        assertEquals(Block.STONE, instance.getBlock(0, -64, 0));
        // Calls a method
        assertEquals(Block.GRASS_BLOCK, instance.getBlock(0, -64, 16));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void neighborX(Env env) {
        // Calls a method
        var manager = env.process().instance();
        // Calls a method
        var instance = manager.createInstanceContainer();
        // Start of a method/block
        instance.setGenerator(unit -> unit.fork(setter -> {
            // Calls a method
            var dynamic = (GeneratorImpl.DynamicFork) setter;
            // Calls a method
            assertNull(dynamic.minSection);
            // Calls a method
            assertEquals(0, dynamic.width);
            // Calls a method
            assertEquals(0, dynamic.height);
            // Calls a method
            assertEquals(0, dynamic.depth);
            // Calls a method
            setter.setBlock(unit.absoluteStart(), Block.STONE);
            // Calls a method
            setter.setBlock(unit.absoluteStart().add(16, 0, 0), Block.GRASS_BLOCK);
            // Calls a method
            assertEquals(unit.absoluteStart(), dynamic.minSection);
            // Calls a method
            assertEquals(2, dynamic.width);
            // Calls a method
            assertEquals(1, dynamic.height);
            // Calls a method
            assertEquals(1, dynamic.depth);
        // Code statement
        }));
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Calls a method
        instance.setGenerator(null);
        // Calls a method
        instance.loadChunk(1, 0).join();
        // Calls a method
        assertEquals(Block.STONE, instance.getBlock(0, -64, 0));
        // Calls a method
        assertEquals(Block.GRASS_BLOCK, instance.getBlock(16, -64, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void neighborY(Env env) {
        // Calls a method
        var manager = env.process().instance();
        // Calls a method
        var instance = manager.createInstanceContainer();
        // Start of a method/block
        instance.setGenerator(unit -> unit.fork(setter -> {
            // Calls a method
            var dynamic = (GeneratorImpl.DynamicFork) setter;
            // Calls a method
            assertNull(dynamic.minSection);
            // Calls a method
            assertEquals(0, dynamic.width);
            // Calls a method
            assertEquals(0, dynamic.height);
            // Calls a method
            assertEquals(0, dynamic.depth);
            // Calls a method
            setter.setBlock(unit.absoluteStart(), Block.STONE);
            // Calls a method
            setter.setBlock(unit.absoluteStart().add(0, 16, 0), Block.GRASS_BLOCK);
            // Calls a method
            assertEquals(unit.absoluteStart(), dynamic.minSection);
            // Calls a method
            assertEquals(1, dynamic.width);
            // Calls a method
            assertEquals(2, dynamic.height);
            // Calls a method
            assertEquals(1, dynamic.depth);
        // Code statement
        }));
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Calls a method
        assertEquals(Block.STONE, instance.getBlock(0, -64, 0));
        // Calls a method
        assertEquals(Block.GRASS_BLOCK, instance.getBlock(0, -48, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void verticalAndHorizontalSectionBorders(Env env) {
        // Calls a method
        var manager = env.process().instance();
        // Calls a method
        var instance = manager.createInstanceContainer();
        // Calls a method
        Set<Point> points = ConcurrentHashMap.newKeySet();
        // Start of a method/block
        instance.setGenerator(unit -> {
            // Calls a method
            final Point start = unit.absoluteStart().withY(96);
            // Start of a method/block
            unit.fork(setter -> {
                // Calls a method
                var dynamic = (GeneratorImpl.DynamicFork) setter;
                // Loop: repeats a block
                for (int i = 0; i < 16; i++) {
                    // Calls a method
                    setter.setBlock(start.add(i, 0, 0), Block.STONE);
                    // Calls a method
                    setter.setBlock(start.add(-i, 0, 0), Block.STONE);
                    // Calls a method
                    setter.setBlock(start.add(0, i, 0), Block.STONE);
                    // Calls a method
                    setter.setBlock(start.add(0, -i, 0), Block.STONE);

                    // Calls a method
                    points.add(start.add(i, 0, 0));
                    // Calls a method
                    points.add(start.add(-i, 0, 0));
                    // Calls a method
                    points.add(start.add(0, i, 0));
                    // Calls a method
                    points.add(start.add(0, -i, 0));
                // End of a block/expression
                }
                // Calls a method
                assertEquals(2, dynamic.width);
                // Calls a method
                assertEquals(2, dynamic.height);
                // Calls a method
                assertEquals(1, dynamic.depth);
            // End of a block/expression
            });
        // End of a block/expression
        });
        // Calls a method
        instance.loadChunk(0, 0).join();
        // Loop: repeats a block
        for (Point point : points) {
            // Branch: checks a condition
            if (!instance.isChunkLoaded(point)) continue;
            // Calls a method
            assertEquals(Block.STONE, instance.getBlock(point), point.toString());
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
