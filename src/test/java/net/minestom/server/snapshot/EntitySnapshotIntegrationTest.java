// Package declaration for this file
package net.minestom.server.snapshot;

// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNull;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EntitySnapshotIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void basic(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var ent = new Entity(EntityType.ZOMBIE);
        // Calls a method
        ent.setInstance(instance).join();
        // Calls a method
        var snapshot = ServerSnapshot.update();

        // Calls a method
        var inst = snapshot.instances().iterator().next();
        // Calls a method
        var entities = inst.entities();
        // Calls a method
        assertEquals(1, entities.size());

        // Calls a method
        var entity = entities.iterator().next();
        // Calls a method
        assertEquals(EntityType.ZOMBIE, entity.type());
        // Calls a method
        assertEquals(ent.getUuid(), entity.uuid());
        // Calls a method
        assertEquals(ent.getEntityId(), entity.id());
        // Calls a method
        assertEquals(ent.getPosition(), entity.position());
        // Calls a method
        assertEquals(ent.getVelocity(), entity.velocity());
        // Calls a method
        assertEquals(inst, entity.instance());
        // Calls a method
        assertEquals(inst.chunkAt(entity.position()), entity.chunk());
        // Calls a method
        assertEquals(ent.getViewers().size(), entity.viewers().size());
        // Calls a method
        assertEquals(ent.getPassengers().size(), entity.passengers().size());
        // Calls a method
        assertNull(ent.getVehicle());
        // Calls a method
        assertNull(entity.vehicle());
    // End of a block/expression
    }
// End of a block/expression
}
