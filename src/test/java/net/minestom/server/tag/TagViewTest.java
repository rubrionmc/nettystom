// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class TagViewTest {

    // Affecte une valeur
    private static final Tag<Entry> VIEW_TAG = Tag.View(new TagSerializer<>() {
        // Appelle une méthode
        private static final Tag<String> VALUE_TAG = Tag.String("value");

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @Nullable Entry read(TagReadable reader) {
            // Appelle une méthode
            final String value = reader.getTag(VALUE_TAG);
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

    // Déclaration de type (classe/interface/enum/record)
    private record Entry(String value) {
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void basic() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        assertNull(handler.getTag(VIEW_TAG));
        // Appelle une méthode
        assertFalse(handler.hasTag(VIEW_TAG));

        // Appelle une méthode
        var entry = new Entry("hello");
        // Appelle une méthode
        handler.setTag(VIEW_TAG, entry);
        // Appelle une méthode
        assertTrue(handler.hasTag(VIEW_TAG));
        // Appelle une méthode
        assertEquals(entry, handler.getTag(VIEW_TAG));

        // Appelle une méthode
        handler.removeTag(VIEW_TAG);
        // Appelle une méthode
        assertFalse(handler.hasTag(VIEW_TAG));
        // Appelle une méthode
        assertNull(handler.getTag(VIEW_TAG));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void snbt() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var entry = new Entry("hello");
        // Appelle une méthode
        handler.setTag(VIEW_TAG, entry);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "value":"hello"
                }
                """, handler.asCompound());

        // Appelle une méthode
        handler.removeTag(VIEW_TAG);
        // Appelle une méthode
        assertEqualsSNBT("{}", handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void snbtOverride() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var entry = new Entry("hello");
        // Appelle une méthode
        handler.setTag(VIEW_TAG, entry);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "value":"hello"
                }
                """, handler.asCompound());

        // Appelle une méthode
        handler.setTag(Tag.Integer("value"), 5);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "value":5,
                }
                """, handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void empty() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Affecte une valeur
        var tag = Tag.View(new TagSerializer<Entry>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public @Nullable Entry read(TagReadable reader) {
                // Empty
                // Renvoie une valeur à l'appelant
                return null;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(TagWritable writer, Entry value) {
                // Empty
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assertNull(handler.getTag(tag));
        // Appelle une méthode
        assertFalse(handler.hasTag(tag));

        // Appelle une méthode
        var entry = new Entry("hello");
        // Appelle une méthode
        handler.setTag(tag, entry);
        // Appelle une méthode
        assertNull(handler.getTag(tag));
        // Appelle une méthode
        assertFalse(handler.hasTag(tag));
        // Appelle une méthode
        assertEqualsSNBT("{}", handler.asCompound());

        // Appelle une méthode
        handler.removeTag(tag);
        // Appelle une méthode
        assertFalse(handler.hasTag(tag));
        // Appelle une méthode
        assertNull(handler.getTag(VIEW_TAG));
        // Appelle une méthode
        assertEqualsSNBT("{}", handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void path() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = VIEW_TAG.path("path");
        // Appelle une méthode
        assertNull(handler.getTag(tag));
        // Appelle une méthode
        assertFalse(handler.hasTag(tag));

        // Appelle une méthode
        var entry = new Entry("hello");
        // Appelle une méthode
        handler.setTag(tag, entry);
        // Appelle une méthode
        assertTrue(handler.hasTag(tag));
        // Appelle une méthode
        assertEquals(entry, handler.getTag(tag));

        // Appelle une méthode
        handler.removeTag(tag);
        // Appelle une méthode
        assertFalse(handler.hasTag(tag));
        // Appelle une méthode
        assertNull(handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void pathSnbt() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = VIEW_TAG.path("path");
        // Appelle une méthode
        var entry = new Entry("hello");
        // Appelle une méthode
        handler.setTag(tag, entry);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "path":{
                    "value":"hello"
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
    public void compoundSerializer() {
        // Appelle une méthode
        var tag = Tag.View(TagSerializer.COMPOUND);
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(tag, CompoundBinaryTag.builder().putString("value", "hello").build());
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "value":"hello"
                }
                """, handler.asCompound());

        // Appelle une méthode
        handler.setTag(Tag.Integer("value"), 5);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "value":5,
                }
                """, handler.asCompound());

        // Appelle une méthode
        handler.setTag(tag, CompoundBinaryTag.empty());
        // Appelle une méthode
        assertEqualsSNBT("{}", handler.asCompound());

        // Appelle une méthode
        handler.setTag(tag, null);
        // Appelle une méthode
        assertEqualsSNBT("{}", handler.asCompound());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
