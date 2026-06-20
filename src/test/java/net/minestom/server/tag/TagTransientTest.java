// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNull;

// Type declaration (class/interface/enum/record)
public class TagTransientTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void twoTransientTags() {
        // Calls a method
        var tagHandler = TagHandler.newHandler();
        // Calls a method
        Tag<String> tag1 = Tag.Transient("a");
        // Calls a method
        Tag<String> tag2 = Tag.Transient("b");

        // Calls a method
        tagHandler.setTag(tag1, "abcdef");
        // Calls a method
        var result = tagHandler.getTag(tag2);
        // Calls a method
        assertNull(result);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void twoTransientTagsEqual() {
        // Calls a method
        var tagHandler = TagHandler.newHandler();
        // Calls a method
        Tag<String> tag1 = Tag.Transient("a");
        // Calls a method
        Tag<String> tag2 = Tag.Transient("a");

        // Calls a method
        tagHandler.setTag(tag1, "abcdef");
        // Calls a method
        var result = tagHandler.getTag(tag2);
        // Calls a method
        assertEquals("abcdef", result);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void tagHandlerCopyPreservesTransient() {
        // Calls a method
        var tagHandler = TagHandler.newHandler();
        // Calls a method
        Tag<String> tag = Tag.Transient("a");
        // Calls a method
        tagHandler.setTag(tag, "abcdef");

        // Calls a method
        var copyHandler = tagHandler.copy();
        // Calls a method
        var result = copyHandler.getTag(tag);
        // Calls a method
        assertEquals("abcdef", result);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void asCompoundDoesNotPreserveTransient() {
        // Calls a method
        var tagHandler = TagHandler.newHandler();
        // Calls a method
        Tag<String> tag = Tag.Transient("a");
        // Calls a method
        tagHandler.setTag(tag, "abcdef");

        // Calls a method
        var compound = tagHandler.asCompound();
        // Calls a method
        assertNull(compound.get("a"));
    // End of a block/expression
    }

// End of a block/expression
}
