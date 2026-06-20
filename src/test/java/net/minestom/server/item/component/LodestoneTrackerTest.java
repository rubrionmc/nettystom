// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.coordinate.Vec;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public class LodestoneTrackerTest extends AbstractItemComponentTest<LodestoneTracker> {

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<LodestoneTracker> component() {
        // Returns a value to the caller
        return DataComponents.LODESTONE_TRACKER;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected List<Map.Entry<String, LodestoneTracker>> directReadWriteEntries() {
        // Returns a value to the caller
        return List.of(
            // Code statement
            Map.entry("tracked", new LodestoneTracker("minecraft:overworld", Vec.ZERO, true)),
            // Code statement
            Map.entry("not tracked", new LodestoneTracker("minecraft:overworld", new Vec(1, 2, 3), false))
        // End of a block/expression
        );
    // End of a block/expression
    }

// End of a block/expression
}
