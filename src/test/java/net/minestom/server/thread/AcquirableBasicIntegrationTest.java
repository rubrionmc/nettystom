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
import java.util.concurrent.CountDownLatch;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class AcquirableBasicIntegrationTest {

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
                assertTrue(this.acquirable().isLocal());
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        zombie.setInstance(instance, new Pos(1, 41, 1)).join();
        // Calls a method
        var acquirable = zombie.acquirable();
        // Check local state before initialization
        // Calls a method
        assertTrue(acquirable.isOwned());
        // Calls a method
        acquirable.sync(entity -> assertTrue(acquirable.isLocal()));
        // Calls a method
        Thread.startVirtualThread(() -> assertFalse(acquirable.isLocal()));

        // Code statement
        env.tick(); // Ensure the entity can access itself

        // Check local state after initialization
        // Calls a method
        assertFalse(acquirable.isOwned());
        // Calls a method
        acquirable.sync(entity -> assertFalse(acquirable.isLocal()));
        // Calls a method
        Thread.startVirtualThread(() -> assertFalse(acquirable.isLocal()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void ownedTest(Env env) {
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
                assertTrue(this.acquirable().isOwned());
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        zombie.setInstance(instance, new Pos(1, 41, 1)).join();
        // Calls a method
        var acquirable = zombie.acquirable();
        // Check ownership before initialization
        // Calls a method
        assertTrue(acquirable.isOwned());
        // Calls a method
        acquirable.sync(entity -> assertTrue(acquirable.isOwned()));
        // Calls a method
        Thread.startVirtualThread(() -> assertFalse(acquirable.isOwned()));

        // Code statement
        env.tick(); // Ensure the entity can access itself

        // Check ownership after initialization
        // Calls a method
        assertFalse(acquirable.isOwned());
        // Calls a method
        acquirable.sync(entity -> assertTrue(acquirable.isOwned()));
        // Calls a method
        Thread.startVirtualThread(() -> assertFalse(acquirable.isOwned()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void acquireSingleThreadInit(Env env) {
        // Ensure that acquisition before and after initialization are properly handled
        // Calls a method
        var instance = env.createFlatInstance();

        // Calls a method
        var zombie = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var acquirable = zombie.acquirable();

        // Calls a method
        zombie.setInstance(instance, new Pos(1, 41, 1)).join();
        // Code statement
        env.tick(); // Init entity

        // Calls a method
        AtomicInteger counter = new AtomicInteger(0);

        // Calls a method
        acquirable.sync(entity -> counter.incrementAndGet());
        // Calls a method
        assertEquals(1, counter.get());

        // Calls a method
        acquirable.sync(entity -> counter.incrementAndGet());
        // Calls a method
        assertEquals(2, counter.get());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void acquireBeforeInit(Env env) throws InterruptedException {
        // Ensure that acquisition before initialization are properly handled
        // Calls a method
        var zombie = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var acquirable = zombie.acquirable();
        // Calls a method
        CountDownLatch latch = new CountDownLatch(1);
        // Start of a method/block
        Thread.startVirtualThread(() -> assertThrows(IllegalStateException.class, () -> {
            // Calls a method
            latch.countDown();
            // Start of a method/block
            acquirable.sync(entity -> {
            // End of a block/expression
            });
        // Code statement
        }));
        // Calls a method
        latch.await();
    // End of a block/expression
    }
// End of a block/expression
}
