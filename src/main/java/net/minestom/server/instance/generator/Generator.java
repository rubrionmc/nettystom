// Package declaration for this file
package net.minestom.server.instance.generator;

// Import of a required class
import java.util.Collection;

// Annotation for the following element
@FunctionalInterface
// Type declaration (class/interface/enum/record)
public interface Generator {
    /**
     * This method is called when this generator is requesting this unit to be filled with blocks or biomes.
     *
     * @param unit the unit to fill
     */
    // Calls a method
    void generate(GenerationUnit unit);

    /**
     * Runs {@link #generate(GenerationUnit)} on each unit in the collection.
     *
     * @param units the list of units to fill
     */
    // Start of a method/block
    default void generateAll(Collection<GenerationUnit> units) {
        // Calls a method
        units.forEach(this::generate);
    // End of a block/expression
    }
// End of a block/expression
}
