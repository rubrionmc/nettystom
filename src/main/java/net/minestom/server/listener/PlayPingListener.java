// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.client.common.ClientPingRequestPacket;
// Import of a required class
import net.minestom.server.network.packet.server.common.PingResponsePacket;

// Type declaration (class/interface/enum/record)
public final class PlayPingListener {

    // Start of a method/block
    public static void requestListener(ClientPingRequestPacket packet, Player player) {
        // Calls a method
        player.sendPacket(new PingResponsePacket(packet.number()));
    // End of a block/expression
    }
// End of a block/expression
}
