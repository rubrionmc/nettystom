// Déclaration du paquet de ce fichier
package net.minestom.server.instance.palette;

// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;

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
public class PaletteSetBenchmark {

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
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void incrWrite() {
        // Affecte une valeur
        int value = 0;
        // Appelle une méthode
        final int dimension = palette.dimension();
        // Boucle : répète un bloc
        for (int x = 0; x < dimension; x++) {
            // Boucle : répète un bloc
            for (int y = 0; y < dimension; y++) {
                // Boucle : répète un bloc
                for (int z = 0; z < dimension; z++) {
                    // Appelle une méthode
                    palette.set(x, y, z, value++);
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
    public void incrWriteAll() {
        // Appelle une méthode
        AtomicInteger value = new AtomicInteger(0);
        // Début d'une méthode/d'un bloc
        palette.setAll((x, y, z) -> {
            // Appelle une méthode
            final int v = value.getPlain();
            // Appelle une méthode
            value.setPlain(v + 1);
            // Renvoie une valeur à l'appelant
            return v;
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void constantWrite() {
        // Appelle une méthode
        final int dimension = palette.dimension();
        // Boucle : répète un bloc
        for (int x = 0; x < dimension; x++) {
            // Boucle : répète un bloc
            for (int y = 0; y < dimension; y++) {
                // Boucle : répète un bloc
                for (int z = 0; z < dimension; z++) {
                    // Appelle une méthode
                    palette.set(x, y, z, 5);
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
    public void constantWriteAll() {
        // Appelle une méthode
        palette.setAll((x, y, z) -> 5);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void fill() {
        // Appelle une méthode
        palette.fill(5);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
