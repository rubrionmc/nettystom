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
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.WorldBorder;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.utils.chunk.ChunkCache;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.function.Function;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
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
    // Instruction de code
    public static PhysicsResult handlePhysics(Entity entity, Vec entityVelocity,
                                              // Annotation pour l'élément suivant
                                              @Nullable PhysicsResult lastPhysicsResult, boolean singleCollision) {
        // Appelle une méthode
        final Instance instance = entity.getInstance();
        // Instruction de code
        assert instance != null;
        // Renvoie une valeur à l'appelant
        return handlePhysics(instance, entity.getChunk(),
                // Instruction de code
                entity.getBoundingBox(),
                // Instruction de code
                entity.getPosition(), entityVelocity,
                // Instruction de code
                lastPhysicsResult, singleCollision);
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public static Collection<EntityCollisionResult> checkEntityCollisions(Instance instance, BoundingBox boundingBox, Point pos, Vec velocity, double extendRadius, Function<Entity, Boolean> entityFilter, @Nullable PhysicsResult physicsResult) {
        // Renvoie une valeur à l'appelant
        return EntityCollision.checkCollision(instance, boundingBox, pos, velocity, extendRadius, entityFilter, physicsResult);
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public static Collection<EntityCollisionResult> checkEntityCollisions(Entity entity, Vec velocity, double extendRadius, Function<Entity, Boolean> entityFilter, @Nullable PhysicsResult physicsResult) {
        // Renvoie une valeur à l'appelant
        return EntityCollision.checkCollision(entity.getInstance(), entity.getBoundingBox(), entity.getPosition(), velocity, extendRadius, entityFilter, physicsResult);
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    public static PhysicsResult handlePhysics(Entity entity, Vec entityVelocity,
                                              // Annotation pour l'élément suivant
                                              @Nullable PhysicsResult lastPhysicsResult) {
        // Appelle une méthode
        final Instance instance = entity.getInstance();
        // Instruction de code
        assert instance != null;
        // Renvoie une valeur à l'appelant
        return handlePhysics(instance, entity.getChunk(),
                // Instruction de code
                entity.getBoundingBox(),
                // Instruction de code
                entity.getPosition(), entityVelocity,
                // Instruction de code
                lastPhysicsResult, false);
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    public static PhysicsResult handlePhysics(Instance instance, @Nullable Chunk chunk,
                                              // Instruction de code
                                              BoundingBox boundingBox,
                                              // Instruction de code
                                              Pos position, Vec velocity,
                                              // Annotation pour l'élément suivant
                                              @Nullable PhysicsResult lastPhysicsResult, boolean singleCollision) {
        // Appelle une méthode
        final Block.Getter getter = new ChunkCache(instance, chunk != null ? chunk : instance.getChunkAt(position), Block.STONE);
        // Renvoie une valeur à l'appelant
        return handlePhysics(getter, boundingBox, position, velocity, lastPhysicsResult, singleCollision);
    // Fin d'un bloc/d'une expression
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
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Instruction de code
    public static PhysicsResult handlePhysics(Block.Getter blockGetter,
                                              // Instruction de code
                                              BoundingBox boundingBox,
                                              // Instruction de code
                                              Pos position, Vec velocity,
                                              // Annotation pour l'élément suivant
                                              @Nullable PhysicsResult lastPhysicsResult, boolean singleCollision) {
        // Renvoie une valeur à l'appelant
        return BlockCollision.handlePhysics(boundingBox,
                // Instruction de code
                velocity, position,
                // Instruction de code
                blockGetter, lastPhysicsResult, singleCollision);
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    public static boolean isLineOfSightReachingShape(Instance instance, @Nullable Chunk chunk,
                                                     // Instruction de code
                                                     Point start, Point end,
                                                     // Début d'une méthode/d'un bloc
                                                     Shape shape, Point shapePos) {
        // Affecte une valeur
        final PhysicsResult result = handlePhysics(instance, chunk,
                // Instruction de code
                BoundingBox.ZERO, start.asPos(), end.sub(start).asVec(),
                // Instruction de code
                null, false);

        // Renvoie une valeur à l'appelant
        return shape.intersectBox(shapePos.sub(result.newPosition()).sub(Vec.EPSILON), BoundingBox.ZERO);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static PhysicsResult handlePhysics(Entity entity, Vec entityVelocity) {
        // Renvoie une valeur à l'appelant
        return handlePhysics(entity, entityVelocity, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entity canPlaceBlockAt(Instance instance, Point blockPos, Block b) {
        // Renvoie une valeur à l'appelant
        return BlockCollision.canPlaceBlockAt(instance, blockPos, b);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies world border collision.
     *
     * @param worldBorder     the world border
     * @param currentPosition the current position
     * @param newPosition     the future target position
     * @return the position with the world border collision applied (can be {@code newPosition} if not changed)
     */
    // Début d'une méthode/d'un bloc
    public static Pos applyWorldBorder(WorldBorder worldBorder, Pos currentPosition, Pos newPosition) {
        // Appelle une méthode
        double radius = worldBorder.diameter() / 2;
        // If there is a collision on a given axis prevent the entity
        // from moving forward by supplying their previous position's value
        // Appelle une méthode
        boolean xCollision = newPosition.x() > worldBorder.centerX() + radius || newPosition.x() < worldBorder.centerX() - radius;
        // Appelle une méthode
        boolean zCollision = newPosition.z() > worldBorder.centerZ() + radius || newPosition.z() < worldBorder.centerZ() - radius;
        // Embranchement : vérifie une condition
        if (xCollision || zCollision) {
            // Renvoie une valeur à l'appelant
            return newPosition.withCoord(xCollision ? currentPosition.x() : newPosition.x(), newPosition.y(),
                    // Appelle une méthode
                    zCollision ? currentPosition.z() : newPosition.z());
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return newPosition;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static Shape parseCollisionShape(Map<Object, Object> internCache, String shape) {
        // Appelle une méthode
        final Shape cachedShape = (Shape) internCache.get(shape);
        // Embranchement : vérifie une condition
        if (cachedShape != null) return cachedShape;
        // Appelle une méthode
        final Shape parsedShape = ShapeImpl.parseShapeFromRegistry(shape, (byte) 0);
        // Appelle une méthode
        internCache.put(shape, parsedShape);
        // Renvoie une valeur à l'appelant
        return (Shape) internCache.computeIfAbsent(parsedShape, k -> parsedShape);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static Shape parseOcclusionShape(Map<Object, Object> internCache, String shape, boolean occludes, byte lightEmission) {
        // Déclaration de type (classe/interface/enum/record)
        record ShapeEntry(String shape, boolean occludes, byte lightEmission) {} // Easy way to Hashcode
        // Appelle une méthode
        ShapeEntry entry = new ShapeEntry(shape, occludes, lightEmission);
        // Appelle une méthode
        final Shape cachedShape = (Shape) internCache.get(entry);
        // Embranchement : vérifie une condition
        if (cachedShape != null) return cachedShape;
        // Appelle une méthode
        final Shape parsedShape = occludes ? ShapeImpl.parseShapeFromRegistry(shape, lightEmission) : ShapeImpl.emptyShape(lightEmission);
        // Appelle une méthode
        internCache.put(entry, parsedShape);
        // Renvoie une valeur à l'appelant
        return (Shape) internCache.computeIfAbsent(parsedShape, k -> parsedShape);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Simulate the entity's collision physics as if the world had no blocks
     *
     * @param entityPosition the position of the entity
     * @param entityVelocity the velocity of the entity
     * @return the result of physics simulation
     */
    // Début d'une méthode/d'un bloc
    public static PhysicsResult blocklessCollision(Pos entityPosition, Vec entityVelocity) {
        // Renvoie une valeur à l'appelant
        return new PhysicsResult(entityPosition.add(entityVelocity), entityVelocity, false,
                // Instruction de code
                false, false, false, entityVelocity, new Point[3],
                // Crée un nouvel objet
                new Shape[3], new Point[3], false, SweepResult.NO_COLLISION);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
