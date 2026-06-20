// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerPickBlockEvent;
// Import of a required class
import net.minestom.server.event.player.PlayerPickEntityEvent;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientPickItemFromBlockPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientPickItemFromEntityPacket;

// Type declaration (class/interface/enum/record)
public class PlayerPickListener {

    // Start of a method/block
    public static void playerPickBlockListener(ClientPickItemFromBlockPacket packet, Player player) {
        // Calls a method
        final Instance instance = player.getInstance();
        // Branch: checks a condition
        if (instance == null) return;
        // Calls a method
        final Block block = instance.getBlock(packet.pos());
        // Calls a method
        final boolean includeData = packet.includeData();

        // Calls a method
        PlayerPickBlockEvent playerPickBlockEvent = new PlayerPickBlockEvent(player, instance, block, packet.pos().asBlockVec(), includeData);
        // Calls a method
        EventDispatcher.call(playerPickBlockEvent);
    // End of a block/expression
    }

    // Start of a method/block
    public static void playerPickEntityListener(ClientPickItemFromEntityPacket packet, Player player) {
        // Calls a method
        final Instance instance = player.getInstance();
        // Branch: checks a condition
        if (instance == null) return;
        // Calls a method
        final Entity entity = instance.getEntityById(packet.entityId());
        // Calls a method
        final boolean includeData = packet.includeData();

        // Calls a method
        PlayerPickEntityEvent playerPickEntityEvent = new PlayerPickEntityEvent(player, entity, includeData);
        // Calls a method
        EventDispatcher.call(playerPickEntityEvent);
    // End of a block/expression
    }
// End of a block/expression
}
