// Package declaration for this file
package net.minestom.server.thread;

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
import java.util.concurrent.CountDownLatch;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertFalse;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class AcquirableEntityIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void instanceSet(Env env) throws InterruptedException {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var zombie = new Entity(EntityType.ZOMBIE);
        // Calls a method
        CountDownLatch latch = new CountDownLatch(1);
        // Start of a method/block
        Thread.startVirtualThread(() -> {
            // Calls a method
            assertFalse(zombie.acquirable().isOwned());
            // Calls a method
            assertFalse(zombie.acquirable().isLocal());
            //assertThrows(AcquirableOwnershipException.class, () -> zombie.setInstance(instance, new Pos(1, 41, 1)).join());
            // Calls a method
            latch.countDown();
        // End of a block/expression
        });
        // Calls a method
        latch.await();
    // End of a block/expression
    }
// End of a block/expression
}
