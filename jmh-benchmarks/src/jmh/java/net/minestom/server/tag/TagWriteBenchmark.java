// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import org.openjdk.jmh.annotations.*;

// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;
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
public class TagWriteBenchmark {
    // Calls a method
    static final Tag<String> TAG = Tag.String("key");

    // Code statement
    TagHandler tagHandler;
    // Code statement
    Tag<String> secondTag;

    // Code statement
    Map<String, String> map;
    // Code statement
    Map<String, String> concurrentMap;

    // Annotation for the following element
    @Setup
    // Start of a method/block
    public void setup() {
        // Tag benchmark
        // Access to the current/parent object
        this.tagHandler = TagHandler.newHandler();
        // Calls a method
        tagHandler.setTag(TAG, "value");
        // Calls a method
        secondTag = Tag.String("key");
        // Concurrent map benchmark
        // Calls a method
        map = new HashMap<>();
        // Calls a method
        map.put("key", "value");
        // Hash map benchmark
        // Calls a method
        concurrentMap = new ConcurrentHashMap<>();
        // Calls a method
        concurrentMap.put("key", "value");
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void writeConstantTag() {
        // Calls a method
        tagHandler.setTag(TAG, "value");
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void writeDifferentTag() {
        // Calls a method
        tagHandler.setTag(secondTag, "value");
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void writeNewTag() {
        // Calls a method
        tagHandler.setTag(Tag.String("key"), "value");
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void writeConcurrentMap() {
        // Calls a method
        concurrentMap.put("key", "value");
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void writeMap() {
        // Calls a method
        map.put("key", "value");
    // End of a block/expression
    }
// End of a block/expression
}
