// Déclaration du paquet de ce fichier
package net.minestom.server.entity.ai.target;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityCreature;
// Import d'une classe nécessaire
import net.minestom.server.entity.LivingEntity;
// Import d'une classe nécessaire
import net.minestom.server.entity.ai.TargetSelector;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;

// Import d'une classe nécessaire
import java.util.Comparator;
// Import d'une classe nécessaire
import java.util.function.Predicate;

/**
 * Target the closest targetable entity (based on the target predicate)
 */
// Déclaration de type (classe/interface/enum/record)
public class ClosestEntityTarget extends TargetSelector {

    // Instruction de code
    private final double range;
    // Instruction de code
    private final Predicate<Entity> targetPredicate;

    /**
     * @param entityCreature the entity (self)
     * @param range          the maximum range the entity can target others within
     * @param entitiesTarget the entities to target by class
     * @deprecated Use {@link #ClosestEntityTarget(EntityCreature, double, Predicate)}
     */
    // Annotation pour l'élément suivant
    @SafeVarargs
    // Annotation pour l'élément suivant
    @Deprecated
    // Instruction de code
    public ClosestEntityTarget(EntityCreature entityCreature, float range,
                               // Début d'une méthode/d'un bloc
                               Class<? extends LivingEntity>... entitiesTarget) {
        // Début d'une méthode/d'un bloc
        this(entityCreature, range, ent -> {
            // Appelle une méthode
            Class<? extends Entity> clazz = ent.getClass();
            // Boucle : répète un bloc
            for (Class<? extends LivingEntity> targetClass : entitiesTarget) {
                // Embranchement : vérifie une condition
                if (targetClass.isAssignableFrom(clazz)) {
                    // Renvoie une valeur à l'appelant
                    return true;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    /**
     * @param entityCreature  the entity (self)
     * @param range           the maximum range the entity can target others within
     * @param targetPredicate the predicate used to check if the other entity can be targeted
     */
    // Instruction de code
    public ClosestEntityTarget(EntityCreature entityCreature, double range,
                               // Début d'une méthode/d'un bloc
                               Predicate<Entity> targetPredicate) {
        // Accès à l'objet courant/parent
        super(entityCreature);
        // Accès à l'objet courant/parent
        this.range = range;
        // Accès à l'objet courant/parent
        this.targetPredicate = targetPredicate;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Entity findTarget() {

        // Appelle une méthode
        Instance instance = entityCreature.getInstance();

        // Embranchement : vérifie une condition
        if (instance == null) {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return instance.getNearbyEntities(entityCreature.getPosition(), range).stream()
                // Don't target our self and make sure entity is valid
                // Instruction de code
                .filter(ent -> !entityCreature.equals(ent) && !ent.isRemoved())
                // Instruction de code
                .filter(targetPredicate)
                // Instruction de code
                .min(Comparator.comparingDouble(e -> e.getDistanceSquared(entityCreature)))
                // Appelle une méthode
                .orElse(null);

    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
