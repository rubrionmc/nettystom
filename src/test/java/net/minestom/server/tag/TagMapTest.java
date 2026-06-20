// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNull;

// Déclaration de type (classe/interface/enum/record)
public class TagMapTest {

    // Déclaration de type (classe/interface/enum/record)
    private record Entry(int value) {
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void map() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var intTag = Tag.Integer("key");
        // Appelle une méthode
        var tag = intTag.map(Entry::new, Entry::value);

        // Appelle une méthode
        handler.setTag(tag, new Entry(1));
        // Appelle une méthode
        assertEquals(1, handler.getTag(intTag));
        // Appelle une méthode
        assertEquals(new Entry(1), handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void mapDefault() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var intTag = Tag.Integer("key");
        // Appelle une méthode
        var tag = intTag.map(Entry::new, Entry::value);

        // Appelle une méthode
        assertEquals(new Entry(1), handler.getTag(tag.defaultValue(new Entry(1))));

        // Appelle une méthode
        handler.setTag(tag, new Entry(2));
        // Appelle une méthode
        assertEquals(2, handler.getTag(intTag));
        // Appelle une méthode
        assertEquals(new Entry(2), handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void mapDefaultAbsent() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Integer("key").map(Entry::new, Entry::value);
        // Appelle une méthode
        assertNull(handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
