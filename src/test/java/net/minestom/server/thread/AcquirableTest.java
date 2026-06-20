// Package declaration for this file
package net.minestom.server.thread;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.concurrent.atomic.AtomicReference;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNotEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Type declaration (class/interface/enum/record)
public class AcquirableTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void assignation() {
        // Calls a method
        AtomicReference<TickThread> tickThread = new AtomicReference<>();
        // Assigns a value
        Entity entity = new Entity(EntityType.ZOMBIE) {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void tick(long time) {
                // Access to the current/parent object
                super.tick(time);
                // Calls a method
                tickThread.set(acquirable().assignedThread());
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        Object first = new Object();
        // Calls a method
        Object second = new Object();

        // Calls a method
        ThreadDispatcher<Object, Entity> dispatcher = ThreadDispatcher.dispatcher(ThreadProvider.counter(), 2);
        // Calls a method
        dispatcher.start();
        // Calls a method
        dispatcher.createPartition(first);
        // Calls a method
        dispatcher.createPartition(second);

        // Calls a method
        dispatcher.updateElement(entity, first);
        // Calls a method
        dispatcher.updateAndAwait(System.nanoTime());
        // Calls a method
        TickThread firstThread = tickThread.get();
        // Calls a method
        assertNotNull(firstThread);

        // Calls a method
        tickThread.set(null);
        // Calls a method
        dispatcher.updateElement(entity, second);
        // Calls a method
        dispatcher.updateAndAwait(System.nanoTime());
        // Calls a method
        TickThread secondThread = tickThread.get();
        // Calls a method
        assertNotNull(secondThread);

        // Calls a method
        assertNotEquals(firstThread, secondThread);
    // End of a block/expression
    }
// End of a block/expression
}
