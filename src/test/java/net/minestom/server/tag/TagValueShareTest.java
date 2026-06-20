// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.function.Function;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertFalse;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test tags that can share cached values.
 */
// Déclaration de type (classe/interface/enum/record)
public class TagValueShareTest {

    // Déclaration de type (classe/interface/enum/record)
    record Entry(int value) {
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void same() {
        // Appelle une méthode
        var tag = Tag.String("test");
        // Appelle une méthode
        assertTrue(tag.shareValue(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void similar() {
        // Appelle une méthode
        var tag = Tag.String("test");
        // Appelle une méthode
        var tag2 = Tag.String("test");
        // Appelle une méthode
        assertTrue(tag.shareValue(tag2));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void differentDefault() {
        // Appelle une méthode
        var tag = Tag.String("test").defaultValue("test2");
        // Appelle une méthode
        var tag2 = Tag.String("test").defaultValue("test3");
        // Appelle une méthode
        assertTrue(tag.shareValue(tag2));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void differentType() {
        // Appelle une méthode
        var tag = Tag.String("test");
        // Appelle une méthode
        var tag2 = Tag.Integer("test");
        // Appelle une méthode
        assertFalse(tag.shareValue(tag2));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void mapSame() {
        // Force identical functions
        // Affecte une valeur
        Function<Integer, Entry> t1 = Entry::new;
        // Affecte une valeur
        Function<Entry, Integer> t2 = Entry::value;

        // Appelle une méthode
        var tag = Tag.Integer("key");
        // Appelle une méthode
        var map1 = tag.map(t1, t2);
        // Appelle une méthode
        var map2 = tag.map(t1, t2);
        // Appelle une méthode
        assertTrue(map1.shareValue(map2));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void mapChild() {
        // Appelle une méthode
        var intTag = Tag.Integer("key");
        // Appelle une méthode
        var tag = intTag.map(Entry::new, Entry::value);
        // Appelle une méthode
        assertFalse(intTag.shareValue(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void list() {
        // Appelle une méthode
        var tag = Tag.String("test").list();
        // Appelle une méthode
        assertTrue(tag.shareValue(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void listScope() {
        // Appelle une méthode
        var tag = Tag.String("test");
        // Appelle une méthode
        assertFalse(tag.shareValue(tag.list()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void similarList() {
        // Appelle une méthode
        var tag = Tag.String("test").list();
        // Appelle une méthode
        var tag2 = Tag.String("test").list();
        // Appelle une méthode
        assertTrue(tag.shareValue(tag2));
        // Appelle une méthode
        assertTrue(tag.list().shareValue(tag2.list()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void differentList() {
        // Appelle une méthode
        var tag = Tag.String("test").list();
        // Appelle une méthode
        var tag2 = Tag.String("test").list();
        // Appelle une méthode
        assertFalse(tag.shareValue(tag2.list()));
        // Appelle une méthode
        assertFalse(tag.list().shareValue(tag2.list().list()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void differentListType() {
        // Appelle une méthode
        var tag = Tag.String("test").list();
        // Appelle une méthode
        var tag2 = Tag.Integer("test").list();
        // Appelle une méthode
        assertFalse(tag.shareValue(tag2));
        // Appelle une méthode
        assertFalse(tag.list().shareValue(tag2.list()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void recordStructure() {
        // Appelle une méthode
        var tag = Tag.Structure("test", Vec.class);
        // Appelle une méthode
        var tag2 = Tag.Structure("test", Vec.class);
        // Appelle une méthode
        assertTrue(tag.shareValue(tag2));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void recordStructureList() {
        // Appelle une méthode
        var tag = Tag.Structure("test", Vec.class).list();
        // Appelle une méthode
        var tag2 = Tag.Structure("test", Vec.class).list();
        // Appelle une méthode
        assertTrue(tag.shareValue(tag2));
        // Appelle une méthode
        assertTrue(tag.list().shareValue(tag2.list()));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
