// Package declaration for this file
package net.minestom.server.entity.ai;

// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.EntityCreature;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.ai.target.ClosestEntityTarget;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNull;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class ClosestEntityTargetTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void validFindTarget(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Calls a method
        var self = new EntityCreature(EntityType.ZOMBIE);
        // Calls a method
        self.setInstance(instance, new Pos(0, 42, 0)).join();

        // Calls a method
        var spider = new EntityCreature(EntityType.SPIDER);
        // Calls a method
        spider.setInstance(instance, new Pos(-3, 42, -3)).join();

        // Calls a method
        var secondSpider = new EntityCreature(EntityType.SPIDER);
        // Calls a method
        secondSpider.setInstance(instance, new Pos(-4, 42, -4)).join();

        // Calls a method
        var skeleton = new EntityCreature(EntityType.SKELETON);
        // Calls a method
        skeleton.setInstance(instance, new Pos(5, 42, 5)).join();

        // Calls a method
        var zombie = new EntityCreature(EntityType.ZOMBIE);
        // Calls a method
        zombie.setInstance(instance, new Pos(10, 42, -10)).join();

        // Calls a method
        assertEquals(5, instance.getEntities().size(), "Not all entities are in the instance");

        // Code statement
        assertNull(
                // Creates a new object
                new ClosestEntityTarget(self, 1, e -> true).findTarget(),
                // Code statement
                "Entity targets it self"
        // End of a block/expression
        );

        // Code statement
        assertEquals(spider,
                // Creates a new object
                new ClosestEntityTarget(self, 20, e -> e.getEntityType() == EntityType.SPIDER).findTarget(),
                // Code statement
                "The closest spider was not selected"
        // End of a block/expression
        );

        // Code statement
        assertNull(
                // Creates a new object
                new ClosestEntityTarget(self, 2, e -> e.getEntityType() == EntityType.SPIDER).findTarget(),
                // Code statement
                "Range distance is not being considered"
        // End of a block/expression
        );

        // Calls a method
        zombie.remove();

        // Code statement
        assertNull(
                // Creates a new object
                new ClosestEntityTarget(self, 20, e -> e.getEntityType() == EntityType.ZOMBIE).findTarget(),
                // Code statement
                "Removed entities are included in target selection"
        // End of a block/expression
        );

    // End of a block/expression
    }

// End of a block/expression
}
