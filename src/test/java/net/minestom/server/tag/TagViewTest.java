// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class TagViewTest {

    // Assigns a value
    private static final Tag<Entry> VIEW_TAG = Tag.View(new TagSerializer<>() {
        // Calls a method
        private static final Tag<String> VALUE_TAG = Tag.String("value");

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @Nullable Entry read(TagReadable reader) {
            // Calls a method
            final String value = reader.getTag(VALUE_TAG);
            // Returns a value to the caller
            return value != null ? new Entry(value) : null;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(TagWritable writer, Entry value) {
            // Calls a method
            writer.setTag(VALUE_TAG, value.value);
        // End of a block/expression
        }
    // End of a block/expression
    });

    // Type declaration (class/interface/enum/record)
    private record Entry(String value) {
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void basic() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        assertNull(handler.getTag(VIEW_TAG));
        // Calls a method
        assertFalse(handler.hasTag(VIEW_TAG));

        // Calls a method
        var entry = new Entry("hello");
        // Calls a method
        handler.setTag(VIEW_TAG, entry);
        // Calls a method
        assertTrue(handler.hasTag(VIEW_TAG));
        // Calls a method
        assertEquals(entry, handler.getTag(VIEW_TAG));

        // Calls a method
        handler.removeTag(VIEW_TAG);
        // Calls a method
        assertFalse(handler.hasTag(VIEW_TAG));
        // Calls a method
        assertNull(handler.getTag(VIEW_TAG));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void snbt() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var entry = new Entry("hello");
        // Calls a method
        handler.setTag(VIEW_TAG, entry);
        // Code statement
        assertEqualsSNBT("""
                {
                  "value":"hello"
                }
                """, handler.asCompound());

        // Calls a method
        handler.removeTag(VIEW_TAG);
        // Calls a method
        assertEqualsSNBT("{}", handler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void snbtOverride() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var entry = new Entry("hello");
        // Calls a method
        handler.setTag(VIEW_TAG, entry);
        // Code statement
        assertEqualsSNBT("""
                {
                  "value":"hello"
                }
                """, handler.asCompound());

        // Calls a method
        handler.setTag(Tag.Integer("value"), 5);
        // Code statement
        assertEqualsSNBT("""
                {
                  "value":5,
                }
                """, handler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void empty() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Assigns a value
        var tag = Tag.View(new TagSerializer<Entry>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public @Nullable Entry read(TagReadable reader) {
                // Empty
                // Returns a value to the caller
                return null;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public void write(TagWritable writer, Entry value) {
                // Empty
            // End of a block/expression
            }
        // End of a block/expression
        });
        // Calls a method
        assertNull(handler.getTag(tag));
        // Calls a method
        assertFalse(handler.hasTag(tag));

        // Calls a method
        var entry = new Entry("hello");
        // Calls a method
        handler.setTag(tag, entry);
        // Calls a method
        assertNull(handler.getTag(tag));
        // Calls a method
        assertFalse(handler.hasTag(tag));
        // Calls a method
        assertEqualsSNBT("{}", handler.asCompound());

        // Calls a method
        handler.removeTag(tag);
        // Calls a method
        assertFalse(handler.hasTag(tag));
        // Calls a method
        assertNull(handler.getTag(VIEW_TAG));
        // Calls a method
        assertEqualsSNBT("{}", handler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void path() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = VIEW_TAG.path("path");
        // Calls a method
        assertNull(handler.getTag(tag));
        // Calls a method
        assertFalse(handler.hasTag(tag));

        // Calls a method
        var entry = new Entry("hello");
        // Calls a method
        handler.setTag(tag, entry);
        // Calls a method
        assertTrue(handler.hasTag(tag));
        // Calls a method
        assertEquals(entry, handler.getTag(tag));

        // Calls a method
        handler.removeTag(tag);
        // Calls a method
        assertFalse(handler.hasTag(tag));
        // Calls a method
        assertNull(handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void pathSnbt() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = VIEW_TAG.path("path");
        // Calls a method
        var entry = new Entry("hello");
        // Calls a method
        handler.setTag(tag, entry);
        // Code statement
        assertEqualsSNBT("""
                {
                  "path":{
                    "value":"hello"
                  }
                }
                """, handler.asCompound());

        // Calls a method
        handler.removeTag(tag);
        // Calls a method
        assertEqualsSNBT("{}", handler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void compoundSerializer() {
        // Calls a method
        var tag = Tag.View(TagSerializer.COMPOUND);
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag, CompoundBinaryTag.builder().putString("value", "hello").build());
        // Code statement
        assertEqualsSNBT("""
                {
                  "value":"hello"
                }
                """, handler.asCompound());

        // Calls a method
        handler.setTag(Tag.Integer("value"), 5);
        // Code statement
        assertEqualsSNBT("""
                {
                  "value":5,
                }
                """, handler.asCompound());

        // Calls a method
        handler.setTag(tag, CompoundBinaryTag.empty());
        // Calls a method
        assertEqualsSNBT("{}", handler.asCompound());

        // Calls a method
        handler.setTag(tag, null);
        // Calls a method
        assertEqualsSNBT("{}", handler.asCompound());
    // End of a block/expression
    }
// End of a block/expression
}
