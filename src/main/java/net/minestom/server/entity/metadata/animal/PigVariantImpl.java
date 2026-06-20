// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
public record PigVariantImpl(
        // Code statement
        PigVariant.Model model,
        // Code statement
        Key assetId,
        // Code statement
        Key babyAssetId
// Start of a method/block
) implements PigVariant {

    // Start of a method/block
    public PigVariantImpl {
        // Calls a method
        Objects.requireNonNull(model, "model");
        // Calls a method
        Objects.requireNonNull(assetId, "assetId");
        // Calls a method
        Objects.requireNonNull(babyAssetId, "babyAssetId");
    // End of a block/expression
    }
// End of a block/expression
}
