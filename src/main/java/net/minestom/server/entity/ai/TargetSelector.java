// Déclaration du paquet de ce fichier
package net.minestom.server.entity.ai;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityCreature;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * The target selector is called each time the entity receives an "attack" instruction
 * without having a target.
 */
// Déclaration de type (classe/interface/enum/record)
public abstract class TargetSelector {

    // Instruction de code
    protected final EntityCreature entityCreature;

    // Début d'une méthode/d'un bloc
    public TargetSelector(EntityCreature entityCreature) {
        // Accès à l'objet courant/parent
        this.entityCreature = entityCreature;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Finds the target.
     * <p>
     * Returning null means that this target selector didn't find any entity,
     * the next {@link TargetSelector} will be called until the end of the list or an entity is found.
     *
     * @return the target, null if not any
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Appelle une méthode
    public abstract Entity findTarget();

    /**
     * Gets the entity linked to this target selector.
     *
     * @return the entity
     */
    // Début d'une méthode/d'un bloc
    public EntityCreature getEntityCreature() {
        // Renvoie une valeur à l'appelant
        return entityCreature;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
