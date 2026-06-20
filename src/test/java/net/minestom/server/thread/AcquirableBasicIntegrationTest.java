// Déclaration du paquet de ce fichier
package net.minestom.server.thread;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.concurrent.CountDownLatch;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class AcquirableBasicIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void localTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Affecte une valeur
        var zombie = new Entity(EntityType.ZOMBIE) {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void tick(long time) {
                // Accès à l'objet courant/parent
                super.tick(time);
                // Appelle une méthode
                assertTrue(this.acquirable().isLocal());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        zombie.setInstance(instance, new Pos(1, 41, 1)).join();
        // Appelle une méthode
        var acquirable = zombie.acquirable();
        // Check local state before initialization
        // Appelle une méthode
        assertTrue(acquirable.isOwned());
        // Appelle une méthode
        acquirable.sync(entity -> assertTrue(acquirable.isLocal()));
        // Appelle une méthode
        Thread.startVirtualThread(() -> assertFalse(acquirable.isLocal()));

        // Instruction de code
        env.tick(); // Ensure the entity can access itself

        // Check local state after initialization
        // Appelle une méthode
        assertFalse(acquirable.isOwned());
        // Appelle une méthode
        acquirable.sync(entity -> assertFalse(acquirable.isLocal()));
        // Appelle une méthode
        Thread.startVirtualThread(() -> assertFalse(acquirable.isLocal()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void ownedTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Affecte une valeur
        var zombie = new Entity(EntityType.ZOMBIE) {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void tick(long time) {
                // Accès à l'objet courant/parent
                super.tick(time);
                // Appelle une méthode
                assertTrue(this.acquirable().isOwned());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        zombie.setInstance(instance, new Pos(1, 41, 1)).join();
        // Appelle une méthode
        var acquirable = zombie.acquirable();
        // Check ownership before initialization
        // Appelle une méthode
        assertTrue(acquirable.isOwned());
        // Appelle une méthode
        acquirable.sync(entity -> assertTrue(acquirable.isOwned()));
        // Appelle une méthode
        Thread.startVirtualThread(() -> assertFalse(acquirable.isOwned()));

        // Instruction de code
        env.tick(); // Ensure the entity can access itself

        // Check ownership after initialization
        // Appelle une méthode
        assertFalse(acquirable.isOwned());
        // Appelle une méthode
        acquirable.sync(entity -> assertTrue(acquirable.isOwned()));
        // Appelle une méthode
        Thread.startVirtualThread(() -> assertFalse(acquirable.isOwned()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void acquireSingleThreadInit(Env env) {
        // Ensure that acquisition before and after initialization are properly handled
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Appelle une méthode
        var zombie = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var acquirable = zombie.acquirable();

        // Appelle une méthode
        zombie.setInstance(instance, new Pos(1, 41, 1)).join();
        // Instruction de code
        env.tick(); // Init entity

        // Appelle une méthode
        AtomicInteger counter = new AtomicInteger(0);

        // Appelle une méthode
        acquirable.sync(entity -> counter.incrementAndGet());
        // Appelle une méthode
        assertEquals(1, counter.get());

        // Appelle une méthode
        acquirable.sync(entity -> counter.incrementAndGet());
        // Appelle une méthode
        assertEquals(2, counter.get());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void acquireBeforeInit(Env env) throws InterruptedException {
        // Ensure that acquisition before initialization are properly handled
        // Appelle une méthode
        var zombie = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var acquirable = zombie.acquirable();
        // Appelle une méthode
        CountDownLatch latch = new CountDownLatch(1);
        // Début d'une méthode/d'un bloc
        Thread.startVirtualThread(() -> assertThrows(IllegalStateException.class, () -> {
            // Appelle une méthode
            latch.countDown();
            // Début d'une méthode/d'un bloc
            acquirable.sync(entity -> {
            // Fin d'un bloc/d'une expression
            });
        // Instruction de code
        }));
        // Appelle une méthode
        latch.await();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
