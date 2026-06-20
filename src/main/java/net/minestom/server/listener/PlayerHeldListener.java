// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerChangeHeldSlotEvent;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientHeldItemChangePacket;
// Import of a required class
import net.minestom.server.utils.MathUtils;

// Type declaration (class/interface/enum/record)
public class PlayerHeldListener {

    // Start of a method/block
    public static void heldListener(ClientHeldItemChangePacket packet, Player player) {
        // Branch: checks a condition
        if (!MathUtils.isBetween(packet.slot(), 0, 8)) {
            // Incorrect packet, ignore
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        final byte newSlot = (byte) packet.slot();
        // Calls a method
        final byte oldSlot = player.getHeldSlot();

        // Calls a method
        PlayerChangeHeldSlotEvent changeHeldSlotEvent = new PlayerChangeHeldSlotEvent(player, oldSlot, newSlot);
        // Calls a method
        EventDispatcher.call(changeHeldSlotEvent);

        // Branch: checks a condition
        if (!changeHeldSlotEvent.isCancelled()) {
            // Event hasn't been canceled, process it

            // Calls a method
            final byte resultSlot = changeHeldSlotEvent.getNewSlot();

            // If the held slot has been changed by the event, send the change to the player
            // Branch: checks a condition
            if (resultSlot != newSlot) {
                // Calls a method
                player.setHeldItemSlot(resultSlot);
            // Alternative branch of the condition
            } else {
                // Otherwise, simply refresh the player field
                // Calls a method
                player.refreshHeldSlot(resultSlot);
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else {
            // Event has been canceled, send the last held slot to refresh the client
            // Calls a method
            player.setHeldItemSlot(oldSlot);
        // End of a block/expression
        }

        // Player is not using offhand, reset item use
        // Branch: checks a condition
        if (player.getItemUseHand() != PlayerHand.OFF) {
            // Calls a method
            player.refreshActiveHand(false, false, false);
            // Calls a method
            player.clearItemUse();
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
