// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerEvent;

// Import of a required class
import java.util.Objects;

/**
 * Called when a {@link Player} is about to be redirected to another server.
 * <br>
 * It can be canceled to prevent the transfer from occurring.
 */
// Type declaration (class/interface/enum/record)
public class OutgoingTransferEvent implements PlayerEvent, CancellableEvent {
    // Code statement
    private final Player player;
    // Code statement
    private String host;
    // Code statement
    private int port;
    // Code statement
    private boolean cancelled;

    // Start of a method/block
    public OutgoingTransferEvent(Player player, String host, int port) {
        // Access to the current/parent object
        this.player = Objects.requireNonNull(player);
        // Access to the current/parent object
        this.host = Objects.requireNonNull(host);
        // Access to the current/parent object
        this.port = port;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isCancelled() {
        // Returns a value to the caller
        return this.cancelled;
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

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Player getPlayer() {
        // Returns a value to the caller
        return this.player;
    // End of a block/expression
    }

    /**
     * Returns the address of the target server that the player will be transferred to.
     *
     * @return the target host, usually an IP or domain name
     */
    // Start of a method/block
    public String getHost() {
        // Returns a value to the caller
        return this.host;
    // End of a block/expression
    }

    /**
     * Returns the port of the target server that the player will be transferred to.
     *
     * @return the target port
     */
    // Start of a method/block
    public int getPort() {
        // Returns a value to the caller
        return this.port;
    // End of a block/expression
    }

    /**
     * Changes the address of the target server that the player will be transferred to.
     *
     * @param host the address of the target server, usually an IP or domain name
     */
    // Start of a method/block
    public void setHost(String host) {
        // Access to the current/parent object
        this.host = Objects.requireNonNull(host);
    // End of a block/expression
    }

    /**
     * Changes the port of the target server that the player will be transferred to.
     *
     * @param port the target port
     */
    // Start of a method/block
    public void setPort(int port) {
        // Access to the current/parent object
        this.port = port;
    // End of a block/expression
    }
// End of a block/expression
}
