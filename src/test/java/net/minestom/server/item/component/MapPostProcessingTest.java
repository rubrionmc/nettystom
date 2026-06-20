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

// Déclaration de type (classe/interface/enum/record)
public class MapPostProcessingTest extends AbstractItemComponentTest<MapPostProcessing> {
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected DataComponent<MapPostProcessing> component() {
        // Renvoie une valeur à l'appelant
        return DataComponents.MAP_POST_PROCESSING;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected List<Map.Entry<String, MapPostProcessing>> directReadWriteEntries() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                Map.entry("lock", MapPostProcessing.LOCK),
                // Instruction de code
                Map.entry("scale", MapPostProcessing.SCALE)
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
