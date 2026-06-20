// Déclaration du paquet de ce fichier
package net.minestom.server.thread;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNotEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Déclaration de type (classe/interface/enum/record)
public class AcquirableTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void assignation() {
        // Affecte une valeur
        AtomicReference<TickThread> tickThread = new AtomicReference<>();
        // Affecte une valeur
        Entity entity = new Entity(EntityType.ZOMBIE) {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void tick(long time) {
                // Accès à l'objet courant/parent
                super.tick(time);
                // Appelle une méthode
                tickThread.set(acquirable().assignedThread());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        Object first = new Object();
        // Appelle une méthode
        Object second = new Object();

        // Appelle une méthode
        ThreadDispatcher<Object, Entity> dispatcher = ThreadDispatcher.dispatcher(ThreadProvider.counter(), 2);
        // Appelle une méthode
        dispatcher.start();
        // Appelle une méthode
        dispatcher.createPartition(first);
        // Appelle une méthode
        dispatcher.createPartition(second);

        // Appelle une méthode
        dispatcher.updateElement(entity, first);
        // Appelle une méthode
        dispatcher.updateAndAwait(System.nanoTime());
        // Appelle une méthode
        TickThread firstThread = tickThread.get();
        // Appelle une méthode
        assertNotNull(firstThread);

        // Appelle une méthode
        tickThread.set(null);
        // Appelle une méthode
        dispatcher.updateElement(entity, second);
        // Appelle une méthode
        dispatcher.updateAndAwait(System.nanoTime());
        // Appelle une méthode
        TickThread secondThread = tickThread.get();
        // Appelle une méthode
        assertNotNull(secondThread);

        // Appelle une méthode
        assertNotEquals(firstThread, secondThread);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
