// Package declaration for this file
package net.minestom.server.entity.ai.goal;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityCreature;
// Import of a required class
import net.minestom.server.entity.EntityProjectile;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.ai.GoalSelector;
// Import of a required class
import net.minestom.server.entity.pathfinding.Navigator;
// Import of a required class
import net.minestom.server.utils.time.Cooldown;
// Import of a required class
import net.minestom.server.utils.time.TimeUnit;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.time.temporal.TemporalUnit;
// Import of a required class
import java.util.function.Function;

/**
 * Allows entity to perform both melee and ranged attacks.
 */
// Type declaration (class/interface/enum/record)
public class CombinedAttackGoal extends GoalSelector {

    // Calls a method
    private final Cooldown cooldown = new Cooldown(Duration.of(5, TimeUnit.SERVER_TICK));

    // Code statement
    private final int meleeRangeSquared;
    // Code statement
    private final Duration meleeDelay;
    // Code statement
    private final int rangedRangeSquared;
    // Code statement
    private final double rangedPower;
    // Code statement
    private final double rangedSpread;
    // Code statement
    private final Duration rangedDelay;
    // Code statement
    private final int desirableRangeSquared;
    // Code statement
    private final boolean comeClose;

    // Code statement
    private Function<Entity, EntityProjectile> projectileGenerator;

    // Code statement
    private long lastAttack;
    // Code statement
    private boolean stop;
    // Code statement
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
    // Code statement
    public CombinedAttackGoal(EntityCreature entityCreature,
                              // Code statement
                              int meleeRange, int rangedRange, double rangedPower, double rangedSpread,
                              // Code statement
                              int delay, TemporalUnit timeUnit,
                              // Start of a method/block
                              int desirableRange, boolean comeClose) {
        // Code statement
        this(
                // Code statement
                entityCreature,
                // Code statement
                meleeRange, delay, timeUnit,
                // Code statement
                rangedRange, rangedPower, rangedSpread, delay, timeUnit,
                // Code statement
                desirableRange, comeClose
        // End of a block/expression
        );
    // End of a block/expression
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
    // Code statement
    public CombinedAttackGoal(EntityCreature entityCreature,
                              // Code statement
                              int meleeRange, int rangedRange, double rangedPower, double rangedSpread,
                              // Code statement
                              Duration delay,
                              // Start of a method/block
                              int desirableRange, boolean comeClose) {
        // Code statement
        this(
                // Code statement
                entityCreature,
                // Code statement
                meleeRange, delay,
                // Code statement
                rangedRange, rangedPower, rangedSpread, delay,
                // Code statement
                desirableRange, comeClose
        // End of a block/expression
        );
    // End of a block/expression
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
    // Code statement
    public CombinedAttackGoal(EntityCreature entityCreature,
                              // Code statement
                              int meleeRange, int meleeDelay, TemporalUnit meleeTimeUnit,
                              // Code statement
                              int rangedRange, double rangedPower, double rangedSpread, int rangedDelay, TemporalUnit rangedTimeUnit,
                              // Start of a method/block
                              int desirableRange, boolean comeClose) {
        // Code statement
        this(entityCreature, meleeRange, Duration.of(meleeDelay, meleeTimeUnit), rangedRange, rangedPower, rangedSpread,
                // Calls a method
                Duration.of(rangedDelay, rangedTimeUnit), desirableRange, comeClose);
    // End of a block/expression
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
    // Code statement
    public CombinedAttackGoal(EntityCreature entityCreature,
                              // Code statement
                              int meleeRange, Duration meleeDelay,
                              // Code statement
                              int rangedRange, double rangedPower, double rangedSpread, Duration rangedDelay,
                              // Start of a method/block
                              int desirableRange, boolean comeClose) {
        // Access to the current/parent object
        super(entityCreature);
        // Access to the current/parent object
        this.meleeRangeSquared = meleeRange * meleeRange;
        // Access to the current/parent object
        this.meleeDelay = meleeDelay;
        // Access to the current/parent object
        this.rangedRangeSquared = rangedRange * rangedRange;
        // Access to the current/parent object
        this.rangedPower = rangedPower;
        // Access to the current/parent object
        this.rangedSpread = rangedSpread;
        // Access to the current/parent object
        this.rangedDelay = rangedDelay;
        // Access to the current/parent object
        this.desirableRangeSquared = desirableRange * desirableRange;
        // Access to the current/parent object
        this.comeClose = comeClose;
        // Calls a method
        Check.argCondition(desirableRange > rangedRange, "Desirable range can not exceed ranged range!");
    // End of a block/expression
    }

    // Start of a method/block
    public Cooldown getCooldown() {
        // Returns a value to the caller
        return this.cooldown;
    // End of a block/expression
    }

    // Start of a method/block
    public void setProjectileGenerator(Function<Entity, EntityProjectile> projectileGenerator) {
        // Access to the current/parent object
        this.projectileGenerator = projectileGenerator;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean shouldStart() {
        // Access to the current/parent object
        this.cachedTarget = findTarget();
        // Returns a value to the caller
        return this.cachedTarget != null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void start() {
        // Access to the current/parent object
        this.entityCreature.getNavigator().setPathTo(this.cachedTarget.getPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void tick(long time) {
        // Code statement
        Entity target;
        // Branch: checks a condition
        if (this.cachedTarget != null) {
            // Assigns a value
            target = this.cachedTarget;
            // Access to the current/parent object
            this.cachedTarget = null;
        // Alternative branch of the condition
        } else {
            // Calls a method
            target = findTarget();
        // End of a block/expression
        }
        // Branch: checks a condition
        if (target == null) {
            // Access to the current/parent object
            this.stop = true;
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Calls a method
        double distanceSquared = this.entityCreature.getDistanceSquared(target);
        // Assigns a value
        boolean comeClose = false;
        // First of all, checking if to perform melee or ranged attack depending on the distance to target.
        // Branch: checks a condition
        if (distanceSquared <= this.meleeRangeSquared) {
            // Branch: checks a condition
            if (!Cooldown.hasCooldown(time, this.lastAttack, this.meleeDelay)) {
                // Access to the current/parent object
                this.entityCreature.attack(target, true);
                // Access to the current/parent object
                this.lastAttack = time;
            // End of a block/expression
            }
        // Branch: checks a condition
        } else if (distanceSquared <= this.rangedRangeSquared) {
            // Branch: checks a condition
            if (!Cooldown.hasCooldown(time, this.lastAttack, this.rangedDelay)) {
                // Branch: checks a condition
                if (this.entityCreature.hasLineOfSight(target)) {
                    // If target is on line of entity sight, ranged attack can be performed
                    // Calls a method
                    final var to = target.getPosition().add(0D, target.getEyeHeight(), 0D);

                    // Assigns a value
                    Function<Entity, EntityProjectile> projectileGenerator = this.projectileGenerator;
                    // Branch: checks a condition
                    if (projectileGenerator == null) {
                        // Calls a method
                        projectileGenerator = shooter -> new EntityProjectile(shooter, EntityType.ARROW);
                    // End of a block/expression
                    }
                    // Calls a method
                    EntityProjectile projectile = projectileGenerator.apply(this.entityCreature);
                    // Calls a method
                    projectile.setInstance(this.entityCreature.getInstance(), this.entityCreature.getPosition());

                    // Calls a method
                    projectile.shoot(to, this.rangedPower, this.rangedSpread);
                    // Access to the current/parent object
                    this.lastAttack = time;
                // Alternative branch of the condition
                } else {
                    // Otherwise deciding whether to go to the enemy.
                    // Assigns a value
                    comeClose = this.comeClose;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        Navigator navigator = this.entityCreature.getNavigator();
        // Calls a method
        final var pathPosition = navigator.getPathPosition();
        // If we don't want to come close and we're already within desirable range, no movement is needed.
        // Branch: checks a condition
        if (!comeClose && distanceSquared <= this.desirableRangeSquared) {
            // Branch: checks a condition
            if (pathPosition != null) {
                // Calls a method
                navigator.setPathTo(null);
            // End of a block/expression
            }
            // Access to the current/parent object
            this.entityCreature.lookAt(target);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Otherwise going to the target.
        // Calls a method
        final var targetPosition = target.getPosition();
        // Branch: checks a condition
        if (pathPosition == null || !pathPosition.samePoint(targetPosition)) {
            // Branch: checks a condition
            if (this.cooldown.isReady(time)) {
                // Access to the current/parent object
                this.cooldown.refreshLastUpdate(time);
                // Calls a method
                navigator.setPathTo(targetPosition);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean shouldEnd() {
        // Returns a value to the caller
        return this.stop;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void end() {
        // Stop following the target
        // Access to the current/parent object
        this.entityCreature.getNavigator().setPathTo(null);
    // End of a block/expression
    }

// End of a block/expression
}
