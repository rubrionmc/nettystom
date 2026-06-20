// Package declaration for this file
package net.minestom.server.inventory.click.integration;

// Import of a required class
import net.minestom.server.coordinate.Pos;
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
public class RightClickIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void rightSelf(Env env) {
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
        inventory.setItemStack(2, ItemStack.of(Material.DIAMOND));
        // Empty click
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(event.getInventory(), inventory);
                // Calls a method
                assertEquals(new Click.Right(0), event.getClick());
                // Calls a method
                assertEquals(ItemStack.AIR, inventory.getCursorItem());
            // End of a block/expression
            });
            // Calls a method
            rightClick(player, 0);
        // End of a block/expression
        }
        // Pickup diamond
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(new Click.Right(1), event.getClick());
                // Calls a method
                assertEquals(ItemStack.AIR, inventory.getCursorItem());
                // Calls a method
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
            // End of a block/expression
            });
            // Calls a method
            rightClick(player, 1);
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
                assertEquals(new Click.Right(1), event.getClick());
                // Calls a method
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getCursorItem());
                // Calls a method
                assertEquals(ItemStack.AIR, inventory.getItemStack(1));
            // End of a block/expression
            });
            // Calls a method
            rightClick(player, 1);
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getCursorItem());
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
        // End of a block/expression
        }
        // Pickup diamond
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(new Click.Right(1), event.getClick());
                // Calls a method
                assertEquals(ItemStack.AIR, inventory.getCursorItem());
                // Calls a method
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
            // End of a block/expression
            });
            // Calls a method
            rightClick(player, 1);
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getCursorItem());
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getItemStack(1));
        // End of a block/expression
        }
        // Stack diamond
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(new Click.Right(2), event.getClick());
                // Calls a method
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getCursorItem());
                // Calls a method
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(2));
            // End of a block/expression
            });
            // Calls a method
            rightClick(player, 2);
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getCursorItem());
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND, 2), inventory.getItemStack(2));
        // End of a block/expression
        }
        // Cancel event
        // Start of a block
        {
            // Calls a method
            listener.followup(event -> event.setCancelled(true));
            // Calls a method
            rightClick(player, 2);
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getCursorItem(), "Left click cancellation did not work");
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND, 2), inventory.getItemStack(2));
        // End of a block/expression
        }
        // Change items
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                Click.Right right = assertInstanceOf(Click.Right.class, event.getClick());

                // Calls a method
                inventory.setItemStack(right.slot(), ItemStack.of(Material.DIAMOND, 5));
                // Calls a method
                inventory.setCursorItem(ItemStack.of(Material.DIAMOND));
            // End of a block/expression
            });
            // Calls a method
            rightClick(player, 1);
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
    public void rightExternal(Env env) {
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
                assertEquals(new Click.Right(0), event.getClick());
                // Calls a method
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // End of a block/expression
            });
            // Calls a method
            rightClick(player, 0);
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
                assertEquals(new Click.Right(1), event.getClick());
                // Calls a method
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
                // Calls a method
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(1));
            // End of a block/expression
            });
            // Calls a method
            rightClickOpenInventory(player, 1);
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND), player.getInventory().getCursorItem());
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getItemStack(1));
        // End of a block/expression
        }
        // Place back to player inv
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(player.getInventory(), event.getInventory());
                // Calls a method
                assertEquals(new Click.Right(1), event.getClick());
                // Calls a method
                assertEquals(ItemStack.of(Material.DIAMOND), player.getInventory().getCursorItem());
                // Calls a method
                assertEquals(ItemStack.AIR, inventory.getItemStack(1));
                // Calls a method
                assertEquals(ItemStack.AIR, player.getInventory().getItemStack(1));
            // End of a block/expression
            });
            // Calls a method
            rightClick(player, 1);
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND), player.getInventory().getItemStack(1));
        // End of a block/expression
        }
        // Cancel event
        // Start of a block
        {
            // Calls a method
            listener.followup(event -> event.setCancelled(true));
            // Calls a method
            rightClick(player, 1);
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND), player.getInventory().getItemStack(1), "Left click cancellation did not work");
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
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
                assertEquals(new Click.Right(9), event.getClick());

                // Calls a method
                Click.Right right = assertInstanceOf(Click.Right.class, event.getClick());

                // Calls a method
                event.getInventory().setItemStack(right.slot(), ItemStack.of(Material.DIAMOND, 5));
                // Calls a method
                player.getInventory().setCursorItem(ItemStack.of(Material.DIAMOND));
            // End of a block/expression
            });
            // Calls a method
            rightClick(player, 9);
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND, 6), player.getInventory().getItemStack(9));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void rightClickOpenInventory(Player player, int slot) {
        // Calls a method
        _rightClick(player.getOpenInventory(), true, player, slot);
    // End of a block/expression
    }

    // Start of a method/block
    private void rightClick(Player player, int slot) {
        // Calls a method
        _rightClick(player.getOpenInventory(), false, player, slot);
    // End of a block/expression
    }

    // Start of a method/block
    private void _rightClick(AbstractInventory openInventory, boolean clickOpenInventory, Player player, int slot) {
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
        player.addPacketToQueue(new ClientClickWindowPacket(windowId, 0, (short) slot, (byte) 1,
                // Calls a method
                ClientClickWindowPacket.ClickType.PICKUP, Map.of(), ItemStack.Hash.AIR));
        // Calls a method
        player.interpretPacketQueue();
    // End of a block/expression
    }
// End of a block/expression
}
