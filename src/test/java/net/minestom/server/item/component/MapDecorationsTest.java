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
public class MapDecorationsTest extends AbstractItemComponentTest<MapDecorations> {

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected DataComponent<MapDecorations> component() {
        // Renvoie une valeur à l'appelant
        return DataComponents.MAP_DECORATIONS;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected List<Map.Entry<String, MapDecorations>> directReadWriteEntries() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                Map.entry("empty", new MapDecorations(Map.of())),
                // Instruction de code
                Map.entry("single", new MapDecorations(Map.of("id", new MapDecorations.Entry("type", 1.0, 2.0, 3)))),
                // Instruction de code
                Map.entry("multiple", new MapDecorations(Map.of(
                        // Instruction de code
                        "id1", new MapDecorations.Entry("type1", 1.0, 2.0, 3),
                        // Instruction de code
                        "id2", new MapDecorations.Entry("type2", 4.0, 5.0, 6)
                // Instruction de code
                )))
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
