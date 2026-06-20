// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;
// Import d'une classe nécessaire
import org.openjdk.jmh.infra.Blackhole;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
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
public class TagReadPathBenchmark {
    // Annotation pour l'élément suivant
    @Param({"0", "1", "2", "3"})
    // Instruction de code
    public int scope;

    // Instruction de code
    TagHandler tagHandler;
    // Instruction de code
    Tag<String> tag;

    // Annotation pour l'élément suivant
    @Setup
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Accès à l'objet courant/parent
        this.tagHandler = TagHandler.newHandler();

        // Affecte une valeur
        List<String> path = new ArrayList<>(scope);
        // Boucle : répète un bloc
        for (int i = 0; i < scope; i++) path.add("key" + i);
        // Accès à l'objet courant/parent
        this.tag = Tag.String("key").path(path.toArray(String[]::new));

        // Appelle une méthode
        tagHandler.setTag(tag, "value");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void read(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(tagHandler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
