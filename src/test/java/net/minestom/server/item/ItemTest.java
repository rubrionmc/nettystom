// Déclaration du paquet de ce fichier
package net.minestom.server.item;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.jukebox.JukeboxSong;
// Import d'une classe nécessaire
import net.minestom.server.item.component.EnchantmentList;
// Import d'une classe nécessaire
import net.minestom.server.item.enchant.Enchantment;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class ItemTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testFields(Env env) {
        // Appelle une méthode
        var item = ItemStack.of(Material.DIAMOND_SWORD);
        // Appelle une méthode
        assertEquals(item.material(), Material.DIAMOND_SWORD, "Material must be the same");
        // Appelle une méthode
        assertEquals(item.amount(), 1, "Default item amount must be 1");

        // Should have the exact same components as the material prototype
        // Appelle une méthode
        var prototype = Material.DIAMOND_SWORD.registry().prototype();
        // Boucle : répète un bloc
        for (DataComponent<?> component : DataComponent.values()) {
            // Appelle une méthode
            var proto = prototype.get(component);
            // Embranchement : vérifie une condition
            if (proto == null) {
                // Appelle une méthode
                assertFalse(item.has(component), "Item should not have component " + component);
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                assertEquals(proto, item.get(component), "Item should have the same component as the prototype");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        ItemStack finalItem = item;
        // Appelle une méthode
        assertThrows(UnsupportedOperationException.class, () -> finalItem.get(DataComponents.LORE).add(Component.text("Hey!")), "Lore list cannot be modified directly");

        // Appelle une méthode
        item = item.withAmount(5);
        // Appelle une méthode
        assertEquals(item.amount(), 5, "Items with different amount should not be equals");
        // Appelle une méthode
        assertEquals(item.withAmount(amount -> amount * 2).amount(), 10, "Amount must be multiplied by 2");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void defaultBuilder(Env env) {
        // Appelle une méthode
        var item = ItemStack.builder(Material.DIAMOND_SWORD).build();
        // Appelle une méthode
        assertEquals(item.material(), Material.DIAMOND_SWORD, "Material must be the same");
        // Appelle une méthode
        assertEquals(item.amount(), 1, "Default item amount must be 1");

        // Should have the exact same components as the material prototype
        // Appelle une méthode
        var prototype = Material.DIAMOND_SWORD.registry().prototype();
        // Boucle : répète un bloc
        for (DataComponent<?> component : DataComponent.values()) {
            // Appelle une méthode
            var proto = prototype.get(component);
            // Embranchement : vérifie une condition
            if (proto == null) {
                // Appelle une méthode
                assertFalse(item.has(component), "Item should not have component " + component);
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                assertEquals(proto, item.get(component), "Item should have the same component as the prototype");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        ItemStack finalItem = item;
        // Appelle une méthode
        assertThrows(UnsupportedOperationException.class, () -> finalItem.get(DataComponents.LORE).add(Component.text("Hey!")), "Lore list cannot be modified directly");

        // Appelle une méthode
        item = item.withAmount(5);
        // Appelle une méthode
        assertEquals(item.amount(), 5, "Items with different amount should not be equals");
        // Appelle une méthode
        assertEquals(item.withAmount(amount -> amount * 2).amount(), 10, "Amount must be multiplied by 2");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testEquality(Env env) {
        // Appelle une méthode
        var item1 = ItemStack.of(Material.DIAMOND_SWORD);
        // Appelle une méthode
        var item2 = ItemStack.of(Material.DIAMOND_SWORD);
        // Appelle une méthode
        assertEquals(item1, item2);
        // Appelle une méthode
        assertNotEquals(item1.withAmount(5), item2.withAmount(2));

        // Appelle une méthode
        assertTrue(item1.isSimilar(item2));
        // Appelle une méthode
        assertTrue(item1.withAmount(5).isSimilar(item2.withAmount(2)));
        // Appelle une méthode
        assertFalse(item1.isSimilar(item2.with(DataComponents.CUSTOM_NAME, Component.text("Hey!"))));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testEqualityComponents(Env env) {
        // Appelle une méthode
        var item1 = ItemStack.of(Material.MUSIC_DISC_STAL);
        // Appelle une méthode
        var item2 = ItemStack.of(Material.MUSIC_DISC_STAL).with(DataComponents.JUKEBOX_PLAYABLE, JukeboxSong.STAL);
        // Appelle une méthode
        assertTrue(item1.isSimilar(item2));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testFromNbtLoreSpace(Env env) throws IOException {
        // Affecte une valeur
        var itemStack = ItemStack.of(Material.LAPIS_BLOCK)
                // Instruction de code
                .withLore(Component.text("Hey!", NamedTextColor.RED), Component.empty(), Component.text("hello"))
                // Appelle une méthode
                .with(DataComponents.ITEM_MODEL, "unknown");
        // Appelle une méthode
        var tagOut = MinestomAdventure.tagStringIO().asString(itemStack.toItemNBT());
        // Appelle une méthode
        var tagIn = MinestomAdventure.tagStringIO().asCompound(tagOut);
        // Appelle une méthode
        assertEquals(itemStack, ItemStack.fromItemNBT(tagIn));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testImmutableLore(Env env) {
        // Affecte une valeur
        List<Component> lore = new ArrayList<>();
        // Appelle une méthode
        lore.add(Component.text("Hey!"));
        // Appelle une méthode
        var itemStack = ItemStack.of(Material.LAPIS_BLOCK).withLore(lore);
        // Appelle une méthode
        var itemStackLore = itemStack.get(DataComponents.LORE);
        // Appelle une méthode
        assertNotNull(itemStackLore);
        // Appelle une méthode
        assertEquals(lore, itemStackLore, "Lore list should have the same content");
        // Appelle une méthode
        assertThrows(UnsupportedOperationException.class, () -> itemStackLore.add(Component.text("Hey!")), "Should be immutable");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testFromNbt(Env env) {
        // Appelle une méthode
        var itemNbt = createItem().toItemNBT();
        // Appelle une méthode
        var item = ItemStack.fromItemNBT(itemNbt);
        // Appelle une méthode
        assertEquals(createItem(), item, "Items must be equal if created from the same item nbt");
        // Appelle une méthode
        assertEquals(itemNbt, item.toItemNBT(), "Item nbt must be equal back");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testBuilderReuse(Env env) {
        // Appelle une méthode
        var builder = ItemStack.builder(Material.DIAMOND);
        // Appelle une méthode
        var item1 = builder.build();
        // Appelle une méthode
        var item2 = builder.set(DataComponents.CUSTOM_NAME, Component.text("Name")).build();
        // Appelle une méthode
        assertNull(item1.get(DataComponents.CUSTOM_NAME));
        // Appelle une méthode
        assertNotNull(item2.get(DataComponents.CUSTOM_NAME));
        // Appelle une méthode
        assertNotEquals(item1, item2, "Item builder should be reusable");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void materialUpdate(Env env) {
        // Affecte une valeur
        var item1 = ItemStack.builder(Material.DIAMOND)
                // Instruction de code
                .amount(5).set(DataComponents.CUSTOM_NAME, Component.text("Name"))
                // Appelle une méthode
                .build();
        // Appelle une méthode
        var item2 = item1.withMaterial(Material.GOLD_INGOT);

        // Appelle une méthode
        assertEquals(Material.DIAMOND, item1.material());
        // Appelle une méthode
        assertEquals(Material.GOLD_INGOT, item2.material());

        // Appelle une méthode
        var nbt1 = item1.toItemNBT().remove("id");
        // Appelle une méthode
        var nbt2 = item2.toItemNBT().remove("id");
        // Appelle une méthode
        assertEquals(nbt1, nbt2);

        // Appelle une méthode
        assertEquals(5, item1.amount());
        // Appelle une méthode
        assertEquals(5, item2.amount());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void amountUpdate() {
        // Appelle une méthode
        var item1 = ItemStack.of(Material.DIAMOND, 5);
        // Appelle une méthode
        assertEquals(5, item1.amount());
        // Appelle une méthode
        assertEquals(6, item1.withAmount(6).amount());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testEntityType() {
        // Appelle une méthode
        var item1 = ItemStack.of(Material.DIAMOND, 1);
        // Appelle une méthode
        assertNull(item1.material().registry().spawnEntityType());
        // Appelle une méthode
        var item2 = ItemStack.of(Material.CAMEL_SPAWN_EGG, 1);
        // Appelle une méthode
        assertNotNull(item2.material().registry().spawnEntityType());
        // Appelle une méthode
        assertEquals(EntityType.CAMEL, item2.material().registry().spawnEntityType());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testModifyMaterialAmountNonzero() {
        // Appelle une méthode
        var airItem = ItemStack.of(Material.AIR, 0);
        // Appelle une méthode
        assertEquals(0, airItem.amount());
        // Appelle une méthode
        var nonAirItem = airItem.withMaterial(Material.DIAMOND);
        // Appelle une méthode
        assertEquals(1, nonAirItem.amount());
        // Appelle une méthode
        var airAgainItem = nonAirItem.withMaterial(Material.AIR);
        // Appelle une méthode
        assertEquals(0, airAgainItem.amount());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static ItemStack createItem() {
        // Renvoie une valeur à l'appelant
        return ItemStack.builder(Material.STONE)
                // Instruction de code
                .set(DataComponents.CUSTOM_NAME, Component.text("Display name!", NamedTextColor.GREEN))
                // Instruction de code
                .set(DataComponents.LORE, List.of(Component.text("Line 1"), Component.text("Line 2")))
                // Instruction de code
                .set(DataComponents.ENCHANTMENTS, new EnchantmentList(Map.of(Enchantment.EFFICIENCY, 10)))
                // Appelle une méthode
                .build();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
