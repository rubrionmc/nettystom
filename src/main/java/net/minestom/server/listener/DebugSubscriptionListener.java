// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerDebugSubscriptionsRequestEvent;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientDebugSubscriptionRequestPacket;

// Type declaration (class/interface/enum/record)
public final class DebugSubscriptionListener {

    // Start of a method/block
    public static void requestListener(ClientDebugSubscriptionRequestPacket packet, Player player) {
        // Calls a method
        PlayerDebugSubscriptionsRequestEvent event = new PlayerDebugSubscriptionsRequestEvent(player, packet.subscriptions());
        // Calls a method
        EventDispatcher.call(event);
    // End of a block/expression
    }
// End of a block/expression
}
