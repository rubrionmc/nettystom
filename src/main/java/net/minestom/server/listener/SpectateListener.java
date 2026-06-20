// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerSpectateEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientSpectatePacket;

// Import d'une classe nécessaire
import java.util.UUID;

// Déclaration de type (classe/interface/enum/record)
public class SpectateListener {

    // Début d'une méthode/d'un bloc
    public static void listener(ClientSpectatePacket packet, Player player) {
        // Ignore if the player is not in spectator mode
        // Embranchement : vérifie une condition
        if (player.getGameMode() != GameMode.SPECTATOR) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final UUID targetUuid = packet.target();
        // Appelle une méthode
        final Entity target = player.getInstance().getEntityByUuid(targetUuid);

        // Check if the target is valid
        // Embranchement : vérifie une condition
        if (target == null || target == player) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Ignore if they're not attached to any instances
        // Appelle une méthode
        Instance targetInstance = target.getInstance();
        // Appelle une méthode
        Instance playerInstance = player.getInstance();
        // Embranchement : vérifie une condition
        if (targetInstance == null || playerInstance == null) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Ignore if they're not in the same instance. Vanilla actually allows for
        // cross-instance spectating, but it's not really a good idea for Minestom.
        // Embranchement : vérifie une condition
        if (targetInstance.getUuid() != playerInstance.getUuid()) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Despite the name of this packet being spectate, it is sent when the player
        // uses their hotbar to switch between entities, which actually performs a teleport
        // instead of a spectate.
        // Appelle une méthode
        EventDispatcher.call(new PlayerSpectateEvent(player, target));
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
