// Package declaration for this file
package net.minestom.server.instance.generator;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.world.biome.Biome;

// Type declaration (class/interface/enum/record)
public interface UnitModifier extends Block.Setter, Biome.Setter {
    /**
     * Sets the block relative to the absolute position of the unit.
     *
     * @param x     the x coordinate
     * @param y     the y coordinate
     * @param z     the z coordinate
     * @param block the block to set
     */
    // Calls a method
    void setRelative(int x, int y, int z, Block block);

    /**
     * Sets all blocks within the unit to the block given by the supplier.
     *
     * @param supplier the supplier of the block to set
     */
    // Calls a method
    void setAll(Supplier supplier);

    /**
     * Sets all blocks within the unit to the block given by the supplier, relative to the absolute position of the unit.
     *
     * @param supplier the supplier of the block to set
     */
    // Calls a method
    void setAllRelative(Supplier supplier);

    /**
     * Fills the unit with the given block.
     *
     * @param block the block to fill
     */
    // Calls a method
    void fill(Block block);

    /**
     * Fills the 3d rectangular area with the given block.
     *
     * @param start the start (min) point of the area
     * @param end   the end (max) point of the area
     * @param block the block to fill
     */
    // Calls a method
    void fill(Point start, Point end, Block block);

    /**
     * Fills the 3d rectangular area with the given block.
     *
     * @param minHeight the minimum height of the area
     * @param maxHeight the maximum height of the area
     * @param block     the block to fill
     */
    // Calls a method
    void fillHeight(int minHeight, int maxHeight, Block block);

    /**
     * Fills the 3d rectangular area with the given biome.
     *
     * @param biome the biome to fill
     */
    // Calls a method
    void fillBiome(RegistryKey<Biome> biome);

    // Type declaration (class/interface/enum/record)
    interface Supplier {
        // Calls a method
        Block get(int x, int y, int z);
    // End of a block/expression
    }
// End of a block/expression
}
