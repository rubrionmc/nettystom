// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.utils.Direction;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;

// Import d'une classe nécessaire
import java.util.function.DoubleUnaryOperator;

// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.*;

/**
 * Represents a 3D point.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface Point permits Vec, Pos, BlockVec {

    /**
     * Gets the X coordinate.
     *
     * @return the X coordinate
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Boucle : répète un bloc
    double x();

    /**
     * Gets the Y coordinate.
     *
     * @return the Y coordinate
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Boucle : répète un bloc
    double y();

    /**
     * Gets the Z coordinate.
     *
     * @return the Z coordinate
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Boucle : répète un bloc
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

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default int sectionX() {
        // Renvoie une valeur à l'appelant
        return globalToSection(blockX());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default int sectionY() {
        // Renvoie une valeur à l'appelant
        return globalToSection(blockY());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default int sectionZ() {
        // Renvoie une valeur à l'appelant
        return globalToSection(blockZ());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default int chunkX() {
        // Renvoie une valeur à l'appelant
        return sectionX();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default int chunkZ() {
        // Renvoie une valeur à l'appelant
        return sectionZ();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default int regionX() {
        // Renvoie une valeur à l'appelant
        return globalToRegion(blockX());
    // Fin d'un bloc/d'une expression
    }

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
    @Contract(pure = true)
    // Appelle une méthode
    Point withX(DoubleUnaryOperator operator);

    /**
     * Creates a point with the specified X coordinate.
     *
     * @param x the new X coordinate
     * @return a new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Point withX(double x);

    /**
     * Creates a point with a modified Y coordinate based on its value.
     *
     * @param operator the operator providing the current Y coordinate and returning the new
     * @return a new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Point withY(DoubleUnaryOperator operator);

    /**
     * Creates a point with the specified Y coordinate.
     *
     * @param y the new Y coordinate
     * @return a new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Point withY(double y);

    /**
     * Creates a point with a modified Z coordinate based on its value.
     *
     * @param operator the operator providing the current Z coordinate and returning the new
     * @return a new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Point withZ(DoubleUnaryOperator operator);

    /**
     * Creates a point with the specified Z coordinate.
     *
     * @param z the new Z coordinate
     * @return a new point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Point withZ(double z);

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Point add(double x, double y, double z);

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Point add(Point point);

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Point add(double value);

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Point sub(double x, double y, double z);

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Point sub(Point point);

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Point sub(double value);

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Point mul(double x, double y, double z);

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Point mul(Point point);

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Point mul(double value);

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Point div(double x, double y, double z);

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Point div(Point point);

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Point div(double value);

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default Point relative(BlockFace face) {
        // Appelle une méthode
        final Direction direction = face.toDirection();
        // Renvoie une valeur à l'appelant
        return add(direction.normalX(), direction.normalY(), direction.normalZ());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default double distanceSquared(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return MathUtils.square(x() - x) + MathUtils.square(y() - y) + MathUtils.square(z() - z);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the squared distance between this point and another.
     *
     * @param point the other point
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
     * repeatedly call this method to get the vector's magnitude. NaN will be
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

    // Début d'une méthode/d'un bloc
    default boolean samePoint(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return x == x() && y == y() && z == z();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks it two points have similar (x/y/z).
     *
     * @param point the point to compare
     * @return true if the two positions are similar
     */
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
    // Début d'une méthode/d'un bloc
    default boolean samePoint(Point point, double epsilon) {
        // Renvoie une valeur à l'appelant
        return samePoint(point.x(), point.y(), point.z(), epsilon);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if the three coordinates {@link #x()}, {@link #y()} and {@link #z()}
     * are equal to {@code 0}.
     *
     * @return true if the three coordinates are zero
     */
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
    // Début d'une méthode/d'un bloc
    default boolean sameChunk(Point point) {
        // Renvoie une valeur à l'appelant
        return chunkX() == point.chunkX() && chunkZ() == point.chunkZ();
    // Fin d'un bloc/d'une expression
    }

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
    // Début d'une méthode/d'un bloc
    default boolean sameBlock(Point point) {
        // Renvoie une valeur à l'appelant
        return sameBlock(point.blockX(), point.blockY(), point.blockZ());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default Pos asPos() {
        // Renvoie une valeur à l'appelant
        return switch (this) {
            // Embranchement multiple (switch/case)
            case Pos pos -> pos;
            // Embranchement multiple (switch/case)
            case Vec vec -> new Pos(vec.x(), vec.y(), vec.z());
            // Embranchement multiple (switch/case)
            case BlockVec blockVec -> new Pos(blockVec.blockX(), blockVec.blockY(), blockVec.blockZ());
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default Vec asVec() {
        // Renvoie une valeur à l'appelant
        return switch (this) {
            // Embranchement multiple (switch/case)
            case Vec vec -> vec;
            // Embranchement multiple (switch/case)
            case Pos pos -> new Vec(pos.x(), pos.y(), pos.z());
            // Embranchement multiple (switch/case)
            case BlockVec blockVec -> new Vec(blockVec.blockX(), blockVec.blockY(), blockVec.blockZ());
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default BlockVec asBlockVec() {
        // Renvoie une valeur à l'appelant
        return switch (this) {
            // Embranchement multiple (switch/case)
            case BlockVec blockVec -> blockVec;
            // Embranchement multiple (switch/case)
            case Pos pos -> new BlockVec(pos.blockX(), pos.blockY(), pos.blockZ());
            // Embranchement multiple (switch/case)
            case Vec vec -> new BlockVec(vec.blockX(), vec.blockY(), vec.blockZ());
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
