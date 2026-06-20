// Package declaration for this file
package net.minestom.server.collision;

// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
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
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class PlacementCollisionIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void empty(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        assertNull(BlockCollision.canPlaceBlockAt(instance, new Vec(0, 40, 0), Block.STONE));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityBlock(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Creates a new object
        new Entity(EntityType.ZOMBIE).setInstance(instance, new Pos(0, 40, 0)).join();
        // Calls a method
        assertNotNull(BlockCollision.canPlaceBlockAt(instance, new Vec(0, 40, 0), Block.STONE));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void slab(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Creates a new object
        new Entity(EntityType.ZOMBIE).setInstance(instance, new Pos(0, 40.75, 0)).join();
        // Calls a method
        assertNull(BlockCollision.canPlaceBlockAt(instance, new Vec(0, 40, 0), Block.STONE_SLAB));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void belowPlayer(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        env.createPlayer(instance, new Pos(5.7, -8, 6.389));
        // Calls a method
        assertNull(BlockCollision.canPlaceBlockAt(instance, new Vec(5, -9, 6), Block.STONE));
    // End of a block/expression
    }
// End of a block/expression
}
