// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNull;

// Type declaration (class/interface/enum/record)
public class TagMapTest {

    // Type declaration (class/interface/enum/record)
    private record Entry(int value) {
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void map() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var intTag = Tag.Integer("key");
        // Calls a method
        var tag = intTag.map(Entry::new, Entry::value);

        // Calls a method
        handler.setTag(tag, new Entry(1));
        // Calls a method
        assertEquals(1, handler.getTag(intTag));
        // Calls a method
        assertEquals(new Entry(1), handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void mapDefault() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var intTag = Tag.Integer("key");
        // Calls a method
        var tag = intTag.map(Entry::new, Entry::value);

        // Calls a method
        assertEquals(new Entry(1), handler.getTag(tag.defaultValue(new Entry(1))));

        // Calls a method
        handler.setTag(tag, new Entry(2));
        // Calls a method
        assertEquals(2, handler.getTag(intTag));
        // Calls a method
        assertEquals(new Entry(2), handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void mapDefaultAbsent() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Integer("key").map(Entry::new, Entry::value);
        // Calls a method
        assertNull(handler.getTag(tag));
    // End of a block/expression
    }
// End of a block/expression
}
