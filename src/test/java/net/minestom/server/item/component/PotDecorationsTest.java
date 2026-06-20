// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public class PotDecorationsTest extends AbstractItemComponentTest<PotDecorations> {
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected DataComponent<PotDecorations> component() {
        // Renvoie une valeur à l'appelant
        return DataComponents.POT_DECORATIONS;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected List<Map.Entry<String, PotDecorations>> directReadWriteEntries() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                Map.entry("instance", PotDecorations.EMPTY),
                // Instruction de code
                Map.entry("one", new PotDecorations(Material.DIAMOND, PotDecorations.DEFAULT_ITEM, PotDecorations.DEFAULT_ITEM, PotDecorations.DEFAULT_ITEM)),
                // Instruction de code
                Map.entry("two", new PotDecorations(Material.DIAMOND, Material.DIAMOND, PotDecorations.DEFAULT_ITEM, PotDecorations.DEFAULT_ITEM)),
                // Instruction de code
                Map.entry("three", new PotDecorations(Material.DIAMOND, Material.DIAMOND, Material.DIAMOND, PotDecorations.DEFAULT_ITEM)),
                // Instruction de code
                Map.entry("four", new PotDecorations(Material.DIAMOND, Material.DIAMOND, Material.DIAMOND, Material.DIAMOND))
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
