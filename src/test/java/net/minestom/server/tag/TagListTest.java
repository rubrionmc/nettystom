// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class TagListTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void basic() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        Tag<Integer> tag = Tag.Integer("number");
        // Appelle une méthode
        Tag<List<Integer>> list = tag.list();

        // Appelle une méthode
        handler.setTag(tag, 5);
        // Appelle une méthode
        assertEquals(5, handler.getTag(tag));
        // Appelle une méthode
        assertNull(handler.getTag(list));

        // Appelle une méthode
        handler.setTag(list, List.of(1, 2, 3));
        // Appelle une méthode
        assertEquals(List.of(1, 2, 3), handler.getTag(list));
        // Appelle une méthode
        assertNull(handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cache() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Integer("number").list();
        // Appelle une méthode
        var val = List.of(1, 2, 3);

        // Appelle une méthode
        handler.setTag(tag, val);
        // Appelle une méthode
        assertSame(val, handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void recursiveCache() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Integer("number").list().list();
        // Appelle une méthode
        var val = List.of(List.of(1, 2, 3), List.of(4, 5, 6));

        // Appelle une méthode
        handler.setTag(tag, val);
        // Appelle une méthode
        assertSame(val.get(0), handler.getTag(tag).get(0));
        // Appelle une méthode
        assertSame(val.get(1), handler.getTag(tag).get(1));
        // Appelle une méthode
        assertSame(val, handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void recursiveCacheIncorrect() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Integer("number").list().list();
        // Appelle une méthode
        var val = List.of(List.of(1, 2, 3), new ArrayList<>(Arrays.asList(4, 5, 6)));

        // Appelle une méthode
        handler.setTag(tag, val);
        // Appelle une méthode
        assertSame(val.get(0), handler.getTag(tag).get(0));
        // Appelle une méthode
        assertNotSame(val.get(1), handler.getTag(tag).get(1));
        // Appelle une méthode
        assertNotSame(val, handler.getTag(tag));
        // Appelle une méthode
        assertEquals(val, handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void snbt() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        Tag<List<Integer>> tag = Tag.Integer("numbers").list();

        // Appelle une méthode
        handler.setTag(tag, List.of(1, 2, 3));
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "numbers": [1,2,3]
                }
                """, handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void empty() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        Tag<List<Integer>> tag = Tag.Integer("numbers").list();
        // Appelle une méthode
        handler.setTag(tag, List.of());
        // Appelle une méthode
        assertEquals(List.of(), handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void emptySnbt() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        Tag<List<Integer>> tag = Tag.Integer("numbers").list();
        // Appelle une méthode
        handler.setTag(tag, List.of());
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "numbers":[]
                }
                """, handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void removal() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        Tag<List<Integer>> tag = Tag.Integer("numbers").list();
        // Appelle une méthode
        handler.setTag(tag, List.of(1));
        // Appelle une méthode
        assertEquals(List.of(1), handler.getTag(tag));
        // Appelle une méthode
        handler.removeTag(tag);
        // Appelle une méthode
        assertNull(handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void removalSnbt() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        Tag<List<Integer>> tag = Tag.Integer("numbers").list();
        // Appelle une méthode
        handler.setTag(tag, List.of(1));
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "numbers": [1]
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
    public void chaining() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        Tag<List<List<Integer>>> tag = Tag.Integer("numbers").list().list();
        // Appelle une méthode
        var integers = List.of(List.of(1, 2, 3), List.of(4, 5, 6));
        // Appelle une méthode
        handler.setTag(tag, integers);
        // Appelle une méthode
        assertEquals(integers, handler.getTag(tag));
        // Appelle une méthode
        handler.removeTag(tag);
        // Appelle une méthode
        assertNull(handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chainingSnbt() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        Tag<List<List<Integer>>> tag = Tag.Integer("numbers").list().list();
        // Appelle une méthode
        var integers = List.of(List.of(1, 2, 3), List.of(4, 5, 6));
        // Appelle une méthode
        handler.setTag(tag, integers);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "numbers":[
                    [1,2,3],
                    [4,5,6]
                  ]
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
    public void defaultValue() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var val = List.of(1, 2, 3);
        // Appelle une méthode
        var tag = Tag.Integer("number").list().defaultValue(val);
        // Appelle une méthode
        assertEquals(List.of(1, 2, 3), handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void defaultValueReset() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Integer("number").defaultValue(5);
        // Appelle une méthode
        var list = tag.list();
        // Appelle une méthode
        assertNull(handler.getTag(list));
        // Appelle une méthode
        assertEquals(List.of(1, 2, 3), handler.getTag(list.defaultValue(List.of(1, 2, 3))));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void immutability() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Integer("number").list();
        // Appelle une méthode
        List<Integer> val = new ArrayList<>();
        // Appelle une méthode
        val.add(1);

        // Appelle une méthode
        handler.setTag(tag, val);
        // Appelle une méthode
        assertNotSame(val, handler.getTag(tag));
        // Appelle une méthode
        assertEquals(List.of(1), handler.getTag(tag));

        // Instruction de code
        val.add(2); // Must not modify the nbt
        // Appelle une méthode
        assertNotSame(val, handler.getTag(tag));
        // Appelle une méthode
        assertEquals(List.of(1), handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chainingImmutability() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        Tag<List<List<Integer>>> tag = Tag.Integer("numbers").list().list();
        // Appelle une méthode
        List<List<Integer>> val = new ArrayList<>();
        // Appelle une méthode
        val.add(new ArrayList<>(Arrays.asList(1, 2, 3)));
        // Appelle une méthode
        val.add(new ArrayList<>(Arrays.asList(4, 5, 6)));

        // Appelle une méthode
        handler.setTag(tag, val);
        // Appelle une méthode
        assertNotSame(val, handler.getTag(tag));
        // Appelle une méthode
        assertEquals(List.of(List.of(1, 2, 3), List.of(4, 5, 6)), handler.getTag(tag));

        // Must not modify the nbt
        // Appelle une méthode
        val.get(0).add(7);
        // Appelle une méthode
        val.get(1).add(8);
        // Appelle une méthode
        val.add(new ArrayList<>(Arrays.asList(9, 10, 11)));
        // Appelle une méthode
        assertNotSame(val, handler.getTag(tag));
        // Appelle une méthode
        assertEquals(List.of(List.of(1, 2, 3), List.of(4, 5, 6)), handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void immutabilitySnbt() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var tag = Tag.Integer("numbers").list();
        // Appelle une méthode
        List<Integer> val = new ArrayList<>();
        // Appelle une méthode
        val.add(1);

        // Appelle une méthode
        handler.setTag(tag, val);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "numbers": [1]
                }
                """, handler.asCompound());

        // Instruction de code
        val.add(2); // Must not modify the nbt
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "numbers": [1]
                }
                """, handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chainingImmutabilitySnbt() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        Tag<List<List<Integer>>> tag = Tag.Integer("numbers").list().list();
        // Appelle une méthode
        List<List<Integer>> val = new ArrayList<>();
        // Appelle une méthode
        val.add(new ArrayList<>(Arrays.asList(1, 2, 3)));
        // Appelle une méthode
        val.add(new ArrayList<>(Arrays.asList(4, 5, 6)));

        // Appelle une méthode
        handler.setTag(tag, val);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "numbers":[
                    [1,2,3],
                    [4,5,6]
                  ]
                }
                """, handler.asCompound());


        // Must not modify the nbt
        // Appelle une méthode
        val.get(0).add(7);
        // Appelle une méthode
        val.get(1).add(8);
        // Appelle une méthode
        val.add(new ArrayList<>(Arrays.asList(9, 10, 11)));
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "numbers":[
                    [1,2,3],
                    [4,5,6]
                  ]
                }
                """, handler.asCompound());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
