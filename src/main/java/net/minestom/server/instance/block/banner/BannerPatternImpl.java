// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block.banner;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Déclaration de type (classe/interface/enum/record)
record BannerPatternImpl(
        // Instruction de code
        Key assetId,
        // Instruction de code
        String translationKey
// Début d'une méthode/d'un bloc
) implements BannerPattern {

    // Annotation pour l'élément suivant
    @SuppressWarnings("ConstantValue") // The builder can violate the nullability constraints
    // Début d'une méthode/d'un bloc
    BannerPatternImpl {
        // Appelle une méthode
        Check.argCondition(assetId == null, "missing asset id");
        // Appelle une méthode
        Check.argCondition(translationKey == null || translationKey.isEmpty(), "missing translation key");
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
