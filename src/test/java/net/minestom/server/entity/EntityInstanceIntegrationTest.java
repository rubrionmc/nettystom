// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Assertions;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.time.Duration;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityInstanceIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityJoin(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        assertEquals(instance, entity.getInstance());
        // Appelle une méthode
        assertEquals(new Pos(0, 42, 0), entity.getPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void playerJoin(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertEquals(instance, player.getInstance());
        // Appelle une méthode
        assertEquals(new Pos(0, 42, 0), player.getPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void playerSwitch(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var instance2 = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertEquals(instance, player.getInstance());
        // #join may cause the thread to hang as scheduled for the next tick when initially in a pool
        // Appelle une méthode
        Assertions.assertTimeout(Duration.ofSeconds(2), () -> player.setInstance(instance2).join());
        // Appelle une méthode
        assertEquals(instance2, player.getInstance());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
