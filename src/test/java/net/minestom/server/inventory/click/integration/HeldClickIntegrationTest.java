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
public class HeldClickIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void heldSelf(Env env) {
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
        inventory.setItemStack(2, ItemStack.of(Material.GOLD_INGOT));
        // Calls a method
        inventory.setItemStack(3, ItemStack.of(Material.EGG));
        // Calls a method
        inventory.setItemStack(6, ItemStack.of(Material.DIAMOND));
        // Empty
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(event.getInventory(), inventory);
                // Calls a method
                assertEquals(new Click.HotbarSwap(5, 4), event.getClick());

                // Calls a method
                Click.HotbarSwap swap = assertInstanceOf(Click.HotbarSwap.class, event.getClick());
                // Calls a method
                assertEquals(ItemStack.AIR, inventory.getCursorItem());
                // Calls a method
                assertEquals(ItemStack.AIR, inventory.getItemStack(swap.slot()));
            // End of a block/expression
            });
            // Calls a method
            heldClick(player, 4, 5);
        // End of a block/expression
        }
        // Swap air
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(event.getInventory(), inventory);
                // Calls a method
                assertEquals(new Click.HotbarSwap(0, 1), event.getClick());

                // Calls a method
                Click.HotbarSwap swap = assertInstanceOf(Click.HotbarSwap.class, event.getClick());
                // Calls a method
                assertEquals(ItemStack.AIR, inventory.getCursorItem());
                // Calls a method
                assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(swap.slot()));
            // End of a block/expression
            });
            // Calls a method
            heldClick(player, 1, 0);
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getCursorItem());
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getItemStack(1));
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(0));
        // End of a block/expression
        }
        // Swap items
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(new Click.HotbarSwap(2, 0), event.getClick());
                // Calls a method
                assertEquals(ItemStack.AIR, inventory.getCursorItem());
                // Calls a method
                assertEquals(ItemStack.AIR, inventory.getItemStack(1));
            // End of a block/expression
            });
            // Calls a method
            heldClick(player, 0, 2);
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getCursorItem());
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getItemStack(1));
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(2));
            // Calls a method
            assertEquals(ItemStack.of(Material.GOLD_INGOT), inventory.getItemStack(0));
        // End of a block/expression
        }
        // Swap offhand
        // Start of a block
        {
            // Calls a method
            listener.followup(event -> assertEquals(new Click.OffhandSwap(3), event.getClick()));
            // Calls a method
            heldClick(player, 3, 40);
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getItemStack(3));
            // Calls a method
            assertEquals(ItemStack.of(Material.EGG), player.getItemInOffHand());
        // End of a block/expression
        }
        // Cancel event
        // Start of a block
        {
            // Calls a method
            listener.followup(event -> event.setCancelled(true));
            // Calls a method
            heldClick(player, 2, 0);
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getCursorItem());
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getItemStack(1));
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(2));
            // Calls a method
            assertEquals(ItemStack.of(Material.GOLD_INGOT), inventory.getItemStack(0));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void heldExternal(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 40, 0));
        // Calls a method
        var inventory = new Inventory(InventoryType.HOPPER, "test");
        // Calls a method
        var playerInv = player.getInventory();
        // Calls a method
        player.openInventory(inventory);
        // Calls a method
        var listener = env.listen(InventoryPreClickEvent.class);
        // Calls a method
        inventory.setItemStack(1, ItemStack.of(Material.DIAMOND));
        // Calls a method
        inventory.setItemStack(2, ItemStack.of(Material.GOLD_INGOT));
        // Calls a method
        inventory.setItemStack(3, ItemStack.of(Material.EGG));
        // Calls a method
        inventory.setItemStack(4, ItemStack.of(Material.DIAMOND));
        // Empty
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(inventory, event.getInventory());
                // Calls a method
                assertEquals(new Click.HotbarSwap(0, 0), event.getClick());
                // Calls a method
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // End of a block/expression
            });
            // Calls a method
            heldClickOpenInventory(player, 0, 0);
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getItemStack(0));
        // End of a block/expression
        }
        // Swap empty
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(inventory, event.getInventory());
                // Calls a method
                assertEquals(new Click.HotbarSwap(0, 1), event.getClick());
                // Calls a method
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // End of a block/expression
            });
            // Calls a method
            heldClickOpenInventory(player, 1, 0);
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getItemStack(1));
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND), playerInv.getItemStack(0));
        // End of a block/expression
        }
        // Swap items
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(inventory, event.getInventory());
                // Calls a method
                assertEquals(new Click.HotbarSwap(0, 2), event.getClick());
                // Calls a method
                assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // End of a block/expression
            });
            // Calls a method
            heldClickOpenInventory(player, 2, 0);
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(2));
            // Calls a method
            assertEquals(ItemStack.of(Material.GOLD_INGOT), playerInv.getItemStack(0));
        // End of a block/expression
        }
        // Swap offhand
        // Start of a block
        {
            // Start of a method/block
            listener.followup(event -> {
                // Calls a method
                assertEquals(inventory, event.getInventory());
                // Calls a method
                assertEquals(new Click.OffhandSwap(3), event.getClick());
            // End of a block/expression
            });
            // Calls a method
            heldClickOpenInventory(player, 3, 40);
            // Calls a method
            assertEquals(ItemStack.AIR, inventory.getItemStack(3));
            // Calls a method
            assertEquals(ItemStack.of(Material.EGG), player.getItemInOffHand());
        // End of a block/expression
        }
        // Cancel event
        // Start of a block
        {
            // Calls a method
            listener.followup(event -> event.setCancelled(true));
            // Calls a method
            heldClickOpenInventory(player, 2, 0);
            // Calls a method
            assertEquals(ItemStack.AIR, player.getInventory().getCursorItem());
            // Calls a method
            assertEquals(ItemStack.of(Material.DIAMOND), inventory.getItemStack(2));
            // Calls a method
            assertEquals(ItemStack.of(Material.GOLD_INGOT), playerInv.getItemStack(0));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void heldClickOpenInventory(Player player, int slot, int target) {
        // Calls a method
        _heldClick(player.getOpenInventory(), true, player, slot, target);
    // End of a block/expression
    }

    // Start of a method/block
    private void heldClick(Player player, int slot, int target) {
        // Calls a method
        _heldClick(player.getOpenInventory(), false, player, slot, target);
    // End of a block/expression
    }

    // Start of a method/block
    private void _heldClick(AbstractInventory openInventory, boolean clickOpenInventory, Player player, int slot, int target) {
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
        player.addPacketToQueue(new ClientClickWindowPacket(windowId, 0, (short) slot, (byte) target,
                // Calls a method
                ClientClickWindowPacket.ClickType.SWAP, Map.of(), ItemStack.Hash.AIR));
        // Calls a method
        player.interpretPacketQueue();
    // End of a block/expression
    }
// End of a block/expression
}
