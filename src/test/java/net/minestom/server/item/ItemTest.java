// Package declaration for this file
package net.minestom.server.item;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.instance.block.jukebox.JukeboxSong;
// Import of a required class
import net.minestom.server.item.component.EnchantmentList;
// Import of a required class
import net.minestom.server.item.enchant.Enchantment;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class ItemTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testFields(Env env) {
        // Calls a method
        var item = ItemStack.of(Material.DIAMOND_SWORD);
        // Calls a method
        assertEquals(Material.DIAMOND_SWORD, item.material(), "Material must be the same");
        // Calls a method
        assertEquals(1, item.amount(), "Default item amount must be 1");

        // Should have the exact same components as the material prototype
        // Calls a method
        var prototype = Material.DIAMOND_SWORD.registry().prototype();
        // Loop: repeats a block
        for (DataComponent<?> component : DataComponent.values()) {
            // Calls a method
            var proto = prototype.get(component);
            // Branch: checks a condition
            if (proto == null) {
                // Calls a method
                assertFalse(item.has(component), "Item should not have component " + component);
            // Alternative branch of the condition
            } else {
                // Calls a method
                assertEquals(proto, item.get(component), "Item should have the same component as the prototype");
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Assigns a value
        ItemStack finalItem = item;
        // Calls a method
        assertThrows(UnsupportedOperationException.class, () -> finalItem.get(DataComponents.LORE).add(Component.text("Hey!")), "Lore list cannot be modified directly");

        // Calls a method
        item = item.withAmount(5);
        // Calls a method
        assertEquals(5, item.amount(), "Items with different amount should not be equals");
        // Calls a method
        assertEquals(10, item.withAmount(amount -> amount * 2).amount(), "Amount must be multiplied by 2");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void defaultBuilder(Env env) {
        // Calls a method
        var item = ItemStack.builder(Material.DIAMOND_SWORD).build();
        // Calls a method
        assertEquals(Material.DIAMOND_SWORD, item.material(), "Material must be the same");
        // Calls a method
        assertEquals(1, item.amount(), "Default item amount must be 1");

        // Should have the exact same components as the material prototype
        // Calls a method
        var prototype = Material.DIAMOND_SWORD.registry().prototype();
        // Loop: repeats a block
        for (DataComponent<?> component : DataComponent.values()) {
            // Calls a method
            var proto = prototype.get(component);
            // Branch: checks a condition
            if (proto == null) {
                // Calls a method
                assertFalse(item.has(component), "Item should not have component " + component);
            // Alternative branch of the condition
            } else {
                // Calls a method
                assertEquals(proto, item.get(component), "Item should have the same component as the prototype");
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Assigns a value
        ItemStack finalItem = item;
        // Calls a method
        assertThrows(UnsupportedOperationException.class, () -> finalItem.get(DataComponents.LORE).add(Component.text("Hey!")), "Lore list cannot be modified directly");

        // Calls a method
        item = item.withAmount(5);
        // Calls a method
        assertEquals(5, item.amount(), "Items with different amount should not be equals");
        // Calls a method
        assertEquals(10, item.withAmount(amount -> amount * 2).amount(), "Amount must be multiplied by 2");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testEquality(Env env) {
        // Calls a method
        var item1 = ItemStack.of(Material.DIAMOND_SWORD);
        // Calls a method
        var item2 = ItemStack.of(Material.DIAMOND_SWORD);
        // Calls a method
        assertEquals(item1, item2);
        // Calls a method
        assertNotEquals(item1.withAmount(5), item2.withAmount(2));

        // Calls a method
        assertTrue(item1.isSimilar(item2));
        // Calls a method
        assertTrue(item1.withAmount(5).isSimilar(item2.withAmount(2)));
        // Calls a method
        assertFalse(item1.isSimilar(item2.with(DataComponents.CUSTOM_NAME, Component.text("Hey!"))));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testEqualityComponents(Env env) {
        // Calls a method
        var item1 = ItemStack.of(Material.MUSIC_DISC_STAL);
        // Calls a method
        var item2 = ItemStack.of(Material.MUSIC_DISC_STAL).with(DataComponents.JUKEBOX_PLAYABLE, JukeboxSong.STAL);
        // Calls a method
        assertTrue(item1.isSimilar(item2));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testFromNbtLoreSpace(Env env) throws IOException {
        // Assigns a value
        var itemStack = ItemStack.of(Material.LAPIS_BLOCK)
                // Code statement
                .withLore(Component.text("Hey!", NamedTextColor.RED), Component.empty(), Component.text("hello"))
                // Calls a method
                .with(DataComponents.ITEM_MODEL, "unknown");
        // Calls a method
        var tagOut = MinestomAdventure.tagStringIO().asString(itemStack.toItemNBT());
        // Calls a method
        var tagIn = MinestomAdventure.tagStringIO().asCompound(tagOut);
        // Calls a method
        assertEquals(itemStack, ItemStack.fromItemNBT(tagIn));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testImmutableLore(Env env) {
        // Calls a method
        List<Component> lore = new ArrayList<>();
        // Calls a method
        lore.add(Component.text("Hey!"));
        // Calls a method
        var itemStack = ItemStack.of(Material.LAPIS_BLOCK).withLore(lore);
        // Calls a method
        var itemStackLore = itemStack.get(DataComponents.LORE);
        // Calls a method
        assertNotNull(itemStackLore);
        // Calls a method
        assertEquals(lore, itemStackLore, "Lore list should have the same content");
        // Calls a method
        assertThrows(UnsupportedOperationException.class, () -> itemStackLore.add(Component.text("Hey!")), "Should be immutable");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testBuilderImmutableLore(Env env) {
        // Calls a method
        List<Component> lore = new ArrayList<>();
        // Calls a method
        lore.add(Component.text("Hey!"));
        // Calls a method
        var itemStack = ItemStack.builder(Material.LAPIS_BLOCK).lore(lore).build();
        // Calls a method
        var itemStackLore = itemStack.get(DataComponents.LORE);
        // Calls a method
        assertNotNull(itemStackLore);
        // Calls a method
        assertEquals(lore, itemStackLore, "Lore list should have the same content");
        // Calls a method
        assertThrows(UnsupportedOperationException.class, () -> itemStackLore.add(Component.text("Hey!")), "Should be immutable");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testFromNbt(Env env) {
        // Calls a method
        var itemNbt = createItem().toItemNBT();
        // Calls a method
        var item = ItemStack.fromItemNBT(itemNbt);
        // Calls a method
        assertEquals(createItem(), item, "Items must be equal if created from the same item nbt");
        // Calls a method
        assertEquals(itemNbt, item.toItemNBT(), "Item nbt must be equal back");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testBuilderReuse(Env env) {
        // Calls a method
        var builder = ItemStack.builder(Material.DIAMOND);
        // Calls a method
        var item1 = builder.build();
        // Calls a method
        var item2 = builder.set(DataComponents.CUSTOM_NAME, Component.text("Name")).build();
        // Calls a method
        assertNull(item1.get(DataComponents.CUSTOM_NAME));
        // Calls a method
        assertNotNull(item2.get(DataComponents.CUSTOM_NAME));
        // Calls a method
        assertNotEquals(item1, item2, "Item builder should be reusable");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void materialUpdate(Env env) {
        // Assigns a value
        var item1 = ItemStack.builder(Material.DIAMOND)
                // Code statement
                .amount(5).set(DataComponents.CUSTOM_NAME, Component.text("Name"))
                // Calls a method
                .build();
        // Calls a method
        var item2 = item1.withMaterial(Material.GOLD_INGOT);

        // Calls a method
        assertEquals(Material.DIAMOND, item1.material());
        // Calls a method
        assertEquals(Material.GOLD_INGOT, item2.material());

        // Calls a method
        var nbt1 = item1.toItemNBT().remove("id");
        // Calls a method
        var nbt2 = item2.toItemNBT().remove("id");
        // Calls a method
        assertEquals(nbt1, nbt2);

        // Calls a method
        assertEquals(5, item1.amount());
        // Calls a method
        assertEquals(5, item2.amount());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void amountUpdate() {
        // Calls a method
        var item1 = ItemStack.of(Material.DIAMOND, 5);
        // Calls a method
        assertEquals(5, item1.amount());
        // Calls a method
        assertEquals(6, item1.withAmount(6).amount());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testEntityType() {
        // Calls a method
        var item1 = ItemStack.of(Material.DIAMOND, 1);
        // Calls a method
        assertNull(item1.material().registry().spawnEntityType());
        // Calls a method
        var item2 = ItemStack.of(Material.CAMEL_SPAWN_EGG, 1);
        // Calls a method
        assertNotNull(item2.material().registry().spawnEntityType());
        // Calls a method
        assertEquals(EntityType.CAMEL, item2.material().registry().spawnEntityType());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testModifyMaterialAmountNonzero() {
        // Calls a method
        var airItem = ItemStack.of(Material.AIR, 0);
        // Calls a method
        assertEquals(0, airItem.amount());
        // Calls a method
        var nonAirItem = airItem.withMaterial(Material.DIAMOND);
        // Calls a method
        assertEquals(1, nonAirItem.amount());
        // Calls a method
        var airAgainItem = nonAirItem.withMaterial(Material.AIR);
        // Calls a method
        assertEquals(0, airAgainItem.amount());
    // End of a block/expression
    }

    // Start of a method/block
    static ItemStack createItem() {
        // Returns a value to the caller
        return ItemStack.builder(Material.STONE)
                // Code statement
                .set(DataComponents.CUSTOM_NAME, Component.text("Display name!", NamedTextColor.GREEN))
                // Code statement
                .set(DataComponents.LORE, List.of(Component.text("Line 1"), Component.text("Line 2")))
                // Code statement
                .set(DataComponents.ENCHANTMENTS, new EnchantmentList(Map.of(Enchantment.EFFICIENCY, 10)))
                // Calls a method
                .build();
    // End of a block/expression
    }
// End of a block/expression
}
