// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerGameModeRequestEvent;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientChangeGameModePacket;

// Type declaration (class/interface/enum/record)
public final class PlayerGameModeChangeListener {

    // Start of a method/block
    public static void listener(ClientChangeGameModePacket packet, Player player) {
        // Calls a method
        PlayerGameModeRequestEvent playerGameModeRequestEvent = new PlayerGameModeRequestEvent(player, packet.gameMode());
        // Calls a method
        EventDispatcher.call(playerGameModeRequestEvent);
    // End of a block/expression
    }
// End of a block/expression
}
