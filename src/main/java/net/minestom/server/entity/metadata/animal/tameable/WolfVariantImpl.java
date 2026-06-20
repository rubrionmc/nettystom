// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal.tameable;

// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Déclaration de type (classe/interface/enum/record)
record WolfVariantImpl(Assets assets) implements WolfVariant {
    // Début d'une méthode/d'un bloc
    WolfVariantImpl {
        // The builder can violate the nullability constraints
        // Appelle une méthode
        Check.notNull(assets, "missing assets Asset");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
