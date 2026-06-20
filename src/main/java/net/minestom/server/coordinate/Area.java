// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.List;

/**
 * Represents a collection of aligned block coordinates in a 3D space.
 * <p>
 * If switched over, consider a fallback to the iterator as more implementations may be added in the future.
 */
// Annotation pour l'élément suivant
@ApiStatus.Experimental
// Déclaration de type (classe/interface/enum/record)
public sealed interface Area extends Iterable<BlockVec> {

    /**
     * Returns this area translated by the given block offset.
     *
     * @param x the X block offset
     * @param y the Y block offset
     * @param z the Z block offset
     * @return the translated area
     */
    // Début d'une méthode/d'un bloc
    default Area offset(int x, int y, int z) {
        // Renvoie une valeur à l'appelant
        return switch (this) {
            // Embranchement multiple (switch/case)
            case Single single -> single(single.point().add(x, y, z));
            // Embranchement multiple (switch/case)
            case Line line -> line(line.start().add(x, y, z), line.end().add(x, y, z));
            // Embranchement multiple (switch/case)
            case Cuboid cuboid -> cuboid(cuboid.min().add(x, y, z), cuboid.max().add(x, y, z));
            // Embranchement multiple (switch/case)
            case Sphere sphere -> sphere(sphere.center().add(x, y, z), sphere.radius());
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns this area translated by the block coordinates of {@code offset}.
     *
     * @param offset the offset point
     * @return the translated area
     */
    // Début d'une méthode/d'un bloc
    default Area offset(Point offset) {
        // Renvoie une valeur à l'appelant
        return offset(offset.blockX(), offset.blockY(), offset.blockZ());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the bounding box of this area.
     *
     * @return a cuboid representing the bounding box with the lowest and highest points
     */
    // Début d'une méthode/d'un bloc
    default Cuboid bound() {
        // Renvoie une valeur à l'appelant
        return switch (this) {
            // Embranchement multiple (switch/case)
            case Single single -> cuboid(single.point(), single.point());
            // Embranchement multiple (switch/case)
            case Line line -> {
                // Appelle une méthode
                final BlockVec start = line.start();
                // Appelle une méthode
                final BlockVec end = line.end();
                // Appelle une méthode
                yield cuboid(start.min(end), start.max(end));
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case Cuboid cuboid -> cuboid;
            // Embranchement multiple (switch/case)
            case Sphere sphere -> {
                // Appelle une méthode
                final BlockVec center = sphere.center();
                // Appelle une méthode
                final int radius = sphere.radius();
                // Appelle une méthode
                yield cuboid(center.sub(radius, radius, radius), center.add(radius, radius, radius));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks whether this area contains the given block coordinate.
     *
     * @param x the block X coordinate
     * @param y the block Y coordinate
     * @param z the block Z coordinate
     * @return {@code true} if the block coordinate is contained in this area
     */
    // Appelle une méthode
    boolean contains(int x, int y, int z);

    /**
     * Checks whether this area contains the block coordinate of {@code point}.
     *
     * @param point the point to convert to a block coordinate
     * @return {@code true} if the block coordinate is contained in this area
     */
    // Début d'une méthode/d'un bloc
    default boolean contains(Point point) {
        // Renvoie une valeur à l'appelant
        return contains(point.blockX(), point.blockY(), point.blockZ());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the number of blocks contained in this area.
     * <p>
     * Counting a sphere requires scanning its bounding box and may be expensive for large radii.
     *
     * @return the contained block count
     */
    // Appelle une méthode
    long blockCount();

    /**
     * Splits this area into multiple cuboids which do not cross section boundaries.
     * <p>
     * Single sections may have multiple cuboids if the section-local portion is not a perfect cuboid.
     *
     * @return list of sub-cuboids covering exactly this area
     */
    // Appelle une méthode
    List<Cuboid> split();

    /**
     * Creates an area containing a single block.
     *
     * @param point the point to convert to a block coordinate
     * @return a single-block area
     */
    // Début d'une méthode/d'un bloc
    static Single single(Point point) {
        // Renvoie une valeur à l'appelant
        return new AreaImpl.Single(point.asBlockVec());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an area containing a single block.
     *
     * @param x the block X coordinate
     * @param y the block Y coordinate
     * @param z the block Z coordinate
     * @return a single-block area
     */
    // Début d'une méthode/d'un bloc
    static Single single(int x, int y, int z) {
        // Renvoie une valeur à l'appelant
        return single(new BlockVec(x, y, z));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a line area between two block coordinates.
     *
     * @param start the start point to convert to a block coordinate
     * @param end   the end point to convert to a block coordinate
     * @return a line area
     */
    // Début d'une méthode/d'un bloc
    static Line line(Point start, Point end) {
        // Renvoie une valeur à l'appelant
        return new AreaImpl.Line(start.asBlockVec(), end.asBlockVec());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a cuboid area from two corners. The corners may be supplied in any order.
     *
     * @param min one corner to convert to a block coordinate
     * @param max the other corner to convert to a block coordinate
     * @return a cuboid area with ordered minimum and maximum coordinates
     */
    // Début d'une méthode/d'un bloc
    static Cuboid cuboid(Point min, Point max) {
        // Renvoie une valeur à l'appelant
        return new AreaImpl.Cuboid(min.asBlockVec(), max.asBlockVec());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a cuboid centered around {@code center} with the same size on each axis.
     * <p>
     * Since the bounds are inclusive block coordinates, even sizes include the center block and
     * extend {@code size / 2} blocks in each direction. The size is a coordinate span, not the
     * final number of blocks.
     *
     * @param center the center point to convert to a block coordinate
     * @param size   the size used for each axis
     * @return a cuboid area
     * @throws IllegalArgumentException if {@code size} is negative
     */
    // Début d'une méthode/d'un bloc
    static Cuboid cube(Point center, int size) {
        // Renvoie une valeur à l'appelant
        return AreaImpl.cube(center, size);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a cuboid centered around {@code center} with the given size on each axis.
     * <p>
     * Since the bounds are inclusive block coordinates, even sizes include the center block and
     * extend half of the size in each direction. The size is a coordinate span, not the final
     * number of blocks.
     *
     * @param center the center point to convert to a block coordinate
     * @param size   the size point, converted through its coordinates
     * @return a cuboid area
     * @throws IllegalArgumentException if any size component is negative
     */
    // Début d'une méthode/d'un bloc
    static Cuboid box(Point center, Point size) {
        // Renvoie une valeur à l'appelant
        return AreaImpl.box(center, size);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a cuboid containing all blocks in the given section.
     *
     * @param sectionX the section X coordinate
     * @param sectionY the section Y coordinate
     * @param sectionZ the section Z coordinate
     * @return a 16x16x16 section cuboid
     */
    // Début d'une méthode/d'un bloc
    static Cuboid section(int sectionX, int sectionY, int sectionZ) {
        // Renvoie une valeur à l'appelant
        return AreaImpl.section(sectionX, sectionY, sectionZ);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a sphere area from a center and non-negative radius.
     *
     * @param center the center point to convert to a block coordinate
     * @param radius the radius in blocks
     * @return a sphere area
     * @throws IllegalArgumentException if {@code radius} is negative
     */
    // Début d'une méthode/d'un bloc
    static Sphere sphere(Point center, int radius) {
        // Renvoie une valeur à l'appelant
        return new AreaImpl.Sphere(center.asBlockVec(), radius);
    // Fin d'un bloc/d'une expression
    }

    /**
     * An area containing exactly one block.
     */
    // Déclaration de type (classe/interface/enum/record)
    sealed interface Single extends Area permits AreaImpl.Single {
        /**
         * @return the contained block
         */
        // Appelle une méthode
        BlockVec point();
    // Fin d'un bloc/d'une expression
    }

    /**
     * An area containing blocks traced by a line between two block coordinates.
     */
    // Déclaration de type (classe/interface/enum/record)
    sealed interface Line extends Area permits AreaImpl.Line {
        /**
         * @return the start block
         */
        // Appelle une méthode
        BlockVec start();

        /**
         * @return the end block
         */
        // Appelle une méthode
        BlockVec end();
    // Fin d'un bloc/d'une expression
    }

    /**
     * An area containing all blocks inside an inclusive cuboid.
     */
    // Déclaration de type (classe/interface/enum/record)
    sealed interface Cuboid extends Area permits AreaImpl.Cuboid {
        /**
         * @return the minimum corner
         */
        // Appelle une méthode
        BlockVec min();

        /**
         * @return the maximum corner
         */
        // Appelle une méthode
        BlockVec max();
    // Fin d'un bloc/d'une expression
    }

    /**
     * An area containing all blocks within a radius of a center block.
     */
    // Déclaration de type (classe/interface/enum/record)
    sealed interface Sphere extends Area permits AreaImpl.Sphere {
        /**
         * @return the center block
         */
        // Appelle une méthode
        BlockVec center();

        /**
         * @return the non-negative radius
         */
        // Appelle une méthode
        int radius();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
