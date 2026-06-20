// Déclaration du paquet de ce fichier
package net.minestom.server.event.trait;

// Import d'une classe nécessaire
import net.minestom.server.event.Event;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;

/**
 * Represents any event targeting an {@link Instance}.
 */
// Déclaration de type (classe/interface/enum/record)
public interface InstanceEvent extends Event {

    /**
     * Gets the instance.
     *
     * @return instance
     */
    // Appelle une méthode
    Instance getInstance();
// Fin d'un bloc/d'une expression
}
