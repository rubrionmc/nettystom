// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Déclaration de type (classe/interface/enum/record)
record CowVariantImpl(
        // Instruction de code
        CowVariant.Model model,
        // Instruction de code
        Key assetId
// Début d'une méthode/d'un bloc
) implements CowVariant {

    // Début d'une méthode/d'un bloc
    public CowVariantImpl {
        // Builder may violate nullability constraints
        // Appelle une méthode
        Check.notNull(model, "model");
        // Appelle une méthode
        Check.notNull(assetId, "assetId");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
