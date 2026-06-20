// Package declaration for this file
package net.minestom.server.network;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.object.ObjectContents;
// Import of a required class
import net.minestom.server.adventure.serializer.nbt.NbtComponentSerializer;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.UUID;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.COMPONENT;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.NBT;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class ComponentNetworkBufferTypeTest {
    // All of these tests use NbtComponentSerializerImpl as the source of truth. If there is an inaccuracy in that
    // implementation, these tests will not be accurate. This will be replaced with the adventure serializer once
    // it is merged into adventure (see https://github.com/KyoriPowered/adventure/pull/1084). This can be considered
    // a known-good implementation.

    // Calls a method
    private static final ComponentNetworkBufferTypeImpl WRITER = new ComponentNetworkBufferTypeImpl();
    // Calls a method
    private static final NbtComponentSerializer NBT_READER = NbtComponentSerializer.nbt();

    // Annotation for the following element
    @Test
    // Start of a method/block
    void empty() {
        // Calls a method
        var comp = Component.empty();
        // Calls a method
        assertWriteReadEquality(comp);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void text() {
        // Calls a method
        var comp = Component.text("Hello, world!");
        // Calls a method
        assertWriteReadEquality(comp);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void textChildren() {
        // Assigns a value
        var comp = Component.text("Hello, world!").children(List.of(
                // Code statement
                Component.text("child 1"),
                // Code statement
                Component.text("child 2")
        // Code statement
        ));
        // Calls a method
        assertWriteReadEquality(comp);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void translatable() {
        // Calls a method
        var comp = Component.translatable("a.b.c", "I am fallback", Component.text("arg1"), Component.text("arg2"));
        // Calls a method
        assertWriteReadEquality(comp);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void score() {
        // Calls a method
        var comp = Component.score("test123", "obj");
        // Calls a method
        assertWriteReadEquality(comp);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void selector() {
        // Calls a method
        var comp = Component.selector("@a", Component.text(", "));
        // Calls a method
        assertWriteReadEquality(comp);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void keybind() {
        // Calls a method
        var comp = Component.keybind("key.jump");
        // Calls a method
        assertWriteReadEquality(comp);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void textModifiedUtf8() {
        // Calls a method
        var comp = Component.text("abc\0\0def");
        // Calls a method
        assertWriteReadEquality(comp);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void hoverAction() {
        // Calls a method
        var comp = Component.text("hello").hoverEvent(Component.text("world"));
        // Calls a method
        assertWriteReadEquality(comp);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testObjectComponentHeadString() {
        // Calls a method
        var comp = Component.object(ObjectContents.playerHead("Hello"));
        // Calls a method
        assertWriteReadEquality(comp);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testObjectComponentHeadUUID() {
        // Calls a method
        var comp = Component.object(ObjectContents.playerHead(UUID.randomUUID()));
        // Calls a method
        assertWriteReadEquality(comp);
    // End of a block/expression
    }

    // Start of a method/block
    private static void assertWriteReadEquality(Component comp) {
        // Calls a method
        var array = NetworkBuffer.makeArray(buffer -> buffer.write(COMPONENT, comp));
        // Calls a method
        var buffer = NetworkBuffer.wrap(array, 0, array.length);
        // Calls a method
        var actual = NBT_READER.deserialize(buffer.read(NBT));
        // Calls a method
        assertEquals(comp, actual);
    // End of a block/expression
    }
// End of a block/expression
}
