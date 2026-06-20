// Déclaration du paquet de ce fichier
package net.minestom.server.entity.damage;

// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
record DamageTypeImpl(
        // Instruction de code
        String messageId,
        // Instruction de code
        String scaling,
        // Instruction de code
        float exhaustion,
        // Annotation pour l'élément suivant
        @Nullable String effects,
        // Annotation pour l'élément suivant
        @Nullable String deathMessageType
// Début d'une méthode/d'un bloc
) implements DamageType {

    // Annotation pour l'élément suivant
    @SuppressWarnings("ConstantValue") // The builder can violate the nullability constraints
    // Début d'une méthode/d'un bloc
    DamageTypeImpl {
        // Appelle une méthode
        Check.argCondition(messageId == null || messageId.isEmpty(), "missing message id");
        // Appelle une méthode
        Check.argCondition(scaling == null || scaling.isEmpty(), "missing scaling");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}