// Package declaration for this file
package net.minestom.server.entity.metadata.animal.tameable;

// Import of a required class
import net.kyori.adventure.key.Key;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
record CatVariantImpl(Key assetId, Key babyAssetId) implements CatVariant {

    // Start of a method/block
    public CatVariantImpl {
        // Calls a method
        Objects.requireNonNull(assetId, "assetId");
        // Calls a method
        Objects.requireNonNull(babyAssetId, "babyAssetId");
    // End of a block/expression
    }
// End of a block/expression
}
