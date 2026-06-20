// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.instance.block.Block;
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
public class EntityPhysicsIntegrationTest
// Start of a block
{
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void onGround(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(1, 40, 1, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(1, 41, 1)).join();
        // Calls a method
        env.tick();

        // Entity shouldn't be on ground because it intitially spawns in with onGround = false
        // and a velocity of 0, it'll take 1 entity tick for gravity to be applied to their velocity
        // and a downward block collision to occur
        // Calls a method
        assertFalse(entity.onGround);
        // Loop: repeats a block
        for (int i = 0; i < 10; i++) {
            // Calls a method
            env.tick();
            // Calls a method
            assertTrue(entity.onGround, "entity needs to be grounded on tick: " + entity.getAliveTicks());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void onGroundWithoutPhysics(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(1, 40, 1, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity.setHasPhysics(false);
        // Calls a method
        entity.setInstance(instance, new Pos(1, 41, 1)).join();

        // Loop: repeats a block
        for (int i = 0; i < 10; i++) {
            // Calls a method
            env.tick();
            // Calls a method
            assertFalse(entity.onGround, "entity shouldn't be grounded on tick: " + entity.getAliveTicks() + " due to lack of physics");
        // End of a block/expression
        }

        // Calls a method
        entity.setHasPhysics(true);
        // Loop: repeats a block
        for (int i = 0; i < 10; i++) {
            // Calls a method
            env.tick();
            // Calls a method
            assertTrue(entity.onGround, "entity should be grounded on tick: " + entity.getAliveTicks());
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
