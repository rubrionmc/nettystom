// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal.tameable;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
record WolfVariantImpl(Assets assets, Assets babyAssets) implements WolfVariant {
    // Début d'une méthode/d'un bloc
    WolfVariantImpl {
        // The builder can violate the nullability constraints
        // Appelle une méthode
        Objects.requireNonNull(assets, "assets");
        // Appelle une méthode
        Objects.requireNonNull(babyAssets, "babyAssets");
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record AssetsImpl(Key wild, Key tame, Key angry) implements WolfVariant.Assets {
        // Début d'une méthode/d'un bloc
        public AssetsImpl {
            // Appelle une méthode
            Objects.requireNonNull(wild, "wild");
            // Appelle une méthode
            Objects.requireNonNull(tame, "tame");
            // Appelle une méthode
            Objects.requireNonNull(angry, "angry");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
