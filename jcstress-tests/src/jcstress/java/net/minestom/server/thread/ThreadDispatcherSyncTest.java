// Déclaration du paquet de ce fichier
package net.minestom.server.thread;

// Import d'une classe nécessaire
import net.minestom.server.Tickable;
// Import d'une classe nécessaire
import org.openjdk.jcstress.annotations.*;
// Import d'une classe nécessaire
import org.openjdk.jcstress.infra.results.L_Result;

// Import statique d'un membre
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;

// Annotation pour l'élément suivant
@JCStressTest
// Annotation pour l'élément suivant
@Outcome(id = "301", expect = ACCEPTABLE)
// Annotation pour l'élément suivant
@State
// Déclaration de type (classe/interface/enum/record)
public class ThreadDispatcherSyncTest {
    // Appelle une méthode
    private final ThreadDispatcher<World, Element> dispatcher = ThreadDispatcher.singleThread();
    // Appelle une méthode
    private final World world = new World();
    // Appelle une méthode
    private final Element element = new Element();

    // Déclaration de type (classe/interface/enum/record)
    record World() {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static final class Element implements Tickable {
        // Instruction de code
        int value;

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void tick(long time) {
            // Appelle une méthode
            compute();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        void compute() {
            // Instruction de code
            value++;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'un bloc
    {
        // Appelle une méthode
        dispatcher.createPartition(world);
        // Appelle une méthode
        dispatcher.updateElement(element, world);
        // Appelle une méthode
        dispatcher.start();
        // Appelle une méthode
        dispatcher.refreshThreads();
        // Appelle une méthode
        dispatcher.updateAndAwait(0);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Actor
    // Début d'une méthode/d'un bloc
    public void actor1() {
        // Boucle : répète un bloc
        for (int i = 0; i < 100; i++) dispatcher.updateAndAwait(0);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Actor
    // Début d'une méthode/d'un bloc
    public void actor2() {
        // Boucle : répète un bloc
        for (int i = 0; i < 100; i++) dispatcher.updateAndAwait(0);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Actor
    // Début d'une méthode/d'un bloc
    public void actor3() {
        // Boucle : répète un bloc
        for (int i = 0; i < 100; i++) dispatcher.updateAndAwait(0);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Arbiter
    // Début d'une méthode/d'un bloc
    public void arbiter(L_Result r) {
        // Affecte une valeur
        r.r1 = element.value;
        // Appelle une méthode
        dispatcher.shutdown();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
