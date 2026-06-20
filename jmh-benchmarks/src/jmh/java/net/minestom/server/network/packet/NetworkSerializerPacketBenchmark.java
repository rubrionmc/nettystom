// Package declaration for this file
package net.minestom.server.network.packet;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.server.common.KeepAlivePacket;
// Import of a required class
import org.openjdk.jmh.annotations.*;
// Import of a required class
import org.openjdk.jmh.infra.Blackhole;

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
@State(Scope.Thread)
// Annotation for the following element
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Type declaration (class/interface/enum/record)
public class NetworkSerializerPacketBenchmark {

    // Code statement
    private KeepAlivePacket packet;
    // Code statement
    private NetworkBuffer readBuffer;
    // Code statement
    private NetworkBuffer writeBuffer;

    // Annotation for the following element
    @Setup(Level.Iteration)
    // Start of a method/block
    public void setup() {
        // Calls a method
        packet = new KeepAlivePacket(0);
        // Calls a method
        readBuffer = NetworkBuffer.staticBuffer(256);
        // Calls a method
        readBuffer.write(KeepAlivePacket.SERIALIZER, new KeepAlivePacket(12451235));
        // Calls a method
        writeBuffer = NetworkBuffer.staticBuffer(256);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void writePacket(Blackhole blackhole) {
        // Calls a method
        writeBuffer.writeAt(0, KeepAlivePacket.SERIALIZER, packet);
        // Calls a method
        blackhole.consume(writeBuffer);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readPacket(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(readBuffer.readAt(0, KeepAlivePacket.SERIALIZER));
    // End of a block/expression
    }

    // Annotation for the following element
    @TearDown
    // Start of a method/block
    public void teardown(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(packet);
        // Calls a method
        blackhole.consume(readBuffer);
        // Calls a method
        blackhole.consume(writeBuffer);
    // End of a block/expression
    }
// End of a block/expression
}
