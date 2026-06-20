// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal.tameable;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
record CatVariantImpl(Key assetId, Key babyAssetId) implements CatVariant {

    // Début d'une méthode/d'un bloc
    public CatVariantImpl {
        // Appelle une méthode
        Objects.requireNonNull(assetId, "assetId");
        // Appelle une méthode
        Objects.requireNonNull(babyAssetId, "babyAssetId");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
