// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertSame;

// Type declaration (class/interface/enum/record)
public class TagHandlerReadableCopyTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void copyCache() {
        // Calls a method
        var tag = Tag.String("key");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag, "test");

        // Calls a method
        var copy = handler.readableCopy();
        // Calls a method
        assertEquals(handler.getTag(tag), copy.getTag(tag));

        // Calls a method
        handler.setTag(tag, "test2");
        // Calls a method
        assertEquals("test2", handler.getTag(tag));
        // Calls a method
        assertEquals("test", copy.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void copyCachePath() {
        // Calls a method
        var tag = Tag.String("key").path("path");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag, "test");
        // Code statement
        assertEqualsSNBT("""
                {"path":{"key":"test"}}
                """, handler.asCompound());

        // Calls a method
        var copy = handler.readableCopy();
        // Calls a method
        handler.setTag(tag, "test2");
        // Calls a method
        assertEquals("test2", handler.getTag(tag));
        // Calls a method
        assertEquals("test", copy.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void copyCacheReuse() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(Tag.String("key"), "test");
        // Calls a method
        assertSame(handler.readableCopy(), handler.readableCopy());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void copyRehashing() {
        // Calls a method
        var tag = Tag.String("key");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag, "test");
        // Calls a method
        var copy = handler.readableCopy();
        // Loop: repeats a block
        for (int i = 0; i < 1000; i++) {
            // Calls a method
            handler.setTag(Tag.Integer("copyRehashing" + i), i);
        // End of a block/expression
        }
        // Calls a method
        assertEquals("test", handler.getTag(tag));
        // Calls a method
        assertEquals("test", copy.getTag(tag));

        // Calls a method
        handler.setTag(tag, "test2");
        // Calls a method
        assertEquals("test2", handler.getTag(tag));
        // Calls a method
        assertEquals("test", copy.getTag(tag));
    // End of a block/expression
    }
// End of a block/expression
}
