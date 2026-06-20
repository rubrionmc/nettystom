// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.StringBinaryTag;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Static import of a member
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class TagRecordTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void basic() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Structure("vec", Vec.class);
        // Calls a method
        var vec = new Vec(1, 2, 3);
        // Calls a method
        assertNull(handler.getTag(tag));
        // Calls a method
        handler.setTag(tag, vec);
        // Calls a method
        assertEquals(vec, handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void fromNBT() {
        // Assigns a value
        var vecCompound = CompoundBinaryTag.builder()
                // Code statement
                .putDouble("x", 1)
                // Code statement
                .putDouble("y", 2)
                // Code statement
                .putDouble("z", 3)
                // Calls a method
                .build();
        // Calls a method
        var handler = TagHandler.fromCompound(CompoundBinaryTag.from(Map.of("vec", vecCompound)));
        // Calls a method
        var tag = Tag.Structure("vec", Vec.class);
        // Calls a method
        assertEquals(new Vec(1, 2, 3), handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void fromNBTView() {
        // Assigns a value
        var handler = TagHandler.fromCompound(CompoundBinaryTag.builder()
                // Code statement
                .putDouble("x", 1)
                // Code statement
                .putDouble("y", 2)
                // Code statement
                .putDouble("z", 3)
                // Calls a method
                .build());
        // Calls a method
        var tag = Tag.View(Vec.class);
        // Calls a method
        assertEquals(new Vec(1, 2, 3), handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void basicSerializer() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var serializer = TagRecord.serializer(Vec.class);
        // Calls a method
        serializer.write(handler, new Vec(1, 2, 3));
        // Calls a method
        assertEquals(new Vec(1, 2, 3), serializer.read(handler));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void basicSnbt() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var tag = Tag.Structure("vec", Vec.class);
        // Calls a method
        var vec = new Vec(1, 2, 3);
        // Calls a method
        handler.setTag(tag, vec);
        // Code statement
        assertEqualsSNBT("""
                {
                  "vec": {
                    "x":1D,
                    "y":2D,
                    "z":3D
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
    public void nbtSerializer() {
        // Type declaration (class/interface/enum/record)
        record CompoundRecord(CompoundBinaryTag compound) {
        // End of a block/expression
        }
        // Calls a method
        var test = new CompoundRecord(CompoundBinaryTag.from(Map.of("key", StringBinaryTag.stringBinaryTag("value"))));
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var serializer = TagRecord.serializer(CompoundRecord.class);
        // Calls a method
        serializer.write(handler, test);
        // Calls a method
        assertEquals(test, serializer.read(handler));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void unsupportedList() {
        // Type declaration (class/interface/enum/record)
        record Test(List<Object> list) {
        // End of a block/expression
        }
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> Tag.Structure("test", Test.class));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void unsupportedArray() {
        // Type declaration (class/interface/enum/record)
        record Test(Object[] array) {
        // End of a block/expression
        }
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> Tag.Structure("test", Test.class));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void forceRecord() {
        // Calls a method
        assertThrows(Throwable.class, () -> Tag.Structure("entity", Class.class.cast(Entity.class)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void invalidItem() {
        // ItemStack cannot become a record due to `ItemStack#toItemNBT` being serialized differently, and independently of
        // the item record components
        // Calls a method
        assertThrows(Throwable.class, () -> Tag.Structure("item", Class.class.cast(ItemStack.class)));
    // End of a block/expression
    }
// End of a block/expression
}
