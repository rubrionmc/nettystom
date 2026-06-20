// Package declaration for this file
package net.minestom.server.thread;

// Import of a required class
import net.minestom.server.Tickable;
// Import of a required class
import org.openjdk.jcstress.annotations.*;
// Import of a required class
import org.openjdk.jcstress.infra.results.L_Result;

// Static import of a member
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;

// Annotation for the following element
@JCStressTest
// Annotation for the following element
@Outcome(id = "301", expect = ACCEPTABLE)
// Annotation for the following element
@State
// Type declaration (class/interface/enum/record)
public class ThreadDispatcherSyncTest {
    // Calls a method
    private final ThreadDispatcher<World, Element> dispatcher = ThreadDispatcher.singleThread();
    // Calls a method
    private final World world = new World();
    // Calls a method
    private final Element element = new Element();

    // Type declaration (class/interface/enum/record)
    record World() {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static final class Element implements Tickable {
        // Code statement
        int value;

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void tick(long time) {
            // Calls a method
            compute();
        // End of a block/expression
        }

        // Start of a method/block
        void compute() {
            // Code statement
            value++;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a block
    {
        // Calls a method
        dispatcher.createPartition(world);
        // Calls a method
        dispatcher.updateElement(element, world);
        // Calls a method
        dispatcher.start();
        // Calls a method
        dispatcher.refreshThreads();
        // Calls a method
        dispatcher.updateAndAwait(0);
    // End of a block/expression
    }

    // Annotation for the following element
    @Actor
    // Start of a method/block
    public void actor1() {
        // Loop: repeats a block
        for (int i = 0; i < 100; i++) dispatcher.updateAndAwait(0);
    // End of a block/expression
    }

    // Annotation for the following element
    @Actor
    // Start of a method/block
    public void actor2() {
        // Loop: repeats a block
        for (int i = 0; i < 100; i++) dispatcher.updateAndAwait(0);
    // End of a block/expression
    }

    // Annotation for the following element
    @Actor
    // Start of a method/block
    public void actor3() {
        // Loop: repeats a block
        for (int i = 0; i < 100; i++) dispatcher.updateAndAwait(0);
    // End of a block/expression
    }

    // Annotation for the following element
    @Arbiter
    // Start of a method/block
    public void arbiter(L_Result r) {
        // Assigns a value
        r.r1 = element.value;
        // Calls a method
        dispatcher.shutdown();
    // End of a block/expression
    }
// End of a block/expression
}
