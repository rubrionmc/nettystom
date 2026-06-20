// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTagTypes;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.IntBinaryTag;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import org.junit.jupiter.api.Test;
// Import of a required class
import org.junit.jupiter.api.condition.DisabledIf;
// Import of a required class
import org.junit.jupiter.api.condition.EnabledIf;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Static import of a member
import static net.kyori.adventure.nbt.IntArrayBinaryTag.intArrayBinaryTag;
// Static import of a member
import static net.kyori.adventure.nbt.IntBinaryTag.intBinaryTag;
// Static import of a member
import static net.kyori.adventure.nbt.ListBinaryTag.listBinaryTag;
// Static import of a member
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Ensure that NBT tag can be read from other tags properly.
 */
// Type declaration (class/interface/enum/record)
public class TagNbtTest {

    // Start of a method/block
    static boolean isSerializeEmptyCompoundEnabled() {
        // Returns a value to the caller
        return ServerFlag.SERIALIZE_EMPTY_COMPOUND;
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void list() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.NBT("nbt").list();
        // Calls a method
        List<BinaryTag> list = List.of(intBinaryTag(1), intBinaryTag(2), intBinaryTag(3));
        // Calls a method
        handler.setTag(tag, list);
        // Calls a method
        assertEquals(list, handler.getTag(tag));
        // Code statement
        assertEqualsSNBT("""
                {
                  "nbt": [1,2,3]
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
    public void map() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.NBT("nbt").map(nbt -> ((IntBinaryTag) nbt).value(), IntBinaryTag::intBinaryTag);
        // Calls a method
        handler.setTag(tag, 5);
        // Calls a method
        assertEquals(5, handler.getTag(tag));
        // Code statement
        assertEqualsSNBT("""
                {
                  "nbt":5
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
    public void fromCompoundModify() {
        // Calls a method
        var compound = CompoundBinaryTag.builder().putInt("key", 5).build();
        // Calls a method
        var handler = TagHandler.fromCompound(compound);
        // Calls a method
        assertEquals(compound, handler.asCompound());
        // Code statement
        assertEqualsSNBT("""
                {"key":5}
                """, handler.asCompound());

        // Calls a method
        handler.setTag(Tag.Integer("key"), 10);
        // Calls a method
        assertEquals(10, handler.getTag(Tag.Integer("key")));
        // Code statement
        assertEqualsSNBT("""
                {"key":10}
                """, handler.asCompound());
        // Calls a method
        handler.setTag(Tag.Integer("key"), 15);
        // Code statement
        assertEqualsSNBT("""
                {"key":15}
                """, handler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void fromCompoundModifyPath() {
        // Calls a method
        var compound = CompoundBinaryTag.builder().put("path", CompoundBinaryTag.builder().putInt("key", 5).build()).build();
        // Calls a method
        var handler = TagHandler.fromCompound(compound);
        // Calls a method
        var tag = Tag.Integer("key").path("path");

        // Calls a method
        handler.setTag(tag, 10);
        // Calls a method
        assertEquals(10, handler.getTag(tag));
        // Code statement
        assertEqualsSNBT("""
                {"path":{"key":10}}
                """, handler.asCompound());
        // Calls a method
        handler.setTag(tag, 15);
        // Code statement
        assertEqualsSNBT("""
                {"path":{"key":15}}
                """, handler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void fromCompoundModifyDoublePath() {
        // Assigns a value
        var compound = CompoundBinaryTag.builder().put("path", CompoundBinaryTag.builder()
                // Calls a method
                .put("path2", CompoundBinaryTag.builder().putInt("key", 5).build()).build()).build();
        // Calls a method
        var handler = TagHandler.fromCompound(compound);
        // Calls a method
        var tag = Tag.Integer("key").path("path", "path2");

        // Calls a method
        handler.setTag(tag, 10);
        // Calls a method
        assertEquals(10, handler.getTag(tag));
        // Code statement
        assertEqualsSNBT("""
                {"path":{"path2":{"key":10}}}
                """, handler.asCompound());
        // Calls a method
        handler.setTag(tag, 15);
        // Code statement
        assertEqualsSNBT("""
                {"path":{"path2":{"key":15}}}
                """, handler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void compoundOverride() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var nbtTag = Tag.NBT("path1");

        // Calls a method
        var nbt1 = CompoundBinaryTag.from(Map.of("key", intBinaryTag(5)));
        // Calls a method
        var nbt2 = CompoundBinaryTag.from(Map.of("other-key", intBinaryTag(5)));
        // Calls a method
        handler.setTag(nbtTag, nbt1);
        // Calls a method
        assertEquals(nbt1, handler.getTag(nbtTag));

        // Calls a method
        handler.setTag(nbtTag, nbt2);
        // Calls a method
        assertEquals(nbt2, handler.getTag(nbtTag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void compoundRead() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var nbtTag = Tag.NBT("path1");

        // Calls a method
        var nbt = CompoundBinaryTag.from(Map.of("key", intBinaryTag(5)));
        // Calls a method
        handler.setTag(nbtTag, nbt);
        // Calls a method
        assertEquals(nbt, handler.getTag(nbtTag));

        // Calls a method
        var path = Tag.Integer("key").path("path1");
        // Calls a method
        assertEquals(5, handler.getTag(path));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void compoundPathRead() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var nbtTag = Tag.NBT("compound").path("path");

        // Calls a method
        var nbt = CompoundBinaryTag.from(Map.of("key", intBinaryTag(5)));
        // Calls a method
        handler.setTag(nbtTag, nbt);
        // Calls a method
        assertEquals(nbt, handler.getTag(nbtTag));

        // Calls a method
        var path = Tag.Integer("key").path("path", "compound");
        // Calls a method
        assertEquals(5, handler.getTag(path));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void doubleCompoundRead() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var nbtTag = Tag.NBT("path1");

        // Calls a method
        var nbt = CompoundBinaryTag.from(Map.of("path2", CompoundBinaryTag.from(Map.of("key", intBinaryTag(5)))));
        // Calls a method
        handler.setTag(nbtTag, nbt);
        // Calls a method
        assertEquals(nbt, handler.getTag(nbtTag));

        // Calls a method
        var path = Tag.Integer("key").path("path1", "path2");
        // Calls a method
        assertEquals(5, handler.getTag(path));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void compoundWrite() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var nbtTag = Tag.NBT("path1");

        // Calls a method
        var nbt = CompoundBinaryTag.from(Map.of("key", intBinaryTag(5)));
        // Calls a method
        handler.setTag(nbtTag, nbt);
        // Calls a method
        assertEquals(nbt, handler.getTag(nbtTag));

        // Calls a method
        var path = Tag.Integer("key").path("path1");
        // Calls a method
        handler.setTag(path, 10);
        // Calls a method
        assertEquals(10, handler.getTag(path));
        // Calls a method
        assertEquals(CompoundBinaryTag.from(Map.of("key", intBinaryTag(10))), handler.getTag(nbtTag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void rawList() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var nbtTag = Tag.NBT("list");
        // Calls a method
        var list = listBinaryTag(BinaryTagTypes.INT, List.of(intBinaryTag(1)));
        // Calls a method
        handler.setTag(nbtTag, list);
        // Calls a method
        assertEquals(list, handler.getTag(nbtTag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void listConversion() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var nbtTag = Tag.NBT("list");
        // Calls a method
        var listTag = Tag.Integer("list").list();
        // Calls a method
        var list = listBinaryTag(BinaryTagTypes.INT, List.of(intBinaryTag(1)));
        // Calls a method
        handler.setTag(nbtTag, list);

        // Calls a method
        assertEquals(list, handler.getTag(nbtTag));
        // Calls a method
        assertNotSame(list, handler.getTag(nbtTag));
        // Calls a method
        assertEquals(List.of(1), handler.getTag(listTag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void rawArray() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var nbtTag = Tag.NBT("array");
        // Calls a method
        var array = intArrayBinaryTag(1, 2, 3);
        // Calls a method
        handler.setTag(nbtTag, array);
        // Calls a method
        assertEquals(array, handler.getTag(nbtTag));
    // End of a block/expression
    }

    // from #2912
    // Annotation for the following element
    @Test
    // Annotation for the following element
    @EnabledIf("isSerializeEmptyCompoundEnabled")
    // Start of a method/block
    public void emptyCompoundSerialization() {
        // Calls a method
        var tag = Tag.NBT("test");
        // Calls a method
        var handler = TagHandler.newHandler();

        // Assigns a value
        var value = CompoundBinaryTag.builder()
                // Code statement
                .putString("type", "something")
                // Code statement
                .put("value", CompoundBinaryTag.empty())
                // Calls a method
                .build();
        // Calls a method
        handler.setTag(tag, value);

        // Calls a method
        var nbt = handler.asCompound();
        // Calls a method
        var newHandler = TagHandler.fromCompound(nbt);

        // Code statement
        assertEquals(value, newHandler.getTag(tag),
            // Code statement
            "Empty compound should be preserved during serialization when SERIALIZE_EMPTY_COMPOUND flag is enabled");

        // Code statement
        assertEqualsSNBT("""
                {
                  "test": {
                    "type": "something",
                    "value": {}
                  }
                }
                """, newHandler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Annotation for the following element
    @DisabledIf("isSerializeEmptyCompoundEnabled")
    // Start of a method/block
    public void emptyCompoundSerializationDisabled() {
        // Calls a method
        var tag = Tag.NBT("test");
        // Calls a method
        var handler = TagHandler.newHandler();

        // Assigns a value
        var originalValue = CompoundBinaryTag.builder()
                // Code statement
                .putString("type", "something")
                // Code statement
                .put("value", CompoundBinaryTag.empty())
                // Calls a method
                .build();
        // Calls a method
        handler.setTag(tag, originalValue);

        // Calls a method
        var nbt = handler.asCompound();
        // Calls a method
        var newHandler = TagHandler.fromCompound(nbt);
        // Calls a method
        var deserializedValue = newHandler.getTag(tag);

        // Assigns a value
        var expectedValue = CompoundBinaryTag.builder()
                // Code statement
                .putString("type", "something")
                // Calls a method
                .build();
        // Code statement
        assertEquals(expectedValue, deserializedValue,
            // Code statement
            "Empty compound should be stripped during serialization when SERIALIZE_EMPTY_COMPOUND flag is disabled");

        // Code statement
        assertEqualsSNBT("""
                {
                  "test": {
                    "type": "something"
                  }
                }
                """, newHandler.asCompound());
    // End of a block/expression
    }
// End of a block/expression
}
