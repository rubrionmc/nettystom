// Déclaration du paquet de ce fichier
package net.minestom.server.entity.damage;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;

/**
 * Represents damage inflicted by an {@link Entity}.
 */
// Déclaration de type (classe/interface/enum/record)
public class EntityDamage extends Damage {

    // Début d'une méthode/d'un bloc
    public EntityDamage(Entity source, float amount) {
        // Accès à l'objet courant/parent
        super(DamageType.MOB_ATTACK, source, source, null, amount);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the source of the damage.
     *
     * @return the source
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Entity getSource() {
        // Renvoie une valeur à l'appelant
        return super.getSource();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Entity getAttacker() {
        // Renvoie une valeur à l'appelant
        return getSource();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}