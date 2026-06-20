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
@Outcome(id = "40101", expect = ACCEPTABLE)
// Annotation pour l'élément suivant
@State
// Déclaration de type (classe/interface/enum/record)
public class AcquirableSyncTest {

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
    static final class Element implements Tickable, AcquirableSource<Element> {
        // Appelle une méthode
        private final Acquirable<Element> acquirable = Acquirable.unassigned(this);
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

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Acquirable<? extends Element> acquirable() {
            // Renvoie une valeur à l'appelant
            return acquirable;
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

    // Début d'une méthode/d'un bloc
    private void loop() {
        // Boucle : répète un bloc
        for (int i = 0; i < 10_000; i++) {
            // Appelle une méthode
            element.acquirable().sync(Element::compute);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Actor
    // Début d'une méthode/d'un bloc
    public void actor0() {
        // Boucle : répète un bloc
        for (int i = 0; i < 100; i++) {
            // Appelle une méthode
            dispatcher.updateAndAwait(0);
            // Gestion des exceptions
            try {
                // Appelle une méthode
                Thread.sleep(1);
            // Début d'une méthode/d'un bloc
            } catch (InterruptedException e) {
                // Lève une exception
                throw new RuntimeException(e);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Actor
    // Début d'une méthode/d'un bloc
    public void actor1() {
        // Affecte une valeur
        TickThread tickThread = new TickThread(1) {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void run() {
                // Appelle une méthode
                loop();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        tickThread.start();
        // Gestion des exceptions
        try {
            // Appelle une méthode
            tickThread.join();
        // Début d'une méthode/d'un bloc
        } catch (InterruptedException e) {
            // Lève une exception
            throw new RuntimeException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Actor
    // Début d'une méthode/d'un bloc
    public void actor2() {
        // Affecte une valeur
        TickThread tickThread = new TickThread(2) {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void run() {
                // Appelle une méthode
                loop();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        tickThread.start();
        // Gestion des exceptions
        try {
            // Appelle une méthode
            tickThread.join();
        // Début d'une méthode/d'un bloc
        } catch (InterruptedException e) {
            // Lève une exception
            throw new RuntimeException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Actor
    // Début d'une méthode/d'un bloc
    public void actor3() {
        // Affecte une valeur
        TickThread tickThread = new TickThread(3) {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void run() {
                // Appelle une méthode
                loop();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        tickThread.start();
        // Gestion des exceptions
        try {
            // Appelle une méthode
            tickThread.join();
        // Début d'une méthode/d'un bloc
        } catch (InterruptedException e) {
            // Lève une exception
            throw new RuntimeException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Actor
    // Début d'une méthode/d'un bloc
    public void actor4() {
        // Appelle une méthode
        Thread thread = new Thread(this::loop);
        // Appelle une méthode
        thread.start();
        // Gestion des exceptions
        try {
            // Appelle une méthode
            thread.join();
        // Début d'une méthode/d'un bloc
        } catch (InterruptedException e) {
            // Lève une exception
            throw new RuntimeException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Arbiter
    // Début d'une méthode/d'un bloc
    public void arbiter(L_Result r) {
        // Appelle une méthode
        element.acquirable().sync(test -> r.r1 = test.value);
        // Appelle une méthode
        dispatcher.shutdown();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
