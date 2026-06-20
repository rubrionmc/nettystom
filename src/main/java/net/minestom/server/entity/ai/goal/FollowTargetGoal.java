// Déclaration du paquet de ce fichier
package net.minestom.server.entity.ai.goal;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityCreature;
// Import d'une classe nécessaire
import net.minestom.server.entity.ai.GoalSelector;
// Import d'une classe nécessaire
import net.minestom.server.entity.pathfinding.Navigator;

// Import d'une classe nécessaire
import java.time.Duration;

// Déclaration de type (classe/interface/enum/record)
public class FollowTargetGoal extends GoalSelector {
    // Instruction de code
    private final Duration pathDuration;
    // Affecte une valeur
    private long lastUpdateTime = 0;
    // Affecte une valeur
    private boolean forceEnd = false;
    // Instruction de code
    private Point lastTargetPos;

    // Instruction de code
    private Entity target;

    /**
     * Creates a follow target goal object.
     *
     * @param entityCreature the entity
     * @param pathDuration   the time between each path update (to check if the target moved)
     */
    // Début d'une méthode/d'un bloc
    public FollowTargetGoal(EntityCreature entityCreature, Duration pathDuration) {
        // Accès à l'objet courant/parent
        super(entityCreature);
        // Accès à l'objet courant/parent
        this.pathDuration = pathDuration;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean shouldStart() {
        // Appelle une méthode
        Entity target = entityCreature.getTarget();
        // Embranchement : vérifie une condition
        if (target == null) target = findTarget();
        // Embranchement : vérifie une condition
        if (target == null) return false;
        // Appelle une méthode
        final boolean result = target.getPosition().distanceSquared(entityCreature.getPosition()) >= 2 * 2;
        // Embranchement : vérifie une condition
        if (result) {
            // Accès à l'objet courant/parent
            this.target = target;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void start() {
        // Affecte une valeur
        lastUpdateTime = 0;
        // Affecte une valeur
        forceEnd = false;
        // Affecte une valeur
        lastTargetPos = null;
        // Embranchement : vérifie une condition
        if (target == null) {
            // No defined target
            // Accès à l'objet courant/parent
            this.forceEnd = true;
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Accès à l'objet courant/parent
        this.entityCreature.setTarget(target);
        // Appelle une méthode
        Navigator navigator = entityCreature.getNavigator();
        // Accès à l'objet courant/parent
        this.lastTargetPos = target.getPosition();
        // Embranchement : vérifie une condition
        if (lastTargetPos.distanceSquared(entityCreature.getPosition()) < 2 * 2) {
            // Target is too far
            // Accès à l'objet courant/parent
            this.forceEnd = true;
            // Appelle une méthode
            navigator.setPathTo(null);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (navigator.getPathPosition() == null || !navigator.getPathPosition().samePoint(lastTargetPos)) {
            // Appelle une méthode
            navigator.setPathTo(lastTargetPos);
        // Branche alternative de la condition
        } else {
            // Affecte une valeur
            forceEnd = true;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void tick(long time) {
        // Embranchement : vérifie une condition
        if (forceEnd ||
                // Instruction de code
                pathDuration.isZero() ||
                // Début d'une méthode/d'un bloc
                pathDuration.toMillis() + lastUpdateTime > time) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final Pos targetPos = entityCreature.getTarget() != null ? entityCreature.getTarget().getPosition() : null;
        // Embranchement : vérifie une condition
        if (targetPos != null && !targetPos.sameBlock(lastTargetPos)) {
            // Accès à l'objet courant/parent
            this.lastUpdateTime = time;
            // Accès à l'objet courant/parent
            this.lastTargetPos = targetPos;
            // Accès à l'objet courant/parent
            this.entityCreature.getNavigator().setPathTo(targetPos);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean shouldEnd() {
        // Appelle une méthode
        final Entity target = entityCreature.getTarget();
        // Renvoie une valeur à l'appelant
        return forceEnd ||
                // Instruction de code
                target == null ||
                // Instruction de code
                target.isRemoved() ||
                // Appelle une méthode
                target.getPosition().distanceSquared(entityCreature.getPosition()) < 2 * 2;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void end() {
        // Accès à l'objet courant/parent
        this.entityCreature.getNavigator().setPathTo(null);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
