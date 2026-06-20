// Package declaration for this file
package net.minestom.server.coordinate;

// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.List;

/**
 * Represents a collection of aligned block coordinates in a 3D space.
 * <p>
 * If switched over, consider a fallback to the iterator as more implementations may be added in the future.
 */
// Annotation for the following element
@ApiStatus.Experimental
// Type declaration (class/interface/enum/record)
public sealed interface Area extends Iterable<BlockVec> {

    /**
     * Returns this area translated by the given block offset.
     *
     * @param x the X block offset
     * @param y the Y block offset
     * @param z the Z block offset
     * @return the translated area
     */
    // Start of a method/block
    default Area offset(int x, int y, int z) {
        // Returns a value to the caller
        return switch (this) {
            // Multiple branching (switch/case)
            case Single single -> single(single.point().add(x, y, z));
            // Multiple branching (switch/case)
            case Line line -> line(line.start().add(x, y, z), line.end().add(x, y, z));
            // Multiple branching (switch/case)
            case Cuboid cuboid -> cuboid(cuboid.min().add(x, y, z), cuboid.max().add(x, y, z));
            // Multiple branching (switch/case)
            case Sphere sphere -> sphere(sphere.center().add(x, y, z), sphere.radius());
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Returns this area translated by the block coordinates of {@code offset}.
     *
     * @param offset the offset point
     * @return the translated area
     */
    // Start of a method/block
    default Area offset(Point offset) {
        // Returns a value to the caller
        return offset(offset.blockX(), offset.blockY(), offset.blockZ());
    // End of a block/expression
    }

    /**
     * Returns the bounding box of this area.
     *
     * @return a cuboid representing the bounding box with the lowest and highest points
     */
    // Start of a method/block
    default Cuboid bound() {
        // Returns a value to the caller
        return switch (this) {
            // Multiple branching (switch/case)
            case Single single -> cuboid(single.point(), single.point());
            // Multiple branching (switch/case)
            case Line line -> {
                // Calls a method
                final BlockVec start = line.start();
                // Calls a method
                final BlockVec end = line.end();
                // Calls a method
                yield cuboid(start.min(end), start.max(end));
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case Cuboid cuboid -> cuboid;
            // Multiple branching (switch/case)
            case Sphere sphere -> {
                // Calls a method
                final BlockVec center = sphere.center();
                // Calls a method
                final int radius = sphere.radius();
                // Calls a method
                yield cuboid(center.sub(radius, radius, radius), center.add(radius, radius, radius));
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Checks whether this area contains the given block coordinate.
     *
     * @param x the block X coordinate
     * @param y the block Y coordinate
     * @param z the block Z coordinate
     * @return {@code true} if the block coordinate is contained in this area
     */
    // Calls a method
    boolean contains(int x, int y, int z);

    /**
     * Checks whether this area contains the block coordinate of {@code point}.
     *
     * @param point the point to convert to a block coordinate
     * @return {@code true} if the block coordinate is contained in this area
     */
    // Start of a method/block
    default boolean contains(Point point) {
        // Returns a value to the caller
        return contains(point.blockX(), point.blockY(), point.blockZ());
    // End of a block/expression
    }

    /**
     * Returns the number of blocks contained in this area.
     * <p>
     * Counting a sphere requires scanning its bounding box and may be expensive for large radii.
     *
     * @return the contained block count
     */
    // Calls a method
    long blockCount();

    /**
     * Splits this area into multiple cuboids which do not cross section boundaries.
     * <p>
     * Single sections may have multiple cuboids if the section-local portion is not a perfect cuboid.
     *
     * @return list of sub-cuboids covering exactly this area
     */
    // Calls a method
    List<Cuboid> split();

    /**
     * Creates an area containing a single block.
     *
     * @param point the point to convert to a block coordinate
     * @return a single-block area
     */
    // Start of a method/block
    static Single single(Point point) {
        // Returns a value to the caller
        return new AreaImpl.Single(point.asBlockVec());
    // End of a block/expression
    }

    /**
     * Creates an area containing a single block.
     *
     * @param x the block X coordinate
     * @param y the block Y coordinate
     * @param z the block Z coordinate
     * @return a single-block area
     */
    // Start of a method/block
    static Single single(int x, int y, int z) {
        // Returns a value to the caller
        return single(new BlockVec(x, y, z));
    // End of a block/expression
    }

    /**
     * Creates a line area between two block coordinates.
     *
     * @param start the start point to convert to a block coordinate
     * @param end   the end point to convert to a block coordinate
     * @return a line area
     */
    // Start of a method/block
    static Line line(Point start, Point end) {
        // Returns a value to the caller
        return new AreaImpl.Line(start.asBlockVec(), end.asBlockVec());
    // End of a block/expression
    }

    /**
     * Creates a cuboid area from two corners. The corners may be supplied in any order.
     *
     * @param min one corner to convert to a block coordinate
     * @param max the other corner to convert to a block coordinate
     * @return a cuboid area with ordered minimum and maximum coordinates
     */
    // Start of a method/block
    static Cuboid cuboid(Point min, Point max) {
        // Returns a value to the caller
        return new AreaImpl.Cuboid(min.asBlockVec(), max.asBlockVec());
    // End of a block/expression
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
    // Start of a method/block
    static Cuboid cube(Point center, int size) {
        // Returns a value to the caller
        return AreaImpl.cube(center, size);
    // End of a block/expression
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
    // Start of a method/block
    static Cuboid box(Point center, Point size) {
        // Returns a value to the caller
        return AreaImpl.box(center, size);
    // End of a block/expression
    }

    /**
     * Creates a cuboid containing all blocks in the given section.
     *
     * @param sectionX the section X coordinate
     * @param sectionY the section Y coordinate
     * @param sectionZ the section Z coordinate
     * @return a 16x16x16 section cuboid
     */
    // Start of a method/block
    static Cuboid section(int sectionX, int sectionY, int sectionZ) {
        // Returns a value to the caller
        return AreaImpl.section(sectionX, sectionY, sectionZ);
    // End of a block/expression
    }

    /**
     * Creates a sphere area from a center and non-negative radius.
     *
     * @param center the center point to convert to a block coordinate
     * @param radius the radius in blocks
     * @return a sphere area
     * @throws IllegalArgumentException if {@code radius} is negative
     */
    // Start of a method/block
    static Sphere sphere(Point center, int radius) {
        // Returns a value to the caller
        return new AreaImpl.Sphere(center.asBlockVec(), radius);
    // End of a block/expression
    }

    /**
     * An area containing exactly one block.
     */
    // Type declaration (class/interface/enum/record)
    sealed interface Single extends Area permits AreaImpl.Single {
        /**
         * @return the contained block
         */
        // Calls a method
        BlockVec point();
    // End of a block/expression
    }

    /**
     * An area containing blocks traced by a line between two block coordinates.
     */
    // Type declaration (class/interface/enum/record)
    sealed interface Line extends Area permits AreaImpl.Line {
        /**
         * @return the start block
         */
        // Calls a method
        BlockVec start();

        /**
         * @return the end block
         */
        // Calls a method
        BlockVec end();
    // End of a block/expression
    }

    /**
     * An area containing all blocks inside an inclusive cuboid.
     */
    // Type declaration (class/interface/enum/record)
    sealed interface Cuboid extends Area permits AreaImpl.Cuboid {
        /**
         * @return the minimum corner
         */
        // Calls a method
        BlockVec min();

        /**
         * @return the maximum corner
         */
        // Calls a method
        BlockVec max();
    // End of a block/expression
    }

    /**
     * An area containing all blocks within a radius of a center block.
     */
    // Type declaration (class/interface/enum/record)
    sealed interface Sphere extends Area permits AreaImpl.Sphere {
        /**
         * @return the center block
         */
        // Calls a method
        BlockVec center();

        /**
         * @return the non-negative radius
         */
        // Calls a method
        int radius();
    // End of a block/expression
    }
// End of a block/expression
}
