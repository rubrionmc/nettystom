// Package declaration for this file
package net.minestom.server.codec;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.IntBinaryTag;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.event.ClickEvent;
// Import of a required class
import net.kyori.adventure.text.event.HoverEvent;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.kyori.adventure.text.object.ObjectContents;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.util.UUID;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
class ComponentCodecsTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    void readExpandFromStringInList() throws Exception {
        // Calls a method
        var input = MinestomAdventure.tagStringIO().asTag("{extra:[{color:\"red\",text:\"Hello\"},\" World\"],text:\"\"}");
        // Calls a method
        var actual = ComponentCodecs.COMPONENT.decode(Transcoder.NBT, input).orElseThrow();
        // Assigns a value
        var expected = Component.text()
                // Code statement
                .append(Component.text("Hello", NamedTextColor.RED))
                // Code statement
                .append(Component.text(" World"))
                // Calls a method
                .build();
        // Calls a method
        assertEquals(expected, actual);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void writeFlattenToInList() throws IOException {
        // Assigns a value
        var component = Component.text()
                // Code statement
                .append(Component.text("Hello", NamedTextColor.RED))
                // Code statement
                .append(Component.text(" World"))
                // Calls a method
                .build();
        // Calls a method
        var nbt = ComponentCodecs.COMPONENT.encode(Transcoder.NBT, component).orElseThrow();
        // Calls a method
        assertEquals("{extra:[{color:\"red\",text:\"Hello\"},\" World\"],text:\"\"}", MinestomAdventure.tagStringIO().asString(nbt));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void writeCustomClickEvent() throws IOException {
        // Assigns a value
        var component = Component.text("Click me!").clickEvent(ClickEvent.custom(
                // Calls a method
                Key.key("hello:world"), MinestomAdventure.wrapNbt(IntBinaryTag.intBinaryTag(55))));
        // Calls a method
        var nbt = ComponentCodecs.COMPONENT.encode(Transcoder.NBT, component).orElseThrow();
        // Code statement
        assertEquals("{click_event:{payload:55,action:\"custom\",id:\"hello:world\"},text:\"Click me!\"}",
                // Calls a method
                MinestomAdventure.tagStringIO().asString(nbt));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void readCustomClickEvent() throws IOException {
        // Calls a method
        var input = MinestomAdventure.tagStringIO().asTag("{click_event:{payload:55,action:\"custom\",id:\"hello:world\"},text:\"Click me!\"}");
        // Calls a method
        var actual = ComponentCodecs.COMPONENT.decode(Transcoder.NBT, input).orElseThrow();
        // Assigns a value
        var expected = Component.text("Click me!").clickEvent(ClickEvent.custom(
                // Calls a method
                Key.key("hello:world"), MinestomAdventure.wrapNbt(IntBinaryTag.intBinaryTag(55))));
        // Calls a method
        assertEquals(expected, actual);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void readShowEntityWithoutName() {
        // Calls a method
        UUID uuid = UUID.randomUUID();

        // Assigns a value
        var input = CompoundBinaryTag.builder()
                // Code statement
                .putString("text", "hover")
                // Code statement
                .put("hover_event", CompoundBinaryTag.builder()
                        // Code statement
                        .putString("action", "show_entity")
                        // Code statement
                        .putString("id", "minecraft:player")
                        // Code statement
                        .putString("uuid", uuid.toString())
                        // Code statement
                        .build())
                // Calls a method
                .build();

        // Calls a method
        var actual = ComponentCodecs.COMPONENT.decode(Transcoder.NBT, input).orElseThrow();
        // Assigns a value
        var expected = Component.text("hover")
                // Code statement
                .hoverEvent(HoverEvent.showEntity(
                        // Code statement
                        Key.key("minecraft:player"),
                        // Code statement
                        uuid,
                        // Code statement
                        null
                // Code statement
                ));

        // Calls a method
        assertEquals(expected, actual);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void readObjectFallback() {
        // Assigns a value
        var input = CompoundBinaryTag.builder()
                // Code statement
                .putString("type", "object")
                // Code statement
                .putString("sprite", "missing")
                // Code statement
                .putString("fallback", "Missing")
                // Calls a method
                .build();

        // Calls a method
        var actual = ComponentCodecs.COMPONENT.decode(Transcoder.NBT, input).orElseThrow();
        // Assigns a value
        var expected = Component.object()
                // Code statement
                .contents(ObjectContents.sprite(Key.key("missing")))
                // Code statement
                .fallback(Component.text("Missing"))
                // Calls a method
                .build();

        // Calls a method
        assertEquals(expected, actual);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void writeObjectFallback() {
        // Assigns a value
        var component = Component.object()
                // Code statement
                .contents(ObjectContents.sprite(Key.key("missing")))
                // Code statement
                .fallback(Component.text("Missing"))
                // Calls a method
                .build();

        // Calls a method
        var nbt = ComponentCodecs.COMPONENT.encode(Transcoder.NBT, component).orElseThrow();
        // Calls a method
        var actual = ComponentCodecs.COMPONENT.decode(Transcoder.NBT, nbt).orElseThrow();

        // Calls a method
        assertEquals(component, actual);
    // End of a block/expression
    }
// End of a block/expression
}
