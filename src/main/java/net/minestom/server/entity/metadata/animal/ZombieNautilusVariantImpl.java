// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
record ZombieNautilusVariantImpl(
        // Instruction de code
        Model model,
        // Instruction de code
        Key assetId
// Début d'une méthode/d'un bloc
) implements ZombieNautilusVariant {

    // Début d'une méthode/d'un bloc
    public ZombieNautilusVariantImpl {
        // Appelle une méthode
        Objects.requireNonNull(model, "Model cannot be null");
        // Appelle une méthode
        Objects.requireNonNull(assetId, "Asset ID cannot be null");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
