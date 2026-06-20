// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerTickEndEvent;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientTickEndPacket;

// Type declaration (class/interface/enum/record)
public final class PlayerTickListener {

    // Start of a method/block
    public static void listener(ClientTickEndPacket packet, Player player) {
        // Calls a method
        EventDispatcher.call(new PlayerTickEndEvent(player));
    // End of a block/expression
    }

// End of a block/expression
}
