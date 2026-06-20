// Déclaration du paquet de ce fichier
package net.minestom.testing.test;

// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Assertions;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class IntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testEnv(Env env) {
        // Appelle une méthode
        Assertions.assertNotNull(env);
        // Appelle une méthode
        Assertions.assertNotNull(env.process());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
