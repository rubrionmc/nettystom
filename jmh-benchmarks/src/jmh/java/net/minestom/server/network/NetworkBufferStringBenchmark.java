// Package declaration for this file
package net.minestom.server.network;

// Import of a required class
import org.openjdk.jmh.annotations.*;
// Import of a required class
import org.openjdk.jmh.infra.Blackhole;

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
@State(Scope.Benchmark)
// Type declaration (class/interface/enum/record)
public class NetworkBufferStringBenchmark {

    // Code statement
    private NetworkBuffer buffer;

    // Annotation for the following element
    @Setup
    // Start of a method/block
    public void setup() {
        // Calls a method
        buffer = NetworkBuffer.resizableBuffer(8096);

        // Calls a method
        buffer.writeIndex(3);
        // Calls a method
        buffer.readIndex(3);

        // Calls a method
        buffer.write(NetworkBuffer.STRING, "hello i am bob, im quite a long string. It would be a shame to copy me twice");
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void read(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(buffer.read(NetworkBuffer.STRING));
        // Calls a method
        buffer.readIndex(3);
    // End of a block/expression
    }

    // Annotation for the following element
    @TearDown
    // Start of a method/block
    public void teardown(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(buffer);
    // End of a block/expression
    }
// End of a block/expression
}
