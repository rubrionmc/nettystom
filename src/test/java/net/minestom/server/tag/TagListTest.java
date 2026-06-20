// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class TagListTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void basic() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        Tag<Integer> tag = Tag.Integer("number");
        // Calls a method
        Tag<List<Integer>> list = tag.list();

        // Calls a method
        handler.setTag(tag, 5);
        // Calls a method
        assertEquals(5, handler.getTag(tag));
        // Calls a method
        assertNull(handler.getTag(list));

        // Calls a method
        handler.setTag(list, List.of(1, 2, 3));
        // Calls a method
        assertEquals(List.of(1, 2, 3), handler.getTag(list));
        // Calls a method
        assertNull(handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cache() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Integer("number").list();
        // Calls a method
        var val = List.of(1, 2, 3);

        // Calls a method
        handler.setTag(tag, val);
        // Calls a method
        assertSame(val, handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void recursiveCache() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Integer("number").list().list();
        // Calls a method
        var val = List.of(List.of(1, 2, 3), List.of(4, 5, 6));

        // Calls a method
        handler.setTag(tag, val);
        // Calls a method
        assertSame(val.get(0), handler.getTag(tag).get(0));
        // Calls a method
        assertSame(val.get(1), handler.getTag(tag).get(1));
        // Calls a method
        assertSame(val, handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void recursiveCacheIncorrect() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Integer("number").list().list();
        // Calls a method
        var val = List.of(List.of(1, 2, 3), new ArrayList<>(Arrays.asList(4, 5, 6)));

        // Calls a method
        handler.setTag(tag, val);
        // Calls a method
        assertSame(val.get(0), handler.getTag(tag).get(0));
        // Calls a method
        assertNotSame(val.get(1), handler.getTag(tag).get(1));
        // Calls a method
        assertNotSame(val, handler.getTag(tag));
        // Calls a method
        assertEquals(val, handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void snbt() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        Tag<List<Integer>> tag = Tag.Integer("numbers").list();

        // Calls a method
        handler.setTag(tag, List.of(1, 2, 3));
        // Code statement
        assertEqualsSNBT("""
                {
                  "numbers": [1,2,3]
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
        // Calls a method
        Tag<List<Integer>> tag = Tag.Integer("numbers").list();
        // Calls a method
        handler.setTag(tag, List.of());
        // Calls a method
        assertEquals(List.of(), handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void emptySnbt() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        Tag<List<Integer>> tag = Tag.Integer("numbers").list();
        // Calls a method
        handler.setTag(tag, List.of());
        // Code statement
        assertEqualsSNBT("""
                {
                  "numbers":[]
                }
                """, handler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void removal() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        Tag<List<Integer>> tag = Tag.Integer("numbers").list();
        // Calls a method
        handler.setTag(tag, List.of(1));
        // Calls a method
        assertEquals(List.of(1), handler.getTag(tag));
        // Calls a method
        handler.removeTag(tag);
        // Calls a method
        assertNull(handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void removalSnbt() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        Tag<List<Integer>> tag = Tag.Integer("numbers").list();
        // Calls a method
        handler.setTag(tag, List.of(1));
        // Code statement
        assertEqualsSNBT("""
                {
                  "numbers": [1]
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
    public void chaining() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        Tag<List<List<Integer>>> tag = Tag.Integer("numbers").list().list();
        // Calls a method
        var integers = List.of(List.of(1, 2, 3), List.of(4, 5, 6));
        // Calls a method
        handler.setTag(tag, integers);
        // Calls a method
        assertEquals(integers, handler.getTag(tag));
        // Calls a method
        handler.removeTag(tag);
        // Calls a method
        assertNull(handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chainingSnbt() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        Tag<List<List<Integer>>> tag = Tag.Integer("numbers").list().list();
        // Calls a method
        var integers = List.of(List.of(1, 2, 3), List.of(4, 5, 6));
        // Calls a method
        handler.setTag(tag, integers);
        // Code statement
        assertEqualsSNBT("""
                {
                  "numbers":[
                    [1,2,3],
                    [4,5,6]
                  ]
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
    public void defaultValue() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var val = List.of(1, 2, 3);
        // Calls a method
        var tag = Tag.Integer("number").list().defaultValue(val);
        // Calls a method
        assertEquals(List.of(1, 2, 3), handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void defaultValueReset() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Integer("number").defaultValue(5);
        // Calls a method
        var list = tag.list();
        // Calls a method
        assertNull(handler.getTag(list));
        // Calls a method
        assertEquals(List.of(1, 2, 3), handler.getTag(list.defaultValue(List.of(1, 2, 3))));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void immutability() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Integer("number").list();
        // Calls a method
        List<Integer> val = new ArrayList<>();
        // Calls a method
        val.add(1);

        // Calls a method
        handler.setTag(tag, val);
        // Calls a method
        assertNotSame(val, handler.getTag(tag));
        // Calls a method
        assertEquals(List.of(1), handler.getTag(tag));

        // Code statement
        val.add(2); // Must not modify the nbt
        // Calls a method
        assertNotSame(val, handler.getTag(tag));
        // Calls a method
        assertEquals(List.of(1), handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chainingImmutability() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        Tag<List<List<Integer>>> tag = Tag.Integer("numbers").list().list();
        // Calls a method
        List<List<Integer>> val = new ArrayList<>();
        // Calls a method
        val.add(new ArrayList<>(Arrays.asList(1, 2, 3)));
        // Calls a method
        val.add(new ArrayList<>(Arrays.asList(4, 5, 6)));

        // Calls a method
        handler.setTag(tag, val);
        // Calls a method
        assertNotSame(val, handler.getTag(tag));
        // Calls a method
        assertEquals(List.of(List.of(1, 2, 3), List.of(4, 5, 6)), handler.getTag(tag));

        // Must not modify the nbt
        // Calls a method
        val.get(0).add(7);
        // Calls a method
        val.get(1).add(8);
        // Calls a method
        val.add(new ArrayList<>(Arrays.asList(9, 10, 11)));
        // Calls a method
        assertNotSame(val, handler.getTag(tag));
        // Calls a method
        assertEquals(List.of(List.of(1, 2, 3), List.of(4, 5, 6)), handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void immutabilitySnbt() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Integer("numbers").list();
        // Calls a method
        List<Integer> val = new ArrayList<>();
        // Calls a method
        val.add(1);

        // Calls a method
        handler.setTag(tag, val);
        // Code statement
        assertEqualsSNBT("""
                {
                  "numbers": [1]
                }
                """, handler.asCompound());

        // Code statement
        val.add(2); // Must not modify the nbt
        // Code statement
        assertEqualsSNBT("""
                {
                  "numbers": [1]
                }
                """, handler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chainingImmutabilitySnbt() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        Tag<List<List<Integer>>> tag = Tag.Integer("numbers").list().list();
        // Calls a method
        List<List<Integer>> val = new ArrayList<>();
        // Calls a method
        val.add(new ArrayList<>(Arrays.asList(1, 2, 3)));
        // Calls a method
        val.add(new ArrayList<>(Arrays.asList(4, 5, 6)));

        // Calls a method
        handler.setTag(tag, val);
        // Code statement
        assertEqualsSNBT("""
                {
                  "numbers":[
                    [1,2,3],
                    [4,5,6]
                  ]
                }
                """, handler.asCompound());


        // Must not modify the nbt
        // Calls a method
        val.get(0).add(7);
        // Calls a method
        val.get(1).add(8);
        // Calls a method
        val.add(new ArrayList<>(Arrays.asList(9, 10, 11)));
        // Code statement
        assertEqualsSNBT("""
                {
                  "numbers":[
                    [1,2,3],
                    [4,5,6]
                  ]
                }
                """, handler.asCompound());
    // End of a block/expression
    }
// End of a block/expression
}
