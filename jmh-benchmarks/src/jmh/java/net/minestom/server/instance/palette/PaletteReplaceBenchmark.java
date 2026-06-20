// Package declaration for this file
package net.minestom.server.instance.palette;

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
public class PaletteReplaceBenchmark {

    //@Param({"4", "16"})
    //public int dimension;

    // Code statement
    private Palette palette;

    // Annotation for the following element
    @Setup
    // Start of a method/block
    public void setup() {
        // FIXME: StackOverflowError
        // palette = Palette.newPalette(dimension, 15, 4, 1);
        // Calls a method
        palette = Palette.blocks();
        // Calls a method
        palette.setAll((x, y, z) -> x + y + z + 1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void replaceAll() {
        // Calls a method
        palette.replaceAll((x, y, z, value) -> value + 1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void replaceLoop() {
        // Calls a method
        final int dimension = palette.dimension();
        // Loop: repeats a block
        for (int x = 0; x < dimension; x++) {
            // Loop: repeats a block
            for (int y = 0; y < dimension; y++) {
                // Loop: repeats a block
                for (int z = 0; z < dimension; z++) {
                    // Calls a method
                    palette.replace(x, y, z, value -> value + 1);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
