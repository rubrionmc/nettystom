// Package declaration for this file
package net.minestom.server.entity.pathfinding.followers;

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
public class NoPhysicsNodeFollower implements NodeFollower {
    // Code statement
    private final Entity entity;

    // Start of a method/block
    public NoPhysicsNodeFollower(Entity entity) {
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

        // Branch: checks a condition
        if (dy > 0 && entity.isOnGround()) jump(4f);

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
        var newPosition = position.add(speedX, 0, speedZ);
        // Access to the current/parent object
        this.entity.refreshPosition(newPosition.withView(yaw, pitch));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void jump(@Nullable Point point, @Nullable Point target) {
        // Branch: checks a condition
        if (entity.isOnGround()) {
            // Calls a method
            jump(4f);
        // End of a block/expression
        }
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

    // Start of a method/block
    public void jump(float height) {
        // Access to the current/parent object
        this.entity.setVelocity(new Vec(0, height * 2.5f, 0));
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
