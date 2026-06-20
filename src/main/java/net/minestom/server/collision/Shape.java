// Déclaration du paquet de ce fichier
package net.minestom.server.collision;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;

// Déclaration de type (classe/interface/enum/record)
public interface Shape {
    // Appelle une méthode
    boolean isOccluded(Shape shape, BlockFace face);

    /**
     * Returns true if the given block face is completely covered by this shape, false otherwise.
     * @param face The face to test
     */
    // Début d'une méthode/d'un bloc
    default boolean isFaceFull(BlockFace face) {
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if two bounding boxes intersect.
     *
     * @param positionRelative Relative position of bounding box to check with
     * @param boundingBox      Bounding box to check for intersections with
     * @return is an intersection found
     */
    // Appelle une méthode
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
    // Instruction de code
    boolean intersectBoxSwept(Point rayStart, Point rayDirection,
                              // Instruction de code
                              Point shapePos, BoundingBox moving, SweepResult finalResult);


    /**
     * Used to know if this {@link BoundingBox} intersects with the bounding box of an entity.
     *
     * @param entity the entity to check the bounding box
     * @return true if this bounding box intersects with the entity, false otherwise
     */
    // Début d'une méthode/d'un bloc
    default boolean intersectEntity(Point src, Entity entity) {
        // Renvoie une valeur à l'appelant
        return intersectBox(src.sub(entity.getPosition()), entity.getBoundingBox());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Relative Start
     *
     * @return Start of shape
     */
    // Appelle une méthode
    Point relativeStart();

    /**
     * Relative End
     *
     * @return End of shape
     */
    // Appelle une méthode
    Point relativeEnd();
// Fin d'un bloc/d'une expression
}
