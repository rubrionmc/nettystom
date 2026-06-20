// Package declaration for this file
package net.minestom.server.listener.common;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerPluginMessageEvent;
// Import of a required class
import net.minestom.server.network.packet.client.common.ClientPluginMessagePacket;

// Type declaration (class/interface/enum/record)
public class PluginMessageListener {

    // Start of a method/block
    public static void listener(ClientPluginMessagePacket packet, Player player) {
        // Calls a method
        PlayerPluginMessageEvent pluginMessageEvent = new PlayerPluginMessageEvent(player, packet.channel(), packet.data());
        // Calls a method
        EventDispatcher.call(pluginMessageEvent);
    // End of a block/expression
    }

// End of a block/expression
}
