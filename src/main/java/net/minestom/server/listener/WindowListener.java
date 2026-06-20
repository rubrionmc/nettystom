// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.inventory.InventoryPreClickEvent;
// Import d'une classe nécessaire
import net.minestom.server.inventory.AbstractInventory;
// Import d'une classe nécessaire
import net.minestom.server.inventory.click.Click;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.ClientPongPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientClickWindowPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientCloseWindowPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.SetCursorItemPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class WindowListener {

    // Début d'une méthode/d'un bloc
    public static void clickWindowListener(ClientClickWindowPacket packet, Player player) {
        // Appelle une méthode
        final int windowId = packet.windowId();
        // Instruction de code
        final boolean playerInventory = windowId == 0;
        // Appelle une méthode
        final AbstractInventory inventory = playerInventory ? player.getInventory() : player.getOpenInventory();

        // Prevent some invalid packets
        // Embranchement : vérifie une condition
        if (inventory == null || packet.slot() == -1) return;

        // Process the click
        // Annotation pour l'élément suivant
        @Nullable Integer size = playerInventory ? null : inventory.getSize();
        // Appelle une méthode
        Click click = player.getClickPreprocessor().processClick(packet, size);

        // Affecte une valeur
        boolean successful = true;
        // Début d'une méthode/d'un bloc
        check: if (click != null) {
            // Disallow creative clicks when not in creative
            // Appelle une méthode
            boolean isNotCreative = player.getGameMode() != GameMode.CREATIVE;
            // Embranchement : vérifie une condition
            if (isNotCreative && player.getClickPreprocessor().isCreativeClick(click, !player.getInventory().getCursorItem().isAir())) {
                // Affecte une valeur
                successful = false;
                // Interrompt la boucle/le bloc
                break check;
            // Fin d'un bloc/d'une expression
            }

            // Reset the didCloseInventory field
            // Wait for events to possibly close the inventory
            // Appelle une méthode
            player.UNSAFE_changeDidCloseInventory(false);

            // Appelle une méthode
            Click.Window window = Click.toWindow(click, size);
            // Call InventoryPreClickEvent
            // Appelle une méthode
            InventoryPreClickEvent inventoryPreClickEvent = new InventoryPreClickEvent(window.inOpened() ? inventory : player.getInventory(), player, window.click());
            // Appelle une méthode
            EventDispatcher.call(inventoryPreClickEvent);

            // Appelle une méthode
            click = Click.fromWindow(new Click.Window(window.inOpened(), inventoryPreClickEvent.getClick()), size);

            // Embranchement : vérifie une condition
            if (player.didCloseInventory()) {
                // Cancel the click if the inventory has been closed by Player#closeInventory
                // Appelle une méthode
                player.UNSAFE_changeDidCloseInventory(false);
                // Affecte une valeur
                successful = false;
            // Embranchement : vérifie une condition
            } else if (inventoryPreClickEvent.isCancelled()) {
                // Cancel it if the event is cancelled and we haven't already done that
                // Affecte une valeur
                successful = false;
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                successful = inventory.handleClick(player, click);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Prevent ghost item when the click is cancelled
        // Embranchement : vérifie une condition
        if (!successful) {
            // Appelle une méthode
            player.getInventory().update(player);
            // Embranchement : vérifie une condition
            if (!playerInventory) {
                // Appelle une méthode
                inventory.update(player);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Resync in case the client sent item does not match what we think it should be.
        // Appelle une méthode
        ItemStack cursorItem = player.getInventory().getCursorItem();
        // Embranchement : vérifie une condition
        if (!ItemStack.Hash.of(cursorItem).equals(packet.clickedItem()))
            // Appelle une méthode
            player.sendPacket(new SetCursorItemPacket(cursorItem));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void pong(ClientPongPacket packet, Player player) {
        // Empty
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void closeWindowListener(ClientCloseWindowPacket packet, Player player) {
        // Appelle une méthode
        player.closeInventory(true, (byte) packet.windowId());
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
