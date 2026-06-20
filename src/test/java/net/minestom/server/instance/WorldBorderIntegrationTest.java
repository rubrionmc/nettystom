// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertThrows;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class WorldBorderIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void setWorldborderSize(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();

        // Appelle une méthode
        instance.setWorldBorder(WorldBorder.DEFAULT_BORDER.withDiameter(50));
        // Appelle une méthode
        assertEquals(50, instance.getWorldBorder().diameter());
        // Appelle une méthode
        instance.setWorldBorder(WorldBorder.DEFAULT_BORDER.withDiameter(10));
        // Appelle une méthode
        assertEquals(10, instance.getWorldBorder().diameter());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void resizeWorldBorder(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();

        // Appelle une méthode
        WorldBorder border = instance.getWorldBorder();
        // Appelle une méthode
        instance.setWorldBorder(border.withDiameter(10));
        // Appelle une méthode
        assertEquals(10, instance.getWorldBorder().diameter());

        // Lerp
        // Appelle une méthode
        instance.setWorldBorder(border.withDiameter(30), 1);
        // Boucle : répète un bloc
        for (int i = 0; i < 10; i++) {
            // Appelle une méthode
            assertEquals(10 + i, instance.getWorldBorder().diameter());
            // Appelle une méthode
            instance.tick(0);
        // Fin d'un bloc/d'une expression
        }

        // Lerp from another diameter mid lerp
        // Appelle une méthode
        instance.setWorldBorder(border.withDiameter(25), 0.25);
        // Boucle : répète un bloc
        for (int i = 0; i < 5; i++) {
            // Appelle une méthode
            assertEquals(20 + i, instance.getWorldBorder().diameter());
            // Appelle une méthode
            instance.tick(0);
        // Fin d'un bloc/d'une expression
        }

        // Ensure lerp finished
        // Boucle : répète un bloc
        for (int i = 0; i < 4; i++) {
            // Appelle une méthode
            assertEquals(25, instance.getWorldBorder().diameter());
            // Appelle une méthode
            instance.tick(0);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void invalidArguments(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();

        // Appelle une méthode
        WorldBorder border = instance.getWorldBorder();
        // Appelle une méthode
        assertThrows(IllegalStateException.class, () -> instance.setWorldBorder(border, -1));
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> border.withDiameter(-1));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
