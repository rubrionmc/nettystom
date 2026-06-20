// Package declaration for this file
package net.minestom.server.listener.common;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerSettingsChangeEvent;
// Import of a required class
import net.minestom.server.network.packet.client.common.ClientSettingsPacket;

// Type declaration (class/interface/enum/record)
public final class SettingsListener {
    // Start of a method/block
    public static void listener(ClientSettingsPacket packet, Player player) {
        // Since viewDistance bounds checking is performed in the refresh function, it is not necessary to check it here
        // Calls a method
        player.refreshSettings(packet.settings());
        // Calls a method
        EventDispatcher.call(new PlayerSettingsChangeEvent(player));
    // End of a block/expression
    }
// End of a block/expression
}
