// Package declaration for this file
package net.minestom.server.entity.metadata.other;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.utils.Either;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
record PaintingVariantImpl(
        // Code statement
        int width,
        // Code statement
        int height,
        // Code statement
        Key assetId,
        // Annotation for the following element
        @Nullable Component title,
        // Annotation for the following element
        @Nullable Component author
// Start of a method/block
) implements PaintingVariant {

    // Annotation for the following element
    @SuppressWarnings("ConstantValue") // The builder can violate the nullability constraints
    // Start of a method/block
    PaintingVariantImpl {
        // Calls a method
        Check.argCondition(assetId == null, "missing asset id");
        // Calls a method
        Check.argCondition(width <= 0, "width must be positive");
        // Calls a method
        Check.argCondition(height <= 0, "height must be positive");
    // End of a block/expression
    }

    // BELOW ARE WORKAROUND METHODS FOR BROKEN INLINE VALUES
    // See PaintingVariant for the documentation of its brokenness. TLDR: inline values are broken.
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Either<RegistryKey<PaintingVariant>, PaintingVariant> unwrap() {
        // Returns a value to the caller
        return Either.left(asKey());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public RegistryKey<PaintingVariant> asKey() {
        // Returns a value to the caller
        return RegistryKey.unsafeOf(assetId);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isDirect() {
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable PaintingVariant asValue() {
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }
// End of a block/expression
}
