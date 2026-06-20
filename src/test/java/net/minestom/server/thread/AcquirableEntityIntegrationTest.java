// Déclaration du paquet de ce fichier
package net.minestom.server.thread;

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

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertFalse;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class AcquirableEntityIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void instanceSet(Env env) throws InterruptedException {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var zombie = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        CountDownLatch latch = new CountDownLatch(1);
        // Début d'une méthode/d'un bloc
        Thread.startVirtualThread(() -> {
            // Appelle une méthode
            assertFalse(zombie.acquirable().isOwned());
            // Appelle une méthode
            assertFalse(zombie.acquirable().isLocal());
            //assertThrows(AcquirableOwnershipException.class, () -> zombie.setInstance(instance, new Pos(1, 41, 1)).join());
            // Appelle une méthode
            latch.countDown();
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        latch.await();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
