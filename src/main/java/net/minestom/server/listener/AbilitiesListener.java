// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerStartFlyingEvent;
// Import of a required class
import net.minestom.server.event.player.PlayerStopFlyingEvent;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientPlayerAbilitiesPacket;

// Type declaration (class/interface/enum/record)
public class AbilitiesListener {

    // Start of a method/block
    public static void listener(ClientPlayerAbilitiesPacket packet, Player player) {
        // Calls a method
        final boolean canFly = player.isAllowFlying() || player.getGameMode() == GameMode.CREATIVE;

        // Branch: checks a condition
        if (canFly) {
            // Calls a method
            final boolean isFlying = (packet.flags() & 0x2) > 0;

            // Calls a method
            player.refreshFlying(isFlying);

            // Branch: checks a condition
            if (isFlying) {
                // Calls a method
                PlayerStartFlyingEvent startFlyingEvent = new PlayerStartFlyingEvent(player);
                // Calls a method
                EventDispatcher.call(startFlyingEvent);
            // Alternative branch of the condition
            } else {
                // Calls a method
                PlayerStopFlyingEvent stopFlyingEvent = new PlayerStopFlyingEvent(player);
                // Calls a method
                EventDispatcher.call(stopFlyingEvent);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
