// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
record CowVariantImpl(
        // Instruction de code
        CowVariant.Model model,
        // Instruction de code
        Key assetId,
        // Instruction de code
        Key babyAssetId
// Début d'une méthode/d'un bloc
) implements CowVariant {

    // Début d'une méthode/d'un bloc
    public CowVariantImpl {
        // Appelle une méthode
        Objects.requireNonNull(model, "model");
        // Appelle une méthode
        Objects.requireNonNull(assetId, "assetId");
        // Appelle une méthode
        Objects.requireNonNull(babyAssetId, "babyAssetId");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
