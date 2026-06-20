// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerEditSignEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientUpdateSignPacket;

// Déclaration de type (classe/interface/enum/record)
public class EditSignListener {
    // Début d'une méthode/d'un bloc
    public static void listener(ClientUpdateSignPacket packet, Player player) {
        // Appelle une méthode
        BlockVec position = new BlockVec(packet.blockPosition());
        // Appelle une méthode
        Block block = player.getInstance().getBlock(position);
        // Instruction de code
        EventDispatcher.call(new PlayerEditSignEvent(
                // Instruction de code
                player,
                // Instruction de code
                block,
                // Instruction de code
                position,
                // Instruction de code
                packet.lines(),
                // Instruction de code
                packet.isFrontText()
        // Instruction de code
        ));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
