// Package declaration for this file
package net.minestom.server.snapshot;

// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class InstanceSnapshotIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void basic(Env env) {
        // Calls a method
        env.createFlatInstance();
        // Calls a method
        var snapshot = ServerSnapshot.update();

        // Ensure that the collection is immutable
        // Start of a block
        {
            // Calls a method
            var instances = snapshot.instances();
            // Calls a method
            assertEquals(1, instances.size());

            // Calls a method
            env.createFlatInstance();
            // Calls a method
            instances = snapshot.instances();
            // Calls a method
            assertEquals(1, instances.size());
        // End of a block/expression
        }

        // Calls a method
        var inst = snapshot.instances().iterator().next();

        // Calls a method
        assertEquals(snapshot, inst.server(), "Instance must have access to the server snapshot");

        // Calls a method
        assertEquals(0, inst.time());
        // Calls a method
        assertEquals(0, inst.worldAge());

        // Calls a method
        assertEquals(0, inst.chunks().size());
        // Calls a method
        assertEquals(0, inst.entities().size());
    // End of a block/expression
    }
// End of a block/expression
}
