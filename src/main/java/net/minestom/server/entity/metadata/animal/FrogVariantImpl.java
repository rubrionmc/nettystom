// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Déclaration de type (classe/interface/enum/record)
public record FrogVariantImpl(
        // Instruction de code
        Key assetId
// Début d'une méthode/d'un bloc
) implements FrogVariant {

    // Début d'une méthode/d'un bloc
    public FrogVariantImpl {
        // Builder may violate nullability constraints
        // Appelle une méthode
        Check.notNull(assetId, "asset_id");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
