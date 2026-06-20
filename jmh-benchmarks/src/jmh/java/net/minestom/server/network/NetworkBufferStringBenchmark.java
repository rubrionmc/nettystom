// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;
// Import d'une classe nécessaire
import org.openjdk.jmh.infra.Blackhole;

// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;

// Annotation pour l'élément suivant
@Warmup(iterations = 8, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation pour l'élément suivant
@Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation pour l'élément suivant
@Fork(3)
// Annotation pour l'élément suivant
@BenchmarkMode(Mode.AverageTime)
// Annotation pour l'élément suivant
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Annotation pour l'élément suivant
@State(Scope.Benchmark)
// Déclaration de type (classe/interface/enum/record)
public class NetworkBufferStringBenchmark {

    // Instruction de code
    private NetworkBuffer buffer;

    // Annotation pour l'élément suivant
    @Setup
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Appelle une méthode
        buffer = NetworkBuffer.resizableBuffer(8096);

        // Appelle une méthode
        buffer.writeIndex(3);
        // Appelle une méthode
        buffer.readIndex(3);

        // Appelle une méthode
        buffer.write(NetworkBuffer.STRING, "hello i am bob, im quite a long string. It would be a shame to copy me twice");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void read(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(buffer.read(NetworkBuffer.STRING));
        // Appelle une méthode
        buffer.readIndex(3);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @TearDown
    // Début d'une méthode/d'un bloc
    public void teardown(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(buffer);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
