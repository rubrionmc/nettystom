// Déclaration du paquet de ce fichier
package net.minestom.server.inventory.click.integration;

// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.EquipmentSlot;
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
import net.minestom.server.item.component.Equippable;
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
public class LeftClickIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void leftSelf(Env env) {
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
        inventory.setItemStack(2, ItemStack.of(Material.DIAMOND_HELMET));
        // Appelle une méthode
        inventory.setItemStack(3, ItemStack.of(Material.SHIELD));
        // Instruction de code
        inventory.setItemStack(4, ItemStack.builder(Material.COOKED_BEEF).set(DataComponents.EQUIPPABLE,
                // Crée un nouvel objet
                new Equippable(EquipmentSlot.LEGGINGS, Equippable.DEFAULT_EQUIP_SOUND, null, null, null,
                        // Appelle une méthode
                        true, true, true, true, true, Equippable.DEFAULT_SHEARING_SOUND)).build());
        // Empty click
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(event.getInventory(), inventory);
                // Appelle une méthode
                assertEquals(new Click.Left(0), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, inventory.getCursorItem());
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            leftClick(player, 0);
        // Fin d'un bloc/d'une expression
        }
        // Pickup diamond
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(new Click.Left(1), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, inventory.getCursorItem());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            leftClick(player, 1);
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
                assertEquals(new Click.Left(1), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getCursorItem());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, inventory.getItemStack(1));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            leftClick(player, 1);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, inventory.getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
        // Fin d'un bloc/d'une expression
        }
        // Shift click an armor item into the armor slot
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Appelle une méthode
                assertEquals(new Click.LeftShift(2), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.DIAMOND_HELMET), player.getInventory().getItemStack(2));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            shiftClick(player, 2);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getItemStack(2));
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND_HELMET), player.getHelmet());
        // Fin d'un bloc/d'une expression
        }
        // Shift click non armor material but equippable item into the armor slot
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Appelle une méthode
                assertEquals(new Click.LeftShift(4), event.getClick());
                // Appelle une méthode
                assertEquals(Material.COOKED_BEEF, player.getInventory().getItemStack(4).material());
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            shiftClick(player, 4);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getItemStack(4));
            // Appelle une méthode
            assertEquals(Material.COOKED_BEEF, player.getLeggings().material());
        // Fin d'un bloc/d'une expression
        }
        // Shift click an armor slot item back into the inventory
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Appelle une méthode
                assertEquals(new Click.LeftShift(41), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.DIAMOND_HELMET), player.getHelmet());
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            shiftClick(player, 41);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.AIR), player.getHelmet());
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND_HELMET), player.getInventory().getItemStack(9));
        // Fin d'un bloc/d'une expression
        }
        // Shift click a shield into the off-hand slot
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Appelle une méthode
                assertEquals(new Click.LeftShift(3), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.SHIELD), player.getInventory().getItemStack(3));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            shiftClick(player, 3);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getItemStack(3));
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.SHIELD), player.getInventory().getItemStack(45));
        // Fin d'un bloc/d'une expression
        }
        // Shift click a shield in the off-hand slot to the inventory
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Appelle une méthode
                assertEquals(new Click.LeftShift(45), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.SHIELD), player.getInventory().getItemStack(45));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            shiftClick(player, 45);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getItemStack(45));
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.SHIELD), player.getInventory().getItemStack(10));
        // Fin d'un bloc/d'une expression
        }
        // Shift click a player crafting inventory ingredient to the player inventory
        // Début d'un bloc
        {
            // Appelle une méthode
            player.getInventory().setItemStack(37, ItemStack.of(Material.GOLDEN_HELMET));
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Appelle une méthode
                assertEquals(new Click.LeftShift(37), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.GOLDEN_HELMET), player.getInventory().getItemStack(37));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            shiftClick(player, 37);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getItemStack(37));
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.GOLDEN_HELMET), player.getInventory().getItemStack(11));
        // Fin d'un bloc/d'une expression
        }
        // Shift click a player crafting inventory result to the player hotbar
        // Début d'un bloc
        {
            // Appelle une méthode
            player.getInventory().setItemStack(36, ItemStack.of(Material.IRON_HELMET));
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Appelle une méthode
                assertEquals(new Click.LeftShift(36), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.IRON_HELMET), player.getInventory().getItemStack(36));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            shiftClick(player, 36);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getItemStack(36));
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.IRON_HELMET), player.getInventory().getItemStack(8));
        // Fin d'un bloc/d'une expression
        }
        // Cancel event
        // Début d'un bloc
        {
            // Appelle une méthode
            listener.followup(event -> event.setCancelled(true));
            // Appelle une méthode
            leftClick(player, 1);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, inventory.getCursorItem(), "Left click cancellation did not work");
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
        // Fin d'un bloc/d'une expression
        }
        // Change items
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                Click.Left left = assertInstanceOf(Click.Left.class, event.getClick());

                // Appelle une méthode
                inventory.setItemStack(left.slot(), ItemStack.of(Material.DIAMOND, 5));
                // Appelle une méthode
                inventory.setCursorItem(ItemStack.of(Material.DIAMOND));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            leftClick(player, 1);
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
    public void leftExternal(Env env) {
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
                assertEquals(new Click.Left(0), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            leftClick(player, 0);
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
                assertEquals(new Click.Left(1), event.getClick());
                // Ensure that the inventory didn't change yet
                // Appelle une méthode
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            leftClickOpenInventory(player, 1);
            // Verify inventory changes
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND), player.getInventory().getCursorItem());
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
                assertEquals(inventory, event.getInventory());
                // Appelle une méthode
                assertEquals(new Click.Left(1), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.DIAMOND), player.getInventory().getCursorItem());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, inventory.getItemStack(1));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            leftClickOpenInventory(player, 1);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
        // Fin d'un bloc/d'une expression
        }
        // Shift click the item into the player's inventory
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(inventory, event.getInventory());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Appelle une méthode
                assertEquals(new Click.LeftShift(1), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            shiftClickOpenInventory(player, 1);
            // Instruction de code
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem()); // When shift-clicking, the cursor item shouldn't change
            // Instruction de code
            assertEquals(ItemStack.of(Material.DIAMOND), player.getInventory().getItemStack(8)); // The item should appear in the player's last hotbar slot
        // Fin d'un bloc/d'une expression
        }
        // Shift click the item back into the external inventory
        // Début d'un bloc
        {
            // Début d'une méthode/d'un bloc
            listener.followup(event -> {
                // Appelle une méthode
                assertEquals(player.getInventory(), event.getInventory());
                // Appelle une méthode
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Appelle une méthode
                assertEquals(new Click.LeftShift(8), event.getClick());
                // Appelle une méthode
                assertEquals(ItemStack.of(Material.DIAMOND), player.getInventory().getItemStack(8));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            shiftClick(player, 8);
            // Instruction de code
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem()); // When shift-clicking, the cursor item shouldn't change
            // Instruction de code
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(0)); // The item should appear in the external inventory's first slot
        // Fin d'un bloc/d'une expression
        }
        // Shift click into the player's inventory when their hotbar is full
        // Début d'un bloc
        {
            // Appelle une méthode
            inventory.setItemStack(1, ItemStack.of(Material.GOLD_INGOT));
            // Boucle : répète un bloc
            for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
                // Appelle une méthode
                player.getInventory().setItemStack(hotbarSlot, ItemStack.of(Material.BRICK));
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            listener.followup(event -> assertEquals(inventory, event.getInventory()));
            // Appelle une méthode
            shiftClickOpenInventory(player, 1);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Instruction de code
            assertEquals(ItemStack.of(Material.GOLD_INGOT), player.getInventory().getItemStack(35)); // The item should appear in the bottom right of the player's inventory excluding the hotbar
        // Fin d'un bloc/d'une expression
        }
        // Cancel event
        // Début d'un bloc
        {
            // Appelle une méthode
            listener.followup(event -> event.setCancelled(true));
            // Appelle une méthode
            leftClickOpenInventory(player, 0);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem(), "Left click cancellation did not work");
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(0));
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
                assertEquals(new Click.Left(9), event.getClick());

                // Appelle une méthode
                Click.Left left = assertInstanceOf(Click.Left.class, event.getClick());

                // Appelle une méthode
                event.getInventory().setItemStack(left.slot(), ItemStack.of(Material.DIAMOND, 5));
                // Appelle une méthode
                player.getInventory().setCursorItem(ItemStack.of(Material.DIAMOND));
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            leftClick(player, 9);
            // Appelle une méthode
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.DIAMOND, 6), player.getInventory().getItemStack(9));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void shiftClickOpenInventory(Player player, int slot) {
        // Appelle une méthode
        _leftClick(player.getOpenInventory(), true, player, slot, true);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void shiftClick(Player player, int slot) {
        // Appelle une méthode
        _leftClick(player.getOpenInventory(), false, player, slot, true);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void leftClickOpenInventory(Player player, int slot) {
        // Appelle une méthode
        _leftClick(player.getOpenInventory(), true, player, slot, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void leftClick(Player player, int slot) {
        // Appelle une méthode
        _leftClick(player.getOpenInventory(), false, player, slot, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void _leftClick(AbstractInventory openInventory, boolean clickOpenInventory, Player player, int slot, boolean shift) {
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
        player.addPacketToQueue(new ClientClickWindowPacket(windowId, 0, (short) slot, (byte) 0,
                // Appelle une méthode
                shift ? ClientClickWindowPacket.ClickType.QUICK_MOVE : ClientClickWindowPacket.ClickType.PICKUP, Map.of(), ItemStack.Hash.AIR));
        // Appelle une méthode
        player.interpretPacketQueue();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}