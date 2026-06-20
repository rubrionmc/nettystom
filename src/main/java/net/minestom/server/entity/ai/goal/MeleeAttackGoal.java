// Déclaration du paquet de ce fichier
package net.minestom.server.entity.ai.goal;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityCreature;
// Import d'une classe nécessaire
import net.minestom.server.entity.ai.GoalSelector;
// Import d'une classe nécessaire
import net.minestom.server.entity.ai.TargetSelector;
// Import d'une classe nécessaire
import net.minestom.server.entity.pathfinding.Navigator;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.Cooldown;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.TimeUnit;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.time.temporal.TemporalUnit;

/**
 * Attacks the entity's target ({@link EntityCreature#getTarget()}) OR the closest entity
 * which can be targeted with the entity {@link TargetSelector}.
 */
// Déclaration de type (classe/interface/enum/record)
public class MeleeAttackGoal extends GoalSelector {

    // Appelle une méthode
    private final Cooldown cooldown = new Cooldown(Duration.of(5, TimeUnit.SERVER_TICK));

    // Instruction de code
    private long lastHit;
    // Instruction de code
    private final double range;
    // Instruction de code
    private final Duration delay;

    // Instruction de code
    private boolean stop;
    // Instruction de code
    private Entity cachedTarget;

    /**
     * @param entityCreature the entity to add the goal to
     * @param range          the allowed range the entity can attack others.
     * @param delay          the delay between each attacks
     * @param timeUnit       the unit of the delay
     */
    // Début d'une méthode/d'un bloc
    public MeleeAttackGoal(EntityCreature entityCreature, double range, int delay, TemporalUnit timeUnit) {
        // Appelle une méthode
        this(entityCreature, range, Duration.of(delay, timeUnit));
    // Fin d'un bloc/d'une expression
    }

    /**
     * @param entityCreature the entity to add the goal to
     * @param range          the allowed range the entity can attack others.
     * @param delay          the delay between each attacks
     */
    // Début d'une méthode/d'un bloc
    public MeleeAttackGoal(EntityCreature entityCreature, double range, Duration delay) {
        // Accès à l'objet courant/parent
        super(entityCreature);
        // Accès à l'objet courant/parent
        this.range = range;
        // Accès à l'objet courant/parent
        this.delay = delay;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Cooldown getCooldown() {
        // Renvoie une valeur à l'appelant
        return this.cooldown;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean shouldStart() {
        // Accès à l'objet courant/parent
        this.cachedTarget = findTarget();
        // Renvoie une valeur à l'appelant
        return this.cachedTarget != null;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void start() {
        // Appelle une méthode
        final Point targetPosition = this.cachedTarget.getPosition();
        // Appelle une méthode
        entityCreature.getNavigator().setPathTo(targetPosition);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void tick(long time) {
        // Instruction de code
        Entity target;
        // Embranchement : vérifie une condition
        if (this.cachedTarget != null) {
            // Affecte une valeur
            target = this.cachedTarget;
            // Accès à l'objet courant/parent
            this.cachedTarget = null;
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            target = findTarget();
        // Fin d'un bloc/d'une expression
        }

        // Accès à l'objet courant/parent
        this.stop = target == null;

        // Embranchement : vérifie une condition
        if (!stop) {

            // Attack the target entity
            // Embranchement : vérifie une condition
            if (entityCreature.getDistanceSquared(target) <= range * range) {
                // Appelle une méthode
                entityCreature.lookAt(target);
                // Embranchement : vérifie une condition
                if (!Cooldown.hasCooldown(time, lastHit, delay)) {
                    // Appelle une méthode
                    entityCreature.attack(target, true);
                    // Accès à l'objet courant/parent
                    this.lastHit = time;
                // Fin d'un bloc/d'une expression
                }
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }

            // Move toward the target entity
            // Appelle une méthode
            Navigator navigator = entityCreature.getNavigator();
            // Appelle une méthode
            final var pathPosition = navigator.getPathPosition();
            // Appelle une méthode
            final var targetPosition = target.getPosition();
            // Embranchement : vérifie une condition
            if (pathPosition == null || !pathPosition.samePoint(targetPosition)) {
                // Embranchement : vérifie une condition
                if (this.cooldown.isReady(time)) {
                    // Accès à l'objet courant/parent
                    this.cooldown.refreshLastUpdate(time);
                    // Appelle une méthode
                    navigator.setPathTo(targetPosition);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean shouldEnd() {
        // Renvoie une valeur à l'appelant
        return stop;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void end() {
        // Stop following the target
        // Appelle une méthode
        entityCreature.getNavigator().setPathTo(null);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
