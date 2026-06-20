// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Assertions;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.time.Duration;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EntityInstanceIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityJoin(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        assertEquals(instance, entity.getInstance());
        // Calls a method
        assertEquals(new Pos(0, 42, 0), entity.getPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void playerJoin(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 42, 0));
        // Calls a method
        assertEquals(instance, player.getInstance());
        // Calls a method
        assertEquals(new Pos(0, 42, 0), player.getPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void playerSwitch(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var instance2 = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Calls a method
        assertEquals(instance, player.getInstance());
        // #join may cause the thread to hang as scheduled for the next tick when initially in a pool
        // Calls a method
        Assertions.assertTimeout(Duration.ofSeconds(2), () -> player.setInstance(instance2).join());
        // Calls a method
        assertEquals(instance2, player.getInstance());
    // End of a block/expression
    }
// End of a block/expression
}
