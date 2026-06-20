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
public class DebugStickStateTest extends AbstractItemComponentTest<DebugStickState> {

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected DataComponent<DebugStickState> component() {
        // Renvoie une valeur à l'appelant
        return DataComponents.DEBUG_STICK_STATE;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected List<Map.Entry<String, DebugStickState>> directReadWriteEntries() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                entry("empty", new DebugStickState(Map.of())),
                // Note that an invalid block id is present. Minestom currently does not validate the block id or state value.
                // Instruction de code
                entry("contents", new DebugStickState(Map.of("minecraft:stone_stairs", "shape", "minecraft:nothing", "abcdef")))
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
