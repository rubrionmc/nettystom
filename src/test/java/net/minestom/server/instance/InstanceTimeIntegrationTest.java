// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.world.clock.WorldClock;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Objects;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class InstanceTimeIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void overworldTicking(Env env) {
        // Appelle une méthode
        var instance = env.createEmptyInstance();
        // Appelle une méthode
        var clock = instance.defaultClock();
        // Appelle une méthode
        assertNotNull(clock);

        // Appelle une méthode
        assertEquals(0, clock.time());

        // Boucle : répète un bloc
        for (int i = 0; i < 100; i++) {
            // Appelle une méthode
            env.tick();
            // Appelle une méthode
            assertEquals(i + 1, clock.time());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void pausing(Env env) {
        // Appelle une méthode
        var instance = env.createEmptyInstance();
        // Appelle une méthode
        var clock = instance.defaultClock();
        // Appelle une méthode
        assertNotNull(clock);

        // Appelle une méthode
        assertEquals(0, clock.time());
        // Boucle : répète un bloc
        for (int i = 0; i < 5; i++) env.tick();
        // Appelle une méthode
        assertEquals(5, clock.time());

        // Appelle une méthode
        clock.pause();
        // Appelle une méthode
        assertTrue(clock.paused());

        // Boucle : répète un bloc
        for (int i = 0; i < 5; i++) env.tick();
        // Appelle une méthode
        assertEquals(5, clock.time());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void partialTickRate(Env env) {
        // Appelle une méthode
        var instance = env.createEmptyInstance();
        // Appelle une méthode
        var clock = instance.defaultClock();
        // Appelle une méthode
        assertNotNull(clock);

        // Appelle une méthode
        clock.rate(0.2f);
        // Boucle : répète un bloc
        for (int i = 0; i < 10; i++) env.tick();
        // Appelle une méthode
        assertEquals(2, clock.time());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void multipleClocks(Env env) {
        // Appelle une méthode
        var myOtherClock = env.process().worldClock().register(Key.key("minestom:clock"), WorldClock.create());

        // Appelle une méthode
        var instance = env.createEmptyInstance();
        // Appelle une méthode
        var defaultClock = Objects.requireNonNull(instance.defaultClock());
        // Appelle une méthode
        var otherClock = instance.clock(myOtherClock);

        // Boucle : répète un bloc
        for (int i = 0; i < 5; i++) env.tick();
        // Appelle une méthode
        defaultClock.pause();
        // Boucle : répète un bloc
        for (int i = 0; i < 5; i++) env.tick();

        // Appelle une méthode
        assertEquals(5, defaultClock.time());
        // Appelle une méthode
        assertEquals(10, otherClock.time());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
