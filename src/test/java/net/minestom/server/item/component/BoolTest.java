// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponents;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Static import of a member
import static java.util.Map.entry;

// Type declaration (class/interface/enum/record)
public class BoolTest extends AbstractItemComponentTest<Boolean> {
    // This is not a test, but it creates a compile error if the component type is changed away from boolean,
    // as a reminder that tests should be added for that new component type.
    // Assigns a value
    private static final List<DataComponent<Boolean>> SHARED_COMPONENTS = List.of(
           // Code statement
           DataComponents.ENCHANTMENT_GLINT_OVERRIDE
    // End of a block/expression
    );

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<Boolean> component() {
        // Returns a value to the caller
        return SHARED_COMPONENTS.getFirst();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected List<Map.Entry<String, Boolean>> directReadWriteEntries() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                entry("true", true),
                // Code statement
                entry("false", false)
        // End of a block/expression
        );
    // End of a block/expression
    }
// End of a block/expression
}
