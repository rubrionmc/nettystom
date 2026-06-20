// Déclaration du paquet de ce fichier
package net.minestom.server.instance.generator;

// Import d'une classe nécessaire
import java.util.Collection;

// Annotation pour l'élément suivant
@FunctionalInterface
// Déclaration de type (classe/interface/enum/record)
public interface Generator {
    /**
     * This method is called when this generator is requesting this unit to be filled with blocks or biomes.
     *
     * @param unit the unit to fill
     */
    // Appelle une méthode
    void generate(GenerationUnit unit);

    /**
     * Runs {@link #generate(GenerationUnit)} on each unit in the collection.
     *
     * @param units the list of units to fill
     */
    // Début d'une méthode/d'un bloc
    default void generateAll(Collection<GenerationUnit> units) {
        // Appelle une méthode
        units.forEach(this::generate);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
