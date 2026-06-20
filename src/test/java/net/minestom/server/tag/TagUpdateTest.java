// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class TagUpdateTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void update() {
        // Calls a method
        var tag = Tag.Integer("coin");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Start of a method/block
        handler.updateTag(tag, integer -> {
            // Calls a method
            assertNull(integer);
            // Returns a value to the caller
            return 5;
        // End of a block/expression
        });
        // Calls a method
        assertEquals(5, handler.getTag(tag));
        // Start of a method/block
        handler.updateTag(tag, integer -> {
            // Calls a method
            assertEquals(5, integer);
            // Returns a value to the caller
            return 10;
        // End of a block/expression
        });
        // Calls a method
        assertEquals(10, handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void updateDefault() {
        // Calls a method
        var tag = Tag.Integer("coin").defaultValue(25);
        // Calls a method
        var handler = TagHandler.newHandler();
        // Start of a method/block
        handler.updateTag(tag, integer -> {
            // Calls a method
            assertEquals(25, integer);
            // Returns a value to the caller
            return 5;
        // End of a block/expression
        });
        // Calls a method
        assertEquals(5, handler.getTag(tag));
        // Start of a method/block
        handler.updateTag(tag, integer -> {
            // Calls a method
            assertEquals(5, integer);
            // Returns a value to the caller
            return 10;
        // End of a block/expression
        });
        // Calls a method
        assertEquals(10, handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void updateRemoval() {
        // Calls a method
        var tag = Tag.Integer("coin");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag, 5);
        // Start of a method/block
        handler.updateTag(tag, integer -> {
            // Calls a method
            assertEquals(5, integer);
            // Returns a value to the caller
            return null;
        // End of a block/expression
        });
        // Calls a method
        assertNull(handler.getTag(tag));
        // Calls a method
        assertEqualsSNBT("{}", handler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void updateRemovalPath() {
        // Calls a method
        var tag = Tag.Integer("coin").path("path");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag, 5);
        // Start of a method/block
        handler.updateTag(tag, integer -> {
            // Calls a method
            assertEquals(5, integer);
            // Returns a value to the caller
            return null;
        // End of a block/expression
        });
        // Calls a method
        assertNull(handler.getTag(tag));
        // Calls a method
        assertEqualsSNBT("{}", handler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void updateAndGet() {
        // Calls a method
        var tag = Tag.Integer("coin");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Assigns a value
        var result = handler.updateAndGetTag(tag, integer -> {
            // Calls a method
            assertNull(integer);
            // Returns a value to the caller
            return 5;
        // End of a block/expression
        });
        // Calls a method
        assertEquals(5, result);
        // Assigns a value
        result = handler.updateAndGetTag(tag, integer -> {
            // Calls a method
            assertEquals(5, integer);
            // Returns a value to the caller
            return 10;
        // End of a block/expression
        });
        // Calls a method
        assertEquals(10, result);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void getAndUpdate() {
        // Calls a method
        var tag = Tag.Integer("coin");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Assigns a value
        var result = handler.getAndUpdateTag(tag, integer -> {
            // Calls a method
            assertNull(integer);
            // Returns a value to the caller
            return 5;
        // End of a block/expression
        });
        // Calls a method
        assertNull(result);
        // Assigns a value
        result = handler.getAndUpdateTag(tag, integer -> {
            // Calls a method
            assertEquals(5, integer);
            // Returns a value to the caller
            return 10;
        // End of a block/expression
        });
        // Calls a method
        assertEquals(5, result);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void updateHiddenSimilarity() {
        // Calls a method
        var tag1 = Tag.Integer("coin");
        // Calls a method
        var tag2 = Tag.Integer("coin").map(i -> i + 1, i -> i - 1);
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag1, 5);
        // Calls a method
        assertDoesNotThrow(() -> handler.updateTag(tag2, value -> 5));
        // Calls a method
        assertEquals(4, handler.getTag(tag1));
        // Calls a method
        assertEquals(5, handler.getTag(tag2));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void updateStructureConversion() {
        // Type declaration (class/interface/enum/record)
        record Test(int coin) {
        // End of a block/expression
        }

        // Calls a method
        var tag1 = Tag.Integer("coin").path("path");
        // Calls a method
        var tag2 = Tag.Structure("path", Test.class);
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag1, 5);
        // Calls a method
        assertEquals(5, handler.getTag(tag1));
        // Calls a method
        assertEquals(new Test(5), handler.getTag(tag2));

        // Calls a method
        assertDoesNotThrow(() -> handler.updateTag(tag2, value -> new Test(value.coin + 1)));
        // Calls a method
        assertEquals(6, handler.getTag(tag1));
        // Calls a method
        assertEquals(new Test(6), handler.getTag(tag2));

        // Calls a method
        handler.updateTag(tag2, value -> null);
        // Calls a method
        assertNull(handler.getTag(tag1));
        // Calls a method
        assertNull(handler.getTag(tag2));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void updateStructureConversionPath() {
        // Type declaration (class/interface/enum/record)
        record Test(int coin) {
        // End of a block/expression
        }

        // Calls a method
        var tag1 = Tag.Integer("coin").path("path", "path2");
        // Calls a method
        var tag2 = Tag.Structure("path2", Test.class).path("path");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag1, 5);
        // Calls a method
        assertEquals(5, handler.getTag(tag1));
        // Calls a method
        assertEquals(new Test(5), handler.getTag(tag2));

        // Calls a method
        assertDoesNotThrow(() -> handler.updateTag(tag2, value -> new Test(value.coin + 1)));
        // Calls a method
        assertEquals(6, handler.getTag(tag1));
        // Calls a method
        assertEquals(new Test(6), handler.getTag(tag2));

        // Calls a method
        handler.updateTag(tag2, value -> null);
        // Calls a method
        assertNull(handler.getTag(tag1));
        // Calls a method
        assertNull(handler.getTag(tag2));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void updateStructureConversionPathDouble() {
        // Type declaration (class/interface/enum/record)
        record Test(int coin) {
        // End of a block/expression
        }
        // Type declaration (class/interface/enum/record)
        record Structure(Test test) {
        // End of a block/expression
        }

        // Calls a method
        var tag1 = Tag.Integer("coin").path("path", "test");
        // Calls a method
        var tag2 = Tag.Structure("path", Structure.class);

        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag1, 5);
        // Calls a method
        assertEquals(5, handler.getTag(tag1));
        // Calls a method
        assertEquals(new Structure(new Test(5)), handler.getTag(tag2));

        // Calls a method
        assertDoesNotThrow(() -> handler.updateTag(tag2, value -> new Structure(new Test(value.test.coin + 1))));
        // Calls a method
        assertEquals(6, handler.getTag(tag1));
        // Calls a method
        assertEquals(new Structure(new Test(6)), handler.getTag(tag2));

        // Calls a method
        handler.updateTag(tag2, value -> null);
        // Calls a method
        assertNull(handler.getTag(tag1));
        // Calls a method
        assertNull(handler.getTag(tag2));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void updateViewConversion() {
        // Type declaration (class/interface/enum/record)
        record Test(int coin) {
        // End of a block/expression
        }

        // Calls a method
        var tag1 = Tag.Integer("coin");
        // Calls a method
        var tag2 = Tag.View(Test.class);
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag1, 5);
        // Calls a method
        assertDoesNotThrow(() -> handler.updateTag(tag2, value -> new Test(value.coin + 1)));
        // Calls a method
        assertEquals(6, handler.getTag(tag1));
        // Calls a method
        assertEquals(new Test(6), handler.getTag(tag2));

        // Calls a method
        handler.updateTag(tag2, value -> null);
        // Calls a method
        assertNull(handler.getTag(tag1));
        // Calls a method
        assertNull(handler.getTag(tag2));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void updateIncompatible() {
        // Calls a method
        var tagI = Tag.Integer("coin");
        // Calls a method
        var tagD = Tag.Double("coin");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tagI, 5);
        // Calls a method
        assertThrows(ClassCastException.class, () -> handler.updateTag(tagD, value -> 5d));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void updateInner() {
        // Calls a method
        var tag = Tag.Structure("vec", Vec.class);
        // Calls a method
        var tagX = Tag.Double("x").path("vec");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag, new Vec(5, 10, 15));
        // Start of a method/block
        handler.updateTag(tagX, x -> {
            // Calls a method
            assertEquals(5, x);
            // Returns a value to the caller
            return 7d;
        // End of a block/expression
        });
        // Calls a method
        assertEquals(7d, handler.getTag(tagX));
        // Calls a method
        assertEquals(new Vec(7, 10, 15), handler.getTag(tag));
    // End of a block/expression
    }
// End of a block/expression
}
