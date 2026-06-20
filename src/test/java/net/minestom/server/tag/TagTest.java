// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.io.IOException;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class TagTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void intGet() {
        // Appelle une méthode
        var mutable = CompoundBinaryTag.builder().putInt("key", 5);
        // Appelle une méthode
        var tag = Tag.Integer("key");
        // Appelle une méthode
        var handler = TagHandler.fromCompound(CompoundBinaryTag.empty());
        // Appelle une méthode
        handler.setTag(tag, 5);
        // Appelle une méthode
        assertEquals(5, handler.getTag(tag));
        // Appelle une méthode
        assertEquals(mutable.build(), handler.asCompound(), "NBT is not the same");

        // Removal
        // Appelle une méthode
        handler.setTag(tag, null);
        // Appelle une méthode
        assertEquals(CompoundBinaryTag.empty(), handler.asCompound(), "Tag must be removed when set to null");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void intNull() {
        // Appelle une méthode
        var handler = TagHandler.fromCompound(CompoundBinaryTag.builder().putInt("key", 5).build());
        // Removal
        // Appelle une méthode
        var tag = Tag.Integer("key");
        // Appelle une méthode
        handler.setTag(tag, null);
        // Appelle une méthode
        assertFalse(handler.hasTag(tag));
        // Appelle une méthode
        assertEquals(CompoundBinaryTag.empty(), handler.asCompound(), "Tag must be removed when set to null");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void intRemove() {
        // Appelle une méthode
        var handler = TagHandler.fromCompound(CompoundBinaryTag.builder().putInt("key", 5).build());
        // Removal
        // Appelle une méthode
        var tag = Tag.Integer("key");
        // Appelle une méthode
        handler.removeTag(tag);
        // Appelle une méthode
        assertFalse(handler.hasTag(tag));
        // Appelle une méthode
        assertEquals(CompoundBinaryTag.empty(), handler.asCompound(), "Tag must be removed when set to null");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void getAndSet() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Integer("key");
        // Appelle une méthode
        assertNull(handler.getTag(tag));
        // Appelle une méthode
        assertNull(handler.getAndSetTag(tag, 5));
        // Appelle une méthode
        assertEquals(5, handler.getAndSetTag(tag, 6));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void snbt() throws IOException {
        // Appelle une méthode
        var compound = CompoundBinaryTag.builder().putInt("key", 5).build();
        // Appelle une méthode
        var reader = TagHandler.fromCompound(compound);
        // Appelle une méthode
        assertEquals(MinestomAdventure.tagStringIO().asString(reader.asCompound()), MinestomAdventure.tagStringIO().asString(compound), "SNBT is not the same");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void fromNbt() {
        // Appelle une méthode
        var compound = CompoundBinaryTag.builder().putInt("key", 5).build();
        // Appelle une méthode
        var handler = TagHandler.fromCompound(compound);
        // Appelle une méthode
        assertEquals(5, handler.getTag(Tag.Integer("key")));
        // Appelle une méthode
        assertEquals(compound, handler.asCompound(), "NBT is not the same");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void fromNbtCache() {
        // Ensure that TagHandler#asCompound reuse the same compound used for construction
        // Appelle une méthode
        var compound = CompoundBinaryTag.builder().putInt("key", 5).build();
        // Appelle une méthode
        var handler = TagHandler.fromCompound(compound);
        // Appelle une méthode
        assertSame(compound, handler.asCompound(), "NBT is not the same");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void defaultValue() {
        // Appelle une méthode
        var nullable = Tag.String("key");
        // Appelle une méthode
        var notNull = nullable.defaultValue("Hey");
        // Appelle une méthode
        assertNotSame(nullable, notNull);

        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        assertFalse(handler.hasTag(nullable));
        // Instruction de code
        assertTrue(handler.hasTag(notNull)); // default value is set
        // Appelle une méthode
        assertFalse(handler.hasTag(nullable));

        // Appelle une méthode
        assertNull(handler.getTag(nullable));
        // Appelle une méthode
        assertEquals("Hey", handler.getTag(notNull));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void invalidType() {
        // Appelle une méthode
        var tag1 = Tag.Integer("key");
        // Appelle une méthode
        var tag2 = Tag.String("key");

        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(tag1, 5);
        // Appelle une méthode
        assertEquals(5, handler.getTag(tag1));

        // Appelle une méthode
        assertNull(handler.getTag(tag2));
        // Appelle une méthode
        assertEquals("hey", handler.getTag(tag2.defaultValue("hey")));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void item() {
        // Appelle une méthode
        var item = ItemStack.of(Material.DIAMOND);
        // Appelle une méthode
        var tag = Tag.ItemStack("item");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(tag, item);
        // Appelle une méthode
        assertEquals(item, handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void tagResizing() {
        // Appelle une méthode
        var tag1 = Tag.Integer("tag1");
        // Appelle une méthode
        var tag2 = Tag.Integer("tag2");
        // Appelle une méthode
        var handler = TagHandler.newHandler();

        // Appelle une méthode
        handler.setTag(tag1, 5);
        // Appelle une méthode
        handler.setTag(tag2, 1);

        // Appelle une méthode
        assertEquals(5, handler.getTag(tag1));
        // Appelle une méthode
        assertEquals(1, handler.getTag(tag2));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void nbtResizing() {
        // Affecte une valeur
        var handler = TagHandler.fromCompound(CompoundBinaryTag.builder()
                // Instruction de code
                .putInt("tag1", 5)
                // Instruction de code
                .putInt("tag2", 1)
                // Appelle une méthode
                .build());

        // Appelle une méthode
        assertEquals(5, handler.getTag(Tag.Integer("tag1")));
        // Appelle une méthode
        assertEquals(1, handler.getTag(Tag.Integer("tag2")));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void rehashing() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Boucle : répète un bloc
        for (int i = 0; i < 1000; i++) {
            // Appelle une méthode
            handler.setTag(Tag.Integer("rehashing" + i), i);
            // Boucle : répète un bloc
            for (int j = i; j > 0; j--) {
                // Appelle une méthode
                assertEquals(j, handler.getTag(Tag.Integer("rehashing" + j)));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
