// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTagTypes;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.IntBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;
// Import d'une classe nécessaire
import org.junit.jupiter.api.condition.DisabledIf;
// Import d'une classe nécessaire
import org.junit.jupiter.api.condition.EnabledIf;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static net.kyori.adventure.nbt.IntArrayBinaryTag.intArrayBinaryTag;
// Import statique d'un membre
import static net.kyori.adventure.nbt.IntBinaryTag.intBinaryTag;
// Import statique d'un membre
import static net.kyori.adventure.nbt.ListBinaryTag.listBinaryTag;
// Import statique d'un membre
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Ensure that NBT tag can be read from other tags properly.
 */
// Déclaration de type (classe/interface/enum/record)
public class TagNbtTest {

    // Début d'une méthode/d'un bloc
    static boolean isSerializeEmptyCompoundEnabled() {
        // Renvoie une valeur à l'appelant
        return ServerFlag.SERIALIZE_EMPTY_COMPOUND;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void list() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.NBT("nbt").list();
        // Appelle une méthode
        List<BinaryTag> list = List.of(intBinaryTag(1), intBinaryTag(2), intBinaryTag(3));
        // Appelle une méthode
        handler.setTag(tag, list);
        // Appelle une méthode
        assertEquals(list, handler.getTag(tag));
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "nbt": [1,2,3]
                }
                """, handler.asCompound());

        // Appelle une méthode
        handler.removeTag(tag);
        // Appelle une méthode
        assertEqualsSNBT("{}", handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void map() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.NBT("nbt").map(nbt -> ((IntBinaryTag) nbt).value(), IntBinaryTag::intBinaryTag);
        // Appelle une méthode
        handler.setTag(tag, 5);
        // Appelle une méthode
        assertEquals(5, handler.getTag(tag));
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "nbt":5
                }
                """, handler.asCompound());

        // Appelle une méthode
        handler.removeTag(tag);
        // Appelle une méthode
        assertEqualsSNBT("{}", handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void fromCompoundModify() {
        // Appelle une méthode
        var compound = CompoundBinaryTag.builder().putInt("key", 5).build();
        // Appelle une méthode
        var handler = TagHandler.fromCompound(compound);
        // Appelle une méthode
        assertEquals(compound, handler.asCompound());
        // Instruction de code
        assertEqualsSNBT("""
                {"key":5}
                """, handler.asCompound());

        // Appelle une méthode
        handler.setTag(Tag.Integer("key"), 10);
        // Appelle une méthode
        assertEquals(10, handler.getTag(Tag.Integer("key")));
        // Instruction de code
        assertEqualsSNBT("""
                {"key":10}
                """, handler.asCompound());
        // Appelle une méthode
        handler.setTag(Tag.Integer("key"), 15);
        // Instruction de code
        assertEqualsSNBT("""
                {"key":15}
                """, handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void fromCompoundModifyPath() {
        // Appelle une méthode
        var compound = CompoundBinaryTag.builder().put("path", CompoundBinaryTag.builder().putInt("key", 5).build()).build();
        // Appelle une méthode
        var handler = TagHandler.fromCompound(compound);
        // Appelle une méthode
        var tag = Tag.Integer("key").path("path");

        // Appelle une méthode
        handler.setTag(tag, 10);
        // Appelle une méthode
        assertEquals(10, handler.getTag(tag));
        // Instruction de code
        assertEqualsSNBT("""
                {"path":{"key":10}}
                """, handler.asCompound());
        // Appelle une méthode
        handler.setTag(tag, 15);
        // Instruction de code
        assertEqualsSNBT("""
                {"path":{"key":15}}
                """, handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void fromCompoundModifyDoublePath() {
        // Affecte une valeur
        var compound = CompoundBinaryTag.builder().put("path", CompoundBinaryTag.builder()
                // Appelle une méthode
                .put("path2", CompoundBinaryTag.builder().putInt("key", 5).build()).build()).build();
        // Appelle une méthode
        var handler = TagHandler.fromCompound(compound);
        // Appelle une méthode
        var tag = Tag.Integer("key").path("path", "path2");

        // Appelle une méthode
        handler.setTag(tag, 10);
        // Appelle une méthode
        assertEquals(10, handler.getTag(tag));
        // Instruction de code
        assertEqualsSNBT("""
                {"path":{"path2":{"key":10}}}
                """, handler.asCompound());
        // Appelle une méthode
        handler.setTag(tag, 15);
        // Instruction de code
        assertEqualsSNBT("""
                {"path":{"path2":{"key":15}}}
                """, handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void compoundOverride() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var nbtTag = Tag.NBT("path1");

        // Appelle une méthode
        var nbt1 = CompoundBinaryTag.from(Map.of("key", intBinaryTag(5)));
        // Appelle une méthode
        var nbt2 = CompoundBinaryTag.from(Map.of("other-key", intBinaryTag(5)));
        // Appelle une méthode
        handler.setTag(nbtTag, nbt1);
        // Appelle une méthode
        assertEquals(nbt1, handler.getTag(nbtTag));

        // Appelle une méthode
        handler.setTag(nbtTag, nbt2);
        // Appelle une méthode
        assertEquals(nbt2, handler.getTag(nbtTag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void compoundRead() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var nbtTag = Tag.NBT("path1");

        // Appelle une méthode
        var nbt = CompoundBinaryTag.from(Map.of("key", intBinaryTag(5)));
        // Appelle une méthode
        handler.setTag(nbtTag, nbt);
        // Appelle une méthode
        assertEquals(nbt, handler.getTag(nbtTag));

        // Appelle une méthode
        var path = Tag.Integer("key").path("path1");
        // Appelle une méthode
        assertEquals(5, handler.getTag(path));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void compoundPathRead() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var nbtTag = Tag.NBT("compound").path("path");

        // Appelle une méthode
        var nbt = CompoundBinaryTag.from(Map.of("key", intBinaryTag(5)));
        // Appelle une méthode
        handler.setTag(nbtTag, nbt);
        // Appelle une méthode
        assertEquals(nbt, handler.getTag(nbtTag));

        // Appelle une méthode
        var path = Tag.Integer("key").path("path", "compound");
        // Appelle une méthode
        assertEquals(5, handler.getTag(path));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void doubleCompoundRead() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var nbtTag = Tag.NBT("path1");

        // Appelle une méthode
        var nbt = CompoundBinaryTag.from(Map.of("path2", CompoundBinaryTag.from(Map.of("key", intBinaryTag(5)))));
        // Appelle une méthode
        handler.setTag(nbtTag, nbt);
        // Appelle une méthode
        assertEquals(nbt, handler.getTag(nbtTag));

        // Appelle une méthode
        var path = Tag.Integer("key").path("path1", "path2");
        // Appelle une méthode
        assertEquals(5, handler.getTag(path));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void compoundWrite() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var nbtTag = Tag.NBT("path1");

        // Appelle une méthode
        var nbt = CompoundBinaryTag.from(Map.of("key", intBinaryTag(5)));
        // Appelle une méthode
        handler.setTag(nbtTag, nbt);
        // Appelle une méthode
        assertEquals(nbt, handler.getTag(nbtTag));

        // Appelle une méthode
        var path = Tag.Integer("key").path("path1");
        // Appelle une méthode
        handler.setTag(path, 10);
        // Appelle une méthode
        assertEquals(10, handler.getTag(path));
        // Appelle une méthode
        assertEquals(CompoundBinaryTag.from(Map.of("key", intBinaryTag(10))), handler.getTag(nbtTag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void rawList() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var nbtTag = Tag.NBT("list");
        // Appelle une méthode
        var list = listBinaryTag(BinaryTagTypes.INT, List.of(intBinaryTag(1)));
        // Appelle une méthode
        handler.setTag(nbtTag, list);
        // Appelle une méthode
        assertEquals(list, handler.getTag(nbtTag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void listConversion() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var nbtTag = Tag.NBT("list");
        // Appelle une méthode
        var listTag = Tag.Integer("list").list();
        // Appelle une méthode
        var list = listBinaryTag(BinaryTagTypes.INT, List.of(intBinaryTag(1)));
        // Appelle une méthode
        handler.setTag(nbtTag, list);

        // Appelle une méthode
        assertEquals(list, handler.getTag(nbtTag));
        // Appelle une méthode
        assertNotSame(list, handler.getTag(nbtTag));
        // Appelle une méthode
        assertEquals(List.of(1), handler.getTag(listTag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void rawArray() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var nbtTag = Tag.NBT("array");
        // Appelle une méthode
        var array = intArrayBinaryTag(1, 2, 3);
        // Appelle une méthode
        handler.setTag(nbtTag, array);
        // Appelle une méthode
        assertEquals(array, handler.getTag(nbtTag));
    // Fin d'un bloc/d'une expression
    }

    // from #2912
    // Annotation pour l'élément suivant
    @Test
    // Annotation pour l'élément suivant
    @EnabledIf("isSerializeEmptyCompoundEnabled")
    // Début d'une méthode/d'un bloc
    public void emptyCompoundSerialization() {
        // Appelle une méthode
        var tag = Tag.NBT("test");
        // Appelle une méthode
        var handler = TagHandler.newHandler();

        // Affecte une valeur
        var value = CompoundBinaryTag.builder()
                // Instruction de code
                .putString("type", "something")
                // Instruction de code
                .put("value", CompoundBinaryTag.empty())
                // Appelle une méthode
                .build();
        // Appelle une méthode
        handler.setTag(tag, value);

        // Appelle une méthode
        var nbt = handler.asCompound();
        // Appelle une méthode
        var newHandler = TagHandler.fromCompound(nbt);

        // Instruction de code
        assertEquals(value, newHandler.getTag(tag),
            // Instruction de code
            "Empty compound should be preserved during serialization when SERIALIZE_EMPTY_COMPOUND flag is enabled");

        // Instruction de code
        assertEqualsSNBT("""
                {
                  "test": {
                    "type": "something",
                    "value": {}
                  }
                }
                """, newHandler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Annotation pour l'élément suivant
    @DisabledIf("isSerializeEmptyCompoundEnabled")
    // Début d'une méthode/d'un bloc
    public void emptyCompoundSerializationDisabled() {
        // Appelle une méthode
        var tag = Tag.NBT("test");
        // Appelle une méthode
        var handler = TagHandler.newHandler();

        // Affecte une valeur
        var originalValue = CompoundBinaryTag.builder()
                // Instruction de code
                .putString("type", "something")
                // Instruction de code
                .put("value", CompoundBinaryTag.empty())
                // Appelle une méthode
                .build();
        // Appelle une méthode
        handler.setTag(tag, originalValue);

        // Appelle une méthode
        var nbt = handler.asCompound();
        // Appelle une méthode
        var newHandler = TagHandler.fromCompound(nbt);
        // Appelle une méthode
        var deserializedValue = newHandler.getTag(tag);

        // Affecte une valeur
        var expectedValue = CompoundBinaryTag.builder()
                // Instruction de code
                .putString("type", "something")
                // Appelle une méthode
                .build();
        // Instruction de code
        assertEquals(expectedValue, deserializedValue,
            // Instruction de code
            "Empty compound should be stripped during serialization when SERIALIZE_EMPTY_COMPOUND flag is disabled");

        // Instruction de code
        assertEqualsSNBT("""
                {
                  "test": {
                    "type": "something"
                  }
                }
                """, newHandler.asCompound());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
