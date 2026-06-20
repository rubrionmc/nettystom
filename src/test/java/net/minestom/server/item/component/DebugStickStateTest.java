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
public class DebugStickStateTest extends AbstractItemComponentTest<DebugStickState> {

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<DebugStickState> component() {
        // Returns a value to the caller
        return DataComponents.DEBUG_STICK_STATE;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected List<Map.Entry<String, DebugStickState>> directReadWriteEntries() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                entry("empty", new DebugStickState(Map.of())),
                // Note that an invalid block id is present. Minestom currently does not validate the block id or state value.
                // Code statement
                entry("contents", new DebugStickState(Map.of("minecraft:stone_stairs", "shape", "minecraft:nothing", "abcdef")))
        // End of a block/expression
        );
    // End of a block/expression
    }

// End of a block/expression
}
