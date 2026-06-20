// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.utils.Unit;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static java.util.Map.entry;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.fail;

// Déclaration de type (classe/interface/enum/record)
public class UnitTest extends AbstractItemComponentTest<Unit> {
    // This is not a test, but it creates a compile error if the component type is changed away from Unit,
    // as a reminder that tests should be added for that new component type.
    // Affecte une valeur
    private static final List<DataComponent<Unit>> UNIT_COMPONENTS = List.of(
            // Instruction de code
            DataComponents.CREATIVE_SLOT_LOCK,
            // Instruction de code
            DataComponents.INTANGIBLE_PROJECTILE,
            // Instruction de code
            DataComponents.GLIDER,
            // Instruction de code
            DataComponents.UNBREAKABLE
    // Fin d'un bloc/d'une expression
    );

    // Début d'une méthode/d'un bloc
    static {
        // Appelle une méthode
        MinecraftServer.init();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected DataComponent<Unit> component() {
        // Renvoie une valeur à l'appelant
        return UNIT_COMPONENTS.getFirst();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected List<Map.Entry<String, Unit>> directReadWriteEntries() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                entry("instance", Unit.INSTANCE)
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void ensureUnitComponentsPresent() {
        // Affecte une valeur
        var fails = new ArrayList<String>();
        // Boucle : répète un bloc
        for (var component : DataComponent.values()) {
            // Embranchement : vérifie une condition
            if (!component.isSynced()) continue;

            // Try to write as a Unit and if it fails we can ignore that type
            // Gestion des exceptions
            try {
                //noinspection unchecked
                // Appelle une méthode
                ((DataComponent<Unit>) component).write(NetworkBuffer.resizableBuffer(MinecraftServer.process()), Unit.INSTANCE);
            // Début d'une méthode/d'un bloc
            } catch (ClassCastException | IllegalArgumentException ignored) {
                // Passe à l'itération suivante de la boucle
                continue;
            // Fin d'un bloc/d'une expression
            }

            // Embranchement : vérifie une condition
            if (!UNIT_COMPONENTS.contains(component)) {
                // Appelle une méthode
                fails.add(component.name());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (!fails.isEmpty()) {
            // Appelle une méthode
            fail("Some components are not included in UnitTest: " + fails);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
