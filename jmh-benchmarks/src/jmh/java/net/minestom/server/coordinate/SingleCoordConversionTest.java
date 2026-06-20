// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;
// Import d'une classe nécessaire
import org.openjdk.jmh.infra.Blackhole;

// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;

// Annotation pour l'élément suivant
@BenchmarkMode(Mode.AverageTime)
// Annotation pour l'élément suivant
@State(Scope.Thread)
// Annotation pour l'élément suivant
@Threads(2)
// Annotation pour l'élément suivant
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Annotation pour l'élément suivant
@Fork(2)
// Annotation pour l'élément suivant
@Warmup(time = 2, iterations = 10)
// Annotation pour l'élément suivant
@Measurement(time = 6, iterations = 100)
// Déclaration de type (classe/interface/enum/record)
public class SingleCoordConversionTest {
    // Affecte une valeur
    private static final int CHUNK_X = 0;
    // Affecte une valeur
    private static final int CHUNK_Y = 0;

    // Instruction de code
    private int zeroIndex;

    // Annotation pour l'élément suivant
    @Setup
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Appelle une méthode
        zeroIndex = CoordConversion.chunkBlockIndex(0, 0, 0);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void chunkBlockIndexGetGlobalSingle(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(CoordConversion.chunkBlockIndexGetGlobal(zeroIndex, CHUNK_X, CHUNK_Y));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void chunkBlockIndexSingle(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(CoordConversion.chunkBlockIndex(0, 0, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @TearDown
    // Début d'une méthode/d'un bloc
    public void tearDown(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(zeroIndex);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
