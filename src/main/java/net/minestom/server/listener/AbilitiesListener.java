// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerStartFlyingEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerStopFlyingEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientPlayerAbilitiesPacket;

// Déclaration de type (classe/interface/enum/record)
public class AbilitiesListener {

    // Début d'une méthode/d'un bloc
    public static void listener(ClientPlayerAbilitiesPacket packet, Player player) {
        // Appelle une méthode
        final boolean canFly = player.isAllowFlying() || player.getGameMode() == GameMode.CREATIVE;

        // Embranchement : vérifie une condition
        if (canFly) {
            // Appelle une méthode
            final boolean isFlying = (packet.flags() & 0x2) > 0;

            // Appelle une méthode
            player.refreshFlying(isFlying);

            // Embranchement : vérifie une condition
            if (isFlying) {
                // Appelle une méthode
                PlayerStartFlyingEvent startFlyingEvent = new PlayerStartFlyingEvent(player);
                // Appelle une méthode
                EventDispatcher.call(startFlyingEvent);
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                PlayerStopFlyingEvent stopFlyingEvent = new PlayerStopFlyingEvent(player);
                // Appelle une méthode
                EventDispatcher.call(stopFlyingEvent);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
