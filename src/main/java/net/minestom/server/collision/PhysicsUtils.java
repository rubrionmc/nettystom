// Déclaration du paquet de ce fichier
package net.minestom.server.collision;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.instance.WorldBorder;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public final class PhysicsUtils {
    /**
     * Simulate the entity's movement physics
     * <p>
     * This is done by first attempting to move the entity forward with the
     * current velocity passed in. Then adjusting the velocity by applying
     * air resistance and friction.
     *
     * @param entityPosition the current entity position
     * @param entityVelocityPerTick the current entity velocity in blocks/tick
     * @param entityBoundingBox the current entity bounding box
     * @param worldBorder the world border to test bounds against
     * @param blockGetter the block getter to test block collisions against
     * @param aerodynamics the current entity aerodynamics
     * @param entityNoGravity whether the entity has gravity
     * @param entityHasPhysics whether the entity has physics
     * @param entityOnGround whether the entity is on the ground
     * @param entityFlying whether the entity is flying
     * @param previousPhysicsResult the physics result from the previous simulation or null
     * @return a {@link PhysicsResult} containing the resulting physics state of this simulation
     */
    // Instruction de code
    public static PhysicsResult simulateMovement(Pos entityPosition, Vec entityVelocityPerTick, BoundingBox entityBoundingBox,
                                                          // Instruction de code
                                                          WorldBorder worldBorder, Block.Getter blockGetter, Aerodynamics aerodynamics, boolean entityNoGravity,
                                                          // Début d'une méthode/d'un bloc
                                                          boolean entityHasPhysics, boolean entityOnGround, boolean entityFlying, @Nullable PhysicsResult previousPhysicsResult) {
        // Affecte une valeur
        final PhysicsResult physicsResult = entityHasPhysics ?
                // Instruction de code
                CollisionUtils.handlePhysics(blockGetter, entityBoundingBox, entityPosition, entityVelocityPerTick, previousPhysicsResult, false) :
                // Appelle une méthode
                CollisionUtils.blocklessCollision(entityPosition, entityVelocityPerTick);

        // Appelle une méthode
        Pos newPosition = physicsResult.newPosition();
        // Appelle une méthode
        Vec newVelocity = physicsResult.newVelocity();

        // Appelle une méthode
        Pos positionWithinBorder = CollisionUtils.applyWorldBorder(worldBorder, entityPosition, newPosition);
        // Appelle une méthode
        newVelocity = updateVelocity(positionWithinBorder, newVelocity, blockGetter, aerodynamics, !positionWithinBorder.samePoint(entityPosition), entityFlying, entityOnGround, entityNoGravity);

        // Appelle une méthode
        final boolean stillCached = physicsResult.cached() && newVelocity.samePoint(physicsResult.newVelocity()) && positionWithinBorder.samePoint(physicsResult.newPosition());

        // Renvoie une valeur à l'appelant
        return new PhysicsResult(positionWithinBorder, newVelocity, physicsResult.isOnGround(), physicsResult.collisionX(), physicsResult.collisionY(), physicsResult.collisionZ(),
                // Appelle une méthode
                physicsResult.originalDelta(), physicsResult.collisionPoints(), physicsResult.collisionShapes(), physicsResult.collisionShapePositions(), physicsResult.hasCollision(), physicsResult.res(), stillCached);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Calculates an updated velocity for an entity
     * <p>
     * If the position has not changed then the x and z values will not be touched, and only gravity will be accounted for if the entity is not flying.
     * Otherwise, the velocity will be adjusted by applying air resistance, gravity, and friction (only if the entity is on the ground).
     *
     * @param entityPosition the current entity position
     * @param currentVelocity the current entity velocity in blocks/tick
     * @param blockGetter the block getter to test block collisions against
     * @param aerodynamics the current entity aerodynamics
     * @param positionChanged whether the position changed for the entity
     * @param entityFlying whether the entity is flying
     * @param entityOnGround whether the entity is on the ground
     * @param entityNoGravity whether the entity has gravity
     * @return the updated velocity or {@link Vec#ZERO} if the entity is flying
     */
    // Instruction de code
    public static Vec updateVelocity(Pos entityPosition, Vec currentVelocity, Block.Getter blockGetter, Aerodynamics aerodynamics,
                                               // Début d'une méthode/d'un bloc
                                               boolean positionChanged, boolean entityFlying, boolean entityOnGround, boolean entityNoGravity) {
        // Embranchement : vérifie une condition
        if (!positionChanged) {
            // Embranchement : vérifie une condition
            if (entityFlying) return Vec.ZERO;
            // Renvoie une valeur à l'appelant
            return new Vec(0, entityNoGravity ? 0 : -aerodynamics.gravity() * aerodynamics.verticalAirResistance(), 0);
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        double drag = entityOnGround ? blockGetter.getBlock(entityPosition.sub(0, 0.5000001, 0)).registry().friction() * aerodynamics.horizontalAirResistance() :
                // Appelle une méthode
                aerodynamics.horizontalAirResistance();
        // Appelle une méthode
        double gravity = entityFlying ? 0 : aerodynamics.gravity();
        // Appelle une méthode
        double gravityDrag = entityFlying ? 0.6 : aerodynamics.verticalAirResistance();

        // Appelle une méthode
        double x = currentVelocity.x() * drag;
        // Appelle une méthode
        double y = entityNoGravity ? currentVelocity.y() : (currentVelocity.y() - gravity) * gravityDrag;
        // Appelle une méthode
        double z = currentVelocity.z() * drag;
        // Renvoie une valeur à l'appelant
        return new Vec(Math.abs(x) < Vec.EPSILON ? 0 : x, Math.abs(y) < Vec.EPSILON ? 0 : y, Math.abs(z) < Vec.EPSILON ? 0 : z);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private PhysicsUtils() {}
// Fin d'un bloc/d'une expression
}
