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
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.WorldBorder;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.utils.chunk.ChunkCache;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.function.Function;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class CollisionUtils {

    /**
     * Moves an entity with physics applied (ie checking against blocks)
     * <p>
     * Works by getting all the full blocks that an entity could interact with.
     * All bounding boxes inside the full blocks are checked for collisions with the entity.
     *
     * @param entity            the entity to move
     * @param entityVelocity    the velocity of the entity
     * @param lastPhysicsResult the last physics result, can be null
     * @param singleCollision   if the entity should only collide with one block
     * @return the result of physics simulation
     */
    // Code statement
    public static PhysicsResult handlePhysics(Entity entity, Vec entityVelocity,
                                              // Annotation for the following element
                                              @Nullable PhysicsResult lastPhysicsResult, boolean singleCollision) {
        // Calls a method
        final Instance instance = entity.getInstance();
        // Code statement
        assert instance != null;
        // Returns a value to the caller
        return handlePhysics(instance, entity.getChunk(),
                // Code statement
                entity.getBoundingBox(),
                // Code statement
                entity.getPosition(), entityVelocity,
                // Code statement
                lastPhysicsResult, singleCollision);
    // End of a block/expression
    }

    /**
     * Checks for entity collisions
     *
     * @param velocity     the velocity of the entity
     * @param extendRadius the largest entity bounding box we can collide with
     *                     Measured from bottom center to top corner
     *                     This is used to extend the search radius for entities we collide with
     *                     For players this is (0.3^2 + 0.3^2 + 1.8^2) ^ (1/3) ~= 1.51
     */
    // Start of a method/block
    public static Collection<EntityCollisionResult> checkEntityCollisions(Instance instance, BoundingBox boundingBox, Point pos, Vec velocity, double extendRadius, Function<Entity, Boolean> entityFilter, @Nullable PhysicsResult physicsResult) {
        // Returns a value to the caller
        return EntityCollision.checkCollision(instance, boundingBox, pos, velocity, extendRadius, entityFilter, physicsResult);
    // End of a block/expression
    }

    /**
     * Checks for entity collisions
     *
     * @param entity        the entity to check collisions for
     * @param velocity      the velocity of the entity
     * @param extendRadius  the largest entity bounding box we can collide with
     * @param entityFilter  the entity filter
     * @param physicsResult optional physics result
     * @return the entity collision results
     */
    // Start of a method/block
    public static Collection<EntityCollisionResult> checkEntityCollisions(Entity entity, Vec velocity, double extendRadius, Function<Entity, Boolean> entityFilter, @Nullable PhysicsResult physicsResult) {
        // Returns a value to the caller
        return EntityCollision.checkCollision(entity.getInstance(), entity.getBoundingBox(), entity.getPosition(), velocity, extendRadius, entityFilter, physicsResult);
    // End of a block/expression
    }

    /**
     * Moves an entity with physics applied (ie checking against blocks)
     * <p>
     * Works by getting all the full blocks that an entity could interact with.
     * All bounding boxes inside the full blocks are checked for collisions with the entity.
     *
     * @param entity            the entity to move
     * @param entityVelocity    the velocity of the entity
     * @param lastPhysicsResult the last physics result, can be null
     * @return the result of physics simulation
     */
    // Code statement
    public static PhysicsResult handlePhysics(Entity entity, Vec entityVelocity,
                                              // Annotation for the following element
                                              @Nullable PhysicsResult lastPhysicsResult) {
        // Calls a method
        final Instance instance = entity.getInstance();
        // Code statement
        assert instance != null;
        // Returns a value to the caller
        return handlePhysics(instance, entity.getChunk(),
                // Code statement
                entity.getBoundingBox(),
                // Code statement
                entity.getPosition(), entityVelocity,
                // Code statement
                lastPhysicsResult, false);
    // End of a block/expression
    }

    /**
     * Moves bounding box with physics applied (ie checking against blocks)
     * <p>
     * Works by getting all the full blocks that a bounding box could interact with.
     * All bounding boxes inside the full blocks are checked for collisions with the given bounding box.
     *
     * @param boundingBox the bounding box to move
     * @return the result of physics simulation
     */
    // Code statement
    public static PhysicsResult handlePhysics(Instance instance, @Nullable Chunk chunk,
                                              // Code statement
                                              BoundingBox boundingBox,
                                              // Code statement
                                              Pos position, Vec velocity,
                                              // Annotation for the following element
                                              @Nullable PhysicsResult lastPhysicsResult, boolean singleCollision) {
        // Calls a method
        final Block.Getter getter = new ChunkCache(instance, chunk != null ? chunk : instance.getChunkAt(position), Block.STONE);
        // Returns a value to the caller
        return handlePhysics(getter, boundingBox, position, velocity, lastPhysicsResult, singleCollision);
    // End of a block/expression
    }

    /**
     * Moves bounding box with physics applied (ie checking against blocks)
     * <p>
     * Works by getting all the full blocks that a bounding box could interact with.
     * All bounding boxes inside the full blocks are checked for collisions with the given bounding box.
     *
     * @param blockGetter the block getter to check collisions against, ensure block access is synchronized
     * @return the result of physics simulation
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Code statement
    public static PhysicsResult handlePhysics(Block.Getter blockGetter,
                                              // Code statement
                                              BoundingBox boundingBox,
                                              // Code statement
                                              Pos position, Vec velocity,
                                              // Annotation for the following element
                                              @Nullable PhysicsResult lastPhysicsResult, boolean singleCollision) {
        // Returns a value to the caller
        return BlockCollision.handlePhysics(boundingBox,
                // Code statement
                velocity, position,
                // Code statement
                blockGetter, lastPhysicsResult, singleCollision);
    // End of a block/expression
    }

    /**
     * Checks whether shape is reachable by the given line of sight
     * (ie there are no blocks colliding with it).
     *
     * @param instance the instance.
     * @param chunk    optional chunk reference for speedup purposes.
     * @param start    start of the line of sight.
     * @param end      end of the line of sight.
     * @param shape    shape to check.
     * @param shapePos position of the shape to check.
     * @return true is shape is reachable by the given line of sight; false otherwise.
     */
    // Code statement
    public static boolean isLineOfSightReachingShape(Instance instance, @Nullable Chunk chunk,
                                                     // Code statement
                                                     Point start, Point end,
                                                     // Start of a method/block
                                                     Shape shape, Point shapePos) {
        // Assigns a value
        final PhysicsResult result = handlePhysics(instance, chunk,
                // Code statement
                BoundingBox.ZERO, start.asPos(), end.sub(start).asVec(),
                // Code statement
                null, false);

        // Returns a value to the caller
        return shape.intersectBox(shapePos.sub(result.newPosition()).sub(Vec.EPSILON), BoundingBox.ZERO);
    // End of a block/expression
    }

    // Start of a method/block
    public static PhysicsResult handlePhysics(Entity entity, Vec entityVelocity) {
        // Returns a value to the caller
        return handlePhysics(entity, entityVelocity, null);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entity canPlaceBlockAt(Instance instance, Point blockPos, Block b) {
        // Returns a value to the caller
        return BlockCollision.canPlaceBlockAt(instance, blockPos, b);
    // End of a block/expression
    }

    /**
     * Applies world border collision.
     *
     * @param worldBorder     the world border
     * @param currentPosition the current position
     * @param newPosition     the future target position
     * @return the position with the world border collision applied (can be {@code newPosition} if not changed)
     */
    // Start of a method/block
    public static Pos applyWorldBorder(WorldBorder worldBorder, Pos currentPosition, Pos newPosition) {
        // Calls a method
        double radius = worldBorder.diameter() / 2;
        // If there is a collision on a given axis prevent the entity
        // from moving forward by supplying their previous position's value
        // Calls a method
        boolean xCollision = newPosition.x() > worldBorder.centerX() + radius || newPosition.x() < worldBorder.centerX() - radius;
        // Calls a method
        boolean zCollision = newPosition.z() > worldBorder.centerZ() + radius || newPosition.z() < worldBorder.centerZ() - radius;
        // Branch: checks a condition
        if (xCollision || zCollision) {
            // Returns a value to the caller
            return newPosition.withCoord(xCollision ? currentPosition.x() : newPosition.x(), newPosition.y(),
                    // Calls a method
                    zCollision ? currentPosition.z() : newPosition.z());
        // End of a block/expression
        }
        // Returns a value to the caller
        return newPosition;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static Shape parseCollisionShape(Map<Object, Object> internCache, String shape) {
        // Calls a method
        final Shape cachedShape = (Shape) internCache.get(shape);
        // Branch: checks a condition
        if (cachedShape != null) return cachedShape;
        // Calls a method
        final Shape parsedShape = ShapeImpl.parseShapeFromRegistry(shape, (byte) 0);
        // Calls a method
        internCache.put(shape, parsedShape);
        // Returns a value to the caller
        return (Shape) internCache.computeIfAbsent(parsedShape, k -> parsedShape);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static Shape parseOcclusionShape(Map<Object, Object> internCache, String shape, boolean occludes, byte lightEmission) {
        // Type declaration (class/interface/enum/record)
        record ShapeEntry(String shape, boolean occludes, byte lightEmission) {} // Easy way to Hashcode
        // Calls a method
        ShapeEntry entry = new ShapeEntry(shape, occludes, lightEmission);
        // Calls a method
        final Shape cachedShape = (Shape) internCache.get(entry);
        // Branch: checks a condition
        if (cachedShape != null) return cachedShape;
        // Calls a method
        final Shape parsedShape = occludes ? ShapeImpl.parseShapeFromRegistry(shape, lightEmission) : ShapeImpl.emptyShape(lightEmission);
        // Calls a method
        internCache.put(entry, parsedShape);
        // Returns a value to the caller
        return (Shape) internCache.computeIfAbsent(parsedShape, k -> parsedShape);
    // End of a block/expression
    }

    /**
     * Simulate the entity's collision physics as if the world had no blocks
     *
     * @param entityPosition the position of the entity
     * @param entityVelocity the velocity of the entity
     * @return the result of physics simulation
     */
    // Start of a method/block
    public static PhysicsResult blocklessCollision(Pos entityPosition, Vec entityVelocity) {
        // Returns a value to the caller
        return new PhysicsResult(entityPosition.add(entityVelocity), entityVelocity, false,
                // Code statement
                false, false, false, entityVelocity, BlockCollision.NO_COLLISION_POINTS,
                // Code statement
                BlockCollision.NO_COLLISION_SHAPES, BlockCollision.NO_COLLISION_SHAPE_POSITIONS, false, SweepResult.NO_COLLISION);
    // End of a block/expression
    }
// End of a block/expression
}
