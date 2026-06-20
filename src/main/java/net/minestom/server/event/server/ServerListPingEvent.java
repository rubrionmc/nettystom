// Package declaration for this file
package net.minestom.server.event.server;

// Import of a required class
import net.minestom.server.event.trait.AsyncEvent;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.network.player.PlayerConnection;
// Import of a required class
import net.minestom.server.ping.ServerListPingType;
// Import of a required class
import net.minestom.server.ping.Status;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Objects;

/**
 * Called when a {@link PlayerConnection} sends a status packet,
 * usually to display information on the server list.
 */
// Type declaration (class/interface/enum/record)
public class ServerListPingEvent implements CancellableEvent, AsyncEvent {
    // Code statement
    private final PlayerConnection connection;
    // Code statement
    private final ServerListPingType type;

    // Code statement
    private boolean cancelled;
    // Code statement
    private Status status;

    /**
     * Creates a new server list ping event with no player connection.
     *
     * @param type the ping type to respond with
     */
    // Start of a method/block
    public ServerListPingEvent(ServerListPingType type) {
        // Calls a method
        this(null, type);
    // End of a block/expression
    }

    /**
     * Creates a new server list ping event.
     *
     * @param connection the player connection, if the ping type is modern
     * @param type       the ping type to respond with
     */
    // Start of a method/block
    public ServerListPingEvent(@Nullable PlayerConnection connection, ServerListPingType type) {
        // Access to the current/parent object
        this.status = Status.builder().build();
        // Access to the current/parent object
        this.connection = connection;
        // Access to the current/parent object
        this.type = type;
    // End of a block/expression
    }

    /**
     * Gets the response data that is sent to the client.
     * This is mutable and can be modified to change what is returned.
     *
     * @return the response data being returned
     */
    // Start of a method/block
    public Status getStatus() {
        // Returns a value to the caller
        return status;
    // End of a block/expression
    }

    /**
     * Sets the response data, overwriting the exiting data.
     *
     * @param status the new data
     */
    // Start of a method/block
    public void setStatus(Status status) {
        // Access to the current/parent object
        this.status = Objects.requireNonNull(status);
    // End of a block/expression
    }

    /**
     * PlayerConnection of received packet. Note that the player has not joined the server
     * at this time. This will <b>only</b> be non-null for modern server list pings.
     *
     * @return the playerConnection.
     */
    // Start of a method/block
    public @Nullable PlayerConnection getConnection() {
        // Returns a value to the caller
        return connection;
    // End of a block/expression
    }

    /**
     * Gets the ping type that the client is pinging with.
     *
     * @return the ping type
     */
    // Start of a method/block
    public ServerListPingType getPingType() {
        // Returns a value to the caller
        return type;
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

    /**
     * Cancelling this event will cause the server to appear offline in the vanilla server list.
     * Note that this will have no effect if the ping version is {@link ServerListPingType#OPEN_TO_LAN}.
     *
     * @param cancel true if the event should be cancelled, false otherwise
     */
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
