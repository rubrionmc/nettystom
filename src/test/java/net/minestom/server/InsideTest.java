// Déclaration du paquet de ce fichier
package net.minestom.server;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertTrue;

// Déclaration de type (classe/interface/enum/record)
public class InsideTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void inside() {
        // Appelle une méthode
        assertTrue(ServerFlag.INSIDE_TEST);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
