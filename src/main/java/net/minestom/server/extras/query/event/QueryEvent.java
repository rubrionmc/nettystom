// Déclaration du paquet de ce fichier
package net.minestom.server.extras.query.event;

// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;

// Import d'une classe nécessaire
import java.net.SocketAddress;
// Import d'une classe nécessaire
import java.util.Objects;

/**
 * An event called when a query is received and ready to be responded to.
 *
 * @param <T> the type of the response
 */
// Déclaration de type (classe/interface/enum/record)
public abstract class QueryEvent<T> implements CancellableEvent {
    // Instruction de code
    private final SocketAddress sender;
    // Instruction de code
    private final int sessionID;

    // Instruction de code
    private T response;
    // Instruction de code
    private boolean cancelled;

    /**
     * Creates a new query event.
     *
     * @param sender    the sender
     * @param sessionID the session ID of the query sender
     * @param response  the initial response
     */
    // Début d'une méthode/d'un bloc
    public QueryEvent(SocketAddress sender, int sessionID, T response) {
        // Accès à l'objet courant/parent
        this.sender = sender;
        // Accès à l'objet courant/parent
        this.sessionID = sessionID;
        // Accès à l'objet courant/parent
        this.response = response;
        // Accès à l'objet courant/parent
        this.cancelled = false;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the query response that will be sent back to the sender.
     * This can be mutated.
     *
     * @return the response
     */
    // Début d'une méthode/d'un bloc
    public T getQueryResponse() {
        // Renvoie une valeur à l'appelant
        return this.response;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the query response that will be sent back to the sender.
     *
     * @param response the response
     */
    // Début d'une méthode/d'un bloc
    public void setQueryResponse(T response) {
        // Accès à l'objet courant/parent
        this.response = Objects.requireNonNull(response, "response");
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the socket address of the initiator of the query.
     *
     * @return the initiator
     */
    // Début d'une méthode/d'un bloc
    public SocketAddress getSender() {
        // Renvoie une valeur à l'appelant
        return this.sender;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the Session ID of the initiator of the query.
     *
     * @return the session ID
     */
    // Début d'une méthode/d'un bloc
    public int getSessionID() {
        // Renvoie une valeur à l'appelant
        return this.sessionID;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isCancelled() {
        // Renvoie une valeur à l'appelant
        return this.cancelled;
    // Fin d'un bloc/d'une expression
    }

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
