// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.object.ObjectContents;
// Import d'une classe nécessaire
import net.minestom.server.adventure.serializer.nbt.NbtComponentSerializer;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.UUID;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.COMPONENT;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.NBT;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class ComponentNetworkBufferTypeTest {
    // All of these tests use NbtComponentSerializerImpl as the source of truth. If there is an inaccuracy in that
    // implementation, these tests will not be accurate. This will be replaced with the adventure serializer once
    // it is merged into adventure (see https://github.com/KyoriPowered/adventure/pull/1084). This can be considered
    // a known-good implementation.

    // Appelle une méthode
    private static final ComponentNetworkBufferTypeImpl WRITER = new ComponentNetworkBufferTypeImpl();
    // Appelle une méthode
    private static final NbtComponentSerializer NBT_READER = NbtComponentSerializer.nbt();

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void empty() {
        // Appelle une méthode
        var comp = Component.empty();
        // Appelle une méthode
        assertWriteReadEquality(comp);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void text() {
        // Appelle une méthode
        var comp = Component.text("Hello, world!");
        // Appelle une méthode
        assertWriteReadEquality(comp);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void textChildren() {
        // Affecte une valeur
        var comp = Component.text("Hello, world!").children(List.of(
                // Instruction de code
                Component.text("child 1"),
                // Instruction de code
                Component.text("child 2")
        // Instruction de code
        ));
        // Appelle une méthode
        assertWriteReadEquality(comp);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void translatable() {
        // Appelle une méthode
        var comp = Component.translatable("a.b.c", "I am fallback", Component.text("arg1"), Component.text("arg2"));
        // Appelle une méthode
        assertWriteReadEquality(comp);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void score() {
        // Appelle une méthode
        var comp = Component.score("test123", "obj");
        // Appelle une méthode
        assertWriteReadEquality(comp);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void selector() {
        // Appelle une méthode
        var comp = Component.selector("@a", Component.text(", "));
        // Appelle une méthode
        assertWriteReadEquality(comp);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void keybind() {
        // Appelle une méthode
        var comp = Component.keybind("key.jump");
        // Appelle une méthode
        assertWriteReadEquality(comp);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void textModifiedUtf8() {
        // Appelle une méthode
        var comp = Component.text("abc\0\0def");
        // Appelle une méthode
        assertWriteReadEquality(comp);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void hoverAction() {
        // Appelle une méthode
        var comp = Component.text("hello").hoverEvent(Component.text("world"));
        // Appelle une méthode
        assertWriteReadEquality(comp);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testObjectComponentHeadString() {
        // Appelle une méthode
        var comp = Component.object(ObjectContents.playerHead("Hello"));
        // Appelle une méthode
        assertWriteReadEquality(comp);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testObjectComponentHeadUUID() {
        // Appelle une méthode
        var comp = Component.object(ObjectContents.playerHead(UUID.randomUUID()));
        // Appelle une méthode
        assertWriteReadEquality(comp);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void assertWriteReadEquality(Component comp) {
        // Appelle une méthode
        var array = NetworkBuffer.makeArray(buffer -> buffer.write(COMPONENT, comp));
        // Appelle une méthode
        var buffer = NetworkBuffer.wrap(array, 0, array.length);
        // Appelle une méthode
        var actual = NBT_READER.deserialize(buffer.read(NBT));
        // Appelle une méthode
        assertEquals(comp, actual);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
