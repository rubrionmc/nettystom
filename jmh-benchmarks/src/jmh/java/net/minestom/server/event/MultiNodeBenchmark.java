// Déclaration du paquet de ce fichier
package net.minestom.server.event;

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
public class MultiNodeBenchmark {

    // Annotation pour l'élément suivant
    @Param({"0", "1", "3", "10"})
    // Instruction de code
    public int children;

    // Instruction de code
    private EventNode<Event> node;

    // Déclaration de type (classe/interface/enum/record)
    record TestEvent() implements Event {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record TestEvent2() implements Event {
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Setup
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Appelle une méthode
        node = EventNode.all("node");
        // Boucle : répète un bloc
        for (int i = 0; i < children; i++) {
            // Appelle une méthode
            var child = EventNode.all("child-" + i);
            // Début d'une méthode/d'un bloc
            child.addListener(TestEvent.class, e -> {
                // Empty
            // Fin d'un bloc/d'une expression
            });

            // Appelle une méthode
            node.addChild(child);

            // Real-world code are very unlikely to use entirely empty nodes.
            // This ensures that the handle map is properly lazily initialized to prevent fast exits.
            // Début d'une méthode/d'un bloc
            child.addListener(TestEvent2.class, e -> {
                // Empty
            // Appelle une méthode
            }).call(new TestEvent2());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void call() {
        // Appelle une méthode
        node.call(new TestEvent());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
