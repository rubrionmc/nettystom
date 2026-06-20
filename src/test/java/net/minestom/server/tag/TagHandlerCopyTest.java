// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNull;

// Type declaration (class/interface/enum/record)
public class TagHandlerCopyTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void copy() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(Tag.String("key"), "test");

        // Calls a method
        var copy = handler.copy();
        // Calls a method
        assertEquals(handler.getTag(Tag.String("key")), copy.getTag(Tag.String("key")));
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
        var copy = handler.copy();
        // Calls a method
        handler.setTag(tag, "test2");
        // Code statement
        assertEqualsSNBT("""
                {"path":{"key":"test2"}}
                """, handler.asCompound());
        // Code statement
        assertEqualsSNBT("""
                {"path":{"key":"test"}}
                """, copy.asCompound());

        // Calls a method
        copy.setTag(tag, "test3");
        // Calls a method
        assertEquals("test3", copy.getTag(tag));
        // Code statement
        assertEqualsSNBT("""
                {"path":{"key":"test3"}}
                """, copy.asCompound());
    // End of a block/expression
    }

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
        // Code statement
        assertEqualsSNBT("""
                {"key":"test"}
                """, handler.asCompound());

        // Calls a method
        var copy = handler.copy();
        // Calls a method
        handler.setTag(tag, "test2");
        // Code statement
        assertEqualsSNBT("""
                {"key":"test2"}
                """, handler.asCompound());
        // Code statement
        assertEqualsSNBT("""
                {"key":"test"}
                """, copy.asCompound());

        // Calls a method
        copy.setTag(tag, "test3");
        // Calls a method
        assertEquals("test3", copy.getTag(tag));
        // Code statement
        assertEqualsSNBT("""
                {"key":"test2"}
                """, handler.asCompound());
        // Code statement
        assertEqualsSNBT("""
                {"key":"test3"}
                """, copy.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void copyRehashing() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Code statement
        TagHandler handlerCopy;
        // Loop: repeats a block
        for (int i = 0; i < 1000; i++) {
            // Calls a method
            handlerCopy = handler.copy();
            // Calls a method
            var tag = Tag.Integer("copyRehashing" + i);
            // Calls a method
            handler.setTag(tag, i);
            // Calls a method
            assertNull(handlerCopy.getTag(tag));
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
