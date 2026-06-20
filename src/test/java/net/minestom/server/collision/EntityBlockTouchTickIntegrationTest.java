// Package declaration for this file
package net.minestom.server.collision;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.HashSet;
// Import of a required class
import java.util.Set;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EntityBlockTouchTickIntegrationTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckTouchTick(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Calls a method
        Set<Point> positions = new HashSet<>();
        // Assigns a value
        var handler = new BlockHandler() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void onTouch(Touch touch) {
                // Calls a method
                assertTrue(positions.add(touch.getBlockPosition()));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Key getKey() {
                // Returns a value to the caller
                return Key.key("minestom:test");
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        instance.setBlock(0, 42, 0, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(0, 42, 1, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(0, 43, 1, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(0, 43, -1, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(1, 42, 1, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(1, 42, 0, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(0, 42, 10, Block.STONE.withHandler(handler));

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0.7)).join();

        // Calls a method
        entity.tick(0);

        // Code statement
        assertEquals(Set.of(new Vec(0, 42, 0),
                // Creates a new object
                new Vec(0, 42, 1),
                // Creates a new object
                new Vec(0, 43, 1)),
                // Code statement
                positions);

        // Calls a method
        assertEquals(instance, entity.getInstance());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckTouchTickFarZ(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.loadChunk(new Pos(1000, 1000, 1000));

        // Calls a method
        Set<Point> positions = new HashSet<>();
        // Assigns a value
        var handler = new BlockHandler() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void onTouch(Touch touch) {
                // Calls a method
                assertTrue(positions.add(touch.getBlockPosition()));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Key getKey() {
                // Returns a value to the caller
                return Key.key("minestom:test");
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        instance.setBlock(1000, 42, 1000, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(1000, 42, 1001, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(1000, 43, 1001, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(1000, 43, 999, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(1001, 42, 1001, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(1001, 42, 1000, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(1000, 42, 1010, Block.STONE.withHandler(handler));

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(1000, 42, 1000.7)).join();

        // Calls a method
        entity.tick(0);

        // Code statement
        assertEquals(Set.of(
                // Creates a new object
                new Vec(1000, 42, 1000),
                // Creates a new object
                new Vec(1000, 42, 1001),
                // Creates a new object
                new Vec(1000, 43, 1001)
            // Code statement
            ), positions);

        // Calls a method
        assertEquals(instance, entity.getInstance());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckTouchTickFarX(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.loadChunk(new Pos(1000, 1000, 1000));

        // Calls a method
        Set<Point> positions = new HashSet<>();
        // Assigns a value
        var handler = new BlockHandler() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void onTouch(Touch touch) {
                // Calls a method
                assertTrue(positions.add(touch.getBlockPosition()));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Key getKey() {
                // Returns a value to the caller
                return Key.key("minestom:test");
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        instance.setBlock(1000, 42, 1000, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(1000, 42, 1001, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(1000, 43, 1001, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(1000, 43, 999, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(1001, 43, 999, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(1001, 42, 999, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(1001, 42, 1001, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(1001, 43, 1000, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(999, 42, 1001, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(1001, 43, 1001, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(1001, 42, 1000, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(1000, 42, 1010, Block.STONE.withHandler(handler));

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(1000.699, 42, 1000)).join();

        // Calls a method
        entity.tick(0);

        // Code statement
        assertEquals(Set.of(
                // Creates a new object
                new Vec(1000, 43, 999),
                // Creates a new object
                new Vec(1000, 42, 1000),
                // Creates a new object
                new Vec(1001, 43, 1000),
                // Creates a new object
                new Vec(1001, 42, 1000),
                // Creates a new object
                new Vec(1001, 42, 999),
                // Creates a new object
                new Vec(1001, 43, 999)
            // Code statement
            ), positions);

        // Calls a method
        assertEquals(instance, entity.getInstance());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckTouchTickFarNegative(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.loadChunk(new Pos(-1000, 44, -1000));

        // Calls a method
        Set<Point> positions = new HashSet<>();
        // Assigns a value
        var handler = new BlockHandler() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void onTouch(Touch touch) {
                // Calls a method
                assertTrue(positions.add(touch.getBlockPosition()));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Key getKey() {
                // Returns a value to the caller
                return Key.key("minestom:test");
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        instance.setBlock(-1000, 42, -1000, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(-1000, 42, -1001, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(-1000, 43, -1001, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(-1000, 43, -999, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(-1001, 43, -999, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(-1001, 42, -999, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(-1001, 42, -1001, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(-1001, 43, -1000, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(-999, 42, -1001, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(-1001, 43, -1001, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(-1001, 42, -1000, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(-1000, 42, -1010, Block.STONE.withHandler(handler));

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(-1000.301, 42, -1000)).join();

        // Calls a method
        entity.tick(0);

        // Code statement
        assertEquals(Set.of(
                // Creates a new object
                new Vec(-1001, 43, -1000),
                // Creates a new object
                new Vec(-1001, 42, -1000),
                // Creates a new object
                new Vec(-1001, 43, -1001),
                // Creates a new object
                new Vec(-1001, 42, -1001),
                // Creates a new object
                new Vec(-1000, 43, -1001),
                // Creates a new object
                new Vec(-1000, 42, -1001),
                // Creates a new object
                new Vec(-1000, 42, -1000)
        // Code statement
        ), positions);

        // Calls a method
        assertEquals(instance, entity.getInstance());
    // End of a block/expression
    }
// End of a block/expression
}
