// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.event.entity.EntityFireExtinguishEvent;
// Import of a required class
import net.minestom.server.event.entity.EntitySetFireEvent;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EntityFireTest
// Start of a block
{
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void duration(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.loadChunk(0, 0).join();

        // Assigns a value
        final int fireTicks = 10;
        // Calls a method
        LivingEntity entity = new LivingEntity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Vec(0, 0, 0));

        // Calls a method
        entity.setFireTicks(fireTicks);
        // Calls a method
        assertTrue(entity.getEntityMeta().isOnFire());

        // Loop: repeats a block
        for (int i = 0; i < fireTicks; i++) {
            // Calls a method
            assertTrue(entity.getEntityMeta().isOnFire());
            // Calls a method
            assertEquals(fireTicks - i, entity.getFireTicks());
            // Calls a method
            entity.tick(0);
        // End of a block/expression
        }

        // Calls a method
        assertFalse(entity.getEntityMeta().isOnFire());
        // Calls a method
        assertEquals(0, entity.getFireTicks());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void nonNegativeFireDuration(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.loadChunk(0, 0).join();

        // Calls a method
        LivingEntity entity = new LivingEntity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Vec(0, 0, 0));

        // Natural fire decay
        // Calls a method
        entity.setFireTicks(5);
        // Loop: repeats a block
        for (int i = 0; i < 20; i++) {
            // Calls a method
            assertTrue(entity.getFireTicks() >= 0);
        // End of a block/expression
        }

        // Explicit negative
        // Calls a method
        entity.setFireTicks(-1);
        // Calls a method
        assertEquals(0, entity.getFireTicks());

        // Explicit negative in event
        // Calls a method
        env.listen(EntitySetFireEvent.class).followup(e -> e.setFireTicks(-1));

        // Calls a method
        entity.setFireTicks(1);
        // Calls a method
        assertEquals(0, entity.getFireTicks());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void setFireMetadata(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.loadChunk(0, 0).join();

        // Calls a method
        LivingEntity entity = new LivingEntity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Vec(0, 0, 0));

        // Do not extinguish an entity when they're set on fire explicitly
        // Calls a method
        entity.getEntityMeta().setOnFire(true);
        // Loop: repeats a block
        for (int i = 0; i < 40; i++) {
            // Calls a method
            entity.tick(0);
            // Calls a method
            assertTrue(entity.getEntityMeta().isOnFire());
        // End of a block/expression
        }

        // Unless setFireTicks has been called to activate the internal remainingFireTicks timer
        // Calls a method
        entity.setFireTicks(1);
        // Calls a method
        entity.tick(0);
        // Calls a method
        assertFalse(entity.isOnFire());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void extinguishEvent(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.loadChunk(0, 0).join();

        // Calls a method
        LivingEntity entity = new LivingEntity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Vec(0, 0, 0));

        // Calls a method
        AtomicInteger callCount = new AtomicInteger();
        // Start of a method/block
        env.listen(EntityFireExtinguishEvent.class).followup(e -> {
            // Calls a method
            callCount.getAndIncrement();
            // Branch: checks a condition
            if (callCount.get() == 2) assertTrue(e.isNatural());
            // Alternative branch of the condition
            else assertFalse(e.isNatural());
        // End of a block/expression
        });

        // Don't call when the entity is already on fire
        // Calls a method
        entity.setFireTicks(0);
        // Calls a method
        assertEquals(0, callCount.get());

        // Call now, the entity is set on fire
        // Calls a method
        entity.setFireTicks(1);
        // Calls a method
        entity.setFireTicks(-1);
        // Calls a method
        assertEquals(1, callCount.get());

        // Call naturally
        // Calls a method
        entity.setFireTicks(3);
        // Loop: repeats a block
        for (int i = 0; i < 3; i++) {
            // Calls a method
            entity.tick(0);
        // End of a block/expression
        }
        // Calls a method
        assertEquals(2, callCount.get());

        // Don't call if cancelled EntitySetFireEvent
        // Calls a method
        env.listen(EntitySetFireEvent.class).followup(e -> e.setCancelled(true));
        // Calls a method
        entity.setFireTicks(5);
        // Calls a method
        assertEquals(2, callCount.get());
    // End of a block/expression
    }
// End of a block/expression
}
