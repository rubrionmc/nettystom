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
public class StringTest extends AbstractItemComponentTest<String> {
    // This is not a test, but it creates a compile error if the component type is changed away,
    // as a reminder that tests should be added for that new component type.
    // Affecte une valeur
    private static final List<DataComponent<String>> SHARED_COMPONENTS = List.of(
           // Instruction de code
           DataComponents.NOTE_BLOCK_SOUND
    // Fin d'un bloc/d'une expression
    );

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected DataComponent<String> component() {
        // Renvoie une valeur à l'appelant
        return SHARED_COMPONENTS.getFirst();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected List<Map.Entry<String, String>> directReadWriteEntries() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                entry("instance", "hello, world")
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
