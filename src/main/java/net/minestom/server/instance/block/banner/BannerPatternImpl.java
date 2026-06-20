// Package declaration for this file
package net.minestom.server.instance.block.banner;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Type declaration (class/interface/enum/record)
record BannerPatternImpl(
        // Code statement
        Key assetId,
        // Code statement
        String translationKey
// Start of a method/block
) implements BannerPattern {

    // Annotation for the following element
    @SuppressWarnings("ConstantValue") // The builder can violate the nullability constraints
    // Start of a method/block
    BannerPatternImpl {
        // Calls a method
        Check.argCondition(assetId == null, "missing asset id");
        // Calls a method
        Check.argCondition(translationKey == null || translationKey.isEmpty(), "missing translation key");
    // End of a block/expression
    }

// End of a block/expression
}
