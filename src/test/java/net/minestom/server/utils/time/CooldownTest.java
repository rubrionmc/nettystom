// Déclaration du paquet de ce fichier
package net.minestom.server.utils.time;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.time.temporal.ChronoUnit;
// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class CooldownTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testReadySinceBeginning() {
        // Appelle une méthode
        var cooldown = new Cooldown(Duration.ofSeconds(1));
        // Appelle une méthode
        assertTrue(cooldown.isReady(0));
        // Appelle une méthode
        assertTrue(cooldown.isReady(Long.MIN_VALUE));
        // Appelle une méthode
        assertTrue(cooldown.isReady(Long.MAX_VALUE));
    // Fin d'un bloc/d'une expression
    }
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testConstructorAndIsReady() {
        // Appelle une méthode
        var beforeNanos = System.nanoTime() - 1;
        // Appelle une méthode
        var cooldown = new Cooldown(Duration.ofSeconds(1), ChronoUnit.NANOS);
        // Appelle une méthode
        cooldown.refreshLastUpdate(System.nanoTime());
        // Appelle une méthode
        var afterNanos = System.nanoTime() + 1;
        // Appelle une méthode
        assertFalse(cooldown.isReady(beforeNanos + TimeUnit.SECONDS.toNanos(1)));
        // Appelle une méthode
        assertTrue(cooldown.isReady(afterNanos + TimeUnit.SECONDS.toNanos(1)));
        // Appelle une méthode
        assertEquals(cooldown.getDuration(), Duration.ofSeconds(1));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testHasCooldown() {
        // Appelle une méthode
        var nanoTime = System.nanoTime();
        // Appelle une méthode
        assertTrue(Cooldown.hasCooldown(ChronoUnit.NANOS, nanoTime, nanoTime - TimeUnit.SECONDS.toNanos(1) + 1, ChronoUnit.SECONDS, 1));
        // Appelle une méthode
        assertFalse(Cooldown.hasCooldown(ChronoUnit.NANOS, nanoTime, nanoTime - TimeUnit.SECONDS.toNanos(1), ChronoUnit.SECONDS, 1));

        // we assume this test does not take longer than 1 hour
        // Appelle une méthode
        assertTrue(Cooldown.hasCooldown(nanoTime, ChronoUnit.HOURS, 1));

        // Appelle une méthode
        assertFalse(Cooldown.hasCooldown(nanoTime - TimeUnit.HOURS.toNanos(1), ChronoUnit.HOURS, 1));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
