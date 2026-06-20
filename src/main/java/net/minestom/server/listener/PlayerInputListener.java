// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientInputPacket;

// Déclaration de type (classe/interface/enum/record)
public class PlayerInputListener {

    // Début d'une méthode/d'un bloc
    public static void listener(ClientInputPacket packet, Player player) {
        // Instruction de code
        player.refreshInput(
                // Instruction de code
                packet.forward(), packet.backward(),
                // Instruction de code
                packet.left(), packet.right(),
                // Instruction de code
                packet.jump(),
                // Instruction de code
                packet.shift(),
                // Instruction de code
                packet.sprint()
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
