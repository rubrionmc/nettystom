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
@Outcome(id = "40101", expect = ACCEPTABLE)
// Annotation for the following element
@State
// Type declaration (class/interface/enum/record)
public class AcquirableSyncTest {

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
    static final class Element implements Tickable, AcquirableSource<Element> {
        // Calls a method
        private final Acquirable<Element> acquirable = Acquirable.unassigned(this);
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

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Acquirable<? extends Element> acquirable() {
            // Returns a value to the caller
            return acquirable;
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

    // Start of a method/block
    private void loop() {
        // Loop: repeats a block
        for (int i = 0; i < 10_000; i++) {
            // Calls a method
            element.acquirable().sync(Element::compute);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Actor
    // Start of a method/block
    public void actor0() {
        // Loop: repeats a block
        for (int i = 0; i < 100; i++) {
            // Calls a method
            dispatcher.updateAndAwait(0);
            // Exception handling
            try {
                // Calls a method
                Thread.sleep(1);
            // Start of a method/block
            } catch (InterruptedException e) {
                // Throws an exception
                throw new RuntimeException(e);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Actor
    // Start of a method/block
    public void actor1() {
        // Assigns a value
        TickThread tickThread = new TickThread(1) {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void run() {
                // Calls a method
                loop();
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        tickThread.start();
        // Exception handling
        try {
            // Calls a method
            tickThread.join();
        // Start of a method/block
        } catch (InterruptedException e) {
            // Throws an exception
            throw new RuntimeException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Actor
    // Start of a method/block
    public void actor2() {
        // Assigns a value
        TickThread tickThread = new TickThread(2) {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void run() {
                // Calls a method
                loop();
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        tickThread.start();
        // Exception handling
        try {
            // Calls a method
            tickThread.join();
        // Start of a method/block
        } catch (InterruptedException e) {
            // Throws an exception
            throw new RuntimeException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Actor
    // Start of a method/block
    public void actor3() {
        // Assigns a value
        TickThread tickThread = new TickThread(3) {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void run() {
                // Calls a method
                loop();
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        tickThread.start();
        // Exception handling
        try {
            // Calls a method
            tickThread.join();
        // Start of a method/block
        } catch (InterruptedException e) {
            // Throws an exception
            throw new RuntimeException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Actor
    // Start of a method/block
    public void actor4() {
        // Calls a method
        Thread thread = new Thread(this::loop);
        // Calls a method
        thread.start();
        // Exception handling
        try {
            // Calls a method
            thread.join();
        // Start of a method/block
        } catch (InterruptedException e) {
            // Throws an exception
            throw new RuntimeException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Arbiter
    // Start of a method/block
    public void arbiter(L_Result r) {
        // Calls a method
        element.acquirable().sync(test -> r.r1 = test.value);
        // Calls a method
        dispatcher.shutdown();
    // End of a block/expression
    }
// End of a block/expression
}
