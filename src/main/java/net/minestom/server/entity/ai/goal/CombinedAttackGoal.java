// Déclaration du paquet de ce fichier
package net.minestom.server.entity.ai.goal;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityCreature;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityProjectile;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.ai.GoalSelector;
// Import d'une classe nécessaire
import net.minestom.server.entity.pathfinding.Navigator;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.Cooldown;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.TimeUnit;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.time.temporal.TemporalUnit;
// Import d'une classe nécessaire
import java.util.function.Function;

/**
 * Allows entity to perform both melee and ranged attacks.
 */
// Déclaration de type (classe/interface/enum/record)
public class CombinedAttackGoal extends GoalSelector {

    // Appelle une méthode
    private final Cooldown cooldown = new Cooldown(Duration.of(5, TimeUnit.SERVER_TICK));

    // Instruction de code
    private final int meleeRangeSquared;
    // Instruction de code
    private final Duration meleeDelay;
    // Instruction de code
    private final int rangedRangeSquared;
    // Instruction de code
    private final double rangedPower;
    // Instruction de code
    private final double rangedSpread;
    // Instruction de code
    private final Duration rangedDelay;
    // Instruction de code
    private final int desirableRangeSquared;
    // Instruction de code
    private final boolean comeClose;

    // Instruction de code
    private Function<Entity, EntityProjectile> projectileGenerator;

    // Instruction de code
    private long lastAttack;
    // Instruction de code
    private boolean stop;
    // Instruction de code
    private Entity cachedTarget;

    /**
     * @param entityCreature the entity to add the goal to.
     * @param meleeRange     the allowed range the entity can hit others in melee.
     * @param rangedRange    the allowed range the entity can shoot others.
     * @param rangedPower    shot power (1 for normal).
     * @param rangedSpread   shot spread (0 for best accuracy).
     * @param delay          the delay between any attacks.
     * @param timeUnit       the unit of the delay.
     * @param desirableRange the desirable range: the entity will try to stay no further than this distance.
     * @param comeClose      if entity should go as close as possible to the target whether target is not in line of sight for a ranged attack.
     */
    // Instruction de code
    public CombinedAttackGoal(EntityCreature entityCreature,
                              // Instruction de code
                              int meleeRange, int rangedRange, double rangedPower, double rangedSpread,
                              // Instruction de code
                              int delay, TemporalUnit timeUnit,
                              // Début d'une méthode/d'un bloc
                              int desirableRange, boolean comeClose) {
        // Instruction de code
        this(
                // Instruction de code
                entityCreature,
                // Instruction de code
                meleeRange, delay, timeUnit,
                // Instruction de code
                rangedRange, rangedPower, rangedSpread, delay, timeUnit,
                // Instruction de code
                desirableRange, comeClose
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    /**
     * @param entityCreature the entity to add the goal to.
     * @param meleeRange     the allowed range the entity can hit others in melee.
     * @param rangedRange    the allowed range the entity can shoot others.
     * @param rangedPower    shot power (1 for normal).
     * @param rangedSpread   shot spread (0 for best accuracy).
     * @param delay          the delay between any attacks.
     * @param desirableRange the desirable range: the entity will try to stay no further than this distance.
     * @param comeClose      if entity should go as close as possible to the target whether target is not in line of sight for a ranged attack.
     */
    // Instruction de code
    public CombinedAttackGoal(EntityCreature entityCreature,
                              // Instruction de code
                              int meleeRange, int rangedRange, double rangedPower, double rangedSpread,
                              // Instruction de code
                              Duration delay,
                              // Début d'une méthode/d'un bloc
                              int desirableRange, boolean comeClose) {
        // Instruction de code
        this(
                // Instruction de code
                entityCreature,
                // Instruction de code
                meleeRange, delay,
                // Instruction de code
                rangedRange, rangedPower, rangedSpread, delay,
                // Instruction de code
                desirableRange, comeClose
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    /**
     * @param entityCreature the entity to add the goal to.
     * @param meleeRange     the allowed range the entity can hit others in melee.
     * @param meleeDelay     the delay between melee attacks.
     * @param meleeTimeUnit  the unit of the melee delay.
     * @param rangedRange    the allowed range the entity can shoot others.
     * @param rangedPower    shot power (1 for normal).
     * @param rangedSpread   shot spread (0 for best accuracy).
     * @param rangedDelay    the delay between ranged attacks.
     * @param rangedTimeUnit the unit of the ranged delay.
     * @param desirableRange the desirable range: the entity will try to stay no further than this distance.
     * @param comeClose      if entity should go as close as possible to the target whether target is not in line of sight for a ranged attack.
     */
    // Instruction de code
    public CombinedAttackGoal(EntityCreature entityCreature,
                              // Instruction de code
                              int meleeRange, int meleeDelay, TemporalUnit meleeTimeUnit,
                              // Instruction de code
                              int rangedRange, double rangedPower, double rangedSpread, int rangedDelay, TemporalUnit rangedTimeUnit,
                              // Début d'une méthode/d'un bloc
                              int desirableRange, boolean comeClose) {
        // Instruction de code
        this(entityCreature, meleeRange, Duration.of(meleeDelay, meleeTimeUnit), rangedRange, rangedPower, rangedSpread,
                // Appelle une méthode
                Duration.of(rangedDelay, rangedTimeUnit), desirableRange, comeClose);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @param entityCreature the entity to add the goal to.
     * @param meleeRange     the allowed range the entity can hit others in melee.
     * @param meleeDelay     the delay between melee attacks.
     * @param rangedRange    the allowed range the entity can shoot others.
     * @param rangedPower    shot power (1 for normal).
     * @param rangedSpread   shot spread (0 for best accuracy).
     * @param rangedDelay    the delay between ranged attacks.
     * @param desirableRange the desirable range: the entity will try to stay no further than this distance.
     * @param comeClose      if entity should go as close as possible to the target whether target is not in line of sight for a ranged attack.
     */
    // Instruction de code
    public CombinedAttackGoal(EntityCreature entityCreature,
                              // Instruction de code
                              int meleeRange, Duration meleeDelay,
                              // Instruction de code
                              int rangedRange, double rangedPower, double rangedSpread, Duration rangedDelay,
                              // Début d'une méthode/d'un bloc
                              int desirableRange, boolean comeClose) {
        // Accès à l'objet courant/parent
        super(entityCreature);
        // Accès à l'objet courant/parent
        this.meleeRangeSquared = meleeRange * meleeRange;
        // Accès à l'objet courant/parent
        this.meleeDelay = meleeDelay;
        // Accès à l'objet courant/parent
        this.rangedRangeSquared = rangedRange * rangedRange;
        // Accès à l'objet courant/parent
        this.rangedPower = rangedPower;
        // Accès à l'objet courant/parent
        this.rangedSpread = rangedSpread;
        // Accès à l'objet courant/parent
        this.rangedDelay = rangedDelay;
        // Accès à l'objet courant/parent
        this.desirableRangeSquared = desirableRange * desirableRange;
        // Accès à l'objet courant/parent
        this.comeClose = comeClose;
        // Appelle une méthode
        Check.argCondition(desirableRange > rangedRange, "Desirable range can not exceed ranged range!");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Cooldown getCooldown() {
        // Renvoie une valeur à l'appelant
        return this.cooldown;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setProjectileGenerator(Function<Entity, EntityProjectile> projectileGenerator) {
        // Accès à l'objet courant/parent
        this.projectileGenerator = projectileGenerator;
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
        // Accès à l'objet courant/parent
        this.entityCreature.getNavigator().setPathTo(this.cachedTarget.getPosition());
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
        // Embranchement : vérifie une condition
        if (target == null) {
            // Accès à l'objet courant/parent
            this.stop = true;
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        double distanceSquared = this.entityCreature.getDistanceSquared(target);
        // Affecte une valeur
        boolean comeClose = false;
        // First of all, checking if to perform melee or ranged attack depending on the distance to target.
        // Embranchement : vérifie une condition
        if (distanceSquared <= this.meleeRangeSquared) {
            // Embranchement : vérifie une condition
            if (!Cooldown.hasCooldown(time, this.lastAttack, this.meleeDelay)) {
                // Accès à l'objet courant/parent
                this.entityCreature.attack(target, true);
                // Accès à l'objet courant/parent
                this.lastAttack = time;
            // Fin d'un bloc/d'une expression
            }
        // Embranchement : vérifie une condition
        } else if (distanceSquared <= this.rangedRangeSquared) {
            // Embranchement : vérifie une condition
            if (!Cooldown.hasCooldown(time, this.lastAttack, this.rangedDelay)) {
                // Embranchement : vérifie une condition
                if (this.entityCreature.hasLineOfSight(target)) {
                    // If target is on line of entity sight, ranged attack can be performed
                    // Appelle une méthode
                    final var to = target.getPosition().add(0D, target.getEyeHeight(), 0D);

                    // Affecte une valeur
                    Function<Entity, EntityProjectile> projectileGenerator = this.projectileGenerator;
                    // Embranchement : vérifie une condition
                    if (projectileGenerator == null) {
                        // Appelle une méthode
                        projectileGenerator = shooter -> new EntityProjectile(shooter, EntityType.ARROW);
                    // Fin d'un bloc/d'une expression
                    }
                    // Appelle une méthode
                    EntityProjectile projectile = projectileGenerator.apply(this.entityCreature);
                    // Appelle une méthode
                    projectile.setInstance(this.entityCreature.getInstance(), this.entityCreature.getPosition());

                    // Appelle une méthode
                    projectile.shoot(to, this.rangedPower, this.rangedSpread);
                    // Accès à l'objet courant/parent
                    this.lastAttack = time;
                // Branche alternative de la condition
                } else {
                    // Otherwise deciding whether to go to the enemy.
                    // Affecte une valeur
                    comeClose = this.comeClose;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        Navigator navigator = this.entityCreature.getNavigator();
        // Appelle une méthode
        final var pathPosition = navigator.getPathPosition();
        // If we don't want to come close and we're already within desirable range, no movement is needed.
        // Embranchement : vérifie une condition
        if (!comeClose && distanceSquared <= this.desirableRangeSquared) {
            // Embranchement : vérifie une condition
            if (pathPosition != null) {
                // Appelle une méthode
                navigator.setPathTo(null);
            // Fin d'un bloc/d'une expression
            }
            // Accès à l'objet courant/parent
            this.entityCreature.lookAt(target);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Otherwise going to the target.
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

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean shouldEnd() {
        // Renvoie une valeur à l'appelant
        return this.stop;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void end() {
        // Stop following the target
        // Accès à l'objet courant/parent
        this.entityCreature.getNavigator().setPathTo(null);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
