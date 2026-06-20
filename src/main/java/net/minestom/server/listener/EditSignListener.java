// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.coordinate.BlockVec;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerEditSignEvent;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientUpdateSignPacket;

// Type declaration (class/interface/enum/record)
public class EditSignListener {
    // Start of a method/block
    public static void listener(ClientUpdateSignPacket packet, Player player) {
        // Calls a method
        Instance instance = player.getInstance();
        // Calls a method
        BlockVec position = packet.blockPosition().asBlockVec();
        // Calls a method
        Block block = instance.getBlock(position);
        // Code statement
        EventDispatcher.call(new PlayerEditSignEvent(
                // Code statement
                player,
                // Code statement
                instance,
                // Code statement
                block,
                // Code statement
                position,
                // Code statement
                packet.lines(),
                // Code statement
                packet.isFrontText()
        // Code statement
        ));
    // End of a block/expression
    }
// End of a block/expression
}
