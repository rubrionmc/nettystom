// Déclaration du paquet de ce fichier
package net.minestom.server.extras.query.event;

// Import d'une classe nécessaire
import net.minestom.server.extras.query.response.BasicQueryResponse;

// Import d'une classe nécessaire
import java.net.SocketAddress;

/**
 * An event called when a basic query is received and ready to be responded to.
 */
// Déclaration de type (classe/interface/enum/record)
public class BasicQueryEvent extends QueryEvent<BasicQueryResponse> {

    /**
     * Creates a new basic query event.
     *
     * @param sessionID the session ID
     * @param sender the sender
     */
    // Début d'une méthode/d'un bloc
    public BasicQueryEvent(SocketAddress sender, int sessionID) {
        // Accès à l'objet courant/parent
        super(sender, sessionID, new BasicQueryResponse());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
