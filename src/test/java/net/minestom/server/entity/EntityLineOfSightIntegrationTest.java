// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EntityLineOfSightIntegrationTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckLineOfSight(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Calls a method
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        entity.setView(-90, 0);

        // Calls a method
        var entity2 = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity2.setInstance(instance, new Pos(10, 42, 0)).join();

        // Calls a method
        assertEquals(entity2, entity.getLineOfSightEntity(20, (e) -> true));
        // Calls a method
        assertTrue(entity.hasLineOfSight(entity2, true));

        // Loop: repeats a block
        for (int z = -1; z <= 1; ++z) {
            // Loop: repeats a block
            for (int y = 40; y <= 44; ++y) {
                // Calls a method
                instance.setBlock(5, y, z, Block.STONE);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        assertNull(entity.getLineOfSightEntity(20, (e) -> true));
        // Calls a method
        assertFalse(entity.hasLineOfSight(entity2, true));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckLineOfSightBehind(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Calls a method
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        entity.setView(-90, 0);

        // Calls a method
        var entity2 = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity2.setInstance(instance, new Pos(-10, 42, 0)).join();

        // Calls a method
        assertNull(entity.getLineOfSightEntity(20, (e) -> true));
        // Calls a method
        assertFalse(entity.hasLineOfSight(entity2, true));
        // Calls a method
        assertTrue(entity.hasLineOfSight(entity2, false));

        // Loop: repeats a block
        for (int z = -1; z <= 1; ++z) {
            // Loop: repeats a block
            for (int y = 40; y <= 44; ++y) {
                // Calls a method
                instance.setBlock(-5, y, z, Block.STONE);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        assertFalse(entity.hasLineOfSight(entity2, false));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckLineOfSightNearMiss(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Calls a method
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        entity.setView(-90, 0);

        // Calls a method
        var entity2 = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity2.setInstance(instance, new Pos(10, 42, 0.31)).join();

        // Calls a method
        assertNull(entity.getLineOfSightEntity(20, (e) -> true));
        // Calls a method
        assertFalse(entity.hasLineOfSight(entity2, true));
        // Calls a method
        assertTrue(entity.hasLineOfSight(entity2, false));

        // Loop: repeats a block
        for (int z = -1; z <= 1; ++z) {
            // Loop: repeats a block
            for (int y = 40; y <= 44; ++y) {
                // Calls a method
                instance.setBlock(5, y, z, Block.STONE);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        assertFalse(entity.hasLineOfSight(entity2, false));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckLineOfSightNearHit(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Calls a method
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        entity.setView(-90, 0);

        // Calls a method
        var entity2 = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity2.setInstance(instance, new Pos(10, 42, 0.3)).join();

        // Calls a method
        assertEquals(entity2, entity.getLineOfSightEntity(20, (e) -> true));
        // Calls a method
        assertTrue(entity.hasLineOfSight(entity2, true));
        // Calls a method
        assertTrue(entity.hasLineOfSight(entity2, false));

        // Loop: repeats a block
        for (int z = -1; z <= 1; ++z) {
            // Loop: repeats a block
            for (int y = 40; y <= 44; ++y) {
                // Calls a method
                instance.setBlock(5, y, z, Block.STONE);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        assertNull(entity.getLineOfSightEntity(20, (e) -> true));
        // Calls a method
        assertFalse(entity.hasLineOfSight(entity2, true));
        // Calls a method
        assertFalse(entity.hasLineOfSight(entity2, false));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckLineOfSightCorrectOrder(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Calls a method
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        entity.setView(-90, 0);

        // Calls a method
        var entity2 = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity2.setInstance(instance, new Pos(10, 42, 0)).join();

        // Calls a method
        var entity3 = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity3.setInstance(instance, new Pos(5, 42, 0)).join();

        // Calls a method
        assertEquals(entity3, entity.getLineOfSightEntity(20, (e) -> true));
        // Calls a method
        assertTrue(entity.hasLineOfSight(entity2, true));
        // Calls a method
        assertTrue(entity.hasLineOfSight(entity2, false));
        // Calls a method
        assertTrue(entity.hasLineOfSight(entity3, true));
        // Calls a method
        assertTrue(entity.hasLineOfSight(entity3, false));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckLineOfSightBigMiss(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Calls a method
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        entity.setView(-90, 0);

        // Calls a method
        var entity2 = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity2.setInstance(instance, new Pos(10, 42, 10)).join();

        // Calls a method
        assertNull(entity.getLineOfSightEntity(20, (e) -> true));
        // Calls a method
        assertFalse(entity.hasLineOfSight(entity2, true));
        // Calls a method
        assertTrue(entity.hasLineOfSight(entity2, false));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckLineOfSightLargeBoundingBox(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Calls a method
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        entity.setView(-90, 0);

        // Calls a method
        var entity2 = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity2.setInstance(instance, new Pos(6, 42, 0)).join();
        // Calls a method
        entity2.setBoundingBox(4.0, 2.0, 4.0);

        // Loop: repeats a block
        for (int z = -1; z <= 1; ++z) {
            // Loop: repeats a block
            for (int y = 40; y <= 44; ++y) {
                // Calls a method
                instance.setBlock(5, y, z, Block.STONE);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        assertEquals(entity2, entity.getLineOfSightEntity(20, (e) -> true));
        // Calls a method
        assertTrue(entity.hasLineOfSight(entity2, true));
        // Calls a method
        assertTrue(entity.hasLineOfSight(entity2, false));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckLineOfSightDifferentTypes(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Calls a method
        var entity = new Entity(EntityTypes.CHICKEN);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        entity.setView(-90, 0);

        // Calls a method
        var entity2 = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity2.setInstance(instance, new Pos(10, 42, 0)).join();

        // Calls a method
        assertEquals(entity2, entity.getLineOfSightEntity(20, (e) -> true));
        // Calls a method
        assertTrue(entity.hasLineOfSight(entity2, true));

        // Calls a method
        entity.teleport(new Pos(10, 42, 0)).join();
        // Calls a method
        entity2.teleport(new Pos(0, 42, 0)).join();
        // Calls a method
        entity2.setView(-90, 0);

        // Calls a method
        assertNull(entity2.getLineOfSightEntity(20, (e) -> true));
        // Calls a method
        assertFalse(entity2.hasLineOfSight(entity, true));
        // Calls a method
        assertTrue(entity2.hasLineOfSight(entity, false));
    // End of a block/expression
    }
// End of a block/expression
}
