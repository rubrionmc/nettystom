// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.KeepAlivePacket;
// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;
// Import d'une classe nécessaire
import org.openjdk.jmh.infra.Blackhole;

// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;

// Annotation pour l'élément suivant
@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation pour l'élément suivant
@Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation pour l'élément suivant
@Fork(3)
// Annotation pour l'élément suivant
@BenchmarkMode(Mode.AverageTime)
// Annotation pour l'élément suivant
@State(Scope.Thread)
// Annotation pour l'élément suivant
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Déclaration de type (classe/interface/enum/record)
public class NetworkSerializerPacketBenchmark {

    // Instruction de code
    private KeepAlivePacket packet;
    // Instruction de code
    private NetworkBuffer readBuffer;
    // Instruction de code
    private NetworkBuffer writeBuffer;

    // Annotation pour l'élément suivant
    @Setup(Level.Iteration)
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Appelle une méthode
        packet = new KeepAlivePacket(0);
        // Appelle une méthode
        readBuffer = NetworkBuffer.staticBuffer(256);
        // Appelle une méthode
        readBuffer.write(KeepAlivePacket.SERIALIZER, new KeepAlivePacket(12451235));
        // Appelle une méthode
        writeBuffer = NetworkBuffer.staticBuffer(256);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void writePacket(Blackhole blackhole) {
        // Appelle une méthode
        writeBuffer.writeAt(0, KeepAlivePacket.SERIALIZER, packet);
        // Appelle une méthode
        blackhole.consume(writeBuffer);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readPacket(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(readBuffer.readAt(0, KeepAlivePacket.SERIALIZER));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @TearDown
    // Début d'une méthode/d'un bloc
    public void teardown(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(packet);
        // Appelle une méthode
        blackhole.consume(readBuffer);
        // Appelle une méthode
        blackhole.consume(writeBuffer);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
