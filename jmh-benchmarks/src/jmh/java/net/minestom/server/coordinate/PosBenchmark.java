// Package declaration for this file
package net.minestom.server.coordinate;

// Import of a required class
import org.openjdk.jmh.annotations.*;

// Import of a required class
import java.util.Random;
// Import of a required class
import java.util.concurrent.TimeUnit;

// Annotation for the following element
@BenchmarkMode(Mode.AverageTime)
// Annotation for the following element
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Annotation for the following element
@State(Scope.Thread)
// Annotation for the following element
@Warmup(iterations = 5, time = 1)
// Annotation for the following element
@Measurement(iterations = 10, time = 1)
// Annotation for the following element
@Fork(3)
// Type declaration (class/interface/enum/record)
public class PosBenchmark {

    // Annotation for the following element
    @Param({"true", "false"})
    // Code statement
    boolean inside;

    // Annotation for the following element
    @Param({"1024", "4096"})
    // Code statement
    int sampleSize;

    // Code statement
    private int sampleBound;

    // Code statement
    private float[] randomPitches;
    // Code statement
    private float[] randomYaws;
    // Assigns a value
    private int pitchIndex = 0;
    // Assigns a value
    private int yawIndex = 0;

    // Annotation for the following element
    @Setup
    // Start of a method/block
    public void setup() {
        // Assigns a value
        final int sampleSize = this.sampleSize;
        // Assigns a value
        sampleBound = sampleSize - 1;
        // Assigns a value
        randomPitches = new float[sampleSize];
        // Assigns a value
        randomYaws = new float[sampleSize];

        // Calls a method
        Random r = new Random(67);
        // Loop: repeats a block
        for (int i = 0; i < randomPitches.length; i++) {
            // Calls a method
            randomPitches[i] = inside ? r.nextFloat(-90f, 90.0f) : r.nextFloat(-1000.0f, 1000.0f);
        // End of a block/expression
        }
        // Loop: repeats a block
        for (int i = 0; i < randomYaws.length; i++) {
            // Calls a method
            randomYaws[i] = inside ? r.nextFloat(-179.99f, 180.0f) : r.nextFloat(-1000.0f, 1000.0f);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public float fixYaw() {
        // Assigns a value
        float yaw = randomYaws[pitchIndex++ & sampleBound];
        // Returns a value to the caller
        return Pos.fixYaw(yaw);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public float fixPitch() {
        // Assigns a value
        float pitch = randomPitches[yawIndex++ & sampleBound];
        // Returns a value to the caller
        return Pos.fixPitch(pitch);
    // End of a block/expression
    }
// End of a block/expression
}