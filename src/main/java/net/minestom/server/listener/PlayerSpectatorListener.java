// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerSpectateEntityEvent;
// Import of a required class
import net.minestom.server.event.player.PlayerTeleportToEntityEvent;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientSpectateEntityPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientTeleportToEntityPacket;

// Import of a required class
import java.util.UUID;

// Type declaration (class/interface/enum/record)
public class PlayerSpectatorListener {

    // Start of a method/block
    public static void listener(ClientSpectateEntityPacket packet, Player player) {
        // Ignore if the player is not in spectator mode
        // Branch: checks a condition
        if (player.getGameMode() != GameMode.SPECTATOR) {
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        final int targetId = packet.targetId();
        // Calls a method
        final Entity target = player.getInstance().getEntityById(targetId);

        // Check if the target is valid, and the use is allowed
        // Branch: checks a condition
        if (target == null || target == player || UseEntityListener.invalidUse(player, target)) {
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        EventDispatcher.call(new PlayerSpectateEntityEvent(player, target));
    // End of a block/expression
    }

    // Start of a method/block
    public static void listener(ClientTeleportToEntityPacket packet, Player player) {
        // Ignore if the player is not in spectator mode
        // Branch: checks a condition
        if (player.getGameMode() != GameMode.SPECTATOR) {
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        final UUID targetUuid = packet.target();
        // Calls a method
        final Instance playerInstance = player.getInstance();
        // Calls a method
        Entity target = playerInstance.getEntityByUuid(targetUuid);

        // If the target is not found, try to find it in other instances
        // Branch: checks a condition
        if (target == null) {
            // Loop: repeats a block
            for (Instance instance : MinecraftServer.getInstanceManager().getInstances()) {
                // Branch: checks a condition
                if (instance == playerInstance) continue;
                // Calls a method
                target = instance.getEntityByUuid(targetUuid);
                // Branch: checks a condition
                if (target != null) break;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Check if the target is valid
        // Branch: checks a condition
        if (target == null || target == player) {
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        EventDispatcher.call(new PlayerTeleportToEntityEvent(player, target));
    // End of a block/expression
    }
// End of a block/expression
}
