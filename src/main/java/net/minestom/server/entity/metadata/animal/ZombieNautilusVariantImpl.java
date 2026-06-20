// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

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
        Check.notNull(model, "Model cannot be null");
        // Appelle une méthode
        Check.notNull(assetId, "Asset ID cannot be null");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
