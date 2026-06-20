// Package declaration for this file
package net.minestom.server.coordinate;

// Import of a required class
import net.minestom.server.instance.Chunk;
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
@Warmup(time = 2, iterations = 5)
// Annotation for the following element
@Measurement(time = 6, iterations = 8)
// Type declaration (class/interface/enum/record)
public class MultiCoordConversionTest {
    // Assigns a value
    private static final int CHUNK_X = 0;
    // Assigns a value
    private static final int CHUNK_Y = 0;

    // Annotation for the following element
    @Param({"0", "-16", "-64"})
    // Code statement
    public int yMin;
    // Annotation for the following element
    @Param({"16", "64", "320"})
    // Code statement
    public int yMaX;

    // Code statement
    private int[] blockIndexes;

    // Annotation for the following element
    @Setup
    // Start of a method/block
    public void setup() {
        // Calls a method
        blockIndexes = new int[Chunk.CHUNK_SIZE_Z * (Math.abs(yMin) + yMaX) * Chunk.CHUNK_SIZE_X];

        // Calls a method
        final int yMinAbs = Math.abs(yMin);
        // Loop: repeats a block
        for (int z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
            // Loop: repeats a block
            for (int y = yMin; y < yMaX; y++) {
                // Loop: repeats a block
                for (int x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
                    // Calls a method
                    blockIndexes[x + (y + yMinAbs) + z] = CoordConversion.chunkBlockIndex(x, y, z);
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
    public void chunkBlockIndexGetGlobalMulti(Blackhole blackhole) {
        // Loop: repeats a block
        for (final int index : blockIndexes) {
            // Calls a method
            blackhole.consume(CoordConversion.chunkBlockIndexGetGlobal(index, CHUNK_X, CHUNK_Y));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void chunkBlockIndexMulti(Blackhole blackhole) {
        // Loop: repeats a block
        for (int z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
            // Loop: repeats a block
            for (int y = yMin; y < yMaX; y++) {
                // Loop: repeats a block
                for (int x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
                    // Calls a method
                    blackhole.consume(CoordConversion.chunkBlockIndex(x, y, z));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @TearDown
    // Start of a method/block
    public void teardown(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(blockIndexes);
    // End of a block/expression
    }
// End of a block/expression
}
