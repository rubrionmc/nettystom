// Package declaration for this file
package net.minestom.server.item.armor;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
record TrimMaterialImpl(
        // Code statement
        String assetName,
        // Code statement
        Map<String, String> overrideArmorMaterials,
        // Code statement
        Component description
// Start of a method/block
) implements TrimMaterial {

    // Annotation for the following element
    @SuppressWarnings("ConstantValue") // The builder can violate the nullability constraints
    // Start of a method/block
    TrimMaterialImpl {
        // Calls a method
        Check.argCondition(assetName == null || assetName.isEmpty(), "missing asset name");
        // Calls a method
        Check.argCondition(overrideArmorMaterials == null, "missing override armor materials");
        // Calls a method
        Check.argCondition(description == null, "missing description");
        // Calls a method
        overrideArmorMaterials = Map.copyOf(overrideArmorMaterials);
    // End of a block/expression
    }

// End of a block/expression
}
