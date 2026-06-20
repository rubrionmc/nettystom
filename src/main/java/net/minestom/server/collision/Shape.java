// Package declaration for this file
package net.minestom.server.collision;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;

// Type declaration (class/interface/enum/record)
public interface Shape {
    // Calls a method
    boolean isOccluded(Shape shape, BlockFace face);

    /**
     * Returns true if the given block face is completely covered by this shape, false otherwise.
     * @param face The face to test
     */
    // Start of a method/block
    default boolean isFaceFull(BlockFace face) {
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    /**
     * Checks if two bounding boxes intersect.
     *
     * @param positionRelative Relative position of bounding box to check with
     * @param boundingBox      Bounding box to check for intersections with
     * @return is an intersection found
     */
    // Calls a method
    boolean intersectBox(Point positionRelative, BoundingBox boundingBox);

    /**
     * Checks if a moving bounding box will hit this shape.
     *
     * @param rayStart     Position of the moving shape
     * @param rayDirection Movement vector
     * @param shapePos     Position of this shape
     * @param moving       Bounding Box of moving shape
     * @param finalResult  Stores final SweepResult
     * @return is an intersection found
     */
    // Code statement
    boolean intersectBoxSwept(Point rayStart, Point rayDirection,
                              // Code statement
                              Point shapePos, BoundingBox moving, SweepResult finalResult);


    /**
     * Used to know if this {@link BoundingBox} intersects with the bounding box of an entity.
     *
     * @param entity the entity to check the bounding box
     * @return true if this bounding box intersects with the entity, false otherwise
     */
    // Start of a method/block
    default boolean intersectEntity(Point src, Entity entity) {
        // Returns a value to the caller
        return intersectBox(src.sub(entity.getPosition()), entity.getBoundingBox());
    // End of a block/expression
    }

    /**
     * Relative Start
     *
     * @return Start of shape
     */
    // Calls a method
    Point relativeStart();

    /**
     * Relative End
     *
     * @return End of shape
     */
    // Calls a method
    Point relativeEnd();
// End of a block/expression
}
