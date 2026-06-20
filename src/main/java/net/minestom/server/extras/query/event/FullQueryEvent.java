// Déclaration du paquet de ce fichier
package net.minestom.server.extras.query.event;

// Import d'une classe nécessaire
import net.minestom.server.extras.query.response.FullQueryResponse;

// Import d'une classe nécessaire
import java.net.SocketAddress;

/**
 * An event called when a full query is received and ready to be responded to.
 */
// Déclaration de type (classe/interface/enum/record)
public class FullQueryEvent extends QueryEvent<FullQueryResponse> {

    /**
     * Creates a new full query event.
     *
     * @param sender the sender
     * @param sessionID the sessionID
     */
    // Début d'une méthode/d'un bloc
    public FullQueryEvent(SocketAddress sender, int sessionID) {
        // Accès à l'objet courant/parent
        super(sender, sessionID, new FullQueryResponse());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
