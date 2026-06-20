// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.io.IOException;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class TagTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void intGet() {
        // Calls a method
        var mutable = CompoundBinaryTag.builder().putInt("key", 5);
        // Calls a method
        var tag = Tag.Integer("key");
        // Calls a method
        var handler = TagHandler.fromCompound(CompoundBinaryTag.empty());
        // Calls a method
        handler.setTag(tag, 5);
        // Calls a method
        assertEquals(5, handler.getTag(tag));
        // Calls a method
        assertEquals(mutable.build(), handler.asCompound(), "NBT is not the same");

        // Removal
        // Calls a method
        handler.setTag(tag, null);
        // Calls a method
        assertEquals(CompoundBinaryTag.empty(), handler.asCompound(), "Tag must be removed when set to null");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void intNull() {
        // Calls a method
        var handler = TagHandler.fromCompound(CompoundBinaryTag.builder().putInt("key", 5).build());
        // Removal
        // Calls a method
        var tag = Tag.Integer("key");
        // Calls a method
        handler.setTag(tag, null);
        // Calls a method
        assertFalse(handler.hasTag(tag));
        // Calls a method
        assertEquals(CompoundBinaryTag.empty(), handler.asCompound(), "Tag must be removed when set to null");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void intRemove() {
        // Calls a method
        var handler = TagHandler.fromCompound(CompoundBinaryTag.builder().putInt("key", 5).build());
        // Removal
        // Calls a method
        var tag = Tag.Integer("key");
        // Calls a method
        handler.removeTag(tag);
        // Calls a method
        assertFalse(handler.hasTag(tag));
        // Calls a method
        assertEquals(CompoundBinaryTag.empty(), handler.asCompound(), "Tag must be removed when set to null");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void getAndSet() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Integer("key");
        // Calls a method
        assertNull(handler.getTag(tag));
        // Calls a method
        assertNull(handler.getAndSetTag(tag, 5));
        // Calls a method
        assertEquals(5, handler.getAndSetTag(tag, 6));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void snbt() throws IOException {
        // Calls a method
        var compound = CompoundBinaryTag.builder().putInt("key", 5).build();
        // Calls a method
        var reader = TagHandler.fromCompound(compound);
        // Calls a method
        assertEquals(MinestomAdventure.tagStringIO().asString(reader.asCompound()), MinestomAdventure.tagStringIO().asString(compound), "SNBT is not the same");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void fromNbt() {
        // Calls a method
        var compound = CompoundBinaryTag.builder().putInt("key", 5).build();
        // Calls a method
        var handler = TagHandler.fromCompound(compound);
        // Calls a method
        assertEquals(5, handler.getTag(Tag.Integer("key")));
        // Calls a method
        assertEquals(compound, handler.asCompound(), "NBT is not the same");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void fromNbtCache() {
        // Ensure that TagHandler#asCompound reuse the same compound used for construction
        // Calls a method
        var compound = CompoundBinaryTag.builder().putInt("key", 5).build();
        // Calls a method
        var handler = TagHandler.fromCompound(compound);
        // Calls a method
        assertSame(compound, handler.asCompound(), "NBT is not the same");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void defaultValue() {
        // Calls a method
        var nullable = Tag.String("key");
        // Calls a method
        var notNull = nullable.defaultValue("Hey");
        // Calls a method
        assertNotSame(nullable, notNull);

        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        assertFalse(handler.hasTag(nullable));
        // Code statement
        assertTrue(handler.hasTag(notNull)); // default value is set
        // Calls a method
        assertFalse(handler.hasTag(nullable));

        // Calls a method
        assertNull(handler.getTag(nullable));
        // Calls a method
        assertEquals("Hey", handler.getTag(notNull));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void invalidType() {
        // Calls a method
        var tag1 = Tag.Integer("key");
        // Calls a method
        var tag2 = Tag.String("key");

        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag1, 5);
        // Calls a method
        assertEquals(5, handler.getTag(tag1));

        // Calls a method
        assertNull(handler.getTag(tag2));
        // Calls a method
        assertEquals("hey", handler.getTag(tag2.defaultValue("hey")));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void item() {
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
    public void tagResizing() {
        // Calls a method
        var tag1 = Tag.Integer("tag1");
        // Calls a method
        var tag2 = Tag.Integer("tag2");
        // Calls a method
        var handler = TagHandler.newHandler();

        // Calls a method
        handler.setTag(tag1, 5);
        // Calls a method
        handler.setTag(tag2, 1);

        // Calls a method
        assertEquals(5, handler.getTag(tag1));
        // Calls a method
        assertEquals(1, handler.getTag(tag2));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void nbtResizing() {
        // Assigns a value
        var handler = TagHandler.fromCompound(CompoundBinaryTag.builder()
                // Code statement
                .putInt("tag1", 5)
                // Code statement
                .putInt("tag2", 1)
                // Calls a method
                .build());

        // Calls a method
        assertEquals(5, handler.getTag(Tag.Integer("tag1")));
        // Calls a method
        assertEquals(1, handler.getTag(Tag.Integer("tag2")));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void rehashing() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Loop: repeats a block
        for (int i = 0; i < 1000; i++) {
            // Calls a method
            handler.setTag(Tag.Integer("rehashing" + i), i);
            // Loop: repeats a block
            for (int j = i; j > 0; j--) {
                // Calls a method
                assertEquals(j, handler.getTag(Tag.Integer("rehashing" + j)));
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
