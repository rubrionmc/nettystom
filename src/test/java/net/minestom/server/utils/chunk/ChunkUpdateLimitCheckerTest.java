// Déclaration du paquet de ce fichier
package net.minestom.server.utils.chunk;

// Import d'une classe nécessaire
import net.minestom.server.instance.DynamicChunk;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertFalse;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertTrue;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class ChunkUpdateLimitCheckerTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testHistory(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var limiter = new ChunkUpdateLimitChecker(3);

        // Appelle une méthode
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 0)));
        // Appelle une méthode
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 1)));
        // Appelle une méthode
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 2)));
        // history : 0, 1, 2

        // Appelle une méthode
        assertFalse(limiter.addToHistory(new DynamicChunk(instance, 0, 0)));
        // history : 1, 2, 0
        // Appelle une méthode
        assertFalse(limiter.addToHistory(new DynamicChunk(instance, 0, 1)));
        // history : 2, 0, 1
        // Appelle une méthode
        assertFalse(limiter.addToHistory(new DynamicChunk(instance, 0, 2)));
        // history : 0, 1, 2

        // Appelle une méthode
        assertFalse(limiter.addToHistory(new DynamicChunk(instance, 0, 2)));
        // history : 1, 2, 2
        // Appelle une méthode
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 0)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testOneSlotHistory(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var limiter = new ChunkUpdateLimitChecker(1);
        // Appelle une méthode
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 0)));
        // Appelle une méthode
        assertFalse(limiter.addToHistory(new DynamicChunk(instance, 0, 0)));
        // Appelle une méthode
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 1)));
        // Appelle une méthode
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 0)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testDisabling(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var limiter = new ChunkUpdateLimitChecker(0);
        // Appelle une méthode
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 0)));
        // Appelle une méthode
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 0)));
        // Appelle une méthode
        assertTrue(limiter.addToHistory(new DynamicChunk(instance, 0, 1)));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
