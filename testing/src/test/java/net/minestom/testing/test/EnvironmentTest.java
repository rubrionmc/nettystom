// Déclaration du paquet de ce fichier
package net.minestom.testing.test;

// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Assertions;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Déclaration de type (classe/interface/enum/record)
public class EnvironmentTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void insideTest() {
        // Appelle une méthode
        Assertions.assertTrue(ServerFlag.INSIDE_TEST);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
