// Déclaration du paquet de ce fichier
package net.minestom.server.item.armor;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Déclaration de type (classe/interface/enum/record)
record TrimPatternImpl(
        // Instruction de code
        Key assetId,
        // Instruction de code
        Component description,
        // Instruction de code
        boolean isDecal
// Début d'une méthode/d'un bloc
) implements TrimPattern {

    // Début d'une méthode/d'un bloc
    TrimPatternImpl {
        // Appelle une méthode
        Check.notNull(assetId, "missing asset id");
        // Appelle une méthode
        Check.notNull(description, "missing description");
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
