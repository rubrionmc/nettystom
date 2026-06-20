// Package declaration for this file
package net.minestom.server.listener.common;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.client.common.ClientKeepAlivePacket;

// Import of a required class
import java.util.concurrent.TimeUnit;

// Type declaration (class/interface/enum/record)
public final class KeepAliveListener {
    // Calls a method
    private static final Component KICK_MESSAGE = Component.text("Bad Keep Alive packet", NamedTextColor.RED);

    // Start of a method/block
    public static void listener(ClientKeepAlivePacket packet, Player player) {
        // Calls a method
        final long packetId = packet.id();
        // Branch: checks a condition
        if (packetId != player.getLastKeepAlive()) {
            // Calls a method
            player.kick(KICK_MESSAGE);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Calls a method
        player.refreshAnswerKeepAlive(true);
        // Update latency
        // Calls a method
        final long latencyNanos = System.nanoTime() - packetId;

        // Calls a method
        final int latency = (int) TimeUnit.NANOSECONDS.toMillis(latencyNanos);
        // Calls a method
        player.refreshLatency(latency);
    // End of a block/expression
    }
// End of a block/expression
}
