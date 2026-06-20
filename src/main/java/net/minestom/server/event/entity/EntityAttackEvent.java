// Déclaration du paquet de ce fichier
package net.minestom.server.event.entity;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityInstanceEvent;

/**
 * Called when a player does a left click on an entity or with
 * {@link net.minestom.server.entity.EntityCreature#attack(Entity)}.
 */
// Déclaration de type (classe/interface/enum/record)
public class EntityAttackEvent implements EntityInstanceEvent {

    // Instruction de code
    private final Entity entity;
    // Instruction de code
    private final Entity target;

    // Début d'une méthode/d'un bloc
    public EntityAttackEvent(Entity source, Entity target) {
        // Accès à l'objet courant/parent
        this.entity = source;
        // Accès à l'objet courant/parent
        this.target = target;
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

    /**
     * @return the target of the attack
     */
    // Début d'une méthode/d'un bloc
    public Entity getTarget() {
        // Renvoie une valeur à l'appelant
        return target;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
