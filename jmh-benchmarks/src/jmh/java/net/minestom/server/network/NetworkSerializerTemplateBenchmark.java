// Package declaration for this file
package net.minestom.server.network;

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
public class NetworkSerializerTemplateBenchmark {

    // Type declaration (class/interface/enum/record)
    record Packet(long id) {
        // Code statement
        private static final NetworkBuffer.Type<Packet> SERIALIZER = 
                // Calls a method
                NetworkBufferTemplate.template(NetworkBuffer.LONG, Packet::id, Packet::new);
    // End of a block/expression
    }
    
    // Code statement
    private NetworkBuffer.Type<Packet> serializer;
    // Code statement
    private Packet packet;
    // Code statement
    private NetworkBuffer readBuffer;
    // Code statement
    private NetworkBuffer writeBuffer;

    // Annotation for the following element
    @Setup
    // Start of a method/block
    public void setup() {
        // Assigns a value
        serializer = Packet.SERIALIZER;
        // Calls a method
        packet = new Packet(0);
        // Calls a method
        readBuffer = NetworkBuffer.staticBuffer(256);
        // Calls a method
        readBuffer.write(serializer, new Packet(12451235));
        // Calls a method
        writeBuffer = NetworkBuffer.staticBuffer(256);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void writePacket(Blackhole blackhole) {
        // Assigns a value
        var writeBuffer = this.writeBuffer;
        // Calls a method
        writeBuffer.writeAt(0, serializer, packet);
        // Calls a method
        blackhole.consume(writeBuffer);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readPacket(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(readBuffer.readAt(0, serializer));
    // End of a block/expression
    }

    // Annotation for the following element
    @TearDown
    // Start of a method/block
    public void teardown(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(serializer);
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
