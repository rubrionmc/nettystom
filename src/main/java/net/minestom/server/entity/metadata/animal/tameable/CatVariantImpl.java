// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal.tameable;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Déclaration de type (classe/interface/enum/record)
record CatVariantImpl(Key assetId) implements CatVariant {

    // Début d'une méthode/d'un bloc
    public CatVariantImpl {
        // Appelle une méthode
        Check.notNull(assetId, "assetId");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
