// Package declaration for this file
package net.minestom.server.event;

// Import of a required class
import org.openjdk.jmh.annotations.*;

// Import of a required class
import java.util.concurrent.TimeUnit;

// Annotation for the following element
@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation for the following element
@Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation for the following element
@Fork(3)
// Annotation for the following element
@BenchmarkMode(Mode.AverageTime)
// Annotation for the following element
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Annotation for the following element
@State(Scope.Benchmark)
// Type declaration (class/interface/enum/record)
public class MultiNodeBenchmark {

    // Annotation for the following element
    @Param({"0", "1", "3", "10"})
    // Code statement
    public int children;

    // Code statement
    private EventNode<Event> node;

    // Type declaration (class/interface/enum/record)
    record TestEvent() implements Event {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record TestEvent2() implements Event {
    // End of a block/expression
    }

    // Annotation for the following element
    @Setup
    // Start of a method/block
    public void setup() {
        // Calls a method
        node = EventNode.all("node");
        // Loop: repeats a block
        for (int i = 0; i < children; i++) {
            // Calls a method
            var child = EventNode.all("child-" + i);
            // Start of a method/block
            child.addListener(TestEvent.class, e -> {
                // Empty
            // End of a block/expression
            });

            // Calls a method
            node.addChild(child);

            // Real-world code are very unlikely to use entirely empty nodes.
            // This ensures that the handle map is properly lazily initialized to prevent fast exits.
            // Start of a method/block
            child.addListener(TestEvent2.class, e -> {
                // Empty
            // Calls a method
            }).call(new TestEvent2());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void call() {
        // Calls a method
        node.call(new TestEvent());
    // End of a block/expression
    }
// End of a block/expression
}
