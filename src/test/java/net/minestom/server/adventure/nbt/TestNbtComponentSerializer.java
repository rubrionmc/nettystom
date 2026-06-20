// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.nbt;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.ListBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.adventure.serializer.nbt.NbtComponentSerializer;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.kyori.adventure.nbt.StringBinaryTag.stringBinaryTag;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class TestNbtComponentSerializer {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testReadStringChildren() {
        // Affecte une valeur
        var tag = CompoundBinaryTag.builder()
                // Instruction de code
                .putString("text", "Hello")
                // Instruction de code
                .put("extra", ListBinaryTag.from(List.of(
                        // Instruction de code
                        stringBinaryTag(" "),
                        // Instruction de code
                        stringBinaryTag("World!")
                // Instruction de code
                )))
                // Appelle une méthode
                .build();
        // Appelle une méthode
        var deserialized = NbtComponentSerializer.nbt().deserialize(tag);

        // Appelle une méthode
        var expected = Component.text("Hello").appendSpace().append(Component.text("World!"));
        // Appelle une méthode
        assertEquals(expected, deserialized);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testWriteRead() {
        // Appelle une méthode
        var serializer = NbtComponentSerializer.nbt();
        // Appelle une méthode
        var comp = Component.text("Hello").appendSpace().append(Component.text("World!"));

        // Appelle une méthode
        var tag = serializer.serialize(comp);
        // Appelle une méthode
        var comp2 = serializer.deserialize(tag);

        // Appelle une méthode
        assertEquals(comp, comp2);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
