// Déclaration du paquet de ce fichier
package net.minestom.server.collision;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.utils.block.BlockIterator;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
final class BlockCollision {
    /**
     * Moves an entity with physics applied (ie checking against blocks)
     * <p>
     * Works by getting all the full blocks that an entity could interact with.
     * All bounding boxes inside the full blocks are checked for collisions with the entity.
     */
    // Instruction de code
    static PhysicsResult handlePhysics(BoundingBox boundingBox,
                                       // Instruction de code
                                       Vec velocity, Pos entityPosition,
                                       // Instruction de code
                                       Block.Getter getter,
                                       // Annotation pour l'élément suivant
                                       @Nullable PhysicsResult lastPhysicsResult,
                                       // Début d'une méthode/d'un bloc
                                       boolean singleCollision) {
        // Embranchement : vérifie une condition
        if (velocity.isZero()) {
            // TODO should return a constant
            // Renvoie une valeur à l'appelant
            return new PhysicsResult(entityPosition, Vec.ZERO, false, false, false, false,
                    // Instruction de code
                    velocity, new Point[3], new Shape[3], new Point[3], false, SweepResult.NO_COLLISION);
        // Fin d'un bloc/d'une expression
        }
        // Fast-exit using cache
        // Appelle une méthode
        final PhysicsResult cachedResult = cachedPhysics(velocity, entityPosition, getter, lastPhysicsResult);
        // Embranchement : vérifie une condition
        if (cachedResult != null) {
            // Renvoie une valeur à l'appelant
            return cachedResult;
        // Fin d'un bloc/d'une expression
        }
        // Expensive AABB computation
        // Renvoie une valeur à l'appelant
        return stepPhysics(boundingBox, velocity, entityPosition, getter, singleCollision);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Entity canPlaceBlockAt(Instance instance, Point blockPos, Block b) {
        // Boucle : répète un bloc
        for (Entity entity : instance.getNearbyEntities(blockPos, 3)) {
            // Embranchement : vérifie une condition
            if (!entity.preventBlockPlacement())
                // Passe à l'itération suivante de la boucle
                continue;

            // Instruction de code
            final boolean intersects;
            // Embranchement : vérifie une condition
            if (entity instanceof Player) {
                // Need to move player slightly away from block we're placing.
                // If player is at block 40 we cannot place a block at block 39 with side length 1 because the block will be in [39, 40]
                // For this reason we subtract a small amount from the player position
                // Appelle une méthode
                Point playerPos = entity.getPosition().add(entity.getPosition().sub(blockPos).mul(0.0000001));
                // Appelle une méthode
                intersects = b.registry().collisionShape().intersectBox(playerPos.sub(blockPos), entity.getBoundingBox());
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                intersects = b.registry().collisionShape().intersectBox(entity.getPosition().sub(blockPos), entity.getBoundingBox());
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (intersects) return entity;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static PhysicsResult cachedPhysics(Vec velocity, Pos entityPosition,
                                               // Début d'une méthode/d'un bloc
                                               Block.Getter getter, PhysicsResult lastPhysicsResult) {
        // Embranchement : vérifie une condition
        if (lastPhysicsResult != null && lastPhysicsResult.collisionShapes()[1] instanceof ShapeImpl shape) {
            // Appelle une méthode
            var currentBlock = getter.getBlock(lastPhysicsResult.collisionShapePositions()[1], Block.Getter.Condition.TYPE);
            // Appelle une méthode
            var lastBlockBoxes = shape.boundingBoxes();
            // Appelle une méthode
            var currentBlockBoxes = ((ShapeImpl) currentBlock.registry().collisionShape()).boundingBoxes();

            // Fast exit if entity hasn't moved
            // Embranchement : vérifie une condition
            if (lastPhysicsResult.collisionY()
                    // Instruction de code
                    && velocity.y() == lastPhysicsResult.originalDelta().y()
                    // Check block below to fast exit gravity
                    // Instruction de code
                    && currentBlockBoxes.equals(lastBlockBoxes)
                    // Instruction de code
                    && velocity.x() == 0 && velocity.z() == 0
                    // Instruction de code
                    && entityPosition.samePoint(lastPhysicsResult.newPosition())
                    // Début d'une méthode/d'un bloc
                    && !lastBlockBoxes.isEmpty()) {
                // Embranchement : vérifie une condition
                if (lastPhysicsResult.cached()) {
                    // Renvoie une valeur à l'appelant
                    return lastPhysicsResult;
                // Branche alternative de la condition
                } else {
                    // Renvoie une valeur à l'appelant
                    return new PhysicsResult(lastPhysicsResult.newPosition(), lastPhysicsResult.newVelocity(),
                            // Instruction de code
                            lastPhysicsResult.isOnGround(), lastPhysicsResult.collisionX(), lastPhysicsResult.collisionY(),
                            // Instruction de code
                            lastPhysicsResult.collisionZ(), lastPhysicsResult.originalDelta(), lastPhysicsResult.collisionPoints(),
                            // Appelle une méthode
                            lastPhysicsResult.collisionShapes(), lastPhysicsResult.collisionShapePositions(), lastPhysicsResult.hasCollision(), lastPhysicsResult.res(), true);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static PhysicsResult stepPhysics(BoundingBox boundingBox,
                                             // Instruction de code
                                             Vec velocity, Pos entityPosition,
                                             // Début d'une méthode/d'un bloc
                                             Block.Getter getter, boolean singleCollision) {
        // Allocate once and update values
        // Appelle une méthode
        SweepResult finalResult = new SweepResult(1 - Vec.EPSILON, 0, 0, 0, null, 0, 0, 0, 0, 0, 0);

        // Affecte une valeur
        boolean foundCollisionX = false, foundCollisionY = false, foundCollisionZ = false;

        // Affecte une valeur
        Point[] collidedPoints = new Point[3];
        // Affecte une valeur
        Shape[] collisionShapes = new Shape[3];
        // Affecte une valeur
        Point[] collisionShapePositions = new Point[3];

        // Affecte une valeur
        boolean hasCollided = false;

        // Query faces to get the points needed for collision
        // Appelle une méthode
        final Vec[] allFaces = calculateFaces(velocity, boundingBox);
        // Appelle une méthode
        PhysicsResult result = computePhysics(boundingBox, velocity, entityPosition, getter, allFaces, finalResult);
        // Loop until no collisions are found.
        // When collisions are found, the collision axis is set to 0
        // Looping until there are no collisions will allow the entity to move in axis other than the collision axis after a collision.
        // Boucle : répète un bloc
        while (result.collisionX() || result.collisionY() || result.collisionZ()) {
            // Reset final result
            // Affecte une valeur
            finalResult.normalX = 0;
            // Affecte une valeur
            finalResult.normalY = 0;
            // Affecte une valeur
            finalResult.normalZ = 0;

            // Embranchement : vérifie une condition
            if (result.collisionX()) {
                // Affecte une valeur
                foundCollisionX = true;
                // Affecte une valeur
                collisionShapes[0] = finalResult.collidedShape;
                // Appelle une méthode
                collisionShapePositions[0] = new Vec(finalResult.collidedShapeX, finalResult.collidedShapeY, finalResult.collidedShapeZ);
                // Appelle une méthode
                collidedPoints[0] = new Vec(finalResult.collidedPositionX, finalResult.collidedPositionY, finalResult.collidedPositionZ);
                // Affecte une valeur
                hasCollided = true;
                // Embranchement : vérifie une condition
                if (singleCollision) break;
            // Embranchement : vérifie une condition
            } else if (result.collisionZ()) {
                // Affecte une valeur
                foundCollisionZ = true;
                // Affecte une valeur
                collisionShapes[2] = finalResult.collidedShape;
                // Appelle une méthode
                collisionShapePositions[2] = new Vec(finalResult.collidedShapeX, finalResult.collidedShapeY, finalResult.collidedShapeZ);
                // Appelle une méthode
                collidedPoints[2] = new Vec(finalResult.collidedPositionX, finalResult.collidedPositionY, finalResult.collidedPositionZ);
                // Affecte une valeur
                hasCollided = true;
                // Embranchement : vérifie une condition
                if (singleCollision) break;
            // Embranchement : vérifie une condition
            } else if (result.collisionY()) {
                // Affecte une valeur
                foundCollisionY = true;
                // Affecte une valeur
                collisionShapes[1] = finalResult.collidedShape;
                // Appelle une méthode
                collisionShapePositions[1] = new Vec(finalResult.collidedShapeX, finalResult.collidedShapeY, finalResult.collidedShapeZ);
                // Appelle une méthode
                collidedPoints[1] = new Vec(finalResult.collidedPositionX, finalResult.collidedPositionY, finalResult.collidedPositionZ);
                // Affecte une valeur
                hasCollided = true;
                // Embranchement : vérifie une condition
                if (singleCollision) break;
            // Fin d'un bloc/d'une expression
            }

            // If all axis have had collisions, break
            // Embranchement : vérifie une condition
            if (foundCollisionX && foundCollisionY && foundCollisionZ) break;
            // If the entity isn't moving, break
            // Embranchement : vérifie une condition
            if (result.newVelocity().isZero()) break;

            // Affecte une valeur
            finalResult.res = 1 - Vec.EPSILON;
            // Appelle une méthode
            result = computePhysics(boundingBox, result.newVelocity(), result.newPosition(), getter, allFaces, finalResult);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        finalResult.res = result.res().res;

        // Appelle une méthode
        final double newDeltaX = foundCollisionX ? 0 : velocity.x();
        // Appelle une méthode
        final double newDeltaY = foundCollisionY ? 0 : velocity.y();
        // Appelle une méthode
        final double newDeltaZ = foundCollisionZ ? 0 : velocity.z();

        // Renvoie une valeur à l'appelant
        return new PhysicsResult(result.newPosition(), new Vec(newDeltaX, newDeltaY, newDeltaZ),
                // Instruction de code
                newDeltaY == 0 && velocity.y() < 0,
                // Instruction de code
                foundCollisionX, foundCollisionY, foundCollisionZ, velocity, collidedPoints, collisionShapes, collisionShapePositions, hasCollided, finalResult);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static PhysicsResult computePhysics(BoundingBox boundingBox,
                                                // Instruction de code
                                                Vec velocity, Pos entityPosition,
                                                // Instruction de code
                                                Block.Getter getter,
                                                // Instruction de code
                                                Vec[] allFaces,
                                                // Début d'une méthode/d'un bloc
                                                SweepResult finalResult) {
        // If the movement is small we don't need to run the expensive ray casting.
        // Positions of move less than one can have hardcoded blocks to check for every direction
        // Diagonals are a special case which will work with fast physics
        // Embranchement : vérifie une condition
        if (velocity.length() <= 1 || isDiagonal(velocity)) {
            // Appelle une méthode
            fastPhysics(boundingBox, velocity, entityPosition, getter, allFaces, finalResult);
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            slowPhysics(boundingBox, velocity, entityPosition, getter, allFaces, finalResult);
        // Fin d'un bloc/d'une expression
        }

        // Instruction de code
        final boolean collisionX = finalResult.normalX != 0;
        // Instruction de code
        final boolean collisionY = finalResult.normalY != 0;
        // Instruction de code
        final boolean collisionZ = finalResult.normalZ != 0;

        // Boucle : répète un bloc
        double deltaX = finalResult.res * velocity.x();
        // Boucle : répète un bloc
        double deltaY = finalResult.res * velocity.y();
        // Boucle : répète un bloc
        double deltaZ = finalResult.res * velocity.z();

        // Embranchement : vérifie une condition
        if (Math.abs(deltaX) < Vec.EPSILON) deltaX = 0;
        // Embranchement : vérifie une condition
        if (Math.abs(deltaY) < Vec.EPSILON) deltaY = 0;
        // Embranchement : vérifie une condition
        if (Math.abs(deltaZ) < Vec.EPSILON) deltaZ = 0;

        // Appelle une méthode
        final Pos finalPos = entityPosition.add(deltaX, deltaY, deltaZ);

        // Appelle une méthode
        final double remainingX = collisionX ? 0 : velocity.x() - deltaX;
        // Appelle une méthode
        final double remainingY = collisionY ? 0 : velocity.y() - deltaY;
        // Appelle une méthode
        final double remainingZ = collisionZ ? 0 : velocity.z() - deltaZ;

        // Renvoie une valeur à l'appelant
        return new PhysicsResult(finalPos, new Vec(remainingX, remainingY, remainingZ),
                // Instruction de code
                collisionY, collisionX, collisionY, collisionZ,
                // Instruction de code
                Vec.ZERO, null, null, null, false, finalResult);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static boolean isDiagonal(Vec velocity) {
        // Renvoie une valeur à l'appelant
        return Math.abs(velocity.x()) == 1 && Math.abs(velocity.z()) == 1;
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static void slowPhysics(BoundingBox boundingBox,
                                    // Instruction de code
                                    Vec velocity, Pos entityPosition,
                                    // Instruction de code
                                    Block.Getter getter,
                                    // Instruction de code
                                    Vec[] allFaces,
                                    // Début d'une méthode/d'un bloc
                                    SweepResult finalResult) {
        // Appelle une méthode
        BlockIterator iterator = new BlockIterator();
        // When large moves are done we need to ray-cast to find all blocks that could intersect with the movement
        // Boucle : répète un bloc
        for (Vec point : allFaces) {
            // Appelle une méthode
            iterator.reset(point.add(entityPosition), velocity, 0, velocity.length(), false);
            // Affecte une valeur
            int timer = -1;

            // Boucle : répète un bloc
            while (iterator.hasNext() && timer != 0) {
                // Appelle une méthode
                Point p = iterator.next();

                // If we hit a block, there are at most 3 other blocks that could be closer
                // Embranchement : vérifie une condition
                if (checkBoundingBox(p.blockX(), p.blockY(), p.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult))
                    // Affecte une valeur
                    timer = 3;

                // Instruction de code
                timer--;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static void fastPhysics(BoundingBox boundingBox,
                                    // Instruction de code
                                    Vec velocity, Pos entityPosition,
                                    // Instruction de code
                                    Block.Getter getter,
                                    // Instruction de code
                                    Vec[] allFaces,
                                    // Début d'une méthode/d'un bloc
                                    SweepResult finalResult) {
        // Boucle : répète un bloc
        for (Vec point : allFaces) {
            // Appelle une méthode
            final Vec pointBefore = point.add(entityPosition);
            // Appelle une méthode
            final Vec pointAfter = point.add(entityPosition).add(velocity);
            // Entity can pass through up to 4 blocks. Starting block, Two intermediate blocks, and a final block.
            // This means we must check every combination of block movements when an entity moves over an axis.
            // 000, 001, 010, 011, etc.
            // There are 8 of these combinations
            // Checks can be limited by checking if we moved across an axis line

            // Appelle une méthode
            boolean needsX = pointBefore.x() != pointAfter.x();
            // Appelle une méthode
            boolean needsY = pointBefore.y() != pointAfter.y();
            // Appelle une méthode
            boolean needsZ = pointBefore.z() != pointAfter.z();

            // Appelle une méthode
            checkBoundingBox(pointBefore.blockX(), pointBefore.blockY(), pointBefore.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);

            // Embranchement : vérifie une condition
            if (needsX && needsY && needsZ) {
                // Appelle une méthode
                checkBoundingBox(pointAfter.blockX(), pointAfter.blockY(), pointAfter.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);

                // Appelle une méthode
                checkBoundingBox(pointAfter.blockX(), pointAfter.blockY(), pointBefore.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);
                // Appelle une méthode
                checkBoundingBox(pointAfter.blockX(), pointBefore.blockY(), pointAfter.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);
                // Appelle une méthode
                checkBoundingBox(pointBefore.blockX(), pointAfter.blockY(), pointAfter.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);

                // Appelle une méthode
                checkBoundingBox(pointAfter.blockX(), pointBefore.blockY(), pointBefore.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);
                // Appelle une méthode
                checkBoundingBox(pointBefore.blockX(), pointAfter.blockY(), pointBefore.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);
                // Appelle une méthode
                checkBoundingBox(pointBefore.blockX(), pointBefore.blockY(), pointAfter.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);
            // Embranchement : vérifie une condition
            } else if (needsX && needsY) {
                // Appelle une méthode
                checkBoundingBox(pointAfter.blockX(), pointAfter.blockY(), pointBefore.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);

                // Appelle une méthode
                checkBoundingBox(pointAfter.blockX(), pointBefore.blockY(), pointBefore.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);
                // Appelle une méthode
                checkBoundingBox(pointBefore.blockX(), pointAfter.blockY(), pointBefore.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);
            // Embranchement : vérifie une condition
            } else if (needsX && needsZ) {
                // Appelle une méthode
                checkBoundingBox(pointAfter.blockX(), pointBefore.blockY(), pointAfter.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);

                // Appelle une méthode
                checkBoundingBox(pointAfter.blockX(), pointBefore.blockY(), pointBefore.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);
                // Appelle une méthode
                checkBoundingBox(pointBefore.blockX(), pointBefore.blockY(), pointAfter.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);
            // Embranchement : vérifie une condition
            } else if (needsY && needsZ) {
                // Appelle une méthode
                checkBoundingBox(pointBefore.blockX(), pointAfter.blockY(), pointAfter.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);

                // Appelle une méthode
                checkBoundingBox(pointBefore.blockX(), pointAfter.blockY(), pointBefore.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);
                // Appelle une méthode
                checkBoundingBox(pointBefore.blockX(), pointBefore.blockY(), pointAfter.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);
            // Embranchement : vérifie une condition
            } else if (needsX) {
                // Appelle une méthode
                checkBoundingBox(pointAfter.blockX(), pointBefore.blockY(), pointBefore.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);
            // Embranchement : vérifie une condition
            } else if (needsY) {
                // Appelle une méthode
                checkBoundingBox(pointBefore.blockX(), pointAfter.blockY(), pointBefore.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);
            // Embranchement : vérifie une condition
            } else if (needsZ) {
                // Appelle une méthode
                checkBoundingBox(pointBefore.blockX(), pointBefore.blockY(), pointAfter.blockZ(), velocity, entityPosition, boundingBox, getter, finalResult);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    static boolean checkBoundingBox(int blockX, int blockY, int blockZ,
                                    // Instruction de code
                                    Vec entityVelocity, Pos entityPosition, BoundingBox boundingBox,
                                    // Début d'une méthode/d'un bloc
                                    Block.Getter getter, SweepResult finalResult) {
        // Don't step if chunk isn't loaded yet
        // Appelle une méthode
        final Block currentBlock = getter.getBlock(blockX, blockY, blockZ, Block.Getter.Condition.TYPE);
        // Appelle une méthode
        final Shape currentShape = currentBlock.registry().collisionShape();

        // Appelle une méthode
        final boolean currentCollidable = !currentShape.relativeEnd().isZero();
        // Appelle une méthode
        final boolean currentShort = currentShape.relativeEnd().y() < 0.5;

        // only consider the block below if our current shape is sufficiently short
        // Embranchement : vérifie une condition
        if (currentShort && shouldCheckLower(entityVelocity, entityPosition, blockX, blockY, blockZ)) {
            // we need to check below for a tall block (fence, wall, ...)
            // Appelle une méthode
            final Vec belowPos = new Vec(blockX, blockY - 1, blockZ);
            // Appelle une méthode
            final Block belowBlock = getter.getBlock(belowPos, Block.Getter.Condition.TYPE);
            // Appelle une méthode
            final Shape belowShape = belowBlock.registry().collisionShape();

            // Appelle une méthode
            final Vec currentPos = new Vec(blockX, blockY, blockZ);
            // don't fall out of if statement, we could end up redundantly grabbing a block, and we only need to
            // collision check against the current shape since the below shape isn't tall
            // Embranchement : vérifie une condition
            if (belowShape.relativeEnd().y() > 1) {
                // we should always check both shapes, so no short-circuit here, to handle properties where the bounding box
                // hits the current solid but misses the tall solid
                // Renvoie une valeur à l'appelant
                return belowShape.intersectBoxSwept(entityPosition, entityVelocity, belowPos, boundingBox, finalResult) |
                        // Appelle une méthode
                        (currentCollidable && currentShape.intersectBoxSwept(entityPosition, entityVelocity, currentPos, boundingBox, finalResult));
            // Branche alternative de la condition
            } else {
                // Renvoie une valeur à l'appelant
                return currentCollidable && currentShape.intersectBoxSwept(entityPosition, entityVelocity, currentPos, boundingBox, finalResult);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (currentCollidable && currentShape.intersectBoxSwept(entityPosition, entityVelocity,
                // Crée un nouvel objet
                new Vec(blockX, blockY, blockZ), boundingBox, finalResult)) {
            // if the current collision is sufficiently short, we might need to collide against the block below too
            // Embranchement : vérifie une condition
            if (currentShort) {
                // Appelle une méthode
                final Vec belowPos = new Vec(blockX, blockY - 1, blockZ);
                // Appelle une méthode
                final Block belowBlock = getter.getBlock(belowPos, Block.Getter.Condition.TYPE);
                // Appelle une méthode
                final Shape belowShape = belowBlock.registry().collisionShape();
                // only do sweep if the below block is big enough to possibly hit
                // Embranchement : vérifie une condition
                if (belowShape.relativeEnd().y() > 1)
                    // Appelle une méthode
                    belowShape.intersectBoxSwept(entityPosition, entityVelocity, belowPos, boundingBox, finalResult);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return true;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static boolean shouldCheckLower(Vec entityVelocity, Pos entityPosition, int blockX, int blockY, int blockZ) {
        // Appelle une méthode
        final double yVelocity = entityVelocity.y();
        // if moving horizontally, just check if the floor of the entity's position is the same as the blockY
        // Embranchement : vérifie une condition
        if (yVelocity == 0) return Math.floor(entityPosition.y()) == blockY;
        // Appelle une méthode
        final double xVelocity = entityVelocity.x();
        // Appelle une méthode
        final double zVelocity = entityVelocity.z();
        // if moving straight up, don't bother checking for tall solids beneath anything
        // if moving straight down, only check for a tall solid underneath the last block
        // Embranchement : vérifie une condition
        if (xVelocity == 0 && zVelocity == 0)
            // Renvoie une valeur à l'appelant
            return yVelocity < 0 && blockY == Math.floor(entityPosition.y() + yVelocity);
        // default to true: if no x velocity, only consider YZ line, and vice-versa
        // Appelle une méthode
        final boolean underYX = xVelocity != 0 && computeHeight(yVelocity, xVelocity, entityPosition.y(), entityPosition.x(), blockX) >= blockY;
        // Appelle une méthode
        final boolean underYZ = zVelocity != 0 && computeHeight(yVelocity, zVelocity, entityPosition.y(), entityPosition.z(), blockZ) >= blockY;
        // true if the block is at or below the same height as a line drawn from the entity's position to its final
        // destination
        // Renvoie une valeur à l'appelant
        return underYX && underYZ;
    // Fin d'un bloc/d'une expression
    }

    /*
    computes the height of the entity at the given block position along a projection of the line it's travelling along
    (YX or YZ). the returned value will be greater than or equal to the block height if the block is along the lower
    layer of intersections with this line.
     */
    // Début d'une méthode/d'un bloc
    private static double computeHeight(double yVelocity, double velocity, double entityY, double pos, int blockPos) {
        // Affecte une valeur
        final double m = yVelocity / velocity;
        /*
        offsetting by 1 is necessary with a positive slope, because we can clip the bottom-right corner of blocks
        without clipping the "bottom-left" (the smallest corner of the block on the YZ or YX plane). without the offset
        these would not be considered to be on the lowest layer, since our block position represents the bottom-left
        corner
         */
        // Renvoie une valeur à l'appelant
        return m * (blockPos - pos + (m > 0 ? 1 : 0)) + entityY;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Vec[] calculateFaces(Vec queryVec, BoundingBox boundingBox) {
        // Appelle une méthode
        final int queryX = (int) Math.signum(queryVec.x());
        // Appelle une méthode
        final int queryY = (int) Math.signum(queryVec.y());
        // Appelle une méthode
        final int queryZ = (int) Math.signum(queryVec.z());

        // Appelle une méthode
        final int ceilWidth = (int) Math.ceil(boundingBox.width());
        // Appelle une méthode
        final int ceilHeight = (int) Math.ceil(boundingBox.height());
        // Appelle une méthode
        final int ceilDepth = (int) Math.ceil(boundingBox.depth());
        // Instruction de code
        Vec[] facePoints;
        // Compute array length
        // Début d'un bloc
        {
            // Affecte une valeur
            final int ceilX = ceilWidth + 1;
            // Affecte une valeur
            final int ceilY = ceilHeight + 1;
            // Affecte une valeur
            final int ceilZ = ceilDepth + 1;
            // Affecte une valeur
            int pointCount = 0;
            // Embranchement : vérifie une condition
            if (queryX != 0) pointCount += ceilY * ceilZ;
            // Embranchement : vérifie une condition
            if (queryY != 0) pointCount += ceilX * ceilZ;
            // Embranchement : vérifie une condition
            if (queryZ != 0) pointCount += ceilX * ceilY;
            // Three edge reduction
            // Embranchement : vérifie une condition
            if (queryX != 0 && queryY != 0 && queryZ != 0) {
                // Affecte une valeur
                pointCount -= ceilX + ceilY + ceilZ;
                // inclusion exclusion principle
                // Instruction de code
                pointCount++;
            // Embranchement : vérifie une condition
            } else if (queryX != 0 && queryY != 0) { // Two edge reduction
                // Affecte une valeur
                pointCount -= ceilZ;
            // Embranchement : vérifie une condition
            } else if (queryY != 0 && queryZ != 0) { // Two edge reduction
                // Affecte une valeur
                pointCount -= ceilX;
            // Embranchement : vérifie une condition
            } else if (queryX != 0 && queryZ != 0) { // Two edge reduction
                // Affecte une valeur
                pointCount -= ceilY;
            // Fin d'un bloc/d'une expression
            }
            // Affecte une valeur
            facePoints = new Vec[pointCount];
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        int insertIndex = 0;
        // X -> Y x Z
        // Embranchement : vérifie une condition
        if (queryX != 0) {
            // Affecte une valeur
            int startIOffset = 0, endIOffset = 0, startJOffset = 0, endJOffset = 0;
            // Y handles XY edge
            // Embranchement : vérifie une condition
            if (queryY < 0) startJOffset = 1;
            // Embranchement : vérifie une condition
            if (queryY > 0) endJOffset = 1;
            // Z handles XZ edge
            // Embranchement : vérifie une condition
            if (queryZ < 0) startIOffset = 1;
            // Embranchement : vérifie une condition
            if (queryZ > 0) endIOffset = 1;

            // Boucle : répète un bloc
            for (int i = startIOffset; i <= ceilDepth - endIOffset; ++i) {
                // Boucle : répète un bloc
                for (int j = startJOffset; j <= ceilHeight - endJOffset; ++j) {
                    // Boucle : répète un bloc
                    double cellI = i;
                    // Boucle : répète un bloc
                    double cellJ = j;
                    // Boucle : répète un bloc
                    double cellK = queryX < 0 ? 0 : boundingBox.width();

                    // Embranchement : vérifie une condition
                    if (i >= boundingBox.depth()) cellI = boundingBox.depth();
                    // Embranchement : vérifie une condition
                    if (j >= boundingBox.height()) cellJ = boundingBox.height();

                    // Appelle une méthode
                    cellI += boundingBox.minZ();
                    // Appelle une méthode
                    cellJ += boundingBox.minY();
                    // Appelle une méthode
                    cellK += boundingBox.minX();

                    // Appelle une méthode
                    facePoints[insertIndex++] = new Vec(cellK, cellJ, cellI);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Y -> X x Z
        // Embranchement : vérifie une condition
        if (queryY != 0) {
            // Affecte une valeur
            int startJOffset = 0, endJOffset = 0;
            // Z handles YZ edge
            // Embranchement : vérifie une condition
            if (queryZ < 0) startJOffset = 1;
            // Embranchement : vérifie une condition
            if (queryZ > 0) endJOffset = 1;

            // Boucle : répète un bloc
            for (int i = startJOffset; i <= ceilDepth - endJOffset; ++i) {
                // Boucle : répète un bloc
                for (int j = 0; j <= ceilWidth; ++j) {
                    // Boucle : répète un bloc
                    double cellI = i;
                    // Boucle : répète un bloc
                    double cellJ = j;
                    // Boucle : répète un bloc
                    double cellK = queryY < 0 ? 0 : boundingBox.height();

                    // Embranchement : vérifie une condition
                    if (i >= boundingBox.depth()) cellI = boundingBox.depth();
                    // Embranchement : vérifie une condition
                    if (j >= boundingBox.width()) cellJ = boundingBox.width();

                    // Appelle une méthode
                    cellI += boundingBox.minZ();
                    // Appelle une méthode
                    cellJ += boundingBox.minX();
                    // Appelle une méthode
                    cellK += boundingBox.minY();

                    // Appelle une méthode
                    facePoints[insertIndex++] = new Vec(cellJ, cellK, cellI);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Z -> X x Y
        // Embranchement : vérifie une condition
        if (queryZ != 0) {
            // Boucle : répète un bloc
            for (int i = 0; i <= ceilHeight; ++i) {
                // Boucle : répète un bloc
                for (int j = 0; j <= ceilWidth; ++j) {
                    // Boucle : répète un bloc
                    double cellI = i;
                    // Boucle : répète un bloc
                    double cellJ = j;
                    // Boucle : répète un bloc
                    double cellK = queryZ < 0 ? 0 : boundingBox.depth();

                    // Embranchement : vérifie une condition
                    if (i >= boundingBox.height()) cellI = boundingBox.height();
                    // Embranchement : vérifie une condition
                    if (j >= boundingBox.width()) cellJ = boundingBox.width();

                    // Appelle une méthode
                    cellI += boundingBox.minY();
                    // Appelle une méthode
                    cellJ += boundingBox.minX();
                    // Appelle une méthode
                    cellK += boundingBox.minZ();

                    // Appelle une méthode
                    facePoints[insertIndex++] = new Vec(cellJ, cellI, cellK);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return facePoints;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
