// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNotEquals;

// Déclaration de type (classe/interface/enum/record)
public class TagEqualityTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sameType() {
        // Appelle une méthode
        var tag1 = Tag.Integer("key");
        // Appelle une méthode
        var tag2 = Tag.Integer("key");
        // Appelle une méthode
        assertEquals(tag1, tag1);
        // Appelle une méthode
        assertEquals(tag2, tag2);
        // Appelle une méthode
        assertEquals(tag1, tag2);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void differentKey() {
        // Appelle une méthode
        var tag1 = Tag.Integer("key1");
        // Appelle une méthode
        var tag2 = Tag.Integer("key2");
        // Appelle une méthode
        assertNotEquals(tag1, tag2);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sameList() {
        // Appelle une méthode
        var tag1 = Tag.Integer("key").list();
        // Appelle une méthode
        var tag2 = Tag.Integer("key").list();
        // Appelle une méthode
        assertEquals(tag1, tag2);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void differentList() {
        // Appelle une méthode
        var tag1 = Tag.Integer("key").list();
        // Appelle une méthode
        var tag2 = Tag.Integer("key");
        // Appelle une méthode
        assertNotEquals(tag1, tag2);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void unmatchedList() {
        // Appelle une méthode
        var tag1 = Tag.Integer("key").list().list();
        // Appelle une méthode
        var tag2 = Tag.Integer("key").list();
        // Appelle une méthode
        assertNotEquals(tag1, tag2);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void samePath() {
        // Appelle une méthode
        var tag1 = Tag.Integer("key").path("path");
        // Appelle une méthode
        var tag2 = Tag.Integer("key").path("path");
        // Appelle une méthode
        assertEquals(tag1, tag2);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void differentPath() {
        // Appelle une méthode
        var tag1 = Tag.Integer("key").path("path");
        // Appelle une méthode
        var tag2 = Tag.Integer("key").path("path2");
        // Appelle une méthode
        assertNotEquals(tag1, tag2);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void unmatchedPath() {
        // Appelle une méthode
        var tag1 = Tag.Integer("key").path("path", "path2");
        // Appelle une méthode
        var tag2 = Tag.Integer("key").path("path");
        // Appelle une méthode
        assertNotEquals(tag1, tag2);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
