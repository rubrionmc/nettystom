// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Disabled;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNotNull;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNull;

// Déclaration de type (classe/interface/enum/record)
public class PlayerSkinTest {

    // Annotation pour l'élément suivant
    @Disabled
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void validName() {
        // Appelle une méthode
        var skin = PlayerSkin.fromUsername("jeb_");
        // Appelle une méthode
        assertNotNull(skin);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Disabled
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void invalidName() {
        // Appelle une méthode
        var skin = PlayerSkin.fromUsername("jfdsa84vvcxadubasdfcvn");
        // Appelle une méthode
        assertNull(skin);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
