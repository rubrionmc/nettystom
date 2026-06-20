// Déclaration du paquet de ce fichier
package net.minestom.server.event.entity;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityInstanceEvent;

/**
 * Called when an entity ticks itself.
 * Same event instance used for all tick events for the same entity.
 */
// Déclaration de type (classe/interface/enum/record)
public class EntityTickEvent implements EntityInstanceEvent {

    // Instruction de code
    private final Entity entity;

    // Début d'une méthode/d'un bloc
    public EntityTickEvent(Entity entity) {
        // Accès à l'objet courant/parent
        this.entity = entity;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Entity getEntity() {
        // Renvoie une valeur à l'appelant
        return entity;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
