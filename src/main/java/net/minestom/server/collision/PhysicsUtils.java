// Package declaration for this file
package net.minestom.server.collision;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.instance.WorldBorder;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
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
    // Code statement
    public static PhysicsResult simulateMovement(Pos entityPosition, Vec entityVelocityPerTick, BoundingBox entityBoundingBox,
                                                          // Code statement
                                                          WorldBorder worldBorder, Block.Getter blockGetter, Aerodynamics aerodynamics, boolean entityNoGravity,
                                                          // Start of a method/block
                                                          boolean entityHasPhysics, boolean entityOnGround, boolean entityFlying, @Nullable PhysicsResult previousPhysicsResult) {
        // Assigns a value
        final PhysicsResult physicsResult = entityHasPhysics ?
                // Code statement
                CollisionUtils.handlePhysics(blockGetter, entityBoundingBox, entityPosition, entityVelocityPerTick, previousPhysicsResult, false) :
                // Calls a method
                CollisionUtils.blocklessCollision(entityPosition, entityVelocityPerTick);

        // Calls a method
        Pos newPosition = physicsResult.newPosition();
        // Calls a method
        Vec newVelocity = physicsResult.newVelocity();

        // Calls a method
        Pos positionWithinBorder = CollisionUtils.applyWorldBorder(worldBorder, entityPosition, newPosition);
        // Calls a method
        newVelocity = updateVelocity(positionWithinBorder, newVelocity, blockGetter, aerodynamics, !positionWithinBorder.samePoint(entityPosition), entityFlying, entityOnGround, entityNoGravity);

        // Calls a method
        final boolean stillCached = physicsResult.cached() && newVelocity.samePoint(physicsResult.newVelocity()) && positionWithinBorder.samePoint(physicsResult.newPosition());

        // Returns a value to the caller
        return new PhysicsResult(positionWithinBorder, newVelocity, physicsResult.isOnGround(), physicsResult.collisionX(), physicsResult.collisionY(), physicsResult.collisionZ(),
                // Calls a method
                physicsResult.originalDelta(), physicsResult.collisionPoints(), physicsResult.collisionShapes(), physicsResult.collisionShapePositions(), physicsResult.hasCollision(), physicsResult.res(), stillCached);
    // End of a block/expression
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
    // Code statement
    public static Vec updateVelocity(Pos entityPosition, Vec currentVelocity, Block.Getter blockGetter, Aerodynamics aerodynamics,
                                               // Start of a method/block
                                               boolean positionChanged, boolean entityFlying, boolean entityOnGround, boolean entityNoGravity) {
        // Branch: checks a condition
        if (!positionChanged) {
            // Branch: checks a condition
            if (entityFlying) return Vec.ZERO;
            // Returns a value to the caller
            return new Vec(0, entityNoGravity ? 0 : -aerodynamics.gravity() * aerodynamics.verticalAirResistance(), 0);
        // End of a block/expression
        }

        // Assigns a value
        double drag = entityOnGround ? blockGetter.getBlock(entityPosition.sub(0, 0.5000001, 0)).registry().friction() * aerodynamics.horizontalAirResistance() :
                // Calls a method
                aerodynamics.horizontalAirResistance();
        // Calls a method
        double gravity = entityFlying ? 0 : aerodynamics.gravity();
        // Calls a method
        double gravityDrag = entityFlying ? 0.6 : aerodynamics.verticalAirResistance();

        // Calls a method
        double x = currentVelocity.x() * drag;
        // Calls a method
        double y = entityNoGravity ? currentVelocity.y() : (currentVelocity.y() - gravity) * gravityDrag;
        // Calls a method
        double z = currentVelocity.z() * drag;
        // Returns a value to the caller
        return new Vec(Math.abs(x) < Vec.EPSILON ? 0 : x, Math.abs(y) < Vec.EPSILON ? 0 : y, Math.abs(z) < Vec.EPSILON ? 0 : z);
    // End of a block/expression
    }

    // Code statement
    private PhysicsUtils() {}
// End of a block/expression
}
