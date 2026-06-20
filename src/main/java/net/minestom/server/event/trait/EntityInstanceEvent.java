// Déclaration du paquet de ce fichier
package net.minestom.server.event.trait;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;

/**
 * Represents an {@link EntityEvent} which happen in {@link Entity#getInstance()}.
 * Useful if you need to listen to entity events happening in its instance.
 * <p>
 * Be aware that the entity's instance must be non-null.
 */
// Déclaration de type (classe/interface/enum/record)
public interface EntityInstanceEvent extends EntityEvent, InstanceEvent {
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Instance getInstance() {
        // Appelle une méthode
        final Instance instance = getEntity().getInstance();
        // Instruction de code
        assert instance != null : "EntityInstanceEvent is only supported on events where the entity's instance is non-null!";
        // Renvoie une valeur à l'appelant
        return instance;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
