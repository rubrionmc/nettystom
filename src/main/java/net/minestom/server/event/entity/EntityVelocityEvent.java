// Déclaration du paquet de ce fichier
package net.minestom.server.event.entity;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityInstanceEvent;

/**
 * Called when a velocity is applied to an entity using {@link Entity#setVelocity(Vec)}.
 */
// Déclaration de type (classe/interface/enum/record)
public class EntityVelocityEvent implements EntityInstanceEvent, CancellableEvent {

    // Instruction de code
    private final Entity entity;
    // Instruction de code
    private Vec velocity;

    // Instruction de code
    private boolean cancelled;

    // Début d'une méthode/d'un bloc
    public EntityVelocityEvent(Entity entity, Vec velocity) {
        // Accès à l'objet courant/parent
        this.entity = entity;
        // Accès à l'objet courant/parent
        this.velocity = velocity;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the enity who will be affected by {@link #getVelocity()}.
     *
     * @return the entity
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Entity getEntity() {
        // Renvoie une valeur à l'appelant
        return entity;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the velocity which will be applied.
     *
     * @return the velocity
     */
    // Début d'une méthode/d'un bloc
    public Vec getVelocity() {
        // Renvoie une valeur à l'appelant
        return velocity;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the applied velocity.
     *
     * @param velocity the new velocity
     */
    // Début d'une méthode/d'un bloc
    public void setVelocity(Vec velocity) {
        // Accès à l'objet courant/parent
        this.velocity = velocity;
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
