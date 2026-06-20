// Package declaration for this file
package net.minestom.server.component;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class DataComponentMapTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testBasicGet() {
        // Assigns a value
        var map = DataComponentMap.patchBuilder()
                // Code statement
                .set(DataComponents.REPAIR_COST, 10)
                // Code statement
                .remove(DataComponents.CUSTOM_NAME)
                // Calls a method
                .build();

        // Calls a method
        assertTrue(map.has(DataComponents.REPAIR_COST));
        // Calls a method
        assertEquals(10, map.get(DataComponents.REPAIR_COST));

        // Calls a method
        assertFalse(map.has(DataComponents.CUSTOM_NAME));
        // Calls a method
        assertNull(map.get(DataComponents.CUSTOM_NAME));

        // Calls a method
        assertFalse(map.has(DataComponents.BANNER_PATTERNS));
        // Calls a method
        assertNull(map.get(DataComponents.BANNER_PATTERNS));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testPatchedGet() {
        // Assigns a value
        var prototype = DataComponentMap.patchBuilder()
                // Code statement
                .set(DataComponents.ITEM_NAME, Component.text("Hello"))
                // Code statement
                .set(DataComponents.REPAIR_COST, 55)
                // Code statement
                .set(DataComponents.CUSTOM_NAME, Component.text("World"))
                // Calls a method
                .build();
        // Assigns a value
        var map = DataComponentMap.patchBuilder()
                // Code statement
                .set(DataComponents.REPAIR_COST, 1)
                // Code statement
                .remove(DataComponents.CUSTOM_NAME)
                // Calls a method
                .build();

        // Override
        // Calls a method
        assertTrue(map.has(prototype, DataComponents.REPAIR_COST));
        // Calls a method
        assertEquals(1, map.get(prototype, DataComponents.REPAIR_COST));

        // Inherit
        // Calls a method
        assertTrue(map.has(prototype, DataComponents.ITEM_NAME));
        // Calls a method
        assertEquals(Component.text("Hello"), map.get(prototype, DataComponents.ITEM_NAME));

        // Delete
        // Calls a method
        assertFalse(map.has(prototype, DataComponents.CUSTOM_NAME));
        // Calls a method
        assertNull(map.get(prototype, DataComponents.CUSTOM_NAME));

        // Non-existent
        // Calls a method
        assertFalse(map.has(prototype, DataComponents.BANNER_PATTERNS));
        // Calls a method
        assertNull(map.get(prototype, DataComponents.BANNER_PATTERNS));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testDiffEmpty() {
        // Calls a method
        var prototype = DataComponentMap.patchBuilder().set(DataComponents.REPAIR_COST, 42).build();
        // Assigns a value
        var map = DataComponentMap.EMPTY;
        // Calls a method
        var diff = DataComponentMap.diff(prototype, map);

        // Calls a method
        assertNull(diff.get(DataComponents.REPAIR_COST));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testDiffCompleteDifference() {
        // Calls a method
        var prototype = DataComponentMap.patchBuilder().set(DataComponents.REPAIR_COST, 42).build();
        // Calls a method
        var map = DataComponentMap.patchBuilder().set(DataComponents.CUSTOM_NAME, Component.text("Hello")).build();
        // Calls a method
        var diff = DataComponentMap.diff(prototype, map);

        // Calls a method
        assertNull(diff.get(DataComponents.REPAIR_COST));
        // Calls a method
        assertEquals(Component.text("Hello"), diff.get(DataComponents.CUSTOM_NAME));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testDiffFlatten() {
        // Calls a method
        var prototype = DataComponentMap.builder().set(DataComponents.REPAIR_COST, 42).build();
        // Calls a method
        var map = DataComponentMap.builder().set(DataComponents.REPAIR_COST, 24).build();
        // Calls a method
        var diff = DataComponentMap.diff(prototype, map);

        // Calls a method
        assertEquals(24, diff.get(DataComponents.REPAIR_COST));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testBuilder() {
        // Calls a method
        var builder = DataComponentMap.builder();
        // Calls a method
        builder.set(DataComponents.REPAIR_COST, 42);

        // Builder is a getter for its own entries, so this should be valid
        // Calls a method
        assertEquals(42, builder.get(DataComponents.REPAIR_COST));
        // Calls a method
        var map1 = builder.build();
        // Calls a method
        assertEquals(42, map1.get(DataComponents.REPAIR_COST));

        // Old built map should be unaffected by change
        // Calls a method
        builder.set(DataComponents.REPAIR_COST, 24);
        // Calls a method
        var map2 = builder.build();
        // Calls a method
        assertEquals(42, map1.get(DataComponents.REPAIR_COST));
        // Calls a method
        assertEquals(24, map2.get(DataComponents.REPAIR_COST));
    // End of a block/expression
    }
// End of a block/expression
}
