// Déclaration du paquet de ce fichier
package net.minestom.server.instance.palette;

// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;

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
public class PaletteReplaceBenchmark {

    //@Param({"4", "16"})
    //public int dimension;

    // Instruction de code
    private Palette palette;

    // Annotation pour l'élément suivant
    @Setup
    // Début d'une méthode/d'un bloc
    public void setup() {
        // FIXME: StackOverflowError
        // palette = Palette.newPalette(dimension, 15, 4, 1);
        // Appelle une méthode
        palette = Palette.blocks();
        // Appelle une méthode
        palette.setAll((x, y, z) -> x + y + z + 1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void replaceAll() {
        // Appelle une méthode
        palette.replaceAll((x, y, z, value) -> value + 1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void replaceLoop() {
        // Appelle une méthode
        final int dimension = palette.dimension();
        // Boucle : répète un bloc
        for (int x = 0; x < dimension; x++) {
            // Boucle : répète un bloc
            for (int y = 0; y < dimension; y++) {
                // Boucle : répète un bloc
                for (int z = 0; z < dimension; z++) {
                    // Appelle une méthode
                    palette.replace(x, y, z, value -> value + 1);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
