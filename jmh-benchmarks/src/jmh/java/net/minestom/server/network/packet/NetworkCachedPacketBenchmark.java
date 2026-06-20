// Package declaration for this file
package net.minestom.server.network.packet;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.network.ConnectionState;
// Import of a required class
import net.minestom.server.network.packet.server.CachedPacket;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.packet.server.common.KeepAlivePacket;
// Import of a required class
import org.openjdk.jmh.annotations.*;
// Import of a required class
import org.openjdk.jmh.infra.Blackhole;

// Import of a required class
import java.util.Random;
// Import of a required class
import java.util.concurrent.TimeUnit;

// Annotation for the following element
@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation for the following element
@Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation for the following element
@Fork(3)
// Annotation for the following element
@BenchmarkMode(Mode.SampleTime)
// Annotation for the following element
@State(Scope.Group)
// Annotation for the following element
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Type declaration (class/interface/enum/record)
public class NetworkCachedPacketBenchmark {
    // Start of a method/block
    static {
        // Calls a method
        MinecraftServer.init();
    // End of a block/expression
    }

    // Annotation for the following element
    @Param({"1", "1000", "100000"})
    // Code statement
    private int packetTime;

    // Code statement
    private Random random;
    // Code statement
    private ServerPacket packet;
    // Code statement
    private CachedPacket cachedPacket;

    // Annotation for the following element
    @Setup(Level.Iteration)
    // Start of a method/block
    public void setup() {
        // Calls a method
        random = new Random(151243);
        // Calls a method
        packet = new KeepAlivePacket(0);
        // Assigns a value
        var packetTime = this.packetTime;
        // Assigns a value
        cachedPacket = new CachedPacket(() -> {
            // Calls a method
            Blackhole.consumeCPU(packetTime);
            // Returns a value to the caller
            return packet;
        // End of a block/expression
        });
    // End of a block/expression
    }
    // Annotation for the following element
    @Benchmark
    // Annotation for the following element
    @Group("shared")
    // Annotation for the following element
    @GroupThreads(3)
    // Start of a method/block
    public void packet(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(cachedPacket.packet(ConnectionState.PLAY));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Annotation for the following element
    @Group("shared")
    // Annotation for the following element
    @GroupThreads
    // Start of a method/block
    public void invalidator() {
        // Branch: checks a condition
        if (random.nextInt(100) < 10) {
            // Calls a method
            cachedPacket.invalidate();
        // End of a block/expression
        }
        // Calls a method
        Blackhole.consumeCPU(1500);
    // End of a block/expression
    }

    // Annotation for the following element
    @TearDown
    // Start of a method/block
    public void teardown(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(random);
        // Calls a method
        blackhole.consume(packet);
        // Calls a method
        blackhole.consume(cachedPacket);
    // End of a block/expression
    }
// End of a block/expression
}
