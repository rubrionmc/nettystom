// Déclaration du paquet de ce fichier
package net.minestom.server.event.trait;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.event.Event;

/**
 * Represents any event called on an {@link Entity}.
 */
// Déclaration de type (classe/interface/enum/record)
public interface EntityEvent extends Event {

    /**
     * Gets the entity of this event.
     *
     * @return the entity
     */
    // Appelle une méthode
    Entity getEntity();
// Fin d'un bloc/d'une expression
}
