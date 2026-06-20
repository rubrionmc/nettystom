// Package declaration for this file
package net.minestom.server.network;

// Import of a required class
import org.openjdk.jmh.annotations.*;
// Import of a required class
import org.openjdk.jmh.infra.Blackhole;

// Import of a required class
import java.util.Random;
// Import of a required class
import java.util.concurrent.TimeUnit;

// Annotation for the following element
@Warmup(iterations = 8, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation for the following element
@Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation for the following element
@Fork(3)
// Annotation for the following element
@BenchmarkMode(Mode.AverageTime)
// Annotation for the following element
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Annotation for the following element
@State(Scope.Thread)
// Annotation for the following element
@Threads(4)
// Type declaration (class/interface/enum/record)
public class NetworkBufferVarIntBenchmark {

    // Code statement
    private NetworkBuffer writeBuffer;
    // Code statement
    private NetworkBuffer readBuffer;

    // Assigns a value
    private static final int DATA_SIZE = 4096;
    // Assigns a value
    private static final int MASK = DATA_SIZE - 1;

    // Code statement
    private int[] mixedData;
    // Code statement
    private int[] readPositions; // Offsets for reading different sized VarInts
    // Code statement
    private int index;

    // Annotation for the following element
    @Setup
    // Start of a method/block
    public void setup() {
        // Calls a method
        writeBuffer = NetworkBuffer.staticBuffer(256);
        // Calls a method
        readBuffer = NetworkBuffer.staticBuffer(DATA_SIZE * 5);

        // Calls a method
        Random random = new Random(67);
        // Assigns a value
        mixedData = new int[DATA_SIZE];
        // Assigns a value
        readPositions = new int[DATA_SIZE];

        // Loop: repeats a block
        for (int i = 0; i < DATA_SIZE; i++) {
            // Calls a method
            double r = random.nextDouble();
            // Code statement
            int val;
            // Branch: checks a condition
            if (r < 0.5) val = random.nextInt(0, 128);
            // Branch: checks a condition
            else if (r < 0.8) val = random.nextInt(128, 16384);
            // Alternative branch of the condition
            else val = random.nextInt();

            // Assigns a value
            mixedData[i] = val;

            // Calls a method
            readPositions[i] = (int) readBuffer.writeIndex();
            // Calls a method
            readBuffer.write(NetworkBuffer.VAR_INT, val);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void writeVarint() {
        // Assigns a value
        int val = mixedData[index++ & MASK];
        // Calls a method
        writeBuffer.writeAt(0, NetworkBuffer.VAR_INT, val);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readVarint(Blackhole bh) {
        // Assigns a value
        int pos = readPositions[index++ & MASK];
        // Calls a method
        bh.consume(readBuffer.readAt(pos, NetworkBuffer.VAR_INT));
    // End of a block/expression
    }

    // Annotation for the following element
    @TearDown
    // Start of a method/block
    public void teardown(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(writeBuffer);
        // Calls a method
        blackhole.consume(readBuffer);
        // Calls a method
        blackhole.consume(mixedData);
        // Calls a method
        blackhole.consume(readPositions);
        // Calls a method
        blackhole.consume(index);
    // End of a block/expression
    }
// End of a block/expression
}
