// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.utils.Unit;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Static import of a member
import static java.util.Map.entry;
// Static import of a member
import static org.junit.jupiter.api.Assertions.fail;

// Type declaration (class/interface/enum/record)
public class UnitTest extends AbstractItemComponentTest<Unit> {
    // This is not a test, but it creates a compile error if the component type is changed away from Unit,
    // as a reminder that tests should be added for that new component type.
    // Assigns a value
    private static final List<DataComponent<Unit>> UNIT_COMPONENTS = List.of(
            // Code statement
            DataComponents.CREATIVE_SLOT_LOCK,
            // Code statement
            DataComponents.INTANGIBLE_PROJECTILE,
            // Code statement
            DataComponents.GLIDER,
            // Code statement
            DataComponents.UNBREAKABLE
    // End of a block/expression
    );

    // Start of a method/block
    static {
        // Calls a method
        MinecraftServer.init();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<Unit> component() {
        // Returns a value to the caller
        return UNIT_COMPONENTS.getFirst();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected List<Map.Entry<String, Unit>> directReadWriteEntries() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                entry("instance", Unit.INSTANCE)
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void ensureUnitComponentsPresent() {
        // Calls a method
        var fails = new ArrayList<String>();
        // Loop: repeats a block
        for (var component : DataComponent.values()) {
            // Branch: checks a condition
            if (!component.isSynced()) continue;

            // Try to write as a Unit and if it fails we can ignore that type
            // Exception handling
            try {
                //noinspection unchecked
                // Calls a method
                ((DataComponent<Unit>) component).write(NetworkBuffer.resizableBuffer(MinecraftServer.process()), Unit.INSTANCE);
            // Start of a method/block
            } catch (ClassCastException | IllegalArgumentException ignored) {
                // Continues to the next loop iteration
                continue;
            // End of a block/expression
            }

            // Branch: checks a condition
            if (!UNIT_COMPONENTS.contains(component)) {
                // Calls a method
                fails.add(component.name());
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Branch: checks a condition
        if (!fails.isEmpty()) {
            // Calls a method
            fail("Some components are not included in UnitTest: " + fails);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
