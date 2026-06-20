// Package declaration for this file
package net.minestom.server.entity.pathfinding.followers;

// Import of a required class
import net.minestom.server.collision.CollisionUtils;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.LivingEntity;
// Import of a required class
import net.minestom.server.entity.attribute.Attribute;
// Import of a required class
import net.minestom.server.utils.position.PositionUtils;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class WaterNodeFollower implements NodeFollower {
    // Code statement
    private final Entity entity;
    // Assigns a value
    private static final double WATER_SPEED_MULTIPLIER = 0.5;

    // Start of a method/block
    public WaterNodeFollower(Entity entity) {
        // Access to the current/parent object
        this.entity = entity;
    // End of a block/expression
    }

    /**
     * Used to move the entity toward {@code direction} in the X and Z axis
     * Gravity is still applied but the entity will not attempt to jump
     * Also update the yaw/pitch of the entity to look along 'direction'
     *
     * @param direction the targeted position
     * @param speed     define how far the entity will move
     */
    // Start of a method/block
    public void moveTowards(Point direction, double speed, Point lookAt) {
        // Calls a method
        final Pos position = entity.getPosition();
        // Calls a method
        final double dx = direction.x() - position.x();
        // Calls a method
        final double dy = direction.y() - position.y();
        // Calls a method
        final double dz = direction.z() - position.z();

        // Calls a method
        final double dxLook = lookAt.x() - position.x();
        // Calls a method
        final double dyLook = lookAt.y() - position.y();
        // Calls a method
        final double dzLook = lookAt.z() - position.z();

        // the purpose of these few lines is to slow down entities when they reach their destination
        // Assigns a value
        final double distSquared = dx * dx + dy * dy + dz * dz;
        // Branch: checks a condition
        if (speed > distSquared) {
            // Assigns a value
            speed = distSquared;
        // End of a block/expression
        }

        // Calls a method
        var instance = entity.getInstance();
        // Branch: checks a condition
        if (instance != null)
            // Branch: checks a condition
            if (instance.getBlock(position).isLiquid()) {
                // Code statement
                speed *= WATER_SPEED_MULTIPLIER;
            // End of a block/expression
            }

        // Calls a method
        final double radians = Math.atan2(dz, dx);
        // Calls a method
        final double speedX = Math.cos(radians) * speed;
        // Calls a method
        final double speedZ = Math.sin(radians) * speed;
        // Calls a method
        final float yaw = PositionUtils.getLookYaw(dxLook, dzLook);
        // Calls a method
        final float pitch = PositionUtils.getLookPitch(dxLook, dyLook, dzLook);

        // Calls a method
        double speedY = Math.signum(dy) * 0.5 * speed;
        // Branch: checks a condition
        if (Math.min(Math.abs(dy), Math.abs(speedY)) == Math.abs(dy)) {
            // Assigns a value
            speedY = dy;
        // End of a block/expression
        }

        // Calls a method
        final var physicsResult = CollisionUtils.handlePhysics(entity, new Vec(speedX, speedY, speedZ));
        // Access to the current/parent object
        this.entity.refreshPosition(physicsResult.newPosition().asPos().withView(yaw, pitch));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void jump(@Nullable Point point, @Nullable Point target) {
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isAtPoint(Point point) {
        // Returns a value to the caller
        return entity.getPosition().sameBlock(point);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public double movementSpeed() {
        // Branch: checks a condition
        if (entity instanceof LivingEntity living) {
            // Returns a value to the caller
            return living.getAttribute(Attribute.MOVEMENT_SPEED).getValue();
        // End of a block/expression
        }

        // Returns a value to the caller
        return 0.1f;
    // End of a block/expression
    }
// End of a block/expression
}
