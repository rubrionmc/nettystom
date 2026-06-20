// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.function.Function;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertFalse;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test tags that can share cached values.
 */
// Type declaration (class/interface/enum/record)
public class TagValueShareTest {

    // Type declaration (class/interface/enum/record)
    record Entry(int value) {
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void same() {
        // Calls a method
        var tag = Tag.String("test");
        // Calls a method
        assertTrue(tag.shareValue(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void similar() {
        // Calls a method
        var tag = Tag.String("test");
        // Calls a method
        var tag2 = Tag.String("test");
        // Calls a method
        assertTrue(tag.shareValue(tag2));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void differentDefault() {
        // Calls a method
        var tag = Tag.String("test").defaultValue("test2");
        // Calls a method
        var tag2 = Tag.String("test").defaultValue("test3");
        // Calls a method
        assertTrue(tag.shareValue(tag2));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void differentType() {
        // Calls a method
        var tag = Tag.String("test");
        // Calls a method
        var tag2 = Tag.Integer("test");
        // Calls a method
        assertFalse(tag.shareValue(tag2));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void mapSame() {
        // Force identical functions
        // Assigns a value
        Function<Integer, Entry> t1 = Entry::new;
        // Assigns a value
        Function<Entry, Integer> t2 = Entry::value;

        // Calls a method
        var tag = Tag.Integer("key");
        // Calls a method
        var map1 = tag.map(t1, t2);
        // Calls a method
        var map2 = tag.map(t1, t2);
        // Calls a method
        assertTrue(map1.shareValue(map2));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void mapChild() {
        // Calls a method
        var intTag = Tag.Integer("key");
        // Calls a method
        var tag = intTag.map(Entry::new, Entry::value);
        // Calls a method
        assertFalse(intTag.shareValue(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void list() {
        // Calls a method
        var tag = Tag.String("test").list();
        // Calls a method
        assertTrue(tag.shareValue(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void listScope() {
        // Calls a method
        var tag = Tag.String("test");
        // Calls a method
        assertFalse(tag.shareValue(tag.list()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void similarList() {
        // Calls a method
        var tag = Tag.String("test").list();
        // Calls a method
        var tag2 = Tag.String("test").list();
        // Calls a method
        assertTrue(tag.shareValue(tag2));
        // Calls a method
        assertTrue(tag.list().shareValue(tag2.list()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void differentList() {
        // Calls a method
        var tag = Tag.String("test").list();
        // Calls a method
        var tag2 = Tag.String("test").list();
        // Calls a method
        assertFalse(tag.shareValue(tag2.list()));
        // Calls a method
        assertFalse(tag.list().shareValue(tag2.list().list()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void differentListType() {
        // Calls a method
        var tag = Tag.String("test").list();
        // Calls a method
        var tag2 = Tag.Integer("test").list();
        // Calls a method
        assertFalse(tag.shareValue(tag2));
        // Calls a method
        assertFalse(tag.list().shareValue(tag2.list()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void recordStructure() {
        // Calls a method
        var tag = Tag.Structure("test", Vec.class);
        // Calls a method
        var tag2 = Tag.Structure("test", Vec.class);
        // Calls a method
        assertTrue(tag.shareValue(tag2));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void recordStructureList() {
        // Calls a method
        var tag = Tag.Structure("test", Vec.class).list();
        // Calls a method
        var tag2 = Tag.Structure("test", Vec.class).list();
        // Calls a method
        assertTrue(tag.shareValue(tag2));
        // Calls a method
        assertTrue(tag.list().shareValue(tag2.list()));
    // End of a block/expression
    }
// End of a block/expression
}
