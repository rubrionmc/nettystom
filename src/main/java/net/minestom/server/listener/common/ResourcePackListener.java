// Package declaration for this file
package net.minestom.server.listener.common;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerResourcePackStatusEvent;
// Import of a required class
import net.minestom.server.network.packet.client.common.ClientResourcePackStatusPacket;

// Type declaration (class/interface/enum/record)
public class ResourcePackListener {

    // Start of a method/block
    public static void listener(ClientResourcePackStatusPacket packet, Player player) {
        // Calls a method
        EventDispatcher.call(new PlayerResourcePackStatusEvent(player, packet.id(), packet.status()));
        // Branch: checks a condition
        if (!player.isOnline()) return;

        // Run adventure callbacks for the resource pack
        // Calls a method
        player.onResourcePackStatus(packet.id(), packet.status());
    // End of a block/expression
    }
// End of a block/expression
}
