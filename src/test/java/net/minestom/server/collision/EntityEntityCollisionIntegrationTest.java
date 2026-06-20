// Package declaration for this file
package net.minestom.server.collision;

// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityType;
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
public class EntityEntityCollisionIntegrationTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entitySingleCollisionTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Loop: repeats a block
        for (int i = -2; i <= 2; ++i)
            // Loop: repeats a block
            for (int j = -2; j <= 2; ++j)
                // Calls a method
                instance.loadChunk(i, j).join();

        // Calls a method
        var movingEntity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var stillEntity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var doNotHitEntity = new Entity(EntityType.ZOMBIE);

        // Calls a method
        movingEntity.setInstance(instance, new Vec(0, 42, 0)).join();
        // Calls a method
        stillEntity.setInstance(instance, new Vec(0, 42, 1)).join();
        // Calls a method
        doNotHitEntity.setInstance(instance, new Vec(0, 42, 2)).join();

        // Calls a method
        var result = CollisionUtils.checkEntityCollisions(movingEntity, new Vec(0, 0, 1), 1.51, entity -> entity != movingEntity, null);

        // Calls a method
        assertEquals(1, result.size());
        // Calls a method
        assertEquals(stillEntity, result.iterator().next().entity());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityMultipleCollisionTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Loop: repeats a block
        for (int i = -2; i <= 2; ++i)
            // Loop: repeats a block
            for (int j = -2; j <= 2; ++j)
                // Calls a method
                instance.loadChunk(i, j).join();

        // Calls a method
        var movingEntity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var stillEntity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var stillEntity2 = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var doNotHitEntity = new Entity(EntityType.ZOMBIE);

        // Calls a method
        movingEntity.setInstance(instance, new Vec(0, 42, 0)).join();
        // Calls a method
        stillEntity.setInstance(instance, new Vec(0, 42, 1)).join();
        // Calls a method
        stillEntity2.setInstance(instance, new Vec(0, 42, 2)).join();
        // Calls a method
        doNotHitEntity.setInstance(instance, new Vec(0, 42, 3)).join();

        // Calls a method
        var result = CollisionUtils.checkEntityCollisions(movingEntity, new Vec(0, 0, 2), 1.51, entity -> entity != movingEntity, null);

        // Calls a method
        assertEquals(2, result.size());
    // End of a block/expression
    }
// End of a block/expression
}
