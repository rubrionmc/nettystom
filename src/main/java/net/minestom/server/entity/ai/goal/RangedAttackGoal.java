// Package declaration for this file
package net.minestom.server.entity.ai.goal;

// Import of a required class
import net.minestom.server.coordinate.Pos;
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

// Type declaration (class/interface/enum/record)
public class RangedAttackGoal extends GoalSelector {
    // Calls a method
    private final Cooldown cooldown = new Cooldown(Duration.of(5, TimeUnit.SERVER_TICK));

    // Code statement
    private long lastShot;
    // Code statement
    private final Duration delay;
    // Code statement
    private final int attackRangeSquared;
    // Code statement
    private final int desirableRangeSquared;
    // Code statement
    private final boolean comeClose;
    // Code statement
    private final double power;
    // Code statement
    private final double spread;

    // Code statement
    private ProjectileGenerator projectileGenerator;

    // Code statement
    private boolean stop;
    // Code statement
    private Entity cachedTarget;

    /**
     * @param entityCreature the entity to add the goal to.
     * @param delay          the delay between each shots.
     * @param attackRange    the allowed range the entity can shoot others.
     * @param desirableRange the desirable range: the entity will try to stay no further than this distance.
     * @param comeClose      whether entity should go as close as possible to the target whether target is not in line of sight.
     * @param spread         shot spread (0 for best accuracy).
     * @param power          shot power (1 for normal).
     * @param timeUnit       the unit of the delay.
     */
    // Start of a method/block
    public RangedAttackGoal(EntityCreature entityCreature, int delay, int attackRange, int desirableRange, boolean comeClose, double power, double spread, TemporalUnit timeUnit) {
        // Calls a method
        this(entityCreature, Duration.of(delay, timeUnit), attackRange, desirableRange, comeClose, power, spread);
    // End of a block/expression
    }

    /**
     * @param entityCreature the entity to add the goal to.
     * @param delay          the delay between each shots.
     * @param attackRange    the allowed range the entity can shoot others.
     * @param desirableRange the desirable range: the entity will try to stay no further than this distance.
     * @param comeClose      whether entity should go as close as possible to the target whether target is not in line of sight.
     * @param spread         shot spread (0 for best accuracy).
     * @param power          shot power (1 for normal).
     */
    // Start of a method/block
    public RangedAttackGoal(EntityCreature entityCreature, Duration delay, int attackRange, int desirableRange, boolean comeClose, double power, double spread) {
        // Access to the current/parent object
        super(entityCreature);
        // Access to the current/parent object
        this.delay = delay;
        // Access to the current/parent object
        this.attackRangeSquared = attackRange * attackRange;
        // Access to the current/parent object
        this.desirableRangeSquared = desirableRange * desirableRange;
        // Access to the current/parent object
        this.comeClose = comeClose;
        // Access to the current/parent object
        this.power = power;
        // Access to the current/parent object
        this.spread = spread;
        // Calls a method
        Check.argCondition(desirableRange > attackRange, "Desirable range can not exceed attack range!");
    // End of a block/expression
    }

    // Start of a method/block
    public Cooldown getCooldown() {
        // Returns a value to the caller
        return this.cooldown;
    // End of a block/expression
    }

    // Start of a method/block
    public void setProjectileGenerator(ProjectileGenerator projectileGenerator) {
        // Access to the current/parent object
        this.projectileGenerator = projectileGenerator;
    // End of a block/expression
    }

    // Start of a method/block
    public void setProjectileGenerator(Function<Entity, EntityProjectile> projectileGenerator) {
        // Access to the current/parent object
        this.projectileGenerator = (shooter, target, pow, spr) -> {
            // Calls a method
            EntityProjectile projectile = projectileGenerator.apply(shooter);
            // Calls a method
            projectile.setInstance(shooter.getInstance(), shooter.getPosition().add(0D, shooter.getEyeHeight(), 0D));
            // Calls a method
            projectile.shoot(target, pow, spr);
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    private ProjectileGenerator getProjectileGeneratorOrDefault() {
        // Branch: checks a condition
        if (projectileGenerator == null) {
            // Calls a method
            setProjectileGenerator(shooter -> new EntityProjectile(shooter, EntityType.ARROW));
        // End of a block/expression
        }
        // Returns a value to the caller
        return projectileGenerator;
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
        // Branch: checks a condition
        if (distanceSquared <= this.attackRangeSquared) {
            // Branch: checks a condition
            if (!Cooldown.hasCooldown(time, this.lastShot, this.delay)) {
                // Branch: checks a condition
                if (this.entityCreature.hasLineOfSight(target)) {
                    // Calls a method
                    final var to = target.getPosition().add(0D, target.getEyeHeight(), 0D);
                    // Access to the current/parent object
                    this.getProjectileGeneratorOrDefault().shootProjectile(this.entityCreature, to, this.power, this.spread);

                    // Access to the current/parent object
                    this.lastShot = time;
                // Alternative branch of the condition
                } else {
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

    /**
     * The function used to generate a projectile.
     */
    // Type declaration (class/interface/enum/record)
    public interface ProjectileGenerator {
        /**
         * Shoots a projectile.
         *
         * @param shooter the shooter.
         * @param target  the target position.
         * @param power   the shot power.
         * @param spread  the shot spread.
         */
        // Calls a method
        void shootProjectile(EntityCreature shooter, Pos target, double power, double spread);
    // End of a block/expression
    }
// End of a block/expression
}
