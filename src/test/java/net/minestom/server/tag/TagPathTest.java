// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class TagPathTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void basic() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Integer("number");
        // Calls a method
        var path = tag.path("display");
        // Calls a method
        handler.setTag(path, 5);
        // Calls a method
        assertEquals(5, handler.getTag(path));
        // Calls a method
        assertNull(handler.getTag(tag));

        // Calls a method
        handler.setTag(path, 6);
        // Calls a method
        assertEquals(6, handler.getTag(path));
        // Calls a method
        assertNull(handler.getTag(tag));

        // Calls a method
        handler.removeTag(path);
        // Calls a method
        assertNull(handler.getTag(path));
        // Calls a method
        assertNull(handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void invalidPath() {
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> Tag.Integer("number").path(""));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> Tag.Integer("number").path("path", null));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void emptyRemoval() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Integer("number").path("display");
        // Calls a method
        handler.removeTag(tag);
        // Calls a method
        assertNull(handler.getTag(tag));
        // Calls a method
        assertEqualsSNBT("{}", handler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void snbt() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Integer("number").path("display");
        // Calls a method
        handler.setTag(tag, 5);
        // Code statement
        assertEqualsSNBT("""
                {
                  "display": {
                    "number":5
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
    public void doubleSnbt() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Integer("number").path("display");
        // Calls a method
        var tag1 = Tag.String("string").path("display");
        // Calls a method
        handler.setTag(tag, 5);
        // Calls a method
        handler.setTag(tag1, "test");

        // Code statement
        assertEqualsSNBT("""
                {
                  "display": {
                    "string":"test",
                    "number":5
                  }
                }
                """, handler.asCompound());

        // Calls a method
        handler.removeTag(tag);
        // Code statement
        assertEqualsSNBT("""
                {
                  "display": {
                    "string":"test"
                  }
                }
                """, handler.asCompound());

        // Calls a method
        handler.removeTag(tag1);
        // Calls a method
        assertEqualsSNBT("{}", handler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void secondPathClearSnbt() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var numberTag = Tag.Integer("number").path("path1", "path2");
        // Calls a method
        var stringTag = Tag.String("string").path("path1");
        // Calls a method
        handler.setTag(numberTag, 5);
        // Calls a method
        handler.setTag(stringTag, "test");
        // Code statement
        assertEqualsSNBT("""
                {
                  "path1": {
                    "path2": {
                      "number":5
                    },
                    "string":"test"
                  }
                }
                """, handler.asCompound());

        // Calls a method
        handler.removeTag(numberTag);
        // Code statement
        assertEqualsSNBT("""
                {
                  "path1": {
                    "string":"test"
                  }
                }
                """, handler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void differentPath() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Integer("number");
        // Calls a method
        var path = tag.path("display");
        // Calls a method
        handler.setTag(tag, 5);
        // Code statement
        assertEqualsSNBT("""
                {
                  "number":5
                }
                """, handler.asCompound());

        // Calls a method
        handler.setTag(path, 5);
        // Code statement
        assertEqualsSNBT("""
                {
                  "number":5,
                  "display": {
                    "number":5
                  }
                }
                """, handler.asCompound());

        // Calls a method
        handler.removeTag(tag);
        // Code statement
        assertEqualsSNBT("""
                {
                  "display": {
                    "number":5
                  }
                }
                """, handler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void overrideSnbt() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Integer("key");
        // Calls a method
        var tag1 = Tag.Integer("value").path("key");
        // Calls a method
        handler.setTag(tag, 5);
        // Code statement
        assertEqualsSNBT("""
                {
                  "key":5
                }
                """, handler.asCompound());

        // Calls a method
        handler.setTag(tag1, 2);
        // Code statement
        assertEqualsSNBT("""
                {
                  "key": {
                    "value":2
                  }
                }
                """, handler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void forgetPath() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Integer("key");
        // Calls a method
        var path = Tag.Integer("value").path("key");
        // Calls a method
        handler.setTag(path, 5);
        // Calls a method
        assertNull(handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void pathInvalidClear() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag1 = Tag.Integer("pathInvalidClear1").path("key");
        // Calls a method
        var tag2 = Tag.Integer("pathInvalidClear2").path("key");
        // Calls a method
        handler.setTag(tag1, 5);
        // Calls a method
        handler.setTag(tag2, null);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chaining() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Integer("key");
        // Calls a method
        var path = Tag.Integer("key").path("first", "second");
        // Calls a method
        handler.setTag(path, 5);
        // Code statement
        assertEqualsSNBT("""
                {
                  "first": {
                    "second": {
                      "key":5
                    }
                  }
                }
                """, handler.asCompound());

        // Calls a method
        assertEquals(5, handler.getTag(path));
        // Calls a method
        assertNull(handler.getTag(tag));

        // Calls a method
        handler.removeTag(path);
        // Calls a method
        assertEqualsSNBT("{}", handler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chainingDouble() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var path = Tag.Integer("key").path("first", "second");
        // Calls a method
        var path1 = Tag.Integer("key").path("first");
        // Calls a method
        handler.setTag(path, 5);
        // Code statement
        assertEqualsSNBT("""
                {
                  "first": {
                    "second": {
                      "key":5
                    }
                  }
                }
                """, handler.asCompound());
        // Calls a method
        assertEquals(5, handler.getTag(path));

        // Calls a method
        handler.setTag(path1, 5);
        // Code statement
        assertEqualsSNBT("""
                {
                  "first": {
                    "key":5,
                    "second": {
                      "key":5
                    }
                  }
                }
                """, handler.asCompound());
        // Calls a method
        assertEquals(5, handler.getTag(path));
        // Calls a method
        assertEquals(5, handler.getTag(path1));

        // Calls a method
        handler.removeTag(path);
        // Code statement
        assertEqualsSNBT("""
                {
                  "first": {
                    "key":5
                  }
                }
                """, handler.asCompound());

        // Calls a method
        handler.removeTag(path1);
        // Calls a method
        assertEqualsSNBT("{}", handler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void structureObstruction() {
        // Type declaration (class/interface/enum/record)
        record Entry(int value) {
        // End of a block/expression
        }

        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Integer("value");
        // Assigns a value
        var struct = Tag.Structure("struct", new TagSerializer<Entry>() {
            // Calls a method
            private static final Tag<Integer> VALUE_TAG = Tag.Integer("value");

            // Annotation for the following element
            @Override
            // Start of a method/block
            public @Nullable Entry read(TagReadable reader) {
                // Calls a method
                final Integer value = reader.getTag(VALUE_TAG);
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

        // Calls a method
        handler.setTag(struct, new Entry(5));
        // Code statement
        assertEqualsSNBT("""
                {
                  "struct": {
                    "value":5
                  }
                }
                """, handler.asCompound());
        // Calls a method
        assertEquals(5, handler.getTag(tag.path("struct")));

        // Calls a method
        handler.setTag(tag, 5);
        // Code statement
        assertEqualsSNBT("""
                {
                  value:5,
                  "struct": {
                    "value":5
                  }
                }
                """, handler.asCompound());

        // Calls a method
        handler.setTag(tag.path("struct"), 2);
        // Code statement
        assertEqualsSNBT("""
                {
                  value:5,
                  "struct": {
                    "value":2
                  }
                }
                """, handler.asCompound());
        // Calls a method
        assertEquals(new Entry(2), handler.getTag(struct));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void tagObstruction() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Integer("key");
        // Calls a method
        var path = Tag.Integer("value").path("key", "second");
        // Calls a method
        handler.setTag(tag, 5);
        // Code statement
        assertEqualsSNBT("""
                {
                  "key":5
                }
                """, handler.asCompound());
        // Calls a method
        handler.setTag(path, 2);
        // Code statement
        assertEqualsSNBT("""
                {
                  "key": {
                    "second": {
                      "value":2
                      }
                    }
                }
                """, handler.asCompound());
    // End of a block/expression
    }
// End of a block/expression
}
