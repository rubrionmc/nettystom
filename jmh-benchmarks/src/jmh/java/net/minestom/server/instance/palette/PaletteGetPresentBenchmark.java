// Package declaration for this file
package net.minestom.server.instance.palette;

// Import of a required class
import org.openjdk.jmh.annotations.*;
// Import of a required class
import org.openjdk.jmh.infra.Blackhole;

// Import of a required class
import java.util.Random;
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
public class PaletteGetPresentBenchmark {

    // Annotation for the following element
    @Param({"0", "0.25", "0.5", "0.75", "1"})
    // Code statement
    public double fullness;

    // Code statement
    private Palette palette;

    // Annotation for the following element
    @Setup
    // Start of a method/block
    public void setup() {
        // Calls a method
        palette = Palette.blocks();
        // Calls a method
        var random = new Random(18932365);
        // Calls a method
        final int dimension = palette.dimension();
        // Loop: repeats a block
        for (int y = 0; y < dimension; y++)
            // Loop: repeats a block
            for (int z = 0; z < dimension; z++)
                // Loop: repeats a block
                for (int x = 0; x < dimension; x++)
                    // Branch: checks a condition
                    if (random.nextDouble() < fullness)
                        // Calls a method
                        palette.set(x, y, z, random.nextInt(1, 16));
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

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readAllPresent(Blackhole blackHole) {
        // Calls a method
        palette.getAllPresent((x, y, z, value) -> blackHole.consume(value));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readAllPresentAlt(Blackhole blackHole) {
        // Start of a method/block
        palette.getAll((x, y, z, value) -> {
            // Branch: checks a condition
            if (value != 0) {
                // Calls a method
                blackHole.consume(value);
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }
// End of a block/expression
}
