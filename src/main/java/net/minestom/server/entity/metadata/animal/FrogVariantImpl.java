// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.kyori.adventure.key.Key;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
public record FrogVariantImpl(
        // Code statement
        Key assetId
// Start of a method/block
) implements FrogVariant {

    // Start of a method/block
    public FrogVariantImpl {
        // Builder may violate nullability constraints
        // Calls a method
        Objects.requireNonNull(assetId, "asset_id");
    // End of a block/expression
    }
// End of a block/expression
}
