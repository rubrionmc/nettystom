// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
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
public class BeesTest extends AbstractItemComponentTest<List<Bee>> {
    // Assigns a value
    private static final CustomData SOME_DATA = new CustomData(CompoundBinaryTag.builder()
            // Code statement
            .putString("Id", "minecraft:bee")
            // Calls a method
            .build());

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<List<Bee>> component() {
        // Returns a value to the caller
        return DataComponents.BEES;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public List<Map.Entry<String, List<Bee>>> directReadWriteEntries() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                entry("empty", List.of()),
                // Code statement
                entry("single", List.of(new Bee(SOME_DATA, 1, 2))),
                // Code statement
                entry("multiple", List.of(new Bee(SOME_DATA, 1, 2), new Bee(SOME_DATA, 3, 4)))
        // End of a block/expression
        );
    // End of a block/expression
    }
// End of a block/expression
}
