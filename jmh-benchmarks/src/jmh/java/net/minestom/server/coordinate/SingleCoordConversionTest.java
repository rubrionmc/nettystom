// Package declaration for this file
package net.minestom.server.coordinate;

// Import of a required class
import org.openjdk.jmh.annotations.*;
// Import of a required class
import org.openjdk.jmh.infra.Blackhole;

// Import of a required class
import java.util.concurrent.TimeUnit;

// Annotation for the following element
@BenchmarkMode(Mode.AverageTime)
// Annotation for the following element
@State(Scope.Thread)
// Annotation for the following element
@Threads(2)
// Annotation for the following element
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Annotation for the following element
@Fork(2)
// Annotation for the following element
@Warmup(time = 2, iterations = 10)
// Annotation for the following element
@Measurement(time = 6, iterations = 100)
// Type declaration (class/interface/enum/record)
public class SingleCoordConversionTest {
    // Assigns a value
    private static final int CHUNK_X = 0;
    // Assigns a value
    private static final int CHUNK_Y = 0;

    // Code statement
    private int zeroIndex;

    // Annotation for the following element
    @Setup
    // Start of a method/block
    public void setup() {
        // Calls a method
        zeroIndex = CoordConversion.chunkBlockIndex(0, 0, 0);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void chunkBlockIndexGetGlobalSingle(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(CoordConversion.chunkBlockIndexGetGlobal(zeroIndex, CHUNK_X, CHUNK_Y));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void chunkBlockIndexSingle(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(CoordConversion.chunkBlockIndex(0, 0, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @TearDown
    // Start of a method/block
    public void tearDown(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(zeroIndex);
    // End of a block/expression
    }
// End of a block/expression
}
