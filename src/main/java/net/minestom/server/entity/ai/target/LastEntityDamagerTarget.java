// Déclaration du paquet de ce fichier
package net.minestom.server.entity.ai.target;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityCreature;
// Import d'une classe nécessaire
import net.minestom.server.entity.ai.TargetSelector;
// Import d'une classe nécessaire
import net.minestom.server.entity.damage.Damage;
// Import d'une classe nécessaire
import net.minestom.server.entity.damage.EntityDamage;

/**
 * Targets the last damager of this entity.
 */
// Déclaration de type (classe/interface/enum/record)
public class LastEntityDamagerTarget extends TargetSelector {

    // Instruction de code
    private final float range;

    // Début d'une méthode/d'un bloc
    public LastEntityDamagerTarget(EntityCreature entityCreature, float range) {
        // Accès à l'objet courant/parent
        super(entityCreature);
        // Accès à l'objet courant/parent
        this.range = range;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Entity findTarget() {
        // Appelle une méthode
        final Damage damage = entityCreature.getLastDamageSource();
        // Embranchement : vérifie une condition
        if (!(damage instanceof EntityDamage entityDamage)) {
            // No damager recorded, return null
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final Entity entity = entityDamage.getSource();
        // Embranchement : vérifie une condition
        if (entity.isRemoved()) {
            // Entity not valid
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
        // Check range
        // Renvoie une valeur à l'appelant
        return entityCreature.getDistanceSquared(entity) < range * range ? entity : null;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
