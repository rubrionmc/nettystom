// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.kyori.adventure.nbt.IntArrayBinaryTag;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.UUID;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class TagUuidTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void get() {
        // Calls a method
        var uuid = UUID.randomUUID();
        // Calls a method
        var tag = Tag.UUID("uuid");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag, uuid);
        // Calls a method
        assertEquals(uuid, handler.getTag(tag));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void empty() {
        // Calls a method
        var tag = Tag.UUID("uuid");
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
        var tag = Tag.UUID("entry");
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
    public void toNbt() {
        // Calls a method
        var tag = Tag.UUID("uuid");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(tag, UUID.fromString("9ab8ca63-3d7b-43ba-b805-a20a352dae9c"));
        // Calls a method
        var nbt = handler.asCompound();
        // Calls a method
        IntArrayBinaryTag array = (IntArrayBinaryTag) nbt.get("uuid");
        // Calls a method
        assertArrayEquals(new int[]{-1699165597, 1031488442, -1207590390, 892186268}, array.value());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void fromNbt() {
        // Calls a method
        var tag = Tag.UUID("uuid");
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        handler.setTag(Tag.NBT("uuid"), IntArrayBinaryTag.intArrayBinaryTag(-1699165597, 1031488442, -1207590390, 892186268));
        // Calls a method
        assertEquals(UUID.fromString("9ab8ca63-3d7b-43ba-b805-a20a352dae9c"), handler.getTag(tag));
    // End of a block/expression
    }
// End of a block/expression
}
