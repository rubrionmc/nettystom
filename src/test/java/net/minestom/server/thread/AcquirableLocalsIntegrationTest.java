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
import java.util.Set;
// Import d'une classe nécessaire
import java.util.stream.Collectors;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class AcquirableLocalsIntegrationTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void empty(Env env) {
        // Appelle une méthode
        assertEquals(0, Acquirable.localEntities().count());
    // Fin d'un bloc/d'une expression
    }

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
                assertEquals(Set.of(this), Acquirable.localEntities().collect(Collectors.toUnmodifiableSet()));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        zombie.setInstance(instance, new Pos(1, 41, 1)).join();
        // Appelle une méthode
        env.tick();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
