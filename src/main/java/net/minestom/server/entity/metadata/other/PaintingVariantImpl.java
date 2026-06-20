// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.other;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
record PaintingVariantImpl(
        // Instruction de code
        int width,
        // Instruction de code
        int height,
        // Instruction de code
        Key assetId,
        // Annotation pour l'élément suivant
        @Nullable Component title,
        // Annotation pour l'élément suivant
        @Nullable Component author
// Début d'une méthode/d'un bloc
) implements PaintingVariant {

    // Annotation pour l'élément suivant
    @SuppressWarnings("ConstantValue") // The builder can violate the nullability constraints
    // Début d'une méthode/d'un bloc
    PaintingVariantImpl {
        // Appelle une méthode
        Check.argCondition(assetId == null, "missing asset id");
        // Appelle une méthode
        Check.argCondition(width <= 0, "width must be positive");
        // Appelle une méthode
        Check.argCondition(height <= 0, "height must be positive");
    // Fin d'un bloc/d'une expression
    }

    // BELOW ARE WORKAROUND METHODS FOR BROKEN INLINE VALUES
    // See PaintingVariant for the documentation of its brokenness. TLDR: inline values are broken.
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Either<RegistryKey<PaintingVariant>, PaintingVariant> unwrap() {
        // Renvoie une valeur à l'appelant
        return Either.left(asKey());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public RegistryKey<PaintingVariant> asKey() {
        // Renvoie une valeur à l'appelant
        return RegistryKey.unsafeOf(assetId);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isDirect() {
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable PaintingVariant asValue() {
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
