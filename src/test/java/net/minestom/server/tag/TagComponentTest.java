// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNull;

// Type declaration (class/interface/enum/record)
public class TagComponentTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void get() {
        // Calls a method
        var component = Component.text("Hey");
        // Calls a method
        var tag = Tag.Component("component");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag, component);
        // Calls a method
        assertEquals(component, handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void empty() {
        // Calls a method
        var tag = Tag.Component("component");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        assertNull(handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void invalidTag() {
        // Calls a method
        var tag = Tag.Component("entry");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(Tag.Integer("entry"), 1);
        // Calls a method
        assertNull(handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void nbtFallback() {
        // Calls a method
        var component = Component.text("Hey");
        // Calls a method
        var tag = Tag.Component("component");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag, component);
        // Calls a method
        handler = TagHandler.fromCompound(handler.asCompound());
        // Calls a method
        assertEquals(component, handler.getTag(tag));
    // End of a block/expression
    }
// End of a block/expression
}
