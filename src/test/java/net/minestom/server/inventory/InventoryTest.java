// Déclaration du paquet de ce fichier
package net.minestom.server.inventory;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class InventoryTest {

    // Début d'une méthode/d'un bloc
    static {
        // Required to prevent initialization error during event call
        // Appelle une méthode
        MinecraftServer.init();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testCreation() {
        // Appelle une méthode
        Inventory inventory = new Inventory(InventoryType.CHEST_1_ROW, "title");
        // Appelle une méthode
        assertEquals(InventoryType.CHEST_1_ROW, inventory.getInventoryType());
        // Appelle une méthode
        assertEquals(Component.text("title"), inventory.getTitle());

        // Appelle une méthode
        inventory.setTitle(Component.text("new title"));
        // Appelle une méthode
        assertEquals(Component.text("new title"), inventory.getTitle());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testEntry() {
        // Appelle une méthode
        var item1 = ItemStack.of(Material.DIAMOND);
        // Appelle une méthode
        var item2 = ItemStack.of(Material.GOLD_INGOT);

        // Appelle une méthode
        Inventory inventory = new Inventory(InventoryType.CHEST_1_ROW, "title");
        // Appelle une méthode
        assertSame(ItemStack.AIR, inventory.getItemStack(0));
        // Appelle une méthode
        inventory.setItemStack(0, item1);
        // Appelle une méthode
        assertSame(item1, inventory.getItemStack(0));

        // Appelle une méthode
        inventory.setItemStack(0, ItemStack.AIR);
        // Appelle une méthode
        assertSame(ItemStack.AIR, inventory.getItemStack(0));

        // Replace test
        // Début d'une méthode/d'un bloc
        inventory.replaceItemStack(0, itemStack -> {
            // Appelle une méthode
            assertSame(ItemStack.AIR, itemStack);
            // Renvoie une valeur à l'appelant
            return item2;
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assertSame(item2, inventory.getItemStack(0));
        // Début d'une méthode/d'un bloc
        inventory.replaceItemStack(0, itemStack -> {
            // Appelle une méthode
            assertSame(item2, itemStack);
            // Renvoie une valeur à l'appelant
            return item1;
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assertSame(item1, inventory.getItemStack(0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testTake() {
        // Appelle une méthode
        ItemStack item = ItemStack.of(Material.DIAMOND, 32);
        // Appelle une méthode
        Inventory inventory = new Inventory(InventoryType.CHEST_1_ROW, "title");
        // Appelle une méthode
        inventory.setItemStack(0, item);
        // Appelle une méthode
        assertTrue(inventory.takeItemStack(item, TransactionOption.DRY_RUN));
        // Appelle une méthode
        assertTrue(inventory.takeItemStack(item.withAmount(31), TransactionOption.DRY_RUN));
        // Appelle une méthode
        assertFalse(inventory.takeItemStack(item.withAmount(33), TransactionOption.DRY_RUN));

        // Appelle une méthode
        inventory.setItemStack(1, item.withAmount(2));
        // Appelle une méthode
        assertTrue(inventory.takeItemStack(item.withAmount(33), TransactionOption.DRY_RUN));
        // Appelle une méthode
        assertTrue(inventory.takeItemStack(item.withAmount(34), TransactionOption.DRY_RUN));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testAdd() {
        // Appelle une méthode
        Inventory inventory = new Inventory(InventoryType.HOPPER, "title");
        // Appelle une méthode
        assertTrue(inventory.addItemStack(ItemStack.of(Material.DIAMOND, 32), TransactionOption.ALL_OR_NOTHING));
        // Appelle une méthode
        assertTrue(inventory.addItemStack(ItemStack.of(Material.GOLD_BLOCK, 32), TransactionOption.ALL_OR_NOTHING));
        // Appelle une méthode
        assertTrue(inventory.addItemStack(ItemStack.of(Material.MAP, 32), TransactionOption.ALL_OR_NOTHING));
        // Appelle une méthode
        assertTrue(inventory.addItemStack(ItemStack.of(Material.ANDESITE_WALL, 32), TransactionOption.ALL_OR_NOTHING));
        // Appelle une méthode
        assertTrue(inventory.addItemStack(ItemStack.of(Material.ANDESITE, 32), TransactionOption.ALL_OR_NOTHING));
        // Appelle une méthode
        assertFalse(inventory.addItemStack(ItemStack.of(Material.BLUE_CONCRETE, 32), TransactionOption.ALL_OR_NOTHING));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testIds() {
        // Boucle : répète un bloc
        for (int i = 0; i <= 1000; ++i) {
            // Appelle une méthode
            final byte windowId = new Inventory(InventoryType.CHEST_1_ROW, "title").getWindowId();
            // Appelle une méthode
            assertTrue(windowId > 0);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testStackSize99() {
        // Appelle une méthode
        var inventory = new Inventory(InventoryType.CHEST_1_ROW, "title");
        // Appelle une méthode
        var item = ItemStack.builder(Material.DIAMOND).set(DataComponents.MAX_STACK_SIZE, 99).amount(99).build();

        // Appelle une méthode
        assertTrue(inventory.addItemStack(item, TransactionOption.ALL_OR_NOTHING));
        // Appelle une méthode
        assertEquals(99, inventory.getItemStack(0).amount());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testStackSize99OnSmaller() {
        // Appelle une méthode
        var inventory = new Inventory(InventoryType.CHEST_1_ROW, "title");
        // Appelle une méthode
        var item44 = ItemStack.builder(Material.DIAMOND).set(DataComponents.MAX_STACK_SIZE, 44).amount(43).build();
        // Appelle une méthode
        var item99 = ItemStack.builder(Material.DIAMOND).set(DataComponents.MAX_STACK_SIZE, 99).amount(99).build();

        // Note this is vanilla behavior not to stack these two because they have different components.
        // Appelle une méthode
        assertTrue(inventory.addItemStack(item44, TransactionOption.ALL_OR_NOTHING));
        // Appelle une méthode
        assertTrue(inventory.addItemStack(item99, TransactionOption.ALL_OR_NOTHING));
        // Appelle une méthode
        assertEquals(43, inventory.getItemStack(0).amount());
        // Appelle une méthode
        assertEquals(99, inventory.getItemStack(1).amount());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
