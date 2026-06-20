// Déclaration du paquet de ce fichier
package net.minestom.server.event.instance;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;

/**
 * Called by an Instance when an entity is removed from it.
 */
// Déclaration de type (classe/interface/enum/record)
public class RemoveEntityFromInstanceEvent implements EntityInstanceEvent {
    // Instruction de code
    private final Instance instance;
    // Instruction de code
    private final Entity entity;

    // Début d'une méthode/d'un bloc
    public RemoveEntityFromInstanceEvent(Instance instance, Entity entity) {
        // Accès à l'objet courant/parent
        this.instance = instance;
        // Accès à l'objet courant/parent
        this.entity = entity;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Instance getInstance() {
        // Renvoie une valeur à l'appelant
        return instance;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entity being removed.
     *
     * @return entity being removed
     */
    // Début d'une méthode/d'un bloc
    public Entity getEntity() {
        // Renvoie une valeur à l'appelant
        return entity;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
