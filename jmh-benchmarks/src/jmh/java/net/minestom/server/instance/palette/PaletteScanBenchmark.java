// Déclaration du paquet de ce fichier
package net.minestom.server.instance.palette;

// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;

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
public class PaletteScanBenchmark {

    // Annotation pour l'élément suivant
    @Param({"indirect", "direct"})
    // Instruction de code
    public String mode;

    // Instruction de code
    private Palette palette;
    // Instruction de code
    private int presentValue;
    // Instruction de code
    private int absentValue;

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
                    if (random.nextDouble() < 0.5)
                        // Appelle une méthode
                        palette.set(x, y, z, random.nextInt(1, 16));
        // Embranchement : vérifie une condition
        if (mode.equals("direct")) palette.optimize(Palette.Optimization.SPEED);
        // Affecte une valeur
        presentValue = 7;
        // Affecte une valeur
        absentValue = 9999;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public int count() {
        // Renvoie une valeur à l'appelant
        return palette.count(presentValue);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public boolean any() {
        // Renvoie une valeur à l'appelant
        return palette.any(presentValue);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public boolean anyAbsent() {
        // Renvoie une valeur à l'appelant
        return palette.any(absentValue);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
