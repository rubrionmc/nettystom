// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class TagPathTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void basic() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Integer("number");
        // Appelle une méthode
        var path = tag.path("display");
        // Appelle une méthode
        handler.setTag(path, 5);
        // Appelle une méthode
        assertEquals(5, handler.getTag(path));
        // Appelle une méthode
        assertNull(handler.getTag(tag));

        // Appelle une méthode
        handler.setTag(path, 6);
        // Appelle une méthode
        assertEquals(6, handler.getTag(path));
        // Appelle une méthode
        assertNull(handler.getTag(tag));

        // Appelle une méthode
        handler.removeTag(path);
        // Appelle une méthode
        assertNull(handler.getTag(path));
        // Appelle une méthode
        assertNull(handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void invalidPath() {
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> Tag.Integer("number").path(""));
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> Tag.Integer("number").path("path", null));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void emptyRemoval() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Integer("number").path("display");
        // Appelle une méthode
        handler.removeTag(tag);
        // Appelle une méthode
        assertNull(handler.getTag(tag));
        // Appelle une méthode
        assertEqualsSNBT("{}", handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void snbt() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Integer("number").path("display");
        // Appelle une méthode
        handler.setTag(tag, 5);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "display": {
                    "number":5
                  }
                }
                """, handler.asCompound());

        // Appelle une méthode
        handler.removeTag(tag);
        // Appelle une méthode
        assertEqualsSNBT("{}", handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void doubleSnbt() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Integer("number").path("display");
        // Appelle une méthode
        var tag1 = Tag.String("string").path("display");
        // Appelle une méthode
        handler.setTag(tag, 5);
        // Appelle une méthode
        handler.setTag(tag1, "test");

        // Instruction de code
        assertEqualsSNBT("""
                {
                  "display": {
                    "string":"test",
                    "number":5
                  }
                }
                """, handler.asCompound());

        // Appelle une méthode
        handler.removeTag(tag);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "display": {
                    "string":"test"
                  }
                }
                """, handler.asCompound());

        // Appelle une méthode
        handler.removeTag(tag1);
        // Appelle une méthode
        assertEqualsSNBT("{}", handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void secondPathClearSnbt() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var numberTag = Tag.Integer("number").path("path1", "path2");
        // Appelle une méthode
        var stringTag = Tag.String("string").path("path1");
        // Appelle une méthode
        handler.setTag(numberTag, 5);
        // Appelle une méthode
        handler.setTag(stringTag, "test");
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "path1": {
                    "path2": {
                      "number":5
                    },
                    "string":"test"
                  }
                }
                """, handler.asCompound());

        // Appelle une méthode
        handler.removeTag(numberTag);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "path1": {
                    "string":"test"
                  }
                }
                """, handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void differentPath() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Integer("number");
        // Appelle une méthode
        var path = tag.path("display");
        // Appelle une méthode
        handler.setTag(tag, 5);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "number":5
                }
                """, handler.asCompound());

        // Appelle une méthode
        handler.setTag(path, 5);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "number":5,
                  "display": {
                    "number":5
                  }
                }
                """, handler.asCompound());

        // Appelle une méthode
        handler.removeTag(tag);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "display": {
                    "number":5
                  }
                }
                """, handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void overrideSnbt() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Integer("key");
        // Appelle une méthode
        var tag1 = Tag.Integer("value").path("key");
        // Appelle une méthode
        handler.setTag(tag, 5);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "key":5
                }
                """, handler.asCompound());

        // Appelle une méthode
        handler.setTag(tag1, 2);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "key": {
                    "value":2
                  }
                }
                """, handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void forgetPath() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Integer("key");
        // Appelle une méthode
        var path = Tag.Integer("value").path("key");
        // Appelle une méthode
        handler.setTag(path, 5);
        // Appelle une méthode
        assertNull(handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void pathInvalidClear() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag1 = Tag.Integer("pathInvalidClear1").path("key");
        // Appelle une méthode
        var tag2 = Tag.Integer("pathInvalidClear2").path("key");
        // Appelle une méthode
        handler.setTag(tag1, 5);
        // Appelle une méthode
        handler.setTag(tag2, null);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chaining() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Integer("key");
        // Appelle une méthode
        var path = Tag.Integer("key").path("first", "second");
        // Appelle une méthode
        handler.setTag(path, 5);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "first": {
                    "second": {
                      "key":5
                    }
                  }
                }
                """, handler.asCompound());

        // Appelle une méthode
        assertEquals(5, handler.getTag(path));
        // Appelle une méthode
        assertNull(handler.getTag(tag));

        // Appelle une méthode
        handler.removeTag(path);
        // Appelle une méthode
        assertEqualsSNBT("{}", handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chainingDouble() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var path = Tag.Integer("key").path("first", "second");
        // Appelle une méthode
        var path1 = Tag.Integer("key").path("first");
        // Appelle une méthode
        handler.setTag(path, 5);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "first": {
                    "second": {
                      "key":5
                    }
                  }
                }
                """, handler.asCompound());
        // Appelle une méthode
        assertEquals(5, handler.getTag(path));

        // Appelle une méthode
        handler.setTag(path1, 5);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "first": {
                    "key":5,
                    "second": {
                      "key":5
                    }
                  }
                }
                """, handler.asCompound());
        // Appelle une méthode
        assertEquals(5, handler.getTag(path));
        // Appelle une méthode
        assertEquals(5, handler.getTag(path1));

        // Appelle une méthode
        handler.removeTag(path);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "first": {
                    "key":5
                  }
                }
                """, handler.asCompound());

        // Appelle une méthode
        handler.removeTag(path1);
        // Appelle une méthode
        assertEqualsSNBT("{}", handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void structureObstruction() {
        // Déclaration de type (classe/interface/enum/record)
        record Entry(int value) {
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Integer("value");
        // Affecte une valeur
        var struct = Tag.Structure("struct", new TagSerializer<Entry>() {
            // Appelle une méthode
            private static final Tag<Integer> VALUE_TAG = Tag.Integer("value");

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public @Nullable Entry read(TagReadable reader) {
                // Appelle une méthode
                final Integer value = reader.getTag(VALUE_TAG);
                // Renvoie une valeur à l'appelant
                return value != null ? new Entry(value) : null;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(TagWritable writer, Entry value) {
                // Appelle une méthode
                writer.setTag(VALUE_TAG, value.value);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });

        // Appelle une méthode
        handler.setTag(struct, new Entry(5));
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "struct": {
                    "value":5
                  }
                }
                """, handler.asCompound());
        // Appelle une méthode
        assertEquals(5, handler.getTag(tag.path("struct")));

        // Appelle une méthode
        handler.setTag(tag, 5);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  value:5,
                  "struct": {
                    "value":5
                  }
                }
                """, handler.asCompound());

        // Appelle une méthode
        handler.setTag(tag.path("struct"), 2);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  value:5,
                  "struct": {
                    "value":2
                  }
                }
                """, handler.asCompound());
        // Appelle une méthode
        assertEquals(new Entry(2), handler.getTag(struct));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void tagObstruction() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Integer("key");
        // Appelle une méthode
        var path = Tag.Integer("value").path("key", "second");
        // Appelle une méthode
        handler.setTag(tag, 5);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "key":5
                }
                """, handler.asCompound());
        // Appelle une méthode
        handler.setTag(path, 2);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "key": {
                    "second": {
                      "value":2
                      }
                    }
                }
                """, handler.asCompound());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
