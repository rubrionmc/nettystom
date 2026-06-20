// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerSpectateEntityEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerTeleportToEntityEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientSpectateEntityPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientTeleportToEntityPacket;

// Import d'une classe nécessaire
import java.util.UUID;

// Déclaration de type (classe/interface/enum/record)
public class PlayerSpectatorListener {

    // Début d'une méthode/d'un bloc
    public static void listener(ClientSpectateEntityPacket packet, Player player) {
        // Ignore if the player is not in spectator mode
        // Embranchement : vérifie une condition
        if (player.getGameMode() != GameMode.SPECTATOR) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final int targetId = packet.targetId();
        // Appelle une méthode
        final Entity target = player.getInstance().getEntityById(targetId);

        // Check if the target is valid, and the use is allowed
        // Embranchement : vérifie une condition
        if (target == null || target == player || UseEntityListener.invalidUse(player, target)) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        EventDispatcher.call(new PlayerSpectateEntityEvent(player, target));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void listener(ClientTeleportToEntityPacket packet, Player player) {
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
        final Instance playerInstance = player.getInstance();
        // Appelle une méthode
        Entity target = playerInstance.getEntityByUuid(targetUuid);

        // If the target is not found, try to find it in other instances
        // Embranchement : vérifie une condition
        if (target == null) {
            // Boucle : répète un bloc
            for (Instance instance : MinecraftServer.getInstanceManager().getInstances()) {
                // Embranchement : vérifie une condition
                if (instance == playerInstance) continue;
                // Appelle une méthode
                target = instance.getEntityByUuid(targetUuid);
                // Embranchement : vérifie une condition
                if (target != null) break;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Check if the target is valid
        // Embranchement : vérifie une condition
        if (target == null || target == player) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        EventDispatcher.call(new PlayerTeleportToEntityEvent(player, target));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
