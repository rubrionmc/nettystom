// Déclaration du paquet de ce fichier
package net.minestom.server.event.server;

// Import d'une classe nécessaire
import net.minestom.server.event.trait.AsyncEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.TimeUnit;

// Import d'une classe nécessaire
import java.time.Duration;


/**
 * Called when a {@link PlayerConnection} sends a ping packet,
 * usually after the status packet. Only used in versions since the netty rewrite; 1.7+
 *
 * @see ServerListPingEvent
 */
// Déclaration de type (classe/interface/enum/record)
public class ClientPingServerEvent implements CancellableEvent, AsyncEvent {
    // Appelle une méthode
    private static final Duration DEFAULT_DELAY = Duration.of(0, TimeUnit.MILLISECOND);

    // Instruction de code
    private final PlayerConnection connection;
    // Instruction de code
    private long payload;

    // Affecte une valeur
    private boolean cancelled = false;
    // Instruction de code
    private Duration delay;

    /**
     * Creates a new client ping server event with 0 delay
     *
     * @param connection the player connection
     * @param payload    the payload the client sent
     */
    // Début d'une méthode/d'un bloc
    public ClientPingServerEvent(PlayerConnection connection, long payload) {
        // Accès à l'objet courant/parent
        this.connection = connection;
        // Accès à l'objet courant/parent
        this.payload = payload;
        // Accès à l'objet courant/parent
        this.delay = DEFAULT_DELAY;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new client ping server event with 0 delay
     *
     * @param connection the player connection
     * @param payload    the payload the client sent
     */
    // Début d'une méthode/d'un bloc
    public ClientPingServerEvent(PlayerConnection connection, long payload, Duration delay) {
        // Accès à l'objet courant/parent
        this.connection = connection;
        // Accès à l'objet courant/parent
        this.payload = payload;
        // Accès à l'objet courant/parent
        this.delay = delay;
    // Fin d'un bloc/d'une expression
    }

    /**
     * PlayerConnection of received packet. Note that the player has not joined the server
     * at this time.
     *
     * @return the connection.
     */
    // Début d'une méthode/d'un bloc
    public PlayerConnection getConnection() {
        // Renvoie une valeur à l'appelant
        return connection;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Payload of received packet. May be any number; vanilla uses a system dependant time value.
     *
     * @return the payload
     */
    // Début d'une méthode/d'un bloc
    public long getPayload() {
        // Renvoie une valeur à l'appelant
        return payload;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the payload to respond with.
     * <p>
     * Note: This should be the same as the client sent, however vanilla 1.17 seems to be OK with a different payload.
     *
     * @param payload the payload
     */
    // Début d'une méthode/d'un bloc
    public void setPayload(long payload) {
        // Accès à l'objet courant/parent
        this.payload = payload;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the delay until minestom will send the ping response packet.
     *
     * @return the delay
     */
    // Début d'une méthode/d'un bloc
    public Duration getDelay() {
        // Renvoie une valeur à l'appelant
        return delay;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds to the delay until minestom will send the ping response packet.
     *
     * @param delay the delay
     */
    // Début d'une méthode/d'un bloc
    public void addDelay(Duration delay) {
        // Accès à l'objet courant/parent
        this.delay = this.delay.plus(delay);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the delay until minestom will send the ping response packet.
     *
     * @param delay the delay
     */
    // Début d'une méthode/d'un bloc
    public void setDelay(Duration delay) {
        // Accès à l'objet courant/parent
        this.delay = delay;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Clears the delay until minestom will send the ping response packet.
     */
    // Début d'une méthode/d'un bloc
    public void noDelay() {
        // Accès à l'objet courant/parent
        this.delay = DEFAULT_DELAY;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isCancelled() {
        // Renvoie une valeur à l'appelant
        return cancelled;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Cancelling this event will cause the server to appear offline in the vanilla server list.
     *
     * @param cancel true if the event should be cancelled, false otherwise
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setCancelled(boolean cancel) {
        // Accès à l'objet courant/parent
        this.cancelled = cancel;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
