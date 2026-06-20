// Package declaration for this file
package net.minestom.server.instance.generator;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.instance.block.Block;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.function.Consumer;

/**
 * Represents an area that can be generated.
 * <p>
 * The size is guaranteed to be a multiple of 16 (section).
 */
// Type declaration (class/interface/enum/record)
public interface GenerationUnit {
    /**
     * This unit's modifier, used to place blocks and biomes within this unit.
     *
     * @return the modifier
     */
    // Calls a method
    UnitModifier modifier();

    /**
     * The size of this unit in blocks.
     * <p>
     * Guaranteed to be a multiple of 16.
     *
     * @return the size of this unit
     */
    // Calls a method
    Point size();

    /**
     * The absolute start (min x, y, z) of this unit.
     *
     * @return the absolute start
     */
    // Calls a method
    Point absoluteStart();

    /**
     * The absolute end (max x, y, z) of this unit.
     *
     * @return the absolute end
     */
    // Calls a method
    Point absoluteEnd();

    /**
     * Creates a fork of this unit, which will be applied to the instance whenever possible.
     *
     * @param start the start of the fork
     * @param end   the end of the fork
     * @return the fork
     */
    // Calls a method
    GenerationUnit fork(Point start, Point end);

    /**
     * Creates a fork of this unit depending on the blocks placed within the consumer.
     *
     * @param consumer the consumer
     */
    // Calls a method
    void fork(Consumer<Block.Setter> consumer);

    /**
     * Divides this unit into the smallest independent units.
     *
     * @return an immutable list of independent units
     */
    // Start of a method/block
    default List<GenerationUnit> subdivide() {
        // Returns a value to the caller
        return List.of(this);
    // End of a block/expression
    }

    /**
     * Returns the sections that this unit contains. Coordinates are in section coordinates.
     *
     * @return the contained sections
     */
    // Start of a method/block
    default Set<Vec> sections() {
        // Calls a method
        final Point start = absoluteStart(), end = absoluteEnd();
        // Calls a method
        final int minX = start.sectionX(), minY = start.sectionY(), minZ = start.sectionZ();
        // Calls a method
        final int maxX = end.sectionX(), maxY = end.sectionY(), maxZ = end.sectionZ();
        // Calls a method
        final int count = (maxX - minX) * (maxY - minY) * (maxZ - minZ);
        // Assigns a value
        Vec[] sections = new Vec[count];
        // Assigns a value
        int index = 0;
        // Loop: repeats a block
        for (int sectionX = minX; sectionX < maxX; sectionX++) {
            // Loop: repeats a block
            for (int sectionY = minY; sectionY < maxY; sectionY++) {
                // Loop: repeats a block
                for (int sectionZ = minZ; sectionZ < maxZ; sectionZ++) {
                    // Calls a method
                    sections[index++] = new Vec(sectionX, sectionY, sectionZ);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return Set.of(sections);
    // End of a block/expression
    }
// End of a block/expression
}
