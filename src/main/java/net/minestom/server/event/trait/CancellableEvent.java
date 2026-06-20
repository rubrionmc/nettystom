// Déclaration du paquet de ce fichier
package net.minestom.server.event.trait;

// Import d'une classe nécessaire
import net.minestom.server.event.Event;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;

/**
 * Represents an {@link Event} which can be cancelled.
 * Called using {@link EventDispatcher#callCancellable(CancellableEvent, Runnable)}.
 */
// Déclaration de type (classe/interface/enum/record)
public interface CancellableEvent extends Event {

    /**
     * Gets if the {@link Event} should be cancelled or not.
     *
     * @return true if the event should be cancelled
     */
    // Appelle une méthode
    boolean isCancelled();

    /**
     * Marks the {@link Event} as cancelled or not.
     *
     * @param cancel true if the event should be cancelled, false otherwise
     */
    // Appelle une méthode
    void setCancelled(boolean cancel);
// Fin d'un bloc/d'une expression
}
