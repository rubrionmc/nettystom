// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityPhysicsIntegrationTest
// Début d'un bloc
{
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void onGround(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(1, 40, 1, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(1, 41, 1)).join();
        // Appelle une méthode
        env.tick();

        // Entity shouldn't be on ground because it intitially spawns in with onGround = false
        // and a velocity of 0, it'll take 1 entity tick for gravity to be applied to their velocity
        // and a downward block collision to occur
        // Appelle une méthode
        assertFalse(entity.onGround);
        // Boucle : répète un bloc
        for (int i = 0; i < 10; i++) {
            // Appelle une méthode
            env.tick();
            // Appelle une méthode
            assertTrue(entity.onGround, "entity needs to be grounded on tick: " + entity.getAliveTicks());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void onGroundWithoutPhysics(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(1, 40, 1, Block.STONE);

        // Appelle une méthode
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity.setHasPhysics(false);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(1, 41, 1)).join();

        // Boucle : répète un bloc
        for (int i = 0; i < 10; i++) {
            // Appelle une méthode
            env.tick();
            // Appelle une méthode
            assertFalse(entity.onGround, "entity shouldn't be grounded on tick: " + entity.getAliveTicks() + " due to lack of physics");
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        entity.setHasPhysics(true);
        // Boucle : répète un bloc
        for (int i = 0; i < 10; i++) {
            // Appelle une méthode
            env.tick();
            // Appelle une méthode
            assertTrue(entity.onGround, "entity should be grounded on tick: " + entity.getAliveTicks());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
