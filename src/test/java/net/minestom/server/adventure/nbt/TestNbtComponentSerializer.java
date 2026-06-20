// Package declaration for this file
package net.minestom.server.adventure.nbt;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.ListBinaryTag;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.adventure.serializer.nbt.NbtComponentSerializer;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.kyori.adventure.nbt.StringBinaryTag.stringBinaryTag;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class TestNbtComponentSerializer {

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testReadStringChildren() {
        // Assigns a value
        var tag = CompoundBinaryTag.builder()
                // Code statement
                .putString("text", "Hello")
                // Code statement
                .put("extra", ListBinaryTag.from(List.of(
                        // Code statement
                        stringBinaryTag(" "),
                        // Code statement
                        stringBinaryTag("World!")
                // Code statement
                )))
                // Calls a method
                .build();
        // Calls a method
        var deserialized = NbtComponentSerializer.nbt().deserialize(tag);

        // Calls a method
        var expected = Component.text("Hello").appendSpace().append(Component.text("World!"));
        // Calls a method
        assertEquals(expected, deserialized);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testWriteRead() {
        // Calls a method
        var serializer = NbtComponentSerializer.nbt();
        // Calls a method
        var comp = Component.text("Hello").appendSpace().append(Component.text("World!"));

        // Calls a method
        var tag = serializer.serialize(comp);
        // Calls a method
        var comp2 = serializer.deserialize(tag);

        // Calls a method
        assertEquals(comp, comp2);
    // End of a block/expression
    }

// End of a block/expression
}
