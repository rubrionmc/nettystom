// Déclaration du paquet de ce fichier
package net.minestom.server.entity.ai;

// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityCreature;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.ai.target.ClosestEntityTarget;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNull;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class ClosestEntityTargetTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void validFindTarget(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Appelle une méthode
        var self = new EntityCreature(EntityType.ZOMBIE);
        // Appelle une méthode
        self.setInstance(instance, new Pos(0, 42, 0)).join();

        // Appelle une méthode
        var spider = new EntityCreature(EntityType.SPIDER);
        // Appelle une méthode
        spider.setInstance(instance, new Pos(-3, 42, -3)).join();

        // Appelle une méthode
        var secondSpider = new EntityCreature(EntityType.SPIDER);
        // Appelle une méthode
        secondSpider.setInstance(instance, new Pos(-4, 42, -4)).join();

        // Appelle une méthode
        var skeleton = new EntityCreature(EntityType.SKELETON);
        // Appelle une méthode
        skeleton.setInstance(instance, new Pos(5, 42, 5)).join();

        // Appelle une méthode
        var zombie = new EntityCreature(EntityType.ZOMBIE);
        // Appelle une méthode
        zombie.setInstance(instance, new Pos(10, 42, -10)).join();

        // Appelle une méthode
        assertEquals(5, instance.getEntities().size(), "Not all entities are in the instance");

        // Instruction de code
        assertNull(
                // Crée un nouvel objet
                new ClosestEntityTarget(self, 1, e -> true).findTarget(),
                // Instruction de code
                "Entity targets it self"
        // Fin d'un bloc/d'une expression
        );

        // Instruction de code
        assertEquals(spider,
                // Crée un nouvel objet
                new ClosestEntityTarget(self, 20, e -> e.getEntityType() == EntityType.SPIDER).findTarget(),
                // Instruction de code
                "The closest spider was not selected"
        // Fin d'un bloc/d'une expression
        );

        // Instruction de code
        assertNull(
                // Crée un nouvel objet
                new ClosestEntityTarget(self, 2, e -> e.getEntityType() == EntityType.SPIDER).findTarget(),
                // Instruction de code
                "Range distance is not being considered"
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        zombie.remove();

        // Instruction de code
        assertNull(
                // Crée un nouvel objet
                new ClosestEntityTarget(self, 20, e -> e.getEntityType() == EntityType.ZOMBIE).findTarget(),
                // Instruction de code
                "Removed entities are included in target selection"
        // Fin d'un bloc/d'une expression
        );

    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
