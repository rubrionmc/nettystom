// Déclaration du paquet de ce fichier
package net.minestom.server.event.server;

// Import d'une classe nécessaire
import net.minestom.server.event.trait.AsyncEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;
// Import d'une classe nécessaire
import net.minestom.server.ping.ServerListPingType;
// Import d'une classe nécessaire
import net.minestom.server.ping.Status;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Objects;

/**
 * Called when a {@link PlayerConnection} sends a status packet,
 * usually to display information on the server list.
 */
// Déclaration de type (classe/interface/enum/record)
public class ServerListPingEvent implements CancellableEvent, AsyncEvent {
    // Instruction de code
    private final PlayerConnection connection;
    // Instruction de code
    private final ServerListPingType type;

    // Instruction de code
    private boolean cancelled;
    // Instruction de code
    private Status status;

    /**
     * Creates a new server list ping event with no player connection.
     *
     * @param type the ping type to respond with
     */
    // Début d'une méthode/d'un bloc
    public ServerListPingEvent(ServerListPingType type) {
        // Appelle une méthode
        this(null, type);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new server list ping event.
     *
     * @param connection the player connection, if the ping type is modern
     * @param type       the ping type to respond with
     */
    // Début d'une méthode/d'un bloc
    public ServerListPingEvent(@Nullable PlayerConnection connection, ServerListPingType type) {
        // Accès à l'objet courant/parent
        this.status = Status.builder().build();
        // Accès à l'objet courant/parent
        this.connection = connection;
        // Accès à l'objet courant/parent
        this.type = type;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the response data that is sent to the client.
     * This is mutable and can be modified to change what is returned.
     *
     * @return the response data being returned
     */
    // Début d'une méthode/d'un bloc
    public Status getStatus() {
        // Renvoie une valeur à l'appelant
        return status;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the response data, overwriting the exiting data.
     *
     * @param status the new data
     */
    // Début d'une méthode/d'un bloc
    public void setStatus(Status status) {
        // Accès à l'objet courant/parent
        this.status = Objects.requireNonNull(status);
    // Fin d'un bloc/d'une expression
    }

    /**
     * PlayerConnection of received packet. Note that the player has not joined the server
     * at this time. This will <b>only</b> be non-null for modern server list pings.
     *
     * @return the playerConnection.
     */
    // Début d'une méthode/d'un bloc
    public @Nullable PlayerConnection getConnection() {
        // Renvoie une valeur à l'appelant
        return connection;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the ping type that the client is pinging with.
     *
     * @return the ping type
     */
    // Début d'une méthode/d'un bloc
    public ServerListPingType getPingType() {
        // Renvoie une valeur à l'appelant
        return type;
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
     * Note that this will have no effect if the ping version is {@link ServerListPingType#OPEN_TO_LAN}.
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
