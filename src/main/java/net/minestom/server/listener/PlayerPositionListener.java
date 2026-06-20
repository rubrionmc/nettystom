// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.RelativeFlags;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerMoveEvent;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.network.packet.client.play.*;
// Import of a required class
import net.minestom.server.network.packet.server.play.PlayerPositionAndLookPacket;
// Import of a required class
import net.minestom.server.utils.chunk.ChunkUtils;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Type declaration (class/interface/enum/record)
public class PlayerPositionListener {
    // Assigns a value
    private static final double MAX_COORDINATE = 30_000_000;
    // Calls a method
    private static final Component KICK_MESSAGE = Component.text("You moved too far away!");

    // Start of a method/block
    public static void playerPacketListener(ClientPlayerPositionStatusPacket packet, Player player) {
        // TODO: Should we expose horizontal collision here and the methods below?
        // Calls a method
        player.refreshOnGround(packet.onGround());
    // End of a block/expression
    }

    /**
     * Exists to update yaw/pitch from UseItemListener
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static void playerRotation(Player player, float yaw, float pitch) {
        // Calls a method
        processMovement(player, player.getPosition().withView(yaw, pitch), player.isOnGround());
    // End of a block/expression
    }

    // Start of a method/block
    public static void playerLookListener(ClientPlayerRotationPacket packet, Player player) {
        // Calls a method
        processMovement(player, player.getPosition().withView(packet.yaw(), packet.pitch()), packet.onGround());
    // End of a block/expression
    }

    // Start of a method/block
    public static void playerPositionListener(ClientPlayerPositionPacket packet, Player player) {
        // Calls a method
        processMovement(player, player.getPosition().withCoord(packet.position()), packet.onGround());
    // End of a block/expression
    }

    // Start of a method/block
    public static void playerPositionAndLookListener(ClientPlayerPositionAndRotationPacket packet, Player player) {
        // Calls a method
        processMovement(player, packet.position(), packet.onGround());
    // End of a block/expression
    }

    // Start of a method/block
    public static void teleportConfirmListener(ClientTeleportConfirmPacket packet, Player player) {
        // Calls a method
        player.refreshReceivedTeleportId(packet.teleportId());
    // End of a block/expression
    }

    // Start of a method/block
    private static void processMovement(Player player, Pos packetPosition, boolean onGround) {
        // Prevent the player from moving too far
        // Doubles close to max size can cause overflow, or simply have precision issues
        // Branch: checks a condition
        if (Math.abs(packetPosition.x()) > MAX_COORDINATE ||
                // Code statement
                Math.abs(packetPosition.y()) > MAX_COORDINATE ||
                // Start of a method/block
                Math.abs(packetPosition.z()) > MAX_COORDINATE) {
            // Calls a method
            player.kick(KICK_MESSAGE);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        final var currentPosition = player.getPosition();
        // Branch: checks a condition
        if (currentPosition.equals(packetPosition)) {
            // For some reason, the position is the same
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Calls a method
        final Instance instance = player.getInstance();
        // Prevent moving before the player spawned, probably a modified client (or high latency?)
        // Branch: checks a condition
        if (instance == null) {
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Prevent the player from moving during a teleport
        // Branch: checks a condition
        if (player.getLastSentTeleportId() != player.getLastReceivedTeleportId()) {
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Try to move in an unloaded chunk, prevent it
        // Branch: checks a condition
        if (!currentPosition.sameChunk(packetPosition) && !ChunkUtils.isLoaded(instance, packetPosition)) {
            // Calls a method
            player.teleport(currentPosition);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        PlayerMoveEvent playerMoveEvent = new PlayerMoveEvent(player, packetPosition, onGround);
        // Calls a method
        EventDispatcher.call(playerMoveEvent);
        // Branch: checks a condition
        if (!currentPosition.equals(player.getPosition())) {
            // Player has been teleported in the event
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Branch: checks a condition
        if (playerMoveEvent.isCancelled()) {
            // Teleport to previous position & cancel any velocity
            // Code statement
            player.sendPacket(new PlayerPositionAndLookPacket(player.getNextTeleportId(), currentPosition,
                    // Calls a method
                    Vec.ZERO, currentPosition.yaw(), currentPosition.pitch(), (byte) RelativeFlags.NONE));
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Calls a method
        final Pos eventPosition = playerMoveEvent.getNewPosition();
        // Branch: checks a condition
        if (packetPosition.equals(eventPosition)) {
            // Event didn't change the position
            // Calls a method
            player.refreshPosition(eventPosition);
            // Calls a method
            player.refreshOnGround(onGround);
        // Alternative branch of the condition
        } else {
            // Position modified by the event
            // Branch: checks a condition
            if (packetPosition.samePoint(eventPosition)) {
                // Calls a method
                player.refreshPosition(eventPosition, true);
                // Calls a method
                player.refreshOnGround(onGround);
                // Calls a method
                player.setView(eventPosition.yaw(), eventPosition.pitch());
            // Alternative branch of the condition
            } else {
                // Calls a method
                player.teleport(eventPosition);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
