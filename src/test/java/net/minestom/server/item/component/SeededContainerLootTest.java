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

// Type declaration (class/interface/enum/record)
public class SeededContainerLootTest extends AbstractItemComponentTest<SeededContainerLoot> {

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<SeededContainerLoot> component() {
        // Returns a value to the caller
        return DataComponents.CONTAINER_LOOT;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected List<Map.Entry<String, SeededContainerLoot>> directReadWriteEntries() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                Map.entry("instance", new SeededContainerLoot("loot_table", 1234567890L))
        // End of a block/expression
        );
    // End of a block/expression
    }

// End of a block/expression
}
