// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.event.entity.EntityTickEvent;
// Import of a required class
import net.minestom.server.network.packet.server.play.DestroyEntitiesPacket;
// Import of a required class
import net.minestom.server.utils.time.TimeUnit;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.lang.ref.WeakReference;
// Import of a required class
import java.time.temporal.TemporalUnit;
// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.testing.TestUtils.waitUntilCleared;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EntityRemovalIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void destructionPacket(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        connection.connect(instance, new Pos(0, 40, 0));

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 40, 0)).join();

        // Calls a method
        var tracker = connection.trackIncoming(DestroyEntitiesPacket.class);
        // Calls a method
        entity.remove();
        // Calls a method
        tracker.assertSingle(packet -> assertEquals(List.of(entity.getEntityId()), packet.entityIds()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void instanceRemoval(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 40, 0)).join();
        // Calls a method
        assertFalse(entity.isRemoved());

        // Calls a method
        entity.remove();
        // Calls a method
        assertTrue(entity.isRemoved());
        // Calls a method
        assertFalse(instance.getEntities().contains(entity), "Entity must not be in the instance anymore");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void tickTimedRemoval(Env env) throws InterruptedException {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var entity = new TestEntity(2, TimeUnit.SERVER_TICK);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 40, 0)).join();

        // Calls a method
        assertFalse(entity.isRemoved());
        // Calls a method
        assertEquals(0, entity.getAliveTicks());

        // Code statement
        Thread.sleep(150); // Ensure that time is not used for tick scheduling

        // Calls a method
        env.tick();
        // Calls a method
        assertFalse(entity.isRemoved());
        // Calls a method
        assertEquals(1, entity.getAliveTicks());

        // Calls a method
        env.tick();
        // Calls a method
        assertTrue(entity.isRemoved());
        // Calls a method
        assertEquals(1, entity.getAliveTicks());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityGC(Env env) {
        // Ensure that entities do not stay in memory after they are removed
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 40, 0)).join();
        // Calls a method
        entity.remove();

        // Calls a method
        var ref = new WeakReference<>(entity);
        //noinspection UnusedAssignment
        // Assigns a value
        entity = null;
        // Code statement
        env.tick(); // Required to remove the entity from the thread dispatcher
        // Calls a method
        waitUntilCleared(ref);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityNodeGC(Env env) {
        // Ensure that the entities GCed when a local listener is present
        // Calls a method
        var node = env.process().eventHandler();
        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Start of a method/block
        entity.eventNode().addListener(EntityTickEvent.class, event -> {
        // End of a block/expression
        });
        // Calls a method
        node.call(new EntityTickEvent(entity));

        // Calls a method
        var ref = new WeakReference<>(entity);
        // Calls a method
        entity.remove();
        //noinspection UnusedAssignment
        // Assigns a value
        entity = null;
        // Calls a method
        env.tick();
        // Calls a method
        waitUntilCleared(ref);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static final class TestEntity extends Entity {
        // Start of a method/block
        public TestEntity(long delay, TemporalUnit unit) {
            // Access to the current/parent object
            super(EntityType.ZOMBIE);
            // Calls a method
            scheduleRemove(delay, unit);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
