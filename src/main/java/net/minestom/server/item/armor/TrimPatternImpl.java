// Package declaration for this file
package net.minestom.server.item.armor;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.text.Component;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
record TrimPatternImpl(
        // Code statement
        Key assetId,
        // Code statement
        Component description,
        // Code statement
        boolean isDecal
// Start of a method/block
) implements TrimPattern {

    // Start of a method/block
    TrimPatternImpl {
        // Calls a method
        Objects.requireNonNull(assetId, "missing asset id");
        // Calls a method
        Objects.requireNonNull(description, "missing description");
    // End of a block/expression
    }

// End of a block/expression
}
