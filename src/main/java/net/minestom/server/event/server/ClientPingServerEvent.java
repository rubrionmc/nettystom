// Package declaration for this file
package net.minestom.server.event.server;

// Import of a required class
import net.minestom.server.event.trait.AsyncEvent;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.network.player.PlayerConnection;
// Import of a required class
import net.minestom.server.utils.time.TimeUnit;

// Import of a required class
import java.time.Duration;


/**
 * Called when a {@link PlayerConnection} sends a ping packet,
 * usually after the status packet. Only used in versions since the netty rewrite; 1.7+
 *
 * @see ServerListPingEvent
 */
// Type declaration (class/interface/enum/record)
public class ClientPingServerEvent implements CancellableEvent, AsyncEvent {
    // Calls a method
    private static final Duration DEFAULT_DELAY = Duration.of(0, TimeUnit.MILLISECOND);

    // Code statement
    private final PlayerConnection connection;
    // Code statement
    private long payload;

    // Assigns a value
    private boolean cancelled = false;
    // Code statement
    private Duration delay;

    /**
     * Creates a new client ping server event with 0 delay
     *
     * @param connection the player connection
     * @param payload    the payload the client sent
     */
    // Start of a method/block
    public ClientPingServerEvent(PlayerConnection connection, long payload) {
        // Access to the current/parent object
        this.connection = connection;
        // Access to the current/parent object
        this.payload = payload;
        // Access to the current/parent object
        this.delay = DEFAULT_DELAY;
    // End of a block/expression
    }

    /**
     * Creates a new client ping server event with 0 delay
     *
     * @param connection the player connection
     * @param payload    the payload the client sent
     */
    // Start of a method/block
    public ClientPingServerEvent(PlayerConnection connection, long payload, Duration delay) {
        // Access to the current/parent object
        this.connection = connection;
        // Access to the current/parent object
        this.payload = payload;
        // Access to the current/parent object
        this.delay = delay;
    // End of a block/expression
    }

    /**
     * PlayerConnection of received packet. Note that the player has not joined the server
     * at this time.
     *
     * @return the connection.
     */
    // Start of a method/block
    public PlayerConnection getConnection() {
        // Returns a value to the caller
        return connection;
    // End of a block/expression
    }

    /**
     * Payload of received packet. May be any number; vanilla uses a system dependant time value.
     *
     * @return the payload
     */
    // Start of a method/block
    public long getPayload() {
        // Returns a value to the caller
        return payload;
    // End of a block/expression
    }

    /**
     * Sets the payload to respond with.
     * <p>
     * Note: This should be the same as the client sent, however vanilla 1.17 seems to be OK with a different payload.
     *
     * @param payload the payload
     */
    // Start of a method/block
    public void setPayload(long payload) {
        // Access to the current/parent object
        this.payload = payload;
    // End of a block/expression
    }

    /**
     * Gets the delay until minestom will send the ping response packet.
     *
     * @return the delay
     */
    // Start of a method/block
    public Duration getDelay() {
        // Returns a value to the caller
        return delay;
    // End of a block/expression
    }

    /**
     * Adds to the delay until minestom will send the ping response packet.
     *
     * @param delay the delay
     */
    // Start of a method/block
    public void addDelay(Duration delay) {
        // Access to the current/parent object
        this.delay = this.delay.plus(delay);
    // End of a block/expression
    }

    /**
     * Sets the delay until minestom will send the ping response packet.
     *
     * @param delay the delay
     */
    // Start of a method/block
    public void setDelay(Duration delay) {
        // Access to the current/parent object
        this.delay = delay;
    // End of a block/expression
    }

    /**
     * Clears the delay until minestom will send the ping response packet.
     */
    // Start of a method/block
    public void noDelay() {
        // Access to the current/parent object
        this.delay = DEFAULT_DELAY;
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
