// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import org.openjdk.jmh.annotations.*;
// Import of a required class
import org.openjdk.jmh.infra.Blackhole;

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
public class TagReadBenchmark {
    // Calls a method
    static final Tag<String> TAG = Tag.String("key");

    // Annotation for the following element
    @Param({"false", "true"})
    // Code statement
    public boolean present;

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
        // Branch: checks a condition
        if (present) tagHandler.setTag(TAG, "value");
        // Calls a method
        secondTag = Tag.String("key");
        // Concurrent map benchmark
        // Calls a method
        map = new HashMap<>();
        // Branch: checks a condition
        if (present) map.put("key", "value");
        // Hash map benchmark
        // Calls a method
        concurrentMap = new ConcurrentHashMap<>();
        // Branch: checks a condition
        if (present) concurrentMap.put("key", "value");
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readConstantTag(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(tagHandler.getTag(TAG));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readDifferentTag(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(tagHandler.getTag(secondTag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readNewTag(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(tagHandler.getTag(Tag.String("key")));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readConcurrentMap(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(concurrentMap.get("key"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readMap(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(map.get("key"));
    // End of a block/expression
    }
// End of a block/expression
}
