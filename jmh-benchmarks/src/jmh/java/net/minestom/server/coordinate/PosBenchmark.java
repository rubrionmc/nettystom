// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;

// Import d'une classe nécessaire
import java.util.Random;
// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;

// Annotation pour l'élément suivant
@BenchmarkMode(Mode.AverageTime)
// Annotation pour l'élément suivant
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Annotation pour l'élément suivant
@State(Scope.Thread)
// Annotation pour l'élément suivant
@Warmup(iterations = 5, time = 1)
// Annotation pour l'élément suivant
@Measurement(iterations = 10, time = 1)
// Annotation pour l'élément suivant
@Fork(3)
// Déclaration de type (classe/interface/enum/record)
public class PosBenchmark {

    // Annotation pour l'élément suivant
    @Param({"true", "false"})
    // Instruction de code
    boolean inside;

    // Annotation pour l'élément suivant
    @Param({"1024", "4096"})
    // Instruction de code
    int sampleSize;

    // Instruction de code
    private int sampleBound;

    // Instruction de code
    private float[] randomPitches;
    // Instruction de code
    private float[] randomYaws;
    // Affecte une valeur
    private int pitchIndex = 0;
    // Affecte une valeur
    private int yawIndex = 0;

    // Annotation pour l'élément suivant
    @Setup
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Affecte une valeur
        final int sampleSize = this.sampleSize;
        // Affecte une valeur
        sampleBound = sampleSize - 1;
        // Affecte une valeur
        randomPitches = new float[sampleSize];
        // Affecte une valeur
        randomYaws = new float[sampleSize];

        // Appelle une méthode
        Random r = new Random(67);
        // Boucle : répète un bloc
        for (int i = 0; i < randomPitches.length; i++) {
            // Appelle une méthode
            randomPitches[i] = inside ? r.nextFloat(-90f, 90.0f) : r.nextFloat(-1000.0f, 1000.0f);
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (int i = 0; i < randomYaws.length; i++) {
            // Appelle une méthode
            randomYaws[i] = inside ? r.nextFloat(-179.99f, 180.0f) : r.nextFloat(-1000.0f, 1000.0f);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public float fixYaw() {
        // Affecte une valeur
        float yaw = randomYaws[pitchIndex++ & sampleBound];
        // Renvoie une valeur à l'appelant
        return Pos.fixYaw(yaw);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public float fixPitch() {
        // Affecte une valeur
        float pitch = randomPitches[yawIndex++ & sampleBound];
        // Renvoie une valeur à l'appelant
        return Pos.fixPitch(pitch);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}