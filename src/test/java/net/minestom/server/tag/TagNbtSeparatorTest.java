// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.kyori.adventure.nbt.*;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.HashSet;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Set;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class TagNbtSeparatorTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void primitives() {
        // Code statement
        assertSeparation(new TagNbtSeparator.Entry<>(Tag.Byte("key"), (byte) 1),
                // Calls a method
                "key", ByteBinaryTag.byteBinaryTag((byte) 1));
        // Code statement
        assertSeparation(new TagNbtSeparator.Entry<>(Tag.Short("key"), (short) 1),
                // Calls a method
                "key", ShortBinaryTag.shortBinaryTag((short) 1));
        // Code statement
        assertSeparation(new TagNbtSeparator.Entry<>(Tag.Integer("key"), 1),
                // Calls a method
                "key", IntBinaryTag.intBinaryTag(1));
        // Code statement
        assertSeparation(new TagNbtSeparator.Entry<>(Tag.Long("key"), 1L),
                // Calls a method
                "key", LongBinaryTag.longBinaryTag(1));
        // Code statement
        assertSeparation(new TagNbtSeparator.Entry<>(Tag.Float("key"), 1f),
                // Calls a method
                "key", FloatBinaryTag.floatBinaryTag(1));
        // Code statement
        assertSeparation(new TagNbtSeparator.Entry<>(Tag.Double("key"), 1d),
                // Calls a method
                "key", DoubleBinaryTag.doubleBinaryTag(1));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void compound() {
        // Code statement
        assertSeparation(new TagNbtSeparator.Entry<>(Tag.Byte("key").path("path"), (byte) 1),
                // Calls a method
                "path", CompoundBinaryTag.builder().putByte("key", (byte) 1).build());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void compoundMultiple() {
        // Code statement
        assertSeparation(Set.of(new TagNbtSeparator.Entry<>(Tag.Byte("key").path("path"), (byte) 1),
                        // Creates a new object
                        new TagNbtSeparator.Entry<>(Tag.Integer("key2").path("path"), 2)),
                // Calls a method
                "path", CompoundBinaryTag.builder().putByte("key", (byte) 1).putInt("key2", 2).build());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void list() {
        // Code statement
        assertSeparation(new TagNbtSeparator.Entry<>(Tag.Integer("key").list(), List.of(1)),
                // Calls a method
                "key", ListBinaryTag.listBinaryTag(BinaryTagTypes.INT, List.of(IntBinaryTag.intBinaryTag(1))));
    // End of a block/expression
    }

    // Start of a method/block
    void assertSeparation(Set<TagNbtSeparator.Entry<?>> expected, String key, BinaryTag nbt) {
        // Calls a method
        assertEquals(expected, retrieve(key, nbt));
    // End of a block/expression
    }

    // Start of a method/block
    void assertSeparation(TagNbtSeparator.Entry<?> expected, String key, BinaryTag nbt) {
        // Calls a method
        var entries = retrieve(key, nbt);
        // Calls a method
        assertEquals(1, entries.size());
        // Calls a method
        assertEquals(expected, entries.iterator().next());
    // End of a block/expression
    }

    // Start of a method/block
    Set<TagNbtSeparator.Entry<?>> retrieve(String key, BinaryTag nbt) {
        // Calls a method
        Set<TagNbtSeparator.Entry<?>> entries = new HashSet<>();
        // Calls a method
        TagNbtSeparator.separate(key, nbt, entries::add);
        // Returns a value to the caller
        return Set.copyOf(entries);
    // End of a block/expression
    }
// End of a block/expression
}
