// Déclaration du paquet de ce fichier
package net.minestom.server.instance.generator;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.function.Consumer;

/**
 * Represents an area that can be generated.
 * <p>
 * The size is guaranteed to be a multiple of 16 (section).
 */
// Déclaration de type (classe/interface/enum/record)
public interface GenerationUnit {
    /**
     * This unit's modifier, used to place blocks and biomes within this unit.
     *
     * @return the modifier
     */
    // Appelle une méthode
    UnitModifier modifier();

    /**
     * The size of this unit in blocks.
     * <p>
     * Guaranteed to be a multiple of 16.
     *
     * @return the size of this unit
     */
    // Appelle une méthode
    Point size();

    /**
     * The absolute start (min x, y, z) of this unit.
     *
     * @return the absolute start
     */
    // Appelle une méthode
    Point absoluteStart();

    /**
     * The absolute end (max x, y, z) of this unit.
     *
     * @return the absolute end
     */
    // Appelle une méthode
    Point absoluteEnd();

    /**
     * Creates a fork of this unit, which will be applied to the instance whenever possible.
     *
     * @param start the start of the fork
     * @param end   the end of the fork
     * @return the fork
     */
    // Appelle une méthode
    GenerationUnit fork(Point start, Point end);

    /**
     * Creates a fork of this unit depending on the blocks placed within the consumer.
     *
     * @param consumer the consumer
     */
    // Appelle une méthode
    void fork(Consumer<Block.Setter> consumer);

    /**
     * Divides this unit into the smallest independent units.
     *
     * @return an immutable list of independent units
     */
    // Début d'une méthode/d'un bloc
    default List<GenerationUnit> subdivide() {
        // Renvoie une valeur à l'appelant
        return List.of(this);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the sections that this unit contains. Coordinates are in section coordinates.
     *
     * @return the contained sections
     */
    // Début d'une méthode/d'un bloc
    default Set<Vec> sections() {
        // Appelle une méthode
        final Point start = absoluteStart(), end = absoluteEnd();
        // Appelle une méthode
        final int minX = start.sectionX(), minY = start.sectionY(), minZ = start.sectionZ();
        // Appelle une méthode
        final int maxX = end.sectionX(), maxY = end.sectionY(), maxZ = end.sectionZ();
        // Appelle une méthode
        final int count = (maxX - minX) * (maxY - minY) * (maxZ - minZ);
        // Affecte une valeur
        Vec[] sections = new Vec[count];
        // Affecte une valeur
        int index = 0;
        // Boucle : répète un bloc
        for (int sectionX = minX; sectionX < maxX; sectionX++) {
            // Boucle : répète un bloc
            for (int sectionY = minY; sectionY < maxY; sectionY++) {
                // Boucle : répète un bloc
                for (int sectionZ = minZ; sectionZ < maxZ; sectionZ++) {
                    // Appelle une méthode
                    sections[index++] = new Vec(sectionX, sectionY, sectionZ);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return Set.of(sections);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
