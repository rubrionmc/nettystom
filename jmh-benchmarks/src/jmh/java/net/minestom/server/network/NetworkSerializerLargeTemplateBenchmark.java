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
public class NetworkSerializerLargeTemplateBenchmark {

    // Type declaration (class/interface/enum/record)
    record Packet(boolean var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6, boolean var7, boolean var8, boolean var9, boolean var10, boolean var11, boolean var12, boolean var13, boolean var14, boolean var15, boolean var16, boolean var17, boolean var18, boolean var19, boolean var20) {
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
        serializer = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var1,
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var2,
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var3,
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var4,
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var5,
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var6,
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var7,
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var8,
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var9,
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var10,
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var11,
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var12,
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var13,
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var14,
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var15,
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var16,
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var17,
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var18,
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var19,
                // Code statement
                NetworkBuffer.BOOLEAN, Packet::var20,
                // Code statement
                Packet::new
        // End of a block/expression
        );
        // Calls a method
        packet = new Packet(true, false, true, true, false, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false);
        // Calls a method
        readBuffer = NetworkBuffer.staticBuffer(256);
        // Calls a method
        readBuffer.write(serializer, packet);
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
