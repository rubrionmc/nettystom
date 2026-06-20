// Déclaration du paquet de ce fichier
package net.minestom.server.collision;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityEntityCollisionIntegrationTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entitySingleCollisionTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Boucle : répète un bloc
        for (int i = -2; i <= 2; ++i)
            // Boucle : répète un bloc
            for (int j = -2; j <= 2; ++j)
                // Appelle une méthode
                instance.loadChunk(i, j).join();

        // Appelle une méthode
        var movingEntity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var stillEntity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var doNotHitEntity = new Entity(EntityType.ZOMBIE);

        // Appelle une méthode
        movingEntity.setInstance(instance, new Vec(0, 42, 0)).join();
        // Appelle une méthode
        stillEntity.setInstance(instance, new Vec(0, 42, 1)).join();
        // Appelle une méthode
        doNotHitEntity.setInstance(instance, new Vec(0, 42, 2)).join();

        // Appelle une méthode
        var result = CollisionUtils.checkEntityCollisions(movingEntity, new Vec(0, 0, 1), 1.51, entity -> entity != movingEntity, null);

        // Appelle une méthode
        assertEquals(1, result.size());
        // Appelle une méthode
        assertEquals(stillEntity, result.iterator().next().entity());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityMultipleCollisionTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Boucle : répète un bloc
        for (int i = -2; i <= 2; ++i)
            // Boucle : répète un bloc
            for (int j = -2; j <= 2; ++j)
                // Appelle une méthode
                instance.loadChunk(i, j).join();

        // Appelle une méthode
        var movingEntity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var stillEntity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var stillEntity2 = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var doNotHitEntity = new Entity(EntityType.ZOMBIE);

        // Appelle une méthode
        movingEntity.setInstance(instance, new Vec(0, 42, 0)).join();
        // Appelle une méthode
        stillEntity.setInstance(instance, new Vec(0, 42, 1)).join();
        // Appelle une méthode
        stillEntity2.setInstance(instance, new Vec(0, 42, 2)).join();
        // Appelle une méthode
        doNotHitEntity.setInstance(instance, new Vec(0, 42, 3)).join();

        // Appelle une méthode
        var result = CollisionUtils.checkEntityCollisions(movingEntity, new Vec(0, 0, 2), 1.51, entity -> entity != movingEntity, null);

        // Appelle une méthode
        assertEquals(2, result.size());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
