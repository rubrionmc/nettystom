// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public class LodestoneTrackerTest extends AbstractItemComponentTest<LodestoneTracker> {

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected DataComponent<LodestoneTracker> component() {
        // Renvoie une valeur à l'appelant
        return DataComponents.LODESTONE_TRACKER;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected List<Map.Entry<String, LodestoneTracker>> directReadWriteEntries() {
        // Renvoie une valeur à l'appelant
        return List.of(
            // Instruction de code
            Map.entry("tracked", new LodestoneTracker("minecraft:overworld", Vec.ZERO, true)),
            // Instruction de code
            Map.entry("not tracked", new LodestoneTracker("minecraft:overworld", new Vec(1, 2, 3), false))
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
