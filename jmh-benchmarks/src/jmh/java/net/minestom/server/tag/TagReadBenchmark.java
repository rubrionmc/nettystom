// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;
// Import d'une classe nécessaire
import org.openjdk.jmh.infra.Blackhole;

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
public class TagReadBenchmark {
    // Appelle une méthode
    static final Tag<String> TAG = Tag.String("key");

    // Annotation pour l'élément suivant
    @Param({"false", "true"})
    // Instruction de code
    public boolean present;

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
        // Embranchement : vérifie une condition
        if (present) tagHandler.setTag(TAG, "value");
        // Appelle une méthode
        secondTag = Tag.String("key");
        // Concurrent map benchmark
        // Affecte une valeur
        map = new HashMap<>();
        // Embranchement : vérifie une condition
        if (present) map.put("key", "value");
        // Hash map benchmark
        // Affecte une valeur
        concurrentMap = new ConcurrentHashMap<>();
        // Embranchement : vérifie une condition
        if (present) concurrentMap.put("key", "value");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readConstantTag(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(tagHandler.getTag(TAG));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readDifferentTag(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(tagHandler.getTag(secondTag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readNewTag(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(tagHandler.getTag(Tag.String("key")));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readConcurrentMap(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(concurrentMap.get("key"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readMap(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(map.get("key"));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
