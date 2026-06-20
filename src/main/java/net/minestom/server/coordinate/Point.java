// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.utils.Direction;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;

// Import d'une classe nécessaire
import java.util.function.DoubleUnaryOperator;

// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.*;

/**
 * Represents a 3D point in coordinate space.
 * <p>
 * This interface has three main implementations:
 * <ul>
 *   <li>{@link Vec} - Double-precision coordinates (x, y, z)</li>
 *   <li>{@link Pos} - Double-precision coordinates with view angles (yaw, pitch)</li>
 *   <li>{@link BlockVec} - Integer block-aligned coordinates</li>
 * </ul>
 * <p>
 * <b>Coordinate Scale:</b>
 * <ul>
 *   <li>Block: Individual voxel position (1 block)</li>
 *   <li>Section: 16 blocks ({@link #SECTION_SIZE})</li>
 *   <li>Chunk: Same as a section in X and Z axis ({@link #SECTION_SIZE})</li>
 *   <li>Region: 512 blocks or 32 sections ({@link #REGION_SIZE})</li>
 * </ul>
 * <p>
 * <b>Coordinate Conventions:</b>
 * <ul>
 *   <li>Three {@code double} values represent global coordinates</li>
 *   <li>Three {@code double} values following two {@code float} values represent global position coordinates</li>
 *   <li>Three {@code int} values represent global block coordinates</li>
 * </ul>
 * <p>
 * <b>Directionality:</b>
 * <ul>
 *     <li>X increases towards East, decreases towards West</li>
 *     <li>Y increases upwards, decreases downwards</li>
 *     <li>Z increases towards South, decreases towards North</li>
 * </ul>
 * <p>
 * Avoid relying on {@link Object#equals(Object)} for direct Point comparison, as different implementations
 * may represent the same 3D coordinates but be different instances. Use {@link #samePoint(Point)}
 * or {@link #samePoint(Point, double)} instead. You can also ensure both points are of the same implementation, but this is fragile.
 * <p>
 * Usage: Prefer accepting {@link Point} in method parameters when only
 * coordinate access (x/y/z) is needed. This avoids forcing callers to convert
 * between specific implementations.
 * <p>
 * All implementations are immutable and subject to become value types. Type conversions are also explicit to avoid precision loss.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface Point permits Vec, Pos, BlockVec {
    /**
     * The smallest difference between two double values to consider them equal if applicable.
     */
    // Affecte une valeur
    double EPSILON = 1e-6;

    /**
     * Represents the size of a section (16 blocks).
     * <p>
     * Also known as chunk in X and Z axis.
     */
    // Affecte une valeur
    int SECTION_SIZE = 16;

    /**
     * Represents the size of a region (32 sections) or (512 blocks).
     * Used in Anvil (.mca) region files.
     * <p>
     * Regions do not normally have a Y component.
     */
    // Affecte une valeur
    int REGION_SIZE = 32 * SECTION_SIZE;

    /**
     * Gets the X coordinate.
     *
     * @return the X coordinate
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    double x();

    /**
     * Gets the Y coordinate.
     *
     * @return the Y coordinate
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    double y();

    /**
     * Gets the Z coordinate.
     *
     * @return the Z coordinate
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    double z();

    /**
     * Gets the floored value of the X component
     *
     * @return the block X
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default int blockX() {
        // Renvoie une valeur à l'appelant
        return globalToBlock(x());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the floored value of the Y component
     *
     * @return the block Y
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default int blockY() {
        // Renvoie une valeur à l'appelant
        return globalToBlock(y());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the floored value of the Z component
     *
     * @return the block Z
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default int blockZ() {
        // Renvoie une valeur à l'appelant
        return globalToBlock(z());
    // Fin d'un bloc/d'une expression
    }

    /**
     * The section x coordinate of {@link #blockX()}, determined by {@link #SECTION_SIZE}
     *
     * @return the section x coordinate
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default int sectionX() {
        // Renvoie une valeur à l'appelant
        return globalToSection(blockX());
    // Fin d'un bloc/d'une expression
    }

    /**
     * The section y coordinate of {@link #blockY()}, determined by {@link #SECTION_SIZE}
     *
     * @return the section y coordinate
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default int sectionY() {
        // Renvoie une valeur à l'appelant
        return globalToSection(blockY());
    // Fin d'un bloc/d'une expression
    }

    /**
     * The section z coordinate of {@link #blockZ()}, determined by {@link #SECTION_SIZE}
     *
     * @return the section z coordinate
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default int sectionZ() {
        // Renvoie une valeur à l'appelant
        return globalToSection(blockZ());
    // Fin d'un bloc/d'une expression
    }

    /**
     * The chunk X coordinate of {@link #blockX()}, also known as the section X {@link #sectionX()}
     *
     * @return the chunk X coordinate
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default int chunkX() {
        // Renvoie une valeur à l'appelant
        return sectionX();
    // Fin d'un bloc/d'une expression
    }

    /**
     * The chunk Z coordinate of {@link #blockZ()}, also known as the section Z {@link #sectionZ()}
     *
     * @return the chunk Z coordinate
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default int chunkZ() {
        // Renvoie une valeur à l'appelant
        return sectionZ();
    // Fin d'un bloc/d'une expression
    }

    /**
     * The region x coordinate of {@link #blockX()}, determined by {@link #REGION_SIZE}
     *
     * @return the region x coordinate
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default int regionX() {
        // Renvoie une valeur à l'appelant
        return globalToRegion(blockX());
    // Fin d'un bloc/d'une expression
    }

    /**
     * The region z coordinate of {@link #blockZ()}, determined by {@link #REGION_SIZE}
     *
     * @return the region z coordinate
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default int regionZ() {
        // Renvoie une valeur à l'appelant
        return globalToRegion(blockZ());
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link #sectionY()} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default int section() {
        // Renvoie une valeur à l'appelant
        return sectionY();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a point with a modified X coordinate based on its value.
     *
     * @param operator the operator providing the current X coordinate and returning the new
     * @return a new point
     */
    // Annotation pour l'élément suivant
    @Contract("_ -> new")
    // Appelle une méthode
    Point withX(DoubleUnaryOperator operator);

    /**
     * Creates a point with the specified X coordinate.
     *
     * @param x the new X coordinate
     * @return a new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Appelle une méthode
    Point withX(double x);

    /**
     * Creates a point with a modified Y coordinate based on its value.
     *
     * @param operator the operator providing the current Y coordinate and returning the new
     * @return a new point
     */
    // Annotation pour l'élément suivant
    @Contract("_ -> new")
    // Appelle une méthode
    Point withY(DoubleUnaryOperator operator);

    /**
     * Creates a point with the specified Y coordinate.
     *
     * @param y the new Y coordinate
     * @return a new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Appelle une méthode
    Point withY(double y);

    /**
     * Creates a point with a modified Z coordinate based on its value.
     *
     * @param operator the operator providing the current Z coordinate and returning the new
     * @return a new point
     */
    // Annotation pour l'élément suivant
    @Contract("_ -> new")
    // Appelle une méthode
    Point withZ(DoubleUnaryOperator operator);

    /**
     * Creates a point with the specified Z coordinate.
     *
     * @param z the new Z coordinate
     * @return a new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Appelle une méthode
    Point withZ(double z);

    /**
     * Creates a new point by adding the provided values to this point coordinates.
     *
     * @param x the x to add
     * @param y the y to add
     * @param z the z to add
     * @return the new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Appelle une méthode
    Point add(double x, double y, double z);

    /**
     * Creates a new point by adding another point coordinates to this point coordinates.
     *
     * @param point the point decomposed by {@link #x()}, {@link #y()} and {@link #z()}
     * @return the new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Appelle une méthode
    Point add(Point point);

    /**
     * Creates a new point by adding the provided value to this point coordinates for all XYZ.
     *
     * @param value the value to add
     * @return the new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Appelle une méthode
    Point add(double value);

    /**
     * Creates a new point by subtracting the provided values to this point coordinates.
     *
     * @param x the x to subtract
     * @param y the y to subtract
     * @param z the z to subtract
     * @return the new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Appelle une méthode
    Point sub(double x, double y, double z);

    /**
     * Creates a new point by subtracting another point coordinates to this point coordinates.
     *
     * @param point the point decomposed by {@link #x()}, {@link #y()} and {@link #z()}
     * @return the new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Appelle une méthode
    Point sub(Point point);

    /**
     * Creates a new point by subtracting the provided value to this point coordinates for all XYZ.
     *
     * @param value the value to subtract
     * @return the new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Appelle une méthode
    Point sub(double value);

    /**
     * Creates a new point by multiplying the provided values to this point coordinates.
     *
     * @param x the x to multiply
     * @param y the y to multiply
     * @param z the z to multiply
     * @return the new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Appelle une méthode
    Point mul(double x, double y, double z);

    /**
     * Creates a new point by multiplying another point coordinates to this point coordinates.
     *
     * @param point the point decomposed by {@link #x()}, {@link #y()} and {@link #z()}
     * @return the new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Appelle une méthode
    Point mul(Point point);

    /**
     * Creates a new point by multiplying the provided value to this point coordinates for all XYZ.
     *
     * @param value the value to multiply
     * @return the new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Appelle une méthode
    Point mul(double value);

    /**
     * Creates a new point by dividing the provided values to this point coordinates.
     * <p>
     * Warning: division by zero will not error.
     *
     * @param x the x to divide
     * @param y the y to divide
     * @param z the z to divide
     * @return the new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Appelle une méthode
    Point div(double x, double y, double z);

    /**
     * Creates a new point by dividing another point coordinates to this point coordinates.
     * <p>
     * Warning: division by zero will not error.
     *
     * @param point the point decomposed by {@link #x()}, {@link #y()} and {@link #z()}
     * @return the new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Appelle une méthode
    Point div(Point point);

    /**
     * Creates a new point by dividing the provided value to this point coordinates for all XYZ.
     * <p>
     * Warning: division by zero will not error.
     *
     * @param value the value to divide
     * @return the new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Appelle une méthode
    Point div(double value);

    /**
     * Creates a new point relative to this point based on the provided block face.
     *
     * @param face the face
     * @return the new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    default Point relative(BlockFace face) {
        // Appelle une méthode
        final Direction direction = face.toDirection();
        // Renvoie une valeur à l'appelant
        return add(direction.normalX(), direction.normalY(), direction.normalZ());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the squared distance between this point and the provided coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     * @return the squared distance
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default double distanceSquared(double x, double y, double z) {
        // Appelle une méthode
        final double xDiff = x() - x, yDiff = y() - y, zDiff = z() - z;
        // Renvoie une valeur à l'appelant
        return (xDiff * xDiff) + (yDiff * yDiff) + (zDiff * zDiff);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the squared distance between this point and another.
     *
     * @param point the other point, decomposed by {@link #x()}, {@link #y()} and {@link #z()}
     * @return the squared distance
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default double distanceSquared(Point point) {
        // Renvoie une valeur à l'appelant
        return distanceSquared(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the distance between this point and the provided coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     * @return the distance
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default double distance(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return Math.sqrt(distanceSquared(x, y, z));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the distance between this point and another. The value of this
     * method is not cached and uses a costly square-root function, so do not
     * repeatedly call this method to get the point's magnitude. NaN will be
     * returned if the inner result of the sqrt() function overflows, which
     * will be caused if the distance is too long.
     *
     * @param point the other point
     * @return the distance
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default double distance(Point point) {
        // Renvoie une valeur à l'appelant
        return distance(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if two points have similar (x/y/z).
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     * @return true if the two positions are similar
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default boolean samePoint(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return x == x() && y == y() && z == z();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if two points have similar (x/y/z).
     *
     * @param point the point to compare, decomposed by {@link #x()}, {@link #y()} and {@link #z()}
     * @return true if the two positions are similar
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default boolean samePoint(Point point) {
        // Renvoie une valeur à l'appelant
        return samePoint(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks it two points have similar (x/y/z) coordinates within a given epsilon.
     *
     * @param x       the x coordinate to compare
     * @param y       the y coordinate to compare
     * @param z       the z coordinate to compare
     * @param epsilon the maximum difference allowed between the two points (exclusive)
     * @return true if the two positions are similar within the epsilon
     * @throws IllegalArgumentException if epsilon is less than or equal to 0
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default boolean samePoint(double x, double y, double z, double epsilon) {
        // Appelle une méthode
        Check.argCondition(epsilon <= 0, "Epsilon must be greater than 0 but found {0}", epsilon);
        // Renvoie une valeur à l'appelant
        return Math.abs(x - x()) < epsilon && Math.abs(y - y()) < epsilon && Math.abs(z - z()) < epsilon;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks it two points have similar (x/y/z) coordinates within a given epsilon.
     *
     * @param point   the point to compare
     * @param epsilon the maximum difference allowed between the two points (exclusive)
     * @return true if the two positions are similar within the epsilon
     * @throws IllegalArgumentException if epsilon is less than or equal to 0
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default boolean samePoint(Point point, double epsilon) {
        // Renvoie une valeur à l'appelant
        return samePoint(point.x(), point.y(), point.z(), epsilon);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks it two points have similar (x/y/z) coordinates within {@link #EPSILON}.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     * @return true if the two positions are similar within {@link #EPSILON}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default boolean similarPoint(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return samePoint(x, y, z, EPSILON);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks it two points have similar (x/y/z) coordinates within {@link #EPSILON}.
     *
     * @param point the point to compare
     * @return true if the two positions are similar within {@link #EPSILON}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default boolean similarPoint(Point point) {
        // Renvoie une valeur à l'appelant
        return samePoint(point, EPSILON);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if the three coordinates {@link #x()}, {@link #y()} and {@link #z()}
     * are equal to {@code 0}.
     *
     * @return true if the three coordinates are zero
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default boolean isZero() {
        // Renvoie une valeur à l'appelant
        return x() == 0 && y() == 0 && z() == 0;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if two points are in the same chunk.
     *
     * @param point the point to compare to
     * @return true if 'this' is in the same chunk as {@code point}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default boolean sameChunk(Point point) {
        // Renvoie une valeur à l'appelant
        return chunkX() == point.chunkX() && chunkZ() == point.chunkZ();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if the three {@link #blockX()}, {@link #blockY()}, {@link #blockZ()},
     * are equal to the provided ones.
     *
     * @param blockX the block x
     * @param blockY the block y
     * @param blockZ the block z
     * @return true if 'this' is in the same block as the provided coordinates
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default boolean sameBlock(int blockX, int blockY, int blockZ) {
        // Renvoie une valeur à l'appelant
        return blockX() == blockX && blockY() == blockY && blockZ() == blockZ;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if two points are in the same block.
     *
     * @param point the point to compare to
     * @return true if 'this' is in the same block as {@code point}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default boolean sameBlock(Point point) {
        // Renvoie une valeur à l'appelant
        return sameBlock(point.blockX(), point.blockY(), point.blockZ());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the magnitude of the point squared.
     *
     * @return the magnitude
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default double lengthSquared() {
        // Appelle une méthode
        final double x = x(), y = y(), z = z();
        // Renvoie une valeur à l'appelant
        return (x * x) + (y * y) + (z * z);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the magnitude of the point, defined as sqrt(x^2+y^2+z^2). The
     * value of this method is not cached and uses a costly square-root
     * function, so do not repeatedly call this method to get the point's
     * magnitude. NaN will be returned if the inner result of the sqrt()
     * function overflows, which will be caused if the length is too long.
     *
     * @return the magnitude
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default double length() {
        // Renvoie une valeur à l'appelant
        return Math.sqrt(lengthSquared());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns if a point is normalized
     *
     * @return whether the point is normalized
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default boolean isNormalized() {
        // Renvoie une valeur à l'appelant
        return Math.abs(lengthSquared() - 1) < EPSILON;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the angle between this point and another in radians.
     *
     * @param point the other point
     * @return angle in radians
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default double angle(Point point) {
        // Appelle une méthode
        final double dot = Math.clamp(dot(point) / (length() * point.length()), -1.0, 1.0);
        // Renvoie une valeur à l'appelant
        return Math.acos(dot);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Calculates the dot product of this point with another. The dot product
     * is defined as x1*x2+y1*y2+z1*z2. The returned value is a scalar.
     *
     * @param point the other point
     * @return dot product
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default double dot(Point point) {
        // Renvoie une valeur à l'appelant
        return x() * point.x() + y() * point.y() + z() * point.z();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Represents this point with all coordinates negated.
     * For example, {@code (x, y, z)} becomes {@code (-x, -y, -z)}.
     *
     * @return the negated point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> new")
    // Appelle une méthode
    Point neg();

    /**
     * Represents this point with all coordinates as their absolute values.
     * For example, {@code (x, y, z)} becomes {@code (|x|, |y|, |z|)}.
     *
     * @return the absolute point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> new")
    // Appelle une méthode
    Point abs();

    /**
     * Gets a point representing the minimum values between this point and the provided one (x/y/z).
     *
     * @param point the other point
     * @return the minimum point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Appelle une méthode
    Point min(Point point);

    /**
     * Gets a point representing the minimum values between this point and the provided coordinates (x/y/z).
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     * @return the minimum point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Appelle une méthode
    Point min(double x, double y, double z);

    /**
     * Gets a point representing the minimum values between this point and the provided value for all coordinates.
     *
     * @param value the value
     * @return the minimum point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Appelle une méthode
    Point min(double value);

    /**
     * Gets a point representing the maximum values between this point and the provided one (x/y/z).
     *
     * @param point the other point
     * @return the maximum point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Appelle une méthode
    Point max(Point point);

    /**
     * Gets a point representing the maximum values between this point and the provided coordinates (x/y/z).
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     * @return the maximum point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Appelle une méthode
    Point max(double x, double y, double z);

    /**
     * Gets a point representing the maximum values between this point and the provided value for all coordinates.
     *
     * @param value the value
     * @return the maximum point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Appelle une méthode
    Point max(double value);

    /**
     * Converts this point to a unit point (a point with length of 1).
     *
     * @return the same point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> new")
    // Appelle une méthode
    Point normalize();

    /**
     * Calculates the cross product of this point with another. The cross
     * product is defined as:
     * <ul>
     * <li>x = y1 * z2 - y2 * z1
     * <li>y = z1 * x2 - z2 * x1
     * <li>z = x1 * y2 - x2 * y1
     * </ul>
     *
     * @param point the other point
     * @return the cross product point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Appelle une méthode
    Point cross(Point point);

    /**
     * Calculates a linear interpolation between this point with another
     * point (x/y/z).
     *
     * @param point the other point
     * @param alpha The alpha value, must be between 0.0 and 1.0
     * @return Linear interpolated point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _ -> new")
    // Appelle une méthode
    Point lerp(Point point, double alpha);

    /**
     * Converts this point to a {@link Pos}.
     *
     * @return the converted position or this if already a {@link Pos}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> new")
    // Début d'une méthode/d'un bloc
    default Pos asPos() {
        // Appelle une méthode
        assert !(this instanceof Pos) : "Should be overridden";
        // Renvoie une valeur à l'appelant
        return new Pos(x(), y(), z());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Converts this point to a {@link Pos} with the provided view angles.
     *
     * @param yaw   the yaw
     * @param pitch the pitch
     * @return the converted position
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _ -> new")
    // Début d'une méthode/d'un bloc
    default Pos asPos(float yaw, float pitch) {
        // Appelle une méthode
        assert !(this instanceof Pos) : "Should be overridden";
        // Renvoie une valeur à l'appelant
        return new Pos(x(), y(), z(), yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Converts this point to a {@link Vec}.
     *
     * @return the converted point or this if already a {@link Vec}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> new")
    // Début d'une méthode/d'un bloc
    default Vec asVec() {
        // Appelle une méthode
        assert !(this instanceof Vec) : "Should be overridden";
        // Renvoie une valeur à l'appelant
        return new Vec(x(), y(), z());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Converts this point to a {@link BlockVec}. Likely flooring as needed.
     *
     * @return the converted block point or this if already a {@link BlockVec}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> new")
    // Début d'une méthode/d'un bloc
    default BlockVec asBlockVec() {
        // Appelle une méthode
        assert !(this instanceof BlockVec) : "Should be overridden";
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX(), blockY(), blockZ());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
