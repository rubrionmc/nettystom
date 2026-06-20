// Package declaration for this file
package net.minestom.server.thread;

// Import of a required class
import net.minestom.server.coordinate.Pos;
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

// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.stream.Collectors;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class AcquirableLocalsIntegrationTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void empty(Env env) {
        // Calls a method
        assertEquals(0, Acquirable.localEntities().count());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void localTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Assigns a value
        var zombie = new Entity(EntityType.ZOMBIE) {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void tick(long time) {
                // Access to the current/parent object
                super.tick(time);
                // Calls a method
                assertEquals(Set.of(this), Acquirable.localEntities().collect(Collectors.toUnmodifiableSet()));
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        zombie.setInstance(instance, new Pos(1, 41, 1)).join();
        // Calls a method
        env.tick();
    // End of a block/expression
    }
// End of a block/expression
}
