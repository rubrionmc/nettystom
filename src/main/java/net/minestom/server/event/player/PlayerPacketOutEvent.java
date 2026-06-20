// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerEvent;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

/**
 * Listen to outgoing packets asynchronously.
 * <p>
 * Currently, do not support viewable packets.
 */
// Annotation for the following element
@ApiStatus.Experimental
// Type declaration (class/interface/enum/record)
public class PlayerPacketOutEvent implements PlayerEvent, CancellableEvent {
    // Code statement
    private final Player player;
    // Code statement
    private final ServerPacket packet;
    // Code statement
    private boolean cancelled;

    // Start of a method/block
    public PlayerPacketOutEvent(Player player, ServerPacket packet) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.packet = packet;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Player getPlayer() {
        // Returns a value to the caller
        return player;
    // End of a block/expression
    }

    // Start of a method/block
    public ServerPacket getPacket() {
        // Returns a value to the caller
        return packet;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isCancelled() {
        // Returns a value to the caller
        return cancelled;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setCancelled(boolean cancel) {
        // Access to the current/parent object
        this.cancelled = cancel;
    // End of a block/expression
    }
// End of a block/expression
}
