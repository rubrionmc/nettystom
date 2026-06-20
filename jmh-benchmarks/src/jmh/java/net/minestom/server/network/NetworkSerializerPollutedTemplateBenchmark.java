// Package declaration for this file
package net.minestom.server.network;

// Import of a required class
import org.openjdk.jmh.annotations.*;
// Import of a required class
import org.openjdk.jmh.infra.Blackhole;

// Import of a required class
import java.util.concurrent.TimeUnit;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

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
public class NetworkSerializerPollutedTemplateBenchmark {
    // Code statement
    private NetworkBuffer.Type<Packet> packetSerializer;
    // Code statement
    private Packet packet;
    // Code statement
    private NetworkBuffer readBuffer;
    // Code statement
    private NetworkBuffer writeBuffer;
    // Code statement
    private Polluter<?>[] polluters;

    // Start of a method/block
    private static <T> Polluter<T> polluter(NetworkBuffer.Type<T> type, T value) {
        // Returns a value to the caller
        return new Polluter<>(type, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Setup(Level.Trial)
    // Start of a method/block
    public void setupTrial() {
        // Assigns a value
        polluters = new Polluter[]{
                // Code statement
                polluter(NetworkBufferTemplate.template(BOOLEAN, BooleanPacket::value, BooleanPacket::new), new BooleanPacket(true)),
                // Code statement
                polluter(NetworkBufferTemplate.template(BYTE, BytePacket::value, BytePacket::new), new BytePacket((byte) 1)),
                // Code statement
                polluter(NetworkBufferTemplate.template(SHORT, ShortPacket::value, ShortPacket::new), new ShortPacket((short) 2)),
                // Code statement
                polluter(NetworkBufferTemplate.template(INT, IntPacket::value, IntPacket::new), new IntPacket(3)),
                // Code statement
                polluter(NetworkBufferTemplate.template(FLOAT, FloatPacket::value, FloatPacket::new), new FloatPacket(4.0f)),
                // Code statement
                polluter(NetworkBufferTemplate.template(DOUBLE, DoublePacket::value, DoublePacket::new), new DoublePacket(5.0d)),
                // Code statement
                polluter(NetworkBufferTemplate.template(STRING, StringPacket::value, StringPacket::new), new StringPacket("polluted")),
                // Code statement
                polluter(NetworkBufferTemplate.template(VAR_INT, VarIntPacket::value, VarIntPacket::new), new VarIntPacket(6)),
                // Code statement
                polluter(NetworkBufferTemplate.template(VAR_LONG, VarLongPacket::value, VarLongPacket::new), new VarLongPacket(7L)),
        // End of a block/expression
        };
        // Calls a method
        packetSerializer = NetworkBufferTemplate.template(NetworkBuffer.LONG, Packet::id, Packet::new);
    // End of a block/expression
    }

    // Annotation for the following element
    @Setup(Level.Iteration)
    // Start of a method/block
    public void setupIteration() {
        // Calls a method
        packet = new Packet(12451235L);
        // Calls a method
        readBuffer = NetworkBuffer.staticBuffer(256);
        // Calls a method
        readBuffer.write(packetSerializer, packet);
        // Calls a method
        writeBuffer = NetworkBuffer.staticBuffer(256);

        // Loop: repeats a block
        for (int i = 0; i < 20_000; i++) {
            // Assigns a value
            Polluter<?> polluter = polluters[i % polluters.length];
            // Calls a method
            polluter.write();
            // Calls a method
            polluter.read();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void writePacket(Blackhole blackhole) {
        // Assigns a value
        var writeBuffer = this.writeBuffer;
        // Calls a method
        writeBuffer.writeIndex(0);
        // Calls a method
        packetSerializer.write(writeBuffer, packet);
        // Calls a method
        blackhole.consume(writeBuffer);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readPacket(Blackhole blackhole) {
        // Assigns a value
        var readBuffer = this.readBuffer;
        // Calls a method
        readBuffer.readIndex(0);
        // Calls a method
        blackhole.consume(packetSerializer.read(readBuffer));
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
        // Calls a method
        blackhole.consume(polluters);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Packet(long id) {
    // End of a block/expression
    }

    // Start of a method/block
    private static final class Polluter<T> {
        // Code statement
        private final NetworkBuffer.Type<T> type;
        // Code statement
        private final T value;
        // Code statement
        private final NetworkBuffer buffer;

        // Start of a method/block
        private Polluter(NetworkBuffer.Type<T> type, T value) {
            // Access to the current/parent object
            this.type = type;
            // Access to the current/parent object
            this.value = value;
            // Access to the current/parent object
            this.buffer = NetworkBuffer.staticBuffer(256);
            // Access to the current/parent object
            super();
            // Calls a method
            write();
        // End of a block/expression
        }

        // Start of a method/block
        private void write() {
            // Calls a method
            buffer.writeIndex(0);
            // Calls a method
            type.write(buffer, value);
        // End of a block/expression
        }

        // Start of a method/block
        private T read() {
            // Calls a method
            buffer.readIndex(0);
            // Returns a value to the caller
            return type.read(buffer);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private record BooleanPacket(boolean value) {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private record BytePacket(byte value) {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private record ShortPacket(short value) {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private record IntPacket(int value) {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private record FloatPacket(float value) {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private record DoublePacket(double value) {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private record StringPacket(String value) {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private record VarIntPacket(int value) {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private record VarLongPacket(long value) {
    // End of a block/expression
    }
// End of a block/expression
}
