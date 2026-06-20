// Déclaration du paquet de ce fichier
package net.minestom.server.inventory.click.integration;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.inventory.InventoryPreClickEvent;
// Import d'une classe nécessaire
import net.minestom.server.inventory.AbstractInventory;
// Import d'une classe nécessaire
import net.minestom.server.inventory.Inventory;
// Import d'une classe nécessaire
import net.minestom.server.inventory.InventoryType;
// Import d'une classe nécessaire
import net.minestom.server.inventory.click.Click;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientClickWindowPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.inventory.PlayerInventoryUtils;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class HeldClickIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void heldSelf(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        var inventory = player.getInventory();
        // Appelle une méthode
        var listener = env.listen(InventoryPreClickEvent.class);
        // Appelle une méthode
        inventory.setItemStack(1, ItemStack.of(Material.DIAMOND));
        // Appelle une méthode
        inventory.setItemStack(2, ItemStack.of(Material.GOLD_INGOT));
        // Appelle une méthode
        inventory.setItemStack(3, ItemStack.of(Material.EGG));
        // Appelle une méthode
        inventory.setItemStack(6, ItemStack.of(Material.DIAMOND));
        // Empty
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(event.getInventory(), inventory);
                // Appelle une méthode
                assertEquals(new Click.HotbarSwap(5, 4), event.getClick());

                // Appelle une méthode
                Click.HotbarSwap swap = assertInstanceOf(Click.HotbarSwap.class, event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, inventory.getCursorItem());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, inventory.getItemStack(swap.slot()));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            heldClick(player, 4, 5);
        // Fin d'un bloc/d'une expression
        }
        // Swap air
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(event.getInventory(), inventory);
                // Appelle une méthode
                assertEquals(new Click.HotbarSwap(0, 1), event.getClick());

                // Appelle une méthode
                Click.HotbarSwap swap = assertInstanceOf(Click.HotbarSwap.class, event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, inventory.getCursorItem());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(swap.slot()));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            heldClick(player, 1, 0);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, inventory.getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.AIR, inventory.getItemStack(1));
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(0));
        // Fin d'un bloc/d'une expression
        }
        // Swap items
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(new Click.HotbarSwap(2, 0), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, inventory.getCursorItem());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, inventory.getItemStack(1));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            heldClick(player, 0, 2);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, inventory.getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.AIR, inventory.getItemStack(1));
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(2));
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.GOLD_INGOT), inventory.getItemStack(0));
        // Fin d'un bloc/d'une expression
        }
        // Swap offhand
        // Début d'un bloc
        {
            // Appelle une méthode
            listener.followup(event -> assertEquals(new Click.OffhandSwap(3), event.getClick()));
            // Appelle une méthode
            heldClick(player, 3, 40);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, inventory.getItemStack(3));
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.EGG), player.getItemInOffHand());
        // Fin d'un bloc/d'une expression
        }
        // Cancel event
        // Début d'un bloc
        {
            // Appelle une méthode
            listener.followup(event -> event.setCancelled(true));
            // Appelle une méthode
            heldClick(player, 2, 0);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, inventory.getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.AIR, inventory.getItemStack(1));
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(2));
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.GOLD_INGOT), inventory.getItemStack(0));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void heldExternal(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        var inventory = new Inventory(InventoryType.HOPPER, "test");
        // Appelle une méthode
        var playerInv = player.getInventory();
        // Appelle une méthode
        player.openInventory(inventory);
        // Appelle une méthode
        var listener = env.listen(InventoryPreClickEvent.class);
        // Appelle une méthode
        inventory.setItemStack(1, ItemStack.of(Material.DIAMOND));
        // Appelle une méthode
        inventory.setItemStack(2, ItemStack.of(Material.GOLD_INGOT));
        // Appelle une méthode
        inventory.setItemStack(3, ItemStack.of(Material.EGG));
        // Appelle une méthode
        inventory.setItemStack(4, ItemStack.of(Material.DIAMOND));
        // Empty
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(inventory, event.getInventory());
                // Appelle une méthode
                assertEquals(new Click.HotbarSwap(0, 0), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            heldClickOpenInventory(player, 0, 0);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.AIR, inventory.getItemStack(0));
        // Fin d'un bloc/d'une expression
        }
        // Swap empty
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(inventory, event.getInventory());
                // Appelle une méthode
                assertEquals(new Click.HotbarSwap(0, 1), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            heldClickOpenInventory(player, 1, 0);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.AIR, inventory.getItemStack(1));
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND), playerInv.getItemStack(0));
        // Fin d'un bloc/d'une expression
        }
        // Swap items
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(inventory, event.getInventory());
                // Appelle une méthode
                assertEquals(new Click.HotbarSwap(0, 2), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            heldClickOpenInventory(player, 2, 0);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(2));
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.GOLD_INGOT), playerInv.getItemStack(0));
        // Fin d'un bloc/d'une expression
        }
        // Swap offhand
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(inventory, event.getInventory());
                // Appelle une méthode
                assertEquals(new Click.OffhandSwap(3), event.getClick());
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            heldClickOpenInventory(player, 3, 40);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, inventory.getItemStack(3));
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.EGG), player.getItemInOffHand());
        // Fin d'un bloc/d'une expression
        }
        // Cancel event
        // Début d'un bloc
        {
            // Appelle une méthode
            listener.followup(event -> event.setCancelled(true));
            // Appelle une méthode
            heldClickOpenInventory(player, 2, 0);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(2));
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.GOLD_INGOT), playerInv.getItemStack(0));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void heldClickOpenInventory(Player player, int slot, int target) {
        // Appelle une méthode
        _heldClick(player.getOpenInventory(), true, player, slot, target);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void heldClick(Player player, int slot, int target) {
        // Appelle une méthode
        _heldClick(player.getOpenInventory(), false, player, slot, target);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void _heldClick(AbstractInventory openInventory, boolean clickOpenInventory, Player player, int slot, int target) {
        // Appelle une méthode
        final byte windowId = openInventory != null ? openInventory.getWindowId() : 0;
        // Embranchement : vérifie une condition
        if (clickOpenInventory) {
            // Appelle une méthode
            assertNotNull(openInventory);
            // Do not touch slot
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            int offset = openInventory != null ? openInventory.getInnerSize() : 0;
            // Appelle une méthode
            slot = PlayerInventoryUtils.convertMinestomSlotToWindowSlot(slot);
            // Embranchement : vérifie une condition
            if (openInventory != null) {
                // Affecte une valeur
                slot = slot - 9 + offset;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Instruction de code
        player.addPacketToQueue(new ClientClickWindowPacket(windowId, 0, (short) slot, (byte) target,
                // Appelle une méthode
                ClientClickWindowPacket.ClickType.SWAP, Map.of(), ItemStack.Hash.AIR));
        // Appelle une méthode
        player.interpretPacketQueue();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
