// Déclaration du paquet de ce fichier
package net.minestom.server.collision;

// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class PlacementCollisionIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void empty(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        assertNull(BlockCollision.canPlaceBlockAt(instance, new Vec(0, 40, 0), Block.STONE));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityBlock(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Crée un nouvel objet
        new Entity(EntityType.ZOMBIE).setInstance(instance, new Pos(0, 40, 0)).join();
        // Appelle une méthode
        assertNotNull(BlockCollision.canPlaceBlockAt(instance, new Vec(0, 40, 0), Block.STONE));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void slab(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Crée un nouvel objet
        new Entity(EntityType.ZOMBIE).setInstance(instance, new Pos(0, 40.75, 0)).join();
        // Appelle une méthode
        assertNull(BlockCollision.canPlaceBlockAt(instance, new Vec(0, 40, 0), Block.STONE_SLAB));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void belowPlayer(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        env.createPlayer(instance, new Pos(5.7, -8, 6.389));
        // Appelle une méthode
        assertNull(BlockCollision.canPlaceBlockAt(instance, new Vec(5, -9, 6), Block.STONE));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
