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
public class NetworkBufferMakeArrayBenchmark {

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void makeArray(Blackhole blackhole) {
        // Début d'une méthode/d'un bloc
        blackhole.consume(NetworkBuffer.makeArray(buffer -> {
            // Appelle une méthode
            buffer.write(NetworkBuffer.LONG, 54L);
            // Appelle une méthode
            buffer.write(NetworkBuffer.INT, 54);
            // Appelle une méthode
            buffer.write(NetworkBuffer.SHORT, (short) 54);
            // Appelle une méthode
            buffer.write(NetworkBuffer.BYTE, (byte) 54);
            // Appelle une méthode
            buffer.write(NetworkBuffer.BOOLEAN, true);
            // Appelle une méthode
            buffer.write(NetworkBuffer.FLOAT, 54.0f);
            // Appelle une méthode
            buffer.write(NetworkBuffer.DOUBLE, 54.0);
            // Appelle une méthode
            buffer.write(NetworkBuffer.VAR_INT, 54);
            // Appelle une méthode
            buffer.write(NetworkBuffer.VAR_LONG, 54L);
            // Appelle une méthode
            buffer.write(NetworkBuffer.STRING, "4");
        // Instruction de code
        }));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
