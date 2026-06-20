// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNull;

// Déclaration de type (classe/interface/enum/record)
public class TagHandlerCopyTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void copy() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(Tag.String("key"), "test");

        // Appelle une méthode
        var copy = handler.copy();
        // Appelle une méthode
        assertEquals(handler.getTag(Tag.String("key")), copy.getTag(Tag.String("key")));
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
        var copy = handler.copy();
        // Appelle une méthode
        handler.setTag(tag, "test2");
        // Instruction de code
        assertEqualsSNBT("""
                {"path":{"key":"test2"}}
                """, handler.asCompound());
        // Instruction de code
        assertEqualsSNBT("""
                {"path":{"key":"test"}}
                """, copy.asCompound());

        // Appelle une méthode
        copy.setTag(tag, "test3");
        // Appelle une méthode
        assertEquals("test3", copy.getTag(tag));
        // Instruction de code
        assertEqualsSNBT("""
                {"path":{"key":"test3"}}
                """, copy.asCompound());
    // Fin d'un bloc/d'une expression
    }

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
        // Instruction de code
        assertEqualsSNBT("""
                {"key":"test"}
                """, handler.asCompound());

        // Appelle une méthode
        var copy = handler.copy();
        // Appelle une méthode
        handler.setTag(tag, "test2");
        // Instruction de code
        assertEqualsSNBT("""
                {"key":"test2"}
                """, handler.asCompound());
        // Instruction de code
        assertEqualsSNBT("""
                {"key":"test"}
                """, copy.asCompound());

        // Appelle une méthode
        copy.setTag(tag, "test3");
        // Appelle une méthode
        assertEquals("test3", copy.getTag(tag));
        // Instruction de code
        assertEqualsSNBT("""
                {"key":"test2"}
                """, handler.asCompound());
        // Instruction de code
        assertEqualsSNBT("""
                {"key":"test3"}
                """, copy.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void copyRehashing() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Instruction de code
        TagHandler handlerCopy;
        // Boucle : répète un bloc
        for (int i = 0; i < 1000; i++) {
            // Appelle une méthode
            handlerCopy = handler.copy();
            // Appelle une méthode
            var tag = Tag.Integer("copyRehashing" + i);
            // Appelle une méthode
            handler.setTag(tag, i);
            // Appelle une méthode
            assertNull(handlerCopy.getTag(tag));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
