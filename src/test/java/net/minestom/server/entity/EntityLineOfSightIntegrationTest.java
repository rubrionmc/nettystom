// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityLineOfSightIntegrationTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckLineOfSight(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Appelle une méthode
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        entity.setView(-90, 0);

        // Appelle une méthode
        var entity2 = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity2.setInstance(instance, new Pos(10, 42, 0)).join();

        // Appelle une méthode
        assertEquals(entity2, entity.getLineOfSightEntity(20, (e) -> true));
        // Appelle une méthode
        assertTrue(entity.hasLineOfSight(entity2, true));

        // Boucle : répète un bloc
        for (int z = -1; z <= 1; ++z) {
            // Boucle : répète un bloc
            for (int y = 40; y <= 44; ++y) {
                // Appelle une méthode
                instance.setBlock(5, y, z, Block.STONE);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        assertNull(entity.getLineOfSightEntity(20, (e) -> true));
        // Appelle une méthode
        assertFalse(entity.hasLineOfSight(entity2, true));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckLineOfSightBehind(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Appelle une méthode
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        entity.setView(-90, 0);

        // Appelle une méthode
        var entity2 = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity2.setInstance(instance, new Pos(-10, 42, 0)).join();

        // Appelle une méthode
        assertNull(entity.getLineOfSightEntity(20, (e) -> true));
        // Appelle une méthode
        assertFalse(entity.hasLineOfSight(entity2, true));
        // Appelle une méthode
        assertTrue(entity.hasLineOfSight(entity2, false));

        // Boucle : répète un bloc
        for (int z = -1; z <= 1; ++z) {
            // Boucle : répète un bloc
            for (int y = 40; y <= 44; ++y) {
                // Appelle une méthode
                instance.setBlock(-5, y, z, Block.STONE);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        assertFalse(entity.hasLineOfSight(entity2, false));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckLineOfSightNearMiss(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Appelle une méthode
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        entity.setView(-90, 0);

        // Appelle une méthode
        var entity2 = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity2.setInstance(instance, new Pos(10, 42, 0.31)).join();

        // Appelle une méthode
        assertNull(entity.getLineOfSightEntity(20, (e) -> true));
        // Appelle une méthode
        assertFalse(entity.hasLineOfSight(entity2, true));
        // Appelle une méthode
        assertTrue(entity.hasLineOfSight(entity2, false));

        // Boucle : répète un bloc
        for (int z = -1; z <= 1; ++z) {
            // Boucle : répète un bloc
            for (int y = 40; y <= 44; ++y) {
                // Appelle une méthode
                instance.setBlock(5, y, z, Block.STONE);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        assertFalse(entity.hasLineOfSight(entity2, false));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckLineOfSightNearHit(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Appelle une méthode
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        entity.setView(-90, 0);

        // Appelle une méthode
        var entity2 = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity2.setInstance(instance, new Pos(10, 42, 0.3)).join();

        // Appelle une méthode
        assertEquals(entity2, entity.getLineOfSightEntity(20, (e) -> true));
        // Appelle une méthode
        assertTrue(entity.hasLineOfSight(entity2, true));
        // Appelle une méthode
        assertTrue(entity.hasLineOfSight(entity2, false));

        // Boucle : répète un bloc
        for (int z = -1; z <= 1; ++z) {
            // Boucle : répète un bloc
            for (int y = 40; y <= 44; ++y) {
                // Appelle une méthode
                instance.setBlock(5, y, z, Block.STONE);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        assertNull(entity.getLineOfSightEntity(20, (e) -> true));
        // Appelle une méthode
        assertFalse(entity.hasLineOfSight(entity2, true));
        // Appelle une méthode
        assertFalse(entity.hasLineOfSight(entity2, false));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckLineOfSightCorrectOrder(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Appelle une méthode
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        entity.setView(-90, 0);

        // Appelle une méthode
        var entity2 = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity2.setInstance(instance, new Pos(10, 42, 0)).join();

        // Appelle une méthode
        var entity3 = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity3.setInstance(instance, new Pos(5, 42, 0)).join();

        // Appelle une méthode
        assertEquals(entity3, entity.getLineOfSightEntity(20, (e) -> true));
        // Appelle une méthode
        assertTrue(entity.hasLineOfSight(entity2, true));
        // Appelle une méthode
        assertTrue(entity.hasLineOfSight(entity2, false));
        // Appelle une méthode
        assertTrue(entity.hasLineOfSight(entity3, true));
        // Appelle une méthode
        assertTrue(entity.hasLineOfSight(entity3, false));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckLineOfSightBigMiss(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Appelle une méthode
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        entity.setView(-90, 0);

        // Appelle une méthode
        var entity2 = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity2.setInstance(instance, new Pos(10, 42, 10)).join();

        // Appelle une méthode
        assertNull(entity.getLineOfSightEntity(20, (e) -> true));
        // Appelle une méthode
        assertFalse(entity.hasLineOfSight(entity2, true));
        // Appelle une méthode
        assertTrue(entity.hasLineOfSight(entity2, false));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckLineOfSightLargeBoundingBox(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Appelle une méthode
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        entity.setView(-90, 0);

        // Appelle une méthode
        var entity2 = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity2.setInstance(instance, new Pos(6, 42, 0)).join();
        // Appelle une méthode
        entity2.setBoundingBox(4.0, 2.0, 4.0);

        // Boucle : répète un bloc
        for (int z = -1; z <= 1; ++z) {
            // Boucle : répète un bloc
            for (int y = 40; y <= 44; ++y) {
                // Appelle une méthode
                instance.setBlock(5, y, z, Block.STONE);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        assertEquals(entity2, entity.getLineOfSightEntity(20, (e) -> true));
        // Appelle une méthode
        assertTrue(entity.hasLineOfSight(entity2, true));
        // Appelle une méthode
        assertTrue(entity.hasLineOfSight(entity2, false));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckLineOfSightDifferentTypes(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Appelle une méthode
        var entity = new Entity(EntityTypes.CHICKEN);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        entity.setView(-90, 0);

        // Appelle une méthode
        var entity2 = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity2.setInstance(instance, new Pos(10, 42, 0)).join();

        // Appelle une méthode
        assertEquals(entity2, entity.getLineOfSightEntity(20, (e) -> true));
        // Appelle une méthode
        assertTrue(entity.hasLineOfSight(entity2, true));

        // Appelle une méthode
        entity.teleport(new Pos(10, 42, 0)).join();
        // Appelle une méthode
        entity2.teleport(new Pos(0, 42, 0)).join();
        // Appelle une méthode
        entity2.setView(-90, 0);

        // Appelle une méthode
        assertNull(entity2.getLineOfSightEntity(20, (e) -> true));
        // Appelle une méthode
        assertFalse(entity2.hasLineOfSight(entity, true));
        // Appelle une méthode
        assertTrue(entity2.hasLineOfSight(entity, false));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
