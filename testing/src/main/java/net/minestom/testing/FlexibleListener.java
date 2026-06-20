// Déclaration du paquet de ce fichier
package net.minestom.testing;

// Import d'une classe nécessaire
import net.minestom.server.event.Event;

// Import d'une classe nécessaire
import java.util.function.Consumer;

// Déclaration de type (classe/interface/enum/record)
public interface FlexibleListener<E extends Event> {
    /**
     * Updates the handler. Fails if the previous followup has not been called.
     */
    // Appelle une méthode
    void followup(Consumer<E> handler);

    // Début d'une méthode/d'un bloc
    default void followup() {
        // Début d'une méthode/d'un bloc
        followup(event -> {
            // Empty
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    /**
     * Fails if an event is received. Valid until the next followup call.
     */
    // Appelle une méthode
    void failFollowup();
// Fin d'un bloc/d'une expression
}
