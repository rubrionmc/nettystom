// Déclaration du paquet de ce fichier
package net.minestom.server.snapshot;

// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class InstanceSnapshotIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void basic(Env env) {
        // Appelle une méthode
        env.createFlatInstance();
        // Appelle une méthode
        var snapshot = ServerSnapshot.update();

        // Ensure that the collection is immutable
        // Début d'un bloc
        {
            // Appelle une méthode
            var instances = snapshot.instances();
            // Appelle une méthode
            assertEquals(1, instances.size());

            // Appelle une méthode
            env.createFlatInstance();
            // Appelle une méthode
            instances = snapshot.instances();
            // Appelle une méthode
            assertEquals(1, instances.size());
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        var inst = snapshot.instances().iterator().next();

        // Appelle une méthode
        assertEquals(snapshot, inst.server(), "Instance must have access to the server snapshot");

        // Appelle une méthode
        assertEquals(0, inst.time());
        // Appelle une méthode
        assertEquals(0, inst.worldAge());

        // Appelle une méthode
        assertEquals(0, inst.chunks().size());
        // Appelle une méthode
        assertEquals(0, inst.entities().size());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
