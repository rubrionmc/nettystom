// Déclaration du paquet de ce fichier
package net.minestom.server.instance.palette;

// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;
// Import d'une classe nécessaire
import org.openjdk.jmh.infra.Blackhole;

// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;

// Annotation pour l'élément suivant
@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
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
public class PaletteGetBenchmark {

    // Annotation pour l'élément suivant
    @Param({"4", "16"})
    // Instruction de code
    public int dimension;

    // Instruction de code
    private Palette palette;

    // Annotation pour l'élément suivant
    @Setup
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Appelle une méthode
        palette = Palette.sized(dimension, 4, 8, 15, 4);
        // Appelle une méthode
        AtomicInteger value = new AtomicInteger();
        // Appelle une méthode
        palette.setAll((x, y, z) -> value.getAndIncrement());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void read(Blackhole blackHole) {
        // Appelle une méthode
        final int dimension = palette.dimension();
        // Boucle : répète un bloc
        for (int x = 0; x < dimension; x++) {
            // Boucle : répète un bloc
            for (int y = 0; y < dimension; y++) {
                // Boucle : répète un bloc
                for (int z = 0; z < dimension; z++) {
                    // Appelle une méthode
                    blackHole.consume(palette.get(x, y, z));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readAll(Blackhole blackHole) {
        // Appelle une méthode
        palette.getAll((x, y, z, value) -> blackHole.consume(value));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
