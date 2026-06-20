// Package declaration for this file
package net.minestom.server.instance.palette;

// Import of a required class
import org.openjdk.jmh.annotations.*;

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
public class PaletteScanBenchmark {

    // Annotation for the following element
    @Param({"indirect", "direct"})
    // Code statement
    public String mode;

    // Code statement
    private Palette palette;
    // Code statement
    private int presentValue;
    // Code statement
    private int absentValue;

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
                    if (random.nextDouble() < 0.5)
                        // Calls a method
                        palette.set(x, y, z, random.nextInt(1, 16));
        // Branch: checks a condition
        if (mode.equals("direct")) palette.optimize(Palette.Optimization.SPEED);
        // Assigns a value
        presentValue = 7;
        // Assigns a value
        absentValue = 9999;
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public int count() {
        // Returns a value to the caller
        return palette.count(presentValue);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public boolean any() {
        // Returns a value to the caller
        return palette.any(presentValue);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public boolean anyAbsent() {
        // Returns a value to the caller
        return palette.any(absentValue);
    // End of a block/expression
    }
// End of a block/expression
}
