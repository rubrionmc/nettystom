// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class GameModeTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void toId() {
        // Appelle une méthode
        assertEquals(GameMode.SURVIVAL.ordinal(), 0);
        // Appelle une méthode
        assertEquals(GameMode.CREATIVE.ordinal(), 1);
        // Appelle une méthode
        assertEquals(GameMode.ADVENTURE.ordinal(), 2);
        // Appelle une méthode
        assertEquals(GameMode.SPECTATOR.ordinal(), 3);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
