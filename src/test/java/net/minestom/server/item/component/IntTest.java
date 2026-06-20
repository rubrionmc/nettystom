// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static java.util.Map.entry;

// Déclaration de type (classe/interface/enum/record)
public class IntTest extends AbstractItemComponentTest<Integer> {
    // This is not a test, but it creates a compile error if the component type is changed away from Integer,
    // as a reminder that tests should be added for that new component type.
    // Affecte une valeur
    private static final List<DataComponent<Integer>> INT_COMPONENTS = List.of(
           // Instruction de code
           DataComponents.MAX_STACK_SIZE,
           // Instruction de code
           DataComponents.MAX_DAMAGE,
           // Instruction de code
           DataComponents.DAMAGE,
           // Instruction de code
           DataComponents.REPAIR_COST,
           // Instruction de code
           DataComponents.MAP_ID,
           // Instruction de code
           DataComponents.OMINOUS_BOTTLE_AMPLIFIER
    // Fin d'un bloc/d'une expression
    );

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected DataComponent<Integer> component() {
        // Renvoie une valeur à l'appelant
        return INT_COMPONENTS.getFirst();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected List<Map.Entry<String, Integer>> directReadWriteEntries() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                entry("instance", 2)
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
