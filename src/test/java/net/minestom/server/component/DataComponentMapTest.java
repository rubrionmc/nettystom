// Déclaration du paquet de ce fichier
package net.minestom.server.component;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class DataComponentMapTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testBasicGet() {
        // Affecte une valeur
        var map = DataComponentMap.patchBuilder()
                // Instruction de code
                .set(DataComponents.REPAIR_COST, 10)
                // Instruction de code
                .remove(DataComponents.CUSTOM_NAME)
                // Appelle une méthode
                .build();

        // Appelle une méthode
        assertTrue(map.has(DataComponents.REPAIR_COST));
        // Appelle une méthode
        assertEquals(10, map.get(DataComponents.REPAIR_COST));

        // Appelle une méthode
        assertFalse(map.has(DataComponents.CUSTOM_NAME));
        // Appelle une méthode
        assertNull(map.get(DataComponents.CUSTOM_NAME));

        // Appelle une méthode
        assertFalse(map.has(DataComponents.BANNER_PATTERNS));
        // Appelle une méthode
        assertNull(map.get(DataComponents.BANNER_PATTERNS));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testPatchedGet() {
        // Affecte une valeur
        var prototype = DataComponentMap.patchBuilder()
                // Instruction de code
                .set(DataComponents.ITEM_NAME, Component.text("Hello"))
                // Instruction de code
                .set(DataComponents.REPAIR_COST, 55)
                // Instruction de code
                .set(DataComponents.CUSTOM_NAME, Component.text("World"))
                // Appelle une méthode
                .build();
        // Affecte une valeur
        var map = DataComponentMap.patchBuilder()
                // Instruction de code
                .set(DataComponents.REPAIR_COST, 1)
                // Instruction de code
                .remove(DataComponents.CUSTOM_NAME)
                // Appelle une méthode
                .build();

        // Override
        // Appelle une méthode
        assertTrue(map.has(prototype, DataComponents.REPAIR_COST));
        // Appelle une méthode
        assertEquals(1, map.get(prototype, DataComponents.REPAIR_COST));

        // Inherit
        // Appelle une méthode
        assertTrue(map.has(prototype, DataComponents.ITEM_NAME));
        // Appelle une méthode
        assertEquals(Component.text("Hello"), map.get(prototype, DataComponents.ITEM_NAME));

        // Delete
        // Appelle une méthode
        assertFalse(map.has(prototype, DataComponents.CUSTOM_NAME));
        // Appelle une méthode
        assertNull(map.get(prototype, DataComponents.CUSTOM_NAME));

        // Non-existent
        // Appelle une méthode
        assertFalse(map.has(prototype, DataComponents.BANNER_PATTERNS));
        // Appelle une méthode
        assertNull(map.get(prototype, DataComponents.BANNER_PATTERNS));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testDiffEmpty() {
        // Appelle une méthode
        var prototype = DataComponentMap.patchBuilder().set(DataComponents.REPAIR_COST, 42).build();
        // Affecte une valeur
        var map = DataComponentMap.EMPTY;
        // Appelle une méthode
        var diff = DataComponentMap.diff(prototype, map);

        // Appelle une méthode
        assertNull(diff.get(DataComponents.REPAIR_COST));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testDiffCompleteDifference() {
        // Appelle une méthode
        var prototype = DataComponentMap.patchBuilder().set(DataComponents.REPAIR_COST, 42).build();
        // Appelle une méthode
        var map = DataComponentMap.patchBuilder().set(DataComponents.CUSTOM_NAME, Component.text("Hello")).build();
        // Appelle une méthode
        var diff = DataComponentMap.diff(prototype, map);

        // Appelle une méthode
        assertNull(diff.get(DataComponents.REPAIR_COST));
        // Appelle une méthode
        assertEquals(Component.text("Hello"), diff.get(DataComponents.CUSTOM_NAME));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testDiffFlatten() {
        // Appelle une méthode
        var prototype = DataComponentMap.builder().set(DataComponents.REPAIR_COST, 42).build();
        // Appelle une méthode
        var map = DataComponentMap.builder().set(DataComponents.REPAIR_COST, 24).build();
        // Appelle une méthode
        var diff = DataComponentMap.diff(prototype, map);

        // Appelle une méthode
        assertEquals(24, diff.get(DataComponents.REPAIR_COST));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testBuilder() {
        // Appelle une méthode
        var builder = DataComponentMap.builder();
        // Appelle une méthode
        builder.set(DataComponents.REPAIR_COST, 42);

        // Builder is a getter for its own entries, so this should be valid
        // Appelle une méthode
        assertEquals(42, builder.get(DataComponents.REPAIR_COST));
        // Appelle une méthode
        var map1 = builder.build();
        // Appelle une méthode
        assertEquals(42, map1.get(DataComponents.REPAIR_COST));

        // Old built map should be unaffected by change
        // Appelle une méthode
        builder.set(DataComponents.REPAIR_COST, 24);
        // Appelle une méthode
        var map2 = builder.build();
        // Appelle une méthode
        assertEquals(42, map1.get(DataComponents.REPAIR_COST));
        // Appelle une méthode
        assertEquals(24, map2.get(DataComponents.REPAIR_COST));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
