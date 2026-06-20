// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientInputPacket;

// Type declaration (class/interface/enum/record)
public class PlayerInputListener {

    // Start of a method/block
    public static void listener(ClientInputPacket packet, Player player) {
        // Code statement
        player.refreshInput(
                // Code statement
                packet.forward(), packet.backward(),
                // Code statement
                packet.left(), packet.right(),
                // Code statement
                packet.jump(),
                // Code statement
                packet.shift(),
                // Code statement
                packet.sprint()
        // End of a block/expression
        );
    // End of a block/expression
    }

// End of a block/expression
}
