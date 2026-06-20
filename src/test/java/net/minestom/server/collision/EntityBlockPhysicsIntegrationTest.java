// Package declaration for this file
package net.minestom.server.collision;

// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
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
import net.minestom.server.entity.metadata.other.SlimeMeta;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EntityBlockPhysicsIntegrationTest {
    // Calls a method
    private static final Point PRECISION = new Pos(0.01, 0.01, 0.01);

    // Start of a method/block
    private static boolean checkPoints(Point expected, Point actual) {
        // Calls a method
        Point diff = expected.sub(actual);

        // Returns a value to the caller
        return (PRECISION.x() > Math.abs(diff.x()))
                // Code statement
                && (PRECISION.y() > Math.abs(diff.y()))
                // Calls a method
                && (PRECISION.z() > Math.abs(diff.z()));
    // End of a block/expression
    }

    // Start of a method/block
    private static void assertEqualsPoint(Point expected, Point actual) {
        // Calls a method
        assertEquals(expected.x(), actual.x(), PRECISION.x());
        // Calls a method
        assertEquals(expected.y(), actual.y(), PRECISION.y());
        // Calls a method
        assertEquals(expected.z(), actual.z(), PRECISION.z());
    // End of a block/expression
    }

    // Start of a method/block
    private static void assertPossiblePoints(List<Point> expected, Point actual) {
        // Loop: repeats a block
        for (Point point : expected) {
            // Branch: checks a condition
            if (checkPoints(point, actual)) {
                // Returns a value to the caller
                return;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        fail("Expected one of the following points: " + expected);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckCollision(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 43, 1, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, 10));
        // Calls a method
        assertEqualsPoint(new Pos(0, 42, 0.7), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckShortDiagonal(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 42, 1, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0.9)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        entity.setBoundingBox(BoundingBox.ZERO);

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, 1.3));
        // Calls a method
        assertEqualsPoint(new Pos(0, 42, 1), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckSlab(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Loop: repeats a block
        for (int i = -2; i <= 2; ++i)
            // Loop: repeats a block
            for (int j = -2; j <= 2; ++j)
                // Calls a method
                instance.loadChunk(i, j).join();

        // Calls a method
        instance.setBlock(0, 42, 0, Block.STONE_SLAB);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 44, 0)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -10, 0));
        // Calls a method
        assertEqualsPoint(new Pos(0, 42.5, 0), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckShallowAngle(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(13, 99, 16, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(12.812, 100.0, 16.498)).join();

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.273, -0.0784, 0.0));
        // Calls a method
        assertTrue(res.isOnGround());
        // Calls a method
        assertTrue(res.collisionY());
        // Calls a method
        assertEqualsPoint(new Vec(13.09, 100, 16.5), res.newPosition());
        // Calls a method
        assertEqualsPoint(new Vec(0.273, 0, 0), res.newVelocity());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckFallFence(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 42, 0, Block.OAK_FENCE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.5, 43.5, 0.5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -0.25, 0));
        // Calls a method
        assertEqualsPoint(new Pos(0.5, 43.5, 0.5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckFallHitCarpet(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Loop: repeats a block
        for (int i = -2; i <= 2; ++i)
            // Loop: repeats a block
            for (int j = -2; j <= 2; ++j)
                // Calls a method
                instance.loadChunk(i, j).join();

        // Calls a method
        instance.setBlock(0, 42, 0, Block.OAK_FENCE);
        // Calls a method
        instance.setBlock(0, 43, 0, Block.BROWN_CARPET);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 54.0625, 0)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -11.03, 0));
        // Calls a method
        assertEqualsPoint(new Pos(0, 43.0625, 0), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckFallHitFence(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 42, 0, Block.OAK_FENCE);
        // Calls a method
        instance.setBlock(0, 43, 0, Block.BROWN_CARPET);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.5, 54.0625, 0.5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -11.03, 0));
        // Calls a method
        assertEqualsPoint(new Pos(0.5, 43.5, 0.5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckHorizontalFence(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(1, 42, 0, Block.OAK_FENCE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.5, 43.25, 0.5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(2, 0, 0));
        // Calls a method
        assertEqualsPoint(new Pos(1.075, 43.25, 0.5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckMultipleBlocksPassFirst(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(4, 40, -1, Block.SANDSTONE_STAIRS);
        // Calls a method
        instance.setBlock(16, 40, 0, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.0, 40.51, 0.0)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(20, 0, 0));
        // Calls a method
        assertEqualsPoint(new Pos(15.7, 40.51, 0), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckMultipleBlocksHitFirst(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(4, 40, 0, Block.GRASS_BLOCK);
        // Calls a method
        instance.setBlock(16, 40, 0, Block.STONE);

        // Calls a method
        instance.loadChunk(0, -1).join();

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.0, 40.51, 0.0)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(20, 0, 0));

        // Calls a method
        assertEqualsPoint(new Pos(3.7, 40.51, 0), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckHorizontalCarpetedFence(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(1, 42, 0, Block.OAK_FENCE);
        // Calls a method
        instance.setBlock(1, 43, 0, Block.BROWN_CARPET);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.5, 43.25, 0.5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(2, 0, 0));
        // Calls a method
        assertEqualsPoint(new Pos(1.075, 43.25, 0.5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckDiagonalCarpetedFenceX(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Loop: repeats a block
        for (int i = -2; i <= 2; ++i)
            // Loop: repeats a block
            for (int j = -2; j <= 2; ++j)
                // Calls a method
                instance.loadChunk(i, j).join();

        // Calls a method
        instance.setBlock(1, 42, 0, Block.OAK_FENCE);
        // Calls a method
        instance.setBlock(1, 43, 0, Block.BROWN_CARPET);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.075, 44.0625, 0.5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(2, -2, 0));
        // Calls a method
        assertEqualsPoint(new Pos(1.075, 43.0625, 0.5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckDiagonalCarpetedFenceZ(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Loop: repeats a block
        for (int i = -2; i <= 2; ++i)
            // Loop: repeats a block
            for (int j = -2; j <= 2; ++j)
                // Calls a method
                instance.loadChunk(i, j).join();

        // Calls a method
        instance.setBlock(0, 42, 1, Block.OAK_FENCE);
        // Calls a method
        instance.setBlock(0, 43, 1, Block.BROWN_CARPET);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.5, 44.0625, 0.075)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -2, 2));
        // Calls a method
        assertEqualsPoint(new Pos(0.5, 43.0625, 1.075), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckDiagonalCarpetedFenceXZ(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Loop: repeats a block
        for (int i = -2; i <= 2; ++i)
            // Loop: repeats a block
            for (int j = -2; j <= 2; ++j)
                // Calls a method
                instance.loadChunk(i, j).join();

        // Calls a method
        instance.setBlock(0, 42, 1, Block.OAK_FENCE.withProperties(Map.of("north", "true", "west", "true")));
        // Calls a method
        instance.setBlock(0, 42, 0, Block.OAK_FENCE.withProperties(Map.of("south", "true")));
        // Calls a method
        instance.setBlock(-1, 42, 1, Block.OAK_FENCE.withProperties(Map.of("east", "true")));

        // Calls a method
        instance.setBlock(0, 43, 1, Block.BROWN_CARPET);
        // Calls a method
        instance.setBlock(0, 43, 0, Block.BROWN_CARPET);
        // Calls a method
        instance.setBlock(-1, 43, 1, Block.BROWN_CARPET);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(-0.925, 44.0625, 0.075)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(2, -2, 2));
        // Calls a method
        PhysicsResult res2 = CollisionUtils.handlePhysics(entity, new Vec(5, -5, 2));
        // Calls a method
        PhysicsResult res3 = CollisionUtils.handlePhysics(entity, new Vec(2, -5, 5));

        // Calls a method
        Point expected = new Pos(0.075, 43.0625, 1.075);

        // Calls a method
        assertEqualsPoint(expected, res.newPosition());
        // Calls a method
        assertEqualsPoint(expected, res2.newPosition());
        // Calls a method
        assertEqualsPoint(expected, res3.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckFallHitFenceLongMove(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 42, 0, Block.OAK_FENCE);
        // Calls a method
        instance.setBlock(0, 43, 0, Block.BROWN_CARPET);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.5, 54.0625, 0.5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -21, 0));
        // Calls a method
        assertEqualsPoint(new Pos(0.5, 43.5, 0.5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckFenceAboveHead(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Calls a method
        instance.setBlock(0, 45, 0, Block.OAK_FENCE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.5, 43.0, 0.5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 2, 0));
        // Calls a method
        assertEqualsPoint(new Pos(0.5, 43.05, 0.5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckDiagonal(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(1, 43, 1, Block.STONE);
        // Calls a method
        instance.setBlock(1, 43, 2, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(10, 0, 10));

        // First and second are both valid, it depends on the implementation
        // Calls a method
        assertPossiblePoints(List.of(new Pos(10, 42, 0.7), new Pos(0.7, 42, 10)), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckDirectSlide(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(1, 43, 1, Block.STONE);
        // Calls a method
        instance.setBlock(1, 43, 2, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.69, 42, 0.69)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(10, 0, 11));
        // Calls a method
        assertEqualsPoint(new Pos(0.7, 42, 11.69), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckCorner(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Loop: repeats a block
        for (int i = -2; i <= 2; ++i)
            // Loop: repeats a block
            for (int j = -2; j <= 2; ++j)
                // Calls a method
                instance.loadChunk(i, j).join();

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);

        // Calls a method
        instance.setBlock(5, 43, -5, Block.STONE);

        // Calls a method
        entity.setInstance(instance, new Pos(-0.3, 42, -0.3)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(10, 0, -10));

        // Calls a method
        assertEqualsPoint(new Pos(4.7, 42, -10.3), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckEnclosedHit(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Loop: repeats a block
        for (int i = -2; i <= 2; ++i)
            // Loop: repeats a block
            for (int j = -2; j <= 2; ++j)
                // Calls a method
                instance.loadChunk(i, j).join();

        // Calls a method
        instance.setBlock(8, 42, 8, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.SLIME);
        // Calls a method
        SlimeMeta meta = (SlimeMeta) entity.getEntityMeta();
        // Calls a method
        meta.setSize(20);

        // Calls a method
        entity.setInstance(instance, new Pos(5, 50, 5)).join();

        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -20, 0));

        // Calls a method
        assertEqualsPoint(new Pos(5, 43, 5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckEnclosedHitSubBlock(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Loop: repeats a block
        for (int i = -2; i <= 2; ++i)
            // Loop: repeats a block
            for (int j = -2; j <= 2; ++j)
                // Calls a method
                instance.loadChunk(i, j).join();

        // Calls a method
        instance.setBlock(8, 42, 8, Block.LANTERN);

        // Calls a method
        var entity = new Entity(EntityType.SLIME);
        // Calls a method
        SlimeMeta meta = (SlimeMeta) entity.getEntityMeta();
        // Calls a method
        meta.setSize(20);

        // Calls a method
        entity.setInstance(instance, new Pos(5, 42.8, 5)).join();

        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -0.4, 0));

        // Calls a method
        assertEqualsPoint(new Pos(5, 42.56, 5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckEnclosedMiss(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(11, 43, 11, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.SLIME);
        // Calls a method
        SlimeMeta meta = (SlimeMeta) entity.getEntityMeta();
        // Calls a method
        meta.setSize(5);

        // Calls a method
        entity.setInstance(instance, new Pos(5, 44, 5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -2, 0));

        // Calls a method
        assertEqualsPoint(new Pos(5, 42, 5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckEntityHit(Env env) {
        // Calls a method
        Point z1 = new Pos(0, 0, 0);
        // Calls a method
        Point z2 = new Pos(15, 0, 0);
        // Calls a method
        Point z3 = new Pos(11, 0, 0);
        // Calls a method
        Point movement = new Pos(20, 1, 0);

        // Calls a method
        BoundingBox bb = new Entity(EntityType.ZOMBIE).getBoundingBox();

        // Calls a method
        SweepResult sweepResultFinal = new SweepResult(1, 0, 0, 0, null, 0, 0, 0, 0, 0, 0);

        // Calls a method
        bb.intersectBoxSwept(z1, movement, z2, bb, sweepResultFinal);
        // Calls a method
        bb.intersectBoxSwept(z1, movement, z3, bb, sweepResultFinal);

        // Calls a method
        assertEqualsPoint(new Pos(10.4, 0.52, 0), new Vec(sweepResultFinal.collidedPositionX, sweepResultFinal.collidedPositionY, sweepResultFinal.collidedPositionZ));
        // Calls a method
        assertEquals(sweepResultFinal.collidedShape, bb);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckEdgeClip(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(1, 43, 1, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0.7)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(10, 0, 0));
        // Calls a method
        assertEqualsPoint(new Pos(0.7, 42, 0.7), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckEdgeClipSmall(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(1, 42, 1, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.6999, 42, 0.6999)).join();

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.702, 0, 0.702));

        // First and second are both valid, it depends on the implementation
        // Calls a method
        assertPossiblePoints(List.of(new Pos(1.402, 42, 0.7), new Pos(0.7, 42, 1.402)), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckDoorSubBlockNorth(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        Block b = Block.ACACIA_TRAPDOOR.withProperties(Map.of("facing", "north", "open", "true"));

        // Calls a method
        instance.setBlock(0, 42, 0, b);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.5, 42.5, 0.5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, 0.4));
        // Calls a method
        assertEqualsPoint(new Pos(0.5, 42.5, 0.512), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckDoorSubBlockSouth(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        Block b = Block.ACACIA_TRAPDOOR.withProperties(Map.of("facing", "south", "open", "true"));

        // Calls a method
        instance.setBlock(0, 42, 0, b);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.5, 42.5, 0.5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, -0.4));
        // Calls a method
        assertEqualsPoint(new Pos(0.5, 42.5, 0.487), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckDoorSubBlockWest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        Block b = Block.ACACIA_TRAPDOOR.withProperties(Map.of("facing", "west", "open", "true"));

        // Calls a method
        instance.setBlock(0, 42, 0, b);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.5, 42.5, 0.5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.6, 0, 0));
        // Calls a method
        assertEqualsPoint(new Pos(0.512, 42.5, 0.5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckDoorSubBlockEast(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        Block b = Block.ACACIA_TRAPDOOR.withProperties(Map.of("facing", "east", "open", "true"));

        // Calls a method
        instance.setBlock(0, 42, 0, b);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.5, 42.5, 0.5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(-0.6, 0, 0));
        // Calls a method
        assertEqualsPoint(new Pos(0.487, 42.5, 0.5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckDoorSubBlockUp(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        Block b = Block.ACACIA_TRAPDOOR.withProperties(Map.of("half", "top"));

        // Calls a method
        instance.setBlock(0, 44, 0, b);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.5, 42.7, 0.5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0.4, 0));
        // Calls a method
        assertEqualsPoint(new Pos(0.5, 42.862, 0.5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckDoorSubBlockDown(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Assigns a value
        Block b = Block.ACACIA_TRAPDOOR;

        // Calls a method
        instance.setBlock(0, 42, 0, b);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.5, 42.2, 0.5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -0.4, 0));
        // Calls a method
        assertEqualsPoint(new Pos(0.5, 42.187, 0.5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckOnGround(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 40, 0, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 50, 0)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -20, 0));
        // Calls a method
        assertTrue(res.isOnGround());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckStairTop(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 42, 0, Block.ACACIA_STAIRS);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.4, 42.5, 0.9)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, -1.2));
        // Calls a method
        assertEqualsPoint(new Pos(0.4, 42.5, 0.8), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckStairTopSmall(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 42, 0, Block.ACACIA_STAIRS);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.4, 42.5, 0.9)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, -0.2));
        // Calls a method
        assertEqualsPoint(new Pos(0.4, 42.5, 0.8), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckNotOnGround(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Loop: repeats a block
        for (int i = -2; i <= 2; ++i)
            // Loop: repeats a block
            for (int j = -2; j <= 2; ++j)
                // Calls a method
                instance.loadChunk(i, j).join();

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 50, 0)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -1, 0));
        // Calls a method
        assertFalse(res.isOnGround());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckNotOnGroundHitUp(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 60, 0, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 50, 0)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 20, 0));
        // Calls a method
        assertFalse(res.isOnGround());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckSlide(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(1, 43, 1, Block.STONE);
        // Calls a method
        instance.setBlock(1, 43, 2, Block.STONE);
        // Calls a method
        instance.setBlock(1, 43, 3, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(11, 0, 10));
        // Calls a method
        assertEqualsPoint(new Pos(11, 42, 0.7), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsSmallMoveCollide(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(1, 43, 0, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.6, 42, 0)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.3, 0, 0));
        // Calls a method
        assertEqualsPoint(new Pos(0.7, 42, 0), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void tmp(Env env) {
        // Calls a method
        BoundingBox boundingBox = new BoundingBox(3,2.8,3);
        // Calls a method
        Vec velocity = new Vec(1,3,5);
        // Calls a method
        Pos entityPosition = new Pos(0,0,0);
    // End of a block/expression
    }

    // Checks C include all checks for crossing one intermediate block (3 block checks)
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsSmallMoveC0(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(1, 42, 0, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setBoundingBox(BoundingBox.ZERO);

        // Calls a method
        entity.setInstance(instance, new Pos(0.7, 42, 0.5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.6, 0, 0.6));
        // Calls a method
        assertEqualsPoint(new Pos(1, 42, 1.1), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsSmallMoveC1(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 42, 1, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setBoundingBox(BoundingBox.ZERO);

        // Calls a method
        entity.setInstance(instance, new Pos(0.5, 42, 0.7)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.6, 0, 0.6));
        // Calls a method
        assertEqualsPoint(new Pos(1.1, 42, 1), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsSmallMoveC2(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(1, 42, 1, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setBoundingBox(BoundingBox.ZERO);

        // Calls a method
        entity.setInstance(instance, new Pos(0.8, 42, 1.3)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.6, 0, -0.6));
        // Calls a method
        assertEqualsPoint(new Pos(1, 42, 0.7), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsSmallMoveC3(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 42, 0, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setBoundingBox(BoundingBox.ZERO);

        // Calls a method
        entity.setInstance(instance, new Pos(0.7, 42, 1.1)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.6, 0, -0.6));
        // Calls a method
        assertEqualsPoint(new Pos(1.3, 42, 1), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsSmallMoveC4(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 42, 1, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setBoundingBox(BoundingBox.ZERO);

        // Calls a method
        entity.setInstance(instance, new Pos(1.1, 42, 1.3)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(-0.6, 0, -0.6));
        // Calls a method
        assertEqualsPoint(new Pos(1, 42, 0.7), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsSmallMoveC5(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(1, 42, 0, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setBoundingBox(BoundingBox.ZERO);

        // Calls a method
        entity.setInstance(instance, new Pos(1.3, 42, 1.1)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(-0.6, 0, -0.6));
        // Calls a method
        assertEqualsPoint(new Pos(0.7, 42, 1), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsSmallMoveC6(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 42, 0, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setBoundingBox(BoundingBox.ZERO);

        // Calls a method
        entity.setInstance(instance, new Pos(1.1, 42, 0.7)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(-0.6, 0, 0.6));
        // Calls a method
        assertEqualsPoint(new Pos(1, 42, 1.3), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsSmallMoveC7(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(1, 42, 1, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setBoundingBox(BoundingBox.ZERO);

        // Calls a method
        entity.setInstance(instance, new Pos(1.3, 42, 0.8)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(-0.6, 0, 0.6));
        // Calls a method
        assertEqualsPoint(new Pos(0.7, 42, 1), res.newPosition());
    // End of a block/expression
    }

    // Checks CE include checks for crossing two intermediate block (4 block checks)
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsSmallMoveC0E(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(1, 43, 0, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setBoundingBox(BoundingBox.ZERO);

        // Calls a method
        entity.setInstance(instance, new Pos(0.51, 42.51, 0.5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.57, 0.57, 0.57));
        // Calls a method
        assertPossiblePoints(List.of(new Pos(1.08, 43, 1.07), new Pos(1.0, 43.08, 1.07)), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsSmallMoveC1E(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 43, 1, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setBoundingBox(BoundingBox.ZERO);

        // Calls a method
        entity.setInstance(instance, new Pos(0.50, 42.51, 0.51)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.57, 0.57, 0.57));
        // Calls a method
        assertPossiblePoints(List.of(new Pos(1.07, 43, 1.08), new Pos(1.07, 43.08, 1.0)), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsSmallMoveC2E(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(1, 43, 1, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setBoundingBox(BoundingBox.ZERO);

        // Calls a method
        entity.setInstance(instance, new Pos(0.51, 42.50, 0.51)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.57, 0.57, 0.57));

        // Calls a method
        assertPossiblePoints(List.of(new Pos(1.0, 43.08, 1.08), new Pos(1.08, 43.0, 1.08), new Pos(1.08, 43.08, 1.0)), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckNoCollision(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Loop: repeats a block
        for (int i = -2; i <= 2; ++i)
            // Loop: repeats a block
            for (int j = -2; j <= 2; ++j)
                // Calls a method
                instance.loadChunk(i, j).join();

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, 10));
        // Calls a method
        assertEqualsPoint(new Pos(0, 42, 10), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckBlockMiss(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 43, 2, Block.STONE);
        // Calls a method
        instance.setBlock(2, 43, 0, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(10, 0, 10));
        // Calls a method
        assertEqualsPoint(new Pos(10, 42, 10), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckBlockDirections(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Calls a method
        instance.setBlock(0, 43, 1, Block.STONE);
        // Calls a method
        instance.setBlock(1, 43, 0, Block.STONE);

        // Calls a method
        instance.setBlock(0, 43, -1, Block.STONE);
        // Calls a method
        instance.setBlock(-1, 43, 0, Block.STONE);

        // Calls a method
        instance.setBlock(0, 41, 0, Block.STONE);
        // Calls a method
        instance.setBlock(0, 44, 0, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.5, 42, 0.5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult px = CollisionUtils.handlePhysics(entity, new Vec(10, 0, 0));
        // Calls a method
        PhysicsResult py = CollisionUtils.handlePhysics(entity, new Vec(0, 10, 0));
        // Calls a method
        PhysicsResult pz = CollisionUtils.handlePhysics(entity, new Vec(0, 0, 10));

        // Calls a method
        PhysicsResult nx = CollisionUtils.handlePhysics(entity, new Vec(-10, 0, 0));
        // Calls a method
        PhysicsResult ny = CollisionUtils.handlePhysics(entity, new Vec(0, -10, 0));
        // Calls a method
        PhysicsResult nz = CollisionUtils.handlePhysics(entity, new Vec(0, 0, -10));

        // Calls a method
        assertEqualsPoint(new Pos(0.7, 42, 0.5), px.newPosition());
        // Calls a method
        assertEqualsPoint(new Pos(0.5, 42.05, 0.5), py.newPosition());
        // Calls a method
        assertEqualsPoint(new Pos(0.5, 42, 0.7), pz.newPosition());

        // Calls a method
        assertEqualsPoint(new Pos(0.3, 42, 0.5), nx.newPosition());
        // Calls a method
        assertEqualsPoint(new Pos(0.5, 42, 0.5), ny.newPosition());
        // Calls a method
        assertEqualsPoint(new Pos(0.5, 42, 0.3), nz.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckLargeVelocityMiss(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);

        // Assigns a value
        final int distance = 20;
        // Loop: repeats a block
        for (int x = 0; x < distance; ++x) instance.loadChunk(x, 0).join();

        // Calls a method
        entity.setInstance(instance, new Pos(5, 42, 5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec((distance - 1) * 16, 0, 0));
        // Calls a method
        assertEqualsPoint(new Pos((distance - 1) * 16 + 5, 42, 5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckLargeVelocityHit(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);

        // Assigns a value
        final int distance = 20;
        // Loop: repeats a block
        for (int x = 0; x < distance; ++x) instance.loadChunk(x, 0).join();

        // Calls a method
        instance.setBlock(distance * 8, 43, 5, Block.STONE);

        // Calls a method
        entity.setInstance(instance, new Pos(5, 42, 5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec((distance - 1) * 16, 0, 0));
        // Calls a method
        assertEqualsPoint(new Pos(distance * 8 - 0.3, 42, 5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckNoMove(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);

        // Calls a method
        entity.setInstance(instance, new Pos(5, 42, 5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, Vec.ZERO);
        // Calls a method
        assertEqualsPoint(new Pos(5, 42, 5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsRepeatedCollision(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Assigns a value
        PhysicsResult previousResult = null;

        // Calls a method
        instance.setBlock(0, 41, 0, Block.STONE);

        // Calls a method
        instance.setBlock(1, 42, 0, Block.STONE);
        // Calls a method
        instance.setBlock(0, 42, 1, Block.STONE);
        // Calls a method
        instance.setBlock(0, 42, -1, Block.STONE);
        // Calls a method
        instance.setBlock(-1, 42, 0, Block.STONE);

        // Calls a method
        instance.setBlock(1, 43, 0, Block.STONE);
        // Calls a method
        instance.setBlock(0, 43, 1, Block.STONE);
        // Calls a method
        instance.setBlock(0, 43, -1, Block.STONE);
        // Calls a method
        instance.setBlock(-1, 43, 0, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.5, 43.1, 0.5)).join();

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, 0));
        // Calls a method
        entity.teleport(res.newPosition()).join();

        // Loop: repeats a block
        while ((previousResult == null || !previousResult.newPosition().samePoint(res.newPosition())) && entity.getPosition().y() >= 42) {
            // Assigns a value
            previousResult = res;
            // Calls a method
            res = CollisionUtils.handlePhysics(entity, new Vec(0.1, -0.01, 0));
            // Calls a method
            entity.teleport(res.newPosition()).join();
        // End of a block/expression
        }

        // Calls a method
        assertEqualsPoint(new Pos(0.7, 42, 0.5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckNoMoveCache(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);

        // Calls a method
        entity.setInstance(instance, new Pos(5, 42, 5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, Vec.ZERO);
        // Calls a method
        entity.teleport(res.newPosition());
        // Calls a method
        res = CollisionUtils.handlePhysics(entity, Vec.ZERO, res);
        // Calls a method
        assertEqualsPoint(new Pos(5, 42, 5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckNoMoveLargeVelocityHit(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);

        // Assigns a value
        final int distance = 20;
        // Loop: repeats a block
        for (int x = 0; x < distance; ++x) instance.loadChunk(x, 0).join();

        // Calls a method
        instance.setBlock(distance * 8, 43, 5, Block.STONE);

        // Calls a method
        entity.setInstance(instance, new Pos(5, 42, 5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, Vec.ZERO);
        // Calls a method
        entity.teleport(res.newPosition());
        // Calls a method
        res = CollisionUtils.handlePhysics(entity, new Vec((distance - 1) * 16, 0, 0), res);
        // Calls a method
        assertEqualsPoint(new Pos(distance * 8 - 0.3, 42, 5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckLargeVelocityHitNoMove(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);

        // Assigns a value
        final int distance = 20;
        // Loop: repeats a block
        for (int x = 0; x < distance; ++x) instance.loadChunk(x, 0).join();

        // Calls a method
        instance.setBlock(distance * 8, 43, 5, Block.STONE);

        // Calls a method
        entity.setInstance(instance, new Pos(5, 42, 5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec((distance - 1) * 16, 0, 0));
        // Calls a method
        entity.teleport(res.newPosition());
        // Calls a method
        res = CollisionUtils.handlePhysics(entity, Vec.ZERO, res);
        // Calls a method
        assertEqualsPoint(new Pos(distance * 8 - 0.3, 42, 5), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckDoorSubBlockSouthRepeat(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        Block b = Block.ACACIA_TRAPDOOR.withProperties(Map.of("facing", "south", "open", "true"));

        // Calls a method
        instance.setBlock(0, 42, 0, b);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0.5, 42.5, 0.5)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, -0.4));
        // Calls a method
        entity.teleport(res.newPosition());
        // Calls a method
        res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, -0.4), res);

        // Calls a method
        assertEqualsPoint(new Pos(0.5, 42.5, 0.487), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckCollisionDownCache(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 43, 1, Block.STONE);

        // Loop: repeats a block
        for (int i = -2; i <= 2; ++i)
            // Loop: repeats a block
            for (int j = -2; j <= 2; ++j)
                // Calls a method
                instance.loadChunk(i, j).join();

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, 10));
        // Calls a method
        entity.teleport(res.newPosition());
        // Calls a method
        res = CollisionUtils.handlePhysics(entity, new Vec(0, -10, 0), res);

        // Calls a method
        assertEqualsPoint(new Pos(0, 40, 0.7), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCheckGravityCached(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 43, 1, Block.STONE);

        // Loop: repeats a block
        for (int i = -2; i <= 2; ++i)
            // Loop: repeats a block
            for (int j = -2; j <= 2; ++j)
                // Calls a method
                instance.loadChunk(i, j).join();

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());

        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, 10));
        // Calls a method
        entity.teleport(res.newPosition());
        // Calls a method
        res = CollisionUtils.handlePhysics(entity, new Vec(0, -10, 0), res);
        // Calls a method
        entity.teleport(res.newPosition());

        // Code statement
        PhysicsResult lastPhysicsResult;

        // Loop: repeats a block
        for (int x = 0; x < 50; ++x) {
            // Assigns a value
            lastPhysicsResult = res;
            // Calls a method
            res = CollisionUtils.handlePhysics(entity, new Vec(0, -1.7, 0), res);
            // Calls a method
            entity.teleport(res.newPosition());

            // Branch: checks a condition
            if (x > 10) assertSame(lastPhysicsResult, res, "Physics result not cached");
        // End of a block/expression
        }

        // Calls a method
        assertEqualsPoint(new Pos(0, 40, 0.7), res.newPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityBlockPositionTestSlightlyAbove(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 42, 0, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 43.00001, 0));

        // Calls a method
        var deltaPos = new Vec(0.0, -10, 0.0);
        // Calls a method
        var physicsResult = CollisionUtils.handlePhysics(entity, deltaPos, null);

        // Calls a method
        var newPos = physicsResult.newPosition();
        // Calls a method
        assertEquals(43, newPos.blockY());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityBlockPositionTestFarAbove(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 42, 0, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 43.5, 0));

        // Calls a method
        var deltaPos = new Vec(0.0, -10, 0.0);
        // Calls a method
        var physicsResult = CollisionUtils.handlePhysics(entity, deltaPos, null);

        // Calls a method
        var newPos = physicsResult.newPosition();
        // Calls a method
        assertEquals(43, newPos.blockY());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityPhysicsCacheTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.setBlock(0, 42, 0, Block.STONE);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 43.5, 0));

        // Calls a method
        var deltaPos = new Vec(0.0, -10, 0.0);
        // Calls a method
        var physicsResult = CollisionUtils.handlePhysics(entity, deltaPos, null);

        // Calls a method
        var newPos = physicsResult.newPosition();
        // Calls a method
        assertEquals(43, newPos.blockY());
        // Calls a method
        assertEqualsPoint(new Vec(0, 0, 0), physicsResult.newVelocity());
        // Calls a method
        assertEqualsPoint(deltaPos, physicsResult.originalDelta());

        // Create a new instance of the physics result to simulate gravity or we will never cache because velocity would be zero.
        // Calls a method
        var velocityFixedResult = new PhysicsResult(physicsResult.newPosition(), physicsResult.newVelocity().add(deltaPos), physicsResult.isOnGround(), physicsResult.collisionX(), physicsResult.collisionY(), physicsResult.collisionZ(), physicsResult.originalDelta(), physicsResult.collisionPoints(), physicsResult.collisionShapes(), physicsResult.collisionShapePositions(), physicsResult.hasCollision(), physicsResult.res(), false);

        // Assigns a value
        var physicsResult2 = CollisionUtils.handlePhysics(instance, entity.getChunk(),
                // Code statement
                entity.getBoundingBox(),
                // Code statement
                physicsResult.newPosition(), deltaPos,
                // Code statement
                velocityFixedResult, false);

        // Calls a method
        assertTrue(physicsResult2.cached());
    // End of a block/expression
    }
// End of a block/expression
}