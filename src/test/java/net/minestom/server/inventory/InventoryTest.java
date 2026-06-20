// Package declaration for this file
package net.minestom.server.inventory;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class InventoryTest {

    // Start of a method/block
    static {
        // Required to prevent initialization error during event call
        // Calls a method
        MinecraftServer.init();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testCreation() {
        // Calls a method
        Inventory inventory = new Inventory(InventoryType.CHEST_1_ROW, "title");
        // Calls a method
        assertEquals(InventoryType.CHEST_1_ROW, inventory.getInventoryType());
        // Calls a method
        assertEquals(Component.text("title"), inventory.getTitle());

        // Calls a method
        inventory.setTitle(Component.text("new title"));
        // Calls a method
        assertEquals(Component.text("new title"), inventory.getTitle());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testEntry() {
        // Calls a method
        var item1 = ItemStack.of(Material.DIAMOND);
        // Calls a method
        var item2 = ItemStack.of(Material.GOLD_INGOT);

        // Calls a method
        Inventory inventory = new Inventory(InventoryType.CHEST_1_ROW, "title");
        // Calls a method
        assertSame(ItemStack.AIR, inventory.getItemStack(0));
        // Calls a method
        inventory.setItemStack(0, item1);
        // Calls a method
        assertSame(item1, inventory.getItemStack(0));

        // Calls a method
        inventory.setItemStack(0, ItemStack.AIR);
        // Calls a method
        assertSame(ItemStack.AIR, inventory.getItemStack(0));

        // Replace test
        // Start of a method/block
        inventory.replaceItemStack(0, itemStack -> {
            // Calls a method
            assertSame(ItemStack.AIR, itemStack);
            // Returns a value to the caller
            return item2;
        // End of a block/expression
        });
        // Calls a method
        assertSame(item2, inventory.getItemStack(0));
        // Start of a method/block
        inventory.replaceItemStack(0, itemStack -> {
            // Calls a method
            assertSame(item2, itemStack);
            // Returns a value to the caller
            return item1;
        // End of a block/expression
        });
        // Calls a method
        assertSame(item1, inventory.getItemStack(0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testTake() {
        // Calls a method
        ItemStack item = ItemStack.of(Material.DIAMOND, 32);
        // Calls a method
        Inventory inventory = new Inventory(InventoryType.CHEST_1_ROW, "title");
        // Calls a method
        inventory.setItemStack(0, item);
        // Calls a method
        assertTrue(inventory.takeItemStack(item, TransactionOption.DRY_RUN));
        // Calls a method
        assertTrue(inventory.takeItemStack(item.withAmount(31), TransactionOption.DRY_RUN));
        // Calls a method
        assertFalse(inventory.takeItemStack(item.withAmount(33), TransactionOption.DRY_RUN));

        // Calls a method
        inventory.setItemStack(1, item.withAmount(2));
        // Calls a method
        assertTrue(inventory.takeItemStack(item.withAmount(33), TransactionOption.DRY_RUN));
        // Calls a method
        assertTrue(inventory.takeItemStack(item.withAmount(34), TransactionOption.DRY_RUN));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testAdd() {
        // Calls a method
        Inventory inventory = new Inventory(InventoryType.HOPPER, "title");
        // Calls a method
        assertTrue(inventory.addItemStack(ItemStack.of(Material.DIAMOND, 32), TransactionOption.ALL_OR_NOTHING));
        // Calls a method
        assertTrue(inventory.addItemStack(ItemStack.of(Material.GOLD_BLOCK, 32), TransactionOption.ALL_OR_NOTHING));
        // Calls a method
        assertTrue(inventory.addItemStack(ItemStack.of(Material.MAP, 32), TransactionOption.ALL_OR_NOTHING));
        // Calls a method
        assertTrue(inventory.addItemStack(ItemStack.of(Material.ANDESITE_WALL, 32), TransactionOption.ALL_OR_NOTHING));
        // Calls a method
        assertTrue(inventory.addItemStack(ItemStack.of(Material.ANDESITE, 32), TransactionOption.ALL_OR_NOTHING));
        // Calls a method
        assertFalse(inventory.addItemStack(ItemStack.of(Material.BLUE_CONCRETE, 32), TransactionOption.ALL_OR_NOTHING));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testIds() {
        // Loop: repeats a block
        for (int i = 0; i <= 1000; ++i) {
            // Calls a method
            final byte windowId = new Inventory(InventoryType.CHEST_1_ROW, "title").getWindowId();
            // Calls a method
            assertTrue(windowId > 0);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testStackSize99() {
        // Calls a method
        var inventory = new Inventory(InventoryType.CHEST_1_ROW, "title");
        // Calls a method
        var item = ItemStack.builder(Material.DIAMOND).set(DataComponents.MAX_STACK_SIZE, 99).amount(99).build();

        // Calls a method
        assertTrue(inventory.addItemStack(item, TransactionOption.ALL_OR_NOTHING));
        // Calls a method
        assertEquals(99, inventory.getItemStack(0).amount());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testStackSize99OnSmaller() {
        // Calls a method
        var inventory = new Inventory(InventoryType.CHEST_1_ROW, "title");
        // Calls a method
        var item44 = ItemStack.builder(Material.DIAMOND).set(DataComponents.MAX_STACK_SIZE, 44).amount(43).build();
        // Calls a method
        var item99 = ItemStack.builder(Material.DIAMOND).set(DataComponents.MAX_STACK_SIZE, 99).amount(99).build();

        // Note this is vanilla behavior not to stack these two because they have different components.
        // Calls a method
        assertTrue(inventory.addItemStack(item44, TransactionOption.ALL_OR_NOTHING));
        // Calls a method
        assertTrue(inventory.addItemStack(item99, TransactionOption.ALL_OR_NOTHING));
        // Calls a method
        assertEquals(43, inventory.getItemStack(0).amount());
        // Calls a method
        assertEquals(99, inventory.getItemStack(1).amount());
    // End of a block/expression
    }
// End of a block/expression
}
