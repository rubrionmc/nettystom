// Package declaration for this file
package net.minestom.server.instance.palette;

// Import of a required class
import org.openjdk.jmh.annotations.*;
// Import of a required class
import org.openjdk.jmh.infra.Blackhole;

// Import of a required class
import java.util.concurrent.TimeUnit;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;

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
public class PaletteGetBenchmark {

    // Annotation for the following element
    @Param({"4", "16"})
    // Code statement
    public int dimension;

    // Code statement
    private Palette palette;

    // Annotation for the following element
    @Setup
    // Start of a method/block
    public void setup() {
        // Calls a method
        palette = Palette.sized(dimension, 4, 8, 15, 4);
        // Calls a method
        AtomicInteger value = new AtomicInteger();
        // Calls a method
        palette.setAll((x, y, z) -> value.getAndIncrement());
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void read(Blackhole blackHole) {
        // Calls a method
        final int dimension = palette.dimension();
        // Loop: repeats a block
        for (int x = 0; x < dimension; x++) {
            // Loop: repeats a block
            for (int y = 0; y < dimension; y++) {
                // Loop: repeats a block
                for (int z = 0; z < dimension; z++) {
                    // Calls a method
                    blackHole.consume(palette.get(x, y, z));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readAll(Blackhole blackHole) {
        // Calls a method
        palette.getAll((x, y, z, value) -> blackHole.consume(value));
    // End of a block/expression
    }
// End of a block/expression
}
