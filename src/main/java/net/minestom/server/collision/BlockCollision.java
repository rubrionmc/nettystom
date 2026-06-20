// Package declaration for this file
package net.minestom.server.collision;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
final class BlockCollision {
    // Assigns a value
    static final Point[] NO_COLLISION_POINTS = new Point[3];
    // Assigns a value
    static final Shape[] NO_COLLISION_SHAPES = new Shape[3];
    // Assigns a value
    static final Point[] NO_COLLISION_SHAPE_POSITIONS = new Point[3];

    /**
     * Moves an entity with physics applied (ie checking against blocks)
     * <p>
     * Works by getting all the full blocks that an entity could interact with.
     * All bounding boxes inside the full blocks are checked for collisions with the entity.
     */
    // Code statement
    static PhysicsResult handlePhysics(BoundingBox boundingBox,
                                       // Code statement
                                       Vec velocity, Pos entityPosition,
                                       // Code statement
                                       Block.Getter getter,
                                       // Annotation for the following element
                                       @Nullable PhysicsResult lastPhysicsResult,
                                       // Start of a method/block
                                       boolean singleCollision) {
        // Branch: checks a condition
        if (velocity.isZero()) {
            // Returns a value to the caller
            return new PhysicsResult(entityPosition, Vec.ZERO, false, false, false, false,
                    // Code statement
                    velocity, NO_COLLISION_POINTS, NO_COLLISION_SHAPES, NO_COLLISION_SHAPE_POSITIONS, false, SweepResult.NO_COLLISION);
        // End of a block/expression
        }
        // Fast-exit using cache
        // Calls a method
        final PhysicsResult cachedResult = cachedPhysics(velocity, entityPosition, getter, lastPhysicsResult);
        // Branch: checks a condition
        if (cachedResult != null) {
            // Returns a value to the caller
            return cachedResult;
        // End of a block/expression
        }
        // Expensive AABB computation
        // Returns a value to the caller
        return stepPhysics(boundingBox, velocity, entityPosition, getter, singleCollision);
    // End of a block/expression
    }

    // Start of a method/block
    static Entity canPlaceBlockAt(Instance instance, Point blockPos, Block b) {
        // Loop: repeats a block
        for (Entity entity : instance.getNearbyEntities(blockPos, 3)) {
            // Branch: checks a condition
            if (!entity.preventBlockPlacement())
                // Continues to the next loop iteration
                continue;

            // Code statement
            final boolean intersects;
            // Branch: checks a condition
            if (entity instanceof Player) {
                // Need to move player slightly away from block we're placing.
                // If player is at block 40 we cannot place a block at block 39 with side length 1 because the block will be in [39, 40]
                // For this reason we subtract a small amount from the player position
                // Calls a method
                Point playerPos = entity.getPosition().add(entity.getPosition().sub(blockPos).mul(0.0000001));
                // Calls a method
                intersects = b.registry().collisionShape().intersectBox(playerPos.sub(blockPos), entity.getBoundingBox());
            // Alternative branch of the condition
            } else {
                // Calls a method
                intersects = b.registry().collisionShape().intersectBox(entity.getPosition().sub(blockPos), entity.getBoundingBox());
            // End of a block/expression
            }
            // Branch: checks a condition
            if (intersects) return entity;
        // End of a block/expression
        }
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    // Code statement
    private static PhysicsResult cachedPhysics(Vec velocity, Pos entityPosition,
                                               // Start of a method/block
                                               Block.Getter getter, PhysicsResult lastPhysicsResult) {
        // Branch: checks a condition
        if (lastPhysicsResult != null && lastPhysicsResult.collisionShapes()[1] instanceof ShapeImpl shape) {
            // Calls a method
            var currentBlock = getter.getBlock(lastPhysicsResult.collisionShapePositions()[1], Block.Getter.Condition.TYPE);
            // Calls a method
            var lastBlockBoxes = shape.boundingBoxes();
            // Calls a method
            var currentBlockBoxes = ((ShapeImpl) currentBlock.registry().collisionShape()).boundingBoxes();

            // Fast exit if entity hasn't moved
            // Branch: checks a condition
            if (lastPhysicsResult.collisionY()
                    // Code statement
                    && velocity.y() == lastPhysicsResult.originalDelta().y()
                    // Check block below to fast exit gravity
                    // Code statement
                    && currentBlockBoxes.equals(lastBlockBoxes)
                    // Code statement
                    && velocity.x() == 0 && velocity.z() == 0
                    // Code statement
                    && entityPosition.samePoint(lastPhysicsResult.newPosition())
                    // Start of a method/block
                    && !lastBlockBoxes.isEmpty()) {
                // Branch: checks a condition
                if (lastPhysicsResult.cached()) {
                    // Returns a value to the caller
                    return lastPhysicsResult;
                // Alternative branch of the condition
                } else {
                    // Returns a value to the caller
                    return new PhysicsResult(lastPhysicsResult.newPosition(), lastPhysicsResult.newVelocity(),
                            // Code statement
                            lastPhysicsResult.isOnGround(), lastPhysicsResult.collisionX(), lastPhysicsResult.collisionY(),
                            // Code statement
                            lastPhysicsResult.collisionZ(), lastPhysicsResult.originalDelta(), lastPhysicsResult.collisionPoints(),
                            // Calls a method
                            lastPhysicsResult.collisionShapes(), lastPhysicsResult.collisionShapePositions(), lastPhysicsResult.hasCollision(), lastPhysicsResult.res(), true);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    // Code statement
    private static PhysicsResult stepPhysics(BoundingBox boundingBox,
                                             // Code statement
                                             Vec velocity, Pos entityPosition,
                                             // Start of a method/block
                                             Block.Getter getter, boolean singleCollision) {
        // Calls a method
        final SweepResult finalResult = new SweepResult(1 - Vec.EPSILON, 0, 0, 0, null, 0, 0, 0, 0, 0, 0);

        // Start as the shared (all-null) arrays; only allocate real ones on the first collision.
        // Assigns a value
        Point[] collidedPoints = NO_COLLISION_POINTS;
        // Assigns a value
        Shape[] collisionShapes = NO_COLLISION_SHAPES;
        // Assigns a value
        Point[] collisionShapePositions = NO_COLLISION_SHAPE_POSITIONS;

        // Assigns a value
        Pos position = entityPosition;
        // Assigns a value
        Vec remaining = velocity;
        // Each sweep advances along `remaining` until the first hit, zeroes the
        // collided axis, then repeats so the entity slides along the others.
        // Loop: repeats a block
        while (true) {
            // Calls a method
            sweepBlocks(boundingBox, remaining, position, getter, finalResult);
            // Calls a method
            double dx = finalResult.res * remaining.x();
            // Calls a method
            double dy = finalResult.res * remaining.y();
            // Calls a method
            double dz = finalResult.res * remaining.z();
            // Branch: checks a condition
            if (Math.abs(dx) < Vec.EPSILON) dx = 0;
            // Branch: checks a condition
            if (Math.abs(dy) < Vec.EPSILON) dy = 0;
            // Branch: checks a condition
            if (Math.abs(dz) < Vec.EPSILON) dz = 0;
            // Calls a method
            position = position.add(dx, dy, dz);

            // The slab method records the entry face as a single non-zero normal.
            // Code statement
            final int axis;
            // Branch: checks a condition
            if (finalResult.normalX != 0) axis = 0;
            // Branch: checks a condition
            else if (finalResult.normalY != 0) axis = 1;
            // Branch: checks a condition
            else if (finalResult.normalZ != 0) axis = 2;
            // Alternative branch of the condition
            else break; // no collision this pass

            // Branch: checks a condition
            if (collisionShapes == NO_COLLISION_SHAPES) {
                // Assigns a value
                collidedPoints = new Point[3];
                // Assigns a value
                collisionShapes = new Shape[3];
                // Assigns a value
                collisionShapePositions = new Point[3];
            // End of a block/expression
            }
            // Assigns a value
            collisionShapes[axis] = finalResult.collidedShape;
            // Calls a method
            collisionShapePositions[axis] = new Vec(finalResult.collidedShapeX, finalResult.collidedShapeY, finalResult.collidedShapeZ);
            // Calls a method
            collidedPoints[axis] = new Vec(finalResult.collidedPositionX, finalResult.collidedPositionY, finalResult.collidedPositionZ);

            // Branch: checks a condition
            if (singleCollision || (collisionShapes[0] != null && collisionShapes[1] != null && collisionShapes[2] != null))
                // Breaks out of the loop/block
                break;

            // Assigns a value
            remaining = new Vec(
                    // Code statement
                    axis == 0 ? 0 : remaining.x() - dx,
                    // Code statement
                    axis == 1 ? 0 : remaining.y() - dy,
                    // Calls a method
                    axis == 2 ? 0 : remaining.z() - dz);
            // Branch: checks a condition
            if (remaining.isZero()) break;

            // Assigns a value
            finalResult.normalX = 0;
            // Assigns a value
            finalResult.normalY = 0;
            // Assigns a value
            finalResult.normalZ = 0;
            // Assigns a value
            finalResult.res = 1 - Vec.EPSILON;
        // End of a block/expression
        }

        // Assigns a value
        final boolean foundX = collisionShapes[0] != null;
        // Assigns a value
        final boolean foundY = collisionShapes[1] != null;
        // Assigns a value
        final boolean foundZ = collisionShapes[2] != null;
        // Assigns a value
        final boolean anyCollision = foundX || foundY || foundZ;
        // Assigns a value
        final boolean allCollision = foundX && foundY && foundZ;
        // Code statement
        final Vec newDelta;
        // Branch: checks a condition
        if (!anyCollision) {
            // Assigns a value
            newDelta = velocity;
        // Branch: checks a condition
        } else if (allCollision) {
            // Assigns a value
            newDelta = Vec.ZERO;
        // Alternative branch of the condition
        } else {
            // Calls a method
            newDelta = new Vec(foundX ? 0 : velocity.x(), foundY ? 0 : velocity.y(), foundZ ? 0 : velocity.z());
        // End of a block/expression
        }
        // Returns a value to the caller
        return new PhysicsResult(position, newDelta,
                // Code statement
                foundY && velocity.y() < 0,
                // Code statement
                foundX, foundY, foundZ,
                // Code statement
                velocity, collidedPoints, collisionShapes, collisionShapePositions,
                // Code statement
                anyCollision, finalResult);
    // End of a block/expression
    }

    /**
     * Iterate the blocks overlapping the swept bounding box (start -> start+velocity), near-to-far
     * along the movement so {@code finalResult.res} tightens early and farther blocks are rejected
     * cheaply by the SweepResult distance gate. Each block is visited exactly once.
     */
    // Code statement
    private static void sweepBlocks(BoundingBox boundingBox,
                                    // Code statement
                                    Vec velocity, Pos entityPosition,
                                    // Code statement
                                    Block.Getter getter,
                                    // Start of a method/block
                                    SweepResult finalResult) {
        // Calls a method
        final double startX = entityPosition.x();
        // Calls a method
        final double startY = entityPosition.y();
        // Calls a method
        final double startZ = entityPosition.z();
        // Calls a method
        final double endX = startX + velocity.x();
        // Calls a method
        final double endY = startY + velocity.y();
        // Calls a method
        final double endZ = startZ + velocity.z();

        // Block-aligned bounds of the swept AABB.
        // Calls a method
        final int minX = (int) Math.floor(Math.min(startX, endX) + boundingBox.minX());
        // Calls a method
        final int minY = (int) Math.floor(Math.min(startY, endY) + boundingBox.minY());
        // Calls a method
        final int minZ = (int) Math.floor(Math.min(startZ, endZ) + boundingBox.minZ());
        // Calls a method
        final int maxX = (int) Math.floor(Math.max(startX, endX) + boundingBox.maxX());
        // Calls a method
        final int maxY = (int) Math.floor(Math.max(startY, endY) + boundingBox.maxY());
        // Calls a method
        final int maxZ = (int) Math.floor(Math.max(startZ, endZ) + boundingBox.maxZ());

        // Walk from near to far along velocity.
        // Calls a method
        final int stepX = velocity.x() < 0 ? -1 : 1;
        // Calls a method
        final int stepY = velocity.y() < 0 ? -1 : 1;
        // Calls a method
        final int stepZ = velocity.z() < 0 ? -1 : 1;
        // Assigns a value
        final int firstX = stepX > 0 ? minX : maxX, lastX = stepX > 0 ? maxX : minX;
        // Assigns a value
        final int firstY = stepY > 0 ? minY : maxY, lastY = stepY > 0 ? maxY : minY;
        // Assigns a value
        final int firstZ = stepZ > 0 ? minZ : maxZ, lastZ = stepZ > 0 ? maxZ : minZ;

        // Loop: repeats a block
        for (int x = firstX; x != lastX + stepX; x += stepX) {
            // Loop: repeats a block
            for (int y = firstY; y != lastY + stepY; y += stepY) {
                // Loop: repeats a block
                for (int z = firstZ; z != lastZ + stepZ; z += stepZ) {
                    // Calls a method
                    checkBoundingBox(x, y, z, velocity, entityPosition, boundingBox, getter, finalResult);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Check if a moving entity will collide with a block. Updates finalResult
     *
     * @param blockX         block x position
     * @param blockY         block y position
     * @param blockZ         block z position
     * @param entityVelocity entity movement vector
     * @param entityPosition entity position
     * @param boundingBox    entity bounding box
     * @param getter         block getter
     * @param finalResult    place to store final result of collision
     * @return true if entity finds collision, other false
     */
    // Code statement
    static boolean checkBoundingBox(int blockX, int blockY, int blockZ,
                                    // Code statement
                                    Vec entityVelocity, Pos entityPosition, BoundingBox boundingBox,
                                    // Start of a method/block
                                    Block.Getter getter, SweepResult finalResult) {
        // Don't step if chunk isn't loaded yet
        // Calls a method
        final Block currentBlock = getter.getBlock(blockX, blockY, blockZ, Block.Getter.Condition.TYPE);
        // Calls a method
        final Shape currentShape = currentBlock.registry().collisionShape();

        // Calls a method
        final boolean currentCollidable = !currentShape.relativeEnd().isZero();
        // Calls a method
        final boolean currentShort = currentShape.relativeEnd().y() < 0.5;

        // only consider the block below if our current shape is sufficiently short
        // Branch: checks a condition
        if (currentShort && shouldCheckLower(entityVelocity, entityPosition, blockX, blockY, blockZ)) {
            // we need to check below for a tall block (fence, wall, ...)
            // Calls a method
            final Vec belowPos = new Vec(blockX, blockY - 1, blockZ);
            // Calls a method
            final Block belowBlock = getter.getBlock(belowPos, Block.Getter.Condition.TYPE);
            // Calls a method
            final Shape belowShape = belowBlock.registry().collisionShape();

            // Calls a method
            final Vec currentPos = new Vec(blockX, blockY, blockZ);
            // don't fall out of if statement, we could end up redundantly grabbing a block, and we only need to
            // collision check against the current shape since the below shape isn't tall
            // Branch: checks a condition
            if (belowShape.relativeEnd().y() > 1) {
                // we should always check both shapes, so no short-circuit here, to handle properties where the bounding box
                // hits the current solid but misses the tall solid
                // Returns a value to the caller
                return belowShape.intersectBoxSwept(entityPosition, entityVelocity, belowPos, boundingBox, finalResult) |
                        // Calls a method
                        (currentCollidable && currentShape.intersectBoxSwept(entityPosition, entityVelocity, currentPos, boundingBox, finalResult));
            // Alternative branch of the condition
            } else {
                // Returns a value to the caller
                return currentCollidable && currentShape.intersectBoxSwept(entityPosition, entityVelocity, currentPos, boundingBox, finalResult);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Branch: checks a condition
        if (currentCollidable && currentShape.intersectBoxSwept(entityPosition, entityVelocity,
                // Creates a new object
                new Vec(blockX, blockY, blockZ), boundingBox, finalResult)) {
            // if the current collision is sufficiently short, we might need to collide against the block below too
            // Branch: checks a condition
            if (currentShort) {
                // Calls a method
                final Vec belowPos = new Vec(blockX, blockY - 1, blockZ);
                // Calls a method
                final Block belowBlock = getter.getBlock(belowPos, Block.Getter.Condition.TYPE);
                // Calls a method
                final Shape belowShape = belowBlock.registry().collisionShape();
                // only do sweep if the below block is big enough to possibly hit
                // Branch: checks a condition
                if (belowShape.relativeEnd().y() > 1)
                    // Calls a method
                    belowShape.intersectBoxSwept(entityPosition, entityVelocity, belowPos, boundingBox, finalResult);
            // End of a block/expression
            }
            // Returns a value to the caller
            return true;
        // End of a block/expression
        }
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Start of a method/block
    private static boolean shouldCheckLower(Vec entityVelocity, Pos entityPosition, int blockX, int blockY, int blockZ) {
        // Calls a method
        final double yVelocity = entityVelocity.y();
        // if moving horizontally, just check if the floor of the entity's position is the same as the blockY
        // Branch: checks a condition
        if (yVelocity == 0) return Math.floor(entityPosition.y()) == blockY;
        // Calls a method
        final double xVelocity = entityVelocity.x();
        // Calls a method
        final double zVelocity = entityVelocity.z();
        // if moving straight up, don't bother checking for tall solids beneath anything
        // if moving straight down, only check for a tall solid underneath the last block
        // Branch: checks a condition
        if (xVelocity == 0 && zVelocity == 0)
            // Returns a value to the caller
            return yVelocity < 0 && blockY == Math.floor(entityPosition.y() + yVelocity);
        // default to true: if no x velocity, only consider YZ line, and vice-versa
        // Calls a method
        final boolean underYX = xVelocity != 0 && computeHeight(yVelocity, xVelocity, entityPosition.y(), entityPosition.x(), blockX) >= blockY;
        // Calls a method
        final boolean underYZ = zVelocity != 0 && computeHeight(yVelocity, zVelocity, entityPosition.y(), entityPosition.z(), blockZ) >= blockY;
        // true if the block is at or below the same height as a line drawn from the entity's position to its final
        // destination
        // Returns a value to the caller
        return underYX && underYZ;
    // End of a block/expression
    }

    /*
    computes the height of the entity at the given block position along a projection of the line it's travelling along
    (YX or YZ). the returned value will be greater than or equal to the block height if the block is along the lower
    layer of intersections with this line.
     */
    // Start of a method/block
    private static double computeHeight(double yVelocity, double velocity, double entityY, double pos, int blockPos) {
        // Assigns a value
        final double m = yVelocity / velocity;
        /*
        offsetting by 1 is necessary with a positive slope, because we can clip the bottom-right corner of blocks
        without clipping the "bottom-left" (the smallest corner of the block on the YZ or YX plane). without the offset
        these would not be considered to be on the lowest layer, since our block position represents the bottom-left
        corner
         */
        // Returns a value to the caller
        return m * (blockPos - pos + (m > 0 ? 1 : 0)) + entityY;
    // End of a block/expression
    }
// End of a block/expression
}
