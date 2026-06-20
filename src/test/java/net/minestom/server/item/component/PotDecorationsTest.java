// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.item.Material;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public class PotDecorationsTest extends AbstractItemComponentTest<PotDecorations> {
    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<PotDecorations> component() {
        // Returns a value to the caller
        return DataComponents.POT_DECORATIONS;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected List<Map.Entry<String, PotDecorations>> directReadWriteEntries() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                Map.entry("instance", PotDecorations.EMPTY),
                // Code statement
                Map.entry("one", new PotDecorations(Material.DIAMOND, PotDecorations.DEFAULT_ITEM, PotDecorations.DEFAULT_ITEM, PotDecorations.DEFAULT_ITEM)),
                // Code statement
                Map.entry("two", new PotDecorations(Material.DIAMOND, Material.DIAMOND, PotDecorations.DEFAULT_ITEM, PotDecorations.DEFAULT_ITEM)),
                // Code statement
                Map.entry("three", new PotDecorations(Material.DIAMOND, Material.DIAMOND, Material.DIAMOND, PotDecorations.DEFAULT_ITEM)),
                // Code statement
                Map.entry("four", new PotDecorations(Material.DIAMOND, Material.DIAMOND, Material.DIAMOND, Material.DIAMOND))
        // End of a block/expression
        );
    // End of a block/expression
    }
// End of a block/expression
}
