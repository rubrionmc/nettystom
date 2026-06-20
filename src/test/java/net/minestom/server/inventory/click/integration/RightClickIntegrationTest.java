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
public class RightClickIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void rightSelf(Env env) {
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
        inventory.setItemStack(2, ItemStack.of(Material.DIAMOND));
        // Empty click
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(event.getInventory(), inventory);
                // Appelle une méthode
                assertEquals(new Click.Right(0), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, inventory.getCursorItem());
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            rightClick(player, 0);
        // Fin d'un bloc/d'une expression
        }
        // Pickup diamond
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(new Click.Right(1), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, inventory.getCursorItem());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            rightClick(player, 1);
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.AIR, inventory.getItemStack(1));
        // Fin d'un bloc/d'une expression
        }
        // Place it back
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(new Click.Right(1), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getCursorItem());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, inventory.getItemStack(1));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            rightClick(player, 1);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, inventory.getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
        // Fin d'un bloc/d'une expression
        }
        // Pickup diamond
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(new Click.Right(1), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, inventory.getCursorItem());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            rightClick(player, 1);
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.AIR, inventory.getItemStack(1));
        // Fin d'un bloc/d'une expression
        }
        // Stack diamond
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(new Click.Right(2), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getCursorItem());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(2));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            rightClick(player, 2);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, inventory.getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND, 2), inventory.getItemStack(2));
        // Fin d'un bloc/d'une expression
        }
        // Cancel event
        // Début d'un bloc
        {
            // Appelle une méthode
            listener.followup(event -> event.setCancelled(true));
            // Appelle une méthode
            rightClick(player, 2);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, inventory.getCursorItem(), "Left click cancellation did not work");
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND, 2), inventory.getItemStack(2));
        // Fin d'un bloc/d'une expression
        }
        // Change items
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                Click.Right right = assertInstanceOf(Click.Right.class, event.getClick());

                // Appelle une méthode
                inventory.setItemStack(right.slot(), ItemStack.of(Material.DIAMOND, 5));
                // Appelle une méthode
                inventory.setCursorItem(ItemStack.of(Material.DIAMOND));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            rightClick(player, 1);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, inventory.getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND, 6), inventory.getItemStack(1));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void rightExternal(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        var inventory = new Inventory(InventoryType.HOPPER, "test");
        // Appelle une méthode
        player.openInventory(inventory);
        // Appelle une méthode
        var listener = env.listen(InventoryPreClickEvent.class);
        // Appelle une méthode
        inventory.setItemStack(1, ItemStack.of(Material.DIAMOND));
        // Empty click in player inv
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(player.getInventory(), event.getInventory());
                // Appelle une méthode
                assertEquals(new Click.Right(0), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            rightClick(player, 0);
        // Fin d'un bloc/d'une expression
        }
        // Pickup diamond
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(inventory, event.getInventory());
                // Appelle une méthode
                assertEquals(new Click.Right(1), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            rightClickOpenInventory(player, 1);
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND), player.getInventory().getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.AIR, inventory.getItemStack(1));
        // Fin d'un bloc/d'une expression
        }
        // Place back to player inv
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(player.getInventory(), event.getInventory());
                // Appelle une méthode
                assertEquals(new Click.Right(1), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.DIAMOND), player.getInventory().getCursorItem());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, inventory.getItemStack(1));
                // Appelle une méthode
                assertEquals(ItemStack.AIR, player.getInventory().getItemStack(1));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            rightClick(player, 1);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND), player.getInventory().getItemStack(1));
        // Fin d'un bloc/d'une expression
        }
        // Cancel event
        // Début d'un bloc
        {
            // Appelle une méthode
            listener.followup(event -> event.setCancelled(true));
            // Appelle une méthode
            rightClick(player, 1);
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND), player.getInventory().getItemStack(1), "Left click cancellation did not work");
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
        // Fin d'un bloc/d'une expression
        }
        // Change items
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(player.getInventory(), event.getInventory());
                // Appelle une méthode
                assertEquals(new Click.Right(9), event.getClick());

                // Appelle une méthode
                Click.Right right = assertInstanceOf(Click.Right.class, event.getClick());

                // Appelle une méthode
                event.getInventory().setItemStack(right.slot(), ItemStack.of(Material.DIAMOND, 5));
                // Appelle une méthode
                player.getInventory().setCursorItem(ItemStack.of(Material.DIAMOND));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            rightClick(player, 9);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND, 6), player.getInventory().getItemStack(9));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void rightClickOpenInventory(Player player, int slot) {
        // Appelle une méthode
        _rightClick(player.getOpenInventory(), true, player, slot);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void rightClick(Player player, int slot) {
        // Appelle une méthode
        _rightClick(player.getOpenInventory(), false, player, slot);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void _rightClick(AbstractInventory openInventory, boolean clickOpenInventory, Player player, int slot) {
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
        player.addPacketToQueue(new ClientClickWindowPacket(windowId, 0, (short) slot, (byte) 1,
                // Appelle une méthode
                ClientClickWindowPacket.ClickType.PICKUP, Map.of(), ItemStack.Hash.AIR));
        // Appelle une méthode
        player.interpretPacketQueue();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
