// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.IntArrayBinaryTag;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.UUID;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class TagUuidTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void get() {
        // Appelle une méthode
        var uuid = UUID.randomUUID();
        // Appelle une méthode
        var tag = Tag.UUID("uuid");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(tag, uuid);
        // Appelle une méthode
        assertEquals(uuid, handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void empty() {
        // Appelle une méthode
        var tag = Tag.UUID("uuid");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        assertNull(handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void invalidTag() {
        // Appelle une méthode
        var tag = Tag.UUID("entry");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(Tag.Integer("entry"), 1);
        // Appelle une méthode
        assertNull(handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void toNbt() {
        // Appelle une méthode
        var tag = Tag.UUID("uuid");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(tag, UUID.fromString("9ab8ca63-3d7b-43ba-b805-a20a352dae9c"));
        // Appelle une méthode
        var nbt = handler.asCompound();
        // Appelle une méthode
        IntArrayBinaryTag array = (IntArrayBinaryTag) nbt.get("uuid");
        // Appelle une méthode
        assertArrayEquals(new int[]{-1699165597, 1031488442, -1207590390, 892186268}, array.value());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void fromNbt() {
        // Appelle une méthode
        var tag = Tag.UUID("uuid");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(Tag.NBT("uuid"), IntArrayBinaryTag.intArrayBinaryTag(-1699165597, 1031488442, -1207590390, 892186268));
        // Appelle une méthode
        assertEquals(UUID.fromString("9ab8ca63-3d7b-43ba-b805-a20a352dae9c"), handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
