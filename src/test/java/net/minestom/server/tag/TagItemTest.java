// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.lang.ref.WeakReference;

// Import statique d'un membre
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Import statique d'un membre
import static net.minestom.testing.TestUtils.waitUntilCleared;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNull;

// Déclaration de type (classe/interface/enum/record)
public class TagItemTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void get() {
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
    public void getDifferentObject() {
        // Appelle une méthode
        var item = ItemStack.of(Material.DIAMOND);
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(Tag.ItemStack("item"), item);

        // Appelle une méthode
        assertEquals(item, handler.getTag(Tag.ItemStack("item")));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void remove() {
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

        // Appelle une méthode
        handler.setTag(tag, null);
        // Appelle une méthode
        assertNull(handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void gc() {
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
        // Appelle une méthode
        handler.setTag(tag, null);

        // Appelle une méthode
        var ref = new WeakReference<>(item);
        //noinspection UnusedAssignment
        // Affecte une valeur
        item = null;
        // Appelle une méthode
        waitUntilCleared(ref);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void invalidation() {
        // Appelle une méthode
        var item = ItemStack.of(Material.DIAMOND);
        // Appelle une méthode
        var item2 = ItemStack.of(Material.DIAMOND, 2);
        // Appelle une méthode
        var handler = TagHandler.newHandler();

        // Appelle une méthode
        var tag = Tag.ItemStack("item");
        // Appelle une méthode
        handler.setTag(tag, item);
        // Appelle une méthode
        assertEquals(item, handler.getTag(tag));
        // Appelle une méthode
        handler.setTag(tag, item2);
        // Appelle une méthode
        assertEquals(item2, handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void differentTagInvalidation() {
        // Appelle une méthode
        var item = ItemStack.of(Material.DIAMOND);
        // Appelle une méthode
        var item2 = ItemStack.of(Material.DIAMOND, 2);
        // Appelle une méthode
        var handler = TagHandler.newHandler();

        // Appelle une méthode
        var itemTag = Tag.ItemStack("item");
        // Appelle une méthode
        var nbtTag = Tag.NBT("item");
        // Write the item using the ItemStack tag
        // Début d'un bloc
        {
            // Appelle une méthode
            handler.setTag(itemTag, item);
            // Appelle une méthode
            assertEquals(item, handler.getTag(itemTag));
            // Appelle une méthode
            assertEquals(item.toItemNBT(), handler.getTag(nbtTag));
        // Fin d'un bloc/d'une expression
        }
        // Override it with an NBT tag
        // Début d'un bloc
        {
            // Appelle une méthode
            handler.setTag(nbtTag, item2.toItemNBT());
            // Appelle une méthode
            assertEquals(item2, handler.getTag(itemTag));
            // Appelle une méthode
            assertEquals(item2.toItemNBT(), handler.getTag(nbtTag));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void snbt() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.ItemStack("item");
        // Appelle une méthode
        handler.setTag(tag, ItemStack.of(Material.DIAMOND));
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "item": {
                    "id":"minecraft:diamond",
                    "count":1
                  }
                }
                """, handler.asCompound());
        // Appelle une méthode
        handler.removeTag(tag);
        // Appelle une méthode
        assertEqualsSNBT("{}", handler.asCompound());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
