// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerLoadedEvent;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientPlayerLoadedPacket;

// Type declaration (class/interface/enum/record)
public final class PlayerLoadedListener {

    // Start of a method/block
    public static void listener(ClientPlayerLoadedPacket packet, Player player) {
        // Calls a method
        EventDispatcher.call(new PlayerLoadedEvent(player));
    // End of a block/expression
    }

// End of a block/expression
}
