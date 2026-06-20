// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.EquipmentSlot;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.event.EventFilter;
// Import of a required class
import net.minestom.server.event.player.PlayerUseItemEvent;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.item.component.Equippable;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientUseItemPacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.function.UnaryOperator;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class TestUseItemListenerIntegration {

    // Annotation for the following element
    @Test
    // Start of a method/block
    void useItemNonSpecial(Env env) {
        // Any random item should not trigger any hand updates
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 41, 0));

        // Calls a method
        var useItemCollector = env.trackEvent(PlayerUseItemEvent.class, EventFilter.PLAYER, player);

        // Calls a method
        var itemStack = ItemStack.of(Material.DIAMOND);
        // Calls a method
        player.setItemInMainHand(itemStack);
        // Calls a method
        UseItemListener.useItemListener(new ClientUseItemPacket(PlayerHand.MAIN, 42, 0f, 0f), player);

        // Start of a method/block
        useItemCollector.assertSingle(event -> {
            // Calls a method
            assertEquals(PlayerHand.MAIN, event.getHand());
            // Calls a method
            assertEquals(itemStack, event.getItemStack());
            // Calls a method
            assertEquals(0, event.getItemUseTime());
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testEquipArmorToAir(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 41, 0));

        // Calls a method
        var boots = ItemStack.of(Material.DIAMOND_BOOTS);
        // Calls a method
        player.setItemInMainHand(boots);
        // Calls a method
        UseItemListener.useItemListener(new ClientUseItemPacket(PlayerHand.MAIN, 42, 0f, 0f), player);

        // Calls a method
        assertEquals(ItemStack.AIR, player.getItemInMainHand());
        // Calls a method
        assertEquals(boots, player.getEquipment(EquipmentSlot.BOOTS));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testEquipArmorSwap(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 41, 0));

        // Calls a method
        var oldBoots = ItemStack.of(Material.GOLDEN_BOOTS);
        // Calls a method
        player.setEquipment(EquipmentSlot.BOOTS, oldBoots);

        // Calls a method
        var boots = ItemStack.of(Material.DIAMOND_BOOTS);
        // Calls a method
        player.setItemInMainHand(boots);
        // Calls a method
        UseItemListener.useItemListener(new ClientUseItemPacket(PlayerHand.MAIN, 42, 0f, 0f), player);

        // Calls a method
        assertEquals(oldBoots, player.getItemInMainHand());
        // Calls a method
        assertEquals(boots, player.getEquipment(EquipmentSlot.BOOTS));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testDoNotEquipNonEquippableArmor(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 41, 0));

        // Calls a method
        var badBoots = ItemStack.of(Material.GOLDEN_BOOTS).without(DataComponents.EQUIPPABLE);
        // Calls a method
        player.setItemInMainHand(badBoots);
        // Calls a method
        UseItemListener.useItemListener(new ClientUseItemPacket(PlayerHand.MAIN, 42, 0f, 0f), player);

        // Calls a method
        assertEquals(badBoots, player.getItemInMainHand());
        // Calls a method
        assertEquals(ItemStack.AIR, player.getEquipment(EquipmentSlot.BOOTS));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testDoNotSwapNonSwappableArmor(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 41, 0));

        // Calls a method
        var oldBoots = ItemStack.of(Material.GOLDEN_BOOTS);
        // Calls a method
        player.setEquipment(EquipmentSlot.BOOTS, oldBoots);

        // Assigns a value
        var boots = ItemStack.of(Material.DIAMOND_BOOTS).with(DataComponents.EQUIPPABLE,
                // Calls a method
                (UnaryOperator<Equippable>) (eq) -> eq.withSwappable(false));
        // Calls a method
        player.setItemInMainHand(boots);
        // Calls a method
        UseItemListener.useItemListener(new ClientUseItemPacket(PlayerHand.MAIN, 42, 0f, 0f), player);

        // Calls a method
        assertEquals(boots, player.getItemInMainHand());
        // Calls a method
        assertEquals(oldBoots, player.getEquipment(EquipmentSlot.BOOTS));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testRotation(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 41, 0));
        // Calls a method
        player.refreshReceivedTeleportId(player.getLastSentTeleportId());

        // Calls a method
        assertEquals(new Pos(0, 41, 0), player.getPosition());
        // Calls a method
        UseItemListener.useItemListener(new ClientUseItemPacket(PlayerHand.MAIN, 42, 5f, 10f), player);
        // Calls a method
        assertEquals(new Pos(0, 41, 0, 5f, 10f), player.getPosition());
    // End of a block/expression
    }
// End of a block/expression
}
