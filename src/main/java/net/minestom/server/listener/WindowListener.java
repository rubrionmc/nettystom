// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.inventory.InventoryButtonClickEvent;
// Import of a required class
import net.minestom.server.event.inventory.InventoryPreClickEvent;
// Import of a required class
import net.minestom.server.inventory.AbstractInventory;
// Import of a required class
import net.minestom.server.inventory.Inventory;
// Import of a required class
import net.minestom.server.inventory.click.Click;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.network.packet.client.common.ClientPongPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientClickWindowButtonPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientClickWindowPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientCloseWindowPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.SetCursorItemPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class WindowListener {

    // Start of a method/block
    public static void clickWindowListener(ClientClickWindowPacket packet, Player player) {
        // Calls a method
        final int windowId = packet.windowId();
        // Assigns a value
        final boolean playerInventory = windowId == 0;
        // Calls a method
        final AbstractInventory inventory = playerInventory ? player.getInventory() : player.getOpenInventory();

        // Prevent some invalid packets
        // Branch: checks a condition
        if (inventory == null || packet.slot() == -1) return;

        // Process the click
        // Annotation for the following element
        @Nullable Integer size = playerInventory ? null : inventory.getSize();
        // Calls a method
        Click click = player.getClickPreprocessor().processClick(packet, size);

        // Assigns a value
        boolean successful = true;
        // Start of a method/block
        check: if (click != null) {
            // Disallow creative clicks when not in creative
            // Calls a method
            boolean isNotCreative = player.getGameMode() != GameMode.CREATIVE;
            // Branch: checks a condition
            if (isNotCreative && player.getClickPreprocessor().isCreativeClick(click, !player.getInventory().getCursorItem().isAir())) {
                // Assigns a value
                successful = false;
                // Breaks out of the loop/block
                break check;
            // End of a block/expression
            }

            // Reset the didCloseInventory field
            // Wait for events to possibly close the inventory
            // Calls a method
            player.UNSAFE_changeDidCloseInventory(false);

            // Calls a method
            Click.Window window = Click.toWindow(click, size);
            // Call InventoryPreClickEvent
            // Calls a method
            InventoryPreClickEvent inventoryPreClickEvent = new InventoryPreClickEvent(window.inOpened() ? inventory : player.getInventory(), player, window.click());
            // Calls a method
            EventDispatcher.call(inventoryPreClickEvent);

            // Calls a method
            click = Click.fromWindow(new Click.Window(window.inOpened(), inventoryPreClickEvent.getClick()), size);

            // Branch: checks a condition
            if (player.didCloseInventory()) {
                // Cancel the click if the inventory has been closed by Player#closeInventory
                // Calls a method
                player.UNSAFE_changeDidCloseInventory(false);
                // Assigns a value
                successful = false;
            // Branch: checks a condition
            } else if (inventoryPreClickEvent.isCancelled()) {
                // Cancel it if the event is cancelled and we haven't already done that
                // Assigns a value
                successful = false;
            // Alternative branch of the condition
            } else {
                // Calls a method
                successful = inventory.handleClick(player, click);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Prevent ghost item when the click is cancelled
        // Branch: checks a condition
        if (!successful) {
            // Calls a method
            player.getInventory().update(player);
            // Branch: checks a condition
            if (!playerInventory) {
                // Calls a method
                inventory.update(player);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Resync in case the client sent item does not match what we think it should be.
        // Calls a method
        ItemStack cursorItem = player.getInventory().getCursorItem();
        // Branch: checks a condition
        if (!ItemStack.Hash.of(cursorItem).equals(packet.clickedItem()))
            // Calls a method
            player.sendPacket(new SetCursorItemPacket(cursorItem));
    // End of a block/expression
    }

    // Start of a method/block
    public static void pong(ClientPongPacket packet, Player player) {
        // Empty
    // End of a block/expression
    }

    // Start of a method/block
    public static void closeWindowListener(ClientCloseWindowPacket packet, Player player) {
        // Calls a method
        player.closeInventory(true, (byte) packet.windowId());
    // End of a block/expression
    }

    // Start of a method/block
    public static void inventoryButtonClickListener(ClientClickWindowButtonPacket packet, Player player) {
        // Calls a method
        AbstractInventory inventory = player.getOpenInventory();

        // Can't press a button if the inventory is not open
        // Branch: checks a condition
        if (inventory == null) return;

        // Can't press a button if the inventory is different from the packet's window id
        // Branch: checks a condition
        if (packet.windowId() != (int) inventory.getWindowId()) return;

        // Calls a method
        EventDispatcher.call(new InventoryButtonClickEvent(player, inventory, packet.buttonId()));
    // End of a block/expression
    }
// End of a block/expression
}
