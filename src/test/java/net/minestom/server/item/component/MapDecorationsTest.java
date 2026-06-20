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
public class MapDecorationsTest extends AbstractItemComponentTest<MapDecorations> {

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<MapDecorations> component() {
        // Returns a value to the caller
        return DataComponents.MAP_DECORATIONS;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected List<Map.Entry<String, MapDecorations>> directReadWriteEntries() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                Map.entry("empty", new MapDecorations(Map.of())),
                // Code statement
                Map.entry("single", new MapDecorations(Map.of("id", new MapDecorations.Entry("type", 1.0, 2.0, 3)))),
                // Code statement
                Map.entry("multiple", new MapDecorations(Map.of(
                        // Code statement
                        "id1", new MapDecorations.Entry("type1", 1.0, 2.0, 3),
                        // Code statement
                        "id2", new MapDecorations.Entry("type2", 4.0, 5.0, 6)
                // Code statement
                )))
        // End of a block/expression
        );
    // End of a block/expression
    }

// End of a block/expression
}
