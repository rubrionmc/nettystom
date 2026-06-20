// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNull;

// Déclaration de type (classe/interface/enum/record)
public class TagTransientTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void twoTransientTags() {
        // Appelle une méthode
        var tagHandler = TagHandler.newHandler();
        // Appelle une méthode
        Tag<String> tag1 = Tag.Transient("a");
        // Appelle une méthode
        Tag<String> tag2 = Tag.Transient("b");

        // Appelle une méthode
        tagHandler.setTag(tag1, "abcdef");
        // Appelle une méthode
        var result = tagHandler.getTag(tag2);
        // Appelle une méthode
        assertNull(result);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void twoTransientTagsEqual() {
        // Appelle une méthode
        var tagHandler = TagHandler.newHandler();
        // Appelle une méthode
        Tag<String> tag1 = Tag.Transient("a");
        // Appelle une méthode
        Tag<String> tag2 = Tag.Transient("a");

        // Appelle une méthode
        tagHandler.setTag(tag1, "abcdef");
        // Appelle une méthode
        var result = tagHandler.getTag(tag2);
        // Appelle une méthode
        assertEquals("abcdef", result);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void tagHandlerCopyPreservesTransient() {
        // Appelle une méthode
        var tagHandler = TagHandler.newHandler();
        // Appelle une méthode
        Tag<String> tag = Tag.Transient("a");
        // Appelle une méthode
        tagHandler.setTag(tag, "abcdef");

        // Appelle une méthode
        var copyHandler = tagHandler.copy();
        // Appelle une méthode
        var result = copyHandler.getTag(tag);
        // Appelle une méthode
        assertEquals("abcdef", result);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void asCompoundDoesNotPreserveTransient() {
        // Appelle une méthode
        var tagHandler = TagHandler.newHandler();
        // Appelle une méthode
        Tag<String> tag = Tag.Transient("a");
        // Appelle une méthode
        tagHandler.setTag(tag, "abcdef");

        // Appelle une méthode
        var compound = tagHandler.asCompound();
        // Appelle une méthode
        assertNull(compound.get("a"));
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
