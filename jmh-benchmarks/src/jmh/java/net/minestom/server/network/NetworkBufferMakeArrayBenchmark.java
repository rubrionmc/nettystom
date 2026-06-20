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
public class NetworkBufferMakeArrayBenchmark {

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void makeArray(Blackhole blackhole) {
        // Start of a method/block
        blackhole.consume(NetworkBuffer.makeArray(buffer -> {
            // Calls a method
            buffer.write(NetworkBuffer.LONG, 54L);
            // Calls a method
            buffer.write(NetworkBuffer.INT, 54);
            // Calls a method
            buffer.write(NetworkBuffer.SHORT, (short) 54);
            // Calls a method
            buffer.write(NetworkBuffer.BYTE, (byte) 54);
            // Calls a method
            buffer.write(NetworkBuffer.BOOLEAN, true);
            // Calls a method
            buffer.write(NetworkBuffer.FLOAT, 54.0f);
            // Calls a method
            buffer.write(NetworkBuffer.DOUBLE, 54.0);
            // Calls a method
            buffer.write(NetworkBuffer.VAR_INT, 54);
            // Calls a method
            buffer.write(NetworkBuffer.VAR_LONG, 54L);
            // Calls a method
            buffer.write(NetworkBuffer.STRING, "4");
        // Code statement
        }));
    // End of a block/expression
    }
// End of a block/expression
}
