// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNotEquals;

// Type declaration (class/interface/enum/record)
public class TagEqualityTest {

    // Annotation for the following element
    @SuppressWarnings("EqualsWithItself")
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sameType() {
        // Calls a method
        var tag1 = Tag.Integer("key");
        // Calls a method
        var tag2 = Tag.Integer("key");
        // Calls a method
        assertEquals(tag1, tag1);
        // Calls a method
        assertEquals(tag2, tag2);
        // Calls a method
        assertEquals(tag1, tag2);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void differentKey() {
        // Calls a method
        var tag1 = Tag.Integer("key1");
        // Calls a method
        var tag2 = Tag.Integer("key2");
        // Calls a method
        assertNotEquals(tag1, tag2);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sameList() {
        // Calls a method
        var tag1 = Tag.Integer("key").list();
        // Calls a method
        var tag2 = Tag.Integer("key").list();
        // Calls a method
        assertEquals(tag1, tag2);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void differentList() {
        // Calls a method
        var tag1 = Tag.Integer("key").list();
        // Calls a method
        var tag2 = Tag.Integer("key");
        // Calls a method
        assertNotEquals(tag1, tag2);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void unmatchedList() {
        // Calls a method
        var tag1 = Tag.Integer("key").list().list();
        // Calls a method
        var tag2 = Tag.Integer("key").list();
        // Calls a method
        assertNotEquals(tag1, tag2);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void samePath() {
        // Calls a method
        var tag1 = Tag.Integer("key").path("path");
        // Calls a method
        var tag2 = Tag.Integer("key").path("path");
        // Calls a method
        assertEquals(tag1, tag2);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void differentPath() {
        // Calls a method
        var tag1 = Tag.Integer("key").path("path");
        // Calls a method
        var tag2 = Tag.Integer("key").path("path2");
        // Calls a method
        assertNotEquals(tag1, tag2);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void unmatchedPath() {
        // Calls a method
        var tag1 = Tag.Integer("key").path("path", "path2");
        // Calls a method
        var tag2 = Tag.Integer("key").path("path");
        // Calls a method
        assertNotEquals(tag1, tag2);
    // End of a block/expression
    }
// End of a block/expression
}
