// Déclaration du paquet de ce fichier
package net.minestom.server.game;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Déclaration de type (classe/interface/enum/record)
public class GameEventTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void load() {
        // Appelle une méthode
        assertNotNull(GameEventImpl.REGISTRY);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
