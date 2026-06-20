// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.AdvancementTabEvent;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientAdvancementTabPacket;

// Type declaration (class/interface/enum/record)
public class AdvancementTabListener {

    // Start of a method/block
    public static void listener(ClientAdvancementTabPacket packet, Player player) {
        // Calls a method
        final String tabIdentifier = packet.tabIdentifier();
        // Branch: checks a condition
        if (tabIdentifier != null) {
            // Calls a method
            EventDispatcher.call(new AdvancementTabEvent(player, packet.action(), tabIdentifier));
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
