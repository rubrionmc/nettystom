// Déclaration du paquet de ce fichier
package net.minestom.server.entity.damage;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * Represents damage inflicted by an entity, via a projectile.
 */
// Déclaration de type (classe/interface/enum/record)
public class EntityProjectileDamage extends Damage {

    // Début d'une méthode/d'un bloc
    public EntityProjectileDamage(@Nullable Entity shooter, Entity projectile, float amount) {
        // Accès à l'objet courant/parent
        super(DamageType.MOB_PROJECTILE, projectile, shooter, null, amount);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the projectile responsible for the damage.
     *
     * @return the projectile
     */
    // Début d'une méthode/d'un bloc
    public Entity getProjectile() {
        // Renvoie une valeur à l'appelant
        return getSource();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the shooter of the projectile.
     *
     * @return the shooter of the projectile, null if not any
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public Entity getShooter() {
        // Renvoie une valeur à l'appelant
        return getAttacker();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Entity getSource() {
        // Renvoie une valeur à l'appelant
        return super.getSource();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}