// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.EquipmentSlot;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.event.EventFilter;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerUseItemEvent;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.item.component.Equippable;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientUseItemPacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class TestUseItemListenerIntegration {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void useItemNonSpecial(Env env) {
        // Any random item should not trigger any hand updates
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 41, 0));

        // Appelle une méthode
        var useItemCollector = env.trackEvent(PlayerUseItemEvent.class, EventFilter.PLAYER, player);

        // Appelle une méthode
        var itemStack = ItemStack.of(Material.DIAMOND);
        // Appelle une méthode
        player.setItemInMainHand(itemStack);
        // Appelle une méthode
        UseItemListener.useItemListener(new ClientUseItemPacket(PlayerHand.MAIN, 42, 0f, 0f), player);

        // Début d'une méthode/d'un bloc
        useItemCollector.assertSingle(event -> {
            // Appelle une méthode
            assertEquals(PlayerHand.MAIN, event.getHand());
            // Appelle une méthode
            assertEquals(itemStack, event.getItemStack());
            // Appelle une méthode
            assertEquals(0, event.getItemUseTime());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testEquipArmorToAir(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 41, 0));

        // Appelle une méthode
        var boots = ItemStack.of(Material.DIAMOND_BOOTS);
        // Appelle une méthode
        player.setItemInMainHand(boots);
        // Appelle une méthode
        UseItemListener.useItemListener(new ClientUseItemPacket(PlayerHand.MAIN, 42, 0f, 0f), player);

        // Appelle une méthode
        assertEquals(ItemStack.AIR, player.getItemInMainHand());
        // Appelle une méthode
        assertEquals(boots, player.getEquipment(EquipmentSlot.BOOTS));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testEquipArmorSwap(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 41, 0));

        // Appelle une méthode
        var oldBoots = ItemStack.of(Material.GOLDEN_BOOTS);
        // Appelle une méthode
        player.setEquipment(EquipmentSlot.BOOTS, oldBoots);

        // Appelle une méthode
        var boots = ItemStack.of(Material.DIAMOND_BOOTS);
        // Appelle une méthode
        player.setItemInMainHand(boots);
        // Appelle une méthode
        UseItemListener.useItemListener(new ClientUseItemPacket(PlayerHand.MAIN, 42, 0f, 0f), player);

        // Appelle une méthode
        assertEquals(oldBoots, player.getItemInMainHand());
        // Appelle une méthode
        assertEquals(boots, player.getEquipment(EquipmentSlot.BOOTS));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testDoNotEquipNonEquippableArmor(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 41, 0));

        // Appelle une méthode
        var badBoots = ItemStack.of(Material.GOLDEN_BOOTS).without(DataComponents.EQUIPPABLE);
        // Appelle une méthode
        player.setItemInMainHand(badBoots);
        // Appelle une méthode
        UseItemListener.useItemListener(new ClientUseItemPacket(PlayerHand.MAIN, 42, 0f, 0f), player);

        // Appelle une méthode
        assertEquals(badBoots, player.getItemInMainHand());
        // Appelle une méthode
        assertEquals(ItemStack.AIR, player.getEquipment(EquipmentSlot.BOOTS));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testDoNotSwapNonSwappableArmor(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 41, 0));

        // Appelle une méthode
        var oldBoots = ItemStack.of(Material.GOLDEN_BOOTS);
        // Appelle une méthode
        player.setEquipment(EquipmentSlot.BOOTS, oldBoots);

        // Affecte une valeur
        var boots = ItemStack.of(Material.DIAMOND_BOOTS).with(DataComponents.EQUIPPABLE,
                // Appelle une méthode
                (UnaryOperator<Equippable>) (eq) -> eq.withSwappable(false));
        // Appelle une méthode
        player.setItemInMainHand(boots);
        // Appelle une méthode
        UseItemListener.useItemListener(new ClientUseItemPacket(PlayerHand.MAIN, 42, 0f, 0f), player);

        // Appelle une méthode
        assertEquals(boots, player.getItemInMainHand());
        // Appelle une méthode
        assertEquals(oldBoots, player.getEquipment(EquipmentSlot.BOOTS));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testRotation(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 41, 0));
        // Appelle une méthode
        player.refreshReceivedTeleportId(player.getLastSentTeleportId());

        // Appelle une méthode
        assertEquals(new Pos(0, 41, 0), player.getPosition());
        // Appelle une méthode
        UseItemListener.useItemListener(new ClientUseItemPacket(PlayerHand.MAIN, 42, 5f, 10f), player);
        // Appelle une méthode
        assertEquals(new Pos(0, 41, 0, 5f, 10f), player.getPosition());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
