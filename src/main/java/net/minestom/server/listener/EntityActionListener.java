// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerLeaveBedEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerStartFlyingWithElytraEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerStartSprintingEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerStopSprintingEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientEntityActionPacket;

// Déclaration de type (classe/interface/enum/record)
public class EntityActionListener {

    // Début d'une méthode/d'un bloc
    public static void listener(ClientEntityActionPacket packet, Player player) {
        // Embranchement multiple (switch/case)
        switch (packet.action()) {
            // Embranchement multiple (switch/case)
            case START_SPRINTING -> EntityActionListener.setSprinting(player, true);
            // Embranchement multiple (switch/case)
            case STOP_SPRINTING -> EntityActionListener.setSprinting(player, false);
            // Embranchement multiple (switch/case)
            case START_FLYING_ELYTRA -> EntityActionListener.startFlyingElytra(player);
            // Embranchement multiple (switch/case)
            case LEAVE_BED -> EntityActionListener.onLeaveBed(player);

            // TODO do remaining actions
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void setSprinting(Player player, boolean sprinting) {
        // Appelle une méthode
        boolean oldState = player.isSprinting();

        // Appelle une méthode
        player.setSprinting(sprinting);

        // Embranchement : vérifie une condition
        if (oldState != sprinting) {
            // Embranchement : vérifie une condition
            if (sprinting) {
                // Appelle une méthode
                EventDispatcher.call(new PlayerStartSprintingEvent(player));
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                EventDispatcher.call(new PlayerStopSprintingEvent(player));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void startFlyingElytra(Player player) {
        // Appelle une méthode
        player.setFlyingWithElytra(true);
        // Appelle une méthode
        EventDispatcher.call(new PlayerStartFlyingWithElytraEvent(player));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void onLeaveBed(Player player) {
        // Appelle une méthode
        var event = new PlayerLeaveBedEvent(player);
        // Début d'une méthode/d'un bloc
        EventDispatcher.callCancellable(event, () -> {
            // Appelle une méthode
            player.getLivingEntityMeta().setBedInWhichSleepingPosition(null);
            // Appelle une méthode
            player.leaveBed();
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
