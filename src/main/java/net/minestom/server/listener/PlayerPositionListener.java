// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerMoveEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.*;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.PlayerPositionAndLookPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.chunk.ChunkUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Déclaration de type (classe/interface/enum/record)
public class PlayerPositionListener {
    // Affecte une valeur
    private static final double MAX_COORDINATE = 30_000_000;
    // Appelle une méthode
    private static final Component KICK_MESSAGE = Component.text("You moved too far away!");

    // Début d'une méthode/d'un bloc
    public static void playerPacketListener(ClientPlayerPositionStatusPacket packet, Player player) {
        // TODO: Should we expose horizontal collision here and the methods below?
        // Appelle une méthode
        player.refreshOnGround(packet.onGround());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Exists to update yaw/pitch from UseItemListener
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static void playerRotation(Player player, float yaw, float pitch) {
        // Appelle une méthode
        processMovement(player, player.getPosition().withView(yaw, pitch), player.isOnGround());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void playerLookListener(ClientPlayerRotationPacket packet, Player player) {
        // Appelle une méthode
        processMovement(player, player.getPosition().withView(packet.yaw(), packet.pitch()), packet.onGround());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void playerPositionListener(ClientPlayerPositionPacket packet, Player player) {
        // Appelle une méthode
        processMovement(player, player.getPosition().withCoord(packet.position()), packet.onGround());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void playerPositionAndLookListener(ClientPlayerPositionAndRotationPacket packet, Player player) {
        // Appelle une méthode
        processMovement(player, packet.position(), packet.onGround());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void teleportConfirmListener(ClientTeleportConfirmPacket packet, Player player) {
        // Appelle une méthode
        player.refreshReceivedTeleportId(packet.teleportId());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void processMovement(Player player, Pos packetPosition, boolean onGround) {
        // Prevent the player from moving too far
        // Doubles close to max size can cause overflow, or simply have precision issues
        // Embranchement : vérifie une condition
        if (Math.abs(packetPosition.x()) > MAX_COORDINATE ||
                // Instruction de code
                Math.abs(packetPosition.y()) > MAX_COORDINATE ||
                // Début d'une méthode/d'un bloc
                Math.abs(packetPosition.z()) > MAX_COORDINATE) {
            // Appelle une méthode
            player.kick(KICK_MESSAGE);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final var currentPosition = player.getPosition();
        // Embranchement : vérifie une condition
        if (currentPosition.equals(packetPosition)) {
            // For some reason, the position is the same
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final Instance instance = player.getInstance();
        // Prevent moving before the player spawned, probably a modified client (or high latency?)
        // Embranchement : vérifie une condition
        if (instance == null) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Prevent the player from moving during a teleport
        // Embranchement : vérifie une condition
        if (player.getLastSentTeleportId() != player.getLastReceivedTeleportId()) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Try to move in an unloaded chunk, prevent it
        // Embranchement : vérifie une condition
        if (!currentPosition.sameChunk(packetPosition) && !ChunkUtils.isLoaded(instance, packetPosition)) {
            // Appelle une méthode
            player.teleport(currentPosition);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        PlayerMoveEvent playerMoveEvent = new PlayerMoveEvent(player, packetPosition, onGround);
        // Appelle une méthode
        EventDispatcher.call(playerMoveEvent);
        // Embranchement : vérifie une condition
        if (!currentPosition.equals(player.getPosition())) {
            // Player has been teleported in the event
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (playerMoveEvent.isCancelled()) {
            // Teleport to previous position & cancel any velocity
            // Instruction de code
            player.sendPacket(new PlayerPositionAndLookPacket(player.getNextTeleportId(), currentPosition,
                    // Appelle une méthode
                    Vec.ZERO, currentPosition.yaw(), currentPosition.pitch(), (byte) 0x00));
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final Pos eventPosition = playerMoveEvent.getNewPosition();
        // Embranchement : vérifie une condition
        if (packetPosition.equals(eventPosition)) {
            // Event didn't change the position
            // Appelle une méthode
            player.refreshPosition(eventPosition);
            // Appelle une méthode
            player.refreshOnGround(onGround);
        // Branche alternative de la condition
        } else {
            // Position modified by the event
            // Embranchement : vérifie une condition
            if (packetPosition.samePoint(eventPosition)) {
                // Appelle une méthode
                player.refreshPosition(eventPosition, true);
                // Appelle une méthode
                player.refreshOnGround(onGround);
                // Appelle une méthode
                player.setView(eventPosition.yaw(), eventPosition.pitch());
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                player.teleport(eventPosition);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
