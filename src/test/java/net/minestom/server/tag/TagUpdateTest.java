// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class TagUpdateTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void update() {
        // Appelle une méthode
        var tag = Tag.Integer("coin");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Début d'une méthode/d'un bloc
        handler.updateTag(tag, integer -> {
            // Appelle une méthode
            assertNull(integer);
            // Renvoie une valeur à l'appelant
            return 5;
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assertEquals(5, handler.getTag(tag));
        // Début d'une méthode/d'un bloc
        handler.updateTag(tag, integer -> {
            // Appelle une méthode
            assertEquals(5, integer);
            // Renvoie une valeur à l'appelant
            return 10;
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assertEquals(10, handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void updateDefault() {
        // Appelle une méthode
        var tag = Tag.Integer("coin").defaultValue(25);
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Début d'une méthode/d'un bloc
        handler.updateTag(tag, integer -> {
            // Appelle une méthode
            assertEquals(25, integer);
            // Renvoie une valeur à l'appelant
            return 5;
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assertEquals(5, handler.getTag(tag));
        // Début d'une méthode/d'un bloc
        handler.updateTag(tag, integer -> {
            // Appelle une méthode
            assertEquals(5, integer);
            // Renvoie une valeur à l'appelant
            return 10;
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assertEquals(10, handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void updateRemoval() {
        // Appelle une méthode
        var tag = Tag.Integer("coin");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(tag, 5);
        // Début d'une méthode/d'un bloc
        handler.updateTag(tag, integer -> {
            // Appelle une méthode
            assertEquals(5, integer);
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assertNull(handler.getTag(tag));
        // Appelle une méthode
        assertEqualsSNBT("{}", handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void updateRemovalPath() {
        // Appelle une méthode
        var tag = Tag.Integer("coin").path("path");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(tag, 5);
        // Début d'une méthode/d'un bloc
        handler.updateTag(tag, integer -> {
            // Appelle une méthode
            assertEquals(5, integer);
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assertNull(handler.getTag(tag));
        // Appelle une méthode
        assertEqualsSNBT("{}", handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void updateAndGet() {
        // Appelle une méthode
        var tag = Tag.Integer("coin");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Affecte une valeur
        var result = handler.updateAndGetTag(tag, integer -> {
            // Appelle une méthode
            assertNull(integer);
            // Renvoie une valeur à l'appelant
            return 5;
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assertEquals(5, result);
        // Affecte une valeur
        result = handler.updateAndGetTag(tag, integer -> {
            // Appelle une méthode
            assertEquals(5, integer);
            // Renvoie une valeur à l'appelant
            return 10;
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assertEquals(10, result);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void getAndUpdate() {
        // Appelle une méthode
        var tag = Tag.Integer("coin");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Affecte une valeur
        var result = handler.getAndUpdateTag(tag, integer -> {
            // Appelle une méthode
            assertNull(integer);
            // Renvoie une valeur à l'appelant
            return 5;
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assertNull(result);
        // Affecte une valeur
        result = handler.getAndUpdateTag(tag, integer -> {
            // Appelle une méthode
            assertEquals(5, integer);
            // Renvoie une valeur à l'appelant
            return 10;
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assertEquals(5, result);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void updateHiddenSimilarity() {
        // Appelle une méthode
        var tag1 = Tag.Integer("coin");
        // Appelle une méthode
        var tag2 = Tag.Integer("coin").map(i -> i + 1, i -> i - 1);
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(tag1, 5);
        // Appelle une méthode
        assertDoesNotThrow(() -> handler.updateTag(tag2, value -> 5));
        // Appelle une méthode
        assertEquals(4, handler.getTag(tag1));
        // Appelle une méthode
        assertEquals(5, handler.getTag(tag2));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void updateStructureConversion() {
        // Déclaration de type (classe/interface/enum/record)
        record Test(int coin) {
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        var tag1 = Tag.Integer("coin").path("path");
        // Appelle une méthode
        var tag2 = Tag.Structure("path", Test.class);
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(tag1, 5);
        // Appelle une méthode
        assertEquals(5, handler.getTag(tag1));
        // Appelle une méthode
        assertEquals(new Test(5), handler.getTag(tag2));

        // Appelle une méthode
        assertDoesNotThrow(() -> handler.updateTag(tag2, value -> new Test(value.coin + 1)));
        // Appelle une méthode
        assertEquals(6, handler.getTag(tag1));
        // Appelle une méthode
        assertEquals(new Test(6), handler.getTag(tag2));

        // Appelle une méthode
        handler.updateTag(tag2, value -> null);
        // Appelle une méthode
        assertNull(handler.getTag(tag1));
        // Appelle une méthode
        assertNull(handler.getTag(tag2));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void updateStructureConversionPath() {
        // Déclaration de type (classe/interface/enum/record)
        record Test(int coin) {
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        var tag1 = Tag.Integer("coin").path("path", "path2");
        // Appelle une méthode
        var tag2 = Tag.Structure("path2", Test.class).path("path");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(tag1, 5);
        // Appelle une méthode
        assertEquals(5, handler.getTag(tag1));
        // Appelle une méthode
        assertEquals(new Test(5), handler.getTag(tag2));

        // Appelle une méthode
        assertDoesNotThrow(() -> handler.updateTag(tag2, value -> new Test(value.coin + 1)));
        // Appelle une méthode
        assertEquals(6, handler.getTag(tag1));
        // Appelle une méthode
        assertEquals(new Test(6), handler.getTag(tag2));

        // Appelle une méthode
        handler.updateTag(tag2, value -> null);
        // Appelle une méthode
        assertNull(handler.getTag(tag1));
        // Appelle une méthode
        assertNull(handler.getTag(tag2));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void updateStructureConversionPathDouble() {
        // Déclaration de type (classe/interface/enum/record)
        record Test(int coin) {
        // Fin d'un bloc/d'une expression
        }
        // Déclaration de type (classe/interface/enum/record)
        record Structure(Test test) {
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        var tag1 = Tag.Integer("coin").path("path", "test");
        // Appelle une méthode
        var tag2 = Tag.Structure("path", Structure.class);

        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(tag1, 5);
        // Appelle une méthode
        assertEquals(5, handler.getTag(tag1));
        // Appelle une méthode
        assertEquals(new Structure(new Test(5)), handler.getTag(tag2));

        // Appelle une méthode
        assertDoesNotThrow(() -> handler.updateTag(tag2, value -> new Structure(new Test(value.test.coin + 1))));
        // Appelle une méthode
        assertEquals(6, handler.getTag(tag1));
        // Appelle une méthode
        assertEquals(new Structure(new Test(6)), handler.getTag(tag2));

        // Appelle une méthode
        handler.updateTag(tag2, value -> null);
        // Appelle une méthode
        assertNull(handler.getTag(tag1));
        // Appelle une méthode
        assertNull(handler.getTag(tag2));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void updateViewConversion() {
        // Déclaration de type (classe/interface/enum/record)
        record Test(int coin) {
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        var tag1 = Tag.Integer("coin");
        // Appelle une méthode
        var tag2 = Tag.View(Test.class);
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(tag1, 5);
        // Appelle une méthode
        assertDoesNotThrow(() -> handler.updateTag(tag2, value -> new Test(value.coin + 1)));
        // Appelle une méthode
        assertEquals(6, handler.getTag(tag1));
        // Appelle une méthode
        assertEquals(new Test(6), handler.getTag(tag2));

        // Appelle une méthode
        handler.updateTag(tag2, value -> null);
        // Appelle une méthode
        assertNull(handler.getTag(tag1));
        // Appelle une méthode
        assertNull(handler.getTag(tag2));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void updateIncompatible() {
        // Appelle une méthode
        var tagI = Tag.Integer("coin");
        // Appelle une méthode
        var tagD = Tag.Double("coin");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(tagI, 5);
        // Appelle une méthode
        assertThrows(ClassCastException.class, () -> handler.updateTag(tagD, value -> 5d));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void updateInner() {
        // Appelle une méthode
        var tag = Tag.Structure("vec", Vec.class);
        // Appelle une méthode
        var tagX = Tag.Double("x").path("vec");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(tag, new Vec(5, 10, 15));
        // Début d'une méthode/d'un bloc
        handler.updateTag(tagX, x -> {
            // Appelle une méthode
            assertEquals(5, x);
            // Renvoie une valeur à l'appelant
            return 7d;
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assertEquals(7d, handler.getTag(tagX));
        // Appelle une méthode
        assertEquals(new Vec(7, 10, 15), handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
