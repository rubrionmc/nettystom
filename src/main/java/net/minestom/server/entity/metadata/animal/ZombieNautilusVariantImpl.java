// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.kyori.adventure.key.Key;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
record ZombieNautilusVariantImpl(
        // Code statement
        Model model,
        // Code statement
        Key assetId
// Start of a method/block
) implements ZombieNautilusVariant {

    // Start of a method/block
    public ZombieNautilusVariantImpl {
        // Calls a method
        Objects.requireNonNull(model, "Model cannot be null");
        // Calls a method
        Objects.requireNonNull(assetId, "Asset ID cannot be null");
    // End of a block/expression
    }
// End of a block/expression
}
