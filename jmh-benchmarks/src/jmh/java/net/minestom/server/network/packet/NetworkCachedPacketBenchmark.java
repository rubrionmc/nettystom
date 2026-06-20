// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionState;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.CachedPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.KeepAlivePacket;
// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;
// Import d'une classe nécessaire
import org.openjdk.jmh.infra.Blackhole;

// Import d'une classe nécessaire
import java.util.Random;
// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;

// Annotation pour l'élément suivant
@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation pour l'élément suivant
@Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation pour l'élément suivant
@Fork(3)
// Annotation pour l'élément suivant
@BenchmarkMode(Mode.SampleTime)
// Annotation pour l'élément suivant
@State(Scope.Group)
// Annotation pour l'élément suivant
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Déclaration de type (classe/interface/enum/record)
public class NetworkCachedPacketBenchmark {
    // Début d'une méthode/d'un bloc
    static {
        // Appelle une méthode
        MinecraftServer.init();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Param({"1", "1000", "100000"})
    // Instruction de code
    private int packetTime;

    // Instruction de code
    private Random random;
    // Instruction de code
    private ServerPacket packet;
    // Instruction de code
    private CachedPacket cachedPacket;

    // Annotation pour l'élément suivant
    @Setup(Level.Iteration)
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Appelle une méthode
        random = new Random(151243);
        // Appelle une méthode
        packet = new KeepAlivePacket(0);
        // Affecte une valeur
        var packetTime = this.packetTime;
        // Affecte une valeur
        cachedPacket = new CachedPacket(() -> {
            // Appelle une méthode
            Blackhole.consumeCPU(packetTime);
            // Renvoie une valeur à l'appelant
            return packet;
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }
    // Annotation pour l'élément suivant
    @Benchmark
    // Annotation pour l'élément suivant
    @Group("shared")
    // Annotation pour l'élément suivant
    @GroupThreads(3)
    // Début d'une méthode/d'un bloc
    public void packet(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(cachedPacket.packet(ConnectionState.PLAY));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Annotation pour l'élément suivant
    @Group("shared")
    // Annotation pour l'élément suivant
    @GroupThreads
    // Début d'une méthode/d'un bloc
    public void invalidator() {
        // Embranchement : vérifie une condition
        if (random.nextInt(100) < 10) {
            // Appelle une méthode
            cachedPacket.invalidate();
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        Blackhole.consumeCPU(1500);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @TearDown
    // Début d'une méthode/d'un bloc
    public void teardown(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(random);
        // Appelle une méthode
        blackhole.consume(packet);
        // Appelle une méthode
        blackhole.consume(cachedPacket);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
