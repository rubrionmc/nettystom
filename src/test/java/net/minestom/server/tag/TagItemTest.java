// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.lang.ref.WeakReference;

// Static import of a member
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Static import of a member
import static net.minestom.testing.TestUtils.waitUntilCleared;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNull;

// Type declaration (class/interface/enum/record)
public class TagItemTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void get() {
        // Calls a method
        var item = ItemStack.of(Material.DIAMOND);
        // Calls a method
        var tag = Tag.ItemStack("item");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag, item);

        // Calls a method
        assertEquals(item, handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void getDifferentObject() {
        // Calls a method
        var item = ItemStack.of(Material.DIAMOND);
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(Tag.ItemStack("item"), item);

        // Calls a method
        assertEquals(item, handler.getTag(Tag.ItemStack("item")));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void remove() {
        // Calls a method
        var item = ItemStack.of(Material.DIAMOND);
        // Calls a method
        var tag = Tag.ItemStack("item");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag, item);
        // Calls a method
        assertEquals(item, handler.getTag(tag));

        // Calls a method
        handler.setTag(tag, null);
        // Calls a method
        assertNull(handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void gc() {
        // Calls a method
        var item = ItemStack.of(Material.DIAMOND);
        // Calls a method
        var tag = Tag.ItemStack("item");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag, item);
        // Calls a method
        assertEquals(item, handler.getTag(tag));
        // Calls a method
        handler.setTag(tag, null);

        // Calls a method
        var ref = new WeakReference<>(item);
        //noinspection UnusedAssignment
        // Assigns a value
        item = null;
        // Calls a method
        waitUntilCleared(ref);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void invalidation() {
        // Calls a method
        var item = ItemStack.of(Material.DIAMOND);
        // Calls a method
        var item2 = ItemStack.of(Material.DIAMOND, 2);
        // Calls a method
        var handler = TagHandler.newHandler();

        // Calls a method
        var tag = Tag.ItemStack("item");
        // Calls a method
        handler.setTag(tag, item);
        // Calls a method
        assertEquals(item, handler.getTag(tag));
        // Calls a method
        handler.setTag(tag, item2);
        // Calls a method
        assertEquals(item2, handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void differentTagInvalidation() {
        // Calls a method
        var item = ItemStack.of(Material.DIAMOND);
        // Calls a method
        var item2 = ItemStack.of(Material.DIAMOND, 2);
        // Calls a method
        var handler = TagHandler.newHandler();

        // Calls a method
        var itemTag = Tag.ItemStack("item");
        // Calls a method
        var nbtTag = Tag.NBT("item");
        // Write the item using the ItemStack tag
        // Start of a block
        {
            // Calls a method
            handler.setTag(itemTag, item);
            // Calls a method
            assertEquals(item, handler.getTag(itemTag));
            // Calls a method
            assertEquals(item.toItemNBT(), handler.getTag(nbtTag));
        // End of a block/expression
        }
        // Override it with an NBT tag
        // Start of a block
        {
            // Calls a method
            handler.setTag(nbtTag, item2.toItemNBT());
            // Calls a method
            assertEquals(item2, handler.getTag(itemTag));
            // Calls a method
            assertEquals(item2.toItemNBT(), handler.getTag(nbtTag));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void snbt() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.ItemStack("item");
        // Calls a method
        handler.setTag(tag, ItemStack.of(Material.DIAMOND));
        // Code statement
        assertEqualsSNBT("""
                {
                  "item": {
                    "id":"minecraft:diamond",
                    "count":1
                  }
                }
                """, handler.asCompound());
        // Calls a method
        handler.removeTag(tag);
        // Calls a method
        assertEqualsSNBT("{}", handler.asCompound());
    // End of a block/expression
    }
// End of a block/expression
}
