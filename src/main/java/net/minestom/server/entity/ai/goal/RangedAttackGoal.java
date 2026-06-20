// Déclaration du paquet de ce fichier
package net.minestom.server.entity.ai.goal;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
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

// Déclaration de type (classe/interface/enum/record)
public class RangedAttackGoal extends GoalSelector {
    // Appelle une méthode
    private final Cooldown cooldown = new Cooldown(Duration.of(5, TimeUnit.SERVER_TICK));

    // Instruction de code
    private long lastShot;
    // Instruction de code
    private final Duration delay;
    // Instruction de code
    private final int attackRangeSquared;
    // Instruction de code
    private final int desirableRangeSquared;
    // Instruction de code
    private final boolean comeClose;
    // Instruction de code
    private final double power;
    // Instruction de code
    private final double spread;

    // Instruction de code
    private ProjectileGenerator projectileGenerator;

    // Instruction de code
    private boolean stop;
    // Instruction de code
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
    // Début d'une méthode/d'un bloc
    public RangedAttackGoal(EntityCreature entityCreature, int delay, int attackRange, int desirableRange, boolean comeClose, double power, double spread, TemporalUnit timeUnit) {
        // Appelle une méthode
        this(entityCreature, Duration.of(delay, timeUnit), attackRange, desirableRange, comeClose, power, spread);
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public RangedAttackGoal(EntityCreature entityCreature, Duration delay, int attackRange, int desirableRange, boolean comeClose, double power, double spread) {
        // Accès à l'objet courant/parent
        super(entityCreature);
        // Accès à l'objet courant/parent
        this.delay = delay;
        // Accès à l'objet courant/parent
        this.attackRangeSquared = attackRange * attackRange;
        // Accès à l'objet courant/parent
        this.desirableRangeSquared = desirableRange * desirableRange;
        // Accès à l'objet courant/parent
        this.comeClose = comeClose;
        // Accès à l'objet courant/parent
        this.power = power;
        // Accès à l'objet courant/parent
        this.spread = spread;
        // Appelle une méthode
        Check.argCondition(desirableRange > attackRange, "Desirable range can not exceed attack range!");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Cooldown getCooldown() {
        // Renvoie une valeur à l'appelant
        return this.cooldown;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setProjectileGenerator(ProjectileGenerator projectileGenerator) {
        // Accès à l'objet courant/parent
        this.projectileGenerator = projectileGenerator;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setProjectileGenerator(Function<Entity, EntityProjectile> projectileGenerator) {
        // Accès à l'objet courant/parent
        this.projectileGenerator = (shooter, target, pow, spr) -> {
            // Appelle une méthode
            EntityProjectile projectile = projectileGenerator.apply(shooter);
            // Appelle une méthode
            projectile.setInstance(shooter.getInstance(), shooter.getPosition().add(0D, shooter.getEyeHeight(), 0D));
            // Appelle une méthode
            projectile.shoot(target, pow, spr);
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private ProjectileGenerator getProjectileGeneratorOrDefault() {
        // Embranchement : vérifie une condition
        if (projectileGenerator == null) {
            // Appelle une méthode
            setProjectileGenerator(shooter -> new EntityProjectile(shooter, EntityType.ARROW));
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return projectileGenerator;
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
        // Appelle une méthode
        double distanceSquared = this.entityCreature.getDistanceSquared(target);
        // Affecte une valeur
        boolean comeClose = false;
        // Embranchement : vérifie une condition
        if (distanceSquared <= this.attackRangeSquared) {
            // Embranchement : vérifie une condition
            if (!Cooldown.hasCooldown(time, this.lastShot, this.delay)) {
                // Embranchement : vérifie une condition
                if (this.entityCreature.hasLineOfSight(target)) {
                    // Appelle une méthode
                    final var to = target.getPosition().add(0D, target.getEyeHeight(), 0D);
                    // Accès à l'objet courant/parent
                    this.getProjectileGeneratorOrDefault().shootProjectile(this.entityCreature, to, this.power, this.spread);

                    // Accès à l'objet courant/parent
                    this.lastShot = time;
                // Branche alternative de la condition
                } else {
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

    /**
     * The function used to generate a projectile.
     */
    // Déclaration de type (classe/interface/enum/record)
    public interface ProjectileGenerator {
        /**
         * Shoots a projectile.
         *
         * @param shooter the shooter.
         * @param target  the target position.
         * @param power   the shot power.
         * @param spread  the shot spread.
         */
        // Appelle une méthode
        void shootProjectile(EntityCreature shooter, Pos target, double power, double spread);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
