// Déclaration du paquet de ce fichier
package net.minestom.server.instance.generator;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.world.biome.Biome;

// Déclaration de type (classe/interface/enum/record)
public interface UnitModifier extends Block.Setter, Biome.Setter {
    /**
     * Sets the block relative to the absolute position of the unit.
     *
     * @param x     the x coordinate
     * @param y     the y coordinate
     * @param z     the z coordinate
     * @param block the block to set
     */
    // Appelle une méthode
    void setRelative(int x, int y, int z, Block block);

    /**
     * Sets all blocks within the unit to the block given by the supplier.
     *
     * @param supplier the supplier of the block to set
     */
    // Appelle une méthode
    void setAll(Supplier supplier);

    /**
     * Sets all blocks within the unit to the block given by the supplier, relative to the absolute position of the unit.
     *
     * @param supplier the supplier of the block to set
     */
    // Appelle une méthode
    void setAllRelative(Supplier supplier);

    /**
     * Fills the unit with the given block.
     *
     * @param block the block to fill
     */
    // Appelle une méthode
    void fill(Block block);

    /**
     * Fills the 3d rectangular area with the given block.
     *
     * @param start the start (min) point of the area
     * @param end   the end (max) point of the area
     * @param block the block to fill
     */
    // Appelle une méthode
    void fill(Point start, Point end, Block block);

    /**
     * Fills the 3d rectangular area with the given block.
     *
     * @param minHeight the minimum height of the area
     * @param maxHeight the maximum height of the area
     * @param block     the block to fill
     */
    // Appelle une méthode
    void fillHeight(int minHeight, int maxHeight, Block block);

    /**
     * Fills the 3d rectangular area with the given biome.
     *
     * @param biome the biome to fill
     */
    // Appelle une méthode
    void fillBiome(RegistryKey<Biome> biome);

    // Déclaration de type (classe/interface/enum/record)
    interface Supplier {
        // Appelle une méthode
        Block get(int x, int y, int z);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
