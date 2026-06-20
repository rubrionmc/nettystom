// Package declaration for this file
package net.minestom.server.entity.metadata.animal.tameable;

// Import of a required class
import net.kyori.adventure.key.Key;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
record WolfVariantImpl(Assets assets, Assets babyAssets) implements WolfVariant {
    // Start of a method/block
    WolfVariantImpl {
        // The builder can violate the nullability constraints
        // Calls a method
        Objects.requireNonNull(assets, "assets");
        // Calls a method
        Objects.requireNonNull(babyAssets, "babyAssets");
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record AssetsImpl(Key wild, Key tame, Key angry) implements WolfVariant.Assets {
        // Start of a method/block
        public AssetsImpl {
            // Calls a method
            Objects.requireNonNull(wild, "wild");
            // Calls a method
            Objects.requireNonNull(tame, "tame");
            // Calls a method
            Objects.requireNonNull(angry, "angry");
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
