// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerHandAnimationEvent;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientAnimationPacket;

// Déclaration de type (classe/interface/enum/record)
public class AnimationListener {

    // Début d'une méthode/d'un bloc
    public static void animationListener(ClientAnimationPacket packet, Player player) {
        // Appelle une méthode
        final PlayerHand hand = packet.hand();
        // Appelle une méthode
        final ItemStack itemStack = player.getItemInHand(hand);
        //itemStack.onLeftClick(player, hand);
        // Appelle une méthode
        PlayerHandAnimationEvent handAnimationEvent = new PlayerHandAnimationEvent(player, hand);
        // Début d'une méthode/d'un bloc
        EventDispatcher.callCancellable(handAnimationEvent, () -> {
            // Embranchement multiple (switch/case)
            switch (hand) {
                // Embranchement multiple (switch/case)
                case MAIN -> player.swingMainHand(true);
                // Embranchement multiple (switch/case)
                case OFF -> player.swingOffHand(true);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
