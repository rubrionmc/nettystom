// Déclaration du paquet de ce fichier
package net.minestom.server.codec;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.IntBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.ClickEvent;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.HoverEvent;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.kyori.adventure.text.object.ObjectContents;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.util.UUID;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
class ComponentCodecsTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void readExpandFromStringInList() throws Exception {
        // Appelle une méthode
        var input = MinestomAdventure.tagStringIO().asTag("{extra:[{color:\"red\",text:\"Hello\"},\" World\"],text:\"\"}");
        // Appelle une méthode
        var actual = ComponentCodecs.COMPONENT.decode(Transcoder.NBT, input).orElseThrow();
        // Affecte une valeur
        var expected = Component.text()
                // Instruction de code
                .append(Component.text("Hello", NamedTextColor.RED))
                // Instruction de code
                .append(Component.text(" World"))
                // Appelle une méthode
                .build();
        // Appelle une méthode
        assertEquals(expected, actual);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void writeFlattenToInList() throws IOException {
        // Affecte une valeur
        var component = Component.text()
                // Instruction de code
                .append(Component.text("Hello", NamedTextColor.RED))
                // Instruction de code
                .append(Component.text(" World"))
                // Appelle une méthode
                .build();
        // Appelle une méthode
        var nbt = ComponentCodecs.COMPONENT.encode(Transcoder.NBT, component).orElseThrow();
        // Appelle une méthode
        assertEquals("{extra:[{color:\"red\",text:\"Hello\"},\" World\"],text:\"\"}", MinestomAdventure.tagStringIO().asString(nbt));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void writeCustomClickEvent() throws IOException {
        // Affecte une valeur
        var component = Component.text("Click me!").clickEvent(ClickEvent.custom(
                // Appelle une méthode
                Key.key("hello:world"), MinestomAdventure.wrapNbt(IntBinaryTag.intBinaryTag(55))));
        // Appelle une méthode
        var nbt = ComponentCodecs.COMPONENT.encode(Transcoder.NBT, component).orElseThrow();
        // Instruction de code
        assertEquals("{click_event:{payload:55,action:\"custom\",id:\"hello:world\"},text:\"Click me!\"}",
                // Appelle une méthode
                MinestomAdventure.tagStringIO().asString(nbt));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void readCustomClickEvent() throws IOException {
        // Appelle une méthode
        var input = MinestomAdventure.tagStringIO().asTag("{click_event:{payload:55,action:\"custom\",id:\"hello:world\"},text:\"Click me!\"}");
        // Appelle une méthode
        var actual = ComponentCodecs.COMPONENT.decode(Transcoder.NBT, input).orElseThrow();
        // Affecte une valeur
        var expected = Component.text("Click me!").clickEvent(ClickEvent.custom(
                // Appelle une méthode
                Key.key("hello:world"), MinestomAdventure.wrapNbt(IntBinaryTag.intBinaryTag(55))));
        // Appelle une méthode
        assertEquals(expected, actual);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void readShowEntityWithoutName() {
        // Appelle une méthode
        UUID uuid = UUID.randomUUID();

        // Affecte une valeur
        var input = CompoundBinaryTag.builder()
                // Instruction de code
                .putString("text", "hover")
                // Instruction de code
                .put("hover_event", CompoundBinaryTag.builder()
                        // Instruction de code
                        .putString("action", "show_entity")
                        // Instruction de code
                        .putString("id", "minecraft:player")
                        // Instruction de code
                        .putString("uuid", uuid.toString())
                        // Instruction de code
                        .build())
                // Appelle une méthode
                .build();

        // Appelle une méthode
        var actual = ComponentCodecs.COMPONENT.decode(Transcoder.NBT, input).orElseThrow();
        // Affecte une valeur
        var expected = Component.text("hover")
                // Instruction de code
                .hoverEvent(HoverEvent.showEntity(
                        // Instruction de code
                        Key.key("minecraft:player"),
                        // Instruction de code
                        uuid,
                        // Instruction de code
                        null
                // Instruction de code
                ));

        // Appelle une méthode
        assertEquals(expected, actual);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void readObjectFallback() {
        // Affecte une valeur
        var input = CompoundBinaryTag.builder()
                // Instruction de code
                .putString("type", "object")
                // Instruction de code
                .putString("sprite", "missing")
                // Instruction de code
                .putString("fallback", "Missing")
                // Appelle une méthode
                .build();

        // Appelle une méthode
        var actual = ComponentCodecs.COMPONENT.decode(Transcoder.NBT, input).orElseThrow();
        // Affecte une valeur
        var expected = Component.object()
                // Instruction de code
                .contents(ObjectContents.sprite(Key.key("missing")))
                // Instruction de code
                .fallback(Component.text("Missing"))
                // Appelle une méthode
                .build();

        // Appelle une méthode
        assertEquals(expected, actual);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void writeObjectFallback() {
        // Affecte une valeur
        var component = Component.object()
                // Instruction de code
                .contents(ObjectContents.sprite(Key.key("missing")))
                // Instruction de code
                .fallback(Component.text("Missing"))
                // Appelle une méthode
                .build();

        // Appelle une méthode
        var nbt = ComponentCodecs.COMPONENT.encode(Transcoder.NBT, component).orElseThrow();
        // Appelle une méthode
        var actual = ComponentCodecs.COMPONENT.decode(Transcoder.NBT, nbt).orElseThrow();

        // Appelle une méthode
        assertEquals(component, actual);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
