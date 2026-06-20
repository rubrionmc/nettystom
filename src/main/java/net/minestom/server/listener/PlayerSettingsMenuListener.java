// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerGameRulesRequestEvent;
// Import of a required class
import net.minestom.server.event.player.PlayerSetGameRulesEvent;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientSetGameRulesPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientStatusPacket;

// Type declaration (class/interface/enum/record)
public final class PlayerSettingsMenuListener {

    // Start of a method/block
    public static void requestGameRules(ClientStatusPacket ignored, Player player) {
        // Calls a method
        EventDispatcher.call(new PlayerGameRulesRequestEvent(player));
    // End of a block/expression
    }

    // Start of a method/block
    public static void setGameRules(ClientSetGameRulesPacket packet, Player player) {
        // Calls a method
        EventDispatcher.call(new PlayerSetGameRulesEvent(player, packet.entries()));
    // End of a block/expression
    }

    //todo: add listeners for setting difficulty
// End of a block/expression
}
