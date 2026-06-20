// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.StringBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class TagRecordTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void basic() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Structure("vec", Vec.class);
        // Appelle une méthode
        var vec = new Vec(1, 2, 3);
        // Appelle une méthode
        assertNull(handler.getTag(tag));
        // Appelle une méthode
        handler.setTag(tag, vec);
        // Appelle une méthode
        assertEquals(vec, handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void fromNBT() {
        // Affecte une valeur
        var vecCompound = CompoundBinaryTag.builder()
                // Instruction de code
                .putDouble("x", 1)
                // Instruction de code
                .putDouble("y", 2)
                // Instruction de code
                .putDouble("z", 3)
                // Appelle une méthode
                .build();
        // Appelle une méthode
        var handler = TagHandler.fromCompound(CompoundBinaryTag.from(Map.of("vec", vecCompound)));
        // Appelle une méthode
        var tag = Tag.Structure("vec", Vec.class);
        // Appelle une méthode
        assertEquals(new Vec(1, 2, 3), handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void fromNBTView() {
        // Affecte une valeur
        var handler = TagHandler.fromCompound(CompoundBinaryTag.builder()
                // Instruction de code
                .putDouble("x", 1)
                // Instruction de code
                .putDouble("y", 2)
                // Instruction de code
                .putDouble("z", 3)
                // Appelle une méthode
                .build());
        // Appelle une méthode
        var tag = Tag.View(Vec.class);
        // Appelle une méthode
        assertEquals(new Vec(1, 2, 3), handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void basicSerializer() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var serializer = TagRecord.serializer(Vec.class);
        // Appelle une méthode
        serializer.write(handler, new Vec(1, 2, 3));
        // Appelle une méthode
        assertEquals(new Vec(1, 2, 3), serializer.read(handler));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void basicSnbt() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Structure("vec", Vec.class);
        // Appelle une méthode
        var vec = new Vec(1, 2, 3);
        // Appelle une méthode
        handler.setTag(tag, vec);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "vec": {
                    "x":1D,
                    "y":2D,
                    "z":3D
                  }
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
    public void nbtSerializer() {
        // Déclaration de type (classe/interface/enum/record)
        record CompoundRecord(CompoundBinaryTag compound) {
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        var test = new CompoundRecord(CompoundBinaryTag.from(Map.of("key", StringBinaryTag.stringBinaryTag("value"))));
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var serializer = TagRecord.serializer(CompoundRecord.class);
        // Appelle une méthode
        serializer.write(handler, test);
        // Appelle une méthode
        assertEquals(test, serializer.read(handler));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void unsupportedList() {
        // Déclaration de type (classe/interface/enum/record)
        record Test(List<Object> list) {
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> Tag.Structure("test", Test.class));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void unsupportedArray() {
        // Déclaration de type (classe/interface/enum/record)
        record Test(Object[] array) {
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> Tag.Structure("test", Test.class));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void forceRecord() {
        // Appelle une méthode
        assertThrows(Throwable.class, () -> Tag.Structure("entity", Class.class.cast(Entity.class)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void invalidItem() {
        // ItemStack cannot become a record due to `ItemStack#toItemNBT` being serialized differently, and independently of
        // the item record components
        // Appelle une méthode
        assertThrows(Throwable.class, () -> Tag.Structure("item", Class.class.cast(ItemStack.class)));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
