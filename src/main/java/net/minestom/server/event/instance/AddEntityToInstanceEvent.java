// Déclaration du paquet de ce fichier
package net.minestom.server.event.instance;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.InstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;

/**
 * Called by an Instance when an entity is added to it.
 * Can be used attach data.
 */
// Déclaration de type (classe/interface/enum/record)
public class AddEntityToInstanceEvent implements InstanceEvent, EntityEvent, CancellableEvent {

    // Instruction de code
    private final Instance instance;
    // Instruction de code
    private final Entity entity;

    // Instruction de code
    private boolean cancelled;

    // Début d'une méthode/d'un bloc
    public AddEntityToInstanceEvent(Instance instance, Entity entity) {
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
     * Entity being added.
     *
     * @return the entity being added
     */
    // Début d'une méthode/d'un bloc
    public Entity getEntity() {
        // Renvoie une valeur à l'appelant
        return entity;
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
