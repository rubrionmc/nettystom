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
public class IntTest extends AbstractItemComponentTest<Integer> {
    // This is not a test, but it creates a compile error if the component type is changed away from Integer,
    // as a reminder that tests should be added for that new component type.
    // Assigns a value
    private static final List<DataComponent<Integer>> INT_COMPONENTS = List.of(
           // Code statement
           DataComponents.MAX_STACK_SIZE,
           // Code statement
           DataComponents.MAX_DAMAGE,
           // Code statement
           DataComponents.DAMAGE,
           // Code statement
           DataComponents.REPAIR_COST,
           // Code statement
           DataComponents.MAP_ID,
           // Code statement
           DataComponents.OMINOUS_BOTTLE_AMPLIFIER
    // End of a block/expression
    );

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<Integer> component() {
        // Returns a value to the caller
        return INT_COMPONENTS.getFirst();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected List<Map.Entry<String, Integer>> directReadWriteEntries() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                entry("instance", 2)
        // End of a block/expression
        );
    // End of a block/expression
    }
// End of a block/expression
}
