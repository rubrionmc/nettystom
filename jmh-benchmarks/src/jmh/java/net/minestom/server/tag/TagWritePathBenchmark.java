// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import org.openjdk.jmh.annotations.*;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
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
public class TagWritePathBenchmark {
    // Annotation for the following element
    @Param({"0", "1", "2", "3"})
    // Code statement
    public int scope;

    // Code statement
    TagHandler tagHandler;
    // Code statement
    Tag<String> tag;

    // Annotation for the following element
    @Setup
    // Start of a method/block
    public void setup() {
        // Access to the current/parent object
        this.tagHandler = TagHandler.newHandler();

        // Calls a method
        List<String> path = new ArrayList<>(scope);
        // Loop: repeats a block
        for (int i = 0; i < scope; i++) path.add("key" + i);
        // Access to the current/parent object
        this.tag = Tag.String("key").path(path.toArray(String[]::new));

        // Calls a method
        tagHandler.setTag(tag, "value");
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void write() {
        // Calls a method
        tagHandler.setTag(tag, "value");
    // End of a block/expression
    }
// End of a block/expression
}
