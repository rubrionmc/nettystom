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
public class MapPostProcessingTest extends AbstractItemComponentTest<MapPostProcessing> {
    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<MapPostProcessing> component() {
        // Returns a value to the caller
        return DataComponents.MAP_POST_PROCESSING;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected List<Map.Entry<String, MapPostProcessing>> directReadWriteEntries() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                Map.entry("lock", MapPostProcessing.LOCK),
                // Code statement
                Map.entry("scale", MapPostProcessing.SCALE)
        // End of a block/expression
        );
    // End of a block/expression
    }
// End of a block/expression
}
