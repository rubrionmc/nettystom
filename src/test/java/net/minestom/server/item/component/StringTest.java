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
public class StringTest extends AbstractItemComponentTest<String> {
    // This is not a test, but it creates a compile error if the component type is changed away,
    // as a reminder that tests should be added for that new component type.
    // Assigns a value
    private static final List<DataComponent<String>> SHARED_COMPONENTS = List.of(
           // Code statement
           DataComponents.NOTE_BLOCK_SOUND
    // End of a block/expression
    );

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<String> component() {
        // Returns a value to the caller
        return SHARED_COMPONENTS.getFirst();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected List<Map.Entry<String, String>> directReadWriteEntries() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                entry("instance", "hello, world")
        // End of a block/expression
        );
    // End of a block/expression
    }
// End of a block/expression
}
