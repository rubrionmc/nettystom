// Package declaration for this file
package net.minestom.server.item;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class ItemAirTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testAir() {
        // Calls a method
        var item = ItemStack.of(Material.DIAMOND_SWORD);
        // Calls a method
        assertFalse(item.isAir());
        // Calls a method
        assertTrue(ItemStack.AIR.isAir());
        // Calls a method
        var emptyItem = item.withAmount(0);
        // Calls a method
        assertTrue(emptyItem.isAir());
        // Calls a method
        assertEquals(ItemStack.AIR, emptyItem, "AIR item can be compared to empty item");
        // Calls a method
        assertSame(ItemStack.AIR, emptyItem, "AIR item identity can be compared to empty item");

        // Calls a method
        assertSame(ItemStack.AIR, ItemStack.of(Material.DIAMOND, 0));
        // Calls a method
        assertSame(ItemStack.AIR, ItemStack.builder(Material.DIAMOND).amount(0).build());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testAirWithComponent() {
        // Calls a method
        var item = ItemStack.AIR.with(DataComponents.CUSTOM_NAME, Component.text("Name"));
        // Calls a method
        assertSame(ItemStack.AIR, item);
    // End of a block/expression
    }
// End of a block/expression
}
