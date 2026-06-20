// Package declaration for this file
package net.minestom.server.inventory.click.integration;

// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.EquipmentSlot;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.inventory.InventoryPreClickEvent;
// Import of a required class
import net.minestom.server.inventory.AbstractInventory;
// Import of a required class
import net.minestom.server.inventory.Inventory;
// Import of a required class
import net.minestom.server.inventory.InventoryType;
// Import of a required class
import net.minestom.server.inventory.click.Click;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.item.component.Equippable;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientClickWindowPacket;
// Import of a required class
import net.minestom.server.utils.inventory.PlayerInventoryUtils;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Map;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class LeftClickIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void leftSelf(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 40, 0));
        // Calls a method
        var inventory = player.getInventory();
        // Calls a method
        var listener = env.listen(InventoryPreClickEvent.class);
        // Calls a method
        inventory.setItemStack(1, ItemStack.of(Material.DIAMOND));
        // Calls a method
        inventory.setItemStack(2, ItemStack.of(Material.DIAMOND_HELMET));
        // Calls a method
        inventory.setItemStack(3, ItemStack.of(Material.SHIELD));
        // Code statement
        inventory.setItemStack(4, ItemStack.builder(Material.COOKED_BEEF).set(DataComponents.EQUIPPABLE,
                // Creates a new object
                new Equippable(EquipmentSlot.LEGGINGS, Equippable.DEFAULT_EQUIP_SOUND, null, null, null,
                        // Calls a method
                        true, true, true, true, true, Equippable.DEFAULT_SHEARING_SOUND)).build());
        // Empty click
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(event.getInventory(), inventory);
                // Calls a method
                assertEquals(new Click.Left(0), event.getClick());
                // Calls a method
                assertEquals(ItemStack.AIR, inventory.getCursorItem());
            // End of a block/expression
            });
            // Calls a method
            leftClick(player, 0);
        // End of a block/expression
        }
        // Pickup diamond
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(new Click.Left(1), event.getClick());
                // Calls a method
                assertEquals(ItemStack.AIR, inventory.getCursorItem());
                // Calls a method
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
            // End of a block/expression
            });
            // Calls a method
            leftClick(player, 1);
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getCursorItem());
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getItemStack(1));
        // End of a block/expression
        }
        // Place it back
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(new Click.Left(1), event.getClick());
                // Calls a method
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getCursorItem());
                // Calls a method
                assertEquals(ItemStack.AIR, inventory.getItemStack(1));
            // End of a block/expression
            });
            // Calls a method
            leftClick(player, 1);
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getCursorItem());
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
        // End of a block/expression
        }
        // Shift click an armor item into the armor slot
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Calls a method
                assertEquals(new Click.LeftShift(2), event.getClick());
                // Calls a method
                assertEquals(ItemStack.of(Material.DIAMOND_HELMET), player.getInventory().getItemStack(2));
            // End of a block/expression
            });
            // Calls a method
            shiftClick(player, 2);
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getItemStack(2));
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND_HELMET), player.getHelmet());
        // End of a block/expression
        }
        // Shift click non armor material but equippable item into the armor slot
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Calls a method
                assertEquals(new Click.LeftShift(4), event.getClick());
                // Calls a method
                assertEquals(Material.COOKED_BEEF, player.getInventory().getItemStack(4).material());
            // End of a block/expression
            });
            // Calls a method
            shiftClick(player, 4);
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getItemStack(4));
            // Calls a method
            assertEquals(Material.COOKED_BEEF, player.getLeggings().material());
        // End of a block/expression
        }
        // Shift click an armor slot item back into the inventory
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Calls a method
                assertEquals(new Click.LeftShift(41), event.getClick());
                // Calls a method
                assertEquals(ItemStack.of(Material.DIAMOND_HELMET), player.getHelmet());
            // End of a block/expression
            });
            // Calls a method
            shiftClick(player, 41);
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Calls a method
            assertEquals(ItemStack.of(Material.AIR), player.getHelmet());
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND_HELMET), player.getInventory().getItemStack(9));
        // End of a block/expression
        }
        // Shift click a shield into the off-hand slot
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Calls a method
                assertEquals(new Click.LeftShift(3), event.getClick());
                // Calls a method
                assertEquals(ItemStack.of(Material.SHIELD), player.getInventory().getItemStack(3));
            // End of a block/expression
            });
            // Calls a method
            shiftClick(player, 3);
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getItemStack(3));
            // Calls a method
            assertEquals(ItemStack.of(Material.SHIELD), player.getInventory().getItemStack(45));
        // End of a block/expression
        }
        // Shift click a shield in the off-hand slot to the inventory
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Calls a method
                assertEquals(new Click.LeftShift(45), event.getClick());
                // Calls a method
                assertEquals(ItemStack.of(Material.SHIELD), player.getInventory().getItemStack(45));
            // End of a block/expression
            });
            // Calls a method
            shiftClick(player, 45);
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getItemStack(45));
            // Calls a method
            assertEquals(ItemStack.of(Material.SHIELD), player.getInventory().getItemStack(10));
        // End of a block/expression
        }
        // Shift click a player crafting inventory ingredient to the player inventory
        // Start of a block
        {
            // Calls a method
            player.getInventory().setItemStack(37, ItemStack.of(Material.GOLDEN_HELMET));
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Calls a method
                assertEquals(new Click.LeftShift(37), event.getClick());
                // Calls a method
                assertEquals(ItemStack.of(Material.GOLDEN_HELMET), player.getInventory().getItemStack(37));
            // End of a block/expression
            });
            // Calls a method
            shiftClick(player, 37);
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getItemStack(37));
            // Calls a method
            assertEquals(ItemStack.of(Material.GOLDEN_HELMET), player.getInventory().getItemStack(11));
        // End of a block/expression
        }
        // Shift click a player crafting inventory result to the player hotbar
        // Start of a block
        {
            // Calls a method
            player.getInventory().setItemStack(36, ItemStack.of(Material.IRON_HELMET));
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Calls a method
                assertEquals(new Click.LeftShift(36), event.getClick());
                // Calls a method
                assertEquals(ItemStack.of(Material.IRON_HELMET), player.getInventory().getItemStack(36));
            // End of a block/expression
            });
            // Calls a method
            shiftClick(player, 36);
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getItemStack(36));
            // Calls a method
            assertEquals(ItemStack.of(Material.IRON_HELMET), player.getInventory().getItemStack(8));
        // End of a block/expression
        }
        // Cancel event
        // Start of a block
        {
            // Calls a method
            listener.followup(event -> event.setCancelled(true));
            // Calls a method
            leftClick(player, 1);
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getCursorItem(), "Left click cancellation did not work");
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
        // End of a block/expression
        }
        // Change items
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                Click.Left left = assertInstanceOf(Click.Left.class, event.getClick());

                // Calls a method
                inventory.setItemStack(left.slot(), ItemStack.of(Material.DIAMOND, 5));
                // Calls a method
                inventory.setCursorItem(ItemStack.of(Material.DIAMOND));
            // End of a block/expression
            });
            // Calls a method
            leftClick(player, 1);
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getCursorItem());
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND, 6), inventory.getItemStack(1));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void leftExternal(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 40, 0));
        // Calls a method
        var inventory = new Inventory(InventoryType.HOPPER, "test");
        // Calls a method
        player.openInventory(inventory);
        // Calls a method
        var listener = env.listen(InventoryPreClickEvent.class);
        // Calls a method
        inventory.setItemStack(1, ItemStack.of(Material.DIAMOND));
        // Empty click in player inv
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(player.getInventory(), event.getInventory());
                // Calls a method
                assertEquals(new Click.Left(0), event.getClick());
                // Calls a method
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // End of a block/expression
            });
            // Calls a method
            leftClick(player, 0);
        // End of a block/expression
        }
        // Pickup diamond
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(inventory, event.getInventory());
                // Calls a method
                assertEquals(new Click.Left(1), event.getClick());
                // Ensure that the inventory didn't change yet
                // Calls a method
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Calls a method
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
            // End of a block/expression
            });
            // Calls a method
            leftClickOpenInventory(player, 1);
            // Verify inventory changes
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND), player.getInventory().getCursorItem());
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getItemStack(1));
        // End of a block/expression
        }
        // Place it back
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(inventory, event.getInventory());
                // Calls a method
                assertEquals(new Click.Left(1), event.getClick());
                // Calls a method
                assertEquals(ItemStack.of(Material.DIAMOND), player.getInventory().getCursorItem());
                // Calls a method
                assertEquals(ItemStack.AIR, inventory.getItemStack(1));
            // End of a block/expression
            });
            // Calls a method
            leftClickOpenInventory(player, 1);
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
        // End of a block/expression
        }
        // Shift click the item into the player's inventory
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(inventory, event.getInventory());
                // Calls a method
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Calls a method
                assertEquals(new Click.LeftShift(1), event.getClick());
                // Calls a method
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
            // End of a block/expression
            });
            // Calls a method
            shiftClickOpenInventory(player, 1);
            // Code statement
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem()); // When shift-clicking, the cursor item shouldn't change
            // Code statement
            assertEquals(ItemStack.of(Material.DIAMOND), player.getInventory().getItemStack(8)); // The item should appear in the player's last hotbar slot
        // End of a block/expression
        }
        // Shift click the item back into the external inventory
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(player.getInventory(), event.getInventory());
                // Calls a method
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Calls a method
                assertEquals(new Click.LeftShift(8), event.getClick());
                // Calls a method
                assertEquals(ItemStack.of(Material.DIAMOND), player.getInventory().getItemStack(8));
            // End of a block/expression
            });
            // Calls a method
            shiftClick(player, 8);
            // Code statement
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem()); // When shift-clicking, the cursor item shouldn't change
            // Code statement
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(0)); // The item should appear in the external inventory's first slot
        // End of a block/expression
        }
        // Shift click into the player's inventory when their hotbar is full
        // Start of a block
        {
            // Calls a method
            inventory.setItemStack(1, ItemStack.of(Material.GOLD_INGOT));
            // Loop: repeats a block
            for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
                // Calls a method
                player.getInventory().setItemStack(hotbarSlot, ItemStack.of(Material.BRICK));
            // End of a block/expression
            }
            // Calls a method
            listener.followup(event -> assertEquals(inventory, event.getInventory()));
            // Calls a method
            shiftClickOpenInventory(player, 1);
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Code statement
            assertEquals(ItemStack.of(Material.GOLD_INGOT), player.getInventory().getItemStack(35)); // The item should appear in the bottom right of the player's inventory excluding the hotbar
        // End of a block/expression
        }
        // Cancel event
        // Start of a block
        {
            // Calls a method
            listener.followup(event -> event.setCancelled(true));
            // Calls a method
            leftClickOpenInventory(player, 0);
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem(), "Left click cancellation did not work");
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(0));
        // End of a block/expression
        }
        // Change items
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(player.getInventory(), event.getInventory());
                // Calls a method
                assertEquals(new Click.Left(9), event.getClick());

                // Calls a method
                Click.Left left = assertInstanceOf(Click.Left.class, event.getClick());

                // Calls a method
                event.getInventory().setItemStack(left.slot(), ItemStack.of(Material.DIAMOND, 5));
                // Calls a method
                player.getInventory().setCursorItem(ItemStack.of(Material.DIAMOND));
            // End of a block/expression
            });
            // Calls a method
            leftClick(player, 9);
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND, 6), player.getInventory().getItemStack(9));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void shiftClickOpenInventory(Player player, int slot) {
        // Calls a method
        _leftClick(player.getOpenInventory(), true, player, slot, true);
    // End of a block/expression
    }

    // Start of a method/block
    private void shiftClick(Player player, int slot) {
        // Calls a method
        _leftClick(player.getOpenInventory(), false, player, slot, true);
    // End of a block/expression
    }

    // Start of a method/block
    private void leftClickOpenInventory(Player player, int slot) {
        // Calls a method
        _leftClick(player.getOpenInventory(), true, player, slot, false);
    // End of a block/expression
    }

    // Start of a method/block
    private void leftClick(Player player, int slot) {
        // Calls a method
        _leftClick(player.getOpenInventory(), false, player, slot, false);
    // End of a block/expression
    }

    // Start of a method/block
    private void _leftClick(AbstractInventory openInventory, boolean clickOpenInventory, Player player, int slot, boolean shift) {
        // Calls a method
        final byte windowId = openInventory != null ? openInventory.getWindowId() : 0;
        // Branch: checks a condition
        if (clickOpenInventory) {
            // Calls a method
            assertNotNull(openInventory);
            // Do not touch slot
        // Alternative branch of the condition
        } else {
            // Calls a method
            int offset = openInventory != null ? openInventory.getInnerSize() : 0;
            // Calls a method
            slot = PlayerInventoryUtils.convertMinestomSlotToWindowSlot(slot);
            // Branch: checks a condition
            if (openInventory != null) {
                // Assigns a value
                slot = slot - 9 + offset;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Code statement
        player.addPacketToQueue(new ClientClickWindowPacket(windowId, 0, (short) slot, (byte) 0,
                // Calls a method
                shift ? ClientClickWindowPacket.ClickType.QUICK_MOVE : ClientClickWindowPacket.ClickType.PICKUP, Map.of(), ItemStack.Hash.AIR));
        // Calls a method
        player.interpretPacketQueue();
    // End of a block/expression
    }
// End of a block/expression
}