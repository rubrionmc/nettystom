// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.*;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.HashSet;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Set;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class TagNbtSeparatorTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void primitives() {
        // Instruction de code
        assertSeparation(new TagNbtSeparator.Entry<>(Tag.Byte("key"), (byte) 1),
                // Appelle une méthode
                "key", ByteBinaryTag.byteBinaryTag((byte) 1));
        // Instruction de code
        assertSeparation(new TagNbtSeparator.Entry<>(Tag.Short("key"), (short) 1),
                // Appelle une méthode
                "key", ShortBinaryTag.shortBinaryTag((short) 1));
        // Instruction de code
        assertSeparation(new TagNbtSeparator.Entry<>(Tag.Integer("key"), 1),
                // Appelle une méthode
                "key", IntBinaryTag.intBinaryTag(1));
        // Instruction de code
        assertSeparation(new TagNbtSeparator.Entry<>(Tag.Long("key"), 1L),
                // Appelle une méthode
                "key", LongBinaryTag.longBinaryTag(1));
        // Instruction de code
        assertSeparation(new TagNbtSeparator.Entry<>(Tag.Float("key"), 1f),
                // Appelle une méthode
                "key", FloatBinaryTag.floatBinaryTag(1));
        // Instruction de code
        assertSeparation(new TagNbtSeparator.Entry<>(Tag.Double("key"), 1d),
                // Appelle une méthode
                "key", DoubleBinaryTag.doubleBinaryTag(1));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void compound() {
        // Instruction de code
        assertSeparation(new TagNbtSeparator.Entry<>(Tag.Byte("key").path("path"), (byte) 1),
                // Appelle une méthode
                "path", CompoundBinaryTag.builder().putByte("key", (byte) 1).build());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void compoundMultiple() {
        // Instruction de code
        assertSeparation(Set.of(new TagNbtSeparator.Entry<>(Tag.Byte("key").path("path"), (byte) 1),
                        // Crée un nouvel objet
                        new TagNbtSeparator.Entry<>(Tag.Integer("key2").path("path"), 2)),
                // Appelle une méthode
                "path", CompoundBinaryTag.builder().putByte("key", (byte) 1).putInt("key2", 2).build());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void list() {
        // Instruction de code
        assertSeparation(new TagNbtSeparator.Entry<>(Tag.Integer("key").list(), List.of(1)),
                // Appelle une méthode
                "key", ListBinaryTag.listBinaryTag(BinaryTagTypes.INT, List.of(IntBinaryTag.intBinaryTag(1))));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void assertSeparation(Set<TagNbtSeparator.Entry<?>> expected, String key, BinaryTag nbt) {
        // Appelle une méthode
        assertEquals(expected, retrieve(key, nbt));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void assertSeparation(TagNbtSeparator.Entry<?> expected, String key, BinaryTag nbt) {
        // Appelle une méthode
        var entries = retrieve(key, nbt);
        // Appelle une méthode
        assertEquals(1, entries.size());
        // Appelle une méthode
        assertEquals(expected, entries.iterator().next());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    Set<TagNbtSeparator.Entry<?>> retrieve(String key, BinaryTag nbt) {
        // Affecte une valeur
        Set<TagNbtSeparator.Entry<?>> entries = new HashSet<>();
        // Appelle une méthode
        TagNbtSeparator.separate(key, nbt, entries::add);
        // Renvoie une valeur à l'appelant
        return Set.copyOf(entries);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
