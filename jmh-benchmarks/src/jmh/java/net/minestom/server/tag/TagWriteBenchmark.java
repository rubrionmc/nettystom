// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;

// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;
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
public class TagWriteBenchmark {
    // Appelle une méthode
    static final Tag<String> TAG = Tag.String("key");

    // Instruction de code
    TagHandler tagHandler;
    // Instruction de code
    Tag<String> secondTag;

    // Instruction de code
    Map<String, String> map;
    // Instruction de code
    Map<String, String> concurrentMap;

    // Annotation pour l'élément suivant
    @Setup
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Tag benchmark
        // Accès à l'objet courant/parent
        this.tagHandler = TagHandler.newHandler();
        // Appelle une méthode
        tagHandler.setTag(TAG, "value");
        // Appelle une méthode
        secondTag = Tag.String("key");
        // Concurrent map benchmark
        // Appelle une méthode
        map = new HashMap<>();
        // Appelle une méthode
        map.put("key", "value");
        // Hash map benchmark
        // Appelle une méthode
        concurrentMap = new ConcurrentHashMap<>();
        // Appelle une méthode
        concurrentMap.put("key", "value");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void writeConstantTag() {
        // Appelle une méthode
        tagHandler.setTag(TAG, "value");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void writeDifferentTag() {
        // Appelle une méthode
        tagHandler.setTag(secondTag, "value");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void writeNewTag() {
        // Appelle une méthode
        tagHandler.setTag(Tag.String("key"), "value");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void writeConcurrentMap() {
        // Appelle une méthode
        concurrentMap.put("key", "value");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void writeMap() {
        // Appelle une méthode
        map.put("key", "value");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
