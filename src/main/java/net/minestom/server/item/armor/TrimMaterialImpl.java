// Déclaration du paquet de ce fichier
package net.minestom.server.item.armor;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
record TrimMaterialImpl(
        // Instruction de code
        String assetName,
        // Instruction de code
        Map<String, String> overrideArmorMaterials,
        // Instruction de code
        Component description
// Début d'une méthode/d'un bloc
) implements TrimMaterial {

    // Annotation pour l'élément suivant
    @SuppressWarnings("ConstantValue") // The builder can violate the nullability constraints
    // Début d'une méthode/d'un bloc
    TrimMaterialImpl {
        // Appelle une méthode
        Check.argCondition(assetName == null || assetName.isEmpty(), "missing asset name");
        // Appelle une méthode
        Check.argCondition(overrideArmorMaterials == null, "missing override armor materials");
        // Appelle une méthode
        Check.argCondition(description == null, "missing description");
        // Appelle une méthode
        overrideArmorMaterials = Map.copyOf(overrideArmorMaterials);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
