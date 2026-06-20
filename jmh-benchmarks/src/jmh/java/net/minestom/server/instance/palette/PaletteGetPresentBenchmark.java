// Déclaration du paquet de ce fichier
package net.minestom.server.instance.palette;

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
@BenchmarkMode(Mode.AverageTime)
// Annotation pour l'élément suivant
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Annotation pour l'élément suivant
@State(Scope.Benchmark)
// Déclaration de type (classe/interface/enum/record)
public class PaletteGetPresentBenchmark {

    // Annotation pour l'élément suivant
    @Param({"0", "0.25", "0.5", "0.75", "1"})
    // Instruction de code
    public double fullness;

    // Instruction de code
    private Palette palette;

    // Annotation pour l'élément suivant
    @Setup
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Appelle une méthode
        palette = Palette.blocks();
        // Appelle une méthode
        var random = new Random(18932365);
        // Appelle une méthode
        final int dimension = palette.dimension();
        // Boucle : répète un bloc
        for (int y = 0; y < dimension; y++)
            // Boucle : répète un bloc
            for (int z = 0; z < dimension; z++)
                // Boucle : répète un bloc
                for (int x = 0; x < dimension; x++)
                    // Embranchement : vérifie une condition
                    if (random.nextDouble() < fullness)
                        // Appelle une méthode
                        palette.set(x, y, z, random.nextInt(1, 16));
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

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readAllPresent(Blackhole blackHole) {
        // Appelle une méthode
        palette.getAllPresent((x, y, z, value) -> blackHole.consume(value));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readAllPresentAlt(Blackhole blackHole) {
        // Début d'une méthode/d'un bloc
        palette.getAll((x, y, z, value) -> {
            // Embranchement : vérifie une condition
            if (value != 0) {
                // Appelle une méthode
                blackHole.consume(value);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
