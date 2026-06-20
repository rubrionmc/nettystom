// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertSame;

// Déclaration de type (classe/interface/enum/record)
public class TagHandlerReadableCopyTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void copyCache() {
        // Appelle une méthode
        var tag = Tag.String("key");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(tag, "test");

        // Appelle une méthode
        var copy = handler.readableCopy();
        // Appelle une méthode
        assertEquals(handler.getTag(tag), copy.getTag(tag));

        // Appelle une méthode
        handler.setTag(tag, "test2");
        // Appelle une méthode
        assertEquals("test2", handler.getTag(tag));
        // Appelle une méthode
        assertEquals("test", copy.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void copyCachePath() {
        // Appelle une méthode
        var tag = Tag.String("key").path("path");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(tag, "test");
        // Instruction de code
        assertEqualsSNBT("""
                {"path":{"key":"test"}}
                """, handler.asCompound());

        // Appelle une méthode
        var copy = handler.readableCopy();
        // Appelle une méthode
        handler.setTag(tag, "test2");
        // Appelle une méthode
        assertEquals("test2", handler.getTag(tag));
        // Appelle une méthode
        assertEquals("test", copy.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void copyCacheReuse() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(Tag.String("key"), "test");
        // Appelle une méthode
        assertSame(handler.readableCopy(), handler.readableCopy());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void copyRehashing() {
        // Appelle une méthode
        var tag = Tag.String("key");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(tag, "test");
        // Appelle une méthode
        var copy = handler.readableCopy();
        // Boucle : répète un bloc
        for (int i = 0; i < 1000; i++) {
            // Appelle une méthode
            handler.setTag(Tag.Integer("copyRehashing" + i), i);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertEquals("test", handler.getTag(tag));
        // Appelle une méthode
        assertEquals("test", copy.getTag(tag));

        // Appelle une méthode
        handler.setTag(tag, "test2");
        // Appelle une méthode
        assertEquals("test2", handler.getTag(tag));
        // Appelle une méthode
        assertEquals("test", copy.getTag(tag));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
