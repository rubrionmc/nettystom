// Déclaration du paquet de ce fichier
package net.minestom.server.collision;

// Import d'une classe nécessaire
import net.minestom.server.utils.block.BlockIterator;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.other.SlimeMeta;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.Iterator;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityBlockPhysicsIntegrationTest {
    // Appelle une méthode
    private static final Point PRECISION = new Pos(0.01, 0.01, 0.01);

    // Début d'une méthode/d'un bloc
    private static boolean checkPoints(Point expected, Point actual) {
        // Appelle une méthode
        Point diff = expected.sub(actual);

        // Renvoie une valeur à l'appelant
        return (PRECISION.x() > Math.abs(diff.x()))
                // Instruction de code
                && (PRECISION.y() > Math.abs(diff.y()))
                // Appelle une méthode
                && (PRECISION.z() > Math.abs(diff.z()));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void assertEqualsPoint(Point expected, Point actual) {
        // Appelle une méthode
        assertEquals(expected.x(), actual.x(), PRECISION.x());
        // Appelle une méthode
        assertEquals(expected.y(), actual.y(), PRECISION.y());
        // Appelle une méthode
        assertEquals(expected.z(), actual.z(), PRECISION.z());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void assertPossiblePoints(List<Point> expected, Point actual) {
        // Boucle : répète un bloc
        for (Point point : expected) {
            // Embranchement : vérifie une condition
            if (checkPoints(point, actual)) {
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        fail("Expected one of the following points: " + expected);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckCollision(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 43, 1, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, 10));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0, 42, 0.7), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckShortDiagonal(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 42, 1, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0.9)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        entity.setBoundingBox(BoundingBox.ZERO);

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, 1.3));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0, 42, 1), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckSlab(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Boucle : répète un bloc
        for (int i = -2; i <= 2; ++i)
            // Boucle : répète un bloc
            for (int j = -2; j <= 2; ++j)
                // Appelle une méthode
                instance.loadChunk(i, j).join();

        // Appelle une méthode
        instance.setBlock(0, 42, 0, Block.STONE_SLAB);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 44, 0)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -10, 0));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0, 42.5, 0), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckShallowAngle(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(13, 99, 16, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(12.812, 100.0, 16.498)).join();

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.273, -0.0784, 0.0));
        // Appelle une méthode
        assertTrue(res.isOnGround());
        // Appelle une méthode
        assertTrue(res.collisionY());
        // Appelle une méthode
        assertEqualsPoint(new Vec(13.09, 100, 16.5), res.newPosition());
        // Appelle une méthode
        assertEqualsPoint(new Vec(0.273, 0, 0), res.newVelocity());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckFallFence(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 42, 0, Block.OAK_FENCE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.5, 43.5, 0.5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -0.25, 0));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.5, 43.5, 0.5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckFallHitCarpet(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Boucle : répète un bloc
        for (int i = -2; i <= 2; ++i)
            // Boucle : répète un bloc
            for (int j = -2; j <= 2; ++j)
                // Appelle une méthode
                instance.loadChunk(i, j).join();

        // Appelle une méthode
        instance.setBlock(0, 42, 0, Block.OAK_FENCE);
        // Appelle une méthode
        instance.setBlock(0, 43, 0, Block.BROWN_CARPET);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 54.0625, 0)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -11.03, 0));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0, 43.0625, 0), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckFallHitFence(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 42, 0, Block.OAK_FENCE);
        // Appelle une méthode
        instance.setBlock(0, 43, 0, Block.BROWN_CARPET);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.5, 54.0625, 0.5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -11.03, 0));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.5, 43.5, 0.5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckHorizontalFence(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(1, 42, 0, Block.OAK_FENCE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.5, 43.25, 0.5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(2, 0, 0));
        // Appelle une méthode
        assertEqualsPoint(new Pos(1.075, 43.25, 0.5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckMultipleBlocksPassFirst(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(4, 40, -1, Block.SANDSTONE_STAIRS);
        // Appelle une méthode
        instance.setBlock(16, 40, 0, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.0, 40.51, 0.0)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(20, 0, 0));
        // Appelle une méthode
        assertEqualsPoint(new Pos(15.7, 40.51, 0), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckMultipleBlocksHitFirst(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(4, 40, 0, Block.GRASS_BLOCK);
        // Appelle une méthode
        instance.setBlock(16, 40, 0, Block.STONE);

        // Appelle une méthode
        instance.loadChunk(0, -1).join();

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.0, 40.51, 0.0)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(20, 0, 0));

        // Appelle une méthode
        assertEqualsPoint(new Pos(3.7, 40.51, 0), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckHorizontalCarpetedFence(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(1, 42, 0, Block.OAK_FENCE);
        // Appelle une méthode
        instance.setBlock(1, 43, 0, Block.BROWN_CARPET);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.5, 43.25, 0.5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(2, 0, 0));
        // Appelle une méthode
        assertEqualsPoint(new Pos(1.075, 43.25, 0.5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckDiagonalCarpetedFenceX(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Boucle : répète un bloc
        for (int i = -2; i <= 2; ++i)
            // Boucle : répète un bloc
            for (int j = -2; j <= 2; ++j)
                // Appelle une méthode
                instance.loadChunk(i, j).join();

        // Appelle une méthode
        instance.setBlock(1, 42, 0, Block.OAK_FENCE);
        // Appelle une méthode
        instance.setBlock(1, 43, 0, Block.BROWN_CARPET);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.075, 44.0625, 0.5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(2, -2, 0));
        // Appelle une méthode
        assertEqualsPoint(new Pos(1.075, 43.0625, 0.5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckDiagonalCarpetedFenceZ(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Boucle : répète un bloc
        for (int i = -2; i <= 2; ++i)
            // Boucle : répète un bloc
            for (int j = -2; j <= 2; ++j)
                // Appelle une méthode
                instance.loadChunk(i, j).join();

        // Appelle une méthode
        instance.setBlock(0, 42, 1, Block.OAK_FENCE);
        // Appelle une méthode
        instance.setBlock(0, 43, 1, Block.BROWN_CARPET);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.5, 44.0625, 0.075)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -2, 2));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.5, 43.0625, 1.075), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckDiagonalCarpetedFenceXZ(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Boucle : répète un bloc
        for (int i = -2; i <= 2; ++i)
            // Boucle : répète un bloc
            for (int j = -2; j <= 2; ++j)
                // Appelle une méthode
                instance.loadChunk(i, j).join();

        // Appelle une méthode
        instance.setBlock(0, 42, 1, Block.OAK_FENCE.withProperties(Map.of("north", "true", "west", "true")));
        // Appelle une méthode
        instance.setBlock(0, 42, 0, Block.OAK_FENCE.withProperties(Map.of("south", "true")));
        // Appelle une méthode
        instance.setBlock(-1, 42, 1, Block.OAK_FENCE.withProperties(Map.of("east", "true")));

        // Appelle une méthode
        instance.setBlock(0, 43, 1, Block.BROWN_CARPET);
        // Appelle une méthode
        instance.setBlock(0, 43, 0, Block.BROWN_CARPET);
        // Appelle une méthode
        instance.setBlock(-1, 43, 1, Block.BROWN_CARPET);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(-0.925, 44.0625, 0.075)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(2, -2, 2));
        // Appelle une méthode
        PhysicsResult res2 = CollisionUtils.handlePhysics(entity, new Vec(5, -5, 2));
        // Appelle une méthode
        PhysicsResult res3 = CollisionUtils.handlePhysics(entity, new Vec(2, -5, 5));

        // Appelle une méthode
        Point expected = new Pos(0.075, 43.0625, 1.075);

        // Appelle une méthode
        assertEqualsPoint(expected, res.newPosition());
        // Appelle une méthode
        assertEqualsPoint(expected, res2.newPosition());
        // Appelle une méthode
        assertEqualsPoint(expected, res3.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckFallHitFenceLongMove(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 42, 0, Block.OAK_FENCE);
        // Appelle une méthode
        instance.setBlock(0, 43, 0, Block.BROWN_CARPET);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.5, 54.0625, 0.5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -21, 0));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.5, 43.5, 0.5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckFenceAboveHead(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Appelle une méthode
        instance.setBlock(0, 45, 0, Block.OAK_FENCE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.5, 43.0, 0.5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 2, 0));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.5, 43.05, 0.5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckDiagonal(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(1, 43, 1, Block.STONE);
        // Appelle une méthode
        instance.setBlock(1, 43, 2, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(10, 0, 10));

        // First and second are both valid, it depends on the implementation
        // Appelle une méthode
        assertPossiblePoints(List.of(new Pos(10, 42, 0.7), new Pos(0.7, 42, 10)), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckDirectSlide(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(1, 43, 1, Block.STONE);
        // Appelle une méthode
        instance.setBlock(1, 43, 2, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.69, 42, 0.69)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(10, 0, 11));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.7, 42, 11.69), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckCorner(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Boucle : répète un bloc
        for (int i = -2; i <= 2; ++i)
            // Boucle : répète un bloc
            for (int j = -2; j <= 2; ++j)
                // Appelle une méthode
                instance.loadChunk(i, j).join();

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);

        // Appelle une méthode
        instance.setBlock(5, 43, -5, Block.STONE);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(-0.3, 42, -0.3)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(10, 0, -10));

        // Appelle une méthode
        assertEqualsPoint(new Pos(4.7, 42, -10.3), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckEnclosedHit(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Boucle : répète un bloc
        for (int i = -2; i <= 2; ++i)
            // Boucle : répète un bloc
            for (int j = -2; j <= 2; ++j)
                // Appelle une méthode
                instance.loadChunk(i, j).join();

        // Appelle une méthode
        instance.setBlock(8, 42, 8, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.SLIME);
        // Appelle une méthode
        SlimeMeta meta = (SlimeMeta) entity.getEntityMeta();
        // Appelle une méthode
        meta.setSize(20);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(5, 50, 5)).join();

        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -20, 0));

        // Appelle une méthode
        assertEqualsPoint(new Pos(5, 43, 5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckEnclosedHitSubBlock(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Boucle : répète un bloc
        for (int i = -2; i <= 2; ++i)
            // Boucle : répète un bloc
            for (int j = -2; j <= 2; ++j)
                // Appelle une méthode
                instance.loadChunk(i, j).join();

        // Appelle une méthode
        instance.setBlock(8, 42, 8, Block.LANTERN);

        // Appelle une méthode
        var entity = new Entity(EntityType.SLIME);
        // Appelle une méthode
        SlimeMeta meta = (SlimeMeta) entity.getEntityMeta();
        // Appelle une méthode
        meta.setSize(20);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(5, 42.8, 5)).join();

        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -0.4, 0));

        // Appelle une méthode
        assertEqualsPoint(new Pos(5, 42.56, 5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckEnclosedMiss(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(11, 43, 11, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.SLIME);
        // Appelle une méthode
        SlimeMeta meta = (SlimeMeta) entity.getEntityMeta();
        // Appelle une méthode
        meta.setSize(5);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(5, 44, 5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -2, 0));

        // Appelle une méthode
        assertEqualsPoint(new Pos(5, 42, 5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckEntityHit(Env env) {
        // Appelle une méthode
        Point z1 = new Pos(0, 0, 0);
        // Appelle une méthode
        Point z2 = new Pos(15, 0, 0);
        // Appelle une méthode
        Point z3 = new Pos(11, 0, 0);
        // Appelle une méthode
        Point movement = new Pos(20, 1, 0);

        // Appelle une méthode
        BoundingBox bb = new Entity(EntityType.ZOMBIE).getBoundingBox();

        // Appelle une méthode
        SweepResult sweepResultFinal = new SweepResult(1, 0, 0, 0, null, 0, 0, 0, 0, 0, 0);

        // Appelle une méthode
        bb.intersectBoxSwept(z1, movement, z2, bb, sweepResultFinal);
        // Appelle une méthode
        bb.intersectBoxSwept(z1, movement, z3, bb, sweepResultFinal);

        // Appelle une méthode
        assertEqualsPoint(new Pos(10.4, 0.52, 0), new Vec(sweepResultFinal.collidedPositionX, sweepResultFinal.collidedPositionY, sweepResultFinal.collidedPositionZ));
        // Appelle une méthode
        assertEquals(sweepResultFinal.collidedShape, bb);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckEdgeClip(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(1, 43, 1, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0.7)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(10, 0, 0));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.7, 42, 0.7), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckEdgeClipSmall(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(1, 42, 1, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.6999, 42, 0.6999)).join();

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.702, 0, 0.702));

        // First and second are both valid, it depends on the implementation
        // Appelle une méthode
        assertPossiblePoints(List.of(new Pos(1.402, 42, 0.7), new Pos(0.7, 42, 1.402)), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckDoorSubBlockNorth(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        Block b = Block.ACACIA_TRAPDOOR.withProperties(Map.of("facing", "north", "open", "true"));

        // Appelle une méthode
        instance.setBlock(0, 42, 0, b);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.5, 42.5, 0.5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, 0.4));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.5, 42.5, 0.512), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckDoorSubBlockSouth(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        Block b = Block.ACACIA_TRAPDOOR.withProperties(Map.of("facing", "south", "open", "true"));

        // Appelle une méthode
        instance.setBlock(0, 42, 0, b);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.5, 42.5, 0.5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, -0.4));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.5, 42.5, 0.487), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckDoorSubBlockWest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        Block b = Block.ACACIA_TRAPDOOR.withProperties(Map.of("facing", "west", "open", "true"));

        // Appelle une méthode
        instance.setBlock(0, 42, 0, b);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.5, 42.5, 0.5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.6, 0, 0));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.512, 42.5, 0.5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckDoorSubBlockEast(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        Block b = Block.ACACIA_TRAPDOOR.withProperties(Map.of("facing", "east", "open", "true"));

        // Appelle une méthode
        instance.setBlock(0, 42, 0, b);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.5, 42.5, 0.5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(-0.6, 0, 0));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.487, 42.5, 0.5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckDoorSubBlockUp(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        Block b = Block.ACACIA_TRAPDOOR.withProperties(Map.of("half", "top"));

        // Appelle une méthode
        instance.setBlock(0, 44, 0, b);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.5, 42.7, 0.5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0.4, 0));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.5, 42.862, 0.5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckDoorSubBlockDown(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Affecte une valeur
        Block b = Block.ACACIA_TRAPDOOR;

        // Appelle une méthode
        instance.setBlock(0, 42, 0, b);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.5, 42.2, 0.5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -0.4, 0));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.5, 42.187, 0.5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckOnGround(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 40, 0, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 50, 0)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -20, 0));
        // Appelle une méthode
        assertTrue(res.isOnGround());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckStairTop(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 42, 0, Block.ACACIA_STAIRS);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.4, 42.5, 0.9)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, -1.2));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.4, 42.5, 0.8), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckStairTopSmall(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 42, 0, Block.ACACIA_STAIRS);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.4, 42.5, 0.9)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, -0.2));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.4, 42.5, 0.8), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckNotOnGround(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Boucle : répète un bloc
        for (int i = -2; i <= 2; ++i)
            // Boucle : répète un bloc
            for (int j = -2; j <= 2; ++j)
                // Appelle une méthode
                instance.loadChunk(i, j).join();

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 50, 0)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, -1, 0));
        // Appelle une méthode
        assertFalse(res.isOnGround());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckNotOnGroundHitUp(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 60, 0, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 50, 0)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 20, 0));
        // Appelle une méthode
        assertFalse(res.isOnGround());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckSlide(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(1, 43, 1, Block.STONE);
        // Appelle une méthode
        instance.setBlock(1, 43, 2, Block.STONE);
        // Appelle une méthode
        instance.setBlock(1, 43, 3, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(11, 0, 10));
        // Appelle une méthode
        assertEqualsPoint(new Pos(11, 42, 0.7), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsSmallMoveCollide(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(1, 43, 0, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.6, 42, 0)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.3, 0, 0));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.7, 42, 0), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void tmp(Env env) {
        // Appelle une méthode
        BoundingBox boundingBox = new BoundingBox(3,2.8,3);
        // Appelle une méthode
        Vec velocity = new Vec(1,3,5);
        // Appelle une méthode
        Pos entityPosition = new Pos(0,0,0);
    // Fin d'un bloc/d'une expression
    }

    // Checks C include all checks for crossing one intermediate block (3 block checks)
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsSmallMoveC0(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(1, 42, 0, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setBoundingBox(BoundingBox.ZERO);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.7, 42, 0.5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.6, 0, 0.6));
        // Appelle une méthode
        assertEqualsPoint(new Pos(1, 42, 1.1), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsSmallMoveC1(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 42, 1, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setBoundingBox(BoundingBox.ZERO);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.5, 42, 0.7)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.6, 0, 0.6));
        // Appelle une méthode
        assertEqualsPoint(new Pos(1.1, 42, 1), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsSmallMoveC2(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(1, 42, 1, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setBoundingBox(BoundingBox.ZERO);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.8, 42, 1.3)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.6, 0, -0.6));
        // Appelle une méthode
        assertEqualsPoint(new Pos(1, 42, 0.7), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsSmallMoveC3(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 42, 0, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setBoundingBox(BoundingBox.ZERO);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.7, 42, 1.1)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.6, 0, -0.6));
        // Appelle une méthode
        assertEqualsPoint(new Pos(1.3, 42, 1), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsSmallMoveC4(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 42, 1, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setBoundingBox(BoundingBox.ZERO);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(1.1, 42, 1.3)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(-0.6, 0, -0.6));
        // Appelle une méthode
        assertEqualsPoint(new Pos(1, 42, 0.7), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsSmallMoveC5(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(1, 42, 0, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setBoundingBox(BoundingBox.ZERO);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(1.3, 42, 1.1)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(-0.6, 0, -0.6));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.7, 42, 1), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsSmallMoveC6(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 42, 0, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setBoundingBox(BoundingBox.ZERO);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(1.1, 42, 0.7)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(-0.6, 0, 0.6));
        // Appelle une méthode
        assertEqualsPoint(new Pos(1, 42, 1.3), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsSmallMoveC7(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(1, 42, 1, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setBoundingBox(BoundingBox.ZERO);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(1.3, 42, 0.8)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(-0.6, 0, 0.6));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.7, 42, 1), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Checks CE include checks for crossing two intermediate block (4 block checks)
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsSmallMoveC0E(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(1, 43, 0, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setBoundingBox(BoundingBox.ZERO);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.51, 42.51, 0.5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.57, 0.57, 0.57));
        // Appelle une méthode
        assertPossiblePoints(List.of(new Pos(1.08, 43, 1.07), new Pos(1.0, 43.08, 1.07)), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsSmallMoveC1E(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 43, 1, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setBoundingBox(BoundingBox.ZERO);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.50, 42.51, 0.51)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.57, 0.57, 0.57));
        // Appelle une méthode
        assertPossiblePoints(List.of(new Pos(1.07, 43, 1.08), new Pos(1.07, 43.08, 1.0)), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsSmallMoveC2E(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(1, 43, 1, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setBoundingBox(BoundingBox.ZERO);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.51, 42.50, 0.51)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0.57, 0.57, 0.57));

        // Appelle une méthode
        assertPossiblePoints(List.of(new Pos(1.0, 43.08, 1.08), new Pos(1.08, 43.0, 1.08), new Pos(1.08, 43.08, 1.0)), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckNoCollision(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Boucle : répète un bloc
        for (int i = -2; i <= 2; ++i)
            // Boucle : répète un bloc
            for (int j = -2; j <= 2; ++j)
                // Appelle une méthode
                instance.loadChunk(i, j).join();

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, 10));
        // Appelle une méthode
        assertEqualsPoint(new Pos(0, 42, 10), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckBlockMiss(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 43, 2, Block.STONE);
        // Appelle une méthode
        instance.setBlock(2, 43, 0, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(10, 0, 10));
        // Appelle une méthode
        assertEqualsPoint(new Pos(10, 42, 10), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckBlockDirections(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Appelle une méthode
        instance.setBlock(0, 43, 1, Block.STONE);
        // Appelle une méthode
        instance.setBlock(1, 43, 0, Block.STONE);

        // Appelle une méthode
        instance.setBlock(0, 43, -1, Block.STONE);
        // Appelle une méthode
        instance.setBlock(-1, 43, 0, Block.STONE);

        // Appelle une méthode
        instance.setBlock(0, 41, 0, Block.STONE);
        // Appelle une méthode
        instance.setBlock(0, 44, 0, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.5, 42, 0.5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult px = CollisionUtils.handlePhysics(entity, new Vec(10, 0, 0));
        // Appelle une méthode
        PhysicsResult py = CollisionUtils.handlePhysics(entity, new Vec(0, 10, 0));
        // Appelle une méthode
        PhysicsResult pz = CollisionUtils.handlePhysics(entity, new Vec(0, 0, 10));

        // Appelle une méthode
        PhysicsResult nx = CollisionUtils.handlePhysics(entity, new Vec(-10, 0, 0));
        // Appelle une méthode
        PhysicsResult ny = CollisionUtils.handlePhysics(entity, new Vec(0, -10, 0));
        // Appelle une méthode
        PhysicsResult nz = CollisionUtils.handlePhysics(entity, new Vec(0, 0, -10));

        // Appelle une méthode
        assertEqualsPoint(new Pos(0.7, 42, 0.5), px.newPosition());
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.5, 42.05, 0.5), py.newPosition());
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.5, 42, 0.7), pz.newPosition());

        // Appelle une méthode
        assertEqualsPoint(new Pos(0.3, 42, 0.5), nx.newPosition());
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.5, 42, 0.5), ny.newPosition());
        // Appelle une méthode
        assertEqualsPoint(new Pos(0.5, 42, 0.3), nz.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckLargeVelocityMiss(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);

        // Affecte une valeur
        final int distance = 20;
        // Boucle : répète un bloc
        for (int x = 0; x < distance; ++x) instance.loadChunk(x, 0).join();

        // Appelle une méthode
        entity.setInstance(instance, new Pos(5, 42, 5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec((distance - 1) * 16, 0, 0));
        // Appelle une méthode
        assertEqualsPoint(new Pos((distance - 1) * 16 + 5, 42, 5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckLargeVelocityHit(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);

        // Affecte une valeur
        final int distance = 20;
        // Boucle : répète un bloc
        for (int x = 0; x < distance; ++x) instance.loadChunk(x, 0).join();

        // Appelle une méthode
        instance.setBlock(distance * 8, 43, 5, Block.STONE);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(5, 42, 5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec((distance - 1) * 16, 0, 0));
        // Appelle une méthode
        assertEqualsPoint(new Pos(distance * 8 - 0.3, 42, 5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckNoMove(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(5, 42, 5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, Vec.ZERO);
        // Appelle une méthode
        assertEqualsPoint(new Pos(5, 42, 5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsRepeatedCollision(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Affecte une valeur
        PhysicsResult previousResult = null;

        // Appelle une méthode
        instance.setBlock(0, 41, 0, Block.STONE);

        // Appelle une méthode
        instance.setBlock(1, 42, 0, Block.STONE);
        // Appelle une méthode
        instance.setBlock(0, 42, 1, Block.STONE);
        // Appelle une méthode
        instance.setBlock(0, 42, -1, Block.STONE);
        // Appelle une méthode
        instance.setBlock(-1, 42, 0, Block.STONE);

        // Appelle une méthode
        instance.setBlock(1, 43, 0, Block.STONE);
        // Appelle une méthode
        instance.setBlock(0, 43, 1, Block.STONE);
        // Appelle une méthode
        instance.setBlock(0, 43, -1, Block.STONE);
        // Appelle une méthode
        instance.setBlock(-1, 43, 0, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.5, 43.1, 0.5)).join();

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, 0));
        // Appelle une méthode
        entity.teleport(res.newPosition()).join();

        // Boucle : répète un bloc
        while ((previousResult == null || !previousResult.newPosition().samePoint(res.newPosition())) && entity.getPosition().y() >= 42) {
            // Affecte une valeur
            previousResult = res;
            // Appelle une méthode
            res = CollisionUtils.handlePhysics(entity, new Vec(0.1, -0.01, 0));
            // Appelle une méthode
            entity.teleport(res.newPosition()).join();
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        assertEqualsPoint(new Pos(0.7, 42, 0.5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckNoMoveCache(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(5, 42, 5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, Vec.ZERO);
        // Appelle une méthode
        entity.teleport(res.newPosition());
        // Appelle une méthode
        res = CollisionUtils.handlePhysics(entity, Vec.ZERO, res);
        // Appelle une méthode
        assertEqualsPoint(new Pos(5, 42, 5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckNoMoveLargeVelocityHit(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);

        // Affecte une valeur
        final int distance = 20;
        // Boucle : répète un bloc
        for (int x = 0; x < distance; ++x) instance.loadChunk(x, 0).join();

        // Appelle une méthode
        instance.setBlock(distance * 8, 43, 5, Block.STONE);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(5, 42, 5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, Vec.ZERO);
        // Appelle une méthode
        entity.teleport(res.newPosition());
        // Appelle une méthode
        res = CollisionUtils.handlePhysics(entity, new Vec((distance - 1) * 16, 0, 0), res);
        // Appelle une méthode
        assertEqualsPoint(new Pos(distance * 8 - 0.3, 42, 5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckLargeVelocityHitNoMove(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);

        // Affecte une valeur
        final int distance = 20;
        // Boucle : répète un bloc
        for (int x = 0; x < distance; ++x) instance.loadChunk(x, 0).join();

        // Appelle une méthode
        instance.setBlock(distance * 8, 43, 5, Block.STONE);

        // Appelle une méthode
        entity.setInstance(instance, new Pos(5, 42, 5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec((distance - 1) * 16, 0, 0));
        // Appelle une méthode
        entity.teleport(res.newPosition());
        // Appelle une méthode
        res = CollisionUtils.handlePhysics(entity, Vec.ZERO, res);
        // Appelle une méthode
        assertEqualsPoint(new Pos(distance * 8 - 0.3, 42, 5), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckDoorSubBlockSouthRepeat(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        Block b = Block.ACACIA_TRAPDOOR.withProperties(Map.of("facing", "south", "open", "true"));

        // Appelle une méthode
        instance.setBlock(0, 42, 0, b);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0.5, 42.5, 0.5)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, -0.4));
        // Appelle une méthode
        entity.teleport(res.newPosition());
        // Appelle une méthode
        res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, -0.4), res);

        // Appelle une méthode
        assertEqualsPoint(new Pos(0.5, 42.5, 0.487), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckCollisionDownCache(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 43, 1, Block.STONE);

        // Boucle : répète un bloc
        for (int i = -2; i <= 2; ++i)
            // Boucle : répète un bloc
            for (int j = -2; j <= 2; ++j)
                // Appelle une méthode
                instance.loadChunk(i, j).join();

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, 10));
        // Appelle une méthode
        entity.teleport(res.newPosition());
        // Appelle une méthode
        res = CollisionUtils.handlePhysics(entity, new Vec(0, -10, 0), res);

        // Appelle une méthode
        assertEqualsPoint(new Pos(0, 40, 0.7), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckGravityCached(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 43, 1, Block.STONE);

        // Boucle : répète un bloc
        for (int i = -2; i <= 2; ++i)
            // Boucle : répète un bloc
            for (int j = -2; j <= 2; ++j)
                // Appelle une méthode
                instance.loadChunk(i, j).join();

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());

        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(entity, new Vec(0, 0, 10));
        // Appelle une méthode
        entity.teleport(res.newPosition());
        // Appelle une méthode
        res = CollisionUtils.handlePhysics(entity, new Vec(0, -10, 0), res);
        // Appelle une méthode
        entity.teleport(res.newPosition());

        // Instruction de code
        PhysicsResult lastPhysicsResult;

        // Boucle : répète un bloc
        for (int x = 0; x < 50; ++x) {
            // Affecte une valeur
            lastPhysicsResult = res;
            // Appelle une méthode
            res = CollisionUtils.handlePhysics(entity, new Vec(0, -1.7, 0), res);
            // Appelle une méthode
            entity.teleport(res.newPosition());

            // Embranchement : vérifie une condition
            if (x > 10) assertSame(lastPhysicsResult, res, "Physics result not cached");
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        assertEqualsPoint(new Pos(0, 40, 0.7), res.newPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityBlockPositionTestSlightlyAbove(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 42, 0, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 43.00001, 0));

        // Appelle une méthode
        var deltaPos = new Vec(0.0, -10, 0.0);
        // Appelle une méthode
        var physicsResult = CollisionUtils.handlePhysics(entity, deltaPos, null);

        // Appelle une méthode
        var newPos = physicsResult.newPosition();
        // Appelle une méthode
        assertEquals(43, newPos.blockY());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityBlockPositionTestFarAbove(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 42, 0, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 43.5, 0));

        // Appelle une méthode
        var deltaPos = new Vec(0.0, -10, 0.0);
        // Appelle une méthode
        var physicsResult = CollisionUtils.handlePhysics(entity, deltaPos, null);

        // Appelle une méthode
        var newPos = physicsResult.newPosition();
        // Appelle une méthode
        assertEquals(43, newPos.blockY());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCacheTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 42, 0, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 43.5, 0));

        // Appelle une méthode
        var deltaPos = new Vec(0.0, -10, 0.0);
        // Appelle une méthode
        var physicsResult = CollisionUtils.handlePhysics(entity, deltaPos, null);

        // Appelle une méthode
        var newPos = physicsResult.newPosition();
        // Appelle une méthode
        assertEquals(43, newPos.blockY());
        // Appelle une méthode
        assertEqualsPoint(new Vec(0, 0, 0), physicsResult.newVelocity());
        // Appelle une méthode
        assertEqualsPoint(deltaPos, physicsResult.originalDelta());

        // Create a new instance of the physics result to simulate gravity or we will never cache because velocity would be zero.
        // Appelle une méthode
        var velocityFixedResult = new PhysicsResult(physicsResult.newPosition(), physicsResult.newVelocity().add(deltaPos), physicsResult.isOnGround(), physicsResult.collisionX(), physicsResult.collisionY(), physicsResult.collisionZ(), physicsResult.originalDelta(), physicsResult.collisionPoints(), physicsResult.collisionShapes(), physicsResult.collisionShapePositions(), physicsResult.hasCollision(), physicsResult.res(), false);

        // Affecte une valeur
        var physicsResult2 = CollisionUtils.handlePhysics(instance, entity.getChunk(),
                // Instruction de code
                entity.getBoundingBox(),
                // Instruction de code
                physicsResult.newPosition(), deltaPos,
                // Instruction de code
                velocityFixedResult, false);

        // Appelle une méthode
        assertTrue(physicsResult2.cached());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}